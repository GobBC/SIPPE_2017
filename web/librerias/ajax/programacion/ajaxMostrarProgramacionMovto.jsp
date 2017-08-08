<%-- 
    Document   : ajaxMostrarProgramacionMovto
    Created on : 5/06/2017, 09:09:13 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.MovimientosBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    MovimientosBean movtoBean = null;
    Accion acccionObj = new Accion();
    Meta metaObj = new Meta();
    
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<Estimacion> estimacionAccionList = new ArrayList<Estimacion>();
        
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    
    boolean bandera = true;
    
    int year = 0;
    int meta = 0;
    int accion = 0;
    int oficio = 0;
    
    if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
        tipoDependencia = (String) session.getAttribute("tipoDependencia");
    }
    
    if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
        year = Integer.parseInt((String) session.getAttribute("year"));
    }
    
    if(Utilerias.existeParametro("ramo", request)){
        ramo = request.getParameter("ramo");
    }
    
    if(Utilerias.existeParametro("meta", request)){
        meta = Integer.parseInt(request.getParameter("meta"));
    }
    
    if(Utilerias.existeParametro("accion", request)){
        accion = Integer.parseInt(request.getParameter("accion"));
    }
    
    if(Utilerias.existeParametro("oficio", request)){
        oficio = Integer.parseInt(request.getParameter("oficio"));
    }
    
    movtoBean = new MovimientosBean(tipoDependencia);
    movtoBean.setStrServer(request.getHeader("host"));
    movtoBean.setStrUbicacion(getServletContext().getRealPath(""));
    movtoBean.resultSQLConecta(tipoDependencia);
    
    try{
        
        if(oficio == 0){
            if(meta < 0 || accion < 0){
                bandera = false;
            }else{
                metaObj = movtoBean.getResultGetMetaById(year, ramo, meta);
                estimacionList = movtoBean.getResultSQLGetEstimacion(year, ramo, meta);

                acccionObj = movtoBean.getResultGetAccionById(year, ramo, meta, accion);
                estimacionAccionList = movtoBean.getResultSQLGetEstimacionByAccion(year, ramo, meta, accion);
            }
        }else{
            if(meta > 0){
                metaObj = movtoBean.getResultGetMetaById(year, ramo, meta);
                estimacionList = movtoBean.getResultSQLGetEstimacion(year, ramo, meta);
            }else{                
                metaObj = movtoBean.getResultGetMovoficiosMeta(oficio,ramo,meta);
                estimacionList = movtoBean.getResultSQLGetMovoficioEstimacion(oficio, ramo, meta);    
            }
            acccionObj = movtoBean.getResultGetMovoficioAccion(oficio, ramo, meta, accion);            
            estimacionAccionList = movtoBean.getResultSQLGetMovoficiosAccionEstimacion(oficio, ramo, meta, accion);
        }
        
        if(bandera){
            strResultado += "<div>";
                strResultado += "<div id='seccionMeta'>";
                    strResultado += "<div class='col-md-12'>";
                        strResultado += "<div class='col-md-3'>Meta: </div>";
                        strResultado += "<div class='col-md-9'>"+metaObj.getMetaId()+"-"+metaObj.getMeta()+"</div>";
                        strResultado += "<div class='col-md-3'>Unidad de Medida: </div>";
                        strResultado += "<div class='col-md-9'>"+metaObj.getMedidaDescr()+"</div>";
                        strResultado += movtoBean.getEstimacionHtml(estimacionList);
                        strResultado += "<div style='padding: 10px 44px 0px 0px;' class='col-md-12'><div class='col-md-10 text-right'>Cantidad:</div><div class='col-md-2'>"
                                + "<input class='cantidadEst text-center' type='text'  value='"+Utileria.calculaEstimacion(estimacionList, metaObj.getCalculo())+"' disabled/></div> </div>";
                    strResultado += "</div>";
                strResultado += "</div>";
                strResultado += "<br/><br/>";
                strResultado += "<div id='seccionAccion'>";
                    strResultado += "<div class='col-md-12'>";
                        strResultado += "<div class='col-md-3'>Acci&oacute;n: </div>";
                        strResultado += "<div class='col-md-9'>"+acccionObj.getAccionId()+"-"+acccionObj.getAccion()+"</div>";
                        strResultado += "<div class='col-md-3'>Unidad de Medida: </div>";
                        strResultado += "<div class='col-md-9'>"+acccionObj.getMedida()+"</div>";
                        strResultado += movtoBean.getEstimacionHtml(estimacionAccionList);
                        strResultado += "<div style='padding: 10px 44px 0px 0px;' class='col-md-12'><div class='col-md-10 text-right'>Cantidad:</div><div class='col-md-2'>"
                                + "<input class='cantidadEst text-center' type='text' value='"+Utileria.calculaEstimacion(estimacionAccionList, acccionObj.getCalculo())+"' disabled/></div> </div>";
                    strResultado += "</div>";
                strResultado += "</div>";
            strResultado += "</div>";
        }else{
            strResultado = "0";
        }
        
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (movtoBean != null) {
            movtoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
