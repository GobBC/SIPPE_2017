<%-- 
    Document   : presupuestoIngresos
    Created on : Jul 7, 2015, 2:01:48 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.ConceptoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaPresupuestoBean"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captura de Presupuesto de ingreso</title>
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
        List<ConceptoIngreso> conceptoList = new ArrayList<ConceptoIngreso>();
        CapturaPresupuestoBean capturaPresBean = null;
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
        capturaPresBean = new CapturaPresupuestoBean(tipoDependencia);
        capturaPresBean.setStrServer(( request.getHeader("Host")));
        capturaPresBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaPresBean.resultSQLConecta(tipoDependencia);
        ramoList = capturaPresBean.getRamosByUsuario(year, usuario);
        conceptoList = capturaPresBean.getConceptoIngreso(year);
        capturaPresBean.resultSQLDesconecta();
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >Captura de Presupuesto de ingreso<label/> </div>
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
                <td> Ramo: </td>
                <td>
                    <select id="selRamo" name="selRamo" onchange='getPresupuestoIngreso()'>
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
            <tr>
                <td> Concepto: </td>
                <td>
                    <select id="selConcepto" name="selPrograma" onchange='getPresupuestoIngreso()'>
                        <option value='-1'>
                            -- Seleccione un Concepto --
                        </option>
                        <%
                            for(ConceptoIngreso concepto : conceptoList){
                                out.write("<option value='"+concepto.getConceptoPresupuestoId()+"'>");
                                out.write(concepto.getConceptoPresupuesto());
                                out.write("</option>");
                            }
                        %>
                    </select>
                </td>
            </tr>
        </table>
        <br/>
        <div id="divPresupuesto">
            <div id="divPres">
                
            </div>
            <br/>
            <div style="display: none">
                <center>
                    <input type="button" value="Insertar" onclick="insertPresupuesto()"/>
                    <input type="button" value="Editar" onclick="mostrarPresupuesto()"/>
                    <input type="button" value="Eliminar" onclick="borrarPresupuesto()"/>
                </center>
            </div>
        </div>
    </div>
    <div id="infoPresupuestacion" style="display: none">
    </div>
    <jsp:include page="template/piePagina.jsp" />
</html>
