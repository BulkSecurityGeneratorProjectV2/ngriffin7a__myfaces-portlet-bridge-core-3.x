/*
 * Copyright (c) 2004 Your Corporation. All Rights Reserved.
 */
package org.apache.myfaces.custom.popup;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.taglib.html.HtmlComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.5  2005/03/14 13:43:34  svieujot
 * Close MyFaces-132 thanks to Gerhard Hofmann
 *
 * Revision 1.4  2005/02/18 17:19:30  matzew
 * added release() to tag clazzes.
 *
 * Revision 1.3  2004/11/25 08:41:25  matzew
 * removed unused import-statements
 *
 * Revision 1.2  2004/11/23 23:24:04  mmarinschek
 * Popup tag has now more attributes
 *
 * Revision 1.1  2004/11/16 16:25:52  mmarinschek
 * new popup - component; not yet finished
 *
 *
 */
public class HtmlPopupTag
        extends HtmlComponentTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlDataScrollerTag.class);

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _displayAtDistanceX = null;
    private String _displayAtDistanceY = null;
    private String _closePopupOnExitingElement = null;
    private String _closePopupOnExitingPopup = null;
    
    public void release() {
        super.release();

        _enabledOnUserRole = null;
        _visibleOnUserRole = null;
        _displayAtDistanceX = null;
        _displayAtDistanceY = null;
        _closePopupOnExitingElement = null;
        _closePopupOnExitingPopup = null;

    }

    public String getComponentType()
    {
        return HtmlPopup.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlPopupRenderer.RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setIntegerProperty(component,"displayAtDistanceX",_displayAtDistanceX);
        setIntegerProperty(component,"displayAtDistanceY",_displayAtDistanceY);
        setBooleanProperty(component,"closePopupOnExitingElement",_closePopupOnExitingElement);
        setBooleanProperty(component,"closePopupOnExitingPopup",_closePopupOnExitingPopup);
    }

    // userrole attributes
    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setDisplayAtDistanceX(String displayAtDistanceX)
    {
        _displayAtDistanceX = displayAtDistanceX;
    }

    public void setDisplayAtDistanceY(String displayAtDistanceY)
    {
        _displayAtDistanceY = displayAtDistanceY;
    }

    public void setClosePopupOnExitingElement(String closePopupOnExitingElement)
    {
        _closePopupOnExitingElement = closePopupOnExitingElement;
    }

    public void setClosePopupOnExitingPopup(String closePopupOnExitingPopup)
    {
        _closePopupOnExitingPopup = closePopupOnExitingPopup;
    }
}
