<%-- 
    Document   : reporteRamPrgGrupPob
    Created on : May 12, 2015, 04:54:40 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Ponderado"%>
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
        <title>Reporte de Ponderados Ramo - Programa - Meta</title>
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
        List<Ponderado> ponderadoProgramaInList = new ArrayList<Ponderado>();
        List<Ponderado> ponderadoMetaInList = new ArrayList<Ponderado>();
              
        String usuario = new String();
        String msjSelect = "Seleccionados";
        String tipoDependencia = new String();
        String optionPonderadosPrg = "";
        String optionPonderadosMeta = "";

        int year = 0;
        int contReg = 0;
        int contRegPonP = 0;
        int contRegPonM = 0;
        int contRegSelect = 0;
        int contRegSelectPonP = 0;
        int contRegSelectPonM = 0;

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
        ponderadoProgramaInList = ponderadoMetaInList = resultSQL.getResultGetPonderado();
        
        resultSQL.resultSQLDesconecta();

        contReg = ramoList.size();
        contRegSelect = contReg;

        if (contReg == 1) {
            msjSelect = "Seleccionado";
        }
    %>
    <body>
        <div Id="TitProcess"><label >Reporte de los Ponderados Programa - Meta<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <button type="button" class="btnbootstrap btn-reporte" onclick="generarReportePrgMetaPonderado()">
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-Excel" onclick="generarReportePrgMetaPonderadoExcel()"> 
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
        </div>
        <div class="center-div"> 
            <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
                <input type="hidden" id="filename" name="filename" value="rptPonderadoPrgMeta.jasper"/> 
                <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
                <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
                <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
                <input type="hidden" id="ponderadoProgramaList" name="ponderadoProgramaList" value="0"/>
                <input type="hidden" id="ponderadoMetaList" name="ponderadoMetaList" value="0"/>
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
                <%
                    contRegPonP = ponderadoProgramaInList.size();
                    int RegTempPonP = 0;

                    for (Ponderado ponderadoPrograma : ponderadoProgramaInList) {
                        RegTempPonP++;
                        contRegSelectPonP++;
                        optionPonderadosPrg += "<tr >";
                        optionPonderadosPrg += "<td align='left' >";
                        optionPonderadosPrg += "<input id='checkPonP" + RegTempPonP + "' name='checkPonP" + RegTempPonP + "' type='checkbox' onclick='contCheckComboPonderadoPrg(" + RegTempPonP + ")' checked='true' />";
                        optionPonderadosPrg += "<input id='PonPCheck" + RegTempPonP + "' name='PonPCheck" + RegTempPonP + "' type='hidden'  value='" + ponderadoPrograma.getPonderadoId() + "' />";
                        optionPonderadosPrg += "</td>";
                        optionPonderadosPrg += "<td align='left'  >";
                        optionPonderadosPrg += ponderadoPrograma.getPonderadoId() + "-" + ponderadoPrograma.getPonderadoDescr() + " ";
                        optionPonderadosPrg += "</td>";
                        optionPonderadosPrg += "</tr>";
                    }
                %>                 
                <div id="divComboPonP" class="comboPonP">
                    <label> Ponderación del Programa:</label>
                    <input id='contRegPonP' name='contRegPonP' type='hidden' value='<%=contRegPonP%>'/>
                    <input id='contRegSelectPonP' name='contRegSelectPonP' type='hidden' value='<%=contRegSelectPonP%>' />
                    <ul>
                        <li><div id="comboSelectPonP" name="comboSelectPonP" ><input id="labelContPonP" name="labelContPonP" type="Text" value="<%=contRegSelectPonP + " " + msjSelect%>" disabled="true" onchange="getMovimientos()"/><img id="dropDownPonP" name="dropDownPonP" src="imagenes/OpenArrow.png" ></div>
                            <ul>
                                <li>
                                    <div id="divPonderadoPrg" name="divPonderadoPrg"  >
                                        <input type="checkbox" id="allChecksPonP" name="allChecksPonP" onclick="allChecksComboPonderadoPrg()" checked="true"/>Todos/Ninguno
                                        <table>
                                            <%
                                                out.print(optionPonderadosPrg);
                                            %>
                                        </table>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <%
                    contRegPonM = ponderadoMetaInList.size();
                    int RegTempPonM = 0;

                    for (Ponderado ponderadoMeta : ponderadoMetaInList) {
                        RegTempPonM++;
                        contRegSelectPonM++;
                        optionPonderadosMeta += "<tr >";
                        optionPonderadosMeta += "<td align='left' >";
                        optionPonderadosMeta += "<input id='checkPonM" + RegTempPonM + "' name='checkPonM" + RegTempPonM + "' type='checkbox' onclick='contCheckComboPonderadoMeta(" + RegTempPonM + ")' checked='true' />";
                        optionPonderadosMeta += "<input id='PonMCheck" + RegTempPonM + "' name='PonMCheck" + RegTempPonM + "' type='hidden'  value='" + ponderadoMeta.getPonderadoId() + "' />";
                        optionPonderadosMeta += "</td>";
                        optionPonderadosMeta += "<td align='left'  >";
                        optionPonderadosMeta += ponderadoMeta.getPonderadoId() + "-" + ponderadoMeta.getPonderadoDescr() + " ";
                        optionPonderadosMeta += "</td>";
                        optionPonderadosMeta += "</tr>";
                    }
                %> 
                <div id="divComboPonM" class="comboPonM">
                    <label> Ponderación de la Meta:</label>
                    <input id='contRegPonM' name='contRegPonM' type='hidden' value='<%=contRegPonM%>'/>
                    <input id='contRegSelectPonM' name='contRegSelectPonM' type='hidden' value='<%=contRegSelectPonM%>' />
                    <ul>
                        <li><div id="comboSelectPonM" name="comboSelectPonM" ><input id="labelContPonM" name="labelContPonM" type="Text" value="<%=contRegSelectPonM + " " + msjSelect%>" disabled="true" onchange="getMovimientos()"/><img id="dropDownPonM" name="dropDownPonM" src="imagenes/OpenArrow.png" ></div>
                            <ul>
                                <li>
                                    <div id="divPonderadoMeta" name="divPonderadoMeta"  >
                                        <input type="checkbox" id="allChecksPonM" name="allChecksPonM" onclick="allChecksComboPonderadoMeta()" checked="true"/>Todos/Ninguno
                                        <table>
                                            <%
                                                out.print(optionPonderadosMeta);
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
