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

import net.sourceforge.myfaces.component.CommonComponentAttributes;
import net.sourceforge.myfaces.renderkit.attr.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.FacesBodyTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

/**
 * BodyContent is automatically added as an attribute ({@link #BODY_CONTENT_ATTR)
 * to the associated component. This is a convenient way to get the
 * BodyContent in the encodeEnd of the corresponding Renderer.
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class MyFacesBodyTag
    extends FacesBodyTag
    implements MyFacesTagBaseIF,
               CommonComponentAttributes,
               CommonRendererAttributes,
               HTMLUniversalAttributes,
               HTMLEventHandlerAttributes,
               KeyBundleAttributes,
               UserRoleAttributes
{
    public static final String BODY_CONTENT_ATTR
        = MyFacesBodyTag.class.getName() + ".BODY_CONTENT";

    protected MyFacesTagHelper _helper;

    public MyFacesBodyTag()
    {
        super();
        _helper = new MyFacesTagHelper(this);
    }

    public int getDoStartValue() throws JspException
    {
        return BodyTag.EVAL_BODY_BUFFERED;
    }


    public int doEndTag() throws JspException
    {
        try
        {
            UIComponent comp = getComponent();
            comp.setAttribute(BODY_CONTENT_ATTR, getBodyContent());
            int ret = super.doEndTag();
            comp.setAttribute(BODY_CONTENT_ATTR, null);
            return ret;
        }
        finally
        {
            _helper.release();
            id = null;
            modelReference = null;
            created = false;
        }
    }

    public int getDoEndValue() throws JspException
    {
        return Tag.EVAL_PAGE;
    }


    public void release()
    {
        super.release();
        _helper.release();
    }


    //Iteration Tag support
    public int getDoAfterBodyValue() throws JspException
    {
        return Tag.SKIP_BODY;
    }


    //subclass helpers
    public void setPageContext(PageContext pageContext)
    {
        super.setPageContext(pageContext);
        _helper.setPageContext(pageContext);
    }

    protected PageContext getPageContext()
    {
        return _helper.getPageContext();
    }

    protected FacesContext getFacesContext()
    {
        return _helper.getFacesContext();
    }


    //property helpers
    protected void setComponentProperty(String attrName, Object attrValue)
    {
        _helper.setComponentProperty(attrName, attrValue);
    }

    protected void setComponentProperty(String attrName, boolean attrValue)
    {
        _helper.setComponentProperty(attrName, attrValue);
    }

    protected void setRendererAttribute(String attrName, Object attrValue)
    {
        _helper.setRendererAttribute(attrName, attrValue);
    }

    protected void setRendererAttribute(String attrName, boolean attrValue)
    {
        _helper.setRendererAttribute(attrName, attrValue);
    }

    protected void setRendererAttribute(String attrName, int attrValue)
    {
        _helper.setRendererAttribute(attrName, attrValue);
    }

    public void overrideProperties(UIComponent uiComponent)
    {
        super.overrideProperties(uiComponent);
        _helper.overrideProperties(uiComponent);
    }

    protected final UIComponent findComponent()
        throws JspException
    {
        UIComponent c = _helper.findComponent();
        if (c == null)
        {
            c = super.findComponent();
        }
        return c;
    }

    public void setCreated(boolean b)
    {
        created = b;
    }

    /**
     * Overwrite to make public.
     * @param value
     */
    protected void setValue(Object value)
    {
        setComponentProperty(CommonComponentAttributes.VALUE_ATTR, value);
    }




//----------------- common tag attributes -------------------------------

    // UIComponent attributes

    public void setId(String s)
    {
        super.setId(s);
    }

    public void setConverter(Object converter)
    {
        setRendererAttribute(CONVERTER_ATTR, converter);
    }

    public void setModelReference(String s)
    {
        setComponentProperty(MODEL_REFERENCE_ATTR, s);
    }

    public void setRendered(boolean rendered)
    {
        super.setRendered(rendered);
    }

    public void setRendered(Boolean rendered)
    {
        super.setRendered(rendered.booleanValue());
    }


    // HTML 4.0 universal attributes

    public void setDir(String value)
    {
        setRendererAttribute(DIR_ATTR, value);
    }

    public void setLang(String value)
    {
        setRendererAttribute(LANG_ATTR, value);
    }

    public void setStyle(String value)
    {
        setRendererAttribute(STYLE_ATTR, value);
    }

    public void setTitle(String value)
    {
        setRendererAttribute(TITLE_ATTR, value);
    }



    // HTML 4.0 event-handler attributes

    public void setOnclick(String value)
    {
        setRendererAttribute(ONCLICK_ATTR, value);
    }

    public void setOndblclick(String value)
    {
        setRendererAttribute(ONDBLCLICK_ATTR, value);
    }

    public void setOnmousedown(String value)
    {
        setRendererAttribute(ONMOUSEDOWN_ATTR, value);
    }

    public void setOnmouseup(String value)
    {
        setRendererAttribute(ONMOUSEUP_ATTR, value);
    }

    public void setOnmouseover(String value)
    {
        setRendererAttribute(ONMOUSEOVER_ATTR, value);
    }

    public void setOnmousemove(String value)
    {
        setRendererAttribute(ONMOUSEMOVE_ATTR, value);
    }

    public void setOnmouseout(String value)
    {
        setRendererAttribute(ONMOUSEOUT_ATTR, value);
    }

    public void setOnkeypress(String value)
    {
        setRendererAttribute(ONKEYPRESS_ATTR, value);
    }

    public void setOnkeydown(String value)
    {
        setRendererAttribute(ONKEYDOWN_ATTR, value);
    }

    public void setOnkeyup(String value)
    {
        setRendererAttribute(ONKEYUP_ATTR, value);
    }


    // key & bundle attributes

    public void setKey(String v)
    {
        setRendererAttribute(KEY_ATTR, v);
    }

    public void setBundle(String v)
    {
        setRendererAttribute(BUNDLE_ATTR, v);
    }



    // MyFaces extension: user role attributes

    public void setEnabledOnUserRole(String value)
    {
        setRendererAttribute(ENABLED_ON_USER_ROLE_ATTR, value);
    }

    public void setVisibleOnUserRole(String value)
    {
        setRendererAttribute(VISIBLE_ON_USER_ROLE_ATTR, value);
    }

}
