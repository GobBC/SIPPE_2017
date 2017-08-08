<%-- 
    Document   : ajaxGetLineasProyecto
    Created on : Mar 30, 2015, 8:47:24 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.TipoGasto"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
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
    String programa = new String();
    String tipoDependencia = new String();
    int opcion = 0;
    int metaId = 0;
    int accionId = 0;
    int year = 0;
    int fuente = 0;
    String idFuentes = new String();
    String arregloGasto[] = null;
    String selected = new String();
    AccionBean accionBean = null;
    List<TipoGasto> accionTipoGastoList = new ArrayList<TipoGasto>();
    List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
     try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
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
        
        if(request.getParameter("fuente") != null
                && !request.getParameter("fuente").equals("")){
             fuente = Integer.parseInt((String)request.getParameter("fuente"));
        }
        
        if(request.getParameter("opcion") != null
                && !request.getParameter("opcion").equals("")){
             opcion = Integer.parseInt((String)request.getParameter("opcion"));
        }
        if(request.getParameter("arregloGasto") != null
                && !request.getParameter("arregloGasto").equals("")){
             idFuentes = (String)request.getParameter("arregloGasto");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        
        if(opcion == 1){
            accionTipoGastoList = accionBean.getAccionTipoGasto(year, ramoId, metaId, accionId, fuente);
            tipoGastoList = accionBean.getTipoGasto(year);
            if(tipoGastoList.size() == 0){
                tipoGastoList.add(new TipoGasto());
            }
        }else{
            if(session.getAttribute("tipoGasto") != null){
                tipoGastoList = (List<TipoGasto>) session.getAttribute("tipoGasto");
            }
            if(session.getAttribute("accion-gasto") != null){
                accionTipoGastoList = (List<TipoGasto>) session.getAttribute("accion-gasto"); 
            }
            if(opcion == 2){
                TipoGasto gastoTemp = new TipoGasto();
                gastoTemp.setTipoGastoId(0);
                accionTipoGastoList.add(gastoTemp);
            }
            if(opcion == 3){
                arregloGasto = new String[idFuentes.split(",").length];
                arregloGasto = idFuentes.split(",");                
                accionTipoGastoList = accionBean.saveTipoGasto(year, ramoId, metaId, accionId, fuente, arregloGasto, programa);
            }
        }
        strResultado = "<table>";
        for(TipoGasto tipoGasto : accionTipoGastoList){
        strResultado += "<tr> <td>";
            strResultado += "<select class='selLinea' onchange='cambioLineaSectorial()'>";   
            strResultado += "   <option " +selected +" value='-1'>";
            strResultado += "-- Selecciones un tipo de gasto --";
            strResultado += "   </option>";
            for(TipoGasto tipoG : tipoGastoList){
                if(tipoGasto.getTipoGastoId() == tipoG.getTipoGastoId()){
                    selected = "selected";
                }
                if(tipoG.getTipoGastoId() != 0){ 
                    strResultado += "   <option " +selected +" value='"+tipoG.getTipoGastoId()+"'>";
                    strResultado += tipoG.getTipoGastoId()+"-"+tipoG.getTipoGasto();
                    strResultado += "   </option>";
                }
                selected = new String();
            }            
            strResultado += "</select>";
        strResultado += "</td> </tr>";
        }
        strResultado += "</table>";
        strResultado += "<script type=\"text/javascript\">";
        strResultado += "  $(\"#sectorial-lineas tr\").click(function(){";
        strResultado += "  $(this).addClass('selected-linea').siblings().removeClass('selected-linea');";
        strResultado += "});";
        strResultado += "</script>";
        session.setAttribute("tipoGasto", tipoGastoList);
        session.setAttribute("accion-gasto", accionTipoGastoList);
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
