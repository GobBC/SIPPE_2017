<%-- 
    Document   : ajaxMostrarCaratulas
    Created on : Apr 1, 2016, 4:32:00 PM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CaratulaBean caratulaBean = new CaratulaBean();
    String ramoId = new String();
    String sFecha = new String();
    String sTipoSesion = "", sSesionProg = "", sSesionPres = "";
    int proyId = 0;
    String sNoSesion = new String();
    int year = 0;

    String strResult = new String();

    String tipoDependencia = new String();
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
            ramoId = (String) session.getAttribute("ramoAsignado");
        }

        ArrayList<Caratula> arCaratulas = null;

        caratulaBean.setStrServer(request.getHeader("host"));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(caratulaBean.getsConexionBD());
        Caratula carTemp = null;
        arCaratulas = caratulaBean.getCaratulas(String.valueOf(year), ramoId, false, 1,false);
        String sTablaDatos = "";
        if (arCaratulas != null) {
            if (arCaratulas.size() > 0) {
                sTablaDatos = "<div id='infoCaratulas'><table width='800px' ><tr><th>&nbsp;</th><th>Numero de Sesion</th><th>Fecha de Revision</th>"
                        + "<th>TipoSesion</th><th>ID Caratula</th><th>Estatus</th><th>Fecha de Registro</th>"
                        + "<th>Usuario</th></tr>";
                String rowPar = "";
                for (int iRow = 0; iRow < arCaratulas.size(); iRow++) {
                    carTemp = arCaratulas.get(iRow);

                    if (iRow % 2 == 0) {
                        rowPar = "rowPar";
                    } else {
                        rowPar = "rowImpar";
                    }
                    sTablaDatos += "<tr class='" + rowPar + "'><td><button type=\"button\" "
                            + " class=\"btnbootstrap btn-edicion\" onclick=\"cargaInfoCaratulas('" + carTemp.getsNumeroSesion()
                            + "','" + carTemp.getsFechaRevision() + "',"
                            + carTemp.getiTipoSesion() + ")\"</td><td>" + carTemp.getsNumeroSesion() + "</td>"
                            + "<td>" + carTemp.getsFechaRevision() + "</td>"
                            + "<td>" + carTemp.getsDescr() + "</td>"
                            + "<td>" + carTemp.getsIdCaratula() + "</td>"
                            + "<td>" + carTemp.getiStatus() + "</td>"
                            + "<td>" + carTemp.getsFechaRegistro() + "</td>"
                            + "<td>" + carTemp.getsAppLogin() + "</td></tr>";
                }
                sTablaDatos += "</table><input type=\"button\" onclick=\"javascript:$('#PopUpZone').hide()\" value= 'Cerrar'/></div>";
                JSONObject json = new JSONObject();
                /* if (carTemp != null) {
                 json.put("resultado", "1");
                 json.put("prog", carTemp.getsNumodProg());
                 json.put("pres", carTemp.getsNumModPres());
                 ;
                 json.put("id", carTemp.getsIdCaratula());
                 } else {
                 json.put("resultado", "0");
                 }*/
                strResult = sTablaDatos;
            }
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
