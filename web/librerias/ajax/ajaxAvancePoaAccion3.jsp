<%-- 
    Document   : ajaxAvancePoaMetas.jsp
    Created on : Jan 22, 2015, 10:14:03 AM
    Author     : mavalle
--%>

<%@page import="gob.gbc.entidades.AvancePoaMetaObservaciones"%>
<%@page import="gob.gbc.entidades.AccionesAvancePoa"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.AvancePoaMeta"%>
<%@page import="gob.gbc.entidades.AvancePoaAcciones"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Meta> metaList = new ArrayList<Meta>();
    Meta meta;
    MetaBean metaBean = null;
    List<AvancePoaMetaObservaciones> metaObservList = new ArrayList<AvancePoaMetaObservaciones>(); 
    AvancePoaMetaObservaciones metaObserv;
    
    List<AvancePoaMeta> avancePoaMetaList=new ArrayList<AvancePoaMeta>();
    AvancePoaMeta avancePoaMeta;
    
    List<AvancePoaAcciones> avancePoaAccionesList=new ArrayList<AvancePoaAcciones>();
    AvancePoaAcciones avancePoaAcciones;
    
    
    List<Evaluacion> evaluacionList = new ArrayList<Evaluacion>();
    Evaluacion evaluacion;
    List<AccionesAvancePoa> accionesAvanceList = new ArrayList<AccionesAvancePoa>();
    AccionesAvancePoa accionesAvancePoa;
    Dependencia dependencia;
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramoId = new String();
    int metaId = 0;
    String cadValidaPeriodo = new String();
    String validaPeriodo[] = null;
    String calculo="";
    String validaCierreAccion="";
    String metaDescr="";
    String Deshabilitado="";
    int mes=0;
    int accionId =0;
    int year = 0;
    int rol=0;
    int intCuantos=0;
    boolean accesoPrg=true;
    int insertoMes=0;
    int selEstilo=0;
    String realizado="";
    float ampRed=0;
    float ejercido=0;
    String observacion="";
    boolean graba=true;
    
    String strPeriodo="";     
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
        
        if (request.getParameter("accionId") != null
                && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt((String) request.getParameter("accionId"));
        }
        
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }else{
        metaId=-1;
        
        }
       
       if (request.getParameter("mes") != null
                && !request.getParameter("mes").equals("")) {
            mes = Integer.parseInt((String) request.getParameter("mes"));
        }
        if (request.getParameter("realizado") != null
                && !request.getParameter("realizado").equals("")) {
            realizado = (String) request.getParameter("realizado");
        }
       if (request.getParameter("ampRed") != null
                && !request.getParameter("ampRed").equals("")) {
            ampRed = Float.parseFloat((String) request.getParameter("ampRed"));
        }
               
        if (request.getParameter("ejercido") != null
                && !request.getParameter("ejercido").equals("")) {
            ejercido = Float.parseFloat((String) request.getParameter("ejercido"));
        }
        if (request.getParameter("observacion") != null
                && !request.getParameter("observacion").equals("")) {
            observacion = (String) request.getParameter("observacion");
        }
        
        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
        
        
        if (!ramoId.equals("-1")) {
          if(!(metaId == -1)){
              insertoMes=metaBean.getUpdateAvanceAccionesAvancePoa(metaId,ramoId,year,accionId,mes,realizado,ampRed,ejercido,observacion);
              if(insertoMes>0){
               strResultado="<center><br/><br/><table border=1 ><tr><td>Actualizado!</td></tr></table><br/><br/><input type='button' value='cerrar' onclick='$(\"#Observa\").hide();'></center>";
              }else{
               strResultado="<center><br/><br/>Error al Actualizar<br/><br/><input type='button' value='cerrar' onclick='$(\"#Observa\").hide();'></center>";
              }
         }
        }
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
        graba=false;
     }finally {
        if (metaBean != null) {
            session.setAttribute("metaList", metaList);
            if(graba){
            metaBean.transaccionCommit();
            }
            metaBean.resultSQLDesconecta();
           
        }
        out.print(strResultado);
    }
%>