<%-- 
    Document   : ajaxGetSubconceptosCapturaPresupuestoIngreso
    Created on : Jun 16, 2016, 2:00:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.entidades.PresupuestoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaPresupuestoBean"%>
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
    CapturaPresupuestoBean capturaPresBean = null;

    List<PresupuestoIngreso> presupuestoList = new ArrayList<PresupuestoIngreso>();

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

        capturaPresBean = new CapturaPresupuestoBean(tipoDependencia);
        capturaPresBean.setStrServer((request.getHeader("Host")));
        capturaPresBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaPresBean.resultSQLConecta(tipoDependencia);

        capturaIngresoBean = new CapturaIngresoBean(tipoDependencia);
        capturaIngresoBean.setStrServer((request.getHeader("Host")));
        capturaIngresoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaIngresoBean.resultSQLConecta(tipoDependencia);

        presupuestoList = capturaPresBean.getSubconceptosPresupuestoIngresoList(year, ramoId, concepto, caratula);
        status = capturaIngresoBean.getStatusModificacionIngreso(caratula, year, ramoId, concepto);

        strResultado += "               <option value='-1' Selected > -- Seleccione un Subconcepto -- </option> ";

        if (presupuestoList.size() > 0) {
            for (PresupuestoIngreso presupuesto : presupuestoList) {
                strResultado += "               <option value=" + presupuesto.getPresupuestoIngresoId() + "  >" + presupuesto.getPresupuestoIngreso() + " </option>";
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
        if (capturaPresBean != null) {
            capturaPresBean.resultSQLDesconecta();
        }
        out.print(strResultado + "|" + status);
    }
%>
