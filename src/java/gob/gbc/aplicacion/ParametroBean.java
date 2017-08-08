/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Parametro;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles 
 */
public class ParametroBean extends ResultSQL {

    Bitacora bitacora;

    public ParametroBean() throws Exception {
        super();        
        
        bitacora = new Bitacora();
    }

    public ParametroBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public boolean validarCierreEjercicio(int year) {
        List<Parametro> parametroList = new ArrayList<Parametro>();
        boolean cerrado = false;
        try {
            parametroList = super.validarCierreEjercicioByYear(year);
            if (parametroList.get(0).getYear_ant() ==  year) {
                cerrado = true;
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return cerrado;
    }
}
