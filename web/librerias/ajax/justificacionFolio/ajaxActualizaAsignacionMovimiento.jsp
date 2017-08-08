<%-- 
    Document   : ajaxActualizaAsignacionMovimiento
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
    String ramo = new String();
    String depto = new String();
    String fondo = new String();
    String fuente = new String();
    String recurso = new String();
    String partida = new String();
    String programa = new String();
    String tipoProy = new String();
    String asignado = new String();
    String strResult = new String();
    String tipoDependencia = new String();
    long selJustificaciones = 0;
    int meta = 0;
    int tabla = 0;
    int accion = 0;
    int proyecto = 0;
    int folio = 0;
    int movimiento = 0;
    int diferenciador = 0;
    boolean resultado = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("selJustificaciones") != null && !request.getParameter("selJustificaciones").equals("")) {
            selJustificaciones = Long.parseLong(request.getParameter("selJustificaciones"));
        }
        if (request.getParameter("asignado") != null && !request.getParameter("asignado").equals("")) {
            asignado = request.getParameter("asignado");
        }
        if (request.getParameter("tabla") != null && !request.getParameter("tabla").equals("")) {
            tabla = Integer.parseInt(request.getParameter("tabla"));
        }
        if (request.getParameter("folio") != null && !request.getParameter("folio").equals("")) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramo = request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            programa = request.getParameter("programa");
        }
        if (request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")) {
            proyecto = Integer.parseInt(request.getParameter("proyecto"));
        }
        if (request.getParameter("tipoProy") != null && !request.getParameter("tipoProy").equals("")) {
            tipoProy = request.getParameter("tipoProy");
        }
        if (request.getParameter("meta") != null && !request.getParameter("meta").equals("")) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        if (request.getParameter("accion") != null && !request.getParameter("accion").equals("")) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        if (request.getParameter("depto") != null && !request.getParameter("depto").equals("")) {
            depto = request.getParameter("depto");
        }
        if (request.getParameter("partida") != null && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if (request.getParameter("fuente") != null && !request.getParameter("fuente").equals("")) {
            fuente = request.getParameter("fuente");
        }
        if (request.getParameter("fondo") != null && !request.getParameter("fondo").equals("")) {
            fondo = request.getParameter("fondo");
        }
        if (request.getParameter("recurso") != null && !request.getParameter("recurso").equals("")) {
            recurso = request.getParameter("recurso");
        }
        if (request.getParameter("diferenciador") != null && !request.getParameter("diferenciador").equals("")) {
            diferenciador = Integer.parseInt(request.getParameter("diferenciador"));
        }

        movimientoFolioBean = new MovimientoFolioBean(tipoDependencia);
        movimientoFolioBean.setStrServer(request.getHeader("host"));
        movimientoFolioBean.setStrUbicacion(getServletContext().getRealPath(""));
        movimientoFolioBean.resultSQLConecta(tipoDependencia);

        resultado = movimientoFolioBean.getActualizaAsignacionMovimientos(tabla, asignado, selJustificaciones, folio, ramo, programa, proyecto, tipoProy, meta, accion, depto, partida, fuente, fondo, recurso, diferenciador);

        if (resultado) {
            strResult = "1";
            movimientoFolioBean.transaccionCommit();
        } else {
            strResult = "-1";
            movimientoFolioBean.transaccionRollback();
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
