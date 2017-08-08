<%-- 
    Document   : ajaxGetMetaByProyectoUsuarioAmpliacionReduccion
    Created on : Feb 2, 2016, 1:53:28 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;
    MovimientosReprogramacion reprogramacion = new MovimientosReprogramacion();
    MovimientosAmpliacionReduccion movimiento = new MovimientosAmpliacionReduccion();
    MovimientosTransferencia movimientoTrans = new MovimientosTransferencia();
    Transferencia transferencia = new Transferencia();
    List<Meta> metaList = new ArrayList<Meta>();
    List<Meta> metaAutorizadasList = new ArrayList<Meta>();
    List<ReprogramacionMeta> reprogramacionMetaList = new ArrayList<ReprogramacionMeta>();
    List<AmpliacionReduccionMeta> ampliacionReduccionMetaList = new ArrayList<AmpliacionReduccionMeta>();
    List<TransferenciaMeta> transMetaMetaList = new ArrayList<TransferenciaMeta>();
    String strResultado = new String();
    String ramo = new String();
    String appLogin = new String();
    String tipoDependencia = new String();
    String prg = new String();
    String proy = new String();
    String tipoProy = new String();
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

        if (request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")) {
            proy = request.getParameter("proyecto");
            proyecto = Integer.parseInt(proy.split(",")[0]);
            tipoProy = proy.split(",")[1];
        }

        if (request.getParameter("transferenciaId") != null && !request.getParameter("transferenciaId").equals("")) {
            transferenciaId = Integer.parseInt(request.getParameter("transferenciaId"));
        }

        if (session.getAttribute("reprogramacion") != null) {
            reprogramacion = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            reprogramacionMetaList = reprogramacion.getMovOficioMetaList();
        }

        if (session.getAttribute("ampliacionReduccion") != null) {
            movimiento = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampliacionReduccionMetaList = movimiento.getAmpReducMetaList();
        }

        if (session.getAttribute("transferencia") != null) {
            movimientoTrans = (MovimientosTransferencia) session.getAttribute("transferencia");
            transMetaMetaList = movimientoTrans.getTransferenciaMetaList();
        }


        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        metaList = recalBean.getMetaByProyectoUsuario(year, appLogin, ramo, prg, proyecto, tipoProy);
        metaAutorizadasList = recalBean.getResultGetMetaMovoficiosAutorizado(year, appLogin, ramo, prg, proyecto, tipoProy);
        
        for (Meta metaAutorizada : metaAutorizadasList) {
            boolean agregar = true;
            for (Meta metaDef : metaList) {
                if(metaDef.getMetaId() == metaAutorizada.getMetaId()){
                    agregar = false;
                }        
            }
            if(agregar){
                metaList.add(metaAutorizada);
            }
        }        

        if (metaList.size() > 0 || transMetaMetaList.size() > 0 || ampliacionReduccionMetaList.size() > 0 || reprogramacionMetaList.size() > 0) {
            strResultado += "<option value='-1'> -- Seleccione una meta -- </option>";
            for (Meta meta : metaList) {
                strResultado += "<option value='" + meta.getMetaId() + "'> " + "" + meta.getMetaId() + "-" + meta.getMeta() + "</option>";
            }

            if (reprogramacionMetaList.size() > 0) {
                for (ReprogramacionMeta movMeta : reprogramacionMetaList) {
                    if (movMeta.getMetaInfo().getMetaId() < 0 && proyecto == movMeta.getMetaInfo().getProyId()
                            && tipoProy.equals(movMeta.getMetaInfo().getTipoProy())) {
                        strResultado += "<option value='" + movMeta.getMetaInfo().getMetaId() + "'> " + "" + movMeta.getMetaInfo().getMetaId() + "-" + movMeta.getMetaInfo().getMetaDescr() + "</option>";
                    }
                }
            }

            if (ampliacionReduccionMetaList.size() > 0) {
                for (AmpliacionReduccionMeta movMeta : ampliacionReduccionMetaList) {
                    if (movMeta.getMovOficioMeta().getMetaId() < 0 && proyecto == movMeta.getMovOficioMeta().getProyId()
                            && tipoProy.equals(movMeta.getMovOficioMeta().getTipoProy())) {
                        strResultado += "<option value='" + movMeta.getMovOficioMeta().getMetaId() + "'> " + "" + movMeta.getMovOficioMeta().getMetaId() + "-" + movMeta.getMovOficioMeta().getMetaDescr() + "</option>";
                    }
                }
            }

            if (transMetaMetaList.size() > 0) {
                for (TransferenciaMeta movMeta : transMetaMetaList) {
                    if (movMeta.getMovOficioMeta().getMetaId() < 0 && proyecto == movMeta.getMovOficioMeta().getProyId()
                            && tipoProy.equals(movMeta.getMovOficioMeta().getTipoProy())) {
                        strResultado += "<option value='" + movMeta.getMovOficioMeta().getMetaId() + "'> " + "" + movMeta.getMovOficioMeta().getMetaId() + "-" + movMeta.getMovOficioMeta().getMetaDescr() + "</option>";
                    }
                }
            }

        } else {
            strResultado += "0|Este proyecto/actividad no tiene metas asignadas";
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