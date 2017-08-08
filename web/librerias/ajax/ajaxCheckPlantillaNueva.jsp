<%-- 
    Document   : ajaxCheckPlantillaNueva
    Created on : Jun 14, 2016, 4:32:00 PM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.aplicacion.MetaAccionPlantillaBean"%>
<%@page import="gob.gbc.aplicacion.PlantillaBean"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.RevisionCaratula"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.RevisionCaratulaBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    PlantillaBean plantillaBean = null;
    MetaAccionPlantillaBean metaAccionPlantillaBean = null;

    String ramoId = new String();
    String deptoId = new String();
    String strResult = new String();
    String plantilla = new String();
    String programaId = new String();
    String tipoDependencia = new String();

    long selCaratula = -1;

    int year = 0;
    int metaId = 0;
    int accionId = 0;
    int estadoAnterior = 0;

    boolean blResultado = true;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (request.getParameter("year") != null && !request.getParameter("year").equals("")) {
            year = Integer.parseInt(request.getParameter("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = request.getParameter("programaId");
        }
        if (request.getParameter("deptoId") != null && !request.getParameter("deptoId").equals("")) {
            deptoId = request.getParameter("deptoId");
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accionId"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("plantilla") != null && !request.getParameter("plantilla").equals("")) {
            plantilla = request.getParameter("plantilla");
        }
        if (request.getParameter("estadoAnterior") != null && !request.getParameter("estadoAnterior").equals("")) {
            estadoAnterior = Integer.parseInt(request.getParameter("estadoAnterior"));
        }

        plantillaBean = new PlantillaBean(tipoDependencia);
        plantillaBean.setStrServer(request.getHeader("host"));
        plantillaBean.setStrUbicacion(getServletContext().getRealPath(""));
        plantillaBean.resultSQLConecta(tipoDependencia);

        metaAccionPlantillaBean = new MetaAccionPlantillaBean(tipoDependencia);
        metaAccionPlantillaBean.setStrServer(request.getHeader("host"));
        metaAccionPlantillaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaAccionPlantillaBean.resultSQLConecta(tipoDependencia);

        strResult = "1";

        if (estadoAnterior > 0) {
            if (!plantilla.equals("on")) {
                // Delete
                blResultado = metaAccionPlantillaBean.deleteMetaAccionPlantilla(ramoId, programaId, deptoId, metaId, accionId, "", "", "", year);
                if (blResultado) {
                    strResult = "1";
                } else {
                    strResult = "Error al deshabilitar plantilla";
                }
            }
        } else {
            if (plantilla.equals("on")) {
                //insert
                if (plantillaBean.existPlantillaByRamoProgramaDeptoMetaAccionYear(ramoId, programaId, deptoId, metaId, accionId, year)) {
                    strResult = "Ya existe una plantilla habilitada para la unidad ejecutora "+ramoId+"-"+programaId+"-"+deptoId;
                } else {
                    blResultado = metaAccionPlantillaBean.insertMetaAccionPlantilla(ramoId, programaId, deptoId, metaId, accionId, "", "", "", "", year);
                    if (blResultado) {
                        strResult = "1";
                    } else {
                        strResult = "Error al habilitar plantilla";
                    }
                }
            }
        }

    } catch (Exception ex) {
        strResult = "No es posible actualizar la información";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (plantillaBean != null) {
            plantillaBean.resultSQLDesconecta();
        }
        if (metaAccionPlantillaBean != null) {
            metaAccionPlantillaBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
