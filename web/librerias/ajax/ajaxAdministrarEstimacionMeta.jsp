<%-- 
    Document   : ajaxAdministrarEstimacionMeta
    Created on : Apr 15, 2015, 8:38:15 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    Evaluacion evaluacion = new Evaluacion();
    MetaBean metaBean = null;
    String strResultado = new String();
    String cadValores = new String();
    String arrValores[];
    String tipoC = new String();
    String tipoDependencia = new String();
    String ramoId = new String();
    String programaId = new String();
    //String tipoMeta = new String();
    //String tipoProyecto = new String();
    //String disabled = new String();
    Double valor = 0.0;
    boolean resultadoAct = false;
    int year = 0;
    int metaId = 0;
    int optEst = 0;
    int contFallas = 0;
    int proyectoId = 0;
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("programaId") != null
                && !request.getParameter("programaId").equals("")) {
            programaId = request.getParameter("programaId");
        }
        if (request.getParameter("proyectoId") != null
                && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt(request.getParameter("proyectoId"));
        }
        /*if (request.getParameter("tipoProy") != null
                && !request.getParameter("tipoProy").equals("")) {
            tipoProyecto = request.getParameter("tipoProy");
        }*/
        if (request.getParameter("valores") != null
                && !request.getParameter("valores").equals("")) {
            cadValores = request.getParameter("valores");
        }
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("optEst") != null
                && !request.getParameter("optEst").equals("")) {
            optEst = Integer.parseInt(request.getParameter("optEst"));
        }
        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
        /*tipoMeta = metaBean.getObraMeta(year, ramoId, metaId);
        if(tipoMeta.equals("I")){
            disabled = "disabled";
        }*/
        if (optEst != 1) {
            estimacionList = metaBean.getEstimacionByMeta(year, ramoId, metaId);
            evaluacion = metaBean.getEvaluacionMeta(year);
            tipoC = metaBean.getTipoCalculo(year, ramoId, programaId, proyectoId, metaId);
            if (estimacionList.size() > 0) {
                strResultado += "<center>";
                strResultado += "   <label>CALENDARIZACI&Oacute;N " + evaluacion.getTipoEvaluacion() + "</label>";
                strResultado += "</center>";
                strResultado += "<div class='calenVistaC'>";
                strResultado += "<table id='tblEstimacion'> <thead> <tr> <th> Periodo </th> <th> Valor </th> </tr> </thead>";
                strResultado += "   <tbody>";
                for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                    strResultado += "<tr>";
                    if (estimacionList.get(it) != null) {
                        if (estimacionList.get(it).getPeriodo() != 0) {
                            strResultado += "<td>" + Utileria.getStringMes(estimacionList.get(it).getPeriodo()) + "</td>";
                        } else {
                            strResultado += "<td>" + Utileria.getStringMes((it + 1)) + "</td>";
                        }
                        if (estimacionList.get(it).getValor() != 0) {
                            strResultado += "<td> <input class='estimacion' id='estimacion"+it+"' type='text' value='" + numberF.format(estimacionList.get(it).getValor()) + "' maxlength='14' onBlur='agregarFormato(\"estimacion" + it+"\");validarFlotanteMetaAccion(this.value,\"" + tipoC + "\")'  onkeyup='validaMascara(\"estimacion" + it +"\")' /></td> </tr>";
                           // strResultado += "<td> <input class='estimacion' id='estimacion"+it+"' type='text' value='" + numberF.format(estimacionList.get(it).getValor()) + "' maxlength='14' onBlur='validarFlotante(this.value,\"" + tipoC + "\")' onfocus='selectEnteros(\"estimacion"+it+"\")' onkeyPress='pressPoint(event,\"estimacion"+it+"\")' /></td> </tr>";
                        } else {
                            strResultado += "<td> <input class='estimacion' id='estimacion"+it+"' type='text' value='0' maxlength='14' onBlur='agregarFormato(\"estimacion" + it+"\");validarFlotanteMetaAccion(this.value,\"" + tipoC + "\")' onkeyup='validaMascara(\"estimacion" + it +"\")' /></td> </tr>";
                            //strResultado += "<td> <input class='estimacion' id='estimacion"+it+"' type='text' value='' onBlur='validarFlotante(this.value,\"" + tipoC + "\")' onfocus='selectEnteros(\"estimacion"+it+"\")' maxlength='14' onkeyPress='pressPoint(event,\"estimacion"+it+"\")'/> </td> </tr>";
                        }
                    } else {
                        strResultado += "<td>" + Utileria.getStringMes((it + 1)) + "</td>";
                        strResultado += "<td> <input type= 'text' id='estimacion"+it+"' value='0' maxlength='14' onBlur='agregarFormato(\"estimacion" + it+"\");validarFlotanteMetaAccion(this.value,\"" + tipoC + "\")' onkeyup='validaMascara(\"estimacion" + it +"\")' /></td> </tr>";
                        //strResultado += "<td> <input  type='text' id='estimacion"+it+"' value='' onBlur='validarFlotante(this.value,\"" + tipoC + "\")' onfocus='selectEnteros(\"estimacion"+it+"\")' maxlength='14' > </tr>";
                    }
                }
                strResultado += "   </tbody>";
                strResultado += "</table>";
                strResultado += "</div> </br>";
                strResultado += "   <center><label> Cantidad </label> &nbsp;&nbsp;";
                strResultado += "   <input type='text' id='inTxtTotalEst' value='" + numberF.format(metaBean.getValorCalculado(estimacionList, tipoC)) + "' "
                        + "disabled style='width: 100px;text-align: right;'/> </center>";
                strResultado += "<script type='text/javascript'>";
                strResultado += "              $('.estimacion').keydown(function (e) {";
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
                /*strResultado += "$('.estimacion').mask('00,000,000,000.00', {reverse: true});";*/
                //strResultado += "$('.estimacion').maskMoney({showSymbol:false,decimal:'.',precision:2});";
                strResultado += "</script>";
            } else {
                contFallas = 0;
                contFallas = metaBean.insertEstimacionMeta(year, ramoId, metaId, evaluacion.getNumMeses());
                if (contFallas == 0) {
                    metaBean.transaccionCommit();
                    strResultado += "<center>";
                    strResultado += "   <label>PROGRAMACI&Oacute;N " + evaluacion.getTipoEvaluacion() + "</label>";
                    strResultado += "</center>";
                    strResultado += "<div class='calenVistaC'>";
                    strResultado += "<table id='tblEstimacion'> <thead> <tr> <th> Periodo </th> <th> Valor </th> </tr> </thead>";
                    strResultado += "   <tbody>";
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        strResultado += "<tr>";
                        strResultado += "<td>" + Utileria.getStringMes((it + 1)) + "</td>";
                        strResultado += "<td> <input class='estimacion' id='estimacion"+it+"' type='text' value='' onBlur='agregarFormato(\"estimacion" + it+"\");validarFlotanteMetaAccion(this.value,\"" + tipoC + "\")' onkeyup='validaMascara(\"estimacion" + it +"\")' maxlength='14'> </tr>";
                        //strResultado += "<td> <input class='estimacion' id='estimacion"+it+"' type='text' value='' onBlur='validarFlotante(this.value,\"" + tipoC + "\")' onfocus='selectEnteros(\"estimacion"+it+"\")' onkeyPress='pressPoint(event,\"estimacion"+it+"\")' maxlength='14'> </tr>";
                    }
                    strResultado += "   </tbody>";
                    strResultado += "</table>";
                    strResultado += "</div> </br>";
                    strResultado += "   <center><label> Cantidad </label> &nbsp;&nbsp;";
                    strResultado += "   <input type='text' id='inTxtTotalEst' value='" + metaBean.getValorCalculado(estimacionList, tipoC) + "' maxlength='14' onfocus='selectEnteros(\"inpTxtEne\")' "
                            + "disabled style='width: 100px;text-align: right;' /> </center>";
                    strResultado += "<script type='text/javascript'>";
                    strResultado += "              $('.estimacion').keydown(function (e) {";
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
                    /*strResultado += "$('.estimacion').mask('00,000,000,000.00', {reverse: true});";*/
                    //strResultado += "$('.estimacion').maskMoney({showSymbol:false,decimal:'.',precision:2});";
                    strResultado += "</script>";
                }
            }
        } else {
            arrValores = new String[cadValores.split(",").length];
            arrValores = cadValores.split(",");
            for (int it = 0; it < arrValores.length; it++) {
                if (!arrValores[it].trim().equals("")) {
                    valor = Double.parseDouble(arrValores[it].trim());
                } else {
                    valor = 0.0;
                }
                resultadoAct = metaBean.updateEstimacionMeta(year, ramoId, metaId, it + 1, valor);
                if (!resultadoAct) {
                    contFallas++;
                }
            }
            
            if (contFallas > 0) {
                strResultado = "malo";
                metaBean.transaccionRollback();
            } else {
                strResultado = "bien";
                metaBean.transaccionCommit(); //revisado
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
            metaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
