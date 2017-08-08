/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.IndicadorGeneralSei;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class IndicadorGeneralSeiBean extends ResultSQL {

    Bitacora bitacora;

    public IndicadorGeneralSeiBean() throws Exception {
        super();

        bitacora = new Bitacora();
    }

    public IndicadorGeneralSeiBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public String getRolNormativo() {
        String normativo = new String();
        try {
            normativo = super.getResultSQLGetRolesPrg();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return normativo;
    }

    public ArrayList<IndicadorGeneralSei> getObtieneIndicadoresGeneralesSei(String sYear) {
        ArrayList<IndicadorGeneralSei> arrIndicadoresGeneralesSei = null;
        try {
            arrIndicadoresGeneralesSei = super.getResultSQLObtieneIndicadoresGeneralesSei(sYear);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return arrIndicadoresGeneralesSei;
    }

}
