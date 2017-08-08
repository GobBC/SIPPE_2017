<%-- 
    Document   : ajaxGetCostoArticulo
    Created on : Jun 29, 2015, 11:05:01 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String partida = new String();
    double costo = 0.0;
    int year = 0;
    String tipoDependencia = new String();
    AccionBean accionBean = null;
    String articuloPart = new String();
    String arrValores[];
    int articulo = 0;
    int gpogto = 0;
    int subgpo = 0;

    try {
        
        response.setCharacterEncoding("UTF-8");
        
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("partida") != null && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if (request.getParameter("articulo") != null && !request.getParameter("articulo").equals("")) {
            articuloPart = request.getParameter("articulo");
            if (articuloPart.contains(".")) {
                arrValores = new String[articuloPart.split("\\.").length];
                arrValores = articuloPart.split("\\.");
                articulo = Integer.parseInt((arrValores[2]));
                gpogto = Integer.parseInt((arrValores[1]));
                subgpo = Integer.parseInt((arrValores[0]));
            } else {
                articulo = Integer.parseInt(articuloPart);
            }

        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        costo = accionBean.getCostoArticulo(articulo, partida, year);
        strResultado = "" + costo + "";
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>