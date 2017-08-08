<%-- 
    Document   : consultaSolicitudes
    Created on : Dec 11, 2015, 9:44:10 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.EstatusMov"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="gob.gbc.entidades.TipoFlujo"%>
<%@page import="gob.gbc.entidades.TipoMovimiento"%>
<%@page import="gob.gbc.entidades.TipoGasto"%>
<%@page import="gob.gbc.entidades.FuenteFinanciamiento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }

        int year = 0;
        String tipoDependencia = new String();
        String usuario = new String();
        List<TipoMovimiento> tipoMovimientoList = new ArrayList<TipoMovimiento>();
        List<EstatusMov> estatusMovimientosList = new ArrayList<EstatusMov>();
        List<Ramo> ramoList = new ArrayList<Ramo>();

        String tipoMov = new String();
        String estatus = new String();
        String selectTipoMov = new String();
        String selectEstatus = new String();
        String rol = new String();
        String opRamo = new String();

        int opcion = 1;
        int folio = 0;
        int nuevo = 0;

        boolean existeInversion = false;

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }

        if (request.getParameter("tipoMov") != null && !request.getParameter("tipoMov").equals("")) {
            tipoMov = request.getParameter("tipoMov");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = request.getParameter("estatus");
        }
        if (request.getParameter("opcion") != null && !request.getParameter("opcion").equals("")) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (request.getParameter("nuevo") != null && !request.getParameter("nuevo").equals("")) {
            nuevo = Integer.parseInt(request.getParameter("nuevo"));
        }
        if (request.getParameter("folio") != null && !request.getParameter("folio").equals("")) {
            if (opcion == 1) {
                folio = Integer.parseInt(request.getParameter("folio"));
            }
        }
        if (request.getParameter("ramoCons") != null && !request.getParameter("ramoCons").equals("")) {
            if (opcion == 2) {
                opRamo = request.getParameter("ramoCons");
            }
        }

        AutorizacionBean autorizacionBean = new AutorizacionBean(tipoDependencia);
        autorizacionBean.setStrServer((request.getHeader("Host")));
        autorizacionBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        autorizacionBean.resultSQLConecta(tipoDependencia);

        tipoMovimientoList = autorizacionBean.getTipoMovimiento();
        estatusMovimientosList = autorizacionBean.getResultEstatusMovimientos();

    %>
    <jsp:include page="template/encabezado.jsp" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consulta de solicitudes</title>
        <link type="text/css" rel="stylesheet" href="librerias/js/jquery.qtip.custom/jquery.qtip.css" />
        <script type="text/javascript" src="librerias/js/jquery.qtip.custom/jquery.qtip.js"></script>
    </head>
    <div Id="TitProcess"><label>CONSULTA DE SOLICITUDES<label/></div>
    <div class="col-md-10 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div" style="margin: 5px 2%;">
        <div>
            <div>

            </div>
            <section>
                <div id="div-inpt-folio" class="col-md-12">
                    <div class="col-md-4">
                        <div class="col-md-12">
                            <%if (opcion == 0) {%>
                            <input type="radio" id="radioFolio" name="radio-busca" value="folio" onclick="cambioBusqueda()" > 
                            <label for="radioFolio">Folio: </label>
                            <input type="text" id="inpt-folio" value="<%=folio%>" disabled />
                            </input>          
                            <input type="button" id="btnFolio" name="btnFolio" value="Buscar" onclick="getTablaSolicitudesMovimientosByFolio()" disabled /> <br/>                   
                            <%} else {%>
                            <input type="radio" id="radioFolio" name="radio-busca" value="folio" onclick="cambioBusqueda()" checked > 
                            <label for="radioFolio">Folio: </label>
                            <input type="text" id="inpt-folio" value="<%=folio%>"/>
                            </input>              
                            <input type="button" id="btnFolio" name="btnFolio" value="Buscar" onclick="getTablaSolicitudesMovimientosByFolio()"/> <br/>                
                            <%}%>                             
                        </div>  
                    </div>
                </div>
            </section>
            <br/>   <br/>   
            <section>
                <div class="col-md-12">
                    <div class="col-md-4">
                        <div class="col-md-1">
                            <%if (opcion == 0) {%>
                            <input type="radio" id="radioCompleto" name="radio-busca" onclick="cambioBusqueda()" value="folio" checked/>   
                            <%} else {%>               
                            <input type="radio" id="radioCompleto" name="radio-busca" onclick="cambioBusqueda()" value="folio"/>  
                            <%}%>
                        </div>
                        <div class="col-md-10">
                            <label id="lbTipoSolicitud" for="radioCompleto" >Tipo de Solicitud</label>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <%if (opcion == 1) {%>
                        <select id='selTipoSolicitud' onchange='getTablaSolicitudesMovimientos()' >
                            <option value='-1' >-- Selecciona un tipo de solicitud --</option>
                            <%for (TipoMovimiento tipoMovimiento : tipoMovimientoList) {
                                    selectTipoMov = "";
                                    if (tipoMovimiento.getTipoMovId().equalsIgnoreCase(tipoMov) && opcion == 0) {
                                        selectTipoMov = "selected";
                                    }

                                    out.println("<option value='" + tipoMovimiento.getTipoMovId() + "' " + selectTipoMov + "  >"
                                            + tipoMovimiento.getTipoMov() + " </option>");
                                }
                            %>
                        </select>
                        <%} else {%>
                        <select id='selTipoSolicitud' onchange='getTablaSolicitudesMovimientos()' disabled>
                            <option value='-1' >-- Selecciona un tipo de solicitud --</option>
                            <%for (TipoMovimiento tipoMovimiento : tipoMovimientoList) {
                                    selectTipoMov = "";
                                    if (tipoMovimiento.getTipoMovId().equalsIgnoreCase(tipoMov) && opcion == 0) {
                                        selectTipoMov = "selected";
                                    }

                                    out.println("<option value='" + tipoMovimiento.getTipoMovId() + "' " + selectTipoMov + "  >"
                                            + tipoMovimiento.getTipoMov() + " </option>");
                                }
                            %>
                        </select>
                        <%}%>
                    </div>
                    <div class="col-md-4">
                        <%if (opcion == 1) {%>
                        <select id="selEstatusMovimiento"  onchange='getTablaSolicitudesMovimientos()' >
                            <option value="-1">-- Selecciona un estatus --</option>
                            <%
                                for (EstatusMov estatusMovimiento : estatusMovimientosList) {
                                    selectEstatus = "";
                                    if (estatusMovimiento.getEstatusMovId().equalsIgnoreCase(estatus) && opcion == 0) {
                                        selectEstatus = "selected";
                                    }

                                    out.println("<option value='" + estatusMovimiento.getEstatusMovId() + "' " + selectEstatus + " >"
                                            + estatusMovimiento.getEstatusMov() + " </option>");
                                }
                            %>
                        </select>
                        <%} else {%>
                        <select id="selEstatusMovimiento"  onchange='getTablaSolicitudesMovimientos()' disabled>
                            <option value="-1">-- Selecciona un estatus --</option>
                            <%
                                for (EstatusMov estatusMovimiento : estatusMovimientosList) {
                                    selectEstatus = "";
                                    if (estatusMovimiento.getEstatusMovId().equalsIgnoreCase(estatus) && opcion == 0) {
                                        selectEstatus = "selected";
                                    }

                                    out.println("<option value='" + estatusMovimiento.getEstatusMovId() + "' " + selectEstatus + " >"
                                            + estatusMovimiento.getEstatusMov() + " </option>");
                                }
                            %>
                        </select>
                        <%}%>  
                    </div>
                </div>
                    <br/>
                    <br/>
                <%if (autorizacionBean.getRolNormativo(Integer.parseInt(rol)) > 0) { %>
                <div class="col-md-4">
                </div>   
                <div class="col-md-8" style="padding: 0px 20px;">
                    <select style="width:100%" id="selRamoAfectado" onchange="getMovtosByRamoAfectado()">
                        <option>-- Selecciona ramo afectado--</option>
                    </select>
                </div>
                <%}%>
            </section>
            <br/>  <br/>   
            <%if (autorizacionBean.getRolNormativo(Integer.parseInt(rol)) > 0) {
                    ramoList = autorizacionBean.getResultRamoByYear(year, usuario);
            %>
            <section>
                <div class="col-md-12">
                    <div class="col-md-4">
                        <div class="col-md-1">
                            <%if (opcion != 2) { %>
                            <input id="radioRamo" type="radio" name="radio-busca" value="ramo" onClick="cambioBusqueda()"/>
                            <%} else {%>
                            <input id="radioRamo" type="radio" name="radio-busca" value="ramo" onClick="cambioBusqueda()" checked />
                            <%}%>
                        </div>
                        <div class="col-md-10">
                            <label for="radioRamo"> Ramo: </label>
                        </div>
                    </div>                    
                    <div class="col-md-8">
                        <%if (opcion != 2) { %>
                        <select id="opcRamo" id="opcRamo" onchange='getTablaSolicitudesMovimientosByRamo()' disabled>
                            <option value="-1"> -- Seleccione un ramo -- </option>
                            <%
                                for (Ramo ramo : ramoList) {
                                    out.write("<option value='" + ramo.getRamo() + "'>");
                                    out.write(ramo.getRamo() + "-" + ramo.getRamoDescr());
                                    out.write("</option>");

                                    if (ramo.getRamo().equals("07")) {
                                        existeInversion = true;
                                    }
                                }
                                if (rol.equals(autorizacionBean.getRolNormativo())) {
                                    if (!existeInversion) {
                                        out.write("<option value='07'>");
                                        out.write("07-SECRETARIA DE PLANEACION Y FINANZAS");
                                        out.write("</option>");
                                    }
                                }
                            %>
                        </select>
                        <%} else {%>
                        <select id="opcRamo" id="opcRamo" onchange='getTablaSolicitudesMovimientosByRamo()'>
                            <option value="-1"> -- Seleccione un ramo -- </option>
                            <%
                                for (Ramo ramo : ramoList) {
                                    selectEstatus = "";
                                    if (ramo.getRamo().equals(opRamo)) {
                                        selectEstatus = "selected";
                                    }
                                    out.write("<option value='" + ramo.getRamo() + "' " + selectEstatus + ">");
                                    out.write(ramo.getRamo() + "-" + ramo.getRamoDescr());
                                    out.write("</option>");

                                    if (ramo.getRamo().equals("07")) {
                                        existeInversion = true;
                                    }
                                }
                                if (rol.equals(autorizacionBean.getRolNormativo())) {
                                    if (!existeInversion) {
                                        out.write("<option value='07'>");
                                        out.write("07-SECRETARIA DE PLANEACION Y FINANZAS");
                                        out.write("</option>");
                                    }
                                }
                            %>
                        </select>
                        <%}%>
                    </div>
                </div>
            </section>
            <%}%>
        </div>
        <br>
        <br>
        <br>
        <div id="TableZone">
            <table id="tbl-solMovs" class="display" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <th align="right" >Oficio</th>
                        <th align="right" >Fecha<br>Elaboración</th>
                        <th align="right" >Fecha Envío<br>Autorización</th>
                        <th align="right" width ="200">Ramo</th>                        
                        <th align="right" >Estatus</th>
                            <%-- <th align="right" ></th>   --%>
                        <th align="right" ></th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>

        <form id="frmConsulta" action="recalendarizacion.jsp" method="post">
            <input type="hidden" id="folio" name="folio">
            <input type="hidden" id="estatus" name="estatus">
            <%-- <input type="hidden" id="estatus" name="statusDescr"> --%>
            <input type="hidden" id="tipoMov" name="tipoMov">
            <input type="hidden" id="tipoOficio" name="tipoOficio">
            <input type="hidden" id="urlRegresa" name="urlRegresa" value="consultaSolicitudes.jsp">
            <input type="hidden" id="opcion" name="opcion" value="<%=opcion%>" />
            <input type="hidden" id="nuevo" name="nuevo" value="<%=nuevo%>" />
            <input type="hidden" id="ramoCons" name="ramoCons" value="" />
        </form>
        <%if (opcion == 0) {%>
        <script>
            getTablaSolicitudesMovimientos();
        </script>       
        <%} else if (opcion == 1) {%>
        <script>
            getTablaSolicitudesMovimientosByFolio();
        </script>   
        <%} else {%>
        <script>
            getTablaSolicitudesMovimientosByRamo()
        </script>   
        <%}%>  
        <script>
            dataTablePOA("tbl-solMovs");
        </script>
    </div>
    <jsp:include page="template/piePagina.jsp" />
</html>
