<%-- 
    Document   : ajaxGetDepartamentosMonitoreoPresupuestal
    Created on : Abr 15, 2016, 09:20:04 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="gob.gbc.entidades.Fin"%>
<%@page import="gob.gbc.aplicacion.FinBean"%>
<%@page import="gob.gbc.aplicacion.ProgramaConacBean"%>
<%@page import="gob.gbc.entidades.ProgramaConac"%>
<%@page import="gob.gbc.aplicacion.ParametroPrgBean"%>
<%@page import="gob.gbc.entidades.ParametroPrg"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="gob.gbc.aplicacion.DependenciaBean"%>
<%@page import="gob.gbc.entidades.Persona"%>
<%@page import="gob.gbc.aplicacion.PersonaBean"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.ProgramaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    DependenciaBean dependenciaBean = null;
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    String ramoId = new String();
    String programaId = new String();
    String strResultado = new String();
    String tipoDependencia = new String();
    int intYear = 0;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = (String) request.getParameter("ramo");
            ramoId = ramoId.replaceAll("'","");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            programaId = (String) request.getParameter("programa");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            intYear = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (!programaId.equals("-1")) {
            dependenciaBean = new DependenciaBean(tipoDependencia);
            dependenciaBean.setStrServer(request.getHeader("host"));
            dependenciaBean.setStrUbicacion(getServletContext().getRealPath(""));
            dependenciaBean.resultSQLConecta(tipoDependencia);
            dependenciaList = dependenciaBean.getDependenciaByRamoPrograma(ramoId, programaId, intYear);
            dependenciaBean.resultSQLDesconecta();

            strResultado += "<option value='-1'> -- Seleccione un departamento -- </option>";
            if (dependenciaList != null) {
                for (Dependencia dependencia : dependenciaList) {
                    strResultado += "           <option value='" + dependencia.getDeptoId() + "' >";
                    strResultado += dependencia.getDeptoId() + "-" + dependencia.getDepartamento();
                    strResultado += "           </option>";
                }
            }
        } else {
            strResultado += "<option value='-1'> -- Seleccione un departamento -- </option>";
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
        if (dependenciaBean != null) {
            dependenciaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>