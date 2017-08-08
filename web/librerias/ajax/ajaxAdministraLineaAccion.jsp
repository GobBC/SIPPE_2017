<%-- 
    Document   : ajaxAdministraLineaAccion
    Created on : Jul 31, 2015, 12:17:05 PM
    Author     : ugarcia
--%>

<%@page import="java.util.Iterator"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<Linea> lineaRamoList = new ArrayList<Linea>();
    List<Linea> lineaList = new ArrayList<Linea>();
    
    Iterator<Linea> lineaIte;
    Linea lineaTemp;
    Linea lineaI = new Linea();
    int cont = 1;
    
    String nuevaLinea = new String();
    String strResultado = new String();
    String selected = new String();
    
    int indexLinea = -1;
    int opcion = 0;
    
    try{
        if(session.getAttribute("linea-ramo") != null && session.getAttribute("linea-ramo") != ""){
            lineaRamoList = (List<Linea>) session.getAttribute("linea-ramo");
        }
        if(session.getAttribute("linea-accion") != null && session.getAttribute("linea-accion") != ""){
            lineaList = (List<Linea>) session.getAttribute("linea-accion");
        }
        if (request.getParameter("opcion") != null
                && !request.getParameter("opcion").equals("")) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (request.getParameter("index") != null
                && !request.getParameter("index").equals("")) {
            indexLinea = Integer.parseInt(request.getParameter("index"));
        }
        if (request.getParameter("nuevaLinea") != null
                && !request.getParameter("nuevaLinea").equals("")) {
            nuevaLinea = request.getParameter("nuevaLinea");
        }
        if(opcion == 1){
            lineaTemp = new Linea();
            lineaTemp.setLinea(new String());
            lineaTemp.setLineaId("0");
            lineaRamoList.add(lineaTemp);
        }else if(opcion == 2){
            lineaTemp = new Linea();
            lineaTemp.setLinea(new String());
            lineaTemp.setLineaId(nuevaLinea);
            if(lineaList.size() > 0)
                lineaRamoList.set(indexLinea, lineaTemp);
            /*
            if(lineaList.size() > 0)
                lineaRamoList.remove(indexLinea);
            lineaRamoList.add(indexLinea, lineaTemp);
            */
        }
        strResultado += "<table id='tblLineaAcc'> ";
        for(Linea linea : lineaRamoList){
            cont ++;
            strResultado += "<tr> <td>";
            strResultado += "<select id='sel"+cont+"' class='ramoLineaAccion' onChange='return cambioLineaAccion()'>";
            strResultado += "<option value='0'>--Seleccione una l&iacute;nea de acci&oacute;n--</option>";
            for(Linea lineaE : lineaList){
                if(linea.getLineaId().equals(lineaE.getLineaId())){
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
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        /*if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        */
        out.print(strResultado);
    }
%>