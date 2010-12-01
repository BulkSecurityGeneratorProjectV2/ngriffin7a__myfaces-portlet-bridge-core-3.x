/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.spi;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFWebConfigParam;
import org.apache.myfaces.shared_impl.util.ClassUtils;
import org.apache.myfaces.spi.impl.DefaultServiceProviderFinder;

/**
 * 
 * @author Leonardo Uribe
 * @since 2.0.3
 *
 */
public class ServiceProviderFinderFactory
{

    private final static String SERVICE_PROVIDER_KEY = "org.apache.myfaces.spi.ServiceProviderFinder";
    
    @JSFWebConfigParam(since = "2.0.3", desc = "Class name of a custom ServiceProviderFinder implementation.")
    private static final String SERVICE_PROVIDER_FINDER_PARAM = "org.apache.myfaces.SERVICE_PROVIDER_FINDER";
    

    /**
     * 
     * @param ectx
     * @return
     */
    public static ServiceProviderFinder getServiceLoaderFinder(ExternalContext ectx)
    {
        ServiceProviderFinder slp = (ServiceProviderFinder) ectx.getApplicationMap().get(SERVICE_PROVIDER_KEY);
        if (slp == null)
        {
            slp = _getServiceLoaderFinderFromInitParam(ectx);
            if (slp == null)
            {
                slp = new DefaultServiceProviderFinder();
                setServiceLoaderFinder(ectx, slp);
            }
        }
        return slp;
    }

    
    /**
     * Set a ServiceLoaderFinder to the current application, to locate 
     * SPI service providers used by MyFaces.  
     * 
     * This method should be called before the web application is initialized,
     * specifically before AbstractFacesInitializer.initFaces(ServletContext)
     * otherwise it will have no effect.
     * 
     * @param ectx
     * @param slp
     */
    public static void setServiceLoaderFinder(ExternalContext ectx, ServiceProviderFinder slp)
    {
        ectx.getApplicationMap().put(SERVICE_PROVIDER_KEY, slp);
    }
    
    public static void setServiceLoaderFinder(ServletContext ctx, ServiceProviderFinder slp)
    {
        ctx.setAttribute(SERVICE_PROVIDER_KEY, slp);
    }

    /**
     * Gets a ServiceLoaderFinder from the web.xml config param.
     * @param context
     * @return
     */
    private static ServiceProviderFinder _getServiceLoaderFinderFromInitParam(ExternalContext context)
    {
        String initializerClassName = context.getInitParameter(SERVICE_PROVIDER_FINDER_PARAM);
        if (initializerClassName != null)
        {
            try
            {
                // get Class object
                Class<?> clazz = ClassUtils.classForName(initializerClassName);
                if (!ServiceProviderFinder.class.isAssignableFrom(clazz))
                {
                    throw new FacesException("Class " + clazz 
                            + " does not implement FacesInitializer");
                }
                
                // create instance and return it
                return (ServiceProviderFinder) ClassUtils.newInstance(clazz);
            }
            catch (ClassNotFoundException cnfe)
            {
                throw new FacesException("Could not find class of specified FacesInitializer", cnfe);
            }
        }
        return null;
    }
    
}
