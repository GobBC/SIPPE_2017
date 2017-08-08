<%-- 
    Document   : actualiza-info-proyecto-act
    Created on : Mar 25, 2015, 11:29:40 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualización de información complementaria a Proyectos/Act. institucionales </title>
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
        String tipoDependencia = new String();
        int year = 0;
        String usuario = new String();
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
        resultSQL.resultSQLDesconecta();
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >Actualización de información complementaria a Proyectos/Act. institucionales<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-guardar" onclick="actualizarProyecto()">
                <br/> <small> Guardar </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div"> 
        <form>
            <div id="divCombo">
                <table>
                    <tr>
                        <td> Ramo: </td>
                        <td>
                            <select id="selRamo" onchange="getProgramasOnChange()" style="width: 460px;">
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
                        <td> Programa: </td>
                        <td>
                            <select id="selPrograma" style="width: 460px;" onchange="getProyectosByPrograma()">
                                <option value="-1">
                                    -- Seleccione un programa --
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td> Proy./Act.: </td>
                        <td>
                            <select id="selProyecto" style="width: 460px;" onchange="getProyectosByid()">
                                <option value="-1">
                                    -- Seleccione un proyecto --
                                </option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="divInfoProyecto">                
            </div>
            <input type="hidden" id="inTxtRFC" value="" />
            <input type="hidden" id="inTxtHC" value="" />
            <%--
            <div id='captura-dep'class='captura-lineas' style="display: none;">
                <label> Captura l&iacute;nea PED </label>
                <br/>
                <div id='ped-lineas' class='lineas-drop'>

                </div>
                <div id='lineas-ctrl'>
                    <input id="lineaCambiar" type='hidden' value=''  />
                    <input type='button' value='Insertar' onclick="insertarLineaPed()"/>
                    <input id="borrarLineasPed" type='hidden' value=''  />
                    <input type='button' value='Borrar' onclick="borrarLinea()"/>
                    <input type='button' value='Guardar' onclick="guardarLineaPed()"/>
                    <input type='button' value='Cancelar' onclick='ocultarLineaPED()'/>
                </div>
            </div>

            <div id='captura-sect'class='captura-lineas' style="display: none;">

                <label> Captura l&iacute;neas Sectoriales </label>
                <br/>
                <div>
                    <textarea rows="4" cols="50" id='titLineaSectlLineaPed' disabled ></textarea>
                    <div id='sectorial-lineas' class='lineas-drop-sectorial'>

                    </div>
                </div> 
                <div id='lineas-sectorial-ctrl'>
                    <input type='button' value='Insertar' onclick="insertarLineaSectorial()"/>
                    <input id="ultimaLineaSectorial" type='hidden' value=''  />
                    <input id="borrarLineasSectoriales" type='hidden' value=''  />
                    <input type='button' value='Borrar' onclick="borrarLineaSectorial()" />
                    <input type='button' value='Guardar' onclick="guardarLineaSectorial()"/>
                    <input Id="lineaFiltro" type='hidden' value='' />
                    <input type='button' value='Cancelar' onclick='ocultarLineaSectorial()'/>
                </div>
            </div>
            --%>
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
