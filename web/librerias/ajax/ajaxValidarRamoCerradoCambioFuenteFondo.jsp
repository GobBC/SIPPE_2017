<%-- 
    Document   : ajaxValidarRamoCerradoCambioFuenteFondo
    Created on : Oct 6, 2016, 11:06:28 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.MigracionRequerimientoBean"%>
<%@page import="gob.gbc.aplicacion.CargaCodigoBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    CargaCodigoBean cargaBean = null;
    MigracionRequerimientoBean migracionRequerimientoBean = null;

    String rol = new String();
    String ramoId = new String();
    String strResultado = new String();
    String tipoDependencia = new String();

    int opt = 0;
    int year = 0;
    int presup = 0;

    boolean bandera = false;

    try {

        if ((String) session.getAttribute("strRol") != null) {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = (String) request.getParameter("ramo");
        }
        if (request.getParameter("opt") != null && !request.getParameter("opt").equals("")) {
            opt = Integer.parseInt(request.getParameter("opt"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        cargaBean = new CargaCodigoBean(tipoDependencia);
        cargaBean.setStrServer(request.getHeader("host"));
        cargaBean.setStrUbicacion(getServletContext().getRealPath(""));
        cargaBean.resultSQLConecta(tipoDependencia);

        migracionRequerimientoBean = new MigracionRequerimientoBean(tipoDependencia);
        migracionRequerimientoBean.setStrServer((request.getHeader("Host")));
        migracionRequerimientoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        migracionRequerimientoBean.resultSQLConecta(tipoDependencia);

        if (!rol.equals(migracionRequerimientoBean.getRolNormativo())) {

            if (rol != cargaBean.getResultSQLGetRolesPrg()) {
                bandera = cargaBean.getResultSQLValidaRamo(ramoId, year);
            } else {
                bandera = false;
            }

            if (!bandera) {
                strResultado = "A";
            } else {
                strResultado = "C";
            }

        } else {
            strResultado = "A";
        }

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (cargaBean != null) {
            cargaBean.resultSQLDesconecta();
        }
        if (migracionRequerimientoBean != null) {
            migracionRequerimientoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
