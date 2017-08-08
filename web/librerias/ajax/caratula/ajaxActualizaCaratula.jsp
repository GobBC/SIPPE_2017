<%-- 
    Document   : ajaxActualizaCaratula
    Created on : Apr 1, 2015, 4:32:00 PM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.RevisionCaratula"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.RevisionCaratulaBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    Caratula carTemp = null;
    CaratulaBean caratulaBean = null;
    RevisionCaratulaBean revisionCaratulaBean = null;
    List<RevisionCaratula> revisionesCaratulaList = null;
    String ramo = new String();
    String fecha = new String();
    String estatus = "A";
    String strResult = new String();
    String selModProg = new String();
    String selModPresup = new String();
    String selTipoSesion = new String();
    String selNumeroSesion = new String();
    String tipoDependencia = new String();
    String selTipoModificacion = new String();
    long selCaratula = -1;
    int year = 0;
    int caratulaYearSesion = 0;
    int caratulaTipoSesion = 0;
    int caratulaTipoModificacion = 0;
    boolean continuar = true;

    try {

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramo = request.getParameter("ramoId");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }
        if (request.getParameter("fecha") != null && !request.getParameter("fecha").equals("")) {
            fecha = (String) request.getParameter("fecha");
        }
        if (request.getParameter("selNumeroSesion") != null && !request.getParameter("selNumeroSesion").equals("")) {
            selNumeroSesion = (String) request.getParameter("selNumeroSesion");
        }
        if (request.getParameter("selTipoSesion") != null && !request.getParameter("selTipoSesion").equals("")) {
            selTipoSesion = (String) request.getParameter("selTipoSesion");
        }
        if (request.getParameter("selTipoModificacion") != null && !request.getParameter("selTipoModificacion").equals("")) {
            selTipoModificacion = (String) request.getParameter("selTipoModificacion");
        }
        if (request.getParameter("selModPresup") != null && !request.getParameter("selModPresup").equals("")) {
            selModPresup = (String) request.getParameter("selModPresup");
        }
        if (request.getParameter("selModProg") != null && !request.getParameter("selModProg").equals("")) {
            selModProg = (String) request.getParameter("selModProg");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = (String) request.getParameter("estatus");
        }

        Caratula caratula = new Caratula();
        caratula.setiYear(year);
        caratula.setsRamo(ramo);
        caratula.setsIdCaratula(selCaratula);
        caratula.setsFechaRevision(fecha);
        caratula.setsNumeroSesion(selNumeroSesion);
        caratula.setiTipoSesion(Integer.parseInt(selTipoSesion));
        caratula.setIntTipoModificacion(Integer.parseInt(selTipoModificacion));
        caratula.setsNumModPres(selModPresup);
        caratula.setsNumodProg(selModProg);
        caratula.setsSysClave("1");
        caratula.setsAppLogin((String) session.getAttribute("strUsuario"));
        caratula.setiStatus(estatus);

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        revisionCaratulaBean = new RevisionCaratulaBean(tipoDependencia);
        revisionCaratulaBean.setStrServer((request.getHeader("Host")));
        revisionCaratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        revisionCaratulaBean.resultSQLConecta(tipoDependencia);

        if (selCaratula != -1) {
            carTemp = caratulaBean.getResultSQLCaratulaByYearIdRamoIdCaratula(String.valueOf(year), ramo, selCaratula);
        }

        if (!fecha.equals("")) {
            caratulaYearSesion = Integer.parseInt(fecha.split("/")[2]);
        }

        caratulaTipoModificacion = Integer.parseInt(selTipoModificacion);
        caratulaTipoSesion = Integer.parseInt(selTipoSesion);

        revisionesCaratulaList = revisionCaratulaBean.getListRevisionesCaratulaByRamoCaratulaYear(ramo, selCaratula, year, caratulaYearSesion, caratulaTipoModificacion, caratulaTipoSesion);

        for (RevisionCaratula revisionCaratula : revisionesCaratulaList) {
            if (revisionCaratula.getRevision().equalsIgnoreCase(selNumeroSesion)) {
                if (!revisionCaratula.isLibre_Num_Session()) {
                    continuar = false;
                    strResult = "El n&uacute;mero de sesi&oacute;n ya no se encuentra disponible";
                }
            }
        }

        if (year > caratulaYearSesion) {
            continuar = false;
            strResult = "El a&ntilde;o de la sesi&oacute;n no puede ser menor al a&ntilde;o presupuestal";
        }

        if (continuar) {
            if (caratulaBean.actualizaCaratula(caratula)) {
                strResult = "1";
            }
        }

    } catch (Exception ex) {
        strResult = "No es posible actualizar la informacion";
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
        if (revisionCaratulaBean != null) {
            revisionCaratulaBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
