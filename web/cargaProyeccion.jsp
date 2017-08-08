<%-- 
    Document   : cargaProyeccion
    Created on : Aug 20, 2015, 9:06:03 AM
    Author     : ugarcia
--%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.Proyeccion"%>
<%@page import="org.json.JSONObject"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.CargaProyeccionBean"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carga de proyecci&oacute;n</title>
    </head>
    <%
        CargaProyeccionBean cargaBean = null;
        Bitacora bitacora = new Bitacora();
        List<Proyeccion> proyeccionList = new ArrayList<Proyeccion>();
        List<String> yearList = new ArrayList<String>();
        BigDecimal totalProyeccion = new BigDecimal(0.0);
        String disabled = new String();
        String selRamo = new String();
        String selected = new String();
        String display = new String();
        NumberFormat numberF = NumberFormat.getInstance(Locale.US);
        int countRow = 0;
        JSONObject json = null;
        String srtCarga = new String();
        try {
            if (session.getAttribute("strUsuario") == null
                    || session.getAttribute("tipoDependencia") == null){
                response.sendRedirect("logout.jsp");
                return;
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
            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }
            if(session.getAttribute("selRamo") != null && session.getAttribute("selRamo") != ""){
                selRamo = (String) session.getAttribute("selRamo");
                session.removeAttribute("selRamo");
            }
            if(selRamo.isEmpty()){
                display = "display:none;";
            }
            cargaBean = new CargaProyeccionBean(tipoDependencia);
            List<Ramo> ramoList = new ArrayList<Ramo>();
            cargaBean.setStrServer((request.getHeader("Host")));
            cargaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            cargaBean.resultSQLConecta(tipoDependencia);
            ramoList = cargaBean.getResultRamoByYear(year, usuario);
            if (session.getAttribute("resultadoCarga") != null) {
                proyeccionList = (List<Proyeccion>) session.getAttribute("resultadoCarga");
            }
            if (session.getAttribute("jason") != null) {
                json = (JSONObject) session.getAttribute("jason");
                if (json != null) {
                    if (json.get("resultado").equals("0")) {
                        srtCarga = "alertExito";
                    } else {
                        srtCarga = "alertFallo";
                    }
                }
            }
            yearList = cargaBean.getYears();
            cargaBean.resultSQLDesconecta();
            if (!String.valueOf(year).equals(yearList.get(2))) {
                disabled = "disabled";
            }
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label  >Carga de proyecci&oacute;n<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-Excel" onclick="enviarDocumento()" <%=disabled%> >
                <br/> <small> Cargar </small>
            </button>
            <button type="button" class="btnbootstrap btn-guardar" onclick="guardarExcel()" <%=disabled%> >
                <br/> <small> Guardar </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div" >
        <form id="form-captura" action="cargarExcel" method="POST" enctype="multipart/form-data">
            <table id="tblPrgAct">
                <tr>
                    <td>
                        <label>Ramo:</label>
                    </td>
                    <td>
                        <select id="selRamo" name="selRamo" onchange='validaRamoCargaProyeccion()' <%=disabled%> >
                            <option value="-1">
                                -- Seleccione un ramo --
                            </option>
                            <%
                                for (Ramo ramo : ramoList) {
                                    if(ramo.getRamo().equals(selRamo)){
                                        selected = "selected";
                                    }
                                    out.print("<option value='" + ramo.getRamo() + "' "+selected+">");
                                    out.print(ramo.getRamo() + "-" + ramo.getRamoDescr());
                                    out.print("</option>");
                                    selected = new String();
                                }
                            %>
                        </select>
                    </td>
                </tr>
            </table>
            <br/>
            <input type="file" name="datafile" size="40" id='archivo' style='<%= display %>'
                   accept=".xlsx,.XLSX"   alt="Solo se permiten archivos con formato de excel"/>
        <div id="mensaje">
        </div>
        </form>        
        <br/>
        <%
        
        if(!selRamo.isEmpty()){
            if (proyeccionList.size() > 0) {
        %>
        <div id="divTblProyeccion" >
            <center>
                <table id="tblProyeccion" class="table">
                    <thead>
                        <tr>
                            <th>Ramo</th>
                            <th>Programa</th>
                            <th>Partida</th>
                            <th>Relaci&oacute;n laboral</th>
                            <th>Proyectado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                                for (Proyeccion proye : proyeccionList) {
                                    countRow++;
                                    if (countRow % 2 == 0) {
                                        out.print("<tr class='rowPar'>");
                                    } else {
                                        out.print("<tr>");
                                    }
                                    out.print("<td>" + proye.getRamo() + "</td>");
                                    out.print("<td>" + proye.getPrograma() + "</td>");
                                    out.print("<td>" + proye.getPartida() + "</td>");
                                    out.print("<td>" + proye.getRelLaboral() + "</td>");
                                    out.print("<td>" + numberF.format(proye.getProyectado()) + "</td>");
                                    totalProyeccion = totalProyeccion.add(new BigDecimal(proye.getProyectado()));
                                }
                           
                        %>
                    </tbody>
                </table>
            </center>
        </div>
        <%
                    out.print("<div id='divTotalProyeccion'>");
                    out.print("Total: " + numberF.format(totalProyeccion.setScale(2, RoundingMode.HALF_UP).doubleValue()));
                    out.print("</div>");
                     }
                }else{
                    session.removeAttribute("resultadoCarga");
                }

            } catch (Exception ex) {
                bitacora.setStrUbicacion(cargaBean.getStrUbicacion());
                bitacora.setStrServer(cargaBean.getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                //session.setAttribute("resultadoCarga", null);
            }
        %> 
        <div class="<%=srtCarga%>" id="infoCarga">
            <%
                if (!srtCarga.equals("")) {
                    session.removeAttribute("jason");
                }
                if (json != null) {%>
            <p><%=json.get("mensaje")%></p>
            <%
                if (json.get("resultado").equals("-2")) {%>
            <p><%=json.get("badCode")%></p>
            <%}%>
            <%}%>
        </div>
    </div>
    <jsp:include page="template/piePagina.jsp" />
</html>
