<%-- 
    Document   : ajaxDeleteLineaAccion
    Created on : Aug 26, 2015, 10:26:57 AM
    Author     : ugarcia
--%>

<%@page import="java.util.Iterator"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.ProgramaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String linea = new String();
    String ramo = new String();
    String programa = new String();
    String tipoDependencia = new String();
    String selected = new String();
    List<Linea> lineaRamoList = new ArrayList<Linea>();
    List<Linea> lineaList = new ArrayList<Linea>();
    Linea lineaB = null;
    Iterator<Linea> lineaIte;
    
    int year = 0;
    int contLM = 0;
    int contLA = 0;
    int cont = 1;
    
    String strResultado = new String();
    ProgramaBean programaBean = null;
    try {
        if (request.getParameter("linea") != null && !request.getParameter("linea").equals("")) {
            linea = request.getParameter("linea");
        }
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramo = request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            programa = request.getParameter("programa");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(session.getAttribute("linea-ramo") != null && session.getAttribute("linea-ramo") != ""){
            lineaRamoList = (List<Linea>) session.getAttribute("linea-ramo");
        }
        if(session.getAttribute("linea-accion") != null && session.getAttribute("linea-accion") != ""){
            lineaList = (List<Linea>) session.getAttribute("linea-accion");
        }
        programaBean = new ProgramaBean(tipoDependencia);
        programaBean.setStrServer(request.getHeader("host"));
        programaBean.setStrUbicacion(getServletContext().getRealPath(""));
        programaBean.resultSQLConecta(tipoDependencia);
        contLM = programaBean.getCountLineaAccionMeta(year, ramo, programa, linea);
        contLA = programaBean.getCountLineaAccionAccion(year, ramo, programa, linea);
        if((contLM+contLA) == 0){
            strResultado += "borrar|";
            lineaIte = lineaRamoList.iterator();
            while(lineaIte.hasNext()){
                lineaB = lineaIte.next();
                if(linea.equals(lineaB.getLineaId())){
                    lineaIte.remove();
                }
            }
            /*
            for(Linea lineaT : lineaRamoList){
                if(lineaT.getLineaId().equalsIgnoreCase(linea)){
                    lineaB = lineaT;
                }
            }
            if(lineaB != null){
                lineaRamoList.remove(lineaB);
            }
            */
            strResultado += "<table id='tblLineaAcc'> ";
            for(Linea lineaF : lineaRamoList){
                cont ++;
                strResultado += "<tr> <td>";
                strResultado += "<select id='sel"+cont+"' class='ramoLineaAccion' onChange='return cambioLineaAccion()'>";
                strResultado += "<option value='0'>--Seleccione una l&iacute;nea de acci&oacute;n--</option>";
                for(Linea lineaE : lineaList){
                    if(lineaF.getLineaId().equals(lineaE.getLineaId())){
                        selected = "selected";
                    }
                    strResultado += "<option value='"+lineaE.getLineaId()+"' "+selected+" >"+lineaE.getLineaId()
                            +"-"+lineaE.getLinea()+"</option>";
                    selected = new String();
                }
                strResultado += "</select>";
                strResultado += "</tr> </td>";
            }
            strResultado += "</table>";
            strResultado += "<script type=\"text/javascript\">";
            strResultado += "  $(\"#tblLineaAcc tr\").click(function(){";
            strResultado += "  $(this).addClass('selected-linea').siblings().removeClass('selected-linea');";
            strResultado += "});";
            strResultado += "</script>";
            session.setAttribute("linea-ramo", lineaRamoList);
            session.setAttribute("linea-accion", lineaList);
            strResultado += "<div id='divLinRamo'>";
            strResultado += "</div>";
            strResultado += "<div id='divControlLinea'> ";
            strResultado += "<input id='inpTxtInsLin' value='Insertar' onclick='insertarLineaAccion()'/> ";
            strResultado += "<input id='inpTxtBorrarLin' value='Borrar' onclick='borrarLineaAccion()/> ";
            strResultado += "</div>";
        }else{
            strResultado += "lineas";
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
        if (programaBean != null) {
            programaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
