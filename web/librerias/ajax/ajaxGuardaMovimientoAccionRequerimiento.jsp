<%-- 
    Document   : ajaxGuardaMovimientoAccionRequerimiento
    Created on : Dec 15, 2015, 8:39:00 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="gob.gbc.entidades.MovOficiosAccionReq"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;
    MovOficiosAccionReq requerimiento = new MovOficiosAccionReq();
    MovimientosRecalendarizacion movRecal = new MovimientosRecalendarizacion();
    List<RecalendarizacionAccionReq> movOficioList  = new ArrayList<RecalendarizacionAccionReq>();
    RecalendarizacionAccionReq recalenTemp = new RecalendarizacionAccionReq();
    DecimalFormat df;
    String strResultado = new String();
    String ramo = new String();
    String programa = new String();
    String partida = new String();
    String fuente = new String();
    String fuenteFin[] = new String[3];
    String relLaboral = new String();
    String tipoGasto = new String();
    String articulo = new String();
    String depto = new String();
    String descr = new String();
    String infoReq[] = new String[19];
    String tipoDependencia = new String();
    String estatus = new String();
    int identificador = 0;
    int meta = 0;
    int accion = 0;
    int requerimientoId = 0;
    int year =0;
    int folio = 0;
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
    double costUnitaria = 0.0;
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(Utilerias.existeParametro("ramo", request)) {
            ramo = request.getParameter("ramo");
        }
        if(Utilerias.existeParametro("programa",request)) {
            programa = request.getParameter("programa");
        }
        if(Utilerias.existeParametro("meta",request)) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        if(Utilerias.existeParametro("accion",request)) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        if(Utilerias.existeParametro("partida",request)) {
            partida = request.getParameter("partida");
        }
        if(Utilerias.existeParametro("relLaboral",request)) {
            relLaboral = request.getParameter("relLaboral");
        }
        if(Utilerias.existeParametro("fuente",request)) {
            fuente = request.getParameter("fuente");
            fuenteFin = fuente.split("\\.");
        }
        if(Utilerias.existeParametro("requerimiento",request)) {
            requerimientoId = Integer.parseInt(request.getParameter("requerimiento"));
        }
        if(Utilerias.existeParametro("descripcion",request)) {
            descr = request.getParameter("descripcion");
        }
        if(Utilerias.existeParametro("tipoGasto",request)) {
            tipoGasto = request.getParameter("tipoGasto");
        }
        if(Utilerias.existeParametro("articulo",request)) {
            articulo = request.getParameter("articulo");
        }
        if(Utilerias.existeParametro("estatus",request)) {
            estatus = request.getParameter("estatus");
        }
        if(Utilerias.existeParametro("depto",request)) {
            depto = request.getParameter("depto");
        }
        if(Utilerias.existeParametro("enero",request)) {
            ene = Double.parseDouble(request.getParameter("enero"));
        }
        if(Utilerias.existeParametro("febrero",request)) {
            feb = Double.parseDouble(request.getParameter("febrero"));
        }
        if(Utilerias.existeParametro("marzo",request)) {
            mar = Double.parseDouble(request.getParameter("marzo"));
        }
        if(Utilerias.existeParametro("abril",request)) {
            abr = Double.parseDouble(request.getParameter("abril"));
        }
        if(Utilerias.existeParametro("mayo",request)) {
            may = Double.parseDouble(request.getParameter("mayo"));
        }
        if(Utilerias.existeParametro("junio",request)) {
            jun = Double.parseDouble(request.getParameter("junio"));
        }
        if(Utilerias.existeParametro("julio",request)) {
            jul = Double.parseDouble(request.getParameter("julio"));
        }
        if(Utilerias.existeParametro("agosto",request)) {
            ago = Double.parseDouble(request.getParameter("agosto"));
        }
        if(Utilerias.existeParametro("sept",request)) {
            sep = Double.parseDouble(request.getParameter("sept"));
        }
        if(Utilerias.existeParametro("octubre",request)) {
            oct = Double.parseDouble(request.getParameter("octubre"));
        }
        if(Utilerias.existeParametro("noviembre",request)) {
            nov = Double.parseDouble(request.getParameter("noviembre"));
        }
        if(Utilerias.existeParametro("diciembre",request)) {
            dic = Double.parseDouble(request.getParameter("diciembre"));
        }
        if(Utilerias.existeParametro("costoUnitario",request)) {
            costUnitaria = Double.parseDouble(request.getParameter("costoUnitario"));
        }
        if(Utilerias.existeParametro("identificador",request)) {
            identificador = Integer.parseInt(request.getParameter("identificador"));
        }
        if(Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        if(folio != 0){
            infoReq = recalBean.getRequerimientoByIdUsuario(year, ramo, programa, meta, accion, requerimientoId);
            ramo = infoReq[0];
            programa = infoReq[2];
            meta = Integer.parseInt(infoReq[7]);
            accion = Integer.parseInt(infoReq[9]);
            requerimientoId = Integer.parseInt(infoReq[11]);
            partida = infoReq[13];
            relLaboral = infoReq[15];
            fuente = infoReq[17];
            fuenteFin = fuente.split("\\.");
        }
        if (session.getAttribute("recalendarizacion") != null && session.getAttribute("recalendarizacion") != "") {
            movRecal = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
            movOficioList = movRecal.getMovOficiosAccionReq();
            if (identificador != -1) {
                for (RecalendarizacionAccionReq movtoAccionReqList : movOficioList) {
                    if (movtoAccionReqList.getIdentificador() == identificador) {
                        requerimiento = movtoAccionReqList.getMovAccionReq();
                    }
                }
            }
        }
        df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        requerimiento.setYear(year);
        requerimiento.setRamo(ramo);
        requerimiento.setPrograma(programa);
        requerimiento.setMeta(meta);
        requerimiento.setAccion(accion);
        requerimiento.setFuente(fuenteFin[0]);
        requerimiento.setFondo(fuenteFin[1]);
        requerimiento.setRecurso(fuenteFin[2]);
        requerimiento.setPartida(partida);
        requerimiento.setRequerimiento(requerimientoId);
        requerimiento.setReqDescr(descr);
        requerimiento.setTipoGasto(tipoGasto);
        requerimiento.setArticulo(articulo);
        requerimiento.setDepto(depto);
        requerimiento.setDeptoDescr(recalBean.getResultSQLgetDeptoDescripcion(year, ramo, depto));
        if(relLaboral.isEmpty() || relLaboral.equals("-1"))
            requerimiento.setRelLaboral("0");
        else
            requerimiento.setRelLaboral(relLaboral);
        requerimiento.setEne(new Double(df.format(ene)));
        requerimiento.setFeb(new Double(df.format(feb)));
        requerimiento.setMar(new Double(df.format(mar)));
        requerimiento.setAbr(new Double(df.format(abr)));
        requerimiento.setMay(new Double(df.format(may)));
        requerimiento.setJun(new Double(df.format(jun)));
        requerimiento.setJul(new Double(df.format(jul)));
        requerimiento.setAgo(new Double(df.format(ago)));
        requerimiento.setSep(new Double(df.format(sep)));
        requerimiento.setOct(new Double(df.format(oct)));
        requerimiento.setNov(new Double(df.format(nov)));
        requerimiento.setDic(new Double(df.format(dic)));
        requerimiento.setCostoUnitario(costUnitaria);
        requerimiento.setCostoAnual(Utileria.getCostoAnual(requerimiento));
        requerimiento.setCantidad(Utileria.getCantidad(requerimiento));
        requerimiento.setConsiderar("S");
        requerimiento.setRecalendarizado(recalBean.getImporteRecalendarizacion(estatus,requerimiento,folio));
        if(identificador == -1){
            recalenTemp.setIdentificador(movOficioList.size());
            recalenTemp.setMovAccionReq(requerimiento);
            movOficioList.add(recalenTemp);
        }else{
            for (RecalendarizacionAccionReq movtoAccionReqList : movOficioList) {
                if (movtoAccionReqList.getIdentificador() == identificador) {
                    movtoAccionReqList.setMovAccionReq(requerimiento);
                }
            }
        }
        movRecal.setMovOficiosAccionReq(movOficioList);
        session.setAttribute("recalendarizacion", movRecal);
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        out.print(strResultado);
    }
%>