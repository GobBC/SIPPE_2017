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
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.JSONObject"%>

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


    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    String contenidoArch = "";
    Archivo archivo;
    JSONObject json = new JSONObject();
    json.put("resultado","-1");
    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        estructuraPresupuestalBean = new EstructuraPresupuestalBean(tipoDependencia);
        estructuraPresupuestalBean.setStrServer(request.getHeader("host"));
        estructuraPresupuestalBean.setStrUbicacion(getServletContext().getRealPath(""));
        estructuraPresupuestalBean.resultSQLConecta(tipoDependencia);

        if (request.getParameter("selRamo") != null && !request.getParameter("selRamo").equals("")) {
            ramoId = (String) request.getParameter("selRamo");
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (!ramoId.equals("-1")) {
            cierre = estructuraPresupuestalBean.validaRamoCierre(ramoId, year);
            cierre = true;
            if (cierre) {
                String sUsuario= "";
                if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "")
                    sUsuario = (String)session.getAttribute("strUsuario");
                pathLogico = application.getRealPath("/") + File.separatorChar + "temp" + File.separatorChar + "TempEstructuraPresupuestal" + numTemp;
                File theDir = new File(pathLogico);

                if (!theDir.exists()) {
                    boolean result = false;
                    theDir.mkdir();
                    result = true;
                }
                ResultSQL resultSQL = new ResultSQL(tipoDependencia);
                resultSQL.setStrServer(((String) request.getHeader("Host")));
                resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
                resultSQL.resultSQLConecta(tipoDependencia);
                
                FileOutputStream fos = new FileOutputStream(pathLogico + File.separatorChar + "EstructuraPresupuestal" + numTemp + ".zip");
                ZipOutputStream zos = new ZipOutputStream(fos);
                String sLista[] = request.getParameterValues("chkMovimientos");
                ArrayList<String> sOficios = new ArrayList<String>();
                ArrayList<String> sTipoOficio = new ArrayList<String>();
                String sTemp = "";
                Long lOficio;
                String sTipo,sIdTraspaso;                  
                sIdTraspaso = sUsuario + "" + System.currentTimeMillis();
                for (int iRow = 0; iRow < sLista.length; iRow++) {
                    sTemp = sLista[iRow];
                    sTipo= sTemp.substring(0, sTemp.indexOf("-"));
                    lOficio = Long.parseLong(sTemp.substring(sTemp.indexOf("-") + 1));
                    resultSQL.insertaTraspaso(String.valueOf(year), ramoId, lOficio, sTipo, sIdTraspaso, sUsuario);
                }                
                pathLogico = application.getRealPath("/") + File.separatorChar + "temp" + File.separatorChar + 
                resultSQL.extraeTablas(pathLogico, String.valueOf(year), ramoId,  sIdTraspaso);                
                strResultado = "1";
               
                json.put("resultado","1");
                json.put("ruta","EstructuraPresupuestal"+sIdTraspaso);
                strResultado = json.toString();
            } else {
                json.put("resultado","-2");                
                strResultado = json.toString();
            }
        } else {
            json.put("resultado","-3");                            
            strResultado = json.toString();
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