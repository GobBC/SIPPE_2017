/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Caratula;
import gob.gbc.entidades.Movimiento;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.RevisionCaratula;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class CaratulaBean extends ResultSQL {

    Bitacora bitacora;

    public CaratulaBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public CaratulaBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
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

    public Caratula existeCaratula(Caratula caratula) {
        Caratula car = null;
        try {
            car = super.getResultSQLExisteCaratula(caratula);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return car;
    }

    public boolean actualizaCaratula(Caratula caratula) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLActualizaCaratula(caratula);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean insertCaratulaOficio(int folio, int year, String ramoSession, long selCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMovCaratula(folio, year, ramoSession, selCaratula);
        return resultado;
    }

    public boolean updateCaratulaOficio(int folio, int year, String ramoSession, long selCaratula, long actCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateMovCaratula(folio, year, ramoSession, selCaratula, actCaratula);
        return resultado;
    }

    public boolean deleteCaratulaOficio(int folio, int year, String ramoSession, long actCaratula) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMovCaratula(folio, year, ramoSession, actCaratula);
        return resultado;
    }

    public boolean saveCaratulaOficio(int folio, int year, String ramoSession, long selCaratula, long selCaratulaAnterior) {
        boolean resultado = false;
        try {
            if (selCaratula > 0) {
                if (selCaratulaAnterior < 0) {
                    resultado = insertCaratulaOficio(folio, year, ramoSession, selCaratula);
                } else {
                    resultado = updateCaratulaOficio(folio, year, ramoSession, selCaratula, selCaratulaAnterior);
                }
            } else {
                if (selCaratula == -2) {
                    resultado = deleteCaratulaOficio(folio, year, ramoSession, selCaratulaAnterior);
                }
            }
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public Caratula cargaCaratula(Caratula caratula) {
        Caratula car = null;
        try {
            car = super.getResultSQLCargaCaratula(caratula);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return car;
    }

    public Caratula getCaratulaByYearIdRamoIdCaratula(String year, String idRamo, long idCaratula) {
        Caratula car = null;
        try {
            car = super.getResultSQLCaratulaByYearIdRamoIdCaratula(year, idRamo, idCaratula);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return car;
    }

    public ArrayList<Caratula> getResultSQLObtieneCaratulas(String sYear, String sRamos, boolean bFiltraEstatusAbiertas, int tipoCaratula, boolean isNormativo) {
        ArrayList<Caratula> arCaratulas = null;
        try {
            arCaratulas = super.getResultSQLObtieneCaratulas(sYear, sRamos, bFiltraEstatusAbiertas, tipoCaratula, isNormativo);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return arCaratulas;
    }

    public ArrayList<Caratula> getCaratulas(String sYear, String sRamos, boolean bFiltraEstatusAbiertas, int tipoCaratula, boolean isNormativo) {
        ArrayList<Caratula> CaratulaList = new ArrayList();
        try {
            CaratulaList = super.getResultSQLObtieneCaratulas(sYear, sRamos, bFiltraEstatusAbiertas, tipoCaratula, isNormativo);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
            CaratulaList = null;
        }
        return CaratulaList;
    }

    public List<Movimiento> getMovimientosByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        try {
            lineaList = super.getResultMovimientosByCaratula(year, ramo, appLogin, isNormativo, caratula);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (IOException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<Movimiento> getMovimientosReporteByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        try {
            lineaList = super.getResultMovimientosReporteByCaratula(year, ramo, appLogin, isNormativo, caratula);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (IOException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
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

    public boolean disponibleTipoSesion(int year, String ramo, String numSesion, String tipoSesion, long caratula) {
        boolean disponible = false;
        try {
            disponible = super.getResultSQLDisponibleTipoSesion(year, ramo, numSesion, tipoSesion, caratula);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return disponible;
    }

    public boolean isLigadadaIngreso(long caratula) {
        boolean ligada = false;
        try {
            ligada = super.getResultSQLIsLigadaIngreso(caratula);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ligada;
    }

    public boolean isLigadadaPresupuesto(long caratula) {
        boolean ligada = false;
        try {
            ligada = super.getResultSQLIsLigadaPresupuesto(caratula);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ligada;
    }

    public boolean eliminarCaratula(long caratula) {
        boolean resultado = false;
        resultado = super.getResultSQLEliminarCaratula(caratula);
        return resultado;
    }

    public boolean eliminarEstatusIngresoCaratula(long caratula) {
        boolean resultado = false;
        resultado = super.getResultSQLEliminarEstatusIngresoCaratula(caratula);
        return resultado;
    }
}
