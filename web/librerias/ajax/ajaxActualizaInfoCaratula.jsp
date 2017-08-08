<%-- 
    Document   : ajaxActualizaInfoCaratula
    Created on : Jan 11, 2016, 4:32:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Caratula objCaratula = null;
    Caratula carTemp = null;
    CaratulaBean caratulaBean = new CaratulaBean();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String ramoSession = new String();
    String sFecha = new String();
    String sTipoSesion = "";
    String sSesionProg = "";
    String sSesionPres = "";
    String sNoSesion = new String();
    String strResult = new String();
    String tipoDependencia = new String();
    String sTablaDatos = "";
    String rowPar = "";
    long selCaratula = 0;
    int proyId = 0;
    int year = 0;
    boolean blResultado = false;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }

        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }

        caratulaBean.setStrServer(request.getHeader("host"));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(caratulaBean.getsConexionBD());

        objCaratula = caratulaBean.getCaratulaByYearIdRamoIdCaratula(String.valueOf(year), ramoSession, selCaratula);

        if (objCaratula != null) {
            sTablaDatos += " <table>";
            sTablaDatos += "     <tr>";
            sTablaDatos += "         <td>";
            sTablaDatos += "             <label>Fecha Registro:</label>";
            sTablaDatos += "         </td>";
            sTablaDatos += "         <td>";
            if (objCaratula.getsFechaRegistro() != null) {
                sTablaDatos += "             <input id='fechaRegistro' class='info-usuario-der'  value='" + df.format(formatter.parse(objCaratula.getsFechaRegistro())) + "' disabled/>";
            } else {
                sTablaDatos += "             <input id='fechaRegistro' class='info-usuario-der'  value='" + "" + "' disabled/>";
            }
            sTablaDatos += "         </td>";
            sTablaDatos += "         <td>";
            sTablaDatos += "             <label>Numero Sesi&oacute;n:</label>";
            sTablaDatos += "         </td>";
            sTablaDatos += "         <td>";
            sTablaDatos += "             <input id='numeroSesion' class='info-usuario-der'  value='" + objCaratula.getsNumSesionDescr() + "' disabled/>";
            sTablaDatos += "         </td>";
            sTablaDatos += "     </tr>";
            sTablaDatos += "     <tr>";
            sTablaDatos += "         <td>";
            sTablaDatos += "             <label>Fecha Revisi&oacute;n:</label>";
            sTablaDatos += "         </td>";
            sTablaDatos += "         <td>";
            if (objCaratula.getsFechaRevision() != null) {
                sTablaDatos += "             <input id='fechaRevision' class='info-usuario-der'  value='" + df.format(formatter.parse(objCaratula.getsFechaRevision())) + "' disabled/>";
            } else {
                sTablaDatos += "             <input id='fechaRevision' class='info-usuario-der'  value='" + "" + "' disabled/>";
            }
            sTablaDatos += "         </td>";
            sTablaDatos += "         <td>";
            sTablaDatos += "             <label>Tipo Sesi&oacute;n:</label>";
            sTablaDatos += "         </td>";
            sTablaDatos += "         <td>";
            sTablaDatos += "             <input id='tipoSesion' class='info-usuario-der'  value='" + objCaratula.getsTipoSesionDescr() + "' disabled/>";
            sTablaDatos += "         </td>";
            sTablaDatos += "     </tr>";
            sTablaDatos += " </table>";
            strResult = sTablaDatos;

        }

    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
