<%-- 
    Document   : reporteRamPrgGrupPob
    Created on : May 12, 2015, 04:54:40 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.GrupoPoblacional"%>
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
        <title>Reporte Ramo - Programa - Grupo Poblacional</title>
        <script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
        <link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
    </head>
    <%
        request.setCharacterEncoding("UTF-8");

        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }

        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<GrupoPoblacional> grupoPoblacionalList = new ArrayList<GrupoPoblacional>();

        String usuario = new String();
        String msjSelect = "Seleccionados";
        String tipoDependencia = new String();
        String optionGruposPoblacionales = "";

        int year = 0;
        int contReg = 0;
        int contRegGP = 0;
        int contRegSelect = 0;
        int contRegSelectGP = 0;

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }

        ResultSQL resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer(((String) request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(tipoDependencia);
        
        ramoList = resultSQL.getResultRamoByYear(year, usuario);
        grupoPoblacionalList = resultSQL.getResultGetGrupoPoblacional();

        resultSQL.resultSQLDesconecta();

        contReg = ramoList.size();
        contRegSelect = contReg;

        if (contReg == 1) {
            msjSelect = "Seleccionado";
        }
    %>
    <body>
        <div Id="TitProcess"><label >Reporte Ramo - Programa - Grupo Poblacional<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <button type="button" class="btnbootstrap btn-reporte" onclick="generarReporteRamoProgramaGrupoPoblacional()">
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-Excel" onclick="generarReporteRamoProgramaGrupoPoblacionalExcel()">
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
        </div>
        <div class="center-div"> 
            <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
                <input type="hidden" id="filename" name="filename" value="rptRamoProgramaGrupoPoblacional.jasper"/> 
                <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
                <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
                <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
                <input type="hidden" id="grupoPobInList" name="grupoPobInList" value="0"/>
                <div id="divCombo" class="comboRamos">
                    <label> Ramo:</label>
                    <ul>
                        <li><div id="comboSelect" name="comboSelect" ><input id="labelCont" name="labelCont" type="Text" value="<%=contReg + " " + msjSelect%>" disabled="true"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                            <ul>
                                <li>
                                    <div id="divRamos" name="divRamos" >
                                        <input type="checkbox" id="allChecks" name="allChecks" onclick="allChecksRptRamPrgGrupPob()" checked="true"/>Todos/Ninguno
                                        <table>
                                            <%
                                                int RegTemp = 0;
                                                for (Ramo ramo : ramoList) {
                                                    RegTemp++;
                                                    out.print("<tr >");
                                                    out.print("<td align='left' >");
                                                    out.print("<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrgGrupPob(" + RegTemp + ")' checked='true' />");
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
                <%

                    contRegGP = grupoPoblacionalList.size();
                    int RegTempGP = 0;

                    for (GrupoPoblacional grupoPoblacional : grupoPoblacionalList) {
                        RegTempGP++;
                        contRegSelectGP++;
                        optionGruposPoblacionales += "<tr >";
                        optionGruposPoblacionales += "<td align='left' >";
                        optionGruposPoblacionales += "<input id='checkGP" + RegTempGP + "' name='checkGP" + RegTempGP + "' type='checkbox' onclick='contCheckComboGrupoPoblacional(" + RegTempGP + ")' checked='true' />";
                        optionGruposPoblacionales += "<input id='GPCheck" + RegTempGP + "' name='GPCheck" + RegTempGP + "' type='hidden'  value='" + grupoPoblacional.getGrupoPobId() + "' />";
                        optionGruposPoblacionales += "</td>";
                        optionGruposPoblacionales += "<td align='left'  >";
                        optionGruposPoblacionales += grupoPoblacional.getGrupoPobId() + "-" + grupoPoblacional.getGripoPoblacional() + " ";
                        optionGruposPoblacionales += "</td>";
                        optionGruposPoblacionales += "</tr>";
                    }
                %> 
                <div id="divComboGP" class="comboGP">
                    <label> Grupo Poblacional:</label>
                    <input id='contRegGP' name='contRegGP' type='hidden' value='<%=contRegGP%>'/>
                    <input id='contRegSelectGP' name='contRegSelectGP' type='hidden' value='<%=contRegSelectGP%>' />
                    <ul>
                        <li><div id="comboSelectGP" name="comboSelectGP" ><input id="labelContGP" name="labelContGP" type="Text" value="<%=contRegSelectGP + " " + msjSelect%>" disabled="true" onchange="getMovimientosGP()"/><img id="dropDownGP" name="dropDownGP" src="imagenes/OpenArrow.png" ></div>
                            <ul>
                                <li>
                                    <div id="divGrupoPoblacional" name="divGrupoPoblacional"  >
                                        <input type="checkbox" id="allChecksGP" name="allChecksGP" onclick="allChecksComboGrupoPoblacional()" checked="true"/>Todos/Ninguno
                                        <table>
                                            <%
                                                out.print(optionGruposPoblacionales);
                                            %>
                                        </table>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
        <div id="mensaje"></div>
        <div id="PopUpZone"></div>
        <jsp:include page="template/piePagina.jsp" />
    </body> 
</html>
