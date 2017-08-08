<%-- 
    Document   : ajaxMovimientosJustificacionesValMovsAsig
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
    String strResult = "1";
    String strNumsMovs = "";
    String tipoDependencia = new String();
    long selJustificaciones = 0;
    int selFolios = 0;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("selFolios") != null && !request.getParameter("selFolios").equals("")) {
            selFolios = Integer.parseInt(request.getParameter("selFolios"));
        }

        movimientoFolioBean = new MovimientoFolioBean(tipoDependencia);
        movimientoFolioBean.setStrServer(request.getHeader("host"));
        movimientoFolioBean.setStrUbicacion(getServletContext().getRealPath(""));
        movimientoFolioBean.resultSQLConecta(tipoDependencia);

        if (movimientoFolioBean.isValidaJustificacionesMovsActivo()) {

            movimientoFolioList = movimientoFolioBean.getMovimientosByFolio(selFolios);

            if (selFolios != 0) {

                for (MovimientoFolio movimientoFolio : movimientoFolioList) {

                    if (movimientoFolio.getId_justificacion() == 0) {

                        if (strNumsMovs.equals("")) {
                            strNumsMovs += movimientoFolio.getMovimiento();
                        } else {
                            strNumsMovs = strNumsMovs + ", " + movimientoFolio.getMovimiento();
                        }

                    }

                }

                if (!strNumsMovs.equals("")) {
                    strResult = "No es posible enviar el Oficio " + selFolios + " para Autorizaci&oacute;n, existen movimientos sin captura de Justificaci&oacute;n.";
                }

            } else {
                strResult = "Se debe de generar un folio para el oficio primero.";
            }

        } else {
            strResult = "1";
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
