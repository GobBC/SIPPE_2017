/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Accion;
import gob.gbc.entidades.AccionReq;
import gob.gbc.entidades.CentroCosto;
import gob.gbc.entidades.CodProg; 
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.Programa;
import gob.gbc.entidades.Dependencia;
import gob.gbc.entidades.Fin;
import gob.gbc.entidades.Finalidad;
import gob.gbc.entidades.Fondo;
import gob.gbc.entidades.Fuente;
import gob.gbc.entidades.Funcion;
import gob.gbc.entidades.Grupos;
import gob.gbc.entidades.Localidad;
import gob.gbc.entidades.MedidaMeta;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.Municipio;
import gob.gbc.entidades.Partida;
import gob.gbc.entidades.ProgramaConac;
import gob.gbc.entidades.Proyecto;
import gob.gbc.entidades.ProyectoFuncional;
import gob.gbc.entidades.RamoPrograma;
import gob.gbc.entidades.Recurso;
import gob.gbc.entidades.RelacionLaboral;
import gob.gbc.entidades.SubGrupos;
import gob.gbc.entidades.SubSubGpo;
import gob.gbc.entidades.Subfuncion;
import gob.gbc.entidades.TipoAccion;
import gob.gbc.entidades.TipoGasto;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class EstructuraPresupuestalBean extends ResultSQL {

    Bitacora bitacora;

    public EstructuraPresupuestalBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public EstructuraPresupuestalBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public boolean validaRamoCierre(String ramoId, int year) {
        boolean resultado = false;
        resultado = super.getResultSQLValidaRamoCierre(ramoId, year);
        return resultado;
    }

    public List<Ramo> getCatalogoRamosByRamoYear(String ramoId, int year) {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        try {
            ramoList = super.getResultSQLRamosByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramoList;
    }

    public List<Programa> getCatalogoProgramaByYear(int year) {
        List<Programa> programaList = new ArrayList<Programa>();
        try {
            programaList = super.getResultSQLProgramaByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return programaList;
    }

    public List<Dependencia> getCatalogoDependenciaByRamoYear(String ramoId, int year) {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        try {
            dependenciaList = super.getResultSQLCatalogoDependenciaByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return dependenciaList;
    }

    public List<CodProg> getCatalogoCodProgByRamoYear(String ramoId, int year) {
        List<CodProg> codProgList = new ArrayList<CodProg>();
        try {
            codProgList = super.getResultSQLCatalogoCodProgByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return codProgList;
    }

    public List<Grupos> getCatalogoGruposByYear(int year) {
        List<Grupos> gruposList = new ArrayList<Grupos>();
        try {
            gruposList = super.getResultSQLCatalogoGruposByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return gruposList;
    }

    public List<SubGrupos> getCatalogoSubGruposByYear(int year) {
        List<SubGrupos> subgruposList = new ArrayList<SubGrupos>();
        try {
            subgruposList = super.getResultSQLCatalogoSubGruposByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return subgruposList;
    }

    public List<SubSubGpo> getCatalogoSubSubGpoByYear(int year) {
        List<SubSubGpo> subsubgpoList = new ArrayList<SubSubGpo>();
        try {
            subsubgpoList = super.getResultSQLCatalogoSubSubGpoByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return subsubgpoList;
    }

    public List<Partida> getCatalogoPartidaByYear(int year) {
        List<Partida> partidaList = new ArrayList<Partida>();
        try {
            partidaList = super.getResultSQLCatalogoPartidaByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return partidaList;
    }

    public List<CodigoPPTO> getCatalogoCodigosByRamoYear(String ramoId, int year) {
        List<CodigoPPTO> codigosList = new ArrayList<CodigoPPTO>();
        try {
            codigosList = super.getResultSQLCatalogoCodigosByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return codigosList;
    }

    public List<CodigoPPTO> existeCodigoPPTO(String ramoId, int year, String prg, String finalidad, String funcion, String subfuncion, String prg_conac, String depto, String tipo_proy, int proyecto, int meta, int accion, String Partida, String tipo_gasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String rel_laboral) {
        List<CodigoPPTO> codigosList = new ArrayList<CodigoPPTO>();
        try {
            codigosList = super.getResultSQLExisteCodigoPPTO(ramoId, year, prg, finalidad, funcion, subfuncion, prg_conac, depto, tipo_proy, proyecto, meta, accion, Partida, tipo_gasto, fuente, fondo, recurso, municipio, delegacion, rel_laboral);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return codigosList;
    }

    public void getCatalogoPPTOByRamoYear(String ubicacion,String ramoId, int year) {
        
        try {
            super.getResultSQLCatalogoPPTOByRamoYear(ramoId, year,ubicacion);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ;
    }

    public List<RamoPrograma> getCatalogoRamoProgramaByRamoYear(String ramoId, int year) {
        List<RamoPrograma> ramoProgramaList = new ArrayList<RamoPrograma>();
        try {
            ramoProgramaList = super.getResultSQLCatalogoRamoProgramaByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return ramoProgramaList;
    }

    public List<Finalidad> getCatalogoFinalidadByYear(int year) {
        List<Finalidad> finalidadList = new ArrayList<Finalidad>();
        try {
            finalidadList = super.getResultSQLCatalogoFinalidadByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return finalidadList;
    }

    public List<Funcion> getCatalogoFuncionByYear(int year) {
        List<Funcion> funcionList = new ArrayList<Funcion>();
        try {
            funcionList = super.getResultSQLCatalogoFuncionByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return funcionList;
    }

    public List<Subfuncion> getCatalogoSubfuncionByYear(int year) {
        List<Subfuncion> subfuncionList = new ArrayList<Subfuncion>();
        try {
            subfuncionList = super.getResultSQLCatalogoSubfuncionByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return subfuncionList;
    }

    public List<Fin> getCatalogoFinByYear(int year) {
        List<Fin> finList = new ArrayList<Fin>();
        try {
            finList = super.getResultSQLCatalogoFinByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return finList;
    }

    public List<Fuente> getCatalogoFuenteByYear(int year) {
        List<Fuente> fuenteList = new ArrayList<Fuente>();
        try {
            fuenteList = super.getResultSQLCatalogoFuenteByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteList;
    }

    public List<Fondo> getCatalogoFondoByYear(int year) {
        List<Fondo> fondoList = new ArrayList<Fondo>();
        try {
            fondoList = super.getResultSQLCatalogoFondoByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return fondoList;
    }

    public List<Recurso> getCatalogoRecursoByYear(int year) {
        List<Recurso> recursoList = new ArrayList<Recurso>();
        try {
            recursoList = super.getResultSQLCatalogoRecursoByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return recursoList;
    }

    public List<ProgramaConac> getCatalogoPrgConacByYear(int year) {
        List<ProgramaConac> prgConacList = new ArrayList<ProgramaConac>();
        try {
            prgConacList = super.getResultSQLCatalogoPrgConacByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return prgConacList;
    }

    public List<TipoGasto> getCatalogoTipoGastoByYear(int year) {
        List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
        try {
            tipoGastoList = super.getResultSQLCatalogoTipoGastoByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoGastoList;
    }

    public List<TipoAccion> getCatalogoTipoAccion() {
        List<TipoAccion> tipoAccionList = new ArrayList<TipoAccion>();
        try {
            tipoAccionList = super.getResultSQLCatalogoTipoAccion();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoAccionList;
    }

    public List<RelacionLaboral> getCatalogoRelLaboralByYear(int year) {
        List<RelacionLaboral> relacionLaboralList = new ArrayList<RelacionLaboral>();
        try {
            relacionLaboralList = super.getResultSQLCatalogoRelLaboralByYear(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return relacionLaboralList;
    }

    public List<CentroCosto> getCatalogoCentroCosto() {
        List<CentroCosto> centroCostoList = new ArrayList<CentroCosto>();
        try {
            centroCostoList = super.getResultSQLCatalogoCentroCosto();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return centroCostoList;
    }

    public List<Municipio> getCatalogoMunicipio() {
        List<Municipio> municipioList = new ArrayList<Municipio>();
        try {
            municipioList = super.getResultSQLCatalogoMunicipio();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return municipioList;
    }

    public List<Proyecto> getCatalogoProyectoByRamoYear(String ramoId, int year) {
        List<Proyecto> proyectoList = new ArrayList<Proyecto>();
        try {
            proyectoList = super.getResultSQLCatalogoProyectoByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return proyectoList;
    }

    public List<ProyectoFuncional> getCatalogoProyectoFuncionalByRamoYear(String ramoId, int year) {
        List<ProyectoFuncional> proyectoFuncionalList = new ArrayList<ProyectoFuncional>();
        try {
            proyectoFuncionalList = super.getResultSQLCatalogoProyectoFuncionalByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return proyectoFuncionalList;
    }

    public List<Meta> getCatalogoMetaByRamoYear(String ramoId, int year) {
        List<Meta> metaList = new ArrayList<Meta>();
        try {
            metaList = super.getResultSQLCatalogoMetaByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return metaList;
    }

    public List<Accion> getCatalogoAccionByRamoYear(String ramoId, int year) {
        List<Accion> accionList = new ArrayList<Accion>();
        try {
            accionList = super.getResultSQLCatalogoAccionByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return accionList;
    }

    public List<AccionReq> getCatalogoAccionReqByRamoYear(String ramoId, int year) {
        List<AccionReq> accionReqList = new ArrayList<AccionReq>();
        try {
            accionReqList = super.getResultSQLCatalogoAccionReqByRamoYear(ramoId, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return accionReqList;
    }

    public List<Localidad> getCatalogoLocalidad(int year) {
        List<Localidad> localidadList = new ArrayList<Localidad>();
        try {
            localidadList = super.getResultSQLCatalogoLocalidad(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return localidadList;
    }
    
        public List<MedidaMeta> getCatalogoMedidaMeta(int year) {
        List<MedidaMeta> medidaMetaList = new ArrayList<MedidaMeta>();
        try {
            medidaMetaList = super.getResultSQLCatalogoMedidaMeta(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return medidaMetaList;
    }
}
