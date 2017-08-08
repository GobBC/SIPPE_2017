<%-- 
    Document   : ajaxValidaCodigoTransferencia
    Created on : Mar 3, 2016, 3:23:53 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%
    MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
    Transferencia transferencia = new Transferencia();
    Transferencia transTemp = null;
    TransferenciaBean transBean = null;
    String strResultado = new String();
    String ramo = new String();
    String programa = new String();
    String partida = new String();
    String relLaboral = new String();
    String fuente = new String();
    String proyecto = new String();
    String tipoDependencia = new String();
    double importe = 0.00;
    double disponible = 0.00;
    int metaId = 0;
    int accion = 0;
    int year = 0;
    boolean bandera = true;
    try {
        if (Utilerias.existeParametro("ramo", request)) {
            ramo = request.getParameter("ramo");
        }
        if (Utilerias.existeParametro("programa", request)) {
            programa = request.getParameter("programa");
        }
        if (Utilerias.existeParametro("partida", request)) {
            partida = request.getParameter("partida");
        }
        if (Utilerias.existeParametro("relLaboral", request)) {
            relLaboral = request.getParameter("relLaboral");
            if (relLaboral.equals("-1")) {
                relLaboral = new String("0");
            }
        }
        if (Utilerias.existeParametro("fuente", request)) {
            fuente = request.getParameter("fuente");
        }
        if (Utilerias.existeParametro("proyecto", request)) {
            proyecto = request.getParameter("proyecto");
        }
        if (Utilerias.existeParametro("meta", request)) {
            metaId = Integer.parseInt(request.getParameter("meta"));
        }
        if (Utilerias.existeParametro("accion", request)) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        if (Utilerias.existeParametro("importe", request)) {
            importe = Double.parseDouble(request.getParameter("importe"));
        }
        if (Utilerias.existeParametro("disponible", request)) {
            disponible = Double.parseDouble(request.getParameter("disponible"));
        }
        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            movTransferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        transBean = new TransferenciaBean(tipoDependencia);
        transBean.setStrServer((request.getHeader("Host")));
        transBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        transBean.resultSQLConecta(tipoDependencia);

        if (importe <= disponible || transBean.isPartidaDePlantilla(partida, year)) {

            for (Transferencia trans : movTransferencia.getTransferenciaList()) {
                if (trans.getRamo() != null) {
                    if (trans.getRamo().equals(ramo) && trans.getPrograma().equals(programa) && (trans.getProyecto() + "," + trans.getTipoProy()).equals(proyecto)
                            && trans.getMeta() == metaId && trans.getAccion() == accion && trans.getPartida().equals(partida)
                            && (trans.getFuente() + "." + trans.getFondo() + "." + trans.getRecurso()).equals(fuente) && trans.getRelLaboral().equals(relLaboral)) {
                        bandera = false;
                    }
                } else {
                    transTemp = trans;
                }
            }

            if (transTemp != null) {
                movTransferencia.getTransferenciaList().remove(transTemp);
            }

            if (bandera) {
                strResultado += "1";
            } else {
                strResultado += "2";
            }

        } else {
            strResultado += "3";
        }

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