/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.aplicacion;

import gob.gbc.entidades.Ramo;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.io.IOException;

/** 
 *
 * @author ugarcia
 */
public class RamoBean extends ResultSQL{
    Bitacora bitacora;
    public RamoBean() throws Exception{        
        super();        
        
        bitacora = new Bitacora();
    }
    public RamoBean(String tipoDependecia) throws Exception{        
        super(tipoDependecia);
        bitacora = new Bitacora();
    }
    
    public Ramo getRamoById(int ramoId, int year){
        Ramo ramo = new Ramo();
        try {
            ramo = super.getRamoByIdAndYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramo;
    }
    
    public boolean actualizaResponsableRamo(String rfc, String homoclave, int ramoId, int year){
        boolean resultado = false;
        try{
            resultado = super.getResultSQLActualizaResponsableRamo(rfc, homoclave, year, ramoId);
        }catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }
    
}
