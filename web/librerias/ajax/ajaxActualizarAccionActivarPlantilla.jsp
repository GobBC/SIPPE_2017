<%-- 
    Document   : ajaxActualizarAccionAcctivarPlantilla
    Created on : May 14, 2015, 3:53:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.PlantillaBean"%>
<%@page import="gob.gbc.aplicacion.MetaAccionPlantillaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    String ramoId = "";
    String deptoDelete = new String();
    String deptoArray[] = new String[0];
    String tipoDependencia = new String();
    String regMetasAccionesPlantillas = new String();
    String strResult = new String();
    boolean blResultado = true;
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    MetaAccionPlantillaBean metaAccionPlantillaBean = null;
    String arrRenglones[] = new String[0];
    int year = 0;

    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        metaAccionPlantillaBean = new MetaAccionPlantillaBean(tipoDependencia);
        metaAccionPlantillaBean.setStrServer(request.getHeader("host"));
        metaAccionPlantillaBean.setStrUbicacion(getServletContext().getRealPath(""));

        metaAccionPlantillaBean.resultSQLConecta(tipoDependencia);

        if (session.getAttribute("year") != null
                && !session.getAttribute("year").equals("")) {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }

        if (request.getParameter("deptoDelete") != null && !request.getParameter("deptoDelete").equals("")) {
            deptoDelete = (String) request.getParameter("deptoDelete");
            if (deptoDelete.split(",").length > 0) {
                deptoArray = new String[deptoDelete.split(",").length];
                deptoArray = deptoDelete.split(",");
            }
        }

        if (request.getParameter("regMetasAccionesPlantillas") != null && !request.getParameter("regMetasAccionesPlantillas").equals("")) {
            regMetasAccionesPlantillas = (String) request.getParameter("regMetasAccionesPlantillas");
            arrRenglones = new String[regMetasAccionesPlantillas.split("\\|").length];
            arrRenglones = regMetasAccionesPlantillas.split("\\|");
        }
        if (deptoArray.length > 0) {
            blResultado = metaAccionPlantillaBean.deleteAsigandosPlantilla(year, ramoId, deptoArray);
        }


        for (int i = 0; i < arrRenglones.length; i++) {
            String renglon = arrRenglones[i].trim();
            String arrColumnas[];
            arrColumnas = new String[renglon.split(",").length];
            arrColumnas = renglon.split(",");
            String plantilla = arrColumnas[0].trim();
            int metaId = Integer.parseInt(arrColumnas[1].trim());
            int accionId = Integer.parseInt(arrColumnas[2].trim());
            String fuenteId = (String) "";
            String fondoId = (String) "";
            String recursoId = (String) "";
            int estadoAnterior = Integer.parseInt(arrColumnas[6].trim());
            String tipoGasto = (String) arrColumnas[7].trim();
            String departamentoId = (String) arrColumnas[8].trim();
            String programaId = (String) arrColumnas[9].trim();

            if (estadoAnterior > 0) {
                if (!plantilla.equals("on")) {
                    // Delete
                    blResultado = metaAccionPlantillaBean.deleteMetaAccionPlantilla(ramoId, programaId, departamentoId, metaId, accionId, fuenteId, fondoId, recursoId, year);
                }
            } else {
                if (plantilla.equals("on")) {
                    //insert
                    blResultado = metaAccionPlantillaBean.insertMetaAccionPlantilla(ramoId, programaId, departamentoId, metaId, accionId, fuenteId, fondoId, recursoId, tipoGasto, year);
                }
            }

        }
        if (blResultado) {
            strResult += "1";
        }
    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (metaAccionPlantillaBean != null) {
            metaAccionPlantillaBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
