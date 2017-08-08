<%-- 
    Document   : ajaxEnviarRecalendarizacion
    Created on : Dec 16, 2015, 2:51:47 PM
    Author     : ugarcia
--%>

<%@page import="java.sql.Date"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
    RecalendarizacionBean recalBean = null;    
    MovimientosRecalendarizacion recalendarizacion = new MovimientosRecalendarizacion();
    List<RecalendarizacionAccion> movtoAccionesList = new ArrayList<RecalendarizacionAccion>();
    List<RecalendarizacionMeta> movtoMetaList = new ArrayList<RecalendarizacionMeta>();
    
    String tipoDependencia = new String();
    String strResultado = new String();
    String estatus = new String();
    String mensaje = new String();
    String codigoAutorizado[] = new String[5];
    String valAutorizado = new String();
    
    int year = 0;
    int folio = 0;
    int folioAutorizado = 0;
    
    boolean resultado = false;
    
    try{
        
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(Utilerias.existeParametro("estatus", request)){
            estatus = request.getParameter("estatus");
        }
        if(Utilerias.existeParametro("folio", request)){
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        
        if (session.getAttribute("recalendarizacion") != null && session.getAttribute("recalendarizacion") != "") {
            recalendarizacion = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
            movtoAccionesList = recalendarizacion.getMovAccionEstimacionList();
            movtoMetaList = recalendarizacion.getMovEstimacionList();
            if(recalendarizacion.getFechaElab() == null)
                recalendarizacion.setFechaElab(new Date(System.currentTimeMillis()));
        }
        
        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        
        for(RecalendarizacionMeta meta: movtoMetaList){
            valAutorizado = recalBean.getSQLValidaModificacionEstimacionAutorizado(folio,
                    meta.getMovEstimacionList().get(0).getRamo(),
                    meta.getMovEstimacionList().get(0).getMeta());
            if(valAutorizado.equals("P")){
                folioAutorizado = recalBean.getResultSQLValidaOficioAutorizadoMeta(year,
                        meta.getMovEstimacionList().get(0).getRamo(),
                        meta.getMovEstimacionList().get(0).getMeta(),
                        recalendarizacion.getFechaElab());
                if(folioAutorizado > 0){
                    if(recalBean.getResultSQLUpdateModificacionEstimacion("N",
                            folio, meta.getMovEstimacionList().get(0).getRamo(),
                            meta.getMovEstimacionList().get(0).getMeta())){
                        meta.setValAutorizado("N");
                        codigoAutorizado[0] = meta.getMovEstimacionList().get(0).getRamo();
                        codigoAutorizado[1] = meta.getMovEstimacionList().get(0).getPrograma();
                        codigoAutorizado[2] = String.valueOf(meta.getMovEstimacionList().get(0).getMeta());
                        codigoAutorizado[3] = new String();
                        recalBean.transaccionCommit();
                    }else{
                        recalBean.transaccionRollback();
                    }
                    break;
                }
            }else if(valAutorizado.equals("N")){
                folioAutorizado = recalBean.getResultSQLValidaOficioAutorizadoMeta(year,
                        meta.getMovEstimacionList().get(0).getRamo(),
                        meta.getMovEstimacionList().get(0).getMeta(),
                        recalendarizacion.getFechaElab());
                if(folioAutorizado > 0){
                    codigoAutorizado[0] = meta.getMovEstimacionList().get(0).getRamo();
                    codigoAutorizado[1] = meta.getMovEstimacionList().get(0).getPrograma();
                    codigoAutorizado[2] = String.valueOf(meta.getMovEstimacionList().get(0).getMeta());
                    codigoAutorizado[3] = new String();
                    break;
                }
            }
        }    
        if(folioAutorizado == 0){
            for(RecalendarizacionAccion accion: movtoAccionesList){
                valAutorizado = recalBean.getSQLValidaModificacionAccionEstimacionAutorizado(folio,
                    accion.getMovEstimacionList().get(0).getRamo(),
                    accion.getMovEstimacionList().get(0).getMeta(),
                    accion.getMovEstimacionList().get(0).getAccion());
                if(valAutorizado.equals("P")){
                    folioAutorizado = recalBean.getResultSQLValidaOficioAutorizadoAccion(year,
                            accion.getMovEstimacionList().get(0).getRamo(),
                            accion.getMovEstimacionList().get(0).getMeta(),
                            accion.getMovEstimacionList().get(0).getAccion(),
                            recalendarizacion.getFechaElab());
                    if(folioAutorizado > 0){
                        if(recalBean.getResultSQLUpdateModificacionAccionEstimacion("N",
                            folio, accion.getMovEstimacionList().get(0).getRamo(),
                            accion.getMovEstimacionList().get(0).getMeta(),
                            accion.getMovEstimacionList().get(0).getAccion())){
                            accion.setValAutorizacion("N");
                            codigoAutorizado[0] = accion.getMovEstimacionList().get(0).getRamo();
                            codigoAutorizado[1] = accion.getMovEstimacionList().get(0).getPrograma();
                            codigoAutorizado[2] = String.valueOf(accion.getMovEstimacionList().get(0).getMeta());
                        codigoAutorizado[3] =  String.valueOf(accion.getMovEstimacionList().get(0).getAccion());
                            recalBean.transaccionCommit();
                        }else{
                            recalBean.transaccionRollback();
                        }
                            break;
                    }
                }else if(valAutorizado.equals("N")){
                    folioAutorizado = recalBean.getResultSQLValidaOficioAutorizadoAccion(year,
                            accion.getMovEstimacionList().get(0).getRamo(),
                            accion.getMovEstimacionList().get(0).getMeta(),
                            accion.getMovEstimacionList().get(0).getAccion(),
                            recalendarizacion.getFechaElab());
                    if(folioAutorizado > 0){
                        codigoAutorizado[0] = accion.getMovEstimacionList().get(0).getRamo();
                        codigoAutorizado[1] = accion.getMovEstimacionList().get(0).getPrograma();
                        codigoAutorizado[2] = String.valueOf(accion.getMovEstimacionList().get(0).getMeta());
                        codigoAutorizado[3] =  String.valueOf(accion.getMovEstimacionList().get(0).getAccion());
                        break;
                    }
                }
            }   
        }
        if(folioAutorizado == 0){
            mensaje = recalBean.actualizaMovOficio(folio, "C", tipoDependencia, estatus,recalendarizacion.getMovOficiosAccionReq());
            if(mensaje.isEmpty()){
                resultado = true;
            }
            if(mensaje.isEmpty() && estatus.equals("R")){
                if(recalBean.getResultSQLcountBitmovtosByFolio(folio))
                    resultado = recalBean.getResultDeleteBitMovtoByOficio(folio);
            }
            if(resultado){
                recalBean.transaccionCommit();
            }
            else{
                strResultado = "-1";
                recalBean.transaccionRollback();
            }
        }else{
            strResultado += "Existe movimiento "+folioAutorizado+ " Autorizado que realizó modificación de Impacto Programático posterior a su captura Original. \n";
            strResultado += "Código Ramo "+codigoAutorizado[0]+" Programa "+ codigoAutorizado[1] +" Meta " + codigoAutorizado[2];
            if(!codigoAutorizado[3].isEmpty())
                strResultado += " Acción " + codigoAutorizado[3] + "\n";
            else
                strResultado += "\n";
            strResultado += "Deberá modificar su captura Original de Impacto sobre el código anterior.";
            session.setAttribute("recalendarizacion", recalendarizacion);
        }
    }catch (Exception ex) {
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
