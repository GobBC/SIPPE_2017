<%-- 
    Document   : captura-avance-poa
    Created on : Jan 22, 2016, 9:03:03 AM
    Author     : mavalle
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
         <link rel="stylesheet" href="librerias/portal.css" type="text/css">
        <title>Captura de Avance Metas</title>
        
       
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
        ResultSQL resultSQL = null;
        String ramoId = new String();
        String programaId = new String();
        String proyectoId = new String();
        int year = 0;
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
        //if(request.getParameter("redirect") != null && request.getParameter("redirect") != ""){
            if(request.getParameter("ramo") != null && request.getParameter("ramo") != ""){
                ramoId = (String) request.getParameter("ramo");
            }
            if(request.getParameter("programa") != null && request.getParameter("programa") != ""){
                programaId = (String) request.getParameter("programa");
            }
            if(request.getParameter("proyecto") != null && request.getParameter("proyecto") != ""){
                proyectoId = (String) request.getParameter("proyecto");
            }
            if(request.getParameter("tipoProy") != null && request.getParameter("tipoProy") != ""){
                proyectoId += ","+(String) request.getParameter("tipoProy");
            }
          
        
       // }
        resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer(((String) request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(tipoDependencia);
        ramoList = resultSQL.getResultRamoByYear(year, usuario);
        resultSQL.resultSQLDesconecta();
        
    
 
        
        
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label  >Avance POA<label/></div>
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
    <div class="center-div" >
        <form id="frmDefMetas" name="frmDefMetas" action='capturaAccion.jsp' method="POST">
            <input type="hidden" id="metaId" name='metaId' value="" />
            <div id="div-drop">
                <table id="tblMeta">
                    <tr>
                        <td> Ramo: </td>
                        <td>
                            <select id="selRamo" name="selRamo" onchange='getProgramasOnChangeAvancePoa()'>
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
                            <select id='selProyecto' name="selProyecto" onchange="getMetasAvancePoaOnChange()">
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
       
        <div id="infoMeta" style="display: none;">                            
        </div>
        <div id="avancePoaMeta" style="display: none">                            
            <div>

            </div>
            
            <center>
                <!--<input type="button" value="Guardar" onclick="actualizarEstimacion()" />
                <input type="button" value="Cancelar" onclick="cerrarEstimacion()" />-->
                
            </center>
        </div>
        <div id="Observa" style="display:none"></div>
    </div>
    
    <script type="text/javascript">        
       cargarDatosProgramaMetaAvancePoa('<%=ramoId%>');
    </script>
    <script type="text/javascript">
       cargarDatosProyectoMeta('<%=programaId%>');
    </script>
    <script type="text/javascript">
        cargarMetasAvancePoa1('<%=proyectoId%>');
        //getMetasAvancePoaOnChange();
    </script>
    <jsp:include page="template/piePagina.jsp" />
</html>
