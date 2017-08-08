<%-- 
    Document   : ajaxGetInfoRequerimientos
    Created on : May 6, 2015, 2:13:37 PM
    Author     : ugarcia
--%>

<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="gob.gbc.aplicacion.PartidaBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.TipoGasto"%>
<%@page import="gob.gbc.entidades.FuenteFinanciamiento"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.entidades.Articulo"%>
<%@page import="gob.gbc.entidades.RelacionLaboral"%>
<%@page import="gob.gbc.entidades.FuenteRecurso"%>
<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    AccionBean accionBean = null;
    Accion accion = new Accion();
    Requerimiento req = new Requerimiento();
    List<Partida> partidaList = new ArrayList<Partida>();
    List<Articulo> articuloList = new ArrayList<Articulo>();
    List<FuenteRecurso> fuenteList = new ArrayList<FuenteRecurso>();
    List<FuenteFinanciamiento> fuenteFinList = new ArrayList<FuenteFinanciamiento>();
    List<RelacionLaboral> relacionLabList = new ArrayList<RelacionLaboral>();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
    String tipoC = new String();
    String strResultado = new String();
    String editDis = new String();
    String disabledRel = new String();
    String disableJus = new String();
    String disableReqPPTO = new String();
    String ramoId = new String();
    String programaId = new String();
    String selected = new String();
    String fuenteRec = new String();
    String tipoDependencia = new String();
    String numTemp = new String();
    String disabled = new String();
    String disabledConsulta = new String();
    String ramoDescr = new String();
    String metaDescr = new String();
    String obraAccion = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String tipoGasto = new String();
    String numObra = new String();
    String articuloId = new String();
    double costoViejo[] = new double[12];
    int requerimiento = 0;
    int metaId = 0;
    int year = 0;
    int justific = 0;
    int accionId = 0;
    int consulta = 0;
    int opcion = 0;
    int cont = 1;
    boolean cierreReqPPTO = false;
    boolean requDescr = false;
    boolean cierrePPTO = false;
    boolean reqDescr = false;
    try {

        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("requerimiento") != null && !request.getParameter("requerimiento").equals("")) {
            numTemp = request.getParameter("requerimiento");
            requerimiento = Integer.parseInt(numTemp.trim());
        }
        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = request.getParameter("programaId");
        }
        if (request.getParameter("consulta") != null && !request.getParameter("consulta").equals("")) {
            consulta = Integer.parseInt(request.getParameter("consulta"));
        }
        if (request.getParameter("tipoGasto") != null && !request.getParameter("tipoGasto").equals("")) {
            tipoGasto = request.getParameter("tipoGasto");
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("accion") != null && !request.getParameter("accion").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accion"));
        }
        if (request.getParameter("opcion") != null && !request.getParameter("opcion").equals("")) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramoDescr") != null && !request.getParameter("ramoDescr").equals("")) {
            ramoDescr = request.getParameter("ramoDescr");
        }
        if (request.getParameter("metaDescr") != null && !request.getParameter("metaDescr").equals("")) {
            metaDescr = request.getParameter("metaDescr");
        }
        if (request.getParameter("programaDescr") != null && !request.getParameter("programaDescr").equals("")) {
            programaDescr = request.getParameter("programaDescr");
        }
        if (request.getParameter("proyectoDescr") != null && !request.getParameter("proyectoDescr").equals("")) {
            proyectoDescr = request.getParameter("proyectoDescr");
        }
        if (request.getParameter("obra") != null && !request.getParameter("obra").equals("")) {
            obraAccion = request.getParameter("obra");
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

        NumberFormat numberF = NumberFormat.getInstance(Locale.US);
        accion = accionBean.getAccionById(year, ramoId, metaId, accionId);
        reqDescr = accionBean.isRequDescrCerrado(ramoId, year);
        numObra = accionBean.getNumeroObra(year, ramoId, metaId, accionId);
        if (reqDescr) {
            disabled = "disabled";
        } else {
            disabled = "";
        }
        requDescr = accionBean.isRequDescrCerrado(ramoId, year);
        if (requDescr) {
            disableJus = "disabled";
        } else {
            disableJus = "";
        }
        cierreReqPPTO = accionBean.getCierreRequerimientoPPTO(year, ramoId);
        if (cierreReqPPTO) {
            disableReqPPTO = "disabled";
        } else {
            disableReqPPTO = "";
            disabledRel = "";
        }
        if (!numObra.equals("0")) {
            disabledConsulta = "disabled";
        }
        if (consulta == 1) {
            disabledConsulta = "disabled";
        }
        if (cierreReqPPTO) {
            cierrePPTO = true;
        }
        if (opcion == 1) {
            req.setReq("");
            disabledRel = "display: none;";
            req.setPartida("");
            req.setFuenteFin("");
            req.setFondo("");
            req.setRecurso("");
            req.setTipoGasto("");
            req.setRelLaboral("-1");
            req.setCostoAnual(0.0);
            req.setCostoUnitario(0.0);

        } else {
            if (opcion == 2) {
                req = new Requerimiento();
                req = accionBean.getRequerimientoById(year, ramoId, programaId, metaId, accionId, requerimiento);
                fuenteList = accionBean.getFuenteRecuros(year, Integer.parseInt(req.getFuenteFin()));
                justific = accionBean.isPartidaJustific(year, req.getPartida());
                tipoGasto = accionBean.getTipoGastoByPartida(req.getPartida(), year);
                editDis = "disabled";
                if (!accionBean.isRelLaboral(year, req.getPartida())) {
                    disabledRel = "visibility:hidden;";
                } else {
                    disabledRel = "visibility:visible";
                }
                costoViejo[0] = req.getCantEne() * req.getCostoUnitario();
                costoViejo[1] = req.getCantFeb() * req.getCostoUnitario();
                costoViejo[2] = req.getCantMar() * req.getCostoUnitario();
                costoViejo[3] = req.getCantAbr() * req.getCostoUnitario();
                costoViejo[4] = req.getCantMay() * req.getCostoUnitario();
                costoViejo[5] = req.getCantJun() * req.getCostoUnitario();
                costoViejo[6] = req.getCantJul() * req.getCostoUnitario();
                costoViejo[7] = req.getCantAgo() * req.getCostoUnitario();
                costoViejo[8] = req.getCantSep() * req.getCostoUnitario();
                costoViejo[9] = req.getCantOct() * req.getCostoUnitario();
                costoViejo[10] = req.getCantNov() * req.getCostoUnitario();
                costoViejo[11] = req.getCantDic() * req.getCostoUnitario();
                //session.setAttribute("costoViejo", costoViejo);
            }
        }
        if (!cierrePPTO) {
            strResultado += "<table id='informacionAccion' cellspacing='10'>";
            strResultado += "   <tr>";
            strResultado += "       <td> Ramo </td>";
            strResultado += "       <td>" + ramoDescr + "</td>";
            strResultado += "   </tr>";
            strResultado += "   <tr>";
            strResultado += "       <td> Programa </td>";
            strResultado += "       <td>" + programaDescr + "</td>";
            strResultado += "   </tr>";
            strResultado += "   <tr> ";
            strResultado += "       <td> Proy./Act. </td>";
            strResultado += "       <td>" + proyectoDescr + "</td> ";
            strResultado += "   </tr>";
            strResultado += "   <tr> ";
            strResultado += "       <td> Meta </td>";
            strResultado += "       <td>" + metaDescr + "</td> ";
            strResultado += "   </tr>";
            strResultado += "   <tr> ";
            strResultado += "       <td> Acción </td>";
            strResultado += "       <td>" + accionId + "-" + accion.getAccion() + "</td> ";
            strResultado += "   </tr>";
            if (!obraAccion.equals("")) {
                strResultado += "   <tr> ";
                strResultado += "       <td> Obra </td>";
                strResultado += "       <td>" + obraAccion + "</td> ";
                strResultado += "   </tr>";
            }
            strResultado += "</table>";
            strResultado += "</br>";

            estimacionList = accionBean.getEstimacionByAccion(year, ramoId, metaId, accionId);
            tipoC = accionBean.getTipoCalculo(year, ramoId, metaId, accionId);

            if (opcion == 2) {
                strResultado += "<input id='reqJust' type='hidden' value='" + justific + "' />";
            }

            if (estimacionList.size() > 0) {
                strResultado += "<center>";
                strResultado += "   <label>PROGRAMACI&Oacute;N DE ACCI&Oacute;N POR MES </label>";
                strResultado += "</center>";
                strResultado += "<div id='calenVista'>";
                for (Estimacion estimacion : estimacionList) {
                    strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                    cont++;
                }
                strResultado += "</div>";

            } else {

                strResultado += "<center>";
                strResultado += "   <label>PROGRAMACI&Oacute;N DE ACCI&Oacute;N POR MES </label>";
                strResultado += "</center>";
                strResultado += "</br>";

            }

            strResultado += "<form id='frmReq'>";
            if (opcion == 1) {
                strResultado += "<table>";
            } else {
                strResultado += "<table><tr><td> Requerimiento: &nbsp" + req.getReqId() + "</td> </tr>";
            }
            strResultado += "<tr id='partidaGasto'> <td> <div> Partida </div> <select id='selPartida' onchange='getArticulos()' "
                    + "onclomplete='changeRelacionLab($('#isRelLaboral').val());' " + editDis + " tabindex='1' " + disabledConsulta + ">";
            strResultado += "<option value='-1'> Seleccione </option> ";
            if (opcion == 2) {
                partidaList = accionBean.getResultSQLGetPartidasGeneral(year);
            } else if (opcion == 1) {
                partidaList = accionBean.getPartidaList(year, tipoDependencia);
            }
            for (Partida partida : partidaList) {
                if (req.getPartida().equals(partida.getPartidaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + partida.getPartidaId() + "' " + selected + ">"
                        + partida.getPartidaId() + "-" + partida.getPartida() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td>";
            strResultado += "<td id='divVwTipoGasto'>   <div> Tipo de gasto </div> <input id='vwTipoGasto' type='text' value='" + tipoGasto + "' disabled />";
            strResultado += "</td>";
            strResultado += "<td id='relTd' style='" + disabledRel + "'> <div> Relaci&oacute;n laboral </div> <select id='selRelacion' " + editDis + " " + disabledConsulta + "> ";
            strResultado += "<option value='0'> -- Selecciona una relaci&oacute;n laboral -- </option>";
            relacionLabList = accionBean.getRelacionLaboral(year);
            for (RelacionLaboral relLaboral : relacionLabList) {
                if (req.getRelLaboral().equals(relLaboral.getRelacionLabId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + relLaboral.getRelacionLabId() + "' " + selected + ">"
                        + relLaboral.getRelacionLabId() + "-" + relLaboral.getRelacionLab() + "</option>";
                selected = "";
            }
            strResultado += "</select>";
            strResultado += "</td> </tr>";

            if (req.getJustif() == null) {
                req.setJustif("");
            }

            strResultado += " <tr> <td id='colPartida' align='left' colspan='3'> <div> Requerimiento </div> ";
            if (opcion == 2) {
                if (req.getArticulo() != 0 && req.getArticulo() != -1) {
                    strResultado += "<select id='ArtPartida' " + disabled + " " + disabledConsulta + " onChange='getCosto()' tabindex=\"2\" >";
                    strResultado += "<option value='-1'> -- Seleccione un art&iacute;culo -- </option>";

                    articuloList = accionBean.getArticulosByPartida(year, req.getPartida());

                    for (Articulo articulo : articuloList) {
                        if (req.getGpogto() != 0 && req.getSubgpo() != 0) {
                            articuloId = req.getGpogto() + "." + req.getSubgpo() + "." + req.getArticulo();
                        } else {
                            articuloId = String.valueOf(req.getArticulo());
                        }

                        if (articuloId.equalsIgnoreCase(articulo.getArticuloId())) {
                            selected = "selected";
                        }

                        strResultado += "<option value='" + articulo.getArticuloId() + "' " + selected + " >" + articulo.getArticuloId() + "-" + articulo.getArticulo() + "</option>";
                        selected = "";
                    }

                    strResultado += "</select>";
                } else {
                    strResultado += "<textArea id='txtAreaPart' name='limitedtextarea' class='no-enter' tabindex='0' maxlength='300' " + disabled + " " + disabledConsulta + "  >" + req.getReq() + "</textArea>";
                }
            } else {
                strResultado += "<select > <option value='-1'> "
                        + "-- Seleccione un art&iacute;culo -- </option> </select> ";
            }
            strResultado += "</td> </tr>";
            /*if (req.getArticulo() <= 0) {
                
             strResultado += "<tr id='tdDescrRequ'> <td colspan='3'> <div> Descripci&oacute;n de requerimiento </div>";
             strResultado += "<textArea id='txtAreaPart' name='limitedtextarea' tabindex='0'"
             + "onKeyDown='limitText(this.form.limitedtextarea,this.form.countdown,100);'"
             + "onKeyUp='limitText(this.form.limitedtextarea,this.form.countdown,100);' " + disabled + "  >" + req.getReq() + "</textArea> </td> <tr> ";
             }*/
            strResultado += "<tr> <td colspan='3'> <div> Justificaci&oacute;n </div>";
            strResultado += "<textArea id='txtAreaJust' class='no-enter' maxlength='300' " + disableJus + "" + disabledConsulta + " tabindex='0' >" + req.getJustif().trim() + "</textArea> </td> <tr> ";
            strResultado += "<tr> <td colspan='3'>";
            strResultado += "<div id='divMese'>";

            strResultado += "<div> Ene <input type='text' id='inpTxtEne' class='capt-mes'  value='" + numberF.format(req.getCantEne()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtEne\");calculaCosto()' onkeyup='validaMascara(\"inpTxtEne\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Ene <input type='text' id='inpTxtEne' class='capt-mes'  value='" + numberF.format(req.getCantEne()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtEne\")' onkeyPress='pressPoint(event,\"inpTxtEne\")' " + disableReqPPTO +" "+disabledConsulta+"/> </div>";

            strResultado += "<div> Feb <input type='text' id='inpTxtFeb' class='capt-mes'  value='" + numberF.format(req.getCantFeb()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtFeb\");calculaCosto()' onkeyup='validaMascara(\"inpTxtFeb\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Feb <input type='text' id='inpTxtFeb' class='capt-mes'  value='" + numberF.format(req.getCantFeb()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtFeb\")' onkeyPress='pressPoint(event,\"inpTxtFeb\")' " + disableReqPPTO +" "+disabledConsulta+"/> </div>";

            strResultado += "<div> Mar <input type='text' id='inpTxtMar' class='capt-mes'  value='" + numberF.format(req.getCantMar()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtMar\");calculaCosto()' onkeyup='validaMascara(\"inpTxtMar\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Mar <input type='text' id='inpTxtMar' class='capt-mes'  value='" + numberF.format(req.getCantMar()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtMar\")' onkeyPress='pressPoint(event,\"inpTxtMar\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> Abr <input type='text' id='inpTxtAbr' class='capt-mes'  value='" + numberF.format(req.getCantAbr()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtAbr\");calculaCosto()' onkeyup='validaMascara(\"inpTxtAbr\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Abr <input type='text' id='inpTxtAbr' class='capt-mes'  value='" + numberF.format(req.getCantAbr()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtAbr\")' onkeyPress='pressPoint(event,\"inpTxtAbr\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> May <input type='text' id='inpTxtMay' class='capt-mes'  value='" + numberF.format(req.getCantMay()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtMay\");calculaCosto()' onkeyup='validaMascara(\"inpTxtMay\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> May <input type='text' id='inpTxtMay' class='capt-mes'  value='" + numberF.format(req.getCantMay()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtMay\")' onkeyPress='pressPoint(event,\"inpTxtMay\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> Jun <input type='text' id='inpTxtJun' class='capt-mes'  value='" + numberF.format(req.getCantJun()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtJun\");calculaCosto()' onkeyup='validaMascara(\"inpTxtJun\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Jun <input type='text' id='inpTxtJun' class='capt-mes'  value='" + numberF.format(req.getCantJun()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtJun\")' onkeyPress='pressPoint(event,\"inpTxtJun\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> Jul <input type='text' id='inpTxtJul' class='capt-mes'  value='" + numberF.format(req.getCantJul()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtJul\");calculaCosto()' onkeyup='validaMascara(\"inpTxtJul\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Jul <input type='text' id='inpTxtJul' class='capt-mes'  value='" + numberF.format(req.getCantJul()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtJul\")' onkeyPress='pressPoint(event,\"inpTxtJul\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> Ago <input type='text' id='inpTxtAgo' class='capt-mes'  value='" + numberF.format(req.getCantAgo()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtAgo\");calculaCosto()' onkeyup='validaMascara(\"inpTxtAgo\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Ago <input type='text' id='inpTxtAgo' class='capt-mes'  value='" + numberF.format(req.getCantAgo()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtAgo\")' onkeyPress='pressPoint(event,\"inpTxtAgo\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> Sep <input type='text' id='inpTxtSep' class='capt-mes'  value='" + numberF.format(req.getCantSep()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtSep\");calculaCosto()' onkeyup='validaMascara(\"inpTxtSep\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Sep <input type='text' id='inpTxtSep' class='capt-mes'  value='" + numberF.format(req.getCantSep()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtSep\")' onkeyPress='pressPoint(event,\"inpTxtSep\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> Oct <input type='text' id='inpTxtOct' class='capt-mes'  value='" + numberF.format(req.getCantOct()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtOct\");calculaCosto()' onkeyup='validaMascara(\"inpTxtOct\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Oct <input type='text' id='inpTxtOct' class='capt-mes'  value='" + numberF.format(req.getCantOct()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtOct\")' onkeyPress='pressPoint(event,\"inpTxtOct\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> Nov <input type='text' id='inpTxtNov' class='capt-mes'  value='" + numberF.format(req.getCantNov()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtNov\");calculaCosto()' onkeyup='validaMascara(\"inpTxtNov\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Nov <input type='text' id='inpTxtNov' class='capt-mes' ' value='" + numberF.format(req.getCantNov()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtNov\")' onkeyPress='pressPoint(event,\"inpTxtNov\")' " + disableReqPPTO +" "+disabledConsulta+" /> </div>";

            strResultado += "<div> Dic <input type='text' id='inpTxtDic' class='capt-mes'  value='" + numberF.format(req.getCantDic()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtDic\");calculaCosto()' onkeyup='validaMascara(\"inpTxtDic\")' " + disableReqPPTO + " " + disabledConsulta + "/> </div>";
            //strResultado += "<div> Dic <input type='text' id='inpTxtDic' class='capt-mes'  value='" + numberF.format(req.getCantDic()) + "' tabindex='0' maxlength='16' onblur='calculaCosto()' onfocus='selectEnteros(\"inpTxtDic\")' onkeyPress='pressPoint(event,\"inpTxtDic\")' " + disableReqPPTO +" "+disabledConsulta+"/> </div>";
            strResultado += "</div> <br/>";
            strResultado += "</td> </tr>";
            strResultado += "<tr> <td> <div> Cantidad <div> ";
            /*if(Utileria.getNumberOfDecimalPlaces(BigDecimal.valueOf(req.getCostoUnitario())) == 1){
             req.setCostoUnitario(req.getCostoUnitario() * 100);                
             }else if(req.getCantDic() > 99 && req.getCantDic() % 1 == 0){
             req.setCostoUnitario(req.getCostoUnitario() * 100);  
             }*/
            strResultado += "<input type='text' id='inpTxtCantidad' value='" + numberF.format(req.getCantidad()) + "' maxlength='14' disabled/> </td>";
            //strResultado += "<div> Dic <input type='text' id='inpTxtDic' class='capt-mes'  value='" + numberF.format(req.getCantNov()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"inpTxtDic\");calculaCosto()' onkeyup='validaMascara(\"inpTxtDic\")' " + disableReqPPTO +" "+disabledConsulta+"/> </div>";
            strResultado += "<td> <div> Costo unitario </div> <input type='text' id='inpTxtCantUnit' value='" + numberF.format(req.getCostoUnitario()) + "' onblur='agregarFormato(\"inpTxtCantUnit\");calculaCosto()' onkeyup='validaMascara(\"inpTxtCantUnit\")' tabindex='0' maxlength='16' " + disabledConsulta + " /> </td>";
            strResultado += "<td> <div> Costo anual </div> <input type='text' id='inpTxtCantAnual' maxlength='14' value='" + numberF.format(req.getCostoAnual()) + "' disabled/> </td> </tr>";

            strResultado += "</table>";
            strResultado += "<center> <div>";
            if (consulta != 1) {
                if (opcion == 2) {
                    strResultado += " <input id='edtRequerimiento' type='button' value='Aceptar' onclick='editarRequerimiento(" + req.getReqId() + ")'/>";
                    strResultado += " <input type='hidden' value='" + tipoGasto + "' id='selTipoGasto' />";
                } else {
                    strResultado += " <input id='nvoRequerimiento' type='button' value='Aceptar' onclick='nuevoRequerimiento()'/>";
                }
            }
            strResultado += " <input type='button' value='Cancelar' onclick='cerrarRequerimiento()'/> </div> </center> </form>";
            strResultado += "<script type='text/javascript'>";

            /*strResultado += "$('#inpTxtEne').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtFeb').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtMar').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtAbr').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtMay').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtJun').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtJul').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtAgo').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtSep').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtOct').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtNov').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtDic').mask('00,000,000,000,000.00', {reverse: true});";
             strResultado += "$('#inpTxtCantUnit').mask('00,000,000,000,000.00', {reverse: true});";*/
            strResultado += "              $('.capt-mes').keydown(function (e) {";
            strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||";
            strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
            strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
            strResultado += "                            return;";
            strResultado += "                  }";
            strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
            strResultado += " e.preventDefault();";
            strResultado += "                   }";
            strResultado += "                });";
            strResultado += "              $('#inpTxtCantUnit').keydown(function (e) {";
            strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||";
            strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
            strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
            strResultado += "                            return;";
            strResultado += "                  }";
            strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
            strResultado += " e.preventDefault();";
            strResultado += "                   }";
            strResultado += "                });";
            strResultado += "</script>";
            strResultado += "<script>  "+  
                   " $('textarea').bind('keypress', function (event) {"+
                    "    var regex = new RegExp('\\'');"+
                    "    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);"+
                   "     if (regex.test(key)) {"+
                   "        event.preventDefault();"+
                   "        return false;"+
                     "   }"+
                  "  });"+
                "</script>";
        } else {
            strResultado = "cerrado";
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
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

<script>
    $('.no-enter').bind('keypress', function(e){
        if(e.keyCode == 13)
        {
           return false;
        }
     });     
</script>