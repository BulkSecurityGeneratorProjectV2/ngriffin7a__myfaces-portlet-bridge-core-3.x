/*
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

package net.sourceforge.myfaces.confignew;

import java.io.InputStream;
import java.util.List;
import java.util.Iterator;

import javax.faces.application.ViewHandler;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.StateManager;
import javax.faces.application.NavigationHandler;
import javax.faces.FactoryFinder;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;
import javax.faces.event.PhaseListener;

import org.apache.cactus.ServletTestCase;
import net.sourceforge.myfaces.confignew.impl.digester.DigesterFacesConfigUnmarshallerImpl;
import net.sourceforge.myfaces.confignew.impl.digester.elements.*;
import net.sourceforge.myfaces.context.servlet.ServletExternalContextImpl;
import net.sourceforge.myfaces.application.jsp.JspViewHandlerImpl;
import net.sourceforge.myfaces.application.jsp.JspStateManagerImpl;
import net.sourceforge.myfaces.application.NavigationHandlerImpl;
import net.sourceforge.myfaces.el.PropertyResolverImpl;
import net.sourceforge.myfaces.el.VariableResolverImpl;


public class ConfigurationCactusTest extends ServletTestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }


    protected void tearDown() throws Exception
    {
        super.tearDown();
    }


    public ConfigurationCactusTest(String name)
    {
        super(name);
    }


    public void testConfiguration() throws Exception
    {
        ServletExternalContextImpl externalContext = new ServletExternalContextImpl(request.getSession().getServletContext(), null, null);

        // test decorator pattern support

        // application factory
        ApplicationFactory applicationFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        assertEquals(TestApplicationFactory.class, applicationFactory.getClass());

        javax.faces.application.Application application = applicationFactory.getApplication();

        // view handler
        ViewHandler viewHandler = application.getViewHandler();

        assertEquals(TestViewHandlerB.class, viewHandler.getClass());
        assertEquals( TestViewHandlerA.class, ((TestViewHandlerB)viewHandler).getDelegate().getClass());
        assertEquals( JspViewHandlerImpl.class, ((TestViewHandlerA)((TestViewHandlerB)viewHandler).getDelegate()).getDelegate().getClass());

        // navigation handler
        NavigationHandler navigationHandler = application.getNavigationHandler();

        assertEquals(TestNavigationHandler.class, navigationHandler.getClass());
        assertEquals( NavigationHandlerImpl.class, ((TestNavigationHandler)navigationHandler).getDelegate().getClass());

        // state manager
        StateManager stateManager = application.getStateManager();

        assertEquals(TestStateManager.class, stateManager.getClass());
        assertEquals(JspStateManagerImpl.class, ((TestStateManager)stateManager).getDelegate().getClass());

        // PropvertyResolver
        PropertyResolver propertyResolver = application.getPropertyResolver();

        assertEquals(TestPropertyResolver.class, propertyResolver.getClass());
        assertEquals(PropertyResolverImpl.class, ((TestPropertyResolver)propertyResolver).getDelegate().getClass());

        // VariableResolver
        VariableResolver variableResolver = application.getVariableResolver();

        assertEquals(TestVariableResolver.class, variableResolver.getClass());
        assertEquals(VariableResolverImpl.class, ((TestVariableResolver)variableResolver).getDelegate().getClass());


        // RuntimeConfig

        // LifecycleConfig
        LifecycleConfig lifecycleConfig = LifecycleConfig.getCurrentInstance(externalContext);

        assertEquals(2, lifecycleConfig.getLifecyclePhaseListeners().size());
        for (Iterator iterator = lifecycleConfig.getLifecyclePhaseListeners().iterator(); iterator.hasNext();)
        {
            PhaseListener listener = (PhaseListener) iterator.next();
            assertTrue(listener instanceof TestPhaseListenerA || listener instanceof TestPhaseListenerB);
        }
    }

}
