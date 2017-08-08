/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.aplicacion;

import gob.gbc.entidades.Fin;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author ugarcia
 */
public class FinBean extends ResultSQL{
    Bitacora bitacora;
    
    public FinBean() throws Exception{
        super();        
        bitacora = new Bitacora();
    }
    
    public FinBean(String tipoDependencia) throws Exception{
        super(tipoDependencia);
        bitacora = new Bitacora();
    }
    
    public List<Fin> getFinByRamo(int ramoId, int year){
        List<Fin> finList = new ArrayList<Fin>();
        try {
            finList = super.getResultFinRamo(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return finList;
    }
    
        public List<Fin> getFinOnRamoPrograma(String ramoId, int year){
        List<Fin> finList = new ArrayList<Fin>();
        try {
            finList = super.getResultFinOnRamoPrograma(ramoId,year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return finList;
    }
    
}
