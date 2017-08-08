/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Proyeccion;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Utilerias;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ugarcia
 */
public class CargaProyeccionBean extends ResultSQL {

    Bitacora bitacora;

    public String partida = new String();

    public CargaProyeccionBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);

        bitacora = new Bitacora();
    }

    public boolean isRamoCierrePOA(String ramo, int year) {
        boolean result = false;
        try {
            result = super.getResultSQLisPOACerrado(ramo, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return result;
    }

    public boolean validaRamoParaestatal(XSSFWorkbook archivo, String ramo) {
        boolean valida = false;
        int row = 0;
        Sheet hojaXLS;
        Row rows;
        hojaXLS = archivo.getSheetAt(0);
        row = hojaXLS.getPhysicalNumberOfRows();
        for (int it = 0; it < row; it++) {
            rows = hojaXLS.getRow(it);
            if (rows != null && rows.getCell(0) != null) {
                if (Utilerias.getStringValueProyeccion(rows.getCell(0),false).equals(ramo)) {
                    valida = true;
                } else {
                    valida = false;
                    break;
                }
            } else {
                break;
            }
        }
        return valida;
    }

    public boolean validaRamoPartida(XSSFWorkbook archivo, int year) {
        boolean valida = false;
        int row = 0;       
        String errorV = new String();
        
        
        Sheet hojaXLS;
        Row rows;
        hojaXLS = archivo.getSheetAt(0);
        row = hojaXLS.getPhysicalNumberOfRows();
        try {
            errorV = super.getResultSQLGetValidaParametros(hojaXLS, year);
            if(errorV.isEmpty())
                valida = true;
            else
                partida = errorV;
                
        } catch (Exception ex) {
            valida = false;
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return valida;
    }

    public Proyeccion getProyeccion(String ramo, String programa, String partida, int year) {
        Proyeccion proyeccion = new Proyeccion();
        try {
            proyeccion = super.getResultSQLgetProyeccion(ramo, programa, partida, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return proyeccion;
    }

    public boolean insertProyeccion(List<Proyeccion> proyeccionList, int year) {
        boolean result = false;
        result = super.getResultSQLInsertProyeccion(proyeccionList, year);
        return result;
    }

    public boolean deleteProyeccion(String ramo, int year) {
        boolean result = false;
        result = super.getResultSQLDeleteProyeccion(ramo, year);
        return result;
    }

    public List<String> getYears() {
        List<String> yearList = new ArrayList<String>();
        try {
            yearList = super.getYears();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return yearList;
    }

    public boolean getCountRamoAnterior(String ramo, int year) {
        int countRamo = -1;
        try {
            ramo = ramo.trim();
            //countRamo = super.getResultSQLGetCountRamosAnteriores(ramo, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        if (countRamo > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getCountPartidaAnterior(String partida, int year) {
        int countRamo = -1;
        try {
            countRamo = super.getResultSQLGetCountPartidaAnteriores(partida, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        if (countRamo > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean getCountRelLaboralAnterior(String relLaboral, int year) {
        int countRamo = -1;
        try {
            countRamo = super.getResultSQLGetCountRelLaboralAnterior(relLaboral, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        if (countRamo > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getCountProyeccion(int year, String ramo) {
        int presup = 0;
        try {
            presup = super.getResultSQLGetCountProyeccion(year, ramo);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return presup;
    }

}
