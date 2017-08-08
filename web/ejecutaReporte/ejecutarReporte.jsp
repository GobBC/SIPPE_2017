<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="net.sf.jasperreports.engine.JRPrintElement"%>
<%@page import="net.sf.jasperreports.engine.fill.JRTemplatePrintText"%>
<%@page import="net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRXlsExporterParameter"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.sf.jasperreports.engine.base.JRBasePrintPage"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.jasperreports.engine.JRPrintPage"%>
<%@page import="gob.gbc.aplicacion.ParametroBean"%>
<%@page import="gob.gbc.bd.ConectaBD"%>
<%@page import="java.io.File"%>
<%@page import="gob.gbc.estandar.DatosGeneral"%>
<%@page import="gob.gbc.bd.DatosBdConfig"%>
<%@page import="gob.gbc.sql.QuerysBD"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="net.sf.jasperreports.engine.export.JRCsvExporter"%>
<%@page import="net.sf.jasperreports.engine.JRException"%>
<%@page import="java.io.IOException"%>
<%@page import="net.sf.jasperreports.engine.export.JRXlsExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRRtfExporter"%>
<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@page import="net.sf.jasperreports.engine.JRExporter"%>
<%@page import="java.io.OutputStream"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>

<html>
    <head>
        <title>Reporte Descripci&oacute;n de Programas</title>
        <%
            if (session.getAttribute("strUsuario") == null
                    || session.getAttribute("strUsuario").equals("")) {
                response.sendRedirect("logout.jsp");
                return;
            }
            System.setProperty("java.awt.headless", "true");
            ConectaBD conectaBD;
            String tipoDependencia = new String();
            if (request.isRequestedSessionIdValid()) {

                if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {

                    tipoDependencia = (String) session.getAttribute("tipoDependencia");
                }
            }
            if (Utilerias.existeParametro("tipoDep", request)) {
                tipoDependencia = request.getParameter("tipoDep");
            }
            ResultSQL resultSQL = new ResultSQL(tipoDependencia);
            resultSQL.setStrServer(request.getHeader("host"));
            resultSQL.setStrUbicacion(getServletContext().getRealPath(""));
            resultSQL.resultSQLConecta(tipoDependencia);//cambiar 

            int intYear = 0;
            String queryPartidas = new String();


        %>

    </head>
    <body>
        <%            QuerysBD querysBD = new QuerysBD();
            OutputStream ouputStream = null;
            DatosGeneral datosConfig = new DatosGeneral("General.properties");
            String path = application.getRealPath("/reportes") + File.separatorChar;
            String imgpath = application.getRealPath("/reportes/imagenes") + File.separatorChar;
            String document_path = application.getRealPath("/reportes") + File.separatorChar;
            String rptPath = application.getRealPath("/reportes") + File.separatorChar;
            String filename = new String();
            String reporttype = new String();
            String ramoI = new String();
            String ramoF = new String();
            String programaI = new String();
            String programaF = new String();
            String deptoIni = new String();
            String deptoFin = new String();
            String proyIni = new String();
            String proyFin = new String();
            String partidaIni = new String();
            String partidaFin = new String();
            String inCapsule = new String();
            String strTransparencia = new String();
            String usuario = new String();
            String dirReporte = new String();
            String estatusMovimientoId = new String();
            String tipoMovimiento = new String();
            String codCompleto = new String();
            String pptoModAct = new String();
            List<JRPrintPage> paginas;
            boolean banderaPage = false;
            int tipoReporte = 0;
            int countPages = 0;
            int yearSig = 0;
            int pagina = 0;
            int yearCaptura = 0;
            int costo = 0;
            int periodo = 0;
            int periodoIni = 1;
            int periodoFin = 12;
            int mes = 0;
            int firmas = -1;
            int radioBusqueda = 0;
            int iCandadoMTvsCIM = 0;
            long lOficio = 0;
            long caratula = 0;
            String oficioList = new String();
            String opCon = new String();
            //String[] strURL = null;
            String Ramo = "";
            String Programa = "";
            String ramoInList = "";
            String programaInList = "";
            String proyectoInList = "";
            String ponderadoProgramaInList = "";
            String ponderadoMetaInList = "";
            String grupoPobInList = "";
            String reportesExcel = new String();
            String sTipoOficio = "";
            String sTipoMov = "";
            String ramoSession = "";
            String rol = "";
            Connection conConexion;
            String OpcionRepDep = "";
            String NivelesInList = "";
            int esNormativoRepPend = 0;
            String RepDefPlantilla = new String();
            String strParamValidaCIM = new String();
            String strDocValido = "S";
            conectaBD = resultSQL.getConectaBD();
            String query = "";
            String isConsultaxRangoFolios="";
            String folioIni="";
            String folioFin="";
            String folioInList = "";
            String fecI="";
            String fecF="";

            try {
                if (request.isRequestedSessionIdValid()) {
                    if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                        intYear = Integer.parseInt((String) session.getAttribute("year"));
                        yearSig = intYear - 1;
                    }
                    if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
                        usuario = (String) session.getAttribute("strUsuario");
                    }
                }
                if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
                    ramoSession = (String) session.getAttribute("ramoAsignado");
                }
                if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
                    rol = (String) session.getAttribute("strRol");
                }
                if (Utilerias.existeParametro("yearT", request)) {
                    intYear = Integer.parseInt(request.getParameter("yearT"));
                }

                if (Utilerias.existeParametro("opCon", request)) {
                    opCon = request.getParameter("opCon");
                }

                if (Utilerias.existeParametro("usuario", request)) {
                    usuario = request.getParameter("usuario");
                }
                if (Utilerias.existeParametro("filename", request)) {
                    filename = request.getParameter("filename");
                }
                if (Utilerias.existeParametro("rptPath", request)) {
                    rptPath += request.getParameter("rptPath") + File.separatorChar;
                    filename = request.getParameter("rptPath") + File.separatorChar + filename;
                }
                if (Utilerias.existeParametro("tipoReporte", request)) {
                    tipoReporte = Integer.parseInt(request.getParameter("tipoReporte"));
                    querysBD = new QuerysBD();
                }
                
                if (Utilerias.existeParametro("fecI", request)) {
                    fecI = request.getParameter("fecI");
                }
                
                if (Utilerias.existeParametro("fecF", request)) {
                    fecF = request.getParameter("fecF");
                }

                if (tipoReporte == 6) {
                    if (Utilerias.existeParametro("radio-busca", request)) {
                        radioBusqueda = Integer.parseInt(request.getParameter("radio-busca"));
                    }
                }

                if (Utilerias.existeParametro("estatusMovimientoId", request)) {
                    estatusMovimientoId = request.getParameter("estatusMovimientoId");
                }
                /*bloque para reporte de transferencias ampliaciones*/
                if (Utilerias.existeParametro("codCompleto", request)) {
                    codCompleto = request.getParameter("codCompleto");
                }

                if (Utilerias.existeParametro("tipoOficio", request)) {
                    sTipoOficio = request.getParameter("tipoOficio");
                    if (sTipoOficio.equals("null") || sTipoOficio.isEmpty() || sTipoOficio == null) {
                        sTipoOficio = new String();
                    }
                }
                if (Utilerias.existeParametro("tipoMovimiento", request)) {
                    sTipoMov = request.getParameter("tipoMovimiento");
                }

                if (Utilerias.existeParametro("tipoReporteAmpTran", request)) {
                    tipoReporte = Integer.parseInt(request.getParameter("tipoReporteAmpTran"));
                    switch (tipoReporte) {
                        case 1:
                            if (sTipoMov.equals("A")) {
                                filename = "rptAmpliacionesConcentrado.jasper";
                            } else {
                                filename = "rptTransferenciaConcentrado.jasper";
                            }
                            break;
                        case 2:
                            if (sTipoMov.equals("A")) {
                                filename = "rptAmpliacionesDetallado.jasper";
                            } else {
                                filename = "rptTransferenciasDetallado.jasper";
                            }
                            break;
                        case 3:
                            filename = "RecalendarizacionFormatoRevision" + File.separatorChar + "rptRecalendarizacionFormatoRevision.jasper";
                            break;

                    }
                    tipoReporte = 99;
                }
                /*bloque para reporte de transferencias ampliaciones termina*/
                if (Utilerias.existeParametro("costoMeta", request)) {
                    costo = Integer.parseInt(request.getParameter("costoMeta"));
                }

                if (Utilerias.existeParametro("selRamoI", request)) {
                    ramoI = request.getParameter("selRamoI");
                }

                if (Utilerias.existeParametro("selRamoF", request)) {
                    ramoF = request.getParameter("selRamoF");
                }

                if (Utilerias.existeParametro("banderaPage", request)) {
                    banderaPage = Boolean.valueOf(request.getParameter("banderaPage"));
                }

                if (Utilerias.existeParametro("selProgramaI", request)) {
                    programaI = request.getParameter("selProgramaI");
                }

                if (Utilerias.existeParametro("selProgramaF", request)) {
                    programaF = request.getParameter("selProgramaF");
                }

                if (Utilerias.existeParametro("selDeptoI", request)) {
                    deptoIni = request.getParameter("selDeptoI");
                }

                if (Utilerias.existeParametro("selDeptoF", request)) {
                    deptoFin = request.getParameter("selDeptoF");
                }

                if (Utilerias.existeParametro("selProActI", request)) {
                    proyIni = request.getParameter("selProActI");
                }

                if (Utilerias.existeParametro("selProActF", request)) {
                    proyFin = request.getParameter("selProActF");
                }

                if (Utilerias.existeParametro("periodoIni", request)) {
                    periodoIni = Integer.parseInt(request.getParameter("periodoIni"));
                }

                if (Utilerias.existeParametro("periodoFin", request)) {
                    periodoFin = Integer.parseInt(request.getParameter("periodoFin"));
                }

                if (Utilerias.existeParametro("selCaratula", request)) {
                    caratula = Integer.parseInt(request.getParameter("selCaratula"));
                }
                if (Utilerias.existeParametro("selPartIni", request)) {
                    partidaIni = request.getParameter("selPartIni");
                }

                if (Utilerias.existeParametro("selPartFin", request)) {
                    partidaFin = request.getParameter("selPartFin");
                }

                if (Utilerias.existeParametro("selPartidaI", request)) {
                    partidaIni = request.getParameter("selPartidaI");
                }

                if (Utilerias.existeParametro("selPartidaF", request)) {
                    partidaFin = request.getParameter("selPartidaF");
                }

                if (Utilerias.existeParametro("tipoGrupo", request)) {
                    dirReporte = request.getParameter("tipoGrupo");
                }

                if (Utilerias.existeParametro("reporttype", request)) {
                    reporttype = request.getParameter("reporttype");
                }

                if (Utilerias.existeParametro("selRamo", request)) {
                    Ramo = request.getParameter("selRamo");
                }

                if (Utilerias.existeParametro("pagina", request)) {
                    pagina = Integer.parseInt(request.getParameter("pagina"));
                }

                if (Utilerias.existeParametro("selPrograma", request)) {
                    Programa = request.getParameter("selPrograma");
                }

                if (Utilerias.existeParametro("yearCaptura", request)) {
                    yearCaptura = Integer.parseInt(request.getParameter("yearCaptura"));
                }

                if (Utilerias.existeParametro("inCapsule", request)) {
                    inCapsule = request.getParameter("inCapsule");
                }

                if (Utilerias.existeParametro("ramoInList", request)) {
                    ramoInList = request.getParameter("ramoInList");
                }
                
                if (Utilerias.existeParametro("grupoPobInList", request)) {
                    grupoPobInList = request.getParameter("grupoPobInList");
                }

                if (Utilerias.existeParametro("programaInList", request)) {
                    programaInList = request.getParameter("programaInList");
                }

                if (Utilerias.existeParametro("proyectoInList", request)) {
                    proyectoInList = request.getParameter("proyectoInList");
                }
                
                if (Utilerias.existeParametro("ponderadoProgramaList", request)) {
                    ponderadoProgramaInList = request.getParameter("ponderadoProgramaList");
                }
                
                if (Utilerias.existeParametro("ponderadoMetaList", request)) {
                    ponderadoMetaInList = request.getParameter("ponderadoMetaList");
                }

                if (Utilerias.existeParametro("reportesExcel", request)) {
                    reportesExcel = request.getParameter("reportesExcel");
                }

                if (Utilerias.existeParametro("queryPartidas", request)) {
                    queryPartidas = request.getParameter("queryPartidas");
                }
                if (Utilerias.existeParametro("periodo", request)) {
                    periodo = Integer.parseInt(request.getParameter("periodo"));
                    mes = periodo * 3;
                }
                if (Utilerias.existeParametro("tipoMovimiento", request)) {
                    tipoMovimiento = request.getParameter("tipoMovimiento");
                }
                if (Utilerias.existeParametro("oficioList", request)) {
                    oficioList = request.getParameter("oficioList");
                }
                if (Utilerias.existeParametro("oficio", request)) {
                    lOficio = Integer.parseInt(request.getParameter("oficio"));
                }
                if (tipoDependencia.equals("SPPD")) {
                    firmas = 0;
                } else if (tipoDependencia.equals("PROGD")) {
                    firmas = 1;
                }

                if (Utilerias.existeParametro("OpcionRepDep", request)) {
                    OpcionRepDep = request.getParameter("OpcionRepDep");
                }
                if (Utilerias.existeParametro("NivelesInList", request)) {
                    NivelesInList = request.getParameter("NivelesInList");
                }

                if (Utilerias.existeParametro("pptoModAut", request)) {
                    pptoModAct = request.getParameter("pptoModAut");
                    if(dirReporte.equals("anual") && pptoModAct.equals("ACT"))
                        dirReporte = "anualModificado";
                }

                if (Utilerias.existeParametro("esNormativo", request)) {
                    esNormativoRepPend = Integer.parseInt(request.getParameter("esNormativo"));
                }
                
                 if (Utilerias.existeParametro("isConsultaxRangoFolios", request)) {
                    isConsultaxRangoFolios = request.getParameter("isConsultaxRangoFolios");
                }
                
                if (Utilerias.existeParametro("folioIni", request)) {
                    folioIni = request.getParameter("folioIni");
                }
                
                if (Utilerias.existeParametro("folioFin", request)) {
                    folioFin = request.getParameter("folioFin");
                }
                
                if (Utilerias.existeParametro("folioInList", request)) {
                    folioInList = request.getParameter("folioInList");
                }

                if (tipoReporte == 1 && ramoInList != null) {
                    query = querysBD.getSQLReportePOARamos(intYear, ramoInList);
                    banderaPage = true;
                } else if (tipoReporte == 2 && ramoInList != null) {
                    query = querysBD.getSQLReportePOARamosPrgs(intYear, ramoInList, programaI, programaF);
                    banderaPage = true;
                } else if (tipoReporte == 3 && ramoInList != null) {
                    query = " R.RAMO IN (" + ramoInList + ") ";
                    if (!programaI.equals("-1") && !programaF.equals("-1")) {
                        query += " AND P.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' ";
                    }
                    rptPath += "reporteMasivo" + File.separatorChar;
                } else if (tipoReporte == 4 && ramoInList != null) {
                    query = " R.RAMO IN (" + ramoInList + ") AND "
                            + "P.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' ";
                    rptPath += "reporteMasivo" + File.separatorChar;
                } else if (tipoReporte == 5) {
                    if (!partidaIni.equals("-1") && !partidaFin.equals("-1") && !partidaIni.isEmpty() && !partidaFin.isEmpty()) {
                        query += "\n AND TR.PARTIDA BETWEEN '" + partidaIni + "' AND '" + partidaFin + "' ";
                    }
                    if (!programaI.equalsIgnoreCase("-1")) {
                        if (programaI.equalsIgnoreCase(programaF)) {
                            query += ""
                                    + " AND TR.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' "
                                    + " AND TR.DEPTO BETWEEN '" + deptoIni + "' AND '" + deptoFin + "' ";
                        } else {
                            query += ""
                                    + " AND TR.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' ";
                        }
                    } else {
                        query += "";
                    }
                } else if (tipoReporte == 6) {
                    if (radioBusqueda == 1) {
                        //getResultSQLgetFoliosByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula)
                        if (caratula != -2 && caratula != 0) {
                            oficioList = resultSQL.getResultSQLgetFoliosByCaratula(intYear, Ramo, usuario, rol.equals(resultSQL.getResultSQLGetRolesPrg()), caratula);
                            if (oficioList.isEmpty()) {
                                query = "TR.OFICIO IN (0)";
                            } else {
                                for (int cont = 0; cont < oficioList.split(",").length; cont++) {
                                    query += "TR.OFICIO IN (" + oficioList.split(",")[cont] + ") ";
                                    if (cont < oficioList.split(",").length - 1) {
                                        query += " OR \n";
                                    }
                                }
                            }
                        } else {
                            query += "TR.RAMO IN (" + Ramo + ") \n";
                            if (!programaI.equals("-1") && !programaF.equals("-1")) {
                                if (!programaI.equals(programaF)) {
                                    query += " AND TR.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' \n";
                                } else {
                                    query += " AND TR.PRG = '" + programaI + "' \n";
                                    if (!proyIni.equals("-1") && !proyFin.equals("-1")) {
                                        query += " AND TR.PROY||','||TR.TIPO_PROY BETWEEN '" + proyIni + "' AND '" + proyFin + "' \n";
                                    }

                                }
                            }
                            if (caratula != 0) {
                                query += "AND (";
                                oficioList = resultSQL.getResultSQLgetFoliosByCaratula(intYear, Ramo, usuario, rol.equals(resultSQL.getResultSQLGetRolesPrg()), caratula);
                                if (oficioList.isEmpty()) {
                                    query += " TR.OFICIO IN (0)";
                                } else {
                                    for (int cont = 0; cont < oficioList.split(",").length; cont++) {
                                        query += "TR.OFICIO IN (" + oficioList.split(",")[cont] + ") ";
                                        if (cont < oficioList.split(",").length - 1) {
                                            query += " OR \n";
                                        }
                                    }
                                }
                                query += ")";
                            }
                        }
                    } else if (radioBusqueda == 0) {
                        query = "TR.OFICIO = " + oficioList + " ";
                    }
                }

                if (!reportesExcel.isEmpty()) {
                    if (!programaInList.equals("")) {
                        query += " AND PRG.PRG IN(" + programaInList + ") ";
                    }

                    if (!proyectoInList.equals("")) {
                        query += " AND PROY.TIPO_PROY||PROY.PROY IN(" + proyectoInList + ") ";
                    }

                }
                if (filename.equals("avanceAnual" + File.separatorChar + "rptAvanceAnual.jasper")) {
                    rptPath += "avanceAnual" + File.separatorChar;
                    iCandadoMTvsCIM = resultSQL.getResultSQLvalidaCapturaCIM(periodo, intYear, ramoInList, programaI, programaF);
                    strParamValidaCIM = resultSQL.getResultSQLParamvalidaCIM();
                    if (iCandadoMTvsCIM <= 0 && strParamValidaCIM.equals("S")) {
                        strDocValido = "N";/*out.println("<script> alert('EL PERIODO QUE SE REQUIERE IMPRIMIR NO TIENE INFORMACIÓN DE CARGA EN EL CIM'); </script>");
                         return;*/

                    }
                    /**
                     * *
                     */
                } else if (filename.equals("MasivoTrimestre" + File.separatorChar + "rptAvancePoaTrimestre.jasper")) {
                    rptPath += "MasivoTrimestre" + File.separatorChar;
                    /**
                     * **
                     */
                    iCandadoMTvsCIM = resultSQL.getResultSQLvalidaCapturaCIM(periodo, intYear, ramoInList, programaI, programaF);
                    strParamValidaCIM = resultSQL.getResultSQLParamvalidaCIM();
                    if (iCandadoMTvsCIM <= 0 && strParamValidaCIM.equals("S")) {
                        strDocValido = "N";/*out.println("<script> alert('EL PERIODO QUE SE REQUIERE IMPRIMIR NO TIENE INFORMACIÓN DE CARGA EN EL CIM'); </script>");
                         return;*/

                    }
                    /**
                     * *
                     */
                } else if (filename.equals("rptAvisoCierre.jasper")) {
                    filename = "avisoCierre" + File.separatorChar + filename;
                    rptPath += "avisoCierre" + File.separatorChar;
                } else if (filename.equals("rptPendienteIrreductible.jasper")) {
                    filename = "repPendienteIrreductible" + File.separatorChar + filename;
                    rptPath += "repPendienteIrreductible" + File.separatorChar;
                } else if (filename.equals("avanceAnual" + File.separatorChar + "rptAvanceAnual.jasper")) {
                    rptPath += "avanceAnual" + File.separatorChar;
                } else if (filename.equals("ingresoEgreso" + File.separatorChar + "rptPresupIngEgr.jasper")) {
                    rptPath += "ingresoEgreso" + File.separatorChar;
                } else if (filename.equals("ComparativoIngresoEgreso" + File.separatorChar + "RptComparativoIngresoEgresoActualSiguiente.jasper")) {
                    rptPath += "ComparativoIngresoEgreso" + File.separatorChar;
                } else if (filename.equals("rptProgramadoCongelado" + File.separatorChar + "RptPOAweb.jasper")) {
                    rptPath += "rptProgramadoCongelado" + File.separatorChar;
                } else if (filename.equals("rptMIR.jasper")||filename.equals("rptMIRxls.jasper")) {
                    rptPath += "MIR" + File.separatorChar;
                    filename = "MIR" + File.separatorChar + filename;
                }
                if (!dirReporte.isEmpty()) {
                    filename = "PptoCalendarizadoAnual" + File.separatorChar + dirReporte + File.separatorChar + filename;
                    rptPath += "PptoCalendarizadoAnual" + File.separatorChar + dirReporte + File.separatorChar;
                    query = new String();
                    if (dirReporte.equals("anual") || dirReporte.equals("anualModificado")) {
                        /*
                        if(pptoModAct.equals("ACT")){
                            dirReporte = "anualModificado";
                        }
                        */
                        query += " PPTO.PARTIDA BETWEEN '" + partidaIni + "' AND '" + partidaFin + "' AND ";
                        if (!programaI.equals("-1") && !programaF.equals("-1")) {
                            query += " PPTO.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' AND ";
                        }
                        if (!deptoIni.equals("-1") && !deptoFin.equals("-1")) {
                            query += " PPTO.DEPTO BETWEEN '" + deptoIni + "' AND '" + deptoFin + "' AND ";
                        }
                    } else if (dirReporte.equals("calendarizado")) {
                        query += " VPAM.PARTIDA BETWEEN '" + partidaIni + "' AND '" + partidaFin + "' AND ";
                        if (!programaI.equals("-1") && !programaF.equals("-1")) {
                            query += " VPAM.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' AND ";
                        }
                        if (!deptoIni.equals("-1") && !deptoFin.equals("-1")) {
                            query += " VPAM.DEPTO BETWEEN '" + deptoIni + "' AND '" + deptoFin + "' AND ";
                        }
                    }
                }

                //validar cierre intYear
                // jasperParameter is a Hashmap contains the  parameters
                // passed from application to the jrxml layout
                Map<String, Object> jasperParameter = new HashMap<String, Object>();
                //jasperParameter.put("paramtype",Paramtype);
                //System.out.println(path+" Ulisses");

                /**/
                //obtengo la url y la div por / tomo solo el local host la tercera posicion
                // strURL = request.getRequestURL().toString().split("/");
                /**/
                /**
                 * **************Inicia Reportes Excell Masivo Trimestre y
                 * Accion*****
                 */
                if (filename.equals("reporteMasivo" + File.separatorChar + "rptAvanceAccionMasivo.jasper") && reporttype.equals("xls")) {
                    filename = "reporteMasivo" + File.separatorChar + "rptAvanceAccionMasivoExcell.jasper";
                }
                if (filename.equals("MasivoTrimestre" + File.separatorChar + "rptAvancePoaTrimestre.jasper") && reporttype.equals("xls")) {
                    filename = "MasivoTrimestre" + File.separatorChar + "rptAvancePoaTrimestreExcell.jasper";
                }
                /**
                 * **************Termina Reportes Excell Masivo Trimestre y
                 * Accion*****
                 */
                /**
                 * ******Reportes para
                 * Transparencia*****************************
                 */
                if (Utilerias.existeParametro("opcionReporte", request)) {
                    strTransparencia = (request.getParameter("opcionReporte"));
                }
                if (strTransparencia.equals("Transparencia")) {
                    String strMilespesos = new String();
                    String strTipoAnual = "";
                    String strTipoConcentrado = "";
                    if (Utilerias.existeParametro("Miles", request)) {
                        jasperParameter.put("MILESPESOS", 'S');
                    }
                    path += "ReportesTransparencia" + File.separatorChar;

                    if (filename.equals("rptClasificacionEconomicaTipoGasto.jasper")) {
                    }

                    if (Utilerias.existeParametro("chkTipoConcentrado", request)) {
                        strTipoConcentrado = (request.getParameter("chkTipoConcentrado"));
                    }

                    if (filename.equals("Calendario")) {
                        if (Utilerias.existeParametro("chkTipoanual", request)) {
                            strTipoAnual = (request.getParameter("chkTipoanual"));
                        }

                        if (strTipoAnual.equals("1")) {
                            filename = "calendarioPresupuestoEgresosCapituloConcepto.jasper";
                            query = "";
                            query += " VPAM.PARTIDA BETWEEN '" + partidaIni + "' AND '" + partidaFin + "' ";
                            if (ramoInList.length() > 1) {
                                query += " AND VPAM.RAMO IN(" + ramoInList + ")  ";
                            }

                            if (!programaI.equals("-1") && !programaF.equals("-1")) {
                                query += " AND VPAM.PRG BETWEEN '" + programaI + "' AND '" + programaF + "'";
                            }

                            if (!deptoIni.equals("-1") && !deptoFin.equals("-1")) {
                                query += " AND VPAM.DEPTO BETWEEN '" + deptoIni + "' AND '" + deptoFin + "' ";
                            }

                        } else if (strTipoAnual.equals("2")) {
                            filename = "calendarioPresupuestoEgresosCapituloConceptoAnual.jasper";
                            query = "";
                            query += " PPTO.PARTIDA BETWEEN '" + partidaIni + "' AND '" + partidaFin + "' ";
                            if (ramoInList.length() > 1) {
                                query += " AND PPTO.RAMO IN(" + ramoInList + ")  ";
                            }

                            if (!programaI.equals("-1") && !programaF.equals("-1")) {
                                query += " AND PPTO.PRG BETWEEN '" + programaI + "' AND '" + programaF + "'";
                            }

                            if (!deptoIni.equals("-1") && !deptoFin.equals("-1")) {
                                query += " AND PPTO.DEPTO BETWEEN '" + deptoIni + "' AND '" + deptoFin + "' ";
                            }
                        }

                    }//reportes calendarizado

                    //Inicia Clasificacion por objeto del gasto
                    if (filename.equals("objetoGasto")) {

                        if (strTipoConcentrado.equals("D")) {
                            filename = "rptPresupuestoEgresosporClasificacionporObjetodelGastoDet.jasper";
                        } else if (strTipoConcentrado.equals("C")) {
                            filename = "rptPresupuestoEgresosporClasificacionporObjetodelGastoConcentrado.jasper";
                        }
                        query = "";
                        query += " PPTO.PARTIDA BETWEEN '" + partidaIni + "' AND '" + partidaFin + "' ";
                        if (ramoInList.length() > 1) {
                            query += " AND PPTO.RAMO IN(" + ramoInList + ")  ";
                        }

                        if (!programaI.equals("-1") && !programaF.equals("-1")) {
                            query += " AND PPTO.PRG BETWEEN '" + programaI + "' AND '" + programaF + "'";
                        }

                        if (!deptoIni.equals("-1") && !deptoFin.equals("-1")) {
                            query += " AND PPTO.DEPTO BETWEEN '" + deptoIni + "' AND '" + deptoFin + "' ";
                        }

                    }//reportes calsificacion objeto del gasto

                    //Inician reportes por Clasificacion Funcional del Gasto
                    if (filename.equals("ClasificacionFuncional")) {
                        String strFinalidad = "";
                        String strFinalidad2 = "";
                        String strFuncion = "";
                        String strFuncion2 = "";
                        String strSubfuncion = "";
                        String strSubfuncion2 = "";
                        if (Utilerias.existeParametro("selFinalidad1", request)) {
                            strFinalidad = (request.getParameter("selFinalidad1"));
                        }
                        if (Utilerias.existeParametro("selFinalidad2", request)) {
                            strFinalidad2 = (request.getParameter("selFinalidad2"));
                        }
                        if (Utilerias.existeParametro("selFuncion1", request)) {
                            strFuncion = (request.getParameter("selFuncion1"));
                        }

                        if (Utilerias.existeParametro("selFuncion2", request)) {
                            strFuncion2 = (request.getParameter("selFuncion2"));
                        }
                        if (Utilerias.existeParametro("selSubFuncion1", request)) {
                            strSubfuncion = (request.getParameter("selSubFuncion1"));
                        }
                        if (Utilerias.existeParametro("selSubFuncion2", request)) {
                            strSubfuncion2 = (request.getParameter("selSubFuncion2"));
                        }

                        if (strTipoConcentrado.equals("D")) {
                            filename = "PresupEgreAprobporClasifFuncGastoDet.jasper";
                        } else if (strTipoConcentrado.equals("C")) {
                            filename = "PresupEgreAprobporClasifFuncGastoConcentrado.jasper";
                        }
                        query = " P.FINALIDAD BETWEEN " + strFinalidad + " AND " + strFinalidad2;
                        if (!strFuncion.equals("-1") && !strFuncion2.equals("-1")) {
                            query += " AND P.FUNCION BETWEEN " + strFuncion + " AND " + strFuncion2;
                        }

                        if (!strSubfuncion.equals("-1") && !strSubfuncion2.equals("-1")) {
                            query += " AND P.SUBFUNCION BETWEEN " + strSubfuncion + " AND " + strSubfuncion2;
                        }

                    }//reportes por clasificacion funcional

                    //Inician reportes por Clasificacion Administrativa
                    if (filename.equals("ClasifAdmin")) {
                        if (tipoDependencia.equals("SPPD")) {
                            /*Reportes para la bd Central */
                            String strTEI = "";
                            String strTEF = "";

                            if (Utilerias.existeParametro("selTEI", request)) {
                                strTEI = (request.getParameter("selTEI"));
                            }
                            if (Utilerias.existeParametro("selTEF", request)) {
                                strTEF = (request.getParameter("selTEF"));
                            }

                            if (strTipoConcentrado.equals("D")) {
                                filename = "PresupEgreAprobporClasifAdmiDet.jasper";
                            } else if (strTipoConcentrado.equals("C")) {
                                filename = "PresupEgreAprobporClasifAdmiConcentrado.jasper";
                            }
                            query = "";
                            query = " D.SECTOR_CONAC||D.SECTOR_ECONOMICO||D.SECTOR_TIPO||D.SUBSECTOR||D.TIPO_ENTE_PUBLICO||D.ENTE_PUBLICO BETWEEN '" + strTEI + "' AND '" + strTEF + "'";
                        } else if (tipoDependencia.equals("PROGD")) {
                            /*Reportes para la bd paraestatal*/
                            if (strTipoConcentrado.equals("D")) {
                                filename = "PresupEgreAprobporClasifAdmiPEDet.jasper";
                            } else if (strTipoConcentrado.equals("C")) {
                                filename = "PresupEgreAprobporClasifAdmiPEConcentrado.jasper";
                            }
                            query = "";
                            query += " P.PARTIDA BETWEEN '" + partidaIni + "' AND '" + partidaFin + "' ";
                            if (ramoInList.length() > 1) {
                                query += " AND P.RAMO IN(" + ramoInList + ")  ";
                            }

                            if (!programaI.equals("-1") && !programaF.equals("-1")) {
                                query += " AND P.PRG BETWEEN '" + programaI + "' AND '" + programaF + "'";
                            }

                            if (!deptoIni.equals("-1") && !deptoFin.equals("-1")) {
                                query += " AND P.DEPTO BETWEEN '" + deptoIni + "' AND '" + deptoFin + "' ";
                            }

                        }

                    }//termina reportes por clasificacion administrativa

                }//reportes transparencia
                /**
                 * PARA REPORTE BITACORA DE FIRMAS*
                 */
                if (filename.length() >= 20) {
                    if ((filename.substring(0, 20).equals("BitacoraAutorizacion"))) {
                        rptPath += "BitacoraAutorizacion" + File.separatorChar;
                    }
                }

                /**
                 * **********Reportes para Transparencia***********************
                 */
                //Inicia Reporte pendientes por dependencia
                if (filename.contains("repPendientesDependencia")) {
                    query = "";
                    //rptPath += File.separatorChar + "repPendientesDependencia" + File.separatorChar;
                    if (OpcionRepDep.equals("1")) {
                        query = " R.RAMO IN(" + ramoInList + ") ";
                    } else if (OpcionRepDep.equals("2")) {
                        if (esNormativoRepPend == 0) {
                            query = " R.RAMO IN(" + ramoSession + ") AND ";
                        } /*else {
                            query = " R.RAMO IN(" + ramoInList + ") AND ";
                        }*/
                        query += " "
                                /*Ulisses 15-Jun-17 Comenté parte del query*/
                                /*+ "M.OFICIO IN( SELECT MIN(MOV.OFICIO) "
                                + " FROM "
                                + " POA.FLUJO_AUTORIZACION FA, "
                                + " POA.MOVOFICIOS MOV, "
                                + " POA.FLUJO_FIRMAS F,"
                                + " POA.BITMOVTOS B, "
                                + " POA.ESTATUS_MOV E, "
                                + " DGI.PARAMETROS P "
                                + " WHERE "
                                + " MOV.TIPOMOV=FA.TIPOMOV AND "
                                + " FA.TIPO_USR=F.TIPO_USR AND "
                                + " F.ORDEN=FA.ORDEN AND "
                                + " FA.TIPO_USR='" + tipoDependencia + "' AND "
                                + " MOV.OFICIO=B.OFICIO(+) AND "
                                + " E.ESTATUS=FA.ESTATUS AND "
                                + " FA.ORDEN NOT IN( SELECT FA1.ORDEN "
                                + " FROM "
                                + " POA.FLUJO_AUTORIZACION FA1, "
                                + " POA.MOVOFICIOS MOV1, "
                                + " POA.FLUJO_FIRMAS F1, "
                                + " POA.BITMOVTOS B1, "
                                + " POA.ESTATUS_MOV E1 "
                                + " WHERE "
                                + " MOV1.OFICIO=MOV.OFICIO AND "
                                + " MOV1.TIPOMOV=FA1.TIPOMOV AND "
                                + " FA1.TIPO_USR=F1.TIPO_USR AND "
                                + " F1.ORDEN=FA1.ORDEN AND "
                                + " FA1.TIPO_USR='" + tipoDependencia + "' AND "
                                + " MOV1.OFICIO=B1.OFICIO(+) AND "
                                + " E1.ESTATUS=FA1.ESTATUS AND "
                                + " F1.APP_LOGIN=B1.APP_LOGIN)  "
                                + " GROUP BY "
                                + " MOV.OFICIO) AND "
                                */
                                + " E.ORDEN in(" + NivelesInList + ") "
                                + " AND E.ORDEN<=(select MAX(E.ORDEN) from "
                                + " poa.estatus_mov E, "
                                + " poa.flujo_autorizacion FA, "
                                + " poa.movoficios MOV "
                                + " where "
                                + " MOV.TIPOMOV=FA.TIPOMOV AND "
                                + " FA.TIPO_USR='" + tipoDependencia + "' AND "
                                + " E.ESTATUS=FA.ESTATUS and "
                                + "        MOV.OFICIO=M.OFICIO)";
                    }
                }
                    //termina reporte pendientes por dependencia

                //Inician Nuevos reportes recalendarizaciones Concentrados por ramo/ramo-prg/ramo-prg-depto
                if (filename.equals("ConsolidadoControlRecalendarizaciones" + File.separatorChar + "rptConsolidadoControlRecalConcentrado.jasper")) {
                    filename = "ConsolidadoControlRecalendarizaciones" + File.separatorChar + opCon;

                    if (filename.equals("ConsolidadoControlRecalendarizaciones" + File.separatorChar + "rptConsolidadoControlRecalConcentradoRamoPrg.jasper")) {
                        query = " AND TR.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' ";
                    }
                }
                //terminan Nuevos reportes recalendarizaciones Concentrados por ramo/ramo-prg/ramo-prg-depto

                if (Utilerias.existeParametro("RepDefPlantilla", request)) {
                    RepDefPlantilla = request.getParameter("RepDefPlantilla");
                    if (!RepDefPlantilla.equals(null)) {
                        query = RepDefPlantilla;
                    }
                }
                if (filename.equals("repAmpTrans" + File.separatorChar + "rptAmpliacionesDetallado.jasper")
                        || filename.equals("repAmpTrans" + File.separatorChar + "rptAmpliacionesConcentrado.jasper")
                        || filename.equals("repAmpTrans" + File.separatorChar + "rptTransferenciaConcentrado.jasper")
                        || filename.equals("repAmpTrans" + File.separatorChar + "rptTransferenciasDetalladoCC.jasper")
                        || filename.equals("repAmpTrans" + File.separatorChar + "rptTransferenciasDetallado.jasper")) {
                    banderaPage = false;
                }
                
                if(filename.equals("rptProyectoActividadRamo.jasper")){
                  query =" AND RAM.RAMO IN("+ramoInList+") ORDER BY  RAMPRG.RAMO,PROY.TIPO_PROY,PROY.PROY";
                }else if(filename.equals("rptProyectoActividadRamoPrograma.jasper")){
                  query =" AND RAM.RAMO IN("+ramoInList+") ORDER BY RAMPRG.RAMO,RAMPRG.PRG,PROY.TIPO_PROY,PROY.PROY";
                }
                
                if(isConsultaxRangoFolios.equals("S")){
                  query =" AND TR.OFICIO IN("+folioInList+")";
                }
                if(filename.equals("EstadisticaFirmasFolio"+File.separator+"rptEstadisticaFirmasFolio.jasper")){
                 query=" AND AFECTADOS.RAMO IN("+ramoInList+") AND TRUNC(MOV0.FECPPTO) BETWEEN TO_DATE('"+fecI+"','DD/MM/YYYY') AND TO_DATE('"+fecF+"','DD/MM/YYYY') "+
                       " AND MOV0.STATUS IN("+NivelesInList+")    ";
                         banderaPage=false;
                         if(tipoDependencia.equals("SPPD")){
                         filename="EstadisticaFirmasFolio"+File.separator+"rptEstadisticaFirmasFolioCentral.jasper";
                         }else if(tipoDependencia.equals("PROGD")){
                         filename="EstadisticaFirmasFolio"+File.separator+"rptEstadisticaFirmasFolio.jasper";
                         }
                }
                

                jasperParameter.put("YEAR", intYear);
                jasperParameter.put("YEAR_SIG", yearSig);
                jasperParameter.put("PAGINA", pagina);
                jasperParameter.put("YEAR_CAPTURA", yearCaptura);
                jasperParameter.put("IN_CAPSULE", inCapsule);
                jasperParameter.put("RAMO_INI", Ramo);
                jasperParameter.put("RAMO", Ramo);
                jasperParameter.put("ID_CARATULA", caratula);
                jasperParameter.put("PROGRAMA", Programa);
                jasperParameter.put("PROGRAMA_INI", Programa);
                jasperParameter.put("RAMO_IN_LIST", ramoInList);
                jasperParameter.put("GRUPOPOB_IN_LIST", grupoPobInList);                
                jasperParameter.put("PONDERADOPRG_IN_LIST", ponderadoProgramaInList); 
                jasperParameter.put("PONDERADOMETA_IN_LIST", ponderadoMetaInList);                              
                jasperParameter.put("PARTIDAINI", partidaIni);
                jasperParameter.put("PARTIDAFIN", partidaFin);
                jasperParameter.put("imgDir", path);
                jasperParameter.put("IMG_DIR", imgpath);
                jasperParameter.put("RPT_DIR", rptPath);
                jasperParameter.put("RAMOI", ramoI);
                jasperParameter.put("RAMOF", ramoF);
                jasperParameter.put("QUERY", query);
                jasperParameter.put("QUERY_P", queryPartidas);
                jasperParameter.put("PERIODO", periodo);
                jasperParameter.put("PERIODO_INI", periodoIni);
                jasperParameter.put("PERIODO_FIN", periodoFin);
                jasperParameter.put("MES", mes);
                jasperParameter.put("VISIBLE", "S");
                jasperParameter.put("PRG_INI", programaI);
                jasperParameter.put("PRG_FIN", programaF);
                jasperParameter.put("USUARIO", usuario);
                jasperParameter.put("COSTO", costo);
                jasperParameter.put("FIRMA", firmas);
                jasperParameter.put("OFICIO", lOficio);
                jasperParameter.put("TIPO", tipoMovimiento);
                jasperParameter.put("TIPOFICIO", sTipoOficio);
                jasperParameter.put("ESTATUS", estatusMovimientoId);
                jasperParameter.put("CODIGOCOMPLETO", codCompleto);
                jasperParameter.put("TIPOUSR", tipoDependencia);
                jasperParameter.put("OFICIO_LIST", oficioList);
                jasperParameter.put("DOCVALIDO", strDocValido);
                jasperParameter.put("MODACT", pptoModAct);

                conectaBD = resultSQL.getConectaBD();
                JasperPrint jasperPrint = JasperFillManager.fillReport(path + filename, jasperParameter,
                        conectaBD.getConConexion());
                ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                JRXlsxExporter export = null;
                if (banderaPage) {
                    JRTemplatePrintText template;
                    paginas = jasperPrint.getPages();
                    for (Iterator<JRPrintPage> it = paginas.iterator(); it.hasNext();) {
                        JRPrintPage pag = it.next();
                        if (pag.getElements().size() <= 24) {
                            if (filename.equals("rptProgramadoCongelado" + File.separatorChar + "RptPOAweb.jasper")) {
                                for (JRPrintElement elemento : pag.getElements()) {
                                    if (elemento instanceof JRTemplatePrintText) {
                                        template = (JRTemplatePrintText) elemento;
                                        if (!template.getText().equals("TRUE")) {
                                            it.remove();
                                            break;
                                        } else {
                                            countPages++;
                                            template = (JRTemplatePrintText) pag.getElements().get(pag.getElements().size() - 3);
                                            if (!filename.equals("MasivoTrimestre" + File.separatorChar + "rptAvancePoaTrimestre.jasper")
                                                    && !filename.equals("avanceAnual" + File.separatorChar + "rptAvanceAnual.jasper")) {
                                                template.setText("Página " + countPages + " de ");
                                            }
                                        }
                                    } else {
                                        it.remove();
                                        break;
                                    }
                                }
                            } else {
                                it.remove();
                            }
                        } else {
                            countPages++;
                            template = (JRTemplatePrintText) pag.getElements().get(pag.getElements().size() - 3);
                            if (!filename.equals("MasivoTrimestre" + File.separatorChar + "rptAvancePoaTrimestre.jasper")
                                    && !filename.equals("avanceAnual" + File.separatorChar + "rptAvanceAnual.jasper")) {
                                template.setText("Página " + countPages + " de ");
                            }
                        }
                    }
                    for (Iterator<JRPrintPage> it = paginas.iterator(); it.hasNext();) {
                        JRPrintPage pag = it.next();
                        template = (JRTemplatePrintText) pag.getElements().get(pag.getElements().size() - 4);
                        if (!filename.equals("MasivoTrimestre" + File.separatorChar + "rptAvancePoaTrimestre.jasper")
                                && !filename.equals("avanceAnual" + File.separatorChar + "rptAvanceAnual.jasper")) {
                            template.setText(" " + countPages + " ");
                        }
                    }
                }
                //Report  generated in - PDF/
                if ("pdf".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");

                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } //Report generated in - rtf/
                else if ("rtf".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/rtf");
                    response.setHeader("Content-Disposition", "inline; filename=\"report.rtf\"");
                    exporter = new JRRtfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } //Report generated in - html/
                else if ("html".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/html");
                    response.setHeader("Content-Disposition", "inline; filename=\"report.html\"");
                    exporter = new JRHtmlExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
                } //Report  generated in - xls/
                else if ("xls".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/xlsx");
                    response.setHeader("Content-Disposition", "inline; filename=\"report.xlsx\"");
                    //exporter = new JRXlsExporter();
                    export = new JRXlsxExporter();
                    export.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                    export.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, ouputStream);
                } //Report generated in - csv/
                else if ("csv".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/csv");
                    response.setHeader("Content-Disposition", "inline; filename=\"report.csv\"");
                    exporter = new JRCsvExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                if (!"xls".equalsIgnoreCase(reporttype)) {
                    exporter.exportReport();
                } else {
                    export.exportReport();
                }
            } catch (JRException e) {
                throw new ServletException(e);
            } catch (Exception e) {
                throw new ServletException(e);
            } finally {
                resultSQL.desconectaDblink("SPP");
                resultSQL.desconectaDblink("ING");
                resultSQL.desconectaDblink("APPSWEB");
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                conectaBD.desconecta();
            }
        %>
    </body>
</html>
