<%-- 
    Document   : ajaxActualizaAvancePoaMetas.jsp
    Created on : Jan 22, 2015, 10:14:03 AM
    Author     : mavalle
--%>

<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.AvancePoaMeta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Meta> metaList = new ArrayList<Meta>();
    MetaBean metaBean = null;
    Meta meta;
    List<AvancePoaMeta> avancePoaMetaList=new ArrayList<AvancePoaMeta>();
    AvancePoaMeta avancePoaMeta;
    List<Evaluacion> evaluacionList = new ArrayList<Evaluacion>();
    Evaluacion evaluacion;
    Dependencia dependencia;
    String strResultado = new String();
    String tipoDependencia = new String();
    boolean accesoPrg = false;
    int estiloPar = 0;
    String ramoId = new String();
    int metaId = 0;
    String cadValidaPeriodo = new String();
    String validaPeriodo[] = null;
    int year = 0;
    int rol=0;
    String strObservaciontmp="";
    int periodoParametrosPrg = 0;
    int evaluacionEstimacion = 0;
    int maxPeriodoEstimacion = 0;
    String esParaestatal="";
    int intEvaluacion=0;
    int totalRealizado=0;
    int totalProgramado=0;
    String evaluacionDescr="";
    String desahabilitado="";
    String calculo="";
    String valoresRealizado="";
    String valoresProgramado="";
    String Realizado[]=null;
    String Programado[]=null;
    String valoresActivo="";
    String valoresObservacion="";
    String Activo[]=null;
    String Observa[]=null;
    String strActivo="";
    int existe=0;
    int periodo=0;
    int realizo=0;
    boolean resp=false;
    String observacion="";
    String observacionAnual="";
    boolean graba=true;
     
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = Integer.parseInt((String) session.getAttribute("strRol") );
        }
        
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year") );
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
        
        if (request.getParameter("valoresProgramado") != null
                && !request.getParameter("valoresProgramado").equals("")) {
            valoresProgramado =(String) request.getParameter("valoresProgramado");
        }
        
        if (request.getParameter("valoresRealizado") != null
                && !request.getParameter("valoresRealizado").equals("")) {
            valoresRealizado =(String) request.getParameter("valoresRealizado");
        }
        
        if (request.getParameter("valoresActivo") != null
                && !request.getParameter("valoresActivo").equals("")) {
            valoresActivo =(String) request.getParameter("valoresActivo");
        }
        
        if (request.getParameter("periodo") != null
                && !request.getParameter("periodo").equals("")) {
            periodo =Integer.parseInt(request.getParameter("periodo"));
        }
        
        if (request.getParameter("observacion") != null
                && !request.getParameter("observacion").equals("")) {
            valoresObservacion =(String) request.getParameter("observacion");
        }
        
        if (request.getParameter("observacionAnual") != null
                && !request.getParameter("observacionAnual").equals("")) {
            observacionAnual =(String) request.getParameter("observacionAnual");
        }
        
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }else{
        metaId=-1;
        }
        
        
        
        Programado=valoresProgramado.split("}");
        Realizado=valoresRealizado.split("}");                
        Activo=valoresActivo.split("}");
        Observa=valoresObservacion.split("}");
        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
        
        existe=metaBean.getExisteMetaAvancePoa(metaId,ramoId,year,periodo); 
        for(int x=0;x<Programado.length;x++){
            strActivo=String.valueOf(Activo[x]);
           if(strActivo.equals("S")){
               strObservaciontmp="";
               strObservaciontmp=String.valueOf(Observa[x]);
            if(existe<1){
             realizo=metaBean.getInsertaMetaAvancePoa(metaId,ramoId, year, x+1,Double.parseDouble(Realizado[x]),strObservaciontmp,observacionAnual);
             strResultado="";    
             if(realizo>0){strResultado="<center><br/><br/><table border=1><tr><td>Actualizado!</td></tr></table></center>";}else{strResultado="<center><br/><br/>Error al Actualizar!</center>";}
             }else{
             strResultado="";
             realizo=metaBean.getActualizaMetaAvancePoa(metaId,ramoId, year, x+1,Double.parseDouble(Realizado[x]),strObservaciontmp,observacionAnual);
             if(realizo>0){strResultado="<center><br/><br/><table border=1 ><tr><td>Actualizado!</td></tr></table></center>";}else{strResultado="<center><br/><br/>Error al Actualizar!</center>";}
             }
               
           }       
        }
        
        
        
        
        
       } catch (Exception ex) {
        strResultado="";
        strResultado = "Error al Actualizar";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
        graba=false;
    } finally {
        if (metaBean != null) {
            session.setAttribute("metaList", metaList);
            if(graba){
            metaBean.transaccionCommit();}
            metaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>