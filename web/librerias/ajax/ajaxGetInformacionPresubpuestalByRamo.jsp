<%-- 
    Document   : ajaxGetInformacionPresubpuestalByRamo
    Created on : Mar 20, 2015, 4:08:07 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.MedidaMeta"%>
<%@page import="gob.gbc.entidades.Localidad"%>
<%@page import="java.util.zip.ZipEntry"%>
<%@page import="java.util.zip.ZipOutputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="gob.gbc.entidades.RelacionLaboral"%>
<%@page import="gob.gbc.entidades.TipoGasto"%>
<%@page import="gob.gbc.entidades.ProgramaConac"%>
<%@page import="gob.gbc.entidades.Recurso"%>
<%@page import="gob.gbc.entidades.Fondo"%>
<%@page import="gob.gbc.entidades.Fuente"%>
<%@page import="gob.gbc.entidades.Funcion"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="gob.gbc.entidades.RamoPrograma"%>
<%@page import="gob.gbc.entidades.CodProg"%>
<%@page import="gob.gbc.entidades.Grupos"%>
<%@page import="gob.gbc.entidades.SubGrupos"%>
<%@page import="gob.gbc.entidades.SubSubGpo"%>
<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="gob.gbc.entidades.CodigoPPTO"%>
<%@page import="gob.gbc.entidades.Ppto"%>
<%@page import="gob.gbc.entidades.Finalidad"%>
<%@page import="gob.gbc.entidades.Subfuncion"%>
<%@page import="gob.gbc.entidades.Fin"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.entidades.AccionReq"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Municipio"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.File"%>
<%@page import="gob.gbc.util.Archivo"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="gob.gbc.aplicacion.EstructuraPresupuestalBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();

    String ramoId = "";
    int year = 0;
    int cont = 0;
    String ubicacion = new String();
    String numTemp = "";
    boolean cierre = false;
    String pathLogico = "";
    String tipoDependencia = new String();
    EstructuraPresupuestalBean estructuraPresupuestalBean = null;
    List<Archivo> archivoList = new ArrayList<Archivo>();
    List<Ramo> ramoList = new ArrayList<Ramo>();
    List<Programa> programaList = new ArrayList<Programa>();
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    List<CodProg> codProgList = new ArrayList<CodProg>();
    List<Grupos> gruposList = new ArrayList<Grupos>();
    List<SubGrupos> subgruposList = new ArrayList<SubGrupos>();
    List<SubSubGpo> subsubgpoList = new ArrayList<SubSubGpo>();
    List<Partida> partidaList = new ArrayList<Partida>();
    List<CodigoPPTO> codigosList = new ArrayList<CodigoPPTO>();
    List<Ppto> pptoList = new ArrayList<Ppto>();
    List<RamoPrograma> ramoProgramaList = new ArrayList<RamoPrograma>();
    List<Finalidad> finalidadList = new ArrayList<Finalidad>();
    List<Funcion> funcionList = new ArrayList<Funcion>();
    List<Subfuncion> subfuncionList = new ArrayList<Subfuncion>();
    List<Fin> finList = new ArrayList<Fin>();
    List<Fuente> fuenteList = new ArrayList<Fuente>();
    List<Fondo> fondoList = new ArrayList<Fondo>();
    List<Recurso> recursoList = new ArrayList<Recurso>();
    List<ProgramaConac> prgConacList = new ArrayList<ProgramaConac>();
    List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
    List<RelacionLaboral> relLaboralList = new ArrayList<RelacionLaboral>();
    List<Municipio> municipioList = new ArrayList<Municipio>();
    List<Proyecto> proyectoList = new ArrayList<Proyecto>();
    List<Meta> metaList = new ArrayList<Meta>();
    List<Accion> accionList = new ArrayList<Accion>();
    List<Localidad> localidadList = new ArrayList<Localidad>();
    List<MedidaMeta> medidaMetaList = new ArrayList<MedidaMeta>();

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    String contenidoArch = "";
    Archivo archivo;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        estructuraPresupuestalBean = new EstructuraPresupuestalBean(tipoDependencia);
        estructuraPresupuestalBean.setStrServer(request.getHeader("host"));
        estructuraPresupuestalBean.setStrUbicacion(getServletContext().getRealPath(""));
        estructuraPresupuestalBean.resultSQLConecta(tipoDependencia);

        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }

        if (request.getParameter("numTemp") != null && !request.getParameter("numTemp").equals("")) {
            numTemp = (String) request.getParameter("numTemp");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (!ramoId.equals("-1")) {
            cierre = estructuraPresupuestalBean.validaRamoCierre(ramoId, year);
            cierre = true;
            if (cierre) {
                pathLogico = application.getRealPath("/") + File.separatorChar + "temp" + File.separatorChar + "TempEstructuraPresupuestal" + numTemp;
                File theDir = new File(pathLogico);

                if (!theDir.exists()) {
                    boolean result = false;
                    theDir.mkdir();
                    result = true;
                }

                FileOutputStream fos = new FileOutputStream(pathLogico + File.separatorChar + "EstructuraPresupuestal" + numTemp + ".zip");
                ZipOutputStream zos = new ZipOutputStream(fos);

                //crear archivo ramo
                ramoList = estructuraPresupuestalBean.getCatalogoRamosByRamoYear(ramoId, year);
                contenidoArch += "ramo" + "\t";
                contenidoArch += "descr" + "\t";
                contenidoArch += "transitorio" + "\t";
                contenidoArch += "year" + "\n";

                for (Ramo ramo : ramoList) {
                    contenidoArch += (ramo.getRamo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (ramo.getRamoDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (ramo.getTransitorio() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += ramo.getYear() + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
    
                archivo.setStrNombreArchivo("01-" + ramoId + "-DGI.RAMOS.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";

                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);
 
                //crear archivo programa
                programaList = estructuraPresupuestalBean.getCatalogoProgramaByYear(year);

                contenidoArch += "prg" + "\t";
                contenidoArch += "descr" + "\t";
                contenidoArch += "year" + "\t";
                contenidoArch += "prg_conac" + "\n";

                for (Programa programa : programaList) {

                    contenidoArch += (programa.getProgramaId() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (programa.getProgramaDesc() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += programa.getYear() + "\t";
                    contenidoArch += (programa.getProgramaConac() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("02-" + ramoId + "-POA.PROGRAMA.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));                
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo dependencia
                dependenciaList = estructuraPresupuestalBean.getCatalogoDependenciaByRamoYear(ramoId, year);

                contenidoArch += "ramo\t";
                contenidoArch += "depto" + "\t";
                contenidoArch += "descr" + "\t";
                contenidoArch += "mpo" + "\t";
                contenidoArch += "year" + "\n";

                for (Dependencia dependencia : dependenciaList) {

                    contenidoArch += (dependencia.getRamo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (dependencia.getDeptoId() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (dependencia.getDepartamento() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (dependencia.getMpo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += dependencia.getYear() + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("03-" + ramoId + "-DGI.DEPENDENCIA.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo ramoprograma                  
                ramoProgramaList = estructuraPresupuestalBean.getCatalogoRamoProgramaByRamoYear(ramoId, year);

                contenidoArch += "year\t";
                contenidoArch += "ramo\t";
                contenidoArch += "prg\n";

                for (RamoPrograma ramoPrograma : ramoProgramaList) {

                    contenidoArch += ramoPrograma.getYear() + "\t";
                    contenidoArch += (ramoPrograma.getRamo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (ramoPrograma.getPrg() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("04-" + ramoId + "-POA.RAMO_PROGRAMA.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo codprog
                codProgList = estructuraPresupuestalBean.getCatalogoCodProgByRamoYear(ramoId, year);

                contenidoArch += "ramo\t";
                contenidoArch += "prg\t";
                contenidoArch += "depto" + "\t";
                contenidoArch += "year" + "\n";

                for (CodProg codProg : codProgList) {

                    contenidoArch += (codProg.getRamo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (codProg.getPrg() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (codProg.getDepto() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += codProg.getYear() + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("05-" + ramoId + "-POA.CODPROG.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo grupos                   
                gruposList = estructuraPresupuestalBean.getCatalogoGruposByYear(year);

                contenidoArch += "grupo\t";
                contenidoArch += "descr\t";
                contenidoArch += "year" + "\t";
                contenidoArch += "tipo_gasto" + "\n";

                for (Grupos grupos : gruposList) {

                    contenidoArch += (grupos.getGrupo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (grupos.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += grupos.getYear() + "\t";
                    contenidoArch += (grupos.getTipoGasto() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("06-" + ramoId + "-DGI.GRUPOS.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo subgrupos                   
                subgruposList = estructuraPresupuestalBean.getCatalogoSubGruposByYear(year);

                contenidoArch += "grupo\t";
                contenidoArch += "subgrupo\t";
                contenidoArch += "descr\t";
                contenidoArch += "year" + "\n";

                for (SubGrupos subgrupos : subgruposList) {

                    contenidoArch += (subgrupos.getGrupo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (subgrupos.getSubgrupo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (subgrupos.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += subgrupos.getYear() + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("07-" + ramoId + "-DGI.SUBGRUPOS.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo subsubgpo                   
                subsubgpoList = estructuraPresupuestalBean.getCatalogoSubSubGpoByYear(year);

                contenidoArch += "grupo\t";
                contenidoArch += "subgrupo\t";
                contenidoArch += "subsubgrupo\t";
                contenidoArch += "descr\t";
                contenidoArch += "year" + "\n";

                for (SubSubGpo subsubgpo : subsubgpoList) {

                    contenidoArch += (subsubgpo.getGrupo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (subsubgpo.getSubgrupo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (subsubgpo.getSubsubgrupo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (subsubgpo.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += subsubgpo.getYear() + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("08-" + ramoId + "-DGI.SUBSUBGPO.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo partida                  
                partidaList = estructuraPresupuestalBean.getCatalogoPartidaByYear(year);

                contenidoArch += "partida\t";
                contenidoArch += "descr\t";
                contenidoArch += "plantilla\t";
                contenidoArch += "year\t";
                contenidoArch += "subsubgpo\t";
                contenidoArch += "cta_aprobado\t";
                contenidoArch += "cta_modificado\t";
                contenidoArch += "cta_comprometido\t";
                contenidoArch += "cta_devengado\t";
                contenidoArch += "cta_ejercido\t";
                contenidoArch += "cta_pagado\t";
                contenidoArch += "cta_pptoxejer\t";
                contenidoArch += "relacion_laboral" + "\n";

                for (Partida partida : partidaList) {

                    contenidoArch += (partida.getPartidaId() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getPartida() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getPlantilla() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += partida.getYear() + "\t";
                    contenidoArch += (partida.getSubsubgrupo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getCtaAprobado() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getCtaModificado() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getCtaComprometido() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getCtaDevengado() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getCtaEjercido() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getCtaPagado() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getCtaPptoXEjer() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (partida.getRelacionLaboral() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("09-" + ramoId + "-DGI.PARTIDA.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo codigos                  
                codigosList = estructuraPresupuestalBean.getCatalogoCodigosByRamoYear(ramoId, year);

                contenidoArch += "year\t";
                contenidoArch += "ramo\t";
                contenidoArch += "depto\t";
                contenidoArch += "finalidad\t";
                contenidoArch += "funcion\t";
                contenidoArch += "subfuncion\t";
                contenidoArch += "prg_conac\t";
                contenidoArch += "prg\t";
                contenidoArch += "tipo_proy\t";
                contenidoArch += "proyecto\t";
                contenidoArch += "meta\t";
                contenidoArch += "accion\t";
                contenidoArch += "partida\t";
                contenidoArch += "tipo_gasto\t";
                contenidoArch += "fuente\t";
                contenidoArch += "fondo\t";
                contenidoArch += "recurso\t";
                contenidoArch += "municipio\t";
                contenidoArch += "delegacion\t";
                contenidoArch += "rel_laboral" + "\n";

                for (CodigoPPTO codigos : codigosList) {

                    contenidoArch += codigos.getYear() + "\t";
                    contenidoArch += (codigos.getRamoId() + "") + "\t";
                    contenidoArch += (codigos.getDepto() + "") + "\t";
                    contenidoArch += (codigos.getFinalidad() + "") + "\t";
                    contenidoArch += (codigos.getFuncion() + "") + "\t";
                    contenidoArch += (codigos.getSubfuncion() + "") + "\t";
                    contenidoArch += (codigos.getProgCONAC() + "") + "\t";
                    contenidoArch += (codigos.getPrograma() + "") + "\t";
                    contenidoArch += (codigos.getTipoProy() + "") + "\t";
                    contenidoArch += (codigos.getProyecto() + "") + "\t";
                    contenidoArch += codigos.getMetaId() + "\t";
                    contenidoArch += codigos.getAccionId() + "\t";
                    contenidoArch += (codigos.getPartida() + "") + "\t";
                    contenidoArch += (codigos.getTipoGasto() + "") + "\t";
                    contenidoArch += (codigos.getFuente() + "") + "\t";
                    contenidoArch += (codigos.getFondo() + "") + "\t";
                    contenidoArch += (codigos.getRecurso() + "") + "\t";
                    contenidoArch += (codigos.getMunicipio() + "") + "\t";
                    contenidoArch += codigos.getDelegacionId() + "\t";
                    contenidoArch += (codigos.getRelLaboral() + "") + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("10-" + ramoId + "-SPP.CODIGOS.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);


                //crear archivo finalidad                
                finalidadList = estructuraPresupuestalBean.getCatalogoFinalidadByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "finalidad\t";
                contenidoArch += "descr\n";

                for (Finalidad finalidad : finalidadList) {

                    contenidoArch += finalidad.getYear() + "\t";
                    contenidoArch += (finalidad.getFinalidad() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (finalidad.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("11-" + ramoId + "-POA.FINALIDAD.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo funcion               
                funcionList = estructuraPresupuestalBean.getCatalogoFuncionByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "finalidad\t";
                contenidoArch += "funcion\t";
                contenidoArch += "descr\n";

                for (Funcion funcion : funcionList) {

                    contenidoArch += funcion.getYear() + "\t";
                    contenidoArch += (funcion.getFinalidad() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (funcion.getFuncion() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (funcion.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("12-" + ramoId + "-POA.FUNCION.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo subfuncion               
                subfuncionList = estructuraPresupuestalBean.getCatalogoSubfuncionByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "finalidad\t";
                contenidoArch += "funcion\t";
                contenidoArch += "subFuncion\t";
                contenidoArch += "descr\n";

                for (Subfuncion subfuncion : subfuncionList) {

                    contenidoArch += subfuncion.getYear() + "\t";
                    contenidoArch += (subfuncion.getFinalidad() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (subfuncion.getFuncion() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (subfuncion.getSubfuncion() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (subfuncion.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("13-" + ramoId + "-POA.SUBFUNCION.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo fin               
                finList = estructuraPresupuestalBean.getCatalogoFinByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "fin\t";
                contenidoArch += "nombre\t";
                contenidoArch += "descr\n";

                for (Fin fin : finList) {

                    contenidoArch += fin.getYear() + "\t";
                    contenidoArch += fin.getFinId() + "\t";
                    contenidoArch += (fin.getFin() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (fin.getDescripcion() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("14-" + ramoId + "-POA.FIN.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo fuente            
                fuenteList = estructuraPresupuestalBean.getCatalogoFuenteByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "fuente\t";
                contenidoArch += "descr\n";


                for (Fuente fuente : fuenteList) {

                    contenidoArch += fuente.getYear() + "\t";
                    contenidoArch += (fuente.getFuente() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (fuente.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("15-" + ramoId + "-POA.FUENTE.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo fondo            
                fondoList = estructuraPresupuestalBean.getCatalogoFondoByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "fuente\t";
                contenidoArch += "fondo\t";
                contenidoArch += "descr\n";


                for (Fondo fondo : fondoList) {

                    contenidoArch += fondo.getYear() + "\t";
                    contenidoArch += (fondo.getFuente() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (fondo.getFondo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (fondo.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("16-" + ramoId + "-POA.FONDO.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo Recurso            
                recursoList = estructuraPresupuestalBean.getCatalogoRecursoByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "fuente\t";
                contenidoArch += "fondo\t";
                contenidoArch += "recurso\t";
                contenidoArch += "descr\n";

                for (Recurso recurso : recursoList) {

                    contenidoArch += recurso.getYear() + "\t";
                    contenidoArch += (recurso.getFuente() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (recurso.getFondo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (recurso.getRecurso() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (recurso.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("17-" + ramoId + "-POA.RECURSO.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo prgconac            
                prgConacList = estructuraPresupuestalBean.getCatalogoPrgConacByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "prg_conac\t";
                contenidoArch += "descr\n";


                for (ProgramaConac prgConac : prgConacList) {

                    contenidoArch += prgConac.getAnio() + "\t";
                    contenidoArch += (prgConac.getProgramaConac() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (prgConac.getDescripcion() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("18-" + ramoId + "-POA.PROGRAMA_CONAC.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo tipogasto            
                tipoGastoList = estructuraPresupuestalBean.getCatalogoTipoGastoByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "tipo_gasto\t";
                contenidoArch += "descr\n";

                for (TipoGasto tipoGasto : tipoGastoList) {

                    contenidoArch += tipoGasto.getYear() + "\t";
                    contenidoArch += tipoGasto.getTipoGastoId() + "\t";
                    contenidoArch += (tipoGasto.getTipoGasto() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("19-" + ramoId + "-POA.TIPO_GASTO.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo relLaboral           
                relLaboralList = estructuraPresupuestalBean.getCatalogoRelLaboralByYear(year);
                contenidoArch += "year\t";
                contenidoArch += "rel_laboral\t";
                contenidoArch += "descr\n";

                for (RelacionLaboral relLaboral : relLaboralList) {

                    contenidoArch += relLaboral.getYear() + "\t";
                    contenidoArch += (relLaboral.getRelacionLabId() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (relLaboral.getRelacionLab() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("20-" + ramoId + "-POA.REL_LABORAL.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";

                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo municipio       
                municipioList = estructuraPresupuestalBean.getCatalogoMunicipio();
                contenidoArch += "mpo\t";
                contenidoArch += "nombrempo\n";

                for (Municipio municipio : municipioList) {

                    contenidoArch += municipio.getMunicipioId() + "\t";
                    contenidoArch += (municipio.getMunicipio() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("21-" + ramoId + "-DGI.MUNICIPIO.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo proyecto                 
                proyectoList = estructuraPresupuestalBean.getCatalogoProyectoByRamoYear(ramoId, year);

                contenidoArch += "proy\t";
                contenidoArch += "tipo_proy\t";
                contenidoArch += "year\t";
                contenidoArch += "ramo\t";
                contenidoArch += "prg\n";

                for (Proyecto proyecto : proyectoList) {
                    contenidoArch += proyecto.getProyId() + "\t";
                    contenidoArch += (proyecto.getTipoProyecto() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += proyecto.getYear() + "\t";
                    contenidoArch += (proyecto.getRamo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (proyecto.getPrg() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("22-" + ramoId + "-POA.PROYECTO.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo meta                
                metaList = estructuraPresupuestalBean.getCatalogoMetaByRamoYear(ramoId, year);

                contenidoArch += "year\t";
                contenidoArch += "ramo\t";
                contenidoArch += "prg\t";
                contenidoArch += "meta\t";
                contenidoArch += "descr\t";
                contenidoArch += "linea\t";
                contenidoArch += "finalidad\t";
                contenidoArch += "funcion\t";
                contenidoArch += "subfuncion\t";
                contenidoArch += "presupuestar\t";
                contenidoArch += "aprobcongreso\t";
                contenidoArch += "convenio\n";

                for (Meta meta : metaList) {

                    contenidoArch += meta.getYear() + "\t";
                    contenidoArch += (meta.getRamo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (meta.getPrograma() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += meta.getMetId() + "\t";
                    contenidoArch += (meta.getMeta() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (meta.getLineaPED() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (meta.getFinalidad() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (meta.getFuncion() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (meta.getSubfuncion() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (meta.getPresupuestar() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (meta.getAprobCongreso() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (meta.getConvenio() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("23-" + ramoId + "-POA.META.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo accion                
                accionList = estructuraPresupuestalBean.getCatalogoAccionByRamoYear(ramoId, year);

                contenidoArch += "year\t";
                contenidoArch += "ramo\t";
                contenidoArch += "prg\t";
                contenidoArch += "depto\t";
                contenidoArch += "meta\t";
                contenidoArch += "accion\t";
                contenidoArch += "descr\n";


                for (Accion accion : accionList) {

                    contenidoArch += accion.getYear() + "\t";
                    contenidoArch += (accion.getRamo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (accion.getPrg() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (accion.getDeptoId() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += accion.getMetaId() + "\t";
                    contenidoArch += accion.getAccId() + "\t";
                    contenidoArch += (accion.getAccion() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";
                }
                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("24-" + ramoId + "-POA.ACCION.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);
                ubicacion = pathLogico + File.separatorChar + "25-" + ramoId + "-SPP.PPTO.txt";
                //crear archivo ppto    
                System.out.println("1.-"+new Date());
                estructuraPresupuestalBean.getCatalogoPPTOByRamoYear(ubicacion,ramoId, year);
                System.out.println("2.-"+new Date());
                

               /* for (Ppto ppto : pptoList) {
                    cont++;
                    if(cont % 100 == 0 || cont == 1){
                        System.out.println(new Date() +"-"+cont);
                    }
                    //codigosList = estructuraPresupuestalBean.existeCodigoPPTO(ppto.getRamo(), ppto.getYear(), ppto.getPrg(), ppto.getFinalidad(), ppto.getFuncion(), ppto.getSubfuncion(), ppto.getPrgConac(), ppto.getDepto(), ppto.getTipoProy(), ppto.getProyecto(), ppto.getMeta(), ppto.getAccion(), ppto.getPartida(), ppto.getTipoGasto(), ppto.getFuente(), ppto.getFondo(), ppto.getRecurso(), ppto.getMunicipio(), ppto.getDelegacion(), ppto.getRelLaboral());
                    //if (codigosList.size() > 0) {
                    contenidoArch += ppto.getYear() + "\t";
                    contenidoArch += (ppto.getRamo() + "") + "\t";
                    contenidoArch += (ppto.getDepto() + "") + "\t";
                    contenidoArch += (ppto.getFinalidad() + "") + "\t";
                    contenidoArch += (ppto.getFuncion() + "") + "\t";
                    contenidoArch += (ppto.getSubfuncion() + "") + "\t";
                    contenidoArch += (ppto.getPrgConac() + "") + "\t";
                    contenidoArch += (ppto.getPrg() + "") + "\t";
                    contenidoArch += (ppto.getTipoProy() + "") + "\t";
                    contenidoArch += ppto.getProyectoId() + "\t";
                    contenidoArch += ppto.getMetaId() + "\t";
                    contenidoArch += ppto.getAccionId() + "\t";
                    contenidoArch += (ppto.getPartida() + "") + "\t";
                    contenidoArch += (ppto.getTipoGasto() + "") + "\t";
                    contenidoArch += (ppto.getFuente() + "") + "\t";
                    contenidoArch += (ppto.getFondo() + "") + "\t";
                    contenidoArch += (ppto.getRecurso() + "") + "\t";
                    contenidoArch += (ppto.getMunicipio() + "") + "\t";
                    contenidoArch += ppto.getDelegacionId() + "\t";
                    contenidoArch += (ppto.getRelLaboral() + "") + "\t";
                    contenidoArch += ppto.getMes() + "\t";
                    contenidoArch += ppto.getAsignado() + "\n";
                    //}
                }*/

                //contenidoArch = contenidoArch.replace("null", "");
                /*archivo = new Archivo();
                archivo.setStrNombreArchivo("25-" + ramoId + "-SPP.PPTO.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);*/
                contenidoArch = "";
                archivo.addToZipFile(ubicacion, zos);

                //crear archivo localidad      
                localidadList = estructuraPresupuestalBean.getCatalogoLocalidad(year);
                contenidoArch += "mpo\t";
                contenidoArch += "localidad\t";
                contenidoArch += "descr\n";

                for (Localidad localidad : localidadList) {

                    contenidoArch += (localidad.getMpo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += localidad.getLocId() + "\t";
                    contenidoArch += (localidad.getLocalidad() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\n";

                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("26-" + ramoId + "-DGI.LOCALIDAD.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                //crear archivo medida meta     
                medidaMetaList = estructuraPresupuestalBean.getCatalogoMedidaMeta(year);
                contenidoArch += "medida\t";
                contenidoArch += "descr\t";
                contenidoArch += "activo\t";
                contenidoArch += "year\n";

                for (MedidaMeta medidaMeta : medidaMetaList) {

                    contenidoArch += medidaMeta.getMedida() + "\t";
                    contenidoArch += (medidaMeta.getDescr() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += (medidaMeta.getActivo() + "").replace("\n", " ").replace("\r", " ").replace("\t", " ") + "\t";
                    contenidoArch += medidaMeta.getYear() + "\n";
                }

                contenidoArch = contenidoArch.replace("null", "");
                archivo = new Archivo();
                archivo.setStrNombreArchivo("27-" + ramoId + "-DGI.MEDIDA_META.txt");
                archivo.setStrUbicacion(pathLogico + File.separatorChar);
                archivo.setStrInformacion(new String(contenidoArch));
                archivo.grabaArchivo();
                archivoList.add(archivo);
                contenidoArch = "";
                archivo.addToZipFile(archivo.getStrUbicacion() + archivo.getStrNombreArchivo(), zos);

                zos.close();
                fos.close();

                strResultado = "1";

            } else {
                strResultado = "-2";
            }
        } else {
            strResultado = "-3";
        }
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (estructuraPresupuestalBean != null) {
            estructuraPresupuestalBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>