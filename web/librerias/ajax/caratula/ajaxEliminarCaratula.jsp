<%-- 
    Document   : ajaxEliminarCaratula
    Created on : Jun, 2016, 4:32:00 PM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    CaratulaBean caratulaBean = null;
    String strResult = new String();
    String tipoDependencia = new String();
    long selCaratula = -1;
    boolean continuar = true;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        if (selCaratula != -1) {

            if (caratulaBean.isLigadadaPresupuesto(selCaratula)) {
                strResult += "No se puede eliminar la caratula porque est&aacute; asignada a movimientos presupuestales.<br>";
                continuar = false;
            }

            if (caratulaBean.isLigadadaIngreso(selCaratula)) {
                strResult += "No se puede eliminar la caratula porque est&aacute; asignada a modificaciones del ingreso.<br>";
                continuar = false;
            }

            if (continuar) {
                if (caratulaBean.eliminarEstatusIngresoCaratula(selCaratula)) {
                    if (caratulaBean.eliminarCaratula(selCaratula)) {
                        caratulaBean.transaccionCommit();
                        strResult = "1";
                    } else {
                        caratulaBean.transaccionRollback();
                        strResult += "No fue posible actualizar la informaci&oacute;n<br>";
                    }
                } else {
                    caratulaBean.transaccionRollback();
                    strResult += "No fue posible actualizar la informaci&oacute;n<br>";
                }
            }

        } else {
            strResult += "Seleccionar una caratula para eliminar<br>";
        }

    } catch (Exception ex) {
        strResult += "No fue posible actualizar la informacion<br>";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
