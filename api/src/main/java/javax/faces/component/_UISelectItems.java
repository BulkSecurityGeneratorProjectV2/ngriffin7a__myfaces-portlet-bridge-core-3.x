/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * This tag associates a set of selection list items with the nearest
 * parent UIComponent. The set of SelectItem objects is retrieved via
 * a value-binding.
 * <p>
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * </p>
 * <p>
 * UISelectItems should be nested inside a UISelectMany or UISelectOne component,
 * and results in  the addition of one ore more SelectItem instance to the list of available options
 * for the parent component
 * </p>
 */
@JSFComponent
(clazz = "javax.faces.component.UISelectItems",template=true,
 name = "f:selectItems",
 tagClass = "org.apache.myfaces.taglib.core.SelectItemsTag",
 bodyContent = "empty")
abstract class _UISelectItems extends UIComponentBase
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.SelectItems";
  static public final String COMPONENT_TYPE =
    "javax.faces.SelectItems";

  /**
   * Disable this property; although this class extends a base-class that
   * defines a read/write rendered property, this particular subclass
   * does not support setting it. Yes, this is broken OO design: direct
   * all complaints to the JSF spec group.
   */
  @JSFProperty(tagExcluded=true)
  @Override
  public void setRendered(boolean state) {
      super.setRendered(state);
      //call parent method due TCK problems
      //throw new UnsupportedOperationException();
  }
  
  /**
   * The initial value of this component.
   *
   * @return  the new value value
   */
  @JSFProperty
  public abstract Object getValue();
  
  /**
   * Name of a request-scope attribute under which the current item
   * of the collection, array, etc. of the value attribute will be 
   * exposed so that it can be referred to in EL for other attributes 
   * of this component.
   * 
   * @since 2.0
   * @return
   */
  @JSFProperty(literalOnly = true)
  public abstract String getVar();
  
  /**
   * The value for the current item.
   * 
   * @since 2.0
   * @return
   */
  @JSFProperty
  public abstract Object getItemValue();
  
  /**
   * The label of the current item.
   * 
   * @since 2.0
   * @return
   */
  @JSFProperty
  public abstract String getItemLabel();
  
  /**
   * The description of the current item.
   * 
   * @since 2.0
   * @return
   */
  @JSFProperty
  public abstract String getItemDescription();
  
  /**
   * Determines if the current item is selectable or not.
   * 
   * @since 2.0
   * @return
   */
  @JSFProperty(defaultValue = "false")
  public abstract boolean isItemDisabled();
  
  /**
   * Determines if the rendered markup for the current item receives
   * normal JSF HTML escaping or not.
   * 
   * @since 2.0
   * @return
   */
  @JSFProperty(defaultValue = "true")
  public abstract boolean isItemLabelEscaped();
  
  /**
   * Is either an EL expression pointing to the element in the value collection
   * whose value should be marked as a "no selection" item, or a literal string
   * that exactly matches the value of the item in the collection that must be 
   * marked as the "no selection" item. If the user selects such an item and 
   * the field is marked as required, then it will not pass validation.
   * 
   * @since 2.0
   * @return
   */
  @JSFProperty
  public abstract Object getNoSelectionValue();

}
