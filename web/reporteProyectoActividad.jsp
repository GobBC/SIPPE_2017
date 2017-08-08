<%-- 
    Document   : reporteProyectoActividad
    Created on : Jun 15, 2015, 04:54:40 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.ReporteExcelBean"%>
<%@page import="gob.gbc.aplicacion.ParametroBean"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <%
        
        String usuario=null;
        Integer year=0;
        String tipoDependencia=null;
        int contReg = 0;
        int contRegSelect = 0;
        String msjSelect = "Seleccionados";
        
        
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        
        
          if (session.getAttribute(
                "year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (session.getAttribute(
                "strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
         if (session.getAttribute(
                "tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
          
          List<Ramo> ramoList = new ArrayList<Ramo>();
          
          ReporteExcelBean reporteExcelBean = new ReporteExcelBean(tipoDependencia);

        reporteExcelBean.setStrServer(
                ((String) request.getHeader("Host")));
        reporteExcelBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        reporteExcelBean.resultSQLConecta(tipoDependencia);
        ramoList = reporteExcelBean.getResultRamoByYear(year, usuario);
         contReg = ramoList.size();
        contRegSelect = contReg;
        
           if (contReg
                == 1) {
            msjSelect = "Seleccionado";
        }
      
    %>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Proyecto/Actividad</title>
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <body>
        <div Id="TitProcess"><label >Reporte Proyecto/Actividad<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <button type="button" class="btnbootstrap btn-reporte" onclick="generarReporteProyectoActividad()">
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
        </div>
        <div class="center-div"> 
            <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
                <input type="hidden" id="filename" name="filename" value="" /> 
                <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
                <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
                <input type="hidden" id="inCapsule" name="inCapsule" value=""/>
                <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
                <div class="radioDivProyAct">
                    <table>
                        <tr>
                            <td><input type="radio" Id="ramo" name="selRpt" value="ramo" checked></td>
                            <td>Ramo</td>
                        </tr>
                        <tr>
                            <td><input type="radio" Id="ramoPrograma" name="selRpt" value="ramoPrograma"></td>
                            <td>Ramo - Programa</td>
                        </tr>
                    </table>
                </div>
                <label class="labelGroupBy">Tipo de reporte:</label>
                <div class="checkDivProyAct">
                    <table>
                        <tr>
                            <td><input type="checkbox" Id="actividad" name="actividad" value="A" checked="true"></td>
                            <td>Actividad</td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" Id="proyecto" name="proyecto" value="P" checked="true" ></td>
                            <td>Proyecto</td>
                        </tr>
                    </table>
                </div>
                <div class="yearDivProyAct">
                <table>
                    <tr>
                        <td><label > Correspondiente al A&#241;o:</label> </td>
                        <td>
                            <input min="0" id="yearCaptura" name="yearCaptura" value="2016" tabindex="1" onchange="cargaComboRamosbyYear()" type="number">
                        </td>
                    </tr>
                </table>
                    </div>
                            <div id="divCombo" class="comboRamos">
                <label> Ramo:</label>

                <ul>
                    <li><div id="comboSelect" name="comboSelect" ><input id="labelCont" name="labelCont" type="Text" value="<%=contReg + " " + msjSelect%>" disabled="true"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                        <ul>
                            <li>
                                <div id="divRamos" name="divRamos"  >
                                    <input type="checkbox" id="allChecks" name="allChecks" onclick="allChecksRptRamPrgGrupPob()" checked="true"/>Todos/Ninguno
                                    <table>
                                        <%                                            
                                            int RegTemp = 0;
                                            for (Ramo ramo : ramoList) {
                                                RegTemp++;
                                                out.print("<tr >");
                                                out.print("<td align='left' >");
                                                out.print("<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrg(" + RegTemp + ")' checked='true' />");
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
               <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />    
                
            </form>
        </div>
        <div id="mensaje"></div>
        <jsp:include page="template/piePagina.jsp" />
    </body> 
</html>
<%
reporteExcelBean.resultSQLDesconecta();

%>
