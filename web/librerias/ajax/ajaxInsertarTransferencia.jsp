<%-- 
    Document   : ajaxInsertarTransferencia
    Created on : Jan 8, 2016, 2:14:37 PM
    Author     : ugarcia
--%>
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.ProgramaConac"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.MovOficiosAccionReq"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="gob.gbc.entidades.CodigoPPTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    TransferenciaBean transBean = null;
    CodigoPPTO codigoPPTO = new CodigoPPTO();
    ProgramaConac prgConac = new ProgramaConac();
    MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
    TransferenciaAccionReq transAccionReq = new TransferenciaAccionReq();
    TransferenciaAccion transAccion = new TransferenciaAccion();
    TransferenciaMeta transMeta = new TransferenciaMeta();
    Transferencia transferencia = new Transferencia();
    Meta metaObj = new Meta();
    List<TransferenciaAccionReq> transAccionReqList = new ArrayList<TransferenciaAccionReq>();
    List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
    List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
    DecimalFormat df;
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    String ramoT = new String();
    String programa = new String();
    String partida = new String();
    String partidaT = new String();
    String fuente = new String();
    String fuenteFin[] = new String[3];
    String relLaboral = new String();
    String reqDescr = new String();
    String tipoProy = new String();
    String justificacion = new String();
    String tipoGasto = new String();
    String fecha = new String();
    String articuloDescr = new String();
    String articulo = "0";
    BigDecimal nuevoImporte = new BigDecimal(0.0);
    BigDecimal importeViejo = new BigDecimal(0.0);
    int reqId = 0;
    int monthAct = 0;
    int proy = 0;
    int meta = 0;
    int accion = 0;
    int year = 0;
    int edicion = 0;
    int identificador = 0;
    int transferenciaId = -1;
    int mesActual = 0;
    double ene = 0.0;
    double feb = 0.0;
    double mar = 0.0;
    double abr = 0.0;
    double may = 0.0;
    double jun = 0.0;
    double jul = 0.0;
    double ago = 0.0;
    double sep = 0.0;
    double oct = 0.0;
    double nov = 0.0;
    double dic = 0.0;
    double costUnitario = 0.0;
    double costoAnual = 0.0;
    double cantAnual = 0.0;
    double disponible = 0.0;
    double importe = 0.0;
    double quincePor = 0.0;
    double acumulado = 0.0;
    try {
        df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        /*if (session.getAttribute("accionReqIdNegativo") != null && session.getAttribute("accionReqIdNegativo") != "") {
         accionIdNegativo = Integer.parseInt( session.getAttribute("accionReqIdNegativo"));
         }*/
        if (Utilerias.existeParametro("fecha", request)) {
            fecha =    request.getParameter("fecha");
            monthAct = Integer.parseInt(fecha.split("\\/")[1]);
        }
        if (Utilerias.existeParametro("transferenciaId", request)) {
            transferenciaId = Integer.parseInt(request.getParameter("transferenciaId"));
        }
        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            movTransferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
            for(Transferencia transferenciatemp : movTransferencia.getTransferenciaList()){
                if(transferenciatemp.getConsec() == transferenciaId){
                    transferencia = transferenciatemp;
                    break;
                }
            }
            transAccionReqList = transferencia.getTransferenciaAccionReqList();
            transMetaList = movTransferencia.getTransferenciaMetaList();
            transAccionList = movTransferencia.getTransferenciaAccionList();
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramo") != null
                && !request.getParameter("ramo").equals("")) {
            ramo =  request.getParameter("ramo");
        }
        if (request.getParameter("ramoT") != null
                && !request.getParameter("ramoT").equals("")) {
            ramoT =  request.getParameter("ramoT");
        }
        if (request.getParameter("programa") != null
                && !request.getParameter("programa").equals("")) {
            programa =  request.getParameter("programa");
        }
        if (request.getParameter("proy") != null
                && !request.getParameter("proy").equals("")) {
            proy = Integer.parseInt(request.getParameter("proy"));
        }
        if (request.getParameter("tipoProy") != null
                && !request.getParameter("tipoProy").equals("")) {
            tipoProy =  request.getParameter("tipoProy");
        }
        if (request.getParameter("meta") != null
                && !request.getParameter("meta").equals("")) {
            meta = Integer.parseInt( request.getParameter("meta"));
        }
        if (request.getParameter("accion") != null
                && !request.getParameter("accion").equals("")) {
            accion = Integer.parseInt( request.getParameter("accion"));
        }
        if (request.getParameter("partida") != null
                && !request.getParameter("partida").equals("")) {
            partida =  request.getParameter("partida");
        }
        if (request.getParameter("partidaT") != null
                && !request.getParameter("partidaT").equals("")) {
            partidaT =  request.getParameter("partidaT");
        }
        if (request.getParameter("relLaboral") != null
                && !request.getParameter("relLaboral").equals("")) {
            relLaboral =  request.getParameter("relLaboral");
        }
        if (request.getParameter("reqDescr") != null
                && !request.getParameter("reqDescr").equals("")) {
            reqDescr =  request.getParameter("reqDescr");
        }
        if (request.getParameter("articuloDescr") != null
                && !request.getParameter("articuloDescr").equals("")) {
            articuloDescr =  request.getParameter("articuloDescr");
        }
        if (request.getParameter("articulo") != null
                && !request.getParameter("articulo").equals("")) {
            articulo =  request.getParameter("articulo");
        }
        if (request.getParameter("justificacion") != null
                && !request.getParameter("justificacion").equals("")) {
            justificacion =  request.getParameter("justificacion");
        }
        if (request.getParameter("fuente") != null
                && !request.getParameter("fuente").equals("")) {
            fuente =  request.getParameter("fuente");
            fuenteFin = fuente.split("\\.");
        }
        if (request.getParameter("ene") != null
                && !request.getParameter("ene").equals("")) {
            ene = Double.parseDouble( request.getParameter("ene"));
        }
        if (request.getParameter("feb") != null
                && !request.getParameter("feb").equals("")) {
            feb = Double.parseDouble( request.getParameter("feb"));
        }
        if (request.getParameter("mar") != null
                && !request.getParameter("mar").equals("")) {
            mar = Double.parseDouble( request.getParameter("mar"));
        }
        if (request.getParameter("abr") != null
                && !request.getParameter("abr").equals("")) {
            abr = Double.parseDouble(request.getParameter("abr"));
        }
        if (request.getParameter("may") != null
                && !request.getParameter("may").equals("")) {
            may = Double.parseDouble( request.getParameter("may"));
        }
        if (request.getParameter("jun") != null
                && !request.getParameter("jun").equals("")) {
            jun = Double.parseDouble(  request.getParameter("jun"));
        }
        if (request.getParameter("jul") != null
                && !request.getParameter("jul").equals("")) {
            jul = Double.parseDouble(  request.getParameter("jul"));
        }
        if (request.getParameter("ago") != null
                && !request.getParameter("ago").equals("")) {
            ago = Double.parseDouble(  request.getParameter("ago"));
        }
        if (request.getParameter("sep") != null
                && !request.getParameter("sep").equals("")) {
            sep = Double.parseDouble(  request.getParameter("sep"));
        }
        if (request.getParameter("oct") != null
                && !request.getParameter("oct").equals("")) {
            oct = Double.parseDouble(  request.getParameter("oct"));
        }
        if (request.getParameter("nov") != null
                && !request.getParameter("nov").equals("")) {
            nov = Double.parseDouble(  request.getParameter("nov"));
        }
        if (request.getParameter("dic") != null
                && !request.getParameter("dic").equals("")) {
            dic = Double.parseDouble(  request.getParameter("dic"));
        }
        if (request.getParameter("costoUnitario") != null
                && !request.getParameter("costoUnitario").equals("")) {
            costUnitario = Double.parseDouble(  request.getParameter("costoUnitario"));
        }
        if (request.getParameter("costoTotal") != null
                && !request.getParameter("costoTotal").equals("")) {
            costoAnual = Double.parseDouble(  request.getParameter("costoTotal"));
        }
        if (request.getParameter("cantAnual") != null
                && !request.getParameter("cantAnual").equals("")) {
            cantAnual = Double.parseDouble(  request.getParameter("cantAnual"));
        }
        if (Utilerias.existeParametro("importe", request)) {
            importe = Double.parseDouble(request.getParameter("importe"));
        }
        if (Utilerias.existeParametro("disponible", request)) {
            disponible = Double.parseDouble(request.getParameter("disponible"));
        }
        if (Utilerias.existeParametro("edicion", request)) {
            edicion = Integer.parseInt(request.getParameter("edicion"));
        }
        if (Utilerias.existeParametro("identificador", request)) {
            identificador = Integer.parseInt(request.getParameter("identificador"));
        }
        if (Utilerias.existeParametro("mesPPTO", request)) {
            mesActual = Integer.parseInt(request.getParameter("mesPPTO"));
        }
        transBean = new TransferenciaBean(tipoDependencia);
        transBean.setStrServer(request.getHeader("host"));
        transBean.setStrUbicacion(getServletContext().getRealPath(""));
        transBean.resultSQLConecta(tipoDependencia);
        if (edicion == 1) {
            for (TransferenciaAccionReq transAccReqTemp : transAccionReqList) {
                if (transAccReqTemp.getIdentidicador() == identificador) {
                    transAccionReq = transAccReqTemp;
                }
            } 
            reqId = transAccionReq.getAccionReq();
            if (accion > 0) {
                codigoPPTO = transBean.getCodigoProgramaticoByRequerimiento(year, ramo, programa, transAccionReq.getTipoProy(),
                        transAccionReq.getProy(), meta, accion, partida, fuenteFin[0], fuenteFin[1], fuenteFin[2], relLaboral);
            }else{
                for (TransferenciaAccion transAccionTemp : transAccionList) {
                    if (transAccionTemp.getMovOficioAccion().getAccionId() == accion) {
                        transAccion = transAccionTemp;
                    }
                }
                for (TransferenciaMeta transMetaTemp : transMetaList) {
                    if (transMetaTemp.getMovOficioMeta().getMetaId() == meta) {
                        transMeta = transMetaTemp;
                    }
                }
                tipoGasto = transBean.getResultSQLgetTipoDeGastoByPartida(partida, year);
                prgConac = transBean.getResultProgramaConacSimple(programa, year);
                metaObj = transBean.getResultGetMetaById(ramo, programa, proy, meta, year, tipoProy);
                if(meta > 0){
                    /*transMeta.getMovOficioMeta().setRamoId(metaObj.getRamo());
                    transMeta.getMovOficioMeta().setPrgId(metaObj.getPrograma());
                    transMeta.getMovOficioMeta().setProyId(metaObj.getProyecto());
                    transMeta.getMovOficioMeta().setTipoProy(metaObj.getTipoProyecto());
                    transMeta.getMovOficioMeta().setFuncion(metaObj.getFuncion());
                    transMeta.getMovOficioMeta().setFinalidad(metaObj.getFinalidad());
                    transMeta.getMovOficioMeta().setSubfuncion(metaObj.getSubfuncion());
                    transMeta.getMovOficioMeta().setClasificacionFuncionalId(metaObj.getFuncion()
                            +"."+metaObj.getFinalidad()+"."+metaObj.getSubfuncion());*/
                }
                codigoPPTO.setRamoId(transAccionReq.getRamo());
                codigoPPTO.setDepto(transAccionReq.getDepto());
                codigoPPTO.setFinalidad(transAccionReq.getFinalidad());
                codigoPPTO.setFuncion(transAccionReq.getFuncion());
                codigoPPTO.setSubfuncion(transAccionReq.getSubfuncion());
                codigoPPTO.setProgCONAC(transAccionReq.getPrgConac());
                codigoPPTO.setPrograma(transAccionReq.getPrograma());
                codigoPPTO.setTipoProy(String.valueOf(transAccionReq.getTipoProy()));
                codigoPPTO.setProyecto(String.valueOf(transAccionReq.getProy()));
                codigoPPTO.setMeta(transAccionReq.getMeta());
                codigoPPTO.setAccion(transAccionReq.getAccion());
                codigoPPTO.setPartida(transAccionReq.getPartida());
                codigoPPTO.setTipoGasto(transAccionReq.getTipoGasto());
                codigoPPTO.setFuente(transAccionReq.getFuente());
                codigoPPTO.setFondo(transAccionReq.getFondo());
                codigoPPTO.setRecurso(transAccionReq.getRecurso());
                codigoPPTO.setMunicipio(transAccionReq.getMunicipio());
                codigoPPTO.setDelegacion(transAccionReq.getDelegacion());
                codigoPPTO.setRelLaboral(transAccionReq.getRelLaboral());
            }
            //reqId = transAccionReq.getConsecutivo() * -1;
        } else {
            if (accion < 0) {
                for (TransferenciaAccion transAccionTemp : transAccionList) {
                    if (transAccionTemp.getMovOficioAccion().getAccionId() == accion) {
                        transAccion = transAccionTemp;
                    }
                }
                for (TransferenciaMeta transMetaTemp : transMetaList) {
                    if (transMetaTemp.getMovOficioMeta().getMetaId() == meta) {
                        transMeta = transMetaTemp;
                    }
                }
                tipoGasto = transBean.getResultSQLgetTipoDeGastoByPartida(partida, year);
                prgConac = transBean.getResultProgramaConacSimple(programa, year);
                metaObj = transBean.getResultGetMetaById(ramo, programa, proy, meta, year, tipoProy);
                if(meta > 0){
                    /*transMeta.getMovOficioMeta().setRamoId(metaObj.getRamo());
                    transMeta.getMovOficioMeta().setPrgId(metaObj.getPrograma());
                    transMeta.getMovOficioMeta().setProyId(metaObj.getProyecto());
                    transMeta.getMovOficioMeta().setTipoProy(metaObj.getTipoProyecto());
                    transMeta.getMovOficioMeta().setFuncion(metaObj.getFuncion());
                    transMeta.getMovOficioMeta().setFinalidad(metaObj.getFinalidad());
                    transMeta.getMovOficioMeta().setSubfuncion(metaObj.getSubfuncion());
                    transMeta.getMovOficioMeta().setClasificacionFuncionalId(metaObj.getFuncion()
                            +"."+metaObj.getFinalidad()+"."+metaObj.getSubfuncion());*/
                }
            }
            codigoPPTO = transBean.getCodigoProgramaticoByRequerimiento(year, ramo, programa, tipoProy,
                    proy, meta, accion, partida, fuenteFin[0], fuenteFin[1], fuenteFin[2], relLaboral);

            if (transAccionReqList.size() > 0) {
                reqId = transAccionReqList.get(transAccionReqList.size() - 1).getMovOficioAccionReq().getRequerimiento();
                boolean bandTr = false;
                for(TransferenciaAccionReq tAccionReq : transAccionReqList){
                    if((reqId -1) == tAccionReq.getMovOficioAccionReq().getRequerimiento()){
                        bandTr = true;
                        break;
                    }
                }
                if(!bandTr)
                    reqId --;
                else
                    reqId = Integer.parseInt(reqId+"1");
            } else {
                reqId = Integer.parseInt(String.valueOf(transferenciaId)+"01");
                reqId = reqId*-1;
            }
        }
        if (edicion != 1) {
            transAccionReq.setConsecutivo(transferenciaId);
            if(transAccionReqList.size() > 0)
                if(transAccionReqList.size() == transAccionReqList.get(transAccionReqList.size() - 1).getIdentidicador()){
                    transAccionReq.setIdentidicador(transAccionReqList.size() + 1);
                }else{
                    transAccionReq.setIdentidicador(transAccionReqList.size());
                }            
            else
                transAccionReq.setIdentidicador(1);
        }
        if (accion < 0) {
            transAccionReq.setRamo(transAccion.getMovOficioAccion().getRamoId());
            transAccionReq.setDepto(transAccion.getMovOficioAccion().getDeptoId());
            if(meta > 0){                
                transAccionReq.setFinalidad(metaObj.getFinalidad());
                transAccionReq.setFuncion(metaObj.getFuncion());
                transAccionReq.setSubfuncion(metaObj.getSubfuncion());
                transAccionReq.setTipoProy(metaObj.getTipoProyecto());
                transAccionReq.setProy(metaObj.getProyecto());
            }else{
                transAccionReq.setFinalidad(transMeta.getMovOficioMeta().getClasificacionFuncionalId().split("\\.")[0]);
                transAccionReq.setFuncion(transMeta.getMovOficioMeta().getClasificacionFuncionalId().split("\\.")[1]);
                transAccionReq.setSubfuncion(transMeta.getMovOficioMeta().getClasificacionFuncionalId().split("\\.")[2]);
                transAccionReq.setTipoProy(transMeta.getMovOficioMeta().getTipoProy());
                transAccionReq.setProy(transMeta.getMovOficioMeta().getProyId());
            }
            transAccionReq.setPrgConac(prgConac.getProgramaConac());
            transAccionReq.setPrograma(transAccion.getMovOficioAccion().getProgramaId());
            /*transAccionReq.setTipoProy(transMeta.getMovOficioMeta().getTipoProy());
            transAccionReq.setProy(transMeta.getMovOficioMeta().getProyId());*/
            transAccionReq.setMeta(transAccion.getMovOficioAccion().getMetaId());
            transAccionReq.setAccion(transAccion.getMovOficioAccion().getAccionId());
            transAccionReq.setPartida(partida);
            transAccionReq.setTipoGasto(tipoGasto.split("-")[0].trim());
            transAccionReq.setFuente(fuenteFin[0]);
            transAccionReq.setFondo(fuenteFin[1]);
            transAccionReq.setRecurso(fuenteFin[2]);
            transAccionReq.setMunicipio(transAccion.getMovOficioAccion().getMunicipio());
            transAccionReq.setDelegacion(transAccion.getMovOficioAccion().getLocalidad());
            transAccionReq.setRelLaboral(relLaboral);
        } else {
            transAccionReq.setRamo(codigoPPTO.getRamoId());
            transAccionReq.setDepto(codigoPPTO.getDepto());
            transAccionReq.setFinalidad(codigoPPTO.getFinalidad());
            transAccionReq.setFuncion(codigoPPTO.getFuncion());
            transAccionReq.setSubfuncion(codigoPPTO.getSubfuncion());
            transAccionReq.setPrgConac(codigoPPTO.getProgCONAC());
            transAccionReq.setPrograma(codigoPPTO.getPrograma());
            transAccionReq.setTipoProy(codigoPPTO.getTipoProy());
            transAccionReq.setProy(Integer.parseInt(codigoPPTO.getProyecto()));
            transAccionReq.setMeta(codigoPPTO.getMeta());
            transAccionReq.setAccion(codigoPPTO.getAccion());
            transAccionReq.setPartida(codigoPPTO.getPartida());
            transAccionReq.setTipoGasto(codigoPPTO.getTipoGasto());
            transAccionReq.setFuente(codigoPPTO.getFuente());
            transAccionReq.setFondo(codigoPPTO.getFondo());
            transAccionReq.setRecurso(codigoPPTO.getRecurso());
            transAccionReq.setMunicipio(codigoPPTO.getMunicipio());
            transAccionReq.setDelegacion(codigoPPTO.getDelegacion());
            transAccionReq.setRelLaboral(codigoPPTO.getRelLaboral());
        }
        transAccionReq.setImporte(new Double(df.format(costoAnual)));
        /*if(transBean.getResultSQLisParaestatal() && !transBean.getResultSqlGetIsAyuntamiento())
            transAccionReq.setDisponible(transBean.getResultSQLgetDisponibleParaestatal(year,
                    monthAct, ramo, programa, tipoProy, proy, meta, accion, partida, relLaboral,
                    fuenteFin[0],fuenteFin[1],fuenteFin[2]));
        else*/
            transAccionReq.setDisponible(transBean.getDisponible(year, ramo, programa, 
                        tipoProy, proy, meta, accion, partida, fuenteFin[0], 
                        fuenteFin[1], fuenteFin[2], relLaboral, monthAct));
            transAccionReq.setDisponibleAnual(transBean.getResultSQLgetImporteAnual(year, ramo, programa, 
                        tipoProy, proy, meta, accion, partida, fuenteFin[0], 
                        fuenteFin[1], fuenteFin[2], relLaboral, 12));
        transAccionReq.setQuincePor(transBean.getQuincePorCiento(partida, ramo, year));
        transAccionReq.setAcumulado(transBean.getResultSQLgetAcumluladoMovtos(year, partida, ramo));
        transAccionReq.setMovOficioAccionReq(new MovOficiosAccionReq());
        transAccionReq.getMovOficioAccionReq().setYear(year);
        transAccionReq.getMovOficioAccionReq().setRamo(ramo);
        transAccionReq.getMovOficioAccionReq().setPrograma(programa);
        transAccionReq.getMovOficioAccionReq().setMeta(meta);
        transAccionReq.getMovOficioAccionReq().setAccion(accion);
        transAccionReq.getMovOficioAccionReq().setOficio(String.valueOf(movTransferencia.getOficio()));
        transAccionReq.getMovOficioAccionReq().setDepto(transAccionReq.getDepto());
        transAccionReq.getMovOficioAccionReq().setFuente(transAccionReq.getFuente());
        transAccionReq.getMovOficioAccionReq().setFondo(transAccionReq.getFondo());
        transAccionReq.getMovOficioAccionReq().setRecurso(transAccionReq.getRecurso());
        transAccionReq.getMovOficioAccionReq().setTipoGasto(transAccionReq.getTipoGasto());
        transAccionReq.getMovOficioAccionReq().setPartida(transAccionReq.getPartida());
        transAccionReq.getMovOficioAccionReq().setRelLaboral(transAccionReq.getRelLaboral());
        
        if(reqDescr.isEmpty()){
            transAccionReq.getMovOficioAccionReq().setReqDescr(articuloDescr);            
        }else{
            transAccionReq.getMovOficioAccionReq().setReqDescr(reqDescr);
        }
        transAccionReq.getMovOficioAccionReq().setConsec(transferenciaId);
        transAccionReq.getMovOficioAccionReq().setArticulo(articulo);
        transAccionReq.getMovOficioAccionReq().setJustificacion(justificacion);
        transAccionReq.getMovOficioAccionReq().setEne(new Double(df.format(ene)));
        transAccionReq.getMovOficioAccionReq().setFeb(new Double(df.format(feb)));
        transAccionReq.getMovOficioAccionReq().setMar(new Double(df.format(mar)));
        transAccionReq.getMovOficioAccionReq().setAbr(new Double(df.format(abr)));
        transAccionReq.getMovOficioAccionReq().setMay(new Double(df.format(may)));
        transAccionReq.getMovOficioAccionReq().setJun(new Double(df.format(jun)));
        transAccionReq.getMovOficioAccionReq().setJul(new Double(df.format(jul)));
        transAccionReq.getMovOficioAccionReq().setAgo(new Double(df.format(ago)));
        transAccionReq.getMovOficioAccionReq().setSep(new Double(df.format(sep)));
        transAccionReq.getMovOficioAccionReq().setOct(new Double(df.format(oct)));
        transAccionReq.getMovOficioAccionReq().setNov(new Double(df.format(nov)));
        transAccionReq.getMovOficioAccionReq().setDic(new Double(df.format(dic)));
        transAccionReq.getMovOficioAccionReq().setDic(new Double(df.format(dic)));
        transAccionReq.getMovOficioAccionReq().setCostoUnitario(new Double(df.format(costUnitario)));
        transAccionReq.getMovOficioAccionReq().setCostoAnual(new Double(df.format(costoAnual)));
        transAccionReq.getMovOficioAccionReq().setCantidad(new Double(df.format(cantAnual)));
        if(edicion != 1){
            transAccionReq.getMovOficioAccionReq().setRequerimiento(reqId);                   
            transAccionReq.setAccionReq(reqId);
        }else{
            transAccionReq.getMovOficioAccionReq().setRequerimiento(reqId);      
        }
        transAccionReq.setEstatusTipoOficio(transBean.getTipoOficioTransferencia(year, partidaT, ramoT, partida,ramo,transferencia.getQuincePor()));
        if(accion < 0){
            transAccionReq.setTipoMovTransf("C");
        }else{
            if (transBean.getResultSQLisCodigoRepetido(year, codigoPPTO.getRamoId(), codigoPPTO.getDepto(), codigoPPTO.getFinalidad(),
                    codigoPPTO.getFuncion(), codigoPPTO.getSubfuncion(), codigoPPTO.getProgCONAC(), codigoPPTO.getPrograma(),
                    codigoPPTO.getTipoProy(), Integer.parseInt(codigoPPTO.getProyecto()), codigoPPTO.getMeta(), codigoPPTO.getAccion(), codigoPPTO.getPartida(),
                    codigoPPTO.getTipoGasto(), codigoPPTO.getFuente(), codigoPPTO.getFondo(), codigoPPTO.getRecurso(), codigoPPTO.getMunicipio(),
                    codigoPPTO.getDelegacion(), codigoPPTO.getRelLaboral())) {
                transAccionReq.setTipoMovTransf("A");
            } else {
                transAccionReq.setTipoMovTransf("C");
            }
        }
        if(transAccionReqList.size() > 0){
            for(TransferenciaAccionReq tranfAccion : transAccionReqList){
                nuevoImporte = nuevoImporte.add(new BigDecimal(tranfAccion.getImporte()));
            }
            nuevoImporte = nuevoImporte.add(new BigDecimal(transAccionReq.getImporte()));
        }
        else
            nuevoImporte = new BigDecimal(transAccionReq.getImporte());
        nuevoImporte = nuevoImporte.setScale(2, RoundingMode.HALF_UP);
        importeViejo = new BigDecimal(importe).setScale(2, RoundingMode.HALF_UP);;
        if(nuevoImporte.compareTo(importeViejo) > 0){
            strResultado = "2|La suma del total de los importes capturados supera el importe a transferir";
        }else{
            if (edicion == 1) {
                for (TransferenciaAccionReq transReqTemp : transAccionReqList) {
                    if (transReqTemp.getIdentidicador() == identificador) {
                        transReqTemp = transAccionReq;
                    }
                }
            } else {
                transAccionReqList.add(transAccionReq);
            }
            for (Transferencia transTemp : movTransferencia.getTransferenciaList()) {
                if (transTemp.getConsec() == transferenciaId) {                
                    transTemp.setTransferenciaAccionReqList(transAccionReqList);
                }
            }               
            session.setAttribute("transferencia", movTransferencia);
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
