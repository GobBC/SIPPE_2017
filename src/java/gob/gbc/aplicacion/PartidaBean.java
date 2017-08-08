/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Partida;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class PartidaBean extends ResultSQL {

    Bitacora bitacora;

    public PartidaBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public PartidaBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public Partida getPartidaByIdPartidaYear(String idPartida, int year) {
        Partida partida = new Partida();
        boolean isParaestatal = false;
        try {
            partida = super.getResultSQLGetPartidaByIdPartidaYear(idPartida, year);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return partida;
    }
}
