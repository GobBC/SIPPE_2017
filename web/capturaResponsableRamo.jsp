<%-- 
    Document   : captura-responsable-ramo
    Created on : Mar 18, 2015, 2:00:52 PM
    Author     : ugarcia
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captura de responsable de ramo </title>
    </head>
    <%
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        List<Ramo> ramoList = new ArrayList<Ramo>();
        int year = 0;
        String tipoDependencia = new String();
        String usuario = new String();
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
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
        resultSQL.resultSQLDesconecta();
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >Captura de responsable de ramo <label/> </div>
    <div class="col-md-8 col-md-offset-2">
        
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-guardar" onclick="updateResponsableRamo()">
                <br/> <small> Guardar </small>
            </button>
            <button type="button" class="btnbootstrap btn-limpiar" onclick="limpiar()">
                <br/> <small> Limpiar </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div">
        <form id="ramoForm" >
            <table>
                <tr><td><label>RAMO:</label></td>
                    <td>
                        <select id="selRamo" name="selRamo"  onchange="changeRamo()">
                            <option value="-1">
                                -- Seleccione un ramo --
                            </option>
                            <%
                                for (Ramo ramo : ramoList) {
                                    out.print("<option value=\"" + ramo.getRamo() + "\">");
                                    out.print(ramo.getRamo() + "-" + ramo.getRamoDescr());
                                    out.print("</option>");
                                }
                            %>
                        </select>
                    </td>
                </tr>
            </table>
            <br/>
            <br/>
            <div id="datos-responsable">
            </div>
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
	<div id="mensaje"></div>

    <jsp:include page="template/piePagina.jsp" />
</html>
