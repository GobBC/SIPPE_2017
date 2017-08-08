<%-- 
    Document   : ajaxActualizaAsignacionTodosMovimientos
    Created on : jun 12, 2017, 9:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.MovimientoFolio"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.MovimientoFolioBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    MovimientoFolioBean movimientoFolioBean = null;
    List<MovimientoFolio> movimientoFolioList = new ArrayList<MovimientoFolio>();
    String asignado = new String();
    String strResult = new String();
    String tipoDependencia = new String();
    long selJustificaciones = 0;
    int selFolios = 0;
    boolean resultado = false;

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
        if (request.getParameter("asignado") != null && !request.getParameter("asignado").equals("")) {
            asignado = request.getParameter("asignado");
        }

        movimientoFolioBean = new MovimientoFolioBean(tipoDependencia);
        movimientoFolioBean.setStrServer(request.getHeader("host"));
        movimientoFolioBean.setStrUbicacion(getServletContext().getRealPath(""));
        movimientoFolioBean.resultSQLConecta(tipoDependencia);

        if (selJustificaciones != -1) {

            movimientoFolioList = movimientoFolioBean.getMovimientosByFolioJustificacion(selFolios, selJustificaciones);

            for (MovimientoFolio movimientoFolio : movimientoFolioList) {

                resultado = movimientoFolioBean.getActualizaAsignacionMovimientos(movimientoFolio.getTabla(), asignado, selJustificaciones, selFolios, movimientoFolio.getRamo(), movimientoFolio.getPrg(), movimientoFolio.getProyecto(), movimientoFolio.getTipoProy(), movimientoFolio.getMeta(), movimientoFolio.getAccion(), movimientoFolio.getDepto(), movimientoFolio.getPartida(), movimientoFolio.getFuente(), movimientoFolio.getFondo(), movimientoFolio.getRecurso(), movimientoFolio.getDiferenciador());

                if (resultado) {
                    strResult = "1";
                    movimientoFolioBean.transaccionCommit();
                } else {
                    strResult = "-1";
                    movimientoFolioBean.transaccionRollback();
                }

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
        if (movimientoFolioBean != null) {
            movimientoFolioBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
