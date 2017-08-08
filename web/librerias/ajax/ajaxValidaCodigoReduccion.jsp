<%-- 
    Document   : ajaxValidaCodigoReduccion
    Created on : Nov 3, 2016, 10:37:57 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.CodigoPPTO"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
    AmpliacionReduccionBean ampRedBean = null;
    CodigoPPTO codigoPPTO = new CodigoPPTO();
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    String programa = new String();
    String partida = new String();
    String fuente = new String();
    String fuenteFin[] = new String[3];
    String relLaboral = new String();
    String proyecto = new String();
    String tipoProy = new String();
    int  proy = 0;
    int meta = 0;
    int  accion = 0 ;
    int year = 0;
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramo") != null
                && !request.getParameter("ramo").equals("")) {
            ramo = request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null
                && !request.getParameter("programa").equals("")) {
            programa = request.getParameter("programa");
        }
        if (request.getParameter("proyecto") != null
                && !request.getParameter("proyecto").equals("")) {
            proyecto = request.getParameter("proyecto");
            proy = Integer.parseInt(proyecto.split(",")[0]);
            tipoProy = proyecto.split(",")[1];
        }
        if (request.getParameter("meta") != null
                && !request.getParameter("meta").equals("")) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        if (request.getParameter("accion") != null
                && !request.getParameter("accion").equals("")) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        if (request.getParameter("partida") != null
                && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        
        if (request.getParameter("relLaboral") != null
                && !request.getParameter("relLaboral").equals("")) {
            relLaboral =  request.getParameter("relLaboral");
            if(relLaboral.equals("-1")){
                relLaboral = "0";
            }
        }
        if (request.getParameter("fuente") != null
                && !request.getParameter("fuente").equals("")) {
            fuente =  request.getParameter("fuente");
            fuenteFin = fuente.split("\\.");
        }
        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer(request.getHeader("host"));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRedBean.resultSQLConecta(tipoDependencia);
        
        codigoPPTO = ampRedBean.getCodigoProgramaticoByRequerimiento(year, ramo, programa, tipoProy, 
            proy, meta, accion, partida, fuenteFin[0], fuenteFin[1], fuenteFin[2],
            relLaboral);
            
        if(!ampRedBean.getResultSQLisCodigoRepetido(year,
                codigoPPTO.getRamoId(),
                codigoPPTO.getDepto(),
                codigoPPTO.getFinalidad(),
                codigoPPTO.getFuncion(),
                codigoPPTO.getSubfuncion(),
                codigoPPTO.getProgCONAC(),
                codigoPPTO.getPrograma(),
                codigoPPTO.getTipoProy(),
                Integer.parseInt(codigoPPTO.getProyecto()),
                codigoPPTO.getMeta(),
                codigoPPTO.getAccion(),
                codigoPPTO.getPartida(),
                codigoPPTO.getTipoGasto(),
                codigoPPTO.getFuente(),
                codigoPPTO.getFondo(),
                codigoPPTO.getRecurso(),
                codigoPPTO.getMunicipio(),
                codigoPPTO.getDelegacion(),
                codigoPPTO.getRelLaboral())){
            strResultado = "2|Este código no existe en el presupuesto " + year;
        }else{
            strResultado = "1";
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
        if (ampRedBean != null) {
            ampRedBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
