<%-- 
    Document   : ajaxGetAllMetasByRamo
    Created on : Nov 22, 2016, 1:18:25 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.MigracionRequerimientoBean"%>
<%
    MigracionRequerimientoBean migracionBean = null;
    
    List<Meta> metaList = new ArrayList<Meta>();
    
    String strResultado = new String();
    String ramo = new String();
    String tipoDependencia = new String();
    
    int year = 0;
    
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")){
             ramo = request.getParameter("ramo");
        }
        
        migracionBean = new MigracionRequerimientoBean(tipoDependencia);
        migracionBean.setStrServer(request.getHeader("host"));
        migracionBean.setStrUbicacion(getServletContext().getRealPath(""));
        migracionBean.resultSQLConecta(tipoDependencia);
        metaList = migracionBean.getResultgetAllMetasByRamo(ramo, year);
        strResultado += "<option value='-1'> -- Seleccione una meta -- </option>";
        for(Meta meta : metaList){
            strResultado += "<option value='"+meta.getMetaId()+"'> "
                    + ""+meta.getMetaId()+"-"+meta.getMeta()+"</option>";
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
        if (migracionBean != null) {
            migracionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
