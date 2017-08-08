<%-- 
    Document   : ajaxGetInfoPresupuestoIngreso
    Created on : Jul 7, 2015, 3:16:03 PM
    Author     : ugarcia
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.CapturaPresupuestoBean"%>
<%@page import="gob.gbc.entidades.PresupuestoIngreso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    PresupuestoIngreso presupuestoIngreso = new PresupuestoIngreso();
    CapturaPresupuestoBean capturaPresBean = null;
    int year = 0;
    int opcion = 0;
    int pptoId = 0;
    double total = 0.0;
    NumberFormat nFormat = NumberFormat.getInstance(Locale.US);
    String strResultado = new String();
    String ramoId = new String();
    String concepto = new String();
    String ramoDescr = new String();
    String conceptoDescr = new String();
    String tipoDependencia = new String();
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String)session.getAttribute("year"));
        }
        if (request.getParameter("concepto") != null && !request.getParameter("concepto").equals("")) {
            concepto = request.getParameter("concepto");
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = request.getParameter("ramo");
        }
        if (request.getParameter("ramoDescr") != null && !request.getParameter("ramoDescr").equals("")) {
            ramoDescr = request.getParameter("ramoDescr");
        }
        if (request.getParameter("conceptoDescr") != null && !request.getParameter("conceptoDescr").equals("")) {
            conceptoDescr = request.getParameter("conceptoDescr");
        }
        if (request.getParameter("opcion") != null && !request.getParameter("opcion").equals("")) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (request.getParameter("pptoId") != null && !request.getParameter("pptoId").equals("")) {
            pptoId = Integer.parseInt(request.getParameter("pptoId"));
        }
        capturaPresBean = new CapturaPresupuestoBean(tipoDependencia);
        capturaPresBean.setStrServer(((String) request.getHeader("Host")));
        capturaPresBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaPresBean.resultSQLConecta(tipoDependencia);
        if (opcion == 1) {
            presupuestoIngreso = new PresupuestoIngreso();
            presupuestoIngreso.setPresupuestoIngreso("");
        } else if (opcion == 2) {
            presupuestoIngreso = capturaPresBean.getPresupuestoIngresoById(year, ramoId, concepto, pptoId);
        }
        strResultado += "<table>";
        strResultado += "<tr> <td> Ramo: </td> <td>&nbsp;&nbsp; "+ ramoDescr +"</td> </tr>";        
        strResultado += "<tr> <td> Concepto: </td> <td>&nbsp;&nbsp; "+ conceptoDescr +"</td> </tr>";
        strResultado += "</table>";
        if(opcion == 2){
            strResultado += "<div> <label> Subconcepto: </label> "+presupuestoIngreso.getPresupuestoIngresoId()+" </div> ";
        }
        strResultado += "<div> Descripci&oacute;n <textarea class='no-enter' id='txtAreaPresupuestacion' maxlength='100'>" + presupuestoIngreso.getPresupuestoIngreso() + "</textarea> </div> <br/>";
        strResultado += "<center> <div id='divMese'>";        
        strResultado += "<div> Ene <input id='inpTxtEne' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getEne()) + "' maxlength='13' tabindex='1' onblur='agregarFormato(\"inpTxtEne\")' onkeyup='validaMascara(\"inpTxtEne\")' onChange='totalPresupuestoIngreso()'/>  </div>";
        strResultado += "<div> Feb <input id='inpTxtFeb' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getFeb()) + "' maxlength='13' tabindex='2' onblur='agregarFormato(\"inpTxtFeb\")' onkeyup='validaMascara(\"inpTxtFeb\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Mar <input id='inpTxtMar' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getMar()) + "' maxlength='13' tabindex='3' onblur='agregarFormato(\"inpTxtMar\")' onkeyup='validaMascara(\"inpTxtMar\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Abr <input id='inpTxtAbr' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getAbr())+ "' maxlength='13' tabindex='4' onblur='agregarFormato(\"inpTxtAbr\")' onkeyup='validaMascara(\"inpTxtAbr\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> May <input id='inpTxtMay' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getMay())+ "' maxlength='13' tabindex='5' onblur='agregarFormato(\"inpTxtMay\")' onkeyup='validaMascara(\"inpTxtMay\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Jun <input id='inpTxtJun' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getJun()) + "' maxlength='13' tabindex='6' onblur='agregarFormato(\"inpTxtJun\")' onkeyup='validaMascara(\"inpTxtJun\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Jul <input id='inpTxtJul' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getJul()) + "' maxlength='13' tabindex='7' onblur='agregarFormato(\"inpTxtJul\")' onkeyup='validaMascara(\"inpTxtJul\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Ago <input id='inpTxtAgo' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getAgo()) + "' maxlength='13' tabindex='8' onblur='agregarFormato(\"inpTxtAgo\")' onkeyup='validaMascara(\"inpTxtAgo\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Sep <input id='inpTxtSep' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getSep()) + "' maxlength='13' tabindex='9' onblur='agregarFormato(\"inpTxtSep\")' onkeyup='validaMascara(\"inpTxtSep\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Oct <input id='inpTxtOct' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getOct()) + "' maxlength='13' tabindex='10' onblur='agregarFormato(\"inpTxtOct\")' onkeyup='validaMascara(\"inpTxtOct\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Nov <input id='inpTxtNov' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getNov()) + "' maxlength='13' tabindex='11' onblur='agregarFormato(\"inpTxtNov\")' onkeyup='validaMascara(\"inpTxtNov\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "<div> Dic <input id='inpTxtDic' class='capt-mes' value='" + nFormat.format(presupuestoIngreso.getDic()) + "' maxlength='13' tabindex='12' onblur='agregarFormato(\"inpTxtDic\")' onkeyup='validaMascara(\"inpTxtDic\")' onChange='totalPresupuestoIngreso()'/> </div>";
        strResultado += "</div> </center> <br/> <center>";
        total = presupuestoIngreso.getEne() + presupuestoIngreso.getFeb()+ presupuestoIngreso.getMar()+ presupuestoIngreso.getAbr()+ presupuestoIngreso.getMay()+ presupuestoIngreso.getJun()+ presupuestoIngreso.getJul()+ presupuestoIngreso.getAgo()+
                presupuestoIngreso.getSep()+ presupuestoIngreso.getOct()+presupuestoIngreso.getNov()+presupuestoIngreso.getDic();
        strResultado += "<div style=' display: block;text-align: right;margin-right: 15px;'> <b> TOTAL: </b> <input id='totalIngreso' style='text-align: right;' type='text' disabled value='"+ nFormat.format(total) +"' /> </div>";
        if (opcion == 2) {
            strResultado += " <input type='button' value='Aceptar' onclick='editarPresupuesto(" + presupuestoIngreso.getPresupuestoIngresoId() + ")'/>";
        } else {
            strResultado += " <input type='button' value='Aceptar' onclick='nuevoPresupuesto()'/>";
        }
        strResultado += " <input type='button' value='Cancelar' onclick='cerrarPresupuesto()'/> </div> </center>";
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
        if (capturaPresBean != null) {
            capturaPresBean.resultSQLDesconecta();
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