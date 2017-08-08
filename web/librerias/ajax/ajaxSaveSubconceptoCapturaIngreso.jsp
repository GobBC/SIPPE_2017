<%-- 
    Document   : ajaxSaveSubconceptoCapturaIngreso
    Created on : Abr 22, 2016, 2:00:00 PM
    Author     : jarguelles
--%>

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

    String ramoId = new String();
    String concepto = new String();
    String selTipoMov = new String();

    String strResultado = new String();
    String tipoDependencia = new String();
    String subConceptoDescr = new String();

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

    long selCaratula = -1;

    int year = 0;
    int countRow = 0;
    int subConcepto = -1;

    boolean resultado = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
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
        if (request.getParameter("selTipoMov") != null && !request.getParameter("selTipoMov").equals("")) {
            selTipoMov = request.getParameter("selTipoMov");
        }
        if (request.getParameter("subConcepto") != null && !request.getParameter("subConcepto").equals("")) {
            subConcepto = Integer.parseInt(String.valueOf(request.getParameter("subConcepto")));
        }
        if (request.getParameter("subConceptoDescr") != null && !request.getParameter("subConceptoDescr").equals("")) {
            subConceptoDescr = request.getParameter("subConceptoDescr");
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

        capturaIngresoBean = new CapturaIngresoBean(tipoDependencia);
        capturaIngresoBean.setStrServer((request.getHeader("Host")));
        capturaIngresoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaIngresoBean.resultSQLConecta(tipoDependencia);

        if (subConcepto == -1) {
            subConcepto = capturaIngresoBean.getMaxSubConceptoCapturaIngreso(selCaratula, year, ramoId, concepto);
            resultado = capturaIngresoBean.insertSubConceptoCapturaIngreso(selCaratula, year, ramoId, concepto, subConcepto, subConceptoDescr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, selTipoMov);
        } else {
            if (capturaIngresoBean.existeSubConceptoCapturaIngreso(year, ramoId, concepto, subConcepto, selCaratula)) {
                resultado = capturaIngresoBean.updateSubConceptoCapturaIngreso(selCaratula, year, ramoId, concepto, subConcepto, subConceptoDescr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, selTipoMov);
            } else {
                resultado = capturaIngresoBean.insertSubConceptoCapturaIngreso(selCaratula, year, ramoId, concepto, subConcepto, subConceptoDescr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, selTipoMov);
            }
        }

        if (capturaIngresoBean.existeStatusModificacionIngreso(selCaratula, year, ramoId, concepto)) {
            resultado = capturaIngresoBean.updateStatusIngresoModificado(selCaratula, year, ramoId, concepto, "A");
        } else {
            resultado = capturaIngresoBean.insertStatusIngresoModificado(selCaratula, year, ramoId, concepto, "A");
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
        if (capturaIngresoBean != null) {
            capturaIngresoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
