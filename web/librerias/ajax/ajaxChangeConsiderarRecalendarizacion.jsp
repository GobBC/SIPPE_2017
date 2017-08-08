<%-- 
    Document   : ajaxChangeConsiderarRecalendarizacion
    Created on : Dec 17, 2015, 4:01:07 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Locale"%>
<%@page import="gob.gbc.entidades.MovOficiosAccionReq"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%


    MovOficiosAccionReq movAccReq = null;
    RecalendarizacionBean recalBean = null;
    MovimientosRecalendarizacion recalendarizacion;
    Requerimiento requerimiento = new Requerimiento();

    List<RecalendarizacionAccionReq> recalAccionReq = new ArrayList<RecalendarizacionAccionReq>();

    String considerar = new String();
    String strResultado = new String();
    String tipoDependencia = new String();

    int folio = 0;
    int identificador = -1;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt(request.getParameter("identificador"));
        }
        if (request.getParameter("folio") != null && !request.getParameter("folio").equals("")) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (request.getParameter("considerar") != null && !request.getParameter("considerar").equals("")) {
            considerar = request.getParameter("considerar");
        }

        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);


        if (session.getAttribute("recalendarizacion") != null && session.getAttribute("recalendarizacion") != "") {
            recalendarizacion = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
            recalAccionReq = recalendarizacion.getMovOficiosAccionReq();
            if (identificador != -1) {
                for (RecalendarizacionAccionReq movtoAccionReq : recalAccionReq) {
                    if (movtoAccionReq.getIdentificador() == identificador) {
                        movAccReq = movtoAccionReq.getMovAccionReq();
                        if (recalBean.updateMovimientoOficioRecalAccionReqConsiderar(movAccReq.getYear(), folio, movAccReq.getRamo(), movAccReq.getPrograma(), movAccReq.getDepto(), movAccReq.getMeta(), movAccReq.getAccion(), movAccReq.getRequerimiento(), considerar)) {
                            strResultado = "Se logro modificar el campo considerar del requerimiento";
                            recalBean.transaccionCommit();
                            movAccReq.setConsiderar(considerar);
                            movtoAccionReq.setMovAccionReq(movAccReq);
                            recalendarizacion.setMovOficiosAccionReq(recalAccionReq);
                            session.setAttribute("recalendarizacion", recalendarizacion);
                        } else {
                            strResultado = "No se logro modificar el campo considerar del requerimiento";
                            recalBean.transaccionRollback();
                        }
                    }
                }
            }
        }

    } catch (Exception ex) {
        strResultado = "No se logro modificar el campo considerar del requerimiento";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (recalBean != null) {
            recalBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

