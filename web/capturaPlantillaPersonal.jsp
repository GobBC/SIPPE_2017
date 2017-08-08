<%-- 
    Document   : capturaPlantillaPersonal
    Created on : Jul 9, 2015, 1:50:14 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.PlantillaPersonalBean"%>
<%@page import="gob.gbc.entidades.PlantillaPersonal"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
        List<PlantillaPersonal> plantillaList = new ArrayList<PlantillaPersonal>();
        PlantillaPersonalBean plantillaPersonalBean = null;
        String tipoDependencia = new String();
        String usuario = new String();
        int year = 0;
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }        
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        plantillaPersonalBean = new PlantillaPersonalBean(tipoDependencia);
        plantillaPersonalBean.setStrServer(((String) request.getHeader("Host")));
        plantillaPersonalBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        plantillaPersonalBean.resultSQLConecta(tipoDependencia);
        ramoList = plantillaPersonalBean.getRamosPlantillaPersonal(usuario, year);
        plantillaPersonalBean.resultSQLDesconecta();
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >Captura de N&uacute;mero de Plazas Por Ramo<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div">
        <table id="tblMeta">
            <tr>
                <td>
                    Presupuesto:
                </td>
                <td>
                    <input type="number" id="yearPresu" value="<%=year%>">
                </td>
            </tr>
            <tr>
                <td> Ramo: </td>
                <td>
                    <select id="selRamo" name="selRamo" onchange='getPlantillaPersonal()'>
                        <option value="-1"> -- Seleccione un ramo -- </option>
                        <%
                            for (Ramo ramo : ramoList) {
                                out.write("<option value='" + ramo.getRamo() + "'>");
                                out.write(ramo.getRamoDescr());
                                out.write("</option>");
                            }

                        %>
                    </select>
                </td>                
            </tr>
        </table>
        <br/>
        <div id="divPlantillaPer">
            <div id="divPlant">
                
            </div>
            <br/>
            <div style="display: none">
                <center>
                    <input type="button" value="Insertar" onclick="insertPlantilla()"/>
                    <input type="button" value="Editar" onclick="mostrarPlantilla()"/>
                    <input type="button" value="Eliminar" onclick="borrarPlantilla()"/>
                </center>
            </div>
        </div>
        <div id="infoPlantillaPer" style="display: none">
            
        </div>
    </div>
    <jsp:include page="template/piePagina.jsp" />
</html>
