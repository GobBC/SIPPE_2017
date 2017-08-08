<%-- 
    Document   : ajaxEnviarAutorizacionTransferencia
    Created on : Feb 16, 2016, 15:51:20 AM
    Author     : ugarcia
--%>

<%@page import="java.sql.Date"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
    BigDecimal importeTransferencia = new BigDecimal(0.0);
    BigDecimal importeSuma = new BigDecimal(0.0);
    BigDecimal disponibleEnvio = new BigDecimal(0.0);
    BigDecimal acRamoPartida = new BigDecimal(0.0);
    TransferenciaBean transBean = null;
    List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
    Map<String, List<Transferencia>> map = new HashMap<String, List<Transferencia>>();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String ramo = new String();
    String partida = new String();
    String strResultado = new String();
    String tipoDependencia = new String();
    String redPasad = new String();
    String estatus = new String();
    String fecha = new String();
    String capturaEspecial = new String();
    String tipoOficio = new String();
    String llave = new String();
    String ramoSession = new String();
    String valAutorizado = new String();
    String codigoAutorizado[] = new String[5];
    int folioAutorizado = 0;
    long selCaratula = -1;
    int folio = 0;
    int year = 0;
    int totMeta = 0;
    int totAccion = 0;
    int contMeta = 0;
    int contAccion = 0;
    int monthAct = 0;

    boolean isFechaContable = false;
    boolean isParaestatal = false;
    boolean bandera = true;
    boolean banderaContable = true;
    boolean isActual = false;
    boolean banderaTipof = false;
    boolean banderaTemp = false;
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            movTransferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
            if (movTransferencia.getFechaElab() == null) {
                movTransferencia.setFechaElab(new Date(System.currentTimeMillis()));
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
        transBean = new TransferenciaBean(tipoDependencia);
        transBean.setStrServer(request.getHeader("host"));
        transBean.setStrUbicacion(getServletContext().getRealPath(""));
        transBean.resultSQLConecta(tipoDependencia);
        isParaestatal = transBean.getResultSQLisParaestatal();
        if (!isParaestatal) {
            for (Transferencia transferencia : movTransferencia.getTransferenciaList()) {
                disponibleEnvio = new BigDecimal(transBean.getDisponible(year, transferencia.getRamo(), transferencia.getPrograma(),
                        transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(),
                        transferencia.getAccion(), transferencia.getPartida(), transferencia.getFuente(),
                        transferencia.getFondo(), transferencia.getRecurso(), transferencia.getRelLaboral(), monthAct)).setScale(2, RoundingMode.HALF_UP);
                
                
                if (disponibleEnvio.compareTo(new BigDecimal(transferencia.getImporte()).abs().setScale(2, RoundingMode.HALF_UP)) < 0 && !transBean.isPartidaDePlantilla(transferencia.getPartida(), year)) {
                    banderaContable = false;
                    redPasad = transferencia.getRamo() + "-" + transferencia.getPrograma() + "-" + transferencia.getMeta() + "-" + transferencia.getAccion() + "-" + transferencia.getPartida();
                }
                
                
            }
        } else {
            banderaContable = true;
        }
        //validación de transferencias sobre variable de sessión
        if (banderaContable) {
            for (Transferencia transferencia : movTransferencia.getTransferenciaList()) {
                totMeta = movTransferencia.getTransferenciaMetaList().size();
                totAccion = movTransferencia.getTransferenciaAccionList().size();
                for (TransferenciaMeta transMeta : movTransferencia.getTransferenciaMetaList()) {
                    //if(transMeta.getMovOficioMeta().getNva_creacion().equals("S") && transMeta.getMovOficioMeta().getMetaId() < 0){

                    valAutorizado = transBean.getResultSQLValidaModificacionMetaAutorizado(folio, transMeta.getMovOficioMeta().getRamoId(), transMeta.getMovOficioMeta().getMetaId());

                    if (valAutorizado.equals("P")) {
                        folioAutorizado = transBean.getResultSQLValidaOficioAutorizadoMeta(year, transMeta.getMovOficioMeta().getRamoId(), transMeta.getMovOficioMeta().getMetaId(), movTransferencia.getFechaElab());
                        if (folioAutorizado > 0) {
                            if (transBean.getResultSQLUpdateModificacionMeta("N", folio, transMeta.getMovOficioEstimacionList().get(0).getRamo(), transMeta.getMovOficioEstimacionList().get(0).getMeta())) {
                                transMeta.setValAutorizado("N");
                                codigoAutorizado[0] = transMeta.getMovOficioMeta().getRamoId();
                                codigoAutorizado[1] = transMeta.getMovOficioMeta().getPrgId();
                                codigoAutorizado[2] = transMeta.getMovOficioMeta().getProyId() + transMeta.getMovOficioMeta().getTipoProy();
                                codigoAutorizado[3] = String.valueOf(transMeta.getMovOficioMeta().getMetaId());
                                codigoAutorizado[4] = new String();
                                transBean.transaccionCommit();
                            } else {
                                transBean.transaccionRollback();
                            }
                            break;
                        }
                    } else {
                        if (valAutorizado.equals("N")) {
                            folioAutorizado = transBean.getResultSQLValidaOficioAutorizadoMeta(year, transMeta.getMovOficioEstimacionList().get(0).getRamo(), transMeta.getMovOficioEstimacionList().get(0).getMeta(), movTransferencia.getFechaElab());
                            if (folioAutorizado > 0) {
                                codigoAutorizado[0] = transMeta.getMovOficioMeta().getRamoId();
                                codigoAutorizado[1] = transMeta.getMovOficioMeta().getPrgId();
                                codigoAutorizado[2] = transMeta.getMovOficioMeta().getProyId() + transMeta.getMovOficioMeta().getTipoProy();
                                codigoAutorizado[3] = String.valueOf(transMeta.getMovOficioMeta().getMetaId());
                                codigoAutorizado[4] = new String();
                                break;
                            }
                        }
                    }

                    banderaTemp = false;

                    if (folioAutorizado == 0) {

                        for (TransferenciaAccionReq transAccionReq : transferencia.getTransferenciaAccionReqList()) {
                            if (transMeta.getMovOficioMeta().getRamoId().equals(transAccionReq.getRamo())
                                    && transMeta.getMovOficioMeta().getPrgId().equals(transAccionReq.getPrograma())
                                    && transMeta.getMovOficioMeta().getMetaId() == transAccionReq.getMeta()) {
                                banderaTemp = true;
                                contMeta++;
                                break;
                            } else {
                                if (transMeta.getMovOficioMeta().getRamoId().equals(transferencia.getRamo())
                                        && transMeta.getMovOficioMeta().getPrgId().equals(transferencia.getPrograma())
                                        && transMeta.getMovOficioMeta().getMetaId() == transferencia.getMeta()) {
                                    banderaTemp = true;
                                    contMeta++;
                                    break;
                                }
                            }
                        }
                        if (banderaTemp) {
                            bandera = true;
                        } else {

                            bandera = false;
                        }
                    }
                }

                if (folioAutorizado == 0) {
                    for (TransferenciaAccion ampRedAccion : movTransferencia.getTransferenciaAccionList()) {
                        //if(ampRedAccion.getMovOficioAccion().getNvaCreacion().equals("S")  && ampRedAccion.getMovOficioAccion().getAccionId() < 0){

                        valAutorizado = transBean.getResultSQLValidaModificacionAccionAutorizado(folio, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId());

                        if (valAutorizado.equals("P")) {
                            folioAutorizado = transBean.getResultSQLValidaOficioAutorizadoAccion(year, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId(), movTransferencia.getFechaElab());
                            if (folioAutorizado > 0) {
                                if (transBean.getResultSQLUpdateModificacionAccion("N", folio, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId())) {
                                    ampRedAccion.setValAutorizado("N");
                                    codigoAutorizado[0] = ampRedAccion.getMovOficioAccion().getRamoId();
                                    codigoAutorizado[1] = ampRedAccion.getMovOficioAccion().getProgramaId();
                                    codigoAutorizado[2] = ampRedAccion.getMovOficioAccion().getProyectoId() + ampRedAccion.getMovOficioAccion().getTipoProy();
                                    codigoAutorizado[3] = String.valueOf(ampRedAccion.getMovOficioAccion().getMetaId());
                                    codigoAutorizado[4] = String.valueOf(ampRedAccion.getMovOficioAccion().getAccionId());
                                    transBean.transaccionCommit();
                                } else {
                                    transBean.transaccionRollback();
                                }
                                break;
                            }
                        } else {
                            if (valAutorizado.equals("N")) {
                                folioAutorizado = transBean.getResultSQLValidaOficioAutorizadoAccion(year, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId(), movTransferencia.getFechaElab());
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
                            for (TransferenciaAccionReq transAccionReq : transferencia.getTransferenciaAccionReqList()) {
                                banderaTemp = false;
                                if (ampRedAccion.getMovOficioAccion().getRamoId().equals(transAccionReq.getRamo())
                                        && ampRedAccion.getMovOficioAccion().getProgramaId().equals(transAccionReq.getPrograma())
                                        && ampRedAccion.getMovOficioAccion().getMetaId() == transAccionReq.getMeta()
                                        && ampRedAccion.getMovOficioAccion().getAccionId() == transAccionReq.getAccion()) {
                                    banderaTemp = true;
                                    contAccion++;
                                    break;
                                } else {
                                    if (ampRedAccion.getMovOficioAccion().getRamoId().equals(transferencia.getRamo())
                                            && ampRedAccion.getMovOficioAccion().getProgramaId().equals(transferencia.getPrograma())
                                            && ampRedAccion.getMovOficioAccion().getMetaId() == transferencia.getMeta()
                                            && ampRedAccion.getMovOficioAccion().getAccionId() == transferencia.getAccion()) {
                                        banderaTemp = true;
                                        contAccion++;
                                        break;
                                    }
                                }
                            }
                            if (banderaTemp) {
                                bandera = true;
                            } else {
                                bandera = false;
                            }
                        }
                    }
                }
                if (contAccion >= totAccion && contMeta >= totMeta && bandera) {
                    break;
                }
            }
            /*
             if(totMeta != movTransferencia.getTransferenciaMetaList().size())
             for(TransferenciaMeta transMeta : movTransferencia.getTransferenciaMetaList()){
             //if(transMeta.getMovOficioMeta().getNva_creacion().equals("S") && transMeta.getMovOficioMeta().getMetaId() < 0){
             banderaTemp = false;
             for(Transferencia transAccionReq : movTransferencia.getTransferenciaList()){
             if(transMeta.getMovOficioMeta().getRamoId().equals(transAccionReq.getRamo()) && 
             transMeta.getMovOficioMeta().getPrgId().equals(transAccionReq.getPrograma()) && 
             transMeta.getMovOficioMeta().getMetaId() == transAccionReq.getMeta()){
             banderaTemp = true;
             break;
             }
             }
             if(banderaTemp){
             bandera = true;
             }else{
             bandera = false;
             }
             //}
             }
            
             if(totAccion != movTransferencia.getTransferenciaAccionList().size())
             for(TransferenciaAccion ampRedAccion : movTransferencia.getTransferenciaAccionList()){  
             //if(ampRedAccion.getMovOficioAccion().getNvaCreacion().equals("S")  && ampRedAccion.getMovOficioAccion().getAccionId() < 0){
             banderaTemp = false;
             for(Transferencia transAccion : movTransferencia.getTransferenciaList()){
             if(ampRedAccion.getMovOficioAccion().getRamoId().equals(transAccion.getRamo()) && 
             ampRedAccion.getMovOficioAccion().getProgramaId().equals(transAccion.getPrograma()) && 
             ampRedAccion.getMovOficioAccion().getMetaId() == transAccion.getMeta() &&
             ampRedAccion.getMovOficioAccion().getAccionId() == transAccion.getAccion()){
             banderaTemp = true;
             break;
             }
             }
             if(banderaTemp){
             bandera = true;
             }else{
             bandera = false;
             }
             // }
             }
             */

            if (folioAutorizado == 0) {
                if (bandera) {
                    for (Transferencia transferencia : movTransferencia.getTransferenciaList()) {
                        importeTransferencia = new BigDecimal(transferencia.getImporte());
                        importeSuma = new BigDecimal(0.0);
                        for (TransferenciaAccionReq transAccion : transferencia.getTransferenciaAccionReqList()) {
                            importeSuma = importeSuma.add(new BigDecimal(transAccion.getImporte()));
                        }
                        importeSuma = importeSuma.setScale(2, RoundingMode.HALF_UP);
                        importeTransferencia = new BigDecimal(transferencia.getImporte()).setScale(2, RoundingMode.HALF_UP);
                        if (importeTransferencia.compareTo(importeSuma) != 0) {
                            bandera = false;
                        }
                    }
                    if (bandera) {
                        if (isFechaContable) {
                            fecha = df.format(transBean.getResultSQLgetFechaContabilidad());
                            capturaEspecial = "S";
                        } else {
                            capturaEspecial = "N";
                        }
                        if (!isParaestatal) {
                            for (Transferencia transferencia : movTransferencia.getTransferenciaList()) {
                                transferencia.setTipoOficio(transBean.getTipoOficio(transferencia.getTransferenciaAccionReqList(),
                                        transferencia.getRamo(),
                                        transferencia.getPartida(),
                                        year,
                                        transferencia.getQuincePor(),
                                        folio,
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
                                ramo =  key.split("\\|")[0];
                                partida = key.split("\\|")[1];
                                    transferenciaList = map.get(key);
                                    for (Transferencia transf : transferenciaList) {
                                        if (estatus.equals("R")) {
                                            acRamoPartida = transBean
                                                    .getResultSQLgetAcumluladoMovtosRechazado(year,
                                                            partida,
                                                            ramo,
                                                            folio);
                                        } else if (estatus.equals("X")) {
                                            acRamoPartida = transBean.getResultSQLgetAcumuladoCaptura(year, folio, ramo, partida);
                                        }
                                        BigDecimal bdQuincePor = transBean.getResultSQLgetQuincePorcientoTipoOficio(year,
                                                transf.getRamo(),
                                                transf.getPartida(),
                                                acRamoPartida);
                                        bdQuincePor = bdQuincePor.multiply(new BigDecimal(100));
                                        if (bdQuincePor.compareTo(new BigDecimal(15)) > 0) {
                                            for (Transferencia trans : transferenciaList) {
                                                trans.setTipoOficio("A");
                                            }
                                            break;
                                        }
                                    }
                                    map.put(key, transferenciaList);
                                    if (banderaTipof) {
                                        break;
                                    }
                                }
                            if (banderaTipof) {
                                for (Transferencia trans : movTransferencia.getTransferenciaList()) {
                                    trans.setTipoOficio("A");
                                }
                            }
                            /*
                             for(Transferencia transferencia : movTransferencia.getTransferenciaList()){
                             transferenciaList = new ArrayList<Transferencia>();
                             transferenciaList = map.get(transferencia.getRamo()+"|"+transferencia.getPartida());
                             if(transferenciaList != null)
                             for(Transferencia transTemp : transferenciaList){
                             if(transTemp == transferencia){
                             transferencia.setTipoOficio(transTemp.getTipoOficio());
                             break;
                             }
                             }
                             }
                             */
                            //rharo - 04/08/2016
                        } else {
                            for (Transferencia transferencia : movTransferencia.getTransferenciaList()) {
                                transferencia.setTipoOficio("A");
                            }
                        }
                        movTransferencia.getMovCaratula().setsIdCaratula(selCaratula);
                        movTransferencia.getMovCaratula().setsRamo(ramoSession);
                        strResultado += transBean.cambiaEstatusMovOficioTransferencia(movTransferencia, folio, isActual, "T", tipoDependencia, estatus, year, monthAct, fecha, capturaEspecial, tipoOficio);
                    } else {
                        strResultado += "3|Existen transferencias donde no cuadra el monto transferido con la suma del monto asignado.";
                    }
                } else {
                    strResultado += "3|Todas las metas y acciones creadas deben de ser parte de una transferencia";
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
                session.setAttribute("transferencia", movTransferencia);
            }

        } else {
            strResultado += "3|La transferencia " + redPasad + " sobrepasa al disponible";
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
        if (transBean != null) {
            transBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>