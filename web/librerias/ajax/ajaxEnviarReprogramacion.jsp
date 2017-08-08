<%-- 
    Document   : ajaxEnviarReprogramacion
    Created on : Dec 23, 2015, 3:10:53 PM
    Author     : ugarcia
--%>

<%@page import="java.sql.Date"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.ReprogramacionBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ReprogramacionBean reproBean = null;
    
    
    MovimientosReprogramacion reprogramacion = new MovimientosReprogramacion();
    List<ReprogramacionAccion> movtoAccionesList = new ArrayList<ReprogramacionAccion>();
    List<ReprogramacionMeta> movtoMetaList = new ArrayList<ReprogramacionMeta>();
    
    String tipoDependencia = new String();
    String strResultado = new String();
    String estatus = new String();
    String mensaje = new String();
    String valAutorizado = new String();
    String codigoAutorizado[] = new String[5];
        
    int folioAutorizado = 0;
    int year = 0;
    int folio = 0;
    boolean resultado = false;
    
    
    
    try{
        
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(Utilerias.existeParametro("estatus", request)){
            estatus = (String) request.getParameter("estatus");
        }
        if(Utilerias.existeParametro("folio", request)){
            folio = Integer.parseInt((String) request.getParameter("folio"));
        }
        
        if (session.getAttribute("reprogramacion") != null && session.getAttribute("reprogramacion") != "") {
            reprogramacion = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            movtoAccionesList = reprogramacion.getMovOficioAccionList();
            movtoMetaList = reprogramacion.getMovOficioMetaList();
            if(reprogramacion.getFechaElab() == null)
                reprogramacion.setFechaElab(new Date(System.currentTimeMillis()));
        }
        
        reproBean = new ReprogramacionBean(tipoDependencia);
        reproBean.setStrServer(request.getHeader("host"));
        reproBean.setStrUbicacion(getServletContext().getRealPath(""));
        reproBean.resultSQLConecta(tipoDependencia);
        
        for(ReprogramacionMeta meta: movtoMetaList){
            valAutorizado = reproBean.getResultSQLValidaModificacionMetaAutorizado(folio,
                    meta.getMetaInfo().getRamoId(),
                    meta.getMetaInfo().getMetaId());
            if(valAutorizado.equals("P")){
                folioAutorizado = reproBean.getResultSQLValidaOficioAutorizadoMeta(year,
                    meta.getMetaInfo().getRamoId(),
                    meta.getMetaInfo().getMetaId(),
                        reprogramacion.getFechaElab());
                if(folioAutorizado > 0){
                    if(reproBean.getResultSQLUpdateModificacionMeta("N",
                            folio, meta.getMovEstimacionList().get(0).getRamo(),
                            meta.getMovEstimacionList().get(0).getMeta())){
                        meta.setValAutorizado("N");
                        codigoAutorizado[0] = meta.getMetaInfo().getRamoId();
                        codigoAutorizado[1] = meta.getMetaInfo().getPrgId();
                        codigoAutorizado[2] = meta.getMetaInfo().getProyId()+meta.getMetaInfo().getTipoProy();
                        codigoAutorizado[3] =  String.valueOf(meta.getMetaInfo().getMetaId());
                        codigoAutorizado[4] = new String();
                        reproBean.transaccionCommit();
                    }else{
                        reproBean.transaccionRollback();
                    }
                    break;
                }
            }else if(valAutorizado.equals("N")){
                folioAutorizado = reproBean.getResultSQLValidaOficioAutorizadoMeta(year,
                        meta.getMovEstimacionList().get(0).getRamo(),
                        meta.getMovEstimacionList().get(0).getMeta(),
                        reprogramacion.getFechaElab());
                if(folioAutorizado > 0){
                    codigoAutorizado[0] = meta.getMetaInfo().getRamoId();
                    codigoAutorizado[1] = meta.getMetaInfo().getPrgId();
                    codigoAutorizado[2] = meta.getMetaInfo().getProyId()+meta.getMetaInfo().getTipoProy();
                    codigoAutorizado[3] =  String.valueOf(meta.getMetaInfo().getMetaId());
                    codigoAutorizado[4] = new String();
                    break;
                }
            }
        }    
        if(folioAutorizado == 0){
            for(ReprogramacionAccion accion: movtoAccionesList){
                valAutorizado = reproBean.getResultSQLValidaModificacionAccionAutorizado(folio,
                    accion.getMovOficioAccion().getRamoId(),
                    accion.getMovOficioAccion().getMetaId(),
                    accion.getMovOficioAccion().getAccionId());
                if(valAutorizado.equals("P")){
                    folioAutorizado = reproBean.getResultSQLValidaOficioAutorizadoAccion(year,
                            accion.getMovOficioAccion().getRamoId(),
                            accion.getMovOficioAccion().getMetaId(),
                            accion.getMovOficioAccion().getAccionId(),
                            reprogramacion.getFechaElab());
                    if(folioAutorizado > 0){
                        if(reproBean.getResultSQLUpdateModificacionAccion("N",
                            folio, accion.getMovOficioAccion().getRamoId(),
                            accion.getMovOficioAccion().getMetaId(),
                            accion.getMovOficioAccion().getAccionId())){
                            accion.setValAutorizado("N");
                            codigoAutorizado[0] = accion.getMovOficioAccion().getRamoId();
                            codigoAutorizado[1] = accion.getMovOficioAccion().getProgramaId();
                            codigoAutorizado[2] = accion.getMovOficioAccion().getProyectoId()+accion.getMovOficioAccion().getTipoProy();
                            codigoAutorizado[3] =  String.valueOf(accion.getMovOficioAccion().getMetaId());
                            codigoAutorizado[4] =  String.valueOf(accion.getMovOficioAccion().getAccionId());
                            reproBean.transaccionCommit();
                        }else{
                            reproBean.transaccionRollback();
                        }
                            break;
                    }
                }else if(valAutorizado.equals("N")){
                    folioAutorizado = reproBean.getResultSQLValidaOficioAutorizadoAccion(year,
                        accion.getMovOficioAccion().getRamoId(),
                        accion.getMovOficioAccion().getMetaId(),
                        accion.getMovOficioAccion().getAccionId(),
                        reprogramacion.getFechaElab());
                    if(folioAutorizado > 0){                        
                        accion.setValAutorizado("N");
                        codigoAutorizado[0] = accion.getMovOficioAccion().getRamoId();
                        codigoAutorizado[1] = accion.getMovOficioAccion().getProgramaId();
                        codigoAutorizado[2] = accion.getMovOficioAccion().getProyectoId()+accion.getMovOficioAccion().getTipoProy();
                        codigoAutorizado[3] =  String.valueOf(accion.getMovOficioAccion().getMetaId());
                        codigoAutorizado[4] =  String.valueOf(accion.getMovOficioAccion().getAccionId());
                        break;
                    }
                }
            }   
        }
        if(folioAutorizado == 0){
            mensaje = reproBean.actualizaMovOficio(folio, "R", tipoDependencia, estatus);
            if(mensaje.isEmpty()){
                resultado = true;
            }
            if(mensaje.isEmpty() && estatus.equals("R")){
                resultado = reproBean.getResultDeleteBitMovtoByOficio(folio);
            }
            if(resultado){
                //reproBean.transaccionCommit();
                reproBean.transaccionRollback();
            }
            else{
                strResultado = "-1";
                reproBean.transaccionRollback();
            }
        }else{
            strResultado += "Existe movimiento "+folioAutorizado+ " Autorizado que realizó modificación de Impacto Programático posterior a su captura Original. \n";
            strResultado += "Código Ramo "+codigoAutorizado[0]+" Programa "+ codigoAutorizado[1]+" Proyecto/Actividad " + codigoAutorizado[2] +" Meta " + codigoAutorizado[3];
            if(!codigoAutorizado[4].isEmpty())
                strResultado += " Acción " + codigoAutorizado[4] + "\n";
            else
                strResultado += "\n";
            strResultado += "Deberá modificar su captura Original de Impacto sobre el código anterior.";
            session.setAttribute("reprogramacion", reprogramacion);
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
        if (reproBean != null) {
            reproBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }%>