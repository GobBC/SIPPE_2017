<%-- 
    Document   : asignacionIndicadoresRamoSector
    Created on : 02/11/2016, 11:30:20 AM
    Author     : jarguelles
--%>

<%@page import="java.io.File"%>
<%@page import="gob.gbc.entidades.SectorRamo"%>
<%@page import="gob.gbc.aplicacion.SectorRamoBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.IndicadorGeneralSei"%>
<%@page import="gob.gbc.aplicacion.IndicadorGeneralSeiBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache"> 
        <meta http-equiv="Expires" content="-1">
        <title>Asignaci&oacute;n De Indicadores Por Ramo Sector</title>
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label>Asignaci&oacute;n De Indicadores Por Ramo Sector<label/></div>
    <%
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Expires", "0");
        response.setDateHeader("Expires", -1);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");

        SectorRamoBean sectorRamoBean = null;
        SectorRamo objSectorRamo = new SectorRamo();
        IndicadorGeneralSeiBean indicadorGeneralSeiBean = null;
        IndicadorGeneralSei objIndicadorGenralSei = new IndicadorGeneralSei();

        ArrayList<SectorRamo> arrSectoresRamos = null;
        ArrayList<IndicadorGeneralSei> arrIndicadoresGeneralesSei = null;

        String rol = new String();
        String year = new String();
        String appLogin = new String();
        String tipoDependencia = new String();

        boolean isNormativo = false;

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("strUsuario") == null || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = (String) session.getAttribute("year");
        }
        if (session.getAttribute("tipoDependencia") == null || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }

        indicadorGeneralSeiBean = new IndicadorGeneralSeiBean(tipoDependencia);
        indicadorGeneralSeiBean.setStrServer((request.getHeader("Host")));
        indicadorGeneralSeiBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        indicadorGeneralSeiBean.resultSQLConecta(tipoDependencia);

        sectorRamoBean = new SectorRamoBean(tipoDependencia);
        sectorRamoBean.setStrServer((request.getHeader("Host")));
        sectorRamoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        sectorRamoBean.resultSQLConecta(tipoDependencia);

        isNormativo = rol.equals(indicadorGeneralSeiBean.getRolNormativo());

        if (!isNormativo) {
            out.print("<script> if(confirm('Solo un usuario normativo puede modificar los parámetros del sistema.')){ Home(); } else { Home(); }  </script>");
        }

        arrIndicadoresGeneralesSei = indicadorGeneralSeiBean.getObtieneIndicadoresGeneralesSei(year);      

    %>

    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button id="botonPdf" type="submit" class="btnbootstrap btn-reporte" onclick="document.getElementById('frmActInfoRpt').submit();" >
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
        <input type="hidden" id="filename" name="filename" value="<%="ReportesIndicadores" + File.separatorChar + "rptIndicadoresSectorRamo.jasper"%>"/> 
        <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
    </form>
    <div>
        <% if (isNormativo) {%>             

        <div>
            <table id="tblComboIndicador">
                <tr>
                    <td>
                        <div id='txtIndicador' >
                            Indicador:
                        </div> 
                    </td>
                    <td>
                        <select id="selIndicador" name="selIndicador" onchange='getProgramasFinByYearRamoIndicador(); getRamosSelectorByYearIndicador();  getIndicadoresSectorRamosByYearClaveIndicador();'>
                            <option value="-1"> -- Seleccione un indicador -- </option>
                            <%
                                if (arrIndicadoresGeneralesSei != null) {
                                    for (IndicadorGeneralSei indicadorTemp : arrIndicadoresGeneralesSei) {
                                        out.print("<option value=" + indicadorTemp.getClaveIndicador() + ">" + indicadorTemp.getClaveIndicador() + " - " + indicadorTemp.getNombreIndicador() + " </option>");
                                    }
                                }
                            %>
                        </select>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id='txtSectorRamo' >
                            Ramo:
                        </div> 
                    </td>
                    <td>
                        <select id="selSectorRamo" name="selSectorRamo" onchange='getProgramasFinByYearRamoIndicador(); getIndicadoresSectorRamosByYearClaveIndicador();'>
                            <option value="-1"> -- Seleccione un ramo -- </option>
                        </select>
                    </td>
                    <td>

                    </td>
                </tr>
                <tr>
                    <td>
                        <div id='txtPrograma' >
                            Programa:
                        </div> 
                    </td>
                    <td>
                        <select style=" width: 400px; " id="selPrograma" name="selPrograma" onchange=''>
                            <option value="-1"> -- Seleccione un programa -- </option>
                        </select>
                    </td>
                    <td>
                        <input type="button" value="Asignar" id="btn-Asignar" onclick="ligarIndicadorSectorRamo()"/>
                    </td>
                </tr>                
            </table>    
        </div> 

        <br>

        <div id="divPresupuesto">
            <div id="divPres">
            </div>
            <br/>
            <div style="display: none">
                <center>
                    <%--<input type="button" value="Insertar" onclick="insertSubConceptoIngreso();"/>--%>
                </center>
            </div>
        </div>



        <%  }   %>  
        <div id="mensaje" style="position: relative"></div>
    </div>
    <jsp:include page="template/piePagina.jsp" />
    <%
        if (indicadorGeneralSeiBean != null) {
            indicadorGeneralSeiBean.resultSQLDesconecta();
        }
        if (sectorRamoBean != null) {
            sectorRamoBean.resultSQLDesconecta();
        }
    %>

</html>
