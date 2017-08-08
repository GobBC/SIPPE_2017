<%-- 
    Document   : ajaxGetRequerimientosByFuenteFinanciamientoUsuario
    Created on : Dec 8, 2015, 12:23:11 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;
    List<Requerimiento> requList = new ArrayList<Requerimiento>();
    String strResultado = new String();
    String ramo = new String();
    String appLogin = new String();
    String tipoDependencia = new String();
    String prg = new String();
    String partida = new String();
    String relLaboral = new String();
    String fuente = new String();
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
        if(request.getParameter("fuente") != null && !request.getParameter("fuente").equals("")){
             fuente = (String) request.getParameter("fuente");
        }
        
        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        requList = recalBean.getRequerimientoByFuenteFinanciamientoUsuario(year, ramo, prg, meta, 
                accion, partida, relLaboral, fuente);
        if(requList.size() > 0){
            strResultado += "<option value='-1'> -- Seleccione un requerimiento -- </option>";
            for(Requerimiento req : requList){
                strResultado += "<option value='"+req.getReqId()+"'> "
                        + ""+req.getReqId()+"-"+req.getReq()+"</option>";
            }
        }else{
            strResultado = "0|Esta fuente de financiamiento no tiene requerimientos asiganadas";
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