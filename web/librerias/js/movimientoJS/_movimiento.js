/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$( document ).ready(function() {
    $(function() {
        $("#programacion").dialog({
            autoOpen: false,
            height: 500,
            width: 800,
            draggable : true,
            modal: true,
            buttons: {
                "Cerrar": function() {
                    $(this).dialog("close");
                }
            },close: function() {
                $(this).dialog("close");
            }
        });
    }); 
});

function mostrarProgramacion(ramo,meta, accion){
    var oficio;
    if(meta < 0 || accion < 0){
        oficio = $("#inp-folio-usr").val();
        if(typeof oficio === 'undefined'){
            oficio = $("#folio").val();
        }
    }
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/programacion/ajaxMostrarProgramacionMovto.jsp',
        datatype: 'html',
        data: {
            ramo: ramo,
            meta: meta,
            accion: accion,
            oficio : oficio
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else {
                if(res === "0"){
                    alert("Consultar movimientos programáticos creados");
                }else{
                    $("#programacion").html(res);
                    $("#programacion").dialog("open");
                }
            }
        }
    });
}