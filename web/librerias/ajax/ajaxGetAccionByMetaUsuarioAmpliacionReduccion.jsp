<%-- 
    Document   : ajaxGetAccionByMetaUsuarioAmpliacionReduccions
    Created on : Feb 2, 2016, 1:53:28 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;
    MovimientosAmpliacionReduccion movimiento = new MovimientosAmpliacionReduccion();
    MovimientosTransferencia movimientoTrans = new MovimientosTransferencia();
    MovimientosReprogramacion reprogramacion = new MovimientosReprogramacion();  
    List<ReprogramacionAccion> reprogramacionAccionList = new ArrayList<ReprogramacionAccion>();
    Transferencia transferencia = new Transferencia();
    List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
    List<Accion> accionList = new ArrayList<Accion>();
    List<Accion> accionAutorizadasList = new ArrayList<Accion>();
    List<AmpliacionReduccionAccion> ampliacionReduccionAccionList = new ArrayList<AmpliacionReduccionAccion>();
    String strResultado = new String();
    String ramo = new String();
    String appLogin = new String();
    String tipoDependencia = new String();
    String prg = new String();
    String proy = new String();
    String tipoProy = new String();
    int meta = 0;
    int year = 0;
    int proyecto = -1;
    int transferenciaId = -1;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }

        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramo = (String) request.getParameter("ramo");
        }

        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            prg = (String) request.getParameter("programa");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            prg = (String) request.getParameter("programa");
        }
        
        if (request.getParameter("meta") != null && !request.getParameter("meta").equals("")) {
            meta = Integer.parseInt((String) request.getParameter("meta"));
        }
        
        if (request.getParameter("transferenciaId") != null && !request.getParameter("transferenciaId").equals("")) {
            transferenciaId = Integer.parseInt((String) request.getParameter("transferenciaId"));
        }

        if (request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")) {
            proy = request.getParameter("proyecto");
            proyecto = Integer.parseInt(proy.split(",")[0]);
            tipoProy = proy.split(",")[1];
        }
        
        if (session.getAttribute("reprogramacion") != null) {
            reprogramacion = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            reprogramacionAccionList = reprogramacion.getMovOficioAccionList();
        }

        if (session.getAttribute("ampliacionReduccion") != null) {
            movimiento = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampliacionReduccionAccionList = movimiento.getAmpReducAccionList();
        }
        if (session.getAttribute("transferencia") != null) {
            movimientoTrans = (MovimientosTransferencia) session.getAttribute("transferencia");
            transAccionList = movimientoTrans.getTransferenciaAccionList();
        }

        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        accionList = recalBean.getAccionByMetaUsuario(year, appLogin, ramo, prg, proyecto, tipoProy,meta);        
        accionAutorizadasList = recalBean.getResultGetAccionMovoficiosAutorizado(year, appLogin, ramo, prg, proyecto, tipoProy, meta);
        for (Accion accionAutorizada : accionAutorizadasList) {
            boolean agregar = true;
            for (Accion accionDef : accionList) {
                if(accionDef.getAccionId() == accionAutorizada.getAccionId()){
                    agregar = false;
                }        
            }
            if(agregar){
                accionList.add(accionAutorizada);
            }
        }    
        
        
        strResultado += "<option value='-1'> -- Seleccione una acci&oacute;n -- </option>";
        for (Accion accion : accionList) {
            strResultado += "<option value='" + accion.getAccionId()+ "'> " + "" + accion.getAccionId() + "-" + accion.getAccion() + "</option>";
        }

        if (ampliacionReduccionAccionList.size() > 0) {
            for (AmpliacionReduccionAccion movAccion : ampliacionReduccionAccionList) {
                if (movAccion.getMovOficioAccion().getAccionId() < 0 && meta == movAccion.getMovOficioAccion().getMetaId()) {
                    strResultado += "<option value='" + movAccion.getMovOficioAccion().getAccionId() + "'> " + "" + movAccion.getMovOficioAccion().getAccionId() + "-" + movAccion.getMovOficioAccion().getAccionDescr()
                            + "</option>";
                }
            }
        }
        
        if (reprogramacionAccionList.size() > 0) {
            for (ReprogramacionAccion movAccion : reprogramacionAccionList) {
                if (movAccion.getMovOficioAccion().getAccionId() < 0 && meta == movAccion.getMovOficioAccion().getMetaId()) {
                    strResultado += "<option value='" + movAccion.getMovOficioAccion().getAccionId() + "'> " + "" + movAccion.getMovOficioAccion().getAccionId() + "-" + movAccion.getMovOficioAccion().getAccionDescr()
                            + "</option>";
                }
            }
        }

        if (transAccionList.size() > 0) {
            for (TransferenciaAccion movAccion : transAccionList) {
                if (movAccion.getMovOficioAccion().getAccionId() < 0 && meta == movAccion.getMovOficioAccion().getMetaId()) {
                    strResultado += "<option value='" + movAccion.getMovOficioAccion().getAccionId() + "'> " + "" + movAccion.getMovOficioAccion().getAccionId() + "-" + movAccion.getMovOficioAccion().getAccionDescr()
                            + "</option>";
                }
            }
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
        if (recalBean != null) {
            recalBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>