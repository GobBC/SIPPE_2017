<%-- 
    Document   : ajaxSaveMovtoTransferencia
    Created on : Jan 12, 2016, 10:59:49 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.TipoOficio"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String ramoSession = new String();
    long selCaratula = -1;
    TransferenciaBean transfBean = null;
    MovimientosTransferencia movimiento = new MovimientosTransferencia();
    List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
    List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
    TipoOficio objOficio = new TipoOficio();
    Map<String, List<Transferencia>> map = new HashMap<String, List<Transferencia>>();
    BigDecimal acumulado = new BigDecimal(0.0);
    BigDecimal disponible = new BigDecimal(0.0);
    BigDecimal acumuladoMovtos = new BigDecimal(0.0);
    BigDecimal acRamoPartida = new BigDecimal(0.0);
    String tipoDependencia = new String();
    String strResultado = new String();
    String autorizacion = new String();
    String appLogin = new String();
    String justificacion = new String();
    String estatus = new String();
    String tipoMov = new String();
    String tipoOficio = new String();
    String tipoOficioDescr = new String();
    String capturaEspecial = new String();
    String mensaje = new String();
    String date = new String();
    String llave = new String();
    String ramo = new String();
    String partida = new String();
    String comentarioPlan = new String();
    int year = 0;
    boolean isActual = true;
    boolean checkImpacto = false;
    boolean fechaConta = false;
    boolean bandera = true;
    boolean banderaTipo = false;
    boolean isRechazado = false;
    try {
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (Utilerias.existeParametro("comentarioPlan", request)) {
            comentarioPlan = (String) request.getParameter("comentarioPlan");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }
        if (request.getParameter("autorizacion") != null && !request.getParameter("autorizacion").equals("")) {
            autorizacion = request.getParameter("autorizacion");
        }
        if (request.getParameter("justificacion") != null && !request.getParameter("justificacion").equals("")) {
            justificacion = request.getParameter("justificacion");
            justificacion = justificacion.replaceAll("'","''");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = request.getParameter("estatus");
        }
        if (request.getParameter("tipoMov") != null && !request.getParameter("tipoMov").equals("")) {
            tipoMov = request.getParameter("tipoMov");
        }
        if (Utilerias.existeParametro("checkImpacto", request)) {
            checkImpacto = Boolean.valueOf(request.getParameter("checkImpacto"));
        }
        if (Utilerias.existeParametro("isActual", request)) {
            isActual = Boolean.parseBoolean(request.getParameter("isActual"));
        }
        if (Utilerias.existeParametro("dateFor", request)) {
            date = request.getParameter("dateFor");
        }
        if (Utilerias.existeParametro("fechaContable", request)) {
            fechaConta = Boolean.parseBoolean(request.getParameter("fechaContable"));
        }
        if (Utilerias.existeParametro("tipoOficio", request)) {
            tipoOficio = request.getParameter("tipoOficio");
        }
        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");
            movimiento.setAppLogin(appLogin);
            movimiento.setJutificacion(justificacion);
            //if(!comentarioPlan.isEmpty()){
            movimiento.setComentarioPlaneacion(comentarioPlan);
            //}
            if (movimiento.getStatus() == null || movimiento.getStatus().isEmpty()) {
                movimiento.setStatus(estatus);
            }
            movimiento.setTipoMovimiento(tipoMov);
        }
        transfBean = new TransferenciaBean(tipoDependencia);
        transfBean.setStrServer(request.getHeader("host"));
        transfBean.setStrUbicacion(getServletContext().getRealPath(""));
        transfBean.resultSQLConecta(tipoDependencia);
        if (fechaConta) {
            capturaEspecial = "S";
        } else {
            capturaEspecial = "N";
        }
        if (tipoOficio.equals("A")) {
            tipoOficioDescr = "Solicitud";
        } else if (tipoOficio.equals("V")) {
            tipoOficioDescr = "Aviso";
        } else if (tipoOficio.equals("U")) {
            tipoOficioDescr = "Automático";
        }
        for (Transferencia transferencia : movimiento.getTransferenciaList()) {
            if(estatus.equals("R"))
                if(estatus.equals("R")){
                    acRamoPartida = transfBean
                            .getResultSQLgetAcumluladoMovtosRechazado(year,
                                    transferencia.getPartida(),
                                    transferencia.getRamo(),
                                    movimiento.getOficio());
                }
            transferencia.setTipoOficio(transfBean.getTipoOficio(transferencia
                    .getTransferenciaAccionReqList(),
                        transferencia.getRamo(),
                        transferencia.getPartida(),
                        year,
                        transferencia.getQuincePor(),
                        movimiento.getOficio(),
                        estatus));
            if (transferencia.getTipoOficio().equals("V")) {
                llave = transferencia.getRamo() + "|" + transferencia.getPartida();
                if (map.get(llave) == null) {
                    map.put(llave, new ArrayList<Transferencia>());
                }
                map.get(llave).add(transferencia);
            }
        }
        //rharo - 04/08/2016 - Revisar con Ulisses si se elimina este segmento de código
        for (String key : map.keySet()) {
            acumulado = new BigDecimal(0.0);
            ramo = key.split("|")[0];
            partida = key.split("|")[1];
            transferenciaList = map.get(key);
            acumuladoMovtos = new BigDecimal(transfBean.getResultSQLgetAcumluladoMovtos(year, partida, ramo) * .15);
            for (Transferencia transf : transferenciaList) {
                BigDecimal bdQuincePor = new BigDecimal(transf.getQuincePor());
                disponible = bdQuincePor.subtract(acumuladoMovtos);
                acumulado = acumulado.add(new BigDecimal(transf.getImporte()));
                if ((disponible.subtract(acumulado).doubleValue()) <= 0) {
                    transf.setTipoOficio("A");
                }
            }
            map.put(key, transferenciaList);
        }
        for (Transferencia transferencia : movimiento.getTransferenciaList()) {
            transferenciaList = new ArrayList<Transferencia>();
            transferenciaList = map.get(transferencia.getRamo() + "|" + transferencia.getPartida());
            if (transferenciaList != null) {
                for (Transferencia transTemp : transferenciaList) {
                    if (transTemp == transferencia) {
                        transferencia.setTipoOficio(transTemp.getTipoOficio());
                        break;
                    }
                }
            }
        }
        //rharo - 04/08/2016
        if (estatus.equals("K")) {
            for (Transferencia transferencia : movimiento.getTransferenciaList()) {
                objOficio = new TipoOficio();
                if (!transferencia.getTipoOficio().equals(tipoOficio)) {
                    if (transfBean.getResultSQLexisteTipoOficio(movimiento.getOficio(), transferencia.getTipoOficio())) {
                        mensaje = "5|No es posible guardar el movimiento.\nLa modificación generó un tipo oficio que ya existe en este oficio. (" + tipoOficio + "-" + tipoOficioDescr + ")";
                        bandera = false;
                        break;
                    } else {
                        banderaTipo = true;
                        objOficio.setTipoOficio(transferencia.getTipoOficio());
                        //Uso el atributo oficio como si fuera el consecutivo de la transferencia
                        objOficio.setOficio(transferencia.getConsec());
                        tipoOficioList.add(objOficio);
                    }
                }
            }
        }
        if (movimiento.getTransferenciaList().size() > 0) {
            if (bandera) {
                if (checkImpacto) {
                    if (movimiento.getTransferenciaMetaList().size() > 0 || movimiento.getTransferenciaAccionList().size() > 0) {
                        if (movimiento.getRamo() == null) {
                            mensaje = transfBean.saveMovimientoTransferencia(movimiento, movimiento.getOficio(), isActual, year, date, capturaEspecial, ramoSession, selCaratula, tipoOficio, justificacion, tipoDependencia);
                        } else {
                            mensaje = transfBean.saveMovimientoTransferencia(movimiento, movimiento.getOficio(), isActual, year, date, capturaEspecial, movimiento.getRamo(), selCaratula, tipoOficio, justificacion, tipoDependencia);
                        }
                    } else {
                        mensaje = "3|Al seleccionar 'Impacto programático' debe de realizar al menos un movimiento en metas o acciones";
                    }
                } else {
                    if (movimiento.getRamo() == null) {
                        mensaje = transfBean.saveMovimientoTransferencia(movimiento, movimiento.getOficio(), isActual, year, date, capturaEspecial, ramoSession, selCaratula, tipoOficio, justificacion, tipoDependencia);
                    } else {
                        mensaje = transfBean.saveMovimientoTransferencia(movimiento, movimiento.getOficio(), isActual, year, date, capturaEspecial, movimiento.getRamo(), selCaratula, tipoOficio, justificacion, tipoDependencia);
                    }
                }
            }
        } else {
            mensaje = "2|Debe de realizar al menos una Transferencia";
        }
        if (mensaje.isEmpty()) {
            strResultado += "4|Ocurrió un error al procesar la solicitud";
        }
        if (mensaje.startsWith("$")) {
            if (mensaje.replace("$", "").trim().split("\\|")[1].equals("N")) {
                isRechazado = true;
            }
            movimiento.setOficio(Integer.parseInt(mensaje.replace("$", "").split("\\|")[0].trim()));
            if (banderaTipo) {
                for (TipoOficio tipo : tipoOficioList) {
                    bandera = transfBean.insertTipoOficioNuevo(movimiento.getOficio(), tipo.getTipoOficio(), tipo.getOficio());
                }
            }
            if (bandera) {
                if (banderaTipo) {
                    if (isRechazado) {
                        strResultado = "1|Se creo un nuevo folio para continuar su trámite|" + movimiento.getOficio() + "|N";
                        movimiento.setStatus("X");
                    } else {
                        strResultado = "1|Los registros se grabaron satisfactoriamente|" + movimiento.getOficio() + "|N";
                    }
                } else {
                    if (isRechazado) {
                        strResultado = "1|Se creo un nuevo folio para continuar su trámite|" + movimiento.getOficio();
                        movimiento.setStatus("X");
                    } else {
                        strResultado = "1|Los registros se grabaron satisfactoriamente|" + movimiento.getOficio();
                    }
                }
                session.setAttribute("transferencia", movimiento);
            } else {
                strResultado += "7|Ocurrió un error al guardar ";
            }
        } else {
            strResultado += mensaje;
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
        if (transfBean != null) {
            transfBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>