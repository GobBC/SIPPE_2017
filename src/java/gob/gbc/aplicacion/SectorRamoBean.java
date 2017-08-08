/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.SectorRamo;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class SectorRamoBean extends ResultSQL {

    Bitacora bitacora;

    public SectorRamoBean() throws Exception {
        super();

        bitacora = new Bitacora();
    }

    public SectorRamoBean(String tipoDependencia) throws Exception {
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

    public ArrayList<SectorRamo> getObtieneSectorRamos(String sYear, String sClaveIndicador) {
        
        ArrayList<SectorRamo> arrSectoresRamos = null;
        
        try {
            arrSectoresRamos = super.getResultSQLObtieneSectorRamos(sYear, sClaveIndicador);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        
        return arrSectoresRamos;
        
    }

}
