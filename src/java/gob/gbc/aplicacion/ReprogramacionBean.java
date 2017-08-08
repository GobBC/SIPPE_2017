/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Caratula;
import gob.gbc.entidades.EstatusMov;
import gob.gbc.entidades.MovOficioAccionEstimacion;
import gob.gbc.entidades.MovOficioEstimacion;
import gob.gbc.entidades.Movimiento;
import gob.gbc.entidades.MovimientoOficioAccion;
import gob.gbc.entidades.MovimientoOficioMeta;
import gob.gbc.entidades.MovimientosReprogramacion;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.ReprogramacionAccion;
import gob.gbc.entidades.ReprogramacionMeta;
import gob.gbc.entidades.Usuario;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Fechas;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author ugarcia
 */
public class ReprogramacionBean extends ResultSQL {

    Bitacora bitacora;

    public ReprogramacionBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
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

    public List<ReprogramacionMeta> getMovOficioMeta(int folio, int year) {
        List<ReprogramacionMeta> movimientoMetaList = new ArrayList<ReprogramacionMeta>();
        try {
            movimientoMetaList = super.getResultGetMovOficioMeta(folio, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movimientoMetaList;
    }

    public List<ReprogramacionAccion> getMovOficioAccion(int folio, int year) {
        List<ReprogramacionAccion> movimientoAccionList = new ArrayList<ReprogramacionAccion>();
        try {
            movimientoAccionList = super.getResultGetMovOficioAccion(folio, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movimientoAccionList;
    }

    public MovimientosReprogramacion getInfoMovOficioReprogramacion(int folio) {
        MovimientosReprogramacion movReprog = new MovimientosReprogramacion();
        try {
            movReprog = super.getResultGetInfoMovOficioReprogramacion(folio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movReprog;
    }

    public MovimientosReprogramacion getMovimientosReprogramacion(int year, String ramoSession, int folio) {
        MovimientosReprogramacion movReprogramacion = new MovimientosReprogramacion();
        try {
            movReprogramacion = this.getInfoMovOficioReprogramacion(folio);
            movReprogramacion.setMovCaratula(this.getCaratulaReprogramacion(year, movReprogramacion.getRamo(), folio));
            movReprogramacion.setMovOficioMetaList(this.getMovOficioMeta(folio, year));
            movReprogramacion.setMovOficioAccionList(this.getMovOficioAccion(folio, year));
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movReprogramacion;
    }
    
    public boolean updateJustificacionMovOficios(int folio, String justificacion) {
        boolean resultado = false;
        resultado = super.getResultSQLupdateJustificacionMovoficio(folio, justificacion, new String());
        return resultado;
    }
    
    public String saveMovimientoReprogramacion(MovimientosReprogramacion movReprog, int folio,
            boolean isActual, int year, String fecha, String ramoSession, long selCaratula, String justificacion) {
        List<ReprogramacionMeta> reproMetaList = new ArrayList<ReprogramacionMeta>();
        List<ReprogramacionAccion> reproAccionList = new ArrayList<ReprogramacionAccion>();
        String mensaje = new String();
        boolean resultado = false;
        try {
            reproMetaList = movReprog.getMovOficioMetaList();
            reproAccionList = movReprog.getMovOficioAccionList();
            if (folio == 0) {
                folio = this.getSecuenciaMovimientos(isActual, year);
                movReprog.setOficio(folio);
                resultado = this.saveOficioReprogramacion(movReprog, fecha, isActual);
                if (!(selCaratula < 0)) {
                    resultado = this.saveCaratulaReprogramacion(folio, year, ramoSession, selCaratula);
                }
            } else {
                resultado = this.updateJustificacionMovOficios(folio, justificacion);
                if(resultado){
                    resultado = this.deleteMovientosReprogramacion(folio);
                    if (!(selCaratula < 0)) {
                        if (movReprog.getMovCaratula().getsIdCaratula() < 0) {
                            resultado = this.saveCaratulaReprogramacion(folio, year, ramoSession, selCaratula);
                        } else {
                            resultado = this.updateCaratulaReprogramacion(folio, year, ramoSession, selCaratula, movReprog.getMovCaratula().getsIdCaratula());
                        }
                    } else {
                        if (selCaratula == -2) {
                            deleteCaratulaReprogramacion(folio, year, ramoSession, movReprog.getMovCaratula().getsIdCaratula());
                        }
                    }
                    mensaje = "Problemas al editar la información";
                }else{
                    mensaje = "Problemas al editar la justificación";
                }
            }
            if (resultado) {
                for (ReprogramacionMeta reproTemp : reproMetaList) {
                    resultado = this.saveMovimientoOficioMeta(reproTemp.getMetaInfo(), folio, reproTemp.getValAutorizado());
                    if (resultado) {
                        for (MovOficioEstimacion estimacionTemp : reproTemp.getMovEstimacionList()) {
                            resultado = this.saveMovimientoEstimacionMeta(folio, estimacionTemp, reproTemp.getMetaInfo().getNvaCreacion(), reproTemp.getValAutorizado());
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
                    for (ReprogramacionAccion reproTemp : reproAccionList) {
                        resultado = this.saveMovimientoOficioAccion(reproTemp.getMovOficioAccion(), folio, reproTemp.getValAutorizado());
                        if (resultado) {
                            for (MovOficioAccionEstimacion accionEstimacionTemp : reproTemp.getMovAcionEstimacionList()) {
                                resultado = this.saveMovimientoAccionEstimacionMeta(folio, accionEstimacionTemp, 
                                        reproTemp.getMovOficioAccion().getNvaCreacion(), "N",reproTemp.getValAutorizado());
                                if (!resultado) {
                                    break;
                                }
                            }
                            if (!resultado) {
                                mensaje = "Error al insertar acción";
                                break;
                            }
                        }else{
                            mensaje = "Error al insertar acción";
                            break;
                        }
                    }
                }
            }
            if (resultado) {
                super.transaccionCommit();
                //super.transaccionRollback();
                mensaje = "$" + String.valueOf(folio);
            } else {
                super.transaccionRollback();
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

    public boolean deleteMovientosReprogramacion(int folio) {
        boolean resultado = false;
        resultado = this.deleteMovimientoOficioMeta(folio);
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

    public boolean saveOficioReprogramacion(MovimientosReprogramacion movReprogramacion, String fecha, boolean isActual) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovOficio(movReprogramacion.getOficio(), movReprogramacion.getAppLogin(),
                movReprogramacion.getStatus(), movReprogramacion.getTipoMovimiento(), movReprogramacion.getJutificacion(), fecha, isActual, "N");
        return resultado;
    }

    public boolean saveMovimientoOficioMeta(MovimientoOficioMeta movOficioMeta, int folio, String valAutorizado) {
        boolean resultado = false;
        if (movOficioMeta.getFinalidad() == null) {
            movOficioMeta.setFinalidad(movOficioMeta.getClasificacionFuncionalId().split("\\.")[0]);
        }
        if (movOficioMeta.getFuncion() == null) {
            movOficioMeta.setFuncion(movOficioMeta.getClasificacionFuncionalId().split("\\.")[1]);
        }
        if (movOficioMeta.getSubfuncion() == null) {
            movOficioMeta.setSubfuncion(movOficioMeta.getClasificacionFuncionalId().split("\\.")[2]);
        }
        resultado = super.getResultSQLInsertMovReprogramacionMeta(movOficioMeta.getYear(), folio,
                movOficioMeta.getRamoId(), movOficioMeta.getPrgId(), movOficioMeta.getMetaId(),
                movOficioMeta.getMetaDescr(), movOficioMeta.getCalculoId(),
                movOficioMeta.getClaveMedida(), movOficioMeta.getFinalidad(), movOficioMeta.getFuncion(),
                movOficioMeta.getSubfuncion(), movOficioMeta.getTipoProy(), movOficioMeta.getProyId(),
                movOficioMeta.getLineaPedId(), movOficioMeta.getCompromisoId(), movOficioMeta.getPonderacionId(),
                movOficioMeta.getLineaSectorialId(), movOficioMeta.getCriterioTransversalidad(), movOficioMeta.getNvaCreacion(),valAutorizado);
        return resultado;
    }

    public boolean saveMovimientoOficioAccion(MovimientoOficioAccion movOficioAccion, int folio, String valAutorizado) {
        boolean resultado = false;
        resultado = getResultSQLInsertMovReprogramacionAccion(movOficioAccion.getYear(), folio, movOficioAccion.getRamoId(),
                movOficioAccion.getProgramaId(), movOficioAccion.getDeptoId(), movOficioAccion.getMetaId(), movOficioAccion.getAccionId(),
                movOficioAccion.getAccionDescr(), movOficioAccion.getCalculo(), movOficioAccion.getClaveMedida(),
                movOficioAccion.getGrupoPoblacional(), movOficioAccion.getBenefHombre(), movOficioAccion.getBenefMujer(),
                movOficioAccion.getMunicipio(), movOficioAccion.getLocalidad(), movOficioAccion.getLinea(),
                movOficioAccion.getLineaSectorial(), movOficioAccion.getNvaCreacion(), movOficioAccion.getObra(),valAutorizado);
        return resultado;
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

    public boolean saveMovimientoEstimacionMeta(int oficio, MovOficioEstimacion movOficioEst, String nvaCreacion, String valAutorizado) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovEstimacion(oficio, movOficioEst.getYear(), movOficioEst.getRamo(), movOficioEst.getMeta(), movOficioEst.getPeriodo(), movOficioEst.getValor(), nvaCreacion, "N",valAutorizado);
        return resultado;
    }

    public boolean saveMovimientoAccionEstimacionMeta(int folio, MovOficioAccionEstimacion movOficioAccionEst, String nvaCreacion, String indTransf,String valAutorizado) {
        boolean resultado = false;      
        resultado = super.getResultSQLInsertMovAccionEstimacion(folio, movOficioAccionEst.getYear(),
                movOficioAccionEst.getRamo(), movOficioAccionEst.getMeta(),
                movOficioAccionEst.getAccion(), movOficioAccionEst.getPeriodo(), movOficioAccionEst.getValor(), nvaCreacion, indTransf,valAutorizado);
        return resultado;
    }

    public String actualizaMovOficio(int folio, String tipoMov, String tipoUsuario, String estatus) {
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

    public boolean updateMovimientoOficio(int oficio, String estatus, String fecha, boolean isActual, String capturaEspecial) {
        boolean resultado = false;
        resultado = super.getResultSQLupdateMovOficio(oficio, estatus, fecha, isActual, capturaEspecial);
        return resultado;
    }

    public Caratula getCaratulaReprogramacion(int year, String ramoSession, int folio) {
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

    public boolean saveCaratulaReprogramacion(int oficio, int year, String ramoSession, long selCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovCaratula(oficio, year, ramoSession, selCaratula);
        return resultado;
    }

    public boolean deleteMovimientoCaratula(int folio) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovCaratula(folio);
        return resultado;
    }

    public boolean updateCaratulaReprogramacion(int folio, int year, String ramoSession, long selCaratula, long actCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateMovCaratula(folio, year, ramoSession, selCaratula, actCaratula);
        return resultado;
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

    public boolean deleteCaratulaReprogramacion(int folio, int year, String ramoSession, long actCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovCaratula(folio, year, ramoSession, actCaratula);
        return resultado;
    }
}
