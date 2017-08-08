<%-- 
    Document   : ajaxCheckPlantillaNueva
    Created on : Jun 14, 2016, 4:32:00 PM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.aplicacion.PlantillaBean"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.RevisionCaratula"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.RevisionCaratulaBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
//    response.setCharacterEncoding("UTF-8");
    MetaBean metaBean = null;
    

    String ramoId = new String();
    String deptoId = new String();
    String strResult = new String();
    String plantilla = new String();
    String programaId = new String();
    String tipoDependencia = new String();
    String traeResultado= new String();

    long selCaratula = -1;

    int year = 0;
    int metaId = 0;
    int accionId = 0;
    int estadoAnterior = 0;

    boolean blResultado = true;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (request.getParameter("year") != null && !request.getParameter("year").equals("")) {
            year = Integer.parseInt(request.getParameter("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
      
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        
        if (request.getParameter("traeResultado") != null && !request.getParameter("traeResultado").equals("")) {
            traeResultado = (String) request.getParameter("traeResultado");
        }

        
        if (request.getParameter("estadoAnterior") != null && !request.getParameter("estadoAnterior").equals("")) {
            estadoAnterior = Integer.parseInt(request.getParameter("estadoAnterior"));
        }

        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
        strResult = "";

        //if (estadoAnterior > 0) {
            if (traeResultado.equals("off")) {
                // apagar parametro
                blResultado = metaBean.getResultSQLOffTraeResultadoMeta(year, ramoId, metaId);
                if (blResultado) {
                    strResult = "2";
                } else {
                    strResult = "Error al indicar que la meta No trae Resultado";
                }
            }
       // } else {
            if (traeResultado.equals("on")) {
                //encender parametro
                 blResultado = metaBean.getResultSQLOnTraeResultadoMeta(year, ramoId, metaId);
                    if (blResultado) {
                        strResult = "1";
                    } else {
                        strResult = "Error al Indicar que la meta Si trae Resultado";
                    }
               
            }
       // }

    } catch (Exception ex) {
        strResult = "No es posible actualizar la información";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
       if (metaBean != null) {
            metaBean.resultSQLDesconecta();
        }
        
        out.print(strResult);
    }
%>
