<%-- 
    Document   : ajaxEvaluaMovimiento
    Created on : 29/05/2017, 01:53:36 PM
    Author     : ugarcia
--%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.MovimientosBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%    
    MovimientosBean movimientoBean = null;

    String strResult = new String();
    String tipoDependencia = new String();
    String usuario = new String();
    
    int year = 0;
    int oficio = 0;
    
    
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
        
        if(Utilerias.existeParametro("oficio", request)){
            oficio = Integer.parseInt(request.getParameter("oficio"));
        }
        
        movimientoBean = new MovimientosBean(tipoDependencia);
        movimientoBean.setStrServer((request.getHeader("Host")));
        movimientoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        movimientoBean.resultSQLConecta(tipoDependencia);
        
        if(!movimientoBean.isMovimientoEvaludo(oficio)){
            if(movimientoBean.getUpdateEvaluacionMovto(oficio)){
                if(movimientoBean.getInsertBitacoraEvaluacion(usuario, oficio, year))
                    strResult += "1";
                else{
                    strResult += "No se pudo insertar bitácora";
                    movimientoBean.transaccionRollback();
                }
            }else{
                strResult += "No se pudo actualizar el movimiento";
                movimientoBean.transaccionRollback();
            }
        }else{
            strResult += "Este movimiento ya fue evaluado anteriormente";
        }
        
    }catch (Exception ex) {
        movimientoBean.transaccionRollback();
        strResult = "No es posible actualizar la informacion";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (movimientoBean != null) {
            movimientoBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
    
%>
