/* 
 * Document   : funcionesMIREdit
 * Created on : Jul 14, 2017, 10:42:45 AM
 * Author     : ealonso
 */

$(document).ready(function() {
    var drop1 = $("#ramo").data("kendoDropDownList");       
    var drop2 = $("#prg").data("kendoDropDownList");
    var input1 = $("#ramo");
    var input2 = $("#prg");
    if ($('[name="programaComboBox"]').val()!= "") {
        drop2.value($('[name="programaComboBox"]').val());
        refreshDrop(drop1);
    }
    if ($('[name="ramoComboBox"]').val()!= "") {        
        drop1.value($('[name="ramoComboBox"]').val());
        refreshDrop(drop2);
    }
    if($('#accion').val() == "2") {
         drop1.readonly()
    }
});

$(function () {
    cargarEventos();
});

function cargarEventos(){
    var forma = $('#popup-edit');

    forma.off('submit').on('submit', function (e) {
        e.preventDefault();
        var form = $(this);
        var data = form.serializeObject();

        if(data.ramo !== "" && data.prg !== "" ){
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/MIR/ajaxCapturaMIR.jsp',
                datatype: 'json',
                data: {accion: $('#accion').val(), entidad: kendo.stringify(data), programaAnt: $("input#programa-anterior").val()},
                success: function(response) {
                    form.closest("[data-role=window]").data("kendoWindow").close();

                    $.Framework.showNotification({
                        text: response.mensaje,
                        type: response.exito ? 'success' : 'warning'
                    });

                    var grid = $('.k-grid:visible').data('kendoGrid');
                    refreshGrid(grid);
                }
            });
        }
    });

    forma.find('button.close').off('click').on('click', function(e){
        e.preventDefault();
        $(this).closest("[data-role=window]").data("kendoWindow").close();
    });

    forma.kendoValidator({
        rules: {
            customRule2: function(input) {
                //only 'Tom' will be valid value for the username input
                if (input.is("[name=ramo]")) {
                    return input.val() !== "";
                }
                return true;
            },
            customRule3: function(input) {
                //only 'Tom' will be valid value for the username input
                if (input.is("[name=prg]")) {
                    return input.val() !== "";
                }
                return true;
            }
        },
        messages: {
            customRule2: "El campo ramo es requerido.",
            customRule3: "El campo programa es requerido."
        }
    });
}