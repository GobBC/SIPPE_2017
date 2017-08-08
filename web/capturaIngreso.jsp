<%--
    Document   : capturaIngreso
    Created on : 21/04/2016, 1:56:20 AM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.CapturaPresupuestoIngreso"%>
<%@page import="gob.gbc.entidades.ConceptoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaIngresoBean"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.TipoSesion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captura Ingreso</title>
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <%

        if (session.getAttribute("strUsuario") == null || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }

        CaratulaBean caratulaBean = null;
        CapturaIngresoBean capturaIngresoBean = null;

        ArrayList<Caratula> arrCaratulas = null;
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<ConceptoIngreso> conceptoList = new ArrayList<ConceptoIngreso>();
        List<CapturaPresupuestoIngreso> capturaPresupuestoList = new ArrayList<CapturaPresupuestoIngreso>();

        String rol = new String();
        String styleNone = "";
        String disabledCierre = "";        
        String appLogin = new String();
        String urlRegresa = new String();
        String ramoSession = new String();
        String fechaSession = new String();
        String numeroSession = new String();
        String tipoDependencia = new String();

        double ene = 0.0;
        double feb = 0.0;
        double mar = 0.0;
        double abr = 0.0;
        double may = 0.0;
        double jun = 0.0;
        double jul = 0.0;
        double ago = 0.0;
        double sep = 0.0;
        double oct = 0.0;
        double nov = 0.0;
        double dic = 0.0;
        double total = 0.0;

        int year = 0;

        boolean bFiltraEstatusAbiertas = false;

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependecia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (Utilerias.existeParametro("urlRegresa", request)) {
            urlRegresa = (String) request.getParameter("urlRegresa");
        }
        if (Utilerias.existeParametro("fechaSession", request)) {
            fechaSession = (String) request.getParameter("fechaSession");
        }
        if (Utilerias.existeParametro("numeroSession", request)) {
            numeroSession = (String) request.getParameter("numeroSession");
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }

        capturaIngresoBean = new CapturaIngresoBean(tipoDependencia);
        capturaIngresoBean.setStrServer((request.getHeader("Host")));
        capturaIngresoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaIngresoBean.resultSQLConecta(tipoDependencia);

        ramoList = capturaIngresoBean.getRamosByUsuario(year, appLogin);
        conceptoList = capturaIngresoBean.getConceptoIngreso(year);

        if (!rol.equals(capturaIngresoBean.getRolNormativo())) {
            disabledCierre = "disabled";
            styleNone = "display:none;";
        }

    %>
    <div Id="TitProcess"><label >Captura Ingreso<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>

    <div> 
        <table id="tblMeta" style="border-collapse:separate; border-spacing:10px;">
            <tr>
                <td>
                    <label style="<%=styleNone %>" <%= disabledCierre %> id="lCheckStatus" >Ingreso Abierto:</label>
                </td>
                <td>
                    <input style="<%=styleNone %>" <%= disabledCierre %> checked type='checkbox' id='checkStatus' onchange='actualizaStatusIngresoModificado();' >         
                </td>
                <td>
                </td>
            </tr>
            <tr>
               <!-- <td>
                    Presupuesto:
                </td>-->
                <td>
                    <input id="inp-periodo" name="inp-periodo" type="hidden" value="<%= year%>" />
                   <!-- <input id="btn-decr" type="button" value="-" onclick="decrPer(); getSubconceptosCapturaPresupuestoIngreso(); getSubconceptosCapturaIngresos(); getStatusIngresoModificado();"/>
                    <input id="btn-incr" type="button" value="+" onclick="incrPer(); getSubconceptosCapturaPresupuestoIngreso(); getSubconceptosCapturaIngresos(); getStatusIngresoModificado();"/>-->
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td> Ramo: </td>
                <td>
                    <select id="selRamo" name="selRamo"  onchange='getCaratulasByRamo(); getSubconceptosCapturaPresupuestoIngreso(); getSubconceptosCapturaIngresos(); getStatusIngresoModificado();' >
                        <option value="-1"> -- Seleccione un ramo -- </option>
                        <%
                            if (ramoList.size() > 0) {
                                for (Ramo ramo : ramoList) {
                                    out.print("<option value=" + ramo.getRamo() + ">" + ramo.getRamo() + "-" + ramo.getRamoDescr() + " </option>");
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
                    Caratula: 
                </td> 
                <td> 
                    <select id='selCaratula' name='selCaratula' onchange='getSubconceptosCapturaPresupuestoIngreso(); getSubconceptosCapturaIngresos(); getStatusIngresoModificado();' > 
                        <option value='-1' Selected > -- Seleccione un caratula -- </option> 
                    </select> 
                </td> 
            </tr> 
            <tr>
                <td> Concepto: </td>
                <td>
                    <select id="selConcepto" name="selConcepto" onchange='getSubconceptosCapturaPresupuestoIngreso(); getSubconceptosCapturaIngresos(); getStatusIngresoModificado();' >
                        <option value="-1"> -- Seleccione un Concepto -- </option>
                        <%
                            for (ConceptoIngreso concepto : conceptoList) {
                                out.write("<option value='" + concepto.getConceptoPresupuestoId() + "'>");
                                out.write(concepto.getConceptoPresupuesto());
                                out.write("</option>");
                            }
                        %>
                    </select>
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td> Subconcepto: </td>
                <td>
                    <select id="selSubconcepto" name="selSubconcepto" onchange="cambiarEstadoBySelect('Subconcepto')" >
                        <option value='-1'>
                            -- Seleccione un Subconcepto --
                        </option>
                    </select>
                </td>
                <td>
                    <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-Subconcepto" style="" onclick="insertSubConceptoIngreso();"/>
                    <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-Subconcepto" style="display:none" onclick="editSubConceptoIngreso();"/>
                </td>
            </tr>
        </table>
    </div>

    <br/>

    <div id="divPresupuesto">
        <div id="divPres">
        </div>
        <br/>
        <div style="display: none">
            <center>
                <%--<input type="button" value="Insertar" onclick="insertSubConceptoIngreso();"/>--%>
                <input id="botonEditar" type="button" value="Editar" onclick="editarSubConceptoCapturaIngreso()"/>
            </center>
        </div>
    </div>

    <div id="infoPresupuestacion" style="display: none">
    </div>           


    <div id="PopUpZone">        
    </div>
    <div id="mensaje">        
    </div>
    <jsp:include page="template/piePagina.jsp" />
    <script>    
    </script>
    <%
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
        if (capturaIngresoBean != null) {
            capturaIngresoBean.resultSQLDesconecta();
        }

    %>
</html>
