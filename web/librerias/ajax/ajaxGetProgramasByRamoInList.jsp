<%-- 
    Document   : ajaxGetProgramasByRamo
    Created on : Mar 20, 2015, 4:08:07 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.aplicacion.ProgramaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String strTablaProgramas = new String();
    String ramoInList = new String();
    boolean cierre = false;
    ProgramaBean programaBean = null;
    int year = 0;
    int RegTemp = 0;
    String rol = new String();
    String usuario = new String();
    String tipoDependencia = new String();
    int contRegProgramas = 0;
    int contRegSelectProgramas = 0;
    String msjSelectProgramas = "Seleccionados";
    List<Programa> programaList = new ArrayList<Programa>();
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
        programaBean = new ProgramaBean(tipoDependencia);
        programaBean.setStrServer(request.getHeader("host"));
        programaBean.setStrUbicacion(getServletContext().getRealPath(""));
        programaBean.resultSQLConecta(tipoDependencia);

        if (request.getParameter("ramoInList") != null && !request.getParameter("ramoInList").equals("")) {
            ramoInList = (String) request.getParameter("ramoInList");
        }



        programaList = programaBean.getProgramasByRamoInList(ramoInList, year, usuario);
        contRegProgramas = programaList.size();
        contRegSelectProgramas = contRegProgramas;

        if (contRegProgramas == 1) {
            msjSelectProgramas = "Seleccionado";
        }

        RegTemp = 0;
        for (Programa programa : programaList) {
            RegTemp++;
            strTablaProgramas += "<tr >";
            strTablaProgramas += "<td align='left' >";
            strTablaProgramas += "<input id='checkPrograma" + RegTemp + "' name='checkPrograma" + RegTemp + "' type='checkbox' onclick='contCheckProgramasRptExcel(" + RegTemp + ")'  >";
            strTablaProgramas += "<input id='programaCheck" + RegTemp + "' name='programaCheck" + RegTemp + "' type='hidden'  value='" + programa.getProgramaId() + "' >";
            strTablaProgramas += "</td>";
            strTablaProgramas += "<td align='left'  >";
            strTablaProgramas += programa.getProgramaId() + "-" + programa.getProgramaDesc() + " ";
            strTablaProgramas += "</td>";
            strTablaProgramas += "</tr>";
        }

        strResultado += "<label> Programas:</label>";
        strResultado += "<ul>";
        strResultado += "<li>";
        strResultado += "<div id='comboSelectProgramas' name='comboSelectProgramas' >";
        strResultado += "<input id='labelContProgramas' name='labelContProgramas' type='Text' value='" + 0 + " " + msjSelectProgramas + "' disabled='true'>";
        strResultado += "<img id='dropDownProgramas' name='dropDownProgramas' src='imagenes/OpenArrow.png' >";
        strResultado += "</div>";
        strResultado += "<ul>";
        strResultado += "<li>";
        strResultado += "<div id='divProgramas' name='divProgramas' >";
        strResultado += "<input type='checkbox' id='allChecksPrograma' name='allChecksPrograma' onclick='allChecksProgramasRptExcel()' >Todos/Ninguno";
        strResultado += "<table>";
        strResultado += strTablaProgramas;
        strResultado += "</table></div></li></ul></li></ul>";
        strResultado += "<input id='contRegPrograma' name='contRegPrograma' type='hidden' value='" + RegTemp + "' >";
        strResultado += "<input id='contRegPrgSelect' name='contRegPrgSelect' type='hidden' value='0' />";

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