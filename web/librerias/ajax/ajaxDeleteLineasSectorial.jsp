<%-- 
    Document   : ajaxDeleteLineasSectorial
    Created on : Jun 24, 2015, 4:11:42 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.LineaBean"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
    List<LineaSectorial> lineaSectorialListTemp = new ArrayList<LineaSectorial>();

    LineaBean lineaBean = null;

    String strResultado = new String();
    String lineaId = new String();
    String tipoDependencia = new String();

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (request.getParameter("lineaId") != null
                && !request.getParameter("lineaId").equals("")) {
            lineaId = (String) request.getParameter("lineaId");
        }

        if (session.getAttribute("linea-proyecto2") != null) {
            lineaSectorialList = (List<LineaSectorial>) session.getAttribute("linea-proyecto2");
        }
        
        lineaBean = new LineaBean(tipoDependencia);
        lineaBean.setStrServer(request.getHeader("host"));
        lineaBean.setStrUbicacion(getServletContext().getRealPath(""));
        lineaBean.resultSQLConecta(tipoDependencia);

        /* lineaSectorial = new LineaSectorial();
         lineaSectorial.setLineaId("0.0.0.0");
         lineaSectorialList.add(lineaSectorial);
         */
        if (lineaId.equals("-1")) {
            lineaSectorialList.remove(lineaSectorialList.size() - 1);
        } else {

            boolean encontrado = false;

            for (LineaSectorial lineaTemp : lineaSectorialList) {
                if ((lineaTemp.getLineaId().equals(lineaId))) {
                    encontrado = true;
                }
            }

            if (!encontrado) {
                lineaSectorialList.remove(lineaSectorialList.size() - 1);
            }
        }

        for (LineaSectorial lineaTemp : lineaSectorialList) {
            if ((!lineaTemp.getLineaId().equals(lineaId))) {
                lineaSectorialListTemp.add(lineaTemp);
            }
        }



        for (LineaSectorial linea : lineaSectorialList) {
        }

   
        session.setAttribute("linea-proyecto2", lineaSectorialListTemp);
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
