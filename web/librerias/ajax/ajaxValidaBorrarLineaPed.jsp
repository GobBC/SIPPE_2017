<%-- 
    Document   : ajaxValidaBorrarLineaPed
    Created on : Jun 2, 2015, 3:53:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.LineaBean"%>
<%@page import="gob.gbc.aplicacion.MetaAccionPlantillaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    boolean blResultado = false;
    String strResultado = new String();
    String ramoId = new String();
    String programaId = new String();
    String lineaId = new String();
    String tipoProy = new String();
    String selectNew = new String();
    String tipoDependencia = new String();
    int proyectoId = 0;
    int year = 0;
    LineaBean lineaBean = null;
    List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
    List<Linea> lineaProyecto = new ArrayList<Linea>();
    List<Linea> lineaProyectoTemp = new ArrayList<Linea>();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }

        if (request.getParameter("programaId") != null
                && !request.getParameter("programaId").equals("")) {
            programaId = (String) request.getParameter("programaId");
        }

        if (request.getParameter("proyectoId") != null
                && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt((String) request.getParameter("proyectoId"));
        }
        if (request.getParameter("tipoProy") != null
                && !request.getParameter("tipoProy").equals("")) {
            tipoProy = (String) request.getParameter("tipoProy");
        }
        if (request.getParameter("lineaId") != null
                && !request.getParameter("lineaId").equals("")) {
            lineaId = (String) request.getParameter("lineaId");
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (session.getAttribute("linea-proyecto") != null) {
            lineaProyecto = (List<Linea>) session.getAttribute("linea-proyecto");
        }

        if (request.getParameter("selectNew") != null
                && !request.getParameter("selectNew").equals("")) {
            selectNew = (String) request.getParameter("selectNew");
        }
        lineaBean = new LineaBean(tipoDependencia);
        lineaBean.setStrServer(request.getHeader("host"));
        lineaBean.setStrUbicacion(getServletContext().getRealPath(""));
        lineaBean.resultSQLConecta(tipoDependencia);

        lineaSectorialList = lineaBean.getLineaSectorialByEstrategia(ramoId, programaId, proyectoId, year, lineaId, tipoProy);

        if (lineaSectorialList.size() > 0) {
            blResultado = false;
        } else {

            if (lineaId.equals("-1")) {
                lineaProyecto.remove(lineaProyecto.size() - 1);
            } else {

                boolean encontrado = false;

                for (Linea lineaPed : lineaProyecto) {
                    if ((lineaPed.getLineaId().equals(lineaId))) {
                        encontrado = true;
                    }
                }

                if (!encontrado) {
                    lineaProyecto.remove(lineaProyecto.size() - 1);
                }
            }
            
            for (Linea lineaPed : lineaProyecto) {
                if ((!lineaPed.getLineaId().equals(lineaId))) {
                    lineaProyectoTemp.add(lineaPed);
                }
            }


            session.setAttribute("linea-proyecto", lineaProyectoTemp);
            blResultado = true;
        }

        if (blResultado) {
            strResultado += "1";
        } else {
            strResultado += "-1";
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
        if (lineaBean != null) {
            lineaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
