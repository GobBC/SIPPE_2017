<%-- 
    Document   : ajaxGetAccionByMetaUsuario
    Created on : Dec 7, 2015, 3:29:21 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;
    MovimientosReprogramacion reprogramacion = new MovimientosReprogramacion();
    MovimientosAmpliacionReduccion movimiento = new MovimientosAmpliacionReduccion();
    List<ReprogramacionAccion> reprogramacionAccionList = new ArrayList<ReprogramacionAccion>();
    List<AmpliacionReduccionAccion> ampliacionReduccionAccionList = new ArrayList<AmpliacionReduccionAccion>();
    List<Accion> accionList = new ArrayList<Accion>();
    List<Accion> accionAutorizadasList = new ArrayList<Accion>();
    String strResultado = new String();
    String ramo = new String();
    String appLogin = new String();
    String tipoDependencia = new String();
    String prg = new String();
    String proy = new String();
    String tipoProy = new String();
    int year = 0;
    int proyecto = -1;
    int meta = -1;

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
            ramo = request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            prg = request.getParameter("programa");
        }
        if (request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")) {
            proy = request.getParameter("proyecto");
            proyecto = Integer.parseInt(proy.split(",")[0]);
            tipoProy = proy.split(",")[1];
        }
        if (request.getParameter("meta") != null && !request.getParameter("meta").equals("")) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        if (session.getAttribute("reprogramacion") != null) {
            reprogramacion = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            reprogramacionAccionList = reprogramacion.getMovOficioAccionList();
        }
        if (session.getAttribute("ampliacionReduccion") != null) {
            movimiento = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampliacionReduccionAccionList = movimiento.getAmpReducAccionList();
        }
        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        accionList = recalBean.getAccionByMetaUsuario(year, appLogin, ramo, prg, proyecto, tipoProy, meta);
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
        if (accionList.size() > 0 || ampliacionReduccionAccionList.size() > 0 || reprogramacionAccionList.size() > 0) {
            strResultado += "<option value='-1'> -- Seleccione una acci&oacute;n -- </option>";
            for (Accion accion : accionList) {
                strResultado += "<option value='" + accion.getAccionId() + "'> "
                        + "" + accion.getAccionId() + "-" + accion.getAccion() + "</option>";
            }
            for (ReprogramacionAccion ampRedAccionList : reprogramacionAccionList) {
                if (ampRedAccionList.getMovOficioAccion().getAccionId() <= 0
                        && ampRedAccionList.getMovOficioAccion().getMetaId() == meta) {
                    strResultado += "<option value='" + ampRedAccionList.getMovOficioAccion().getAccionId() + "'> "
                            + "" + ampRedAccionList.getMovOficioAccion().getAccionId() + "-"
                            + ampRedAccionList.getMovOficioAccion().getAccionDescr() + "</option>";
                }
            }
            for (AmpliacionReduccionAccion ampRedAccionList : ampliacionReduccionAccionList) {
                if (ampRedAccionList.getMovOficioAccion().getAccionId() <= 0
                        && ampRedAccionList.getMovOficioAccion().getMetaId() == meta) {
                    strResultado += "<option value='" + ampRedAccionList.getMovOficioAccion().getAccionId() + "'> "
                            + "" + ampRedAccionList.getMovOficioAccion().getAccionId() + "-"
                            + ampRedAccionList.getMovOficioAccion().getAccionDescr() + "</option>";
                }
            }
        } else {
            strResultado += "0|Esta meta no tiene acciones asignadas";
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