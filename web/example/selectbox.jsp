<%@ page import="java.math.BigDecimal,
                 java.util.Date"%>
<%@ page session="false"
%><%@ taglib uri="/WEB-INF/myfaces_basic.tld" prefix="h"
%><%@ taglib uri="/WEB-INF/myfaces_core.tld" prefix="f"
%><%@ taglib uri="/WEB-INF/myfaces_ext.tld" prefix="x"
%><html>

<%@include file="inc/head.inc" %>

<!--
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
//-->

<body>

<jsp:useBean id="simpleList" class="net.sourceforge.myfaces.examples.common.CarConfigurator" scope="request" />

<h:use_faces>

    <x:page_layout id="page" layoutReference="pageLayout" cssClass="pageLayout" >
        <%@include file="inc/page_header.jsp" %>
        <%@include file="inc/navigation.jsp"  %>

        <x:page_body id="body" cssClass="pageBody" >
            <h:form id="form" formName="formName" >

                <h:output_label id="label_cars" for="selone_lb" key="label_cars" bundle="net.sourceforge.myfaces.examples.resource.example_messages"></h:output_label>
                <h:selectone_listbox id="selone_lb" modelReference="simpleList.car" >
                    <h:selectitems id="selone_lb_cars" modelReference="simpleList.cars" />
                </h:selectone_listbox>

                <br>
                <br>

                <h:output_label id="label_colors" for="selone_menu" key="label_colors" bundle="net.sourceforge.myfaces.examples.resource.example_messages"></h:output_label>
                <h:selectone_menu id="selone_menu" size="3" modelReference="simpleList.color" >
                    <h:selectitem id="empty" key="empty_selitem" bundle="net.sourceforge.myfaces.examples.resource.example_messages" ></h:selectitem>
                    <h:selectitems id="selone_lb_cars" modelReference="simpleList.colors" />
                </h:selectone_menu>

                <br>
                <br>

                <h:output_label id="label_extras" for="selone_menu" key="label_extras" bundle="net.sourceforge.myfaces.examples.resource.example_messages"></h:output_label>
                <h:selectmany_listbox id="selmany" modelReference="simpleList.extras" >
                    <h:selectitems id="items" modelReference="simpleList.extrasList" />
                </h:selectmany_listbox>

                <br>
                <br>

                <h:command_button id="submit" commandName="calcPrice" key="button_calcprice" bundle="net.sourceforge.myfaces.examples.resource.example_messages">
                    <f:action_listener type="net.sourceforge.myfaces.examples.common.CarConfiguratorActionListener" ></f:action_listener>
                </h:command_button>
            </h:form>

            <h:message id="msg" key="msg_price" bundle="net.sourceforge.myfaces.examples.resource.example_messages" >
                <h:parameter id="msgp1" modelReference="simpleList.price" />
            </h:message>


        </x:page_body>

        <%@include file="inc/page_footer.jsp" %>
    </x:page_layout>

</h:use_faces>

</body>

</html>