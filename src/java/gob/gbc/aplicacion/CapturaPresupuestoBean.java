/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.ConceptoIngreso;
import gob.gbc.entidades.PresupuestoIngreso;
import gob.gbc.entidades.Ramo;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ugarcia
 */
public class CapturaPresupuestoBean extends ResultSQL {

    Bitacora bitacora;

    public CapturaPresupuestoBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);

        bitacora = new Bitacora();
    }

    public List<Ramo> getRamosByUsuario(int year, String usuario) throws Exception {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        try {
            ramoList = super.getResultSQLRamoByUsuarioPara(usuario, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramoList;
    }

    public List<ConceptoIngreso> getConceptoIngreso(int year) {
        List<ConceptoIngreso> conceptoIngresos = new ArrayList<ConceptoIngreso>();
        conceptoIngresos = super.getResultSQLGetConceptoIngreso(year);
        return conceptoIngresos;
    }

    public List<PresupuestoIngreso> getPresupuestoIngresoList(int year, String ramo, String concepto) throws Exception {
        List<PresupuestoIngreso> presupuestoList = new ArrayList<PresupuestoIngreso>();
        try {
            presupuestoList = super.getResultSQLGetPresupuestoIngresoByRamoConcepto(year, ramo, concepto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return presupuestoList;
    }

    public PresupuestoIngreso getPresupuestoIngresoById(int year, String ramo, String concepto, int pptoId) {
        PresupuestoIngreso presupuesto = new PresupuestoIngreso();
        try {
            presupuesto = super.getResultSQLGetPresupuestoIngresoById(year, ramo, concepto, pptoId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return presupuesto;
    }

    public boolean insertPresupuestoIngreso(int year, String ramo, String concepto, int pptoId, String descr, double ene,
            double feb, double mar, double abr, double may, double jun, double jul,
            double ago, double sep, double oct, double nov, double dic) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertPresupuestoIngreso(year, ramo, concepto, pptoId, descr,
                ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic);
        return resultado;
    }

    public boolean updatePresupuestoIngreso(int year, String ramo, String concepto, int pptoId, String descr, double ene,
            double feb, double mar, double abr, double may, double jun, double jul,
            double ago, double sep, double oct, double nov, double dic) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdatePresupuestoIngreso(year, ramo, concepto, pptoId, descr,
                ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic);
        return resultado;
    }

    public boolean deletePresupuestoIngreso(int year, String ramo, String concepto, int pptoId) {
        boolean resultado = false;
        resultado = super.getResultSQLDeletePresupuestoIngreso(year, ramo, concepto, pptoId);
        return resultado;
    }

    public int getMaxPresupuestoIngreso(int year, String ramo, String concepto) {
        int maxPPTO = 0;
        try {
            maxPPTO = super.getResultSLQGetMaxPresupuestoIngreso(year, ramo, concepto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return maxPPTO;
    }

    public List<PresupuestoIngreso> getSubconceptosPresupuestoIngresoList(int year, String ramo, String concepto, long caratula) throws Exception {
        List<PresupuestoIngreso> presupuestoList = new ArrayList<PresupuestoIngreso>();
        try {
            presupuestoList = super.getResultSQLGetSubconceptosPresupuestoIngreso(year, ramo, concepto, caratula);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return presupuestoList;
    }
}
