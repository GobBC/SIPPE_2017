<%-- 
    Document   : ajaxBusquedaEmpleado
    Created on : Mar 19, 2015, 11:45:05 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.PersonaBean"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Persona"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String resultado = new String();
    String nombre = new String();
    String apPaterno = new String();
    String apMaterno = new String();
    String strScript = new String();
    String tipoDependencia = new String();
    int cont = 1;
    String strClassRow = "";
    PersonaBean personaBean = null;
    List<Persona> personaList = new ArrayList<Persona>();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(request.getParameter("nombre") != null && !request.getParameter("nombre").equals("")){
             nombre = (String)request.getParameter("nombre");
        }
        if(request.getParameter("apPaterno") != null && !request.getParameter("apPaterno").equals("")){
             apPaterno = (String)request.getParameter("apPaterno");
        }
        if(request.getParameter("apMaterno") != null && !request.getParameter("apMaterno").equals("")){
             apMaterno = (String)request.getParameter("apMaterno");
        }
        personaBean = new PersonaBean(tipoDependencia);
        personaBean.setStrServer(request.getHeader("host"));
        personaBean.setStrUbicacion(getServletContext().getRealPath(""));
        personaBean.resultSQLConecta(tipoDependencia);
        if(nombre.isEmpty() && apPaterno.isEmpty() && apMaterno.isEmpty()){
            resultado = "<p id=\"error-captura\"> Capture al menos uno de los 3 datos solicitados </p>";
        }else{
            if(nombre.isEmpty()){
                nombre = "%";
            }
            if(apPaterno.isEmpty()){
                apPaterno = "%";
            }
            if(apMaterno.isEmpty()){
                apMaterno = "%";
            }
            personaList = personaBean.getEmpleadosByNombre(nombre, apPaterno, apMaterno);
            resultado = "<table class=\"tabla-busqueda\">";
            resultado += "  <thead>";
            resultado += "      <tr>"; 
            resultado += "          <th colspan=\"2\"> RFC </th>";
            resultado += "          <th> NOMBRE </th>";
            resultado += "      </tr>";
            resultado += "  </thead>";
            resultado += "  <tbody>";
            for(Persona persona : personaList){
                if(cont %2 == 0)
                    strClassRow = "class=\"rowPar\"";
                else
                    strClassRow = "";
                resultado += "      <tr   "+strClassRow+">";
                resultado += "          <td>" + persona.getRfc() +"</td>";
                resultado += "          <td style=\"padding-left: 20px;\">" + persona.getHomoclave() + "</td>";
                resultado += "          <td style=\"padding-left: 50px;\">" + persona.getNombreComleto() + "</td>";
                resultado += "      </tr>";
                cont ++;
            }
            resultado += "  </tbody>";
            resultado += "</table>";
            resultado += "<center>";
            resultado += "  <input type=\"button\" id=\"btnSelResp\" value=\"Seleccionar\" onClick=\"getEmpleadoSelected()\"/>";
            resultado += "  <input type=\"button\" id=\"btnCancelar2\" value=\"Cancelar\" onClick='esconderBusqueda()'/>";
            resultado += "</center>";
            resultado += "<script type=\"text/javascript\">";
            resultado += "  $(\".tabla-busqueda tbody tr\").click(function(){";
            resultado += "  $(this).addClass('selected').siblings().removeClass('selected');";   
            //var value=$(this).find('td:first').html();
            resultado += "});";
            resultado += "</script>";
        }
    }catch (Exception ex) {
        resultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (personaBean != null) {
            personaBean.resultSQLDesconecta();
        }
        out.print(resultado);
    }

%>
