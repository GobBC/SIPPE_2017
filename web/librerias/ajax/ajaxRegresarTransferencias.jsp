<%-- 
    Document   : ajaxRegresarTransferencias
    Created on : Feb 16, 2016, 9:14:49 AM
    Author     : ugarcia
--%>

<%@page import="java.lang.String"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.CodigoPPTO"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    TransferenciaBean transBean = null;
    MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
    Transferencia transferencia = new Transferencia();
    Transferencia transferenciaAux = new Transferencia();
    CodigoPPTO codigoPPTO = new CodigoPPTO();
    String ramo = new String();
    String programa = new String();
    String tipoProy = new String();
    String partida = new String();
    String fuente = new String();
    String relLaboral = new String();
    String strResultado = new String();
    String tipoDependencia = new String();
    String estatus = new String();
    String fuenteFin[] = new String[3];
    double importe = 0.0;
    int proyecto = 0;
    int meta = 0;
    int accion = 0;
    int transferenciaId = 0;
    int opcion = 0;
    int year = 0;
    int mesActual = 0;    
    Long idCaratula = Long.parseLong("0");   
    
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (Utilerias.existeParametro("transferenciaId", request)) {
            transferenciaId = Integer.parseInt(request.getParameter("transferenciaId"));
        }
        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            movTransferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
        }
        if (session.getAttribute("transferenciaTemp") != null && session.getAttribute("transferenciaTemp") != "") {
            transferencia = (Transferencia) session.getAttribute("transferenciaTemp");
            session.removeAttribute("transferenciaTemp");
        }
        if (Utilerias.existeParametro("ramo", request)) {
            ramo = request.getParameter("ramo");
        }
        if (Utilerias.existeParametro("programa", request)) {
            programa = request.getParameter("programa");
        }
        if (Utilerias.existeParametro("proyecto", request)) {
            proyecto = Integer.parseInt(request.getParameter("proyecto"));
        }
        if (Utilerias.existeParametro("tipoProy", request)) {
            tipoProy = request.getParameter("tipoProy");
        }
        if (Utilerias.existeParametro("meta", request)) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        if (Utilerias.existeParametro("accion", request)) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        if (Utilerias.existeParametro("partida", request)) {
            partida = request.getParameter("partida");
        }
        if (Utilerias.existeParametro("fuente", request)) {
            fuente = request.getParameter("fuente");
            fuenteFin = fuente.split("\\.");
        }
        if (Utilerias.existeParametro("relLaboral", request)) {
            relLaboral = request.getParameter("relLaboral");
        }
        if (Utilerias.existeParametro("importeTrans", request)) {
            importe = new Double(request.getParameter("importeTrans"));
        }
        if (Utilerias.existeParametro("mesActual", request)) {
            mesActual = Integer.parseInt(request.getParameter("mesActual"));
        }
        if (Utilerias.existeParametro("estatus", request)) {
            estatus = request.getParameter("estatus");
        }
        if (Utilerias.existeParametro("opcion", request)) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (Utilerias.existeParametro("idCaratula", request)) {
            idCaratula = Long.parseLong(request.getParameter("idCaratula"));
        }
        transBean = new TransferenciaBean(tipoDependencia);
        transBean.setStrServer(request.getHeader("host"));
        transBean.setStrUbicacion(getServletContext().getRealPath(""));
        transBean.resultSQLConecta(tipoDependencia);
        if (opcion == 1) {
            for (Transferencia transferenciatemp : movTransferencia.getTransferenciaList()) {
                if (transferenciatemp.getConsec() == transferenciaId) {
                    codigoPPTO = transBean.getCodigoProgramaticoByRequerimiento(year, ramo, programa, tipoProy,
                            proyecto, meta, accion, partida, fuenteFin[0], fuenteFin[1], fuenteFin[2], relLaboral);
                    if (codigoPPTO != null) {
                        strResultado = "1|Exito";
                    } else {
                        strResultado = "2|Ocurrió un error al obtener la transferencia";
                        break;
                    }
                    transferenciatemp.setConsec(transferenciaId);
                    transferenciatemp.setRamo(codigoPPTO.getRamoId());
                    transferenciatemp.setDepto(codigoPPTO.getDepto());
                    transferenciatemp.setFinalidad(codigoPPTO.getFinalidad());
                    transferenciatemp.setFuncion(codigoPPTO.getFuncion());
                    transferenciatemp.setSubfuncion(codigoPPTO.getSubfuncion());
                    transferenciatemp.setPrgConac(codigoPPTO.getProgCONAC());
                    transferenciatemp.setPrograma(codigoPPTO.getPrograma());
                    transferenciatemp.setTipoProy(codigoPPTO.getTipoProy());
                    transferenciatemp.setProyecto(Integer.parseInt(codigoPPTO.getProyecto()));
                    transferenciatemp.setMeta(codigoPPTO.getMeta());
                    transferenciatemp.setAccion(codigoPPTO.getAccion());
                    transferenciatemp.setPartida(codigoPPTO.getPartida());
                    transferenciatemp.setTipoGasto(codigoPPTO.getTipoGasto());
                    transferenciatemp.setFuente(codigoPPTO.getFuente());
                    transferenciatemp.setFondo(codigoPPTO.getFondo());
                    transferenciatemp.setRecurso(codigoPPTO.getRecurso());
                    transferenciatemp.setMunicipio(codigoPPTO.getMunicipio());
                    transferenciatemp.setDelegacion(codigoPPTO.getDelegacion());
                    transferenciatemp.setRelLaboral(codigoPPTO.getRelLaboral());
                    transferenciatemp.setEstatus(estatus);
                    transferenciatemp.setImporte(importe);
                    transferenciatemp.setQuincePor(transBean.getQuincePorCiento(partida, ramo, year));
                    transferenciatemp.setAcumulado(transBean.getResultSQLgetAcumluladoMovtos(year, partida, ramo));
                    transferenciatemp.setConsiderar("S");
                    /*if(transBean.getResultSQLisParaestatal() && !transBean.getResultSqlGetIsAyuntamiento())
                        transferenciatemp.setDisponible(transBean.getResultSQLgetDisponibleParaestatal(year,
                                mesActual, ramo, programa, tipoProy, proyecto, meta, accion, partida, relLaboral,
                                fuenteFin[0],fuenteFin[1],fuenteFin[2]));
                    else*/
                    transferenciatemp.setDisponible(transBean.getDisponible(year, ramo, programa, 
                                tipoProy, proyecto, meta, accion, partida, fuenteFin[0], 
                                fuenteFin[1], fuenteFin[2], relLaboral, mesActual));
                    transferenciatemp.setDisponibleAnual(transBean.getResultSQLgetImporteAnual(year, ramo, programa, 
                                tipoProy, proyecto, meta, accion, partida, fuenteFin[0], 
                                fuenteFin[1], fuenteFin[2], relLaboral, 12));
                    break;
                }
            }
        } else {
            for (Transferencia transferenciatemp : movTransferencia.getTransferenciaList()) {
                if (transferenciatemp.getConsec() == transferenciaId) {
                    transferenciaAux = transferenciatemp;
                }
            }
            movTransferencia.getTransferenciaList().remove(transferenciaAux);
            if (transferenciaAux.getTransferenciaAccionReqList().size() > 0) {
                movTransferencia.getTransferenciaList().add(transferencia);
            }
            strResultado = "1|Exito";
        }
        movTransferencia.getMovCaratula().setsIdCaratula(idCaratula);
        session.setAttribute("transferencia", movTransferencia);
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (transBean != null) {
            transBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
