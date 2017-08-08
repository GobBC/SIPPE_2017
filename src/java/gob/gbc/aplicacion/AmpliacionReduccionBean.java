/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Accion;
import gob.gbc.entidades.AmpliacionReduccionAccion;
import gob.gbc.entidades.AmpliacionReduccionAccionReq;
import gob.gbc.entidades.AmpliacionReduccionMeta;
import gob.gbc.entidades.Articulo;
import gob.gbc.entidades.Caratula;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.EstatusMov;
import gob.gbc.entidades.Fuente;
import gob.gbc.entidades.FuenteRecurso;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.MovOficioAccionEstimacion;
import gob.gbc.entidades.MovOficioEstimacion;
import gob.gbc.entidades.Movimiento;
import gob.gbc.entidades.MovimientoOficioAccion;
import gob.gbc.entidades.MovimientoOficioMeta;
import gob.gbc.entidades.MovimientosAmpliacionReduccion;
import gob.gbc.entidades.Partida;
import gob.gbc.entidades.Programa;
import gob.gbc.entidades.Proyecto;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.RelacionLaboral;
import gob.gbc.entidades.TipoOficio;
import gob.gbc.entidades.Usuario;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.MensajeError;
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

/**
 *
 * @author ugarcia
 */
public class AmpliacionReduccionBean extends ResultSQL {

    Bitacora bitacora;

    public AmpliacionReduccionBean(String tipoDependencia) throws Exception {
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

    public List<Accion> getAccionByMetaUsuario(int year, String appLogin, String ramo, String programa,
            int proy, String tipoProy, int meta) {
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

    public List<Partida> getPartidasByAccionAmpRed(int year, String appLogin, String ramo, String programa,
            int proy, String tipoProy, int meta, int accion) {
        List<Partida> partidasList = new ArrayList<Partida>();
        try {
            partidasList = super.getResultGetPartidaByAccionAmplRed(year, appLogin,
                    ramo, programa, proy, tipoProy, meta, accion);
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

    public List<Fuente> getFuenteFinanciamientoByRelLaboralUsuario(int year, String appLogin, String ramo, String programa,
            int proy, String tipoProy, int meta, int accion, String partida, String relLaboral) {
        List<Fuente> fuenteList = new ArrayList<Fuente>();
        try {
            fuenteList = super.getResultGetFuenteFinanciamientoByRelLaboralUsuario(year, appLogin, ramo,
                    programa, proy, tipoProy, meta, accion, partida, relLaboral);
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

    public CodigoPPTO getCodigoProgramaticoByRequerimiento(int year, String ramo, String programa, String tipoProyecto, int proyecto,
            int meta, int accion, String partida, String fuente, String fondo, String recurso, String relLaboral) {
        CodigoPPTO codigoPpto = new CodigoPPTO();
        try {
            codigoPpto = super.getRestulSQLgetCodigoPPTOsinRequerimiento(year, ramo, programa, tipoProyecto,
                    proyecto, meta, accion, partida, fuente, fondo, recurso, relLaboral);
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

    public MovimientosAmpliacionReduccion getInfoMovOficioAmpliacionReduccion(int folio) {
        MovimientosAmpliacionReduccion movAmpRed = new MovimientosAmpliacionReduccion();
        try {
            movAmpRed = super.getResultGetInfoMovOficioAmpliacionReduccion(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movAmpRed;
    }

    public List<AmpliacionReduccionMeta> getMovOficioMeta(int folio, int year) {
        List<AmpliacionReduccionMeta> ampRedoMetaList = new ArrayList<AmpliacionReduccionMeta>();
        try {
            ampRedoMetaList = super.getResultGetMovOficioMetaAmpRed(folio, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ampRedoMetaList;
    }
    
    public List<AmpliacionReduccionMeta> getMovOficioMetaRechazada(int folio, int year,List<AmpliacionReduccionAccionReq> ampliRedList) {
        List<AmpliacionReduccionMeta> ampRedoMetaList = new ArrayList<AmpliacionReduccionMeta>();
        try {
            ampRedoMetaList = super.getResultGetMovOficioMetaAmpRedRechazado(folio, year, ampliRedList);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ampRedoMetaList;
    }

    public List<AmpliacionReduccionAccion> getMovOficioAccion(int folio, int year) {
        List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
        try {
            ampRedAccionList = super.getResultGetMovOficioAccionAmpRed(folio, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ampRedAccionList;
    }
    public List<AmpliacionReduccionAccion> getMovOficioAccionRechazado(int folio, int year,List<AmpliacionReduccionAccionReq> amplAccionReq) {
        List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
        try {
            ampRedAccionList = super.getResultGetMovOficioAccionRechazoAmpRed(folio, year,amplAccionReq);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ampRedAccionList;
    }

    public List<AmpliacionReduccionAccionReq> getMovOficioAccionRequerimiento(int folio, int year) {
        List<AmpliacionReduccionAccionReq> ampRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
        try {
            ampRedAccionReqList = super.getAmpliacionReduccionAccionReqByFolio(folio, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ampRedAccionReqList;
    }

    public List<AmpliacionReduccionAccionReq> getMovOficioAccionRequerimientoByTipoOficio(int folio, int year, String tipoOficio) {
        List<AmpliacionReduccionAccionReq> ampRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
        try {
            ampRedAccionReqList = super.getAmpliacionReduccionAccionReqByTipoOficio(folio, tipoOficio, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ampRedAccionReqList;
    }

    public MovimientosAmpliacionReduccion getMovimientosAmpliacionReduccion(int folio, int year, int mesActual, String ramoSession) {
        MovimientosAmpliacionReduccion movAmpRed = new MovimientosAmpliacionReduccion();
        try {
            movAmpRed = this.getInfoMovOficioAmpliacionReduccion(folio);
            movAmpRed.setMovCaratula(this.getCaratulaAmpliacionReduccion(year, movAmpRed.getRamo(), folio));
            movAmpRed.setAmpReducMetaList(this.getMovOficioMeta(folio, year));
            movAmpRed.setAmpReducAccionList(this.getMovOficioAccion(folio, year));
            movAmpRed.setAmpReducAccionReqList(this.getMovOficioAccionRequerimiento(folio, year));
            movAmpRed.setFolioRelacionado(super.getResultSLQGetFolioRelacionado(folio));
            for (AmpliacionReduccionAccionReq ampRedAccionReq : movAmpRed.getAmpReducAccionReqList()) {
                if(super.getResultSQLisParaestatal())
                    if (this.getTipoOficioByPartida(year, ampRedAccionReq.getPartida())) {
                        ampRedAccionReq.setEstatusTipoOficio("V");
                    } else {
                        ampRedAccionReq.setEstatusTipoOficio("A");
                    }
                else
                    ampRedAccionReq.setEstatusTipoOficio("A");
                ampRedAccionReq.setAcumulado(super.getResultSQLgetAcumluladoMovtos(year, ampRedAccionReq.getPartida(), ampRedAccionReq.getRamo()));
                ampRedAccionReq.setDisponible(this.getDisponible(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                        ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(),
                        ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                        ampRedAccionReq.getRelLaboral(), mesActual));
                ampRedAccionReq.setDisponibleAnual(super.getResultSQLgetImporteAnual(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                        ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(),
                        ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                        ampRedAccionReq.getRelLaboral(), 12));
                ampRedAccionReq.setQuincePor(this.getQuincePorCiento(ampRedAccionReq.getPartida(), ampRedAccionReq.getRamo(), year));
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movAmpRed;
    }

    public MovimientosAmpliacionReduccion getMovimientosAmpliacionReduccionByTipoOficio(int folio, int year, String tipoOficio, int mesActual, String ramoSession) {
        MovimientosAmpliacionReduccion movAmpRed = new MovimientosAmpliacionReduccion();
        try {
            movAmpRed = this.getInfoMovOficioAmpliacionReduccion(folio);
            movAmpRed.setMovCaratula(this.getCaratulaAmpliacionReduccion(year, movAmpRed.getRamo(), folio));
            movAmpRed.setAmpReducMetaList(this.getMovOficioMeta(folio, year));
            movAmpRed.setAmpReducAccionList(this.getMovOficioAccion(folio, year));
            movAmpRed.setAmpReducAccionReqList(this.getMovOficioAccionRequerimientoByTipoOficio(folio, year, tipoOficio));
            movAmpRed.setFolioRelacionado(super.getResultSLQGetFolioRelacionado(folio));
            for (AmpliacionReduccionAccionReq ampRedAccionReq : movAmpRed.getAmpReducAccionReqList()) {
                if(super.getResultSQLisParaestatal())
                    if (this.getTipoOficioByPartida(year, ampRedAccionReq.getPartida())) {
                        ampRedAccionReq.setEstatusTipoOficio("V");
                    } else {
                        ampRedAccionReq.setEstatusTipoOficio("A");
                    }
                else
                    ampRedAccionReq.setEstatusTipoOficio("A");
                ampRedAccionReq.setAcumulado(super.getResultSQLgetAcumluladoMovtos(year, ampRedAccionReq.getPartida(), ampRedAccionReq.getRamo()));
                ampRedAccionReq.setDisponible(this.getDisponible(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                        ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(),
                        ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                        ampRedAccionReq.getRelLaboral(), mesActual));
                ampRedAccionReq.setDisponibleAnual(super.getResultSQLgetImporteAnual(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                        ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(),
                        ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                        ampRedAccionReq.getRelLaboral(), 12));
                ampRedAccionReq.setQuincePor(this.getQuincePorCiento(ampRedAccionReq.getPartida(), ampRedAccionReq.getRamo(), year));
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movAmpRed;
    }
    
    public MovimientosAmpliacionReduccion getMovimientosAmpliacionReduccionRechazado(int folio, int year, String tipoOficio, int mesActual, String ramoSession) {
        MovimientosAmpliacionReduccion movAmpRed = new MovimientosAmpliacionReduccion();
        try {
            movAmpRed = this.getInfoMovOficioAmpliacionReduccion(folio);
            movAmpRed.setAmpReducAccionReqList(this.getMovOficioAccionRequerimientoByTipoOficio(folio, year, tipoOficio));
            movAmpRed.setMovCaratula(this.getCaratulaAmpliacionReduccion(year, movAmpRed.getRamo(), folio));
            movAmpRed.setAmpReducMetaList(this.getMovOficioMetaRechazada(folio, year,movAmpRed.getAmpReducAccionReqList()));
            movAmpRed.setAmpReducAccionList(this.getMovOficioAccionRechazado(folio, year,movAmpRed.getAmpReducAccionReqList()));
            movAmpRed.setFolioRelacionado(super.getResultSLQGetFolioRelacionado(folio));
            for (AmpliacionReduccionAccionReq ampRedAccionReq : movAmpRed.getAmpReducAccionReqList()) {
                if(super.getResultSQLisParaestatal())
                    if (this.getTipoOficioByPartida(year, ampRedAccionReq.getPartida())) {
                        ampRedAccionReq.setEstatusTipoOficio("V");
                    } else {
                        ampRedAccionReq.setEstatusTipoOficio("A");
                    }
                else
                    ampRedAccionReq.setEstatusTipoOficio("A");
                ampRedAccionReq.setAcumulado(super.getResultSQLgetAcumluladoMovtos(year, ampRedAccionReq.getPartida(), ampRedAccionReq.getRamo()));
                ampRedAccionReq.setDisponible(this.getDisponible(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                        ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(),
                        ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                        ampRedAccionReq.getRelLaboral(), mesActual));
                ampRedAccionReq.setDisponibleAnual(super.getResultSQLgetImporteAnual(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                        ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(),
                        ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                        ampRedAccionReq.getRelLaboral(), 12));
                ampRedAccionReq.setQuincePor(this.getQuincePorCiento(ampRedAccionReq.getPartida(), ampRedAccionReq.getRamo(), year));
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movAmpRed;
    }

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

    public boolean saveOficioAmpliacionReduccion(MovimientosAmpliacionReduccion movamplRed, String fecha, boolean isActual, String capturaEspecial) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovOficio(movamplRed.getOficio(), movamplRed.getAppLogin(),
                movamplRed.getStatus(), movamplRed.getTipoMovimiento(), movamplRed.getJutificacion(), fecha, isActual, capturaEspecial);
        return resultado;
    }

    public String saveMovimientoAmpliacionReduccion(MovimientosAmpliacionReduccion movAmplRed, int folio, boolean isActual,
            int year, String fecha, String justificacion, String capturaEspecial, String ramoSession, long selCaratula,
            String estatus, String tipoUsuario, String tipoOficio) {
        List<AmpliacionReduccionMeta> ampRedMetaList = new ArrayList<AmpliacionReduccionMeta>();
        List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
        List<AmpliacionReduccionAccionReq> ampRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
        MensajeError objMensaje = new MensajeError();
        String mensaje = new String();
        String estatusAnt = new String();
        boolean resultado = false;
        int folioAnterior = 0;
        int tipoFlujo = 0;
        try {
            ampRedMetaList = movAmplRed.getAmpReducMetaList();
            ampRedAccionList = movAmplRed.getAmpReducAccionList();
            ampRedAccionReqList = movAmplRed.getAmpReducAccionReqList();
            tipoFlujo = super.getResultTipoFlujoInicial("T", tipoUsuario);
            if(estatus.equals("K")){
                movAmplRed.setStatus("X");
                folioAnterior = folio;
                folio = 0;
            }
            if (folio == 0) {
                folio = this.getSecuenciaMovimientos(isActual, year);
                movAmplRed.setOficio(folio);
                resultado = this.saveOficioAmpliacionReduccion(movAmplRed, fecha, isActual, capturaEspecial);
                if (!(selCaratula < 0)) {
                    resultado = this.saveCaratulaAmpliacionReduccion(folio, year, ramoSession, selCaratula);
                }
                if(estatus.equals("K")){
                    if(resultado){
                        resultado = this.getCancelaRechazaMovAmpRedTipoOficio(folioAnterior, tipoUsuario, movAmplRed.getAppLogin()
                                , "SE GENERÓ UN FOLIO NUEVO - "+ folio,tipoOficio, "C", tipoFlujo, movAmplRed, objMensaje);
                        if(resultado)
                            resultado = super.getResultSQLUpdateFolioRelacionado(folioAnterior, tipoOficio,movAmplRed.getOficio() );
                        estatusAnt = estatus;
                        estatus = "X";
                    }else
                        resultado = false;
                }
            } else {
                resultado = super.getResultSQLupdateJustificacionMovoficio(folio, justificacion, movAmplRed.getComentarioPlaneacion());
                if (!(selCaratula < 0)) {
                    if (movAmplRed.getMovCaratula().getsIdCaratula() < 0) {
                        resultado = this.saveCaratulaAmpliacionReduccion(folio, year, ramoSession, selCaratula);
                    } else {
                        resultado = this.updateCaratulaAmpliacionReduccion(folio, year, ramoSession, selCaratula, movAmplRed.getMovCaratula().getsIdCaratula());
                    }
                } else {
                    if (selCaratula == -2) {
                        deleteCaratulaAmpliacionReduccion(folio, year, ramoSession, movAmplRed.getMovCaratula().getsIdCaratula());
                    }
                }

                if (resultado) {
                    resultado = this.deleteMovientosAmpliacionReduccion(folio);
                    if (resultado) {
                        resultado = this.deleteAmpliacionesByFolio(folio);
                        if (resultado) {
                            if (this.getCountMovtoAccionRequerimiento(folio) > 0) {
                                resultado = this.deleteMovtoAccionReq(folio);
                            }
                        }
                    }
                    mensaje = "Problemas al editar la información";
                }
            }
            if (resultado) {
                for (AmpliacionReduccionMeta ampRedMetaTemp : ampRedMetaList) {
                    resultado = this.saveMovimientoOficioMeta(ampRedMetaTemp.getMovOficioMeta(), folio, ampRedMetaTemp.getValAutorizado());
                    if (resultado) {
                        for (MovOficioEstimacion estimacionTemp : ampRedMetaTemp.getMovOficioEstimacion()) {
                            resultado = this.saveMovimientoEstimacionMeta(folio, estimacionTemp, ampRedMetaTemp.getMovOficioMeta().getNva_creacion(), ampRedMetaTemp.getValAutorizado());
                            if (!resultado) {
                                break;
                            }
                        }
                        if (!resultado) {
                            mensaje = "Error al insertar meta";
                            break;
                        }
                    }
                }
                if (resultado) {
                    for (AmpliacionReduccionAccion ampRedAccionTemp : ampRedAccionList) {
                        resultado = this.saveMovimientoOficioAccion(ampRedAccionTemp.getMovOficioAccion(), folio, ampRedAccionTemp.getValAutorizado());
                        if (resultado) {
                            for (MovOficioAccionEstimacion accionEstimacionTemp : ampRedAccionTemp.getMovOficioAccionEstList()) {
                                resultado = this.saveMovimientoAccionEstimacionMeta(folio, accionEstimacionTemp, ampRedAccionTemp.getMovOficioAccion().getNvaCreacion(), "N", ampRedAccionTemp.getValAutorizado());
                                if (!resultado) {
                                    break;
                                }
                            }
                            if (!resultado) {
                                mensaje = "Error al insertar acción";
                                break;
                            }
                        }
                    }
                }
            }
            if (resultado) {
                mensaje = this.saveAmpliacionReducciones(year, folio, ampRedAccionReqList,estatus);
                if (!mensaje.isEmpty()) {
                    resultado = false;
                }
            }
            if (resultado) {
                super.transaccionCommit();
                if(estatusAnt.equalsIgnoreCase("K"))
                    mensaje = "$" + String.valueOf(folio)+"|N";
                else
                    mensaje = "$" + String.valueOf(folio)+"|S";
            } else {
                super.transaccionRollback();
            }
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public boolean deleteMovientosAmpliacionReduccion(int folio) {
        boolean resultado = false;
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
                movOficioAccion.getLineaSectorial(), movOficioAccion.getNvaCreacion(), movOficioAccion.getObra(),valAutorizado);
        return resultado;
    }

    public boolean saveMovimientoEstimacionMeta(int oficio, MovOficioEstimacion movOficioEst, String nvaCreacion, String valAutorizado) {
        boolean resultado = false;
        // TODO: AUTORIZACION
        resultado = super.getResultSQLInsertMovEstimacion(oficio, movOficioEst.getYear(), movOficioEst.getRamo(), movOficioEst.getMeta(), movOficioEst.getPeriodo(), movOficioEst.getValor(), nvaCreacion, "N", valAutorizado);
        return resultado;
    }

    public boolean saveMovimientoAccionEstimacionMeta(int folio, MovOficioAccionEstimacion movOficioAccionEst, String nvaCreacion, String indTransf, String valAutorizado ) {
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
            String articulo, String justificacion) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLInsertMovAccionReqEstimacion(folio, year, ramo, prg,
                    depto, meta, accion, req, descReq, fuente, fondo, recurso, tipoGasto, partida,
                    relLaboral, cantidad, costoUnitario, costoAnual, ene, feb, mar, abr,
                    may, jun, jul, ago, sep, oct, nov, dic, articulo, "S", justificacion,0,"S");
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public String saveAmpliacionReducciones(int year, int folio, List<AmpliacionReduccionAccionReq> ampRedAccionReqList, String status) {
        boolean resultado = false;
        String mensaje = new String();
        for (AmpliacionReduccionAccionReq ampRedAccionReq : ampRedAccionReqList) {
            resultado = this.saveMovimientoInsertarAmpliaciones(folio, ampRedAccionReq.getConsecutivo(), ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(),
                    ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(), ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(),
                    ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(),
                    ampRedAccionReq.getAccion(), ampRedAccionReq.getPartida(), ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getFuente(),
                    ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(), ampRedAccionReq.getMunicipio(), ampRedAccionReq.getDelegacion(),
                    ampRedAccionReq.getRelLaboral(), ampRedAccionReq.getImporte(), status, ampRedAccionReq.getTipoMovAmpRed(),
                    ampRedAccionReq.getRequerimiento(), ampRedAccionReq.getConsiderar());

            if (resultado) {
                if (ampRedAccionReq.getImporte() > 0) {
                    if(ampRedAccionReq.getMovOficioAccionReq().getJustificacion() ==  null ||  ampRedAccionReq.getMovOficioAccionReq().getJustificacion().contains("NULL"))
                            ampRedAccionReq.getMovOficioAccionReq().setJustificacion(new String());
                    resultado = this.insertMovimientoAccionRecEstimacion(folio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                            ampRedAccionReq.getDepto(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(), ampRedAccionReq.getMovOficioAccionReq().getRequerimiento(),
                            ampRedAccionReq.getMovOficioAccionReq().getReqDescr(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(),
                            ampRedAccionReq.getRecurso(), ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getPartida(), ampRedAccionReq.getRelLaboral(),
                            ampRedAccionReq.getMovOficioAccionReq().getCantidad(), ampRedAccionReq.getMovOficioAccionReq().getCostoUnitario(),
                            ampRedAccionReq.getMovOficioAccionReq().getCostoAnual(), ampRedAccionReq.getMovOficioAccionReq().getEne(),
                            ampRedAccionReq.getMovOficioAccionReq().getFeb(), ampRedAccionReq.getMovOficioAccionReq().getMar(), ampRedAccionReq.getMovOficioAccionReq().getAbr(),
                            ampRedAccionReq.getMovOficioAccionReq().getMay(), ampRedAccionReq.getMovOficioAccionReq().getJun(), ampRedAccionReq.getMovOficioAccionReq().getJul(),
                            ampRedAccionReq.getMovOficioAccionReq().getAgo(), ampRedAccionReq.getMovOficioAccionReq().getSep(), ampRedAccionReq.getMovOficioAccionReq().getOct(),
                            ampRedAccionReq.getMovOficioAccionReq().getNov(), ampRedAccionReq.getMovOficioAccionReq().getDic(), ampRedAccionReq.getMovOficioAccionReq().getArticulo(),
                            ampRedAccionReq.getMovOficioAccionReq().getJustificacion());
                }
                if (!resultado) {
                    mensaje = "3|Ocurrió un error al insertar los registros de ampliacion";
                    break;
                }
            } else {
                mensaje = "3|Ocurrió un error al insertar los registros de ampliacion/reduccion";
                break;
            }
        }
        return mensaje;
    }

    public boolean saveMovimientoInsertarAmpliaciones(int oficio, int consecutivo, String ramo, String depto, String finalidad, String funcion,
            String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral,
            double importe, String status, String tipoMov, int requerimiento, String considerar) {
        boolean resultado = false;
        try {
            resultado = super.getResultInsertAmpliaciones(oficio, consecutivo, ramo, depto, finalidad, funcion, subfuncion,
                    prgConac, programa, tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso,
                    municipio, delegacion, relLaboral, importe, status, tipoMov, requerimiento,considerar);
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

    public String cambiaEstatusMovOficioAmpliacionReduccion(int folio, String tipoMov, String tipoUsuario, String estatus,
           MovimientosAmpliacionReduccion movAmplRed, int year, int mes, String fecha, String capturaEspecial, String tipoficio,
           boolean isActual) {
        List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
        List<AmpliacionReduccionAccionReq> ampRedAcionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
                
        Movimiento objMovimiento;
        Calendar calFechaElab = new GregorianCalendar();
        Calendar calFechaActual = Calendar.getInstance();
        
        String flujo = new String();
        String mensaje = new String();
        String msjRes = new String();
        boolean resultado = false;
        MensajeError objMensaje = new MensajeError();
        int tipoFlujo = 0;
        try {
            ampRedAcionReqList = movAmplRed.getAmpReducAccionReqList();
            
            objMovimiento = super.getResultMovimientoByFolio(folio);
            if(!capturaEspecial.equals("S"))
                calFechaActual.setTime(super.getResultSQLgetServerDate()); 
            else{
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
                calFechaActual.setTime(format.parse(fecha));
            }
            calFechaElab.setTime(objMovimiento.getFechaElab());
            
            if (estatus.equals("R")) {
                
                tipoFlujo = super.getResultTipoFlujoInicial(tipoMov, tipoUsuario);
                
                flujo = super.getResultSQLEstatusBaseByTipoFlujo(tipoFlujo);                
                resultado = super.getResultSQLUpdateEstatusAmpliaciones(flujo, folio);
                //resultado = true;
            } else if (estatus.equals("K")) {
                tipoFlujo = 3;
                flujo = super.getResultSQLEstatusBaseByTipoFlujo(tipoFlujo);
                resultado = super.getResultSQLUpdateEstatusAmpliacionesTipoOficio(flujo, folio, tipoficio);
            } else {
                flujo = this.getEstatusMovimiento(tipoMov, tipoUsuario, estatus);
                resultado = super.getResultSQLUpdateEstatusAmpliaciones(flujo, folio);
                //resultado = true;
            }
            if (!flujo.isEmpty()) {
                if (resultado) {
                    for(AmpliacionReduccionAccionReq movof : movAmplRed.getAmpReducAccionReqList()){
                        if(!movof.getTipoMovAmpRed().equals("R")){
                            movof.getMovOficioAccionReq().setOficio(String.valueOf(folio));
                            if (!super.getResultSQLAjustarAcumuladoMesesAnteriores(movof.getMovOficioAccionReq(),
                                    movof.getMeta(), movof.getAccion(), calFechaElab.get(Calendar.MONTH) + 1)) {
                                mensaje = "Error al ajustar el acumulado de meses anteriores.";
                                resultado = false;
                                break;
                            }  
                        }
                    }
                    if(resultado)
                        resultado = this.updateMovimientoOficio(folio, flujo, fecha, isActual, capturaEspecial);
                }
                if (!resultado) {
                    mensaje = "Error al actualizar el movimiento";
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
                    for (AmpliacionReduccionAccionReq ampRedAccionReq : ampRedAcionReqList) {
                        if (ampRedAccionReq.getTipoMovAmpRed().equals("R")) {
                            //if(flujo.equals("W") || estatus.equals("K")){
                            if(flujo.equals("W")){
                                resultado = this.updatePPTOreduccion(ampRedAccionReq.getImporte(), year, ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(),
                                        ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(), ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(),
                                        ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(),
                                        ampRedAccionReq.getAccion(), ampRedAccionReq.getPartida(), ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getFuente(),
                                        ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(), ampRedAccionReq.getMunicipio(), ampRedAccionReq.getDelegacion(),
                                        ampRedAccionReq.getRelLaboral(), calFechaActual.get(Calendar.MONTH) + 1);
                                if (!resultado) {
                                    super.transaccionRollback();
                                    break;
                                }
                            }
                        }
                        if (resultado) {
                            resultado = this.insertOficons(folio, ampRedAccionReq.getConsecutivo(), ampRedAccionReq.getEstatusTipoOficio());
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
                            }
                        }
                    }else{
                        resultado = false;
                        mensaje = "2|Ocurrió un error al enviar el oficio";
                    }
                }
            }
            if (resultado) {
                if (estatus.equals("R")) {
                    resultado = super.getResultDeleteBitMovtoByOficio(folio);
                } else if (estatus.equals("K")) {
                    resultado = super.getResultDeleteBitMovtoByOficioTipoOficio(folio, tipoficio, tipoFlujo);
                    /*
                    if(resultado){
                        //saveMovimientoAmpliacionReduccion(MovimientosAmpliacionReduccion movAmplRed, int folio, boolean isActual,
                        //int year, String fecha, String justificacion, String capturaEspecial, String ramoSession, long selCaratula)
                        msjRes = this.saveMovimientoAmpliacionReduccion(movAmplRed,0,isActual,year,fecha,movAmplRed.getJutificacion(),
                                capturaEspecial,movAmplRed.getMovCaratula().getsRamo(),movAmplRed.getMovCaratula().getsIdCaratula(),"X");
                        if(msjRes.contains("$")){
                            movAmplRed.setOficio(Integer.parseInt(msjRes.replace("$", "").trim()));
                            mensaje = this.cambiaEstatusMovOficioAmpliacionReduccion(movAmplRed.getOficio(), tipoMov, tipoUsuario, "X", movAmplRed, year, mes, fecha, capturaEspecial, tipoficio, isActual);
                            if(mensaje.startsWith("1")){
                                resultado = this.getCancelaRechazaMovAmpRedTipoOficio(folio, tipoUsuario, movAmplRed.getAppLogin()
                                        , "SE GENERÓ UN FOLIO NUEVO - "+ movAmplRed.getOficio(),tipoficio, "C", tipoFlujo, movAmplRed, objMensaje);
                                if(resultado)
                                    resultado = super.getResultSQLUpdateFolioRelacionado(folio, tipoficio,movAmplRed.getOficio() );
                            }else
                                resultado = false;
                        }else{
                            resultado = false;
                        }
                    }*/
                }
            }
            if (resultado) {
                //super.transaccionRollback();
                super.transaccionCommit();
                mensaje = "1|El registro se actualizó correctamente";
            } else {
                super.transaccionRollback();
                mensaje = "2|Ocurrió un error al actualizar el estatus de oficio";
            }
        } catch (SQLException ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
            mensaje = "2|Ocurrió un error al actualizar el estatus de oficio";
        } catch (NumberFormatException ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
            mensaje = "2|Ocurrió un error al actualizar el estatus de oficio";
        }catch (Exception ex) {
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

     public boolean getCancelaRechazaMovAmpRedTipoOficio(int oficio, String tipoUsr, String appLogin, String motivo, String tipoOficio, String estatusMov,
            int tipoFlujo, MovimientosAmpliacionReduccion movAmpRed, MensajeError objMensaje) {
        TipoOficio objMovimiento;
        boolean bolResultado = false;
        boolean bolGrabo = true;
        Calendar calFechaReduccion = new GregorianCalendar();

        try {
            objMovimiento = super.getResultMovimientoByFolioTipoOficio(oficio, tipoOficio);

            if (super.getResultSQLUpdateEstatusMotivoMovTipoOficio(estatusMov, motivo, oficio, tipoOficio)) {
                if (super.getResultSQLUpdateEstatusAmpliacionesTipoOficio(estatusMov, oficio, tipoOficio)) {
                    if (super.getResultInsertBitMovto(oficio, appLogin, estatusMov, "N", tipoOficio, "", tipoFlujo, tipoUsr,"")) {

                        //Regresa el valor tomado del ppto en las reducciones
                        Iterator iterator = (Iterator) movAmpRed.getAmpReducAccionReqList().iterator();
                        while (bolGrabo && iterator.hasNext()) {
                            AmpliacionReduccionAccionReq ampRedAccionReq = (AmpliacionReduccionAccionReq) iterator.next();

                            if (ampRedAccionReq.getTipoMovAmpRed().equals("R")) {//Reducción
                                //Se afecta el ACTUALIZADO en PPTO 
                                calFechaReduccion.setTime(objMovimiento.getFecPPTO());
                                bolGrabo = super.getResultSQLupdatePPTOreduccion(ampRedAccionReq.getImporte() * -1, calFechaReduccion.get(Calendar.YEAR),
                                        ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(), ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(),
                                        ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(), ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(),
                                        ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(), ampRedAccionReq.getPartida(),
                                        ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                                        ampRedAccionReq.getMunicipio(), ampRedAccionReq.getDelegacion(), ampRedAccionReq.getRelLaboral(),
                                        calFechaReduccion.get(Calendar.MONTH) + 1);

                                if (!bolGrabo) {
                                    objMensaje.setMensaje("Error al devolver el importe al presupuesto");
                                }
                            }

                        }

                        if (bolGrabo) {
                            if (getProcesosTipoOficioByOficio(oficio, objMensaje)) {
                                bolResultado = true;
                            }
                        }
                    } else {
                        objMensaje.setMensaje("Error al insertar en la bit\u00e1cora del movimiento");
                    }
                } else {
                    objMensaje.setMensaje("Error al actualizar el estatus de ampliaciones");
                }
            } else {
                objMensaje.setMensaje("Error al actualizar el estatus del tipo de oficio");
            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado an cancelar/rechazar la ampliaci\u00f3n por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return bolResultado;
    }
    
    public boolean getProcesosTipoOficioByOficio(int oficio, MensajeError objMensaje) throws SQLException {
        boolean bolGrabo = true;
        boolean bolResultado = false;
        List<TipoOficio> tipoOficioList;
        boolean bolTodosCancelados = true;
        boolean bolTodosAutorizados = true;
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

                } else {
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

    public String cancelarMovimiento(int folio, String estatus) {
        String mensaje = new String();
        boolean resultado = false;
        resultado = this.deleteMovientosAmpliacionReduccion(folio);
        if (resultado) {
            if (this.getCountMovtoAccionRequerimiento(folio) > 0) {
                resultado = this.deleteMovtoAccionReq(folio);
            }
            if (resultado) {
                resultado = super.getResultSQLdeleteMovOficio(folio, estatus);
            } else {
                mensaje = "Error al elimiar requerimientos";
            }
        } else {
            mensaje = "Error al elminar metas/acciones";
        }
        return mensaje;
    }

    public boolean deleteAmpliacionesByFolio(int folio) {
        boolean resultado = false;
        try {
            if (super.getResultGetCountAmpliaciones(folio) > 0) {
                resultado = super.getResultSQLDeleteAmpliacionesByFolio(folio);
            } else {
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

    public boolean deleteMovtoAccionReq(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovtoAccionReq(folio);
        return resultado;
    }

    public int getCountMovtoAccionRequerimiento(int folio) {
        int numAccionReq = 0;
        try {
            numAccionReq = super.getResultSQLgetCountMovtoAccionReq(folio);
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
        return numAccionReq;
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

    public double getQuincePorCiento(String partida, String ramo, int year) {
        double monto = 0.0;
        double quincePorciento = 0.0;
        try {
            monto = super.getResultSQLgetquincePorciento(partida, ramo,year);
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
        return monto;
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
        boolean transAnual = false;
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

    public boolean saveCaratulaAmpliacionReduccion(int folio, int year, String ramoSession, long selCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovCaratula(folio, year, ramoSession, selCaratula);
        return resultado;
    }

    public boolean updateCaratulaAmpliacionReduccion(int folio, int year, String ramoSession, long selCaratula, long actCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateMovCaratula(folio, year, ramoSession, selCaratula, actCaratula);
        return resultado;
    }

    public boolean deleteMovimientoCaratula(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovCaratula(folio);
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

    public boolean deleteCaratulaAmpliacionReduccion(int folio, int year, String ramoSession, long actCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovCaratula(folio, year, ramoSession, actCaratula);
        return resultado;
    }
}
