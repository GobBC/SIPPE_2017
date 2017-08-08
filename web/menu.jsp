<%-- 
    Document   : menu.jsp
    Created on : Mar 13, 2015, 1:13:00 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.EnumProcesoEspecial"%>
<%@page import="java.util.Collection"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.OpcionMenu"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Programa Operativo Anual</title>
    </head>
    <%
        request.setCharacterEncoding("UTF-8");
        Utilerias util = new Utilerias();
        util.borrarArchivosTemp(application.getRealPath("/") + "temp");
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }
        session.removeAttribute("linea-ramo");
        session.removeAttribute("linea-accion");
        String strRol = "";
        String strUsuario = "";
        String strDescripcion = new String();
        String year = new String();
        String tipoDependencia = new String();
        String selected = new String();

        boolean isParaestatal = false;
        boolean isAyuntamiento = false;
        boolean isReporteHabilitado = false;
        boolean hasProcesoEspecial = false;

        List<String> yearList = new ArrayList<String>();
        List<OpcionMenu> menuList = new ArrayList<OpcionMenu>();

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strUsuario") != null) {
            strUsuario = (String) session.getAttribute("strUsuario");
        }
        if ((String) session.getAttribute("strRol") != null) {
            strRol = (String) session.getAttribute("strRol");
        }
        if (request.getParameter("year") != null && request.getParameter("year") != "") {
            year = request.getParameter("year");
            session.setAttribute("year", year);
        }
        if (year.isEmpty()) {
            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = (String) session.getAttribute("year");
            }
        }
        ResultSQL resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer((request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        try {
            resultSQL.resultSQLConecta(tipoDependencia);
            menuList = resultSQL.getResultOpcionMenu(strUsuario);
            isParaestatal = resultSQL.getResultSQLisParaestatal();
            isAyuntamiento = resultSQL.getResultSqlGetIsAyuntamiento();
            isReporteHabilitado = resultSQL.getResultgetParametroReporteCierre();
            session.setAttribute("linea-ramo", null);
            session.setAttribute("linea-accion", null);
            yearList = resultSQL.getYears();
            hasProcesoEspecial = resultSQL.getResultSQLhasProcesoEspecual(strUsuario,EnumProcesoEspecial.PROCESO_PARAMETROS.getProceso());
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            resultSQL.resultSQLDesconecta();
        }
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div class="col-lg-12 text-left">
        <div class="col-lg-3">
            <label>Selecciona ejercicio</label>
        </div>
        <div class="col-lg-3 text-left">
            <form id='cambiaEjercicio' action="menu.jsp" method="POST">
                <select id="year" name="year" onchange="cambiaEjercicio()">
                    <%
                        for (String yearL : yearList) {
                            if (!yearL.equals("0")) {
                                if (yearL.equals(year)) {
                                    selected = "selected";
                                } else {
                                    selected = new String();
                                }
                                out.write("<option value='" + yearL + "' " + selected + ">" + yearL + "</option>");
                            }
                        }
                    %>
                </select>
            </form>
        </div>
    </div>
    <div class="menu">

        <div class="menuIzquierda">
            <label class="lblMenu">PROCESOS</label>

            <%
                if (existe(menuList, "m_menu_mir")) {
            %>
            <div class="menuOpciones"> 
                <label> Matriz de Indicadores para Resultados </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_mir") || menu.getSubMenu().equals("m_menu_mir")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <a href="<%= menu.getUrl()%>.jsp">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= strDescripcion%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>
            
            <%
                if (existe(menuList, "m_menu_prog_pres")) {
            %>
            <div class="menuOpciones"> 

                <label>
                    Programaci&oacute;n - Presupuestaci&oacute;n
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_prog_pres") || menu.getSubMenu().equals("m_menu_prog_pres")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <a href="<%= menu.getUrl()%>.jsp">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= strDescripcion%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_mod_prog_pres")) {
            %>
            <div class="menuOpciones"> 

                <label>
                    Modificaciones Program&aacute;tico - Presupuestales
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_mod_prog_pres") || menu.getSubMenu().equals("m_menu_mod_prog_pres")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <a href="<%= menu.getUrl()%>.jsp">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= strDescripcion%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_avance_poa")) {
            %>
            <div class="menuOpciones"> 

                <label>
                    Captura Avance POA
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_avance_poa") || menu.getSubMenu().equals("m_menu_avance_poa")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <a href="<%= menu.getUrl()%>.jsp">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= strDescripcion%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_consultas")) {
            %>
            <div class="menuOpciones"> 

                <label>
                    Consultas
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_consultas") || menu.getSubMenu().equals("m_menu_consultas")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <a href="<%= menu.getUrl()%>.jsp">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= strDescripcion%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_admin")) {
            %>
            <div class="menuOpciones"> 

                <label>
                    Administración
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_admin") || menu.getSubMenu().equals("m_menu_admin")) {
                        if(menu.getDescripcion().equals("MANTENIMIENTO PARÁMETROS")){
                            if(hasProcesoEspecial){
                                strDescripcion = menu.getDescripcion();
                                %>
                                <div class="menuSubOpciones">
                                    <a href="<%= menu.getUrl()%>.jsp">
                                        <img class="menuImg" src="imagenes/bullet.png" alt="<%= strDescripcion%>">
                                        <%= strDescripcion%>
                                    </a>
                                </div>
                                <%
                            }
                        }else{
                            strDescripcion = menu.getDescripcion();
                            %>
                            <div class="menuSubOpciones">
                                <a href="<%= menu.getUrl()%>.jsp">
                                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= strDescripcion%>">
                                    <%= strDescripcion%>
                                </a>
                            </div>
                            <%
                            }
                        }
                    }
                }
            %>
        </div>

        <%
            if (existe(menuList, "m_menu_rep_programaticos")) {
        %>
        <div class="menuDerecha"> 
            <label class="lblMenu">REPORTES</label>


            <div class="menuOpciones"> 
                <label>
                    Program&aacute;ticos
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_rep_programaticos") || menu.getSubMenu().equals("m_menu_rep_programaticos")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <%--<a href="<%= menu.getUrl() %>.jsp">--%>
                <a onclick="evaluaOpcionReportes('<%= menu.getUrl()%>');">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= menu.getDescripcion()%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_rep_presupuesto")) {
            %>
            <div class="menuOpciones"> 
                <label>
                    Presupuesto
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_rep_presupuesto") || menu.getSubMenu().equals("m_menu_rep_presupuesto")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <%--<a href="<%= menu.getUrl() %>.jsp">--%>
                <a onclick="evaluaOpcionReportes('<%= menu.getUrl()%>');">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= menu.getDescripcion()%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_rep_ProgPresup")) {
            %>
            <div class="menuOpciones"> 
                <label>
                    Program&aacute;tico – Presupuestales 
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_rep_ProgPresup") || menu.getSubMenu().equals("m_menu_rep_ProgPresup")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <%--<a href="<%= menu.getUrl() %>.jsp">--%>
                <a onclick="evaluaOpcionReportes('<%= menu.getUrl()%>');">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= menu.getDescripcion()%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>  

            <%
                if (existe(menuList, "m_menu_rep_imp_folios")) {
            %>
            <div class="menuOpciones"> 
                <label>
                    Impresi&oacute;n de Folios
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_rep_imp_folios") || menu.getSubMenu().equals("m_menu_rep_imp_folios")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <%--<a href="<%= menu.getUrl() %>.jsp">--%>
                <a onclick="evaluaOpcionReportes('<%= menu.getUrl()%>');">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= menu.getDescripcion()%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_rep_mod_pptales") && (isParaestatal || !isAyuntamiento)) {
            %>
            <div class="menuOpciones"> 
                <label>
                    Reportes Modificaciones Presupuestales
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_rep_mod_pptales") || menu.getSubMenu().equals("m_menu_rep_mod_pptales")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <%--<a href="<%= menu.getUrl() %>.jsp">--%>
                <a onclick="evaluaOpcionReportes('<%= menu.getUrl()%>');">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= menu.getDescripcion()%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_rep_control")) {
            %>
            <div class="menuOpciones"> 
                <label>
                    Reportes de Control
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_rep_control") || menu.getSubMenu().equals("m_menu_rep_control")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <%--<a href="<%= menu.getUrl() %>.jsp">--%>
                <a onclick="evaluaOpcionReportes('<%= menu.getUrl()%>');">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= menu.getDescripcion()%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %>

            <%
                if (existe(menuList, "m_menu_captura_para")) {
            %>
            <div class="menuOpciones"> 
                <label>
                    Paraestatales
                </label>
            </div>
            <%
                for (OpcionMenu menu : menuList) {
                    if (menu.getSubMenu().equals("m_menu_captura_para") || menu.getSubMenu().equals("m_menu_reporte_para")) {
                        strDescripcion = menu.getDescripcion();
            %>
            <div class="menuSubOpciones">
                <a href="<%= menu.getUrl()%>.jsp">
                    <img class="menuImg" src="imagenes/bullet.png" alt="<%= strDescripcion%>">
                    <%= strDescripcion%>
                </a>
            </div>
            <%
                        }
                    }
                }
            %> 
            <div class="menuLogout">
                <img class="menuImg" src="imagenes/logout.png" width="32" height="32" id="icono-logout"/>
                <%if (isReporteHabilitado) {%>
                <a onclick="cerrarSesion('<%=year%>', '<%=strUsuario%>', '<%=tipoDependencia%>')" >
                    Cerrar sesi&oacute;n
                </a>
                <%} else {%>
                <a onclick="window.location = 'logout.jsp?reporte=0';" >
                    Cerrar sesi&oacute;n
                </a>
                <%}%>
            </div>
        </div>        

        <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
            <input type="hidden" id="filename" name="filename" value="" /> 
            <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
            <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
        </form>
    </div>

    <jsp:include page="template/piePagina.jsp" />
</html>
<%!
    public boolean existe(List<OpcionMenu> menuList, String valor) {
        boolean bExiste = false;
        for (OpcionMenu menu : menuList) {
            if (menu.getSubMenu().equals(valor) || menu.getSubMenu().equals(valor)) {
                bExiste = true;
            }
        }
        return bExiste;
    }
%>