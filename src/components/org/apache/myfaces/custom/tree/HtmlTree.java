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


package net.sourceforge.myfaces.custom.tree;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import net.sourceforge.myfaces.custom.navigation.HtmlPanelNavigation;
import net.sourceforge.myfaces.custom.tree.event.TreeSelectionEvent;
import net.sourceforge.myfaces.custom.tree.event.TreeSelectionListener;
import net.sourceforge.myfaces.custom.tree.model.TreeModel;
import net.sourceforge.myfaces.custom.tree.model.TreePath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Tree implementation based on javax.swing.JTree.</p>
 * <p>The tree model is assigned by using a value binding named <code>model</code> and is not
 * stored in view state.</p>
 * <p>A hierarchy of {@link HtmlTreeNode} objects is used to represent the current
 * expanded state of the tree. The root node is held as a faces named <code>rootNode</code>.</p>
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 *          $Log$
 *          Revision 1.7  2004/04/29 18:48:16  o_rossmueller
 *          node selection handling
 *
 *          Revision 1.6  2004/04/23 19:09:34  o_rossmueller
 *          state transition magic
 *
 *          Revision 1.5  2004/04/22 23:22:33  o_rossmueller
 *          fix: queueEvent
 *          <p/>
 *          Revision 1.4  2004/04/22 22:00:30  o_rossmueller
 *          implemented HtmlTree.expandPath
 *          <p/>
 *          Revision 1.3  2004/04/22 21:14:55  o_rossmueller
 *          TreeSelectionListener support
 *          <p/>
 *          Revision 1.2  2004/04/22 13:00:09  o_rossmueller
 *          changed id creation - node ids are now generated by tree
 *          <p/>
 *          Revision 1.1  2004/04/22 10:20:23  manolito
 *          tree component
 */
public class HtmlTree
    extends HtmlForm
{

    private static final String FACET_ROOTNODE = "rootNode";
    private static final String PREVIOUS_VIEW_ROOT = HtmlPanelNavigation.class.getName() + ".PREVIOUS_VIEW_ROOT";


    private boolean itemStatesRestored = false;
    private Map expanded = new HashMap();
    private String styleClass;
    private String nodeClass;
    private String selectedNodeClass;
    private String iconLine = "images/tree/line.gif";
    private String iconNoline = "images/tree/noline.gif";
    private String iconChild = "images/tree/noline.gif";
    private String iconChildFirst = "images/tree/line_first.gif";
    private String iconChildMiddle = "images/tree/line_middle.gif";
    private String iconChildLast = "images/tree/line_last.gif";
    private String iconNodeOpen = "images/tree/node_open.gif";
    private String iconNodeOpenFirst = "images/tree/node_open_first.gif";
    private String iconNodeOpenMiddle = "images/tree/node_open_middle.gif";
    private String iconNodeOpenLast = "images/tree/node_open_last.gif";
    private String iconNodeClose = "images/tree/node_close.gif";
    private String iconNodeCloseFirst = "images/tree/node_close_first.gif";
    private String iconNodeCloseMiddle = "images/tree/node_close_middle.gif";
    private String iconNodeCloseLast = "images/tree/node_close_last.gif";
    private int uniqueIdCounter = 0;
    private int[] selectedPath;


    public HtmlTree()
    {
    }


    public TreeModel getModel(FacesContext context)
    {
        ValueBinding binding = getValueBinding("model");

        if (binding != null)
        {
            TreeModel model = (TreeModel) binding.getValue(context);
            if (model != null)
            {
                return model;
            }
        }
        return null;
    }


    public String createUniqueId(FacesContext context)
    {
        return "_node_" + uniqueIdCounter++;
    }


    public void addTreeSelectionListener(TreeSelectionListener listener)
    {
        addFacesListener(listener);
    }


    public String getIconLine()
    {
        return iconLine;
    }


    public void setIconLine(String iconLine)
    {
        this.iconLine = iconLine;
    }


    public String getIconNoline()
    {
        return iconNoline;
    }


    public void setIconNoline(String iconNoline)
    {
        this.iconNoline = iconNoline;
    }


    public String getIconChild()
    {
        return iconChild;
    }


    public void setIconChild(String iconChild)
    {
        this.iconChild = iconChild;
    }


    public String getIconChildFirst()
    {
        return iconChildFirst;
    }


    public void setIconChildFirst(String iconChildFirst)
    {
        this.iconChildFirst = iconChildFirst;
    }


    public String getIconChildMiddle()
    {
        return iconChildMiddle;
    }


    public void setIconChildMiddle(String iconChildMiddle)
    {
        this.iconChildMiddle = iconChildMiddle;
    }


    public String getIconChildLast()
    {
        return iconChildLast;
    }


    public void setIconChildLast(String iconChildLast)
    {
        this.iconChildLast = iconChildLast;
    }


    public String getIconNodeOpen()
    {
        return iconNodeOpen;
    }


    public void setIconNodeOpen(String iconNodeOpen)
    {
        this.iconNodeOpen = iconNodeOpen;
    }


    public String getIconNodeOpenFirst()
    {
        return iconNodeOpenFirst;
    }


    public void setIconNodeOpenFirst(String iconNodeOpenFirst)
    {
        this.iconNodeOpenFirst = iconNodeOpenFirst;
    }


    public String getIconNodeOpenMiddle()
    {
        return iconNodeOpenMiddle;
    }


    public void setIconNodeOpenMiddle(String iconNodeOpenMiddle)
    {
        this.iconNodeOpenMiddle = iconNodeOpenMiddle;
    }


    public String getIconNodeOpenLast()
    {
        return iconNodeOpenLast;
    }


    public void setIconNodeOpenLast(String iconNodeOpenLast)
    {
        this.iconNodeOpenLast = iconNodeOpenLast;
    }


    public String getIconNodeClose()
    {
        return iconNodeClose;
    }


    public void setIconNodeClose(String iconNodeClose)
    {
        this.iconNodeClose = iconNodeClose;
    }


    public String getIconNodeCloseFirst()
    {
        return iconNodeCloseFirst;
    }


    public void setIconNodeCloseFirst(String iconNodeCloseFirst)
    {
        this.iconNodeCloseFirst = iconNodeCloseFirst;
    }


    public String getIconNodeCloseMiddle()
    {
        return iconNodeCloseMiddle;
    }


    public void setIconNodeCloseMiddle(String iconNodeCloseMiddle)
    {
        this.iconNodeCloseMiddle = iconNodeCloseMiddle;
    }


    public String getIconNodeCloseLast()
    {
        return iconNodeCloseLast;
    }


    public void setIconNodeCloseLast(String iconNodeCloseLast)
    {
        this.iconNodeCloseLast = iconNodeCloseLast;
    }


    public String getStyleClass()
    {
        return styleClass;
    }


    public void setStyleClass(String styleClass)
    {
        this.styleClass = styleClass;
    }


    public String getNodeClass()
    {
        return nodeClass;
    }


    public void setNodeClass(String nodeClass)
    {
        this.nodeClass = nodeClass;
    }


    public String getSelectedNodeClass()
    {
        return selectedNodeClass;
    }


    public void setSelectedNodeClass(String selectedNodeClass)
    {
        this.selectedNodeClass = selectedNodeClass;
    }


    public String getFamily()
    {
        return "net.sourceforge.myfaces.HtmlTree";
    }


    /**
     * Ensures that the node identified by the specified path is
     * expanded and viewable. If the last item in the path is a
     * leaf, this will have no effect.
     *
     * @param path the <code>TreePath</code> identifying a node
     */
    public void expandPath(TreePath path, FacesContext context)
    {
        // Only expand if not leaf!
        TreeModel model = getModel(context);

        if (path != null && model != null && !model.isLeaf(path.getLastPathComponent()))
        {
            int[] translatedPath = HtmlTreeNode.translatePath(path, getModel(context));
            HtmlTreeNode rootNode = getRootNode();
            if (rootNode == null)
            {
                createRootNode(context);
                rootNode = getRootNode();
            }
            if (!rootNode.isExpanded())
            {
                rootNode.setExpanded(true);
            }
            rootNode.expandPath(translatedPath, 0);

        }
    }


    /**
     * Ensures that the node identified by the specified path is
     * collapsed and viewable.
     *
     * @param path the <code>TreePath</code> identifying a node
     */
    public void collapsePath(TreePath path)
    {
        setExpandedState(path, false);
    }


    /**
     * Sets the expanded state of this <code>JTree</code>.
     * If <code>state</code> is
     * true, all parents of <code>path</code> and path are marked as
     * expanded. If <code>state</code> is false, all parents of
     * <code>path</code> are marked EXPANDED, but <code>path</code> itself
     * is marked collapsed.<p>
     */
    protected void setExpandedState(TreePath path, boolean state)
    {
        if (path != null)
        {
            // Make sure all parents of path are expanded.
            TreePath parentPath = path.getParentPath();

            while (parentPath != null)
            {
                if (isExpanded(parentPath))
                {
                    parentPath = null;
                } else
                {
                    expanded.put(parentPath, Boolean.TRUE);
                    parentPath = parentPath.getParentPath();
                }
            }

            if (!state)
            {
                // collapse last path.
                expanded.put(path, Boolean.FALSE);
//               fireTreeCollapsed(path);
//               if (removeDescendantSelectedPaths(path, false) &&
//                  !isPathSelected(path)) {
//                  // A descendant was selected, select the parent.
//                  addSelectionPath(path);
//               }
//               if (accessibleContext != null) {
//                  ((AccessibleJTree) accessibleContext).
//                     fireVisibleDataPropertyChange();
//               }
//            }
            } else
            {
                // Expand last path.
                expanded.put(path, Boolean.TRUE);
//               fireTreeExpanded(path);
//               if (accessibleContext != null) {
//                  ((AccessibleJTree) accessibleContext).
//                     fireVisibleDataPropertyChange();
//               }
//            }
            }
        }
    }


    public boolean isExpanded(TreePath path)
    {
        if (path == null)
        {
            return false;
        }

        Object value = expanded.get(path);

        if (value == null || !((Boolean) value).booleanValue())
        {
            // not expanded
            return false;
        }

        // Expanded, check parent
        TreePath parentPath = path.getParentPath();

        if (parentPath != null)
        {
            return isExpanded(parentPath);
        }
        return true;
    }

    
    public TreePath getSelectionPath()
    {
        if (selectedPath == null)
        {
            return null;
        }
        return HtmlTreeNode.translatePath(selectedPath, getModel(FacesContext.getCurrentInstance()));
    }


    public void selectionChanged(HtmlTreeNode node)
    {
        TreePath oldPath = null;

        if (selectedPath != null)
        {
            oldPath = HtmlTreeNode.translatePath(selectedPath, getModel(FacesContext.getCurrentInstance()));
        }
        selectedPath = node.getTranslatedPath();
        queueEvent(new TreeSelectionEvent(this, oldPath, node.getPath()));
    }


    private void createRootNode(FacesContext context)
    {
        HtmlTreeNode node;
        TreeModel model = getModel(context);
        Object root = model.getRoot();
        node = (HtmlTreeNode) context.getApplication().createComponent(HtmlTreeNode.COMPONENT_TYPE);
        String id = createUniqueId(context);
        node.setId(id);

        node.setPath(new TreePath(new Object[]{root}));
        node.setUserObject(root);
        node.setLayout(new int[]{HtmlTreeNode.CLOSED_SINGLE});
        getFacets().put(FACET_ROOTNODE, node);
    }


    public HtmlTreeNode getRootNode()
    {
        return (HtmlTreeNode) getFacet(FACET_ROOTNODE);
    }


    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[19];
        values[0] = super.saveState(context);
        values[1] = iconChild;
        values[2] = iconChildFirst;
        values[3] = iconChildLast;
        values[4] = iconLine;
        values[5] = iconNodeClose;
        values[6] = iconNodeCloseFirst;
        values[7] = iconNodeCloseLast;
        values[8] = iconNodeCloseMiddle;
        values[9] = iconNodeOpen;
        values[10] = iconNodeOpenFirst;
        values[11] = iconNodeOpenLast;
        values[12] = iconNodeOpenMiddle;
        values[13] = iconNoline;
        values[14] = styleClass;
        values[15] = nodeClass;
        values[16] = selectedNodeClass;
        values[17] = new Integer(uniqueIdCounter);
        values[18] = selectedPath;
        return ((Object) (values));
    }


    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        iconChild = (String) values[1];
        iconChild = (String) values[2];
        iconChild = (String) values[3];
        iconLine = (String) values[4];
        iconNodeClose = (String) values[5];
        iconNodeCloseFirst = (String) values[6];
        iconNodeCloseLast = (String) values[7];
        iconNodeCloseMiddle = (String) values[8];
        iconNodeOpen = (String) values[9];
        iconNodeOpenFirst = (String) values[10];
        iconNodeOpenLast = (String) values[11];
        iconNodeOpenMiddle = (String) values[12];
        iconNoline = (String) values[13];
        styleClass = (String) values[14];
        nodeClass = (String) values[15];
        selectedNodeClass = (String) values[16];
        uniqueIdCounter = ((Integer) values[17]).intValue();
        selectedPath = (int[]) values[18];
    }


    public void decode(FacesContext context)
    {
        super.decode(context);

        //Save the current view root for later reference...
        context.getExternalContext().getRequestMap().put(PREVIOUS_VIEW_ROOT, context.getViewRoot());
        //...and remember that this instance needs NO special treatment on rendering:
        itemStatesRestored = true;
    }


    public void encodeBegin(FacesContext context) throws IOException
    {
        HtmlTreeNode node = getRootNode();

        if (node == null)
        {
            createRootNode(context);
        }

        if (!itemStatesRestored)
        {
            UIViewRoot previousRoot = (UIViewRoot) context.getExternalContext().getRequestMap().get(PREVIOUS_VIEW_ROOT);
            if (previousRoot != null)
            {
                restoreItemStates(context, previousRoot);
            } else
            {
                //no previous root, means no decode was done
                //--> a new request
            }
        }

        super.encodeBegin(context);
    }


    public void restoreItemStates(FacesContext facesContext, UIViewRoot previousRoot)
    {
        HtmlTree previousTree = (HtmlTree) previousRoot.findComponent(getClientId(facesContext));

        if (previousTree != null)
        {
            HtmlTreeNode node = previousTree.getRootNode();

            if (node != null)
            {
                getRootNode().restoreItemState(node);
            }

            selectedPath = previousTree.selectedPath;
         }
    }


}
