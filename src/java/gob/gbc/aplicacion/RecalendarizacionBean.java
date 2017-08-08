/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Accion;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.EstatusMov;
import gob.gbc.entidades.Fuente;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.MovOficioAccionEstimacion;
import gob.gbc.entidades.MovOficioEstimacion;
import gob.gbc.entidades.MovOficiosAccionReq;
import gob.gbc.entidades.Movimiento;
import gob.gbc.entidades.MovimientosRecalendarizacion;
import gob.gbc.entidades.Partida;
import gob.gbc.entidades.Programa;
import gob.gbc.entidades.Proyecto;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.RecalendarizacionAccion;
import gob.gbc.entidades.RecalendarizacionAccionReq;
import gob.gbc.entidades.RecalendarizacionMeta;
import gob.gbc.entidades.RelacionLaboral;
import gob.gbc.entidades.Requerimiento;
import gob.gbc.entidades.Usuario;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Fechas;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ugarcia
 */
public class RecalendarizacionBean extends ResultSQL {

    Bitacora bitacora;

    public RecalendarizacionBean(String tipoDependencia) throws Exception {
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

    public MovimientosRecalendarizacion getInfoMovimientoRecalendarizcion(int folio) {
        MovimientosRecalendarizacion movReal = new MovimientosRecalendarizacion();
        try {
            movReal = super.getResultGetInfoMovOficio(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movReal;
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

    public List<Partida> getPartidasByAccionUsuario(int year, String appLogin, String ramo, String programa,
            int proy, String tipoProy, int meta, int accion) {
        List<Partida> partidasList = new ArrayList<Partida>();
        try {
            partidasList = super.getResultGetPartidaByAccionUsuario(year, appLogin,
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

    public List<RelacionLaboral> getRelLaboralByPartidaUsuario(int year, String appLogin, String ramo, String programa,
            int proy, String tipoProy, int meta, int accion, String partida) {
        List<RelacionLaboral> relLaboralList = new ArrayList<RelacionLaboral>();
        try {
            relLaboralList = super.getResultGetRelLaboralByPartidaUsuario(year, appLogin, ramo, programa, proy,
                    tipoProy, meta, accion, partida);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return relLaboralList;
    }

    public List<Fuente> getFuenteFinanciamientoByPartidaUsuario(int year, String appLogin, String ramo, String programa,
            int proy, String tipoProy, int meta, int accion, String partida) {
        List<Fuente> fuenteList = new ArrayList<Fuente>();
        try {
            fuenteList = super.getResultGetFuenteFinanciamientoByPartidaUsuario(year, appLogin, ramo, programa, proy, tipoProy, meta, accion, partida);
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

    public List<Requerimiento> getRequerimientoByFuenteFinanciamientoUsuario(int year, String ramo,
            String programa, int meta, int accion, String partida, String relLaboral, String fuenteFinanciamiento) {
        List<Requerimiento> requList = new ArrayList<Requerimiento>();
        String fuenteFin[] = new String[3];
        try {
            fuenteFin = fuenteFinanciamiento.split("\\.");
            requList = super.getResultGetRequerimientoByFuenteFinanciamientoUsuario(year, ramo,
                    programa, meta, accion, partida, relLaboral, fuenteFin[0], fuenteFin[1], fuenteFin[2]);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return requList;
    }

    public MovimientosRecalendarizacion getMovimientosRecalendarizacion(int folio) {
        MovimientosRecalendarizacion movsRecalendarizacion = null;
        try {
            movsRecalendarizacion = new MovimientosRecalendarizacion();
            movsRecalendarizacion = this.getInfoMovimientoRecalendarizcion(folio);
            movsRecalendarizacion.setMovAccionEstimacionList(this.getMovAccionEstimacion(folio));
            movsRecalendarizacion.setMovEstimacionList(this.getMovEstimacion(folio));
            movsRecalendarizacion.setMovOficiosAccionReq(this.getMovAccionReqEstimacion(folio));
            for(RecalendarizacionAccionReq mov : movsRecalendarizacion.getMovOficiosAccionReq()){
                if(movsRecalendarizacion.getStatus().isEmpty() || movsRecalendarizacion.getStatus()== null)
                    movsRecalendarizacion.setStatus("X");
                mov.getMovAccionReq().
                        setRecalendarizado(this.getImporteRecalendarizacion(movsRecalendarizacion.getStatus(),
                                mov.getMovAccionReq(),
                                folio));
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movsRecalendarizacion;
    }

    public List<RecalendarizacionMeta> getMovEstimacion(int folio) {
        List<RecalendarizacionMeta> recalMeta = new ArrayList<RecalendarizacionMeta>();
        try {
            recalMeta = super.getResultSQLgetMovEstimacion(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return recalMeta;
    }

    public Requerimiento getRequerimientoById(int year, String ramoId, String programaId,
            int metaId, int accionId, int requerimientoId) {
        Requerimiento accionReq = new Requerimiento();
        try {
            accionReq = super.getResultGetRequerimientoById(year, ramoId, programaId, metaId, accionId, requerimientoId);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return accionReq;
    }

    public List<RecalendarizacionAccion> getMovAccionEstimacion(int folio) {
        List<RecalendarizacionAccion> recalenAccionList = new ArrayList<RecalendarizacionAccion>();
        try {
            recalenAccionList = super.getResultSQLgetMovAccionEstimacion(folio);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return recalenAccionList;
    }

    public List<RecalendarizacionAccionReq> getMovAccionReqEstimacion(int folio) {
        List<RecalendarizacionAccionReq> recalenAccionReqList = new ArrayList<RecalendarizacionAccionReq>();
        try {
            recalenAccionReqList = super.getResultgetMovAccionRequerimiento(folio);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return recalenAccionReqList;
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

    public String getFlujoAutorizacion(String tipoMov, String tipoUsuario, String estatus) {
        String valor = new String();
        try {
            valor = super.getResultSQLGetFlujoAutorizacion(tipoMov, tipoUsuario, estatus);
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

    public String[] getRequerimientoByIdUsuario(int year, String ramo, String programa, int meta, int accion, int requerimiento) {
        String valor[] = new String[19];
        try {
            valor = super.getResultSQLgetRequerimientoByIdUsuario(year, ramo, programa, meta, accion, requerimiento);
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

    public int getCountMovtoAccionEstimacion(int folio) {
        int numAccionReq = 0;
        try {
            numAccionReq = super.getResultExistenMovAccionByFolio(folio);
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

    public int getCountMovtoEstimacion(int folio) {
        int numAccionReq = 0;
        try {
            numAccionReq = super.getResultExistenMovMetaByFolio(folio);
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

    public int countMovtoAccionReqByInfo(int year, String ramoId, String programa, int metaId, int accionId,
            String partida, String fuente, String fondo, String recurso, String relLaboral, int requerimiento) {
        int numAccionReq = 0;
        try {
            numAccionReq = super.getResultSQLcountMovtoAccionReqByInfo(year, ramoId, programa, metaId,
                    accionId, partida, fuente, fondo, recurso, relLaboral, requerimiento);
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

    public boolean insertMovimientoEstimacion(int folio, int year, String ramo,
            int meta, int periodo, double valor, String nvaCreacion, String valAutorizado) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovEstimacion(folio, year, ramo, meta, periodo, valor, nvaCreacion, "N", valAutorizado);
        return resultado;
    }

    public boolean insertMovimientoAccionEstimacion(int folio, int year,
            String ramo, int meta, int accion, int periodo, double valor, String nvaCreacion, String indTransf, String valAutorizado) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovAccionEstimacion(folio,
                year, ramo, meta, accion, periodo, valor, nvaCreacion,
                indTransf, valAutorizado);
        return resultado;
    }

    public boolean updateJustificacionMovOficios(int folio, String justificacion) {
        boolean resultado = false;
        resultado = super.getResultSQLupdateJustificacionMovoficio(folio, justificacion, new String());
        return resultado;
    }

    public boolean insertMovimientoAccionRecEstimacion(int folio, int year, String ramo, String prg, String depto, int meta, int accion,
            int req, String descReq, String fuente, String fondo, String recurso, String tipoGasto, String partida,
            String relLaboral, double cantidad, double costoUnitario, double costoAnual, double ene, double feb, double mar,
            double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic,
            String articulo, String justificacion, String considerar) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLInsertMovAccionReqEstimacion(folio, year, ramo, prg,
                    depto, meta, accion, req, descReq, fuente, fondo, recurso, tipoGasto, partida,
                    relLaboral, cantidad, costoUnitario, costoAnual, ene, feb, mar, abr,
                    may, jun, jul, ago, sep, oct, nov, dic, articulo, "N", justificacion, 0, considerar);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean insertMovimientoOficio(int oficio, String appLogin, String estatus,
            String tipoMov, String justificacion, String fecha, boolean isActual) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovOficio(oficio, appLogin, estatus, tipoMov, justificacion, fecha, isActual, "N");
        return resultado;
    }

    /*public boolean updateMovimientoOficioRecal(int oficio) {
        boolean resultado = false;
        resultado = super.getResultSQLupdateMovRecalendarizacion(oficio);
        return resultado;
    }*/

    public boolean deleteMovimientoRecalendarizacion(int folio) throws SQLException{
        boolean resultado = false;
        if (this.getCountMovtoAccionRequerimiento(folio) > 0) {
            resultado = this.deleteMovtoAccionReq(folio);
        } else {
            resultado = true;
        }
        if (resultado) {
            if (this.getCountMovtoEstimacion(folio) > 0) {
                resultado = this.deleteMovtoEstimacion(folio);
            } else {
                resultado = true;
            }
            if (resultado) {
                if (this.getCountMovtoAccionEstimacion(folio) > 0) {
                    resultado = this.deleteMovtoAccionEstimacion(folio);
                } else {
                    resultado = true;
                }
            }if(resultado){    
                if(super.getResultSQLcountRecalendarizacion(folio))
                    resultado = super.getResultSQLDeleteRecalendarizacionByFolio(folio);
                else
                    resultado = true;
            }
        }
        return resultado;
    }

    public boolean deleteMovtoAccionReq(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovtoAccionReq(folio);
        return resultado;
    }

    public boolean deleteMovtoAccionEstimacion(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovtoAccion(folio);
        return resultado;
    }

    public boolean deleteMovtoEstimacion(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovtoMeta(folio);
        return resultado;
    }

    public String saveMovimientoRecalendarizacion(MovimientosRecalendarizacion movRecal, boolean isActual, int year, int folio, String justificacion, String fecha) {
        List<RecalendarizacionMeta> movEstimacionList;
        List<RecalendarizacionAccion> movAccionEstimacionList;
        List<RecalendarizacionAccionReq> movOficiosAccionReq;
        String mensaje = new String();
        boolean resultado = false;
        try {
            movEstimacionList = movRecal.getMovEstimacionList();
            movAccionEstimacionList = movRecal.getMovAccionEstimacionList();
            movOficiosAccionReq = movRecal.getMovOficiosAccionReq();
            if (folio == 0) {
                //folio = 5000000;
                folio = this.getSecuenciaMovimientos(isActual, year);
                resultado = this.insertMovimientoOficio(folio, movRecal.getAppLogin(), movRecal.getStatus(),
                        movRecal.getTipoMovimiento(), movRecal.getJutificacion(), fecha, isActual);
            } else {
                resultado = this.updateJustificacionMovOficios(folio, justificacion);
                if (resultado) {
                    resultado = this.deleteMovimientoRecalendarizacion(folio);
                }
            }
            if (resultado) {
                for (RecalendarizacionMeta recMeta : movEstimacionList) {
                    for (MovOficioEstimacion movEst : recMeta.getMovEstimacionList()) {
                        resultado = this.insertMovimientoEstimacion(folio, year, movEst.getRamo(), movEst.getMeta(), movEst.getPeriodo(), movEst.getValor(), "N",recMeta.getValAutorizado());
                        if (!resultado) {
                            super.transaccionRollback();
                            break;
                        }
                    }
                    if (!resultado) {
                        mensaje = "No se pudieron insertar los movimientos de metas";
                        break;
                    }
                }
                if (resultado) {
                    for (RecalendarizacionAccion recAccion : movAccionEstimacionList) {
                        for (MovOficioAccionEstimacion movAcc : recAccion.getMovEstimacionList()) {
                            resultado = this.insertMovimientoAccionEstimacion(folio, year,
                                    movAcc.getRamo(), movAcc.getMeta(), movAcc.getAccion(), movAcc.getPeriodo(), movAcc.getValor(), "N", "N", recAccion.getValAutorizacion());
                            if (!resultado) {
                                super.transaccionRollback();
                                break;
                            }
                        }
                        if (!resultado) {
                            mensaje = "No se pudieron insertar los movimientos de acciones";
                            break;
                        }
                    }
                }
                if (resultado) {
                    for (RecalendarizacionAccionReq recAccionRec : movOficiosAccionReq) {
                        if(recAccionRec.getMovAccionReq().getJustificacion() ==  null ||  recAccionRec.getMovAccionReq().getJustificacion().contains("NULL"))
                             recAccionRec.getMovAccionReq().setJustificacion(new String());
                        resultado = this.insertMovimientoAccionRecEstimacion(folio, year,
                                recAccionRec.getMovAccionReq().getRamo(), recAccionRec.getMovAccionReq().getPrograma(),
                                recAccionRec.getMovAccionReq().getDepto(), recAccionRec.getMovAccionReq().getMeta(),
                                recAccionRec.getMovAccionReq().getAccion(), recAccionRec.getMovAccionReq().getRequerimiento(),
                                recAccionRec.getMovAccionReq().getReqDescr(), recAccionRec.getMovAccionReq().getFuente(),
                                recAccionRec.getMovAccionReq().getFondo(), recAccionRec.getMovAccionReq().getRecurso(),
                                recAccionRec.getMovAccionReq().getTipoGasto(), recAccionRec.getMovAccionReq().getPartida(),
                                recAccionRec.getMovAccionReq().getRelLaboral(), recAccionRec.getMovAccionReq().getCantidad(),
                                recAccionRec.getMovAccionReq().getCostoUnitario(), recAccionRec.getMovAccionReq().getCostoAnual(),
                                recAccionRec.getMovAccionReq().getEne(), recAccionRec.getMovAccionReq().getFeb(),
                                recAccionRec.getMovAccionReq().getMar(), recAccionRec.getMovAccionReq().getAbr(),
                                recAccionRec.getMovAccionReq().getMay(), recAccionRec.getMovAccionReq().getJun(),
                                recAccionRec.getMovAccionReq().getJul(), recAccionRec.getMovAccionReq().getAgo(),
                                recAccionRec.getMovAccionReq().getSep(), recAccionRec.getMovAccionReq().getOct(),
                                recAccionRec.getMovAccionReq().getNov(), recAccionRec.getMovAccionReq().getDic(),
                                recAccionRec.getMovAccionReq().getArticulo(), recAccionRec.getMovAccionReq().getJustificacion(), recAccionRec.getMovAccionReq().getConsiderar());
                        if (!resultado) {
                            super.transaccionRollback();
                            mensaje = "No se pudieron insertar los movimientos de acciones";
                            break;
                        }
                    }
                }
            } else {
                super.transaccionRollback();
                mensaje = "El movimiento no se pudo insertar en la tabla";
            }
            if (resultado) {
                if (this.validaRequerimientoRecal(folio, movRecal.getAppLogin())) {                    
                    if (this.actualizarRecalendarizacion(movOficiosAccionReq, year, folio)) {
                        super.transaccionCommit();
                        mensaje = "$" + String.valueOf(folio);
                    } else {
                        super.transaccionRollback();
                        mensaje = "Ocurrió un error al grabar en SPP.RECALENDARIZACION";
                    }
                } else {
                    super.transaccionRollback();
                    mensaje = "El movimiento contiene incosistencia de códigos";
                }
            }
        } catch (Exception ex) {
            mensaje = "3|" + ex.getMessage();
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public boolean getCountMovsAccionRequerimientoById(String ramo, String programa, int meta, int accion,
            String partida, String fuente, String fondo, String recurso, int requerimiento) {
        int resultado = 0;
        try {
            resultado = super.getResultCountMovAccionRequerimiento(ramo, programa, meta, accion, partida,
                    fuente, fondo, recurso, requerimiento);
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
        if (resultado <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean modificaEnero() {
        boolean enero = false;
        try {
            enero = super.getResultSQLModificarEnero();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return enero;
    }

    public String getRolNormativo() {
        String normativo = new String();
        try {
            normativo = super.getResultSQLGetRolesPrg();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return normativo;
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

    public boolean validaRequerimientoRecal(int oficio, String appLogin) {
        List<RecalendarizacionAccionReq> accionReqList = new ArrayList<RecalendarizacionAccionReq>();
        boolean bandera = false;

        accionReqList = this.getMovAccionReqEstimacion(oficio);
        for (RecalendarizacionAccionReq accionReq : accionReqList) {
            try {
                if (super.getResultSqlGetValidaRequerimientoRecal(oficio, accionReq.getMovAccionReq().getRamo(),
                        accionReq.getMovAccionReq().getPrograma(), accionReq.getMovAccionReq().getMeta(),
                        accionReq.getMovAccionReq().getAccion(), accionReq.getMovAccionReq().getRequerimiento(),
                        accionReq.getMovAccionReq().getDepto(), accionReq.getMovAccionReq().getPartida(),
                        accionReq.getMovAccionReq().getFuente(), accionReq.getMovAccionReq().getFondo(),
                        accionReq.getMovAccionReq().getRecurso(), appLogin)) {
                    bandera = true;
                } else {
                    bandera = false;
                    break;
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return bandera;
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

    public boolean updateMovimientoOficio(int oficio, String estatus, String fecha, boolean isActual, String capturaEspecial) {
        boolean resultado = false;
        resultado = super.getResultSQLupdateMovOficio(oficio, estatus, fecha, isActual, capturaEspecial);
        return resultado;
    }

    public String actualizaMovOficio(int folio, String tipoMov, String tipoUsuario, String estatus, List<RecalendarizacionAccionReq> recalList) {
        String flujo = new String();
        String mensaje = new String();
        boolean resultado = false;
        Movimiento objMovimiento;
        Calendar calFechaElab = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaActual = "";
        Fechas objFechas = new Fechas();
        boolean isActual = true;
        
        try {
            
            objMovimiento = super.getResultMovimientoByFolio(folio);

            calFechaActual.setTime(super.getResultSQLgetServerDate()); 
            calFechaElab.setTime(objMovimiento.getFechaElab());
            
            if (calFechaActual.get(Calendar.YEAR) > calFechaElab.get(Calendar.YEAR)) {
                isActual = false;
                calFechaActual.set(calFechaElab.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
            }
            
            flujo = this.getEstatusMovimiento(tipoMov, tipoUsuario, estatus);
            if (!flujo.isEmpty()) {   
                /*
                if(isParaestatal()){
                    for(RecalendarizacionAccionReq recal : recalList){          
                        if (!super.getResultSQLAjustarAcumuladoMesesAnterioresParaestatal(recal.getMovAccionReq(),
                                recal.getMovAccionReq().getMeta(), recal.getMovAccionReq().getAccion(), calFechaActual.get(Calendar.MONTH) + 1)) {
                            mensaje = "Error al ajustar el acumulado de meses anteriores.";
                            break;
                        }
                    }
                }
                */
                resultado = this.updateMovimientoOficio(folio, flujo, strFechaActual, isActual, "N");
                if (!resultado) {
                    mensaje = "Error al actualizar el movimiento";
                }
            } else {
                mensaje = "Error al obtener el flujo de autorización";
            }
        } catch (Exception ex) {
            mensaje = "Error inesperado al enviar el movimiento";
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public boolean updateMovimientoOficioRecalAccionReqConsiderar(int year, int folio, String ramo, String prg, String depto, int meta, int accion, int requerimiento, String considerar) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateMovimientoOficioRecalAccionReqConsiderar(year, folio, ramo, prg, depto, meta, accion, requerimiento, considerar);
        return resultado;
    }
    
    public double getImporteRecalendarizacion(String status, MovOficiosAccionReq movReq, int folio){
        double recal = 0.0;
        Requerimiento reqOriginal = new Requerimiento();
        try{
            if(!status.equals("A")){
                reqOriginal = super.getResultSQLgetRequerimientoByLlave(movReq.getYear(),
                        movReq.getRamo(),movReq.getMeta(),movReq.getAccion(), movReq.getRequerimiento());
            }else{
                reqOriginal = super.getResultGetRequerimientoHistorico( movReq.getYear(),movReq.getRamo(),
                        movReq.getPrograma(),movReq.getMeta(),movReq.getAccion(), movReq.getRequerimiento(), folio);
            }
            if(movReq.getEne() - reqOriginal.getCantEne() > 0)
                recal += movReq.getEne() - reqOriginal.getCantEne();
            if(movReq.getFeb() - reqOriginal.getCantFeb() > 0)
                recal += movReq.getFeb() - reqOriginal.getCantFeb();
            if(movReq.getMar() - reqOriginal.getCantMar() > 0)
                recal += movReq.getMar() - reqOriginal.getCantMar();
            if(movReq.getAbr() - reqOriginal.getCantAbr() > 0)
                recal += movReq.getAbr() - reqOriginal.getCantAbr();
            if(movReq.getMay() - reqOriginal.getCantMay() > 0)
                recal += movReq.getMay() - reqOriginal.getCantMay();
            if(movReq.getJun() - reqOriginal.getCantJun() > 0)
                recal += movReq.getJun() - reqOriginal.getCantJun();
            if(movReq.getJul() - reqOriginal.getCantJul() > 0)
                recal += movReq.getJul() - reqOriginal.getCantJul();
            if(movReq.getAgo() - reqOriginal.getCantAgo() > 0)
                recal += movReq.getAgo() - reqOriginal.getCantAgo();
            if(movReq.getSep() - reqOriginal.getCantSep() > 0)
                recal += movReq.getSep() - reqOriginal.getCantSep();
            if(movReq.getOct() - reqOriginal.getCantOct() > 0)
                recal += movReq.getOct() - reqOriginal.getCantOct();
            if(movReq.getNov() - reqOriginal.getCantNov() > 0)
                recal += movReq.getNov() - reqOriginal.getCantNov();
            if(movReq.getDic() - reqOriginal.getCantDic() > 0)
                recal += movReq.getDic() - reqOriginal.getCantDic();
            recal = new BigDecimal(recal).
                    multiply(new BigDecimal(movReq.getCostoUnitario())).
                    setScale(2, RoundingMode.HALF_UP).doubleValue();
        }catch(SQLException ex){
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return recal;
    }

    public boolean actualizarRecalendarizacion(List<RecalendarizacionAccionReq> movAccionReq, int year, int folio) {
        CodigoPPTO codPpto;
        Requerimiento reqOriginal = new Requerimiento();
        MovOficiosAccionReq movRequerimiento = new MovOficiosAccionReq();
        String tipoRequerimiento = new String();

        boolean bolGrabo = true;

        int contMes = 1;

        double cantXmes = 0.0;
        double diferenciaMes = 0.0;
        try {
            for (RecalendarizacionAccionReq accionReq : movAccionReq) {
                contMes = 1;
                movRequerimiento = accionReq.getMovAccionReq();
                codPpto = super.getRestulSQLGetCodigoPPTOByReq(year, movRequerimiento.getRamo(),
                        movRequerimiento.getPrograma(), movRequerimiento.getRequerimiento(),
                        movRequerimiento.getPartida(), movRequerimiento.getMeta(),
                        movRequerimiento.getAccion());
                if (codPpto != null) {                    
                    while (bolGrabo && contMes <= 12) {
                        cantXmes = 0;
                        switch (contMes) {
                            case 1:
                                cantXmes = new BigDecimal(movRequerimiento.getEne())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 2:
                                cantXmes = new BigDecimal(movRequerimiento.getFeb())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 3:
                                cantXmes = new BigDecimal(movRequerimiento.getMar())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 4:
                                cantXmes = new BigDecimal(movRequerimiento.getAbr())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 5:
                                cantXmes = new BigDecimal(movRequerimiento.getMay())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 6:
                                cantXmes = new BigDecimal(movRequerimiento.getJun())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 7:
                                cantXmes = new BigDecimal(movRequerimiento.getJul())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 8:
                                cantXmes = new BigDecimal(movRequerimiento.getAgo())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 9:
                                cantXmes = new BigDecimal(movRequerimiento.getSep())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 10:
                                cantXmes = new BigDecimal(movRequerimiento.getOct())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 11:
                                cantXmes = new BigDecimal(movRequerimiento.getNov())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;
                            case 12:
                                cantXmes = new BigDecimal(movRequerimiento.getDic())
                                        .multiply(new BigDecimal(movRequerimiento.getCostoUnitario()))
                                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
                                break;

                        }
                        reqOriginal = super.getResultSQLgetRequerimientoOriginal(year, codPpto.getRamoId(), codPpto.getPrograma(), codPpto.getDepto(),
                            codPpto.getMeta(), codPpto.getAccion(), movRequerimiento.getRequerimiento(), contMes);
                        
                        diferenciaMes = cantXmes - new BigDecimal(reqOriginal.getCostoAnual())
                                .multiply(new BigDecimal(reqOriginal.getCostoUnitario()))
                                .setScale(2, RoundingMode.HALF_UP).doubleValue();
                        
                        bolGrabo = super.getResultSQLUpdateRecalendarizacion(folio, codPpto.getRamoId(), codPpto.getDepto(),
                                codPpto.getFinalidad(), codPpto.getFuncion(), codPpto.getSubfuncion(),codPpto.getPrograma(),
                                codPpto.getProgCONAC(), codPpto.getTipoProy(), Integer.parseInt(codPpto.getProyecto()), codPpto.getMeta(), codPpto.getAccion(),
                                codPpto.getPartida(), codPpto.getTipoGasto(), codPpto.getFuente(), codPpto.getFondo(), codPpto.getRecurso(), codPpto.getMunicipio(),
                                codPpto.getDelegacion(), codPpto.getRelLaboral(), movRequerimiento.getRequerimiento(), contMes, diferenciaMes, reqOriginal.getTipoRequerimiento());

                        contMes++;
                    }
                } else {
                    bolGrabo = false;
                    break;
                }
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
        return bolGrabo;
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

}
