<%-- 
    Document   : ajaxGetLineasSectorial
    Created on : Apr 1, 2015, 4:11:42 PM
    Author     : ugarcia
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
    List<LineaSectorial> lineaPrograma = new ArrayList<LineaSectorial>();
    LineaSectorial lineaSectorial = new LineaSectorial();
    LineaBean lineaBean = null;
    String strResultado = new String();
    String selected = new String();
    String arregloLineas[];
    String arrBorrarLineasSectoriales[];
    String idsBorrarLineasSectoriales = new String();
    String ramoId = new String();
    String programaId = new String();
    String tipoProy = new String();
    String estrategiaDescr = new String();
    int proyectoId = 0;
    int opcionSectorial = 0;
    int year = 0;
    String idLineas = new String();
    String estrategia = new String();
    String ultimaLineaSectorial = new String();
    String tipoDependencia = new String();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        
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
        if (request.getParameter("opcionPed") != null
                && !request.getParameter("opcionPed").equals("")) {
            opcionSectorial = Integer.parseInt((String) request.getParameter("opcionPed"));
        }
        if (request.getParameter("estrategia") != null
                && !request.getParameter("estrategia").equals("")) {
            estrategia = (String) request.getParameter("estrategia");
        }
        if (request.getParameter("estrategiaDescr") != null
                && !request.getParameter("estrategiaDescr").equals("")) {
            estrategiaDescr = (String) request.getParameter("estrategiaDescr");
        }
        if (request.getParameter("ultimaLineaSectorial") != null
                && !request.getParameter("ultimaLineaSectorial").equals("")) {
            ultimaLineaSectorial = (String) request.getParameter("ultimaLineaSectorial");
        }
        if (request.getParameter("arrBorrarLineasSectoriales") != null
                && !request.getParameter("arrBorrarLineasSectoriales").equals("")) {
            idsBorrarLineasSectoriales = (String) request.getParameter("arrBorrarLineasSectoriales");
        }
        if (request.getParameter("arregloLinea") != null
                && !request.getParameter("arregloLinea").equals("")) {
            idLineas = (String) request.getParameter("arregloLinea");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        lineaBean = new LineaBean(tipoDependencia);
        lineaBean.setStrServer(request.getHeader("host"));
        lineaBean.setStrUbicacion(getServletContext().getRealPath(""));
        lineaBean.resultSQLConecta(tipoDependencia);
        if (opcionSectorial == 1) {
            lineaSectorialList = lineaBean.getLineaSectorialByEstrategia(ramoId, programaId, proyectoId, year, estrategia, tipoProy);
            lineaPrograma = lineaBean.getLineaSectorialByYearEstrategia(year, estrategia);
        } else {
            if (session.getAttribute("linea-programa2") != null) {
                lineaPrograma = (List<LineaSectorial>) session.getAttribute("linea-programa2");
            }
            if (session.getAttribute("linea-proyecto2") != null) {
                lineaSectorialList = (List<LineaSectorial>) session.getAttribute("linea-proyecto2");
            }
            if (opcionSectorial == 2) {
                lineaSectorial = new LineaSectorial();
                lineaSectorial.setLineaId("0.0.0.0");
                lineaSectorialList.add(lineaSectorial);
            }
            if (opcionSectorial == 3) {
                arregloLineas = new String[idLineas.split(",").length];
                arregloLineas = idLineas.split(",");
                arrBorrarLineasSectoriales = new String[idsBorrarLineasSectoriales.split(",").length];
                arrBorrarLineasSectoriales = idsBorrarLineasSectoriales.split(",");
                lineaSectorialList = lineaBean.saveLineaSectorial(year, arregloLineas, ramoId, programaId, proyectoId, estrategia, ultimaLineaSectorial, arrBorrarLineasSectoriales, tipoProy);
            }
        }
        strResultado = "<table>";
        for (LineaSectorial linea : lineaSectorialList) {
            strResultado += "<tr> <td>";
            strResultado += "<select class='selLinea' onchange='cambioLineaSectorial()'>";
            strResultado += "   <option " + selected + " value='-1'>";
            strResultado += "-- Seleccione una linea Sectorial --";
            strResultado += "   </option>";
            for (LineaSectorial lineaP : lineaPrograma) {
                if (linea.getLineaId().equals(lineaP.getLineaId())) {
                    selected = "selected";
                }
                if (lineaP.getLineaId() != null) {
                    strResultado += "   <option " + selected + " value='" + lineaP.getLineaId() + "'>";
                    strResultado += lineaP.getLineaId() + "-" + lineaP.getLineaSectorial();
                    strResultado += "   </option>";
                }
                selected = new String();
            }
            strResultado += "<select>";
            strResultado += "</td> ";
            strResultado += "<td> ";

            if (linea.getLineaId().equals("0.0.0.0")) {
                strResultado += "<input  id='prevSectorial' type='hidden' value='' />";
            } else {
                strResultado += "<input  id='prevSectorial" + linea.getLineaId() + "' type='hidden' value='' />";
            }

            strResultado += "</td> ";
            strResultado += "</tr>";
        }
        strResultado += "</table>";
        strResultado += "<script type=\"text/javascript\">";
        strResultado += "  $(\"#sectorial-lineas tr\").click(function(){";
        strResultado += "  $(this).addClass('selected-linea-sectorial').siblings().removeClass('selected-linea-sectorial');";
        strResultado += "});";
        strResultado += "</script>";
        session.setAttribute("linea-programa2", lineaPrograma);
        session.setAttribute("linea-proyecto2", lineaSectorialList);
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
