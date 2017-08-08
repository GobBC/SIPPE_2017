<%-- 
    Document   : ajaxGetProgramasFinByYearRamoIndicador
    Created on : Nov 07, 2016, 11:44:59 AM
    Author     : jarguelles
--%>


<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.aplicacion.ProgramaBean"%>
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

    ProgramaBean programaBean = null;
    ArrayList<Programa> arrPrograma = null;

    String year = new String();
    String selRamo = new String();
    String appLogin = new String();
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
        if (request.getParameter("selRamo") != null && !request.getParameter("selRamo").equals("")) {
            selRamo = request.getParameter("selRamo");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }

        programaBean = new ProgramaBean(tipoDependencia);
        programaBean.setStrServer((request.getHeader("Host")));
        programaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        programaBean.resultSQLConecta(tipoDependencia);

        arrPrograma = programaBean.getProgramasByYearAppLogin(Integer.parseInt(year), appLogin, selRamo, selIndicador);

        if (arrPrograma.size() > 0) {
            strResultado += "<option value='-1'> -- Seleccione un programa -- </option>";
            for (Programa objProgramaTemp : arrPrograma) {
                strResultado += "<option value='" + objProgramaTemp.getProgramaId() + "'> "
                        + "" + objProgramaTemp.getProgramaId() + "-" + objProgramaTemp.getProgramaDesc() + "</option>";
            }
        } else {
            strResultado = "<option value='-1'> -- Seleccione un programa -- </option>";
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
        if (programaBean != null) {
            programaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>