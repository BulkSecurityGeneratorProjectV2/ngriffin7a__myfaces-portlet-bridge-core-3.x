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
package net.sourceforge.myfaces.codegen.component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FieldDef
{
    //private static final Log log = LogFactory.getLog(Field.class);

    private static final Set ATTACHED_CLASSES = new HashSet();
    static
    {
        ATTACHED_CLASSES.add("javax.faces.convert.Converter");
        ATTACHED_CLASSES.add("javax.faces.el.MethodBinding");
        ATTACHED_CLASSES.add("javax.faces.el.ValueBinding");
    }

    private String _qualifiedType;
    private String _name;
    private String _defaultValue;
    private boolean _proprietary = false;
    private boolean _generateProperty = true;
    private boolean _generateSetter = true;
    private boolean _saveState = true;
    private int _saveStateFieldIndex;

    public FieldDef(String name)
    {
        _name = name;
    }

    public String getQualifiedType()
    {
        return _qualifiedType;
    }

    public void setQualifiedType(String type)
    {
        _qualifiedType = type;
    }

    public String getType()
    {
        if (isPrimitiveType())
        {
            return _qualifiedType;
        }
        else
        {
            return getSimpleType(_qualifiedType);
        }
    }

    public String getFieldType()
    {
        if (isPrimitiveType())
        {
            return getSimpleType(getNonPrimitiveType(_qualifiedType));
        }
        else
        {
            return getSimpleType(_qualifiedType);
        }
    }


    private static String getSimpleType(String type)
    {
        for (int i = 0; i < ComponentDef.IMPLICIT_PACKAGES.length; i++)
        {
            String p = ComponentDef.IMPLICIT_PACKAGES[i] + ".";
            if (type.startsWith(p))
            {
                return type.substring(p.length());
            }
        }
        return type;
    }

    private static String getNonPrimitiveType(String type)
    {
        if (type.equals("boolean")) return "java.lang.Boolean";
        else if (type.equals("int")) return "java.lang.Integer";
        else if (type.equals("long")) return "java.lang.Long";
        else throw new IllegalArgumentException(type);
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public String getSaveName()
    {
        if (_name.equals("for")) return "forValue";
        if (_name.equals("transient")) return "transientFlag";
        return _name;
    }

    public String getUcaseName()
    {
        return _name.toUpperCase();
    }

    public String getDefaultValue()
    {
        return _defaultValue;
    }

    public void setDefaultValue(String defaultValue)
    {
        _defaultValue = defaultValue;
    }

    public String getDefaultLiteral()
    {
        if (_defaultValue == null)
        {
            if (_qualifiedType.equals("boolean")) return "false";
            else if (_qualifiedType.equals("int")) return "0";
            else if (_qualifiedType.equals("long")) return "0";
            else return "null";
        }
        else
        {
            if (_qualifiedType.equals("java.lang.String"))
            {
                return '\"' + _defaultValue + '\"';
            }
            else if (_defaultValue.equals("-2147483648") && _qualifiedType.equals("int"))
            {
                return "Integer.MIN_VALUE";
            }
            else if (_defaultValue.equals("-2147483648") && _qualifiedType.equals("int"))
            {
                return "Integer.MIN_VALUE";
            }
            else
            {
                return _defaultValue;
            }
        }
    }

    public boolean isPrimitiveType()
    {
        return _qualifiedType.equals("boolean") ||
               _qualifiedType.equals("int") ||
               _qualifiedType.equals("long");
    }

    public boolean isPrimitiveBoolean()
    {
        return _qualifiedType.equals("boolean");
    }

    public String getSetterMethodName()
    {
        return "set" + Character.toUpperCase(_name.charAt(0)) + _name.substring(1);
    }

    public String getGetterMethodName()
    {
        if (_qualifiedType.equals("boolean"))
        {
            return "is" + Character.toUpperCase(_name.charAt(0)) + _name.substring(1);
        }
        else
        {
            return "get" + Character.toUpperCase(_name.charAt(0)) + _name.substring(1);
        }
    }

    public String getPrimitiveValueMethod()
    {
        if (!isPrimitiveType()) throw new UnsupportedOperationException();
        return _qualifiedType + "Value";
    }

    public boolean isProprietary()
    {
        return _proprietary;
    }

    public void setProprietary(boolean proprietary)
    {
        _proprietary = proprietary;
    }

    public boolean isSaveState()
    {
        return _saveState;
    }

    public void setSaveState(boolean saveState)
    {
        _saveState = saveState;
    }

    public boolean isGenerateSetter()
    {
        return _generateSetter;
    }

    public void setGenerateSetter(boolean generateSetter)
    {
        _generateSetter = generateSetter;
    }

    public int getSaveStateFieldIndex()
    {
        return _saveStateFieldIndex;
    }

    public void setSaveStateFieldIndex(int saveStateFieldIndex)
    {
        _saveStateFieldIndex = saveStateFieldIndex;
    }

    public boolean isGenerateProperty()
    {
        return _generateProperty;
    }

    public void setGenerateProperty(boolean generateProperty)
    {
        _generateProperty = generateProperty;
    }

    public boolean isAttachedStateSaving()
    {
        return ATTACHED_CLASSES.contains(_qualifiedType);
    }


    public void toXml(PrintWriter writer) throws IOException
    {
        writer.print  ("    <field");
        if (isProprietary()) writer.print(" proprietary=\"true\"");
        if (!isGenerateProperty()) writer.print(" generateProperty=\"false\"");
        if (!isGenerateSetter()) writer.print(" generateSetter=\"false\"");
        if (!isSaveState())  writer.print(" saveState=\"false\"");
        writer.println(">");
        writer.print  ("        <name>");
        writer.print  (_name);
        writer.println("</name>");
        writer.print  ("        <type>");
        writer.print  (_qualifiedType);
        writer.println("</type>");
        if (_defaultValue != null)
        {
            writer.print  ("        <default-value>");
            writer.print  (_defaultValue);
            writer.println("</default-value>");
        }
        writer.println("    </field>");
    }
}
