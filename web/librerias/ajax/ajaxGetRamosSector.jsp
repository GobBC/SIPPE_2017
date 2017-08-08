<%-- 
    Document   : ajaxGetRamosSector
    Created on : Nov 07, 2016, 11:44:59 AM
    Author     : jarguelles
--%>


<%@page import="gob.gbc.entidades.SectorRamo"%>
<%@page import="gob.gbc.aplicacion.SectorRamoBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Expires", "0");
    response.setDateHeader("Expires", -1);
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");

    SectorRamoBean sectorRamoBean = null;
    ArrayList<SectorRamo> arrSectoresRamos = null;

    String year = new String();
    String strResultado = new String();
    String selIndicador = new String();
    String tipoDependencia = new String();
    

    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = (String) session.getAttribute("year");
        }
        if (request.getParameter("selIndicador") != null && !request.getParameter("selIndicador").equals("")) {
            selIndicador = request.getParameter("selIndicador");
        }

        sectorRamoBean = new SectorRamoBean(tipoDependencia);
        sectorRamoBean.setStrServer(request.getHeader("host"));
        sectorRamoBean.setStrUbicacion(getServletContext().getRealPath(""));
        sectorRamoBean.resultSQLConecta(tipoDependencia);

        arrSectoresRamos = sectorRamoBean.getObtieneSectorRamos(year, selIndicador);

        if (arrSectoresRamos.size() > 0) {
            strResultado += "<option value='-1'> -- Seleccione un ramo -- </option>";
            for (SectorRamo objSectorRamo : arrSectoresRamos) {
                strResultado += "<option value='" + objSectorRamo.getRamo() + "'> "
                        + "" + objSectorRamo.getRamo() + "-" + objSectorRamo.getRamoDescr() + "</option>";
            }
        } else {
            strResultado = "<option value='-1'> -- Seleccione un ramo -- </option>";
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
        if (sectorRamoBean != null) {
            sectorRamoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>