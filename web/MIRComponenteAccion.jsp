<%--
    Document   : MIRComponenteAccion
    Created on : Jun 30, 2017, 9:03:58 AM
    Author     : ealonso
--%>

<%@page import="gob.gbc.Framework.KendoDropdown"%>
<%@page import="gob.gbc.Framework.KendoGrid"%>
<%@page import="gob.gbc.Framework.KendoGridColumn"%>
<%@page import="gob.gbc.Framework.KendoGridSchemaModelField"%>
<%@page import="gob.gbc.entidades.RamoPrograma"%> 
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.HTML"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.lang.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>

<!DOCTYPE html>
<html lang="es-mx">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Matriz de Indicadores para Resultados</title>

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

        <script src="Framework/GobBC/Librerias/kendoGrid.js"></script>
        <script src="Framework/GobBC/Librerias/kendoDropDownList.js"></script>
        <script src="Framework/GobBC/Librerias/eventos.js"></script>
        <script src="librerias/MIR/funcionesMIRComponenteAccion.js"></script>
    </head>
    <jsp:include page="template/encabezadoTemporal.jsp" /> 
    <%  boolean bError = false;
        int year = 0;        
        KendoDropdown drop = null;//Importar libreria, declarar
        KendoGrid grid = null;//Importar libreria, declarar
        KendoGridColumn columna = null;
        KendoGridSchemaModelField field = null;
   //     List<KendoGridColumn> columns = new ArrayList();
        List<KendoGridSchemaModelField> fieldList = new ArrayList();
        String errorMsg = "";
        String readContextPath = null;
        String usuario = new String();
        String tipoDependencia = new String();
        String selRamo = new String();
        String selPrograma = new String();
        Utilerias utileria = null;
        
        HashMap<String, Object> data;

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        } else {
            response.sendRedirect("logout.jsp");
        }
        if (request.getParameter("selRamo") != null && !request.getParameter("selRamo").equals("")) {
            selRamo = request.getParameter("selRamo");
        }
        if (request.getParameter("selPrograma") != null && !request.getParameter("selPrograma").equals("")) {
            selPrograma = request.getParameter("selPrograma");
        }
        if (request.isRequestedSessionIdValid()) {
            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }
        } else {
            response.sendRedirect("logout.jsp");
        }
        if (Utilerias.existeParametro("tipoDep", request)) {
            tipoDependencia = request.getParameter("tipoDep");
        }

        request.setCharacterEncoding("UTF-8");

        try {
    %>
    <div Id="TitProcess"><label >Matriz de Indicadores para Resultados<label/></div>
    <div class="col-md-12 ">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-limpiar" onclick="clearFilters()">
                <br/> <small> Limpiar </small>
            </button>
            <button type="button" id="btnEnviarAutorizar" style="display: none" class="btnbootstrap btn-enviar" onclick="enviaMIR()">
                <br/> <small> Enviar </small>
            </button>
            <button type="button" id="btnAceptar" style="display: none" class="btnbootstrap btn-aceptar" onclick="validaMIR();">
                <br/> <small> Validar </small>
            </button>
            <button type="button" id="btnRechazar" style="display: none" class="btnbootstrap btn-rechazar" onclick="rechazaMIR()">
                <br/> <small> Rechazar </small>
            </button>
            <button type="button" class="btnbootstrap btn-reporte" onclick="printRow()">
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-Excel" onclick="excelPrintRow()"> 
                <br/> <small> Excel </small>
            </button>
            <button id="btnRegresar" type="button" class="btnbootstrap btn-atras" onclick="regresarMIRAdmin()">
                <br/> <small> Regresar </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>          
    </div>
    <form id="frmRegresa" method="POST" action="MIR.jsp" accept-charset="UTF-8"/>
    <form id="frmReporte" method="POST" action="ejecutaReporte/ejecutarReporte.jsp" accept-charset="UTF-8"/>
    <form id="frmReporteExcel" method="POST" action="ejecutaReporte/ejecutarReporte.jsp" accept-charset="UTF-8"/>          
    <input type="hidden" id="filename" name="filename" value="rptMIR.jasper"/> 
    <input type="hidden" id="filenamexls" name="filenamexls" value="rptMIRxls.jasper"/> 
    <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
    <input type="hidden" id="selRamo" name="selRamo" value="<%=selRamo%>"/> 
    <input type="hidden" id="selPrograma" name="selPrograma" value="<%=selPrograma%>"/> 
    <input type="hidden" id="estatusSigEnvia" name="estatusSigEnvia" /> 
    <input type="hidden" id="estatusSigRechaza" name="estatusSigRechaza" /> 
    <input type="hidden" id="estatusSigValida" name="estatusSigValida" /> 
    <input type="hidden" id="context" value="<%=request.getContextPath()%>" />
    <input type="hidden" id="servlet" value="/Componente" />
</form>
<div style="width: 100%;margin:0px auto;display: inline-block;" class="center-div">  
    <div class="filters col-md-12">
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">                           
            <%  readContextPath = request.getContextPath() + "/RamoList";
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
                drop.setValue(selRamo);
                drop.setEnable(false);
                drop.setOptionLabel("Todos los ramos...");
            %>                            
            <%@include file="/Framework/GobBC/Controles/kendoDropDown.jsp" %>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <%  readContextPath = request.getContextPath() + "/ProgramaList";
                drop = new KendoDropdown(); //inicializar
                drop.setLabelFor("cmbPrograma"); //Personalizar Kendo DropDownList
                drop.setLabel("Programa: ");
                drop.setName("programaComboBox");
                drop.setDataTextField("programaDesc");
                drop.setDataValueField("programaId");
                drop.setChange("onChangePrograma");
                drop.setReadUrl(readContextPath);
                drop.setParameterMap("parameterMapPrograma");
                drop.setAutoBind(true);
                drop.setValue(selPrograma);
                drop.setEnable(false);
                drop.setOptionLabel("Todos los programas...");
            %>  
            <%@include file="/Framework/GobBC/Controles/kendoDropDown.jsp" %>
        </div>
    </div>
    <div class="col-md-12 margin-zero padding-zero" >
        CAPTURA: <span id="etapaMIR"></span> ESTATUS: <span id="estatusMIR" class="status-text"></span> <span id="enEdicionMIR"></span>
    </div>    
    <div id="obsRechazo" class="alert alert-warning col-md-12" style="display: none">
    </div>
    <div id="divFinProposito" class="col-md-12 margin-zero padding-zero oculto">
        <%      readContextPath = request.getContextPath() + "/Indicador";
            grid = new KendoGrid();
            grid.setName("gridFinProposito");
            grid.setEditFormUrl(" ");
            grid.setEntityName("finProposito");
            grid.setReadUrl(readContextPath + "/ObtenerFinProposito");
            grid.setEditUrl(readContextPath + "/EditRecordsFinProposito");
            grid.setDeleteFunction("validaRequisitosBorrar");
            grid.setOnEdit("onEditInline");
            grid.setParameterMap("parameterMapFinProposito");
            grid.setPrintRow(false);
            grid.setEditableMode("inline");  // popup, inline                                  
            grid.setModelName("mirModel");
            grid.setPageSize(2);
            grid.setGridExcel(false);
            grid.setGridRefresh(false);
            grid.setScrollable(true);
            grid.setSortable(false);
            grid.setSearchable(false);
            grid.setAddModel(true);
            grid.setPageable(false);
            grid.setFilterable(false);
            grid.setAddRow(false);
            grid.setModelId("finProposito");
            grid.setDeleteRow(false);            

            columna = new KendoGridColumn();
            columna.setField("tipoRegistro");
            columna.setTitle("Fin / Propósito");
            columna.setWidth("100px");
            columna.setHidden(false);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("tipoRegistro");
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("year");
            columna.setTitle("Año");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("year");
            field.setType("number");
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("ramo");
            columna.setTitle("Ramo");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("ramo");
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("prg");
            columna.setTitle("Prg");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);            
            field = new KendoGridSchemaModelField();
            field.setName("prg");
            field.setValidationRequired(true);
            grid.fields.add(field);         

            columna = new KendoGridColumn();
            columna.setField("dimension");
            columna.setTitle("Dimension");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);

            columna = new KendoGridColumn();
            columna.setField("finProposito");
            columna.setTitle("Descripción");
            columna.setWidth("30%");
            columna.setHidden(false);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("finProposito");
            field.setEditable(false);
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("indicadorSEI");
            columna.setTitle("Indicador SEI");
            columna.setWidth("20%");
            columna.setAddEditor(true);
            columna.setEditor("indicadorDropDownEditor");
            columna.setHidden(false);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("indicadorSEI");
            field.setEditable(true);
            field.setValidationRequired(false);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("indicadores");
            columna.setTitle("Indicadores");
            columna.setWidth("20%");
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            columna.setHidden(false);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("indicadores");
            field.setEditable(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("medios");
            columna.setTitle("Medios de Verificación");
            columna.setWidth("25%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("medios");
            field.setEditable(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("supuestos");
            columna.setTitle("Supuestos");
            columna.setWidth("25%");
            columna.setHidden(false);  
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("supuestos");
            field.setEditable(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("estatus");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("estatus");
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("etapa");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("etapa");
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("normativo");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("normativo");
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            data = new HashMap<String, Object>();
            data.put("finProposito", "#=finProposito#");
        %>
        <%@include file="/Framework/GobBC/Controles/kendoGridInLine.jsp" %>    
    </div>
    <div id="divComponenteActividad" class="col-md-12 margin-zero padding-zero">
        <%  readContextPath = request.getContextPath() + "/Componente";
            grid = new KendoGrid();
            grid.setName("componenteGrid");
            grid.setEditFormUrl("");
            grid.setEntityName("Componentes / Actividades");
            grid.setEditActionsUrl("/librerias/ajax/MIR/ajaxCapturaComponente.jsp");
            grid.setReadUrl(readContextPath);
            grid.setAddUrl(readContextPath + "/AddRecords");
            grid.setEditUrl(readContextPath + "/EditRecords");
            grid.setDeleteUrl(readContextPath + "/DeleteRecords");
            grid.setAddFunction("addRowInLine(this)");
            grid.setDeleteFunction("validaRequisitosBorrar");
            grid.setOnEdit("onEditInline");
            grid.setParameterMap("parameterMapComponente");
            grid.setDeleteRow(true);
            grid.setPrintRow(false);
            grid.setEditableMode("inline");  // popup, inline                                  
            grid.setModelName("componenteModel");
            grid.setPageSize(5);
            grid.setOnDataBound("componenteOnDataBound");
            grid.setGridExcel(false);
            grid.setGridRefresh(false);
            grid.setAddText("Agregar componente");
            grid.setScrollable(true);
            grid.setSortable(false);
            grid.setAddModel(true);
            grid.setModelId("componente");
            

            columna = new KendoGridColumn();
            columna.setField("renglon");
            columna.setTitle("NO.");
            columna.setWidth("55px");
            columna.setHidden(false);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("renglon");
            grid.fields.add(field);


            columna = new KendoGridColumn();
            columna.setField("strYear");
            columna.setTitle("Año");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("strYear");
            field.setType("number");
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("ramo");
            columna.setTitle("Ramo");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("ramo");
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("prg");
            columna.setTitle("Prg");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);            
            field = new KendoGridSchemaModelField();
            field.setName("prg");
            field.setValidationRequired(true);
            grid.fields.add(field);         

            columna = new KendoGridColumn();
            columna.setField("componente");
            columna.setTitle("Componente");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);

            columna = new KendoGridColumn();
            columna.setField("descr");
            columna.setTitle("Componentes");
            columna.setWidth("30%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("descr");
            field.setEditable(true);
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("indicadorSEI");
            columna.setTitle("Indicador SEI");
            columna.setWidth("20%");
            columna.setAddEditor(true);
            columna.setEditor("indicadorDropDownEditor");
            columna.setHidden(false);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("indicadorSEI");
            field.setEditable(true);
            field.setValidationRequired(false);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("indicadores");
            columna.setTitle("Indicadores");
            columna.setWidth("20%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("indicadores");
            field.setEditable(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("medios");
            columna.setTitle("Medios de Verificación");
            columna.setWidth("25%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("medios");
            field.setEditable(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("supuestos");
            columna.setTitle("Supuestos");
            columna.setWidth("25%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("supuestos");
            field.setEditable(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("estatus");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("estatus");
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("etapa");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("etapa");
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("normativo");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("normativo");
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            data = new HashMap<String, Object>();
            data.put("masterId", "#=componente#");
        %>
        <%@include file="/Framework/GobBC/Controles/kendoGridInLineMaster.jsp" %>                                
        <%  readContextPath = request.getContextPath() + "/Actividad";
            grid = new KendoGrid();
            grid.setName("componenteGrid_#=componente#");
            grid.setEditFormUrl("");
            grid.setEntityName("Actividades");
            grid.setEditActionsUrl("/librerias/ajax/MIR/ajaxCapturaActividades.jsp");
            grid.setReadUrl(readContextPath);
            grid.setAddUrl(readContextPath + "/AddRecords");
            grid.setEditUrl(readContextPath + "/EditRecords");
            grid.setDeleteUrl(readContextPath + "/DeleteRecords");
            grid.setAddFunction("addRowInLine(this)");
            grid.setDeleteFunction("validaRequisitosBorrar");
            grid.setParameterMap("parameterMapActividad");
            grid.setDeleteRow(true);
            grid.setPrintRow(false);
            grid.setEditableMode("inline");  // popup, inline                                  
            grid.setModelName("actividadModel");
            grid.setPageSize(5);
            grid.setOnDataBinding("actividadOnDataBinding");
            grid.setOnDataBound("actividadOnDataBound");           
            grid.setOnEdit("onEditInline");
            grid.setGridExcel(false);
            grid.setGridRefresh(false);
            grid.setAddText("Agregar actividad");
            grid.setScrollable(true);
            grid.setSortable(false);
            grid.setAddModel(true);
            grid.setModelId("actividad");

            columna = new KendoGridColumn();
            columna.setField("renglon");
            columna.setTitle("NO.");
            columna.setWidth("60px");
            columna.setHidden(false);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("renglon");
            field.setEditable(false);
            field.setValidationRequired(false);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("strYear");
            columna.setTitle("Año");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("strYear");
            field.setType("number");
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("ramo");
            columna.setTitle("Ramo");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("ramo");
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("prg");
            columna.setTitle("Prg");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("prg");
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("componente");
            columna.setTitle("Componente");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("componente");
            field.setType("number");
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("actividad");
            columna.setTitle("Actividad");
            columna.setWidth("50px");
            columna.setHidden(true);
            grid.columns.add(columna);


            columna = new KendoGridColumn();
            columna.setField("descr");
            columna.setTitle("Actividades");
            columna.setWidth("30%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("descr");
            field.setType("string");
            field.setEditable(true);
            field.setValidationRequired(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("indicadorSEI");
            columna.setTitle("Indicador SEI");
            columna.setWidth("20%");
            columna.setAddEditor(true);
            columna.setEditor("indicadorDropDownEditor");
            columna.setHidden(false);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("indicadorSEI");
            field.setEditable(true);
            field.setValidationRequired(false);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("indicadores");
            columna.setTitle("Indicadores");
            columna.setWidth("20%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("indicadores");
            field.setEditable(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("medios");
            columna.setTitle("Medios de Verificación");
            columna.setWidth("25%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("medios");
            field.setEditable(true);
            grid.fields.add(field);

            columna = new KendoGridColumn();
            columna.setField("supuestos");
            columna.setTitle("Supuestos");
            columna.setWidth("25%");
            columna.setHidden(false);
            columna.setAddEditor(true);
            columna.setEditor("textEditorMIR");
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("supuestos");
            field.setEditable(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("estatus");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("estatus");
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("etapa");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("etapa");
            field.setValidationRequired(true);
            grid.fields.add(field);
            
            columna = new KendoGridColumn();
            columna.setField("normativo");
            columna.setHidden(true);
            grid.columns.add(columna);
            field = new KendoGridSchemaModelField();
            field.setName("normativo");
            field.setValidationRequired(true);
            grid.fields.add(field);
        %>
        <%@include file="/Framework/GobBC/Controles/kendoGridInLineDetail.jsp" %>                                          
    </div>
</div>             
<div id="mensaje"></div>
<div id="dialog-valida" title="Confirmación"></div>
<div id="dialog-rechazo" title="Motivo del Rechazo"></div>
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
<%                            }
    }
%>     
<script src="librerias/bootstrap/js/bootstrap.min.js"></script>
<script src="librerias/js/telerik/kendo.all.min.js"></script>
<script src="librerias/js/telerik/cultures/kendo.culture.es-ES.min.js"></script>
<script src="librerias/js/telerik/messages/kendo.messages.es-ES.min.js"></script>
<script src="http://kendo.cdn.telerik.com/2017.2.504/js/jszip.min.js"></script> 
<jsp:include page="template/piePagina.jsp" />
</html>
