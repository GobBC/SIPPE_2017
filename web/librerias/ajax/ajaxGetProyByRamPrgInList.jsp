<%-- 
    Document   : ajaxGetProgramasByRamo
    Created on : Mar 20, 2015, 4:08:07 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.aplicacion.ProyectoBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.aplicacion.ProgramaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String strTablaProyectos = new String();
    String ramoInList = new String();
    String programaInList = new String();
    ProyectoBean proyectoBean = null;

    boolean cierre = false;
    ProgramaBean programaBean = null;
    int year = 0;
    int RegTemp = 0;
    String rol = new String();
    String usuario = new String();
    String tipoDependencia = new String();
    int contRegProyectos = 0;
    int contRegSelectProyectos = 0;
    String msjSelectProyectos = "Seleccionados";

    List<Proyecto> proyectoList = new ArrayList<Proyecto>();

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if ((String) session.getAttribute("strRol") != null) {
            rol = (String) session.getAttribute("strRol");
        }

        if (request.getParameter("ramoInList") != null && !request.getParameter("ramoInList").equals("")) {
            ramoInList = (String) request.getParameter("ramoInList");
        }

        if (request.getParameter("programaInList") != null && !request.getParameter("programaInList").equals("")) {
            programaInList = (String) request.getParameter("programaInList");
        }

        proyectoBean = new ProyectoBean(tipoDependencia);
        proyectoBean.setStrServer(request.getHeader("host"));
        proyectoBean.setStrUbicacion(getServletContext().getRealPath(""));
        proyectoBean.resultSQLConecta(tipoDependencia);

        proyectoList = proyectoBean.getResultSQLProyectosByRamPrgInList(ramoInList, programaInList, year);

        contRegProyectos = proyectoList.size();
        contRegSelectProyectos = contRegProyectos;

        if (contRegProyectos == 1) {
            msjSelectProyectos = "Seleccionado";
        }

        RegTemp = 0;
        for (Proyecto proyecto : proyectoList) {
            RegTemp++;
            strTablaProyectos += "<tr >";
            strTablaProyectos += "<td align='left' >";
            strTablaProyectos += "<input id='checkProyecto" + RegTemp + "' name='checkProyecto" + RegTemp + "' type='checkbox' onclick='contCheckProyectosRptExcel(" + RegTemp + ")'  >";
            strTablaProyectos += "<input id='proyectoCheck" + RegTemp + "' name='proyectoCheck" + RegTemp + "' type='hidden'  value='" + proyecto.getTipoProyecto() + proyecto.getProyectoId() + "' >";
            strTablaProyectos += "</td>";
            strTablaProyectos += "<td align='left'  >";
            strTablaProyectos += proyecto.getTipoProyecto() + proyecto.getProyectoId() + "-" + proyecto.getProyecto() + " ";
            strTablaProyectos += "</td>";
            strTablaProyectos += "</tr>";
        }

        strResultado += "<label> Proyectos:</label>";
        strResultado += "<ul>";
        strResultado += "<li>";
        strResultado += "<div id='comboSelectProyectos' name='comboSelectProyectos' >";
        strResultado += "<input id='labelContProyectos' name='labelContProyectos' type='Text' value='" + 0 + " " + msjSelectProyectos + "' disabled='true'>";
        strResultado += "<img id='dropDownProyectos' name='dropDownProyectos' src='imagenes/OpenArrow.png' >";
        strResultado += "</div>";
        strResultado += "<ul>";
        strResultado += "<li>";
        strResultado += "<div id='divProyectos' name='divProyectos' >";
        strResultado += "<input type='checkbox' id='allChecksProyecto' name='allChecksProyecto' onclick='allChecksProyectosRptExcel()' >Todos/Ninguno";
        strResultado += "<table>";
        strResultado += strTablaProyectos;
        strResultado += "</table></div></li></ul></li></ul>";
        strResultado += "<input id='contRegProyecto' name='contRegProyecto' type='hidden' value='" + RegTemp + "' >";
        strResultado += "<input id='contRegProySelect' name='contRegProySelect' type='hidden' value='0' />";



    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (programaBean != null) {
            programaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>