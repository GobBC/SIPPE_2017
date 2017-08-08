<%-- 
    Document   : ajaxGetPresupuestoIngreso
    Created on : Jul 7, 2015, 4:50:46 PM
    Author     : ugarcia
--%>

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
    CapturaPresupuestoBean capturaPresBean = null;
    List<PresupuestoIngreso> presupuestoList = new ArrayList<PresupuestoIngreso>();
    int year = 0;
    int opcion = 0;
    int countRow = 0;
    int pptoId = 0;
    boolean resultado = false;
    double total = 0.0;
    NumberFormat nformat = NumberFormat.getInstance(Locale.US);
    String tipoDependencia = new String();
    String ramoId = new String();
    String concepto = new String();
    String strResultado = new String();
    String descr = new String();
    String rol = new String();
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
    boolean cierre;

    try {
        
        if ((String) session.getAttribute("strRol") != null) {
            rol = (String) session.getAttribute("strRol");
        }
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
        if (request.getParameter("descr") != null && !request.getParameter("descr").equals("")) {
            descr = request.getParameter("descr");
        }
        if (request.getParameter("opcion") != null && !request.getParameter("opcion").equals("")) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (request.getParameter("ene") != null && !request.getParameter("ene").equals("")) {
            ene = Double.parseDouble(request.getParameter("ene"));
        }
        if (request.getParameter("feb") != null && !request.getParameter("feb").equals("")) {
            feb = Double.parseDouble(request.getParameter("feb"));
        }
        if (request.getParameter("mar") != null && !request.getParameter("mar").equals("")) {
            mar = Double.parseDouble(request.getParameter("mar"));
        }
        if (request.getParameter("abr") != null && !request.getParameter("abr").equals("")) {
            abr = Double.parseDouble(request.getParameter("abr"));
        }
        if (request.getParameter("may") != null && !request.getParameter("may").equals("")) {
            may = Double.parseDouble(request.getParameter("may"));
        }
        if (request.getParameter("jun") != null && !request.getParameter("jun").equals("")) {
            jun = Double.parseDouble(request.getParameter("jun"));
        }
        if (request.getParameter("jul") != null && !request.getParameter("jul").equals("")) {
            jul = Double.parseDouble(request.getParameter("jul"));
        }
        if (request.getParameter("ago") != null && !request.getParameter("ago").equals("")) {
            ago = Double.parseDouble(request.getParameter("ago"));
        }
        if (request.getParameter("sep") != null && !request.getParameter("sep").equals("")) {
            sep = Double.parseDouble(request.getParameter("sep"));
        }
        if (request.getParameter("oct") != null && !request.getParameter("oct").equals("")) {
            oct = Double.parseDouble(request.getParameter("oct"));
        }
        if (request.getParameter("nov") != null && !request.getParameter("nov").equals("")) {
            nov = Double.parseDouble(request.getParameter("nov"));
        }
        if (request.getParameter("dic") != null && !request.getParameter("dic").equals("")) {
            dic = Double.parseDouble(request.getParameter("dic"));
        }
        if (request.getParameter("pptoId") != null && !request.getParameter("pptoId").equals("")) {
            pptoId = Integer.parseInt(request.getParameter("pptoId"));
        }
        capturaPresBean = new CapturaPresupuestoBean(tipoDependencia);
        capturaPresBean.setStrServer((request.getHeader("Host")));
        capturaPresBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaPresBean.resultSQLConecta(tipoDependencia);
        if(opcion == 2){
            pptoId = capturaPresBean.getMaxPresupuestoIngreso(year, ramoId, concepto);
            resultado = capturaPresBean.insertPresupuestoIngreso(year, ramoId, concepto, 
                    pptoId, descr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic);
        }if(opcion == 3){
            resultado = capturaPresBean.updatePresupuestoIngreso(year, ramoId, concepto, 
                    pptoId, descr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic);
        }if(opcion == 4){
            resultado = capturaPresBean.deletePresupuestoIngreso(year, ramoId,concepto, pptoId);
        }
        cierre = capturaPresBean.getResultSQLValidaRamoCierre(ramoId, year);
        if(!cierre || capturaPresBean.getResultSQLGetRolesPrg().equals(rol)){
            presupuestoList = capturaPresBean.getPresupuestoIngresoList(year, ramoId, concepto);
            strResultado += " <table id='tblPresupuesto'> <thead> <tr> <th> Subconcepto </th> <th style='width: 65%;'> Descripci&oacute;n </th> <th> Total </th> </tr> </thead> ";
            strResultado += "<tbody> <tr> ";
            for (PresupuestoIngreso presupuesto : presupuestoList) {
                total = 0.0;
                total = presupuesto.getEne() + presupuesto.getFeb() + presupuesto.getMar() + presupuesto.getAbr() + 
                        presupuesto.getMay() + presupuesto.getJun() + presupuesto.getJul() + presupuesto.getAgo() + 
                        presupuesto.getSep() + presupuesto.getOct() + presupuesto.getNov() + presupuesto.getDic(); 
                countRow++;
                if (countRow % 2 == 0) {
                    strResultado += "<tr class='rowPar'> <td>" + presupuesto.getPresupuestoIngresoId() + "</td>";
                }else{
                    strResultado += "<tr> <td>" + presupuesto.getPresupuestoIngresoId() + "</td>";
                }
                strResultado += "<td> " + presupuesto.getPresupuestoIngreso() + "</td> ";
                strResultado += "<td style='text-align: right;'> " + nformat.format(total) + " </td> </tr>";
            }
            strResultado += " </tbody>";
            strResultado += "</table>";
            strResultado += "<script type=\"text/javascript\">";
            strResultado += "  $(\"#tblPresupuesto tbody tr\").click(function(){";
            strResultado += "  $(this).addClass('selected').siblings().removeClass('selected');";
            strResultado += "});";
            strResultado += "</script>";
        }else{
            strResultado = "0";
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
        if (capturaPresBean != null) {
            capturaPresBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
