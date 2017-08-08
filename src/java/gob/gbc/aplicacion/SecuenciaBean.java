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
 * @author ugarcia
 */
public class SecuenciaBean extends ResultSQL {
    Bitacora bitacora;
    public String mensaje = new String();

    public SecuenciaBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public SecuenciaBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }
    
    public boolean rebootMovoficiosSecuencia(int year){
        boolean resultado = false;
        try{
            if(!super.getResultSQLIsSequenceRebooted(year)){
                resultado = super.getResultSQLRebootMovoficiosSequence();
                if(super.getResultSQLIsYearReinicioRegistrado(year)){
                    resultado = super.getResultSQLUpdateRegistroReinicio(year, true);
                }else{
                    resultado = super.getResultSQLInsertRegistroReinicio(year, 1, 0);
                }
                if(!resultado)
                    mensaje = "Falló al registrar reinicio";
            }else{
                mensaje = "Este proceso ya se realizó este año";
                resultado = false;
            }
        }catch(Exception ex){
            mensaje = "Ocurrio un error";
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }
    
}
