<%-- 
    Document   : ajaxActualizarPrograma
    Created on : Mar 24, 2015, 4:26:41 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.ProgramaBean"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Programa programa = new Programa();
    boolean resultado = false;
    ProgramaBean programaBean = null;
    String strResultado = new String();
    String guardarLinea = new String();
    String lineas[] = new String[0];
    String programaId = new String();
    String ramoId = new String();
    List<Linea> lineaRamoList = new ArrayList<Linea>();
    int fin = 0;
    int ponderado = 0;
    int depto = 0;
    int year = 0;
    String rfc = new String();
    String homoclave = new String();
    String proposito = new String();
    String tipoDependencia = new String();
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        programaBean = new ProgramaBean(tipoDependencia);
        if(request.getParameter("programa") != null
                && !request.getParameter("programa").equals("")){
             programaId = (String)request.getParameter("programa");
        }
        if(request.getParameter("ramo") != null
                && !request.getParameter("ramo").equals("")){
             ramoId = (String)request.getParameter("ramo");
        }
        if(request.getParameter("rfc") != null
                && !request.getParameter("rfc").equals("")){
             rfc = (String)request.getParameter("rfc");
        }
        if(request.getParameter("guardarLinea") != null
                && !request.getParameter("guardarLinea").equals("")){
             guardarLinea = (String)request.getParameter("guardarLinea");
             lineas = new String[guardarLinea.split(",").length];
             lineas = guardarLinea.split(",");
        }
        if(request.getParameter("homoclave") != null
                && !request.getParameter("homoclave").equals("")){
             homoclave = (String)request.getParameter("homoclave");
        }
        if(request.getParameter("fin") != null
                && !request.getParameter("fin").equals("")){
             fin = Integer.parseInt((String)request.getParameter("fin"));
        }
        
        if(request.getParameter("depto") != null
                && !request.getParameter("depto").equals("")){
             depto = Integer.parseInt((String)request.getParameter("depto"));
        }
        
        if(request.getParameter("ponderado") != null
                && !request.getParameter("ponderado").equals("")){
             ponderado = Integer.parseInt((String)request.getParameter("ponderado"));
        }
        if(request.getParameter("proposito") != null
                && !request.getParameter("proposito").equals("")){
             proposito = (String)request.getParameter("proposito");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        programaBean.setStrServer(request.getHeader("host"));
        programaBean.setStrUbicacion(getServletContext().getRealPath(""));
        programaBean.resultSQLConecta(tipoDependencia);
        resultado = programaBean.updatePrograma(programaId, ramoId, rfc, homoclave, fin, proposito, ponderado, depto, year, lineas);
        if(resultado){
            strResultado = "1";
            lineaRamoList = programaBean.getLineaRamoPrg(year, ramoId, programaId);
            session.setAttribute("linea-ramo", lineaRamoList);            
        }
    }catch (Exception ex) {
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
