<%-- 
    Document   : ajaxBorrarReprogramacion
    Created on : Dec 23, 2015, 3:18:42 PM
    Author     : ugarcia
--%>


<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MovimientosReprogramacion movRepro = new MovimientosReprogramacion();
    List<ReprogramacionAccion> reproAccionList = new ArrayList<ReprogramacionAccion>();
    ReprogramacionAccion reproAccionTemp = new ReprogramacionAccion();
    List<ReprogramacionMeta> reproMetaList = new ArrayList<ReprogramacionMeta>();
    ReprogramacionMeta reproMetaTemp = new ReprogramacionMeta();
    String strResultado = new String();
    int identidicador = -1;
    int tipoRecal = 0;
    
    try {
        if(session.getAttribute("reprogramacion") != null && session.getAttribute("reprogramacion") != ""){
            movRepro = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            reproMetaList = movRepro.getMovOficioMetaList();
            reproAccionList = movRepro.getMovOficioAccionList();
        }
        if(Utilerias.existeParametro("identificador", request)){
            identidicador = Integer.parseInt((String) request.getParameter("identificador"));
        }
        if(Utilerias.existeParametro("tipoProg", request)){
            tipoRecal = Integer.parseInt((String) request.getParameter("tipoProg"));
        }
        if(tipoRecal == 1){
            for(ReprogramacionMeta reprogMeta : reproMetaList){
                if(reprogMeta.getIdentificador()== identidicador){
                    reproMetaTemp = reprogMeta;
                    break;
                }
            }
            reproMetaList.remove(reproMetaTemp);
            movRepro.setMovOficioMetaList(reproMetaList);
        }
        if(tipoRecal == 2){
            for(ReprogramacionAccion reprogAccion : reproAccionList){
                if(reprogAccion.getIdentificador() == identidicador){
                    reproAccionTemp = reprogAccion;
                    break;
                }
            }
            reproAccionList.remove(reproAccionTemp);
            movRepro.setMovOficioAccionList(reproAccionList);
        }
        session.setAttribute("reprogramacion", movRepro);
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        out.print(strResultado);
    }
%>
