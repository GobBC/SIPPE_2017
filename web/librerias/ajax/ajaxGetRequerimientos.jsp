<%-- 
    Document   : ajaxGetRequerimientos
    Created on : Apr 24, 2015, 8:53:42 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="gob.gbc.aplicacion.PartidaBean"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="gob.gbc.entidades.CodigoPPTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Requerimiento requ = new Requerimiento();
    AccionBean accionBean = null;
    CodigoPPTO codigo = new CodigoPPTO();
    String strResultado = new String();
    String justificacion = new String();
    String mensajeProcedure = new String();
    String usuario = new String();
    String par = new String();
    int parInt = 0;
    boolean cierreReqPPTO = false;
    double costoViejo[] = new double[12];
    boolean proResult = true;
    boolean insertResult = false;
    double costoTotal = 0.0;
    String depto = new String();
    int req = 0;
    String ramoId = new String();
    String programaId = new String();
    int metaId = 0;
    int proyecto = 0;
    int year = 0;
    int accionId = 0;
    String fuente = new String();
    String tipoGasto = new String();
    String tipoDependencia = new String();
    int opcion = 0;
    String relacionLaboral = new String();
    double contEst = 0;
    String partida = new String();
    String reqDescr = new String();
    String obra = new String();
    String tipoProy = new String();
    BigDecimal cantidad = new BigDecimal(0.0);;
    BigDecimal costoUnit = new BigDecimal(0.0);
    Double costoAn = 0.0;
    double cantXcostoUnit = 0;
    BigDecimal enero = new BigDecimal(0.0);
    BigDecimal febrero = new BigDecimal(0.0);
    BigDecimal marzo = new BigDecimal(0.0);
    BigDecimal abril = new BigDecimal(0.0);
    BigDecimal mayo = new BigDecimal(0.0);
    BigDecimal junio = new BigDecimal(0.0);
    BigDecimal julio = new BigDecimal(0.0);
    BigDecimal agosto = new BigDecimal(0.0);
    BigDecimal sept = new BigDecimal(0.0);
    BigDecimal octubre = new BigDecimal(0.0);
    BigDecimal noviembre = new BigDecimal(0.0);
    BigDecimal diciembre = new BigDecimal(0.0);
    int codigoRep = -1;
    List<Requerimiento> requerimientoList = new ArrayList<Requerimiento>();
    String articuloPart = new String();
    String arrValores[];
    String articulo = new String();
    int gpogto = 0;
    int subgpo = 0;

    try {

        request.setCharacterEncoding("UTF-8");

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (request.getParameter("opcion") != null && !request.getParameter("opcion").equals("")) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (request.getParameter("relacionLab") != null && !request.getParameter("relacionLab").equals("")) {
            relacionLaboral = request.getParameter("relacionLab");
            if (relacionLaboral.isEmpty()) {
                relacionLaboral = "0";
            }
        } else {
            relacionLaboral = "0";
        }
        if (request.getParameter("tipoProy") != null && !request.getParameter("tipoProy").equals("")) {
            tipoProy = request.getParameter("tipoProy");
        }
        if (request.getParameter("partida") != null && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if (request.getParameter("requerimiento") != null && !request.getParameter("requerimiento").equals("")) {
            req = Integer.parseInt(request.getParameter("requerimiento").trim());
        }
        if (request.getParameter("partidaDesc") != null && !request.getParameter("partidaDesc").equals("")) {
            reqDescr = request.getParameter("partidaDesc");
        }
        if (request.getParameter("justificacion") != null && !request.getParameter("justificacion").equals("")) {
            justificacion = request.getParameter("justificacion");
        }
        if (request.getParameter("articulo") != null && !request.getParameter("articulo").equals("")) {
            articuloPart = request.getParameter("articulo");
            if (articuloPart.contains(".")) {
                arrValores = new String[articuloPart.split("\\.").length];
                arrValores = articuloPart.split("\\.");
                articulo = (arrValores[2]);
                subgpo = Integer.parseInt((arrValores[1]));
                gpogto = Integer.parseInt((arrValores[0]));
            } else {
                articulo = articuloPart;
            }
        }
        if (request.getParameter("cantidad") != null && !request.getParameter("cantidad").equals("")) {
            cantidad = new BigDecimal(request.getParameter("cantidad"));
        }
        if (request.getParameter("costoUni") != null && !request.getParameter("costoUni").equals("")) {
            costoUnit = new BigDecimal(request.getParameter("costoUni"));
        }
        if (request.getParameter("costoAn") != null && !request.getParameter("costoAn").equals("")) {
            costoAn = Math.abs(Double.valueOf(request.getParameter("costoAn")));
        }
        if (request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")) {
            proyecto = Integer.parseInt(request.getParameter("proyecto"));
        }
        if (request.getParameter("enero") != null && !request.getParameter("enero").equals("")) {
            enero = new BigDecimal(request.getParameter("enero"));
        }
        if (request.getParameter("febrero") != null && !request.getParameter("febrero").equals("")) {
            febrero = new BigDecimal(request.getParameter("febrero"));
        }
        if (request.getParameter("marzo") != null && !request.getParameter("marzo").equals("")) {
            marzo = new BigDecimal(request.getParameter("marzo"));
        }
        if (request.getParameter("abril") != null && !request.getParameter("abril").equals("")) {
            abril = new BigDecimal(request.getParameter("abril"));
        }
        if (request.getParameter("mayo") != null && !request.getParameter("mayo").equals("")) {
            mayo = new BigDecimal(request.getParameter("mayo"));
        }
        if (request.getParameter("junio") != null && !request.getParameter("junio").equals("")) {
            junio = new BigDecimal(request.getParameter("junio"));
        }
        if (request.getParameter("julio") != null && !request.getParameter("julio").equals("")) {
            julio = new BigDecimal(request.getParameter("julio"));
        }
        if (request.getParameter("agosto") != null && !request.getParameter("agosto").equals("")) {
            agosto = new BigDecimal(request.getParameter("agosto"));
        }
        if (request.getParameter("sept") != null && !request.getParameter("sept").equals("")) {
            sept = new BigDecimal(request.getParameter("sept"));
        }
        if (request.getParameter("octubre") != null && !request.getParameter("octubre").equals("")) {
            octubre = new BigDecimal(request.getParameter("octubre"));
        }
        if (request.getParameter("noviembre") != null && !request.getParameter("noviembre").equals("")) {
            noviembre = new BigDecimal(request.getParameter("noviembre"));
        }
        if (request.getParameter("diciembre") != null && !request.getParameter("diciembre").equals("")) {
            diciembre = new BigDecimal(request.getParameter("diciembre"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = request.getParameter("programaId");
        }
        if (request.getParameter("fuente") != null && !request.getParameter("fuente").equals("")) {
            fuente = request.getParameter("fuente");
        }
        if (request.getParameter("tipoGasto") != null && !request.getParameter("tipoGasto").equals("")) {
            tipoGasto = request.getParameter("tipoGasto");
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accionId"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("obra") != null && !request.getParameter("obra").equals("")) {
            obra = request.getParameter("obra");
        }
        NumberFormat numberF = NumberFormat.getInstance(Locale.US);
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

        contEst = accionBean.getContEstimacionAccion(year, ramoId, metaId, accionId);
        cierreReqPPTO = accionBean.getCierreRequerimientoPPTO(year, ramoId);
        if (!cierreReqPPTO) {
            if (contEst > 0) {
                if (opcion == 2) {
                    if (reqDescr.isEmpty()) {
                        reqDescr = accionBean.getArticuloDescrByArticulo(year, partida, articuloPart);
                    }
                    /*
                    req = accionBean.getMaxRequerimientoId(year, ramoId, metaId, accionId);
                    
                    costoAn = Utileria.getCostoAnaul(costoUnit, enero, febrero, marzo, abril,
                            mayo, junio, julio, agosto, sept, octubre, noviembre, diciembre);
                    insertResult = accionBean.saveRequerimiento(year, ramoId, programaId, depto, metaId,
                            accionId, tipoGasto, req, reqDescr,
                            partida, relacionLaboral, cantidad.doubleValue(), costoUnit.doubleValue(),
                            costoAn.doubleValue(), enero.doubleValue(),
                            febrero.doubleValue(), marzo.doubleValue(), abril.doubleValue(), mayo.doubleValue(),
                            junio.doubleValue(), julio.doubleValue(), agosto.doubleValue(), sept.doubleValue(), octubre.doubleValue(),
                            noviembre.doubleValue(), diciembre.doubleValue(), articulo, justificacion, fuente, usuario, gpogto, subgpo);
                    */
                        //insertResult = false;
                    mensajeProcedure = accionBean.getRestulSQLCallUpdatePresupuesto(year, ramoId, programaId, tipoProy,
                            proyecto, metaId, accionId, partida,fuente.split("\\.")[0], fuente.split("\\.")[1], fuente.split("\\.")[2],
                            tipoGasto,relacionLaboral, req, enero.doubleValue(), febrero.doubleValue(), marzo.doubleValue(),
                            abril.doubleValue(), mayo.doubleValue(), junio.doubleValue(), julio.doubleValue(), agosto.doubleValue(),
                            sept.doubleValue(), octubre.doubleValue(), noviembre.doubleValue(), diciembre.doubleValue(),
                            cantidad.doubleValue(), costoUnit.doubleValue(), "S", "N", reqDescr, String.valueOf(articulo),
                            justificacion, usuario, String.valueOf(gpogto), String.valueOf(subgpo));
                    if (mensajeProcedure.contains("exito")) {
                        accionBean.transaccionCommit();
                        //accionBean.transaccionRollback();
                        strResultado = "exito|" + strResultado;
                    } else {
                        accionBean.transaccionRollback();
                        if (mensajeProcedure.contains("20010")) {
                            strResultado += mensajeProcedure + "|" + strResultado;
                        } else {
                            strResultado = "Ocurri\u00f3 un error al tratar de afectar el presupuesto|" + strResultado;
                        }
                    }
                } else if (opcion == 3) {                    
                    if (reqDescr.isEmpty()) {
                        reqDescr = accionBean.getArticuloDescrByArticulo(year, partida, articuloPart);
                    }
                    mensajeProcedure = accionBean.getRestulSQLCallUpdatePresupuesto(year, ramoId, programaId, tipoProy,
                            proyecto, metaId, accionId, partida,"", "","",
                            tipoGasto,relacionLaboral, req, enero.doubleValue(), febrero.doubleValue(), marzo.doubleValue(),
                            abril.doubleValue(), mayo.doubleValue(), junio.doubleValue(), julio.doubleValue(), agosto.doubleValue(),
                            sept.doubleValue(), octubre.doubleValue(), noviembre.doubleValue(), diciembre.doubleValue(),
                            cantidad.doubleValue(), costoUnit.doubleValue(), "N", "N", reqDescr, String.valueOf(articulo),
                            justificacion, usuario, String.valueOf(gpogto), String.valueOf(subgpo));
                    if (mensajeProcedure.contains("exito")) {
                        accionBean.transaccionCommit();
                        //accionBean.transaccionRollback();
                        strResultado = "exito|" + strResultado;
                    } else {
                        accionBean.transaccionRollback();
                        if (mensajeProcedure.contains("20010")) {
                            strResultado += mensajeProcedure + "|" + strResultado;
                        } else {
                            strResultado = "Ocurri\u00f3 un error al tratar de afectar el presupuesto|" + strResultado;
                        }
                    }
                } else if (opcion == 4) {
                    //requ = accionBean.getRequerimientoById(year, ramoId, programaId, metaId, accionId, req);
                    mensajeProcedure = accionBean.getRestulSQLCallUpdatePresupuesto(year, ramoId, programaId, tipoProy,
                            proyecto, metaId, accionId, partida,"", "","",
                            tipoGasto,relacionLaboral, req, enero.doubleValue(), febrero.doubleValue(), marzo.doubleValue(),
                            abril.doubleValue(), mayo.doubleValue(), junio.doubleValue(), julio.doubleValue(), agosto.doubleValue(),
                            sept.doubleValue(), octubre.doubleValue(), noviembre.doubleValue(), diciembre.doubleValue(),
                            cantidad.doubleValue(), costoUnit.doubleValue(), "N", "S", reqDescr, String.valueOf(articulo),
                            justificacion, usuario, String.valueOf(gpogto), String.valueOf(subgpo));
                    if (mensajeProcedure.contains("exito")) {
                        accionBean.transaccionCommit();
                        //accionBean.transaccionRollback();
                        strResultado = "exito|" + strResultado;
                    } else {
                        accionBean.transaccionRollback();
                        if (mensajeProcedure.contains("20010")) {
                            strResultado += mensajeProcedure + "|" + strResultado;
                        } else {
                            strResultado = "Ocurri\u00f3 un error al tratar de afectar el presupuesto|" + strResultado;
                        }
                    }
                }
                requerimientoList = accionBean.getRequerimientoByAccion(year, ramoId, programaId, metaId, accionId);
                if (requerimientoList != null || requerimientoList.size() > 0) {
                    strResultado += "<table id='tblRequerimientos'>";
                    strResultado += "<thead><tr><th>No.</th>";
                    strResultado += "<th>Partida</th> <th>Fuente de financiamiento</th> ";
                    strResultado += "<th style='width: 270px; display: block;'> Descripci&oacute;n </th> ";
                    strResultado += "<th>Costo anual </tr> </thead>";
                    strResultado += "<tbody> ";
                    for (Requerimiento requerimiento : requerimientoList) {
                        parInt++;
                        if (parInt % 2 == 0) {
                            par = "class='rowPar'";
                        } else {
                            par = "";
                        }
                        //if(String.valueOf(requerimiento.getReqId()).length() == 1){
                        //    strResultado += "<tr "+par+"> <td align='center'> 0" + requerimiento.getReqId() + "</td>";
                        //}else{
                        strResultado += "<tr " + par + " > <td align='center'> " + requerimiento.getReqId() + "</td>";
                        //}                
                        strResultado += "<td align='center'>" + requerimiento.getPartida() + "</td>";
                        strResultado += "<td align='left'>" + requerimiento.getFuenteFin()
                                + "-" + requerimiento.getFuenteDescr() + "</td>";
                        strResultado += "<td> <div style='overflow:hidden; width: 280px;' title='" + requerimiento.getReq() + "'>" + requerimiento.getReq()
                                + "<div></td>";
                        strResultado += "<td>$" + numberF.format(requerimiento.getCostoAnual()) + "</td> </tr>";
                        costoTotal += requerimiento.getCostoAnual();
                    }
                    strResultado += "</tbody>";
                    strResultado += "</table>";
                    strResultado += "<div id='costoAccion'> Total: $" + numberF.format(costoTotal) + " </div>";
                    strResultado += "<script type='text/javascript'>";
                    strResultado += "  $(\"#tblRequerimientos tbody tr\").click(function(){";
                    strResultado += "  $(this).addClass('selected').siblings().removeClass('selected');";
                    strResultado += "  bloqueaBotonesPlantilla('" + req + "')";
                    strResultado += "});";
                    strResultado += "</script>";
                }
            } else {
                strResultado = "calendarizacion";
            }
        } else {
            strResultado = "cerrado";
        }

    } catch (Exception ex) {
        accionBean.transaccionRollback();
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }

%>