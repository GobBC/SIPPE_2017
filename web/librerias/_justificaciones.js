/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function capturaJustificacionesGetCaratulas() {

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    var tipoCaratula = 1;
    var ramoId = $("#selRamo").val();


    if ($("#caratulaPresupuestal").is(':checked')) {
        tipoCaratula = 2;
    }

    $("#rangos-param").hide();
    $(".selRangoPrograma").val(-1);
    $(".selRangoProyecto").val(-1);

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetCaratulasByRamo.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            tipoCaratula: tipoCaratula,
            isReporte: true
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res == "-1") {
                $.msgBox({
                    title: "Error",
                    content: "Ocurri&oacute; un error al procesar la solicitud",
                    type: "error"
                });
            } else {
                $("#selCaratula").html(response);
            }
            capturaJustificacionesGetFolios();
        },
        error: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            capturaJustificacionesGetFolios();
            $.msgBox({
                title: "Error",
                content: res,
                type: "error"
            });
        }
    });
}

function capturaJustificacionesGetFolios() {

    var ramoId = $("#selRamo").val();
    var caratula = $("#selCaratula").val();
    var tipoMov = $("#selTipoMovto").val();
    var statusMov = $("#selEstatusMovimiento").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/justificacionFolio/ajaxGetFoliosByRamoCaratulaTipoMovStatusMov.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            caratula: caratula,
            tipoMov: tipoMov,
            statusMov: statusMov
        },
        success: function(response) {

            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res != '-1') {
                $("#selFolios").html(res);
            } else {
                $("#selFolios").html("<option value='-1'> -- Seleccione un folio -- </option>");
                $.msgBox({
                    title: "Error",
                    content: "Ocurri&oacute; un error al procesar la solicitud",
                    type: "error"
                });
            }
            capturaJustificacionesGetJustificaciones();
            //dataTablePOA("tbl-Justificaciones");
        },
        error: function(response) {
            $('#mensaje').hide();
            $("#selFolios").html("<option value='-1'> -- Seleccione un folio -- </option>");
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            capturaJustificacionesGetJustificaciones();
            $.msgBox({
                title: "Error",
                content: res,
                type: "error"
            });
        }

    });

}

function capturaJustificacionesGetJustificaciones() {

    var selFolios = $("#selFolios").val();
    $("#oficio").val(selFolios);

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/justificacionFolio/ajaxGetJustificacionesByFolio.jsp',
        datatype: 'html',
        data: {
            selFolios: selFolios
        },
        success: function(response) {

            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res != '-1') {
                $("#selJustificaciones").html(res);
            } else {
                $("#selJustificaciones").html("<option value='-1'> -- Seleccione una justificacion -- </option>");
                $.msgBox({
                    title: "Error",
                    content: "Ocurri&oacute; un error al procesar la solicitud",
                    type: "error"
                });
            }

            capturaJustificacionesGetTablaMovimientos();
        },
        error: function(response) {
            $('#mensaje').hide();
            $("#selJustificaciones").html("<option value='-1'> -- Seleccione una justificacion -- </option>");
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            capturaJustificacionesGetTablaMovimientos();
            $.msgBox({
                title: "Error",
                content: res,
                type: "error"
            });
        }

    });

}

function capturaJustificacionesGetTablaMovimientos() {

    var selFolios = $("#selFolios").val();
    var selJustificaciones = $("#selJustificaciones").val();
    var justificacion = "";

    if (selJustificaciones != "-1") {
        $('#btn-add').hide();
        $('#btn-edit').show();
        $('#dvAllChecks').show();
        $('#tlpJustificacion').show();
        justificacion = $("#selJustificaciones option:selected").text();
        justificacion = justificacion.substring(4);

    } else {
        $('#btn-add').show();
        $('#btn-edit').hide();
        $('#dvAllChecks').hide();
        $('#tlpJustificacion').hide();
    }

    $('#tlpJustificacion').attr('title', justificacion);

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/justificacionFolio/ajaxGetMovimientosByFolioJustificacion.jsp',
        datatype: 'html',
        data: {
            selFolios: selFolios,
            selJustificaciones: selJustificaciones
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            capturaJustificacionesReincioTablaMovimientos();
            if (res != '-1') {
                if (res == '1') {
                    $("#dataTableZone tbody").html("");
                    $.msgBox({
                        title: "Aviso",
                        content: "No se encontr&oacute; la informaci&oacute;n solicitada",
                        type: "info"
                    });
                } else {
                    $("#dataTableZone tbody").html(res);
                    if (res.includes("tlpInfoAsignacion")) {
                        $("#allChecks").attr("disabled", true);
                    } else {
                        $("#allChecks").removeAttr("disabled");
                    }
                }
            } else {
                $.msgBox({
                    title: "Error",
                    content: "Ocurri&oacute; un error al procesar la solicitud",
                    type: "error"
                });
            }
            capturaJustificacionesInicializarDataTable("tbl-Justificaciones");
        },
        error: function(response) {
            $('#mensaje').hide();
            capturaJustificacionesReincioTablaMovimientos();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $.msgBox({
                title: "Error",
                content: res,
                type: "error"
            });
        }

    });
}

function capturaJustificacionesGetPopUpJustificacion() {

    var selFolios = $("#selFolios").val();
    var selJustificaciones = $("#selJustificaciones").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/justificacionFolio/ajaxGetPopUpJustificacion.jsp',
        datatype: 'html',
        data: {
            selFolios: selFolios,
            selJustificaciones: selJustificaciones
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res == "-1") {
                $.msgBox({
                    title: "Error",
                    content: "Ocurri&oacute; un error al procesar la solicitud",
                    type: "error"
                });
            } else {
                if (res != "") {
                    $("#dialog-tbl-justificacion").dialog("open");
                    $("#justificacionZone").html(res);
                    $("#cancelEdit").addClass("hidden");
                } else {
                    $("#dialog-tbl-justificacion").dialog("open");
                }
            }

        },
        error: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $.msgBox({
                title: "Error",
                content: res,
                type: "error"
            });
        }
    });
}

function capturaJustificacionesSaveJustificacion() {

    var folio = $("#selFolios").val();
    var justificacion = $("#justificacionAuto").val().toUpperCase();
    var idJustificacion = $("#idJustificacion").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/justificacionFolio/ajaxSaveJustificacion.jsp',
        datatype: 'html',
        data: {
            folio: folio,
            idJustificacion: idJustificacion,
            justificacion: justificacion
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res == '-1') {
                $.msgBox({
                    title: "Error",
                    content: "Ocurri&oacute; un error al procesar la solicitud",
                    type: "error"
                });
            } else {
                $.msgBox({
                    title: "actualizaci&oacute;n correcta",
                    content: "La actualizaci&oacute;n se realiz&oacute; correctamente",
                    type: "info"
                });
            }
            capturaJustificacionesGetJustificaciones();
        },
        error: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $.msgBox({
                title: "Error",
                content: res,
                type: "error"
            });
        }
    });
}

function capturaJustificacionesActAsignacionTodosMovimientos() {

    var selJustificaciones = $("#selJustificaciones").val();

    if (selJustificaciones != -1) {

        var asignado = "N";
        var selFolios = $("#selFolios").val();


        if ($("#allChecks").is(':checked'))
            asignado = "S";

        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/justificacionFolio/ajaxActualizaAsignacionTodosMovimientos.jsp',
            datatype: 'html',
            data: {
                asignado: asignado,
                selJustificaciones: selJustificaciones,
                selFolios: selFolios
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res == '-1') {
                    $.msgBox({
                        title: "Error",
                        content: "Ocurri&oacute; un error al procesar la solicitud",
                        type: "error"
                    });
                } else {
                    $.msgBox({
                        title: "actualizaci&oacute;n correcta",
                        content: "La actualizaci&oacute;n se realiz&oacute; correctamente",
                        type: "info"
                    });
                }
                capturaJustificacionesGetTablaMovimientos();
            },
            error: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                $.msgBox({
                    title: "Error",
                    content: res,
                    type: "error"
                });
            }
        });
    }
}

function capturaJustificacionesActAsignacionMovimiento(tabla, numMov, folio, ramo, programa, proyecto, tipoProy, meta, accion, depto, partida, fuente, fondo, recurso, diferenciador, idJustificacion) {

    var asignado = "N";
    var selJustificaciones = $("#selJustificaciones").val();

    if (idJustificacion != 0 && selJustificaciones != idJustificacion)
    {
        $.msgBox({
            title: "Desea Continuar?",
            content: "El movimiento esta asignado a la justificaci&oacute;n " + idJustificacion + " se desasignara y se asignara a la justificaci&oacute;n " + selJustificaciones + ".",
            type: "confirm",
            buttons: [{value: "Aceptar"}, {value: "Cancelar"}],
            success: function(result) {
                if (result == "Aceptar") {
                    if ($("#chkAsignado" + numMov).is(':checked'))
                        asignado = "S";

                    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/justificacionFolio/ajaxActualizaAsignacionMovimiento.jsp',
                        datatype: 'html',
                        data: {
                            tabla: tabla,
                            asignado: asignado,
                            selJustificaciones: selJustificaciones,
                            folio: folio,
                            ramo: ramo,
                            programa: programa,
                            proyecto: proyecto,
                            tipoProy: tipoProy,
                            meta: meta,
                            accion: accion,
                            depto: depto,
                            partida: partida,
                            fuente: fuente,
                            fondo: fondo,
                            recurso: recurso,
                            diferenciador: diferenciador

                        },
                        success: function(response) {
                            $('#mensaje').hide();
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            if (res == '-1') {
                                $.msgBox({
                                    title: "Error",
                                    content: "Ocurri&oacute; un error al procesar la solicitud",
                                    type: "error"
                                });
                            } else {
                                $.msgBox({
                                    title: "actualizaci&oacute;n correcta",
                                    content: "La actualizaci&oacute;n se realiz&oacute; correctamente",
                                    type: "info"
                                });
                            }
                            capturaJustificacionesGetTablaMovimientos();
                        },
                        error: function(response) {
                            $('#mensaje').hide();
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            $.msgBox({
                                title: "Error",
                                content: res,
                                type: "error"
                            });
                        }
                    });
                } else {
                    capturaJustificacionesGetTablaMovimientos();
                }
            }
        });
    } else {

        if ($("#chkAsignado" + numMov).is(':checked'))
            asignado = "S";

        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/justificacionFolio/ajaxActualizaAsignacionMovimiento.jsp',
            datatype: 'html',
            data: {
                tabla: tabla,
                asignado: asignado,
                selJustificaciones: selJustificaciones,
                folio: folio,
                ramo: ramo,
                programa: programa,
                proyecto: proyecto,
                tipoProy: tipoProy,
                meta: meta,
                accion: accion,
                depto: depto,
                partida: partida,
                fuente: fuente,
                fondo: fondo,
                recurso: recurso,
                diferenciador: diferenciador

            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res == '-1') {
                    $.msgBox({
                        title: "Error",
                        content: "Ocurri&oacute; un error al procesar la solicitud",
                        type: "error"
                    });
                } else {
                    $.msgBox({
                        title: "actualizaci&oacute;n correcta",
                        content: "La actualizaci&oacute;n se realiz&oacute; correctamente",
                        type: "info"
                    });
                }
                capturaJustificacionesGetTablaMovimientos();
            },
            error: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                $.msgBox({
                    title: "Error",
                    content: res,
                    type: "error"
                });
            }
        });
    }
}

function capturaJustificacionesGenerarReporteExcel() {

    var folio = $("#selFolios").val();

    if (folio == -1) {
        $.msgBox({
            title: "Aviso",
            content: "Debe de seleccionar al menos un folio para generar reportes.",
            type: "info"
        });
    } else {
        $("#reporttype").val("xls");
        $("#frmRptExcel").submit();
    }
}

function capturaJustificacionesGenerarReportePDF() {

    var folio = $("#selFolios").val();

    if (folio == -1) {
        $.msgBox({
            title: "Aviso",
            content: "Debe de seleccionar al menos un folio para generar reportes.",
            type: "info"
        });
    } else {
        $("#reporttype").val("pdf");
        $("#frmRptExcel").submit();
    }
}

function capturaJustificacionesReincioTablaMovimientos() {
    $("#tbl-Justificaciones_wrapper").remove();
    $("#dataTableZone").html("<table id='tbl-Justificaciones' class='display'><thead><tr><th  >Num Mov</th><th  >Ramo</th><th  >Programa</th><th  >Proy. / Act.</th><th  >Meta</th><th  >Accion</th><th  >Depto</th><th  >Partida</th><th  >F.F.R</th><th class='check' >Importe</th><th   > Seleccionar </th></tr></thead><tbody></tbody></table>");
}

function capturaJustificacionesInicializarDataTable(table) {
    $("#" + table).DataTable({
        "language": {
            "lengthMenu": "Mostrando _MENU_ registros por página",
            "zeroRecords": "No se encontraron registros",
            "info": "Página _PAGE_ de _PAGES_",
            "infoEmpty": "  ",
            "infoFiltered": "(filtrado de _MAX_ registros)",
            "search": "Búsqueda",
            "paginate": {
                "first": "Primero",
                "last": "Último",
                "next": "Siguiente",
                "previous": "Anterior"
            }
        }
    });
}

function movimientosJustificacionesValMovsAsig(tipoMov) {

    var selFolios = $("#inp-folio-usr").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/justificacionFolio/ajaxMovimientosJustificacionesValMovsAsig.jsp',
        datatype: 'html',
        data: {
            selFolios: selFolios
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res != '-1') {
                if (res == '1') {
                    if (tipoMov == "R"){
                        validaMetasAccionesHabilitadasReprogramacion(true);
                    }
                    if(tipoMov == "A"){
                        validaMetasAccionesHabilitadas(true);
                    }
                    if(tipoMov == "T"){
                        validaMetasAccionesHabilitadasTransferencias(true);
                    }                        
                } else {
                    $.msgBox({
                        title: "Aviso",
                        content: res,
                        type: "info"
                    });
                }
            } else {
                $.msgBox({
                    title: "Error",
                    content: "Ocurri&oacute; un error al procesar la solicitud",
                    type: "error"
                });
            }
        },
        error: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $.msgBox({
                title: "Error",
                content: res,
                type: "error"
            });
        }
    });
}