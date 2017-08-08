<%-- 
    Document   : ajaxEvaluaCaratula
    Created on : 30/05/2017, 09:36:28 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CaratulaBean caratulaBean = null;

    String strResult = new String();
    String tipoDependencia = new String();
    String usuario = new String();
    
    int year = 0;
    int caratulaID = 0;
    
    
    try{
        
        if(session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        
        if(Utilerias.existeParametro("caratulaID", request)){
            caratulaID = Integer.parseInt(request.getParameter("caratulaID"));
        }
        
        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);
        
        if(!caratulaBean.isCaratulaEvaluada(caratulaID)){
            if(caratulaBean.getUpdateEvaluacionCaratula(caratulaID, year)){
                if(caratulaBean.getInsertBitacoraEvaluacion(usuario, caratulaID, year))
                    strResult += "1";
                else{
                    strResult += "No se pudo insertar bitácora";
                    caratulaBean.transaccionRollback();
                }
            }else{
                strResult += "No se pudo actualizar el movimiento";
                caratulaBean.transaccionRollback();
            }
        }else{
            strResult += "Este movimiento ya fue evaluado anteriormente";
        }
        
    }catch (Exception ex) {
        strResult = "No es posible actualizar la informacion";
        caratulaBean.transaccionRollback();
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
