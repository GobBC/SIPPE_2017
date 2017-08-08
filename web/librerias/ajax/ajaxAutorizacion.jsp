<%-- 
    Document   : ajaxAutorizacion
    Created on : 14/12/2015, 12:01:54 PM
    Author     : rharo
--%>

<%@page import="gob.gbc.entidades.MovoficioLigado"%>
<%@page import="gob.gbc.aplicacion.MovoficioLigadoBean"%>
<%@page import="gob.gbc.util.MensajeError"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="org.apache.bcel.generic.AALOAD"%>
<%@page import="gob.gbc.util.Fechas"%>
<%@page import="gob.gbc.entidades.Movimiento"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    MensajeError mensajeError = new MensajeError();
    AutorizacionBean autorizacionBean = null;
    MovimientosAmpliacionReduccion movAmpRed = null;
    MovimientosTransferencia movTransferencia = null;
    String usuario = new String();
    String tipoDependencia = new String();
    String strResultado = new String();
    String motivo = new String();
    String tipoOficio = new String();
    int tipoFlujo = 0;
    String tipoMov = new String();
    String estatus = new String();
    String estatusCancelaRechaza = new String();
    String comentarioAut = new String();
    String comentarioPlan = new String();

    boolean bFechaContabilidad = false;
    int folio = 0;
    int opcion = 0;
    int year = 0;
    boolean bGrabo = false;
    boolean bContinuar = true;
    MovoficioLigadoBean movoficioLigadoBean = null;
    List<MovoficioLigado> movOficiosLigados = new ArrayList<MovoficioLigado>();
    String strFoliosLigados = "";

    try {
        if (session.getAttribute("strUsuario") == null || !((String) session.getAttribute("strUsuario")).equals("")) {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && !((String) session.getAttribute("tipoDependencia")).equals("")) {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (Utilerias.existeParametro("opcion", request)) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (Utilerias.existeParametro("motivo", request)) {
            motivo = request.getParameter("motivo");
        }
        if (Utilerias.existeParametro("tipoOficio", request)) {
            tipoOficio = request.getParameter("tipoOficio");
        }
        if (Utilerias.existeParametro("tipoFlujo", request)) {
            tipoFlujo = Integer.parseInt(request.getParameter("tipoFlujo"));
        }
        if (Utilerias.existeParametro("tipoMov", request)) {
            tipoMov = request.getParameter("tipoMov");
        }
        if (Utilerias.existeParametro("estatus", request)) {
            estatus = request.getParameter("estatus");
        }
        if (Utilerias.existeParametro("bFechaContabilidad", request)) {
            bFechaContabilidad = Boolean.parseBoolean(request.getParameter("bFechaContabilidad"));
        }
        if (Utilerias.existeParametro("comentario", request)) {
            comentarioAut = (String) request.getParameter("comentario");
        }
        if (Utilerias.existeParametro("comentarioPlan", request)) {
            comentarioPlan = (String) request.getParameter("comentarioPlan");
        }

        autorizacionBean = new AutorizacionBean(tipoDependencia);
        autorizacionBean.setStrServer(request.getHeader("host"));
        autorizacionBean.setStrUbicacion(getServletContext().getRealPath(""));
        autorizacionBean.resultSQLConecta(tipoDependencia);

        movoficioLigadoBean = new MovoficioLigadoBean(tipoDependencia);
        movoficioLigadoBean.setStrServer(request.getHeader("host"));
        movoficioLigadoBean.setStrUbicacion(getServletContext().getRealPath(""));
        movoficioLigadoBean.resultSQLConecta(tipoDependencia);

        switch (opcion) {
            case 1: //Cancelacion    (Calendarización y Reprogramación)

                if (!autorizacionBean.isParaestatal() && !autorizacionBean.getResultSqlGetIsAyuntamiento()) {
                    movOficiosLigados = movoficioLigadoBean.getMovOficioLigadoPendiente(year, folio,"C");                        
                    for (MovoficioLigado movoficioLigado : movOficiosLigados) { 
                        if (movoficioLigado.isPendiente()) {                                                                    
                            if (movoficioLigado.isLigadoParaestatal()) {
                                strFoliosLigados += "Paraestatal "+movoficioLigado.getOficioLigado() + ".\n";                                    
                            } else {
                                strFoliosLigados += "Central "+movoficioLigado.getOficioLigado() + ".\n";
                            }                                                                     
                        } 
                    }  

                    if(strFoliosLigados.equalsIgnoreCase(""))
                        bContinuar = true; 
                    else
                        bContinuar = false; 

                    strResultado = "-1|No es posible la cancelaci\u00f3n del oficio " + folio + ", existe ligado como no Cancelado:\n" + strFoliosLigados;
                }

                if (bContinuar) {

                    if (autorizacionBean.getCancelaMov(folio, tipoDependencia, usuario, motivo, tipoFlujo, mensajeError)) {
                        autorizacionBean.commit();
                        strResultado = "1";
                    } else {
                        strResultado = "-1|" + mensajeError.getMensaje();
                        autorizacionBean.rollback();
                    }
                }
                break;
            case 2: //Rechazo      (Calendarización y Reprogramación)

                if (!autorizacionBean.isParaestatal() && !autorizacionBean.getResultSqlGetIsAyuntamiento()) {

                    movOficiosLigados = movoficioLigadoBean.getMovOficioLigadoPendiente(year, folio, "C");
                        
                    for (MovoficioLigado movoficioLigado : movOficiosLigados) { 
                        if (movoficioLigado.isPendiente()) {                                                                    
                            if (movoficioLigado.isLigadoParaestatal()) {
                                strFoliosLigados += "Paraestatal "+movoficioLigado.getOficioLigado() + ".\n";                                    
                            } else {
                                strFoliosLigados += "Central "+movoficioLigado.getOficioLigado() + ".\n";
                            }                                                                     
                        } 
                    }  

                    if(strFoliosLigados.equalsIgnoreCase(""))
                        bContinuar = true; 
                    else
                        bContinuar = false; 

                    strResultado = "-1|No es posible rechazar el oficio " + folio + ", existe ligado como no rechazado\n" + strFoliosLigados;
                }

                if (bContinuar) {

                    if (autorizacionBean.getRechazaMov(folio, tipoDependencia, usuario, motivo, tipoFlujo, mensajeError)) {
                        autorizacionBean.commit();
                        strResultado = "1";
                    } else {
                        strResultado = "-1|" + mensajeError.getMensaje();
                        autorizacionBean.rollback();
                    }
                }

                break;
            case 3://Autoriza Recalendarizacion

                MovimientosRecalendarizacion movRecalendarizacion = new MovimientosRecalendarizacion();

                if (session.getAttribute("recalendarizacion") != null) {
                    movRecalendarizacion = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
                    movRecalendarizacion.setComentarioAutorizacion(comentarioAut);
                    
                    if (!autorizacionBean.isParaestatal() && !autorizacionBean.getResultSqlGetIsAyuntamiento()) {
                        if (estatus.equals("V")) {
                            movOficiosLigados = movoficioLigadoBean.getMovOficioLigadoPendiente(year, folio, "A");
                            for (MovoficioLigado movoficioLigado : movOficiosLigados) { 
                                if (movoficioLigado.isPendiente()) {                                                                    
                                    if (movoficioLigado.isLigadoParaestatal()) {
                                        strFoliosLigados += "Paraestatal "+movoficioLigado.getOficioLigado() + ".\n";                                    
                                    } else {
                                        strFoliosLigados += "Central "+movoficioLigado.getOficioLigado() + ".\n";
                                    }                                                                     
                                } 
                            }  

                            if(strFoliosLigados.equalsIgnoreCase(""))
                                bContinuar = true; 
                            else
                                bContinuar = false; 

                            strResultado = "-1|No es posible la Autorizaci\u00f3n del oficio " + folio + ", existe ligado como no Autorizado:\n" + strFoliosLigados;
                       }
                    }
                    
                    if(!autorizacionBean.getResultSQLExistenMetasAccionesRequerimientosNuevaCreacionErroneas(folio,""))
                        bContinuar = true; 
                    else
                        bContinuar = false; 
                     
                    strResultado = "-1|Error el folio no puede continuar con el proceso de autorizaci\u00f3n debido a que contiene metas o acciones o requerimientos definidas err\u00f3neamente como nueva creaci\u00f3n.";

                    if (bContinuar) {

                        if (autorizacionBean.getAutorizaMovRecalendarizacion(folio, tipoDependencia, usuario, tipoFlujo, movRecalendarizacion, mensajeError)) {
                            autorizacionBean.commit();
                            //autorizacionBean.rollback();
                            strResultado = "1";
                        } else {
                            strResultado = "-1|" + mensajeError.getMensaje();
                            autorizacionBean.rollback();
                        }

                    }

                } else {
                    strResultado = "-1|Error en el objeto de sesi\u00f3n de la recalendarizaci\u00f3n";
                }

                break;
            case 4://Autoriza Reprogramacion

                MovimientosReprogramacion movReprogramacion = new MovimientosReprogramacion();

                if (session.getAttribute("reprogramacion") != null) {

                    if (!autorizacionBean.isParaestatal() && !autorizacionBean.getResultSqlGetIsAyuntamiento()) {
                        if (estatus.equals("V")) {
                            movOficiosLigados = movoficioLigadoBean.getMovOficioLigadoPendiente(year, folio, "A");                            
                            for (MovoficioLigado movoficioLigado : movOficiosLigados) { 
                                if (movoficioLigado.isPendiente()) {                                                                    
                                    if (movoficioLigado.isLigadoParaestatal()) {
                                        strFoliosLigados += "Paraestatal "+movoficioLigado.getOficioLigado() + ".\n";                                    
                                    } else {
                                        strFoliosLigados += "Central "+movoficioLigado.getOficioLigado() + ".\n";
                                    }                                                                     
                                } 
                            }  

                            if(strFoliosLigados.equalsIgnoreCase(""))
                                bContinuar = true; 
                            else
                                bContinuar = false; 

                            strResultado = "-1|No es posible la Autorizaci\u00f3n del oficio " + folio + ", existe ligado como no Autorizado:\n" + strFoliosLigados;                            
                        }
                    }
                    
                    if(!autorizacionBean.getResultSQLExistenMetasAccionesRequerimientosNuevaCreacionErroneas(folio,""))
                        bContinuar = true; 
                    else
                        bContinuar = false; 
                     
                    strResultado = "-1|Error el folio no puede continuar con el proceso de autorizaci\u00f3n debido a que contiene metas o acciones o requerimientos definidas err\u00f3neamente como nueva creaci\u00f3n.";

                    if (bContinuar) {

                        movReprogramacion = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
                        movReprogramacion.setComentarioAutorizacion(comentarioAut);
                        if (autorizacionBean.getAutorizaMovReprogramacion(year, folio, tipoDependencia, usuario, tipoFlujo, movReprogramacion, mensajeError)) {
                            autorizacionBean.commit();
                            //autorizacionBean.transaccionRollback();
                            strResultado = "1";
                        } else {
                            strResultado = "-1|" + mensajeError.getMensaje();
                            autorizacionBean.rollback();
                        }

                    }
                } else {
                    strResultado = "-1|Error en el objeto de sesi\u00f3n de la reprogramaci\u00f3n";
                }

                break;
            case 5://Valida Contabilidad
                strResultado = String.valueOf(autorizacionBean.getValidaContabilidad(year));
                break;
            case 6://Autoriza Ampliaci\u00f3n/Reduccion

                movAmpRed = new MovimientosAmpliacionReduccion();
                if (session.getAttribute("ampliacionReduccion") != null) {

                    movAmpRed = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
                    movAmpRed.setComentarioAutorizacion(comentarioAut);
                    
                    //--->
                    if((autorizacionBean.validaAmpliacionesNumOficons(folio))>0){
                       bContinuar = false;
                       strResultado = "-1|!No se puede continuar... \n Por inconsistencia en la informaci\u00f3n \n Falta consec en oficons " ;
                    }
                      
                    if (!autorizacionBean.isParaestatal() && !autorizacionBean.getResultSqlGetIsAyuntamiento() && bContinuar==true) {
                        if (estatus.equals("T")) {
                            movOficiosLigados = movoficioLigadoBean.getMovOficioLigadoPendiente(year, folio, "A");
                            for (MovoficioLigado movoficioLigado : movOficiosLigados) { 
                                if (movoficioLigado.isPendiente()) {                                                                    
                                    if (movoficioLigado.isLigadoParaestatal()) {
                                        strFoliosLigados += "Paraestatal "+movoficioLigado.getOficioLigado() + ".\n";                                    
                                    } else {
                                        strFoliosLigados += "Central "+movoficioLigado.getOficioLigado() + ".\n";
                                    }                                                                     
                                } 
                            }  

                            if(strFoliosLigados.equalsIgnoreCase(""))
                                bContinuar = true; 
                            else
                                bContinuar = false; 

                            strResultado = "-1|No es posible la Autorizaci\u00f3n del oficio " + folio + ", existe ligado como no Autorizado:\n" + strFoliosLigados;                            
                         }
                    }
                    
                    if(!autorizacionBean.getResultSQLExistenMetasAccionesRequerimientosNuevaCreacionErroneas(folio,tipoOficio))
                        bContinuar = true; 
                    else
                        bContinuar = false; 
                    
                    strResultado = "-1|Error el folio no puede continuar con el proceso de autorizaci\u00f3n debido a que contiene metas o acciones o requerimientos definidas err\u00f3neamente como nueva creaci\u00f3n.";
                    
                    if  (bContinuar)    {
                        if(!autorizacionBean.getResultSQLExistenAmpliacionesTransferenciasAccionMetaErroneas(folio, "A"))
                            bContinuar = true; 
                        else
                            bContinuar = false; 
                        strResultado = "-1|Error el folio no puede continuar con el proceso de autorizaci\u00f3n debido a que contiene ampliaciones con inconsistencias con sus metas o acciones.";    
                    }

                    if (bContinuar) {

                        if (!Utileria.isTipoOficioRequerido(estatus)) {
                            if (autorizacionBean.getAutorizaMovAmpliacionReduccion(year, folio, tipoDependencia, usuario, bFechaContabilidad, tipoFlujo, movAmpRed, mensajeError)) {
                                autorizacionBean.commit();
                                //autorizacionBean.rollback();
                                strResultado = "1";
                            } else {
                                autorizacionBean.rollback();
                                strResultado = "-1|" + mensajeError.getMensaje();
                            }
                        } else {
                            if (autorizacionBean.getAutorizaMovAmpliacionReduccionTipoOficio(year, folio, tipoOficio, tipoDependencia,
                                    usuario, bFechaContabilidad, tipoFlujo, movAmpRed, mensajeError)) {
                                autorizacionBean.commit();
                                strResultado = "1";
                            } else {
                                autorizacionBean.rollback();
                                strResultado = "-1|" + mensajeError.getMensaje();
                            }
                        }

                    }

                } else {
                    strResultado = "-1|Error en el objeto de sesi\u00f3n de la ampliaci\u00f3n";
                }

                break;


            case 7:
            case 8: //Cancela/Rechaza Ampliación/Reducción
                movAmpRed = new MovimientosAmpliacionReduccion();
                if (session.getAttribute("ampliacionReduccion") != null) {

                    movAmpRed = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");

                    if (!autorizacionBean.isParaestatal() && !autorizacionBean.getResultSqlGetIsAyuntamiento()) {
                        movOficiosLigados = movoficioLigadoBean.getMovOficioLigadoPendiente(year, folio, "C");
                        for (MovoficioLigado movoficioLigado : movOficiosLigados) { 
                            if (movoficioLigado.isPendiente()) {                                                                    
                                if (movoficioLigado.isLigadoParaestatal()) {
                                    strFoliosLigados += "Paraestatal "+movoficioLigado.getOficioLigado() + ".\n";                                    
                                } else {
                                    strFoliosLigados += "Central "+movoficioLigado.getOficioLigado() + ".\n";
                                }                                                                     
                            } 
                        }  

                        if(strFoliosLigados.equalsIgnoreCase(""))
                            bContinuar = true; 
                        else
                            bContinuar = false; 

                        strResultado = "-1|No es posible la cancelaci\u00f3n del oficio " + folio + ", existe ligado como no Cancelado:\n" + strFoliosLigados;
                    }

                    if (bContinuar) {

                        if (!Utileria.isTipoOficioRequerido(estatus)) {
                            if (opcion == 7) {
                                estatusCancelaRechaza = "C";
                            } else if (opcion == 8) {
                                estatusCancelaRechaza = "R";
                            }
                            if (autorizacionBean.getCancelaRechazaMovAmpRed(folio, tipoDependencia, usuario, motivo,
                                    estatus, estatusCancelaRechaza, tipoFlujo, movAmpRed, mensajeError)) {
                                autorizacionBean.commit();
                                strResultado = "1";
                            } else {
                                autorizacionBean.rollback();
                                strResultado = "-1|" + mensajeError.getMensaje();
                            }
                        } else {
                            if (opcion == 7) {
                                estatusCancelaRechaza = "C";
                            } else if (opcion == 8) {
                                estatusCancelaRechaza = "K";
                            }
                            if (autorizacionBean.getCancelaRechazaMovAmpRedTipoOficio(folio, tipoDependencia, usuario, motivo, tipoOficio,
                                    estatusCancelaRechaza, tipoFlujo, movAmpRed, mensajeError)) {
                                autorizacionBean.commit();
                                strResultado = "1";
                            } else {
                                autorizacionBean.rollback();
                                strResultado = "-1|" + mensajeError.getMensaje();
                            }
                        }

                    }
                } else {
                    strResultado = "-1|Error en el objeto de sesi\u00f3n de la ampliaci\u00f3n";
                }

                break;
            case 9:
            case 10: //Cancela/Rechaza Transferencias
                movTransferencia = new MovimientosTransferencia();
                if (session.getAttribute("transferencia") != null) {

                    movTransferencia = (MovimientosTransferencia) session.getAttribute("transferencia");

                    if (!autorizacionBean.isParaestatal() && !autorizacionBean.getResultSqlGetIsAyuntamiento()) {
                        movOficiosLigados = movoficioLigadoBean.getMovOficioLigadoPendiente(year, folio, "C");
                        for (MovoficioLigado movoficioLigado : movOficiosLigados) { 
                            if (movoficioLigado.isPendiente()) {                                                                    
                                if (movoficioLigado.isLigadoParaestatal()) {
                                    strFoliosLigados += "Paraestatal "+movoficioLigado.getOficioLigado()+ ".\n";                                    
                                } else {
                                    strFoliosLigados += "Central "+movoficioLigado.getOficioLigado()+ ".\n";
                                }                                                                     
                            } 
                        }  

                        if(strFoliosLigados.equalsIgnoreCase(""))
                            bContinuar = true; 
                        else
                            bContinuar = false; 

                        strResultado = "-1|No es posible la cancelaci\u00f3n del oficio " + folio + ", existe ligado como no Cancelado:\n" + strFoliosLigados;
                    }

                    if (bContinuar) {

                        if (!Utileria.isTipoOficioRequerido(estatus)) {
                            if (opcion == 9) {
                                estatusCancelaRechaza = "C";
                            } else if (opcion == 10) {
                                estatusCancelaRechaza = "R";
                            }
                            if (autorizacionBean.getCancelaRechazaTransferencia(folio, tipoDependencia, usuario, motivo,
                                    estatus, estatusCancelaRechaza, tipoFlujo, movTransferencia, mensajeError)) {
                                autorizacionBean.commit();
                                strResultado = "1";
                            } else {
                                autorizacionBean.rollback();
                                strResultado = "-1|" + mensajeError.getMensaje();
                            }
                        } else {
                            if (opcion == 9) {
                                estatusCancelaRechaza = "C";
                            } else if (opcion == 10) {
                                estatusCancelaRechaza = "K";
                            }
                            if (autorizacionBean.getCancelaRechazaTransferenciaTipoOficio(folio, tipoDependencia, usuario, motivo, tipoOficio,
                                    estatusCancelaRechaza, tipoFlujo, movTransferencia, mensajeError)) {
                                autorizacionBean.commit();
                                strResultado = "1";
                            } else {
                                autorizacionBean.rollback();
                                strResultado = "-1|" + mensajeError.getMensaje();
                            }
                        }

                    }

                } else {
                    strResultado = "-1|Error en el objeto de sesi\u00f3n de la transferencia";
                }

                break;
            case 11://Autoriza Transferencia

                movTransferencia = new MovimientosTransferencia();
                if (session.getAttribute("transferencia") != null) {

                    movTransferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
                    movTransferencia.setComentarioAutorizacion(comentarioAut);
                    movTransferencia.setComentarioPlaneacion(comentarioPlan);
                    if((autorizacionBean.validaTransferenciasNumOficons(movTransferencia.getOficio())>0)){
                     bContinuar= false;
                     strResultado = "-1|!No se puede continuar... \n Por inconsistencia en la informaci\u00f3n \n Falta consec en oficons " ;
                    }

                    if (!autorizacionBean.isParaestatal() && !autorizacionBean.getResultSqlGetIsAyuntamiento() && bContinuar==true ) {
                        if (estatus.equals("T")) {
                            movOficiosLigados = movoficioLigadoBean.getMovOficioLigadoPendiente(year, folio, "A");
                            for (MovoficioLigado movoficioLigado : movOficiosLigados) { 
                                if (movoficioLigado.isPendiente()) {                                                                    
                                    if (movoficioLigado.isLigadoParaestatal()) {
                                        strFoliosLigados += "Paraestatal "+movoficioLigado.getOficioLigado() + ".\n";                                    
                                    } else {
                                        strFoliosLigados += "Central "+movoficioLigado.getOficioLigado() + ".\n";
                                    }                                                                     
                                } 
                            }  

                            if(strFoliosLigados.equalsIgnoreCase(""))
                                bContinuar = true; 
                            else
                                bContinuar = false; 

                            strResultado = "-1|No es posible la Autorizaci\u00f3n del oficio " + folio + ", existe ligado como no Autorizado:\n" + strFoliosLigados;                            
                        }
                    }
                                       
                    if(!autorizacionBean.getResultSQLExistenMetasAccionesRequerimientosNuevaCreacionErroneas(folio,tipoOficio))
                        bContinuar = true; 
                    else
                        bContinuar = false; 
                     
                    strResultado = "-1|Error el folio no puede continuar con el proceso de autorizaci\u00f3n debido a que contiene metas o acciones o requerimientos definidas err\u00f3neamente como nueva creaci\u00f3n.";

                    if  (bContinuar)    {
                        if(!autorizacionBean.getResultSQLExistenAmpliacionesTransferenciasAccionMetaErroneas(folio, "T"))
                            bContinuar = true; 
                        else
                            bContinuar = false; 
                        strResultado = "-1|Error el folio no puede continuar con el proceso de autorizaci\u00f3n debido a que contiene transferencias con inconsistencias con sus metas o acciones.";    
                    }
                    
                    if (bContinuar) {
                        
                        if (!Utileria.isTipoOficioRequerido(estatus)) {       
                            if (autorizacionBean.getAutorizaMovTransferencia(year, folio, tipoDependencia, usuario, bFechaContabilidad, tipoFlujo, movTransferencia, mensajeError)) {
                                autorizacionBean.commit();
                                strResultado = "1";
                            } else {
                                autorizacionBean.rollback();
                                strResultado = "-1|" + mensajeError.getMensaje();
                            }
                        } else {
                            if (autorizacionBean.getAutorizaMovTransferenciaTipoOficio(year, folio, tipoOficio, tipoDependencia,
                                    usuario, bFechaContabilidad, tipoFlujo, movTransferencia, mensajeError)) {
                                autorizacionBean.commit();
                                //autorizacionBean.rollback();
                                strResultado = "1";
                            } else {
                                autorizacionBean.rollback();
                                strResultado = "-1|" + mensajeError.getMensaje();
                            }
                        }

                    }
                } else {
                    strResultado = "-1|Error en el objeto de sesi\u00f3n de la transferencia";
                }

                break;
            default:
                strResultado = "-1|Opci\u00f3n inv\u00e1lida";
                break;
        }

    } catch (Exception ex) {

        if (autorizacionBean != null) {
            autorizacionBean.rollback();
        }

        strResultado = "-1|Error inesperado en la opci\u00f3n " + opcion;
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (autorizacionBean != null) {
            autorizacionBean.resultSQLDesconecta();
        }
        if (movoficioLigadoBean != null) {
            movoficioLigadoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

