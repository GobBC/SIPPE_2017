/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.aplicacion;

import gob.gbc.entidades.Persona;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author ugarcia
 */


public class PersonaBean extends ResultSQL{
    Bitacora bitacora;
    public PersonaBean() throws Exception{
        super();     
        
        bitacora = new Bitacora();
    }
    public PersonaBean(String tipoDependecia) throws Exception{
        super(tipoDependecia);
        bitacora = new Bitacora();
    }
    
    public List<Persona> getEmpleadosByNombre(String nombre, String apPaterno, String apMaterno){
        List<Persona> personaList = new ArrayList<Persona>();
        try {
            personaList = super.getResultadoEmpleadoByNombre(nombre, apPaterno, apMaterno);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return personaList;
    }
    
    public Persona getEmpleadoByRFC(String rfc, String homoclave){
        Persona persona = new Persona();
        try {
            persona = super.getResultPersonaByRFC(rfc, homoclave);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return persona;
    }
    
}
