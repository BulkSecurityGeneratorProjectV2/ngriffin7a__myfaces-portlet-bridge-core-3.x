/**
 * MyFaces - the free JSF implementation
 * Copyright (C) 2003, 2004  The MyFaces Team (http://myfaces.sourceforge.net)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package net.sourceforge.myfaces.application.jsp;

import net.sourceforge.myfaces.renderkit.html.state.StateRenderer;
import net.sourceforge.myfaces.webapp.webxml.ServletMapping;
import net.sourceforge.myfaces.webapp.webxml.WebXml;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * DOCUMENT ME!
 * 
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JspViewHandlerImpl
    implements ViewHandler
{
    private static final Log log = LogFactory.getLog(JspViewHandlerImpl.class);

    private StateManager _stateManager;

    public JspViewHandlerImpl()
    {
        _stateManager = new JspStateManagerImpl();
        if (log.isTraceEnabled()) log.trace("New ViewHandler instance created");
    }

    public UIViewRoot createView(FacesContext facesContext, String viewId)
    {
        UIViewRoot uiViewRoot = new UIViewRoot();
        uiViewRoot.setViewId(viewId);
        uiViewRoot.setLocale(calculateLocale(facesContext));
        if (log.isTraceEnabled()) log.trace("Created view " + viewId);
        return uiViewRoot;
    }

    public UIViewRoot restoreView(FacesContext facesContext, String viewId)
    {
        UIViewRoot viewRoot = getStateManager().restoreView(facesContext, viewId);
        handleCharacterEncoding(facesContext);
        return viewRoot;
    }

    /**
     * Find character encoding and examine Content-Type header as stated in Spec. 2.5.1.2
     * @param facesContext
     */
    private void handleCharacterEncoding(FacesContext facesContext)
    {
        ExternalContext externalContext = facesContext.getExternalContext();
        String characterEncoding = null;

        String contentType = (String)externalContext.getRequestHeaderMap().get("Content-Type");
        if (contentType != null)
        {
            int charsetFind = contentType.indexOf("charset=");
            if (charsetFind != -1)
            {
                if (charsetFind == 0)
                {
                    //charset at beginning of Content-Type, curious
                    characterEncoding = contentType.substring(8);
                }
                else
                {
                    char charBefore = contentType.charAt(charsetFind - 1);
                    if (charBefore == ';' || Character.isWhitespace(charBefore))
                    {
                        //Correct charset after mime type
                        characterEncoding = contentType.substring(charsetFind + 8);
                    }
                }
                if (log.isDebugEnabled()) log.debug("Incoming request has Content-Type header with character encoding " + characterEncoding);
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Incoming request has Content-Type header without character encoding: " + contentType);
            }
        }
        else
        {
            if (log.isDebugEnabled()) log.debug("Incoming request has no Content-Type header.");
        }

        if (characterEncoding == null)
        {
            Map sessionMap = externalContext.getSessionMap();
            if (sessionMap != null)
            {
                characterEncoding = (String)sessionMap.get(ViewHandler.CHARACTER_ENCODING_KEY);
                if (log.isDebugEnabled()) log.debug("Got character encoding from session.");
            }
        }

        if (characterEncoding != null)
        {
            Object request = externalContext.getRequest();
            if (request instanceof ServletRequest)
            {
                try
                {
                    ((ServletRequest)request).setCharacterEncoding(characterEncoding);
                }
                catch (UnsupportedEncodingException e)
                {
                    if (log.isWarnEnabled()) log.warn("Request does not support character encoding " + characterEncoding);
                }
            }
            else
            {
                log.error("Request of type " + request.getClass().getName() + " not supported by ViewHandler " + getClass().getName() + ": Could not set character encoding!");
            }
        }
    }


    public Locale calculateLocale(FacesContext facesContext)
    {
        //TODO: ExternalContext.getLocales()
        Enumeration locales = ((ServletRequest)facesContext.getExternalContext().getRequest()).getLocales();
        while (locales.hasMoreElements())
        {
            Locale locale = (Locale) locales.nextElement();
            for (Iterator it = facesContext.getApplication().getSupportedLocales(); it.hasNext();)
            {
                Locale supportLocale = (Locale)it.next();
                // higher priority to a langauage match over an exact match
                // that occures further down (see Jstl Reference 1.0 8.3.1)
                if (locale.getLanguage().equals(supportLocale.getLanguage()) &&
                    (supportLocale.getCountry() == null ||
                    supportLocale.getCountry().length() == 0))
                {
                    return locale;
                }
                else if (supportLocale.equals(locale))
                {
                    return locale;
                }
            }
        }

        Locale locale = facesContext.getApplication().getDefaultLocale();
        return locale != null ? locale : Locale.getDefault();
    }

    public StateManager getStateManager()
    {
        return _stateManager;
    }

    public String getViewIdPath(FacesContext facescontext, String viewId)
    {
        if (viewId == null)
        {
            log.error("ViewId must not be null");
            throw new NullPointerException("ViewId must not be null");
        }
        if (!viewId.startsWith("/"))
        {
            log.error("ViewId must start with '/' (viewId = " + viewId + ")");
            throw new IllegalArgumentException("ViewId must start with '/' (viewId = " + viewId + ")");
        }

        ExternalContext externalContext = facescontext.getExternalContext();
        ServletMapping servletMapping = getServletMapping(externalContext);

        if (servletMapping.isExtensionMapping())
        {
            String urlpattern = servletMapping.getUrlPattern();
            if (urlpattern.startsWith("*"))
            {
                urlpattern = urlpattern.substring(1, urlpattern.length());
            }
            // extension mapping
            if (viewId.endsWith(urlpattern))
            {
                return viewId;
            }
            else
            {
                int idx = viewId.lastIndexOf(".");
                if (idx >= 0)
                {
                    return viewId.substring(0, idx) + urlpattern;
                }
                else
                {
                    return viewId + urlpattern;
                }

            }
        }
        else
        {
            String urlpattern = servletMapping.getUrlPattern();
            if (urlpattern.endsWith("/*"))
            {
                urlpattern = urlpattern.substring(0, urlpattern.length() - 2);
            }
            // prefix mapping
            return urlpattern + viewId;
        }
    }

    private static ServletMapping getServletMapping(ExternalContext externalContext)
    {
        String servletPath = externalContext.getRequestServletPath();
        String requestPathInfo = externalContext.getRequestPathInfo();

        WebXml webxml = WebXml.getWebXml(externalContext);
        List mappings = webxml.getFacesServletMappings();

        boolean isExtensionMapping = requestPathInfo == null;

        for (int i = 0, size = mappings.size(); i < size; i++)
        {
            ServletMapping servletMapping = (ServletMapping) mappings.get(i);
            if (servletMapping.isExtensionMapping() == isExtensionMapping)
            {
                String urlpattern = servletMapping.getUrlPattern();
                if (isExtensionMapping)
                {
                    String extension = urlpattern.substring(1, urlpattern.length());
                    if (servletPath.endsWith(extension))
                    {
                        return servletMapping;
                    }
                }
                else
                {
                    urlpattern = urlpattern.substring(0, urlpattern.length() - 2);
                    if (servletPath.equals(urlpattern))
                    {
                        return servletMapping;
                    }
                }
            }
        }
        log.error("could not find pathMapping for servletPath = " + servletPath +
                  " requestPathInfo = " + requestPathInfo);
        throw new IllegalArgumentException("could not find pathMapping for servletPath = " + servletPath +
                  " requestPathInfo = " + requestPathInfo);
    }

    public void renderView(FacesContext facesContext, UIViewRoot viewToRender) throws IOException, FacesException
    {
        if (viewToRender == null)
        {
            log.fatal("viewToRender must not be null");
            throw new NullPointerException("viewToRender must not be null");
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        //Since this is a JSP specific implementation, we are allowed to cast:
        HttpServletRequest servletRequest = (HttpServletRequest)externalContext.getRequest();

        String viewId = facesContext.getViewRoot().getId();
        String uri = getViewIdPath(facesContext, viewId);
        externalContext.dispatchMessage(uri);


        //forward request to JSP page
        //ServletMappingFactory smf = MyFacesFactoryFinder.getServletMappingFactory(externalContext);
        //ServletMapping sm = smf.getServletMapping((ServletContext)externalContext.getContext());
        //String forwardURL = sm.mapViewIdToFilename((ServletContext)externalContext.getContext(),
        //                                           viewToRender.getViewId());

        //RequestDispatcher requestDispatcher
        //    = servletRequest.getRequestDispatcher(forwardURL);
        /*
        try
        {
            //requestDispatcher.forward(servletRequest,
                                      (ServletResponse)facesContext.getExternalContext().getResponse());
        }
        catch(IOException ioe)
        {
            log.error("IOException in method renderView of class " + this.getClass().getName(), ioe);
            throw new IOException(ioe.getMessage());
        }
        catch(ServletException se)
        {
            log.error("ServletException in method renderView of class " + this.getClass().getName(), se);
            throw new FacesException(se.getMessage(), se);
        }
        */

    }

    public void writeState(FacesContext facescontext) throws IOException
    {
        // TODO: implement
        throw new UnsupportedOperationException("not yet implemented.");
    }
}
