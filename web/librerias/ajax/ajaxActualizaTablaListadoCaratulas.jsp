<%-- 
    Document   : ajaxActualizaTablaListadoCaratulas
    Created on : Jan 05, 2016, 4:32:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    Caratula carTemp = null;
    CaratulaBean caratulaBean = new CaratulaBean();
    ArrayList<Caratula> arCaratulas = null;
    String rowPar = "";
    String sTablaDatos = "";
    String sTipoSesion = "";
    String sSesionProg = "";
    String sSesionPres = "";
    String ramoId = new String();
    String sFecha = new String();
    String sNoSesion = new String();
    String strResult = new String();
    String tipoDependencia = new String();
    int year = 0;
    int proyId = 0;
    boolean blResultado = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer(( request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        arCaratulas = caratulaBean.getResultSQLObtieneCaratulas(String.valueOf(year), ramoId, false,1,false);

        if (arCaratulas != null) {
            if (arCaratulas.size() > 0) {
                rowPar = "";
                for (int iRow = 0; iRow < arCaratulas.size(); iRow++) {
                    carTemp = arCaratulas.get(iRow);
                    if (iRow % 2 == 0) {
                        rowPar = "rowPar";
                    } else {
                        rowPar = "rowImpar";
                    }
                    sTablaDatos += "        <tr class='" + rowPar + "'> ";
                    sTablaDatos += "            <td align='center' >" + carTemp.getsFechaRevision() + "</td> ";
                    sTablaDatos += "            <td align='center' >" + carTemp.getsNumSesionDescr() + "</td> ";
                    sTablaDatos += "            <td align='center' >" + carTemp.getsTipoSesionDescr() + "</td> ";
                    sTablaDatos += "            <td align='center' >" + carTemp.getsModPresupDescr() + "</td> ";
                    sTablaDatos += "            <td align='center' >" + carTemp.getsModProgDescr() + "</td> ";
                    //sTablaDatos += "            <td align='center' ><input type='button' " + " class='btnbootstrap-drop btn-edicion' " + "   id='btn-edicion-" + carTemp.getsIdCaratula() + "' " + " onclick=\"consultaCaratulaByFechaSessionNumeroSession('" + carTemp.getsIdCaratula() + "','" + ramoId + "')\" /></td> ";
                    sTablaDatos += "        </tr> ";
                }
                strResult = sTablaDatos;
            }
        }

    } catch (Exception ex) {
        strResult = "-1";
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
