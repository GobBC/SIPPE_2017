<%-- 
    Document   : ajaxGetProgramasByRamo
    Created on : Mar 20, 2015, 4:08:07 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Programa"%>
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
    List<Programa> programaList = new ArrayList<Programa>();
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
        if(request.getParameter("ramos") != null && !request.getParameter("ramos").equals("")){
             ramoId = (String)request.getParameter("ramos");
             ramoId = ramoId.replace("'", "");
        }
        if(request.getParameter("rep") != null && !request.getParameter("rep").equals("")){
             rep = Integer.parseInt((String)request.getParameter("rep"));
        }
        if(!ramoId.equals("-1")){
            if(rep == 1){
                cierre = true;
            }else{
                cierre = programaBean.validaRamo(ramoId,year,rol,usuario);
            }
            if(cierre){
                programaList = programaBean.getProgramasByRamo(ramoId, year, usuario);            
                strResultado += "   <option value='-1'>";
                
                strResultado += "       -- Seleccione un programa --";
                strResultado += "   </option>";
                for(Programa programa : programaList){
                    strResultado += "<option value='"+ programa.getProgramaId() +"'>";
                    strResultado +=  programa.getProgramaId() +  "-" + programa.getProgramaDesc();
                    strResultado += "</option>";
                }
            }else{
                strResultado = "-2";
            }
        }else{
            strResultado += "   <option value='-1'>";
            strResultado += "       --Seleccione un programa--";
            strResultado += "   </option>";
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