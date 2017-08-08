<%-- 
    Document   : ajaxGetMovimientos
    Created on : 10/12/2015, 09:35:40 AM
    Author     : rharo
--%>

<%@page import="gob.gbc.entidades.MovoficioLigado"%>
<%@page import="gob.gbc.aplicacion.MovoficioLigadoBean"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="gob.gbc.entidades.TipoOficio"%>
<%@page import="gob.gbc.util.Fechas"%>
<%@page import="gob.gbc.entidades.Movimiento"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    final int AUTORIZACION_PLANEACION = 3;

    String usuario = new String();
    Fechas objFechas = new Fechas();
    AutorizacionBean autorizacionBean = null;
    MovoficioLigadoBean movoficioLigadoBean = null;
    Ramo ramoTemp = null;
    NumberFormat dFormat = NumberFormat.getInstance(Locale.US);
    List<Movimiento> movimientoList = new ArrayList<Movimiento>();
    List<TipoOficio> movimientoTipoOficioList = new ArrayList<TipoOficio>();
    List<MovoficioLigado> movOficiosLigados = new ArrayList<MovoficioLigado>();
    String tipoDependencia = new String();
    String strResultado = new String();
    String estatusBase = new String();
    String tipoMovId = new String();
    String rowPar = new String();
    String btnVer = new String();
    String ramo = new String();
    String btnJust = new String();
    String ramoInList = new String();
    String btnRamosAsoc = new String();
    String btnLigado = new String();
    String strFoliosLigados = "";
    BigDecimal totalMovto = new BigDecimal(0);
    int opcion = 0;
    int contPar = 0;
    int year = 0;
    int tipoFlujo = 0;

    try {

        request.setCharacterEncoding("UTF-8");

        if (session.getAttribute("strUsuario") == null || !((String) session.getAttribute("strUsuario")).equals("")) {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && !((String) session.getAttribute("tipoDependencia")).equals("")) {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (Utilerias.existeParametro("opcion", request)) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (Utilerias.existeParametro("estatusBase", request)) {
            estatusBase = request.getParameter("estatusBase");
        }
        if (Utilerias.existeParametro("tipoMovId", request)) {
            tipoMovId = request.getParameter("tipoMovId");
        }
        if (Utilerias.existeParametro("tipoFlujo", request)) {
            tipoFlujo = Integer.parseInt(request.getParameter("tipoFlujo"));
        }
        if (Utilerias.existeParametro("ramo", request)) {
            ramo = request.getParameter("ramo");
        }
        if (Utilerias.existeParametro("ramoSelect", request)) {
            ramoInList = (String) request.getParameter("ramoSelect");
        }

        autorizacionBean = new AutorizacionBean(tipoDependencia);
        autorizacionBean.setStrServer(request.getHeader("host"));
        autorizacionBean.setStrUbicacion(getServletContext().getRealPath(""));
        autorizacionBean.resultSQLConecta(tipoDependencia);
        movoficioLigadoBean = new MovoficioLigadoBean(tipoDependencia);
        movoficioLigadoBean.setStrServer(request.getHeader("host"));
        movoficioLigadoBean.setStrUbicacion(getServletContext().getRealPath(""));
        movoficioLigadoBean.resultSQLConecta(tipoDependencia);

        switch (opcion) {
            case 1:
                strResultado += "<table id='tblMovimientos'>";
                strResultado += "<thead>";
                strResultado += "<tr>";
                strResultado += "<th>Oficio</th>";
                strResultado += "<th>Fecha<br/>Elaboraci&oacute;n</th>";
                strResultado += "<th>Fecha Env&iacute;o<br/>Autorizaci&oacute;n</th>";
                strResultado += "<th>Ramo</th>";
                strResultado += "<th>Total</th>";
                strResultado += "<th></th>";
                strResultado += "<th>&nbsp;</th>";
                strResultado += "</tr>";
                strResultado += "</thead>";
                strResultado += "<tbody>";

                if (tipoFlujo == AUTORIZACION_PLANEACION && (tipoMovId.equals("A") || tipoMovId.equals("T"))) {

                    movimientoTipoOficioList = autorizacionBean.getMovimientoByTipoMovUsrTipoOficio(tipoMovId, estatusBase, usuario, tipoDependencia, year, tipoFlujo, ramoInList);

                    for (TipoOficio movimiento : movimientoTipoOficioList) {
                        contPar++;
                        if (contPar % 2 == 0) {
                            rowPar = "rowPar";
                        } else {
                            rowPar = "";
                        }

                        btnVer = "<input type='button' class='btnbootstrap-drop btn-ver' id='btn-ver-" + movimiento.getOficio() + "' "
                                + " onClick='verMovtoAutorizar(\"" + movimiento.getOficio() + "\",\"" + movimiento.getStatus() + "\",\""
                                + movimiento.getTipoMovimiento() + "\",\"" + movimiento.getTipoOficio() + "\",\"" + tipoFlujo + "\")' />";

                        strResultado += "<tr class='" + rowPar + "'>";
                        strResultado += "<td>" + movimiento.getOficio() + " - " + movimiento.getTipoOficio() + "</td>";
                        strResultado += "<td>" + objFechas.getFechaFormato(movimiento.getFechaElab(), Fechas.FORMATO_CORTO) + "</td>";
                        strResultado += "<td>" + objFechas.getFechaFormato(movimiento.getFecPPTO(), Fechas.FORMATO_CORTO) + "</td>";
                        ramoTemp = new Ramo();
                        ramoTemp = autorizacionBean.getResultGetRamoByRamoIdYear(movimiento.getRamo(), year);
                        if (ramoTemp != null) {
                            strResultado += "<td>" + ramoTemp.getRamo() + " " + ramoTemp.getAbreviatura() + "</td>";
                        }
                        totalMovto = new BigDecimal(autorizacionBean.getResultSQLGetImporteMovto(movimiento.getOficio(), movimiento.getTipoMovimiento()));
                        strResultado += "<td>" + dFormat.format(totalMovto.doubleValue()) + "</td>";
                        btnRamosAsoc = "<input type='button' class='btnbootstrap-drop btn-infoAsociado' title='" + "Ramos asociados al oficio" + "' " + " onclick = 'getRamosAsociadosOficioByOficio(" + movimiento.getOficio() + "," + year + ");'  />";
                        btnJust = "<input type='button' class='btnbootstrap-drop btn-infoGlobe' title='" + movimiento.getJutificacion() + "' " + "  />";
                        strResultado += "<td></td>";

                        btnLigado = "";

                        if (!movoficioLigadoBean.isParaestatal() && !movoficioLigadoBean.getResultSqlGetIsAyuntamiento()) {
                            if (movoficioLigadoBean.isOficioLigado(year, movimiento.getOficio(), "N")) {

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

                                if (movoficioLigadoBean.isOficioLigadoNormativo(year, movimiento.getOficio())) {

                                    btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                                    btnLigado += "          <td>Normativo</td> ";
                                    btnLigado += "          <td>Central</td> ";
                                    btnLigado += "          <td>" + movimiento.getOficio() + "</td> ";
                                    btnLigado += "        </tr> ";

                                    movOficiosLigados = movoficioLigadoBean.getMovOficioLigados(year, movimiento.getOficio());

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

                                    oficioLigadoNormativo = movoficioLigadoBean.getOficioLigadoNormativo(year, movimiento.getOficio(), "N");

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

                            if (movoficioLigadoBean.isOficioLigado(year, movimiento.getOficio(), "S")) {

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

                                oficioLigadoNormativo = movoficioLigadoBean.getOficioLigadoNormativo(year, movimiento.getOficio(), "S");

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

                        strResultado += "<td>" + btnLigado + btnRamosAsoc + btnJust + btnVer + "</td>";
                        strResultado += "</tr>";
                    }
                } else {

                    movimientoList = autorizacionBean.getMovimientoByTipoMovUsr(tipoMovId, estatusBase, usuario, tipoDependencia, "A", year, tipoFlujo, ramoInList);

                    for (Movimiento movimiento : movimientoList) {
                        contPar++;
                        if (contPar % 2 == 0) {
                            rowPar = "rowPar";
                        } else {
                            rowPar = "";
                        }

                        btnVer = "<input type='button' class='btnbootstrap-drop btn-ver' id='btn-ver-" + movimiento.getOficio() + "' "
                                + " onClick='verMovtoAutorizar(\"" + movimiento.getOficio() + "\",\"" + movimiento.getStatus() + "\",\""
                                + movimiento.getTipoMovimiento() + "\",null,\"" + tipoFlujo + "\")' />";

                        strResultado += "<tr class='" + rowPar + "'>";
                        strResultado += "<td>" + movimiento.getOficio() + "</td>";
                        strResultado += "<td>" + objFechas.getFechaFormato(movimiento.getFechaElab(), Fechas.FORMATO_CORTO) + "</td>";
                        strResultado += "<td>" + objFechas.getFechaFormato(movimiento.getFecPPTO(), Fechas.FORMATO_CORTO) + "</td>";
                        ramoTemp = new Ramo();
                        ramoTemp = autorizacionBean.getResultGetRamoByRamoIdYear(movimiento.getRamo(), year);
                        if (ramoTemp != null) {
                            strResultado += "<td>" + ramoTemp.getRamo() + " " + ramoTemp.getAbreviatura() + "</td>";
                        }
                        totalMovto = new BigDecimal(autorizacionBean.getResultSQLGetImporteMovto(movimiento.getOficio(), movimiento.getTipoMovimiento()));
                        strResultado += "<td>" + dFormat.format(totalMovto.setScale(2, RoundingMode.HALF_UP)) + "</td>";
                        btnRamosAsoc = "<input type='button' class='btnbootstrap-drop btn-infoAsociado' title='" + "Ramos asociados al oficio" + "' " + " onclick = 'getRamosAsociadosOficioByOficio(" + movimiento.getOficio() + "," + year + ");'  />";
                        btnJust = "<input type='button' class='btnbootstrap-drop btn-infoGlobe' title='" + movimiento.getJutificacion() + "' " + "  />";
                        strResultado += "<td></td>";

                        btnLigado = "";

                        if (!movoficioLigadoBean.isParaestatal() && !movoficioLigadoBean.getResultSqlGetIsAyuntamiento()) {
                            if (movoficioLigadoBean.isOficioLigado(year, movimiento.getOficio(), "N")) {

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

                                if (movoficioLigadoBean.isOficioLigadoNormativo(year, movimiento.getOficio())) {

                                    btnLigado += "        <tr Style = 'background-color: #f2f2f2'> ";
                                    btnLigado += "          <td>Normativo</td> ";
                                    btnLigado += "          <td>Central</td> ";
                                    btnLigado += "          <td>" + movimiento.getOficio() + "</td> ";
                                    btnLigado += "        </tr> ";

                                    movOficiosLigados = movoficioLigadoBean.getMovOficioLigados(year, movimiento.getOficio());

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

                                    oficioLigadoNormativo = movoficioLigadoBean.getOficioLigadoNormativo(year, movimiento.getOficio(), "N");

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

                            if (movoficioLigadoBean.isOficioLigado(year, movimiento.getOficio(), "S")) {

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

                                oficioLigadoNormativo = movoficioLigadoBean.getOficioLigadoNormativo(year, movimiento.getOficio(), "S");

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
                        strResultado += "<td>" + btnLigado + btnRamosAsoc + btnJust + btnVer + "</td>";
                        strResultado += "</tr>";
                    }
                }

                strResultado += "</tbody>";
                strResultado += "</table>";
                break;
            case 2:
                strResultado += "<table id='tblMovimientos'>";
                strResultado += "<thead>";
                strResultado += "<tr>";
                strResultado += "<th>&nbsp;</th>";
                strResultado += "<th>Oficio</th>";
                strResultado += "<th>Fecha<br/>Elaboraci&oacute;n</th>";
                strResultado += "<th>Usuario</th>";
                strResultado += "<th>Fecha <br/>Autorizaci&oacute;n</th>";
                strResultado += "</tr>";
                strResultado += "</thead>";
                strResultado += "<tbody>";

                movimientoList = autorizacionBean.getMovimientoByRamoUsr(year, usuario, ramo);

                for (Movimiento movimiento : movimientoList) {
                    contPar++;
                    if (contPar % 2 == 0) {
                        rowPar = "rowPar";
                    } else {
                        rowPar = "";
                    }

                    strResultado += "<tr class='" + rowPar + "'>";
                    strResultado += "<td><input type='radio' name='chkMovimientos' value='" + movimiento.getTipoMovimiento() + "-" + movimiento.getOficio() + "'></td>";
                    strResultado += "<td>" + movimiento.getOficio() + "</td>";
                    strResultado += "<td>" + objFechas.getFechaFormato(movimiento.getFechaElab(), Fechas.FORMATO_CORTO) + "</td>";
                    strResultado += "<td>" + movimiento.getAppLogin() + "</td>";
                    strResultado += "<td>" + objFechas.getFechaFormato(movimiento.getFechaAutorizacion(), Fechas.FORMATO_CORTO) + "</td>";
                    strResultado += "</tr>";
                }
                strResultado += "</tbody>";
                strResultado += "</table>";
                break;
        }

        strResultado += "<script> ";
        strResultado += "$('a').each(function() { ";
        strResultado += "    $(this).qtip({ ";
        strResultado += "        content: { ";
        strResultado += "            text: $(this).next('.tooltiptext') ";
        strResultado += "        }, ";
        strResultado += "	style: { ";
        strResultado += "		classes: 'qtip-light qtip-shadow tooltipHtml'  ";
        strResultado += "	}     ";
        strResultado += "    }); ";
        strResultado += "}); ";
        strResultado += "</script> ";

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (autorizacionBean != null) {
            autorizacionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

