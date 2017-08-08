<%-- 
    Document   : ajaxAvancePoaMetas.jsp
    Created on : Jan 22, 2015, 10:14:03 AM
    Author     : mavalle
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.AvancePoaMetaObservaciones"%>
<%@page import="gob.gbc.entidades.AccionesAvancePoa"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.AvancePoaMeta"%>
<%@page import="gob.gbc.entidades.AvancePoaAcciones"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Meta> metaList = new ArrayList<Meta>();
    Meta meta;
    MetaBean metaBean = null;
    List<AvancePoaMetaObservaciones> metaObservList = new ArrayList<AvancePoaMetaObservaciones>();
    AvancePoaMetaObservaciones metaObserv;

    List<AvancePoaMeta> avancePoaMetaList = new ArrayList<AvancePoaMeta>();
    AvancePoaMeta avancePoaMeta;

    List<AvancePoaAcciones> avancePoaAccionesList = new ArrayList<AvancePoaAcciones>();
    AvancePoaAcciones avancePoaAcciones;

    List<Evaluacion> evaluacionList = new ArrayList<Evaluacion>();
    Evaluacion evaluacion;
    List<AccionesAvancePoa> accionesAvanceList = new ArrayList<AccionesAvancePoa>();
    AccionesAvancePoa accionesAvancePoa;
    Dependencia dependencia;
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramoId = new String();
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    int metaId = 0;
    String cadValidaPeriodo = new String();
    String validaPeriodo[] = null;
    String calculo = "";
    String validaCierreAccion = "";
    String metaDescr = "";
    String Deshabilitado = "";
    int mes = 0;
    int accionId = 0;
    int year = 0;
    int rol = 0;
    int contEstimacion = 0;
    int intCuantos = 0;
    boolean accesoPrg = true;
    boolean insertoMes = false;
    int selEstilo = 0;
    String strExplode[] = null;
    String UnidadMedidaAccion = "";

    Double totalProgramado = 0.0;
    Double totalRealizado = 0.0;
    String strAccionDescr = "";

    String strPeriodo = "";
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = Integer.parseInt((String) session.getAttribute("strRol"));
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }

        if (request.getParameter("accionId") != null
                && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt((String) request.getParameter("accionId"));
        }

        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        } else {
            metaId = -1;
        }

        if (request.getParameter("calculo") != null
                && !request.getParameter("calculo").equals("")) {
            calculo = (String) request.getParameter("calculo");
        }

        if (request.getParameter("metaDescr") != null
                && !request.getParameter("metaDescr").equals("")) {
            metaDescr = (String) request.getParameter("metaDescr");
        }

        if (request.getParameter("accionDescr") != null
                && !request.getParameter("accionDescr").equals("")) {
            strAccionDescr = (String) request.getParameter("accionDescr");
        }

        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);

        if (!ramoId.equals("-1")) {
            if (!(metaId == -1)) {

                strPeriodo = metaBean.getResultSQLgetValidaPeriodoDefinido(year, ramoId);
                validaPeriodo = strPeriodo.split(",");
                if (Integer.parseInt(validaPeriodo[0]) == 0) {
                    strResultado = "<center>Para Este ramo, el Periodo No esta definido<br/><br/><input type='button' value='cerrar' onclick=\"fadeOutPopUp('avanceAccionAvancePOA');\"></center>";
                } else {
                    intCuantos = metaBean.getExisteAvanceAccionesAvancePoa(metaId, ramoId, year, accionId);
                    if (intCuantos > 0 && intCuantos < 12) {
                        strResultado = "<center>Para Esta Accion, los Periodos No están Capturados Completos en la Tabla: DGI.POA_AVANCE_ACCION<br/><br/><input type='button' value='cerrar' onclick=\"fadeOutPopUp('avanceAccionAvancePOA');\"></center>";
                    } else {
                        //validar que tenga estimacion capturada para presentar el programdo en dgi.accion_estimacion    
                        contEstimacion = metaBean.getContEstimacionAccionAvancePoa(ramoId, metaId, accionId, year);
                        if (contEstimacion > 0) {   //validar que tenga estimacion capturada para presentar el programdo en dgi.accion_estimacion  
                            if (intCuantos == 0) {
                                for (mes = 1; mes <= 12; mes++) {
                                    insertoMes = metaBean.getInsertaAvanceAccionesAvancePoa(metaId, ramoId, year, accionId, mes);
                                    if (insertoMes) {
                                        metaBean.transaccionCommit();
                                    } else {
                                        metaBean.transaccionRollback();
                                    }
                                }
                            }
                            UnidadMedidaAccion = metaBean.getResultSQLGetUnidadMedidaAccionAvancePoa(year, ramoId, metaId, accionId);
                            strExplode = UnidadMedidaAccion.split(",");// trae la unidad de medida y si esta definida como de resultado o no
                            strResultado += "";
                            strResultado += "<table style='text-align:left; width:100%'><tr><td style='vertical-align: text-top; width:40px;'><b>Accion:</b>&nbsp;&nbsp;</td><td><b>" + accionId + "-" + strAccionDescr + "</b></td></tr></table>";
                            strResultado += "<table class='tblListAccionesAvancePoa' ><thead><caption style='text-align:left;'><br/><b>Unidad Medida: " + strExplode[1] + "<br/><br/>Captura de avance acciones</b><br/><br/></caption><th>Mes</th><th>Programado</th><th>Realizado</th><th>Observaciones</th><th></th></thead>";
                            avancePoaAccionesList = metaBean.getAvanceAccionesAvancePoa(metaId, ramoId, year, accionId);

                            for (AvancePoaAcciones avancePoaAccionesTemp : avancePoaAccionesList) {
                                selEstilo++;
                                if (selEstilo == Integer.parseInt(validaPeriodo[0])) {
                                    Deshabilitado = " ";
                                } else {
                                    Deshabilitado = " disabled='true' style='background-color: #CCC;' ";
                                }

                                strResultado += "<tr><td><input type='text' disabled='true' style=' background-color:#CCC; width:100px; text-align:left;'  value='" + avancePoaAccionesTemp.getDESCR_MES() + "'></td><td><input disabled='true' style='background-color: #CCC;'  type='text' value='" + numberF.format(Double.parseDouble(avancePoaAccionesTemp.getPROGRAMADO())) + "'></td><td><input " + Deshabilitado + " type='text' id='realizado" + selEstilo + "' name='realizado" + selEstilo + "' value='" + numberF.format(Double.parseDouble(avancePoaAccionesTemp.getREALIZADO())) + "'  onkeyPress='return NumCheck(event,this);' onBlur='agregarFormato(\"realizado" + selEstilo + "\"); validaMascara(\"realizado" + selEstilo + "\");validarFlotanteAvancePoaAcciones(this.value)'></td><td><input " + Deshabilitado + " type='text' style='text-transform:uppercase' id='observaciones" + selEstilo + "' name='observaciones" + selEstilo + "' value='" + avancePoaAccionesTemp.getOBSERVACIONES() + "'></td>";
                                strResultado += "<td><input type='button' value='...' onclick='MuestraObsrvacionAvancePoa(\"observaciones" + selEstilo + "\",\"" + selEstilo + "\",\"N\",\"" + Integer.parseInt(validaPeriodo[0]) + "\")'></td><input " + Deshabilitado + " type='hidden' id='mes" + selEstilo + "' name='mes" + selEstilo + "' value='" + selEstilo + "'></td></tr>";
                                if (calculo.equals("AC")) {
                                    totalProgramado = totalProgramado + Double.parseDouble(avancePoaAccionesTemp.getPROGRAMADO());
                                    totalRealizado = totalRealizado + Double.parseDouble(avancePoaAccionesTemp.getREALIZADO());
                                } else if (calculo.equals("MI")) {
                                    if (selEstilo == 1) {
                                        totalProgramado = Double.parseDouble(avancePoaAccionesTemp.getPROGRAMADO());
                                        totalRealizado = Double.parseDouble(avancePoaAccionesTemp.getREALIZADO());
                                    } else {
                                        if (Double.parseDouble(avancePoaAccionesTemp.getPROGRAMADO()) < totalProgramado || totalProgramado == 0) {
                                            totalProgramado = Double.parseDouble(avancePoaAccionesTemp.getPROGRAMADO());
                                        }
                                        if (Double.parseDouble(avancePoaAccionesTemp.getREALIZADO()) < totalRealizado || totalRealizado == 0) {
                                            totalRealizado = Double.parseDouble(avancePoaAccionesTemp.getREALIZADO());
                                        }
                                    }

                                } else if (calculo.equals("MA")) {
                                    if (selEstilo == 1) {
                                        totalProgramado = Double.parseDouble(avancePoaAccionesTemp.getPROGRAMADO());
                                        totalRealizado = Double.parseDouble(avancePoaAccionesTemp.getREALIZADO());
                                    } else {
                                        if (Double.parseDouble(avancePoaAccionesTemp.getPROGRAMADO()) > totalProgramado) {
                                            totalProgramado = Double.parseDouble(avancePoaAccionesTemp.getPROGRAMADO());
                                        }
                                        if (Double.parseDouble(avancePoaAccionesTemp.getREALIZADO()) > totalRealizado) {
                                            totalRealizado = Double.parseDouble(avancePoaAccionesTemp.getREALIZADO());
                                        }
                                    }
                                }
                            }
                            strResultado += "<tr><td>(" + calculo + ")Total: </td><td><table border='0px' style='width:100%; text-align:center; border-color:#CCCCCC;'><tr><td><input disabled='true' style='background-color: #CCC;' type='text' id='totalProgramado' value='" + numberF.format(totalProgramado) + "' /></td></tr></table></td><td><table border='0px' style='width:100%; text-align:center; border-color:#CCCCCC;'><tr><td><input disabled='true' style='background-color: #CCC;' type='text' id='totalRealizado' value='" + numberF.format(totalRealizado) + "'/></td></tr></table></td><td></td><td></td></tr>";
                            strResultado += "</table>";

                            strResultado += "<input type='hidden' id='ramo' name'ramo' value='" + ramoId + "'>";
                            strResultado += "<input type='hidden' id='accion' name'accion' value='" + accionId + "'>";
                            strResultado += "<input type='hidden' id='meta' name'meta' value='" + metaId + "'>";
                            strResultado += "<input type='hidden' id='cont' name'cont' value='" + selEstilo + "'>";
                            strResultado += "<input type='hidden' id='ObligadoObservacion' name'ObligadoObservacion' value='" + strExplode[0] + "'>";
                            strResultado += "<input type='hidden' id='periodo' name'periodo' value='" + validaPeriodo[0] + "'>";
                            strResultado += "<input type='hidden' id='calc' name'calc' value='" + calculo + "'><br/><br/><br/><br/><br/><br/>";
                            strResultado += "<br/><center><table><tr><td><input type='button' value='Guardar' onclick=\"ActualizaAvancePoaAcciones()\" />&nbsp;&nbsp;</td><td><input type='button' value='Cerrar' onclick=\"fadeOutPopUp('avanceAccionAvancePOA');\" /></td></tr></table> </center><br/><br/>";

                        } else {
                            strResultado = "<center><font color=red>La Estimacion no esta capturada en DGI.ACCION_ESTIMACION para esta accion</font> <br/><br/><input type='button' value='cerrar' onclick=\"fadeOutPopUp('avanceAccionAvancePOA');\"></center>";
                        }
                    }// validar estimacion

                }
            }
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
        if (metaBean != null) {
            session.setAttribute("metaList", metaList);
            metaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>