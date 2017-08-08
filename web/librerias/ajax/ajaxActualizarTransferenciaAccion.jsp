<%-- 
    Document   : ajaxActualizarTransferenciaAccion
    Created on : Jan 15, 2016, 11:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.MovimientoOficioAccion"%>
<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    AccionBean accionBean = null;
    TransferenciaBean transferenciaBean = null;
    Evaluacion evaluacion = new Evaluacion();
    MovimientosTransferencia transferencia = new MovimientosTransferencia();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<TransferenciaAccion> movtoAccionesList = new ArrayList<TransferenciaAccion>();
    List<MovOficioAccionEstimacion> movtoEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
    List<TransferenciaMeta> movtoMetasList = null;
    List<Transferencia> movimientosTransferenciaRecibenList = new ArrayList<Transferencia>();
    MovimientoOficioAccion movtoOficioAccion = new MovimientoOficioAccion();
    String tipoDependencia = new String();
    String strResultado = new String();
    String ramoId = new String();
    String cadValores = new String();
    String arrValores[];
    String calculo = new String();
    String clasificacion = new String();
    String finalidad = new String();
    String funcion = new String();
    String subFuncion = new String();
    String linea = new String();
    String lineaSectorial = new String();
    String ponderacion = new String();
    String programaId = new String();
    String tipoProy = new String();
    String accionDescr = new String();
    String selUnidadEj = new String();
    String selMunicipio = new String();
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    int cont = 1;
    int year = 0;
    int metaId = 0;
    int accionId = 0;
    int identificador = -1;
    int objIdentificado = 0;
    int contFallas = 0;
    int unidadMedida = -1;
    int compromiso = -1;
    int proyectoId = -1;
    int transversalidad = -1;
    int selGrupo = -1;
    int selLocalidad = -1;
    int accMuj = -1;
    int accHom = -1;
    int maxIdentificador = 0;
    int minIdentificador = 0;
    int contForEvalucion = 0;
    Double valor = 0.0;
    boolean resultadoAct = false;
    int accionIdNeg = 0;
    int minAccionId = 0;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }

        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = (String) request.getParameter("programaId");
        }

        if (request.getParameter("tipoProy") != null && !request.getParameter("tipoProy").equals("")) {
            tipoProy = (String) request.getParameter("tipoProy");
        }

        if (request.getParameter("proyectoId") != null && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt((String) request.getParameter("proyectoId"));
        }

        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }

        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt((String) request.getParameter("accionId"));
        }

        if (request.getParameter("accionDescr") != null && !request.getParameter("accionDescr").equals("")) {
            accionDescr = (String) request.getParameter("accionDescr");
        }

        if (request.getParameter("selUnidadEj") != null && !request.getParameter("selUnidadEj").equals("")) {
            selUnidadEj = (String) request.getParameter("selUnidadEj");
        }

        if (request.getParameter("unidadMedida") != null && !request.getParameter("unidadMedida").equals("")) {
            unidadMedida = Integer.parseInt((String) request.getParameter("unidadMedida"));
        }

        if (request.getParameter("selGrupo") != null && !request.getParameter("selGrupo").equals("")) {
            selGrupo = Integer.parseInt((String) request.getParameter("selGrupo"));
        }

        if (request.getParameter("selMunicipio") != null && !request.getParameter("selMunicipio").equals("")) {
            selMunicipio = (String) request.getParameter("selMunicipio");
        }

        if (request.getParameter("selLocalidad") != null && !request.getParameter("selLocalidad").equals("")) {
            selLocalidad = Integer.parseInt((String) request.getParameter("selLocalidad"));
        }

        if (request.getParameter("calculo") != null && !request.getParameter("calculo").equals("")) {
            calculo = (String) request.getParameter("calculo");
        }

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt((String) request.getParameter("identificador"));
        }

        if (request.getParameter("linea") != null && !request.getParameter("linea").equals("")) {
            linea = (String) request.getParameter("linea");
        }

        if (request.getParameter("lineaSectorial") != null && !request.getParameter("lineaSectorial").equals("")) {
            lineaSectorial = (String) request.getParameter("lineaSectorial");
        }

        if (request.getParameter("accMuj") != null && !request.getParameter("accMuj").equals("")) {
            accMuj = Integer.parseInt((String) request.getParameter("accMuj"));
        }

        if (request.getParameter("accHom") != null && !request.getParameter("accHom").equals("")) {
            accHom = Integer.parseInt((String) request.getParameter("accHom"));
        }

        if (request.getParameter("valores") != null && !request.getParameter("valores").equals("")) {
            cadValores = (String) request.getParameter("valores");
        }

        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            transferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
                movtoMetasList = transferencia.getTransferenciaMetaList();
                movtoAccionesList = transferencia.getTransferenciaAccionList();
                if (identificador != -1) {
                    for (TransferenciaAccion movtoAccionListTemp : movtoAccionesList) {
                        if (movtoAccionListTemp.getIdentificador() == identificador) {
                            movtoOficioAccion = movtoAccionListTemp.getMovOficioAccion();
                            movtoEstimacionList = movtoAccionListTemp.getMovOficioAccionEstList();
                        }
                    }
                }
        }

        maxIdentificador = 0;
        minIdentificador = 0;
        contForEvalucion = 0;
        if (movtoAccionesList.size() > 0) {
            for (TransferenciaAccion movtoAccionListTemp : movtoAccionesList) {

                if (contForEvalucion == 0) {
                    minIdentificador = movtoAccionListTemp.getIdentificador();
                    maxIdentificador = movtoAccionListTemp.getIdentificador();
                    minAccionId = movtoAccionListTemp.getMovOficioAccion().getAccionId();
                }

                if (maxIdentificador < movtoAccionListTemp.getIdentificador()) {
                    maxIdentificador = movtoAccionListTemp.getIdentificador();
                }

                if (minIdentificador > movtoAccionListTemp.getIdentificador()) {
                    minIdentificador = movtoAccionListTemp.getIdentificador();
                }

                if (minAccionId > movtoAccionListTemp.getMovOficioAccion().getAccionId()) {
                    minAccionId = movtoAccionListTemp.getMovOficioAccion().getAccionId();
                }

                contForEvalucion++;
            }
        }

        transferenciaBean = new TransferenciaBean(tipoDependencia);
        transferenciaBean.setStrServer(request.getHeader("host"));
        transferenciaBean.setStrUbicacion(getServletContext().getRealPath(""));
        transferenciaBean.resultSQLConecta(tipoDependencia);

        if (movtoAccionesList.size() > 0) {
            if (minAccionId > 0) {
                accionIdNeg = 0;
                minAccionId = 0;
            }
        }

        if (identificador == -1) {
            accionIdNeg = minAccionId;
            accionIdNeg--;
            if (accionIdNeg == -1 ||accionIdNeg == 0) {
                accionIdNeg = -2;
            }
            accionId = accionIdNeg;
        }

        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

        evaluacion = accionBean.getResultSQLGetEvaluacionMeta(year);
        arrValores = new String[cadValores.split(",").length];
        arrValores = cadValores.split(",");
        int contValores = 0;
        if (movtoEstimacionList.size() > 0) {
            for (MovOficioAccionEstimacion reprogramacionTemp : movtoEstimacionList) {
                reprogramacionTemp.setMeta(metaId);
                reprogramacionTemp.setAccion(accionId);
                reprogramacionTemp.setRamo(ramoId);
                reprogramacionTemp.setYear(year);
                reprogramacionTemp.setPrograma(programaId);
                if (!arrValores[contValores].trim().equals("")) {
                    reprogramacionTemp.setPeriodo((contValores + 1));
                    valor = Double.parseDouble(arrValores[contValores].trim());
                    reprogramacionTemp.setValor(valor);
                } else {
                    valor = 0.0;
                    reprogramacionTemp.setPeriodo((contValores + 1));
                    reprogramacionTemp.setValor(valor);
                }
                contValores++;
            }
        } else {
            for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                MovOficioAccionEstimacion reprogramacionTemp = new MovOficioAccionEstimacion();
                reprogramacionTemp.setMeta(metaId);
                if ((accionId * -1) > 999) {
                    accionId = Integer.parseInt(String.valueOf(accionId).substring(3));
                }
                reprogramacionTemp.setAccion(accionId);
                reprogramacionTemp.setRamo(ramoId);
                reprogramacionTemp.setYear(year);
                reprogramacionTemp.setPrograma(programaId);
                if (!arrValores[it].trim().equals("")) {
                    reprogramacionTemp.setPeriodo((contValores + 1));
                    valor = Double.parseDouble(arrValores[it].trim());
                    reprogramacionTemp.setValor(valor);
                } else {
                    valor = 0.0;
                    reprogramacionTemp.setPeriodo((contValores + 1));
                    reprogramacionTemp.setValor(valor);
                }
                movtoEstimacionList.add(reprogramacionTemp);
                contValores++;
            }
        }

        MovimientoOficioAccion accionInfo = new MovimientoOficioAccion();
        accionInfo.setNvaCreacion("S");
        accionInfo.setYear(year);
        accionInfo.setRamoId(ramoId);
        accionInfo.setMetaId(metaId);
        accionInfo.setAccionId(accionId);
        accionInfo.setAccionDescr(accionDescr);
        accionInfo.setProgramaId(programaId);
        accionInfo.setProyectoId(proyectoId);
        accionInfo.setTipoProy(tipoProy);
        accionInfo.setClaveMedida(unidadMedida);
        accionInfo.setCalculo(calculo);
        accionInfo.setLinea(linea);
        accionInfo.setObra(movtoOficioAccion.getObra());
        if (lineaSectorial.equals("-1")) {
            lineaSectorial = new String();
        }
        accionInfo.setLineaSectorial(lineaSectorial);
        accionInfo.setLocalidad(selLocalidad);
        if (selGrupo != -1) {
            accionInfo.setGrupoPoblacional(selGrupo);
        }
        accionInfo.setBenefHombre(accHom);
        accionInfo.setBenefMujer(accMuj);
        accionInfo.setMunicipio(selMunicipio);
        accionInfo.setDeptoId(selUnidadEj);

        if (identificador != -1) {
            for (TransferenciaAccion movtoAccionListTemp : movtoAccionesList) {
                if (movtoAccionListTemp.getIdentificador() == identificador) {
                    movtoAccionListTemp.setMovOficioAccion(accionInfo);
                    movtoAccionListTemp.setMovOficioAccionEstList(movtoEstimacionList);
                    movtoAccionListTemp.setValAutorizado("S");
                }
            }
        } else {
            TransferenciaAccion movtoAccionListTemp = new TransferenciaAccion();
            movtoAccionListTemp.setIdentificador(maxIdentificador + 1);
            movtoAccionListTemp.setMovOficioAccion(accionInfo);
            movtoAccionListTemp.setMovOficioAccionEstList(movtoEstimacionList);
            movtoAccionListTemp.setValAutorizado("P");
            movtoAccionesList.add(movtoAccionListTemp);
        }


            transferencia.setTransferenciaAccionList(movtoAccionesList);


        session.setAttribute("transferencia", transferencia);

    } catch (Exception ex) {

        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();

    } finally {
        if (transferenciaBean != null) {
            transferenciaBean.resultSQLDesconecta();
        }
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);

    }
%>
