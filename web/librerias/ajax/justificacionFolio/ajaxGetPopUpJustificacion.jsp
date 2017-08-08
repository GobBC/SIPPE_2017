<%-- 
    Document   : ajaxGetPopUpJustificacion
    Created on : jun 12, 2017, 9:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Justificacion"%>
<%@page import="gob.gbc.aplicacion.JustificacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    JustificacionBean justificacionBean = null;
    Justificacion justificacion = null;
    String strResult = new String();
    String tipoDependencia = new String();
    long selJustificaciones = 0;
    int selFolios = 0;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("selJustificaciones") != null && !request.getParameter("selJustificaciones").equals("")) {
            selJustificaciones = Long.parseLong(request.getParameter("selJustificaciones"));
        }
        if (request.getParameter("selFolios") != null && !request.getParameter("selFolios").equals("")) {
            selFolios = Integer.parseInt(request.getParameter("selFolios"));
        }

        justificacionBean = new JustificacionBean(tipoDependencia);
        justificacionBean.setStrServer(request.getHeader("host"));
        justificacionBean.setStrUbicacion(getServletContext().getRealPath(""));
        justificacionBean.resultSQLConecta(tipoDependencia);

        if (selJustificaciones != -1) {

            justificacion = justificacionBean.getJustificacionByFolioJustificacion(selFolios, selJustificaciones);

            if (justificacion != null) {
                strResult = "<textarea  maxlength='350' type='text' class='no-enter' name='justificacionAuto' style='max-width: 100%; width: 100%' id='justificacionAuto' class='textarea-comentario text col-md-12' >"
                        + justificacion.getJustificacion()
                        + "</textarea>"
                        + "<input type='hidden' id='idJustificacion' name='idJustificacion' value='" + justificacion.getId_justificacion() + "' />";
            } else {
                strResult = "-1";
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
        if (justificacionBean != null) {
            justificacionBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
