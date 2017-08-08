<%-- 
    Document   : definicionPlantilla
    Created on : May 12, 2015, 04:54:40 PM
    Author     : jarguelles
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
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href='//fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
        <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <title>Definición de plantilla</title>

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
         /*variables para reporte*/
        String strRamoUsuario= new String();
        String rol= new String();
        int intIsNormativo=0;
        String strRamosReporte= new String();
        /*termina variables para reporte*/
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
        /*inicia para reporte*/
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        strRamoUsuario =(String) session.getAttribute("ramoAsignado");
        intIsNormativo=resultSQL.getRolNormativo(Integer.parseInt(rol));
        /*if(intIsNormativo==1){*/
            for (Ramo ramo : ramoList) {
               strRamosReporte +=ramo.getRamo()+","; 
            }
            strRamosReporte=strRamosReporte.substring(0,strRamosReporte.length()-1);
        /*}else{
            strRamosReporte=strRamoUsuario;
        }*/
        /*temina para reporte*/
        resultSQL.resultSQLDesconecta();

    %>
    <jsp:include page="template/encabezado.jsp" />

    <body> 
        <div Id="TitProcess"><label >Definición de plantilla<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <%--                 
                                <button type="button" class="btnbootstrap btn-guardar" onclick="actualizarAccionActivarPlantilla()">
                                    <br/> <small> Guardar </small>
                                </button>
                --%> 
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
           <!--inicia para reporte    -->
            <div  class="botones-menu_bootstrap">&nbsp;&nbsp;
                <input type="radio" name="reporte" id="opcion1" value="1" checked >Asignados&nbsp;&nbsp;
                <input type="radio" name="reporte" id="opcion2" value="2">Pendientes&nbsp;&nbsp;
                <button type="button" class="btnbootstrap btn-reporte" onclick="generarRepPendAsigPlantilla('<%=year%>','<%=strRamosReporte%>')">
                  <br/> <small> Reporte </small>
                </button>
            </div>
            <!--termina para reporte    -->      
        </div>
        <div class="center-div"> 
            <form>
                <div id="divCombo">
                    <table>
                        <tr>
                            <td> Ramo: </td>
                            <td>
                                <select id="selRamo" onchange="getPlantillasOnChange()" style="width: 460px;">
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
                    </table>
                </div>
                <div id="tabContainer" >
                    <input id="tabPorAsignar" type="radio" name="tab-group" checked="checked"/>
                    <label for="tabPorAsignar">
                        Pendiente por asignar
                    </label>
                    <input id="tabAsignado" type="radio" name="tab-group"/>
                    <label for="tabAsignado">
                        Asignados
                    </label>                    
                    <div id='tabContent'>
                        <div id='tabContent-1'>
                        </div>
                        <div id='tabContent-2'>
                        </div>                        
                    </div>
                </div>
                <div id="divInfoPlantilla" style="width: 600px" > 
                    <input id='contadorGrupos' type='hidden' value='0'/>
                    <input id='contadorRegistros' type='hidden' value='0' />
                    <input id='autoAcomodo' type='hidden' value='0' />
                </div>
            </form>
        </div>
        <div id="mensaje"></div>
        <jsp:include page="template/piePagina.jsp" />
    </body> 
</html>


