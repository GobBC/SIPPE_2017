<%-- 
    Document   : ajaxGetSubconceptosCapturaIngresos
    Created on : Abr 22, 2016, 2:00:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.CapturaPresupuestoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaIngresoBean"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    response.setCharacterEncoding("UTF-8");

    CapturaIngresoBean capturaIngresoBean = null;

    List<CapturaPresupuestoIngreso> presupuestoList = new ArrayList<CapturaPresupuestoIngreso>();

    NumberFormat nformat = NumberFormat.getInstance(Locale.US);

    String status = "A";
    String descr = new String();
    String ramoId = new String();
    String concepto = new String();
    String strResultado = new String();
    String tipoDependencia = new String();    

    double total = 0.0;

    long caratula = -1;

    int year = 0;
    int countRow = 0;

    boolean resultado = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("yearPres") != null && request.getParameter("yearPres") != "") {
            year = Integer.parseInt(request.getParameter("yearPres"));
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = request.getParameter("ramo");
        }
        if (request.getParameter("concepto") != null && !request.getParameter("concepto").equals("")) {
            concepto = request.getParameter("concepto");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            caratula = Long.parseLong(request.getParameter("selCaratula"));
        }

        capturaIngresoBean = new CapturaIngresoBean(tipoDependencia);
        capturaIngresoBean.setStrServer((request.getHeader("Host")));
        capturaIngresoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaIngresoBean.resultSQLConecta(tipoDependencia);


       status = capturaIngresoBean.getStatusModificacionIngreso(caratula, year, ramoId, concepto);
   
        presupuestoList = capturaIngresoBean.getPresupuestoIngresoList(year, ramoId, concepto, caratula);

        strResultado += " <table id='tblPresupuesto'>";
        strResultado += "   <thead> ";
        strResultado += "       <tr> ";
        strResultado += "           <th> Subconcepto </th>";
        strResultado += "           <th style='width: 65%;'> Descripci&oacute;n </th>";
        strResultado += "           <th> Total </th> ";
        strResultado += "           <th></th> ";
        strResultado += "       </tr> ";
        strResultado += "   </thead> ";
        strResultado += "   <tbody> ";
        strResultado += "       <tr> ";
        for (CapturaPresupuestoIngreso presupuesto : presupuestoList) {

            total = 0.0;
            total = presupuesto.getEne() + presupuesto.getFeb() + presupuesto.getMar() + presupuesto.getAbr()
                    + presupuesto.getMay() + presupuesto.getJun() + presupuesto.getJul() + presupuesto.getAgo()
                    + presupuesto.getSep() + presupuesto.getOct() + presupuesto.getNov() + presupuesto.getDic();

            if (presupuesto.getTipoMov().equalsIgnoreCase("R")) {
                total = total * (-1);
            }

            if (total == -0) {
                total = 0;
            }

            countRow++;

            if (countRow % 2 == 0) {
                strResultado += "<tr class='rowPar'> <td>" + presupuesto.getConsec() + "</td>";
            } else {
                strResultado += "<tr> <td>" + presupuesto.getConsec() + "</td>";
            }

            strResultado += "<td> " + presupuesto.getDescr() + "</td> ";
            strResultado += "<td style='text-align: right;'> " + nformat.format(total) + " </td> ";
            if (status.equals("A")) {
                strResultado += "<td><input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarSubConceptoCaptura(\"" + presupuesto.getIdCaratula() + "\",\"" + presupuesto.getYear() + "\",\"" + presupuesto.getRamo() + "\",\"" + presupuesto.getConcepto() + "\",\"" + presupuesto.getConsec() + "\"); getSubconceptosCapturaPresupuestoIngreso(); '/></td>";
            } else {
                strResultado += "<td><input style='display:none' type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarSubConceptoCaptura(\"" + presupuesto.getIdCaratula() + "\",\"" + presupuesto.getYear() + "\",\"" + presupuesto.getRamo() + "\",\"" + presupuesto.getConcepto() + "\",\"" + presupuesto.getConsec() + "\"); getSubconceptosCapturaPresupuestoIngreso(); '/></td>";
            }
            strResultado += "</tr>";

        }

        strResultado += " </tbody>";
        strResultado += "</table>";
        strResultado += "<script type=\"text/javascript\">";
        strResultado += "  $(\"#tblPresupuesto tbody tr\").click(function(){";
        strResultado += "  $(this).addClass('selected').siblings().removeClass('selected');";
        strResultado += "});";
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
