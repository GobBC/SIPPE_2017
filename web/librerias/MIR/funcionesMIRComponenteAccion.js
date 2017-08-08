/* 
 * Document   : funcionesMIRComponenteAccion
 * Created on : Jul 12, 2017, 16:10:27 PM
 * Author     : ealonso
 */



$(function () {

    cargaDatosInicioAjax();

    var grid = $("#componenteGrid");                 
    grid.find(".k-grid-toolbar").on("click", ".k-pager-refresh", function (e) {
            e.preventDefault();
            grid.data("kendoGrid").dataSource.read();
        });  

    $("#dialog-rechazo").dialog({
        autoOpen: false,
        height: 205,
        width: 405,
        draggable: false,
        modal: true,
        buttons: {
            "Aceptar": function() {
                rechazaMIRAjax();
            },
            "Cancelar": function() {
                $(this).dialog("close");
            }
        }, close: function() {
            $(this).dialog("close");
        }
    });

    $("#dialog-valida").dialog({
        autoOpen: false,
        height: 155,
        width: 255,
        draggable: false,
        modal: true,
        buttons: {
            "Aceptar": function() {
                validaMIRAjax();
            },
            "Cancelar": function() {
                $(this).dialog("close");
            }
        }, close: function() {
            $(this).dialog("close");
        }
    });

 });    

var textEditorInitialize = function(container, options) {
    $('<textarea name="' + options.field + '" style="width: ' + container.width() + 'px;height:' + container.height() + 'px" />')
    .appendTo(container);
};

function clearFilters() {
    $("#ramoComboBox").val("").data("kendoDropDownList").text("");
    $("#programaComboBox").val("").data("kendoDropDownList").text("");
    $("#ramoComboBox").val("").data("kendoDropDownList").value("");
    $("#programaComboBox").val("").data("kendoDropDownList").value("");
    $("#ramoComboBox").val("").data("kendoDropDownList").trigger("change");  
    $("#programaComboBox").val("").data("kendoDropDownList").trigger("change"); 
}

function onChangePrograma(e){
    var drop = $("#ramoComboBox").data("kendoDropDownList");
    var grid = $("#componenteGrid").data("kendoGrid");
    refreshDrop(drop);
    refreshGrid(grid);           
}  

function onChangeRamo(e){
    var drop = $("#programaComboBox").data("kendoDropDownList");
    var grid = $("#componenteGrid").data("kendoGrid");
    refreshDrop(drop);
    refreshGrid(grid);
}  

function parameterMapRamo(options, operation){
    return {programa: $("#programaComboBox").val(), options: kendo.stringify(options)}            
}

function parameterMapPrograma(options, operation){
    return {ramo: $("#ramoComboBox").val(), options: kendo.stringify(options)}            
}

function parameterMapFinProposito(options, operation){
    var output = null;
    switch(operation){
        case "read":
           options.search = $("#componenteGrid").find(".grid-toolbar-search").val();
           output = {ramo: $("#ramoComboBox").val(), programa: $("#programaComboBox").val(), options: kendo.stringify(options)};     
           break;
        case "update":
           //output = {ramo: $("#ramoComboBox").val(),indicador:kendo.stringify(options.indicadorId) , programa: $("#programaComboBox").val(),options: kendo.stringify(options)};
           output = {ramo: $("#ramoComboBox").val(),indicadorSEI:kendo.stringify(options.indicadorSEI) , programa: $("#programaComboBox").val(),options: kendo.stringify(options)};
           break;
    }
    return output;
}

function parameterMapComponente(options, operation){
   var output = null;
   switch (operation){
       case "read":
           options.search = $("#componenteGrid").find(".grid-toolbar-search").val();
           output = {ramo: $("#ramoComboBox").val(), programa: $("#programaComboBox").val(), options: kendo.stringify(options)};     
           break;
       case "create":
          // debugger;
           output = {ramo: $("#ramoComboBox").val(), programa: $("#programaComboBox").val(),descr: options.descr};     
           break;
       case "update":
          // debugger;
           output = {entidad: kendo.stringify(options)};
           break;
       case "destroy":
         //  debugger;
           output = {entidad: kendo.stringify(options)};
       break;
   }
        
   return output;
}      

function parameterMapActividad(options, operation){
   //debugger;
   var output = null;
   switch (operation){
       case "read":             
           options.search = $("#componenteGrid").find(".grid-toolbar-search").val();              
           output = {ramo: $("#ramoComboBox").val(), programa: $("#programaComboBox").val(), options: kendo.stringify(options)}            
           break;
       case "create":
           output = {ramo: $("#ramoComboBox").val(), programa: $("#programaComboBox").val(),componente:this.options.read.data.masterId, descr: options.descr}            
           break;
       case "update":
           output = {entidad: kendo.stringify(options)};
           break;
       case "destroy":
           output = {entidad: kendo.stringify(options)};
       break;
   }         
   return output;
} 

function validaRequisitosBorrar(e){
    e.preventDefault();
    var row = this;
    var servlet;
    var data = kendo.stringify(this.dataItem($(e.currentTarget).closest("tr")));
    var actividad = this.dataItem($(e.currentTarget).closest("tr"));
    if(typeof actividad.actividad !== 'undefined')
        servlet = '/Actividad';
    else
        servlet = '/Componente';
    var grid = $(e.currentTarget).closest("[data-role=grid]");
    $.ajax({
        type: 'GET',
        url: $('#context').val() + servlet + '/validaRequisitosBorrar',
        datatype: 'json',
        data: {
            entidad: data,
            ramo : $("#ramoComboBox").val(),
            programa : $('#programaComboBox').val()
        },
        success: function(response) {   
            if(response.map.exito)
                $.Framework.hacerAccionConConfirmacion({
                    content: response.map.mensaje,
                    funcionSi: function () {
                        $(this).closest('.k-window-content').data('kendoWindow').close();
                        deleteRowInLine(e,row);
                    }
                });
            else{
                if(response.map.isNormativo){    
                    deleteRowInLine(e,row);
                }else{
                    $.Framework.showNotification({
                        text: response.map.mensaje,
                        type: 'warning',
                        duration: 4500
                    });
                }
            }
        }
    });
}

function printRow(e){
    //  $("#selRamo").val(ramoVal);
    //  $("#filename").val("MIR/rptMIR.jasper");
    //  $("#reporttype").val("pdf");
    // $("#frmReporte").submit();  

    var filename = $("#filename").val();
    var reporttype = "pdf";
    var programaVal = $("#selPrograma").val();
    var ramoVal = $("#selRamo").val();

    window.open('ejecutaReporte/ejecutarReporte.jsp?filename=' + filename + '&reporttype=' + reporttype + '&selPrograma=' + programaVal + '&selRamo=' + ramoVal + '');
}
function excelPrintRow(e){
    var filename = $("#filenamexls").val();
    var reporttype = "xls";
    var ramoVal = $("#selRamo").val();;
    var programaVal = $("#selPrograma").val();                
    window.open('ejecutaReporte/ejecutarReporte.jsp?filename=' + filename + '&reporttype=' + reporttype + '&selPrograma=' + programaVal + '&selRamo=' + ramoVal + '');
}

function regresarMIRAdmin() {
    $("#frmRegresa").submit();
}

function onEditInlineMIR(e){
    e.preventDefault();

    var grid = e.sender;
    var columns = grid.columns;
    
    var row = e.sender.editable.element;
    var editColumn = 0;

    if (grid._editMode() != "inline")
        return false;
    if(row.hasClass('k-master-row')){
        editColumn = 1;
    }
    var cellEdit = row.children().eq(editColumn);
    var cellDelete = row.children().eq(editColumn+1);
    
    cellEdit.html('<a class="k-grid-update botonGrid columna-especial" title="Guardar" href="#"><span class="fa fa-floppy-o"></span></a>').removeClass("k-button-icontext");
    cellDelete.html('<a class="k-grid-cancel botonGrid columna-especial" title="Cancelar" href="#"><span class="fa fa-ban"></span></a>').removeClass("k-button-icontext");

//    if($("#selStatus").val()== "2"){
//        var colIndexDescr = this.wrapper.find(".k-grid-header [data-field=" + "descr" + "]").index();
//        var indexCell = e.container.context.cellIndex;
//        //var grid = $('#BTSession').data('kendoGrid');
//        if (e.model.id) { // when Editing the id is defined
//            if (indexCell != 'undefined' ){ //&& grid.columns[indexCell].title == "Description") {
//                grid.closeCell();
//            }
//        }
//    }
}
function componenteOnDataBound(arg) {
    this.expandRow(this.tbody.find("tr.k-master-row").first());
    agregarTooltips();
}      

function actividadOnDataBound(e) {
    agregarTooltips();
   // permisosEdicion();
//    var grid = e.sender.wrapper;
//    var row = e.sender.editable.element;
//    var editColumn = 0;
//
//    if (grid._editMode() != "inline")
//        return false;
//    
//    if(row.hasClass('k-master-row')){
//        editColumn = 1;
//    }
//    var cellEdit = row.children().eq(editColumn);
//    
//    cellEdit.html('<a class="k-grid-update botonGrid columna-especial" title="Guardar" href="#"><span class="fa fa-floppy-o"></span></a>').removeClass("k-button-icontext");
//    
}  

function actividadOnDataBinding(e){
    permisosEdicion();
}
function agregarTooltips(){
        $('.k-grid-edit').attr('title', 'Editar');
        $('.k-grid-delete').attr('title', 'Borrar');
        $('.k-grid-delete-row').attr('title', 'Borrar');
        $('.k-grid-delete-rowmaster').attr('title', 'Borrar');
}

function permisosEdicion(){
    if($("#componenteGrid").hasClass('bloquearEdicion')){
        $('.container-grid-toolbar').each(function() {
            $('.container-grid-toolbar').has('span.fa-plus-circle').addClass('nuevo');
        });
    } else {
        $('.container-grid-toolbar').each(function() {
            $('.container-grid-toolbar').has('span.fa-plus-circle').removeClass('nuevo');
        });
    }
//    if($("#divFinProposito").hasClass('bloquearEdicion')){
//        $('.container-grid-toolbar').each(function() {
//            $('.container-grid-toolbar').has('span.fa-plus-circle').addClass('nuevo');
//        });
//    } else {
//        $('.container-grid-toolbar').each(function() {
//            $('.container-grid-toolbar').has('span.fa-plus-circle').removeClass('nuevo');
//        });
//    }
}

//FLUJO DE AUTORIZACION
function enviaMIR() {
    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    var estatusSiguiente = $("#estatusSigEnvia").val();

    $.ajax({
        type: "POST",
        url: "librerias/ajax/MIR/ajaxAutorizacionMIR.jsp", 
        dataType: "html", 
        data: { accion: 1,
                selRamo: ramo,
                selPrograma: programa,
                estatusSiguiente: estatusSiguiente}, 
        success: function(response) {
            var respuesta = response;
            if (respuesta != "") {
                var obj = jQuery.parseJSON(response);
                $.Framework.showNotification({
                    text: obj.mensaje,
                    type: obj.exito ? 'success' : 'warning',
                    duration: obj.exito ? 4500 : 6000
                });
                if(obj.exito){
                    cargaDatosInicioAjax();
                }
            }
        }
    });
}

function validaMIR() {
    getPopUpValida();
}

function validaMIRAjax() {
    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    var estatusSiguiente = $("#estatusSigValida").val();

    $.ajax({
        type: "POST",
        url: "librerias/ajax/MIR/ajaxAutorizacionMIR.jsp", 
        dataType: "html", 
        data: { accion: 2,
                selRamo: ramo,
                selPrograma: programa,
                estatusSiguiente: estatusSiguiente}, 
        success: function(response) {
            var respuesta = response;
            if (respuesta != "") {
                var obj = jQuery.parseJSON(response);
                $.Framework.showNotification({
                    text: obj.mensaje,
                    type: obj.exito ? 'success' : 'warning',
                    duration: obj.exito ? 4500 : 6000
                });
                if(obj.exito){
                    cargaDatosInicioAjax();
                }
            }
        }
    });
    $('#dialog-valida').dialog("close");
}

function rechazaMIR() {                
    getPopUpMotivo();                
}

function rechazaMIRAjax() {
    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    var estatusSiguiente = $("#estatusSigRechaza").val();
    var motivo = $("#txt-area-motivo").val();

    $.ajax({
        type: "POST",
        url: "librerias/ajax/MIR/ajaxAutorizacionMIR.jsp", 
        dataType: "html", 
        data: { accion: 3,
                selRamo: ramo,
                selPrograma: programa,
                estatusSiguiente: estatusSiguiente,
                observacion: motivo}, 
        success: function(response) {
            var respuesta = response;
            if (respuesta != "") {
                var obj = jQuery.parseJSON(response);
                $.Framework.showNotification({
                    text: obj.mensaje,
                    type: obj.exito ? 'success' : 'warning',
                    duration: obj.exito ? 4500 : 6000
                });
                if(obj.exito){
                    cargaDatosInicioAjax();
                }
            }
        }
    });

    $('#dialog-rechazo').dialog("close");
}

function getPopUpMotivo() {
    var popUp = "";
    $('#dialog-rechazo').html("");

    popUp = "<textarea id='txt-area-motivo' class='no-enter' maxlength='2000'></textarea>";

    $('#dialog-rechazo').html(popUp);
    $('#dialog-rechazo').dialog("open");

    $('.no-enter').bind('keypress', function(e){
        if(e.keyCode == 13)
        {
           return false;
        }
     }); 
}

function getPopUpValida() {
    var popUp = "";
    $('#dialog-valida').html("");

    popUp = "<label>La MIR será validada.</label>";

    $('#dialog-valida').html(popUp);
    $('#dialog-valida').dialog("open");

    $('.no-enter').bind('keypress', function(e){
        if(e.keyCode == 13)
        {
           return false;
        }
     }); 
}

function cargaDatosInicioAjax() {
    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    $("#obsRechazo").html("");
    $("#obsRechazo").hide();

    $.ajax({
        type: "POST",
        url: "librerias/ajax/MIR/ajaxAutorizacionMIR.jsp", 
        dataType: "html", 
        data: { accion: 4,
                selRamo: ramo,
                selPrograma: programa}, 
        success: function(response) {
            var respuesta = response;
            if (respuesta != "") {
                var obj = jQuery.parseJSON(response);
                $("#estatusSigEnvia").val(obj.estatusSigEnvia);
                $("#estatusSigRechaza").val(obj.estatusSigRechaza);
                $("#estatusSigValida").val(obj.estatusSigValida);
                
                if(obj.bEnviar){
                    $("#btnEnviarAutorizar").show();
                } else {
                    $("#btnEnviarAutorizar").hide();
                }
                
                if(obj.bValidarRechazar){
                    $("#btnRechazar").show();
                    $("#btnAceptar").show();
                } else {
                    $("#btnRechazar").hide();
                    $("#btnAceptar").hide();
                }
                
                if(obj.obsRechazo != ""){
                    $("#obsRechazo").show();
                    $("#obsRechazo").html("<strong>MOTIVO DEL RECHAZO: </strong> "+obj.obsRechazo);
                }
                var etapaMIR = $("#etapaMIR");
                switch(obj.etapaMIR) {
                    case 1: etapaMIR.html("<strong>INICIAL </strong>");
                        break;
                    case 2: etapaMIR.html("<strong>POSTERIOR </strong>");
                        break;
                    case 3: etapaMIR.html("<strong>FINALIZADA </strong>");
                        break;
                    case 4: etapaMIR.html("<strong>CERRADA</strong>");
                        break;
                }
                
                var estatus = $("#estatusMIR");
                var enEdicionMIR = $("#enEdicionMIR");
                switch(obj.estatusMIR) {
                    case 1: estatus.html("BORRADOR");
                            estatus.addClass("estatus-borrador");
                            enEdicionMIR.html("<i class='fa fa-unlock' aria-hidden='true'></i>");
                        break;
                    case 2: estatus.removeClass("estatus-borrador");
                            estatus.removeClass("estatus-rechazado");
                            estatus.html("ENVIADA");                            
                            estatus.addClass("estatus-enviado");
                            enEdicionMIR.html("<i class='fa fa-lock' aria-hidden='true'></i>");
                        break;
                    case 3: estatus.removeClass("estatus-enviado");
                            estatus.html("RECHAZADA");                            
                            estatus.addClass("estatus-rechazado");
                            enEdicionMIR.html("<i class='fa fa-unlock' aria-hidden='true'></i>");
                        break;
                    case 4: estatus.removeClass("estatus-enviado");
                            estatus.html("BORRADOR");                            
                            estatus.addClass("estatus-borrador");
                            enEdicionMIR.html("<i class='fa fa-unlock' aria-hidden='true'></i>");
                        break;
                    case 5: estatus.removeClass("estatus-borrador");
                            estatus.removeClass("estatus-rechazado");
                            estatus.html("ENVIADA");                            
                            estatus.addClass("estatus-enviado");
                            enEdicionMIR.html("<i class='fa fa-lock' aria-hidden='true'></i>");
                        break;
                    case 6: estatus.removeClass("estatus-enviado");
                            estatus.html("RECHAZADA");                            
                            estatus.addClass("estatus-rechazado");
                            enEdicionMIR.html("<i class='fa fa-unlock' aria-hidden='true'></i>");
                        break;
                    case 7: estatus.removeClass("estatus-enviado");
                            estatus.html("VALIDADA");                            
                            estatus.addClass("estatus-validado");
                            enEdicionMIR.html("<i class='fa fa-lock' aria-hidden='true'></i>");
                        break;
                }

                if(obj.bEditarMIR){
                    $("#componenteGrid").removeClass('bloquearEdicion');
                    $("#gridFinProposito").removeClass('bloquearEdicion');                    
                } else {
                    $("#componenteGrid").addClass('bloquearEdicion');
                    $("#gridFinProposito").addClass('bloquearEdicion');
                }
                
                if(parseInt(obj.estatusSigEnvia)>4){
                    $("#divFinProposito").removeClass('oculto');
                } else {
                    $("#divFinProposito").addClass('oculto');
                }
            }
        }
    });                
}

function indicadorDropDownEditor(container, options) {
    var soloLectura = false;   
    var labelText = "";
    //CAPTURA DE INDICADORES, IF(ETAPA == 1 || (ETAPA>1 && ROL!= NOR && (ESTATUS = 5 || ESTATUS == 7)))
    if(options.field == "indicadorSEI"){
        if(options.model.etapa == "1" || options.model.etapa == "" || ( parseInt(options.model.etapa) > 1 && options.model.normativo != "true" && (options.model.estatus == "5" || options.model.estatus == "7"))) {
            soloLectura = true;
            labelText = options.model.supuestos;
        }
    }
    
    if(soloLectura){
         labelText = labelText == undefined ? "" : labelText;
        $('<label name="' + options.field + '" data-bind="value: ' + options.field + '" style="width: ' + (container.width() - 8)  + 'px; font-size: 11px; font-weight: 200; color: #333;  !important" maxlength="150">'+labelText+'</label>').appendTo(container);
    } else {   
        $('<input data-text-field="indicadores" data-value-field="indicadorSEI" data-bind="value:' + options.field + '"/>')
        .appendTo(container)
        .kendoDropDownList({
            valuePrimitive : true,
            dataTextField: "indicadorSEI",
            dataValueField: "indicadorSEI",
            optionLabel: "Seleciona un indicador",
            noDataTemplate: 'No hay indicadores asiagnados',
            change : getInformacionIndicador,
            dataSource: {
                schema: { data: 'Data', total: 'Total'}, 
                transport: {
                    read :{
                        url : $('#context').val() + "/Indicador/ConsultarIndicadores",
                        dataType : 'json',
                        data : {
                            ramo : $("#selRamo").val(),
                            programa : $("#selPrograma").val(),
                            dimension: options.model.dimension
                        }
                    }
                }
            }
        });
    }
}

function getInformacionIndicador(){
    $.ajax({
        type: "GET",
        url: $('#context').val() +'/Indicador/GetInfomacionIndicador', 
        dataType: "html", 
        data: { 
            indicadorSEI: this.value(),
            ramo: $("#selRamo").val(),
            programa: $("#selPrograma").val()}, 
        success: function(response) {
            var obj = jQuery.parseJSON(response);
            $('textarea[name=indicadores]').val(obj.indicadores);
            $('textarea[name=medios]').val(obj.medios);
        },
        error: function(response){
            $.Framework.showNotification({
                text: 'Ocurrió un error al obtener la información del indicador',
                type: 'error',
                duration: 4500
            });
        }
    });
}
function textEditorMIR(container, options) {
    var soloLectura = false;   
    var labelText = "";
    //DESCRIPCION, ESTE EDITOR SE OCULTA CUANDO (ROL!= NOR && (ESTATUS = 2 ||ESTATUS >= 4))
    if(options.field == "descr"){
        if(options.model.normativo != "true" && (options.model.estatus == "2" ||parseInt(options.model.estatus)>=4 )){
            soloLectura = true;
            labelText = options.model.descr;
        }
    }
    //CAPTURA DE INDICADORES, IF(ETAPA == 1 || (ETAPA>1 && ROL!= NOR && (ESTATUS = 5 || ESTATUS == 7)) || (ETAPA>1 && FPP))
    if(options.field == "indicadores" || options.field == "medios"){
        if(options.model.etapa == "1" || 
            options.model.etapa == "" || 
            (parseInt(options.model.etapa) > 1 && options.model.normativo != "true" && (options.model.estatus == "5" || options.model.estatus == "7")) || 
            (parseInt(options.model.etapa) > 1 && options.model.politicaPublica == "true") || 
            (options.model.indicadorSEI != "" && options.model.indicadorSEI != undefined)) {
            soloLectura = true;
            labelText = options.field == "indicadores" ? options.model.indicadores : options.model.medios ;
            
        }
    }
    //CAPTURA DE INDICADORES, IF(ETAPA == 1 || (ETAPA>1 && ROL!= NOR && (ESTATUS = 5 || ESTATUS == 7)))
    if(options.field == "supuestos"){
        if(options.model.etapa == "1" || options.model.etapa == "" || ( parseInt(options.model.etapa) > 1 && options.model.normativo != "true" && (options.model.estatus == "5" || options.model.estatus == "7"))) {
            soloLectura = true;
            labelText = options.model.supuestos;
        }
    }
    
    if(soloLectura){
         labelText = labelText == undefined ? "" : labelText;
        $('<label name="' + options.field + '" data-bind="value: ' + options.field + '" style="width: ' + (container.width() - 8)  + 'px; font-size: 11px; font-weight: 200; color: #333;  !important" maxlength="150">'+labelText+'</label>').appendTo(container);
    } else {
        $('<textarea name="' + options.field + '" data-bind="value: ' + options.field + '" style="width: ' + (container.width() - 8)  + 'px; font-size: 11px  !important" maxlength="150"></textarea>').appendTo(container);
    }      
}    
