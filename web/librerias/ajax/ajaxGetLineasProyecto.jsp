<%-- 
    Document   : ajaxGetLineasProyecto
    Created on : Mar 30, 2015, 8:47:24 AM
    Author     : ugarcia
--%>

<%@page import="java.lang.String"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.LineaBean"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String ramoId = new String();
    String programaId = new String();
    int proyectoId = 0;
    int opcionPed = 0;
    int year = 0;
    String idLineas = new String();
    String arregloLinea[] = null;
    String selected = new String();
    String tipoProy = new String();
    LineaBean lineaBean = null;
    boolean mostrarBTNLineaSectorial = false;
    List<Linea> lineaProyecto = new ArrayList<Linea>();
    List<Linea> lineaPrograma = new ArrayList<Linea>();
    String arrBorrarLineasPed[];
    String tipoDependencia = new String();
    String idsBorrarLineasPed = new String();
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
        if (request.getParameter("opcionPed") != null
                && !request.getParameter("opcionPed").equals("")) {
            opcionPed = Integer.parseInt((String) request.getParameter("opcionPed"));
        }
        if (request.getParameter("arregloLinea") != null
                && !request.getParameter("arregloLinea").equals("")) {
            idLineas = (String) request.getParameter("arregloLinea");
        }
        if (request.getParameter("idsBorrarLineasPed") != null
                && !request.getParameter("idsBorrarLineasPed").equals("")) {
            idsBorrarLineasPed = (String) request.getParameter("idsBorrarLineasPed");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        lineaBean = new LineaBean(tipoDependencia);
        lineaBean.setStrServer(request.getHeader("host"));
        lineaBean.setStrUbicacion(getServletContext().getRealPath(""));
        lineaBean.resultSQLConecta(tipoDependencia);

        if (opcionPed == 1) {
            lineaProyecto = lineaBean.getLineaByTipoProyecto(ramoId, programaId, proyectoId, year, tipoProy);
            lineaPrograma = lineaBean.getLineaByPrograma(ramoId, programaId, year);
            if (lineaPrograma.size() == 0) {
                lineaPrograma.add(new Linea());
            }
        } else {
            if (session.getAttribute("linea-programa") != null) {
                lineaPrograma = (List<Linea>) session.getAttribute("linea-programa");
            }
            if (session.getAttribute("linea-proyecto") != null) {
                lineaProyecto = (List<Linea>) session.getAttribute("linea-proyecto");
            }
            if (opcionPed == 2) {
                Linea lineaTemp = new Linea();
                lineaTemp.setLineaId("0.0.0.0");
                lineaProyecto.add(lineaTemp);
            }
            if (opcionPed == 3) {
                arregloLinea = new String[idLineas.split(",").length];
                arregloLinea = idLineas.split(",");
                arrBorrarLineasPed = new String[idsBorrarLineasPed.split(",").length];
                arrBorrarLineasPed = idsBorrarLineasPed.split(",");
                lineaProyecto = lineaBean.saveLineasByProyecto(year, arregloLinea, ramoId, programaId, proyectoId, tipoProy, arrBorrarLineasPed);
            }
        }
        strResultado = "<table>";
        for (Linea linea : lineaProyecto) {
            String estrategiaTemp = "";
            String estrategiaDescr = "";
            strResultado += "<tr> <td>";
            strResultado += "<select class='selLinea' onchange=\"cambioLinea()\"     >";
            strResultado += "   <option " + selected + " value='-1'>";
            strResultado += "-- Selecciones una linea PED --";
            strResultado += "   </option>";
            for (Linea lineaP : lineaPrograma) {
                if (linea.getLineaId().equals(lineaP.getLineaId())) {
                    selected = "selected";
                    mostrarBTNLineaSectorial = true;
                    estrategiaTemp = lineaP.getLineaId();
                    estrategiaDescr = lineaP.getLinea();
                }
                if (lineaP.getLineaId() != null) {
                    strResultado += "   <option " + selected + " value='" + lineaP.getLineaId() + "'>";
                    strResultado += lineaP.getLineaId() + "-" + lineaP.getLinea();
                    strResultado += "   </option>";
                }
                selected = new String();
            }

            strResultado += "</select>";
            strResultado += "</td> ";
            strResultado += "<td> ";

            if (estrategiaTemp.equals("")) {
                if (opcionPed == 2) {
                    strResultado += "<button id='btnSectorial" + estrategiaTemp + "' type='button' title='Para asignar líneas sectoriales se requerirá guardar la nueva línea ped' class='lineas-proyecto-sectorial-btn' onclick=\"mostrarLineaSectorial('" + "" + "','" + "" + "')\" disabled >Sectorial</button>";
                    strResultado += "<input  id='btnSectorial" + estrategiaTemp + "' type='hidden' class='lineas-proyecto-sectorial-btn' value='" + "" + "' />";
                }
            } else {
                strResultado += "<button id='btnSectorial" + estrategiaTemp + "' type='button' class='lineas-proyecto-sectorial-btn' onclick=\"mostrarLineaSectorial('" + estrategiaTemp + "','" + estrategiaDescr + "')\" >Sectorial</button>";
                strResultado += "<input  id='btnSectorial" + estrategiaTemp + "' type='hidden' class='lineas-proyecto-sectorial-btn' value='" + estrategiaTemp + "' />";
            }


            strResultado += "</td> ";
            strResultado += "</tr>";

        }
        strResultado += "</table>";
        strResultado += "<script type=\"text/javascript\">";
        strResultado += "  $(\"#ped-lineas tr\").click(function(){";
        strResultado += "  $(this).addClass('selected-linea').siblings().removeClass('selected-linea');";
        strResultado += "});";
        strResultado += "</script>";
        session.setAttribute("linea-programa", lineaPrograma);
        session.setAttribute("linea-proyecto", lineaProyecto);
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
