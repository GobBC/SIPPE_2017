<%-- 
    Document   : ajaxMigrarAcciones
    Created on : Nov 22, 2016, 4:44:43 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.MigracionRequerimientoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    MigracionRequerimientoBean migracionBean = null;

    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    String usuario = new String();

    int metaAct = 0;
    int metaNueva = 0;
    int accionAct = 0;
    int accionNueva = 0;
    int year = 0;

    boolean resultado = false;


    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (Utilerias.existeParametro("ramo", request)) {
            ramo = request.getParameter("ramo");
        }
        if(Utilerias.existeParametro("metaAct", request))
            metaAct = Integer.parseInt(request.getParameter("metaAct"));
        
        if(Utilerias.existeParametro("metaNew", request))
            metaNueva = Integer.parseInt(request.getParameter("metaNew"));
        
        if(Utilerias.existeParametro("accionAct", request))            
            accionAct = Integer.parseInt(request.getParameter("accionAct"));
        
        if(Utilerias.existeParametro("accionNew", request))            
            accionNueva = Integer.parseInt(request.getParameter("accionNew"));

        migracionBean = new MigracionRequerimientoBean(tipoDependencia);
        migracionBean.setStrServer(request.getHeader("host"));
        migracionBean.setStrUbicacion(getServletContext().getRealPath(""));
        migracionBean.resultSQLConecta(tipoDependencia);
        
        resultado = migracionBean.migrarAccion(year, ramo, metaAct, accionAct, metaNueva, accionNueva, usuario);
        
        if(resultado){
            migracionBean.transaccionCommit();
            strResultado = "1";
        }else{
            migracionBean.transaccionRollback();
            strResultado = "0";
        }
        
    } catch (Exception ex) {
        migracionBean.transaccionRollback();
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
        strResultado = "-1";
    } finally {
        if (migracionBean != null) {
            migracionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
