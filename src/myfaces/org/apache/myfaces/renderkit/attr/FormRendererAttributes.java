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

import net.sourceforge.myfaces.renderkit.attr.CommonRendererAttributes;

/**
 * DOCUMENT ME!
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface FormRendererAttributes
    extends CommonRendererAttributes
{
    public static final String ACTION_ATTR = "action";
    public static final String ACCEPT_ATTR = "accept";
    public static final String ACCEPT_CHARSET_ATTR = "accept-charset";
    public static final String ENCTYPE_ATTR = "enctype";
    public static final String METHOD_ATTR = "method";
    public static final String ONRESET_ATTR = "onreset";
    public static final String ONSUMBIT_ATTR = "onsubmit";
    public static final String TARGET_ATTR = "target";
    public static final String[] COMMON_FORM_ATTRIBUTES =
    {
        ACTION_ATTR,
        ACCEPT_ATTR,
        ACCEPT_CHARSET_ATTR,
        ENCTYPE_ATTR,
        METHOD_ATTR,
        ONRESET_ATTR,
        ONSUMBIT_ATTR,
        TARGET_ATTR,
    };
}
