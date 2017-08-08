<%-- 
    Document   : cargaDePlantillaCodigos
    Created on : Jul 13, 2015, 9:23:35 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        String usuario = new String();
        String tipoDependencia = new String();
        String selRamo = new String();
        String select = new String();
        String display  = new String();
        boolean paraestatal = false;
        List<Ramo> ramoList = new ArrayList<Ramo>();
        int year = 0;
        /*if(year == 2015){
            session.removeAttribute("strUsuario");
            session.removeAttribute("tipoDependencia");
        }*/
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
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
        if (session.getAttribute("selRamo") != null && session.getAttribute("selRamo") != "") {
            selRamo = (String) session.getAttribute("selRamo");
            session.removeAttribute("selRamo");
        }
        ResultSQL resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer(((String) request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(tipoDependencia);
        ramoList = resultSQL.getResultRamoByYear(year, usuario);
        paraestatal = resultSQL.getResultSQLisParaestatal();
        resultSQL.resultSQLDesconecta();
        JSONObject json = null;
        String srtCarga = new String();
        if (session.getAttribute("resultadoCarga") != null) {
            json = (JSONObject) session.getAttribute("resultadoCarga");
            if (json != null) {
                if (json.get("resultado").equals("0")) {
                    srtCarga = "alertExito";
                } else {
                    srtCarga = "alertFallo";
                }
            }
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CARGA DE PLANTILLA</title>
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >CARGA DE PLANTILLA<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-Excel" onclick="enviarDocumentoPlantilla();">
                <br/> <small> Cargar</small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div">
        <form id="form-captura-plantilla" action="cargarArchivo" method="POST" enctype="multipart/form-data" name="form-captura-plantilla">
            <%
            if(paraestatal){
            %>
            <table id="tblCargaPlantilla">
                <tr>
                    <td> Ramo: </td>
                    <td>
                        <select id="selRamo" name="selRamo" onchange='validaRamoCarga()'>
                            <option value="-1"> -- Seleccione un ramo -- </option>
                            <%
                                for (Ramo ramo : ramoList) {
                                    if(ramo.getRamo().equals(selRamo)){
                                        select = "selected";
                                    }
                                    out.write("<option value='" + ramo.getRamo() + "' "+select+" >");
                                    out.write(ramo.getRamo() + "-" + ramo.getRamoDescr());
                                    out.write("</option>");
                                    select = new String();
                                }

                            %>
                        </select>
                        <input type="hidden" id="hiddenRamo" name="hiddenRamo" value="" />
                        <input type="hidden" id="hidden88" name="hidden88" value="88" />
                    </td>
                </tr>
            </table>
            <br/>
            <% 
            if(selRamo.isEmpty()){
               display = "display:none";
            }
            %>
            <input type="file" name="datafile" size="40" id='archivo' style="<%=display%>"
                       accept=".xlsx,.XLSX"   alt="Solo se permiten archivos con extension .xls o .xlsx"/>
            <%}else{%>
                <input type="file" name="datafile" size="40" id='archivo'
                       accept=".xlsx,.XLSX"   alt="Solo se permiten archivos con extension .xls o .xlsx"/>
            <%
            }
            %>
        </form>
    </div>
    <center>
        <br/>
        <div id="mensaje">
        </div>
        <div class="<%=srtCarga%>" id="infoCarga">
            <%
                if (!srtCarga.equals("")) {
                    session.removeAttribute("resultadoCarga");
                }
                if (json != null) {%>
                    <p><%=json.get("mensaje")%></p>
            <%
                if(json.get("resultado").equals("-2")){ %>
                    <p><%=json.get("badCode")%></p>
                    <%}%>
       <%}%>
        </div>
    </center>
    <jsp:include page="template/piePagina.jsp" />
</html>
