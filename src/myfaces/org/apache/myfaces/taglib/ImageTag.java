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

import net.sourceforge.myfaces.component.UIGraphic;
import net.sourceforge.myfaces.renderkit.html.ImageRenderer;

import javax.faces.component.UIComponent;


/**
 * DOCUMENT ME!
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageTag
    extends MyFacesTag
{
    public UIComponent createComponent()
    {
        return new UIGraphic();
    }

    public String getRendererType()
    {
        return ImageRenderer.TYPE;
    }

    public void setUrl(String value)
    {
        setComponentAttribute(UIGraphic.URL_ATTR, value);
    }

    public void setKey(String value)
    {
        setRendererAttribute(ImageRenderer.KEY_ATTR, value);
    }

    public void setBundle(String value)
    {
        setRendererAttribute(ImageRenderer.BUNDLE_ATTR, value);
    }

    public void setWidth(Integer value)
    {
        setRendererAttribute(ImageRenderer.WIDTH_ATTR, value);
    }

    public void setHeight(Integer value)
    {
        setRendererAttribute(ImageRenderer.HEIGHT_ATTR, value);
    }

    public void setAlt(String value)
    {
        setRendererAttribute(ImageRenderer.ALT_ATTR, value);
    }

    public void setAltKey(String value)
    {
        setRendererAttribute(ImageRenderer.ALT_KEY_ATTR, value);
    }

    public void setAltBundle(String value)
    {
        setRendererAttribute(ImageRenderer.ALT_BUNDLE_ATTR, value);
    }

}
