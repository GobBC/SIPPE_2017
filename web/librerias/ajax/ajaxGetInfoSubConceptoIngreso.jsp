<%-- 
    Document   : ajaxGetInfoSubConceptoIngreso
    Created on : Abr 25 , 2016, 4:50:46 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="gob.gbc.entidades.ModificacionIngreso"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.CapturaPresupuestoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaIngresoBean"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.PresupuestoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaPresupuestoBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    response.setCharacterEncoding("UTF-8");

    CapturaIngresoBean capturaIngresoBean = null;
    CapturaPresupuestoIngreso calendarizacionPlusMod = new CapturaPresupuestoIngreso();
    CapturaPresupuestoIngreso capturaPresupuestoIngreso = new CapturaPresupuestoIngreso();

    ArrayList<Caratula> arrCaratulas = null;

    Date date;
    DateFormat formatMonth = new SimpleDateFormat("MM");
    NumberFormat nFormat = NumberFormat.getInstance(Locale.US);

    String status = "A";
    String allDisabled = "";
    String ramoId = new String();
    String concepto = new String();
    String ramoDescr = new String();
    String ramoSession = new String();
    String strResultado = new String();
    String conceptoDescr = new String();
    String notVisibleCalSubConcepto = "";
    String tipoDependencia = new String();
    String selCaratulaDescr = new String();

    double ene = 0.0;
    double feb = 0.0;
    double mar = 0.0;
    double abr = 0.0;
    double may = 0.0;
    double jun = 0.0;
    double jul = 0.0;
    double ago = 0.0;
    double sep = 0.0;
    double oct = 0.0;
    double nov = 0.0;
    double dic = 0.0;
    double total = 0.0;

    long selCaratula = -1;

    int year = 0;
    int monthAct = 1;
    int subConcepto = -1;
    int meses[] = new int[3];

    boolean resultado = false;
    boolean validaTrimestre = false;
    boolean bFiltraEstatusAbiertas = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("yearPres") != null && request.getParameter("yearPres") != "") {
            year = Integer.parseInt(request.getParameter("yearPres"));
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = request.getParameter("ramo");
        }
        if (request.getParameter("ramoDescr") != null && !request.getParameter("ramoDescr").equals("")) {
            ramoDescr = request.getParameter("ramoDescr");
        }
        if (request.getParameter("concepto") != null && !request.getParameter("concepto").equals("")) {
            concepto = request.getParameter("concepto");
        }
        if (request.getParameter("conceptoDescr") != null && !request.getParameter("conceptoDescr").equals("")) {
            conceptoDescr = request.getParameter("conceptoDescr");
        }
        if (request.getParameter("subConcepto") != null && !request.getParameter("subConcepto").equals("")) {
            subConcepto = Integer.parseInt(String.valueOf(request.getParameter("subConcepto")));
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }

        capturaIngresoBean = new CapturaIngresoBean(tipoDependencia);
        capturaIngresoBean.setStrServer(((String) request.getHeader("Host")));
        capturaIngresoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaIngresoBean.resultSQLConecta(tipoDependencia);

        status = capturaIngresoBean.getStatusModificacionIngreso(selCaratula, year, ramoId, concepto);

        if (status.equals("C")) {
            allDisabled = "disabled";
        }

        calendarizacionPlusMod = capturaIngresoBean.getCalendarizacionIngresoPlusMod(year, ramoId, concepto, subConcepto, selCaratula);
        total = calendarizacionPlusMod.getEne()
                + calendarizacionPlusMod.getFeb()
                + calendarizacionPlusMod.getMar()
                + calendarizacionPlusMod.getAbr()
                + calendarizacionPlusMod.getMay()
                + calendarizacionPlusMod.getJun()
                + calendarizacionPlusMod.getJul()
                + calendarizacionPlusMod.getAgo()
                + calendarizacionPlusMod.getSep()
                + calendarizacionPlusMod.getOct()
                + calendarizacionPlusMod.getNov()
                + calendarizacionPlusMod.getDic();

        if (subConcepto == -1) {

            capturaPresupuestoIngreso = new CapturaPresupuestoIngreso();
            capturaPresupuestoIngreso.setYear(year);
            capturaPresupuestoIngreso.setRamo(ramoId);
            capturaPresupuestoIngreso.setIdCaratula(selCaratula);
            capturaPresupuestoIngreso.setConsec(0);
            capturaPresupuestoIngreso.setConsec(-1);
            capturaPresupuestoIngreso.setTipoMov("A");
            capturaPresupuestoIngreso.setDescr("");
            capturaPresupuestoIngreso.setEne(0);
            capturaPresupuestoIngreso.setEne(0);
            capturaPresupuestoIngreso.setFeb(0);
            capturaPresupuestoIngreso.setMar(0);
            capturaPresupuestoIngreso.setAbr(0);
            capturaPresupuestoIngreso.setMay(0);
            capturaPresupuestoIngreso.setJun(0);
            capturaPresupuestoIngreso.setJul(0);
            capturaPresupuestoIngreso.setAgo(0);
            capturaPresupuestoIngreso.setSep(0);
            capturaPresupuestoIngreso.setOct(0);
            capturaPresupuestoIngreso.setNov(0);
            capturaPresupuestoIngreso.setDic(0);

        } else {

            capturaPresupuestoIngreso = capturaIngresoBean.getCapturaPresupuestoIngresoById(year, ramoId, concepto, subConcepto, selCaratula);

            if (capturaPresupuestoIngreso.getConsec() == 0) {
                capturaPresupuestoIngreso = capturaIngresoBean.getCapturaPresupuestoIngresoById(year, ramoId, concepto, subConcepto, -2);
            }

            if (capturaPresupuestoIngreso.getConsec() == 0) {
                capturaPresupuestoIngreso = capturaIngresoBean.getPresupuestoIngresoById(year, ramoId, concepto, subConcepto);
                capturaPresupuestoIngreso.setEne(0);
                capturaPresupuestoIngreso.setFeb(0);
                capturaPresupuestoIngreso.setMar(0);
                capturaPresupuestoIngreso.setAbr(0);
                capturaPresupuestoIngreso.setMay(0);
                capturaPresupuestoIngreso.setJun(0);
                capturaPresupuestoIngreso.setJul(0);
                capturaPresupuestoIngreso.setAgo(0);
                capturaPresupuestoIngreso.setSep(0);
                capturaPresupuestoIngreso.setOct(0);
                capturaPresupuestoIngreso.setNov(0);
                capturaPresupuestoIngreso.setDic(0);
            }

        }

        strResultado += "<table>";

        strResultado += "<tr> ";
        strResultado += "   <td> TipoMov: </td> ";
        strResultado += "   <td>";

        if (subConcepto != -1 && total > 0) {

            strResultado += "       <select " + allDisabled + " id='selTipoMov' onChange='totalCapturaIngreso();' >";

            if (capturaPresupuestoIngreso.getTipoMov() == null) {
                capturaPresupuestoIngreso.setTipoMov("");
            }

            if (capturaPresupuestoIngreso.getTipoMov().equalsIgnoreCase("R")) {
                strResultado += "           <option value='R' selected >Reducci&oacute;n</option>";
                strResultado += "           <option value='A' >Ampliaci&oacute;n</option>";
            } else {
                if (capturaPresupuestoIngreso.getTipoMov().equalsIgnoreCase("A")) {
                    strResultado += "           <option value='R' >Reducci&oacute;n</option>";
                    strResultado += "           <option value='A' selected >Ampliaci&oacute;n</option>";
                } else {
                    strResultado += "           <option value='R' selected >Reducci&oacute;n</option>";
                    strResultado += "           <option value='A' >Ampliaci&oacute;n</option>";
                }
            }
        } else {

            strResultado += "       <select disabled id='selTipoMov' onChange='totalCapturaIngreso();' >";

            if (capturaPresupuestoIngreso.getTipoMov() == null) {
                capturaPresupuestoIngreso.setTipoMov("");
            }

            strResultado += "           <option value='R' >Reducci&oacute;n</option>";
            strResultado += "           <option value='A' selected >Ampliaci&oacute;n</option>";
        }
        strResultado += "       </select>";
        strResultado += "   </td> ";
        strResultado += "</tr>";
        strResultado += "<tr> <td> Ramo: </td> <td>&nbsp;&nbsp; " + ramoDescr + "</td> </tr>";
        strResultado += "<tr> <td> Concepto: </td> <td>&nbsp;&nbsp; " + conceptoDescr + "</td> </tr>";
        strResultado += "</table>";

        if (subConcepto != -1) {
            strResultado += "<div> Subconcepto: " + capturaPresupuestoIngreso.getConsec() + " </div> ";
            if (total > 0) {
                strResultado += "<div> Descripci&oacute;n: <textarea class='no-enter' disabled id='txtAreaPresupuestacion' maxlength='100'>" + capturaPresupuestoIngreso.getDescr() + "</textarea> </div> <br/>";
            } else {
                strResultado += "<div> Descripci&oacute;n: <textarea class='no-enter' id='txtAreaPresupuestacion' maxlength='100'>" + capturaPresupuestoIngreso.getDescr() + "</textarea> </div> <br/>";
            }
        } else {
            notVisibleCalSubConcepto = " display:none; ";
            strResultado += "<div> Descripci&oacute;n: <textarea class='no-enter' id='txtAreaPresupuestacion' maxlength='100'>" + "</textarea> </div> <br/>";
        }


        strResultado += "<center> <div id='divMese' style='" + notVisibleCalSubConcepto + "' >";
        strResultado += "<div> Ene <input disabled id='inEne' value='" + nFormat.format(calendarizacionPlusMod.getEne()) + "' maxlength='13' tabindex='1' /> </div>";
        strResultado += "<div> Feb <input disabled id='inFeb' value='" + nFormat.format(calendarizacionPlusMod.getFeb()) + "' maxlength='13' tabindex='2' /> </div>";
        strResultado += "<div> Mar <input disabled id='inMar' value='" + nFormat.format(calendarizacionPlusMod.getMar()) + "' maxlength='13' tabindex='3' /> </div>";
        strResultado += "<div> Abr <input disabled id='inAbr' value='" + nFormat.format(calendarizacionPlusMod.getAbr()) + "' maxlength='13' tabindex='4' /> </div>";
        strResultado += "<div> May <input disabled id='inMay' value='" + nFormat.format(calendarizacionPlusMod.getMay()) + "' maxlength='13' tabindex='5' /> </div>";
        strResultado += "<div> Jun <input disabled id='inJun' value='" + nFormat.format(calendarizacionPlusMod.getJun()) + "' maxlength='13' tabindex='6' /> </div>";
        strResultado += "<div> Jul <input disabled id='inJul' value='" + nFormat.format(calendarizacionPlusMod.getJul()) + "' maxlength='13' tabindex='7' /> </div>";
        strResultado += "<div> Ago <input disabled id='inAgo' value='" + nFormat.format(calendarizacionPlusMod.getAgo()) + "' maxlength='13' tabindex='8' /> </div>";
        strResultado += "<div> Sep <input disabled id='inSep' value='" + nFormat.format(calendarizacionPlusMod.getSep()) + "' maxlength='13' tabindex='9' /> </div>";
        strResultado += "<div> Oct <input disabled id='inOct' value='" + nFormat.format(calendarizacionPlusMod.getOct()) + "' maxlength='13' tabindex='10' /> </div>";
        strResultado += "<div> Nov <input disabled id='inNov' value='" + nFormat.format(calendarizacionPlusMod.getNov()) + "' maxlength='13' tabindex='11' /> </div>";
        strResultado += "<div> Dic <input disabled id='inDic' value='" + nFormat.format(calendarizacionPlusMod.getDic()) + "' maxlength='13' tabindex='12' /> </div>";
        strResultado += "</div> </center> <br/> <center>";

        strResultado += "<div style=' " + notVisibleCalSubConcepto + " '>";
        strResultado += "<div style=' display: block;text-align: right;margin-right: 15px;'> <b> TOTAL: </b> <input id='totalAnt' style='text-align: right;' type='text' disabled value='" + nFormat.format(total) + "' /> </div>";
        strResultado += " <input type='hidden' id='totalAnt' name='totalAnt' value='" + total + "'> ";
        strResultado += "</div>";

        date = capturaIngresoBean.getResultSQLgetServerDate();
        validaTrimestre = capturaIngresoBean.getResultSqlGetparametroTrimestre();
        monthAct = Integer.parseInt(formatMonth.format(date.getTime()));
        meses = capturaIngresoBean.getMesesTrimestreByMes(monthAct, validaTrimestre);

        validaTrimestre = false;
        
        if (!validaTrimestre) {
            if (meses.length > 0) {
                //monthAct = meses[0];
                monthAct = 0;
            } else {
                monthAct = 1;
            }
        }

        strResultado += "<center> <div id='divMese'>";
        strResultado += "<div> Ene <input " + Utileria.getDisabledMonth(1, monthAct, meses) + " " + allDisabled + " id='inpTxtEne' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getEne()) + "' maxlength='13' tabindex='1' onblur='agregarFormato(\"inpTxtEne\")' onkeyup='validaMascara(\"inpTxtEne\")' onChange='totalCapturaIngreso()'/>  </div>";
        strResultado += "<div> Feb <input " + Utileria.getDisabledMonth(2, monthAct, meses) + " " + allDisabled + " id='inpTxtFeb' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getFeb()) + "' maxlength='13' tabindex='2' onblur='agregarFormato(\"inpTxtFeb\")' onkeyup='validaMascara(\"inpTxtFeb\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Mar <input " + Utileria.getDisabledMonth(3, monthAct, meses) + " " + allDisabled + " id='inpTxtMar' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getMar()) + "' maxlength='13' tabindex='3' onblur='agregarFormato(\"inpTxtMar\")' onkeyup='validaMascara(\"inpTxtMar\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Abr <input " + Utileria.getDisabledMonth(4, monthAct, meses) + " " + allDisabled + " id='inpTxtAbr' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getAbr()) + "' maxlength='13' tabindex='4' onblur='agregarFormato(\"inpTxtAbr\")' onkeyup='validaMascara(\"inpTxtAbr\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> May <input " + Utileria.getDisabledMonth(5, monthAct, meses) + " " + allDisabled + " id='inpTxtMay' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getMay()) + "' maxlength='13' tabindex='5' onblur='agregarFormato(\"inpTxtMay\")' onkeyup='validaMascara(\"inpTxtMay\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Jun <input " + Utileria.getDisabledMonth(6, monthAct, meses) + " " + allDisabled + " id='inpTxtJun' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getJun()) + "' maxlength='13' tabindex='6' onblur='agregarFormato(\"inpTxtJun\")' onkeyup='validaMascara(\"inpTxtJun\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Jul <input " + Utileria.getDisabledMonth(7, monthAct, meses) + " " + allDisabled + " id='inpTxtJul' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getJul()) + "' maxlength='13' tabindex='7' onblur='agregarFormato(\"inpTxtJul\")' onkeyup='validaMascara(\"inpTxtJul\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Ago <input " + Utileria.getDisabledMonth(8, monthAct, meses) + " " + allDisabled + " id='inpTxtAgo' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getAgo()) + "' maxlength='13' tabindex='8' onblur='agregarFormato(\"inpTxtAgo\")' onkeyup='validaMascara(\"inpTxtAgo\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Sep <input " + Utileria.getDisabledMonth(9, monthAct, meses) + " " + allDisabled + " id='inpTxtSep' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getSep()) + "' maxlength='13' tabindex='9' onblur='agregarFormato(\"inpTxtSep\")' onkeyup='validaMascara(\"inpTxtSep\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Oct <input " + Utileria.getDisabledMonth(10, monthAct, meses) + " " + allDisabled + " id='inpTxtOct' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getOct()) + "' maxlength='13' tabindex='10' onblur='agregarFormato(\"inpTxtOct\")' onkeyup='validaMascara(\"inpTxtOct\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Nov <input " + Utileria.getDisabledMonth(11, monthAct, meses) + " " + allDisabled + " id='inpTxtNov' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getNov()) + "' maxlength='13' tabindex='11' onblur='agregarFormato(\"inpTxtNov\")' onkeyup='validaMascara(\"inpTxtNov\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "<div> Dic <input " + Utileria.getDisabledMonth(12, monthAct, meses) + " " + allDisabled + " id='inpTxtDic' class='capt-mes' value='" + nFormat.format(capturaPresupuestoIngreso.getDic()) + "' maxlength='13' tabindex='12' onblur='agregarFormato(\"inpTxtDic\")' onkeyup='validaMascara(\"inpTxtDic\")' onChange='totalCapturaIngreso()'/> </div>";
        strResultado += "</div> </center> <br/> <center>";

        total = 0;
        total = capturaPresupuestoIngreso.getEne()
                + capturaPresupuestoIngreso.getFeb()
                + capturaPresupuestoIngreso.getMar()
                + capturaPresupuestoIngreso.getAbr()
                + capturaPresupuestoIngreso.getMay()
                + capturaPresupuestoIngreso.getJun()
                + capturaPresupuestoIngreso.getJul()
                + capturaPresupuestoIngreso.getAgo()
                + capturaPresupuestoIngreso.getSep()
                + capturaPresupuestoIngreso.getOct()
                + capturaPresupuestoIngreso.getNov()
                + capturaPresupuestoIngreso.getDic();


        if (capturaPresupuestoIngreso.getTipoMov().equalsIgnoreCase("R")) {
            total = total * -1;
        } else {
            if (!capturaPresupuestoIngreso.getTipoMov().equalsIgnoreCase("A")) {
                total = total * -1;
            }
        }

        if (total == -0) {
            total = 0;
        }

        strResultado += "<div style=' display: block;text-align: right;margin-right: 15px;'> <b> TOTAL: </b> <input id='totalIngreso' style='text-align: right;' type='text' disabled value='" + nFormat.format(total) + "' /> </div>";

        if (status.equals("A")) {
            if (subConcepto != -1) {
                strResultado += " <input type='button' value='Aceptar' onclick='modificarSubConceptoCapturaIngreso(" + capturaPresupuestoIngreso.getConsec() + "); getSubconceptosCapturaPresupuestoIngreso();  '/>";
            } else {
                strResultado += " <input type='button' value='Aceptar' onclick='nuevoSubConceptoCapturaIngreso(); getSubconceptosCapturaPresupuestoIngreso();'/>";
            }
        }

        strResultado += " <input type='button' value='Cancelar' onclick='cerrarPresupuesto()'/> </div> </center>";
        strResultado += " <input type='hidden' id='totalNvo' name='totalNvo' value='" + total + "'> ";
        strResultado += " </center>";

        strResultado += "<script type='text/javascript'>";
        //strResultado += "function() {";
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

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (capturaIngresoBean != null) {
            capturaIngresoBean.resultSQLDesconecta();
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