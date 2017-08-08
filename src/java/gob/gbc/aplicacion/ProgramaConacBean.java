/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.aplicacion;

import gob.gbc.entidades.ProgramaConac;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author jarguelles
 */
public class ProgramaConacBean extends ResultSQL{
    Bitacora bitacora;
    public ProgramaConacBean() throws Exception{
        super();  
        
        bitacora = new Bitacora();
    }
    public ProgramaConacBean(String tipoDependecia) throws Exception{
        super(tipoDependecia);
        bitacora = new Bitacora();
    }
    
    public List<ProgramaConac> getDescripcionByProgramaConacAnio(String programaId, int year){
        List<ProgramaConac> ProgramaConacList = new ArrayList<ProgramaConac>();
        try {
            ProgramaConacList = super.getResultProgramaConac(programaId,year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ProgramaConacList;
    }
    
}
