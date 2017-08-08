<%-- 
    Document   : ajaxAvancePoaMetas.jsp
    Created on : Jan 22, 2015, 10:14:03 AM
    Author     : mavalle
--%>

<%@page import="gob.gbc.util.EnumProcesoEspecial"%>
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
    String usuario = new String();
    int year = 0;
    int rol=0;
    boolean accesoPrg=true;

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
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year") );
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
        
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }else{
        metaId=-1;
        }
        
        if (request.getParameter("calculo") != null
                && !request.getParameter("calculo").equals("")) {
            calculo = (String) request.getParameter("calculo");
        }
        
        if (request.getParameter("metaDescr") != null
                && !request.getParameter("metaDescr").equals("")) {
            metaDescr = (String) request.getParameter("metaDescr");
        }
        
        
        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
        
        if (!ramoId.equals("-1")) {
          if(!(metaId == -1)){
           //validar cierre accion
             validaCierreAccion=metaBean.getValidaCierreAccionAvancePoa(ramoId,year);                
             if(validaCierreAccion.equals("S")){
                accesoPrg=metaBean.getResultSQLhasProcesoEspecual(usuario, EnumProcesoEspecial.NORMATIVO_AVANCE.getProceso());
              }
            if(accesoPrg==false){
               strResultado +="<center><table><tr><td><font color='red'>El ramo esta cerrado para el avance accion<br><br><center>       <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('avancePoaMeta');\" /> </center></td></tr></table></center>";
            }else{
                 ///si no esta cerrado
                 strResultado +="<input type='hidden' id='meta' name='meta' value='"+metaId+"' >";
                 strResultado +="<input type='hidden' id='ramo' name='ramo' value='"+ramoId+"' >";
                strResultado +="<table  style='width:100%;' ><tr style='width:100%;'><tr>Meta: "+metaId+" "+metaDescr+"</tr></table> <br><br>";
                 
                 strResultado +="<table id='tblAvancePoaAccion'><thead><th>Accion</th><th>Descripci&oacute;n</th><th>Unidad Medida</th><th>Unidad Ejecutora</th><th>Autorizado</th><th>Ampliaciones Reducciones</th><th>Ejercido</th><th>Diferencia</th></thead> <tbody id='tbody-acciones'>";
                  accionesAvanceList=metaBean.getObtieneAccionesAvancePoa(metaId,ramoId,year);
                 for(AccionesAvancePoa accionesAvanceTemp : accionesAvanceList){
                     
                   strResultado +="<tr onclick='avancePoaAccionSelAccion()'><td>"+accionesAvanceTemp.getACCION()+"</td><td>"+accionesAvanceTemp.getDESCR()+"</td><td>"+accionesAvanceTemp.getUNIDAD_MEDIDA()+"</td><td>"+accionesAvanceTemp.getUE_DESCR()+"</td><td>"+accionesAvanceTemp.getIMPORTE()+"</td><td>"+accionesAvanceTemp.getASIGNADO()+"</td><td>"+accionesAvanceTemp.getEJERCIDO()+"</td><td>"+((accionesAvanceTemp.getIMPORTE()+accionesAvanceTemp.getASIGNADO()) - accionesAvanceTemp.getEJERCIDO())+"</td></tr>";  
                     
                 
                 }
                 strResultado +=" </tbody></table>";
                 strResultado +="<center>       <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('avancePoaMeta');\" /> </center>";
            
            strResultado += "<script type=\"text/javascript\">";
            strResultado += "       $('#tblAvancePoaAccion tbody tr').click(function(){";
            strResultado += "       $(this).addClass('selected').siblings().removeClass('selected');";
            strResultado += "   });";
            strResultado += "</script>";
       
            strResultado +=" $( document ).ready(function() { "
                         +" seleccionaRenglonAvancePoaAccion(); "
                         +"}); ";
            strResultado +="<div id='avanceAccion'></div>"; 


            
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
    } finally {
        if (metaBean != null) {
            session.setAttribute("metaList", metaList);
            metaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>