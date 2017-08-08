<%-- 
    Document   : migrarRequerimientos
    Created on : Sep , 2016, 12:10:00 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.FuenteRecurso"%>
<%@page import="gob.gbc.aplicacion.MigracionRequerimientoBean"%>
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
        <title>Cambio de fuente de financiamiento</title>
    </head>
    <%
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }

        MigracionRequerimientoBean migracionRequerimientoBean = null;

        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<FuenteRecurso> fuenteFinList = new ArrayList<FuenteRecurso>();

        String rol = new String();
        String ramoId = new String();
        String appLogin = new String();
        String disabled = new String();
        String ramoSession = new String();
        String tipoDependencia = new String();
        
        int year = 0;

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("strUsuario") == null || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("tipoDependencia") == null || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        migracionRequerimientoBean = new MigracionRequerimientoBean(tipoDependencia);
        migracionRequerimientoBean.setStrServer((request.getHeader("Host")));
        migracionRequerimientoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        migracionRequerimientoBean.resultSQLConecta(tipoDependencia);

        ramoList = migracionRequerimientoBean.getResultRamoByYear(year, appLogin);
        fuenteFinList = migracionRequerimientoBean.getResultSQLGetFuenteRecursoCompleto(year);
        
        if (!rol.equals(migracionRequerimientoBean.getRolNormativo())) {
            ramoId = ramoSession;
        }

        migracionRequerimientoBean.resultSQLDesconecta();

    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label  >Cambio de fuente de financiamiento<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div id="mensaje" style="position: relative"></div>
    <div class="center-div" style="margin: 0px; width: 100%">
        <form id="frmDefMetas" name="frmDefMetas" action='capturaAccion.jsp' method="POST">
            <input type="hidden" id="metaId" name='metaId' value="" />
            <div id="div-drop" style="position: relative;margin: 20px 19%">
                <table id="tblMeta">
                    <tr>
                        <td> Ramo: </td>
                        <td>
                            <select <%= disabled%> id="selRamo" name="selRamo" onchange='validaRamoCargaCambioFuenteFondo()'>
                                <option value="-1"> -- Seleccione un ramo -- </option>
                                <%
                                    if (ramoList.size() > 0) {
                                        for (Ramo ramoTemp : ramoList) {
                                                out.print("<option value=" + ramoTemp.getRamo() + ">" + ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr() + " </option>");                                           
                                        }
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td> Fuente de financiamento: </td>
                        <td>
                            <select id="selFuente" name="selFuente" onchange="getAccionesByFuenteFinanciamiento()" >
                                <option value="-1"> -- Seleccione una fuente de financiamiento-- </option>
                                <%
                                  for(FuenteRecurso fuenteFin : fuenteFinList){
                                      out.print("<option value='"+fuenteFin.getFuente()+"."+fuenteFin.getFondo()+"."+fuenteFin.getRecurso()+"'>"
                                              + "" + fuenteFin.getFuente()+"."+fuenteFin.getFondo()+"."+fuenteFin.getRecurso()+"-"+fuenteFin.getFuenteRecursoDescr()+"</option>" );
                                  }  
                                %>
                            </select>
                        </td>
                   </tr>
                   <tr>
                        <td> Nueva fuente de financiamento: </td>
                        <td>
                            <select id="selFuenteNueva" name="selFuenteNueva" onchange="" >
                               <option value="-1"> -- Seleccione una fuente de financiamiento nueva-- </option>
                               <%
                                  for(FuenteRecurso fuenteFin : fuenteFinList){
                                      out.print("<option value='"+fuenteFin.getFuente()+"."+fuenteFin.getFondo()+"."+fuenteFin.getRecurso()+"'>"
                                              + "" + fuenteFin.getFuente()+"."+fuenteFin.getFondo()+"."+fuenteFin.getRecurso()+"-"+fuenteFin.getFuenteRecursoDescr()+"</option>" );
                                  }  
                                %>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="col-md-12 text-center" style="margin-top: 20px">
                <label> Acciones por fuente de financiamiento </label>
            </div>   
        <br/>  
        <div class="col-md-12">
            <table id="tbl-accion-fuente" class="table table-striped table-responsive table-fixed">
                <thead>
                    <tr>
                        <th>
                            <input type="checkbox" id="selAllCheck" onchange="selectAllCheck();calculaTodalMetaAccionSeleccionada();" checked/>
                        </th>
                        <th>
                            Meta
                        </th>
                        <th>
                            Descripci&oacute;n meta
                        </th>
                        <th>
                            Acci&oacute;n
                        </th>
                        <th>
                            Descripci&oacute;n acci&oacute;n
                        </th>
                        <th>
                            Total
                        </th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            
            <div class="col-md-12 text-right">
                    <label>Total:</label>
                    <label id='total-cambios'/>
            </div>
            
        </div>
        <div >
            <input class="center-block" type="button" id="btnProcesar" value="Procesar cambio" onclick="cambiarFuenteFinanciamiento()"/>
        </div>
                
        </form>
    </div>
    <%
        if (migracionRequerimientoBean != null) {
            migracionRequerimientoBean.resultSQLDesconecta();
        }
    %>
    <script>
        calculaTodalMetaAccionSeleccionada();
    </script>
    <jsp:include page="template/piePagina.jsp" />
</html>
