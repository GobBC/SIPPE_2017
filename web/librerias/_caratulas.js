/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function capturaCaratulasGetPopUpCaratula() {

    var caratula = $("#selCaratula").val();
    var ramoId = $("#selRamo").val();

    if (ramoId == "-1") {
        $.msgBox({
            title: "Aviso",
            content: "Para Continuar Debe De Seleccionar Un Ramo.",
            type: "info"
        });
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/caratula/ajaxDisplayPopUpCaratula.jsp',
        datatype: 'html',
        data: {
            caratula: caratula,
            ramoId: ramoId
        },
        success: function(response) {

            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res != "") {
                $("#dialog-tbl-caratula").dialog("open");
                $("#caratulaZone").html(res);
                $("#cancelEdit").addClass("hidden");
            } else {
                $("#dialog-tbl-caratula").dialog("open");
            }

        },
        error: function(response) {
            $('#mensaje').hide();
            $.msgBox({
                title: "Error",
                content: "Ocurri&oacute; un error al procesar la solicitud",
                type: "error"
            });
        }
    });

}

function capturaCaratulasActualizaCaratula(redibujar) {

    var selCaratula = $("#selCaratula").val();
    var ramoId = $("#selRamo").val();
    var fecha = $("#fecha").val();
    var selNumeroSesion = $("#selNumeroSesion").val();
    var selTipoSesion = $("#selTipoSesion").val();
    var selTipoModificacion = $("#selTipoModificacion").val();
    var selModPresup = $("#selModPresup").val();
    var selModProg = $("#selModProg").val();
    var estatus = "";

    if ($("#checkStatus").is(":checked")) {
        estatus = "A";
    } else {
        estatus = "C";
    }

    if (fecha == "") {
        $.msgBox({
            title: "Error",
            content: "Es necesario capturar la fecha de la sesi&oacute;n",
            type: "error"
        });
        return false;
    }

    if (selNumeroSesion == "-1") {
        $.msgBox({
            title: "Error",
            content: "Es necesario capturar el numero de sesi&oacute;n",
            type: "error"
        });
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/caratula/ajaxActualizaCaratula.jsp',
        datatype: 'html',
        data: {
            selCaratula: selCaratula,
            fecha: fecha,
            selNumeroSesion: selNumeroSesion,
            selTipoSesion: selTipoSesion,
            selTipoModificacion: selTipoModificacion,
            selModPresup: selModPresup,
            selModProg: selModProg,
            ramoId: ramoId,
            estatus: estatus
        },
        success: function(response) {

            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res != "1") {
                $.msgBox({
                    title: "Aviso",
                    content: res,
                    type: "info"
                });
            } else {
                $.msgBox({
                    title: "Aviso",
                    content: "La informaci&oacute;n fue actualizada exitosamente",
                    type: "info"
                });
                capturaCaratulasGetListadoCaratulas();
                getTablaMovimientosByCaratula();
            }
        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            $.msgBox({
                title: "Error",
                content: "Ocurri&oacute; un error al procesar la solicitud",
                type: "error"
            });
            capturaCaratulasGetListadoCaratulas();
            getTablaMovimientosByCaratula();
        }
    });

}

function capturaCaratulasGetComboNumSesion() {

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    var ramoId = $("#selRamo").val();
    var selCaratula = $("#selCaratula").val();
    var fecha = $("#fecha").val();
    var selTipoSesion = $("#selTipoSesion").val();
    var selTipoModificacion = $("#selTipoModificacion").val();

    if ($("#caratulaPresupuestal").is(':checked')) {
        tipoCaratula = 2;
    }
    $("#rangos-param").hide();
    $(".selRangoPrograma").val(-1);
    $(".selRangoProyecto").val(-1);

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/caratula/ajaxGetComboNumSesion.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            selCaratula: selCaratula,
            fecha: fecha,
            selTipoSesion: selTipoSesion,
            selTipoModificacion: selTipoModificacion
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            if (res == "-1") {
                $.msgBox({
                    title: "Error",
                    content: "Ocurri&oacute; un error al procesar la solicitud",
                    type: "error"
                });
            } else {
                $("#selNumeroSesion").html(res);
            }
        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            $.msgBox({
                title: "Error",
                content: "Ocurri&oacute; un error al procesar la solicitud",
                type: "error"
            });
        }
    });
}

function capturaCaratulasGetListadoCaratulas() {

    var caratula = $("#selCaratula").val();
    var ramoId = $("#selRamo").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/caratula/ajaxActualizaListadoCaratulas.jsp',
        datatype: 'html',
        data: {
            caratula: caratula,
            ramoId: ramoId
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $("#selCaratula").html(res);
            $('#mensaje').hide();

        },
        error: function(response) {
            $('#mensaje').hide();
        }
    });

}

function capturaCaratulasEliminarCaratula() {

    $.msgBox({
        title: "Desea Continuar",
        content: "Est\u00e1 seguro que desea borrar esta caratula?",
        type: "confirm",
        buttons: [{value: "Continuar"}, {value: "Cancelar"}],
        success: function(result) {
            
            if (result == "Continuar") {

                var selCaratula = $("#selCaratula").val();

                mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

                $.ajax({
                    type: 'POST',
                    url: 'librerias/ajax/caratula/ajaxEliminarCaratula.jsp',
                    datatype: 'html',
                    data: {
                        selCaratula: selCaratula
                    },
                    success: function(response) {

                        $('#mensaje').hide();
                        
                        var res = trim(response.replace("<!DOCTYPE html>", ""));
                        
                        if (res != "1") {
                            $.msgBox({
                                title: "Aviso",
                                content: res,
                                type: "info"
                            });
                        } else {
                            $.msgBox({
                                title: "Aviso",
                                content: "La informaci&oacute;n fue actualizada exitosamente",
                                type: "info"
                            });
                            capturaCaratulasGetListadoCaratulas();
                            getTablaMovimientosByCaratula();
                        }

                    },
                    error: function(response) {
                        $('#mensaje').hide();
                    }
                });
            }
        }
    });
}