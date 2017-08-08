<%-- 
    Document   : ajaxGetCaratulasByRamo
    Created on : Jun 23 , 2016, 1:50:46 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Utilerias"%>
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
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    CaratulaBean caratulaBean = null;
    ArrayList<Caratula> arrCaratulas = null;
    String ramoId = new String();
    String strResultado = new String();
    String caratulaDisable = new String();
    String tipoDependencia = new String();

    int year = 0;
    int tipoCaratula = 1;
    boolean bFiltraEstatusAbiertas = false;
    boolean isReporte = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("tipoCaratula") != null && !request.getParameter("tipoCaratula").equals("")) {
            tipoCaratula = Integer.parseInt(request.getParameter("tipoCaratula"));
        }
        if (Utilerias.existeParametro("isReporte", request)) {
            isReporte = Boolean.parseBoolean(request.getParameter("isReporte"));
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        arrCaratulas = caratulaBean.getCaratulas(String.valueOf(year), ramoId, bFiltraEstatusAbiertas, tipoCaratula, false);

        strResultado += "<option value='-1' Selected > -- Seleccione un caratula -- </option> ";

        if (arrCaratulas.size() > 0) {
            if (isReporte) {
                strResultado += "<option value='-2' >No aplica </option>";
            }
            for (Caratula objCaratula : arrCaratulas) {
                caratulaDisable = "";
                if (year != objCaratula.getiYear()) {
                    caratulaDisable = " class='disabled' disabled ";
                } else {
                    caratulaDisable = " class='enabled' ";
                }

                if (year != objCaratula.getiYearSesion()) {
                    strResultado += "<option " + caratulaDisable + " title='Sesi&oacute;n " + objCaratula.getiYearSesion() + "'  value=" + objCaratula.getsIdCaratula() + "  >" + objCaratula.getStrTipoModificacionDescr() + " " + objCaratula.getsDescr() + " </option>";
                } else {
                    strResultado += "<option " + caratulaDisable + " value=" + objCaratula.getsIdCaratula() + "  >" + objCaratula.getStrTipoModificacionDescr() + " " + objCaratula.getsDescr() + " </option>";
                }

            }
        } else {
            strResultado += "<option value='-2'  > No aplica </option>";
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
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
