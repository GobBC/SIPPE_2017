<%-- 
    Document   : ajaxGetLineasProyecto
    Created on : Mar 30, 2015, 8:47:24 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.entidades.FuenteFinanciamiento"%>
<%@page import="java.lang.String"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
    String strResultado = new String();
    String ramoId = new String();
    int opcion = 0;
    int metaId = 0;
    int accionId = 0;
    int year = 0;
    String idFuentes = new String();
    String arregloFuente[] = null;
    String selected = new String();
    String programa = new String();
    String tipoDependencia = new String();
    AccionBean accionBean = null;
    List<FuenteFinanciamiento> accionFuenteList = new ArrayList<FuenteFinanciamiento>();
    List<FuenteFinanciamiento> fuenteList = new ArrayList<FuenteFinanciamiento>();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
     try{
        
        if(request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")){
             ramoId = (String)request.getParameter("ramoId");
        }
        if(request.getParameter("programa") != null
                && !request.getParameter("programa").equals("")){
             programa = (String)request.getParameter("programa");
        }
        if(request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")){
             metaId = Integer.parseInt((String)request.getParameter("metaId"));
        }
        
        if(request.getParameter("accionId") != null
                && !request.getParameter("accionId").equals("")){
             accionId = Integer.parseInt((String)request.getParameter("accionId"));
        }
        if(request.getParameter("opcion") != null
                && !request.getParameter("opcion").equals("")){
             opcion = Integer.parseInt((String)request.getParameter("opcion"));
        }
        if(request.getParameter("arregloFuente") != null
                && !request.getParameter("arregloFuente").equals("")){
             idFuentes = (String)request.getParameter("arregloFuente");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        if(opcion == 1){
            accionFuenteList = accionBean.getAccionFuente(year, ramoId, metaId, accionId);
            fuenteList = accionBean.getFuenteFinanciamiento(year);
            if(fuenteList.size() == 0){
                fuenteList.add(new FuenteFinanciamiento());
            }
        }else{
            if(session.getAttribute("fuenteFin") != null){
                fuenteList = (List<FuenteFinanciamiento>) session.getAttribute("fuenteFin");
            }
            if(session.getAttribute("accion-fuente") != null){
                accionFuenteList = (List<FuenteFinanciamiento>) session.getAttribute("accion-fuente"); 
            }
            if(opcion == 2){
                FuenteFinanciamiento fuenteTemp = new FuenteFinanciamiento();
                fuenteTemp.setFuenteId(0);
                accionFuenteList.add(fuenteTemp);
            }
            if(opcion == 3){
                arregloFuente = new String[idFuentes.split(",").length];
                arregloFuente = idFuentes.split(",");
                accionFuenteList = accionBean.saveFuenteFinanciamiento(year, ramoId, metaId, accionId, arregloFuente, programa);
            }
        }
        strResultado = "<table>";
        for(FuenteFinanciamiento fuente : accionFuenteList){
        strResultado += "<tr> <td>";
            strResultado += "<select class='selLinea' onchange='cambioLinea()'>";   
            strResultado += "   <option " +selected +" value='-1'>";
            strResultado += "-- Seleccione una Fuente de financiamiento --";
            strResultado += "   </option>";
            for(FuenteFinanciamiento fuenteF : fuenteList){
                if(fuente.getFuenteId() == fuenteF.getFuenteId()){
                    selected = "selected";
                }
                if(fuenteF.getFuenteId() != 0){ 
                    strResultado += "   <option " +selected +" value='"+fuenteF.getFuenteId()+"'>";
                    strResultado += fuenteF.getFuenteId()+"-"+fuenteF.getFuenteFinanciamiento();
                    strResultado += "   </option>";
                }
                selected = new String();
            }            
            strResultado += "<select>";
        strResultado += "</td> </tr>";
        }
        strResultado += "</table>";
        strResultado += "<script type=\"text/javascript\">";
        strResultado += "  $(\"#ped-lineas tr\").click(function(){";
        strResultado += "  $(this).addClass('selected-linea').siblings().removeClass('selected-linea');";
        strResultado += "});";
        strResultado += "</script>";
        session.setAttribute("fuenteFin", fuenteList);
        session.setAttribute("accion-fuente", accionFuenteList);
     }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
