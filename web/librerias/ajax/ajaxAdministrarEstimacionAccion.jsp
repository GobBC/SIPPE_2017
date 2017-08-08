<%-- 
    Document   : ajaxAdministrarEstimacionMeta
    Created on : Apr 15, 2015, 8:38:15 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.util.Bitacora"%>
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
    List<Estimacion> estMetaList = new ArrayList<Estimacion>();
    AccionBean accionBean = null;
    String strResultado = new String();
    String cadValores = new String();
    String arrValores[];
    String tipoC = new String();
    String ramoDescr = new String();
    String metaDescr = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String obraAccion = new String();
    String etiquetaObraAccion = new String();
    String disabled = new String();
    Double valor = 0.0;
    boolean cierreAccion = false;
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    boolean resultadoAct = false;
    int year = 0;
    String ramoId = new String();
    String tipoDependencia = new String();
    int metaId = 0;
    int accionId = 0;
    int optEst = 0;
    int contFallas = 0;
    int programaId = 0;
    int proyectoId = 0;
    int cont = 1;
    int consulta = 0;
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null
                && !session.getAttribute("year").equals("")) {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("programaId") != null
                && !request.getParameter("programaId").equals("")) {
            programaId = Integer.parseInt(request.getParameter("programaId"));
        }
        if (request.getParameter("proyectoId") != null
                && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt(request.getParameter("proyectoId"));
        }
        if (request.getParameter("consulta") != null
                && !request.getParameter("consulta").equals("")) {
            consulta = Integer.parseInt(request.getParameter("consulta"));
        }
        if (request.getParameter("valores") != null
                && !request.getParameter("valores").equals("")) {
            cadValores = request.getParameter("valores");
        }
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("accionId") != null
                && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accionId"));
        }
        if (request.getParameter("optEst") != null
                && !request.getParameter("optEst").equals("")) {
            optEst = Integer.parseInt(request.getParameter("optEst"));
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
        obraAccion = obraAccion.replace("<div> </div>","");
        if (!obraAccion.equals("")) {
            etiquetaObraAccion = "disabled";
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        cierreAccion = accionBean.isAccionCerrado(ramoId, year);
        if(consulta > 0){
            disabled = "disabled";
        }
        if (!cierreAccion) {
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
            if (!obraAccion.equals("")) {
                strResultado += "   <tr> ";
                strResultado += "       <td> Obra </td>";
                strResultado += "       <td>" + obraAccion + "</td> ";
                strResultado += "   </tr>";
            }
            strResultado += "</table>";
            if (optEst != 1) {
                strResultado += "<center>";
                strResultado += "   <label>CALENDARIZACI&Oacute;N DE META</label>";
                strResultado += "</center>";
                estMetaList = accionBean.getEstimacionByMeta(year, ramoId, metaId);
                if (estMetaList.size() > 0) {
                    strResultado += "<div class='calenVistaC'> ";
                    for (Estimacion estimacion : estMetaList) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                        cont++;
                    }
                }
                strResultado += "</div>";
                estimacionList = accionBean.getEstimacionByAccion(year, ramoId, metaId, accionId);
                tipoC = accionBean.getTipoCalculo(year, ramoId, metaId, accionId);
                if (estimacionList.size() > 0) {
                    strResultado += "<br/> <br/>";
                    strResultado += "<center>";
                    strResultado += "   <label>CALENDARIZACI&Oacute;N DE ACCI&Oacute;N POR MES </label>";
                    strResultado += "</center>";
                    strResultado += "<div>";
                    /*
                     strResultado += "<table id='tblEstimacion'> <thead> <tr> <th> Periodo </th> <th> Valor </th> </tr> </thead>";
                     strResultado += "   <tbody>";*/
                    strResultado += "<div class='calenVistaC'>";
                    for (int it = 0; it < 12; it++) {
                        //strResultado += "<tr>";
                        strResultado += "<div>";
                        if (estimacionList.get(it) != null) {
                            if (estimacionList.get(it).getPeriodo() != 0) {
                                //strResultado += "<td>" + estimacionList.get(it).getPeriodo() + "</td>";
                                strResultado += Utileria.getStringMes(estimacionList.get(it).getPeriodo());
                            } else {
                                //strResultado += "<td>" + (it + 1) + "</td>";
                                strResultado += Utileria.getStringMes((it + 1));
                            }
                            if (estimacionList.get(it).getValor() != 0) {
                                //strResultado += "<td> <input class='estimacion' type='text' value='" + estimacionList.get(it).getValor() + "' onChange='validarFlotante(this.value,\""+tipoC+"\")'/></td> </tr>";
                                strResultado += "<input class='estimacion' id='estimacion" + it + "' onkeyup='validaMascara(\"estimacion" + it + "\")' type='text' value='" + numberF.format(estimacionList.get(it).getValor()) + "'  maxlength='14' onBlur='agregarFormato(\"estimacion" + it + "\");validarFlotanteMetaAccion(this.value,\"" + tipoC + "\")' "+ etiquetaObraAccion +" "+disabled+" />";
                            } else {
                                //strResultado += "<td> <input class='estimacion' type='text' value='' onBlur='validarFlotante(this.value,\""+tipoC+"\")'/> </td> </tr>";
                                strResultado += "<input class='estimacion' id='estimacion" + it + "' onkeyup='validaMascara(\"estimacion" + it + "\")' type='text' value='0' onBlur='agregarFormato(\"estimacion" + it + "\");validarFlotanteMetaAccion(this.value,\"" + tipoC + "\")'  maxlength'14' "+ etiquetaObraAccion +" "+disabled+" />";
                            }
                        } else {
                            //strResultado += "<td>" + (it + 1) + "</td>";
                            //strResultado += "<td> <input type='text' value='' onChange='validarFlotante(this.value,\""+tipoC+"\")'> </tr>";
                            strResultado += " " + (it + 1);
                            strResultado += "<input type='text' value='0' id='estimacion" + it + "' onkeyup='validaMascara(\"estimacion" + it + "\")' onBlur='agregarFormato(\"estimacion" + it + "\");validarFlotanteMetaAccion(this.value,\"" + tipoC + "\")'  maxlength='14' "+ etiquetaObraAccion +" "+disabled+" >";
                        }
                        strResultado += "</div>";
                    }
                    strResultado += "</div>";
                    strResultado += "<br/>";
                    /*strResultado += "   </tbody>";
                     strResultado += "</table>";
                     strResultado += "</div> </br>";*/
                    strResultado += "   <center><label> Cantidad </label> &nbsp;&nbsp;";
                    strResultado += "<br/>";
                    strResultado += "   <input type='text' id='inTxtTotalEst' value='" + numberF.format(accionBean.getValorCalculado(estimacionList, tipoC)) + "' "
                            + "disabled style='width: 250px;text-align: right;'/> </center>";
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
                    //strResultado += "$('.estimacion').mask('00,000,000,000,000.00', {reverse: true});";
                    //strResultado += "$('.estimacion').maskMoney({showSymbol:false,decimal:'.',precision:2});";
                    strResultado += "</script>";
                } else {
                    contFallas = 0;
                    contFallas = accionBean.insertAccionEstimacion(year, ramoId, metaId, accionId);
                    if (contFallas == 0) {
                        strResultado += "<center>";
                        strResultado += "   <label>PROGRAMACI&Oacute;N POR MES</label>";
                        strResultado += "</center>";
                        strResultado += "<div>";
                        strResultado += "<div class='calenVistaC'>";
                        //strResultado += "<table id='tblEstimacion'> <thead> <tr> <th> Periodo </th> <th> Valor </th> </tr> </thead>";
                        //strResultado += "   <tbody>";
                        for (int it = 0; it < 12; it++) {
                            strResultado += "<div> " + Utileria.getStringMes((it + 1));
                            strResultado += "<input class='estimacion' id='estimacion" + it + "' type='text' value='' onkeyup='validaMascara(\"estimacion" + it + "\")' onBlur='agregarFormato(\"estimacion" + it + "\");validarFlotanteMetaAccion(this.value,\"" + tipoC + "\")'  maxlength='14' "+disabled+" />";
                            strResultado += "</div>";
                            //strResultado += "<tr>";        
                            //strResultado += "<td>" + (it + 1) + "</td>";
                            //strResultado += "<td> <input class='estimacion' type='text' value='' onChange='validarFlotante(this.value,\""+tipoC+"\")'> </tr>";
                        }
                        strResultado += "</div>";
                        //strResultado += "   </tbody>";
                        //strResultado += "</table>";
                        strResultado += "</div> </br>";
                        strResultado += "   <center><label> Cantidad </label> &nbsp;&nbsp;";
                        strResultado += "   <input type='text' id='inTxtTotalEst' value='" + accionBean.getValorCalculado(estimacionList, tipoC) + "' "
                                + "disabled='true' style='width: 250px;text-align: right;' maxlength='14'/> </center>";
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
                        //strResultado += "$('.estimacion').mask('00,000,000,000,000.00', {reverse: true});";
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
                    resultadoAct = accionBean.updateAccionEstimacion(year, ramoId, metaId, accionId, (it + 1), valor);
                    if (!resultadoAct) {
                        contFallas++;
                    }
                }
                if (contFallas > 0) {
                    strResultado = "malo La información no se actualizó correctamente";
                    accionBean.transaccionRollback();
                } else {
                    strResultado = "bien La información se actualizó correctamente";
                    accionBean.transaccionCommit(); //revisado
                }
            }
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
