<%-- 
    Document   : ajaxBloqueaBotones
    Created on : Oct 9, 2015, 3:32:25 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.aplicacion.CargaCodigoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    String prg = new String();
    String partida = new String();
    String fuente = new String();
    String fondo = new String();
    String recurso = new String();
    int meta = 0;
    int accion = 0;
    int year = 0;
    int requerimiento = 0;
    AccionBean accionBean = null;
    

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String)session.getAttribute("year"));
        }
        if(request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")){
             ramo = (String)request.getParameter("ramo");
        }
        if(request.getParameter("progrma") != null && !request.getParameter("progrma").equals("")){
             prg = (String)request.getParameter("progrma");
        }
        if(request.getParameter("partida") != null && !request.getParameter("partida").equals("")){
             partida = (String)request.getParameter("partida");
        }
        if(request.getParameter("meta") != null && !request.getParameter("meta").equals("")){
             meta = Integer.parseInt(request.getParameter("meta"));
        }
        if(request.getParameter("accion") != null && !request.getParameter("accion").equals("")){
             accion = Integer.parseInt(request.getParameter("accion"));
        }
        if(request.getParameter("accion") != null && !request.getParameter("accion").equals("")){
             accion = Integer.parseInt(request.getParameter("accion"));
        }
        if(request.getParameter("fuente") != null && !request.getParameter("fuente").equals("")){
             fuente = (String )request.getParameter("fuente");
        }
        if(request.getParameter("fondo") != null && !request.getParameter("fondo").equals("")){
             fondo = (String )request.getParameter("fondo");
        }
        if(request.getParameter("recurso") != null && !request.getParameter("recurso").equals("")){
             recurso = (String )request.getParameter("recurso");
        }
        if(request.getParameter("requerimiento") != null && !request.getParameter("requerimiento").equals("")){
             requerimiento = Integer.parseInt(request.getParameter("requerimiento"));
        }
        
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        
        strResultado += accionBean.getTipoRequerimientoPlantilla(year, ramo, prg, meta, 
                accion, partida, fuente, fondo, recurso, requerimiento);

        /* lineaSectorial = new LineaSectorial();
         lineaSectorial.setLineaId("0.0.0.0");
         lineaSectorialList.add(lineaSectorial);
         */
        
    } catch (Exception ex) {
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
