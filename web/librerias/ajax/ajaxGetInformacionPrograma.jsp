<%-- 
    Document   : ajaxGetInformacionPrograma
    Created on : Mar 23, 2015, 12:07:04 PM
    Author     : ugarcia
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
    String strResultado = new String();
    String selected = new String();

    String ramoId = new String();
    String programaId = new String();
    String tipoDependencia = new String();
    FinBean finBean = null;
    List<Fin> finList = new ArrayList<Fin>();
    List<Linea> lineaRamoList = new ArrayList<Linea>();
    List<Linea> lineaList = new ArrayList<Linea>();
    ProgramaBean programaBean = new ProgramaBean();
    DependenciaBean dependenciaBean = null;
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    ProgramaConacBean programaConacBean = null;
    List<ProgramaConac> programaConacList = new ArrayList<ProgramaConac>();
    ParametroPrgBean parametroPrgBean = null;
    String disabledFinProposito = new String();
    PersonaBean personaBean = null;
    Persona persona = new Persona();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    int intYear = 0;
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = (String) request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            programaId = (String) request.getParameter("programa");
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            intYear = Integer.parseInt((String) session.getAttribute("year"));
        }
        programaBean = new ProgramaBean(tipoDependencia);
        programaBean.setStrServer(request.getHeader("host"));
        programaBean.setStrUbicacion(getServletContext().getRealPath(""));
        programaBean.resultSQLConecta(tipoDependencia);
        if (!programaId.equals("-1")) {
            Programa programa = new Programa();
            programa = programaBean.getProgramaById(programaId, ramoId, intYear);
            if (programa != null) {
                if (programa.getRfc() == null) {
                    programa.setRfc("No capturado");
                }
                if (programa.getHomoclave() == null) {
                    programa.setHomoclave("No capturado");
                }
                strResultado += "<div id='tabContent-1'>";
                strResultado += "    <table>";
                strResultado += "       <tr>";
                strResultado += "           <td>";
                strResultado += "               RFC:";
                strResultado += "           </td>";
                strResultado += "           <td>";
                strResultado += "               <input type='text' disabled='true' id='inTxtRFC' value='" + programa.getRfc() + "' style='width:300px; height:28px; '/>";
                strResultado += "               <input type='text' disabled='true' id='inTxtHC' value='" + programa.getHomoclave() + "' style='width: 56px; height:28px; text-align: center'/>";
                strResultado += "           </td>";
                strResultado += "       </tr>";
                strResultado += "       <tr>";
                strResultado += "           <td>";
                strResultado += "               Nombre:";
                strResultado += "           </td>";
                strResultado += "           <td>";
                personaBean = new PersonaBean(tipoDependencia);
                personaBean.setStrServer(request.getHeader("host"));
                personaBean.setStrUbicacion(getServletContext().getRealPath(""));
                personaBean.resultSQLConecta(tipoDependencia);
                persona = personaBean.getEmpleadoByRFC(programa.getRfc(), programa.getHomoclave());
                personaBean.resultSQLDesconecta();
                if (persona != null) {
                    strResultado += "               <input type='text' disabled='true' id='inTxtNombreC' value='"
                            + persona.getApPaterno() + " " + persona.getApMaterno() + " " + persona.getNombre() + "'"
                            + "style='width:300px; height:28px;'/>";
                } else {
                    strResultado += "               <input type='text' disabled='true' id='inTxtNombreC' value='No capturado'"
                            + "style='width:300px; height:28px;'/>";
                }
                strResultado += "                   <input type='button' class='edit-icon-table' onclick='mostrarBusqueda()' title='Editar responsable'/>";
                strResultado += "           </td>";
                strResultado += "       </tr>";
                strResultado += "       <tr>";
                strResultado += "           <td>";
                strResultado += "               U.E. Responsable:";
                strResultado += "           </td>";
                strResultado += "           <td>";
                strResultado += "               <select id='selUE' style='width: 380px;' value='" + programa.getFin() + "'>";
                dependenciaBean = new DependenciaBean(tipoDependencia);
                dependenciaBean.setStrServer(request.getHeader("host"));
                dependenciaBean.setStrUbicacion(getServletContext().getRealPath(""));
                dependenciaBean.resultSQLConecta(tipoDependencia);
                dependenciaList = dependenciaBean.getDependenciaByRamoPrograma(ramoId, programaId, intYear);
                dependenciaBean.resultSQLDesconecta();
                strResultado += "<option value='-1'> -- Seleccione un departamente -- </option>";
                for (Dependencia dependencia : dependenciaList) {
                    if (dependencia.getDeptoId().equalsIgnoreCase(programa.getUnidadResponsable())) {
                        selected = "selected";
                    } else {
                        selected = "";
                    }
                    strResultado += "           <option value='" + dependencia.getDeptoId() + "' " + selected + ">";
                    strResultado += dependencia.getDeptoId() + "-" + dependencia.getDepartamento();
                    strResultado += "           </option>";
                }
                strResultado += "               </select>";
                strResultado += "           </td>";
                strResultado += "       </tr>";
                strResultado += "   </table>";
                strResultado += "</div>";
                parametroPrgBean = new ParametroPrgBean(tipoDependencia);
                parametroPrgBean.setStrServer(request.getHeader("host"));
                parametroPrgBean.setStrUbicacion(getServletContext().getRealPath(""));
                parametroPrgBean.resultSQLConecta(tipoDependencia);
                if (!parametroPrgBean.getResultSQLGetRolesPrg().equals((String) session.getAttribute("strRol"))) {
                    disabledFinProposito = " disabled = True ";
                }

                parametroPrgBean.resultSQLDesconecta();
                strResultado += "<div id='tabContent-2'>";
                strResultado += "<table>";
                strResultado += "   <tr> <td>";
                strResultado += "Fin:";
                strResultado += "   </td> <td>";
                strResultado += "<select id='selFin'>";
                finBean = new FinBean(tipoDependencia);
                finBean.setStrServer(request.getHeader("host"));
                finBean.setStrUbicacion(getServletContext().getRealPath(""));
                finBean.resultSQLConecta(tipoDependencia);
                finList = finBean.getFinOnRamoPrograma(ramoId, intYear);
                finBean.resultSQLDesconecta();
                strResultado += "   <option value='-1'>";

                strResultado += "--Programa sin fines definidos--";
                strResultado += "</option>";

                if (finList.size() > 0) {
                    for (Fin fin : finList) {
                        if (fin.getFinId() == programa.getFin()) {
                            selected = "selected";
                        }
                        strResultado += "   <option " + selected + " value='" + fin.getFinId() + "' >";
                        strResultado += fin.getFinId() + "-" + fin.getDescripcion();
                        strResultado += "   </option>";
                        selected = "";
                    }
                }

                strResultado += "</select> </td> </tr>";
                strResultado += "<tr> <td>";
                strResultado += "Prop&oacute;sito: </td>";
                strResultado += "<td> <textarea id='txtAreaProposito' class='no-enter' style='width:400px; height:75px; text-transform: uppercase;' " + disabledFinProposito + "  >";
                strResultado += programa.getProposito();
                strResultado += "</textarea> </td> </tr>";
                strResultado += "</table>";
                strResultado += "</div>";
                strResultado += "<div id='tabContent-3'>";
                strResultado += "   <select id='selPonderacion' value='" + programa.getPonderado() + "'>";
                strResultado += "<option value='-1'>-- Seleccione una ponderaci&oacute;n --</option>";
                if (programa.getPonderado() == 1) {
                    strResultado += "       <option selected value='1'>";
                } else {
                    strResultado += "       <option value='1'>";
                }
                strResultado += "           1-Baja";
                strResultado += "       </option>";
                if (programa.getPonderado() == 2) {
                    strResultado += "       <option selected value='2'>";
                } else {
                    strResultado += "       <option value='2'>";
                }
                strResultado += "           2-Media";
                strResultado += "       </option>";
                if (programa.getPonderado() == 3) {
                    strResultado += "       <option selected value='3'>";
                } else {
                    strResultado += "       <option value='3'>";
                }
                strResultado += "           3-Alta";
                strResultado += "       </option>";
                strResultado += "   </select>";
                strResultado += "</div>";

                strResultado += "<div id='tabContent-4'>";
                programaConacBean = new ProgramaConacBean(tipoDependencia);
                programaConacBean.setStrServer(request.getHeader("host"));
                programaConacBean.setStrUbicacion(getServletContext().getRealPath(""));
                programaConacBean.resultSQLConecta(tipoDependencia);
                programaConacList = programaConacBean.getDescripcionByProgramaConacAnio(programaId, intYear);
                programaConacBean.resultSQLDesconecta();

                if (programaConacList.size() > 0) {
                    for (ProgramaConac programaConac : programaConacList) {

                        strResultado += "<table>";
                        strResultado += "       <tr>";
                        strResultado += "           <td>";
                        strResultado += "               Clave: ";
                        strResultado += "           </td>";
                        strResultado += "           <td>";
                        strResultado += "               <input type='text' disabled='true' id='inTxtClaveProgramaConac' value='" + programaConac.getProgramaConac() + "' style='width:300px; height:28px; '/>";
                        strResultado += "           </td>";
                        strResultado += "       </tr>";
                        strResultado += "       <tr>";
                        strResultado += "           <td>";
                        strResultado += "               Descripción: ";
                        strResultado += "           </td>";
                        strResultado += "           <td>";
                        strResultado += "               <textarea style=\"width:400px; class='no-enter' height:75px; text-transform: uppercase;\" disabled>";
                        strResultado += programaConac.getDescripcion();
                        strResultado += "               </textarea >";
                        strResultado += "           </td>";
                        strResultado += "       </tr>";
                        strResultado += "</table>";
                    }
                }
                strResultado += "</div>";
                lineaRamoList = programaBean.getLineaRamoPrg(intYear, ramoId, programaId);
                lineaList = programaBean.getAllLineaRamoPrg(intYear, ramoId);
                strResultado += "<div id='tabContent-5'>";
                strResultado += "<section id='divLinRamo'>";
                strResultado += "<table id='tblLineaAcc'> ";
                for(Linea linea : lineaRamoList){
                    strResultado += "<tr> <td>";
                    strResultado += "<select class='ramoLineaAccion' onChange='return cambioLineaAccion()'>";
                    strResultado += "<option style='width=250px;' value='0'>--Seleccione una l&iacute;nea de acci&oacute;n--</option>";
                    for(Linea lineaE : lineaList){
                        if(linea.getLineaId().equals(lineaE.getLineaId())){
                            selected = "selected";
                        }
                        strResultado += "<option style='width=250px;' value='"+lineaE.getLineaId()+"' "+selected+" >"+lineaE.getLineaId()
                                +"-"+lineaE.getLinea()+"</option>";
                        selected = new String();
                    }
                    strResultado += "</select>";
                    strResultado += "</td> </tr> ";
                }
                strResultado += "</table>";
                session.setAttribute("linea-ramo", lineaRamoList);
                session.setAttribute("linea-accion", lineaList);
                strResultado += "</section>";
                strResultado += "<section id='divControlLinea'> ";
                strResultado += "<input type='button' id='inpTxtInsLin' value='Insertar' onclick='insertarLineaAccion()'/> ";
                strResultado += "<input type='button' id='inpTxtBorrarLin' value='Borrar' onclick='borrarLineaAccion()'/> ";
                strResultado += "</section>";
                strResultado += "</div>";
                strResultado += "<script type=\"text/javascript\">";
                strResultado += "  $(\"#tblLineaAcc tr\").click(function(){";
                strResultado += "  $(this).addClass('selected-linea').siblings().removeClass('selected-linea');";
                strResultado += "});";
                strResultado += "</script>";
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
        if (programaBean != null) {
            programaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

<script>
    $('.no-enter').bind('keypress', function(e){
        if(e.keyCode == 13)
        {
           return false;
        }
     });     
</script>