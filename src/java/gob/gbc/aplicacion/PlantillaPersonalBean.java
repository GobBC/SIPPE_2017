/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.aplicacion;

import gob.gbc.entidades.PlantillaPersonal;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.RelacionLaboral;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author ugarcia
 */
public class PlantillaPersonalBean extends ResultSQL{
    
    Bitacora bitacora;
    
    public PlantillaPersonalBean(String tipoDependencia) throws Exception{
        super(tipoDependencia);    
        
        bitacora = new Bitacora();        
    }
    
    public List<Ramo> getRamosPlantillaPersonal(String usuario, int year) throws Exception{
        List<Ramo> ramoList = new ArrayList<Ramo>();
        try{
            ramoList = super.getResultSQLRamoPlantillaEmple(usuario, year);
        }catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramoList;
    }
    
    public List<PlantillaPersonal> getPlantillaPestonal(int year, String ramo) throws Exception{
        List<PlantillaPersonal> plantillaList = new ArrayList<PlantillaPersonal>();
        try{
            plantillaList = super.getResultSQLGetPlantillaPersonal(year, ramo);
        }catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return plantillaList;
    }
    
    public List<RelacionLaboral> getRelacionLaboralList(int year) throws Exception{
        List<RelacionLaboral> relacionLaboralList = new ArrayList<RelacionLaboral>();
        try{
            relacionLaboralList = super.getResultSQLCatalogoRelLaboralByYear(year);
        }catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return relacionLaboralList;
    }
    
    public PlantillaPersonal getPlantillaPersonalById(int yeat, String ramo, String relLaboral){
        PlantillaPersonal plantillaPersonal = new PlantillaPersonal();
        try{
            plantillaPersonal = super.getResultSQLGetPlatnillaPersonalById(yeat, ramo, relLaboral);
        }catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return plantillaPersonal;
    }
    
    public boolean insertPlantillaPersonal(int year, String ramo, String relLab, double cantidad){
        boolean resultado = false;
        resultado = super.getResultSQLInsertPlantillaPersonal(year, ramo, relLab, cantidad);
        return resultado;
    }
    
    public boolean updatePlantillaPersonal(int year, String ramo, String relLaboral, double cantidad, String relLaboralAnt){
        boolean resultado = false;
        resultado = super.getResultSQLUpdatePlantillaPersonal(year, ramo, relLaboral, cantidad, relLaboralAnt);
        return resultado;
    }
    
    public boolean deletePlantillaPersonal(int year, String ramo, String relLaboral){
        boolean resultado = false;
        resultado = super.getResultSQLDeletePlantillaPersonal(year, ramo, relLaboral);
        return resultado;
    }
    
}
