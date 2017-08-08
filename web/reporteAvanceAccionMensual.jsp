<%-- 
    Document   : reporteAvanceAccionMensual
    Created on : May 31, 2017, 10:24:40 PM
    Author     : ealonso
--%>
<%@page import="java.io.File"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <jsp:include page="template/encabezado.jsp" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte de Estimación y Avance de Acción Mensual</title>
        <script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
        <link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
    </head>
    <%
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("strUsuario") == null || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        String msjSelect = "Seleccionados";
        String usuario = new String();
        String tipoDependencia = new String();        
        int year = 0;
        
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        ResultSQL resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer(((String) request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(tipoDependencia);
        
        //Para combo multicheck
        int contReg = 0;
        int contRegSelect = 0;
        List<Ramo> ramoList = new ArrayList<Ramo>();
        
        ramoList = resultSQL.getResultRamoByYear(year, usuario);        
        
        resultSQL.resultSQLDesconecta();

        contReg = ramoList.size();
        contRegSelect = contReg;

        if (contReg == 1) {
            msjSelect = "Seleccionado";
        }
    %>
    <body>
        <div Id="TitProcess"><label >Reporte de Estimación y Avance de Acción Mensual<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <button type="button" class="btnbootstrap btn-reporte" onclick="generarReporteRam()">
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-Excel" onclick="generarReporteRamExcel()"> 
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
        </div>
        <div class="center-div"> 
            <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
                <input type="hidden" id="filename" name="filename" value="<%="reporteMasivo" + File.separatorChar + "rptAvanceAccionMensual.jasper"%>"/> 
                <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
                <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
                <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>

                <div id="divCombo" class="comboRamos">
                    <label> Ramo:</label>
                    <ul>
                        <li><div id="comboSelect" name="comboSelect" ><input id="labelCont" name="labelCont" type="Text" value="<%=contReg + " " + msjSelect%>" disabled="true"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                            <ul>
                                <li>
                                    <div id="divRamos" name="divRamos" >
                                        <input type="checkbox" id="allChecks" name="allChecks" onclick="allChecksRptRam()" checked="true"/>Todos/Ninguno
                                        <table>
                                            <%
                                                int RegTemp = 0;
                                                for (Ramo ramo : ramoList) {
                                                    RegTemp++;
                                                    out.print("<tr >");
                                                    out.print("<td align='left' >");
                                                    out.print("<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRam(" + RegTemp + ")' checked='true' />");
                                                    out.print("<input id='ramoCheck" + RegTemp + "' name='ramoCheck" + RegTemp + "' type='hidden'  value='" + ramo.getRamo() + "' />");
                                                    out.print("</td>");
                                                    out.print("<td align='left'  >");
                                                    out.print(ramo.getRamo() + "-" + ramo.getRamoDescr() + " ");
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                }
                                            %> 
                                        </table>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <div id="divInfoPlantilla" style="width: 1200px" > 
                    <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                    <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
                </div>                  
            </form>
        </div>
        <div id="mensaje"></div>
        <div id="PopUpZone"></div>
        <jsp:include page="template/piePagina.jsp" />
    </body> 
</html>
