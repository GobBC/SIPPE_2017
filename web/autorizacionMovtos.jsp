<%-- 
    Document   : autorizacionMovtos
    Created on : 9/12/2015, 11:03:02 AM
    Author     : rharo
--%>

<%@page import="java.io.File"%>
<%@page import="gob.gbc.entidades.TipoOficio"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.TipoFlujo"%>
<%@page import="gob.gbc.entidades.TipoMovimiento"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="template/encabezado.jsp" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitudes de Revisi&oacute;n/Autorizaci&oacute;n</title>
        <script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
        <link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
        <link type="text/css" rel="stylesheet" href="librerias/js/jquery.qtip.custom/jquery.qtip.css" />
        <script type="text/javascript" src="librerias/js/jquery.qtip.custom/jquery.qtip.js"></script>
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
        
        List<TipoOficio> movEvaluacionList = null;

        String selected = new String();
        String estatus = new String();
        String tipoMov = new String();
        String tipoDependencia = new String();
        String usuario = new String();
        String rol = new String();
        String msjSelect = "Seleccionados";
        String optionRamos = "";
        String ramoInList = new String();
        int year = 0;
        int contReg = 0;
        int contRegSelect = 0;
        boolean existeInversion = false;
        boolean isEvaludador = false;
        boolean isParaestatal = false;
        
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<TipoMovimiento> tipoMovimientoList = new ArrayList<TipoMovimiento>();
        List<TipoFlujo> tipoFlujoList = new ArrayList<TipoFlujo>();

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }

        if (Utilerias.existeParametro("estatus", request)) {
            estatus = request.getParameter("estatus");
        }

        if (Utilerias.existeParametro("tipoMov", request)) {
            tipoMov = request.getParameter("tipoMov");
        }

        if (Utilerias.existeParametro("ramoInList", request)) {
            ramoInList = request.getParameter("ramoInList");
        }

        AutorizacionBean autorizacionBean = new AutorizacionBean(tipoDependencia);
        autorizacionBean.setStrServer(((String) request.getHeader("Host")));
        autorizacionBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        autorizacionBean.resultSQLConecta(tipoDependencia);

        tipoMovimientoList = autorizacionBean.getTipoMovimiento();
        tipoFlujoList = autorizacionBean.getTipoFlujoByUsuario(usuario, tipoDependencia);
        isEvaludador = autorizacionBean.isUsuarioEvaludaor(usuario);
        isParaestatal = autorizacionBean.isParaestatal();
    %>    
        <div Id="TitProcess"><label>Solicitudes de Revisi&oacute;n/Autorizaci&oacute;n</label> </div>
        <div class="col-md-8 col-md-offset-2">     
            <div class="botones-menu_bootstrap">                       
            <%if(isEvaludador && !isParaestatal){%>
                <button type="button" class="btnbootstrap btn-reporte" onclick="$('#rptEvaluacion').submit()">
                    <br/> <small> Reporte </small>
                </button>
            <%}%>
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
        </div>
        <%
        if(!isEvaludador || isParaestatal){
        %>
        <div style="width: 100%; display: inline-block" class="comboRamos">
            <table>
                <tr>
                    <td>
                        <label>Tipo de Autorización:</label> 
                    </td>
                    <td>
                        <select id='selEstatusBase' onchange="getMovimientos()">
                            <%                            if (tipoFlujoList.size() > 1) {
                            %>
                            <option value='-1|0'>-- Seleccione un tipo de autorización --</option>
                            <%
                                }
                            %>
                            <%
                                for (TipoFlujo tipoFlujo : tipoFlujoList) {
                                    selected = "";
                                    if (tipoFlujo.getEstatusBase().equals(estatus)) {
                                        selected = "selected";
                                    }

                                    out.println("<option value='" + tipoFlujo.getTipoFlujoId() + "|" + tipoFlujo.getEstatusBase() + "' "
                                            + selected + ">"
                                            + tipoFlujo.getTipoFlujo() + "</option>");
                                }
                            %>
                        </select>
                    </td>
                </tr>
            </table>    
            <br>
            <table>
                <tr>
                    <td>
                        <label>Tipo de Movimiento:</label> 
                    </td>
                    <td>
                        <select id='selTipoMovto' onchange="getMovimientos()">
                            <option value='-1'>-- Seleccione un tipo de movimiento --</option>
                            <%
                                for (TipoMovimiento tipoMovimiento : tipoMovimientoList) {
                                    selected = "";
                                    if (tipoMovimiento.getTipoMovId().equals(tipoMov)) {
                                        selected = "selected";
                                    }

                                    out.println("<option value='" + tipoMovimiento.getTipoMovId() + "' " + selected + ">"
                                            + tipoMovimiento.getTipoMov() + " </option>");
                                }
                            %>
                        </select>
                    </td>
                </tr>
            </table>
            <%
                ramoList = autorizacionBean.getResultRamoByYear(year, usuario);
                contReg = ramoList.size();
                int RegTemp = 0;

                for (Ramo ramo : ramoList) {
                    RegTemp++;

                    optionRamos += "<tr >";
                    optionRamos += "<td align='left' >";
                    if (ramoInList.contains(ramo.getRamo()) || ramoInList.equals("")) {
                        contRegSelect++;
                        optionRamos += "<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrgMonitoreo(" + RegTemp + ")' checked='true' />";
                    } else {
                        optionRamos += "<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrgMonitoreo(" + RegTemp + ")'  />";
                    }
                    optionRamos += "<input id='ramoCheck" + RegTemp + "' name='ramoCheck" + RegTemp + "' type='hidden'  value='" + ramo.getRamo() + "' />";
                    optionRamos += "</td>";
                    optionRamos += "<td align='left'  >";
                    optionRamos += ramo.getRamo() + "-" + ramo.getRamoDescr() + " ";
                    optionRamos += "</td>";
                    optionRamos += "</tr>";

                    if (ramo.getRamo().equals("07")) {
                        existeInversion = true;
                    }
                }

                if (rol.equals(autorizacionBean.getRolNormativo())) {
                    if (!existeInversion) {
                        RegTemp++;
                        contReg++;
                        optionRamos += "<tr >";
                        optionRamos += "<td align='left' >";
                        if (ramoInList.contains("07") || ramoInList.equals("")) {
                            contRegSelect++;
                            optionRamos += "<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrgMonitoreo(" + RegTemp + ")' checked='true' />";
                        } else {
                            optionRamos += "<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrgMonitoreo(" + RegTemp + ")'  />";
                        }

                        optionRamos += "<input id='ramoCheck" + RegTemp + "' name='ramoCheck" + RegTemp + "' type='hidden'  value='07' />";
                        optionRamos += "</td>";
                        optionRamos += "<td align='left'  >";
                        optionRamos += "07-SECRETARIA DE PLANEACION Y FINANZAS" + " ";
                        optionRamos += "</td>";
                        optionRamos += "</tr>";
                    }
                }

            %> 



            <div id="divCombo" class="comboRamos">
                <label> Ramo:</label>
                <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
                <ul>
                    <li><div id="comboSelect" name="comboSelect" ><input id="labelCont" name="labelCont" type="Text" value="<%=contRegSelect + " " + msjSelect%>" disabled="true" onchange="getMovimientos()"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                        <ul>
                            <li>
                                <div id="divRamos" name="divRamos"  >
                                    <input type="checkbox" id="allChecks" name="allChecks" onclick="allChecksRptRamPrgGrupPob()" checked="true"/>Todos/Ninguno
                                    <table>
                                        <%
                                            out.print(optionRamos);
                                        %>
                                    </table>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>            
            <br/>
            <div id='div-movimientos'></div>
            <form id="frmAutoriza" action="#" method="post">
                <input type="hidden" id="folio" name="folio">
                <input type="hidden" id="estatus" name="estatus">
                <input type="hidden" id="tipoMov" name="tipoMov">
                <input type="hidden" id="tipoOficio" name="tipoOficio">
                <input type="hidden" id="tipoFlujo" name="tipoFlujo">
                <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
                <input type="hidden" id="autorizacion" name="autorizacion">
                <input type="hidden" id="urlRegresa" name="urlRegresa" value="autorizacionMovtos.jsp">
            </form>
        </div>    
        <%
            if (tipoMovimientoList == null || tipoFlujoList == null || tipoMovimientoList.size() == 0 || tipoFlujoList.size() == 0) {
        %>
        <script type="text/javascript">
            alert('No existen flujos de autorización para este usuario')
        </script> 
        <%
        } else {
        %>
        <script type="text/javascript">
            getMovimientos();
        </script> 
        <%
            }
        %>
        <div id="mensaje"></div>
        <div id="PopUpZone"></div>
    <%
    }else{
        movEvaluacionList = autorizacionBean.getResultMovimientoEvaluacion(year);
    %>
    <table id="tbl-solMovs" class="display" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th align="right" >Oficio</th>
                <th align="right" >Fecha<br>Elaboración</th>
                <th align="right" >Fecha Envío<br>Autorización</th>
                <th align="right" width ="200">Ramo</th>      
                    <%-- <th align="right" ></th>   --%>
                <th align="right" >Vo.Bo.</th>
            </tr>
        </thead>
        <tbody>
        <%
            for(TipoOficio movimiento : movEvaluacionList){
                out.write("<tr>");
                if(movimiento.getTipoOficio() == null)
                    out.write("<td>"+movimiento.getOficio()+"</td>");
                else{
                    out.write("<td>"+movimiento.getOficio()+"-"+movimiento.getTipoOficio()+"</td>");
                }
                out.write("<td>"+movimiento.getFechaElab()+"</td>");
                out.write("<td>"+movimiento.getFecPPTO()+"</td>");
                out.write("<td>"+movimiento.getRamo()+"</td>");
                out.write("<td>");
                if(movimiento.getEvaluacion().equals("S")){
                    out.write("<input id='eva"+movimiento.getOficio()+"' type='checkbox' checked disabled />");
                }else{
                    out.write("<input id='eva"+movimiento.getOficio()+"' type='checkbox' onChange='evaluarMovimiento("+movimiento.getOficio()+")'/>");
                }
                out.write("</td>");
                out.write("</tr>");
            }
        %>
        </tbody>`
    </table>
        <form method="POST" id="rptEvaluacion" action="ejecutaReporte/ejecutarReporte.jsp" target="_blank">
            <input type="hidden" id="filename" name="filename" value='rptOficiosEvaluados.jasper'/>
            <input type="hidden" id="rptPath" name="rptPath" value='Evaluacion'/>
            <input type="hidden" id="reporttype" name="reporttype" value='PDF'/>
        </form>
        <script>
            dataTablePOA("tbl-solMovs");
        </script>
        
        <%    
    }
    %>
    <script src="librerias/_evaluacion.js" type="text/javascript"></script>
    <jsp:include page="template/piePagina.jsp" />
</html>
