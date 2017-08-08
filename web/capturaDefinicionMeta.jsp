<%-- 
    Document   : captura-definicion-metas
    Created on : Apr 7, 2015, 8:28:03 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Definici&oacute;n de metas</title>
    </head>
    <%
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }
        List<Ramo> ramoList = new ArrayList<Ramo>();
        String tipoDependencia = new String();
        ResultSQL resultSQL = null;
        String ramoId = new String();
        String programaId = new String();
        String proyectoId = new String();
        boolean redirect = false;

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
        if (request.getParameter("redirect") != null && request.getParameter("redirect") != "") {
            redirect = true;
            if (request.getParameter("ramo") != null && request.getParameter("ramo") != "") {
                ramoId = request.getParameter("ramo");
            }
            if (request.getParameter("programa") != null && request.getParameter("programa") != "") {
                programaId = request.getParameter("programa");
            }
            if (request.getParameter("proyecto") != null && request.getParameter("proyecto") != "") {
                proyectoId = request.getParameter("proyecto");
            }
            if (request.getParameter("tipoProy") != null && request.getParameter("tipoProy") != "") {
                proyectoId += "," + request.getParameter("tipoProy");
            }
        }
        resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer((request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(tipoDependencia);
        ramoList = resultSQL.getResultRamoByYear(year, usuario);
        resultSQL.resultSQLDesconecta();
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label  >Definici&oacute;n de metas<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <%--<button type="button" class="btnbootstrap btn-guardar" onclick="guardarPrograma()">
                <br/> <small> Guardar </small>
            </button>--%>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div id="mensaje" style="position: relative"></div>
    <div class="center-div" >
        <form id="frmDefMetas" name="frmDefMetas" action='capturaAccion.jsp' method="POST">
            <input type="hidden" id="metaId" name='metaId' value="" />
            <div id="div-drop">
                <table id="tblMeta">
                    <tr>
                        <td> Ramo: </td>
                        <td>
                            <select id="selRamo" name="selRamo" onchange='getProgramasOnChange()'>
                                <option value="-1"> -- Seleccione un ramo -- </option>
                                <%
                                    for (Ramo ramo : ramoList) {
                                        out.write("<option value='" + ramo.getRamo() + "'>");
                                        out.write(ramo.getRamo() + "-" + ramo.getRamoDescr());
                                        out.write("</option>");
                                    }

                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td> Programa: </td>
                        <td>
                            <select id="selPrograma" name="selPrograma" onchange='getProyectosByPrograma()'>
                                <option value='-1'>
                                    -- Seleccione un programa --
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td> Proy./Act.</td>
                        <td>
                            <select id='selProyecto' name="selProyecto" onchange="getMetasOnChange()">
                                <option value='-1'>
                                    -- Seleccione un programa --
                                </option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
        <br/>
        <div id="div-listado">
        </div>
        </br>
        <center>
            <div id='control-metas' style="display: none;">
                <input type="button" id='btnInsertMeta' value="Insertar" class="btnControl" onclick='insertarMeta()'/>
                <input type="button" id='btnDeleteMeta' value="Eliminar" class="btnControl" onclick='deleteMeta()'/>
                <input type="button" id='btnEditMeta' value="Editar" class="btnControl" onclick="getInfoMeta()"/>
                <input type="button" id='btnProgMeta' value="Calendarizaci&oacute;n" class="btnControl" onclick="getEstimacionMeta()"/>
                <input type="button" id='btnPresMeta' value="Presupuestaci&oacute;n" class="btnControl" onclick="isPOACerrado()"/>
            </div>
        </center>
        <div id="infoMeta" style="display: none;">                            
        </div>
        <div id="estimacionMetaO" style="display: none">                            
            <div>

            </div>
            <center>
                <input id="actualizarEstimacionMeta" type="button" value="Guardar" onclick="actualizarEstimacion()" />
                <input type="button" value="Cancelar" onclick="cerrarEstimacion()" />
            </center>
        </div>        
    </div>
    <%if(redirect){%>
        <script type="text/javascript">        
            cargarDatosProgramaMeta('<%=ramoId%>');
        </script>
        <script type="text/javascript">
            cargarDatosProyectoMeta('<%=programaId%>');
        </script>
        <script type="text/javascript">
            cargarMetas('<%=proyectoId%>');
        </script>
    <%}%>
        <jsp:include page="template/piePagina.jsp" />
</html>
