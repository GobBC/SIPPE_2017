/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var mensajes = {
    getMsgInformacionGrabada: function() {
        return this.getMsgExitoFormato("Informaci&oacute;n grabada correctamente.");
    },
    getMsgErrorSolicitud: function() {
        return this.getMsgFormatoError("Ocurri&oacute; un error al procesar su solicitud.");
    },
    getMsgFormatoError: function(mensaje) {
        return "<div class='alert error'>" + mensaje + "</div>";
    },
    getMsgFormatoExito: function(mensaje) {
        return "<div class='alert exito'>" + mensaje + "</div>";
    },
    getMsgFormatoAdvertencia: function(mensaje) {
        return "<div class='alert advertencia'>" + mensaje + "</div>";
    },
    mostrarMensaje: function(elemento, mensajeFinal) {
        $("#" + elemento + "").html(mensajeFinal);
    },
    mostrarAjaxLoad: function(imagen, elemento, mensaje, tamano) {
        if (tamano == "undefined" || tamano == undefined) {
            tamano = 32;
        }
        $("#" + elemento + "").html("<div class='ajaxLoad' ><img src='" + imagen + "' alt='" + mensaje + "' width='" + tamano + "px' height='" + tamano + "px'/>" + mensaje + "</div>");
        $("#" + elemento + "").show();
        $('#mensaje').show();
    }
};

function getAllMetas(){
    var ramo = $("#selRamo").val();
    $("#selMetaNew").html("<option value='-1'> -- Seleccione una meta -- </option>");
    $("#selMetaAct").html("<option value='-1'> -- Seleccione una meta -- </option>");
    $("#selAccionAct").html("<option value='-1'> -- Seleccione una acci&oacute;n -- </option>");
    $("#selAccionNew").html("<option value='-1'> -- Seleccione una acci&oacute;n -- </option>");
    if(ramo !== '-1'){        
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/migracion/ajaxGetAllMetasByRamo.jsp',
            datatype: 'html',
            data: {
                ramo: ramo
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === '-1') {
                    alert("Ocurrió un error al procesar la solicitud");
                } else {
                    $(".metaSel").html(response);
                }
                $('#mensaje').hide();
            }
        });
    }
}

function getAccionesByMeta(isMetaNueva){
    var meta;
    var ramo = $("#selRamo").val();
    if(isMetaNueva){
        meta = $("#selMetaNew").val();
        if(meta === '-1'){
            $("#selAccionNew").html("<option value='-1'> -- Seleccione una acci&oacute;n -- </option>");
            return false;
        }
    }else{
        meta = $("#selMetaAct").val();
        if(meta === '-1'){
            $("#selAccionAct").html("<option value='-1'> -- Seleccione una acci&oacute;n -- </option>");
            return false;
        }
    }
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetAccionByMeta.jsp',
        datatype: 'html',
        data: {
            ramo: ramo,
            meta: meta
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else {
                if(isMetaNueva){
                    $("#selAccionNew").html(response);
                }else{
                    $("#selAccionAct").html(response);
                }
            }
        }
    });
}

function migrarAccion(){
    var ramo = $("#selRamo").val();
    var metaAct = $("#selMetaAct").val();
    var metaNew = $("#selMetaNew").val();
    var accionAct = $("#selAccionAct").val();
    var accionNew = $("#selAccionNew").val();
    if(ramo === '-1' | metaAct === '-1' | metaNew === '-1' | accionAct === '-1' | accionNew === '-1'){
        alert("Debe de seleccionar todos los campos para continuar");
        return false;
    }else{
        if(metaAct === metaNew && accionAct === accionNew){
            alert("La meta-acción de origen no pueden ser igual a la meta-acción destino");
        }else{
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/migracion/ajaxMigrarAcciones.jsp',
                datatype: 'html',
                data: {
                    ramo: ramo,
                    metaAct: metaAct,
                    accionAct : accionAct,
                    metaNew: metaNew,
                    accionNew : accionNew
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res === "-1") {
                        alert("Ocurrió un error al procesar la solicitud");
                    } else {
                        if(res === '1')
                            alert("El proceso terminó correctamente");
                        else
                            alert("El proceso falló al migrar la acción");
                    }
                }
            });
        }
    }
}