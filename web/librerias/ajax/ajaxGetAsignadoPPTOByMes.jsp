<%-- 
    Document   : ajaxGetAsignadoPPTOByMes
    Created on : Jan 13, 2016, 11:52:14 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    AmpliacionReduccionBean ampRedBean = null;
    String strResultado = new String();
    String ramo = new String();
    String tipoDependencia = new String();
    String prg = new String();
    String proy = new String();
    String tipoProy = new String();
    String partida = new String();
    String relLaboral = new String();
    String fuente = new String();
    String fuenteFin[] = new String[3];
    int year = 0;
    int proyecto = -1;
    int meta = -1;
    int accion = -1;
    int mes = 0;
    double asigando = 0.0;
    
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
        if(request.getParameter("programa") != null && !request.getParameter("programa").equals("")){
             prg = request.getParameter("programa");
        }
        if(request.getParameter("meta") != null && !request.getParameter("meta").equals("")){
             meta = Integer.parseInt(request.getParameter("meta"));
        }
        if(request.getParameter("accion") != null && !request.getParameter("accion").equals("")){
             accion = Integer.parseInt(request.getParameter("accion"));
        }
        if(request.getParameter("partida") != null && !request.getParameter("partida").equals("")){
             partida = request.getParameter("partida");
        }
        if(request.getParameter("relLaboral") != null && !request.getParameter("relLaboral").equals("")){
             relLaboral = request.getParameter("relLaboral");
             if(relLaboral.equals("-1"))
                 relLaboral = "0";
        }
        if(request.getParameter("fuente") != null && !request.getParameter("fuente").equals("")){
             fuente =  request.getParameter("fuente");
             fuenteFin = fuente.split("\\.");
        }        
        if(Utilerias.existeParametro("mesPPTO", request)){
            mes = Integer.parseInt( request.getParameter("mesPPTO"));
        }
        if(Utilerias.existeParametro("proyecto", request)){
            proy =  request.getParameter("proyecto");
            proyecto = Integer.parseInt(proy.split(",")[0].trim());
            tipoProy = String.valueOf(proy.split(",")[1].trim());
        }
        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer(request.getHeader("host"));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRedBean.resultSQLConecta(tipoDependencia);
        /*if(ampRedBean.getResultSQLisParaestatal() && !ampRedBean.getResultSqlGetIsAyuntamiento()){
            asigando = ampRedBean.getResultSQLgetDisponibleParaestatal(year, mes, ramo, prg, 
                    tipoProy, proyecto, meta, accion, partida,relLaboral,
                    fuenteFin[0], fuenteFin[1], fuenteFin[2]);
        }else*/
        asigando = ampRedBean.getDisponible(year, ramo, prg, tipoProy, proyecto, 
                meta, accion, partida, fuenteFin[0], fuenteFin[1], fuenteFin[2], relLaboral, mes);
        strResultado += "1|"+asigando;
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (ampRedBean != null) {
            ampRedBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>