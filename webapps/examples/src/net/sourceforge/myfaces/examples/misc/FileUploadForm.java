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
package net.sourceforge.myfaces.examples.misc;

import net.sourceforge.myfaces.component.ext.UploadedFile;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FileUploadForm
{
    private UploadedFile _upFile;

    public UploadedFile getUpFile()
    {
        return _upFile;
    }

    public void setUpFile(UploadedFile upFile)
    {
        _upFile = upFile;
    }

    /*
    FIXME
    public Action getFileUploadAction()
    {
        return new Action() {
            public String invoke()
            {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();

                ApplicationFactory af = (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
                ValueBinding vb = af.getApplication().getValueBinding("fileUploadForm");
                FileUploadForm form = (FileUploadForm)vb.getValue(facesContext);

                if (form != null)
                {
                    UploadedFile upFile = form.getUpFile();
                    servletContext.setAttribute("fileupload_file", upFile.getFile());
                    servletContext.setAttribute("fileupload_type", upFile.getContentType());
                }

                return "ok";
            }
        };
    }
    */

}
