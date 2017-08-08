<%-- 
    Document   : ajaxGetFuenteFinanciamientolByRelLaboralUsuario
    Created on : Dec 8, 2015, 11:15:59 AM
    Author     : ugarcia
--%>

<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="gob.gbc.entidades.RelacionLaboral"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Fuente"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;
    List<Fuente> fuenteList = new ArrayList<Fuente>();
    String strResultado = new String();
    String ramo = new String();
    String appLogin = new String();
    String tipoDependencia = new String();
    String prg = new String();
    String proy = new String();
    String tipoProy = new String();
    String partida = new String();
    String relLaboral = new String();
    int year = 0;
    int proyecto = -1;
    int meta = -1;
    int accion = -1;
    
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != ""){
            appLogin = (String)session.getAttribute("strUsuario");
        }
        if(request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")){
             ramo = (String)request.getParameter("ramo");
        }
        if(request.getParameter("programa") != null && !request.getParameter("programa").equals("")){
             prg = (String)request.getParameter("programa");
        }
        if(request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")){
             proy = request.getParameter("proyecto");
             proyecto = Integer.parseInt(proy.split(",")[0]);
             tipoProy = proy.split(",")[1];
        }
        if(request.getParameter("meta") != null && !request.getParameter("meta").equals("")){
             meta = Integer.parseInt(request.getParameter("meta"));
        }
        if(request.getParameter("accion") != null && !request.getParameter("accion").equals("")){
             accion = Integer.parseInt(request.getParameter("accion"));
        }
        if(request.getParameter("partida") != null && !request.getParameter("partida").equals("")){
             partida = (String) request.getParameter("partida");
        }
        if(request.getParameter("relLaboral") != null && !request.getParameter("relLaboral").equals("")){
             relLaboral = (String) request.getParameter("relLaboral");
        }
        
        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        fuenteList = recalBean.getFuenteFinanciamientoByRelLaboralUsuario(year, 
                appLogin, ramo, prg, proyecto, tipoProy, meta, accion, partida, relLaboral);
        if(fuenteList.size() > 0){
            strResultado += "<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>";
            for(Fuente fuente : fuenteList){
                strResultado += "<option value='"+fuente.getFuente()+"'> "
                        + ""+fuente.getFuente()+"-"+fuente.getDescr()+"</option>";
            }
        }else{
            strResultado = "0|Esta relación laboral no tiene fuentes de financiamiento asiganadas";
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
        if (recalBean != null) {
            recalBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>