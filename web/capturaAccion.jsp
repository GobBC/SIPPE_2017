<%-- 
    Document   : capturaAccion
    Created on : Apr 23, 2015, 10:43:10 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.PlantillaBean"%>
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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captura de acciones</title>

    </head>
    <%
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        int year = 0;
        String ramoId = new String();
        String programaId = new String();
        String tipoDependencia = new String();
        int proyectoId = -1;
        int metaId = 0;
        int contPar = 0;
        String obra = new String();
        String arrayProy[] = new String[2];
        String tipoProyecto = new String();
        String rowPar = new String();
        String usuario = new String();
        String obraAccion = new String();
        boolean bandEncabezadoObra = false;
        String encabezadoObra = new String();
        Ramo objRamo = new Ramo();
        Programa objPrograma = new Programa();
        Proyecto objProyecto = new Proyecto();
        Meta objMeta = new Meta();
        List<Accion> accionList = new ArrayList<Accion>();
        List<FuenteFinanciamiento> fuenteFinList = new ArrayList<FuenteFinanciamiento>();
        List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
        PlantillaBean plantillaBean = null;
        int conReg = 0;
        int plantillaNum = 0;

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (request.getParameter("selRamo") != null
                && !request.getParameter("selRamo").equals("")) {
            ramoId = (String) request.getParameter("selRamo");
        }
        if (request.getParameter("selPrograma") != null
                && !request.getParameter("selPrograma").equals("")) {
            programaId = (String) request.getParameter("selPrograma");
        }
        if (request.getParameter("selProyecto") != null
                && !request.getParameter("selProyecto").equals("")) {
            arrayProy = request.getParameter("selProyecto").split(",");
            proyectoId = Integer.parseInt(arrayProy[0]);
            tipoProyecto = arrayProy[1];
        }
        if (ramoId.isEmpty() || programaId.isEmpty() || proyectoId == -1) {
            response.sendRedirect("capturaDefinicionMeta.jsp");
            //response.setHeader("Location", "/capturaDefinicionMeta.jsp");
            return;
        }
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        AccionBean accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(((String) request.getHeader("Host")));
        accionBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        accionBean.resultSQLConecta(tipoDependencia);

        plantillaBean = new PlantillaBean(tipoDependencia);
        plantillaBean.setStrServer(request.getHeader("host"));
        plantillaBean.setStrUbicacion(getServletContext().getRealPath(""));
        plantillaBean.resultSQLConecta(tipoDependencia);

        objRamo = accionBean.getRamoById(ramoId, year, usuario);
        objPrograma = accionBean.getProgramaById(ramoId, programaId, year, usuario);
        objProyecto = accionBean.getProyectoById(ramoId, programaId, proyectoId, year, tipoProyecto);
        objMeta = accionBean.getMetaById(ramoId, programaId, proyectoId, metaId, year, tipoProyecto);
        accionList = accionBean.getAccionesByMeta(year, ramoId, metaId);
        if (objRamo != null) {
            fuenteFinList = accionBean.getFuenteFinanciamiento(year);
            tipoGastoList = accionBean.getTipoGasto(year);
            obra = accionBean.getObraByMeta(year, ramoId, metaId);
            if (obra == null || obra.equals(" ")) {
                obra = "G";
            }
            accionBean.resultSQLDesconecta();
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label>Captura de acciones<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-atras" onclick="redirectDefinicionMeta()">
                <br/> <small> Atr&aacute;s </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div center-req" >
        <div>
            <form id="frmReq" method="POST" action="capturaDefinicionMeta.jsp">
                <input id="selRamo" name="ramo" type="hidden" value="<%= ramoId%>" />
                <input id="selPrograma" name="programa" type="hidden" value="<%= programaId%>" />
                <input id="selProyecto" name="proyecto" type="hidden" value="<%= proyectoId%>" />
                <input id="selMeta" type="hidden" value="<%= metaId%>" />
                <input id="selTipoProy" name="tipoProy" type="hidden" value="<%= tipoProyecto%>" />
                <input id="redirect" name="redirect" type="hidden" value="1"/>
                <input id="descrRamo" name="ramo" type="hidden" value="<%= objRamo.getRamo() + "-" + objRamo.getRamoDescr()%>" />
                <input id="descrPrograma" name="programa" type="hidden" value="<%= objPrograma.getProgramaId() + "-" + objPrograma.getProgramaDesc()%>" />
                <input id="descrProyecto" name="proyecto" type="hidden" value="<%= objProyecto.getTipoProyecto() + objProyecto.getProyectoId() + "-" + objProyecto.getProyecto()%>" />
                <input id="descrMeta" type="hidden" value="<%= objMeta.getMetaId() + "-" + objMeta.getMeta()%>" />
            </form>
            <table id='infoSeccion'>
                <tr>
                    <td><label> Ramo: </label></td>
                    <td><%= objRamo.getRamo() + "-" + objRamo.getRamoDescr()%></td>
                </tr>
                <tr>
                    <td><label> Programa: </label></td>
                    <td><%= objPrograma.getProgramaId() + "-" + objPrograma.getProgramaDesc()%></td>
                </tr>
                <tr>
                    <td><label> Proy./Act.: </label></td>
                    <td><%=  objProyecto.getTipoProyecto() + objProyecto.getProyectoId() + "-" + objProyecto.getProyecto()%></td>
                </tr>
                <tr>
                    <td><label> Meta: </label></td>
                    <td><%= objMeta.getMetaId() + "-" + objMeta.getMeta()%></td>
                </tr>
            </table>
        </div>
        <div id="divAcciones">
            <div id='listAccion'>
                <%
                    for (Accion accion : accionList) {
                        if (accion.getObra() != 0) {
                            encabezadoObra = "Obra";
                        }
                    }

                %>
                <table id='tblAcciones' class="table col-md-12">
                    <thead>
                        <tr>
                            <th>Acci&oacute;n</th>
                            <th>Descripci&oacute;n de la Acci&oacute;n</th>
                            <th><%=encabezadoObra%></th>
                            <th>Plantilla</th>
                             <th>Resultado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Accion accion : accionList) {
                                contPar++;
                                conReg++;
                                if (contPar % 2 == 0) {
                                    rowPar = "rowPar";
                                } else {
                                    rowPar = "";
                                }
                        %>
                        <tr class='<%= rowPar%>'>
                            <td><%= accion.getAccionId()%></td>
                            <td><div title="<%= accion.getAccion()%>"><%= accion.getAccion()%></div></td>
                            <%
                                if (accion.getObra() == 0) {
                                    obraAccion = "";
                                } else {
                                    obraAccion = "" + accion.getObra();
                                }
                            %>
                            <td><%= obraAccion%></td>
                            <td>
                                <%
                                    if (plantillaBean.isPlantillaByRamoProgramaDeptoMetaAccionYear(accion.getRamo(), accion.getPrg(), accion.getDeptoId(), accion.getMeta(), accion.getAccionId(), accion.getYear())) {
                                        plantillaNum = conReg;
                                %>    
                                <input onclick="checkPlantillaNueva('<%=accion.getRamo()%>','<%=accion.getPrg()%>','<%=accion.getDeptoId()%>',<%=accion.getMeta()%>,<%=accion.getAccionId()%>,<%=accion.getYear()%>,<%=conReg%>,1)" id='plantillaId<%=conReg%>' type='radio' name='FFR<%=accion.getDeptoId()%>' class='asigCheck' checked />
                                <%
                                } else {
                                %> 
                                <input onclick="checkPlantillaNueva('<%=accion.getRamo()%>','<%=accion.getPrg()%>','<%=accion.getDeptoId()%>',<%=accion.getMeta()%>,<%=accion.getAccionId()%>,<%=accion.getYear()%>,<%=conReg%>,0)" id='plantillaId<%=conReg%>' type='radio' name='FFR<%=accion.getDeptoId()%>' class='asigCheck'/>
                                <%
                                    }
                                %>
                            </td>
                            <td>
                                <%if(plantillaBean.getResultSQLIsTraeResultadoAccion(accion.getYear(), accion.getRamo().toString(), accion.getMeta(), accion.getAccionId())){%>
                                <input type="radio" id="AccionTraeResultado<%=conReg%>" name="AccionTraeResultado<%=conReg%>"  onclick="checkDefincionAccionTraeResultado('<%=accion.getRamo()%>','<%=accion.getMeta()%>',<%=accion.getAccionId()%>,<%=accion.getYear()%>,<%=conReg%>,1)" checked/>
                            <%}else{%>
                                <input type="radio" id="AccionTraeResultado<%=conReg%>" name="AccionTraeResultado<%=conReg%>"  onclick="checkDefincionAccionTraeResultado('<%=accion.getRamo()%>','<%=accion.getMeta()%>',<%=accion.getAccionId()%>,<%=accion.getYear()%>,<%=conReg%>,0)"/>
                            <%}%>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                <input type='hidden' id='plantillaNum' value='<%=plantillaNum%>'/>
                <script type="text/javascript">
                    $("#tblAcciones tbody tr").click(function(){
                        $(this).addClass('selected').siblings().removeClass('selected'); 
                        getRequerimientos();
                        //getComboFuenteFinanciamiento();
                    });                        
                </script>
            </div>
            <div id="ctrlAcciones" style="margin-top:20px;">
                <center>
                    <%if (obra.equals("A") || obra.equals("G")) {%>
                    <input type="button" id="btnInsertAccion" value="Insertar" onclick="insertarAccion()"/>
                    <input type="button" id="btnDeleteAccion" value="Eliminar" onclick="borrarAccion()"/>
                    <input type="button" id="btnEditAccion" value="Editar" onclick="editarAccion()"/>
                    <input type="button" id="btnCalendar" value="Calendarizar" onclick="calendarizarEstimacion()"/>
                    <%--
                    <input type="button" id="btnFuente" value="Fuente financiamiento" onclick="mostrarFuenteFunanciamiento()"/>
                    <input type="button" id="btnTipoGasto" value="Tipo Gasto" onclick="mostrarTipoGasto()"/>
                    --%>
                    <%} else {%>
                    <input type="button" id="btnEditAccion" value="Consultar" onclick="consultarAccion()"/>
                    <input type="button" id="btnCalendar" value="Calendario" onclick="calendarizarEstimacionConsulta()"/>
                    <%}%>
                </center>    
            </div>
        </div>
        <hr/>
        <div id="selFuenteGasto" style="display: none;">
            <center>
                <table>
                    <tr>
                        <td>
                            <label>Fuente de finaciamiento</label>
                        </td>
                        <td>
                            <label>Fondo-Recurso</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <select id='selFuenteReq' onchange='getFuenteRecurso()'>
                                <option value='-1'>--Seleccione una fuente de financiamiento</option>
                                <%
                                    for (FuenteFinanciamiento fuente : fuenteFinList) {
                                        out.println("<option value='" + fuente.getFuenteId() + "'>"
                                                + fuente.getFuenteId() + "-" + fuente.getFuenteFinanciamiento() + "</option>");
                                    }
                                %>
                            </select>
                        </td>
                        <td>
                            <select id="selFuenteRecurso">
                                <option value="-1">--Seleccione un fondo-recurso--</option>
                            </select>                            
                        </td>
                    </tr>
                </table>
            </center>                
        </div>
        <center>
            <label>Requerimientos</label>
        </center>
        <div id="divRequerimientos">
            <div id="divReq">
                <table id='tblRequerimientos'>
                </table>
            </div>
            <div style="display: none">
                <center>
                    <%
                        if (obra.equals("A") || obra.equals("G")) {
                    %>
                    <div>
                        <input id="botonInsertRequerimiento" type="button" value="Insertar" onclick="insertRequerimiento()"/>
                        <input id="botonMostrarRequerimiento" type="button" value="Editar" onclick="mostrarRequerimiento()"/>
                        <input id="botonBorrarRequerimiento" type="button" value="Eliminar" onclick="borrarRequerimiento()"/>
                        <input id="botonCosntultarRequerimiento" type='button' value='Consultar' onclick='consultarRequerimiento()' style='display:none;' />
                    </div>
                    <%                    } else if (obra.equals("I")) {
                    %>
                    <input type="button" value="Consultar" onclick="consultarRequerimiento()"/>
                    <%                        }
                    %>
                </center>
            </div>
        </div>

        <div id='infoAccion' style="display: none">

        </div>

        <div id="infoRequerimiento" style="display: none">

        </div>

        <div id="estimacionMeta" style="display: none">                            
            <div>

            </div>
            <center>
                <input type="button" id="btnActEst" value="Guardar" onclick="actualizarAccionEstimacion()" />
                <input type="button" id="btnCancelarEst" value="Cancelar" onclick="cerrarAccionEstimacion()" />
                <input type="button" id="btnAceptarEst" value="Aceptar" onclick="cerrarAccionEstimacion()" style="display: none" />
            </center>
        </div>

        <!--<input id="searchInput" value="Type To Filter"/> -->
        <div id='captura-dep' class='captura-lineas' style="display: none;">
            <label> Fuente financiamiento </label>
            <br/>
            <div id='ped-lineas' class='lineas-drop'>

            </div>
            <div id='lineas-ctrl'>
                <input type='button' value='Insertar' onclick="insertarFuentefin()"/>
                <input type='button' value='Borrar' onclick="borrarLinea()"/>
                <input type='button' value='Guardar' onclick="guardarFuentefin()"/>
                <input type='button' value='Cerrar' onclick='ocultarLineaPED()'/>
            </div>
        </div>
        <div id='captura-sect' class='captura-lineas' style="display: none;">
            <label style="bottom: 95px"> Tipo de gasto </label>
            <br/>
            <div id='sectorial-lineas' class='lineas-drop'>                    
                <b>Seleccione una fuente de financiamiento</b>
                <select id="selFuente" onchange="getTipoGasto()">
                    <option value="-1">
                        -- Seleccione Fuente de financiamiento --
                    </option>
                </select>
                <br/>
                <div></div>
            </div>
            <div id='lineas-ctrl'>
                <input type='button' value='Insertar' onclick="insertarTipoGasto()"/>
                <input type='button' value='Borrar' onclick="borrarLinea()" />
                <input type='button' value='Guardar' onclick="guardarTipoDeGasto()"/>
                <input type='button' value='Cancelar' onclick='ocultarLineaSectorial()'/>
            </div>
        </div>
    </div>
    <%
    } else {
    %>
    <script type="text/javascript">
    location.href = '/poa/capturaDefinicionMeta.jsp';
    </script>
    <%                }
    %>

    <%
        if (plantillaBean != null) {
            plantillaBean.resultSQLDesconecta();
        }
    %>
    <div id="mensaje" style="position: relative"></div>
    <jsp:include page="template/piePagina.jsp" />
</html>
