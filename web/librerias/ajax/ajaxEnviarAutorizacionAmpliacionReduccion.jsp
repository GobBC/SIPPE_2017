<%-- 
    Document   : ajaxEnviarAutorizacionAmpliacionReduccion
    Created on : Jan 14, 2016, 10:40:59 AM
    Author     : ugarcia
--%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MovimientosAmpliacionReduccion movAmpliacionRed = new MovimientosAmpliacionReduccion();
    List<AmpliacionReduccionMeta> ampRedMetaList = new ArrayList<AmpliacionReduccionMeta>();
    List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
    List<AmpliacionReduccionAccionReq> ampRedAccionRecList = new ArrayList<AmpliacionReduccionAccionReq>();
    AmpliacionReduccionBean ampRedBean = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    BigDecimal disponible;
    BigDecimal importe;
    String redPasad = new String();
    String strResultado = new String();
    String tipoDependencia = new String();
    String estatus = new String();
    String fecha = new String();
    String capturaEspecial = new String();
    String tipoOficio = new String();
    String ramoSession = new String();
    String valAutorizado = new String();
    String codigoAutorizado[] = new String[5];
    int folioAutorizado = 0;
    long selCaratula = -1;
    int folio = 0;
    int year = 0;
    int monthAct = 0;
    boolean isFechaContable = false;
    boolean banderaM = true;
    boolean banderaA = true;
    boolean banderaTemp = false;
    boolean isActual = false;
    boolean banderaContable = true;
    boolean isParaestatal = false;

    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "") {
            movAmpliacionRed = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampRedMetaList = movAmpliacionRed.getAmpReducMetaList();
            ampRedAccionList = movAmpliacionRed.getAmpReducAccionList();
            ampRedAccionRecList = movAmpliacionRed.getAmpReducAccionReqList();
            if (movAmpliacionRed.getFechaElab() == null) {
                movAmpliacionRed.setFechaElab(new Date(System.currentTimeMillis()));
            }
        }
        if (Utilerias.existeParametro("fecha", request)) {
            fecha = request.getParameter("fecha");
            monthAct = Integer.parseInt(fecha.split("\\/")[1]);
        }
        if (Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (Utilerias.existeParametro("fechaContable", request)) {
            isFechaContable = Boolean.parseBoolean(request.getParameter("fechaContable"));
        }
        if (Utilerias.existeParametro("tipoOficio", request)) {
            tipoOficio = request.getParameter("tipoOficio");
        }
        if (Utilerias.existeParametro("estatus", request)) {
            estatus = request.getParameter("estatus");
        }
        if (Utilerias.existeParametro("isActual", request)) {
            isActual = Boolean.parseBoolean(request.getParameter("isActual"));
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }
        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer(request.getHeader("host"));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRedBean.resultSQLConecta(tipoDependencia);
        isParaestatal = ampRedBean.getResultSQLisParaestatal();

        if (!isParaestatal) {
            for (AmpliacionReduccionAccionReq ampRedAccionReq : ampRedAccionRecList) {
                disponible = new BigDecimal(ampRedBean.getDisponible(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                        ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(),
                        ampRedAccionReq.getAccion(), ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(),
                        ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(), ampRedAccionReq.getRelLaboral(), monthAct));
                disponible = disponible.setScale(2, RoundingMode.HALF_UP);
                importe = new BigDecimal(ampRedAccionReq.getImporte()).setScale(2, RoundingMode.HALF_UP);
                if (!ampRedBean.isPartidaDePlantilla(ampRedAccionReq.getPartida(), year)) {
                    if (disponible.compareTo(importe.abs()) < 0 && ampRedAccionReq.getImporte() < 0) {
                        banderaContable = false;
                        redPasad = ampRedAccionReq.getRamo() + "-" + ampRedAccionReq.getPrograma() + "-" + ampRedAccionReq.getMeta() + "-" + ampRedAccionReq.getAccion() + "-" + ampRedAccionReq.getPartida();
                    }
                }

            }
        } else {
            banderaContable = true;
        }

        if (banderaContable) {
            for (AmpliacionReduccionMeta ampRedMeta : ampRedMetaList) {
                //if(ampRedMeta.getMovOficioMeta().getNva_creacion().equals("S") && ampRedMeta.getMovOficioMeta().getMetaId() < 0){

                valAutorizado = ampRedBean.getResultSQLValidaModificacionMetaAutorizado(folio, ampRedMeta.getMovOficioMeta().getRamoId(), ampRedMeta.getMovOficioMeta().getMetaId());

                if (valAutorizado.equals("P")) {
                    folioAutorizado = ampRedBean.getResultSQLValidaOficioAutorizadoMeta(year, ampRedMeta.getMovOficioMeta().getRamoId(), ampRedMeta.getMovOficioMeta().getMetaId(), movAmpliacionRed.getFechaElab());
                    if (folioAutorizado > 0) {
                        if (ampRedBean.getResultSQLUpdateModificacionMeta("N", folio, ampRedMeta.getMovOficioEstimacionList().get(0).getRamo(), ampRedMeta.getMovOficioEstimacionList().get(0).getMeta())) {
                            ampRedMeta.setValAutorizado("N");
                            codigoAutorizado[0] = ampRedMeta.getMovOficioMeta().getRamoId();
                            codigoAutorizado[1] = ampRedMeta.getMovOficioMeta().getPrgId();
                            codigoAutorizado[2] = ampRedMeta.getMovOficioMeta().getProyId() + ampRedMeta.getMovOficioMeta().getTipoProy();
                            codigoAutorizado[3] = String.valueOf(ampRedMeta.getMovOficioMeta().getMetaId());
                            codigoAutorizado[4] = new String();
                            ampRedBean.transaccionCommit();
                        } else {
                            ampRedBean.transaccionRollback();
                        }
                        break;
                    }
                } else {
                    if (valAutorizado.equals("N")) {
                        folioAutorizado = ampRedBean.getResultSQLValidaOficioAutorizadoMeta(year, ampRedMeta.getMovOficioEstimacionList().get(0).getRamo(), ampRedMeta.getMovOficioEstimacionList().get(0).getMeta(), movAmpliacionRed.getFechaElab());
                        if (folioAutorizado > 0) {
                            codigoAutorizado[0] = ampRedMeta.getMovOficioMeta().getRamoId();
                            codigoAutorizado[1] = ampRedMeta.getMovOficioMeta().getPrgId();
                            codigoAutorizado[2] = ampRedMeta.getMovOficioMeta().getProyId() + ampRedMeta.getMovOficioMeta().getTipoProy();
                            codigoAutorizado[3] = String.valueOf(ampRedMeta.getMovOficioMeta().getMetaId());
                            codigoAutorizado[4] = new String();
                            break;
                        }
                    }
                }

                banderaTemp = false;

                if (folioAutorizado == 0) {
                    for (AmpliacionReduccionAccionReq ampRedAccionReq : ampRedAccionRecList) {
                        if (ampRedMeta.getMovOficioMeta().getRamoId().equals(ampRedAccionReq.getRamo())
                                && ampRedMeta.getMovOficioMeta().getPrgId().equals(ampRedAccionReq.getPrograma())
                                && ampRedMeta.getMovOficioMeta().getMetaId() == ampRedAccionReq.getMeta()) {
                            banderaTemp = true;
                            break;
                        }
                    }
                    if (banderaTemp) {
                        banderaM = true;
                    } else {
                        banderaM = false;
                        break;
                    }
                }
            }

            if (folioAutorizado == 0) {
                for (AmpliacionReduccionAccion ampRedAccion : ampRedAccionList) {
                    //if(ampRedAccion.getMovOficioAccion().getNvaCreacion().equals("S")  && ampRedAccion.getMovOficioAccion().getAccionId() < 0){

                    valAutorizado = ampRedBean.getResultSQLValidaModificacionAccionAutorizado(folio, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId());

                    if (valAutorizado.equals("P")) {
                        folioAutorizado = ampRedBean.getResultSQLValidaOficioAutorizadoAccion(year, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId(), movAmpliacionRed.getFechaElab());
                        if (folioAutorizado > 0) {
                            if (ampRedBean.getResultSQLUpdateModificacionAccion("N", folio, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId())) {
                                ampRedAccion.setValAutorizado("N");
                                codigoAutorizado[0] = ampRedAccion.getMovOficioAccion().getRamoId();
                                codigoAutorizado[1] = ampRedAccion.getMovOficioAccion().getProgramaId();
                                codigoAutorizado[2] = ampRedAccion.getMovOficioAccion().getProyectoId() + ampRedAccion.getMovOficioAccion().getTipoProy();
                                codigoAutorizado[3] = String.valueOf(ampRedAccion.getMovOficioAccion().getMetaId());
                                codigoAutorizado[4] = String.valueOf(ampRedAccion.getMovOficioAccion().getAccionId());
                                ampRedBean.transaccionCommit();
                            } else {
                                ampRedBean.transaccionRollback();
                            }
                            break;
                        }
                    } else {
                        if (valAutorizado.equals("N")) {
                            folioAutorizado = ampRedBean.getResultSQLValidaOficioAutorizadoAccion(year, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId(), movAmpliacionRed.getFechaElab());
                            if (folioAutorizado > 0) {
                                ampRedAccion.setValAutorizado("N");
                                codigoAutorizado[0] = ampRedAccion.getMovOficioAccion().getRamoId();
                                codigoAutorizado[1] = ampRedAccion.getMovOficioAccion().getProgramaId();
                                codigoAutorizado[2] = ampRedAccion.getMovOficioAccion().getProyectoId() + ampRedAccion.getMovOficioAccion().getTipoProy();
                                codigoAutorizado[3] = String.valueOf(ampRedAccion.getMovOficioAccion().getMetaId());
                                codigoAutorizado[4] = String.valueOf(ampRedAccion.getMovOficioAccion().getAccionId());
                                break;
                            }
                        }
                    }

                    if (folioAutorizado == 0) {
                        for (AmpliacionReduccionAccionReq ampRedAccionReq : ampRedAccionRecList) {
                            banderaTemp = false;
                            if (ampRedAccion.getMovOficioAccion().getRamoId().equals(ampRedAccionReq.getRamo())
                                    && ampRedAccion.getMovOficioAccion().getProgramaId().equals(ampRedAccionReq.getPrograma())
                                    && ampRedAccion.getMovOficioAccion().getMetaId() == ampRedAccionReq.getMeta()
                                    && ampRedAccion.getMovOficioAccion().getAccionId() == ampRedAccionReq.getAccion()) {
                                banderaTemp = true;
                                break;
                            }
                        }

                        if (banderaTemp) {
                            banderaA = true;
                        } else {
                            banderaA = false;
                            break;
                        }
                    }
                }
            }

            if (folioAutorizado == 0) {
                if (isFechaContable) {
                    fecha = df.format(ampRedBean.getResultSQLgetFechaContabilidad());
                    capturaEspecial = "S";
                } else {
                    capturaEspecial = "N";
                }

                if (banderaM && banderaA) {
                    movAmpliacionRed.getMovCaratula().setsIdCaratula(selCaratula);
                    movAmpliacionRed.getMovCaratula().setsRamo(ramoSession);
                    strResultado += ampRedBean.cambiaEstatusMovOficioAmpliacionReduccion(folio, "A", tipoDependencia, estatus,
                            movAmpliacionRed, year, monthAct, fecha, capturaEspecial, tipoOficio, isActual);
                } else {
                    strResultado += "3|Todas las metas y acciones con movimientos programáticos deben ser parte de una ampliación ";
                }
            } else {
                strResultado += "3|Existe movimiento " + folioAutorizado + " Autorizado que realizó modificación de Impacto Programático posterior a su captura Original. \n";
                strResultado += "Código Ramo " + codigoAutorizado[0] + " Programa " + codigoAutorizado[1] + " Proyecto/Actividad " + codigoAutorizado[2] + " Meta " + codigoAutorizado[3];
                if (!codigoAutorizado[4].isEmpty()) {
                    strResultado += " Acción " + codigoAutorizado[4] + "\n";
                } else {
                    strResultado += "\n";
                }
                strResultado += "Deberá modificar su captura Original de Impacto sobre el código anterior.";
                session.setAttribute("ampliacionReduccion", movAmpliacionRed);
            }

        } else {
            strResultado += "3|La reducción " + redPasad + " sobrepasa al disponible";
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
        if (ampRedBean != null) {
            ampRedBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>