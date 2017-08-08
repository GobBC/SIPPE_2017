<%-- 
    Document   : ajaxActualizaMovimientos
    Created on : Dec 10, 2015, 9:03:06 AM
    Autdor     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<AmpliacionReduccionAccionReq> ampReduccionAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
    MovimientosAmpliacionReduccion movimiento = new MovimientosAmpliacionReduccion();
    AmpliacionReduccionBean ampRedBean = null;
    
    String strResult = new String();
    String estatus = new String();
    String tipoDependencia = new String();
    
    NumberFormat  dFormat = NumberFormat.getInstance(Locale.US);
    BigDecimal totalAmp = new BigDecimal(0);
    
    boolean isParaestatal = false;
    boolean autorizar = false;
    try{
        if (session.getAttribute("ampliacionReduccion") != null) {
            movimiento = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus =  request.getParameter("estatus");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (Utilerias.existeParametro("autorizar", request)) {
            autorizar = Boolean.parseBoolean(request.getParameter("autorizar"));
        }
        
        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer((request.getHeader("Host")));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        ampRedBean.resultSQLConecta(tipoDependencia);
        
        
        ampReduccionAccionReqList = movimiento.getAmpReducAccionReqList();
        
        isParaestatal = ampRedBean.getResultSQLisParaestatal();
        
        for (AmpliacionReduccionAccionReq ampRedRequer : ampReduccionAccionReqList) {
            strResult += "<tr>";
            strResult += "<td></td>";
            strResult += "<td>" + ampRedRequer.getRamo() + "</td>";
            strResult += "<td>" + ampRedRequer.getPrograma() + "</td>";
            strResult += "<td>" + ampRedRequer.getTipoProy() + "-" + ampRedRequer.getProy() + "</td>";
            strResult += "<td>" + ampRedRequer.getMeta() + "</td>";
            strResult += "<td>" + ampRedRequer.getAccion() + "</td>";
            strResult += "<td>" + ampRedRequer.getDepto() + "</td>";
            strResult += "<td>" + ampRedRequer.getPartida() + "</td>";
            strResult += "<td>" + ampRedRequer.getFuente() + "."
                    + ampRedRequer.getFondo() + "."
                    + ampRedRequer.getRecurso() + "</td>";
            strResult += "<td>" + ampRedRequer.getRelLaboral() + "</td>";
            strResult += "<td>" + dFormat.format(ampRedRequer.getQuincePor()) + "</td>";
            strResult += "<td>" + dFormat.format(ampRedRequer.getAcumulado()) + "</td>";
            strResult += "<td>" + dFormat.format(ampRedRequer.getDisponible()) + "</td>";
            strResult += "<td>" + dFormat.format(ampRedRequer.getDisponibleAnual()) + "</td>";
            strResult += "<td>" + dFormat.format(ampRedRequer.getImporte()) + "</td>";
            if ((autorizar && estatus.equals("T")) && !isParaestatal && (ampRedRequer.getTipoMovAmpRed().equals("A") || ampRedRequer.getTipoMovAmpRed().equals("C"))) {
                if (ampRedRequer.getIsIngresoPropio().equals("S")) {
                    out.print("<td><input type='checkbox' id='ingPropio' name='ingPropio'  " + "onchange='changeIngresoPropio(\"" + ampRedRequer.getConsecutivo() + "\")' checked ></td>");
                } else {
                    out.print("<td><input type='checkbox' id='ingPropio' name='ingPropio'  " + "onchange='changeIngresoPropio(\"" + ampRedRequer.getConsecutivo() + "\")' ></td>");
                }
            }
            if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {
                if(ampRedRequer.getTipoMovAmpRed().equals("R")){
                   strResult +="<td> <a onClick='mostrarProgramacion('"+ampRedRequer.getRamo()+"',"+ampRedRequer.getMeta()+","+ampRedRequer.getAccion()+")'><span class='fa fa-eye'></span></a>"
                           + " <input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoEdicionAccionRequerimientoAmpReq(\"" + ampRedRequer.getIdentidicador() + "\",\"" + estatus + "\",\""
                            + ampRedRequer.getRamo() + "\",\"" + ampRedRequer.getPrograma() + "\",\"" + ampRedRequer.getMeta() + "\","
                            + "\"" + ampRedRequer.getAccion() + "\",\"" + ampRedRequer.getConsecutivo() + "\",\"R\")' />";
                }else{
                    strResult +="<td><a onClick='mostrarProgramacion(\""+ampRedRequer.getRamo()+"\","+ampRedRequer.getMeta()+","+ampRedRequer.getAccion()+")'><span class='fa fa-calendar botones-img'></span></a>"
                            + "<input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoEdicionAccionRequerimientoAmpReq(\"" + ampRedRequer.getIdentidicador() + "\",\"" + estatus + "\",\""
                            + ampRedRequer.getRamo() + "\",\"" + ampRedRequer.getPrograma() + "\",\"" + ampRedRequer.getMeta() + "\","
                            + "\"" + ampRedRequer.getAccion() + "\",\"" + ampRedRequer.getConsecutivo() + "\",\"A\")' />";
                }
                strResult += "<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarAmpliacionReduccion(\"" + ampRedRequer.getIdentidicador() + "\",\"3\");' /></td>";
            } else {
                strResult += "<td><input type='button' class='btnbootstrap-drop btn-ver' "
                        + "onclick='getInfoEdicionAccionRequerimientoAmpReq(\"" + ampRedRequer.getMovOficioAccionReq() + "\",\"" + estatus + "\",\""
                        + ampRedRequer.getRamo() + "\",\"" + ampRedRequer.getPrograma() + "\",\"" + ampRedRequer.getMeta() + "\","
                        + "\"" + ampRedRequer.getAccion() + "\",\"" + ampRedRequer.getConsecutivo()+ "\",\"A\")' />";
            }
            strResult += "</tr>";
            totalAmp = totalAmp.add(new BigDecimal(ampRedRequer.getImporte()));
        }
        strResult += "<tr><td></td> "
                      +  "<td></td>"
                      +  "<td></td>"
                     +   "<td></td>"
                     +   "<td></td>"
                     +   "<td></td>"
                     +   "<td></td>"
                     +   "<td></td>"
                     +   "<td></td>"
                     +   "<td></td>"
                      +  "<td></td>"
                      +  "<td></td>"
                     +   "<td></td>"
                     +   "<td>Total:</td> "
                     +   "<td>"+dFormat.format(totalAmp.setScale(2, RoundingMode.HALF_UP))+"</td></tr>";
    }catch (Exception ex) {
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
