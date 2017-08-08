<%-- 
    Document   : getInformacionProyecto
    Created on : Mar 26, 2015, 2:47:35 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.DependenciaBean"%>
<%@page import="gob.gbc.entidades.Persona"%>
<%@page import="gob.gbc.aplicacion.PersonaBean"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.ProyectoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ProyectoBean proyectoBean = null;
    DependenciaBean dependenciaBean = null;
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    Proyecto proyecto = new Proyecto();
    PersonaBean personaBean = null;
    Persona persona = new Persona();
    String selected = new String();
    String tipoDependencia = new String();
    int year = 0;
    String tipoProy = new String();
    String ramo = new String();
    String programa = new String();
    int proy = 0;
    String strResult = new String();
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramo") != null
                && !request.getParameter("ramo").equals("")) {
            ramo = (String) request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null
                && !request.getParameter("programa").equals("")) {
            programa = (String) request.getParameter("programa");
        }
        if (request.getParameter("proy") != null
                && !request.getParameter("proy").equals("")) {
            proy = Integer.parseInt((String) request.getParameter("proy"));
        }
        if (request.getParameter("tipoProy") != null
                && !request.getParameter("tipoProy").equals("")) {
            tipoProy = (String) request.getParameter("tipoProy");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        proyectoBean = new ProyectoBean(tipoDependencia);
        proyectoBean.setStrServer(request.getHeader("host"));
        proyectoBean.setStrUbicacion(getServletContext().getRealPath(""));
        proyectoBean.resultSQLConecta(tipoDependencia);
        proyecto = proyectoBean.getProyectoByTipoProyecto(ramo, programa, proy, year, tipoProy);

        strResult += "<table>";
        session.setAttribute("tipoProyecto", proyecto.getTipoProyecto());
        if (proyecto.getTipoProyecto().equalsIgnoreCase("P")) {
            if (proyecto.getRfc() != null || proyecto.getHomoclave() != null) {
                strResult += "  <tr>";
                strResult += "      <td> Lider: </td>";
                strResult += "      <td>";
                personaBean = new PersonaBean(tipoDependencia);
                personaBean.setStrServer(request.getHeader("host"));
                personaBean.setStrUbicacion(getServletContext().getRealPath(""));
                personaBean.resultSQLConecta(tipoDependencia);
                persona = personaBean.getEmpleadoByRFC(proyecto.getRfc(), proyecto.getHomoclave());
                personaBean.resultSQLDesconecta();
                if(persona != null){
                    if(persona.getApMaterno() == null){
                        persona.setApMaterno(new String());
                    }
                    if(persona.getApPaterno()== null){
                        persona.setApPaterno(new String());
                    }
                    if(persona.getNombre()== null){
                        persona.setNombre(new String());
                    }
                }else{
                    persona = new Persona();
                    persona.setApMaterno(new String());
                    persona.setApPaterno(new String());
                    persona.setNombre(new String());
                }
                strResult += "          <input id='inTxtNombreC' style='width: 300px;' value='" + persona.getApPaterno() + " "
                        + persona.getApMaterno() + " " + persona.getNombre() + "' disabled='true'/>";
                strResult += "          <button type='button' class='btn-edit tbl-btn' onclick='mostrarBusqueda()'/> </td>";
            } else {
                strResult += "  <tr>";
                strResult += "      <td> Lider: </td>";
                strResult += "      <td>";
                personaBean = new PersonaBean(tipoDependencia);
                personaBean.setStrServer(request.getHeader("host"));
                personaBean.setStrUbicacion(getServletContext().getRealPath(""));
                personaBean.resultSQLConecta(tipoDependencia);
                persona = personaBean.getEmpleadoByRFC(proyecto.getRfc(), proyecto.getHomoclave());
                personaBean.resultSQLDesconecta();
                strResult += "          <input id='inTxtNombreC' value='No capturado' disabled='true'/>";
                strResult += "          <button type='button' class='btn-edit tbl-btn' onclick='mostrarBusqueda()'/> </td>";
            }
        }
        strResult += "  <tr>";
        strResult += "      <td> Unidad responsable: </td>";
        strResult += "      <td> <select id='selDependencia'>";
        dependenciaBean = new DependenciaBean(tipoDependencia);
        dependenciaBean.setStrServer(request.getHeader("host"));
        dependenciaBean.setStrUbicacion(getServletContext().getRealPath(""));
        dependenciaBean.resultSQLConecta(tipoDependencia);
        dependenciaList = dependenciaBean.getDependenciaByRamoPrograma(ramo, programa, year);
        dependenciaBean.resultSQLDesconecta();
        strResult += "   <option value='-1'>";
        strResult += "       -- Seleccione una unidad responsable --";
        strResult += "   </option>";
        for (Dependencia dependencia : dependenciaList) {
            if (proyecto.getDepto_resp() != null) {
                if (proyecto.getDepto_resp().equals(dependencia.getDeptoId())) {
                    selected = "selected";
                }
            }
            strResult += "           <option " + selected + " value='" + dependencia.getDeptoId() + "'>";
            strResult += dependencia.getDeptoId() + "-" + dependencia.getDepartamento();
            strResult += "           </option>";
            selected = new String();
        }
        strResult += "</select> </td>";
        strResult += "  </tr>";
        strResult += "<table>";
        strResult += "<center>";
        strResult += "<input type='hidden' id='inTxtRFC2' value='"+proyecto.getRfc()+"' />";
        strResult += "<input type='hidden' id='inTxtHC2' value='"+proyecto.getHomoclave()+"' />";
        //strResult += "  <input type='button' class='lineas-proyecto-btn' value='PED' onclick='mostrarLineaPED()'/>";
        //strResult += "  <input type='button' class='lineas-proyecto-btn' value='Sectorial' onclick='mostrarLineaSectorial()'/>";
        strResult += "</center>";
    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (proyectoBean != null) {
            proyectoBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%> 
