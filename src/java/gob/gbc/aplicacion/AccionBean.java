/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Accion;
import gob.gbc.entidades.Articulo;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.Dependencia;
import gob.gbc.entidades.Estimacion;
import gob.gbc.entidades.FuenteFinanciamiento;
import gob.gbc.entidades.FuenteRecurso;
import gob.gbc.entidades.GrupoPoblacional;
import gob.gbc.entidades.Linea;
import gob.gbc.entidades.LineaSectorial;
import gob.gbc.entidades.Localidad;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.MovOficioAccionEstimacion;
import gob.gbc.entidades.Municipio;
import gob.gbc.entidades.Partida;
import gob.gbc.entidades.Programa;
import gob.gbc.entidades.Proyecto;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.RelacionLaboral;
import gob.gbc.entidades.Requerimiento;
import gob.gbc.entidades.TipoAccion;
import gob.gbc.entidades.TipoCalculo;
import gob.gbc.entidades.TipoGasto;
import gob.gbc.entidades.UnidadMedida;
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
 * @author ugarcia
 */
public class AccionBean extends ResultSQL {

    Bitacora bitacora;

    public AccionBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public AccionBean(String tipoDependencia) throws Exception {
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

    public String getDepartamentoString(int departamento) {
        String strDepto = new String();
        strDepto = String.valueOf(departamento);
        if (strDepto.length() == 1) {
            strDepto = "00" + strDepto;
        } else if (strDepto.length() == 2) {
            strDepto = "0" + strDepto;
        }
        return strDepto;
    }

    public Ramo getRamoById(String ramoId, int year, String usuario) {
        Ramo ramo = new Ramo();
        try {
            ramo = super.getResultGetRamoById(ramoId, year, usuario);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramo;
    }

    public List<Linea> getLineaByProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProy) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultLineaByProyecto(ramoId, programaId, proyectoId, year, tipoProy);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<Linea> getLineaAccion(int year, String ramo, String programa) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultGetLineaRamoPrograma(year, ramo, programa);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public double getContEstimacionAccion(int year, String ramo, int meta, int accion) {
        double contEst = 0;
        try {
            contEst = super.getResultSQLGetContEstimacionAccion(year, ramo, meta, accion);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return contEst;
    }

    public List<Linea> getLineaSectorialByLineaAccion(String estrategia, int year) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultGetLineaSectorialByLineaAccion(estrategia, year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<FuenteRecurso> getFuenteRecuros(int year, int fuente) {
        List<FuenteRecurso> fuenteRecursoList = new ArrayList<FuenteRecurso>();
        try {
            fuenteRecursoList = super.getResultSQLGetFuenteRecurso(year, fuente);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteRecursoList;
    }

    public List<FuenteRecurso> getFuenteFiltrado(int year, int fuente) {
        List<FuenteRecurso> fuenteRecursoList = new ArrayList<FuenteRecurso>();
        try {
            fuenteRecursoList = super.getResultSQLGetFuenteRecursoFiltrado(year, fuente);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteRecursoList;
    }

    public Programa getProgramaById(String ramoId, String programaId, int year, String usuario) {
        Programa programa = new Programa();
        try {
            programa = super.getResultGetProgramaById(ramoId, year, programaId, usuario);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return programa;
    }

    public Proyecto getProyectoById(String ramoId, String programaId, int proyectoId, int year, String tipoProy) {
        Proyecto proyecto = new Proyecto();
        try {
            proyecto = super.getResultGetProyectoById(ramoId, programaId, proyectoId, year, tipoProy);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return proyecto;
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

    public List<Accion> getAccionesByMeta(int year, String ramoId, int metaId) throws Exception {
        List<Accion> accionList = new ArrayList<Accion>();
        try {
            accionList = super.getResultSQLGetAcciones(year, ramoId, metaId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return accionList;
    }

    public List<Accion> getAccionesByMetaAvance(int year, String ramoId, int metaId) throws Exception {
        List<Accion> accionList = new ArrayList<Accion>();
        try {
            accionList = super.getResultSQLGetAccionesAvance(year, ramoId, metaId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return accionList;
    }

    public List<Requerimiento> getRequerimientoByAccion(int year, String ramoId, String programaId,
            int metaId, int accionId) {
        List<Requerimiento> requerimientoList = new ArrayList<Requerimiento>();
        try {
            requerimientoList = super.getResultGeRequerimientoByAccion(year, ramoId,
                    programaId, metaId, accionId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return requerimientoList;
    }

    public Double getCostoAccion(int year, String ramo, int meta, int accion) {
        Double costo = 0.0;
        try {
            costo = super.getResultSQLGetCostoAccion(year, ramo, meta, accion);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return costo;
    }

    public double getTechoRamo(int year, String ramo) {
        double costo = 0.0;
        try {
            costo = super.getResultSQLGetTechoRamo(year, ramo);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return costo;
    }

    public Accion getAccionById(int year, String ramoId, int metaId, int accionId) {
        Accion accion = new Accion();
        try {
            accion = super.getResultGetAccionById(year, ramoId, metaId, accionId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return accion;
    }

    public List<Dependencia> getDependencia(String ramoId, int year, String programaId) {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        try {
            dependenciaList = super.getResultDependenciasByRamo(ramoId, year, programaId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return dependenciaList;
    }

    public List<UnidadMedida> getUnidadMedida(int year) {
        List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
        try {
            unidadMedidaList = super.getResultGetUnidadAccionMedidaList(year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return unidadMedidaList;
    }

    public List<GrupoPoblacional> getGrupoPoblacional() {
        List<GrupoPoblacional> grupoList = new ArrayList<GrupoPoblacional>();
        try {
            grupoList = super.getResultGetGrupoPoblacional();
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return grupoList;
    }

    public List<Municipio> getMunicipios() {
        List<Municipio> municipioList = new ArrayList<Municipio>();
        try {
            municipioList = super.getResultGetMunicipio();
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return municipioList;
    }

    public List<Localidad> getlocalidades(int mpo) {
        List<Localidad> localidadList = new ArrayList<Localidad>();
        try {
            localidadList = super.getResultSQLGetLocalidad(mpo);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return localidadList;
    }

    public List<LineaSectorial> getLineaSectorial(int year, String ramoId) {
        List<LineaSectorial> lineaSectorialList = new ArrayList();
        try {
            lineaSectorialList = super.getRestulLineaSectorialByRamo(year, ramoId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaSectorialList;
    }

    public int countRequerimientosByAccion(int year, String ramoId, String programaId, int meta, int accion) {
        int contReq = 0;
        try {
            contReq = super.getResultSQLGetCountRequerimientos(year, ramoId, programaId, meta, accion);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return contReq;
    }

    public String deleteAccion(int year, String ramoId, String programaId, int meta, int accion) {
        String result = new String();
        boolean resultado = false;
        result = super.getResultDeleteAccion(year, ramoId, programaId, meta, accion);
        int sumaBenef[] = new int[2];
        if (result.equals("exito")) {
            sumaBenef = this.getSumaBenef(year, ramoId, meta);
            resultado = super.getResultSQLUpdateBeneficiariosMeta(year, ramoId, programaId, meta, sumaBenef);
        }
        return result;
    }

    public List<Accion> insertAccion(int metaId, String ramoId, int year, int accionId, String accionDesc,
            String depto, int medida, String tipoCalculo, String lineaPed, int grupoPob, int mpo, int localidad,
            String lineaSectorial, int benefMuj, int benefHom, String programaId, int proyectoId, int tipoAccion) {
        List<Accion> accionLits = new ArrayList<Accion>();
        int sumaBenef[] = new int[2];
        try {
            boolean resultado = false;
            resultado = super.getResultSQLinsertAccion(metaId, ramoId, year, accionId, accionDesc,
                    depto, medida, tipoCalculo, lineaPed, grupoPob, mpo, localidad, lineaSectorial, benefMuj, benefHom, tipoAccion, programaId);
            if (resultado) {
                sumaBenef = this.getSumaBenef(year, ramoId, metaId);
                resultado = super.getResultSQLUpdateBeneficiariosMeta(year, ramoId, programaId, metaId, sumaBenef);
            }
            accionLits = this.getAccionesByMeta(year, ramoId, metaId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return accionLits;
    }

    public int[] getSumaBenef(int year, String ramoId, int meta) {
        int sumaBenef[] = new int[2];
        try {
            sumaBenef = super.getResultSQLSumaBeneficiarios(year, ramoId, meta);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return sumaBenef;
    }

    public List<Linea> getLineaPEd(String ramoId, int year) {
        List<Linea> lineaPedList = new ArrayList<Linea>();
        try {
            lineaPedList = super.getResultSQLGetLineaPEDByRamo(ramoId, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaPedList;
    }

    public List<Linea> getLineaPED(String ramoId, String programaId, int year) {
        List<Linea> lineaPedList = new ArrayList<Linea>();
        try {
            lineaPedList = super.getResultLineaByPrograma(ramoId, programaId, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaPedList;
    }

    public int getMaxAccion(int year, String ramoId, int metaId) {
        int maxAccion = 0;
        try {
            maxAccion = super.getResultSQLMaxAccion(year, ramoId, metaId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return maxAccion;
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

    public List<Accion> updateAccion(int year, String ramoId, int metaId, int accionId, String accDesc, String depto, int benefMuj, int benefHom,
            int medida, String calculo, String linea, int grupoPob, int mpo, int localidad, String lineaSect, String programaId, int proyectoId) {
        int sumaBenef[] = new int[2];
        List<Accion> accionList = new ArrayList<Accion>();
        boolean resultado = false;
        resultado = super.getResultSQLUpdateAccion(year, ramoId, metaId, accionId, accDesc, depto,
                medida, calculo, linea, grupoPob, mpo, localidad, lineaSect, benefMuj, benefHom);
        try {
            if (resultado) {
                sumaBenef = this.getSumaBenef(year, ramoId, metaId);
                resultado = super.getResultSQLUpdateBeneficiariosMeta(year, ramoId, programaId, metaId, sumaBenef);
            }
            accionList = this.getAccionesByMeta(year, ramoId, metaId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return accionList;
    }

    public List<Estimacion> getEstimacionByAccion(int year, String ramoId, int metaId, int accionId) {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        try {
            estimacionList = super.getResultSQLGetEstimacionByAccion(year, ramoId, metaId, accionId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return estimacionList;
    }

    public String getTipoCalculo(int year, String ramoId, int metaId, int accionId) {
        String tipoCalculo = new String();
        try {
            tipoCalculo = super.getResultSQLGetTipoCalculoAccion(year, ramoId, metaId, accionId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoCalculo;
    }

    public Double getValorCalculado(List<Estimacion> estimacionList, String tipoC) {
        Double calculo = 0.0;
        if (estimacionList.size() > 0) {
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
        }
        return calculo;
    }

    public Double getValorCalculadoMovOficio(List<MovOficioAccionEstimacion> estimacionList, String tipoC) {
        Double calculo = 0.0;
        if (estimacionList.size() > 0) {
            if (estimacionList.size() > 0) {
                if (tipoC.equalsIgnoreCase("AC")) {
                    for (MovOficioAccionEstimacion estimacion : estimacionList) {
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
        }
        return calculo;
    }

    public int insertAccionEstimacion(int year, String ramoId, int metaId, int accionId) {
        boolean resultado = false;
        int fallas = 0;
        for (int it = 0; it < 12; it++) {
            resultado = super.getResultSQLInsertAccionEstimacion(year, ramoId, metaId, accionId, (it + 1), 0.0);
            if (!resultado) {
                fallas++;
            }
        }
        return fallas;
    }

    public boolean updateAccionEstimacion(int year, String ramoId, int metaId, int accionId, int periodo, Double valor) {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateAccionEstimacion(year, ramoId, metaId, accionId, periodo, valor);
        return resultado;
    }

    public List<FuenteFinanciamiento> getAccionFuente(int year, String ramoId, int metaId, int accionId) {
        List<FuenteFinanciamiento> fuenteList = new ArrayList<FuenteFinanciamiento>();
        try {
            fuenteList = super.getResultSQLGetAccionFuente(year, ramoId, metaId, accionId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteList;
    }

    public List<FuenteFinanciamiento> getFuenteFinanciamiento(int year) {
        List<FuenteFinanciamiento> fuenteList = new ArrayList<FuenteFinanciamiento>();
        try {
            fuenteList = super.getResultSQLGetFuenteFinanciamiento(year);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteList;
    }

    public boolean deleteFuenteFinanciamiento(int year, String ramoId, int metaId, int accionId) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteFuenteFinanciamiento(year, ramoId, metaId, accionId);
        return resultado;
    }

    public boolean deleteRequerimiento(int year, String ramo, String programa, int meta, int accion, int requerimiento) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteRequerimiento(year, ramo, programa, meta, accion, requerimiento);
        return resultado;
    }

    public int getTotalFuentesByAccion(int year, String ramoId, int metaId, int accionId) {
        int totFuente = 0;
        try {
            totFuente = super.getResultSQLTotalFuentes(year, ramoId, metaId, accionId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return totFuente;
    }

    public List<FuenteFinanciamiento> saveFuenteFinanciamiento(int year, String ramoId, int metaId, int accionId, String[] arregloFuente, String programa) {
        List<FuenteFinanciamiento> fuenteList = new ArrayList<FuenteFinanciamiento>();
        FuenteFinanciamiento fuenteF;
        boolean resultado = false;
        int totFuentes = 0;
        int contErr = 0;
        if (arregloFuente.length > 0) {
            for (int it = 0; it < arregloFuente.length; it++) {
                if (!arregloFuente[it].equals("-1")) {
                    fuenteF = new FuenteFinanciamiento();
                    fuenteF.setFuenteId(Integer.parseInt(arregloFuente[it]));
                    fuenteList.add(fuenteF);
                }
            }
            totFuentes = this.getTotalFuentesByAccion(year, ramoId, metaId, accionId);
            if (totFuentes > 0) {
                resultado = this.deleteFuenteFinanciamiento(year, ramoId, metaId, accionId);
            } else {
                resultado = true;
            }
            if (resultado) {
                for (FuenteFinanciamiento fuenteTemp : fuenteList) {
                    resultado = super.getResultSQLInsertFuenteFinanciamiento(year, ramoId, metaId, accionId, fuenteTemp.getFuenteId(), programa);
                    if (!resultado) {
                        contErr++;
                    }
                }
            }
            if (contErr == 0) {
                fuenteList = new ArrayList<FuenteFinanciamiento>();
                try {
                    fuenteList = this.getAccionFuente(year, ramoId, metaId, accionId);
                } catch (Exception sql) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sql, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return fuenteList;
    }

    public List<TipoGasto> getTipoGasto(int year) {
        List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
        try {
            tipoGastoList = super.getResultSQLTipoGasto(year);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoGastoList;
    }

    public List<TipoGasto> getAccionTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente) {
        List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
        try {
            tipoGastoList = super.getResultSQLAccionTipoGasto(year, ramoId, metaId, accionId, fuente);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoGastoList;
    }

    public int getTotalAccionTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente) {
        int totAccion;
        totAccion = super.getResultSQLTotalTipoGasto(year, ramoId, metaId, accionId, fuente);
        return totAccion;
    }

    public List<TipoGasto> saveTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente, String[] arregloGasto, String programa) {
        List<TipoGasto> gastoList = new ArrayList<TipoGasto>();
        TipoGasto tipoGasto;
        boolean resultado = false;
        int totFuentes = 0;
        int contErr = 0;
        if (arregloGasto.length > 0) {
            for (int it = 0; it < arregloGasto.length; it++) {
                if (!arregloGasto[it].equals("-1")) {
                    tipoGasto = new TipoGasto();
                    tipoGasto.setTipoGastoId(Integer.parseInt(arregloGasto[it]));
                    gastoList.add(tipoGasto);
                }
            }
            totFuentes = this.getResultSQLTotalTipoGasto(year, ramoId, metaId, accionId, fuente);
            if (totFuentes > 0) {
                resultado = this.deleteAccionTipoGasto(year, ramoId, metaId, accionId, fuente);
            } else {
                resultado = true;
            }
            if (resultado) {
                for (TipoGasto gastoTemp : gastoList) {
                    resultado = this.insertAccionTipoGasto(year, ramoId, metaId, accionId, fuente, gastoTemp.getTipoGastoId(), programa);
                    if (!resultado) {
                        contErr++;
                    }
                }
            }
            if (contErr == 0) {
                gastoList = new ArrayList<TipoGasto>();
                try {
                    gastoList = this.getAccionTipoGasto(year, ramoId, metaId, accionId, fuente);
                } catch (Exception sql) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sql, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return gastoList;
    }

    public boolean deleteAccionTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteTipoGasto(year, ramoId, metaId, accionId, fuente);
        return resultado;
    }

    public boolean insertAccionTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente, int tipoGasto, String programa) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertTipoGasto(year, ramoId, metaId, accionId, fuente, tipoGasto, programa);
        return resultado;
    }

    public int getMaxRequerimientoId(int year, String ramoId, int metaId, int accionId) {
        int maxReq = 0;
        try {
            maxReq = super.getResultSQLMaxRequerimiento(year, ramoId, metaId, accionId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return maxReq;
    }

    public List<Partida> getPartidaList(int year, String tipoDependencia) {
        List<Partida> partidaList = new ArrayList<Partida>();
        boolean isParaestatal = false;
        try {
            isParaestatal = super.getResultSQLisParaestatal();
            if (isParaestatal) {
                partidaList = super.getResultSQLGetPartidas(year, tipoDependencia);
            } else {
                partidaList = super.getResultSQLGetPartidas(year, tipoDependencia);
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return partidaList;
    }

    public List<Articulo> getArticuloPartida(int year, String partida) {
        List<Articulo> articuloList = new ArrayList<Articulo>();
        try {
            articuloList = super.getResultSQLArticuloPartida(year, partida);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return articuloList;
    }

    public boolean isRelLaboral(int year, String partida) {
        boolean isRelLaboral = false;
        try {
            isRelLaboral = super.getResultIsRelacionLaboral(year, partida);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return isRelLaboral;
    }

    public boolean isArticuloPartida(int year, String partida) {
        boolean resultado = false;
        int cont = -1;
        try {
            cont = super.getResultSQLisArticuloPartida(year, partida);
            if (cont < 1) {
                resultado = true;
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean saveRequerimiento(int year, String ramoId, String programaId, String depto, int metaId,
            int accionId, String tipoGasto, int requerimientoId, String requerimiento, String partida,
            String reqLaboral, double cantidad, Double costoUnit, Double costoAnual, double ene, double feb, double marzo, double abril,
            double mayo, double junio, double julio, double agosto, double sept, double oct, double nov, double dic,
            int articulo, String justificacion, String fuenteF, String usuario, int gpogto, int subgpo) {
        String fuenteArreglo[] = new String[3];
        fuenteArreglo = fuenteF.split("\\.");
        boolean res = false;
        //Double costoAccionAct = 0.0;
        //costoAccionAct = this.getCostoAccion(year, ramoId, metaId, accionId);
        //costoAccionAct = costoAccionAct + costoAnual;
        res = super.getResultSQLInsertRequerimiento(year, ramoId, programaId,
                depto, metaId, accionId, fuenteArreglo[0], tipoGasto, requerimientoId, requerimiento,
                partida, reqLaboral, cantidad, costoUnit, costoAnual, ene, feb, marzo,
                abril, mayo, junio, julio, agosto, sept, oct, nov, dic, articulo,
                justificacion, fuenteArreglo[1], fuenteArreglo[2], usuario, gpogto, subgpo, new String());
        return res;
    }

    public String getArticuloDescr(int year, String partida, int articulo) {
        String strArt = new String();
        try {
            strArt = super.getResultSQLGetArticuloDescr(year, partida, articulo);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return strArt;
    }

    public List<TipoCalculo> getTipoCalculo() {
        List<TipoCalculo> tipoCalculoList = new ArrayList<TipoCalculo>();
        try {
            tipoCalculoList = super.getResultGeTipoCalculo();
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoCalculoList;
    }

    public List<TipoAccion> getTipoAccion() {
        List<TipoAccion> tipoAccionList = new ArrayList<TipoAccion>();
        try {
            tipoAccionList = super.getResultSQLGetTipoAccion();
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoAccionList;
    }

    public List<RelacionLaboral> getRelacionLaboral(int year) {
        List<RelacionLaboral> relacionLaboralList = new ArrayList<RelacionLaboral>();
        try {
            relacionLaboralList = super.getResultSQLGetRelacionLaboral(year);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return relacionLaboralList;
    }

    public int getDeptoRamoPrograma(int year, String ramoId, String programaId) {
        int depto = 0;
        try {
            depto = super.getResultSQLGetDeptoRamoPrograma(year, ramoId, programaId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return depto;
    }

    public String getDeptoByAccion(int year, String ramoId, int meta, int accion) {
        String depto = new String();
        try {
            depto = super.getResultSQLGetDeptoAccion(year, ramoId, meta, accion);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return depto;
    }

    public boolean getCierreRequerimientoPPTO(int year, String ramoId) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisRequerimientoPPTOCerrado(year, ramoId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public int existeArticuloPatidaCosto(int year, String partida) {
        int resultado = -2;

        return 0;
    }

    public boolean isAccionCerrado(String ramoId, int year) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisAccionCerrado(year, ramoId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean isAccionPPTOCerrado(String ramoId, int year) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisAccionPPTOCerrado(year, ramoId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean isRequDescrCerrado(String ramoId, int year) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisRequerimientoDescrCerrado(year, ramoId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean insertarCodigosPPTO(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) {
        boolean resultado = false;
        if (!this.isCodigoRepetido(year, ramo, depto, finalidad, funcion, subfuncion, prgConac, programa,
                tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio,
                delegacion, relLab)) {
            resultado = super.getResultSQLInsertCodigo(year, ramo, depto, finalidad, funcion, subfuncion, prgConac, programa,
                    tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLab);
        } else {
            resultado = false;
        }
        return resultado;
    }

    public CodigoPPTO getCodigoPPTO(int year, String ramo, String programa, int proyecto, String requ, String partida, int meta, int accion, String tipoProy) {
        CodigoPPTO codigo = new CodigoPPTO();
        try {
            codigo = super.getRestulSQLGetCodigoPPTO(year, ramo, programa, proyecto, requ, partida, meta, accion, tipoProy);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return codigo;
    }

    public boolean isCodigoRepetido(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisCodigoRepetido(year, ramo, depto, finalidad, funcion, subfuncion, prgConac,
                    programa, tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio,
                    delegacion, relLab);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public String callProcedureActualizaCodigoPPTO(int year, String ramo, String depto, String finalidad, String funcion, String subFuncion, String progConac, String programa, String tipoProyecto, int proyecto, int meta, int accion, String partida, String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral, double cantXcostoUn,
            int mes, int existeCod, String usuario, int requerimiento) {
        String mensaje = new String();
        try {
            mensaje = super.getRestulSQLCallActualizarCodPPTO(year, ramo, depto, finalidad, funcion, subFuncion, progConac,
                    programa, tipoProyecto, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio,
                    delegacion, relLaboral, cantXcostoUn, mes, existeCod, usuario, requerimiento);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String callProcedureActualizaCodigoPPTO(int year, String ramo, String depto, String finalidad, String funcion,
            String subFuncion, String progConac, String programa, String tipoProyecto, int proyecto, int meta, int accion,
            String partida, String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion,
            String relLaboral, double cantXcostoUn, int mes, int existeCod, String usuario, int requerimiento, int validaBitacora) {
        String mensaje = new String();
        try {
            mensaje = super.getRestulSQLCallActualizarCodPPTO(year, ramo, depto, finalidad, funcion, subFuncion, progConac,
                    programa, tipoProyecto, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio,
                    delegacion, relLaboral, cantXcostoUn, mes, existeCod, usuario, requerimiento, validaBitacora);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public int isPartidaJustific(int year, String partida) {
        int justific = 0;
        try {
            justific = super.getResultSLQisPartidaJustific(year, partida);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return justific;
    }

    public Requerimiento getRequerimientoById(int year, String ramoId, String programaId,
            int metaId, int accionId, int requerimientoId) {
        Requerimiento requ = new Requerimiento();
        try {
            requ = super.getResultGetRequerimientoById(year, ramoId, programaId, metaId, accionId, requerimientoId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return requ;
    }

    public double getEditCantidadXCosto(int ite, double[] arrayCosto, double costoUnitario, double ene, double feb, double mar, double abr, double may,
            double jun, double jul, double ago, double sep, double oct, double nov, double dic) {
        BigDecimal costo = new BigDecimal(costoUnitario);
        switch (ite) {
            case 1:
                costo = costo.multiply(new BigDecimal(ene)) /*- arrayCosto[ite-1]*/;
                break;
            case 2:
                costo = costo.multiply(new BigDecimal(feb)) /*- arrayCosto[ite-1]*/;
                break;
            case 3:
                costo = costo.multiply(new BigDecimal(mar)) /*- arrayCosto[ite-1]*/;
                break;
            case 4:
                costo = costo.multiply(new BigDecimal(abr)) /*- arrayCosto[ite-1]*/;
                break;
            case 5:
                costo = costo.multiply(new BigDecimal(may)) /*- arrayCosto[ite-1]*/;
                break;
            case 6:
                costo = costo.multiply(new BigDecimal(jun)) /*- arrayCosto[ite-1]*/;
                break;
            case 7:
                costo = costo.multiply(new BigDecimal(jul)) /*- arrayCosto[ite-1]*/;
                break;
            case 8:
                costo = costo.multiply(new BigDecimal(ago)) /*- arrayCosto[ite-1]*/;
                break;
            case 9:
                costo = costo.multiply(new BigDecimal(sep)) /*- arrayCosto[ite-1]*/;
                break;
            case 10:
                costo = costo.multiply(new BigDecimal(oct)) /*- arrayCosto[ite-1]*/;
                break;
            case 11:
                costo = costo.multiply(new BigDecimal(nov)) /*- arrayCosto[ite-1]*/;
                break;
            case 12:
                costo = costo.multiply(new BigDecimal(dic)) /*- arrayCosto[ite-1]*/;
                break;
        }
        costo = costo.setScale(2, RoundingMode.HALF_UP);
        return costo.doubleValue();
    }

    public double[] getMesesAsignadoAnterior(int year, String ramo,
            String programa, String depto, int meta, int accion, int req) {
        double meses[] = new double[12];
        DecimalFormat dFormat = new DecimalFormat("##.##");
        dFormat.setRoundingMode(RoundingMode.DOWN);
        try {
            meses = super.getSQLgetAsignadosMesesRequerimiento(year, ramo, programa, depto, meta, accion, req);
            for (int it = 0; it < 12; it++) {
                meses[it] = Double.valueOf(dFormat.format(meses[it]));
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return meses;
    }

    public double getCantidadXCostoUnitario(int ite, double costoUnitario, double ene, double feb, double mar, double abr, double may,
            double jun, double jul, double ago, double sep, double oct, double nov, double dic) {
        BigDecimal costo = new BigDecimal(costoUnitario);
        switch (ite) {
            case 1:
                costo = costo.multiply(new BigDecimal(ene));
                break;
            case 2:
                costo = costo.multiply(new BigDecimal(feb));
                break;
            case 3:
                costo = costo.multiply(new BigDecimal(mar));
                break;
            case 4:
                costo = costo.multiply(new BigDecimal(abr));
                break;
            case 5:
                costo = costo.multiply(new BigDecimal(may));
                break;
            case 6:
                costo = costo.multiply(new BigDecimal(jun));
                break;
            case 7:
                costo = costo.multiply(new BigDecimal(jul));
                break;
            case 8:
                costo = costo.multiply(new BigDecimal(ago));
                break;
            case 9:
                costo = costo.multiply(new BigDecimal(sep));
                break;
            case 10:
                costo = costo.multiply(new BigDecimal(oct));
                break;
            case 11:
                costo = costo.multiply(new BigDecimal(nov));
                break;
            case 12:
                costo = costo.multiply(new BigDecimal(dic));
                break;
        }
        costo = costo.setScale(2, RoundingMode.HALF_UP);
        return costo.doubleValue();
    }

    public boolean updateRequerimiento(int year, String ramoId, String programaId, int meta, int accion, int req,
            String justificacion, double cantidad, double costoUnitario, double costoAnual, String relLab,
            double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago,
            double sep, double oct, double nov, double dic, String partidDesc, String usuario, int articulo, int gpogto, int subgpo) {
        boolean result = false;
        result = super.getResultUpdateRequerimiento(year, ramoId, programaId, meta, accion, req,
                justificacion, cantidad, costoUnitario, costoAnual, relLab, ene, feb, mar, abr, may,
                jun, jul, ago, sep, oct, nov, dic, partidDesc, usuario, articulo, gpogto, subgpo);
        return result;
    }

    public double getCostoArticulo(int articulo, String partida, int year) {
        double costo = 0.0;
        try {
            costo = super.getResultSQLGetCostoArticulo(articulo, partida, year);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return costo;
    }

    public String getObraProyectoActividad(int year, int proyecto, String tipoProyecto) {
        String obra = new String();
        try {
            obra = super.getResultSQLGetObraProyectoActividad(year, proyecto, tipoProyecto);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return obra;
    }

    public String getTipoCompromisoMeta(int year, String ramo, int meta) {
        String tipoCompromiso = new String();
        try {
            tipoCompromiso = super.getResultSQLGetTipoCompromisoMeta(year, ramo, meta);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoCompromiso;
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

    public String getTipoGastoByPartida(String partida, int year) {
        String tipoGasto = new String();
        try {
            tipoGasto = super.getResultSQLgetTipoDeGastoByPartida(partida, year);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoGasto;
    }

    public boolean deleteEstimacionAccion(int year, String ramo, int meta, int accion) {
        boolean resultado = false;
        double contEst = 0.0;
        resultado = super.getResultSQLDeleteEstimacionAccion(year, ramo, meta, accion);
        if (!resultado) {
            contEst = this.getContEstimacionAccion(year, ramo, meta, accion);
            if (contEst == 0) {
                resultado = true;
            }
        }
        return resultado;
    }

    public String getObraByMeta(int year, String ramo, int meta) {
        String obra = new String();
        try {
            obra = super.getResultSLQGetObraMeta(year, ramo, meta);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return obra;
    }

    public String getNumeroObra(int year, String ramo, int meta, int accion) {
        String numObra = new String();
        try {
            numObra = super.getResultSQLGetNumObraAccion(year, ramo, meta, accion);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return numObra;
    }

    public double[] getAnioPresupValidacion(String ramo, String depto,
            int meta, int accion, String partida, String fuente, String fondo, String recurso) {
        double[] anioPresup = new double[12];
        try {
            anioPresup = super.getResultSQLGetAnioPresupuestalValidacion(ramo, depto, meta, accion, partida, fuente, fondo, recurso);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return anioPresup;
    }

    public double[] getPPTOvalidacion(String ramo, String depto,
            int meta, int accion, String partida, String fuente, String fondo, String recurso) {
        double[] anioPresup = new double[12];
        try {
            anioPresup = super.getResultSQLGetAnioPresupuestalValidacion(ramo, depto, meta, accion, partida, fuente, fondo, recurso);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return anioPresup;
    }

    public String getTipoRequerimientoPlantilla(int year, String ramo, String prg, int meta,
            int accion, String partida, String fuente, String fondo, String recurso, int requerimiento) {
        String archivo = new String();
        int count = 0;
        try {
            count = super.getSQLGetTipoRequerimientoPlantilla(year, ramo, prg, meta,
                    accion, partida, fuente, fondo, recurso, requerimiento);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        if (count > 0) {
            archivo = "plantilla";
        } else {
            archivo = new String();
        }
        return archivo;
    }

    public Accion getAccionByYearRamoMetaAccion(int year, String ramoId, int metaId, int accionId) {
        Accion accion = new Accion();
        try {
            accion = super.getResultGetAccionByYearRamoMetaAccion(year, ramoId, metaId, accionId);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return accion;
    }

    public String getTipoCompromisoByTipoCompromiso(int tipoCompromisoId) {
        String tipoCompromiso = new String();
        try {
            tipoCompromiso = super.getResultSQLGetTipoCompromisoByTipoCompromiso(tipoCompromisoId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoCompromiso;
    }

    public Double getValorCalculadoAmpliacionReduccion(List<MovOficioAccionEstimacion> estimacionList, String tipoC) {
        Double calculo = 0.0;
        if (estimacionList.size() > 0) {
            if (estimacionList.size() > 0) {
                if (tipoC.equalsIgnoreCase("AC")) {
                    for (MovOficioAccionEstimacion estimacion : estimacionList) {
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
        }
        return calculo;
    }

    public List<Estimacion> getHistEstimacion(int year, String ramoId, int metaId, int accionId, int folio) {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        try {
            estimacionList = super.getResultSQLGetHistEstimacion(year, ramoId, metaId, accionId, folio);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return estimacionList;
    }

    public List<Articulo> getArticulosSipByYearPartida(int year, String partida) {
        List<Articulo> articuloList = new ArrayList<Articulo>();
        try {
            articuloList = super.getResultSQLArticulosSipByYearPartida(year, partida);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return articuloList;
    }

    public boolean isArticuloSipPartida(int year, String partida) {
        boolean resultado = false;
        int cont = -1;
        try {
            cont = super.getResultSQLisArticuloSipPartida(year, partida);
            if (cont < 1) {
                resultado = true;
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public String getArticuloSipDescr(String partida, int articulo, int gpogto, int subgpo) {
        String strArt = new String();
        try {
            strArt = super.getResultSQLGetArticuloSipDescr(partida, articulo, gpogto, subgpo);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return strArt;
    }

    public List<Articulo> getArticulosByPartida(int year, String partida) {
        List<Articulo> articuloList = new ArrayList<Articulo>();
        try {
            articuloList = super.getResultSQLGetArticulosByPartida(year, partida);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return articuloList;
    }

    public boolean isArticuloPartidas(int year, String partida) {
        boolean resultado = false;
        int cont = -1;
        try {
            cont = super.getResultSQLisArticuloPartidas(year, partida);
            if (cont < 1) {
                resultado = true;
            }
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public String getArticuloDescrByArticulo(int year, String partida, String articuloId) {
        String strArt = new String();
        try {
            strArt = super.getResultSQLGetArticuloDescrByArticulo(year, partida, articuloId);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return strArt;
    }
}
