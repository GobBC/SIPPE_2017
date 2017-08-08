<%-- 
    Document   : ajaxCapturaMIR
    Created on : Jun 28, 2017, 3:28:15 PM
    Author     : ealonso
--%>

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
    String year =  "";
    String strResultado = "";
    boolean res = false;
    String strProgramaH = "";
    String errorMsg = "";
    Utilerias utileria = null;
    boolean bError = false;
    int iAccion = 0;
    String entidad = "";
    ObjectMapper mapper = new ObjectMapper();
    String accion = "guardado";       
    MIR data = new MIR();
    Map<String, Object> map = null;
    String selRamo = new String();
    String selPrograma = new String();
    String selStatus = new String();
    if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
        tipoDependencia = (String) session.getAttribute("tipoDependencia");
    }    
    if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
        year = (String) session.getAttribute("year");
    }

    ResultSQLCatalogos result = new ResultSQLCatalogos(tipoDependencia);
    result.setStrServer(request.getHeader("host"));
    request.setCharacterEncoding("UTF-8");
    
    try {
        utileria = new Utilerias();       
        result.resultSQLConecta(tipoDependencia);

        if(utileria.existeParametro("selRamo", request)){
            selRamo = request.getParameter("selRamo");
        }
        if(utileria.existeParametro("selPrograma", request)){
            selPrograma = request.getParameter("selPrograma");
        }
        
        if (utileria.existeParametro("accion", request)) {

            iAccion = Integer.parseInt(request.getParameter("accion"));
            
            if (iAccion != 4) {
            
                entidad = request.getParameter("entidad");
                if(iAccion == 2 || iAccion == 3){
                    data = new Gson().fromJson(entidad, MIR.class);
                }
                strProgramaH = request.getParameter("programaAnt");
                map = mapper.readValue(entidad, new TypeReference<Map<String,Object>>() { });
                
            }
            
            if (iAccion == 1) {
                
                    res = result.InsertarMIR(year,map.get("ramo").toString(),map.get("prg").toString());
                    if (!res) {
                        errorMsg = "No se pudo Insertar el MIR. Verifique si ya esta capturado. ";
                    } else if (!result.guardar()) {
                        errorMsg = "No se pudo Guardar el MIR. ";
                    }             
                    accion = "agregado";
                
            }
            if (iAccion == 2) {
                
                    res = result.actualizarMIR(year,data.getRamo(),data.getPrg(), strProgramaH);
                    if (!res) {
                        errorMsg = "No se pudo Insertar el MIR. Verifique si ya esta capturado. ";
                    } else if (!result.guardar()) {
                        errorMsg = "No se pudo Guardar el MIR. ";
                    }
                    accion = "editado";
                
            }

            if (iAccion == 3) {
                
                res = result.borrarMIR(year,map.get("ramo").toString(), map.get("programa").toString());
                if (!res) {
                    errorMsg = "No se puede Borrar esta MIR ";
                } else if (!result.guardar()) {
                    errorMsg = "No se pudo Guardar el cambio. ";
                }
                accion ="eliminado";
                
            }
            
            if (iAccion == 4) {
                
                res = result.validaPropositoMIR(year,selRamo,selPrograma);
                if (!res) {
                    errorMsg = "No es posible editar la MIR. Verifique que el propósito haya sido capturado. ";
                }
                
            }
        }
        
        strResultado = new JSONObject()
                  .put("exito", res)
                  .put("mensaje", errorMsg != "" ? errorMsg : "Se ha " + accion + " el registro correctamente." ).toString();
        

    } catch (Exception ex) {
        result.transaccionRollback();
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
        if (result != null) {
            result.resultSQLDesconecta();
        }
       out.print(strResultado);
    }

%>

