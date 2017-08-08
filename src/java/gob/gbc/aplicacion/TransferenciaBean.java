/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Accion;
import gob.gbc.entidades.Articulo;
import gob.gbc.entidades.Caratula;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.EstatusMov;
import gob.gbc.entidades.Fuente;
import gob.gbc.entidades.FuenteRecurso;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.MovOficioAccionEstimacion;
import gob.gbc.entidades.MovOficioEstimacion;
import gob.gbc.entidades.MovOficiosAccionReq;
import gob.gbc.entidades.Movimiento;
import gob.gbc.entidades.MovimientoOficioAccion;
import gob.gbc.entidades.MovimientoOficioMeta;
import gob.gbc.entidades.MovimientosTransferencia;
import gob.gbc.entidades.Transferencia;
import gob.gbc.entidades.Partida;
import gob.gbc.entidades.Programa;
import gob.gbc.entidades.Proyecto;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.RecalendarizacionAccionReq;
import gob.gbc.entidades.RelacionLaboral;
import gob.gbc.entidades.TipoOficio;
import gob.gbc.entidades.TransferenciaAccion;
import gob.gbc.entidades.TransferenciaAccionReq;
import gob.gbc.entidades.TransferenciaMeta;
import gob.gbc.entidades.Usuario;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Fechas;
import gob.gbc.util.MensajeError;
import gob.gbc.util.Utileria;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author jarguelles
 */
public class TransferenciaBean extends ResultSQL {

    Bitacora bitacora;

    public TransferenciaBean(String tipoDependencia) throws Exception {

        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public Usuario getInfoUsuario(String appLogin, int year) {
        Usuario usuario = new Usuario();
        try {
            usuario = super.getResultGetInfoUsuario(appLogin, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return usuario;
    }

    public List<Ramo> getRamosByUsuario(int year, String appLogin) {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        try {
            ramoList = super.getResultGetRamoByUsuario(year, appLogin);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramoList;
    }

    public List<Ramo> getRamosTransitorioByUsuario(int year, String appLogin, String transitorio) {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        try {
            ramoList = super.getResultGetRamoTransitorioByUsuario(year, appLogin, transitorio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramoList;
    }

    public List<Programa> getProgramaByRamoUsuario(int year, String appLogin, String ramo) {
        List<Programa> programaList = new ArrayList<Programa>();
        try {
            programaList = super.getResultGetProgramaByRamoUsuario(year, appLogin, ramo);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return programaList;
    }

    public List<Proyecto> getProyectoByProgramaUsuario(int year, String appLogin, String ramo, String programa) {
        List<Proyecto> proyectoList = new ArrayList<Proyecto>();
        try {
            proyectoList = super.getResultGetProyectoByProyectoUsuario(year, appLogin, ramo, programa);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return proyectoList;
    }

    public List<Meta> getMetaByProyectoUsuario(int year, String appLogin, String ramo, String programa, int proy, String tipoProy) {
        List<Meta> metaList = new ArrayList<Meta>();
        try {
            metaList = super.getResultGetMetaByProyectoUsuario(year, appLogin, ramo, programa, proy, tipoProy);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return metaList;
    }

    public List<Accion> getAccionByMetaUsuario(int year, String appLogin, String ramo, String programa, int proy, String tipoProy, int meta) {
        List<Accion> accionList = new ArrayList<Accion>();
        try {
            accionList = super.getResultGetAccionByMetaUsuario(year, appLogin, ramo, programa, proy, tipoProy, meta);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return accionList;
    }

    public List<Partida> getPartidasByAccionAmpRed(int year, String appLogin, String ramo, String programa, int proy, String tipoProy, int meta, int accion) {
        List<Partida> partidasList = new ArrayList<Partida>();
        try {
            partidasList = super.getResultGetPartidaByAccionAmplRed(year, appLogin, ramo, programa, proy, tipoProy, meta, accion);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return partidasList;
    }

    public List<RelacionLaboral> getRelLaboralByCatalogo(int year) {
        List<RelacionLaboral> relLaboralList = new ArrayList<RelacionLaboral>();
        try {
            relLaboralList = super.getResultSQLGetRelacionLaboral(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return relLaboralList;
    }

    public List<FuenteRecurso> getFuenteFinanciamientoByPartidaAmpRed(int year) {
        List<FuenteRecurso> fuenteList = new ArrayList<FuenteRecurso>();
        try {
            fuenteList = super.getResultSQLGetFuenteRecursoCompleto(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteList;
    }

    public List<Fuente> getFuenteFinanciamientoByRelLaboralUsuario(int year, String appLogin, String ramo, String programa, int proy, String tipoProy, int meta, int accion, String partida, String relLaboral) {
        List<Fuente> fuenteList = new ArrayList<Fuente>();
        try {
            fuenteList = super.getResultGetFuenteFinanciamientoByRelLaboralUsuario(year, appLogin, ramo, programa, proy, tipoProy, meta, accion, partida, relLaboral);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteList;
    }

    public boolean isPartidaDePlantilla(String partida, int year) {
        boolean isPlantilla = false;
        try {
            isPlantilla = super.getResultSQLGetCountPartidaPlantilla(partida, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isPlantilla;
    }

    public CodigoPPTO getCodigoProgramaticoByRequerimiento(int year, String ramo, String programa, String tipoProyecto, int proyecto, int meta, int accion, String partida, String fuente, String fondo, String recurso, String relLaboral) {
        CodigoPPTO codigoPpto = new CodigoPPTO();
        try {
            codigoPpto = super.getRestulSQLgetCodigoPPTOsinRequerimiento(year, ramo, programa, tipoProyecto, proyecto, meta, accion, partida, fuente, fondo, recurso, relLaboral);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return codigoPpto;
    }

    public int getConsecutivoNegativoAccionReq() {
        int negativo = 0;
        try {
            negativo = super.getResultSQLGetConsecutovoNegativoAccionReq();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return negativo;
    }

    public int getConsecutivoNegativoAccion() {
        int negativo = 0;
        try {
            negativo = super.getResultSQLGetConsecutovoNegativoAccion();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return negativo;
    }

    public int getConsecutivoNegativoMeta() {
        int negativo = 0;
        try {
            negativo = super.getResultSQLGetConsecutovoNegativoMeta();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return negativo;
    }

    public MovimientosTransferencia getInfoMovOficioTransferencia(int folio) {
        MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
        try {
            movTransferencia = super.getResultGetInfoMovOficioTransferencia(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movTransferencia;
    }

    public List<TransferenciaMeta> getMovOficioMeta(int folio, int year, String tipoTransferencia) {
        List<TransferenciaMeta> transferenciaMetaList = new ArrayList<TransferenciaMeta>();
        try {
            transferenciaMetaList = super.getResultGetMovOficioMetaTransferencia(folio, year, tipoTransferencia);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return transferenciaMetaList;
    }
    
    public List<TransferenciaMeta> getMovOficioRechazoMeta(int folio, int year, List<Transferencia> transf) {
        List<TransferenciaMeta> transferenciaMetaList = new ArrayList<TransferenciaMeta>();
        try {
            transferenciaMetaList = super.getResultGetMovOficioMetaRechazoTransferencia(folio, year, transf);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return transferenciaMetaList;
    }

    public List<TransferenciaAccion> getMovOficioAccion(int folio, int year, String tipoTransferencia) {
        List<TransferenciaAccion> transferenciaAccionList = new ArrayList<TransferenciaAccion>();
        try {
            transferenciaAccionList = super.getResultGetMovOficioAccionTransferencia(folio, year, tipoTransferencia);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return transferenciaAccionList;
    }
    public List<TransferenciaAccion> getMovOficioRechazoAccion(int folio, int year, List<Transferencia> transf) {
        List<TransferenciaAccion> transferenciaAccionList = new ArrayList<TransferenciaAccion>();
        try {
            transferenciaAccionList = super.getResultGetMovOficioAccionRechazoTransferencia(folio, year, transf);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return transferenciaAccionList;
    }

    public List<TransferenciaAccionReq> getMovOficioAccionRequerimiento(int folio, int year, int consec) {
        List<TransferenciaAccionReq> transferenciaAccionReqList = new ArrayList<TransferenciaAccionReq>();
        try {
            transferenciaAccionReqList = super.getTransferenciaAccionReqByFolioConsec(folio, consec);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return transferenciaAccionReqList;
    }
    /*
     public List<TransferenciaAccionReq> getMovOficioAccionRequerimientoByTipoOficio(int folio, int year, String tipoOficio) {
     List<TransferenciaAccionReq> transferenciaAccionReqList = new ArrayList<TransferenciaAccionReq>();
     try {
     //Cambiar el query solo se modifico para que no marcara error pero el query esta a SPP.AMPLIACIONES
     transferenciaAccionReqList = super.getTransferenciaAccionReqByTipoOficio(folio, tipoOficio);
     } catch (Exception ex) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(ex, new Throwable());
     bitacora.grabaBitacora();
     }
     return transferenciaAccionReqList;
     }
     */

    public int getSecuenciaMovimientos(boolean isActual, int year) {
        String movimientos = new String();
        String strYear = new String();
        int folio = 0;
        try {
            strYear = String.valueOf(year);
            movimientos = super.getResultSQLgetSecuenciaMovimientoOficio(isActual);
            movimientos = strYear.substring(strYear.length() - 1) + movimientos;
            folio = Integer.parseInt(movimientos);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (NumberFormatException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return folio;
    }

    public boolean saveOficioTransferencia(MovimientosTransferencia movTransferencia, String fecha, boolean isActual, String capturaEspecial) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovOficio(movTransferencia.getOficio(), movTransferencia.getAppLogin(), movTransferencia.getStatus(),
                "T", movTransferencia.getJutificacion(), fecha, isActual, capturaEspecial);
        return resultado;
    }

    public boolean updateJustificacionMovOficios(int folio, String justificacion, String comentarioPlaneacion) {
        boolean resultado = false;
        resultado = super.getResultSQLupdateJustificacionMovoficio(folio, justificacion,comentarioPlaneacion);
        return resultado;
    }

    public String saveMovimientoTransferencia(MovimientosTransferencia movTransferencia,
            int folio, boolean isActual, int year, String fecha, String capturaEspecial,
            String ramoSession, long selCaratula, String tipoOficio, String justificacion, String tipoUsuario) {
        List<TransferenciaMeta> transferenciaMetaList = new ArrayList<TransferenciaMeta>();
        List<TransferenciaAccion> transferenciaAccionList = new ArrayList<TransferenciaAccion>();
        List<TransferenciaAccionReq> transferenciaAccionReqList = new ArrayList<TransferenciaAccionReq>();
        List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
        MensajeError objMensaje = new MensajeError();
        String mensaje = new String();
        String estatus = movTransferencia.getStatus();
        String estatusAnt = new String();
        boolean resultado = false;
        int tipoFlujo = 0;
        int folioAnterior = 0;
        try {
            transferenciaMetaList = movTransferencia.getTransferenciaMetaList();
            transferenciaAccionList = movTransferencia.getTransferenciaAccionList();
            transferenciaList = movTransferencia.getTransferenciaList();
            tipoFlujo = super.getResultTipoFlujoInicial("T", tipoUsuario);
            if(estatus.equals("K")){
                movTransferencia.setStatus("X");
                folioAnterior = folio;
                folio = 0;
            }
            if (folio == 0) {
                movTransferencia.setOficio(this.getSecuenciaMovimientos(isActual, year));
                movTransferencia.getOficio();
                resultado = this.saveOficioTransferencia(movTransferencia, fecha, isActual, capturaEspecial);
                if (!(selCaratula < 0)) {
                    resultado = this.saveCaratulaTransferencia(movTransferencia.getOficio(), year, ramoSession, selCaratula);
                }
                if(estatus.equals("K")){
                    if(resultado){
                        resultado = this.getCancelaRechazaTransferenciaTipoOficio(folioAnterior, tipoUsuario, movTransferencia.getAppLogin()
                                , "SE GENERÓ UN FOLIO NUEVO - "+ folioAnterior,tipoOficio, "C", tipoFlujo, movTransferencia, objMensaje);
                        if(resultado)
                            resultado = super.getResultSQLUpdateFolioRelacionado(folioAnterior, tipoOficio,movTransferencia.getOficio() );
                        estatusAnt = estatus;
                        estatus = "X";
                    }else
                        resultado = false;
                }
            } else {
                resultado = this.updateJustificacionMovOficios(folio, justificacion, movTransferencia.getComentarioPlaneacion());
                if (resultado) {
                    if (Utileria.isTipoOficioRequerido(estatus)) {
                        resultado = this.deleteMovientosTransferenciaByTipoOficio(folio, tipoOficio);
                    } else {
                        resultado = this.deleteMovientosTransferencia(folio);
                    }

                    if (!(selCaratula < 0)) {
                        if (movTransferencia.getMovCaratula().getsIdCaratula() < 0) {
                            resultado = this.saveCaratulaTransferencia(folio, year, ramoSession, selCaratula);
                        } else {
                            resultado = this.updateCaratulaTransferencia(folio, year, ramoSession, selCaratula, movTransferencia.getMovCaratula().getsIdCaratula());
                        }
                    } else {
                        if (selCaratula == -2) {
                            deleteCaratulaTransferencia(folio, year, ramoSession, movTransferencia.getMovCaratula().getsIdCaratula());
                        }
                    }

                }
                mensaje = "0|Error al editar la información";
            }
            if (resultado) {
                for (TransferenciaMeta transferenciaMetaTemp : transferenciaMetaList) {
                    /*for (MovOficioEstimacion estimacionTemp : transferenciaMetaTemp.getMovOficioEstimacion()) {
                     resultado = this.saveMovimientoEstimacionMeta(folio, estimacionTemp, "N", "T");
                     if (!resultado) {
                     break;
                     }
                     }
                     Cambiar por el for de arriba cuando se prueben las ampliaciones
                     */
                    //for (TransferenciaMeta transMetaTemp : transferenciaMetaList) {
                    resultado = this.saveMovimientoOficioMeta(transferenciaMetaTemp.getMovOficioMeta(), movTransferencia.getOficio(), transferenciaMetaTemp.getValAutorizado());
                    if (resultado) {
                        for (MovOficioEstimacion estimacionTemp : transferenciaMetaTemp.getMovOficioEstimacion()) {
                            resultado = this.saveMovimientoEstimacionMeta(movTransferencia.getOficio(), estimacionTemp, transferenciaMetaTemp.getMovOficioMeta().getNva_creacion(), "T", transferenciaMetaTemp.getValAutorizado());
                            if (!resultado) {
                                break;
                            }
                        }
                        if (!resultado) {
                            mensaje = "0|Error al insertar meta";
                            break;
                        }
                    }
                    //}
                    if (!resultado) {
                        mensaje = "0|Error al insertar meta";
                        break;
                    }
                }
                if (resultado) {
                    /*for (TransferenciaAccion transferenciaAccionTemp : transferenciaAccionList) {
                     for (MovOficioAccionEstimacion accionEstimacionTemp : transferenciaAccionTemp.getMovOficioAccionEstList()) {
                     resultado = this.saveMovimientoAccionEstimacionMeta(folio, accionEstimacionTemp, "N", "T");
                     if (!resultado) {
                     break;
                     }
                     }
                     if (!resultado) {
                     mensaje = "Error al insertar acción";
                     break;
                     }
                     }
                     Cambiar por el for de arriba cuando se prueben las ampliaciones*/
                    for (TransferenciaAccion transAccionTemp : transferenciaAccionList) {
                        resultado = this.saveMovimientoOficioAccion(transAccionTemp.getMovOficioAccion(), movTransferencia.getOficio(), transAccionTemp.getValAutorizado());
                        if (resultado) {
                            for (MovOficioAccionEstimacion accionEstimacionTemp : transAccionTemp.getMovOficioAccionEstList()) {
                                resultado = this.saveMovimientoAccionEstimacionMeta(movTransferencia.getOficio(), accionEstimacionTemp, transAccionTemp.getMovOficioAccion().getNvaCreacion(), "R", transAccionTemp.getValAutorizado());
                                if (!resultado) {
                                    break;
                                }
                            }
                            if (!resultado) {
                                mensaje = "0|Error al insertar acción";
                                break;
                            }
                        }
                    }
                }
            }
            if (resultado) {
                for (Transferencia transferencia : transferenciaList) {
                    resultado = super.getResultSQLInsertTransferencia(movTransferencia.getOficio(), transferencia.getConsec(), transferencia.getRamo(),
                            transferencia.getDepto(), transferencia.getFinalidad(), transferencia.getFuncion(), transferencia.getSubfuncion(),
                            transferencia.getPrgConac(), transferencia.getPrograma(), transferencia.getTipoProy(), transferencia.getProyecto(),
                            transferencia.getMeta(), transferencia.getAccion(), transferencia.getPartida(), transferencia.getTipoGasto(),
                            transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(), transferencia.getMunicipio(),
                            transferencia.getDelegacion(), transferencia.getRelLaboral(), transferencia.getImporte(),
                            transferencia.getEstatus(), transferencia.getConsiderar());
                    if (resultado) {
                        transferenciaAccionReqList = transferencia.getTransferenciaAccionReqList();
                        if (resultado) {
                            mensaje = this.saveTransferencias(year, movTransferencia.getOficio(), transferenciaAccionReqList);
                            if (!mensaje.isEmpty()) {
                                resultado = false;
                            }
                        }
                    }
                }
            }
            if (resultado) {
                resultado = super.getResultSqlGetValidaCuadreTransferencias(movTransferencia.getOficio());
                if(resultado){
                    super.transaccionCommit();
                    //super.transaccionRollback();
                    if(estatusAnt.equalsIgnoreCase("K"))
                        mensaje = "$" + String.valueOf(movTransferencia.getOficio())+"|N";
                    else
                        mensaje = "$" + String.valueOf(movTransferencia.getOficio())+"|S";
                }else{
                    if(folio != 0)
                        movTransferencia.setOficio(movTransferencia.getOficio());
                    else
                        movTransferencia.setOficio(0);
                        
                    super.transaccionRollback();
                    mensaje = "0|Alguna de las transferencias no cuadran";
                }
            } else {
                if(folio != 0)
                    movTransferencia.setOficio(movTransferencia.getOficio());
                else
                    movTransferencia.setOficio(0);
                super.transaccionRollback();
            }
        } catch (SQLException ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public boolean deleteMovientosTransferencia(int folio) {
        boolean resultado = false;
        try {
            if (this.countMovimientoOficioMeta(folio) > 0) {
                resultado = this.deleteMovimientoOficioMeta(folio);
            } else {
                resultado = true;
            }
            if (resultado) {
                if (this.countMovimientoEstimacion(folio) > 0) {
                    resultado = this.deleteMovimientoEstimacion(folio);
                } else {
                    resultado = true;
                }
            }
            if (resultado) {
                if (this.countMovimientoOficioAccion(folio) > 0) {
                    resultado = this.deleteMovimientoOficioAccion(folio);
                } else {
                    resultado = true;
                }
            }
            if (resultado) {
                if (this.countMovimientoAccionEstimacion(folio) > 0) {
                    resultado = this.deleteMovimientoAccionEstimacion(folio);
                } else {
                    resultado = true;
                }
            }
            if (resultado) {
                if (super.getResultSQLgetCountMovtoAccionReq(folio) > 0) {
                    resultado = super.getResultSQLDeleteMovtoAccionReq(folio);
                }
            }
            if (resultado) {
                if (super.getResultSQLCountTranfrec(folio) > 0) {
                    resultado = super.getResultSQLDeleteTransfrec(folio);
                }
            }
            if (resultado) {
                if (super.getResultSQLCountTranferencia(folio) > 0) {
                    resultado = super.getResultSQLdeleteTransferencia(folio);
                }
            }

        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean deleteMovientosTransferenciaByTipoOficio(int folio, String tipoOficio) {
        boolean resultado = false;
        try {
            if (this.countMovimientoOficioMeta(folio) > 0) {
                resultado = this.deleteMovimientoOficioMeta(folio);
            } else {
                resultado = true;
            }
            if (resultado) {
                if (this.countMovimientoEstimacion(folio) > 0) {
                    resultado = this.deleteMovimientoEstimacion(folio);
                } else {
                    resultado = true;
                }
            }
            if (resultado) {
                if (this.countMovimientoOficioAccion(folio) > 0) {
                    resultado = this.deleteMovimientoOficioAccion(folio);
                } else {
                    resultado = true;
                }
            }
            if (resultado) {
                if (this.countMovimientoAccionEstimacion(folio) > 0) {
                    resultado = this.deleteMovimientoAccionEstimacion(folio);
                } else {
                    resultado = true;
                }
            }
            if (resultado) {
                if (super.getResultSQLgetCountMovtoAccionReqByTipoOficio(folio, tipoOficio) > 0) {
                    resultado = super.getResultSQLDeleteMovtoAccionReqByTipoOficio(folio, tipoOficio);
                }
            }
            if (resultado) {
                if (super.getResultSQLCountTranfrecByTipoOficio(folio, tipoOficio) > 0) {
                    resultado = super.getResultSQLDeleteTransfrecByTipoOficio(folio, tipoOficio);
                }
            }
            if (resultado) {
                if (super.getResultSQLCountTranferenciaByTipoOficio(folio, tipoOficio) > 0) {
                    resultado = super.getResultSQLdeleteTransferenciaByTipoOficio(folio, tipoOficio);
                }
            }

        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public int countMovimientoOficioMeta(int folio) {
        int count = 0;
        try {
            count = super.getResultExistenMovReprogramacionMetaByFolio(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return count;
    }

    public int countMovimientoOficioAccion(int folio) {
        int count = 0;
        try {
            count = super.getResultExistenMovReprogramacionAccionByFolio(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return count;
    }

    public int countMovimientoEstimacion(int folio) {
        int count = 0;
        try {
            count = super.getResultExistenMovMetaByFolio(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return count;
    }

    public int countMovimientoAccionEstimacion(int folio) {
        int count = 0;
        try {
            count = super.getResultExistenMovAccionByFolio(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return count;
    }

    public boolean deleteMovimientoOficioMeta(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovOfReprogramacionMeta(folio);
        return resultado;
    }

    public boolean deleteMovimientoOficioAccion(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovOfReprogramacionAccion(folio);
        return resultado;
    }

    public boolean deleteMovimientoEstimacion(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovtoMeta(folio);
        return resultado;
    }

    public boolean deleteMovimientoAccionEstimacion(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovtoAccion(folio);
        return resultado;
    }

    public boolean saveMovimientoOficioMeta(MovimientoOficioMeta movOficioMeta, int folio, String valAutorizado) {
        boolean resultado = false;
        String clasificacion[] = new String[3];
        clasificacion = movOficioMeta.getClasificacionFuncionalId().split("\\.");
        //TODO: AUTORIZADO
        resultado = super.getResultSQLInsertMovReprogramacionMeta(movOficioMeta.getYear(), folio,
                movOficioMeta.getRamoId(), movOficioMeta.getPrgId(), movOficioMeta.getMetaId(),
                movOficioMeta.getMetaDescr(), movOficioMeta.getCalculoId(),
                movOficioMeta.getClaveMedida(), clasificacion[0], clasificacion[1],
                clasificacion[2], movOficioMeta.getTipoProy(), movOficioMeta.getProyId(),
                movOficioMeta.getLineaPedId(), movOficioMeta.getCompromisoId(), movOficioMeta.getPonderacionId(),
                movOficioMeta.getLineaSectorialId(), movOficioMeta.getCriterioTransversalidad(),
                movOficioMeta.getNva_creacion(), valAutorizado);
        return resultado;
    }

    public boolean saveMovimientoOficioAccion(MovimientoOficioAccion movOficioAccion, int folio, String valAutorizado) {
        boolean resultado = false;
        //TODO: AUTORIZADO
        resultado = getResultSQLInsertMovReprogramacionAccion(movOficioAccion.getYear(), folio, movOficioAccion.getRamoId(),
                movOficioAccion.getProgramaId(), movOficioAccion.getDeptoId(), movOficioAccion.getMetaId(), movOficioAccion.getAccionId(),
                movOficioAccion.getAccionDescr(), movOficioAccion.getCalculo(), movOficioAccion.getClaveMedida(),
                movOficioAccion.getGrupoPoblacional(), movOficioAccion.getBenefHombre(), movOficioAccion.getBenefMujer(),
                movOficioAccion.getMunicipio(), movOficioAccion.getLocalidad(), movOficioAccion.getLinea(),
                movOficioAccion.getLineaSectorial(), movOficioAccion.getNvaCreacion(), movOficioAccion.getObra(), valAutorizado);
        return resultado;
    }

    public boolean saveMovimientoEstimacionMeta(int oficio, MovOficioEstimacion movOficioEst, String nvaCreacion, String indTransf, String valAutorizado) {
        boolean resultado = false;
        // TODO: AUTORIZACION
        resultado = super.getResultSQLInsertMovEstimacion(oficio, movOficioEst.getYear(),
                movOficioEst.getRamo(), movOficioEst.getMeta(), movOficioEst.getPeriodo(), movOficioEst.getValor(), nvaCreacion, indTransf, valAutorizado);
        return resultado;
    }

    public boolean saveMovimientoAccionEstimacionMeta(int folio, MovOficioAccionEstimacion movOficioAccionEst, String nvaCreacion, String indTransf, String valAutorizado) {
        boolean resultado = false;
        // TODO: AUTORIZACION
        resultado = super.getResultSQLInsertMovAccionEstimacion(folio, movOficioAccionEst.getYear(),
                movOficioAccionEst.getRamo(), movOficioAccionEst.getMeta(),
                movOficioAccionEst.getAccion(), movOficioAccionEst.getPeriodo(), movOficioAccionEst.getValor(), nvaCreacion, indTransf, valAutorizado);
        return resultado;
    }

    public boolean insertMovimientoAccionRecEstimacion(int folio, int year, String ramo, String prg, String depto, int meta, int accion,
            int req, String descReq, String fuente, String fondo, String recurso, String tipoGasto, String partida,
            String relLaboral, double cantidad, double costoUnitario, double costoAnual, double ene, double feb, double mar,
            double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic,
            String articulo, String justificacion, int consec) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLInsertMovAccionReqEstimacion(folio, year, ramo, prg,
                    depto, meta, accion, req, descReq, fuente, fondo, recurso, tipoGasto, partida,
                    relLaboral, cantidad, costoUnitario, costoAnual, ene, feb, mar, abr,
                    may, jun, jul, ago, sep, oct, nov, dic, articulo, "S", justificacion, consec,"S");
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public String saveTransferencias(int year, int folio, List<TransferenciaAccionReq> transferenciaAccionReqList) {
        boolean resultado = false;
        String mensaje = new String();
        for (TransferenciaAccionReq transferenciaAccionReq : transferenciaAccionReqList) {
            resultado = this.saveMovimientoInsertarTransferencias(folio, transferenciaAccionReq.getConsecutivo(), transferenciaAccionReq.getRamo(), transferenciaAccionReq.getDepto(),
                    transferenciaAccionReq.getFinalidad(), transferenciaAccionReq.getFuncion(), transferenciaAccionReq.getSubfuncion(), transferenciaAccionReq.getPrgConac(),
                    transferenciaAccionReq.getPrograma(), transferenciaAccionReq.getTipoProy(), transferenciaAccionReq.getProy(), transferenciaAccionReq.getMeta(),
                    transferenciaAccionReq.getAccion(), transferenciaAccionReq.getPartida(), transferenciaAccionReq.getTipoGasto(), transferenciaAccionReq.getFuente(),
                    transferenciaAccionReq.getFondo(), transferenciaAccionReq.getRecurso(), transferenciaAccionReq.getMunicipio(), transferenciaAccionReq.getDelegacion(),
                    transferenciaAccionReq.getRelLaboral(), transferenciaAccionReq.getImporte(), transferenciaAccionReq.getTipoMovTransf(), transferenciaAccionReq.getAccionReq());
            if (resultado) {
                if(transferenciaAccionReq.getMovOficioAccionReq().getJustificacion() ==  null ||  transferenciaAccionReq.getMovOficioAccionReq().getJustificacion().contains("NULL"))
                            transferenciaAccionReq.getMovOficioAccionReq().setJustificacion(new String());
                resultado = this.insertMovimientoAccionRecEstimacion(folio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getPrograma(),
                        transferenciaAccionReq.getDepto(), transferenciaAccionReq.getMeta(), transferenciaAccionReq.getAccion(), transferenciaAccionReq.getAccionReq(),
                        transferenciaAccionReq.getMovOficioAccionReq().getReqDescr(), transferenciaAccionReq.getFuente(), transferenciaAccionReq.getFondo(),
                        transferenciaAccionReq.getRecurso(), transferenciaAccionReq.getTipoGasto(), transferenciaAccionReq.getPartida(), transferenciaAccionReq.getRelLaboral(),
                        transferenciaAccionReq.getMovOficioAccionReq().getCantidad(), transferenciaAccionReq.getMovOficioAccionReq().getCostoUnitario(),
                        transferenciaAccionReq.getMovOficioAccionReq().getCostoAnual(), transferenciaAccionReq.getMovOficioAccionReq().getEne(),
                        transferenciaAccionReq.getMovOficioAccionReq().getFeb(), transferenciaAccionReq.getMovOficioAccionReq().getMar(), transferenciaAccionReq.getMovOficioAccionReq().getAbr(),
                        transferenciaAccionReq.getMovOficioAccionReq().getMay(), transferenciaAccionReq.getMovOficioAccionReq().getJun(), transferenciaAccionReq.getMovOficioAccionReq().getJul(),
                        transferenciaAccionReq.getMovOficioAccionReq().getAgo(), transferenciaAccionReq.getMovOficioAccionReq().getSep(), transferenciaAccionReq.getMovOficioAccionReq().getOct(),
                        transferenciaAccionReq.getMovOficioAccionReq().getNov(), transferenciaAccionReq.getMovOficioAccionReq().getDic(), transferenciaAccionReq.getMovOficioAccionReq().getArticulo(),
                        transferenciaAccionReq.getMovOficioAccionReq().getJustificacion(),transferenciaAccionReq.getMovOficioAccionReq().getConsec());
                if (!resultado) {
                    mensaje = "3|Ocurrió un error al insertar los registros de transfrec";
                    break;
                }
            } else {
                mensaje = "3|Ocurrió un error al insertar los registros de transferencia";
                break;
            }
        }
        return mensaje;
    }

    public boolean saveMovimientoInsertarTransferencias(int oficio, int consecutivo, String ramo, String depto, String finalidad, String funcion,
            String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral,
            double importe, String tipoMov, int requ) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLInsertTransfrec(oficio, consecutivo, ramo, depto, finalidad, funcion, subfuncion,
                    prgConac, programa, tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso,
                    municipio, delegacion, relLaboral, importe, tipoMov, requ);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean getTipoOficioByPartida(int year, String partida) {
        String tipoOficio = new String();
        boolean resultado = false;
        try {
            tipoOficio = super.getResultSQLGetTipoOficioByPartida(year, partida);
            if (tipoOficio.equals("S")) {
                resultado = true;
            } else {
                resultado = false;
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public double getAsignadoPPTOByMes(int year, String ramo, String prg, String tipoProy, int proy,
            int meta, int accion, String partida, String fuente, String fondo, String recurso, String relLaboral, int mes) {
        double asignado = 0.0;
        try {
            asignado = super.getResultSQLgetAsignadoByMes(year, ramo, prg, tipoProy, proy,
                    meta, accion, partida, fuente, fondo, recurso, relLaboral, mes);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return asignado;
    }

    public boolean updatePPTOreduccion(double importe, int year, String ramo, String depto, String finanlidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida, String tipoGasto,
            String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral, int mes) {
        boolean resultado = false;
        resultado = super.getResultSQLupdatePPTOreduccion(importe, year, ramo, depto, finanlidad, funcion, subfuncion, prgConac,
                programa, tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLaboral, mes);
        return resultado;
    }

    public boolean insertOficons(int folio, int consecutivo, String tipo) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertOficons(folio, consecutivo, tipo);
        return resultado;
    }

    public boolean getValidaContabilidad(int ejercicio) {
        boolean resultado = false;
        int ejercicioActivoConta = 0;
        Calendar calFechaAplicacionGasto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        Date fechaAplicacionGasto = null;
        Date fechaActual = null;

        try {
            ejercicioActivoConta = super.getResultSQLGetEjercicioActivoConta();
            fechaAplicacionGasto = super.getResultSQLGetFechaAplicacionGasto(ejercicio);
            if(fechaAplicacionGasto != null){
                calFechaAplicacionGasto.setTime(fechaAplicacionGasto);

                if (ejercicio == ejercicioActivoConta && ejercicio == calFechaAplicacionGasto.get(Calendar.YEAR)) {
                    fechaActual = super.getResultSQLgetServerDate();
                    calFechaActual.setTime(fechaActual);

                    if (calFechaAplicacionGasto.get(Calendar.MONTH) != calFechaActual.get(Calendar.MONTH)) {
                        resultado = true;
                    }
                }
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean isUsuarioCapturaEspecial(String appLogin) {
        boolean isUsuarioCapturaEspecial = false;
        try {
            isUsuarioCapturaEspecial = super.getResultSQLisUsuarioEspacialCaptura(appLogin);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (NumberFormatException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isUsuarioCapturaEspecial;
    }

    public String cambiaEstatusMovOficioTransferencia(MovimientosTransferencia movtoTransferencia, int folio, boolean isActual, 
            String tipoMov, String tipoUsuario, String estatus,int year, int mes, String fecha, String capturaEspecial, String tipoficio) {
        List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
        List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
        MensajeError objMensaje = new MensajeError();        
        
        Movimiento objMovimiento;
        Calendar calFechaElab = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        
        String flujo = new String();
        String mensaje = new String();
        String msjRes = new String();
        boolean resultado = false;
        boolean resultadoFinal = false;
        int tipoFlujo = 0;
        try {
            
            objMovimiento = super.getResultMovimientoByFolio(folio);

            if(!capturaEspecial.equals("S"))
                calFechaActual.setTime(super.getResultSQLgetServerDate()); 
            else{
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
                calFechaActual.setTime(format.parse(fecha));
            }
            calFechaElab.setTime(objMovimiento.getFechaElab());
            
            transferenciaList = movtoTransferencia.getTransferenciaList();
            if (estatus.equals("R")) {
                
                tipoFlujo = super.getResultTipoFlujoInicial(tipoMov, tipoUsuario);
                
                flujo = super.getResultSQLEstatusBaseByTipoFlujo(tipoFlujo);
                resultado = super.getResultSQLUpdateEstatusTransferencia(flujo, folio);
                //resultado = true;
            } else if (estatus.equals("K")) {
                tipoFlujo = 3;
                flujo = super.getResultSQLEstatusBaseByTipoFlujo(tipoFlujo);
                resultado = super.getResultSQLUpdateTransferenciaByTipoOficio(flujo, folio, tipoficio);
            } else {
                flujo = this.getEstatusMovimiento(tipoMov, tipoUsuario, estatus);
                resultado = super.getResultSQLUpdateEstatusTransferencia(flujo, folio);
                //resultado = true;
            }
            if (!flujo.isEmpty()) {
                if (resultado) {
                    if(isParaestatal()){
                        for(Transferencia trans : movtoTransferencia.getTransferenciaList()){                        
                            for(TransferenciaAccionReq movof : trans.getTransferenciaAccionReqList()){
                                movof.getMovOficioAccionReq().setOficio(String.valueOf(folio));
                                if (!super.getResultSQLAjustarAcumuladoMesesAnteriores(movof.getMovOficioAccionReq(),
                                        movof.getMeta(), movof.getAccion(), calFechaActual.get(Calendar.MONTH) + 1)) {
                                    mensaje = "Error al ajustar el acumulado de meses anteriores.";
                                    resultado = false;
                                    break;
                                }                        
                            }
                        }
                    }
                    if(resultado)
                        resultado = this.updateMovimientoOficio(folio, flujo, fecha, isActual, capturaEspecial);                    
                    if (!resultado) {
                        mensaje = "Error al actualizar el movimiento";
                    }
                }
            } else {
                mensaje = "Error al obtener el flujo de autorización";
            }
            if (resultado) {
                if (estatus.equals("R") || estatus.equals("Z")) {
                    resultado = this.deleteTipoOficioOficons(folio);
                } else if (estatus.equals("K")) {
                    resultado = this.deleteTipoOficioOficonsByTipoOficio(folio, tipoficio);
                } else {
                    resultado = true;
                }
                if (resultado) {
                    for (Transferencia transferencia : transferenciaList) {
                        if(flujo.equals("W") || estatus.equals("K")){
                            resultado = this.updatePPTOreduccion(transferencia.getImporte() * -1, year, transferencia.getRamo(), transferencia.getDepto(),
                                transferencia.getFinalidad(), transferencia.getFuncion(), transferencia.getSubfuncion(), transferencia.getPrgConac(),
                                transferencia.getPrograma(), transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(),
                                transferencia.getAccion(), transferencia.getPartida(), transferencia.getTipoGasto(), transferencia.getFuente(),
                                transferencia.getFondo(), transferencia.getRecurso(), transferencia.getMunicipio(), transferencia.getDelegacion(),
                                transferencia.getRelLaboral(), calFechaElab.get(Calendar.MONTH) + 1);
                            if (!resultado) {
                                super.transaccionRollback();
                                break;
                            }
                        }
                        if (resultado) {
                            resultado = this.insertOficons(folio, transferencia.getConsec(), transferencia.getTipoOficio());
                        }
                    }
                }
                if (resultado) {
                    if (estatus.equals("K")) {
                        tipoOficioList = this.getTipoOficioByFolioTipo(folio, tipoficio);
                    } else {
                        tipoOficioList = this.getTipoOficioByFolio(folio);
                    }
                    if(!tipoOficioList.isEmpty()){
                        for (TipoOficio tipoOficio : tipoOficioList) {
                            resultado = this.insertTipoOficio(tipoOficio.getOficio(), tipoOficio.getTipoOficio(), flujo);
                            if (!resultado) {
                                break;
                            }else{
                                resultadoFinal = true;
                            }
                        }
                    }else{
                        resultado = false;
                        mensaje = "2|Ocurrió un error al insertar el tipo oficio";
                    }
                }
            }
            if (resultado) {
                if (estatus.equals("R")) {
                    resultado = super.getResultDeleteBitMovtoByOficio(folio);
                } else if (estatus.equals("K")) {
                    resultado = super.getResultDeleteBitMovtoByOficioTipoOficio(folio, tipoficio, tipoFlujo);
                   /* if(resultado){
                        //saveMovimientoAmpliacionReduccion(MovimientosAmpliacionReduccion movAmplRed, int folio, boolean isActual,
                        //int year, String fecha, String justificacion, String capturaEspecial, String ramoSession, long selCaratula)
                        msjRes = this.saveMovimientoTransferencia(movtoTransferencia,0,isActual,year,fecha,capturaEspecial,
                                movtoTransferencia.getMovCaratula().getsRamo(),movtoTransferencia.getMovCaratula().getsIdCaratula(),
                                tipoficio,movtoTransferencia.getJutificacion());
                        if(msjRes.contains("$")){
                            movtoTransferencia.setOficio(Integer.parseInt(msjRes.replace("$", "").trim()));
                            mensaje = this.cambiaEstatusMovOficioTransferencia(movtoTransferencia, movtoTransferencia.getOficio(), 
                                    isActual, tipoMov, tipoUsuario, "X", year, mes, fecha, capturaEspecial, tipoficio);
                            if(mensaje.startsWith("1")){
                                resultado = this.getCancelaRechazaTransferenciaTipoOficio(folio, tipoUsuario, movtoTransferencia.getAppLogin()
                                        , "SE GENERÓ UN FOLIO NUEVO - "+ movtoTransferencia.getOficio(),tipoficio, "C", tipoFlujo, movtoTransferencia, objMensaje);
                                if(resultado)
                                    resultado = super.getResultSQLUpdateFolioRelacionado(folio, tipoficio,movtoTransferencia.getOficio() );
                            }else
                                resultado = false;
                        }else{
                            resultado = false;
                        }
                    }else{
                        resultado = false;
                    }*/
                }
            }
            if (resultadoFinal) {
            //if (false) {
                if(super.getResultSQLCountTipoficioByFolio(folio) > 0 && super.getResultSQLCountOficonsByFolio(folio) > 0){
                    super.transaccionCommit();   
                    mensaje = "1|El registro se actualizó correctamente";
                }else{
                    super.transaccionRollback();
                    mensaje = "2|Ocurrió un error al actualizar el estatus de oficio";
                }
                //super.transaccionRollback();
            } else {
                super.transaccionRollback();
                mensaje = "2|Ocurrió un error al actualizar el estatus de oficio";
            }
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
            mensaje = "2|Ocurrió un error al actualizar el estatus de oficio";
        }
        return mensaje;
    }
    
    public boolean getCancelaRechazaTransferenciaTipoOficio(int oficio, String tipoUsr, String appLogin, String motivo, String tipoOficio, String estatusMov,
            int tipoFlujo, MovimientosTransferencia movTransferencia, MensajeError objMensaje) {
        TipoOficio objMovimiento;
        boolean bolResultado = false;
        boolean bolGrabo = true;
        Calendar calFechaReduccion = new GregorianCalendar();

        try {
            objMovimiento = super.getResultMovimientoByFolioTipoOficio(oficio, tipoOficio);

            if (super.getResultSQLUpdateEstatusMotivoMovTipoOficio(estatusMov, motivo, oficio, tipoOficio)) {
                if (super.getResultSQLUpdateEstatusTransferenciaTipoOficio(estatusMov, oficio, tipoOficio)) {
                    if (super.getResultInsertBitMovto(oficio, appLogin, estatusMov, "N", tipoOficio, "", tipoFlujo, tipoUsr, "")) {

                        //Regresa el valor tomado del ppto en las reducciones
                        Iterator iterator = (Iterator) movTransferencia.getTransferenciaList().iterator();
                        while (bolGrabo && iterator.hasNext()) {
                            Transferencia transferencia = (Transferencia) iterator.next();

                            //Se afecta el ACTUALIZADO en PPTO 
                            calFechaReduccion.setTime(objMovimiento.getFecPPTO());
                            bolGrabo = super.getResultSQLupdatePPTOreduccion(transferencia.getImporte(), calFechaReduccion.get(Calendar.YEAR),
                                    transferencia.getRamo(), transferencia.getDepto(), transferencia.getFinalidad(), transferencia.getFuncion(),
                                    transferencia.getSubfuncion(), transferencia.getPrgConac(), transferencia.getPrograma(), transferencia.getTipoProy(),
                                    transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(), transferencia.getPartida(),
                                    transferencia.getTipoGasto(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                                    transferencia.getMunicipio(), transferencia.getDelegacion(), transferencia.getRelLaboral(),
                                    calFechaReduccion.get(Calendar.MONTH) + 1);

                            if(!bolGrabo){
                                objMensaje.setMensaje("Error al devolver el importe al presupuesto");
                            }

                        }

                        if (bolGrabo) {
                            if (getProcesosTipoOficioByOficio(oficio, appLogin, motivo, tipoFlujo, tipoUsr, "T", tipoOficio, objMensaje)) {
                                bolResultado = true;
                            }
                        }
                    }
                    else{
                        objMensaje.setMensaje("Error al insertar en la bit\u00e1cora del movimiento");
                    }
                }
                else{
                    objMensaje.setMensaje("Error al actualizar el estatus de transferencias");
                }
            }
            else{
                objMensaje.setMensaje("Error al actualizar el estatus del tipo de oficio");
            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de sql al cancelar/rechazar la transferencia por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado al cancelar/rechazar la transferencia por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return bolResultado;
    }

     public boolean getProcesosTipoOficioByOficio(int oficio, String appLogin, String motivo, int tipoFlujo, String tipoUsr, String tipoMovto, String tipoOficio, MensajeError objMensaje) throws SQLException {
        boolean bolGrabo = true;
        boolean bolResultado = false;
        List<TipoOficio> tipoOficioList;
        boolean bolTodosCancelados = true;
        boolean bolTodosAutorizados = true;
        boolean bolTodosRechazadosTipoOficio = true;
        String estatus = new String();

        //Revisar estatus de los tipoficio del movoficios si todos son <> T
        //Si todos los tipo oficio = C --> movoficios.estatus = C
        //Si por lo menos uno es = A --> movoficios.estatus = A
        if (super.getResultSQLCountTipoOficioByOficioEstatus(oficio, "T") == 0) {

            if (super.getResultSQLCountTipoOficioByOficioEstatus(oficio, "A") > 0) {
                bolGrabo = super.getResultSQLUpdateEstatusMov("A", oficio);
                if(!bolGrabo){
                    objMensaje.setMensaje("Error al actualizar a autorizado el estatus del movimiento");
                }
            }

            if (bolGrabo) {
                tipoOficioList = super.getResultSQLGetStatusTipoOficioByOficio(oficio);
                for (TipoOficio tipOf : tipoOficioList) {
                    if (!tipOf.getStatusTipoOficio().equals("C")) {
                        bolTodosCancelados = false;
                    }
                    if (!tipOf.getStatusTipoOficio().equals("A")) {
                        bolTodosAutorizados = false;
                    }
                    if (!tipOf.getStatusTipoOficio().equals("K")) {
                        bolTodosRechazadosTipoOficio = false;
                    }
                }


                if (bolTodosCancelados || bolTodosAutorizados) {

                    if (bolTodosCancelados) {
                        estatus = "C";
                        bolGrabo = super.getResultSQLUpdateEstatusMov("C", oficio);
                        if(!bolGrabo){
                            objMensaje.setMensaje("Error al actualizar a cancelado el estatus del movimiento");
                        }
                    }

                    if (bolTodosAutorizados) {

                        estatus = "A";

                        if (bolGrabo && super.getResultSQLCountMovoficiosMetasEditadas(oficio) > 0) {
                            bolGrabo = super.getResultInsertHistMeta(oficio);
                            if (bolGrabo) {
                                bolGrabo = super.getResultUpdateReprogramacionMeta(oficio);
                                if (bolGrabo) {
                                    bolGrabo = super.getResultInsertHistCalendarizacionMeta(oficio);
                                    if (bolGrabo) {
                                        if(super.getResultUpdateCalendarizacionMeta(oficio)){
                                            bolGrabo = true;
                                        }
                                        else{
                                            objMensaje.setMensaje("Error al insertar la calendarizaci\u00f3n las metas editadas");
                                        }
                                    }
                                    else{
                                        objMensaje.setMensaje("Error al insertar el hist\u00f3rico de la calendarizaci\u00f3n las metas editadas");
                                    }
                                }
                                else{
                                    objMensaje.setMensaje("Error al actualizar metas editadas");
                                }
                            }
                            else{
                                objMensaje.setMensaje("Error al insertar el hist\u00f3rico de las metas editadas");
                            }
                        }

                        if (bolGrabo && super.getResultSQLCountMovoficiosAccionEditadas(oficio) > 0) {
                            bolGrabo = super.getResultInsertHistAccion(oficio);
                            if (bolGrabo) {
                                bolGrabo = super.getResultUpdateReprogramacionAccion(oficio);
                                if (bolGrabo) {
                                    bolGrabo = super.getResultInsertHistCalendarizacionAccion(oficio);
                                    if (bolGrabo) {
                                        if(super.getResultUpdateCalendarizacionAccion(oficio)){
                                            bolGrabo = true;
                                        }
                                        else{
                                            objMensaje.setMensaje("Error al insertar la calendarizaci\u00f3n las acciones editadas");
                                        }
                                    }
                                    else{
                                        objMensaje.setMensaje("Error al insertar el hist\u00f3rico de la calendarizaci\u00f3n las acciones editadas");
                                    }
                                }
                                else{
                                    objMensaje.setMensaje("Error al actualizar las acciones editadas");
                                }
                            }
                            else{
                                objMensaje.setMensaje("Error al insertar el hist\u00f3rico de las acciones editadas");
                            }
                        }
                    }

                    //Actualizar estatus de SINVP
                    if (bolGrabo) {
                        if (super.getResultSQLValidaOficioSINVP(oficio)) {
                            if (super.getRestulSQLCallProcedureINVP(oficio, estatus)) {
                                bolResultado = true;
                            }
                            else{
                                objMensaje.setMensaje("Error al ejecutar el procedimiento de INVP");
                            }
                        } else {
                            bolResultado = true;
                        }
                    }

                } else if(bolTodosRechazadosTipoOficio){
                 
                    if (super.getResultSQLUpdateEstatusMotivoMov("R", motivo, oficio)) {
                        if (super.getResultSQLUpdateEstatusTipoOficio("R", oficio)) {
                            if(tipoMovto.equals("A")){//Ampliación
                                bolGrabo = super.getResultSQLUpdateEstatusAmpliaciones("R", oficio);
                                if(!bolGrabo){
                                    objMensaje.setMensaje("Error al actualizar el estatus en ampliaciones");
                                }
                            } else if(tipoMovto.equals("T")){//Transferencia
                                bolGrabo = super.getResultSQLUpdateEstatusTransferencia("R", oficio);
                                if(!bolGrabo){
                                    objMensaje.setMensaje("Error al actualizar el estatus en transferencias");
                                }
                            }
                            if (bolGrabo) {
                                if (super.getResultSQLgetUpdateAutorizoBitmovtos(oficio, appLogin, "R", tipoFlujo, tipoUsr)) {
                                    bolResultado = true;
                                }
                                else{
                                    objMensaje.setMensaje("Error insertar en la bit\u00e1cora");
                                }
                            }
                        }
                        else{
                            objMensaje.setMensaje("Error al actualizar el estatus de tipo oficio");
                        }
                    }
                    else{
                        objMensaje.setMensaje("Error al actualizar el estatus del movimiento");
                    }
                }
                else {
                    bolResultado = true;
                }
            }
        } else {
            bolResultado = true;
        }

        return bolResultado;
    }
    
    public boolean updateMovimientoOficio(int oficio, String estatus, String fecha, boolean isActual, String capturaEspecial) {
        boolean resultado = false;
        resultado = super.getResultSQLupdateMovOficio(oficio, estatus, fecha, isActual, capturaEspecial);
        return resultado;
    }

    public List<TipoOficio> getTipoOficioByFolioTipo(int folio, String tipoficio) {
        List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
        try {
            tipoOficioList = super.getResultSQLgetTipoficioByFolioTipo(folio, tipoficio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoOficioList;
    }

    public boolean deleteTipoOficioOficons(int folio) {
        boolean resultado = false;

        try {
            resultado = super.getResultSQLDeleteTipoOficio(folio);
            if (resultado) {
                resultado = super.getResultSQLDeleteOfiCons(folio);
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean deleteTipoOficioOficonsByTipoOficio(int folio, String tipoOficio) {
        boolean resultado = false;

        try {
            resultado = super.getResultSQLDeleteTipoOficioByTipoOficio(folio, tipoOficio);
            if (resultado) {
                resultado = super.getResultSQLDeleteOfiConsByTipoOficio(folio, tipoOficio);
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }
    /*
     public String cambiaEstatusMovOficioTransferencia(int folio, String tipoMov, String tipoUsuario, String estatus, List<Transferencia> transferenciaList, int year, int mes) {
     List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
     String flujo = new String();
     String mensaje = new String();
     boolean resultado = false;
     try {
     flujo = this.getEstatusMovimiento(tipoMov, tipoUsuario, estatus);
     if (!flujo.isEmpty()) {
     resultado = this.updateMovimientoOficio(folio, flujo);
     if (!resultado) {
     mensaje = "Error al actualizar el movimiento";
     }
     } else {
     mensaje = "Error al obtener el flujo de autorización";
     }
     if (resultado) {
     for (Transferencia transferencia : transferenciaList) {
     resultado = this.updatePPTOreduccion(transferencia.getImporte(), year, transferencia.getRamo(), transferencia.getDepto(),
     transferencia.getFinalidad(), transferencia.getFuncion(), transferencia.getSubfuncion(), transferencia.getPrgConac(),
     transferencia.getPrograma(), transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(),
     transferencia.getAccion(), transferencia.getPartida(), transferencia.getTipoGasto(), transferencia.getFuente(),
     transferencia.getFondo(), transferencia.getRecurso(), transferencia.getMunicipio(), transferencia.getDelegacion(),
     transferencia.getRelLaboral(), mes);
     if (!resultado) {
     super.transaccionRollback();
     break;
     }
     if (resultado) {
     resultado = this.insertOficons(folio, transferencia.getConsecutivo(), transferencia.getTipoMovimiento());
     }
     }
     if (resultado) {
     tipoOficioList = this.getTipoOficioByFolio(folio);
     for (TipoOficio tipoOficio : tipoOficioList) {
     resultado = this.insertTipoOficio(tipoOficio.getOficio(), tipoOficio.getTipoOficio(), flujo);
     if (!resultado) {
     break;
     }
     }
     }
     }
     if (resultado) {
     super.transaccionCommit();
     mensaje = "El registro se actualizó correctamente";
     } else {
     super.transaccionRollback();
     mensaje = "Ocurrió un error al actualizar el estatus de oficio";
     }
     } catch (Exception ex) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(ex, new Throwable());
     bitacora.grabaBitacora();
     }
     return mensaje;
     }
     public boolean updateMovimientoOficio(int oficio, String estatus) {
     boolean resultado = false;
     resultado = super.getResultSQLupdateMovOficio(oficio, estatus);
     return resultado;
     }
     */

    public String getEstatusMovimiento(String tipoMov, String tipoUsuario, String estatus) {
        EstatusMov estatusMov = new EstatusMov();
        String flujo = new String();
        try {
            estatusMov = super.getResultEstatusSiguiente(tipoMov, tipoUsuario, estatus);
            flujo = estatusMov.getEstatusMovId();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return flujo;
    }

    public List<TipoOficio> getTipoOficioByFolio(int folio) {
        List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
        try {
            tipoOficioList = super.getResultSQLgetTipoficioByFolio(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoOficioList;
    }

    public boolean insertTipoOficio(int oficio, String tipo, String estatus) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertTipoOficio(oficio, tipo, estatus);
        return resultado;
    }

    public int isPartidaJustific(int year, String partida) {
        int justific = 0;
        try {
            justific = super.getResultSLQisPartidaJustific(year, partida);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return justific;
    }

    public String getTipoGastoByPartida(String partida, int year) {
        String tipoGasto = new String();
        try {
            tipoGasto = super.getResultSQLgetTipoDeGastoByPartida(partida, year);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoGasto;
    }

    public boolean isRelLaboral(int year, String partida) {
        boolean isRelLaboral = false;
        try {
            isRelLaboral = super.getResultIsRelacionLaboral(year, partida);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return isRelLaboral;
    }

    public List<Articulo> getArticuloPartida(int year, String partida) {
        List<Articulo> articuloList = new ArrayList<Articulo>();
        try {
            articuloList = super.getResultSQLArticuloPartida(year, partida);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return articuloList;
    }

    public boolean isArticuloPartida(int year, String partida) {
        boolean resultado = false;
        int cont = -1;
        try {
            cont = super.getResultSQLisArticuloPartida(year, partida);
            if (cont < 1) {
                resultado = true;
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public int[] getMesesTrimestreByMes(int mes, boolean validaTrimestre) {
        int meses[] = new int[3];
        try {
            meses = super.getResultSQLgetMesTrimestreByPeriodo(mes,validaTrimestre);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return meses;
    }

    public MovimientosTransferencia getMovimientosTransferenciaByTipoOficio(int folio, int year, int mesActual,
            String tipoOficio, String ramoSession) {
        MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
        Transferencia transferenciaTemp;
        try {
            movTransferencia = this.getInfoMovOficioTransferencia(folio);
            movTransferencia.setMovCaratula(this.getCaratulaAmpliacionReduccion(year, movTransferencia.getRamo(), folio));
            movTransferencia.setTransferenciaMetaList(this.getMovOficioMeta(folio, year, "T"));
            movTransferencia.setTransferenciaAccionList(this.getMovOficioAccion(folio, year, "T"));
            movTransferencia.setTransferenciaList(super.getTransferenciaByTipoOficio(folio, tipoOficio, year));
            movTransferencia.setFolioRelacionado(super.getResultSLQGetFolioRelacionado(folio));
            for (Transferencia transferencia : movTransferencia.getTransferenciaList()) {
                transferenciaTemp = new Transferencia();
                transferencia.setAcumulado(super.getResultSQLgetAcumluladoMovtos(year,transferencia.getPartida(),transferencia.getRamo()));
                transferenciaTemp = this.getMovimientosTransferenciaReciben(folio, year, mesActual, transferencia.getConsec());
                transferencia.setDisponible(this.getDisponible(year, transferencia.getRamo(), transferencia.getPrograma(),
                        transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(),
                        transferencia.getPartida(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                        transferencia.getRelLaboral(), mesActual));
                transferencia.setDisponibleAnual(super.getResultSQLgetImporteAnual(year, transferencia.getRamo(), transferencia.getPrograma(),
                        transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(),
                        transferencia.getPartida(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                        transferencia.getRelLaboral(), 12));
                transferencia.setQuincePor(this.getQuincePorCiento(transferencia.getPartida(), transferencia.getRamo(), year));
                transferencia.setTransferenciaAccionReqList(transferenciaTemp.getTransferenciaAccionReqList());
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movTransferencia;
    }
    public MovimientosTransferencia getMovimientosTransfRechazadaByTipoOficio(int folio, int year, int mesActual,
            String tipoOficio, String ramoSession) {
        MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
        Transferencia transferenciaTemp;
        try {
            movTransferencia = this.getInfoMovOficioTransferencia(folio);
            movTransferencia.setMovCaratula(this.getCaratulaAmpliacionReduccion(year, movTransferencia.getRamo(), folio));
            movTransferencia.setTransferenciaList(super.getTransferenciaByTipoOficio(folio, tipoOficio, year));
            movTransferencia.setFolioRelacionado(super.getResultSLQGetFolioRelacionado(folio));
            for (Transferencia transferencia : movTransferencia.getTransferenciaList()) {
                transferenciaTemp = new Transferencia();
                transferencia.setAcumulado(super.getResultSQLgetAcumluladoMovtos(year,transferencia.getPartida(),transferencia.getRamo()));
                transferenciaTemp = this.getMovimientosTransferenciaReciben(folio, year, mesActual, transferencia.getConsec());
                transferencia.setDisponible(this.getDisponible(year, transferencia.getRamo(), transferencia.getPrograma(),
                        transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(),
                        transferencia.getPartida(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                        transferencia.getRelLaboral(), mesActual));
                transferencia.setDisponibleAnual(super.getResultSQLgetImporteAnual(year, transferencia.getRamo(), transferencia.getPrograma(),
                        transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(),
                        transferencia.getPartida(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                        transferencia.getRelLaboral(), 12));
                transferencia.setQuincePor(this.getQuincePorCiento(transferencia.getPartida(), transferencia.getRamo(), year));
                transferencia.setTransferenciaAccionReqList(transferenciaTemp.getTransferenciaAccionReqList());
            }
            
            movTransferencia.setTransferenciaMetaList(this.getMovOficioRechazoMeta(folio, year, movTransferencia.getTransferenciaList()));
            movTransferencia.setTransferenciaAccionList(this.getMovOficioRechazoAccion(folio, year, movTransferencia.getTransferenciaList()));
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movTransferencia;
    }

    //Codigos Transfieren
    public MovimientosTransferencia getMovimientosTransferencia(int folio, int year, int mesActual, String ramoSession) {
        MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
        Transferencia transferenciaTemp;
        try {
            movTransferencia = this.getInfoMovOficioTransferencia(folio);
            movTransferencia.setMovCaratula(this.getCaratulaAmpliacionReduccion(year, movTransferencia.getRamo(), folio));
            movTransferencia.setTransferenciaMetaList(this.getMovOficioMeta(folio, year, "T"));
            movTransferencia.setTransferenciaAccionList(this.getMovOficioAccion(folio, year, "T"));
            movTransferencia.setTransferenciaList(super.getTransferenciasByFolio(folio, year));
            movTransferencia.setFolioRelacionado(super.getResultSLQGetFolioRelacionado(folio));
            for (Transferencia transferencia : movTransferencia.getTransferenciaList()) {
                transferenciaTemp = new Transferencia();
                transferenciaTemp = this.getMovimientosTransferenciaReciben(folio, year, mesActual, transferencia.getConsec());
                if (this.getTipoOficioByPartida(year, transferencia.getPartida())) {
                    transferencia.setTipoMovimiento("V");
                } else {
                    transferencia.setTipoMovimiento("A");
                }
                transferencia.setAcumulado(super.getResultSQLgetAcumluladoMovtos(year,transferencia.getPartida(),transferencia.getRamo()));
                transferencia.setDisponible(this.getDisponible(year, transferencia.getRamo(), transferencia.getPrograma(),
                        transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(),
                        transferencia.getPartida(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                        transferencia.getRelLaboral(), mesActual));
                transferencia.setDisponibleAnual(super.getResultSQLgetImporteAnual(year, transferencia.getRamo(), transferencia.getPrograma(),
                        transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(),
                        transferencia.getPartida(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                        transferencia.getRelLaboral(), 12));
                transferencia.setQuincePor(this.getQuincePorCiento(transferencia.getPartida(), transferencia.getRamo(), year));
                transferencia.setTransferenciaAccionReqList(transferenciaTemp.getTransferenciaAccionReqList());
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movTransferencia;
    }
    //Codigos reciben

    public Transferencia getMovimientosTransferenciaReciben(int folio, int year, int mesActual, int consec) {
        Transferencia transferencia = new Transferencia();
        try {
            transferencia.setTransferenciaAccionReqList(this.getMovOficioAccionRequerimiento(folio, year, consec));
            for (TransferenciaAccionReq transAccionReq : transferencia.getTransferenciaAccionReqList()) {
                transAccionReq.setDisponible(this.getDisponible(year, transAccionReq.getRamo(), transAccionReq.getPrograma(),
                        transAccionReq.getTipoProy(), transAccionReq.getProy(), transAccionReq.getMeta(), transAccionReq.getAccion(),
                        transAccionReq.getPartida(), transAccionReq.getFuente(), transAccionReq.getFondo(), transAccionReq.getRecurso(),
                        transAccionReq.getRelLaboral(), mesActual));
                transAccionReq.setDisponibleAnual(super.getResultSQLgetImporteAnual(year, transAccionReq.getRamo(), transAccionReq.getPrograma(),
                        transAccionReq.getTipoProy(), transAccionReq.getProy(), transAccionReq.getMeta(), transAccionReq.getAccion(),
                        transAccionReq.getPartida(), transAccionReq.getFuente(), transAccionReq.getFondo(), transAccionReq.getRecurso(),
                        transAccionReq.getRelLaboral(), 12));
                transAccionReq.setAcumulado(super.getResultSQLgetAcumluladoMovtos(year,transAccionReq.getPartida(),transAccionReq.getRamo()));
                transAccionReq.setQuincePor(this.getQuincePorCiento(transAccionReq.getPartida(), transAccionReq.getRamo(), year));
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return transferencia;
    }

    public double getQuincePorCiento(String partida, String ramo, int year) {
        double monto = 0.0;
        double quincePorciento = 0.0;
        try {
            monto = super.getResultSQLgetquincePorciento(partida, ramo, year);
            quincePorciento = monto * .15;
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return quincePorciento;
    }

    public String getGrupoPartida(int year, String partida) {
        String grupo = new String();
        try {
            grupo = super.getResultSQLgetGrupoPartida(year, partida);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return grupo;
    }
    
    public String getTipoOficioTransferencia(int year, String partidaT,
            String ramoT, String partidaR, String ramoR, double quincePorT) {
        String tipoOficio = new String();
        double acumuladoMovtos = 0.0;
        double disponible = 0.0;
        try {
            if (partidaT.equals(partidaR) && ramoT.equals(ramoR)) {
                tipoOficio = "U";
                return tipoOficio;
            }
            if (ramoT.equals(ramoR) && (!partidaT.equals(partidaR))
                    && this.getGrupoPartida(year, partidaT).equals(this.getGrupoPartida(year, partidaR))) {
                
                acumuladoMovtos = super.getResultSQLgetAcumluladoMovtos(year, partidaT, ramoT);
                disponible = quincePorT - acumuladoMovtos;
                
                if (disponible <= 0) {
                    tipoOficio = "A";
                } else {
                    tipoOficio = "V";
                }
                return tipoOficio;
            }
            if (ramoT.equals(ramoR) && !this.getGrupoPartida(year, partidaT).equals(this.getGrupoPartida(year, partidaR))) {
                tipoOficio = "A";
                return tipoOficio;
            }
            if (!ramoT.equals(ramoR)) {
                tipoOficio = "A";
                return tipoOficio;
            }

        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoOficio;
    }

    public double getDisponible(int year, String ramo, String programa, String tipoProy,
            int proy, int meta, int accion, String partida, String fuente, String fondo,
            String recurso, String relLaboral, int mesActual) {
        double disponible = 0.0;
        double disponibleAnual = 0.0;
        double asignado = 0.0;
        double actualizado = 0.0;
        double compromiso = 0.0;
        double ejercido = 0.0;
        double montos[] = new double[4];
        try {
            CodigoPPTO codPPTO = new CodigoPPTO();
            codPPTO = super.getRestulSQLgetCodigoPPTOsinRequerimiento(year, ramo, programa,
                    tipoProy, proy, meta, accion, partida, fuente, fondo, recurso, relLaboral);
            if (codPPTO != null) {
                if (super.getResultSQLisTransferenciaAnual()) {
                    montos = super.getResultSQLgetDisponibleByRangoMes(year, codPPTO.getRamoId(), codPPTO.getDepto(),
                            codPPTO.getFinalidad(), codPPTO.getFuncion(), codPPTO.getSubfuncion(), codPPTO.getProgCONAC(),
                            codPPTO.getPrograma(), codPPTO.getTipoProy(), Integer.parseInt(codPPTO.getProyecto()), codPPTO.getMeta(),
                            codPPTO.getAccion(), codPPTO.getPartida(), codPPTO.getTipoGasto(), codPPTO.getFuente(),
                            codPPTO.getFondo(), codPPTO.getRecurso(), codPPTO.getMunicipio(), codPPTO.getDelegacion(),
                            codPPTO.getRelLaboral(), 1, 12);
                } else {
                    montos = super.getResultSQLgetDisponibleByRangoMes(year, codPPTO.getRamoId(), codPPTO.getDepto(),
                            codPPTO.getFinalidad(), codPPTO.getFuncion(), codPPTO.getSubfuncion(), codPPTO.getProgCONAC(),
                            codPPTO.getPrograma(), codPPTO.getTipoProy(), Integer.parseInt(codPPTO.getProyecto()), codPPTO.getMeta(),
                            codPPTO.getAccion(), codPPTO.getPartida(), codPPTO.getTipoGasto(), codPPTO.getFuente(),
                            codPPTO.getFondo(), codPPTO.getRecurso(), codPPTO.getMunicipio(), codPPTO.getDelegacion(),
                            codPPTO.getRelLaboral(), 1, mesActual);
                }
                asignado = montos[0];
                actualizado = montos[1];
                compromiso = montos[2];
                ejercido = montos[3];
                if (compromiso > ejercido) {
                    disponible = (actualizado + asignado) - compromiso;
                } else {
                    disponible = (actualizado + asignado) - ejercido;
                }
                
                montos = super.getResultSQLgetDisponibleByRangoMes(year, codPPTO.getRamoId(), codPPTO.getDepto(),
                            codPPTO.getFinalidad(), codPPTO.getFuncion(), codPPTO.getSubfuncion(), codPPTO.getProgCONAC(),
                            codPPTO.getPrograma(), codPPTO.getTipoProy(), Integer.parseInt(codPPTO.getProyecto()), codPPTO.getMeta(),
                            codPPTO.getAccion(), codPPTO.getPartida(), codPPTO.getTipoGasto(), codPPTO.getFuente(),
                            codPPTO.getFondo(), codPPTO.getRecurso(), codPPTO.getMunicipio(), codPPTO.getDelegacion(),
                            codPPTO.getRelLaboral(), 1, 12);
                
                asignado = montos[0];
                actualizado = montos[1];
                compromiso = montos[2];
                ejercido = montos[3];
                
                if (compromiso > ejercido) {
                    disponibleAnual = (actualizado + asignado) - compromiso;
                } else {
                    disponibleAnual = (actualizado + asignado) - ejercido;
                }
                
                if(disponibleAnual < disponible)
                    disponible = disponibleAnual;
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return disponible;
    }

    public String[] getRequerimientoByDatosMovtos(int year, String ramo, String programa,
            int meta, int accion, String partida, String relLaboral, String fuente, String fondo, String recurso) {
        String valor[] = new String[19];
        try {
            valor = super.getResultSQLgetRequerimientoByDatosMovtos(year, ramo, programa,
                    meta, accion, partida, relLaboral, fuente, fondo, recurso);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (NumberFormatException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return valor;
    }

    public String getTipoOficio(List<TransferenciaAccionReq> transAccionReqList, String ramo, String partida, int year,
            double quincePor,int oficio, String status) {
        String tipoOficio = new String();
        String grupoGasto = new String();
        BigDecimal acRamoPartida = new BigDecimal(0);
        boolean bandera = false;
        try {
           /**************se agrego porque no consideraba este criterio mandaba tipoOficio vacio*****************/
            //Sea mismo Ramo y diferente grupo de partida
            for (TransferenciaAccionReq transAccion : transAccionReqList) {
                grupoGasto = this.getGrupoPartida(year, partida);
                if(ramo.equals(transAccion.getRamo())) {
                    if(!grupoGasto.equals(this.getGrupoPartida(year, transAccion.getPartida()))){
                        return tipoOficio = "A";
                    }
                }
            }
           /**************************************************************************************************/
            for (TransferenciaAccionReq transAccion : transAccionReqList) {
                if (!ramo.equals(transAccion.getRamo())) {
                    return tipoOficio = "A";
                }
            }
            for (TransferenciaAccionReq transAccion : transAccionReqList) {
                if (ramo.equals(transAccion.getRamo()) && partida.equals(transAccion.getPartida())) {
                    bandera = true;
                }
            }
            if (bandera) {
                tipoOficio = "U";
            } else {
                grupoGasto = this.getGrupoPartida(year, partida);
                for (TransferenciaAccionReq transAccion : transAccionReqList) {
                    if (ramo.equals(transAccion.getRamo()) && grupoGasto.equals(this.getGrupoPartida(year, transAccion.getPartida()))) {
                        bandera = true;
                    }
                }
                if (bandera) {
                    tipoOficio = "V";
                    for(TransferenciaAccionReq transAccion : transAccionReqList){
                        if(status.equals("R"))
                            acRamoPartida = super.getResultSQLgetAcumluladoMovtosRechazado(year, partida, ramo, oficio);
                        else
                            if(status.equals("X"))
                                acRamoPartida = super.getResultSQLgetAcumuladoCaptura(year, oficio, ramo, partida);
                        BigDecimal bdQuincePor = super.getResultSQLgetQuincePorcientoTipoOficio(year,
                                transAccion.getRamo(),
                                transAccion.getPartida(),
                                acRamoPartida);
                        bdQuincePor = bdQuincePor.multiply(new BigDecimal(100));
                        if (bdQuincePor.compareTo(new BigDecimal(15)) > 0) {
                            tipoOficio = "A";
                            break;
                        }
                    }
                }else{
                    tipoOficio = "A";
                }
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (NumberFormatException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoOficio;
    }

    public boolean isParaestatal() {
        boolean isParaestatal = false;
        try {
            isParaestatal = super.getResultSQLisParaestatal();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isParaestatal;
    }

    public boolean saveCaratulaTransferencia(int folio, int year, String ramoSession, long selCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovCaratula(folio, year, ramoSession, selCaratula);
        return resultado;
    }

    public boolean updateCaratulaTransferencia(int folio, int year, String ramoSession, long selCaratula, long actCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateMovCaratula(folio, year, ramoSession, selCaratula, actCaratula);
        return resultado;
    }

    public boolean insertTipoOficioNuevo(int folio, String tipoOficio, int consec) {
        boolean resultado = true;
        try {
            resultado = super.getResultSQLInsertOficonsNuevo(folio, tipoOficio, consec);
            if (resultado) {
                if (!super.getResultExisteTipoOficioByOficioTipo(folio, tipoOficio)) {
                    resultado = super.getResultSQLInsertTipoOficioNuevo(folio, tipoOficio, "K");
                }
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public Caratula getCaratulaAmpliacionReduccion(int year, String ramoSession, int folio) {
        Caratula movCaratula = new Caratula();
        try {
            movCaratula = super.getResultGetMovCaratula(year, ramoSession, folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movCaratula;
    }

    public boolean deleteCaratulaTransferencia(int folio, int year, String ramoSession, long actCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovCaratula(folio, year, ramoSession, actCaratula);
        return resultado;
    }
    
    public int getMonthTramite(int folio) {
        int month = 0;

        try {
            month = super.getResultSQLgetMonthTramite(folio);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return month;
    }
    
    public double getImporteDisponible(double importe, Transferencia transferencia){
        for(TransferenciaAccionReq accionReq : transferencia.getTransferenciaAccionReqList()){
            importe -= accionReq.getImporte();
        }
        return importe;
    }
    
}
