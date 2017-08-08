<%-- 
    Document   : caratula
    Created on : 23/05/2016, 09:56:20 AM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.RevisionCaratula"%>
<%@page import="gob.gbc.aplicacion.RevisionCaratulaBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.TipoSesion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Caratula de modificaci&oacute;n</title>
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
    <link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
    <script src="librerias/_caratulas.js" type="text/javascript"></script>
    <div Id="TitProcess"><label>CARATULA DE MODIFICACI&Oacute;N<label/></div>
    <%
        request.setCharacterEncoding("UTF-8");

        CaratulaBean caratulaBean = null;
        ArrayList<Caratula> arrCaratulas = new ArrayList<Caratula>();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<RevisionCaratula> revisionesCaratulaList = null;
        String rol = new String();
        String ramoId = new String();
        String appLogin = new String();
        String disabled = new String();
        String urlRegresa = new String();
        String ramoSession = new String();
        String fechaSession = new String();
        String numeroSession = new String();
        String tipoDependencia = new String();
        long caratula = -2;
        int year = 0;
        boolean bFiltraEstatusAbiertas = false;

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("strUsuario") == null || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") == null || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (request.getParameter("urlRegresa") != null && !request.getParameter("urlRegresa").equals("")) {
            urlRegresa = request.getParameter("urlRegresa");
        }
        if (request.getParameter("caratula") != null && !request.getParameter("caratula").equals("")) {
            caratula = Long.parseLong(request.getParameter("caratula"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (urlRegresa.equals("")) {
            urlRegresa = "menu.jsp";
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        ramoList = caratulaBean.getResultRamoByYear(year, appLogin);

        if (!rol.equals(caratulaBean.getRolNormativo())) {
            disabled = "disabled";
            ramoId = ramoSession;
        }

        arrCaratulas = caratulaBean.getCaratulas(String.valueOf(year), ramoId, bFiltraEstatusAbiertas, 1, false);

    %>

    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-atras" onclick="regresarAutorizacion()">
                <br/> <small> Regresar </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div>
        <fieldset style="display:inline">
            <legend>
                Ramo
            </legend>
            <div class='caratula'>     
                <table id="tblComboCaratula">
                    <tr>
                        <td>
                            <div id='txtCaratula' >
                                Ramo:
                            </div> 
                        </td>
                        <td>
                            <select <%= disabled%> id="selRamo" name="selRamo" onchange='capturaCaratulasGetListadoCaratulas()'>
                                <option value="-1"> -- Seleccione un ramo -- </option>
                                <%
                                    if (ramoList.size() > 0) {
                                        for (Ramo ramoTemp : ramoList) {
                                            if (ramoId.equals(ramoTemp.getRamo())) {
                                                out.print("<option selected value=" + ramoTemp.getRamo() + ">" + ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr() + " </option>");
                                            } else {
                                                out.print("<option value=" + ramoTemp.getRamo() + ">" + ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr() + " </option>");
                                            }
                                        }
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                </table>                      
            </div>
        </fieldset>
        <fieldset style="display:inline">
            <legend>
                Caratula de modificaci&oacute;n
            </legend>
            <div class='caratula'>     
                <table id="tblComboCaratula">
                    <tr>
                        <td>
                            <div id='txtCaratula' >
                                Caratula:
                            </div> 
                        </td>
                        <td>
                            <%
                                out.print("<select style='width: 373px;' id='selCaratula' name='selCaratula' onchange='getTablaMovimientosByCaratula();'>");

                                out.print("<option value='-1'> -- Seleccione un caratula -- </option>");

                                if (arrCaratulas.size() > 0) {
                                    for (Caratula objCaratula : arrCaratulas) {
                                        if (caratula == objCaratula.getsIdCaratula()) {
                                            out.print("<option value=" + objCaratula.getsIdCaratula() + " selected   >" + objCaratula.getsDescr() + " </option>");
                                        } else {
                                            out.print("<option value=" + objCaratula.getsIdCaratula() + " >" + objCaratula.getsDescr() + " </option>");
                                        }
                                    }
                                }

                                out.print("</select>");

                            %>
                        </td>
                        <td>
                            <%if (caratula != -2) {%> 

                            <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-caratula" style="display:none" onclick="capturaCaratulasGetPopUpCaratula()"/>
                            <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-caratula" style="" onclick="capturaCaratulasGetPopUpCaratula()"/>
                            <input type="button" class="btnbootstrap-drop btn-borrar " id="btn-borrar-caratula" style="" onclick="capturaCaratulasEliminarCaratula()"/>
                            <%} else {%>

                            <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-caratula" style="" onclick="capturaCaratulasGetPopUpCaratula()"/>
                            <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-caratula" style="display:none" onclick="capturaCaratulasGetPopUpCaratula()"/>
                            <input type="button" class="btnbootstrap-drop btn-borrar " id="btn-borrar-caratula" style="display:none" onclick="capturaCaratulasEliminarCaratula()"/>

                            <%}%>
                        </td>
                    </tr>
                </table>          

                <%
                    out.print("<br>");
                    out.print("<div id='infoCaratula' class='info-usuario'>");
                    out.print("</div>");

                %>                        

            </div>
        </fieldset>
        <fieldset style="">
            <legend>
                Movimientos Ligados A La Caratula
            </legend>
            <div id="TableZone">
                <table id="tbl-solMovs" class="display" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th align="right" >Folio</th>
                            <th align="right" >Fecha<br>Elaboración</th>                   
                            <th align="right" >Estatus</th>
                            <th align="right" ></th>
                            <th align="right" ></th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </fieldset>
        <fieldset style="">
            <legend>
                Movimientos Sin Aplicar Caratula
            </legend>
            <div id="TableNoAplica">
                <table id="tbl-solMovsNoAplica" class="display" cellspacing="0" width="100%" >
                    <thead>
                        <tr>
                            <th align="right" >Folio</th>
                            <th align="right" >Fecha<br>Elaboración</th>                            
                            <th align="right" >Estatus</th>
                            <th align="right" ></th>
                            <th align="right" ></th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </fieldset>
        <div id="dialog-tbl-caratula" title="Caratula de Revisión">
            <p id="errorComentario" style="color:red"  ></p>
            <div> 
                <div id="caratulaZone" class="col-md-12"></div>                
            </div> 
        </div>
        <script type="text/javascript">
            $(function() {
                $("#dialog-tbl-caratula").dialog({
                    autoOpen: false,
                    height: 340,
                    width: 740,
                    draggable: false,
                    modal: true,
                    buttons: {
                        "Aceptar": function() {
                            capturaCaratulasActualizaCaratula();
                            $("#cancelEdit").addClass("hidden");
                            $("#caratulaZone").html("");
                            $("#errorComentario").html("");
                            $(this).dialog("close");
                        },
                        "Cancelar": function() {
                            $("#errorComentario").html("");
                            $("#cancelEdit").addClass("hidden");
                            $(this).dialog("close");
                        }
                    }, close: function() {
                        $("#caratulaZone").html("");
                        $("#errorComentario").html("");
                        $("#cancelEdit").addClass("hidden");
                        $(this).dialog("close");
                    }
                });
            });
        </script> 
        <div id="mensaje" style="position: relative"></div>
        <form id='frmRegresa' method='POST' action="<%=urlRegresa%>">
            <input type="hidden" id="ramoId" name="ramoId" value="<%=ramoId%>" />
        </form>

    </div>
    <jsp:include page="template/piePagina.jsp" />
    <script>
        dataTablePOA("tbl-solMovs");
        dataTablePOA("tbl-solMovsNoAplica");
        <%if (caratula != -2) {%>
        getTablaMovimientosByCaratula();
        capturaCaratulasGetPopUpCaratula();
        <%}%>

    </script>
    <%
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
    %>

</html>
