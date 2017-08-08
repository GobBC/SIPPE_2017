<%-- 
    Document   : ajaxAutorizacionMIR
    Created on : Jul 09, 2017, 1:23:15 PM
    Author     : rharo
--%>

<%@page import="gob.gbc.util.Resultado"%>
<%@page import="gob.gbc.util.EnumEstatusMIR"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.fasterxml.jackson.core.type.TypeReference"%>
<%@page import="java.util.Map"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="gob.gbc.sql.ResultSQLCatalogos"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.Vector"%>
<%@page import="gob.gbc.entidades.MIR"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="com.google.gson.Gson"%>

<%@page contentType="application/json"%>
<%
    String tipoDependencia = new String();
    String selRamo = new String();
    String selPrograma = new String();
    String observacion = new String();
    int estatusSiguiente = -1;
    String year =  "";
    String strResultado = "";
    boolean res = false;
    String errorMsg = "";
    Utilerias utileria = null;
    boolean bError = false;
    int iAccion = 0;
    String accion = "guardado"; 
    
    String rol = new String();
    String obsRechazo = new String();
    boolean bEnviar = false; 
    boolean bValidarRechazar = false;
    boolean bEditarMIR = false;
    int estatusMIR = -1;   
    int etapaMIR = estatusMIR-1;
    MIR mir = new MIR();
     
    if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
        tipoDependencia = (String) session.getAttribute("tipoDependencia");
    }    
    if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
        year = (String) session.getAttribute("year");
    }
    
    ResultSQLCatalogos result = new ResultSQLCatalogos(tipoDependencia);
    result.setStrServer(request.getHeader("host"));
    request.setCharacterEncoding("UTF-8");
    
    ResultSQL resultSQL = new ResultSQL(tipoDependencia);

    int estatusSigEnvia = EnumEstatusMIR.ENVIADA.ordinal();
    int estatusSigValida = EnumEstatusMIR.VALIDADA.ordinal();
    int estatusSigRechaza = EnumEstatusMIR.RECHAZADA.ordinal();
    
    try {
        utileria = new Utilerias();           
        result.resultSQLConecta(tipoDependencia);

        if(utileria.existeParametro("selRamo", request)){
            selRamo = request.getParameter("selRamo");
        }
        if(utileria.existeParametro("selPrograma", request)){
            selPrograma = request.getParameter("selPrograma");
        }
         if(utileria.existeParametro("observacion", request)){
            observacion = request.getParameter("observacion");
        }
        if(utileria.existeParametro("estatusSiguiente", request)){
            estatusSiguiente = Integer.parseInt(request.getParameter("estatusSiguiente"));
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        
        if (utileria.existeParametro("accion", request) && (estatusSiguiente > 0 || Integer.parseInt(request.getParameter("accion")) == 4)) {

            iAccion = Integer.parseInt(request.getParameter("accion"));
            mir.setStrYear(String.valueOf(year));
            mir.setRamo(selRamo);
            mir.setPrg(selPrograma);
            
            if (iAccion == 1) {                
                    res = result.validaComponenteActividadMIR(year,selRamo,selPrograma);
                    if(res){
                        res = result.validaPropositoFinProblemaMIR(year,selRamo,selPrograma);
                        if(res){
                            Resultado resultado;
                            
                            if(estatusSiguiente == 5)
                                resultado = result.validaDimensionesMIR(new MIR(year,selRamo,selPrograma));
                            else
                                resultado = new Resultado("", true);
                            
                            if(resultado.isResultado()){
                                res = result.actualizarEstatusMIR(year,selRamo,selPrograma,estatusSiguiente);
                                if (!res) {
                                    errorMsg = "No se pudo Enviar el MIR. ";
                                } else {
                                    if (!result.guardar()) {
                                        errorMsg = "No se pudo Guardar el MIR. ";
                                    }                                            
                                }
                            } else {
                                res = false;
                                errorMsg = resultado.getMensaje();
                            }
                        } else {
                            errorMsg = "No es posible enviar la MIR. Revise que el Propósito, Fin y Problema hayan sido capturados. ";
                        }
                    } else {
                        int intCantMinComponente = Integer.parseInt(result.getValorParametros("CANT_MIN_COMPONENTES"));
                        int intCantMinActividad = Integer.parseInt(result.getValorParametros("CANT_MIN_ACTIVIDADES"));
                        errorMsg = "No es posible enviar la MIR. Revise que existan " + intCantMinComponente + " o más Componentes "
                                + "y en cada uno " + intCantMinActividad + " o mas Actividades. ";
                    }
                    accion ="enviado";                
            } else if (iAccion == 2) {                
                    res = result.actualizarEstatusMIR(year,selRamo,selPrograma,estatusSiguiente);
                    if (!res) {
                        errorMsg = "No se pudo Validar la MIR. ";
                    }
                    if (!result.guardar()) {
                        errorMsg = "No se pudo Guardar el MIR. ";
                    }
                    accion = "validado";                
            } else if (iAccion == 3) {                
                    res = result.actualizarEstatusMIR(year,selRamo,selPrograma,estatusSiguiente);
                    if(res){
                        res = result.registraObsRechazoMIR(year,selRamo,selPrograma,observacion);
                        if (!res) {
                            errorMsg = "No se puedo registrar la observación de rechazo de la MIR. ";
                        }
                        if (!result.guardar()) {
                            errorMsg = "No se pudo Guardar el cambio. ";
                        }
                    } else {
                        errorMsg = "No se pudo Rechazar la MIR. ";
                    }
                    accion ="rechazado";                                 
            } else if(iAccion == 4) {                
                    resultSQL.setStrServer(request.getHeader("host"));
                    resultSQL.setStrUbicacion(getServletContext().getRealPath(""));
                    resultSQL.resultSQLConecta(tipoDependencia);       
                    
                    mir = result.getMIREstatusEtapa(mir);                           
                    estatusMIR = mir.getEstatus();  
                    etapaMIR = mir.getEtapa();
                    
                    if(estatusMIR >= 4){
                        estatusSigEnvia = EnumEstatusMIR.ENVIADA_POS.ordinal();
                        estatusSigValida = EnumEstatusMIR.VALIDADA_POS.ordinal();
                        estatusSigRechaza = EnumEstatusMIR.RECHAZADA_POS.ordinal();
                    } 
                    if (result.muestraBotonEnviarMIR(estatusMIR)) {
                        bEnviar = true;
                    }                    
                    if (rol.equals(resultSQL.getResultSQLGetRolesPrg()) && result.muestraBotonValidarRechazarMIR(estatusMIR)) {
                        bValidarRechazar = true;
                    }
                    if(estatusMIR == EnumEstatusMIR.RECHAZADA.ordinal() || estatusMIR == EnumEstatusMIR.RECHAZADA_POS.ordinal()){
                        obsRechazo = result.getObsRechazoMIR(String.valueOf(year), selRamo, selPrograma);
                    }
                    if(result.editarMIR(estatusMIR, rol, resultSQL.getResultSQLGetRolesPrg())){
                        bEditarMIR = true;
                    }                
            }
        } else {
            errorMsg = "Datos incorrectos para la solicitud";
        }       
        if(iAccion != 4){
            strResultado = new JSONObject()
              .put("exito", res)
              .put("mensaje", errorMsg != "" ? errorMsg : "Se ha " + accion + " la MIR correctamente." ).toString();
        } else {
            strResultado = new JSONObject()
              .put("etapaMIR", etapaMIR)
              .put("estatusMIR", estatusMIR)
              .put("bEditarMIR", bEditarMIR)
              .put("estatusSigEnvia", estatusSigEnvia)
              .put("estatusSigRechaza", estatusSigRechaza)
              .put("estatusSigValida", estatusSigValida)
              .put("bEnviar", bEnviar)
              .put("bValidarRechazar", bValidarRechazar)
              .put("obsRechazo", obsRechazo)
              .toString();
        }
    } catch (Exception ex) {
        strResultado = new JSONObject()
              .put("exito", false)
              .put("mensaje", "Ocurrio un error, favor de intentarlo mas tarde.").toString();
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(Bitacora.TIPO_ERROR);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (resultSQL != null) {
            resultSQL.resultSQLDesconecta();
        }
        if (result != null) {
            result.resultSQLDesconecta();
        }
       out.print(strResultado);
    }
%>

