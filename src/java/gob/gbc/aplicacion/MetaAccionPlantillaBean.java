/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;

/**
 *
 * @author jarguelles 
 */
public class MetaAccionPlantillaBean extends ResultSQL {

    Bitacora bitacora;

    public MetaAccionPlantillaBean() throws Exception {
        super();        
        bitacora = new Bitacora();
    }
    
    public MetaAccionPlantillaBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public boolean deleteMetaAccionPlantilla(String ramoId, String prgId, String deptoId, int metaId, int accionId, String fuenteId, String fondoId, String recursoId, int year) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteMetaAccionPlantilla(ramoId, prgId, deptoId, metaId, accionId, fuenteId, fondoId, recursoId, year);
        return resultado;
    }

    public boolean insertMetaAccionPlantilla(String ramoId, String prgId, String deptoId, int metaId, int accionId, String fuenteId, String fondoId, String recursoId, String tipoGasto, int year) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertMetaAccionPlantilla(ramoId, prgId, deptoId, metaId, accionId, fuenteId, fondoId, recursoId, tipoGasto, year);
        return resultado;
    }
    
    
    public boolean deleteAsigandosPlantilla(int year, String ramo, String[] depto){
        boolean resultado = false;
        if(depto.length > 0){ 
            for(int it = 0; it < depto.length; it++){
                resultado = super.getResultSQLDeleteAsignado(year, ramo, depto[it]);
            }
        }
        return resultado;
    }  
}
