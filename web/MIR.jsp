<%-- 
    Document   : MIR
    Created on : Jun 26, 2017, 8:09:12 AM
    Author     : ealonso
--%>

<%@page import="gob.gbc.sql.ResultSQLCatalogos"%>
<%@page import="gob.gbc.entidades.GraficaMir"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="gob.gbc.Framework.KendoDropdown"%>
<%@page import="gob.gbc.Framework.KendoGrid"%>
<%@page import="gob.gbc.Framework.KendoGridColumn"%>
<%@page import="gob.gbc.entidades.RamoPrograma"%> 
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.HTML"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>

<!DOCTYPE html>
<html lang="es-mx">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de MIR</title>

        <link href="librerias/css/Site.css" type="text/css" rel="stylesheet"/>
        <link href="librerias/css/site.common.css" type="text/css" rel="stylesheet"/>
        <link href="librerias/css/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
        <link href="librerias/css/styles/kendo.bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="librerias/css/font-awesome.min.css" rel="stylesheet" type="text/css" />        
        <link href="librerias/css/kendo.css" rel="stylesheet" type="text/css"/> 
        <link href="librerias/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <script src="librerias/js/jquery/jquery-2.2.3.min.js"></script>
        <script type="text/javascript" src="librerias/js/jquery/jquery-ui.js"></script>
        <script type="text/javascript" src="librerias/js/jquery/jquery.serialize-object.min.js"></script>
        <script src="librerias/js/modernizr.js" type="text/javascript" ></script>
        
        <script src="Framework/GobBC/Librerias/eventos.js"></script>
        <script src="Framework/GobBC/Librerias/kendoDropDownList.js"></script>
        <script src="Framework/GobBC/Librerias/kendoGrid.js"></script>
        <script src="librerias/MIR/funcionesMIR.js"></script>
    </head>
    <jsp:include page="template/encabezadoTemporal.jsp" /> 
    <%
    
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }
        
        boolean bError = false;
        int year = 0;
        
        ResultSQLCatalogos result = null;
        
        KendoDropdown drop = null;//Importar libreria, declarar
        KendoGrid grid = null;//Importar libreria, declarar  
        KendoGridColumn columna = null;   
        List<KendoGridColumn> columns = new ArrayList(); 
        String errorMsg = "";
        String ramoAsignado = new String();
        String readContextPath = null;
        String strClase = "";
        String tipoDependencia = new String();
        String usuario = new String();
        //Utilerias utileria = null;

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        } else {
            response.sendRedirect("logout.jsp");
        }
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        } else {
            response.sendRedirect("logout.jsp");
        }      
        if(session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != ""){
            ramoAsignado = (String) session.getAttribute("ramoAsignado");
        }
        
        request.setCharacterEncoding("UTF-8");

        try {
            
            if(result != null)
                result.resultSQLDesconecta();
            if (errorMsg.compareTo("") != 0) {
                strClase = "alert error";
            }
    %>
    <div Id="TitProcess"><label >Administración de MIR<label/></div>
    <div class="col-md-12 ">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-limpiar" onclick="clearFilters()">
                <br/> <small> Limpiar </small>
            </button>
            <button type="button" class="btnbootstrap btn-agregar oculto" onclick="agregaMIR()">
                <br/> <small> Agregar </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>    
        
    <div style="width: 100%;margin:0px auto;display: inline-block;" class="center-div">   
        <form id="frmDetalle" method="POST" action="MIRComponenteAccion.jsp" accept-charset="UTF-8">
            <input type="hidden" id="filename" name="filename" value="rptMIR.jasper"/> 
            <input type="hidden" id="filenamexls" name="filenamexls" value="rptMIRxls.jasper"/> 
            <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
            <input type="hidden" id="ramoAsignado" name="ramoAsignado" value="<%=ramoAsignado%>"/> 
            <input type="hidden" id="selRamo" name="selRamo" /> 
            <input type="hidden" id="selPrograma" name="selPrograma"/> 
            <input type="hidden" id="selStatus" name="selStatus"/> 
            <input type="hidden" id="politicaPublica" name="politicaPublica"/> 
            
            <div class="filters col-md-12">
                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">                           
                    <%  readContextPath = request.getContextPath()+"/RamoList";
                        drop = new KendoDropdown(); //inicializar
                        drop.setLabelFor("cmbRamo"); //Personalizar Kendo DropDownList
                        drop.setLabel("Ramo: ");
                        drop.setName("ramoComboBox");
                        drop.setDataTextField("ramoDescr");
                        drop.setDataValueField("ramo");
                        drop.setChange("onChangeRamo");
                        drop.setReadUrl(readContextPath);
                        drop.setParameterMap("parameterMapRamo"); 
                        drop.setAutoBind(true);                      
                        drop.setValue(ramoAsignado);
                        drop.setOptionLabel("Todos los ramos...");                        
                    %>                            
                    <%@include file="/Framework/GobBC/Controles/kendoDropDown.jsp" %>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                    <%  readContextPath = request.getContextPath()+"/ProgramaList";
                        drop = new KendoDropdown(); //inicializar
                        drop.setLabelFor("cmbPrograma"); //Personalizar Kendo DropDownList
                        drop.setLabel("Programa: ");
                        drop.setName("programaComboBox");
                        drop.setDataTextField("programaDesc");
                        drop.setDataValueField("programaId");
                        drop.setChange("onChangePrograma");
                        drop.setReadUrl(readContextPath);
                        drop.setParameterMap("parameterMapPrograma");
                        drop.setOptionLabel("Todos los programas...");
                    %>  
                    <%@include file="/Framework/GobBC/Controles/kendoDropDown.jsp" %>
                </div>
            </div>                
            <div class="col-md-12 col-sm-12 col-xs-12 margin-zero padding-zero" id="graficaMir">   </div>   
            <div style="clear:both"></div>
            <div class="col-md-12 margin-zero padding-zero ">
                <%  readContextPath = request.getContextPath()+"/MIR";
                    grid = new KendoGrid();
                    grid.setName("mirGrid");
                    grid.setEditFormUrl("Edits/mirEdit.jsp");
                    grid.setEntityName("MIR por Ramo-Programa");
                    grid.setEditActionsUrl("/librerias/ajax/MIR/ajaxCapturaMIR.jsp");
                    grid.setReadUrl(readContextPath);     
                    grid.setGridExcel(false);
                    grid.setGridRefresh(false); 
                    grid.setSortable(true);
                    grid.setPageSize(5);

                    grid.setParameterMap("parameterMapMIR");
                    grid.setAddRow(false);
                    grid.setEditRow(false);
                    grid.setDeleteRow(false);
                    grid.setPrintRow(true);

                    grid.setCustomCol1(true);
                    grid.setCustomCol1Name("editarDetalle");
                    grid.setCustomCol1Function("editarDetalle");
                    grid.setCustomCol1Icon("<span class='fa fa-pencil grid-command-item' title='Editar detalle'/>");


                    columna = new KendoGridColumn();
                    columna.setField("mir");
                    columna.setTitle("NO.");
                    columna.setWidth("50px");
                    columna.setHidden(true);
                    grid.columns.add(columna);

                    columna = new KendoGridColumn();
                    columna.setField("strYear");
                    columna.setTitle("Año");
                    columna.setWidth("50px");
                    columna.setHidden(true);
                    grid.columns.add(columna);

                    columna = new KendoGridColumn();
                    columna.setField("ramo");
                    columna.setTitle("Ramo");
                    columna.setWidth("50px");
                    columna.setHidden(true);
                    grid.columns.add(columna);

                    columna = new KendoGridColumn();
                    columna.setField("ramoDescr");
                    columna.setTitle("Descripción de Ramo");
                    columna.setWidth("30%");
                    columna.setHidden(false);
                    grid.columns.add(columna);

                    columna = new KendoGridColumn();
                    columna.setField("prg");
                    columna.setTitle("Prg");
                    columna.setWidth("50px");
                    columna.setHidden(true);
                    grid.columns.add(columna);                         

                    columna = new KendoGridColumn();
                    columna.setField("prgDescr");
                    columna.setTitle("Descripción de Programa");
                    columna.setWidth("35%");
                    columna.setHidden(false);
                    grid.columns.add(columna);

                    columna = new KendoGridColumn();
                    columna.setField("fechaRegistro");
                    columna.setTitle("Registro");
                    columna.setWidth("15%");
                    columna.setHidden(false);
                    grid.columns.add(columna);
                    
                    columna = new KendoGridColumn();
                    columna.setField("politicaPublica");
                    columna.setTitle("FPP");
                    columna.setWidth("50px");
                    columna.setHidden(false);
                    grid.columns.add(columna);

                    columna = new KendoGridColumn();
                    columna.setField("statusIniDescr");
                    columna.setTitle("Inicial");
                    columna.setWidth("150px");
                    columna.setHidden(false);
                    grid.columns.add(columna);      

                    columna = new KendoGridColumn();
                    columna.setField("statusPosDescr");
                    columna.setTitle("Posterior");
                    columna.setWidth("150px");
                    columna.setHidden(false);
                    grid.columns.add(columna);                      
                %>
                <%@include file="/Framework/GobBC/Controles/kendoGrid.jsp" %>               
            </div>
        </form>
    </div>              
    <input type="hidden" id="context" value='<%=request.getContextPath()%>' />
    <div id="mensaje"></div>
    <%
        } catch (Exception ex) {
            bError = true;
            Bitacora bitacora = new Bitacora();
            bitacora.setITipoBitacora(Bitacora.TIPO_ERROR);
            bitacora.setStrInformacion(ex, request.getServletPath());
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.grabaBitacora();
        } finally {
            if (bError) {
    %>
    <%@include file="/error.jsp"%>
    <%     }
        }
    %>     
    <script src="librerias/bootstrap/js/bootstrap.min.js"></script>
    <script src="librerias/js/telerik/kendo.all.min.js"></script>
    <script src="librerias/js/telerik/cultures/kendo.culture.es-ES.min.js"></script>
    <script src="librerias/js/telerik/messages/kendo.messages.es-ES.min.js"></script>
    <script src="http://kendo.cdn.telerik.com/2017.2.504/js/jszip.min.js"></script>    
    <jsp:include page="template/piePagina.jsp" />
</html>
