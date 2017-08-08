<%-- 
    Document   : listadoCaratulas
    Created on : Jan 15, 2015, 9:44:10 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.EstatusMov"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="gob.gbc.entidades.TipoFlujo"%>
<%@page import="gob.gbc.entidades.TipoMovimiento"%>
<%@page import="gob.gbc.entidades.TipoGasto"%>
<%@page import="gob.gbc.entidades.FuenteFinanciamiento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consulta de caratulas de modificaci&Oacute;n</title>
    </head>
    <%
        request.setCharacterEncoding("UTF-8");
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<EstatusMov> estatusMovimientosList = new ArrayList<EstatusMov>();
        List<TipoMovimiento> tipoMovimientoList = new ArrayList<TipoMovimiento>();
        List<Caratula> caratulaList = null;
        
        boolean isEvaluador = false;
        
        String rol = new String();
        String ramoId = new String();
        String tipoMov = new String();
        String estatus = new String();
        String usuario = new String();
        String disabled = new String();
        String ramoSession = new String();
        String selectTipoMov = new String();
        String selectEstatus = new String();
        String tipoDependencia = new String();
        int year = 0;

        if (session.getAttribute("strUsuario") == null || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }

        if (session.getAttribute("tipoDependencia") == null || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }

        if (request.getParameter("tipoMov") != null && !request.getParameter("tipoMov").equals("")) {
            tipoMov = (String) request.getParameter("tipoMov");
        }

        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = (String) request.getParameter("estatus");
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }

        CaratulaBean caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer(((String) request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        ramoList = caratulaBean.getResultRamoByYear(year, usuario);
        
        isEvaluador = caratulaBean.isUsuarioEvaludaor(usuario);

        if (!rol.equals(caratulaBean.getRolNormativo())) {
            disabled = "disabled";
            ramoId = ramoSession;
        }

    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label>CONSULTA DE CARATULAS DE MODIFICACI&Oacute;N<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">                     
            <%if(isEvaluador){%>
                <button type="button" class="btnbootstrap btn-reporte" onclick="$('#rptEvaluacion').submit()">
                    <br/> <small> Reporte </small>
                </button>
            <%}%>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>   
    <br>
    <br>
    <br>
    <%
    if(!isEvaluador){
    %>
    <div>
        <fieldset style="display:inline">
            <legend>
                Ramo
            </legend>
            <div class='caratula'>     
                <table id="tblComboCaratula">
                    <tr>
                        <td>
                            <div id='txtCaratula' >
                                Ramo:
                            </div> 
                        </td>
                        <td>
                            <select id="selRamo" name="selRamo" onchange='getTablaListadoCaratulas()' <%=disabled%> >
                                <option value="-1"> -- Seleccione un ramo -- </option>
                                <%
                                    if (ramoList.size() > 0) {
                                        for (Ramo ramoTemp : ramoList) {
                                            if (ramoId.equals(ramoTemp.getRamo())) {
                                                out.print("<option selected  value=" + ramoTemp.getRamo() + ">" + ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr() + " </option>");
                                            } else {
                                                out.print("<option value=" + ramoTemp.getRamo() + ">" + ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr() + " </option>");
                                            }
                                        }
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                </table>                      
            </div>
        </fieldset>
    </div>
    <br>
    <br>
    <br>
    <div id="TableZone" >
        <table id="tblCaratula" class="display" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th align="right" >Fecha de Sesión</th>
                    <th align="right" >Número de Sesión</th>                    
                    <th align="right" >Tipo Sesión</th>
                    <th align="right" >Número de Modificación Presupuestal</th>
                    <th align="right" >Número de Modificación Programática</th>
                    <th align="right" > </th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>

    <form id="frmConsulta" action="caratula.jsp" method="post">
        <input type="hidden" id="caratula" name="caratula">
        <input type="hidden" id="ramoId" name="ramoId">
        <input type="hidden" id="urlRegresa" name="urlRegresa" value="listadoCaratulas.jsp">
    </form>

    <script>
        getTablaListadoCaratulas();
    </script>
    <%
    }else{
        caratulaList = caratulaBean.getResultSQLObtieneCaratulasEvaluacion(year);
    %>
    <table id="tblCaratula">
        <thead>
            <tr>
                <th>Ramo</th>      
                <th>Fecha de Sesión</th>              
                <th>Número de Sesión</th>                    
                <th>Tipo Sesión</th>
                <th>Número de Modificación Presupuestal</th>
                <th>Número de Modificación Programática</th>
                <th>Vo. Bo. Evaluaci&oacute;n</th>
            </tr>
        </thead>
        <tbody>
                <%
            for(Caratula caratula : caratulaList){
                out.write("<tr>");
                out.write("<td>"+caratula.getsRamo()+"</td>");
                out.write("<td>"+caratula.getsFechaRevision()+"</td>");
                out.write("<td>"+caratula.getsNumSesionDescr()+"</td>");
                out.write("<td>"+caratula.getsTipoSesionDescr()+"</td>");
                out.write("<td>"+caratula.getsModPresupDescr()+"</td>");
                out.write("<td>"+caratula.getsModProgDescr()+"</td>");
                out.write("<td>");
                if(caratula.getEvaluado().equals("S")){
                    out.write("<input id='eva"+caratula.getsIdCaratula()+"' type='checkbox' checked disabled />");
                }else{
                    out.write("<input id='eva"+caratula.getsIdCaratula()+"' type='checkbox' onChange='evaluarCaratula("+caratula.getsIdCaratula()+")'/>");
                }
                out.write("</td>");
                out.write("</tr>");
            }
        %>
        </tbody>
    </table>        
        <form method="POST" id="rptEvaluacion" action="ejecutaReporte/ejecutarReporte.jsp" target="_blank">
            <input type="hidden" id="filename" name="filename" value='rptCaratulasEvaluadas.jasper'/>
            <input type="hidden" id="rptPath" name="rptPath" value='Evaluacion'/>
            <input type="hidden" id="reporttype" name="reporttype" value='PDF'/>
        </form>
        <script>
            dataTablePOA("tblCaratula");
        </script>
    <%
    }
    %>
    <script src="librerias/_evaluacion.js" type="text/javascript"></script>
    <jsp:include page="template/piePagina.jsp" />
</html>
