<%-- 
    Document   : ajaxGetProgramasByRamo
    Created on : Mar 20, 2015, 4:08:07 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.entidades.Funcion"%>
<%@page import="gob.gbc.entidades.Subfuncion"%>
<%@page import="gob.gbc.aplicacion.ProgramaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
    String strResultado = new String();
    String ramoId = new String();
    boolean cierre = false;
    ProgramaBean programaBean = null;
    int year = 0;
    int rep = 0;
    String rol = new String();
    String usuario = new String();
    String tipoDependencia = new String();
    String strFinalidad1="";
    String strFinalidad2="";
    String strFuncion1="";
    String strFuncion2="";
    List<Subfuncion> subfuncionList = new ArrayList<Subfuncion>();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != ""){
            usuario = (String)session.getAttribute("strUsuario");
        }
        if ((String) session.getAttribute("strRol") != null) {
            rol = (String) session.getAttribute("strRol");
        }
        programaBean = new ProgramaBean(tipoDependencia);
        programaBean.setStrServer(request.getHeader("host"));
        programaBean.setStrUbicacion(getServletContext().getRealPath(""));
        programaBean.resultSQLConecta(tipoDependencia);
        if(request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")){
             ramoId = (String)request.getParameter("ramo");
        }
     
        if(request.getParameter("finalidad1") != null && !request.getParameter("finalidad1").equals("")){
           strFinalidad1=request.getParameter("finalidad1");
        }
        
        if(request.getParameter("finalidad2") != null && !request.getParameter("finalidad2").equals("")){
           strFinalidad2=request.getParameter("finalidad2");
        }
        
        if(request.getParameter("funcion1") != null && !request.getParameter("finalidad1").equals("")){
           strFuncion1=request.getParameter("funcion1");
        }
        
        if(request.getParameter("funcion2") != null && !request.getParameter("finalidad2").equals("")){
           strFuncion2=request.getParameter("funcion2");
        }
        
        
        
        
                subfuncionList = programaBean.getResultSubfuncionByYearFinalidadFuncion(year,strFinalidad1,strFinalidad2,strFuncion1,strFuncion2);
                strResultado += "   <option value='-1'>";
                
                strResultado += "       -- Seleccione una subfuncion --";
                strResultado += "   </option>";
                for(Subfuncion subfuncion : subfuncionList){
                    strResultado += "<option value='"+ subfuncion.getSubfuncion() +"'>";
                    strResultado +=  subfuncion.getSubfuncion() +  "-" + subfuncion.getDescr();
                    strResultado += "</option>";
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
        if (programaBean != null) {
            programaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>