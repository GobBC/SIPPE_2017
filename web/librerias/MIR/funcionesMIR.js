/* 
 * Document   : funcionesMIR
 * Created on : Jul 12, 2017, 14:39:12 PM
 * Author     : ealonso
 */

$(document).ready(function(){
    getGraficaMir($('#ramoAsignado').val());
});


function getGraficaMir(ramoInicial){
    var ramo;
    if(typeof ramoInicial === 'undefined'){
        ramo = $('#ramoComboBox').val();
    }else{
        ramo = ramoInicial;
    }
    
    $.ajax({
        type: "GET",
        url: $("#context").val() + "/MIR/GetGraficaMir", 
        dataType: "html", 
        data: { 
                ramo: ramo,
                programa:  $('#programaComboBox').val()
            },
        success: function(response) {
            if($('#ramoComboBox').val() !== "" && $('#programaComboBox').val() !== ""){                
                $('#graficaMir').addClass('hidden');
            }else{
                $('#graficaMir').removeClass('hidden');
                $("#graficaMir").html(response);
                $('[data-toggle="tooltip"]').tooltip();
            }
        },
        error:function(response){
            $.Framework.showNotification({
                text: "Ocurrió un error al obtener la gráfica de la mir",
                type: 'error',
                duration: 4000
            });
        }
    });
}


$(function () {
    var grid = $("#mirGrid");
    grid.find(".k-grid-toolbar").on("click", ".k-pager-refresh", function (e) {
        e.preventDefault();
        grid.data("kendoGrid").dataSource.read();
    });	    
});            
            
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
    var grid = $("#mirGrid").data("kendoGrid");
    refreshDrop(drop);
    refreshGrid(grid);    
    getGraficaMir();
}  

function onChangeRamo(e){
    var drop = $("#programaComboBox").data("kendoDropDownList");
    var grid = $("#mirGrid").data("kendoGrid");
    refreshDrop(drop);
    refreshGrid(grid);
    getGraficaMir();
}  

function onChangeRamoEdit(e){
    var drop = $("#prg").data("kendoDropDownList");
    refreshDrop(drop);
}

function onChangeProgramaEdit(e){
    var drop = $("#ramo").data("kendoDropDownList");
    refreshDrop(drop);
}

function parameterMapRamo(options, operation){
    return {programa: $("#programaComboBox").val(), options: kendo.stringify(options)}            
}

function parameterMapPrograma(options, operation){
    return {ramo: $("#ramoComboBox").val(), options: kendo.stringify(options)}            
}

function parameterMapRamoEdit(options, operation){         
    return {programa: $("#prg").val(),options: kendo.stringify(options)}            
}

function parameterMapProgramaEdit(options, operation){
    //debugger;
    return {ramo: $("#ramo").val(), options: kendo.stringify(options)}            
}

function parameterMapMIR(options, operation){
    // debugger;
    options.search = $("#mirGrid").find(".grid-toolbar-search").val();
    return {ramo: $("#ramoComboBox").val(), programa: $("#programaComboBox").val(), options: kendo.stringify(options)}            
}       
function printRow(e){
    e.preventDefault();
    var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
    var filename = $("#filename").val();
    var reporttype = "pdf";
    var programaVal = dataItem["prg"].toString();
    var yearVal = dataItem["strYear"].toString();
    var ramoVal = dataItem["ramo"].toString();

    window.open('ejecutaReporte/ejecutarReporte.jsp?filename=' + filename + '&reporttype=' + reporttype + '&year=' + yearVal + '&selPrograma=' + programaVal + '&selRamo=' + ramoVal + '');
}
function excelPrintRow(e){
    e.preventDefault();
    var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
    var filename = $("#filenamexls").val();
    var reporttype = "xls";
    var programaVal = dataItem["prg"].toString();
    var yearVal = dataItem["strYear"].toString();
    var ramoVal = dataItem["ramo"].toString();

    window.open('ejecutaReporte/ejecutarReporte.jsp?filename=' + filename + '&reporttype=' + reporttype + '&year=' + yearVal + '&selPrograma=' + programaVal + '&selRamo=' + ramoVal + '');
}

function editarDetalle(e){
    var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
    var programaVal = dataItem["prg"].toString();
    var ramoVal = dataItem["ramo"].toString();
    var statusVal = dataItem["status"];

    $.ajax({
        type: "POST",
        url: "librerias/ajax/MIR/ajaxCapturaMIR.jsp", 
        dataType: "html", 
        data: { accion: 4,
                selRamo: ramoVal,
                selPrograma: programaVal, 
                selStatus: statusVal
            },
        success: function(response) {
            var respuesta = response;
            if (respuesta != "") {
                var obj = jQuery.parseJSON(response);
                if(obj.exito){
                    //e.preventDefault();
                    $("#selRamo").val(ramoVal);
                    $("#selPrograma").val(programaVal);
                    $("#selStatus").val(statusVal);
                    $("#frmDetalle").submit();                            
                } else {
                    $.Framework.showNotification({
                        text: obj.mensaje,
                        type: 'warning',
                        duration: 6000
                    });
                }
            }
        }
    });
}   

function onDataBound(e) {
    // get the index of the cell
    var columns = e.sender.columns;
    var columnIndex = this.wrapper.find(".k-grid-header [data-field=" + "statusIniDescr" + "]").index();
    var columnIndex2 = this.wrapper.find(".k-grid-header [data-field=" + "statusPosDescr" + "]").index();
    var columnIndex3 = this.wrapper.find(".k-grid-header [data-field=" + "politicaPublica" + "]").index();

    // iterate the table rows and apply custom row and cell styling
    var rows = e.sender.tbody.children();
    for (var j = 0; j < rows.length; j++) {
        var row = $(rows[j]);
        var dataItem = e.sender.dataItem(row);

        var statusIniDescr = dataItem.get("statusIniDescr");
        var color = dataItem.get("colorIni");
        var cell = row.children().eq(columnIndex);
        cell.html("<span class='status-text' style='background-color:"+color+"'>"+statusIniDescr+"</span>");
        cell.addClass("text-center");

        var statusPosDescr = dataItem.get("statusPosDescr");
        var color = dataItem.get("colorPos");
        var cell = row.children().eq(columnIndex2);
        cell.html("<span class='status-text' style='background-color:"+color+"'>"+statusPosDescr+"</span>");
        cell.addClass("text-center");
        
        var boleano = dataItem.get("politicaPublica");
        var cell = row.children().eq(columnIndex3);
        if(boleano){
            cell.html("<span class='fa fa-check-square-o grid-check' title='Con fines de política pública'></span>");
            cell.addClass("text-center");
        } else {
            cell.html("<span class='fa fa-square-o grid-check' title='Sin fines de política pública'></span>");
            cell.addClass("text-center");
        }
    }
    if(e.sender.dataSource.total() == 0){
        botonAgregar(); 
    } else {
        $("button.btnbootstrap.btn-agregar").addClass('oculto');
    }
}

function botonAgregar(){
    //debugger;
    if($("#ramoComboBox").val() != '' && $("#programaComboBox").val() != '' ){
        $("button.btnbootstrap.btn-agregar").removeClass('oculto');
    } else {
        $("button.btnbootstrap.btn-agregar").addClass('oculto');
    }
}

function agregaMIR(){
    var data = {ramo: $("#ramoComboBox").val(), prg: $("#programaComboBox").val()};     
    if(data.ramo !== "" && data.prg !== "" ){
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/MIR/ajaxCapturaMIR.jsp',
            datatype: 'json',
            data: {accion: 1, entidad: kendo.stringify(data), programaAnt: $("input#programa-anterior").val()},
            success: function(response) {
               // form.closest("[data-role=window]").data("kendoWindow").close();

                $.Framework.showNotification({
                    text: response.mensaje,
                    type: response.exito ? 'success' : 'error'
                });

                var grid = $('.k-grid:visible').data('kendoGrid');
                refreshGrid(grid);
            }
        });
     }    
} 

function handle_requestEnd(e) {
    // Code to handle the requestEnd event.
    if(e.type === "create" || e.type === "update"  || e.type === "destroy")
    {
        $.Framework.showNotification({
            text: e.response.map.mensaje,
            type: e.response.map.exito ? 'success' : 'warning'
        });
        var grid = $('.k-grid:visible').data('kendoGrid');
        refreshGrid(grid);
    }
}