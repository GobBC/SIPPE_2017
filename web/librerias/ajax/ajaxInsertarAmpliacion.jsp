<%-- 
    Document   : ajaxInsertarAmpliacion
    Created on : Jan 8, 2016, 2:14:37 PM
    Author     : ugarcia
--%>
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="gob.gbc.entidades.ProgramaConac"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.MovOficiosAccionReq"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="gob.gbc.entidades.CodigoPPTO"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    AmpliacionReduccionBean ampRedBean = null;
    CodigoPPTO codigoPPTO = new CodigoPPTO();
    ProgramaConac prgConac = new ProgramaConac();
    MovimientosAmpliacionReduccion movAmpRed = new MovimientosAmpliacionReduccion();
    AmpliacionReduccionAccionReq ampRedAccionReq = new AmpliacionReduccionAccionReq();
    AmpliacionReduccionAccion ampRedAccion = new AmpliacionReduccionAccion();
    AmpliacionReduccionMeta ampRedMeta = new AmpliacionReduccionMeta();
    Meta metaObj = new Meta();
    List<AmpliacionReduccionAccionReq> ampRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
    List<AmpliacionReduccionMeta> ampRedMetaList = new ArrayList<AmpliacionReduccionMeta>();
    List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
    DecimalFormat df;
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    String programa = new String();
    String partida = new String();
    String fuente = new String();
    String fuenteFin[] = new String[3];
    String relLaboral = new String();
    String reqDescr = new String();
    String tipoProy = new String();
    String justificacion = new String();
    String tipoGasto = new String();
    String fecha = new String();
    int articulo = 0;
    int reqId = 0;
    int monthAct = 0;
    int proy = 0;
    int meta = 0;
    int accion = 0;
    int year = 0;
    int edicion = 0;
    int identificador = 0;
    int maxConsec = 0;
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
         accionIdNegativo = Integer.parseInt((String)session.getAttribute("accionReqIdNegativo"));
         }*/
        if (Utilerias.existeParametro("fecha", request)) {
            fecha =  request.getParameter("fecha");
            monthAct = Integer.parseInt(fecha.split("\\/")[1]);
        }
        if (session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "") {
            movAmpRed = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampRedAccionReqList = movAmpRed.getAmpReducAccionReqList();
            ampRedMetaList = movAmpRed.getAmpReducMetaList();
            ampRedAccionList = movAmpRed.getAmpReducAccionList();
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramo") != null
                && !request.getParameter("ramo").equals("")) {
            ramo =  request.getParameter("ramo");
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
            partida = request.getParameter("partida");
        }
        if (request.getParameter("relLaboral") != null
                && !request.getParameter("relLaboral").equals("")) {
            relLaboral = request.getParameter("relLaboral");
        }
        if (request.getParameter("reqDescr") != null
                && !request.getParameter("reqDescr").equals("")) {
            reqDescr =  request.getParameter("reqDescr");
        }
        if (request.getParameter("justificacion") != null
                && !request.getParameter("justificacion").equals("")) {
            justificacion = request.getParameter("justificacion");
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
            mar = Double.parseDouble(request.getParameter("mar"));
        }
        if (request.getParameter("abr") != null
                && !request.getParameter("abr").equals("")) {
            abr = Double.parseDouble( request.getParameter("abr"));
        }
        if (request.getParameter("may") != null
                && !request.getParameter("may").equals("")) {
            may = Double.parseDouble( request.getParameter("may"));
        }
        if (request.getParameter("jun") != null
                && !request.getParameter("jun").equals("")) {
            jun = Double.parseDouble( request.getParameter("jun"));
        }
        if (request.getParameter("jul") != null
                && !request.getParameter("jul").equals("")) {
            jul = Double.parseDouble( request.getParameter("jul"));
        }
        if (request.getParameter("ago") != null
                && !request.getParameter("ago").equals("")) {
            ago = Double.parseDouble( request.getParameter("ago"));
        }
        if (request.getParameter("sep") != null
                && !request.getParameter("sep").equals("")) {
            sep = Double.parseDouble( request.getParameter("sep"));
        }
        if (request.getParameter("oct") != null
                && !request.getParameter("oct").equals("")) {
            oct = Double.parseDouble( request.getParameter("oct"));
        }
        if (request.getParameter("nov") != null
                && !request.getParameter("nov").equals("")) {
            nov = Double.parseDouble( request.getParameter("nov"));
        }
        if (request.getParameter("dic") != null
                && !request.getParameter("dic").equals("")) {
            dic = Double.parseDouble( request.getParameter("dic"));
        }
        if (request.getParameter("costoUnitario") != null
                && !request.getParameter("costoUnitario").equals("")) {
            costUnitario = Double.parseDouble( request.getParameter("costoUnitario"));
        }
        if (request.getParameter("costoTotal") != null
                && !request.getParameter("costoTotal").equals("")) {
            costoAnual = Double.parseDouble( request.getParameter("costoTotal"));
        }
        if (request.getParameter("cantAnual") != null
                && !request.getParameter("cantAnual").equals("")) {
            cantAnual = Double.parseDouble( request.getParameter("cantAnual"));
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
        if (Utilerias.existeParametro("articulo", request)) {
            articulo = Integer.parseInt((String)request.getParameter("articulo"));     
            if(articulo == -1){
                articulo = 0;
            }
        }
        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer(request.getHeader("host"));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRedBean.resultSQLConecta(tipoDependencia);
        if(articulo > 0){
            reqDescr = ampRedBean.getResultSQLGetArticuloDescr(year, partida, articulo);
        }
        if (edicion == 1) {
            for (AmpliacionReduccionAccionReq ampReqTemp : ampRedAccionReqList) {
                if (ampReqTemp.getIdentidicador() == identificador) {
                    ampRedAccionReq = ampReqTemp;
                }
            }
            codigoPPTO = ampRedBean.getCodigoProgramaticoByRequerimiento(year, ramo, programa, ampRedAccionReq.getTipoProy(),
                    ampRedAccionReq.getProy(), meta, accion, partida, fuenteFin[0], fuenteFin[1], fuenteFin[2], relLaboral);
            reqId = ampRedAccionReq.getConsecutivo() * -1;
            if (accion <= 0) {
                for (AmpliacionReduccionAccion ampRedAccionTemp : ampRedAccionList) {
                    if (ampRedAccionTemp.getMovOficioAccion().getAccionId() == accion) {
                        ampRedAccion = ampRedAccionTemp;
                    }
                }
                for (AmpliacionReduccionMeta ampRedMetaTemp : ampRedMetaList) {
                    if (ampRedMetaTemp.getMovOficioMeta().getMetaId() == meta) {
                        ampRedMeta = ampRedMetaTemp;
                    }
                }
                tipoGasto = ampRedBean.getResultSQLgetTipoDeGastoByPartida(partida, year);
                prgConac = ampRedBean.getResultProgramaConacSimple(programa, year);
                metaObj = ampRedBean.getResultGetMetaById(ramo, programa, ampRedAccionReq.getProy(), meta, year, ampRedAccionReq.getTipoProy());
                if(meta > 0){
                    /*ampRedMeta.setMovOficioMeta(new MovimientoOficioMeta());
                    ampRedMeta.getMovOficioMeta().setRamoId(metaObj.getRamo());
                    ampRedMeta.getMovOficioMeta().setPrgId(metaObj.getPrograma());
                    ampRedMeta.getMovOficioMeta().setProyId(metaObj.getProyecto());
                    ampRedMeta.getMovOficioMeta().setTipoProy(metaObj.getTipoProyecto());
                    ampRedMeta.getMovOficioMeta().setFuncion(metaObj.getFuncion());
                    ampRedMeta.getMovOficioMeta().setFinalidad(metaObj.getFinalidad());
                    ampRedMeta.getMovOficioMeta().setSubfuncion(metaObj.getSubfuncion());
                    ampRedMeta.getMovOficioMeta().setClasificacionFuncionalId(metaObj.getFuncion()
                            +"."+metaObj.getFinalidad()+"."+metaObj.getSubfuncion());*/
                }
            }
        } else {
            if (accion < 0) { // estaba <= 28-04-2016
                for (AmpliacionReduccionAccion ampRedAccionTemp : ampRedAccionList) {
                    if (ampRedAccionTemp.getMovOficioAccion().getAccionId() == accion) {
                        ampRedAccion = ampRedAccionTemp;
                    }
                }
                for (AmpliacionReduccionMeta ampRedMetaTemp : ampRedMetaList) {
                    if (ampRedMetaTemp.getMovOficioMeta().getMetaId() == meta) {
                        ampRedMeta = ampRedMetaTemp;
                    }
                }
                tipoGasto = ampRedBean.getResultSQLgetTipoDeGastoByPartida(partida, year);
                prgConac = ampRedBean.getResultProgramaConacSimple(programa, year);
                metaObj = ampRedBean.getResultGetMetaById(ramo, programa, proy, meta, year, tipoProy);
                if(meta > 0){
                    /*ampRedMeta.setMovOficioMeta(new MovimientoOficioMeta());
                    ampRedMeta.getMovOficioMeta().setRamoId(metaObj.getRamo());
                    ampRedMeta.getMovOficioMeta().setPrgId(metaObj.getPrograma());
                    ampRedMeta.getMovOficioMeta().setProyId(metaObj.getProyecto());
                    ampRedMeta.getMovOficioMeta().setTipoProy(metaObj.getTipoProyecto());
                    ampRedMeta.getMovOficioMeta().setFuncion(metaObj.getFuncion());
                    ampRedMeta.getMovOficioMeta().setFinalidad(metaObj.getFinalidad());
                    ampRedMeta.getMovOficioMeta().setSubfuncion(metaObj.getSubfuncion());
                    ampRedMeta.getMovOficioMeta().setClasificacionFuncionalId(metaObj.getFuncion()
                            +"."+metaObj.getFinalidad()+"."+metaObj.getSubfuncion());*/
                }
            }
            codigoPPTO = ampRedBean.getCodigoProgramaticoByRequerimiento(year, ramo, programa, tipoProy,
                    proy, meta, accion, partida, fuenteFin[0], fuenteFin[1], fuenteFin[2], relLaboral);

            if (ampRedAccionReqList.size() > 0) {
                for(AmpliacionReduccionAccionReq ampTemp : ampRedAccionReqList){
                    if(maxConsec == 0){
                        maxConsec = ampTemp.getConsecutivo();
                    } else if(ampTemp.getConsecutivo() > maxConsec){
                        maxConsec = ampTemp.getConsecutivo();
                    }
                }
                reqId = maxConsec;
                //reqId = ampRedAccionReqList.get(ampRedAccionReqList.size() - 1).getConsecutivo();
                reqId = (reqId * -1) - 1;
            } else {
                reqId--;
            }
        }
        if (edicion != 1) {
            ampRedAccionReq.setConsecutivo(reqId * -1);
            ampRedAccionReq.setIdentidicador(ampRedAccionReqList.size());
        }
        if (accion < 0) { // estaba <= 28-04-2016
            ampRedAccionReq.setRamo(ampRedAccion.getMovOficioAccion().getRamoId());
            ampRedAccionReq.setDepto(ampRedAccion.getMovOficioAccion().getDeptoId());
            if(meta > 0){                
                ampRedAccionReq.setFinalidad(metaObj.getFinalidad());
                ampRedAccionReq.setFuncion(metaObj.getFuncion());
                ampRedAccionReq.setSubfuncion(metaObj.getSubfuncion());
                ampRedAccionReq.setTipoProy(metaObj.getTipoProyecto());
                ampRedAccionReq.setProy(metaObj.getProyecto());
            }else{
                ampRedAccionReq.setFinalidad(ampRedMeta.getMovOficioMeta().getClasificacionFuncionalId().split("\\.")[0]);
                ampRedAccionReq.setFuncion(ampRedMeta.getMovOficioMeta().getClasificacionFuncionalId().split("\\.")[1]);
                ampRedAccionReq.setSubfuncion(ampRedMeta.getMovOficioMeta().getClasificacionFuncionalId().split("\\.")[2]);
                ampRedAccionReq.setTipoProy(ampRedMeta.getMovOficioMeta().getTipoProy());
                ampRedAccionReq.setProy(ampRedMeta.getMovOficioMeta().getProyId());
            }
            ampRedAccionReq.setPrgConac(prgConac.getProgramaConac());
            ampRedAccionReq.setPrograma(ampRedAccion.getMovOficioAccion().getProgramaId());
            ampRedAccionReq.setMeta(ampRedAccion.getMovOficioAccion().getMetaId());
            ampRedAccionReq.setAccion(ampRedAccion.getMovOficioAccion().getAccionId());
            ampRedAccionReq.setPartida(partida);
            ampRedAccionReq.setTipoGasto(tipoGasto.split("-")[0].trim());
            ampRedAccionReq.setFuente(fuenteFin[0]);
            ampRedAccionReq.setFondo(fuenteFin[1]);
            ampRedAccionReq.setRecurso(fuenteFin[2]);
            ampRedAccionReq.setMunicipio(ampRedAccion.getMovOficioAccion().getMunicipio());
            ampRedAccionReq.setDelegacion(ampRedAccion.getMovOficioAccion().getLocalidad());
            ampRedAccionReq.setRelLaboral(relLaboral);
            ampRedAccionReq.setConsiderar("S");
        } else {
            ampRedAccionReq.setRamo(codigoPPTO.getRamoId());
            ampRedAccionReq.setDepto(codigoPPTO.getDepto());
            ampRedAccionReq.setFinalidad(codigoPPTO.getFinalidad());
            ampRedAccionReq.setFuncion(codigoPPTO.getFuncion());
            ampRedAccionReq.setSubfuncion(codigoPPTO.getSubfuncion());
            ampRedAccionReq.setPrgConac(codigoPPTO.getProgCONAC());
            ampRedAccionReq.setPrograma(codigoPPTO.getPrograma());
            ampRedAccionReq.setTipoProy(codigoPPTO.getTipoProy());
            ampRedAccionReq.setProy(Integer.parseInt(codigoPPTO.getProyecto()));
            ampRedAccionReq.setMeta(codigoPPTO.getMeta());
            ampRedAccionReq.setAccion(codigoPPTO.getAccion());
            ampRedAccionReq.setPartida(codigoPPTO.getPartida());
            ampRedAccionReq.setTipoGasto(codigoPPTO.getTipoGasto());
            ampRedAccionReq.setFuente(codigoPPTO.getFuente());
            ampRedAccionReq.setFondo(codigoPPTO.getFondo());
            ampRedAccionReq.setRecurso(codigoPPTO.getRecurso());
            ampRedAccionReq.setMunicipio(codigoPPTO.getMunicipio());
            ampRedAccionReq.setDelegacion(codigoPPTO.getDelegacion());
            ampRedAccionReq.setRelLaboral(codigoPPTO.getRelLaboral());            
            ampRedAccionReq.setConsiderar("S");
        }
        ampRedAccionReq.setImporte(importe);
        /*if(ampRedBean.getResultSQLisParaestatal() && !ampRedBean.getResultSqlGetIsAyuntamiento())
            ampRedAccionReq.setDisponible(ampRedBean.getResultSQLgetDisponibleParaestatal(year,
                    monthAct, ramo, programa, tipoProy, proy, meta, accion, partida, relLaboral,
                    fuenteFin[0],fuenteFin[1],fuenteFin[2]));
        else*/
            ampRedAccionReq.setDisponible(ampRedBean.getDisponible(year, ramo, programa, 
                        tipoProy, proy, meta, accion, partida, fuenteFin[0], 
                        fuenteFin[1], fuenteFin[2], relLaboral, monthAct));
            ampRedAccionReq.setDisponibleAnual(ampRedBean.getResultSQLgetImporteAnual(year, ramo, programa, 
                        tipoProy, proy, meta, accion, partida, fuenteFin[0], 
                        fuenteFin[1], fuenteFin[2], relLaboral, 12));
        ampRedAccionReq.setQuincePor(ampRedBean.getQuincePorCiento(partida, ramo, year));
        ampRedAccionReq.setAcumulado(ampRedBean.getResultSQLgetAcumluladoMovtos(year, partida, ramo));
        ampRedAccionReq.setMovOficioAccionReq(new MovOficiosAccionReq());
        ampRedAccionReq.getMovOficioAccionReq().setYear(year);
        ampRedAccionReq.getMovOficioAccionReq().setRamo(ramo);
        ampRedAccionReq.getMovOficioAccionReq().setPrograma(programa);
        ampRedAccionReq.getMovOficioAccionReq().setMeta(meta);
        ampRedAccionReq.getMovOficioAccionReq().setAccion(accion);
        ampRedAccionReq.getMovOficioAccionReq().setReqDescr(reqDescr);
        ampRedAccionReq.getMovOficioAccionReq().setArticulo(String.valueOf(articulo));
        ampRedAccionReq.getMovOficioAccionReq().setDepto(ampRedAccionReq.getDepto());
        ampRedAccionReq.getMovOficioAccionReq().setFuente(ampRedAccionReq.getFuente());
        ampRedAccionReq.getMovOficioAccionReq().setFondo(ampRedAccionReq.getFondo());
        ampRedAccionReq.getMovOficioAccionReq().setRecurso(ampRedAccionReq.getRecurso());
        ampRedAccionReq.getMovOficioAccionReq().setTipoGasto(ampRedAccionReq.getTipoGasto());
        ampRedAccionReq.getMovOficioAccionReq().setPartida(ampRedAccionReq.getPartida());
        ampRedAccionReq.getMovOficioAccionReq().setRelLaboral(ampRedAccionReq.getRelLaboral());
        ampRedAccionReq.getMovOficioAccionReq().setJustificacion(justificacion);
        ampRedAccionReq.getMovOficioAccionReq().setEne(new Double(df.format(ene)));
        ampRedAccionReq.getMovOficioAccionReq().setFeb(new Double(df.format(feb)));
        ampRedAccionReq.getMovOficioAccionReq().setMar(new Double(df.format(mar)));
        ampRedAccionReq.getMovOficioAccionReq().setAbr(new Double(df.format(abr)));
        ampRedAccionReq.getMovOficioAccionReq().setMay(new Double(df.format(may)));
        ampRedAccionReq.getMovOficioAccionReq().setJun(new Double(df.format(jun)));
        ampRedAccionReq.getMovOficioAccionReq().setJul(new Double(df.format(jul)));
        ampRedAccionReq.getMovOficioAccionReq().setAgo(new Double(df.format(ago)));
        ampRedAccionReq.getMovOficioAccionReq().setSep(new Double(df.format(sep)));
        ampRedAccionReq.getMovOficioAccionReq().setOct(new Double(df.format(oct)));
        ampRedAccionReq.getMovOficioAccionReq().setNov(new Double(df.format(nov)));
        ampRedAccionReq.getMovOficioAccionReq().setDic(new Double(df.format(dic)));
        ampRedAccionReq.getMovOficioAccionReq().setDic(new Double(df.format(dic)));
        ampRedAccionReq.getMovOficioAccionReq().setCostoUnitario(new Double(df.format(costUnitario)));
        ampRedAccionReq.getMovOficioAccionReq().setCostoAnual(new Double(df.format(costoAnual)));
        ampRedAccionReq.getMovOficioAccionReq().setCantidad(new Double(df.format(cantAnual)));
        ampRedAccionReq.getMovOficioAccionReq().setConsiderar("S");
        ampRedAccionReq.setRequerimiento(reqId);
        ampRedAccionReq.getMovOficioAccionReq().setRequerimiento(reqId);
        if(!ampRedBean.getResultSQLisParaestatal() || ampRedBean.getResultSqlGetIsAyuntamiento()){
            if (ampRedBean.getTipoOficioByPartida(year, partida)) {
                ampRedAccionReq.setEstatusTipoOficio("V");
            } else {
                ampRedAccionReq.setEstatusTipoOficio("A");
            }
        }else{
            ampRedAccionReq.setEstatusTipoOficio("A");
        }
        if(accion < 0){
            /*if (ampRedBean.getResultSQLisCodigoRepetido(year, ampRedAccion.getMovOficioAccion().getRamoId(), ampRedAccion.getMovOficioAccion().getDeptoId(),
                    ampRedMeta.getMovOficioMeta().getFinalidad(),ampRedMeta.getMovOficioMeta().getFuncion(), ampRedMeta.getMovOficioMeta().getSubfuncion(), prgConac.getProgramaConac(),
                    ampRedAccion.getMovOficioAccion().getProgramaId(), ampRedMeta.getMovOficioMeta().getTipoProy(), ampRedMeta.getMovOficioMeta().getProyId(), 
                    ampRedAccion.getMovOficioAccion().getMetaId(), ampRedAccion.getMovOficioAccion().getAccionId(), partida,
                    tipoGasto.split("-")[0].trim(), fuenteFin[0], fuenteFin[1], fuenteFin[2], ampRedAccion.getMovOficioAccion().getMunicipio(),
                    ampRedAccion.getMovOficioAccion().getLocalidad(), relLaboral)) {
                ampRedAccionReq.setTipoMovAmpRed("A");
            } else {*/
                ampRedAccionReq.setTipoMovAmpRed("C");
            //}
        }else{
            if (ampRedBean.getResultSQLisCodigoRepetido(year, codigoPPTO.getRamoId(), codigoPPTO.getDepto(), codigoPPTO.getFinalidad(),
                    codigoPPTO.getFuncion(), codigoPPTO.getSubfuncion(), codigoPPTO.getProgCONAC(), codigoPPTO.getPrograma(),
                    codigoPPTO.getTipoProy(), Integer.parseInt(codigoPPTO.getProyecto()), codigoPPTO.getMeta(), codigoPPTO.getAccion(), codigoPPTO.getPartida(),
                    codigoPPTO.getTipoGasto(), codigoPPTO.getFuente(), codigoPPTO.getFondo(), codigoPPTO.getRecurso(), codigoPPTO.getMunicipio(),
                    codigoPPTO.getDelegacion(), codigoPPTO.getRelLaboral())) {
                ampRedAccionReq.setTipoMovAmpRed("A");
            } else {
                ampRedAccionReq.setTipoMovAmpRed("C");
            }
        }
        if (edicion == 1) {
            for (AmpliacionReduccionAccionReq ampReqTemp : ampRedAccionReqList) {
                if (ampReqTemp.getIdentidicador() == identificador) {
                    ampReqTemp = ampRedAccionReq;
                }
            }
        } else {
            ampRedAccionReqList.add(ampRedAccionReq);
        }
        movAmpRed.setAmpReducAccionReqList(ampRedAccionReqList);
        session.setAttribute("ampliacionReduccion", movAmpRed);
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
