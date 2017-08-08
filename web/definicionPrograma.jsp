<%-- 
    Document   : actualiza-informacion-programa
    Created on : Mar 20, 2015, 2:08:46 PM
    Author     : ugarcia
--%>

<%@page import="java.io.File"%>
<%@page import="gob.gbc.bd.ConectaBD"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizaci&oacute;n de informaci&oacute;n complementaria del programa</title>
    </head>
    <%
        session.removeAttribute("linea-ramo");
        session.removeAttribute("linea-accion");
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        int year = 0;
        String usuario = new String();
        String tipoDependencia = new String();
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        List<Ramo> ramoList = new ArrayList<Ramo>();
        ResultSQL resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer(((String) request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(tipoDependencia);
        ramoList = resultSQL.getResultRamoByYear(year, usuario);
        resultSQL.resultSQLDesconecta();
        
        int cont = 1;
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >Actualizaci&oacute;n de informaci&oacute;n complementaria del programa<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-guardar" onclick="guardarPrograma()">
                <br/> <small> Guardar </small>
            </button>
            <button type="button" class="btnbootstrap btn-reporte" onclick="generarReporte()">
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div">        
        <div class='content-combos'>            
            <form id='frmActInfoProg' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
                <table id="tblPrgAct">
                    <tr>
                        <td>
                            <label>Ramo:</label>
                        </td>
                        <td>
                            <select id="selRamo" name="selRamo" onchange='getProgramasOnChange()'>
                                <option value="-1">
                                    -- Seleccione un ramo --
                                </option>
                                <%
                                    for (Ramo ramo : ramoList) {
                                        out.print("<option value='" + ramo.getRamo() + "'>");
                                        out.print(ramo.getRamo() + "-" + ramo.getRamoDescr());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Programa:</label>
                        </td>
                        <td>
                            <select id='selPrograma' name="selPrograma" onchange='getInfoOnProgramaChange()'>
                                <option value='-1'>                              
                                    -- Seleccione un programa --
                                </option>
                            </select>
                        </td>
                    </tr>
                </table>
                <br/>
                <div id="tabContainer" style="display: none">
                    <input id="tabResponsable" type="radio" name="tab-group" checked="checked"/>
                    <label for="tabResponsable">
                        Responsable
                    </label>
                    <input id="tabFinProp" type="radio" name="tab-group"/>
                    <label for="tabFinProp">
                        Fin y Prop&oacute;sito
                    </label>
                    <input id="tabPonderado" type="radio" name='tab-group'/>
                    <label for="tabPonderado">
                        Ponderado
                    </label>
                    <input id="tabClasificacionProgramatica" type="radio" name='tab-group'/>
                    <label for="tabClasificacionProgramatica">
                        Clasificaci&oacute;n Program&aacute;tica
                    </label>
                    <input id="tabLineaAccion" type="radio" name='tab-group'/>
                    <label for="tabLineaAccion">
                        L&iacute;nea PED
                    </label>
                    <div id='tabContent'>
                        <div id='tabContent-1'>
                        </div>
                        <div id='tabContent-2'>
                        </div>
                        <div id='tabContent-3'> 
                        </div>
                        <div id='tabContent-4'> 
                        </div>
                        <div id='tabContent-5'>
                        </div>
                    </div>
                </div>
                <input type="hidden" id="filename" name="filename" value="rptDescripcionProgramas.jasper" /> 
                <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
                <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
            </form>
            <form id='frmSelResponsable'>                    
                <div id='divSelResponsable' style="display: none;">
                    <div id="search-responsable"> 
                        <table>
                            <thead>
                                <tr>
                                    <th>
                                        Nombre
                                    </th>
                                    <th>
                                        Ap. Paterno
                                    </th>
                                    <th>
                                        Ap. Materno
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <input id="inTxtNombre" class="captura-caps" type="text" value=""/>
                                    </td>
                                    <td>
                                        <input id="inTxtApPat" class="captura-caps" type="text" value=""/>
                                    </td>
                                    <td>
                                        <input id="inTxtApMat" class="captura-caps" type="text" value=""/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <br/>
                        <center>
                            <input id="btnBuscar" type="button" value="Buscar" onclick="searchResponsable()"/>
                            <input id="btnCancelar" type="button" value="Cancelar" onclick='esconderBusqueda()'/>
                        </center>
                    </div>
                    <br/>
                    <div id="resultado-busqueda">

                    </div>
                </div>
            </form>              
        </div>
    </div>
	<div id="mensaje"></div>
    <jsp:include page="template/piePagina.jsp" />
</html>