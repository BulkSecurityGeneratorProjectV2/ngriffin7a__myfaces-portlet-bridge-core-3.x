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
package org.apache.myfaces.context.portlet;

import java.util.Enumeration;
import javax.portlet.PortletRequest;
import org.apache.myfaces.context.servlet.AbstractAttributeMap;


/**
 * PortletRequest attributes Map.
 *
 * @author  Stan Silvert (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RequestMap extends AbstractAttributeMap
{
    final PortletRequest _portletRequest;

    RequestMap(PortletRequest portletRequest)
    {
        _portletRequest = portletRequest;
    }

    protected Object getAttribute(String key)
    {
        return _portletRequest.getAttribute(key);
    }

    protected void setAttribute(String key, Object value)
    {
        _portletRequest.setAttribute(key, value);
    }

    protected void removeAttribute(String key)
    {
        _portletRequest.removeAttribute(key);
    }

    protected Enumeration getAttributeNames()
    {
        return _portletRequest.getAttributeNames();
    }
}
