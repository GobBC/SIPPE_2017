<%-- 
    Document   : ajaxCambiaContrasenaAct
    Created on : Feb 22, 2016, 4:00:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    Vector vecResultado = null;

    String strTipoDependencia = new String();
    String strContrasenaAct = new String();
    String strContrasenaNva = new String();
    String strNoSesion = new String();
    String strUsuario = new String();
    String strRamoId = new String();
    String strResult = new String();
    String strFecha = new String();
    int iResultado = 0;
    int iProyId = 0;
    int iYear = 0;
    boolean bContrasenaActCorrecta = false;
    boolean bContrasenaCambiada = false;
    boolean bResultado = false;


    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (request.getParameter("tipoDependencia") != null) {
            strTipoDependencia = (String) request.getParameter("tipoDependencia");
            session.setAttribute("tipoDependencia", strTipoDependencia);
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            strTipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("strContrasenaAct") != null && !request.getParameter("strContrasenaAct").equals("")) {
            strContrasenaAct = (String) request.getParameter("strContrasenaAct");
        }
        if (request.getParameter("strContrasenaNva") != null && !request.getParameter("strContrasenaNva").equals("")) {
            strContrasenaNva = (String) request.getParameter("strContrasenaNva");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            strUsuario = (String) session.getAttribute("strUsuario");
        }

        ResultSQL resultSQL = new ResultSQL(strTipoDependencia);
        resultSQL.setStrServer(((String) request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(strTipoDependencia);

        bContrasenaActCorrecta = resultSQL.getResultSQLValidarContrasenaAct(strUsuario, strContrasenaAct);

        if (bContrasenaActCorrecta) {
            bContrasenaCambiada = resultSQL.getResultSQLCambiarContrasena(strUsuario, strContrasenaAct, strContrasenaNva);
            if (bContrasenaCambiada) {
                resultSQL.transaccionCommit();
                strResult = "1";
            } else {
                resultSQL.transaccionRollback();
                strResult = "-3";
            }
        } else {
            strResult = "-2";
        }

        resultSQL.resultSQLDesconecta();

    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {

        out.print(strResult);
    }
%>
