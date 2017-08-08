/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.IndicadorSectorRamo;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class IndicadorSectorRamoBean extends ResultSQL {

    Bitacora bitacora;

    public IndicadorSectorRamoBean() throws Exception {
        super();

        bitacora = new Bitacora();
    }

    public IndicadorSectorRamoBean(String tipoDependencia) throws Exception {
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

    public List<IndicadorSectorRamo> getObtieneIndicadorSectorRamos(String sYear, String sClaveIndicador, String sRamo) {

        List<IndicadorSectorRamo> arrIndicadorSectorRamos = null;

        try {
            arrIndicadorSectorRamos = super.getResultSQLObtieneIndicadorSectorRamos(sYear, sClaveIndicador, sRamo);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }

        return arrIndicadorSectorRamos;

    }

    public boolean getDesligarIndicadorRamoSector(String sYear, String sClaveIndicador, String sRamo, String sPrograma) {

        boolean bResultado = false;

        try {
            bResultado = super.getResultSQLDesligarIndicadorRamoSector(sYear, sClaveIndicador, sRamo, sPrograma);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }

        return bResultado;

    }

    public boolean getLigarIndicadorRamoSector(String sYear, String sClaveIndicador, String sRamo, String sPrograma) {

        boolean bResultado = false;

        try {
            bResultado = super.getResultSQLLigarIndicadorRamoSector(sYear, sClaveIndicador, sRamo, sPrograma);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }

        return bResultado;

    }

}
