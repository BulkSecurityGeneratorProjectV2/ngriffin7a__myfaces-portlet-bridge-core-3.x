/*
 * Copyright 2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.myfaces.webapp.webxml;

import net.sourceforge.myfaces.util.xml.MyFacesErrorHandler;
import net.sourceforge.myfaces.util.xml.XmlUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class WebXmlParser
{
    private static final Log log = LogFactory.getLog(WebXmlParser.class);

    /*
    private static final String JAXP_SCHEMA_LANGUAGE =
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    private static final String W3C_XML_SCHEMA =
        "http://www.w3.org/2001/XMLSchema";
        */

    private static final String WEB_XML_PATH = "/WEB-INF/web.xml";
    private static final String DEFAULT_ENCODING = "ISO-8859-1";

    private static final String WEB_APP_2_2_SYSTEM_ID = "http://java.sun.com/dtd/web-app_2_2.dtd";
    private static final String WEB_APP_2_2_RESOURCE  = "javax/servlet/resources/web-app_2_2.dtd";

    private static final String WEB_APP_2_3_SYSTEM_ID = "http://java.sun.com/dtd/web-app_2_3.dtd";
    private static final String WEB_APP_2_3_RESOURCE  = "javax/servlet/resources/web-app_2_3.dtd";


    private ExternalContext _context;
    private WebXml _webXml;

    public WebXmlParser(ExternalContext context)
    {
        _context = context;
    }

    public WebXml parse()
    {
        _webXml = new WebXml();

        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setIgnoringComments(true);
            dbf.setNamespaceAware(true);
//            dbf.setValidating(true); // Needed false to allow use of version 2.4 spec (there should be a way to do this better)
//            dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new _EntityResolver());
            db.setErrorHandler(new MyFacesErrorHandler(log));

            InputSource is = createContextInputSource(null, WEB_XML_PATH);

            Document document = db.parse(is);

            Element webAppElem = document.getDocumentElement();
            if (webAppElem == null ||
                !webAppElem.getNodeName().equals("web-app"))
            {
                throw new FacesException("No valid web-app root element found!");
            }

            readWebApp(webAppElem);

            return _webXml;
        }
        catch (Exception e)
        {
            log.fatal("Unable to parse web.xml", e);
            throw new FacesException(e);
        }
    }


    private InputSource createContextInputSource(String publicId, String systemId)
    {
        InputStream inStream = _context.getResourceAsStream(systemId);
        if (inStream == null)
        {
            // there is no such entity
            return null;
        }
        InputSource is = new InputSource(inStream);
        is.setPublicId(publicId);
        is.setSystemId(systemId);
        is.setEncoding(DEFAULT_ENCODING);
        return is;
    }

    private InputSource createClassloaderInputSource(String publicId, String systemId)
    {
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(systemId);
        if (inStream == null)
        {
            // there is no such entity
            return null;
        }
        InputSource is = new InputSource(inStream);
        is.setPublicId(publicId);
        is.setSystemId(systemId);
        is.setEncoding(DEFAULT_ENCODING);
        return is;
    }

    private class _EntityResolver implements EntityResolver
    {
        public InputSource resolveEntity(String publicId, String systemId) throws IOException
        {
            if (systemId == null)
            {
                throw new UnsupportedOperationException("systenId must not be null");
            }

            if (systemId.equals(WebXmlParser.WEB_APP_2_2_SYSTEM_ID))
            {
                //Load DTD from servlet.jar
                return createClassloaderInputSource(publicId, WebXmlParser.WEB_APP_2_2_RESOURCE);
            }
            else if (systemId.equals(WebXmlParser.WEB_APP_2_3_SYSTEM_ID))
            {
                //Load DTD from servlet.jar
                return createClassloaderInputSource(publicId, WebXmlParser.WEB_APP_2_3_RESOURCE);
            }
            else
            {
                //Load additional entities from web context
                return createContextInputSource(publicId, systemId);
            }
        }

    }


    private void readWebApp(Element webAppElem)
    {
        NodeList nodeList = webAppElem.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
        {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                if (n.getNodeName().equals("servlet"))
                {
                    readServlet((Element)n);
                }
                if (n.getNodeName().equals("servlet-mapping"))
                {
                    readServletMapping((Element)n);
                }
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Ignored node '" + n.getNodeName() + "' of type " + n.getNodeType());
            }
        }
    }

    private void readServlet(Element servletElem)
    {
        String servletName = null;
        String servletClass = null;
        NodeList nodeList = servletElem.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
        {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                if (n.getNodeName().equals("servlet-name"))
                {
                    servletName = XmlUtils.getElementText((Element)n);
                }
                else if (n.getNodeName().equals("servlet-class"))
                {
                    servletClass = XmlUtils.getElementText((Element)n).trim();
                }
                else if (n.getNodeName().equals("load-on-startup"))
                {
                    //ignore
                }
                else
                {
                    if (log.isWarnEnabled()) log.warn("Ignored element '" + n.getNodeName() + "' as child of '" + servletElem.getNodeName() + "'.");
                }
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Ignored node '" + n.getNodeName() + "' of type " + n.getNodeType());
            }
        }
        _webXml.addServlet(servletName, servletClass);
    }


    private void readServletMapping(Element servletMappingElem)
    {
        String servletName = null;
        String urlPattern = null;
        NodeList nodeList = servletMappingElem.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
        {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                if (n.getNodeName().equals("servlet-name"))
                {
                    servletName = net.sourceforge.myfaces.util.xml.XmlUtils.getElementText((Element)n);
                }
                else if (n.getNodeName().equals("url-pattern"))
                {
                    urlPattern = net.sourceforge.myfaces.util.xml.XmlUtils.getElementText((Element)n).trim();
                }
                else
                {
                    if (log.isWarnEnabled()) log.warn("Ignored element '" + n.getNodeName() + "' as child of '" + servletMappingElem.getNodeName() + "'.");
                }
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Ignored node '" + n.getNodeName() + "' of type " + n.getNodeType());
            }
        }
        urlPattern = urlPattern.trim();
        _webXml.addServletMapping(servletName, urlPattern);
    }

}
