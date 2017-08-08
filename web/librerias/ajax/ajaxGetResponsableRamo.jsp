<%-- 
    Document   : ajaxGetResponsableRamo
    Created on : Mar 18, 2015, 4:14:54 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String tipoDependencia = new String();
    int ramoId = 0;
    RamoBean ramoBean = null;
    Ramo ramo = new Ramo();
    int year = 0;
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")){
             ramoId = Integer.parseInt((String)request.getParameter("ramo"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        ramoBean = new RamoBean(tipoDependencia);
        ramoBean.setStrServer(request.getHeader("host"));
        ramoBean.setStrUbicacion(getServletContext().getRealPath(""));
        ramoBean.resultSQLConecta(tipoDependencia);
        if(ramoId != -1){
            
            ramo = ramoBean.getRamoByIdAndYear(ramoId, year);
            if(ramo != null){
                if(ramo.getRfc() == null){
                    ramo.setRfc("Sin definir");
                }
                if(ramo.getHomoclave() == null){
                    ramo.setHomoclave("Sin definir");
                }
                strResultado = "<div id=\"datos-responsable\"> \n";
                strResultado += "   <label>RFC:</label>";
                strResultado += "   <input id='inTxtRFC' type=\"text\" disabled=\"true\" value=\"" + ramo.getRfc() + "\"/>";
                strResultado += "   <label>HOMOCLAVE:</label>";
                strResultado += "   <input id='inTxtHC' type=\"text\" disabled=\"true\" value=\" " + ramo.getHomoclave()+ "\"/>";
                strResultado += "   <br/>";
                strResultado += "   <label> NOMBRE: </label>";
                strResultado += "   <input id='inTxtNombreC' type='text' disabled='true' value='"+ramo.getNombreResponsable()+"' style='width:350px;  margin-top: 10px;'/>";
                strResultado += "</div>";
                
                strResultado += "<center> <input id='actualizarResp'  type=\"button\" onClick=\"mostrarBusqueda()\"/> </center>";
            }else {
                strResultado = "Ocurrió un problema al procesar su solicitud.";
            }
        }else{
            strResultado = "<div id=\"datos-responsable\"> \n";
            strResultado += "   <label>RFC:</label>";
            strResultado += "   <input id=\"inpTxRFC\" type=\"text\" disabled=\"true\" value=\" \"/>";
            strResultado += "   <label>HOMOCLAVE:</label>";
            strResultado += "   <input id=\"inpTxHomo\" type=\"text\" disabled=\"true\" value=\" \"/>";
            strResultado += "</div>";
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
        if (ramoBean != null) {
            ramoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
