/**
 * MyFaces - the free JSF implementation
 * Copyright (C) 2003  The MyFaces Team (http://myfaces.sourceforge.net)
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
package net.sourceforge.myfaces.taglib;

import net.sourceforge.myfaces.component.UIForm;
import net.sourceforge.myfaces.component.CommonComponentAttributes;
import net.sourceforge.myfaces.renderkit.html.FormRenderer;
import net.sourceforge.myfaces.renderkit.attr.*;

import javax.faces.component.UIComponent;


/**
 * see "form" tag in myfaces_html.tld
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FormTag
    extends MyFacesTag
    implements CommonComponentAttributes,
               CommonRendererAttributes,
               HTMLUniversalAttributes,
               HTMLEventHandlerAttributes,
               HTMLFormAttributes,
               UserRoleAttributes,
               FormRendererAttributes
{
    //MyFaces tag extensions:
    public UIComponent createComponent()
    {
        return new UIForm();
    }

    public String getRendererType()
    {
        return FormRenderer.TYPE;
    }

    // UIComponent attributes --> already implemented in MyFacesTag

    // UIForm attributes

    public void setFormName(String v)
    {
        setComponentProperty(UIForm.FORM_NAME_ATTR, v);
    }

    public void setFormClass(String v)
    {
        setRendererAttribute(FORM_CLASS_ATTR, v);
    }


    // HTML universal attributes --> already implemented in MyFacesTag

    // HTML event handler attributes --> already implemented in MyFacesTag

    // HTML form attributes

    public void setAccept(String value)
    {
        setRendererAttribute(ACCEPT_ATTR, value);
    }

    public void setAcceptCharset(String value)
    {
        setRendererAttribute(ACCEPT_CHARSET_ATTR, value);
    }

    public void setEnctype(String value)
    {
        setRendererAttribute(ENCTYPE_ATTR, value);
    }

    public void setOnreset(String value)
    {
        setRendererAttribute(ONRESET_ATTR, value);
    }

    public void setOnsubmit(String value)
    {
        setRendererAttribute(ONSUMBIT_ATTR, value);
    }

    public void setTarget(String value)
    {
        setRendererAttribute(TARGET_ATTR, value);
    }

    // Form Renderer attributes --> no attributes yet

    // key and bundle attributes --> already implemented in MyFacesTag

    // user role attributes --> already implemented in MyFacesTag

}
