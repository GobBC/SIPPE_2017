<%-- 
    Document   : generarEstructuraPresupuestal
    Created on : Mar 18, 2015, 2:00:52 PM
    Author     : jarguelles
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
        <title>Generar Estructura Presupuestal</title>
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
        String usuario = new String();
        String tipoDependencia = new String();
        String tempMillis = new String();
        tempMillis = "" + System.currentTimeMillis();
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
    <script>
        $body = $("body");

        $(document).on({
            ajaxStart: function() { $body.addClass("loading");},
            ajaxStop: function() { $body.removeClass("loading"); }    
        });
    </script>
    <body>
        <div Id="TitProcess"><label  >Generar Estructura Presupuestal<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <button type="button" class="btnbootstrap btn-generar" onclick="generarInformacionPresubpuestal()">
                    <br/> <small> Generar </small>
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
                            <select id="selRamo" name="selRamo" >
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
                            <input type="hidden" id="numTemp" name="numTemp" value="<%=tempMillis%>">
                        </td>
                    </tr>
                </table>
                <br/>
                <br/>
                <div id="mensajesRespuestas">
                </div>
                <div id="seleccionArchivo" >
                    <a id="donwLink" name="donwLink" href="temp/TempEstructuraPresupuestal<%=tempMillis%>/EstructuraPresupuestal<%=tempMillis%>.zip" download></a>
                    <a id="cosa" name="cosa" href="http://ridiculousfish.com/hexfiend/images/download_square.png" download></a>
                </div>
            </form>

        </div>
        <div id="mensaje"></div>

    </body>
    <jsp:include page="template/piePagina.jsp" />
</html>
