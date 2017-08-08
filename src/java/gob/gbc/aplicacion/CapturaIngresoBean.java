/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.CapturaPresupuestoIngreso;
import gob.gbc.entidades.ConceptoIngreso;
import gob.gbc.entidades.Ramo;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class CapturaIngresoBean extends ResultSQL {

    Bitacora bitacora;

    public CapturaIngresoBean(String tipoDependencia) throws Exception {
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

    public List<ConceptoIngreso> getConceptoIngreso(int year) {
        List<ConceptoIngreso> conceptoIngresos = new ArrayList<ConceptoIngreso>();
        conceptoIngresos = super.getResultSQLGetConceptoIngreso(year);
        return conceptoIngresos;
    }

    public List<CapturaPresupuestoIngreso> getPresupuestoIngresoList(int year, String ramo, String concepto, long caratula) throws Exception {
        List<CapturaPresupuestoIngreso> capturaPresupuestoIngresoList = new ArrayList<CapturaPresupuestoIngreso>();
        try {
            capturaPresupuestoIngresoList = super.getResultSQLGetCaratulaPresupIngByCaratulaPresupRamConcep(year, ramo, concepto, caratula);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return capturaPresupuestoIngresoList;
    }

    public CapturaPresupuestoIngreso getCapturaPresupuestoIngresoById(int year, String ramo, String concepto, int subConcepto, long caratula) {
        CapturaPresupuestoIngreso capturaPresupuestoIngreso = new CapturaPresupuestoIngreso();
        try {
            capturaPresupuestoIngreso = super.getResultSQLGetCapturaPresupuestoIngresoById(year, ramo, concepto, subConcepto, caratula);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return capturaPresupuestoIngreso;
    }

    public int getMaxSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto) {
        int maxPPTO = 0;
        try {
            maxPPTO = super.getResultSLQGetMaxSubConceptoCapturaIngreso(caratula, year, ramo, concepto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return maxPPTO;
    }

    public boolean insertSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto, String subConceptoDescr, double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic, String tipoMov) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertSubConceptoCapturaIngreso(caratula, year, ramo, concepto, subConcepto, subConceptoDescr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, tipoMov);
        return resultado;
    }

    public boolean updateSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto, String subConceptoDescr, double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic, String tipoMov) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateSubConceptoCapturaIngreso(caratula, year, ramo, concepto, subConcepto, subConceptoDescr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, tipoMov);
        return resultado;
    }

    public boolean deleteSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteSubConceptoCapturaIngreso(caratula, year, ramo, concepto, subConcepto);
        return resultado;
    }

    public CapturaPresupuestoIngreso getPresupuestoIngresoById(int year, String ramo, String concepto, int subConcepto) {
        CapturaPresupuestoIngreso capturaPresupuestoIngreso = new CapturaPresupuestoIngreso();
        try {
            capturaPresupuestoIngreso = super.getResultSQLPresupuestoIngresoById(year, ramo, concepto, subConcepto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return capturaPresupuestoIngreso;
    }

    public boolean existeSubConceptoCapturaIngreso(int year, String ramo, String concepto, int subconcepto, long caratula) {
        boolean resultado = false;
        try {
            resultado = super.getResultSLQExisteSubConceptoCapturaIngreso(year, ramo, concepto, subconcepto, caratula);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public CapturaPresupuestoIngreso getCalendarizacionIngresoPlusMod(int year, String ramo, String concepto, int subConcepto, long caratula) {
        CapturaPresupuestoIngreso capturaPresupuestoIngreso = new CapturaPresupuestoIngreso();
        try {
            capturaPresupuestoIngreso = super.getResultSQLGetCalendarizacionIngresoPlusMod(year, ramo, concepto, subConcepto, caratula);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return capturaPresupuestoIngreso;
    }

    public int[] getMesesTrimestreByMes(int mes, boolean validaTrimestre) {
        int meses[] = new int[3];
        try {
            meses = super.getResultSQLgetMesTrimestreByPeriodo(mes, validaTrimestre);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return meses;
    }

    public boolean insertStatusIngresoModificado(long caratula, int year, String ramo, String concepto, String status) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertStatusIngresoModificado(caratula, year, ramo, concepto, status);
        return resultado;
    }

    public boolean existeStatusModificacionIngreso(long caratula, int year, String ramo, String concepto) {
        boolean existe = false;
        int cuenta = 0;
        try {
            cuenta = super.getResultExisteStatusModificacionIngreso(caratula, year, ramo, concepto);
            if (cuenta > 0) {
                existe = true;
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return existe;
    }

    public boolean updateStatusIngresoModificado(long caratula, int year, String ramo, String concepto, String status) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateStatusModificacionIngreso(caratula, year, ramo, concepto, status);
        return resultado;
    }

    public String getStatusModificacionIngreso(long caratula, int year, String ramo, String concepto) {
        String status = new String();
        try {
            status = super.getResultGetStatusModificacionIngreso(caratula, year, ramo, concepto);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return status;
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
}
