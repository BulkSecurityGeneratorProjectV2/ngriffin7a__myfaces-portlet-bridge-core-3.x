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
package net.sourceforge.myfaces.renderkit.attr;

/**
 * see JSF.7.4.3
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ImageRendererAttributes
    extends CommonRendererAttributes, KeyBundleAttributes
{
    //MyFaces extensions
    public static final String ALT_KEY_ATTR = "altKey";
    public static final String ALT_BUNDLE_ATTR = "altBundle";

    public static final String[] GRAPHIC_IMAGE_ATTRIBUTES = {
        GRAPHIC_CLASS_ATTR,
        KEY_ATTR,
        BUNDLE_ATTR,
        ALT_KEY_ATTR,
        ALT_BUNDLE_ATTR
    };

}
