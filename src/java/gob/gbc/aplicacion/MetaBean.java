/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.ClasificacionFuncional;
import gob.gbc.entidades.Estimacion;
import gob.gbc.entidades.Evaluacion;
import gob.gbc.entidades.GrupoPoblacional;
import gob.gbc.entidades.Linea;
import gob.gbc.entidades.LineaSectorial;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.MovOficioEstimacion;
import gob.gbc.entidades.TipoCalculo;
import gob.gbc.entidades.TipoCompromiso;
import gob.gbc.entidades.Transversalidad;
import gob.gbc.entidades.UnidadMedida;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ugarcia
 */
public class MetaBean extends ResultSQL {

    Bitacora bitacora;

    public MetaBean() throws Exception {
        super();

        bitacora = new Bitacora();
    }

    public MetaBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }

    public List<Meta> getMetas(String ramoId, String programaId, int proyectoId, int year, String tipoProy) {
        List<Meta> metaList = new ArrayList<Meta>();
        try {
            metaList = super.getResultGetMetas(ramoId, programaId, proyectoId, year, tipoProy);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return metaList;
    }

    public int getCountAccionByMeta(int year, String ramoId, String programaId, int meta) {
        int countMeta = 0;
        try {
            countMeta = super.getResultSQLGetCountAcciones(year, ramoId, programaId, meta);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return countMeta;
    }

    public Meta getMetaById(String ramoId, String programaId, int proyectoId, int metaId, int year, String tipoProyecto) {
        Meta meta = new Meta();
        try {
            meta = super.getResultGetMetaById(ramoId, programaId, proyectoId, metaId, year, tipoProyecto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return meta;
    }

    public List<TipoCalculo> getTipoCalculo() {
        List<TipoCalculo> tipoCalculoList = new ArrayList<TipoCalculo>();
        try {
            tipoCalculoList = super.getResultGeTipoCalculo();
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoCalculoList;
    }

    public List<UnidadMedida> getUnidadMedidaList(int year) {
        List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
        try {
            unidadMedidaList = super.getResultGetUnidadMedidaList(year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return unidadMedidaList;
    }

    public int getMaxMetaId(int year, String ramoId) {
        int metaId = 0;
        try {
            metaId = super.getMaxMeta(year, ramoId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return metaId;
    }

    public List<Estimacion> getEstimacionByMeta(int year, String ramoId, int metaId) {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        try {
            estimacionList = super.getResultSQLGetEstimacion(year, ramoId, metaId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return estimacionList;
    }

    public boolean updateEstimacionMeta(int year, String ramoId, int metaId, int periodo, Double valor) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateEstimacion(year, ramoId, metaId, periodo, valor);
        return resultado;
    }

    public boolean updateMeta(int year, String ramoId, String programaId, int proyectoId, int metaId, int medida, String calculo, String linea, int compromiso,
            int benefH, int benefM, String principal, String objMeta, int grupoPob, int ponderado, String lineaSect,
            String clasificacionFuncional, String autorizacion, int criterio, String obra) {
        boolean resultado = false;
        String arrayClasificacion[] = new String[3];
        arrayClasificacion = clasificacionFuncional.split("\\.");
        resultado = super.getResultSQLUpdateMeta(year, ramoId, programaId, proyectoId, metaId,
                medida, calculo, linea, compromiso, benefH, benefM, principal, objMeta, grupoPob,
                ponderado, lineaSect, arrayClasificacion[0], arrayClasificacion[1], arrayClasificacion[2], autorizacion, criterio, obra);
        return resultado;
    }

    public List<LineaSectorial> getLineaSectorialByPED(String estr, int year, String ramo, String programa, int proyecto) {
        List<LineaSectorial> sectorialList = new ArrayList<LineaSectorial>();
        try {
            sectorialList = super.getResultLineaSectorialByPED(estr, year, ramo, programa, proyecto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return sectorialList;
    }

    public List<TipoCompromiso> getTipoCompromiso() {
        List<TipoCompromiso> tipoCompromisoList = new ArrayList<TipoCompromiso>();
        try {
            tipoCompromisoList = super.getResultGetTipoCompromiso();
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoCompromisoList;
    }

    public List<GrupoPoblacional> getGrupoPoblacional() {
        List<GrupoPoblacional> grupoPoblacionalList = new ArrayList<GrupoPoblacional>();
        try {
            grupoPoblacionalList = super.getResultGetGrupoPoblacional();
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return grupoPoblacionalList;
    }

    public Evaluacion getEvaluacionMeta(int year) {
        Evaluacion evaluacion = new Evaluacion();
        try {
            evaluacion = super.getResultSQLGetEvaluacionMeta(year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return evaluacion;
    }

    public Double getValorCalculado(List<Estimacion> estimacionList, String tipoC) {
        Double calculo = 0.0;
        if (estimacionList.size() > 0) {
            if (tipoC.equalsIgnoreCase("AC")) {
                for (Estimacion estimacion : estimacionList) {
                    calculo += estimacion.getValor();
                }
            } else {
                if (tipoC.equalsIgnoreCase("MA")) {
                    calculo = estimacionList.get(0).getValor();
                    for (int it = 0; it < estimacionList.size(); it++) {
                        if (calculo < estimacionList.get(it).getValor()) {
                            calculo = estimacionList.get(it).getValor();
                        }
                    }
                } else if (tipoC.equalsIgnoreCase("MI")) {
                    calculo = estimacionList.get(0).getValor();
                    for (int it = 0; it < estimacionList.size(); it++) {
                        if (calculo > estimacionList.get(it).getValor() && estimacionList.get(it).getValor() != 0) {
                            calculo = estimacionList.get(it).getValor();
                        } else {
                            if (calculo == 0) {
                                calculo = estimacionList.get(it).getValor();
                            }
                        }
                    }
                }
            }
        }
        return calculo;
    }

    public Double getValorCalculadoMovOficio(List<MovOficioEstimacion> estimacionList, String tipoC) {
        Double calculo = 0.0;
        if (estimacionList.size() > 0) {
            if (tipoC.equalsIgnoreCase("AC")) {
                for (MovOficioEstimacion estimacion : estimacionList) {
                    calculo += estimacion.getValor();
                }
            } else {
                if (tipoC.equalsIgnoreCase("MA")) {
                    calculo = estimacionList.get(0).getValor();
                    for (int it = 0; it < estimacionList.size(); it++) {
                        if (calculo < estimacionList.get(it).getValor()) {
                            calculo = estimacionList.get(it).getValor();
                        }
                    }
                } else if (tipoC.equalsIgnoreCase("MI")) {
                    calculo = estimacionList.get(0).getValor();
                    for (int it = 0; it < estimacionList.size(); it++) {
                        if (calculo > estimacionList.get(it).getValor() && estimacionList.get(it).getValor() != 0) {
                            calculo = estimacionList.get(it).getValor();
                        } else {
                            if (calculo == 0) {
                                calculo = estimacionList.get(it).getValor();
                            }
                        }
                    }
                }
            }
        }
        return calculo;
    }

    public List<Transversalidad> getTransversalidad() {
        List<Transversalidad> transversalidadList = new ArrayList<Transversalidad>();
        try {
            transversalidadList = super.getResultSQLGetCriterioTrans();
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return transversalidadList;
    }

    public String getTipoCalculo(int year, String ramoId, String programaId, int proyectoId, int metaId) {
        String tipoC = new String();
        try {
            tipoC = super.getResultSQLGetTipoCalculo(year, ramoId, programaId, proyectoId, metaId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoC;
    }

    public boolean insertaMeta(int metaId, String ramoId, String programaId, int year,
            int cvMedida, int proyectoId, String calculo, String tipoProy, String depto, String clave,
            String lineaPed, int tipoCompr, int benefM, int benefH, String principal, String objComp,
            int grupoPoblacion, int ponderado, String lineaSectorial, String clasificacionFuncional,
            String autorizacion, int criterio, String obra) {
        boolean resultado = false;
        String arrayClasificacion[] = new String[3];
        arrayClasificacion = clasificacionFuncional.split("\\.");
        resultado = super.getResultSQLInsertarMeta(metaId, ramoId, programaId, year, cvMedida, proyectoId,
                calculo, tipoProy, depto, clave, lineaPed, tipoCompr, benefM, benefH, principal, objComp,
                grupoPoblacion, ponderado, lineaSectorial, Integer.parseInt(arrayClasificacion[0]),
                Integer.parseInt(arrayClasificacion[1]), Integer.parseInt(arrayClasificacion[2]), autorizacion, criterio, obra);

        return resultado;
    }

    public List<ClasificacionFuncional> getClasificacionFuncional(String ramoId, String programaId, int proyectoId, int year, String tipoProy) {
        List<ClasificacionFuncional> clasificacionList = new ArrayList<ClasificacionFuncional>();
        try {
            if (programaId.length() == 1) {
                programaId = "00" + programaId;
            } else {
                if (programaId.length() == 2) {
                    programaId = "0" + programaId;
                }
            }
            clasificacionList = super.getResultSQLGetClasificacionFuncional(ramoId, programaId, proyectoId, year, tipoProy);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return clasificacionList;
    }

    public boolean deleteMeta(int year, String ramoId, String programaId, int proyectoId, int metaId, String tipoProyecto) {
        boolean resultado = false;
        resultado = super.getResultDeleteMeta(year, ramoId, programaId, proyectoId, metaId, tipoProyecto);
        return resultado;
    }

    public int insertEstimacionMeta(int year, String ramoId, int metaId, int numEval) {
        boolean resultado = false;
        int totalError = 0;

        for (int it = 0; it < numEval; it++) {
            resultado = super.getResultSQLInsertEstimacion(year, ramoId, metaId, (it + 1), 0.0);
            if (!resultado) {
                totalError++;
            }
        }
        return totalError;
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

    public boolean getCierrePOA(String ramoId, int year) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisPOACerrado(ramoId, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean isPOAPresupuestable(String ramoId, String programaId, int metaId, int year) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisPOAPresupueatable(ramoId, programaId, metaId, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public double getCountEstimacionMeta(int year, String ramo, int meta) {
        double estMeta = 0;
        try {
            estMeta = super.getResultSQLGetContEstimacionMeta(year, ramo, meta);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return estMeta;
    }

    public List<Linea> getLineaRamoPrograma(int year, String ramo, String programa) {
        List<Linea> lineasList = new ArrayList<Linea>();
        try {
            lineasList = super.getResultGetLineaRamoPrograma(year, ramo, programa);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineasList;
    }

    public boolean deleteEstimacionMeta(int year, String ramo, int meta) {
        boolean resultado = false;
        double contEst;
        resultado = super.getResultSQLDeleteEstimacionMeta(year, ramo, meta);
        if (!resultado) {
            contEst = this.getCountEstimacionMeta(year, ramo, meta);
            if (contEst == 0) {
                resultado = true;
            }
        }
        return resultado;
    }

    public String getObraProyecto(int year, int proyecto, String tipoProyecto) {
        String result = new String();
        try {
            result = super.getResultSQLGetObraProyectoActividad(year, proyecto, tipoProyecto);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return result;
    }

    public String getObraMeta(int year, String ramo, int meta) {
        String obra = new String();
        try {
            obra = super.getResultSLQGetObraMeta(year, ramo, meta);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return obra;
    }

    public int validaCountEstimación(int year, String ramo, int meta) {
        int estimacion = 0;
        try {
            estimacion = super.getResultSQLGetValidaCountEstimacionMeta(year, ramo, meta);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return estimacion;
    }

    public Meta getMetaByRamoMetaYear(String ramoId, int metaId, int year) {
        Meta meta = new Meta();
        try {
            meta = super.getResultGetMetaByRamoMetaYear(ramoId, metaId, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return meta;
    }

    public Double getValorCalculadoAmpliacionReduccion(List<MovOficioEstimacion> estimacionList, String tipoC) {
        Double calculo = 0.0;
        if (estimacionList.size() > 0) {
            if (estimacionList.size() > 0) {
                if (tipoC.equalsIgnoreCase("AC")) {
                    for (MovOficioEstimacion estimacion : estimacionList) {
                        calculo += estimacion.getValor();
                    }
                } else {
                    if (tipoC.equalsIgnoreCase("MA")) {
                        calculo = estimacionList.get(0).getValor();
                        for (int it = 0; it < estimacionList.size(); it++) {
                            if (calculo < estimacionList.get(it).getValor()) {
                                calculo = estimacionList.get(it).getValor();
                            }
                        }
                    } else if (tipoC.equalsIgnoreCase("MI")) {
                        calculo = estimacionList.get(0).getValor();
                        for (int it = 0; it < estimacionList.size(); it++) {
                            if ((calculo > estimacionList.get(it).getValor() && estimacionList.get(it).getValor() != 0) || calculo == 0) {
                                calculo = estimacionList.get(it).getValor();
                            } else {
                                if (calculo == 0) {
                                    calculo = estimacionList.get(it).getValor();
                                }
                            }
                        }
                    }
                }
            }
        }
        return calculo;
    }

    public List<Linea> getLineaSectorialByLineaMeta(String estrategia, int year) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultGetLineaSectorialByLineaMeta(estrategia, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<Estimacion> getHistEstimacion(int year, String ramoId, int metaId, int folio) {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        try {
            estimacionList = super.getResultSQLGetHistEstimacion(year, ramoId, metaId, folio);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return estimacionList;
    }
}
