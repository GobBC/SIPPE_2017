/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Dependencia;
import gob.gbc.entidades.Partida;
import gob.gbc.entidades.Ramo;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author SYSTEM
 */
public class ReporteExcelBean extends ResultSQL {

    Bitacora bitacora;

    public ReporteExcelBean() throws Exception {
        super();           
        
        bitacora = new Bitacora();
    }

    public ReporteExcelBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }

    public List<Ramo> getRamoListByUsuario(int year, String usuario) {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        try {
            ramoList = super.getResultRamoByYear(year, usuario);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramoList;
    }

    public int getValidarPresDetalleAccionString(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultGetValidarPresDetalleAccionString(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }

    public int getValidarPresXCodigoPrg(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarPresXCodigoPrg(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }

    public int getValidarReporteLineasEstrategicas(int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarReporteLineasEstrategicas(year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }

    public int getValidarReportePptoCalAnualCodigo(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarReportePptoCalAnualCodigo(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }

    public int getValidarReportePptoCalAnualRamoPartida(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarReportePptoCalAnualRamoPartida(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }

    public int getValidarReportePptoCalAnualRamoProgramaPartida(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarReportePptoCalAnualRamoProgramaPartida(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }

    public int getValidarReportePptoAnualRamoGrupo(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarReportePptoAnualRamoGrupo(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }

    public int getValidarReportePptoAnualRamoProgramaGrupo(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarReportePptoAnualRamoProgramaGrupo(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }

    public int getValidarReportePptoAnualCodigo(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarReportePptoAnualCodigo(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }
    
        public int getValidarReporteComparativoPresupuesto(String ramos, int year, String queryPartidas) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarReporteComparativoPresupuesto(ramos, year, queryPartidas);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }
    
        public List<Dependencia> getDependencia(String ramoId, int year, String programaId) {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        try {
            dependenciaList = super.getResultDependenciasByRamo(ramoId, year, programaId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return dependenciaList;
    }
        
    public List<Partida> getPartidaList(int year, String tipoDependencia) {
        List<Partida> partidaList = new ArrayList<Partida>();
            boolean isParaestatal = false;
        try {
            partidaList = super.getResultSQLGetPartidasGeneral(year);            
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return partidaList;
    }
    
    public boolean getParametroIndicadores(){
        boolean resultado = false;
        String parametro = new String();
        try{
            parametro = super.getResultSQLgetParametroIndicadores();
            if(parametro.equalsIgnoreCase("S")){
                resultado = true;
            }
        }catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }
    
    public int getCountPresupuestoCongelado(int year){
        int total = 0;
        try {
            total = super.getResultSQLCountCongelaPresupuesto(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return total;
    }
    
    
  public int getRolNormativo(int rol){
        int isNormativo = 0;
        try {
            isNormativo = super.getRolNormativo(rol);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isNormativo;
    } 
    
  public int getValidarPresXCodigoPrgModif(String ramos, int year) {
        int cuenta = 0;
        try {
            cuenta = super.getResultValidarPresXCodigoPrgModif(ramos, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cuenta;
    }
  
}
