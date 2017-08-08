<%-- 
    Document   : ajaxGetMovimientosByFolioJustificacion
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

        movimientoFolioBean = new MovimientoFolioBean(tipoDependencia);
        movimientoFolioBean.setStrServer(request.getHeader("host"));
        movimientoFolioBean.setStrUbicacion(getServletContext().getRealPath(""));
        movimientoFolioBean.resultSQLConecta(tipoDependencia);

        if (selJustificaciones != -1) {

            movimientoFolioList = movimientoFolioBean.getMovimientosByFolioJustificacion(selFolios, selJustificaciones);

            for (MovimientoFolio movimientoFolio : movimientoFolioList) {

                strResult += "<tr>";

                if (movimientoFolio.getMovimiento() != 0) {
                    strResult += "  <td>" + movimientoFolio.getMovimiento() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                }
                if (movimientoFolio.getRamo() != null) {
                    strResult += "  <td>" + movimientoFolio.getRamo() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                }
                if (movimientoFolio.getPrg() != null) {
                    strResult += "  <td>" + movimientoFolio.getPrg() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                }
                if (movimientoFolio.getProyecto_abr() != null) {
                    strResult += "  <td>" + movimientoFolio.getProyecto_abr() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                    movimientoFolio.setTipoProy("");
                }
                if (movimientoFolio.getMeta() != 0) {
                    strResult += "  <td>" + movimientoFolio.getMeta() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                }
                if (movimientoFolio.getAccion() != 0) {
                    strResult += "  <td>" + movimientoFolio.getAccion() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                }
                if (movimientoFolio.getDepto() != null) {
                    strResult += "  <td>" + movimientoFolio.getDepto() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                    movimientoFolio.setDepto("");
                }
                if (movimientoFolio.getPartida() != null) {
                    strResult += "  <td>" + movimientoFolio.getPartida() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                    movimientoFolio.setPartida("");
                }
                if (movimientoFolio.getFfr_abr() != null) {
                    strResult += "  <td>" + movimientoFolio.getFfr_abr() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                }
                if (movimientoFolio.getImporte().toString() != null) {
                    strResult += "  <td>" + movimientoFolio.getImporte() + "</td>";
                } else {
                    strResult += "  <td>" + " - " + "</td>";
                }

                if (movimientoFolio.getId_justificacion() == selJustificaciones) {
                    strResult += "<td><input type='checkbox' id='chkAsignado" + movimientoFolio.getMovimiento() + "' onclick='capturaJustificacionesActAsignacionMovimiento(" + movimientoFolio.getTabla() + "," + movimientoFolio.getMovimiento() + "," + movimientoFolio.getFolio() + ",\"" + movimientoFolio.getRamo() + "\",\"" + movimientoFolio.getPrg() + "\"," + movimientoFolio.getProyecto() + ",\"" + movimientoFolio.getTipoProy() + "\"," + movimientoFolio.getMeta() + "," + movimientoFolio.getAccion() + ",\"" + movimientoFolio.getDepto() + "\",\"" + movimientoFolio.getPartida() + "\",\"" + movimientoFolio.getFuente() + "\",\"" + movimientoFolio.getFondo() + "\",\"" + movimientoFolio.getRecurso() + "\"," + movimientoFolio.getDiferenciador() + "," + movimientoFolio.getId_justificacion() + ")' checked/></td>";
                } else {
                    if (movimientoFolio.getId_justificacion() == 0) {
                        strResult += "<td><input type='checkbox' id='chkAsignado" + movimientoFolio.getMovimiento() + "' onclick='capturaJustificacionesActAsignacionMovimiento(" + movimientoFolio.getTabla() + "," + movimientoFolio.getMovimiento() + "," + movimientoFolio.getFolio() + ",\"" + movimientoFolio.getRamo() + "\",\"" + movimientoFolio.getPrg() + "\"," + movimientoFolio.getProyecto() + ",\"" + movimientoFolio.getTipoProy() + "\"," + movimientoFolio.getMeta() + "," + movimientoFolio.getAccion() + ",\"" + movimientoFolio.getDepto() + "\",\"" + movimientoFolio.getPartida() + "\",\"" + movimientoFolio.getFuente() + "\",\"" + movimientoFolio.getFondo() + "\",\"" + movimientoFolio.getRecurso() + "\"," + movimientoFolio.getDiferenciador() + "," + movimientoFolio.getId_justificacion() + ")' /></td>";
                    } else {
                        strResult += "<td><input type='button' class='btnbootstrap-drop btn-infoGlobe' id='tlpInfoAsignacion' title='Movimiento asignado a la Justificación No. "+movimientoFolio.getId_justificacion()+"'></td>";
                    }
                }
                strResult += "</tr>";

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
