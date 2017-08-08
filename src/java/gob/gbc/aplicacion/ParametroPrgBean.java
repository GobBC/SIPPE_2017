/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.aplicacion;

import gob.gbc.entidades.ParametroPrg;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.EnumProcesoEspecial;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author jarguelles
 */
public class ParametroPrgBean extends ResultSQL{
    Bitacora bitacora;
    public ParametroPrgBean() throws Exception{
        super();               
        bitacora = new Bitacora();
    }
    
    public ParametroPrgBean(String tipoDependencia) throws Exception{
        super(tipoDependencia);
        bitacora = new Bitacora();
    }
    
    public boolean getPuedeEditarFinPropositoByRol(String usuario){
        boolean puedeEditar = false;
        try {
            puedeEditar = super.getResultSQLhasProcesoEspecual(usuario, EnumProcesoEspecial.NORMATIVO_META.getProceso());       
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return puedeEditar;
    }
    
}
