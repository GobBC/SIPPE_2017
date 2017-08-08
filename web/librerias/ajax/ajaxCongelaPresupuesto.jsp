<%-- 
    Document   : ajaxCongelaPresupuesto
    Created on : Dec 3, 2015, 2:59:16 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.CongelaPresupuestoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int year = 0;
    int total = 0;
    String tipoDependencia = new String();
    String strResultado = new String();
    String mensaje = new String();
    CongelaPresupuestoBean congelaPresupuestoBean = null;
    
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }        
        congelaPresupuestoBean = new CongelaPresupuestoBean(tipoDependencia);
        congelaPresupuestoBean.setStrServer(request.getHeader("host"));
        congelaPresupuestoBean.setStrUbicacion(getServletContext().getRealPath(""));
        congelaPresupuestoBean.resultSQLConecta(tipoDependencia);
        total = congelaPresupuestoBean.getCountPresupuestoCongelado(year);
        if(total == 0){
            mensaje = congelaPresupuestoBean.congelaPresupuesto(year);
            if(mensaje.equals("1"))
                strResultado = mensaje + "|" + "La información se congeló exitosamente";
            else
                strResultado = mensaje;
        }else{
            strResultado = "0|La información para este año ya fue congelada anteriormente";
        }
    }catch(Exception ex){
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    }finally{
        if(congelaPresupuestoBean != null)
            congelaPresupuestoBean.resultSQLDesconecta();
        out.print(strResultado);
    }
%>
