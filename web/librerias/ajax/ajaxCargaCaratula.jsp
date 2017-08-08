<%-- 
    Document   : ajaxCargaCaratula
    Created on : May 25, 2016, 4:32:00 PM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    CaratulaBean caratulaBean = new CaratulaBean();
    String ramoId = new String();
    String sFecha = new String();
    String sTipoSesion = "0", sSesionProg = "", sSesionPres = "";
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


        if (request.getParameter("fecha") != null
                && !request.getParameter("fecha").equals("")) {
            sFecha = (String) request.getParameter("fecha");
        }
        if (request.getParameter("txtSesion") != null
                && !request.getParameter("txtSesion").equals("")) {
            sNoSesion = (String) request.getParameter("txtSesion");
        }
        if (request.getParameter("selTipo") != null
                && !request.getParameter("selTipo").equals("")) {
            sTipoSesion = (String) request.getParameter("selTipo");
        }
       
        Caratula caratula = new Caratula();
        caratula.setiTipoSesion(Integer.parseInt(sTipoSesion));
        caratula.setiYear(year);
        caratula.setsAppLogin((String) session.getAttribute("strUsuario"));
        caratula.setsFechaRevision(sFecha);
        caratula.setsNumeroSesion(sNoSesion);
        caratula.setsRamo(ramoId);
        caratula.setsSysClave("1");
        
        caratulaBean.setStrServer(request.getHeader("host"));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(caratulaBean.getsConexionBD());
        Caratula carTemp = null;
        carTemp = caratulaBean.cargaCaratula(caratula);
        JSONObject json = new JSONObject();
        if (carTemp != null ) {
            json.put("resultado","1");
            json.put("prog",carTemp.getsNumodProg());
            json.put("pres",carTemp.getsNumModPres());
            json.put("id",carTemp.getsIdCaratula());
        } else {
            json.put("resultado","0");
        }
        strResult = json.toString();
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
