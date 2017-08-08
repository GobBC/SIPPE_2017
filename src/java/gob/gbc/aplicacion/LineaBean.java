/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Linea;
import gob.gbc.entidades.LineaSectorial;
import gob.gbc.entidades.Proyecto;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
/**
 *
 * @author ugarcia
 */
public class LineaBean extends ResultSQL {

    Bitacora bitacora;

    public LineaBean() throws Exception {        
        super();        
        bitacora = new Bitacora();
    }
    
    public LineaBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public String getProgramaString(int programa) {
        String strProg = new String();
        strProg = String.valueOf(programa);
        if (strProg.length() == 1) {
            strProg = "00" + strProg;
        } else if (strProg.length() == 2) {
            strProg = "0" + strProg;
        }
        return strProg;
    }

    public String getRamoString(int ramo) {
        String strProg = new String();
        strProg = String.valueOf(ramo);
        if (strProg.length() == 1) {
            strProg = "0" + strProg;
        }
        return strProg;
    }

    public List<Linea> getLineaByPrograma(String ramoId, String programaId, int year) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultLineaByPrograma(ramoId, programaId, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<Linea> getLineaByProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProy) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultLineaByProyecto(ramoId, programaId, proyectoId, year,tipoProy);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<LineaSectorial> saveLineaSectorial(int year, String[] arregloLineas, String ramoId, String programaId, int proyectoId, String estrategia, String ultimaLineaSectorial, String[] arrBorrarLineasSectoriales, String tipoProy) {
        Proyecto proyecto = new Proyecto();
        boolean resBorrar = false;
        int totalLineasPed = 0;
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        List<LineaSectorial> lineaSectorialTemp = new ArrayList<LineaSectorial>();
        LineaSectorial lineaSectorial = new LineaSectorial();
        //int totalLineasPed = 0;
        try {
            proyecto = super.getResultSQLProyectoByTipoProyecto(ramoId, programaId, proyectoId, year, tipoProy);
            totalLineasPed = this.getResultSQLContLineasPed(year, ramoId, programaId, proyectoId, estrategia, tipoProy);

            if (totalLineasPed == 0) {
                Linea lineaTemp = new Linea();
                lineaTemp = super.getResultGetLineaEstatal(year, estrategia);
                super.getResultInsertaLinea(year, ramoId, programaId, proyecto.getTipoProyecto(), proyectoId, lineaTemp.getLineaId());
            }

            for (int it = 0; it < arregloLineas.length; it++) {
                if ((!arregloLineas[it].equals("-1"))) {
                    lineaSectorial = new LineaSectorial();
                    lineaSectorial.setLineaId(arregloLineas[it]);
                    lineaSectorialTemp.add(lineaSectorial);
                    if (!arregloLineas[it].equals("")) {
                        resBorrar = this.deleteLineasSectoriales(ramoId, programaId, proyectoId, year, arregloLineas[it], tipoProy, estrategia);
                    }
                }
            }

            for (int ix = 0; ix < arrBorrarLineasSectoriales.length; ix++) {
                if (!arrBorrarLineasSectoriales[ix].equals("")) {
                    resBorrar = this.deleteLineasSectoriales(ramoId, programaId, proyectoId, year, arrBorrarLineasSectoriales[ix], tipoProy, estrategia);
                }
            }

            for (LineaSectorial linea : lineaSectorialTemp) {
                if (!linea.getLineaId().equals("")) {
                    super.getResultInsertaLineaSectorial(ramoId, programaId, proyectoId, year, proyecto.getTipoProyecto(), linea.getLineaId(), estrategia);
                }

            }

            lineaSectorialList = this.getLineaSectorialByEstrategia(ramoId, programaId, proyectoId, year, estrategia, tipoProy);

        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaSectorialList;
    }

    public List<Linea> saveLineasByProyecto(int year, String[] arregloLineas, String ramoId, String programaId, int proyectoId, String tipoProy, String[] idsBorrarLineasPed) throws SQLException {
        int totalLineas = 0;
        boolean resBorrar = false;
        //Proyecto proyecto = new Proyecto();
        List<Linea> lineaList = new ArrayList<Linea>();
        List<Linea> lineaTemp = new ArrayList<Linea>();
        Linea linea;
        try {

            //proyecto = super.getResultSQLProyectoByTipoProyecto(ramoId, programaId, proyectoId, year, tipoProy);

            for (int it = 0; it < arregloLineas.length; it++) {
                if (!arregloLineas[it].equals("-1")) {
                    linea = new Linea();
                    linea.setLineaId(arregloLineas[it]);
                    lineaTemp.add(linea);

                }
            }


            for (int ix = 0; ix < idsBorrarLineasPed.length; ix++) {
                if (!idsBorrarLineasPed[ix].equals("")) {
                    resBorrar = super.getResultDeleteLineas(ramoId, programaId, proyectoId, year, tipoProy, idsBorrarLineasPed[ix]);
                }
            }

            for (Linea lintemp : lineaTemp) {
                if (!lintemp.getLineaId().equals("")) {
                    int totalLineasPed = this.getResultSQLContLineasPed(year, ramoId, programaId, proyectoId, lintemp.getLineaId(), tipoProy);

                    //linea = new Linea();
                    //linea = super.getResultGetLineaEstatal(year, lintemp.getLineaId());
                    if (totalLineasPed == 0) {
                        super.getResultInsertaLinea(year, ramoId, programaId, tipoProy, proyectoId, lintemp.getLineaId());
                    }
                }
            }

            lineaList = super.getResultLineaByTipoProyecto(ramoId, programaId, proyectoId, year, tipoProy);

        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<LineaSectorial> getLineaSectorialByProyecto(String ramoId, String programaId, int proyectoId, int year) {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        try {
            lineaSectorialList = super.getRestulLineaSectorialByProyecto(ramoId, programaId, proyectoId, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaSectorialList;
    }

    public List<LineaSectorial> getLineaSectorialByPED(String est, int year, String ramoId, String programa, int proyecto) {
        List<LineaSectorial> sectorialList = new ArrayList<LineaSectorial>();
        try {
            sectorialList = super.getResultLineaSectorialByPED(est, year, ramoId, programa, proyecto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return sectorialList;
    }

    public List<LineaSectorial> getLineaSectorial(int year) {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        try {
            lineaSectorialList = super.getResultSQLLineaSectorial(year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaSectorialList;
    }

    public boolean updateProyecto(String rfc, String homoclave, int depto, int ramoId, int programaId,
            int proyectoId, int year) {
        boolean resultado = false;
        resultado = super.getResultSQLActualizaProyecto(rfc, homoclave, depto, ramoId, programaId, proyectoId, year);
        return resultado;
    }

    public boolean deleteLineasSectoriales(String ramoId, String programaid, int proyectoId, int year, String lineaSectorial, String tipoProy, String estrategia) {
        boolean resultado = false;
        resultado = super.getResultDeleteLineasSectoriales(ramoId, programaid, proyectoId, year, lineaSectorial, tipoProy, estrategia);
        return resultado;
    }

    public int getTotalLineasPED(int year, String ramoId, String programaId, int proyectoId) {
        int lineas = 0;
        lineas = super.getResultSQLNumeroLineas(year, ramoId, programaId, proyectoId);
        return lineas;
    }

    public int getTotalLineasSectorial(int year, String ramoId, String programaId, int proyectoId) {
        int sectorial = 0;
        sectorial = super.getResultSQLNumeroSectorial(year, ramoId, programaId, proyectoId);
        return sectorial;
    }

    public List<LineaSectorial> getLineaSectorialByEstrategia(String ramoId, String programaId, int proyectoId, int year, String estrategia, String tipoProy) {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        try {
            lineaSectorialList = super.getRestulLineaSectorialByEstrategia(ramoId, programaId, proyectoId, year, estrategia, tipoProy);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaSectorialList;
    }

    public List<LineaSectorial> getLineaSectorialByYearEstrategia(int year, String estrategia) {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        try {
            lineaSectorialList = super.getResultSQLLineaSectorialByYearEstrategia(year, estrategia);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaSectorialList;
    }

    public boolean updateProyectoByTipoProyecto(String rfc, String homoclave, String depto, String ramoId, String programaId,
            int proyectoId, int year, String tipoProyecto) {
        boolean resultado = false;
        resultado = super.getResultSQLActualizaProyectoByTipoProyecto(rfc, homoclave, depto, ramoId, programaId, proyectoId, year, tipoProyecto);
        return resultado;
    }

    public List<Linea> getLineaByTipoProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProyecto) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultLineaByTipoProyecto(ramoId, programaId, proyectoId, year, tipoProyecto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }
    
    
    public List<Linea> getLineaSectorialByLineaAccion(String estrategia, int year){
        List<Linea> lineaList = new ArrayList<Linea>();
        try{
            lineaList = super.getResultGetLineaSectorialByLineaAccion(estrategia, year);
        }catch(SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }
}
