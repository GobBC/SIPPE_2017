<%-- 
    Document   : ajaxInsertarReduccion
    Created on : Jan 7, 2016, 4:34:57 PM
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
    MovimientosAmpliacionReduccion movAmpRed = new MovimientosAmpliacionReduccion();
    AmpliacionReduccionAccionReq ampRedAccionReq = new AmpliacionReduccionAccionReq();
    List<AmpliacionReduccionAccionReq> ampRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
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
    String fecha = new String();
    int monthAct = 0;
    int proy = 0;
    int meta = 0;
    int accion = 0;
    int year = 0;
    int consecutivo = 0;
    int edicion = 0;
    int maxConsec = 0;
    int identificador = 0;
    double disponible = 0.0;
    double importe = 0.0;
    double importeAbs = 0.0;
    boolean bandera = true;

    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "") {
            movAmpRed = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampRedAccionReqList = movAmpRed.getAmpReducAccionReqList();
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramo") != null
                && !request.getParameter("ramo").equals("")) {
            ramo = request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            programa = request.getParameter("programa");
        }
        if (request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")) {
            proyecto = request.getParameter("proyecto");
            proy = Integer.parseInt(proyecto.split(",")[0]);
            tipoProy = proyecto.split(",")[1];
        }
        if (request.getParameter("meta") != null && !request.getParameter("meta").equals("")) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        if (request.getParameter("accion") != null && !request.getParameter("accion").equals("")) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        if (request.getParameter("partida") != null && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if (Utilerias.existeParametro("importe", request)) {
            importe = Double.parseDouble(request.getParameter("importe"));
        }
        if (Utilerias.existeParametro("disponible", request)) {
            disponible = Double.parseDouble(request.getParameter("disponible"));
        }
        if (Utilerias.existeParametro("edicion", request)) {
            edicion = Integer.parseInt(request.getParameter("edicion"));
        }
        if (Utilerias.existeParametro("identificador", request)) {
            identificador = Integer.parseInt(request.getParameter("identificador"));
        }
        if (request.getParameter("relLaboral") != null && !request.getParameter("relLaboral").equals("")) {
            relLaboral = request.getParameter("relLaboral");
            if (relLaboral.equals("-1")) {
                relLaboral = "0";
            }
        }
        if (Utilerias.existeParametro("fecha", request)) {
            fecha = request.getParameter("fecha");
            monthAct = Integer.parseInt(fecha.split("\\/")[1]);
        }
        if (request.getParameter("fuente") != null
                && !request.getParameter("fuente").equals("")) {
            fuente = request.getParameter("fuente");
            fuenteFin = fuente.split("\\.");
        }
        if (Utilerias.existeParametro("importeAbs", request)) {
            importeAbs = Double.parseDouble(request.getParameter("importeAbs"));
        }

        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer(request.getHeader("host"));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRedBean.resultSQLConecta(tipoDependencia);
        
        if ((importeAbs <= disponible) || ampRedBean.isPartidaDePlantilla(partida, year)) {
            
            if (edicion == 1) {

                for (AmpliacionReduccionAccionReq ampReqTemp : ampRedAccionReqList) {
                    if (ampReqTemp.getIdentidicador() == identificador) {
                        ampRedAccionReq = ampReqTemp;
                    }
                }

                codigoPPTO = ampRedBean.getCodigoProgramaticoByRequerimiento(year, ramo, programa, ampRedAccionReq.getTipoProy(),
                        ampRedAccionReq.getProy(), meta, accion, ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                        ampRedAccionReq.getRelLaboral());

                tipoProy = ampRedAccionReq.getTipoProy();
                proy = ampRedAccionReq.getProy();
                partida = ampRedAccionReq.getPartida();
                fuenteFin[0] = ampRedAccionReq.getFuente();
                fuenteFin[1] = ampRedAccionReq.getFondo();
                fuenteFin[2] = ampRedAccionReq.getRecurso();
                relLaboral = ampRedAccionReq.getRelLaboral();
                consecutivo = ampRedAccionReq.getConsecutivo();

            } else {

                codigoPPTO = ampRedBean.getCodigoProgramaticoByRequerimiento(year, ramo, programa, tipoProy, proy, meta, accion, partida, fuenteFin[0], fuenteFin[1], fuenteFin[2], relLaboral);

                if (ampRedAccionReqList.size() > 0) {

                    for (AmpliacionReduccionAccionReq ampTemp : ampRedAccionReqList) {
                        if (maxConsec == 0) {
                            maxConsec = ampTemp.getConsecutivo();
                        } else if (ampTemp.getConsecutivo() > maxConsec) {
                            maxConsec = ampTemp.getConsecutivo();
                        }
                    }

                    consecutivo = maxConsec;
                    consecutivo++;

                } else {
                    consecutivo++;
                }

                for (AmpliacionReduccionAccionReq ampRed : ampRedAccionReqList) {
                    if (ampRed.getRamo().equals(ramo) && ampRed.getPrograma().equals(programa) && (ampRed.getProy() + ampRed.getTipoProy()).equals(proy + tipoProy)
                            && ampRed.getMeta() == meta && ampRed.getAccion() == accion && ampRed.getPartida().equals(partida)
                            && (ampRed.getFuente() + "." + ampRed.getFondo() + "." + ampRed.getRecurso()).equals(fuente)
                            && ampRed.getRelLaboral().equalsIgnoreCase(relLaboral)) {
                        bandera = false;
                    }
                }
            }

            if (!ampRedBean.getResultSQLisCodigoRepetido(year,
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
                    codigoPPTO.getRelLaboral())) {
                strResultado = "2|Este código no existe en el presupuesto " + year;
            }

            if (bandera) {
                if (codigoPPTO != null) {
                    ampRedAccionReq.setConsecutivo(consecutivo);
                    ampRedAccionReq.setRamo(codigoPPTO.getRamoId());
                    ampRedAccionReq.setDepto(codigoPPTO.getDepto());
                    ampRedAccionReq.setFinalidad(codigoPPTO.getFinalidad());
                    ampRedAccionReq.setFuncion(codigoPPTO.getFuncion());
                    ampRedAccionReq.setSubfuncion(codigoPPTO.getSubfuncion());
                    ampRedAccionReq.setPrgConac(codigoPPTO.getProgCONAC());
                    ampRedAccionReq.setPrograma(codigoPPTO.getPrograma());
                    ampRedAccionReq.setTipoProy(codigoPPTO.getTipoProy());
                    ampRedAccionReq.setProy(Integer.parseInt(codigoPPTO.getProyecto()));
                    ampRedAccionReq.setMeta(codigoPPTO.getMeta());
                    ampRedAccionReq.setAccion(codigoPPTO.getAccion());
                    ampRedAccionReq.setPartida(codigoPPTO.getPartida());
                    ampRedAccionReq.setTipoGasto(codigoPPTO.getTipoGasto());
                    ampRedAccionReq.setFuente(codigoPPTO.getFuente());
                    ampRedAccionReq.setFondo(codigoPPTO.getFondo());
                    ampRedAccionReq.setRecurso(codigoPPTO.getRecurso());
                    ampRedAccionReq.setMunicipio(codigoPPTO.getMunicipio());
                    ampRedAccionReq.setDelegacion(codigoPPTO.getDelegacion());
                    ampRedAccionReq.setRelLaboral(codigoPPTO.getRelLaboral());
                    ampRedAccionReq.setConsiderar("S");
                    ampRedAccionReq.setImporte(importe);
                    /*if(ampRedBean.getResultSQLisParaestatal() && !ampRedBean.getResultSqlGetIsAyuntamiento())
                     ampRedAccionReq.setDisponible(ampRedBean.getResultSQLgetDisponibleParaestatal(year,
                     monthAct, ramo, programa, tipoProy, proy, meta, accion, partida, relLaboral,
                     fuenteFin[0],fuenteFin[1],fuenteFin[2]));
                     else*/
                    ampRedAccionReq.setDisponible(ampRedBean.getDisponible(year, ramo, programa,
                            tipoProy, proy, meta, accion, partida, fuenteFin[0],
                            fuenteFin[1], fuenteFin[2], relLaboral, monthAct));
                    ampRedAccionReq.setDisponibleAnual(ampRedBean.getResultSQLgetImporteAnual(year, ramo, programa,
                            tipoProy, proy, meta, accion, partida, fuenteFin[0],
                            fuenteFin[1], fuenteFin[2], relLaboral, 12));
                    ampRedAccionReq.setQuincePor(ampRedBean.getQuincePorCiento(partida, ramo, year));
                    ampRedAccionReq.setAcumulado(ampRedBean.getResultSQLgetAcumluladoMovtos(year, partida, ramo));

                    if (edicion < 1) {
                        ampRedAccionReq.setIdentidicador(ampRedAccionReqList.size());
                    }

                    if (ampRedBean.getTipoOficioByPartida(year, partida)) {
                        ampRedAccionReq.setEstatusTipoOficio("V");
                    } else {
                        ampRedAccionReq.setEstatusTipoOficio("A");
                    }

                    ampRedAccionReq.setTipoMovAmpRed("R");

                }

                if (edicion == 1) {
                    for (AmpliacionReduccionAccionReq ampReqTemp : ampRedAccionReqList) {
                        if (ampReqTemp.getIdentidicador() == identificador) {
                            ampReqTemp = ampRedAccionReq;
                        }
                    }
                } else {
                    ampRedAccionReqList.add(ampRedAccionReq);
                }

                movAmpRed.setAmpReducAccionReqList(ampRedAccionReqList);
                session.setAttribute("ampliacionReduccion", movAmpRed);

            } else {
                strResultado = "2|Este código ya tiene una reducción en proceso.";
            }

        } else {
            strResultado = "2|El importe no puede sobrepasar el total del disponible 2.";
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
        if (ampRedBean != null) {
            ampRedBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
