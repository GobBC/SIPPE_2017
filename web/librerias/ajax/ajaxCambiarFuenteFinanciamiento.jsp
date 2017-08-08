<%-- 
    Document   : ajaxCambiarFuenteFinanciamiento
    Created on : Sep 13, 2016, 8:25:05 AM
    Author     : ugarcia
--%>

<%@page import="java.util.Date"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.MigracionRequerimientoBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    MigracionRequerimientoBean migracionBean = null;
    
    String strResultado = new String();
    String tipoDependencia = new String();
    String fuenteFinanciamiento = new String();
    String fuenteFinanciamientoN = new String();
    String fuente = new String();
    String fondo = new String();
    String recurso = new String();
    String fuenteN = new String();
    String fondoN = new String();
    String recursoN = new String();
    String ramo = new String();
    String usuario = new  String();
    
    int meta = 0;
    int accion = 0;
    
    String accionList[] = null;
    
    boolean resultado = false;
    
    int year = 0;
    
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (Utilerias.existeParametro("fuente", request)) {
            fuenteFinanciamiento = request.getParameter("fuente");
            fuente = fuenteFinanciamiento.split("\\.")[0];
            fondo = fuenteFinanciamiento.split("\\.")[1];
            recurso = fuenteFinanciamiento.split("\\.")[2];
        }
        if (Utilerias.existeParametro("fuenteN", request)) {
            fuenteFinanciamientoN = request.getParameter("fuenteN");
            fuenteN = fuenteFinanciamientoN.split("\\.")[0];
            fondoN = fuenteFinanciamientoN.split("\\.")[1];
            recursoN = fuenteFinanciamientoN.split("\\.")[2];
        }
        if (Utilerias.existeParametro("ramo", request)) {
            ramo = request.getParameter("ramo");
        }
        
        if(request.getParameterValues("accionArray[]") != null && request.getParameterValues("accionArray[]").length > 0){
            accionList = request.getParameterValues("accionArray[]");
        }
        
        migracionBean = new MigracionRequerimientoBean(tipoDependencia);
        migracionBean.setStrServer(request.getHeader("host"));
        migracionBean.setStrUbicacion(getServletContext().getRealPath(""));
        migracionBean.resultSQLConecta(tipoDependencia);
        System.out.println(new Date() +"- Comienza cambio de fuente de financiamiento");
        for(String metaAccion : accionList){
            meta = Integer.parseInt(metaAccion.split("\\|")[0].trim());
            accion = Integer.parseInt(metaAccion.split("\\|")[1].trim());
            resultado = migracionBean.cambiaFuenteFinanciamientoByMetaAccion(
                    year, ramo, meta, accion, fuente, fondo, recurso,
                    fuenteN, fondoN, recursoN,usuario); 
            if(!resultado)
                break;
        }
        if(resultado){
            System.out.println(new Date() +"- termina cambio de fuente de financiamiento exitoso");
            migracionBean.transaccionCommit();            
        }else{
            System.out.println(new Date() +"- termina cambio de fuente de financiamiento error");
            strResultado = "-2";
            migracionBean.transaccionRollback();        
        }
    }catch (Exception ex) {
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
