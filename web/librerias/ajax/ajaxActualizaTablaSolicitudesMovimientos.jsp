<%-- 
    Document   : ajaxActualizaTablaSolicitudesMovimientos
    Created on : Dec 11, 2015, 9:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.entidades.MovoficioLigado"%>
<%@page import="gob.gbc.aplicacion.MovoficioLigadoBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.util.Fechas"%>
<%@page import="gob.gbc.entidades.Movimiento"%>
<%@page import="gob.gbc.aplicacion.MovimientosBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    request.setCharacterEncoding("UTF-8");
    Fechas objFechas = new Fechas();
    Movimiento movto = new Movimiento();
    MovimientosBean movimientosBean = null;
    MovoficioLigadoBean movoficioLigadoBean = null;
    MovimientosRecalendarizacion movimiento = new MovimientosRecalendarizacion();
    
    List<Ramo> ramoList = new ArrayList<Ramo>();
    List<Movimiento> movimientoList = new ArrayList<Movimiento>();
    List<MovoficioLigado> movOficiosLigados = new ArrayList<MovoficioLigado>();
    List<RecalendarizacionMeta> recalMetaList = new ArrayList<RecalendarizacionMeta>();
    List<RecalendarizacionAccion> recalAccionList = new ArrayList<RecalendarizacionAccion>();
    
    String strResult = new String();
    String tipoMovimiento = new String();
    String estatusMovimientoId = new String();
    String tipoDependencia = new String();
    String rowPar = new String();
    String btnVer = new String();
    String btnInfo = new String();
    String btnJust = new String();
    String rol = new String();
    String ramo = new String();
    String ramoSel = new String();
    String appLogin = new String();
    String btnBitacora = new String();
    String btnLigado = new String();
    String strFoliosLigados = "";
    int contPar = 0;
    int year = 0;
    int opcion = 0;
    int folio = 0;

    try {

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }

        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }

        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramo = (String) session.getAttribute("ramoAsignado");
        }
        if (Utilerias.existeParametro("ramoAfectado",request)) {
            ramoSel = (String) request.getParameter("ramoAfectado");
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (Utilerias.existeParametro("tipoSolicitudId", request)) {
            tipoMovimiento = request.getParameter("tipoSolicitudId");
        }

        if (Utilerias.existeParametro("estatusMovimientoId", request)) {
            estatusMovimientoId = request.getParameter("estatusMovimientoId");
        }

        if (Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (Utilerias.existeParametro("opcion", request)) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (Utilerias.existeParametro("selRamo", request)) {
            ramo = request.getParameter("selRamo");
        }

        movimientosBean = new MovimientosBean(tipoDependencia);
        movimientosBean.setStrServer(request.getHeader("host"));
        movimientosBean.setStrUbicacion(getServletContext().getRealPath(""));
        movimientosBean.resultSQLConecta(tipoDependencia);
        movoficioLigadoBean = new MovoficioLigadoBean(tipoDependencia);
        movoficioLigadoBean.setStrServer(request.getHeader("host"));
        movoficioLigadoBean.setStrUbicacion(getServletContext().getRealPath(""));
        movoficioLigadoBean.resultSQLConecta(tipoDependencia);
        if(opcion == 3){
            if (tipoMovimiento.equalsIgnoreCase("C") || tipoMovimiento.equalsIgnoreCase("R")) {
                movimientoList = movimientosBean.getResultMovimientoListByRamoAfectado(tipoMovimiento,estatusMovimientoId,year,ramoSel);
            }else{
                if (!Utileria.isTipoOficioRequerido(estatusMovimientoId)) {
                    movimientoList = movimientosBean.getResultMovimientoListByRamoAfectadoTransAmp(tipoMovimiento,estatusMovimientoId,year,ramoSel);
                }else{
                    movimientoList = movimientosBean.getResultMovimientoListByRamoAfectadoTransAmpTipof(tipoMovimiento,estatusMovimientoId,year,ramoSel);
                }
            }
        }else{
            if (opcion == 0) {
                if (tipoMovimiento.equalsIgnoreCase("C") || tipoMovimiento.equalsIgnoreCase("R")) {
                    if (rol.equals(movimientosBean.getRolNormativo())) {
                        movimientoList = movimientosBean.getMovimientoByTipoMov(tipoMovimiento, estatusMovimientoId, year);
                        ramoList = movimientosBean.getResultSQLgetRamoListByMTipoMovto(tipoMovimiento, estatusMovimientoId, year);
                    } else {
                        movimientoList = movimientosBean.getMovimientoByTipoMovEstatusMovAppLogin(tipoMovimiento, estatusMovimientoId, year, appLogin);
                    }
                } else {
                    if (tipoMovimiento.equalsIgnoreCase("A") || tipoMovimiento.equalsIgnoreCase("T")) {

                        //if (!Utileria.isTipoOficioRequerido(estatusMovimientoId)) {
                        if (!Utileria.isTipoOficioRequerido(estatusMovimientoId)) {
                            if (rol.equals(movimientosBean.getRolNormativo())) {
                                movimientoList = movimientosBean.getMovimientoByTipoMov(tipoMovimiento, estatusMovimientoId, year);
                                ramoList = movimientosBean.getResultSQLgetRamoListByMTipoMovtoAmpTrans(tipoMovimiento, estatusMovimientoId, year);
                            } else {
                                movimientoList = movimientosBean.getMovimientoByTipoMovEstatusMovAppLogin(tipoMovimiento, estatusMovimientoId, year, appLogin);
                            }
                        } else {
                            if (rol.equals(movimientosBean.getRolNormativo())) {
                                movimientoList = movimientosBean.getMovimientoByTipoMovART(tipoMovimiento, estatusMovimientoId, year);
                                ramoList = movimientosBean.getResultSQLgetRamoListByMTipoMovtoAmpTransTipof(tipoMovimiento, estatusMovimientoId, year);
                            } else {
                                movimientoList = movimientosBean.getMovimientoByTipoMovEstatusMovAppLoginART(tipoMovimiento, estatusMovimientoId, year, appLogin);
                            }
                        }
                    }
                }
            } else if (opcion == 1) {
                movto = movimientosBean.getResultSQLMovimientoByFolio(appLogin, folio, year, rol.equals(movimientosBean.getRolNormativo()));
                if (movto != null) {
                    if (Utileria.isTipoOficioRequerido(movto.getStatus()) && (movto.getTipoMovimiento().equals("A") || movto.getTipoMovimiento().equals("T"))) {
                        movimientoList = movimientosBean.getResultSQLMovimientoTipoOficioByFolio(appLogin, folio, year, rol.equals(movimientosBean.getRolNormativo()));
                    } else {
                        movimientoList.add(movto);
                    }
                } else {
                    strResult += "1";
                }
            } else {
                movimientoList = movimientosBean.getResultMovimientosByRamo(ramo, year);
            } 
        }
        
        for (Movimiento solicitudMovimiento : movimientoList) {

            contPar++;

            if (contPar % 2 == 0) {
                rowPar = "rowPar";
            } else {
                rowPar = "";
            }

            btnVer = "<input type='button' class='btnbootstrap-drop btn-edicion' id='btn-edicion-" + solicitudMovimiento.getOficio() + "' " + " onClick='consultaSolicitudMovto(" + solicitudMovimiento.getOficio() + ",\"" + solicitudMovimiento.getStatus() + "\",\"" + solicitudMovimiento.getTipoMovimiento() + "\",\"" + solicitudMovimiento.getTipoOficio() + "\")' />";
            btnInfo = "";

            if (solicitudMovimiento.getStatus().equalsIgnoreCase("C") || solicitudMovimiento.getStatus().equalsIgnoreCase("R")) {
                btnInfo = "<input type='button' class='btnbootstrap-drop btn-infor' title='" + solicitudMovimiento.getObsRechazo() + "' " + "  />";
            }

            //strResult += "<tr class='" + rowPar + "'>";
            strResult += "<tr onclick='actCamposConsultaSolicitudes(\"" + solicitudMovimiento.getOficio() + "\",\"" + solicitudMovimiento.getTipoMovimiento() + "\",\"" + solicitudMovimiento.getStatus() + "\",\"" + movoficioLigadoBean.getRamoByOficio(solicitudMovimiento.getOficio()) + "\")' >";

            if (!Utileria.isTipoOficioRequerido(solicitudMovimiento.getStatus())) {
                strResult += "<td>" + solicitudMovimiento.getOficio() + "</td>";
            } else {
                if (solicitudMovimiento.getTipoOficio().equalsIgnoreCase("")) {
                    strResult += "<td>" + solicitudMovimiento.getOficio() + "</td>";
                } else {
                    strResult += "<td>" + solicitudMovimiento.getOficio() + "-" + solicitudMovimiento.getTipoOficio() + "</td>";
                }
            }

            if (solicitudMovimiento.getFechaElab() != null) {
                strResult += "<td>" + objFechas.getFechaFormato(solicitudMovimiento.getFechaElab(), Fechas.FORMATO_CORTO) + "</td>";
            } else {
                strResult += "<td>" + "" + "</td>";
            }

            if (solicitudMovimiento.getFecPPTO() != null) {
                strResult += "<td>" + objFechas.getFechaFormato(solicitudMovimiento.getFecPPTO(), Fechas.FORMATO_CORTO) + "</td>";
            } else {
                strResult += "<td>" + "" + "</td>";
            }

            strResult += "<td>" + solicitudMovimiento.getRamo() + "</td>";

            if (solicitudMovimiento.getStatusDescr() != null) {
                strResult += "<td>" + solicitudMovimiento.getStatusDescr() + "</td>";
            } else {
                strResult += "<td>" + "" + "</td>";
            }

            btnLigado = "";

            if (!movimientosBean.isParaestatal() && !movimientosBean.getResultSqlGetIsAyuntamiento()) {
                if (movoficioLigadoBean.isOficioLigado(year, solicitudMovimiento.getOficio(), "N")) {

                    btnLigado += "<a><input type='button' class='btnbootstrap-drop btn-infoLigado' title='' " + "  /></a>";
                    btnLigado += "<div class='tooltiptext' style='display: none;' > ";
                    btnLigado += "    <table width='250' BORDER='2'   > ";
                    btnLigado += "      <thead> ";
                    btnLigado += "        <tr Style='background-color: #21ADE4' > ";
                    btnLigado += "          <th>Tipo</th> ";
                    btnLigado += "          <th>Organismo</th> ";
                    btnLigado += "          <th>Oficio</th> ";
                    btnLigado += "        </tr> ";
                    btnLigado += "      </thead> ";
                    btnLigado += "      <tbody> ";

                    if (movoficioLigadoBean.isOficioLigadoNormativo(year, solicitudMovimiento.getOficio())) {

                        btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                        btnLigado += "          <td>Normativo</td> ";
                        btnLigado += "          <td>Central</td> ";
                        btnLigado += "          <td>" + solicitudMovimiento.getOficio() + "</td> ";
                        btnLigado += "        </tr> ";

                        movOficiosLigados = movoficioLigadoBean.getMovOficioLigados(year, solicitudMovimiento.getOficio());

                        for (MovoficioLigado movoficioLigado : movOficiosLigados) {

                            if (movoficioLigado.isLigadoParaestatal()) {
                                btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                                btnLigado += "          <td>Ejecutor</td> ";
                                btnLigado += "          <td>Paraestatal</td> ";
                                btnLigado += "          <td>" + movoficioLigado.getOficioLigado() + "</td> ";
                                btnLigado += "        </tr> ";
                            } else {
                                btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                                btnLigado += "          <td>Ejecutor</td> ";
                                btnLigado += "          <td>Central</td> ";
                                btnLigado += "          <td>" + movoficioLigado.getOficioLigado() + "</td> ";
                                btnLigado += "        </tr> ";
                            }

                        }

                    } else {

                        int oficioLigadoNormativo = 0;

                        oficioLigadoNormativo = movoficioLigadoBean.getOficioLigadoNormativo(year, solicitudMovimiento.getOficio(), "N");

                        btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                        btnLigado += "          <td>Normativo</td> ";
                        btnLigado += "          <td>Central</td> ";
                        btnLigado += "          <td>" + oficioLigadoNormativo + "</td> ";
                        btnLigado += "        </tr> ";

                        movOficiosLigados = movoficioLigadoBean.getMovOficioLigados(year, oficioLigadoNormativo);

                        for (MovoficioLigado movoficioLigado : movOficiosLigados) {
                            if (movoficioLigado.isLigadoParaestatal()) {
                                btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                                btnLigado += "          <td>Ejecutor</td> ";
                                btnLigado += "          <td>Paraestatal</td> ";
                                btnLigado += "          <td>" + movoficioLigado.getOficioLigado() + "</td> ";
                                btnLigado += "        </tr> ";
                            } else {
                                btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                                btnLigado += "          <td>Ejecutor</td> ";
                                btnLigado += "          <td>Central</td> ";
                                btnLigado += "          <td>" + movoficioLigado.getOficioLigado() + "</td> ";
                                btnLigado += "        </tr> ";
                            }
                        }

                    }

                    btnLigado += "      </tbody> ";
                    btnLigado += "    </table> ";
                    btnLigado += "</div> ";

                }

            } else {

                if (movoficioLigadoBean.isOficioLigado(year, solicitudMovimiento.getOficio(), "S")) {

                    btnLigado += "<a href='#test'><input type='button' class='btnbootstrap-drop btn-infoLigado' title='' " + "  /></a>";
                    btnLigado += "<div class='tooltiptext' style='display: none;' > ";
                    btnLigado += "    <table width='250' BORDER='2' > ";
                    btnLigado += "      <thead> ";
                    btnLigado += "        <tr Style='background-color: #21ADE4'> ";
                    btnLigado += "          <th>Tipo</th> ";
                    btnLigado += "          <th>Organismo</th> ";
                    btnLigado += "          <th>Oficio</th> ";
                    btnLigado += "        </tr> ";
                    btnLigado += "      </thead> ";
                    btnLigado += "      <tbody> ";

                    int oficioLigadoNormativo = 0;

                    oficioLigadoNormativo = movoficioLigadoBean.getOficioLigadoNormativo(year, solicitudMovimiento.getOficio(), "S");

                    btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                    btnLigado += "          <td>Normativo</td> ";
                    btnLigado += "          <td>Central</td> ";
                    btnLigado += "          <td>" + oficioLigadoNormativo + "</td> ";
                    btnLigado += "        </tr> ";

                    movOficiosLigados = movoficioLigadoBean.getMovOficioLigados(year, oficioLigadoNormativo);

                    for (MovoficioLigado movoficioLigado : movOficiosLigados) {
                        if (movoficioLigado.isLigadoParaestatal()) {
                            btnLigado += "        <tr Style = 'background-color: #f2f2f2' > ";
                            btnLigado += "          <td>Ejecutor</td> ";
                            btnLigado += "          <td>Paraestatal</td> ";
                            btnLigado += "          <td>" + movoficioLigado.getOficioLigado() + "</td> ";
                            btnLigado += "        </tr> ";
                        } else {
                            btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                            btnLigado += "          <td>Ejecutor</td> ";
                            btnLigado += "          <td>Central</td> ";
                            btnLigado += "          <td>" + movoficioLigado.getOficioLigado() + "</td> ";
                            btnLigado += "        </tr> ";
                        }
                    }

                    btnLigado += "      </tbody> ";
                    btnLigado += "    </table> ";
                    btnLigado += "</div> ";

                }
            }

            btnJust = "<input type='button' class='btnbootstrap-drop btn-infoGlobe' title='" + solicitudMovimiento.getJutificacion() + "' " + "  />";
            btnBitacora = "<input type='button' class='btnbootstrap-drop btn-ConsultaBitacora' title='Consultar Bitacora Firmas'  onClick=\"SeguimientoFirmasBitacora('" + solicitudMovimiento.getOficio() + "','" + solicitudMovimiento.getTipoOficio() + "')\"" + "  />";
            // strResult += "<td></td>";
            strResult += "<td>" + btnLigado + btnBitacora + btnJust + btnInfo + btnVer + "</td>";
            strResult += "</tr>";

        }

        //Script para tooltip qtip2 plugin
        strResult += "<script> ";
        strResult += "$('a').each(function() { ";
        strResult += "    $(this).qtip({ ";
        strResult += "        content: { ";
        strResult += "            text: $(this).next('.tooltiptext') ";
        strResult += "        }, ";
        strResult += "	style: { ";
        strResult += "		classes: 'qtip-light qtip-shadow tooltipHtml'  ";
        strResult += "	}     ";
        strResult += "    }); ";
        strResult += "}); ";
        strResult += "</script> ";
        
        if(opcion == 0){
            strResult += "|";
            strResult += "<option value='-1'>-- Selecciona ramo afectado --</option>";
            for(Ramo ramoAfectado : ramoList){
                strResult += "<option value="+ramoAfectado.getRamo()+">"+ramoAfectado.getRamo()+"-"+ramoAfectado.getRamoDescr()+"</option>";
            }
        }

    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        out.print(strResult);
    }
%>
