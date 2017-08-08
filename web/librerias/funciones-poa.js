/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    $('.no-enter').bind('keypress', function(e) {
        if (e.keyCode == 13)
        {
            return false;
        }
    });
});

String.prototype.replaceAll = function(target, replacement) {
    return this.split(target).join(replacement);
};

var contImp = 0;
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

function changeRamo() {
    var ramo = $("#selRamo").val();
    if (ramo !== -1) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetResponsableRamo.jsp",
            dataType: 'html',
            data: {
                ramo: ramo
            },
            success: function(response) {
                $('#mensaje').hide();
                $("#datos-responsable").html(response);
            }
        });
    }
    if (ramo == -1) {
        $("#search-responsable").hide();
        $("#inTxtNombre").val("");
        $("#inTxtApPat").val("");
        $("#inTxtApMat").val("");
        $("#resultado-busqueda").html("");
    }
}

function isPOACerrado() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var metaRow = $("#tblMetas .selected td");
    if (metaRow.length > 0) {
        var metaId = metaRow[0].innerHTML;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidaCierrePOA.jsp',
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                metaId: metaId
            },
            success: function(response) {
                $('#mensaje').hide();
                var result = trim(response.replace("<!DOCTYPE html>", ""));
                if (result == "Cerrado") {
                    alert("Este ramo se encuentra cerrado para su captura en POA");
                    return false;
                } else {
                    if (result == "noPres") {
                        alert("Esta meta no esta lista para presupuestar");
                    } else if (result === "calendarizar") {
                        alert("Esta meta no se ha calendarizado");
                    } else {
                        var metaRow = $("#tblMetas .selected td");
                        if (metaRow.length > 0) {
                            $("#metaId").val(metaRow[0].innerHTML);
                            $("#frmDefMetas").submit();
                        } else {
                            alert("Seleccione una meta para continuar");
                        }
                    }
                }
            }
        });
    } else {
        alert("Seleccione una meta para continuar");
    }
}

function getProgramasOnChange() {
    var ramoId = $("#selRamo").val();
    var divInfoPlantilla = $('#divInfoPlantilla').val();
    if (typeof divInfoPlantilla != 'undefined') {
        $('#divInfoPlantilla').html("<input id='contadorGrupos' type='hidden' value='0'/> <input id='contadorRegistros' type='hidden' value='0' />");
    }
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetProgramasByRamo.jsp',
        datatype: 'html',
        async: false,
        data: {
            ramo: ramoId
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                if (trim(response.replace("<!DOCTYPE html>", "")) != -2) {
                    $("#selPrograma").html(response);
                } else {
                    alert("El ramo seleccionado no esta disponible para presupuestaci\u00f3n");
                    $("#selPrograma").html("<option value='-1'> -- Seleccione un programa -- </option>");
                }
            }
            $('#mensaje').hide();

        }
    });
    $("#tabContent").html("");
    $("#tabContainer").hide();
    $("#divInfoProyecto").html("");
    $("#selProyecto").html("<option value='-1'> -- Seleccione un proyecto -- </option>");
    $("#selDepartamento").html("<option value='-1'> -- Seleccione un departamento -- </option>");
    $("#div-listado").html("");
    $("#control-metas").hide();
}

function searchResponsable() {
    var nombre = $("#inTxtNombre").val();
    var apPaterno = $("#inTxtApPat").val();
    var apMaterno = $("#inTxtApMat").val();
    nombre = nombre.toUpperCase();
    apPaterno = apPaterno.toUpperCase();
    apMaterno = apMaterno.toUpperCase();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxBusquedaEmpleado.jsp",
        dataType: 'html',
        data: {
            nombre: nombre,
            apPaterno: apPaterno,
            apMaterno: apMaterno
        },
        success: function(response) {
            $('#mensaje').hide();
            $("#resultado-busqueda").html(response);
        }
    });
    if ((nombre !== "") || (apMaterno !== "") || (apPaterno !== ""))
        $("#btnCancelar").hide();
}

function getEmpleadoSelected() {
    var selectedRow = $(".selected").children();
    if (selectedRow.length > 0) {
        var rfc = selectedRow[0].innerHTML;
        var homoclave = selectedRow[1].innerHTML;
        var nombre = selectedRow[2].innerHTML;
        $("#inTxtRFC").val(rfc);
        $("#inTxtHC").val(homoclave);
        $("#inTxtNombreC").val(nombre);
        $("#divSelResponsable").hide();
        $("#resultado-busqueda").html("");
        $("#inTxtNombre").val("");
        $("#inTxtApPat").val("");
        $("#inTxtApMat").val("");
    } else
        alert("Seleccione un responsable");
}

function updateResponsableRamo() {

    var rfc = $("#inTxtRFC").val();
    var homoclave = $("#inTxtHC").val();
    var resultado;
    //$("#datos-responsable").html("");
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxRegistrarResponsable.jsp",
        dataType: 'text',
        data: {
            rfc: rfc,
            homoclave: homoclave,
            ramo: $("#selRamo").val()
        },
        success: function(response) {
            $('#mensaje').hide();
            resultado = response;
            alert(trim(resultado.replace("<!DOCTYPE html>", "")));
        }
    });

}

function getInfoOnProgramaChange() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $("#tabContainer").show();
    $("#tabContent").html("");
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetInformacionPrograma.jsp",
        dataType: 'html',
        data: {
            ramo: ramoId,
            programa: programaId
        },
        success: function(response) {
            $('#mensaje').hide();
            $("#tabContent").html(response);
            $("#tabContainer").show();
        }
    });

}

function mostrarBusqueda() {
    $("#divSelResponsable").show();
}

function esconderBusqueda() {
    $("#divSelResponsable").hide();
    $("#inTxtNombre").val("");
    $("#inTxtApPat").val("");
    $("#inTxtApMat").val("");
    $("#resultado-busqueda").html("");
    $("#btnCancelar").show();
}

function Home() {
    window.location = "menu.jsp";
}

function limpiar() {
    $("#selRamo").val(-1);
    $("#datos-responsable").html("");
}

function guardarPrograma() {
    var cont = 0;
    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    var rfc = $("#inTxtRFC").val();
    var homoclave = $("#inTxtHC").val();
    var ue = $("#selUE").val();
    var fin = $("#selFin").val();
    var proposito = $("#txtAreaProposito").val();
    var lineas = $("#tblLineaAcc .ramoLineaAccion");
    var guardarLinea = "";
    var ponderacion = $("#selPonderacion").val();
    var depto = $("#selUE").val();
    /*if ($("#tblLineaAcc tr").length === 0) {
     cont++;
     $("#tabContent-5").addClass("errorClass");
     } else {
     $("#tabContent-5").removeClass("errorClass");
     var selects = $("#tblLineaAcc tr select");
     var check = 0;
     for (var it = 0; it < selects.length; it++) {
     if (selects[it].value !== '0') {
     check++;
     }
     }
     if (check === 0) {
     $("#tabContent-5").addClass("errorClass");
     cont++;
     } else {
     $("#tabContent-5").removeClass("errorClass");
     }
     }*/
    if (fin === "-1") {
        cont++;
        $("#selFin").addClass("errorClass");
    } else {
        $("#selFin").removeClass("errorClass");
    }
    if (ponderacion === "-1") {
        cont++;
        $("#selPonderacion").addClass("errorClass");
    } else {
        $("#selPonderacion").removeClass("errorClass");
    }
    if ($("#inTxtRFC").val() === "No capturado") {
        cont++;
        $("#inTxtRFC").addClass("errorClass");
    } else {
        $("#inTxtRFC").removeClass("errorClass");
    }
    if ($("#inTxtHC").val() === "No capturado") {
        cont++;
        $("#inTxtHC").addClass("errorClass");
    } else {
        $("#inTxtHC").removeClass("errorClass")
    }
    if ($("#inTxtNombreC").val() === "No capturado") {
        cont++;
        $("#inTxtNombreC").addClass("errorClass");
    } else {
        $("#inTxtNombreC").removeClass("errorClass");
    }
    if ($("#selUE").val() === "-1") {
        cont++;
        $("#selUE").addClass("errorClass");
    } else {
        $("#selUE").removeClass("errorClass");
    }
    if (cont == 0) {
        for (var it = 0; it < lineas.length; it++) {
            guardarLinea += lineas[it].value + ",";
        }
        if (typeof proposito != 'undefined') {
            proposito = proposito.toUpperCase();
        }
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxActualizarPrograma.jsp",
            dataType: 'html',
            async: false,
            data: {
                rfc: rfc,
                homoclave: homoclave,
                ue: ue,
                fin: fin,
                proposito: proposito,
                ramo: ramo,
                programa: programa,
                ponderado: ponderacion,
                depto: depto,
                guardarLinea: guardarLinea
            },
            success: function(response) {
                $('#mensaje').hide();

                response = trim(response.replace("<!DOCTYPE html>", ""));
                if (response == "1") {
                    alert("La actualizaci\u00F3n se realiz\u00F3 correctamente");
                } else {
                    alert("No se pudo actualizar el programa, int\u00E9ntelo nuevamente");
                }

            }
        });
    } else {
        alert("Debe de capturar todos los campos habilitados para poder continuar");
    }
}

function getProyectosByPrograma() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    if (programaId != -1) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetProyectosByPrograma.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramo: ramoId,
                programa: programaId
            },
            success: function(response) {
                $('#mensaje').hide();

                $("#selProyecto").html(response);
            }
        });
    } else {
        $("#selProyecto").val(-1);
    }
    $("#div-listado").html("");
    $("#control-metas").hide();
}

function getProyectosByid() {
    var programaId = $("#selPrograma").val();
    var ramoId = $("#selRamo").val();
    var proy = $("#selProyecto").val();
    proy = proy.split(",");
    proy = proy[0];
    var tipoProy = $("#selProyecto").val();
    tipoProy = tipoProy.split(",");
    tipoProy = tipoProy[1];

    if (proy != -1) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInformacionProyecto.jsp",
            dataType: 'html',
            data: {
                ramo: ramoId,
                programa: programaId,
                proy: proy,
                tipoProy: tipoProy

            },
            success: function(response) {
                $('#mensaje').hide();
                $("#divInfoProyecto").html(response);

            }
        });
    } else {
        $("#divInfoProyecto").html("");
    }
}

function getMetasOnChange() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    if (typeof programaId === 'undefined') {
        programaId = $("#selPrg").val();
    }
    var proyectoId = $("#selProyecto").val();
    if (typeof proyectoId === 'undefined') {
        proyectoId = $("#selProy").val();
    }
    var arrayProy;
    var optMeta = 1;
    var tipoProyecto;
    arrayProy = proyectoId.split(",");
    tipoProyecto = arrayProy[1];
    proyectoId = arrayProy[0];
    if (proyectoId != "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetMetas.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyectoId,
                optMeta: optMeta,
                tipoProyecto: tipoProyecto
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#div-listado").html(responce);
                $("#control-metas").show();
            }
        });
    } else {
        $("#div-listado").html("");
        $("#control-metas").hide();
    }
}

function redirectAcciones() {
    var metaRow = $("#tblMetas .selected td");
    if (metaRow != null) {
        $("#metaId").val(metaRow[0].innerHTML);
        $("#frmDefMetas").submit();
    } else {
        alert("Seleccione una meta para continuar");
    }
}

function getInfoMeta() {
    var optMeta = 2;
    var metaRow = $("#tblMetas .selected td");
    if (metaRow.length > 0) {
        var metaId = metaRow[0].innerHTML;
        var ramoId = $("#selRamo").val();
        var programaId = $("#selPrograma").val();
        var proyectoId = $("#selProyecto").val();
        var ramoDescr = $("#selRamo option:selected").text();
        var programaDescr = $("#selPrograma option:selected").text();
        var proyectoDescr = $("#selProyecto option:selected").text();
        var arrayProy = proyectoId.split(",");
        proyectoId = arrayProy[0];
        var tipoProyecto = arrayProy[1];
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAdministrarMeta.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyectoId,
                optMeta: optMeta,
                metaId: metaId,
                tipoProyecto: tipoProyecto,
                ramoDescr: ramoDescr,
                programaDescr: programaDescr,
                proyectoDescr: proyectoDescr
            },
            success: function(response) {
                $('#mensaje').hide();
                $("#infoMeta").html(response);
            }
        });
        $("#infoMeta").show();
    } else {
        alert("Debe de seleccionar una meta para continuar");
    }
}

function getRequerimientos() {
    var selRow = $("#tblAcciones .selected td");
    if (selRow.length > 0) {
        var accion = selRow[0].innerHTML;
        if (typeof selRow[2].innerHTML != 'undefined') {
            var obra = selRow[2].innerHTML;
        }
        var ramoId = $("#selRamo").val();
        var programaId = $("#selPrograma").val();
        var metaId = $("#selMeta").val();
        var fuente = $("#selFuenteReq").val();
        var tipoGasto = $("#selTipoGastoReq").val();
        var reqOpt = 1;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetRequerimientos.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                metaId: metaId,
                accionId: accion,
                reqOpt: reqOpt,
                fuente: fuente,
                tipoGasto: tipoGasto,
                obra: obra
            },
            success: function(responce) {
                $('#mensaje').hide();
                var result = trim(responce.replace("<!DOCTYPE html>", ""));
                if (result === "calendarizacion") {
                    alert("La acci\u00f3n seleccionada no ha sido calendarizada");
                    $("#divRequerimientos #divReq").html("");
                    $("#divRequerimientos div:last-child").hide();
                    $("#selFuenteGasto").hide();
                    return false;
                }
                $("#selFuenteGasto").show();

                if (obra != "") {
                    $("#selFuenteReq").attr("disabled", "disabled");
                    $("#selFuenteRecurso").attr("disabled", "disabled");
                    $("#btnInsertAccion").hide();
                    $("#btnDeleteAccion").hide();
                    $("#btnEditAccion").val("Consultar");
                    $("#botonMostrarRequerimiento").val("Consultar");
                    $("#botonInsertRequerimiento").hide();
                    $("#botonBorrarRequerimiento").hide();
                } else {
                    $("#selFuenteReq").removeAttr("disabled");
                    $("#selFuenteRecurso").removeAttr("disabled");
                    $("#btnInsertAccion").show();
                    $("#btnDeleteAccion").show();
                    $("#btnEditAccion").val("Editar");
                    $("#botonMostrarRequerimiento").val("Editar");
                    $("#botonInsertRequerimiento").show();
                    $("#botonBorrarRequerimiento").show();
                }
                $("#divRequerimientos #divReq").html(responce);
                $("#divRequerimientos div:last-child").show();

            }
        });
    } else
        alert("Seleccione una accion para continuar");

}

function limitText(limitField, limitCount, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    } else {
        //limitCount.value = limitNum - limitField.value.length;
    }
}

function insertRequerimiento() {
    var cont = 0;
    var selRow = $("#tblAcciones .selected td");
    var accion;
    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var fuente = $("#selFuenteReq").val();
    var tipoGasto = $("#selTipoGastoReq").val();
    var ramoDescr = $("#descrRamo").val();
    var programaDescr = $("#descrPrograma").val();
    var proyectoDescr = $("#descrProyecto").val();
    var metaDescr = $("#descrMeta").val();
    var fondoRecurso = $("#selFuenteRecurso").val();
    var opcion = 1;
    if (fuente === "-1") {
        cont++;
    }
    if (tipoGasto === "-1") {
        cont++;
    }
    if (fondoRecurso === "-1") {
        cont++;
    }
    if (cont > 0) {
        alert("Debe de seleccionar Fuente de financiamiento, Fondo-Recurso y Tipo de gasto para continuar")
    } else {
        if (selRow.length > 0) {
            accion = selRow[0].innerHTML;
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetInfoRequerimientos.jsp",
                dataType: 'html',
                data: {
                    ramoId: ramoId,
                    metaId: metaId,
                    fuente: fuente,
                    tipoGasto: tipoGasto,
                    opcion: opcion,
                    accion: accion,
                    ramoDescr: ramoDescr,
                    programaDescr: programaDescr,
                    proyectoDescr: proyectoDescr,
                    metaDescr: metaDescr
                },
                success: function(responce) {
                    $('#mensaje').hide();
                    var result = trim(responce.replace("<!DOCTYPE html>", ""));
                    if (result == "cerrado") {
                        alert("El presupuesto se ha cerrado para este ramo");
                        return false;
                    } else {
                        $("#infoRequerimiento").html(responce);
                        $("#infoRequerimiento").show();
                    }
                }
            });
        }
    }
}

function mostrarRequerimiento() {
    var cont = 0;
    var selRow = $("#tblRequerimientos .selected td");
    var selAccRow = $("#tblAcciones .selected td");
    if (selRow.length > 0) {
        var fondoRecurso = $("#selFuenteRecurso").val();
        var requ = selRow[0].innerHTML;
        var accion = selAccRow[0].innerHTML;
        var obra = selAccRow[2].innerHTML;
        var meta = $("#selMeta").val();
        var ramo = $("#selRamo").val();
        var programa = $("#selPrograma").val();
        var fuente = $("#selFuenteReq").val();
        var tipoGasto = $("#selTipoGastoReq").val();
        var opcion = 2;
        var ramoDescr = $("#descrRamo").val();
        var programaDescr = $("#descrPrograma").val();
        var proyectoDescr = $("#descrProyecto").val();
        var metaDescr = $("#descrMeta").val();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInfoRequerimientos.jsp",
            dataType: 'html',
            data: {
                requerimiento: requ,
                metaId: meta,
                ramoId: ramo,
                programaId: programa,
                opcion: opcion,
                accion: accion,
                fuente: fuente,
                tipoGasto: tipoGasto,
                ramoDescr: ramoDescr,
                programaDescr: programaDescr,
                proyectoDescr: proyectoDescr,
                metaDescr: metaDescr,
                obra: obra
            },
            success: function(responce) {
                var res = trim(responce.replace("<!DOCTYPE html>", ""));
                $('#mensaje').hide();
                if (res === "cerrado") {
                    alert("Este ramo est\u00e1 cerrado para presupuestaci\u00f3n");
                } else {
                    $("#infoRequerimiento").html(responce);
                    $("#infoRequerimiento").show();
                }
            }
        });

        if (obra != "") {
            $("#botonMostrarRequerimiento").val("Consultar");
            $("#botonInsertRequerimiento").hide();
            $("#botonBorrarRequerimiento").hide();
        } else {
            $("#botonMostrarRequerimiento").val("Editar");
            $("#botonInsertRequerimiento").show();
            $("#botonBorrarRequerimiento").show();
        }

    } else {
        alert("Seleccione un requerimiento");
    }
}

function consultarRequerimiento() {
    var selRow = $("#tblRequerimientos .selected td");
    var selAccRow = $("#tblAcciones .selected td");
    if (selRow.length > 0) {
        var requ = selRow[0].innerHTML;
        var accion = selAccRow[0].innerHTML;
        var obra = selAccRow[2].innerHTML;
        var meta = $("#selMeta").val();
        var ramo = $("#selRamo").val();
        var programa = $("#selPrograma").val();
        var fuente = $("#selFuenteReq").val();
        var tipoGasto = $("#selTipoGastoReq").val();
        var opcion = 2;
        var ramoDescr = $("#descrRamo").val();
        var programaDescr = $("#descrPrograma").val();
        var proyectoDescr = $("#descrProyecto").val();
        var metaDescr = $("#descrMeta").val();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInfoRequerimientos.jsp",
            dataType: 'html',
            data: {
                requerimiento: requ,
                metaId: meta,
                ramoId: ramo,
                programaId: programa,
                opcion: opcion,
                accion: accion,
                fuente: fuente,
                tipoGasto: tipoGasto,
                ramoDescr: ramoDescr,
                programaDescr: programaDescr,
                proyectoDescr: proyectoDescr,
                metaDescr: metaDescr,
                consulta: 1,
                obra: obra
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#infoRequerimiento").html(responce);
                $("#infoRequerimiento").show();
            }
        });
    } else {
        alert("Seleccione un requerimiento");
    }
}

function nuevoRequerimiento() {
    $("#nvoRequerimiento").prop("disabled", true);
    var cont = 0;
    //ar tipoGasto = $("#selTipoGasto").val();
    var isRelacionLaboral = $('#isRelLaboral').val();
    var proyecto = $("#selProyecto").val();
    var selRow = $("#tblAcciones .selected td");
    var accionId;
    var articulo = $("#ArtPartida").val();
    var fuente = $("#selFuenteRecurso").val();
    var tipoGasto = $("#vwTipoGasto").val();
    var partida = $("#selPartida").val();
    var partidaDesc = $("#txtAreaPart").val();
    //partidaDesc = partidaDesc.toUpperCase();
    var tipoProy = $("#selTipoProy").val();
    if (typeof partidaDesc != 'undefined') {
        partidaDesc = partidaDesc.toUpperCase();
    }
    var justificacion = $("#txtAreaJust").val();
    justificacion = justificacion.toUpperCase();
    var cantidad = $("#inpTxtCantidad").val();
    cantidad = cantidad.replaceAll(",", "");
    var costoUni = $("#inpTxtCantUnit").val();
    costoUni = costoUni.replaceAll(",", "");
    var costoAn = $("#inpTxtCantAnual").val();
    costoAn = costoAn.replaceAll(",", "");
    var enero = $("#inpTxtEne").val();
    enero = enero.replaceAll(",", "");
    var febrero = $("#inpTxtFeb").val();
    febrero = febrero.replaceAll(",", "");
    var marzo = $("#inpTxtMar").val();
    marzo = marzo.replaceAll(",", "");
    var abril = $("#inpTxtAbr").val();
    abril = abril.replaceAll(",", "");
    var mayo = $("#inpTxtMay").val();
    mayo = mayo.replaceAll(",", "");
    var junio = $("#inpTxtJun").val();
    junio = junio.replaceAll(",", "");
    var julio = $("#inpTxtJul").val();
    julio = julio.replaceAll(",", "");
    var agosto = $("#inpTxtAgo").val();
    agosto = agosto.replaceAll(",", "");
    var sept = $("#inpTxtSep").val();
    sept = sept.replaceAll(",", "");
    var octubre = $("#inpTxtOct").val();
    octubre = octubre.replaceAll(",", "");
    var noviembre = $("#inpTxtNov").val();
    noviembre = noviembre.replaceAll(",", "");
    var diciembre = $("#inpTxtDic").val();
    diciembre = diciembre.replaceAll(",", "");
    var relacionLab = $("#selRelacion").val();
    var programaId = $("#selPrograma").val();
    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var reqJust = $("#reqJust").val();
    var fuenteFin = $("#selFuenteReq").val();
    var opcion = 2;
    if (costoAn.length >= 14) {
        $("#inpTxtCantAnual").addClass("errorClass");
        alert("la cantidad anual sobrepasa la capacidad del requerimiento");
        $("#nvoRequerimiento").removeAttr("disabled");
        return false;
    } else {
        $("#inpTxtCantAnual").removeClass("errorClass");
    }
    if (enero.trim() === "" || enero.trim() === ".") {
        $("#inpTxtEne").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtEne").removeClass("errorClass")
    }
    if (febrero.trim() === "" || febrero.trim() === ".") {
        $("#inpTxtFeb").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtFeb").removeClass("errorClass")
    }
    if (marzo.trim() === "" || marzo.trim() === ".") {
        $("#inpTxtMar").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMar").removeClass("errorClass")
    }
    if (abril.trim() === "" || abril.trim() === ".") {
        $("#inpTxtAbr").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAbr").removeClass("errorClass")
    }
    if (mayo.trim() === "" || mayo.trim() === ".") {
        $("#inpTxtMay").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMay").removeClass("errorClass")
    }
    if (junio.trim() === "" || junio.trim() === ".") {
        $("#inpTxtJun").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJun").removeClass("errorClass")
    }
    if (julio.trim() === "" || julio.trim() === ".") {
        $("#inpTxtJul").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJul").removeClass("errorClass")
    }
    if (agosto.trim() === "" || agosto.trim() === ".") {
        $("#inpTxtAgo").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAgo").removeClass("errorClass")
    }
    if (sept.trim() === "" || sept.trim() === ".") {
        $("#inpTxtSep").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtSep").removeClass("errorClass")
    }
    if (octubre.trim() === "" || octubre.trim() === ".") {
        $("#inpTxtOct").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtOct").removeClass("errorClass")
    }
    if (noviembre.trim() === "" || noviembre.trim() === ".") {
        $("#inpTxtNov").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtNov").removeClass("errorClass")
    }
    if (diciembre.trim() === "" || diciembre.trim() === ".") {
        $("#inpTxtDic").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtDic").removeClass("errorClass")
    }
    if (tipoGasto === "") {
        $("#nvoRequerimiento").removeAttr("disabled");
        alert("No existe tipo de gasto asignado para este grupo de partidas");
        $("#vwTipoGasto").css("border", "1px solid #F00");
        return false;
    } else {
        $("#vwTipoGasto").css("border", "1px solid #E5E5E5");
    }
    if (typeof tipoGasto != 'undefined') {
        tipoGasto = tipoGasto.split("-")[0];
        $("#txtAreaPart").css("border", "1px solid #E5E5E5");
    } else {
        cont++;
        $("#txtAreaPart").css("border", "1px solid #F00");
    }
    if (typeof isRelacionLaboral != 'undefined') {
        if (isRelacionLaboral === "true") {
            if (relacionLab === "0") {
                cont++;
                $("#selRelacion").css("border", "1px solid #F00");
            } else {
                $("#selRelacion").css("border", "1px solid #E5E5E5");
            }
        }
    }
    if (typeof partidaDesc !== 'undefined') {
        if (partidaDesc.trim() === "") {
            cont++;
            $("#txtAreaPart").css("border", "1px solid #F00");
        } else {
            $("#txtAreaPart").css("border", "1px solid #E5E5E5");
        }
    }
    if (cantidad === "0.00" || cantidad === "0.0") {
        cont++;
        $("#inpTxtCantidad").css("border", "1px solid #F00");
        $("#inpTxtCantAnual").css("border", "1px solid #F00");
    } else {
        $("#inpTxtCantidad").css("border", "1px solid #E5E5E5");
        $("#inpTxtCantAnual").css("border", "1px solid #E5E5E5");
    }
    if (articulo === "-1") {
        cont++;
        $("#ArtPartida").css("border", "1px solid #F00");
    } else {
        $("#ArtPartida").css("border", "1px solid #E5E5E5");
    }
    if (fuenteFin == "-1") {
        cont++;
        $("#selFuenteReq").css("border", "1px solid #F00");
    } else {
        $("#selFuenteReq").css("border", "1px solid #E5E5E5");
    }

    if (typeof reqJust !== 'undefined') {
        if (reqJust.trim() === "1") {
            if (justificacion.trim() === "") {
                cont++;
                $("#txtAreaJust").css("border", "1px solid #F00");
            } else {
                $("#txtAreaJust").css("border", "1px solid #E5E5E5");
            }
        }
    }
    if (partida === -1) {
        cont++;
        $("#selPartida").css("border", "1px solid #F00");
    } else {
        $("#selPartida").css("border", "1px solid #E5E5E5");
    }
    if (fuente == "-1") {
        cont++;
        $("#selFuenteRecurso").css("border", "1px solid #F00");
    } else {
        $("#selFuenteRecurso").css("border", "1px solid #E5E5E5");
    }
    if (costoUni == "") {
        cont++;
        $("#inpTxtCantUnit").css("border", "1px solid #F00");
    } else {
        $("#inpTxtCantUnit").css("border", "1px solid #E5E5E5");
    }
    if (costoUni == 0.0) {
        cont++;
        $("#inpTxtCantUnit").css("border", "1px solid #F00");
    } else {
        $("#inpTxtCantUnit").css("border", "1px solid #E5E5E5");
    }
    if (cont > 0) {
        alert("Favor de capturar los campos marcados");
        $("#nvoRequerimiento").removeAttr("disabled");
        return false;
    }
    if (selRow.length > 0) {
        accionId = selRow[0].innerHTML;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetRequerimientos.jsp",
            dataType: 'html',
            data: {
                partida: partida,
                partidaDesc: partidaDesc,
                justificacion: justificacion,
                cantidad: cantidad,
                proyecto: proyecto,
                costoUni: costoUni,
                costoAn: costoAn,
                enero: enero,
                febrero: febrero,
                marzo: marzo,
                abril: abril,
                mayo: mayo,
                junio: junio,
                julio: julio,
                agosto: agosto,
                sept: sept,
                octubre: octubre,
                noviembre: noviembre,
                diciembre: diciembre,
                opcion: opcion,
                relacionLab: relacionLab,
                programaId: programaId,
                ramoId: ramoId,
                metaId: metaId,
                tipoGasto: tipoGasto,
                fuente: fuente,
                articulo: articulo,
                accionId: accionId,
                tipoProy: tipoProy
            },
            success: function(responce) {
                $("#nvoRequerimiento").removeAttr("disabled");
                $('#mensaje').hide();
                var res = trim(responce.replace("<!DOCTYPE html>", ""));
                res = res.split("|");
                if (res.length > 1) {
                    if (res[0] !== "exito") {
                        $("#nvoRequerimiento").removeAttr("disabled");
                        res = res[0].split("\n");
                        res = res[0].split(":");
                        if (res.length > 1) {
                            alert(res[1]);
                            $("#nvoRequerimiento").removeAttr("disabled");
                        } else {
                            alert(res[0]);
                            $("#nvoRequerimiento").removeAttr("disabled");
                        }
                    } else {
                        responce = responce.replace(res[0] + "|", "");
                        $("#divRequerimientos #divReq").html(responce);
                        $("#infoRequerimiento").hide();
                    }
                }

            }
        });
    }
}

function borrarRequerimiento() {
    var confirmar = confirm("Est\u00e1 seguro que desea borrar este requerimiento del presupuesto?");
    if (confirmar) {
        var selRow = $("#tblRequerimientos .selected td");
        if (selRow.length > 0) {
            var selRoqAcc = $("#tblAcciones .selected td");
            var tipoProy = $("#selTipoProy").val();
            var accionId = selRoqAcc[0].innerHTML;
            var reqId = selRow[0].innerHTML;
            var partida = selRow[1].innerHTML;
            var ramoId = $("#selRamo").val();
            var programaId = $("#selPrograma").val();
            var metaId = $("#selMeta").val();
            var fuenteFin = $("#selFuenteReq").val();
            var tipoGasto = $("#selTipoGastoReq").val();
            var opcion = 4;
            var proyecto = $("#selProyecto").val();
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetRequerimientos.jsp",
                dataType: 'html',
                data: {
                    accionId: accionId,
                    requerimiento: reqId,
                    ramoId: ramoId,
                    programaId: programaId,
                    metaId: metaId,
                    opcion: opcion,
                    proyecto: proyecto,
                    fuente: fuenteFin,
                    tipoGasto: tipoGasto,
                    partida: partida,
                    tipoProy: tipoProy
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res === "cerrado") {
                        alert("Este ramo est\u00e1 cerrado para presupuestaci\u00f3n");
                    } else {
                        res = res.split("|");
                        if (res.length > 1) {
                            if (res[0] !== "exito") {
                                alert(res[0]);
                            }
                        }
                        response = response.replace(res[0] + "|", "");
                        $("#divRequerimientos #divReq").html(response);
                        $("#divRequerimientos div:last-child").show();
                    }
                }
            });
        } else {
            alert("Seleccione un requerimiento");
        }
    }
}

function redirectDefinicionMeta() {
    $("#frmReq").submit();
}

function cargarDatosProgramaMeta(ramo) {
    $("#selRamo").val(ramo);
    getProgramasOnChange();
}

function cargarDatosProyectoMeta(programa) {
    $("#selPrograma").val(programa);
    getProyectosByPrograma();
}

function cargarMetas(proyecto) {
    $("#selProyecto").val(proyecto);
    getMetasOnChange();
}

function cargarProgramaTransferencia(ramo) {
    $("#selRamo").val(ramo);
    getProgramasByRamoUsuario();
}

function cargarProyectoTransferencia(programa) {
    $("#selPrg").val(programa);
    getProyectosByProgramaUsuario();
}

function cargarMetaTransferencia(proyecto) {
    $("#selProy").val(proyecto);
    getMetasByProyectoUsuario();
}

function cargarAccionTransferencia(meta) {
    $("#selMeta").val(meta);
    getAccionByMetaUsuario();
}

function cargarPartidaTransferencia(accion) {
    $("#selAccion").val(accion);
    getPartidaByAccionUsuario();
}

function cargarRelLaboralTransferencia(partida) {
    $("#selPartida").val(partida);
    getRelLaboralByPartidaUsuario();
}

function cargarFuenteFinTransferencia(relLaboral) {
    if (relLaboral !== '0') {
        $("#selRelLaboral").val(relLaboral);
        getFuenteFinanciamientoByRelLaboralUsuario();
    }
}

function cargarDisponibleTransferencia(fuente) {
    $("#selFuente").val(fuente);
    getAsignadoPPTOByMes();
}

function editarRequerimiento() {
    $("#edtRequerimiento").prop("disabled", true);
    var cont = 0;
    var reqJust = $("#reqJust").val();
    var isRelacionLaboral = $('#isRelLaboral').val();
    var proyecto = $("#selProyecto").val();
    var selRow = $("#tblRequerimientos .selected td");
    var selAcc = $("#tblAcciones .selected td");
    var requerimiento;
    var accionId;
    var articulo = $("#ArtPartida").val();
    var fuente = $("#selFuenteRecurso").val();
    var tipoGasto = $("#selTipoGasto").val();
    tipoGasto = tipoGasto.split("-")[0];
    var partida = $("#selPartida").val();
    var partidaDesc = $("#txtAreaPart").val();
    //partidaDesc = partidaDesc.toUpperCase();
    if (typeof partidaDesc !== 'undefined') {
        partidaDesc = partidaDesc.toUpperCase();
    }
    var fuenteFin = $("#selFuenteReq").val();
    var justificacion = $("#txtAreaJust").val();
    justificacion = justificacion.toUpperCase();
    var cantidad = $("#inpTxtCantidad").val();
    cantidad = cantidad.replaceAll(",", "");
    var costoUni = $("#inpTxtCantUnit").val();
    costoUni = costoUni.replaceAll(",", "");
    var costoAn = $("#inpTxtCantAnual").val();
    costoAn = costoAn.replaceAll(",", "");
    var enero = $("#inpTxtEne").val();
    enero = enero.replaceAll(",", "");
    var febrero = $("#inpTxtFeb").val();
    febrero = febrero.replaceAll(",", "");
    var marzo = $("#inpTxtMar").val();
    marzo = marzo.replaceAll(",", "");
    var abril = $("#inpTxtAbr").val();
    abril = abril.replaceAll(",", "");
    var mayo = $("#inpTxtMay").val();
    mayo = mayo.replaceAll(",", "");
    var junio = $("#inpTxtJun").val();
    junio = junio.replaceAll(",", "");
    var julio = $("#inpTxtJul").val();
    julio = julio.replaceAll(",", "");
    var agosto = $("#inpTxtAgo").val();
    agosto = agosto.replaceAll(",", "");
    var sept = $("#inpTxtSep").val();
    sept = sept.replaceAll(",", "");
    var octubre = $("#inpTxtOct").val();
    octubre = octubre.replaceAll(",", "");
    var noviembre = $("#inpTxtNov").val();
    noviembre = noviembre.replaceAll(",", "");
    var diciembre = $("#inpTxtDic").val();
    diciembre = diciembre.replaceAll(",", "");
    var relacionLab = $("#selRelacion").val();
    var programaId = $("#selPrograma").val();
    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var opcion = 3;
    if (costoAn.length >= 14) {
        $("#inpTxtCantAnual").addClass("errorClass");
        alert("la cantidad anual sobrepasa la capacidad del requerimiento");
        $("#edtRequerimiento").removeAttr("disabled");
        return false;
    } else {
        $("#inpTxtCantAnual").removeClass("errorClass");
    }
    if (enero.trim() === "" || enero.trim() === ".") {
        $("#inpTxtEne").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtEne").removeClass("errorClass")
    }
    if (febrero.trim() === "" || febrero.trim() === ".") {
        $("#inpTxtFeb").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtFeb").removeClass("errorClass")
    }
    if (marzo.trim() === "" || marzo.trim() === ".") {
        $("#inpTxtMar").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMar").removeClass("errorClass")
    }
    if (abril.trim() === "" || abril.trim() === ".") {
        $("#inpTxtAbr").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAbr").removeClass("errorClass")
    }
    if (mayo.trim() === "" || mayo.trim() === ".") {
        $("#inpTxtMay").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMay").removeClass("errorClass")
    }
    if (junio.trim() === "" || junio.trim() === ".") {
        $("#inpTxtJun").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJun").removeClass("errorClass")
    }
    if (julio.trim() === "" || julio.trim() === ".") {
        $("#inpTxtJul").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJul").removeClass("errorClass")
    }
    if (agosto.trim() === "" || agosto.trim() === ".") {
        $("#inpTxtAgo").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAgo").removeClass("errorClass")
    }
    if (sept.trim() === "" || sept.trim() === ".") {
        $("#inpTxtSep").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtSep").removeClass("errorClass")
    }
    if (octubre.trim() === "" || octubre.trim() === ".") {
        $("#inpTxtOct").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtOct").removeClass("errorClass")
    }
    if (noviembre.trim() === "" || noviembre.trim() === ".") {
        $("#inpTxtNov").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtNov").removeClass("errorClass")
    }
    if (diciembre.trim() === "" || diciembre.trim() === ".") {
        $("#inpTxtDic").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtDic").removeClass("errorClass")
    }
    var tipoProy = $("#selTipoProy").val();
    if (typeof isRelacionLaboral != 'undefined') {
        if (isRelacionLaboral === "true") {
            if (relacionLab === "0") {
                cont++;
                $("#selRelacion").css("border", "1px solid #F00");
            } else {
                $("#selRelacion").css("border", "1px solid #E5E5E5");
            }
        }
    }

    if (typeof partidaDesc !== 'undefined') {
        if (partidaDesc.trim() === "") {
            cont++;
            $("#txtAreaPart").css("border", "1px solid #F00");
        } else {
            $("#txtAreaPart").css("border", "1px solid #E5E5E5");
        }
    }
    if (cantidad === "0.00" || cantidad === "0.0") {
        cont++;
        $("#inpTxtCantidad").css("border", "1px solid #F00");
        $("#inpTxtCantAnual").css("border", "1px solid #F00");
    } else {
        $("#inpTxtCantidad").css("border", "1px solid #E5E5E5");
        $("#inpTxtCantAnual").css("border", "1px solid #E5E5E5");
    }
    if (articulo == "-1") {
        cont++;
        $("#ArtPartida").css("border", "1px solid #F00");
    } else {
        $("#ArtPartida").css("border", "1px solid #E5E5E5");
    }
    if (typeof reqJust !== 'undefined') {
        if (reqJust === "1") {
            if (justificacion.trim() === "") {
                cont++;
                $("#txtAreaJust").css("border", "1px solid #F00");
            } else
                $("#txtAreaJust").css("border", "1px solid #E5E5E5");
        }
    }
    if (partida == -1) {
        cont++;
        $("#selPartida").css("border", "1px solid #F00");
    } else {
        $("#selPartida").css("border", "1px solid #E5E5E5");
    }
    if (costoUni == "") {
        cont++;
        $("#inpTxtCantUnit").css("border", "1px solid #F00");
    } else {
        $("#inpTxtCantUnit").css("border", "1px solid #E5E5E5");
    }
    if (costoUni == 0.0) {
        cont++;
        $("#inpTxtCantUnit").css("border", "1px solid #F00");
    } else {
        $("#inpTxtCantUnit").css("border", "1px solid #E5E5E5");
    }
    if (cont > 0) {
        alert("Favor de capturar los campos marcados");
        $("#edtRequerimiento").removeAttr("disabled");
        return false;
    }
    if (selRow.length > 0) {
        requerimiento = selRow[0].innerHTML;
        accionId = selAcc[0].innerHTML;
        var partida = $("#selPartida").val();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetRequerimientos.jsp",
            dataType: 'html',
            data: {
                partida: partida,
                partidaDesc: partidaDesc,
                justificacion: justificacion,
                cantidad: cantidad,
                proyecto: proyecto,
                costoUni: costoUni,
                costoAn: costoAn,
                enero: enero,
                febrero: febrero,
                marzo: marzo,
                abril: abril,
                mayo: mayo,
                junio: junio,
                julio: julio,
                agosto: agosto,
                sept: sept,
                octubre: octubre,
                noviembre: noviembre,
                diciembre: diciembre,
                opcion: opcion,
                relacionLab: relacionLab,
                requerimiento: requerimiento,
                programaId: programaId,
                ramoId: ramoId,
                metaId: metaId,
                tipoGasto: tipoGasto,
                articulo: articulo,
                accionId: accionId,
                tipoProy: tipoProy,
                estatus: 'V'
            },
            success: function(responce) {
                $('#mensaje').hide();
                var res = trim(responce.replace("<!DOCTYPE html>", ""));
                res = res.split("|");
                if (res.length > 1)
                    if (res[0] !== "exito") {
                        res = res[0].split("\n");
                        res = res[0].split(":");
                        $("#edtRequerimiento").removeAttr("disabled");
                        if (res.length > 1) {
                            alert(res[1]);
                            $("#edtRequerimiento").removeAttr("disabled");
                        } else {
                            alert(res[0]);
                            $("#edtRequerimiento").removeAttr("disabled");
                        }
                    } else {
                        responce = responce.replace(res[0] + "|", "");
                        $("#divRequerimientos #divReq").html(responce);
                        $("#infoRequerimiento").hide();
                    }
                $("#edtRequerimiento").removeAttr("disabled");
            }
        });
    }
}

function changeRelacionLab(isRelacion) {
    var tipoGasto = $("#selTipoGasto").val();
    var tipoPartida = $("#selTipoPartida").val();
    if (tipoGasto.trim() === "") {
        alert("No existe tipo de gasto asignado para este subsubgrupo de partidas");
        $("#vwTipoGasto").addClass("errorClass");
    } else {
        $("#vwTipoGasto").removeClass("errorClass");
    }
    if (tipoPartida === "false") {
        $("#tdDescrRequ").hide();
    } else {
        $("#tdDescrRequ").show();
    }
    $("#vwTipoGasto").val(tipoGasto);
    if (isRelacion == "false") {
        $("#relTd").hide();
        $("#selRelacion").val(-1);
    } else {
        $("#relTd").show();
        $("#selRelacion").val(-1);
    }
}

function calculaCosto() {
    var calc = $(".capt-mes");
    var suma = 0;
    var costoAn = 0;
    var valorSinComas;
    for (var it = 0; it < calc.length; it++) {
        valorSinComas = calc[it].value.replaceAll(",", "");
        valorSinComas = Math.abs(valorSinComas);
        suma += parseFloat(valorSinComas);
    }
    $("#inpTxtCantidad").val(addCommas(suma.toFixed(2)));
    costoAn = suma * $("#inpTxtCantUnit").val().replaceAll(",", "");
    costoAn = Math.abs(costoAn);
    $("#inpTxtCantAnual").val(addCommas(costoAn.toFixed(2)));
}

function calculaCosto() {
    var calc = $(".capt-mes");
    var suma = 0;
    var costoAn = 0;
    var valorSinComas;
    for (var it = 0; it < calc.length; it++) {
        valorSinComas = calc[it].value.replaceAll(",", "");
        valorSinComas = Math.abs(valorSinComas);
        suma += parseFloat(valorSinComas);
    }
    $("#inpTxtCantidad").val(addCommas(suma.toFixed(2)));
    costoAn = suma * $("#inpTxtCantUnit").val().replaceAll(",", "");
    costoAn = Math.abs(costoAn);
    $("#inpTxtCantAnual").val(addCommas(costoAn.toFixed(2)));
}

function saveAccion() {
    $("#btnSaveAccion").prop("disabled", true);
    var cont = 0;
    var isTipoCompro = $("#tipoCompro").val();
    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionDesc = $("#txtDescrAcc").val();
    accionDesc = accionDesc.toUpperCase();
    var depto = $("#selUnidadEj").val();
    var medida = $("#selMedida").val();
    var grupoPob = $("#selGrupo").val();
    var municipio = $("#selMunicipio").val();
    var localidad = $("#selLocalidad").val();
    var lineaSect = $("#selLineaSect").val();
    var lineaPed = $("#selLineaPed").val();
    var calculo = $("#selCalculo").val();
    var benefMuj = $("#accMuj").val();
    var benefHom = $("#accHom").val();
    var programaId = $("#selPrograma").val();
    //var proyectoId = $("#selsaveAccionProyecto").val();
    var proyectoId = $("#selProyecto").val();
    var tipoAccion = $("#selTipoAccion").val();
    var optAcc = 1;
    if (isTipoCompro !== "N") {
        if ((parseInt(benefHom) + parseInt(benefMuj)) === 0) {
            cont++;
            $("#accMuj").css("border", "1px solid #F00");
            $("#accHom").css("border", "1px solid #F00");
        } else {
            $("#accMuj").css("border", "1px solid #E5E5E5");
            $("#accHom").css("border", "1px solid #E5E5E5");
        }
    }
    if (isTipoCompro !== "N") {
        if (grupoPob === "-1") {
            cont++;
            $("#selGrupo").css("border", "1px solid #F00");
        } else {
            $("#selGrupo").css("border", "1px solid #E5E5E5");
        }
    }

    if (depto === "-1") {
        cont++;
        $("#selUnidadEj").css("border", "1px solid #F00");
    } else {
        $("#selUnidadEj").css("border", "1px solid #E5E5E5");
    }
    if (lineaPed === "-1") {
        cont++;
        $("#selLineaPed").css("border", "1px solid #F00");
    } else {
        $("#selLineaPed").css("border", "1px solid #E5E5E5");
    }
    if (medida === "-1") {
        cont++;
        $("#selMedida").css("border", "1px solid #F00");
    } else {
        $("#selMedida").css("border", "1px solid #E5E5E5");
    }
    if (calculo == "-1") {
        cont++;
        $("#selCalculo").css("border", "1px solid #F00");
    } else {
        $("#selCalculo").css("border", "1px solid #E5E5E5");
    }
    if (accionDesc == "") {
        cont++;
        $("#txtDescrAcc").css("border", "1px solid #F00");
    } else {
        $("#txtDescrAcc").css("border", "1px solid #E5E5E5");
    }
    if (municipio == -1) {
        cont++;
        $("#selMunicipio").css("border", "1px solid #F00");
    } else {
        $("#selMunicipio").css("border", "1px solid #E5E5E5");
    }
    if (localidad == -1) {
        cont++;
        $("#selLocalidad").css("border", "1px solid #F00");
    } else {
        $("#selLocalidad").css("border", "1px solid #E5E5E5");
    }
    if (cont > 0) {
        alert("Favor de capturar los campos marcados");
        $("#btnSaveAccion").removeAttr("disabled");
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetAcciones.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            accionDesc: accionDesc,
            depto: depto,
            medida: medida,
            grupoPob: grupoPob,
            municipio: municipio,
            localidad: localidad,
            lineaSect: lineaSect,
            lineaPed: lineaPed,
            calculo: calculo,
            optAcc: optAcc,
            //accionId: accionId,
            benefMuj: benefMuj,
            benefHom: benefHom,
            programaId: programaId,
            proyectoId: proyectoId,
            tipoAccion: tipoAccion
        },
        success: function(responce) {
            $('#mensaje').hide();
            $("#listAccion").html(responce);
            $("#btnSaveAccion").removeAttr("disabled");
        },
        error: function(responce) {
            $("#btnSaveAccion").removeAttr("disabled");
        }
    });
    $("#infoAccion").hide();
    $("#infoAccion").html("");
}

function editAccion(accionId) {

    $("#btnSaveAccion").prop("disabled", true);

    var cont = 0;
    var isTipoCompro = $("#tipoCompro").val();
    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionDesc = $("#txtDescrAcc").val();
    accionDesc = accionDesc.toUpperCase();
    var depto = $("#selUnidadEj").val();
    var medida = $("#selMedida").val();
    var grupoPob = $("#selGrupo").val();
    var municipio = $("#selMunicipio").val();
    var localidad = $("#selLocalidad").val();
    var lineaSect = $("#selLineaSect").val();
    var lineaPed = $("#selLineaPed").val();
    var calculo = $("#selCalculo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var benefMuj = $("#accMuj").val();
    var benefHom = $("#accHom").val();
    var tipoAccion = $("#selTipoAccion").val();
    var optAcc = 2;
    if (isTipoCompro !== "N") {
        if ((parseInt(benefHom) + parseInt(benefMuj)) === 0) {
            cont++;
            $("#accMuj").css("border", "1px solid #F00");
            $("#accHom").css("border", "1px solid #F00");
        } else {
            $("#accMuj").css("border", "1px solid #E5E5E5");
            $("#accHom").css("border", "1px solid #E5E5E5");
        }
    }
    if (isTipoCompro !== "N") {
        if (grupoPob === "-1") {
            cont++;
            $("#selGrupo").css("border", "1px solid #F00");
        } else {
            $("#selGrupo").css("border", "1px solid #E5E5E5");
        }
    }

    if (depto === "-1") {
        cont++;
        $("#selUnidadEj").css("border", "1px solid #F00");
    } else {
        $("#selUnidadEj").css("border", "1px solid #E5E5E5");
    }
    if (lineaPed === "-1") {
        cont++;
        $("#selLineaPed").css("border", "1px solid #F00");
    } else {
        $("#selLineaPed").css("border", "1px solid #E5E5E5");
    }
    if (medida === "-1") {
        cont++;
        $("#selMedida").css("border", "1px solid #F00");
    } else {
        $("#selMedida").css("border", "1px solid #E5E5E5");
    }
    if (calculo == "-1") {
        cont++;
        $("#selCalculo").css("border", "1px solid #F00");
    } else {
        $("#selCalculo").css("border", "1px solid #E5E5E5");
    }
    if (accionDesc == "") {
        cont++;
        $("#txtDescrAcc").css("border", "1px solid #F00");
    } else {
        $("#txtDescrAcc").css("border", "1px solid #E5E5E5");
    }
    if (municipio == -1) {
        cont++;
        $("#selMunicipio").css("border", "1px solid #F00");
    } else {
        $("#selMunicipio").css("border", "1px solid #E5E5E5");
    }
    if (localidad == -1) {
        cont++;
        $("#selLocalidad").css("border", "1px solid #F00");
    } else {
        $("#selLocalidad").css("border", "1px solid #E5E5E5");
    }
    if (cont > 0) {
        alert("Favor de capturar los campos marcados");
        $("#btnSaveAccion").removeAttr("disabled");
        return false;
    }
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetAcciones.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            accionDesc: accionDesc,
            depto: depto,
            medida: medida,
            grupoPob: grupoPob,
            municipio: municipio,
            localidad: localidad,
            lineaSect: lineaSect,
            lineaPed: lineaPed,
            calculo: calculo,
            optAcc: optAcc,
            accionId: accionId,
            programaId: programaId,
            benefMuj: benefMuj,
            benefHom: benefHom,
            proyectoId: proyectoId,
            tipoAccion: tipoAccion
        },
        success: function(responce) {
            $('#mensaje').hide();
            $("#listAccion").html(responce);
            $("#btnSaveAccion").removeAttr("disabled");
        },
        error: function(responce) {
            $("#btnSaveAccion").removeAttr("disabled");
        }
    });
    $("#infoAccion").hide();
    $("#infoAccion").html("");
}

function insertarAccion() {
    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var tipoProy = $("#selTipoProy").val();
    var optAccion = 1;
    var ramoDescr = $("#descrRamo").val();
    var programaDescr = $("#descrPrograma").val();
    var proyectoDescr = $("#descrProyecto").val();
    var metaDescr = $("#descrMeta").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetInfoAccion.jsp",
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            optAccion: optAccion,
            programaId: programaId,
            proyectoId: proyectoId,
            tipoProy: tipoProy,
            ramoDescr: ramoDescr,
            programaDescr: programaDescr,
            proyectoDescr: proyectoDescr,
            metaDescr: metaDescr
        },
        success: function(responce) {
            $('#mensaje').hide();
            var result = trim(responce.replace("<!DOCTYPE html>", ""));
            if (result === "cerrado") {
                alert("El presupuesto se ha cerrado para este ramo");
                return false;
            } else {
                $("#infoAccion").html(responce);
                $("#infoAccion").show();
            }
        }
    });

}

function editarAccion() {
    var selRow = $("#tblAcciones .selected td");
    if (selRow.length > 0) {
        var accionId = selRow[0].innerHTML;
        var obra = selRow[2].innerHTML;
        var ramoId = $("#selRamo").val();
        var metaId = $("#selMeta").val();
        var programaId = $("#selPrograma").val();
        var proyectoId = $("#selProyecto").val();
        var optAccion = 2;
        var tipoProy = $("#selTipoProy").val();
        var ramoDescr = $("#descrRamo").val();
        var programaDescr = $("#descrPrograma").val();
        var proyectoDescr = $("#descrProyecto").val();
        var metaDescr = $("#descrMeta").val();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInfoAccion.jsp",
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                optAccion: optAccion,
                programaId: programaId,
                proyectoId: proyectoId,
                accionId: accionId,
                tipoProy: tipoProy,
                ramoDescr: ramoDescr,
                programaDescr: programaDescr,
                proyectoDescr: proyectoDescr,
                metaDescr: metaDescr,
                obra: obra
            },
            success: function(responce) {
                var res = trim(responce.replace("<!DOCTYPE html>", ""));
                $('#mensaje').hide();
                if (res === "cerrado") {
                    alert("Este ramo est\u00e1  cerrado para presupuestaci\u00f3n");
                } else {
                    $("#infoAccion").html(responce);
                    $("#infoAccion").show();
                }
            }
        });
    }
}
function consultarAccion() {
    var selRow = $("#tblAcciones .selected td");
    if (selRow.length > 0) {
        var accionId = selRow[0].innerHTML;
        var obra = selRow[2].innerHTML;
        var ramoId = $("#selRamo").val();
        var metaId = $("#selMeta").val();
        var programaId = $("#selPrograma").val();
        var proyectoId = $("#selProyecto").val();
        var optAccion = 2;
        var tipoProy = $("#selTipoProy").val();
        var ramoDescr = $("#descrRamo").val();
        var programaDescr = $("#descrPrograma").val();
        var proyectoDescr = $("#descrProyecto").val();
        var metaDescr = $("#descrMeta").val();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInfoAccion.jsp",
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                optAccion: optAccion,
                programaId: programaId,
                proyectoId: proyectoId,
                accionId: accionId,
                tipoProy: tipoProy,
                programaDescr: programaDescr,
                proyectoDescr: proyectoDescr,
                metaDescr: metaDescr,
                ramoDescr: ramoDescr,
                consulta: 1,
                obra: obra
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#infoAccion").html(responce);
            }
        });
        $("#infoAccion").show();
    }
}

function cambioCompromiso() {
    var compromiso = $("#selCompromiso").val();
    if (compromiso == "S") {
        $("#inTxtHom").prop("disabled", false);
        $("#inTxtMuj").prop("disabled", false);
        $("#selGrupoPob").prop("disabled", false);
    } else {
        $("#inTxtHom").prop("disabled", true);
        $("#inTxtMuj").prop("disabled", true);
        $("#selGrupoPob").prop("disabled", true);
        $("#selGrupoPob").val(-1);
        $("#inTxtHom").val("");
        $("#inTxtMuj").val("");
    }
}

function borrarAccion() {
    var optAccion = 3;
    var metaRow = $("#tblAcciones .selected td");
    if (metaRow.length > 0) {
        var conf = confirm("¿Est\u00e1 seguro que desea eliminar est\u00e1 acci\u00f3n?");
        if (conf) {
            var accion = metaRow[0].innerHTML;
            var ramo = $("#selRamo").val();
            var programa = $("#selPrograma").val();
            var meta = $("#selMeta").val();
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetAcciones.jsp",
                dataType: 'html',
                data: {
                    ramoId: ramo,
                    programaId: programa,
                    metaId: meta,
                    accionId: accion,
                    optAcc: optAccion
                },
                success: function(response) {
                    var resultado = trim(response.replace("<!DOCTYPE html>", ""));
                    if (resultado === "cerrado") {
                        alert("Este ramo est\u00e1 cerrado para presupuestaci\u00f3n");
                    } else {
                        if (resultado.substring(0, 6) === "borrar") {
                            alert("Esta acci\u00f3n contiene requerimientos que debe eliminar primero");
                        }
                        if (resultado.substring(0, 7) === "integro") {
                            alert("Esta acci\u00f3n se utiliza en la definici\u00f3n de plantilla.");
                        }
                        response = trim(response.replace("integro", ""));
                        response = trim(response.replace("borrar", ""));
                        $("#listAccion").html(response);
                    }
                }
            });
        } else {
            return false;
        }
    } else {
        alert("Seleccione una acci\u00f3n para continuar");
    }
}

function deleteMeta() {
    var optMeta = 2;
    var metaRow = $("#tblMetas .selected td");
    if (metaRow.length > 0) {
        if (confirm("¿Est\u00e1 seguro que desea eliminar est\u00e1 meta?")) {
            var metaId = metaRow[0].innerHTML;
            var ramoId = $("#selRamo").val();
            var programaId = $("#selPrograma").val();
            var proyectoId = $("#selProyecto").val();
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            var arrayProy = proyectoId.split(",");
            proyectoId = arrayProy[0];
            var tipoProyecto = arrayProy[1];
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetMetas.jsp",
                dataType: 'html',
                data: {
                    ramoId: ramoId,
                    programaId: programaId,
                    proyectoId: proyectoId,
                    optMeta: optMeta,
                    metaId: metaId,
                    tipoProyecto: tipoProyecto
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var resultado = trim(response.replace("<!DOCTYPE html>", ""));
                    if (resultado.substring(0, 6) == "borrar") {
                        alert("Est\u00e1 meta contiene acciones que debe eliminar primero");
                    }
                    response = trim(response.replace("borrar", ""));
                    $("#div-listado").html(response);
                }
            });
        }
    } else {
        alert("Debe de seleccionar una meta para continuar");
    }
}

function actualizarMeta(metaId) {

    $("#guardarMeta").prop("disabled", true);

    var cont = 0;
    var checkO = $("#selObraP").val();
    var obra;
    var ramoId = $("#selRamo").val();
    var arrayProy;
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    arrayProy = proyectoId.split(",");
    proyectoId = arrayProy[0];
    var tipoProyecto = arrayProy[1];
    var medida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromiso").val();
    var lineaPed = $("#selLineaPed").val();
    var lineaSect = $("#selLineaSect").val();
    var objComp = $("#txtAreaMeta").val();
    objComp = objComp.toUpperCase();
    var ponderado = $("#selPonderacion").val();
    var autorizacion = "N";
    var principal = "N";
    var optMeta = 4;
    var criterio = $("#selTrasver").val();

    if (criterio === "-1") {
        cont++;
        $("#selTrasver").css("border", "1px solid #F00");
    } else {
        $("#selTrasver").css("border", "1px solid #E5E5E5");
    }
    if (ponderado === "-1") {
        cont++;
        $("#selPonderacion").css("border", "1px solid #F00");
    } else {
        $("#selPonderacion").css("border", "1px solid #E5E5E5");
    }
    if (lineaPed === "-1") {
        cont++;
        $("#selLineaPed").css("border", "1px solid #F00");
    } else {
        $("#selLineaPed").css("border", "1px solid #E5E5E5");
    }
    if (medida === "-1") {
        cont++;
        $("#selMedida").css("border", "1px solid #F00");
    } else {
        $("#selMedida").css("border", "1px solid #E5E5E5");
    }
    if (compromiso === "-1") {
        cont++;
        $("#selCompromiso").css("border", "1px solid #F00");
    } else {
        $("#selCompromiso").css("border", "1px solid #E5E5E5");
    }
    if (calculo === "-1") {
        cont++;
        $("#selCalculo").css("border", "1px solid #F00");
    } else {
        $("#selCalculo").css("border", "1px solid #E5E5E5");
    }

    if (clasificacion == "-1") {
        cont++;
        $("#selClasificacion").css("border", "1px solid #F00");
    } else {
        $("#selClasificacion").css("border", "1px solid #E5E5E5");
    }
    if (objComp == "") {
        cont++;
        $("#txtAreaMeta").css("border", "1px solid #F00");
    } else {
        $("#selClasificacion").css("border", "1px solid #E5E5E5");
    }
    ;
    if (cont > 0) {
        alert("Favor de capturar los campos marcados");
        $("#actualizarMeta").removeAttr("disabled");
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetMetas.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            programaId: programaId,
            proyectoId: proyectoId,
            medida: medida,
            calculo: calculo,
            clasificacion: clasificacion,
            compromiso: compromiso,
            lineaPed: lineaPed,
            lineaSect: lineaSect,
            autorizacion: autorizacion,
            principal: principal,
            optMeta: optMeta,
            metaId: metaId,
            objComp: objComp,
            ponderado: ponderado,
            criterio: criterio,
            tipoProyecto: tipoProyecto,
            obra: checkO
        },
        success: function(response) {
            $('#mensaje').hide();
            $("#div-listado").html(response);
            $("#actualizarMeta").removeAttr("disabled");
        },
        error: function(response) {
            $('#mensaje').hide();
            $("#div-listado").html(response);
            $("#actualizarMeta").removeAttr("disabled");
        }
    });
    $("#infoMeta").hide();
    $("#infoMeta").html("");
}

function guardarMeta() {

    $("#guardarMeta").prop("disabled", true);

    var cont = 0;
    var checkO = $("#selObraP").val();
    var obra;
    var arrayTipoProyecto;
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    arrayTipoProyecto = proyectoId.split(",");
    proyectoId = arrayTipoProyecto[0];
    var tipoProyecto = arrayTipoProyecto[1];
    var medida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromiso").val();
    var lineaPed = $("#selLineaPed").val();
    var lineaSect = $("#selLineaSect").val();
    var autorizacion = "N";
    var objComp = $("#txtAreaMeta").val();
    objComp = objComp.toUpperCase();
    var ponderado = $("#selPonderacion").val();
    var principal = "N";
    var criterio = $("#selTrasver").val();
    var optMeta = 3;

    if (criterio === "-1") {
        cont++;
        $("#selTrasver").css("border", "1px solid #F00");
    } else {
        $("#selTrasver").css("border", "1px solid #E5E5E5");
    }
    if (ponderado === "-1") {
        cont++;
        $("#selPonderacion").css("border", "1px solid #F00");
    } else {
        $("#selPonderacion").css("border", "1px solid #E5E5E5");
    }
    if (lineaPed === "-1") {
        cont++;
        $("#selLineaPed").css("border", "1px solid #F00");
    } else {
        $("#selLineaPed").css("border", "1px solid #E5E5E5");
    }
    if (medida === "-1") {
        cont++;
        $("#selMedida").css("border", "1px solid #F00");
    } else {
        $("#selMedida").css("border", "1px solid #E5E5E5");
    }
    if (compromiso === "-1") {
        cont++;
        $("#selCompromiso").css("border", "1px solid #F00");
    } else {
        $("#selCompromiso").css("border", "1px solid #E5E5E5");
    }
    if (calculo === "-1") {
        cont++;
        $("#selCalculo").css("border", "1px solid #F00");
    } else {
        $("#selCalculo").css("border", "1px solid #E5E5E5");
    }

    if (clasificacion == "-1") {
        cont++;
        $("#selClasificacion").css("border", "1px solid #F00");
    } else {
        $("#selClasificacion").css("border", "1px solid #E5E5E5");
    }
    if (objComp == "") {
        cont++;
        $("#txtAreaMeta").css("border", "1px solid #F00");
    } else {
        $("#txtAreaMeta").css("border", "1px solid #E5E5E5");
    }
    if ($("#chkAutorizacion").is(':checked'))
        autorizacion = "S";
    if ($("#chkPrincipal").is(':checked'))
        principal = "S";
    if (cont > 0) {
        alert("Favor de capturar los campos marcados");
        $("#guardarMeta").removeAttr("disabled");
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetMetas.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            programaId: programaId,
            proyectoId: proyectoId,
            medida: medida,
            calculo: calculo,
            clasificacion: clasificacion,
            compromiso: compromiso,
            lineaPed: lineaPed,
            lineaSect: lineaSect,
            autorizacion: autorizacion,
            principal: principal,
            optMeta: optMeta,
            tipoProyecto: tipoProyecto,
            objComp: objComp,
            ponderado: ponderado,
            criterio: criterio,
            obra: checkO
        },
        success: function(response) {
            $('#mensaje').hide();
            $("#div-listado").html(response);
            $("#guardarMeta").removeAttr("disabled");
            //$("#tblMetas").html(response);
        },
        error: function(response) {
            $('#mensaje').hide();
            $("#div-listado").html(response);
            $("#guardarMeta").removeAttr("disabled");
            //$("#tblMetas").html(response);
        }
    });

    $("#infoMeta").hide();
    $("#infoMeta").html("");
}

function insertarMeta() {
    var optMeta = 1;
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var ramoDescr = $("#selRamo option:selected").text();
    var programaDescr = $("#selPrograma option:selected").text();
    var proyectoDescr = $("#selProyecto option:selected").text();
    var arrayTipo;
    var tipoProyecto;
    arrayTipo = proyectoId.split(",");
    tipoProyecto = arrayTipo[1];
    proyectoId = arrayTipo[0];
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxAdministrarMeta.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            programaId: programaId,
            proyectoId: proyectoId,
            optMeta: optMeta,
            tipoProyecto: tipoProyecto,
            ramoDescr: ramoDescr,
            programaDescr: programaDescr,
            proyectoDescr: proyectoDescr
        },
        success: function(response) {
            $('#mensaje').hide();
            $("#infoMeta").html(response);
        }
    });
    $("#infoMeta").show();
}

function cerrarInfoMeta() {
    $("#infoMeta").hide();
    $("#infoMeta").html("");
}

function cerrarInfoAccion() {
    $("#infoAccion").hide();
    $("#infoAccion").html("");
}

function mostrarLineaPED() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var arrayProy = proyectoId.split(",");
    proyectoId = arrayProy[0];
    var tipoProy = arrayProy[1];
    var opcionPed = 1;
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetLineasProyecto.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            programaId: programaId,
            proyectoId: proyectoId,
            opcionPed: opcionPed,
            tipoProy: tipoProy
        },
        success: function(responce) {
            $('#mensaje').hide();
            $("#ped-lineas").html(responce);
        }
    });
    $("#captura-dep").show();
    $(".btn-guardar").prop("disabled", true);
}

function getLineaSectorial() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyecto = $("#selProyecto").val();
    var arrayProy = proyecto.split(",");
    proyecto = arrayProy[0];
    var est = $("#selLineaPed").val();
    if (est != "-1") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetLineaSectorialOnChange.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyecto,
                estrategia: est
            },
            success: function(responce) {
                $("#selLineaSect").html(responce);
            }
        });
    } else {
        $("#selLineaSect").html("<option value='-1'> -- Seleccione una línea sectorial -- </option>");
    }
}

function mostrarLineaSectorial(estrategiaId, estrategiaDescr) {

    if (estrategiaId == "") {
        estrategiaId = $(".selected-linea select").val();
        estrategiaDescr = $(".selected-linea select option:selected").text();
        var arrayDescr = estrategiaDescr.split("-");
        estrategiaDescr = arrayDescr[1];
    }

    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var arrayProy = proyectoId.split(",");
    proyectoId = arrayProy[0];
    var tipoProy = arrayProy[1];
    var opcionPed = 1;
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetLineasSectorial.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            programaId: programaId,
            proyectoId: proyectoId,
            opcionPed: opcionPed,
            estrategia: estrategiaId,
            tipoProy: tipoProy,
            estrategiaDescr: estrategiaDescr
        },
        success: function(responce) {
            $('#mensaje').hide();
            $("#sectorial-lineas").html(responce);

        }
    });

    $('#titLineaSectlLineaPed').val(estrategiaId + "-" + estrategiaDescr);
    $("#captura-sect").show();
    $(".btn-guardar").prop("disabled", true);
}

function insertarLineaPed() {
    var selectNew = $("#btnSectorial").val();
    if (selectNew != "") {
        var ramoId = $("#selRamo").val();
        var programaId = $("#selPrograma").val();
        var proyectoId = $("#selProyecto").val();
        var arrayProy = proyectoId.split(",");
        proyectoId = arrayProy[0];
        var opcionPed = 2;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetLineasProyecto.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyectoId,
                opcionPed: opcionPed
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#ped-lineas").html(responce);

            },
            error: function(responce) {
                alert(responce);
            }
        });
    } else {
        alert("Para insertar nuevas l\u00edneas ped se requerir\u00e1 guardar la ultima insertada");
    }
    $(".btn-guardar").prop("disabled", true);
}

function insertarFuentefin() {
    var ramoId = $("#selRamo").val();
    var accionRow = $("#tblAcciones .selected td");
    var metaId = $("#selMeta").val();
    var accionId;
    var opcion = 2;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetFuenteFinanciamiento.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                accionId: accionId,
                metaId: metaId,
                opcion: opcion
            },
            success: function(respoce) {
                $('#mensaje').hide();
                $("#ped-lineas").html(respoce);
            }
        });
    }
}

function insertarTipoGasto() {
    var ramoId = $("#selRamo").val();
    var accionRow = $("#tblAcciones .selected td");
    var metaId = $("#selMeta").val();
    var opcion = 2;
    var accionId;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetTipoGasto.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                accionId: accionId,
                metaId: metaId,
                opcion: opcion
            },
            success: function(respoce) {
                $('#mensaje').hide();
                $("#sectorial-lineas div").html(respoce);
            }
        });
    }
}



function getTipoGasto() {
    var ramoId = $("#selRamo").val();
    var accionRow = $("#tblAcciones .selected td");
    var metaId = $("#selMeta").val();
    var fuente = $("#selFuente").val();
    var accionId;
    var opcion = 1;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetTipoGasto.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                accionId: accionId,
                metaId: metaId,
                opcion: opcion,
                fuente: fuente
            },
            success: function(respoce) {
                $('#mensaje').hide();
                $("#sectorial-lineas div").html(respoce);
            }
        });
    }
}

function insertarLineaSectorial() {

    var selectNew = $("#prevSectorial").val();


    if (selectNew != "") {
        var ramoId = $("#selRamo").val();
        var programaId = $("#selPrograma").val();
        var proyectoId = $("#selProyecto").val();
        var arrayProy = proyectoId.split(",");
        proyectoId = arrayProy[0];
        var tipoProy = arrayProy[1];
        var opcionPed = 2;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetLineasSectorial.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyectoId,
                opcionPed: opcionPed,
                tipoProy: tipoProy
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#sectorial-lineas").html(responce);
            }
        });
    } else {
        alert("Para insertar nuevas l\u00edneas sectoriales se requerir\u00e1 guardar la ultima l\u00ednea insertada");
    }
    $(".btn-guardar").prop("disabled", true);
}

function ocultarLineaPED() {
    $("#captura-dep").hide();
    $(".btn-guardar").removeAttr("disabled");
}

function ocultarLineaSectorial() {
    $("#captura-sect").hide();
    $(".btn-guardar").removeAttr("disabled");
    $("#sectorial-lineas div").html("");
    //borrarLineasSectoriales
    var borrarLineasSectoriales = document.getElementById('borrarLineasSectoriales');
    borrarLineasSectoriales.value = "";
}

function cambioLineaAccion() {
    var lineasActuales = $("#divLinRamo tr select").not(".selected-linea select");
    var lineaSelected = $(".selected-linea select");
    var index = $("#divLinRamo tr select").index($(".selected-linea select"));
    var bandera = true;
    if (lineaSelected != '-1') {
        for (var it = 0; it < lineasActuales.length; it++) {
            if (lineasActuales[it].value == lineaSelected.val()) {
                alert("Est\u00e1 l\u00ednea ya fue seleccionada anteriormente");
                $(".selected-linea select").val("0");
                bandera = false;
                return false;
            }
        }

        if (bandera) {
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxAdministraLineaAccion.jsp",
                dataType: 'html',
                data: {
                    opcion: 2,
                    nuevaLinea: lineaSelected.val(),
                    index: index
                },
                success: function(response) {
                    var result = trim(response.replace("<!DOCTYPE html>", ""));
                    if (result !== "-1") {
                        $("#divLinRamo").html(response);
                    }
                }
            });
        }
        /*
         if ($("#divLinRamo tr select").length == 1 && bandera) {
         $.ajax({
         type: 'POST',
         url: "librerias/ajax/ajaxAdministraLineaAccion.jsp",
         dataType: 'html',
         data: {
         opcion: 2,
         nuevaLinea: lineaSelected.val(),
         index: index
         },
         success: function(response) {
         var result = trim(response.replace("<!DOCTYPE html>", ""));
         if (result !== "-1") {
         $("#divLinRamo").html(response);
         }
         }
         });
         }
         */
    }
}

function cambioLinea() {

    var lineaPasada = $(".selected-linea input");

    var lineas = $("#ped-lineas tr select");
    var lineaSelected = $(".selected-linea select");
    var valorLinea = lineaSelected.val();
    var splitLinea = valorLinea.split("-");
    var cont = 0;
    for (var i = 0; i < lineas.size(); i++) {
        if (splitLinea == lineas[i].value) {
            cont++;
        }
    }

    if (cont > 1) {
        alert("Est\u00e1 opci\u00F3n ya fue seleccionada anteriormente");
        if (lineaPasada.val() != "")
            lineaSelected.val(lineaPasada.val());
        else
            lineaSelected.val("0.0.0.0");

    } else {
        if (valorLinea != "-1")
            $("#btnSectorial" + valorLinea).show();

        //Validamos si podemos cambiar la linea
        if (lineaPasada.val() != "") {

            var ramoId = $("#selRamo").val();
            var programaId = $("#selPrograma").val();
            var proyectoId = $("#selProyecto").val();
            var arrayProy = proyectoId.split(",");
            proyectoId = arrayProy[0];
            var tipoProy = arrayProy[1];
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxValidaBorrarLineaPed.jsp",
                dataType: 'html',
                data: {
                    ramoId: ramoId,
                    programaId: programaId,
                    proyectoId: proyectoId,
                    lineaId: lineaPasada.val(),
                    tipoProy: tipoProy
                },
                success: function(response) {
                    $('#mensaje').hide();

                    response = trim(response.replace("<!DOCTYPE html>", ""));

                    if (response == "-1") {
                        alert("La l\u00ednea no se puede " + "modificar" + " porque tiene l\u00edneas sectoriales");
                        lineaSelected.val(lineaPasada.val());
                    } else {
                        var borrarLineasPed = document.getElementById('borrarLineasPed');
                        borrarLineasPed.value = borrarLineasPed.value + lineaPasada.val() + ",";
                        lineaPasada.val(valorLinea);
                        lineaSelected.val(valorLinea);

                    }

                }
            });

        }

    }

}

function cambioLineaSectorial() {

    var lineas = $("#sectorial-lineas tr select");
    var lineaSelected = $(".selected-linea-sectorial select");
    var prevlineaSec = $(".selected-linea-sectorial input").val();
    var valorLinea = lineaSelected.val();
    var splitLinea = valorLinea.split("-");
    var cont = 0;
    for (var i = 0; i < lineas.size(); i++) {
        if (splitLinea == lineas[i].value) {
            cont++;
        }
    }

    if (cont > 1) {
        alert("Est\u00e1 línea ya fue seleccionada anteriormente");
        lineaSelected.val("0.0.0.0");
    }

    if (prevlineaSec != "-1") {
        var borrarLineasSectoriales = document.getElementById('borrarLineasSectoriales');
        borrarLineasSectoriales.value = borrarLineasSectoriales.value + prevlineaSec + ",";
    }

}

function borrarLinea() {

    var selectNew = "";
    if ($("#btnSectorial").is(":disabled")) {
        selectNew = "-1";
    }
    var lineaId = $(".selected-linea select").val();

    //Validamos si podemos remover la linea
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var arrayProy = proyectoId.split(",");
    proyectoId = arrayProy[0];
    var tipoProy = arrayProy[1];
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxValidaBorrarLineaPed.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            programaId: programaId,
            proyectoId: proyectoId,
            lineaId: lineaId,
            tipoProy: tipoProy,
            selectNew: selectNew
        },
        success: function(response) {
            $('#mensaje').hide();

            response = trim(response.replace("<!DOCTYPE html>", ""));

            if (response == "-1") {
                alert("La l\u00ednea no se puede " + "borrar" + " porque tiene l\u00edneas sectoriales");
            } else {
                $(".selected-linea").remove();
                //Asignar valores a borrar
                var borrarLineasPed = document.getElementById('borrarLineasPed');
                borrarLineasPed.value = borrarLineasPed.value + lineaId + ",";



            }
        }
    });

}

function guardarLineaPed() {
    var borrarLineasPed = document.getElementById('borrarLineasPed');
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var arrayProy = proyectoId.split(",");
    proyectoId = arrayProy[0];
    var tipoProy = arrayProy[1];
    var lineasPED = $("#ped-lineas select");
    var lineasId = "";
    var opcionPed = 3;
    for (var i = 0; i < lineasPED.size(); i++) {
        lineasId += lineasPED[i].value + ",";
    }


    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetLineasProyecto.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            programaId: programaId,
            proyectoId: proyectoId,
            opcionPed: opcionPed,
            arregloLinea: lineasId,
            tipoProy: tipoProy,
            idsBorrarLineasPed: borrarLineasPed.value
        },
        success: function(responce) {
            $('#mensaje').hide();
            $("#ped-lineas").html(responce);
        }
    });
    $("#captura-dep").show();
    $(".btn-guardar").prop("disabled", true);

    borrarLineasPed.value = "";


}


/*
 function mostrarLineaSectorial() {
 var ramoId = $("#selRamo").val();
 var programaId = $("#selPrograma").val();
 var proyectoId = $("#selProyecto").val();
 var opcionPed = 1;
 mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", ""); 
 $.ajax({
 type: 'POST',
 url: "librerias/ajax/ajaxGetLineasSectorial.jsp",
 dataType: 'html',
 data: {
 ramoId: ramoId, 
 programaId: programaId, 
 proyectoId: proyectoId, 
 opcionPed: opcionPed
 },
 success: function(responce) {
 $("#sectorial-lineas").html(responce);
 }
 });
 $("#captura-sect").show();
 $(".btn-guardar").attr("disabled", "disabled");
 }
 */
/*function insertarLineaPed() {
 var ramoId = $("#selRamo").val();
 var programaId = $("#selPrograma").val();
 var proyectoId = $("#selProyecto").val();
 var opcionPed = 2;
 mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", ""); 
 $.ajax({
 type: 'POST',
 url: "librerias/ajax/ajaxGetLineasProyecto.jsp",
 dataType: 'html',
 data: {
 ramoId: ramoId,
 programaId: programaId,
 proyectoId: proyectoId,
 opcionPed: opcionPed
 },
 success: function(responce) {
 $("#ped-lineas").html(responce);
 },
 error: function(responce) {
 alert(responce);
 }
 });
 $(".btn-guardar").attr("disabled", "disabled");
 }*/

function getFuenteRecurso() {
    var fuente = $("#selFuenteReq").val();
    if (fuente !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "Procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetFondoRecurso.jsp",
            dataType: 'html',
            data: {
                fuente: fuente
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#selFuenteRecurso").html(responce);
            }
        });
    } else {
        $("#selFuenteRecurso").html("<option value='-1'> --Selecciona un fondo-recurso </option>");
    }
}

function insertarFuentefin() {
    var ramoId = $("#selRamo").val();
    var accionRow = $("#tblAcciones .selected td");
    var metaId = $("#selMeta").val();
    var accionId;
    var opcion = 2;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        var ramoId = $("#selRamo").val();
        var lineasPED = $("#ped-lineas select");
        var funcionesId = "";
        var metaId = $("#selMeta").val();
        var opcion = 3;
        for (var i = 0; i < lineasPED.size(); i++) {
            funcionesId += lineasPED[i].value + ",";
        }
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetFuenteFinanciamiento.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                opcion: opcion,
                arregloFuente: funcionesId,
                metaId: metaId,
                accionId: accionId
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#ped-lineas").html(responce);
            }
        });
    }


}

function guardarFuentefin() {
    var accionRow = $("#tblAcciones .selected td");
    var accionId;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        var ramoId = $("#selRamo").val();
        var lineasPED = $("#ped-lineas select");
        var funcionesId = "";
        var programa = $("#selPrograma").val();
        var metaId = $("#selMeta").val();
        var opcion = 3;
        for (var i = 0; i < lineasPED.size(); i++) {
            funcionesId += lineasPED[i].value + ",";
        }
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetFuenteFinanciamiento.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                opcion: opcion,
                arregloFuente: funcionesId,
                metaId: metaId,
                accionId: accionId,
                programa: programa
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#ped-lineas").html(responce);
            }
        });
    }
}

function cerrarRequerimiento() {
    $("#infoRequerimiento").html("");
    $("#infoRequerimiento").hide();
}

function guardarTipoDeGasto() {
    var accionRow = $("#tblAcciones .selected td");
    var accionId;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        var ramoId = $("#selRamo").val();
        var programa = $("#selPrograma").val();
        var lineasPED = $("#sectorial-lineas div select");
        var funcionesId = "";
        var metaId = $("#selMeta").val();
        var opcion = 3;
        var fuente = $("#selFuente").val();
        for (var i = 0; i < lineasPED.size(); i++) {
            funcionesId += lineasPED[i].value + ",";
        }
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetTipoGasto.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                opcion: opcion,
                arregloGasto: funcionesId,
                metaId: metaId,
                accionId: accionId,
                fuente: fuente,
                programa: programa
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#sectorial-lineas div").html(responce);
            }
        });
    }
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetTipoGasto.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            opcion: opcion,
            arregloGasto: funcionesId,
            metaId: metaId,
            accionId: accionId,
            fuente: fuente
        },
        success: function(responce) {
            $('#mensaje').hide();
            $("#sectorial-lineas div").html(responce);
        }
    });
}

function getComboFuenteFinanciamiento() {
    var accionRow = $("#tblAcciones .selected td");
    var fuente = $("#selFuente").val();
    var accionId;
    var opcion = 1;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        var ramoId = $("#selRamo").val();
        var metaId = $("#selMeta").val();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetComboFuenteFin.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#selFuenteReq").html(responce);
                $("#selFuenteGasto").show();
                $("#tblRequerimientos").hide();
                $("#tblRequerimientos").html("");
                $("#divRequerimientos div:last-child").hide();
                $("#selFuenteReq").val(-1);
                $("#selTipoGastoReq").html("<option value='-1'> -- Seleccione un tipo de gasto -- </option>");
            }
        });
    }

}

function actualizarProyecto() {
    var nombre = $("#inTxtNombreC").val();
    if (nombre == "No capturado") {
        alert("Debe de capturar un responsable antes de continuar");
        return false;
    } else {
        var ramoId = $("#selRamo").val();
        var programaId = $("#selPrograma").val();
        var proyectoId = $("#selProyecto").val();
        var arrayProy = proyectoId.split(",");
        proyectoId = arrayProy[0];
        var tipoProy = $("#selProyecto").val();
        tipoProy = tipoProy.split(",");
        tipoProy = tipoProy[1];
        var depto = $("#selDependencia").val();
        var rfc = $("#inTxtRFC").val();
        var homoclave = $("#inTxtHC").val();
        if (rfc == "")
            rfc = $("#inTxtRFC2").val();
        if (homoclave == "")
            var homoclave = $("#inTxtHC2").val();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxActualizarProyecto.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyectoId,
                depto: depto,
                rfc: rfc,
                homoclave: homoclave,
                tipoProy: tipoProy
            },
            success: function(response) {
                $('#mensaje').hide();
                response = trim(response.replace("<!DOCTYPE html>", ""));
                if (response == "1") {
                    alert("La actualizaci\u00F3n se realiz\u00F3 correctamente");
                } else {
                    alert("No se pudo actualizar el programa, int\u00E9ntelo nuevamente");
                }
            }
        });
    }
}
/*function borrarLinea() {
 $(".selected-linea").remove();
 }*/

function getComboTipoGasto() {
    var accionRow = $("#tblAcciones .selected td");
    var accionId;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        var ramoId = $("#selRamo").val();
        var metaId = $("#selMeta").val();
        var fuente = $("#selFuenteReq").val();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetComboTipoGasto.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                fuente: fuente
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#selTipoGastoReq").html(responce);
                $("#divRequerimientos #divReq").html("");
                $("#divRequerimientos div:last-child").hide();
            }
        });
    }
}

function guardarFuentefin() {
    var accionRow = $("#tblAcciones .selected td");
    var accionId;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        var ramoId = $("#selRamo").val();
        var lineasPED = $("#ped-lineas select");
        var funcionesId = "";
        var metaId = $("#selMeta").val();
        var opcion = 3;
        for (var i = 0; i < lineasPED.size(); i++) {
            funcionesId += lineasPED[i].value + ",";
        }
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetFuenteFinanciamiento.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                opcion: opcion,
                arregloFuente: funcionesId,
                metaId: metaId,
                accionId: accionId
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#ped-lineas").html(responce);
            }
        });
    }

    function guardarFuentefin() {
        var accionRow = $("#tblAcciones .selected td");
        var accionId;
        if (accionRow.length > 0) {
            accionId = accionRow[0].innerHTML;
            var ramoId = $("#selRamo").val();
            var lineasPED = $("#ped-lineas select");
            var funcionesId = "";
            var programa = $("#selPrograma").val();
            var metaId = $("#selMeta").val();
            var opcion = 3;
            for (var i = 0; i < lineasPED.size(); i++) {
                funcionesId += lineasPED[i].value + ",";
            }
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetFuenteFinanciamiento.jsp",
                dataType: 'html',
                data: {
                    ramoId: ramoId,
                    opcion: opcion,
                    arregloFuente: funcionesId,
                    metaId: metaId,
                    accionId: accionId,
                    programa: programa
                },
                success: function(responce) {
                    $('#mensaje').hide();
                    $("#ped-lineas").html(responce);
                }
            });
        }
    }

    function cerrarRequerimiento() {
        $("#infoRequerimiento").html("");
        $("#infoRequerimiento").hide();
    }

    function guardarTipoDeGasto() {
        var accionRow = $("#tblAcciones .selected td");
        var accionId;
        if (accionRow.length > 0) {
            accionId = accionRow[0].innerHTML;
            var ramoId = $("#selRamo").val();
            var programa = $("#selPrograma").val();
            var lineasPED = $("#sectorial-lineas div select");
            var funcionesId = "";
            var metaId = $("#selMeta").val();
            var opcion = 3;
            var fuente = $("#selFuente").val();
            for (var i = 0; i < lineasPED.size(); i++) {
                funcionesId += lineasPED[i].value + ",";
            }
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetTipoGasto.jsp",
                dataType: 'html',
                data: {
                    ramoId: ramoId,
                    opcion: opcion,
                    arregloGasto: funcionesId,
                    metaId: metaId,
                    accionId: accionId,
                    fuente: fuente,
                    programa: programa
                },
                success: function(responce) {
                    $('#mensaje').hide();
                    $("#sectorial-lineas div").html(responce);
                }
            });
        }
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetTipoGasto.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                opcion: opcion,
                arregloGasto: funcionesId,
                metaId: metaId,
                accionId: accionId,
                fuente: fuente
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#sectorial-lineas div").html(responce);
            }
        });
    }
}

function guardarLineaSectorial() {
    var ultimaLineaSectorial = document.getElementById('ultimaLineaSectorial').value;
    //borrarLineasSectoriales
    var arrBorrarLineasSectoriales = document.getElementById('borrarLineasSectoriales').value;


    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var arrayProy = proyectoId.split(",");
    proyectoId = arrayProy[0];
    var tipoProy = arrayProy[1];
    var lineasSectorial = $("#sectorial-lineas select");
    var lineasId = "";
    var opcionPed = 3;
    for (var i = 0; i < lineasSectorial.size(); i++) {
        lineasId += lineasSectorial[i].value + ",";
    }

    var estrategiaId = $(".selected-linea select").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetLineasSectorial.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            programaId: programaId,
            proyectoId: proyectoId,
            opcionPed: opcionPed,
            arregloLinea: lineasId,
            estrategia: estrategiaId,
            ultimaLineaSectorial: ultimaLineaSectorial,
            arrBorrarLineasSectoriales: arrBorrarLineasSectoriales,
            tipoProy: tipoProy
        },
        success: function(responce) {
            $('#mensaje').hide();
            $("#sectorial-lineas").html(responce);
        }
    });

    var borrarLineasSectoriales = document.getElementById('borrarLineasSectoriales');
    borrarLineasSectoriales.value = "";

}

function getEstimacionMeta() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var arrayProy = proyectoId.split(",");
    var tipoProy;
    proyectoId = arrayProy[0];
    var metaRow = $("#tblMetas .selected td");
    var metaId;
    if (metaRow.length > 0) {
        metaId = metaRow[0].innerHTML;
        tipoProy = arrayProy[1];
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAdministrarEstimacionMeta.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                programaId: programaId,
                proyectoId: proyectoId,
                tipoProy: tipoProy
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#estimacionMetaO div").html(responce);
            }
        });
        $("#estimacionMetaO").show();
    } else {
        alert("Debe de seleccionar una meta para continuar");
    }
}

function calendarizarEstimacion() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var metaId = $("#selMeta").val();
    var accionRow = $("#tblAcciones .selected td");
    var ramoDescr = $("#descrRamo").val();
    var programaDescr = $("#descrPrograma").val();
    var proyectoDescr = $("#descrProyecto").val();
    var metaDescr = $("#descrMeta").val();
    var accionId;
    var obra;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        if (typeof accionRow[2].innerHTML != 'undefined') {
            obra = accionRow[2].innerHTML;
        }
        if (obra != "") {
            $("#btnActEst").attr("disabled", "disabled");
        } else
            $("#btnActEst").removeAttr("disabled");
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAdministrarEstimacionAccion.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                programaId: programaId,
                proyectoId: proyectoId,
                accionId: accionId,
                ramoDescr: ramoDescr,
                programaDescr: programaDescr,
                proyectoDescr: proyectoDescr,
                metaDescr: metaDescr,
                obra: obra
            },
            success: function(responce) {
                var resultado = trim(responce.replace("<!DOCTYPE html>", ""));
                if (resultado === "cerrado") {
                    alert("Este ramo est\u00e1 cerrado para presupuestaci\u00f3n");
                } else {
                    $('#mensaje').hide();
                    $("#estimacionMeta div").html(responce);
                }
            }
        });
        $("#estimacionMeta").show();
    }
}

function calendarizarEstimacionConsulta() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var metaId = $("#selMeta").val();
    var accionRow = $("#tblAcciones .selected td");
    var ramoDescr = $("#descrRamo").val();
    var programaDescr = $("#descrPrograma").val();
    var proyectoDescr = $("#descrProyecto").val();
    var metaDescr = $("#descrMeta").val();
    var accionId;
    var obra;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        if (typeof accionRow[2].innerHTML != 'undefined') {
            var obra = accionRow[2].innerHTML;
        }
        if (obra != "")
            $("#btnActEst").attr("disabled", "disabled");
        else
            $("#btnActEst").removeAttr("disabled");
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAdministrarEstimacionAccion.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                programaId: programaId,
                proyectoId: proyectoId,
                accionId: accionId,
                ramoDescr: ramoDescr,
                programaDescr: programaDescr,
                proyectoDescr: proyectoDescr,
                metaDescr: metaDescr,
                obra: obra,
                consuta: 1
            },
            success: function(responce) {
                var resultado = trim(responce.replace("<!DOCTYPE html>", ""));
                if (resultado === "cerrado") {
                    alert("Este ramo est\u00e1 cerrado para presupuestaci\u00f3n");
                } else {
                    $('#mensaje').hide();
                    $("#estimacionMeta div").html(responce);
                }
            }
        });
        $("#estimacionMeta").show();
    }
}

function mostrarFuenteFunanciamiento() {
    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionRow = $("#tblAcciones .selected td");
    var accionId;
    var opcion = 1;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetFuenteFinanciamiento.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                opcion: opcion,
                accionId: accionId,
                metaId: metaId
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#ped-lineas").html(responce);
            }
        });
        $("#captura-dep").show();
        $(".btn-guardar").prop("disabled", true);
    }
}

function mostrarTipoGasto() {
    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionRow = $("#tblAcciones .selected td");
    var accionId;
    var opcion = 1;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetAccionFinanciamiento.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                opcion: opcion,
                accionId: accionId,
                metaId: metaId
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#sectorial-lineas #selFuente").html(responce);
            }
        });
        $("#captura-sect").show();
        $(".btn-guardar").prop("disabled", true);
    }
}


function cerrarEstimacion() {
    $("#estimacionMetaO").hide();
    $("#estimacionMetaO div").html("");
}

function cerrarAccionEstimacion() {
    $("#estimacionMeta").hide();
    $("#estimacionMeta div").html("");
    $("#btnActEst").show();
    $("#btnCancelarEst").show();
    $("#btnAceptarEst").hide();
}


function getArticulos() {
    var partida = $("#selPartida").val();
    var ramoId = $("#selRamo").val();
    if (partida != -1) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetArticuloPartida.jsp",
            dataType: 'html',
            data: {
                partida: partida,
                ramoId: ramoId
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#colPartida").html(responce);
                $("#inpTxtCantUnit").val(0.0);
                if ($("#reqJust").val() === "0") {
                    $("#txtAreaJust").attr("disabled", true);
                } else {
                    $("#txtAreaJust").removeAttr("disabled");
                }
                calculaCosto();
                $("#inpTxtCantUnit").removeAttr("disabled");
                changeRelacionLab($("#isRelLaboral").val());
            }
        });
    }
}


function validarFlotanteMetaAccion(valor, tipoC) {
    var total = 0;
    var input;
    input = $(".calenVistaC .estimacion");
    if (valor == "")
        valor = 0;
    if (tipoC == "AC") {
        for (it = 0; it < input.length; it++) {
            if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                total += parseFloat(input[it].value.replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (total < parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
            }
        } else if (tipoC == "MI") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (total > parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    } else {
                        if (total == 0)
                            total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
            }
        }
    }
    if (total !== 0)
        $("#inTxtTotalEst").val(addCommas(total.toFixed(2)));
}

function validarFlotante(valor, tipoC) {
    var total = 0;
    var input;
    input = $("#tblEstimacion input");
    if (tipoC == "AC") {
        for (it = 0; it < input.length; it++) {
            if (input[it].value != "" || input[it].value != 0) {
                total += parseFloat(input[it].value.replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            if (valor !== "")
                for (it = 0; it < input.length; it++) {
                    if (total < parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
        } else if (tipoC == "MI") {
            if (valor !== "")
                total = parseFloat(valor.replaceAll(",", ""));
            if (valor !== "")
                for (it = 0; it < input.length; it++) {
                    if (total > parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
        }
    }
    if (total !== 0 && valor !== "")
        $("#inTxtTotalEst").val(addCommas(total.toFixed(2)));
}

function actualizarEstimacion() {

    $("#actualizarEstimacionMeta").prop("disabled", true);

    var ramoId = $("#selRamo").val();
    var metaRow = $("#tblMetas .selected td");
    var input = $("#tblEstimacion input");
    var optEst = 1;
    var valores = "";
    var metaId;
    var valorSnComa;
    if (metaRow.length > 0) {
        metaId = metaRow[0].innerHTML;
        for (var it = 0; it < input.length; it++) {
            valorSnComa = input[it].value.replaceAll(",", "");
            if (valorSnComa.trim() === "")
                valorSnComa = 0;
            valores += valorSnComa + ",";
        }

        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAdministrarEstimacionMeta.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                valores: valores,
                optEst: optEst
            },
            success: function(responce) {
                $('#mensaje').hide();
                var resultado = trim(responce.replace("<!DOCTYPE html>", ""));
                if (resultado.substring(0, 4) == "bien") {
                    alert("La calendarizaci\u00f3n para la meta se actualiz\u00f3 correctamente");
                    responce = trim(responce.replace("bien", ""));
                } else if (resultado.substring(0, 4) == "malo") {
                    alert("Sucedi\u00f3 un error, intente m\u00e1s tarde");
                    responce = trim(responce.replace("malo", ""));
                }
                $("#estimacionMetaO").hide();
            },
            error: function(responce) {
                $("#actualizarEstimacionMeta").removeAttr("disabled");
            }
        });
    }

    $("#actualizarEstimacionMeta").removeAttr("disabled");
}

function actualizarAccionEstimacion() {
    $("#btnActEst").prop("disabled", true);
    var ramoId = $("#selRamo").val();
    var accionRow = $("#tblAcciones .selected td");
    var input = $(".calenVistaC .estimacion");
    var metaId = $("#selMeta").val();
    var optEst = 1;
    var valores = "";
    var accionId;
    var valorNoComa;
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        for (var it = 0; it < input.length; it++) {
            valorNoComa = input[it].value.replaceAll(",", "");
            if (valorNoComa.trim() === "")
                valorNoComa = 0;
            valores += valorNoComa + ",";
        }
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAdministrarEstimacionAccion.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                valores: valores,
                optEst: optEst,
                accionId: accionId
            },
            success: function(responce) {
                $('#mensaje').hide();
                var resultado = trim(responce.replace("<!DOCTYPE html>", ""));
                if (resultado.substring(0, 4) == "bien") {
                    alert("La calendarizaci\u00f3n de la acci\u00f3n se actualiz\u00f3 correctamente");
                    responce = trim(responce.replace("bien", ""));
                    getRequerimientos();
                } else if (resultado.substring(0, 4) == "malo") {
                    alert("Sucedi\u00f3 un error, intente mas tarde");
                    responce = trim(responce.replace("malo", ""));
                }
                $("#estimacionMeta").hide();
                $("#btnActEst").removeAttr("disabled");
            },
            error: function(responce) {
                $("#btnActEst").removeAttr("disabled");
            }
        });
        // $("#btnActEst").hide();
        // $("#btnCancelarEst").hide();
        // $("#btnAceptarEst").show();
    }
}

function getLocalidad() {
    var municipio = $("#selMunicipio").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetLocalidad.jsp",
        datatype: 'html',
        data: {
            municipio: municipio
        },
        success: function(responce) {
            $('#mensaje').hide();
            $("#selLocalidad").html(responce);
        }
    });
}

function getInfoOnProgramaChange() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    $("#tabContent").html("");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetInformacionPrograma.jsp",
        dataType: 'html',
        data: {
            ramo: ramoId,
            programa: programaId
        },
        success: function(response) {
            $('#mensaje').hide();
            $("#tabContent").html(response);
            $("#tabContainer").show();
        }
    });
}

function generarReporte() {

    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    var llamarReporte = false;
    var mensajeError = "";
    if (ramo != "-1")
        llamarReporte = true;
    else
        mensajeError += "\nEl ramo es un parametro requerido"

    if (programa != "-1")
        llamarReporte = true;
    else
        mensajeError += "\nEl programa es un parametro requerido."

    if (llamarReporte) {
        document.getElementById('frmActInfoProg').submit();
    } else {
        alert(mensajeError);
    }


}

function getDepartamentosByRamoPrograma() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var divInfoPlantilla = $('#divInfoPlantilla').val();
    if (typeof divInfoPlantilla != 'undefined') {
        $('#divInfoPlantilla').html("<input id='contadorGrupos' type='hidden' value='0'/> <input id='contadorRegistros' type='hidden' value='0' />");
    }
    if (programaId != -1) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetDepartamentosByRamoPrograma.jsp",
            dataType: 'html',
            data: {
                ramo: ramoId,
                programa: programaId
            },
            success: function(response) {
                $('#mensaje').hide();
                $("#selDepartamento").html(response);
            }
        });
    } else {
        $("#selDepartamento").val(-1);
    }

}

function getPlantillasOnChange() {
    $("#tabContainer").show();
    var ramoId = $("#selRamo").val();
    $('#tabContent').html("<input id='contadorGrupos' type='hidden' value='0'/> <input id='contadorRegistros' type='hidden' value='0' />");
    if (ramoId !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInformacionPlantillas.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId
            },
            success: function(response) {
                $('#mensaje').hide();
                $("#tabContent").html(response);
                $("#tabContainer").show();
            }
        });
    } else {
        $("#tabContent").html("<div id='tabContent-1'></div><div id='tabContent-2'></div>");
    }
}

function actualizarAccionActivarPlantilla() {
    var deptos = "";
    $("input:checkbox:not(:checked)").each(function() {
        deptos += $(this).val() + ",";
    });
    var ramoId = $("#selRamo").val();
    var conReg = $("#contadorRegistros").val();
    var regMetasAccionesPlantillas = "";
    if (conReg > 0)
    {
        for (var i = 1; i <= conReg; i++) {
            var plantilla = "off";
            var checkRadio = document.getElementById("plantillaId" + i);
            if (checkRadio.checked)
                plantilla = "on";
            var metaId = $("#metaId" + i).val();
            var accionId = $("#accionId" + i).val();
            var fuenteId = "";
            var fondoId = "";
            var recursoId = "";
            var estadoAnterior = $("#estadoAnterior" + i).val();
            var tipoGasto = $("#tipoGasto" + i).val();
            var departamentoId = $("#departamentoId" + i).val();
            var programaId = $("#programaId" + i).val();
            regMetasAccionesPlantillas += plantilla + "," + metaId + "," + accionId + "," + fuenteId + "," + fondoId + "," + recursoId + "," + estadoAnterior + "," + tipoGasto + "," + departamentoId + "," + programaId + "|";
        }
    }
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxActualizarAccionActivarPlantilla.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            regMetasAccionesPlantillas: regMetasAccionesPlantillas,
            deptoDelete: deptos
        },
        success: function(response) {
            $('#mensaje').hide();

            response = trim(response.replace("<!DOCTYPE html>", ""));

            if (response === "1") {
                alert("La actualizaci\u00F3n se realiz\u00F3 correctamente");
            } else {
                alert("No se pudo actualizar la plantilla, int\u00E9ntelo nuevamente");
            }
            $("#selRamo").change();
        }
    });

}

function continueExecution(ramoId, numTemp)
{

    //finish doing things after the pause
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxBorrarFolderInformacionPresubpuestal.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            numTemp: numTemp
        },
        beforeSend: function() {

        },
        success: function(response) {
        }
    });
}

function generarInformacionPresubpuestal() {

    var ramoId = $("#selRamo").val();

    var generarInformacion = false;
    var mensajeError = "";

    if (ramoId != "-1")
        generarInformacion = true;
    else
        mensajeError += "\nEl ramo es un parametro requerido"

    var numTemp = $("#numTemp").val();

    if (generarInformacion) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInformacionPresubpuestalByRamo.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                numTemp: numTemp
            },
            success: function(response) {
                $('#mensaje').hide();
                response = trim(response.replace("<!DOCTYPE html>", ""));
                if (response == "-1")
                    alert("No se pudo generar los archivos");
                else {
                    if (response == "-2")
                        alert("No se pudo generar los archivos porque el Ramo debe de estar cerrado");
                    else {

                        var donwLink = document.getElementById('donwLink');
                        donwLink.click();
                        setTimeout(continueExecution(ramoId, numTemp), 500000);

                    }

                }
            }
        });



    } else {
        alert(mensajeError);
    }


}

function contCheckRptRamPrgGrupPob(IdCheck) {

    var RamoList = document.getElementById("contRegSelect");
    var longRamoList = RamoList.value;
    var checkRam = document.getElementById("check" + IdCheck);
    var labelCont = document.getElementById("labelCont");

    if (checkRam.checked)
        longRamoList++;
    else
        longRamoList--;

    if (longRamoList == 1) {
        labelCont.value = longRamoList + " Seleccionado";

    } else {
        labelCont.value = longRamoList + " Seleccionados";
    }

    RamoList.value = longRamoList;

}

function contCheckRptRamPrg(IdCheck) {
    var contReg = $("#contReg").val();
    var contRamoSelect = 0;
    var RamoList = document.getElementById("contRegSelect");
    var ramoSelect = "";
    var rangoDepto = $(".selRangoDepto");
    var longRamoList = RamoList.value;
    var checkRam = document.getElementById("check" + IdCheck);
    var labelCont = document.getElementById("labelCont");

    if (checkRam.checked)
        longRamoList++;
    else
        longRamoList--;

    if (longRamoList === 1) {
        labelCont.value = longRamoList + " Seleccionado";
        cargarProgramasReportes();
        $(".selRangoPrograma").prop("disabled", false);
        $("#tipoReporte").val(2);
    } else {
        labelCont.value = longRamoList + " Seleccionados";
        $(".selRangoPrograma").html("<option value='-1'>-- Seleccione un programa --</option>");
        if (typeof rangoDepto !== 'undefined') {
            $(".selRangoDepto").html("<option value='-1'>-- Seleccione un departamento --</option>");
        }
        $("#tipoReporte").val(1);
    }

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }
    ramoSelect = ramoSelect.replaceAll("'", "");
    $("#ramoInList").val(ramoSelect);


    if ($("#chkDetalleAccion").is(":checked")) {
        if (longRamoList == 1) {
            $("#tipoReporte").val(4);
        } else {
            $("#tipoReporte").val(3);
        }
    }
    RamoList.value = longRamoList;
}

function allChecksRptRamPrgGrupPob() {
    var rangoDepto = $(".selRangoPrograma");
    var contReg = document.getElementById("contReg").value;
    var RamoList = document.getElementById("contRegSelect");
    var longRamoList = RamoList.value;
    var allChecks = document.getElementById("allChecks");
    var labelCont = document.getElementById("labelCont");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);
        if (allChecks.checked)
            tempChecks.checked = true;
        else
            tempChecks.checked = false;
    }

    if (allChecks.checked == true) {
        labelCont.value = contReg + " Seleccionados";
        RamoList.value = contReg;
    } else {
        labelCont.value = "0 Seleccionados";
        RamoList.value = 0;
    }
    //$(".selRangoPrograma").prop("disabled", true);
    $(".selRangoPrograma").html("<option value='-1'>-- Seleccione un programa --</option>");
    if (typeof rangoDepto !== 'undefined') {
        $(".selRangoDepto").html("<option value='-1'>-- Seleccione un departamento --</option>");
    }
    $("#labelCont").change();

}


function showOrHide(zap) {
    if (document.getElementById) {
        var abra = document.getElementById(zap).style;
        if (abra.display == "block") {
            abra.display = "none";
        } else {
            abra.display = "block";
        }
        return false;
    } else {
        return true;
    }
}

function generarReporteRamoProgramaGrupoPoblacional() {

    var contReg = document.getElementById("contReg").value;
    var contRegGP = document.getElementById("contRegGP").value;
    var ramoInList = document.getElementById("ramoInList");
    var grupoPobInList = document.getElementById("grupoPobInList");
    var ramoSelect = "";
    var GPSelect = "";
    var contRamoSelect = 0;
    var contGPSelect = 0;
    var llamarReporte = true;
    var mensajeError = "";
    $("#reporttype").val("pdf");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);

        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }


    if (contRamoSelect > 0) {
        ramoInList.value = ramoSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>El ramo es un parametro requerido</p>";
        ramoInList.value = "-1";
    }

    i = 0;
    for (i = 1; i <= contRegGP; i++) {
        var tempChecksGP = document.getElementById("checkGP" + i);

        if (tempChecksGP.checked) {
            var tempGPChecks = document.getElementById("GPCheck" + i);
            if (contGPSelect == 0)
                GPSelect = GPSelect + "'" + tempGPChecks.value + "'";
            else
                GPSelect = GPSelect + "," + "'" + tempGPChecks.value + "'";
            contGPSelect++;
        }
    }

    if (contGPSelect > 0) {
        grupoPobInList.value = GPSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>El grupo poblacional es un parametro requerido</p>";
        grupoPobInList.value = "-1";
    }

    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        $.msgBox({
            title: "Error",
            content: mensajeError,
            type: "error",
            buttons: [{value: "Aceptar"}]
        });
    }
}
function generarReporteRamoProgramaGrupoPoblacionalExcel() {

    var contReg = document.getElementById("contReg").value;
    var contRegGP = document.getElementById("contRegGP").value;
    var ramoInList = document.getElementById("ramoInList");
    var grupoPobInList = document.getElementById("grupoPobInList");
    var ramoSelect = "";
    var GPSelect = "";
    var contRamoSelect = 0;
    var contGPSelect = 0;
    var llamarReporte = true;
    var mensajeError = "";
    $("#reporttype").val("xls");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);

        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        ramoInList.value = ramoSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>El ramo es un parametro requerido.</p>";
        ramoInList.value = "-1";
    }

    i = 0;
    for (i = 1; i <= contRegGP; i++) {
        var tempChecksGP = document.getElementById("checkGP" + i);

        if (tempChecksGP.checked) {
            var tempGPChecks = document.getElementById("GPCheck" + i);
            if (contGPSelect == 0)
                GPSelect = GPSelect + "'" + tempGPChecks.value + "'";
            else
                GPSelect = GPSelect + "," + "'" + tempGPChecks.value + "'";
            contGPSelect++;
        }
    }

    if (contGPSelect > 0) {
        grupoPobInList.value = GPSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>El grupo poblacional es un parametro requerido</p>";
        grupoPobInList.value = "-1";
    }

    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        $.msgBox({
            title: "Error",
            content: mensajeError,
            type: "error",
            buttons: [{value: "Aceptar"}]
        });
    }
}

function borrarLineaSectorial() {

    var lineaId = $(".selected-linea-sectorial select").val();
    //borrarLineasSectoriales
    var borrarLineasSectoriales = document.getElementById('borrarLineasSectoriales');
    borrarLineasSectoriales.value = borrarLineasSectoriales.value + lineaId + ",";
    //ultimaLineaSectorial
    var ultimaLineaSectorial = document.getElementById('ultimaLineaSectorial');
    ultimaLineaSectorial.value = lineaId;
    $(".selected-linea-sectorial").remove();
    //Borramos del arreglo que est\u00e1 en session
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxDeleteLineasSectorial.jsp',
        datatype: 'html',
        async: false,
        data: {
            lineaId: lineaId
        },
        success: function(response) {
        }
    });


}

function cargaComboRamosbyYear() {

    var yearSelect = document.getElementById('yearCaptura');
    var year = parseInt(yearSelect.value);
    if (year < 2016) {
        alert("El año tiene debe ser mayor o igual a 2016");
        yearSelect.value = "2016";
        $("#yearCaptura").change();
    } else {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetRamosByYear.jsp',
            datatype: 'html',
            async: false,
            data: {
                year: year
            },
            success: function(response) {
                $('#mensaje').hide();
                if (trim(response.replace("<!DOCTYPE html>", "")) != -2)
                    $("#selRamo").html(response);
                else {
                    $("#selRamo").html("<option value='-1'> -- Seleccione un programa -- </option>");
                }

            }
        });
    }
}

function validaMenorFinal() {
    var ramoI = $("#selRamoI").val();
    var ramoF = $("#selRamoF").val();
    if (ramoF < ramoI && ramoF !== "-1") {
        alert("El ramo inicial debe de ser menor que el ramo final");
        $("#selRamoI").val(-1);
    }
}

function validaMayorInicial() {
    var ramoI = $("#selRamoI").val();
    var ramoF = $("#selRamoF").val();
    if (ramoI > ramoF && ramoI !== "-1") {
        alert("El ramo inicial debe de ser menor que el ramo final");
        $("#selRamoF").val(-1);
    }
}

function generarReporteExcel() {
    var contReg = $("#contReg").val();
    var ramoSelect = $("#ramoInList").val();
    var contRamoSelect = $("#contRegSelect").val();
    var llamarReporte = false;
    var mensajeError = "";

    if (ramoSelect != "") {
        llamarReporte = true;
        if (contRamoSelect > 1) {
            $("#programaInList").val("");
            $("#proyectoInList").val("");
        } else {
            var programaSelect = $("#programaInList").val();
            if (programaSelect != "") {
                var contRegPrgSelect = $("#contRegPrgSelect").val();
                if (contRegPrgSelect > 1) {
                    $("#proyectoInList").val("");
                } else {
                    var proyectoSelect = $("#proyectoInList").val();
                    if (proyectoSelect == "") {
                        llamarReporte = false;
                        mensajeError += "Debe de seleccionar al menos un proyecto para continuar";
                        $("#programaInList").val("-1");
                    }
                }
            } else {
                llamarReporte = false;
                mensajeError += "Debe de seleccionar al menos un programa para continuar";
                $("#programaInList").val("-1");
            }

        }

    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }




    if (llamarReporte) {
        validarReportesExcel(ramoSelect);
    } else {
        alert(mensajeError);
    }
}

function validarReportesExcel(ramos) {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    if (!$("#inpMovtosPres").is(":checked")) {
        $("#chkDetalleAcciones").val("reportesExcel/rptDetalleAcciones.jasper");
    }
    if (!$("#inpMovtosPresMetas").is(":checked")) {
        $("#chkDetalleMetas").val("reportesExcel/rptDetalleMetas.jasper");
    }

    var reporteName = $("input[name=filename]:checked").val();
    var reporte = -1;
    if (reporteName === "reportesExcel/repPptoXCodigoExce.jasper") {
        reporte = 2;
    } else if (reporteName === "reportesExcel/rptConsolidadoPorMeta.jasper") {
        reporte = 2;
    } else if (reporteName === "reportesExcel/rptConsolidadoPorAccion.jasper") {
        reporte = 2;
    } else if (reporteName === "reportesExcel/rptPresupuestoPorDetalleAccion.jasper") {
        reporte = 1;
    } else if (reporteName === "reportesExcel/rptDetalleMetas.jasper") {
        if ($("#inpMovtosPresMetas").is(":checked")) {
            $("#chkDetalleMetas").val("reportesExcel/rptDetalleMetasMovimientos.jasper");
        } else {
            $("#chkDetalleMetas").val("reportesExcel/rptDetalleMetas.jasper");
        }
        reporte = 1;

    } else if (reporteName === "reportesExcel/rptDetalleAcciones.jasper") {
        if ($("#inpMovtosPres").is(":checked")) {
            $("#chkDetalleAcciones").val("reportesExcel/rptDetalleAccionesMovimientos.jasper");
        } else {
            $("#chkDetalleAcciones").val("reportesExcel/rptDetalleAcciones.jasper");
        }
        reporte = 1;
    } else if (reporteName === "reportesExcel/rptPptoXDetalleRequerimientos.jasper") {
        reporte = 1;
    } else if (reporteName === "reportesExcel/repPptoXCodigoModifExce.jasper") {
        reporte = 11;
    } else if (reporteName === "reportesExcel/rptConsolidadoPorAccionSM.jasper") {
        reporte = 2;
    } else if (reporteName === "reportesExcel/rptConsolidadoPorMetaSM.jasper") {
        reporte = 2;

    }


    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxValidarReportesExcel.jsp',
        datatype: 'html',
        async: false,
        data: {
            reporte: reporte,
            ramos: ramos
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res == "0") {
                alert("El reporte no contiene datos para mostrar");
                return 0;
            } else {
                $('#frmRptExcel').submit();
                return 1;
            }
        }
    });
}

function cargarProgramasReportes() {
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var rep = 1;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetProgramasByRamo.jsp',
        dataType: 'html',
        data: {
            ramos: ramoSelect,
            rep: rep
        },
        success: function(responce) {
            $(".selRangoPrograma").prop("disabled", false);
            $(".selRangoPrograma").html(responce);
            $("#selProgI").html(responce);
            $("#selProgF").html(responce);
        }
    });
}

function cargarProgramasReprogramacion() {
    var contReg = $("#contReg").val();
    var ramo = $("#selRamo").val();
    var contRamoSelect = 0;
    var rep = 1;
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetProgramasByRamo.jsp',
        dataType: 'html',
        data: {
            ramos: ramo,
            rep: rep
        },
        success: function(responce) {
            $(".selRangoPrograma").prop("disabled", false);
            $(".selRangoPrograma").html(responce);
            $("#selProgI").html(responce);
            $("#selProgF").html(responce);
        }
    });
}

function generarReportePOA() {
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var mensajeError = "";
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    var cont = $("#contRegSelect").val();
    $("#periodo").val(document.getElementById("inp-periodo").value);
    var periodillo = document.getElementById("inp-periodo").value;
    var i = 0;
    $("#reporttype").val("pdf");
    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }
    if ($("#inp-costo").is(":checked")) {
        $("#costoMeta").val(1);
    } else {
        $("#costoMeta").val(0);
    }
    if ($("#chkDetalleAccion").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(4);
        } else {
            $("#tipoReporte").val(3);
        }
    } else if ($("#chkDetalleMetas").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(4);
        } else {
            $("#tipoReporte").val(3);
        }
    } else if ($("#chkCodigo").is(":checked") || $("#chkProgramadoAutorizado").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(2);
        } else {
            $("#tipoReporte").val(1);
        }
    } else if ($("#chkAvanceAnual").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(2);
        } else {
            $("#tipoReporte").val(1);
        }
    } else if ($("#chkCodigo").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(2);
        } else {
            $("#tipoReporte").val(1);
        }
    } else if ($("#chkAvanceAccion").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(2);
        } else {
            $("#tipoReporte").val(1);
        }
        $("#tipoReporte").val(3);
    } else if ($("#chkAvanceTrimestral").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(2);
        } else {
            $("#tipoReporte").val(1);
        }
    }

    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }

    if (cont == 1) {
        if (progI == '-1' || progF == '-1') {
            alert("Debe de seleccionar un programa inicial y final");
        } else {
            if (cont == 0) {
                alert("Debe de seleccionar al menos un ramo");
            } else {
                /*Inicia validacion para Mensaje reporte trismetral*/
                if ($("#chkAvanceTrimestral").is(":checked") || $("#chkAvanceAnual").is(":checked")) {
                    if ($("#chkAvanceAnual").is(":checked")) {
                        periodillo = 4;
                    }
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxMensajeTrimestralMetas.jsp',
                        datatype: 'html',
                        async: false,
                        data: {
                            periodo: periodillo,
                            selProgramaI: progI,
                            selProgramaF: progF,
                            ramoInList: ramoSelect
                        },
                        success: function(response) {
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            if (res == "N") {
                                alert("FALTA ENVIAR INFORMACIÓN AL SISTEMA DE CONSOLIDACIÓN CIM");
                            }
                        }
                    });
                }
                /*Termina Validacion para Mensaje reporte trimestral*/


                $("#frmRptExcel").submit();
            }
        }
    } else {
        if (cont == 0) {
            alert("Debe de seleccionar al menos un ramo");
        } else {
            /*Inicia validacion para Mensaje reporte trismetral*/
            if ($("#chkAvanceTrimestral").is(":checked")) {
                $.ajax({
                    type: 'POST',
                    url: 'librerias/ajax/ajaxMensajeTrimestralMetas.jsp',
                    datatype: 'html',
                    async: false,
                    data: {
                        periodo: periodillo,
                        selProgramaI: progI,
                        selProgramaF: progF,
                        ramoInList: ramoSelect
                    },
                    success: function(response) {
                        var res = trim(response.replace("<!DOCTYPE html>", ""));
                        if (res == "N") {
                            alert("FALTA ENVIAR INFORMACIÓN AL SISTEMA DE CONSOLIDACIÓN CIM");
                        }
                    }
                });
            }
            /*Termina Validacion para Mensaje reporte trimestral*/

            $("#frmRptExcel").submit();
        }
    }

}


function generarReportePOAExcel() {
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var mensajeError = "";
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    var cont = $("#contRegSelect").val();
    $("#periodo").val(document.getElementById("inp-periodo").value);
    var i = 0;

    $("#reporttype").val("xls");
    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }
    if ($("#inp-costo").is(":checked")) {
        $("#costoMeta").val(1);
    } else {
        $("#costoMeta").val(0);
    }
    if ($("#chkDetalleAccion").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(4);
        } else {
            $("#tipoReporte").val(3);
        }
    }
    if ($("#chkDetalleMetas").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(4);
        } else {
            $("#tipoReporte").val(3);
        }
    }

    if ($("#chkAvanceAccion").is(":checked")) {
        if (contRamoSelect == 1) {
            $("#tipoReporte").val(4);
        } else {
            $("#tipoReporte").val(3);
        }
    }



    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }
    if (cont == 1) {
        if (progI == '-1' || progF == '-1') {
            alert("Debe de seleccionar un programa inicial y final");
        } else {
            if (cont == 0) {
                alert("Debe de seleccionar al menos un ramo");
            } else {
                $("#frmRptExcel").submit();
            }
        }
    } else {
        if (cont == 0) {
            alert("Debe de seleccionar al menos un ramo");
        } else {
            $("#frmRptExcel").submit();
        }
    }

}

function validaMenorFinal() {
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    if (progF < progI && progF !== "-1") {
        alert("El programa inicial debe de ser menor que el ramo final");
        $("#selProgI").val(-1);
    }
}

function validaMayorInicial() {
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    if (progI > progF && progI !== "-1") {
        alert("El programa inicial debe de ser menor que el ramo final");
        $("#selProgF").val(-1);
    }
}

function generarReporteCalendarizadoPorMes() {
    var yearSelect = document.getElementById('yearCaptura');
    var ramo = $("#selRamo").val();
    var year = parseInt($("#yearCaptura").val());

    var llamarReporte = false;
    var mensajeError = "";

    if (ramo != "-1")
        llamarReporte = true;
    else {
        mensajeError += "\nEl ramo es un parametro requerido";
        alert(mensajeError);
        return false;
    }
    if (year >= 2016) {
        llamarReporte = true;
    } else {
        mensajeError += "\nEl año es un parametro requerido.";
        yearSelect.value = "2016";
        $("#yearCaptura").change();
    }

    var radioCal = document.getElementById("calendarizado");
    if (radioCal.checked)
        $("#filename").val("rptCalMensRamPrg.jasper");

    var radioAvanc = document.getElementById("avance");
    if (radioAvanc.checked)
        $("#filename").val("rptCalMensRamPrgAvac.jasper");

    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        alert(mensajeError);
    }

}


function generarReporteProyectoActividad() {
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var mensajeError = "";


    var yearSelect = document.getElementById('yearCaptura');
    var year = parseInt($("#yearCaptura").val());
    var inCapsule = "";

    var llamarReporte = false;
    var mensajeError = "";


    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }


    if (year >= 2016) {
        llamarReporte = true;
    } else {
        mensajeError += "\nEl año es un parametro requerido."
        yearSelect.value = "2016";
        $("#yearCaptura").change();
    }

    var radioRamo = document.getElementById("ramo");
    if (radioRamo.checked) {
        llamarReporte = true;
        $("#filename").val("rptProyectoActividadRamo.jasper");
    }

    var radioRamoPrograma = document.getElementById("ramoPrograma");
    if (radioRamoPrograma.checked) {
        llamarReporte = true;
        $("#filename").val("rptProyectoActividadRamoPrograma.jasper");
    }

    if ($("#actividad").is(':checked') && $("#proyecto").is(':checked')) {
        llamarReporte = true;
        inCapsule = "'A','P'";
    } else {
        if ($("#actividad").is(':checked')) {
            llamarReporte = true;
            inCapsule = "'A'";
        } else {
            if ($("#proyecto").is(':checked')) {
                llamarReporte = true;
                inCapsule = "'P'";
            } else {
                mensajeError += "\nTipo de proyecto es un parametro requerido.";
                llamarReporte = false;
            }
        }
    }

    $("#inCapsule").val(inCapsule);



    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        alert(mensajeError);
    }

}

function cerrarSesion(year, usuario, tipo) {
    //$("#filename").val("rptAvisoCierre.jasper");
    //llave ++;
    window.open('ejecutaReporte/ejecutarReporte.jsp?filename=rptAvisoCierre.jasper&reporttype=pdf&opcionReporte=1&yearT=' + year + '&usuario=' + usuario + '&tipoDep=' + tipo + '');
    //$("#frmActInfoRpt").submit();
    window.location = "logout.jsp";
}

function evaluaOpcionReportes(nombreReporte) {


    if (nombreReporte == "reporteLineasEstrategicas") {
        $("#filename").val("rptLineasEstrategicasActividadProyectoSinCompromiso.jasper");
        var reporte = 3;
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarReportesExcel.jsp',
            datatype: 'html',
            async: false,
            data: {
                reporte: reporte
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res == "0") {
                    alert("El reporte no contiene datos para mostrar");
                    return 0;
                } else {
                    document.getElementById('frmActInfoRpt').submit();
                    return 1;
                }
            }
        });
    } else if (nombreReporte == "reporteAvisoCierre") {
        $("#filename").val("rptAvisoCierre.jasper");
        $("#frmActInfoRpt").submit();
    } else if (nombreReporte == "rptPendienteIrreductible") {
        $("#filename").val("rptPendienteIrreductible.jasper");
        $("#frmActInfoRpt").submit();

    } else {
        window.location = "" + nombreReporte + ".jsp";
    }
}


function seleccionaMasivoTrimestre() {
    $("#inp-periodo").val(1);
    $("#div-periodo").show();
    $("#div-costo").css("display", "none");
}

function seleccionaAvanceAnual() {
    $("#div-periodo").hide();
    $("#inp-periodo").val(4);
    $("#div-costo").css("display", "none");
}

function chkReporteSinPeriodo() {
    $("#div-periodo").hide();
    $("#inp-periodo").val(1);
    /*if($("#chkAvanceAccion").is(":checked")){
     $(".btn-Excel").prop("disabled", true);
     }else{
     $(".btn-Excel").prop("disabled", false); }*/
    if ($("#chkCodigo").is(":checked")) {
        $("#div-costo").css("display", "block");
    } else {
        $("#div-costo").css("display", "none");
    }
}

function incrPer() {
    var periodo = $("#inp-periodo").val();
    if (periodo != 4) {
        periodo++;
        $("#inp-periodo").val(periodo);
    } else {
        return(false);
    }
}

function decrPer() {
    var periodo = $("#inp-periodo").val();
    if (periodo != 1) {
        periodo--;
        $("#inp-periodo").val(periodo);
    } else {
        return(false);
    }
}

function setCentrales() {
    $("#tipoDependencia").val("SPPD");
    $("#frmInicio").submit();
}

function setParaestatales() {
    $("#tipoDependencia").val("PROGD");
    $("#frmInicio").submit();
}

function getCosto() {
    var articulo = $("#ArtPartida").val();
    var partida = $("#selPartida").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetCostoArticulo.jsp",
        dataType: 'html',
        data: {
            articulo: articulo,
            partida: partida
        },
        success: function(response) {
            $('#mensaje').hide();
            var result = trim(response.replace("<!DOCTYPE html>", ""));
            if (result == null) {
                result = 0.0;
            }
            if (result != 0.0) {
                $("#inpTxtCantUnit").prop("disabled", true);
            } else {
                $("#inpTxtCantUnit").removeAttr("disabled");
            }
            $("#inpTxtCantUnit").val(result);
            calculaCosto();
        }
    });

}

function getPresupuestoIngreso() {
    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var yearPres = $("#yearPresu").val();
    if (ramo !== '-1') {
        if (concepto !== "-1") {
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetPresupuestoIngreso.jsp",
                dataType: 'html',
                data: {
                    ramo: ramo,
                    concepto: concepto,
                    yearPres: yearPres,
                    opcion: 1
                },
                success: function(response) {
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res === "-1") {
                        alert("Ocurri\u00f3 un error al procesar la solicitud");
                        $("divPresupuesto div:last-child").hide();
                    } else {
                        if (res !== '0') {
                            $("#divPres").html(response);
                            $("#divPresupuesto div:last-child").show();
                        } else {
                            $("#divPresupuesto div:last-child").hide();
                            alert("El ramo seleccionado se encuentra cerrado para su captura");
                        }
                    }
                }
            });
        } else {
            $("#divPres").html("");
            $("#divPresupuesto div:last-child").hide();
        }
    } else {
        alert("Debe seleccionar un ramo para continuar");
        $("#divPres").html("");
    }
}

function insertPresupuesto() {
    var ramo = $("#selRamo").val();
    var ramoDescr = $("#selRamo option:selected").text();
    var concepto = $("#selConcepto").val();
    var conceptoDescr = $("#selConcepto option:selected").text();
    if (ramo !== "-1" && concepto !== "-1") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInfoPresupuestoIngreso.jsp",
            dataType: 'html',
            data: {
                ramo: ramo,
                concepto: concepto,
                opcion: 1,
                ramoDescr: ramoDescr,
                conceptoDescr: conceptoDescr
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                    $("divPresupuesto div:last-child").hide();
                } else {
                    $("#infoPresupuestacion").html(response);
                    $("#infoPresupuestacion").show();
                }
            }
        });
    }
}

function cerrarPresupuesto() {
    $("#infoPresupuestacion").hide();
}

function nuevoPresupuesto() {
    var yearPres = $("#yearPresu").val();
    var cont = 0;
    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var descr = $("#txtAreaPresupuestacion").val();
    descr = descr.toUpperCase();
    var ene = $("#inpTxtEne").val().replaceAll(",", "");
    var feb = $("#inpTxtFeb").val().replaceAll(",", "");
    var mar = $("#inpTxtMar").val().replaceAll(",", "");
    var abr = $("#inpTxtAbr").val().replaceAll(",", "");
    var may = $("#inpTxtMay").val().replaceAll(",", "");
    var jun = $("#inpTxtJun").val().replaceAll(",", "");
    var jul = $("#inpTxtJul").val().replaceAll(",", "");
    var ago = $("#inpTxtAgo").val().replaceAll(",", "");
    var sep = $("#inpTxtSep").val().replaceAll(",", "");
    var oct = $("#inpTxtOct").val().replaceAll(",", "");
    var nov = $("#inpTxtNov").val().replaceAll(",", "");
    var dic = $("#inpTxtDic").val().replaceAll(",", "");
    if (descr.trim() === "") {
        $("#txtAreaPresupuestacion").addClass("errorClass");
        cont++;
    } else {
        $("#txtAreaPresupuestacion").removeClass("errorClass");
    }
    if (ene.split(".")[0].length > 10) {
        $("#inpTxtEne").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtEne").removeClass("errorClass");
    }
    if (feb.split(".")[0].length > 10) {
        $("#inpTxtFeb").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtFeb").removeClass("errorClass");
    }
    if (mar.split(".")[0].length > 10) {
        $("#inpTxtMar").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMar").removeClass("errorClass");
    }
    if (abr.split(".")[0].length > 10) {
        $("#inpTxtAbr").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAbr").removeClass("errorClass");
    }
    if (may.split(".")[0].length > 10) {
        $("#inpTxtMay").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMay").removeClass("errorClass");
    }
    if (jun.split(".")[0].length > 10) {
        $("#inpTxtJun").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJun").removeClass("errorClass");
    }
    if (jul.split(".")[0].length > 10) {
        $("#inpTxtJul").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJul").removeClass("errorClass");
    }
    if (ago.split(".")[0].length > 10) {
        $("#inpTxtAgo").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAgo").removeClass("errorClass");
    }
    if (sep.split(".")[0].length > 10) {
        $("#inpTxtSep").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtSep").removeClass("errorClass");
    }
    if (oct.split(".")[0].length > 10) {
        $("#inpTxtOct").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtOct").removeClass("errorClass");
    }
    if (nov.split(".")[0].length > 10) {
        $("#inpTxtNov").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtNov").removeClass("errorClass");
    }
    if (dic.split(".")[0].length > 10) {
        $("#inpTxtDic").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtDic").removeClass("errorClass");
    }
    var opcion = 2;
    if (cont === 0) {
        if (ramo > 0 && concepto !== "-1") {
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetPresupuestoIngreso.jsp",
                dataType: 'html',
                data: {
                    yearPres: yearPres,
                    ramo: ramo,
                    concepto: concepto,
                    descr: descr,
                    ene: ene,
                    feb: feb,
                    mar: mar,
                    abr: abr,
                    may: may,
                    jun: jun,
                    jul: jul,
                    ago: ago,
                    sep: sep,
                    oct: oct,
                    nov: nov,
                    dic: dic,
                    opcion: opcion
                },
                success: function(response) {
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res === "-1") {
                        alert("Ocurri\u00f3 un error al procesar la solicitud");
                    } else {
                        $("#divPres").html(response);
                        $("#infoPresupuestacion").hide();
                    }
                }
            });
        } else {
            alert("Debe de seleccionar un ramo y concepto para continuar");
        }
    } else {
        alert("Debe corregir los datos marcados");
    }
}

function mostrarPresupuesto() {
    var presRow = $("#tblPresupuesto .selected td");
    if (presRow.length > 0) {
        var ramo = $("#selRamo").val();
        var ramoDescr = $("#selRamo option:selected").text();
        var conceptoDescr = $("#selConcepto option:selected").text();
        var concepto = $("#selConcepto").val();
        var opcion = 2;
        var pptoId = presRow[0].innerHTML;
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoPresupuestoIngreso.jsp',
            dataType: 'html',
            data: {
                ramo: ramo,
                concepto: concepto,
                opcion: opcion,
                pptoId: pptoId,
                ramoDescr: ramoDescr,
                conceptoDescr: conceptoDescr
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $("#infoPresupuestacion").html(response);
                    $("#infoPresupuestacion").show();
                }
            }
        });
    } else {
        alert("Debe de seleccionar un registro para continuar");
    }
}

function borrarPresupuesto() {
    var presRow = $("#tblPresupuesto .selected td");
    if (presRow.length > 0) {
        if (confirm("¿Est\u00e1 seguro que desea borrar este registro?")) {
            var ramo = $("#selRamo").val();
            var concepto = $("#selConcepto").val();
            var opcion = 4;
            var pptoId = presRow[0].innerHTML;
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetPresupuestoIngreso.jsp",
                dataType: 'html',
                data: {
                    ramo: ramo,
                    concepto: concepto,
                    opcion: opcion,
                    pptoId: pptoId
                },
                success: function(response) {
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res === "-1") {
                        alert("Ocurri\u00f3 un error al procesar la solicitud");
                    } else {
                        $("#divPres").html(response);
                        $("#infoPresupuestacion").hide();
                    }
                }
            });
        }
    } else {
        alert("Debe de seleccionar un registro para continuar");
    }
}

function editarPresupuesto(pptoId) {
    var yearPres = $("#yearPresu").val();
    var cont = 0;
    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var descr = $("#txtAreaPresupuestacion").val();
    descr = descr.toUpperCase();
    var ene = $("#inpTxtEne").val().replaceAll(",", "");
    var feb = $("#inpTxtFeb").val().replaceAll(",", "");
    var mar = $("#inpTxtMar").val().replaceAll(",", "");
    var abr = $("#inpTxtAbr").val().replaceAll(",", "");
    var may = $("#inpTxtMay").val().replaceAll(",", "");
    var jun = $("#inpTxtJun").val().replaceAll(",", "");
    var jul = $("#inpTxtJul").val().replaceAll(",", "");
    var ago = $("#inpTxtAgo").val().replaceAll(",", "");
    var sep = $("#inpTxtSep").val().replaceAll(",", "");
    var oct = $("#inpTxtOct").val().replaceAll(",", "");
    var nov = $("#inpTxtNov").val().replaceAll(",", "");
    var dic = $("#inpTxtDic").val().replaceAll(",", "");
    if (descr.trim() === "") {
        $("#txtAreaPresupuestacion").addClass("errorClass");
        cont++;
    } else {
        $("#txtAreaPresupuestacion").removeClass("errorClass");
    }
    if (ene.split(".")[0].length > 10) {
        $("#inpTxtEne").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtEne").removeClass("errorClass");
    }
    if (feb.split(".")[0].length > 10) {
        $("#inpTxtFeb").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtFeb").removeClass("errorClass");
    }
    if (mar.split(".")[0].length > 10) {
        $("#inpTxtMar").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMar").removeClass("errorClass");
    }
    if (abr.split(".")[0].length > 10) {
        $("#inpTxtAbr").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAbr").removeClass("errorClass");
    }
    if (may.split(".")[0].length > 10) {
        $("#inpTxtMay").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMay").removeClass("errorClass");
    }
    if (jun.split(".")[0].length > 10) {
        $("#inpTxtJun").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJun").removeClass("errorClass");
    }
    if (jul.split(".")[0].length > 10) {
        $("#inpTxtJul").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJul").removeClass("errorClass");
    }
    if (ago.split(".")[0].length > 10) {
        $("#inpTxtAgo").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAgo").removeClass("errorClass");
    }
    if (sep.split(".")[0].length > 10) {
        $("#inpTxtSep").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtSep").removeClass("errorClass");
    }
    if (oct.split(".")[0].length > 10) {
        $("#inpTxtOct").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtOct").removeClass("errorClass");
    }
    if (nov.split(".")[0].length > 10) {
        $("#inpTxtNov").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtNov").removeClass("errorClass");
    }
    if (dic.split(".")[0].length > 10) {
        $("#inpTxtDic").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtDic").removeClass("errorClass");
    }
    var opcion = 3;
    if (cont === 0) {
        if (ramo > 0 && concepto !== "-1") {
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetPresupuestoIngreso.jsp",
                dataType: 'html',
                data: {
                    yearPres: yearPres,
                    ramo: ramo,
                    concepto: concepto,
                    descr: descr,
                    ene: ene,
                    feb: feb,
                    mar: mar,
                    abr: abr,
                    may: may,
                    jun: jun,
                    jul: jul,
                    ago: ago,
                    sep: sep,
                    oct: oct,
                    nov: nov,
                    dic: dic,
                    opcion: opcion,
                    pptoId: pptoId
                },
                success: function(response) {
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res === "-1") {
                        alert("Ocurri\u00f3 un error al procesar la solicitud");
                    } else {
                        $("#divPres").html(response);
                        $("#infoPresupuestacion").hide();
                    }
                }
            });
        } else {
            alert("Debe de seleccionar un ramo y concepto para continuar");
        }
    } else {
        alert("Debe corregir los datos marcados");
    }
}

function totalPresupuestoIngreso() {
    var presup = $(".capt-mes");
    var total = 0;
    for (var it = 0; it < presup.length; it++) {
        total += parseFloat(presup[it].value.replaceAll(",", ""));
    }
    $("#totalIngreso").val(total.toFixed(2));
    agregarFormato("totalIngreso");
}

function getPlantillaPersonal() {
    var ramo = $("#selRamo").val();
    var year = $("#yearPresu").val();
    if (ramo !== "-1") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetPlantillaPersonal.jsp",
            dataType: 'html',
            data: {
                ramo: ramo,
                yearPres: year
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                    $("#divPlantillaPer div:last-child").hide();
                } else {
                    if (res === "0") {
                        $("#divPlantillaPer div:last-child").hide();
                        alert("Este ramo est\u00e1 cerrado para su captura");
                    } else {
                        $("#divPlant").html(response);
                        $("#divPlantillaPer div:last-child").show();
                    }
                }
            }
        });
    } else {
        alert("Debe de seleccionar un ramo para continuar");
        $("#divPlant").html("");
        $("#divPlantillaPer div:last-child").hide();
    }

}

function insertPlantilla() {
    var year = $("#yearPresu").val();
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetInfoPlantillaPersonal.jsp",
        datatype: 'html',
        data: {
            opcion: 1,
            yearPres: year
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $("#infoPlantillaPer").html(response);
                $("#infoPlantillaPer").show();
            }
        }
    });
}

function cerrarPlantilla() {
    $("#infoPlantillaPer").html("");
    $("#infoPlantillaPer").hide();
}

function mostrarPlantilla() {
    var planRow = $("#tblPlantillaPer .selected td");
    if (planRow.length > 0) {
        var yearPres = $("#yearPresu").val();
        var ramo = $("#selRamo").val();
        var plantilla = planRow[0].innerHTML;
        var relLaboral = plantilla.split("-");
        relLaboral = relLaboral[0].trim();
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInfoPlantillaPersonal.jsp",
            datatype: 'html',
            data: {
                yearPres: yearPres,
                ramo: ramo,
                relacionLaboral: relLaboral,
                opcion: 2
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $("#infoPlantillaPer").html(response);
                    $("#infoPlantillaPer").show();
                }
            }
        });
    }
}

function nuevoPlantillaPer() {
    var yearPres = $("#yearPresu").val();
    var ramo = $("#selRamo").val();
    var relLaboral = $("#selRelLaboral").val();
    var cantidad = $("#inpTxtCantidad").val();
    if (ramo != "-1" && cantidad > 0.0) {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetPlantillaPersonal.jsp",
            dataType: 'html',
            data: {
                yearPres: yearPres,
                ramo: ramo,
                relLaboral: relLaboral,
                cantidad: cantidad,
                opcion: 1
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    if (res.split("-")[0] === "limite") {
                        alert("No se pudo completar la solicitud");
                        response = response.replace("limite-", "");
                    }
                    $("#divPlant").html(response);
                    $("#divPlantillaPer div:last-child").show();
                    $("#infoPlantillaPer").html("");
                    $("#infoPlantillaPer").hide();
                }
            }
        });
    } else {
        alert("Ambos campos son obligatorios para capturar")
    }
}

function editarPlantillaPer() {
    var yearPres = $("#yearPresu").val();
    var ramo = $("#selRamo").val();
    var relLaboral = $("#selRelLaboral").val();
    var planRow = $("#tblPlantillaPer .selected td");
    var plantilla = planRow[0].innerHTML;
    var relLaboralAnt = plantilla.split("-");
    relLaboralAnt = relLaboralAnt[0].trim();
    var cantidad = $("#inpTxtCantidad").val();
    if (ramo != "-1" && cantidad > 0.0) {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetPlantillaPersonal.jsp",
            dataType: 'html',
            data: {
                yearPres: yearPres,
                ramo: ramo,
                relLaboral: relLaboral,
                cantidad: cantidad,
                opcion: 2,
                relLaboralAnt: relLaboralAnt
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    if (res.split("-")[0] === "limite") {
                        alert("No debe de repetir la relaci\u00f3n laboral");
                        response = response.replace("limite-", "");
                    }
                    $("#divPlant").html(response);
                    $("#divPlantillaPer div:last-child").show();
                    $("#infoPlantillaPer").html("");
                    $("#infoPlantillaPer").hide();
                }
            }
        });
    } else {
        alert("Ambos campos son obligatorios para capturar")
    }
}

function borrarPlantilla() {
    var planRow = $("#tblPlantillaPer .selected td");
    if (planRow.length > 0) {
        if (confirm("¿Est\u00e1 seguro que desea eliminar este registro?")) {
            var ramo = $("#selRamo").val();
            var yearPres = $("#yearPresu").val();
            var plantilla = planRow[0].innerHTML;
            var relLaboral = plantilla.split("-");
            relLaboral = relLaboral[0].trim();
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetPlantillaPersonal.jsp",
                dataType: 'html',
                data: {
                    yearPres: yearPres,
                    ramo: ramo,
                    relLaboral: relLaboral,
                    opcion: 3
                },
                success: function(response) {
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res === "-1") {
                        alert("Ocurri\u00f3 un error al procesar la solicitud");
                    } else {
                        if (res.split("-")[0] === "limite") {
                            alert("No se pudo completar la solicitud");
                            response = response.replace("limite-", "");
                        }
                        $("#divPlant").html(response);
                        $("#divPlantillaPer div:last-child").show();
                    }
                }
            });
        } else {
            return false;
        }
    } else {
        alert("Debe de seleccionar un registro para continuar");
    }
}

function enviarDocumento() {
    var archivo = $("#archivo").val();
    $("#divTblProyeccion").html("");
    $("#divTblProyeccion").css("border", "none");
    $("#divTotalProyeccion").html("");
    $("#infoCarga").html("");
    if (archivo.trim() === "") {
        alert("Debe de seleccionar un archivo con formato excel");
        return false;
    }
    if (confirm("Este proceso puede durar varios minutos")) {
        $("#form-captura").serialize();
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $("#form-captura").submit();
    }
}

function enviarDocumentoPlantilla() {
    var archivo = $("#archivo").val();
    var ramo = $("#selRamo").val();
    $("#hiddenRamo").val(ramo);
    if (archivo.trim() === "") {
        alert("Debe de seleccionar un archivo con formato excel");
        return false;
    }
    if (confirm("Este proceso puede durar varios minutos")) {
        $("#infoCarga").html("");
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $("#form-captura-plantilla").submit();
    }
}

function generarReporteIngresoEgreso(reportType) {
    var contReg = document.getElementById("contReg").value;
    var ramoInList = document.getElementById("ramoInList");
    var ramoSelect = "";
    var contRamoSelect = 0;
    var llamarReporte = false;
    var mensajeError = "";

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);

        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }
    if (contRamoSelect > 0) {
        llamarReporte = true;
        ramoInList.value = ramoSelect;
    } else {
        mensajeError += "\nEl ramo es un parametro requerido";
        ramoInList.value = "-1";
    }
    if (llamarReporte) {
        $("#reporttype").val(reportType);
        document.getElementById('frmActInfoRpt').submit();
    } else {
        alert(mensajeError);
    }
}

function generarReporteComparativoPresupuesto(reportType) {

    var partida1 = $("#selPartidaComp1").val();
    var partida2 = $("#selPartidaComp2").val();
    var contReg = document.getElementById("contReg").value;
    var ramoInList = document.getElementById("ramoInList");
    var queryPartidas = document.getElementById("queryPartidas");
    var ramoSelect = "";
    var contRamoSelect = 0;
    var llamarReporte = false;
    var mensajeError = "";
    var reporte = 10;
    $("#selPartIni").val(partida1);
    $("#selPartFin").val(partida2);
    $("#reporttype").val(reportType);

    var strFileName = $("input:radio[name ='selRpt']:checked").val();
    $("#filename").val(strFileName);

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);

        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }


    if (contRamoSelect > 0) {
        llamarReporte = true;
        ramoInList.value = ramoSelect;
    } else {
        llamarReporte = false;
        mensajeError += "\nEl ramo es un parametro requerido";
        ramoInList.value = "-1";
    }

    if (partida1 != '-1' && partida2 != '-1') {
        llamarReporte = true;
    } else {
        llamarReporte = false;
        mensajeError += "\nLa partida es un parametro requerido";
        ramoInList.value = "-1";

    }

    if (partida1 < partida2) {
        llamarReporte = true;
    } else {
        llamarReporte = false;
        mensajeError += "\nLa partida inicial tiene que ser menor a la final";
        ramoInList.value = "-1";
        queryPartidas = "-1";
    }

    if (llamarReporte) {
        queryPartidas.value = "BETWEEN '" + partida1 + "' AND '" + partida2 + "'";
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarReportesExcel.jsp',
            datatype: 'html',
            async: false,
            data: {
                reporte: reporte,
                ramos: ramoSelect,
                queryPartidas: "BETWEEN '" + partida1 + "' AND '" + partida2 + "'"
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res == "0") {
                    alert("El reporte no contiene datos para mostrar");
                    return 0;
                } else {
                    document.getElementById('frmActInfoRpt').submit();
                    return 1;
                }
            }
        });

    } else {
        alert(mensajeError);
    }


}

function generarReporteLeyEgresos() {

    var contReg = document.getElementById("contReg").value;
    var ramoInList = document.getElementById("ramoInList");
    var ramoSelect = "";
    var contRamoSelect = 0;
    var llamarReporte = false;
    var mensajeError = "";

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);

        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }


    if (contRamoSelect > 0) {
        llamarReporte = true;
        ramoInList.value = ramoSelect;
    } else {
        mensajeError += "\nEl ramo es un parametro requerido";
        ramoInList.value = "-1";
    }
    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        alert(mensajeError);
    }
}


function insertarLineaAccion() {
    var opcion = 1;
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxAdministraLineaAccion.jsp",
        dataType: 'html',
        data: {
            opcion: opcion
        },
        success: function(response) {
            var result = trim(response.replace("<!DOCTYPE html>", ""));
            if (result !== "-1") {
                $("#divLinRamo").html(response);
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }
        }
    });
}

function borrarLineaAccion() {
    var linea = $("#tabContent-5 .selected-linea select").val();
    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    var re;
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxDeleteLineaAccion.jsp",
        dataType: 'html',
        data: {
            linea: linea,
            ramo: ramo,
            programa: programa
        },
        success: function(response) {
            var result = trim(response.replace("<!DOCTYPE html>", ""));
            re = result.split("|");
            if (re[0] === "borrar") {
                response = response.replace("borrar|", "");
                $("#divLinRamo").html(response);
            } else if (re[0] === "lineas") {
                alert("Est\u00e1 l\u00ednea est\u00e1 asignada a una meta o acci\u00f3n");
                return false;
            }
        }
    });
}

function currencyFormat(num) {
    return "$" + num.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
}

function ocultaAutomaticos() {
    var largo = $(".menuAcordeon table tbody");
    var menu = "";
    for (var it = 0; it < largo.length; it++) {
        if (largo[it].children.length < 2) {
            menu = (".menuAcordeon");
            menu.setAttribute("style", "display:none");
        }
    }
}

function validaRamoAbierto() {
    var ramo = $("#selRamo").val();
    if (ramo !== "-1") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxIsRamoPOACerrado.jsp",
            dataType: 'html',
            data: {
                ramo: ramo
            },
            success: function(response) {
                var result = trim(response.replace("<!DOCTYPE html>", ""));
                if (result !== "-1") {
                    if (result === "Cerrado") {
                        alert("El ramo est\u00e1 cerrado para programaci\u00f3n");
                        $("#selRamo").val(-1);
                        $("#archivo").hide();
                    } else {
                        $("#archivo").show();
                    }
                } else {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                }
            }
        });
    }
}

function guardarExcel() {
    /*var archivo = $("#archivo").val();
     if (archivo.trim() === "") {
     alert("Debe de seleccionar un archivo con formato excel");
     return false;
     }*/
    $("#divTblProyeccion").html("");
    $("#divTblProyeccion").css("border", "none");
    $("#divTotalProyeccion").html("");
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "Cargando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxSaveExcelInTable.jsp",
        dataType: 'html',
        success: function(response) {
            var result = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            if (result !== "-1") {
                if (result === "exito") {
                    alert("La informaci\u00f3n se grab\u00f3 exitosamente");
                } else if (result.match(/noInsert/i)) {
                    if (result.match(/ORA-00001/i)) {
                        alert("La informaci\u00f3n que intena cargar contiene registros repetidos");
                    } else {
                        alert("La informaci\u00f3n no se grab\u00f3 exitosamente");
                    }
                } else if (result === "noCarga") {
                    alert("No se encontr\u00f3 informaci\u00f3n para grabar");
                } else if (result === "noBorrado") {
                    alert("La informaci\u00f3n anterior no se borr\u00f3 correctamente. Int\u00e9ntelo nuevamente.");
                }
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }
        }
    });
}

function generarReportePpptoCalAnual() {
    $("#reporttype").val("pdf");
    var contReg = $("#contReg").val();
    var partIni = $("#selPartIni").val();
    var partFin = $("#selPartFin").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var mensajeError = "";
    var cont = $("#contRegSelect").val();
    var reporte = 4;
    if (partIni === "-1" || partFin === "-1") {
        alert("Debe de seleccionar una partida inicial y final para continuar");
        return false;
    }
    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }



    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);

        /*if ($("#chkRptPptoCalAnualRamPart").is(":checked"))
         reporte = 4;
         if ($("#chkRptPptoCalAnualRamPrgPart").is(":checked"))
         reporte = 5;
         if ($("#chkRptPptoCalAnualCodigos").is(":checked"))
         reporte = 6;
         if ($("#chkRptPptoAnualGpo").is(":checked"))
         reporte = 7;
         if ($("#chkRptPptoAnualRamPrgGpo").is(":checked"))
         reporte = 8;
         if ($("#chkRptPptoAnualCodigo").is(":checked"))
         reporte = 9;
         
         $.ajax({
         type: 'POST',
         url: 'librerias/ajax/ajaxValidarReportesExcel.jsp',
         datatype: 'html',
         async: false,
         data: {
         reporte: reporte,
         ramos: ramoSelect
         },
         success: function(response) {
         var res = trim(response.replace("<!DOCTYPE html>", ""));
         if (res == "0") {
         alert("El reporte no contiene datos para mostrar");
         return 0;
         } else {
         $("#frmRptExcel").submit();
         return 1;
         }
         }
         });*/
        $("#frmRptExcel").submit();
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }
}

function generarReportePpptoCalAnualExcel() {
    $("#reporttype").val("xls");
    var contReg = $("#contReg").val();
    var partIni = $("#selPartIni").val();
    var partFin = $("#selPartFin").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var mensajeError = "";
    var cont = $("#contRegSelect").val();
    var reporte = 4;
    if (partIni === "-1" || partFin === "-1") {
        alert("Debe de seleccionar una partida inicial y final para continuar");
        return false;
    }
    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }



    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);

        /*if ($("#chkRptPptoCalAnualRamPart").is(":checked"))
         reporte = 4;
         if ($("#chkRptPptoCalAnualRamPrgPart").is(":checked"))
         reporte = 5;
         if ($("#chkRptPptoCalAnualCodigos").is(":checked"))
         reporte = 6;
         if ($("#chkRptPptoAnualGpo").is(":checked"))
         reporte = 7;
         if ($("#chkRptPptoAnualRamPrgGpo").is(":checked"))
         reporte = 8;
         if ($("#chkRptPptoAnualCodigo").is(":checked"))
         reporte = 9;
         
         $.ajax({
         type: 'POST',
         url: 'librerias/ajax/ajaxValidarReportesExcel.jsp',
         datatype: 'html',
         async: false,
         data: {
         reporte: reporte,
         ramos: ramoSelect
         },
         success: function(response) {
         var res = trim(response.replace("<!DOCTYPE html>", ""));
         if (res == "0") {
         alert("El reporte no contiene datos para mostrar");
         return 0;
         } else {
         $("#frmRptExcel").submit();
         return 1;
         }
         }
         });*/
        $("#frmRptExcel").submit();
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }

}
function addCommas(nStr)
{
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}

function selectEnteros(id) {
    var elm = $("#" + id);
    var val = elm.val();
    var input = document.getElementById(id);
    var longitud = input.value.length;
    if (input.value === "0" || input.value === "") {
        setTimeout(function() {
            elm.val("0.00");
            selectEnteros(id);
        }, 1);
    } else {
        var longitud = input.value.length;
        setTimeout(function() {
            input.setSelectionRange(0, (longitud - 3));
        }, 1);
    }
//input.setSelectionRange(0,(longitud - 3));
}

function changeMask(id) {

}

function validaMascara(id) {
    //quitaFormato(id);
    var sImporte = $("#" + id).val();
    var sPos = sImporte.indexOf(".", 0);
    if (sPos >= 0) {
        /*  if (sPos > 12)
         {
         sImporte = sImporte.substr(0, sPos -1);
         }
         else
         {*/
        if (sImporte.indexOf(".", sPos + 1) >= 0) {
            sImporte = sImporte.substring(0, sImporte.indexOf(".", sPos + 1));
            $("#" + id).val(sImporte);
        }
        if ((sImporte.length - sPos) > 2)
        {
            $("#" + id).val(sImporte.substr(0, sPos + 3));
        }
    }
    /* }
     else
     {
     if (sImporte.length > 12)
     {
     $("#" + id).val(sImporte.substr(0, 12));
     }
     }*/
}

function agregarFormato(id) {
    quitaFormato(id);
    var str = $("#" + id).val();
    var parts = (str + "").split("."),
            main = parts[0], negativo;
    if (main.charAt(0) === '-') {
        negativo = '-';
        main = main.replace("-", "");
    }
    len = main.length,
            output = "",
            i = len - 1;

    while (i >= 0) {
        output = main.charAt(i) + output;
        if ((len - i) % 3 === 0 && i > 0) {
            output = "," + output;
        }
        --i;
    }
    // put decimal part back
    if (parts.length > 1) {
        output += "." + parts[1].substring(0, 2);
    }
    if (negativo === '-') {
        output = negativo + output;
    }
    $("#" + id).val(output);
}

function quitaFormato(id)
{
    var sDato = $("#" + id).val();
    $("#" + id).val(sDato.replace(/,/g, ""));
}

function pressPoint(event, id) {
    var input = document.getElementById(id);
    if (input.value.length > 1) {
        if (event.keyCode === 46 || event.keyCode === "190") {
            if (contImp === 0) {
                contImp++;
                var longitud = input.value.length;
                input.setSelectionRange((longitud - 2), longitud);
            }
        } else {
            contImp = 0;
        }
    } else {
        $("#" + id).val(input.value.toString() + "    .00");
    }
}

function validarTipoMeta() {
    var obraActual = $("#tipoMetaObra").val();
    var obraNueva = $("#selObraP").val();
    if (obraActual === "G") {
        if (obraNueva == "A") {
            return false;
        } else if (obraNueva === "I") {
            alert("No se puede modificar el tipo de meta \"Gasto corriente\" a \"Inversi\u00f3n p\u00fablica\"");
            $("#selObraP").val(obraActual);
        } else if (obraNueva === "G") {
            $("#selObraP").val(obraActual);
        } else if (obraNueva === "-1") {
            $("#selObraP").val(obraActual);
        }
    } else if (obraActual === "I") {
        if (obraNueva === "A") {
            return false;
        } else if (obraNueva === "G") {
            alert("No se puede modificar el tipo de meta \"Inversi\u00f3n p\u00fablica\" a \"Gasto corriente\"");
            $("#selObraP").val(obraActual);
        } else if (obraNueva === "-1") {
            $("#selObraP").val(obraActual);
        }
    } else if (obraActual === "A") {
        if (obraNueva === "I" || obraNueva === "G") {
            alert("No se puede modificar el tipo de meta \"Inversi\u00f3n p\u00fablica y Gasto corriente\"");
            $("#selObraP").val(obraActual);
        } else if (obraNueva === "-1") {
            $("#selObraP").val(obraActual);
        }
    }
}

function formatNumber(num) {
    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
}


function validaRamoCarga() {
    var ramo = $("#selRamo").val();
    $("#infoCarga").html("");
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxPreguntaBorradoPlantilla.jsp',
        datatype: 'html',
        async: false,
        data: {
            ramo: ramo
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "S") {
                if (ramo !== "-1") {
                    if (confirm("El ramo seleccionado ya contiene informaci\u00f3n. \u00bfDesea borrar y cargar nuevamente?")) {

                        $("#archivo").show();
                        //$("#archivo").hide();
                    } else {
                        $("#archivo").hide();
                    }
                } else {
                    $("#archivo").hide();
                }
            } else if (res === "N") {
                if (ramo !== "-1") {
                    $("#archivo").show();
                } else {
                    $("#archivo").hide();
                }
            } else if (res === "C") {
                if (ramo !== "-1") {
                    $("#archivo").hide();
                    alert("El ramo seleccionado está cerrado para su captura");
                }
            }
        }
    });
}

function validaRamoCargaProyeccion() {
    var ramo = $("#selRamo").val();
    $("#divTblProyeccion").html("");
    $("#divTblProyeccion").css("border", "none");
    $("#divTotalProyeccion").html("");
    $("#infoCarga").html("");
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxPreguntaBorradoPlantilla.jsp',
        datatype: 'html',
        async: false,
        data: {
            ramo: ramo,
            opt: 1
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "S") {
                if (ramo !== "-1") {
                    if (confirm("El ramo seleccionado ya contiene informaci\u00f3n \u00bfDesea borrar y cargar nuevamente?")) {
                        $("#archivo").show();
                    } else {
                        $("#archivo").hide();
                    }
                } else {
                    $("#archivo").hide();
                }
            } else if (res === "N") {
                if (ramo !== "-1") {
                    $("#archivo").show();
                } else {
                    $("#archivo").hide();
                }
            } else if (res === "C") {
                if (ramo !== "-1") {
                    $("#archivo").hide();
                    alert("El ramo seleccionado está cerrado para su captura");
                }
            }
        }
    });
}


function bloqueaBotonesPlantilla() {
    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    var meta = $("#selMeta").val();
    var tipoProy = $("#selTipoProy").val();
    var requerimiento = $("#tblRequerimientos tbody .selected td")[0].innerHTML;
    var partida = $("#tblRequerimientos tbody .selected td")[1].innerHTML;
    var fuente = $("#tblRequerimientos tbody .selected td")[2].innerHTML.split("-")[0];
    var fondo = fuente.split(".")[1];
    var recurso = fuente.split(".")[2];
    fuente = fuente.split(".")[0];
    var accion = $("#tblAcciones tbody .selected td")[0].innerHTML;
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxBloqueaBotones.jsp',
        datatype: 'html',
        async: false,
        data: {
            ramo: ramo,
            progrma: programa,
            meta: meta,
            tipoProy: tipoProy,
            partida: partida,
            fuente: fuente,
            fondo: fondo,
            recurso: recurso,
            accion: accion,
            requerimiento: requerimiento.trim()
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "plantilla") {
                $("#botonMostrarRequerimiento").hide();
                $("#botonBorrarRequerimiento").hide();
                $("#botonCosntultarRequerimiento").show();
                alert("Los requerimientos por carga de archivo de plantilla no pueden ser modificados. Favor de hacer modificaciones en el archivo de origen para volver a cargar.");
            } else {
                $("#botonMostrarRequerimiento").show()
                $("#botonBorrarRequerimiento").show();
                $("#botonCosntultarRequerimiento").hide();
            }
        }
    });
}

function cargarPlantillaCentral() {
    var origen;
    if ($("#radioSIRHB").is(":checked")) {
        origen = $("#radioSIRHB").val();
    } else if ($("#radioSIRHM").is(":checked")) {
        origen = $("#radioSIRHM").val();
    }
    $("#mensajeCarga").html("");
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "Cargando ...", "");
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxCargaPlantillaCentral.jsp',
        dataType: 'html',
        data: {
            origen: origen
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            var tipo = res.split("|")[0].trim();
            var mensaje = res.split("|")[1].trim();
            var color;
            if (tipo === "0") {
                color = "color: red;";
            } else if (tipo === "1") {
                color = "color: blue;";
            }
            $("#mensajeCarga").html("<label style='" + color + "font-weight: bold;'>" + mensaje + "</label>");
            $('#mensaje').hide();
        }
    });
}

function getDeptoByProgramaF() {
    var prgInicial = $("#selProgramaI").val();
    var prgFinal = $("#selProgramaF").val();
    var ramo = $("#ramoInList").val();
    ramo = ramo.replaceAll("'", "");
    if (prgFinal < prgInicial) {
        alert("El programa final no puede ser menor que el inicial");
        $("#selProgramaF").val(-1);
        return;
    }
    if (parseInt(prgInicial) === parseInt(prgFinal)) {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetDepartamentosByRamoPrograma.jsp",
            dataType: 'html',
            data: {
                programa: prgInicial,
                ramo: ramo
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al consultar los departamentos");
                    return;
                } else {
                    $(".selRangoDepto").html(response);
                }
            }
        });
    } else {
        $(".selRangoDepto").html("<option value='-1'> -- Seleccione un departamento -- </option> ");
    }
}

function getDeptoByProgramaI() {
    var prgInicial = $("#selProgramaI").val();
    var prgFinal = $("#selProgramaF").val();
    var ramo = $("#ramoInList").val();
    ramo = ramo.replaceAll("'", "");
    if (parseInt(prgFinal) < parseInt(prgInicial) && parseInt(prgFinal) !== -1) {
        alert("El programa inicial no puede ser mayor que el final");
        $("#selProgramaI").val(-1);
        return;
    }
    if (prgInicial === prgFinal) {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetDepartamentosByRamoPrograma.jsp",
            dataType: 'html',
            data: {
                programa: prgInicial,
                ramo: ramo
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al consultar los departamentos");
                } else {
                    $(".selRangoDepto").html(response);
                }
            }
        });
    } else {
        $(".selRangoDepto").html("<option value='-1'> -- Seleccione un departamento -- </option> ");
    }
}

function validaPartidaMenor() {
    var partidaIn = $("#selPartIni").val();
    var partidaFi = $("#selPartFin").val();
    if (parseInt(partidaIn) > parseInt(partidaFi) && partidaFi !== "-1") {
        alert("La partida inicial debe de ser menor que la partida final");
        $("#selPartIni").val(-1);
    }
}

function validaPartidaMayor() {
    var partidaIn = $("#selPartIni").val();
    var partidaFi = $("#selPartFin").val();
    if (parseInt(partidaIn) > parseInt(partidaFi) && partidaIn !== "-1") {
        alert("La partida final debe de ser mayor que la partida inicial");
        $("#selPartFin").val(-1);
    }
}

function contCheckRamosRptExcel(IdCheck) {

    var RamoList = document.getElementById("contRegSelect");
    var longRamoList = RamoList.value;
    var checkRam = document.getElementById("check" + IdCheck);
    var labelCont = document.getElementById("labelCont");

    if (checkRam.checked)
        longRamoList++;
    else
        longRamoList--;

    if (longRamoList == 1) {
        labelCont.value = longRamoList + " Seleccionado";

    } else {
        labelCont.value = longRamoList + " Seleccionados";
    }

    RamoList.value = longRamoList;

    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var i = 0;

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    $("#ramoInList").val(ramoSelect);

    if (contRamoSelect > 0 && contRamoSelect < 2) {


        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetProgramasByRamoInList.jsp',
            datatype: 'html',
            data: {
                ramoInList: ramoSelect
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res == "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    if (trim(response.replace("<!DOCTYPE html>", "")) != -2)
                        $("#divComboProgramas").html(response);
                    else {
                        //  alert("El ramo seleccionado no esta disponible para presupuestaci\u00f3n");
                        // $("#selPrograma").html("<option value='-1'> -- Seleccione un programa -- </option>");
                    }
                }
                $('#mensaje').hide();

            }
        });
    } else
    {
        $("#divComboProgramas").html("");
        $("#divComboProyectos").html("");
    }




}

function allChecksRamosRptExcel() {

    var contReg = document.getElementById("contReg").value;
    var RamoList = document.getElementById("contRegSelect");
    var longRamoList = RamoList.value;
    var allChecks = document.getElementById("allChecks");
    var labelCont = document.getElementById("labelCont");
    var i = 0;

    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);
        if (allChecks.checked)
            tempChecks.checked = true;
        else
            tempChecks.checked = false;
    }

    if (allChecks.checked == true) {
        labelCont.value = contReg + " Seleccionados";
        RamoList.value = contReg;
    } else {
        labelCont.value = "0 Seleccionados";
        RamoList.value = 0;
    }

    contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    $("#ramoInList").val(ramoSelect);
    $("#programaInList").val("");
    $("#proyectoInList").val("");
    $("#divComboProgramas").html("");
    $("#divComboProyectos").html("");
}

function contCheckProgramasRptExcel(IdCheck) {


    var ramoSelect = $("#ramoInList").val();
    var programaList = document.getElementById("contRegPrgSelect");
    var longProgramaList = programaList.value;
    var labelContPrograma = document.getElementById("labelContProgramas");

    if ($("#checkPrograma" + IdCheck).is(':checked'))
        longProgramaList++;
    else
        longProgramaList--;

    if (longProgramaList == 1) {
        labelContPrograma.value = longProgramaList + " Seleccionado";

    } else {
        labelContPrograma.value = longProgramaList + " Seleccionados";
    }

    programaList.value = longProgramaList;

    var contRegPrograma = $("#contRegPrograma").val();
    var programaSelect = "";
    var contProgramaSelect = 0;
    var i = 0;

    for (i = 1; i <= contRegPrograma; i++) {
        var tempChecks = $("#checkPrograma" + i).val();
        if ($("#checkPrograma" + i).is(':checked')) {
            var tempProgramaChecks = $("#programaCheck" + i).val();
            if (contProgramaSelect == 0)
                programaSelect = programaSelect + "'" + tempProgramaChecks + "'";
            else
                programaSelect = programaSelect + "," + "'" + tempProgramaChecks + "'";
            contProgramaSelect++;
        }
    }

    $("#programaInList").val(programaSelect);




    if (contProgramaSelect > 0 && contProgramaSelect < 2) {


        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetProyByRamPrgInList.jsp',
            datatype: 'html',
            data: {
                ramoInList: ramoSelect,
                programaInList: programaSelect
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res == "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    if (trim(response.replace("<!DOCTYPE html>", "")) != -2)
                        $("#divComboProyectos").html(response);
                    else {
                        //  alert("El ramo seleccionado no esta disponible para presupuestaci\u00f3n");
                        // $("#selPrograma").html("<option value='-1'> -- Seleccione un programa -- </option>");
                    }
                }
                $('#mensaje').hide();

            }
        });
    } else
    {
        $("#divComboProyectos").html("");
    }




}

function allChecksProgramasRptExcel() {

    var ramoSelect = $("#ramoInList").val();
    var contRegPrograma = document.getElementById("contRegPrograma").value;
    var programaList = document.getElementById("contRegPrgSelect");
    var longProgramaList = programaList.value;
    var allChecksPrograma = document.getElementById("allChecksPrograma");
    var labelContProgramas = document.getElementById("labelContProgramas");
    var i = 0;

    for (i = 1; i <= contRegPrograma; i++) {
        var tempChecks = document.getElementById("checkPrograma" + i);
        if (allChecksPrograma.checked)
            tempChecks.checked = true;
        else
            tempChecks.checked = false;
    }

    if (allChecksPrograma.checked == true) {
        labelContProgramas.value = contRegPrograma + " Seleccionados";
        programaList.value = contRegPrograma;
    } else {
        labelContProgramas.value = "0 Seleccionados";
        programaList.value = 0;
    }

    contRegPrograma = $("#contRegPrograma").val();
    var programaSelect = "";
    var contProgramaSelect = 0;

    var i = 0;
    for (i = 1; i <= contRegPrograma; i++) {
        var tempChecks = $("#checkPrograma" + i).val();
        if ($("#checkPrograma" + i).is(':checked')) {
            contProgramaSelect++;
            var tempProgramaChecks = $("#programaCheck" + i).val();
            if (contProgramaSelect == 1)
                programaSelect = programaSelect + "'" + tempProgramaChecks + "'";
            else
                programaSelect = programaSelect + "," + "'" + tempProgramaChecks + "'";
        }
    }
    if (contProgramaSelect > 0 && contProgramaSelect < 2) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetProyByRamPrgInList.jsp',
            datatype: 'html',
            data: {
                ramoInList: ramoSelect,
                programaInList: programaSelect
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res == "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    if (trim(response.replace("<!DOCTYPE html>", "")) != -2)
                        $("#divComboProyectos").html(response);
                    else {
                        //  alert("El ramo seleccionado no esta disponible para presupuestaci\u00f3n");
                        // $("#selPrograma").html("<option value='-1'> -- Seleccione un programa -- </option>");
                    }
                }
                $('#mensaje').hide();

            }
        });
    } else {
        $("#proyectoInList").val("");
        $("#divComboProyectos").html("");
    }
    $("#programaInList").val(programaSelect);
}

function contCheckProyectosRptExcel(IdCheck) {


    var ramoSelect = $("#ramoInList").val();
    var programaSelect = $("#programaInList").val();

    var proyectoList = document.getElementById("contRegProySelect");
    var longProyectoList = proyectoList.value;
    var labelContProyecto = document.getElementById("labelContProyectos");

    if ($("#checkProyecto" + IdCheck).is(':checked'))
        longProyectoList++;
    else
        longProyectoList--;

    if (longProyectoList == 1) {
        labelContProyecto.value = longProyectoList + " Seleccionado";

    } else {
        labelContProyecto.value = longProyectoList + " Seleccionados";
    }

    proyectoList.value = longProyectoList;

    var contRegProyecto = $("#contRegProyecto").val();
    var proyectoSelect = "";
    var contProyectoSelect = 0;
    var i = 0;

    for (i = 1; i <= contRegProyecto; i++) {
        var tempChecks = $("#checkProyecto" + i).val();
        if ($("#checkProyecto" + i).is(':checked')) {
            var tempProyectoChecks = $("#proyectoCheck" + i).val();
            if (contProyectoSelect == 0)
                proyectoSelect = proyectoSelect + "'" + tempProyectoChecks + "'";
            else
                proyectoSelect = proyectoSelect + "," + "'" + tempProyectoChecks + "'";
            contProyectoSelect++;
        }
    }


    $("#proyectoInList").val(proyectoSelect);




}

function allChecksProyectosRptExcel() {


    var contRegProyecto = document.getElementById("contRegProyecto").value;
    var proyectoList = document.getElementById("contRegProySelect");
    var longProyectoList = proyectoList.value;
    var allChecksProyecto = document.getElementById("allChecksProyecto");
    var labelContProyectos = document.getElementById("labelContProyectos");
    var i = 0;

    for (i = 1; i <= contRegProyecto; i++) {
        var tempChecks = document.getElementById("checkProyecto" + i);
        if (allChecksProyecto.checked)
            tempChecks.checked = true;
        else
            tempChecks.checked = false;
    }

    if (allChecksProyecto.checked == true) {
        labelContProyectos.value = contRegProyecto + " Seleccionados";
        proyectoList.value = contRegProyecto;
    } else {
        labelContProyectos.value = "0 Seleccionados";
        proyectoList.value = 0;
    }

    contRegProyecto = $("#contRegProyecto").val();
    var proyectoSelect = "";
    var contProyectoSelect = 0;

    var i = 0;
    for (i = 1; i <= contRegProyecto; i++) {
        var tempChecks = $("#checkProyecto" + i).val();
        if ($("#checkProyecto" + i).is(':checked')) {
            var tempProyectoChecks = $("#proyectoCheck" + i).val();
            contProyectoSelect++;
            if (contProyectoSelect == 1)
                proyectoSelect = proyectoSelect + "'" + tempProyectoChecks + "'";
            else
                proyectoSelect = proyectoSelect + "," + "'" + tempProyectoChecks + "'";
        }
    }

    $("#proyectoInList").val(proyectoSelect);


}

function congelaPresupuesto() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxCongelaPresupuesto.jsp',
        datatype: 'html',
        data: {
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                if (res.match(/1|/i)) {
                    res = res.split("|")[1];
                    alert(res);
                } else if (res.match(/0|/i)) {
                    res = res.split("|")[1];
                    alert(res);
                } else {
                    alert(res);
                }
            }
        }
    });
}

function generaReporteCongelado() {
    $("#frmReporteCongelado").submit();
}

function getProgramasByRamoUsuario() {
    var ramo = $("#selRamo").val();
    $("#selPrg").html("<option value='-1'>-- Seleccione un programa -- </option> ");
    $("#selProy").html("<option value='-1'>-- Seleccione un proyecto/actividad --</option>");
    $("#selMeta").html("<option value='-1'>-- Seleccione una meta --</option>");
    $("#selAccion").html("<option value='-1'> -- Seleccione una acci&oacute;n --</option>");
    $("#selPartida").html("<option value='-1'> -- Seleccione una partida --</option>");
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (ramo !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetProgramaByRamoUsuario.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res.split("|")[1];
                        alert(res);
                    } else {
                        $("#selPrg").html(response);
                    }
                }
            }
        });
    }
}
function getProyectosByProgramaUsuario() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    $("#selProy").html("<option value='-1'>-- Seleccione un proyecto/actividad --</option>");
    $("#selMeta").html("<option value='-1'>-- Seleccione una meta --</option>");
    $("#selAccion").html("<option value='-1'> -- Seleccione una acci&oacute;n --</option>");
    $("#selPartida").html("<option value='-1'> -- Seleccione una partida --</option>");
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (prg !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetProyectosByProgramaUsuario.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo,
                programa: prg
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selProy").html(response);
                    }
                }
            }
        });
    }
}
function getMetasByProyectoUsuario() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    $("#selMeta").html("<option value='-1'>-- Seleccione una meta --</option>");
    $("#selAccion").html("<option value='-1'> -- Seleccione una acci&oacute;n --</option>");
    $("#selPartida").html("<option value='-1'> -- Seleccione una partida --</option>");
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (proy !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetMetaByProyectoUsuario.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selMeta").html(response);
                    }
                }
            }
        });
    }
}

function getMetasByProyecto() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    $("#selMeta").html("<option value='-1'>-- Seleccione una meta --</option>");
    $("#selAccion").html("<option value='-1'> -- Seleccione una acci&oacute;n --</option>");
    if (proy !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetMetaByProyecto.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selMeta").html(response);
                    }
                }
            }
        });
    }
}
function getAccionByMetaUsuario(ramo, prg, proy, meta) {
    if (typeof ramo === 'undefined')
        ramo = $("#selRamo").val();
    if (typeof prg === 'undefined')
        prg = $("#selPrg").val();
    if (typeof proy === 'undefined')
        proy = $("#selProy").val();
    if (typeof meta === 'undefined')
        meta = $("#selMeta").val();
    $("#selAccion").html("<option value='-1'> -- Seleccione una acci&oacute;n --</option>");
    $("#selPartida").html("<option value='-1'> -- Seleccione una partida --</option>");
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (meta !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetAccionByMetaUsuario.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                meta: meta
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selAccion").html(response);
                    }
                }
            }
        });
    }
}

function getAccionByMeta(ramo, prg, proy, meta) {
    if (typeof ramo === 'undefined')
        ramo = $("#selRamo").val();
    if (typeof prg === 'undefined')
        prg = $("#selPrg").val();
    if (typeof proy === 'undefined')
        proy = $("#selProy").val();
    if (typeof meta === 'undefined')
        meta = $("#selMeta").val();
    $("#selAccion").html("<option value='-1'> -- Seleccione una acci&oacute;n --</option>");
    if (meta !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetAccionByMeta.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: prg,
                meta: meta
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selAccion").html(response);
                    }
                }
            }
        });
    }
}

function getPartidaByAccionUsuario() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    $("#selPartida").html("<option value='-1'> -- Seleccione una partida --</option>");
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (accion !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetPartidasByAccionUsuario.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                meta: meta,
                accion: accion
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selPartida").html(response);
                    }
                }
            }
        });
    }
}
function getPartidaByAccionAmplRed() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    $("#selPartida").html("<option value='-1'> -- Seleccione una partida --</option>");
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (accion !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetPartidasByAccionAmpRed.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                meta: meta,
                accion: accion,
                ampRed: true
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selPartida").html(response);
                    }
                }
            }
        });
    }
}
function getRelLaboralByPartidaUsuario() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var partida = $("#selPartida").val();
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (partida !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetRelLaboralByPartidaUsuario.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                meta: meta,
                accion: accion,
                partida: partida
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else if (res[0] === '1') {
                        $("#selRelLaboral").html(response);
                        $("#tr-rel-laboral").removeAttr("style");
                    } else if (res[0] === '2') {
                        $("#selFuente").html(response);
                        $("#tr-rel-laboral").css("display", "none");
                    }
                }
            }
        });
    }
}

function getRelLaboralByPartidaAmpRed() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var partida = $("#selPartida").val();
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (partida !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetRelLaboralByPartidaAmpRed.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                meta: meta,
                accion: accion,
                partida: partida
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else if (res[0] === '1') {
                        $("#selRelLaboral").html(response);
                        $("#tr-rel-laboral").removeAttr("style");
                    } else if (res[0] === '2') {
                        $("#selFuente").html(response);
                        $("#tr-rel-laboral").css("display", "none");
                    } else if (res[0] === '3') {
                        alert(res[1]);
                        $("#selPartida").val("-1");
                    }
                }
            }
        });
    }
}
function getFuenteFinanciamientoByRelLaboralUsuario() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var partida = $("#selPartida").val();
    var relLaboral = $("#selRelLaboral").val();
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (relLaboral !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetFuenteFinanciamientolByRelLaboralUsuario.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                meta: meta,
                accion: accion,
                partida: partida,
                relLaboral: relLaboral
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selFuente").html(response);
                    }
                }
            }
        });
    }
}
function getFuenteFinanciamientoByRelLaboralAmpRed() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var partida = $("#selPartida").val();
    var relLaboral = $("#selRelLaboral").val();
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (relLaboral !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetFuenteFinanciamientolByRelLaboralAmpRed.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                meta: meta,
                accion: accion,
                partida: partida,
                relLaboral: relLaboral
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selFuente").html(response);
                    }
                }
            }
        });
    }
}
function getRequerimientosByFuenteFinanciamientoUsuario() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var partida = $("#selPartida").val();
    var relLaboral = $("#selRelLaboral").val();
    var fuente = $("#selFuente").val();
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (fuente !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetRequerimientosByFuenteFinanciamientoUsuario.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: prg,
                meta: meta,
                accion: accion,
                partida: partida,
                relLaboral: relLaboral,
                fuente: fuente
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selReq").html(response);
                    }
                }
            }
        });
    }
}
function getAsignadoPPTOByMes() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var partida = $("#selPartida").val();
    var relLaboral = $("#selRelLaboral").val();
    var fuente = $("#selFuente").val();
    var proy = $("#selProy").val();
    var mes = $("#mesPPTO").val();
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    if (fuente !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetAsignadoPPTOByMes.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo,
                programa: prg,
                meta: meta,
                accion: accion,
                partida: partida,
                relLaboral: relLaboral,
                fuente: fuente,
                mesPPTO: mes,
                proyecto: proy
            },
            success: function(response) {
                var disponible;
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    res = res.split("|");
                    disponible = parseFloat(res[1]);
                    disponible = disponible.toFixed(2);
                    //$("#inp-txt-impor-trans").val(addCommas(disponible));
                    $("#inp-txt-disp-ampred").val(addCommas(disponible));
                    $("#inp-txt-disp-trans").val(addCommas(disponible));
                }
            }
        });
    }
}

function getInfoMetaEstimacion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var oficio = $("#inp-folio-usr").val();
    var autorizacion = $("#autorizacion").val();

    if (metaId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarMovimientosPendientes.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                oficio: oficio,
                identificador: identificador,
                estatus: estatus,
                opcion: 1
            },
            success: function(response) {

                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var abrirPopUp = true;

                if (res == "3") {
                    alert("Existe un movimiento igual pendiente no se puede agregar uno nuevo. \nFavor de editar el ya existente.");
                    abrirPopUp = false;
                    $('#mensaje').hide();
                } else {
                    if (res.split("|")[0] == "1") {
                        abrirPopUp = false;
                        alert("Existe un movimiento igual pendiente de autorizar no se puede agregar uno nuevo. Folio: " + res.split("|")[1]);
                        $('#mensaje').hide();
                    }
                }

                if (res == "5") {
                    alert("No hay recalendarizaciones registradas.");
                    $('#mensaje').hide();
                    return;
                }
                if (res == "6") {
                    alert("Para poder modificar esta meta debe existir una recalendarizaci\u00f3n previa");
                    $('#mensaje').hide();
                    return;
                }
                if (res == "7") {
                    alert("Para poder modificar esta acci\u00f3n debe existir una recalendarizaci\u00f3n previa");
                    $('#mensaje').hide();
                    return;
                }
                if (res == "8") {
                    alert("NADA!!");
                    $('#mensaje').hide();
                    return;
                }

                if (abrirPopUp == true) {
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxGetInfoRecalendarizacionMeta.jsp',
                        datatype: 'html',
                        data: {
                            ramoId: ramoId,
                            metaId: metaId,
                            identificador: identificador,
                            estatus: estatus,
                            autorizacion: autorizacion,
                            folio: oficio
                        },
                        success: function(response) {

                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            $("#PopUpZone").html(res);
                            $("#PopUpZone").fadeIn();
                            $('#mensaje').hide();

                        }
                    });
                }
            }
        });



    } else {
        alert("Debe de seleccionar una meta para continuar");
    }
}

function actualizarRecalendarizacionMeta() {

    var calPropuesta = $("#calPropuesta").val();
    calPropuesta = calPropuesta.replaceAll(",", "");
    var calOriginal = $("#calOriginal").val();
    calOriginal = calOriginal.replaceAll(",", "");
    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var metaId = $("#metaId").val();
    var input = $(".calenVistaRC .estimacion");
    var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);
    var valores = "";
    var valorSnComa;

    /*if (sumEstimacion != sumRecalendarizacion) {
     alert("La sumatoria de la recalendarizaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        valores += valorSnComa + ",";
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarRecalendarizacionMeta.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            valores: valores,
            metaId: metaId,
            identificador: identificador,
            sumEstimacion: sumEstimacion,
            sumRecalendarizacion: sumRecalendarizacion,
            calOriginal: calOriginal,
            calPropuesta: calPropuesta

        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            actualizaTablaMovimientos();
            $("#PopUpZone").html();
            fadeOutPopUp("PopUpZone");
            $('#mensaje').hide();

        }
    });

}

function fadeOutPopUp(popUpName) {
    $("#" + popUpName).fadeOut();
}

function mostarMovimientos() {
    if ($("#chk-imp-prog").is(":checked")) {
        $("#fldst-movs").show();
        $("#btn-edit-meta").show();
        $("#btn-edit-accion").show();
    } else {
        $("#fldst-movs").hide();
        $("#btn-edit-meta").hide();
        $("#btn-edit-accion").hide();
    }
}
function mostarMovimientosAmpliacionRed() {
    var selMeta = $("#selMeta").val();
    var selAccion = $("#selAccion").val();
    if ($("#chk-imp-prog").is(":checked")) {
        $("#fldst-movs").show();
        if (selMeta !== '-1') {
            $("#btn-edit-meta").show();
        } else {
            $("#btn-add-meta").show();
        }
        if (selAccion !== '-1') {
            $("#btn-edit-accion").show();
        } else {
            $("#btn-add-accion").show();
        }
    } else {
        $("#fldst-movs").hide();
        $("#btn-edit-meta").hide();
        $("#btn-edit-accion").hide();
        $("#btn-add-meta").hide();
        $("#btn-add-accion").hide();
    }
}

function actualizaTablaMovimientos() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaMovimientos.jsp',
        datatype: 'html',
        data: {
            estatus: estatus
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#fldst-movs tbody").html(response);
            }
        }
    });
}

function actualizaTablaMovimientosAmplRed() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    var autorizar = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaMovtoAmplRed.jsp',
        datatype: 'html',
        data: {
            estatus: estatus,
            autorizar: autorizar
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#tbl-movs tbody").html(response);
            }
        }
    });
}

function actualizaTablaAccionReq() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaAccionReq.jsp',
        datatype: 'html',
        data: {
            estatus: estatus
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#tbl-reqs tbody").html(response);
            }
        }
    });
}

function actualizaMovimientosReprogramacion() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    var autorizar = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaMovtoReprogramacion.jsp',
        datatype: 'html',
        data: {
            estatus: estatus,
            autorizar: autorizar
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#tbl-movto-reprog tbody").html(response);
            }
        }
    });
}

function actualizaMovimientosTransferencia() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    var autorizar = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaMovtoTransferencia.jsp',
        datatype: 'html',
        data: {
            estatus: estatus,
            autorizar: autorizar
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#tbl-movs tbody").html(response);
            }
        }
    });
}

function actualizaMovimientosTransferenciaReciben() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    var transferenciaId = $("#transferenciaId").val();
    var autorizar = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaMovtoTransferenciaReciben.jsp',
        datatype: 'html',
        data: {
            estatus: estatus,
            transferenciaId: transferenciaId,
            autorizar: autorizar
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#tbl-movs tbody").html(response);
            }
        }
    });
}

function enviarAutorizacion(isAutorizado) {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#inpAutorizacion").val();
    var estatus = $("#inpEstatus").val();
    var justificacion = $("#txt-area-justif").val();
    justificacion = justificacion.toUpperCase();
    var checkImpacto;
    var folio = $("#inp-folio-usr").val();
    var dateFor = $("#dateFor").val();
    var isActual = $("#isActual").val();
    var cont = 0;
    if ($("#chk-imp-prog").is(":checked")) {
        checkImpacto = true;
    } else {
        checkImpacto = false;
    }
    if (justificacion === "") {
        cont++;
        $("#txt-area-justif").addClass("errorClass");
    } else {
        $("#txt-area-justif").removeClass("errorClass");
    }
    if (cont > 0) {
        $('#mensaje').hide();
        $("#errorGrabar").val(0);
        alert("Favor de completar los campos marcados");
        return false;
    } else {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxEnviarAutorizacionMovimientoReq.jsp',
            datatype: 'html',
            async: false,
            data: {
                autorizacion: autorizacion,
                estatus: estatus,
                tipoMov: 'C',
                justificacion: justificacion,
                checkImpacto: checkImpacto,
                folio: folio,
                dateFor: dateFor,
                isActual: isActual
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    res = res.split("|");
                    if (res[0] === "1") {
                        $("#inp-folio-usr").val(res[2]);
                        $(".btn-enviar").show();
                        $(".btn-cancelar").show();
                    }
                    res = res[1];
                    if (!isAutorizado) {
                        alert(res);
                    } else {
                        $("#errorGrabar").val(1);
                        return true;
                    }
                } else {
                    alert("Ocurri\u00f3 un error no esperado");
                    if (!isAutorizado) {
                        $("#errorGrabar").val(0);
                        return false;
                    }
                }
            }
        });
    }
}

function sumatoriaRecalendarizacion() {

    var total = 0;
    var input = $(".calenVistaRC .estimacion");

    for (var it = 0; it < input.length; it++) {
        total += parseFloat(input[it].value.replaceAll(",", ""));
    }

    $("#sumRecalendarizacion").val(addCommas(total));

}

function getInfoAccionEstimacion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionId = $("#selAccion").val();
    var oficio = $("#inp-folio-usr").val();
    var autorizacion = $("#autorizacion").val();

    if (accionId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarMovimientosPendientes.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                oficio: oficio,
                opcion: 2
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var abrirPopUp = true;

                if (res == "4") {
                    alert("Existe un movimiento igual pendiente no se puede agregar uno nuevo. \nFavor de editar el ya existente.");
                    abrirPopUp = false;
                    $('#mensaje').hide();
                } else {
                    if (res.split("|")[0] === "2") {
                        abrirPopUp = false;
                        alert("Existe un movimiento igual pendiente de autorizar no se puede agregar uno nuevo. Folio: " + res.split("|")[1]);
                        $('#mensaje').hide();
                    }
                }
                if (res == "5") {
                    alert("No hay recalendarizaciones registradas.");
                    $('#mensaje').hide();
                    return;
                }
                if (res == "6") {
                    alert("Para poder modificar esta meta debe existir una recalendarizaci\u00f3n previa");
                    $('#mensaje').hide();
                    return;
                }
                if (res == "7") {
                    alert("Para poder modificar esta acci\u00f3n debe existir una recalendarización previa");
                    $('#mensaje').hide();
                    return;
                }
                if (res == "8") {
                    alert("NADA!!");
                    $('#mensaje').hide();
                    return;
                }

                if (abrirPopUp == true) {
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxGetInfoRecalendarizacionAccion.jsp',
                        datatype: 'html',
                        data: {
                            ramoId: ramoId,
                            metaId: metaId,
                            accionId: accionId,
                            identificador: identificador,
                            estatus: estatus,
                            autorizacion: autorizacion,
                            folio: oficio
                        },
                        success: function(response) {
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            $("#PopUpZone").html(res);
                            $("#PopUpZone").fadeIn();
                            $('#mensaje').hide();

                        }
                    });
                }
            }
        });



    } else {
        alert("Debe de seleccionar una acci\u00f3n para continuar");
    }
}
function getInfoAccionRequerimiento(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var ramoDescr = $("#selRamo option:selected").text();
    var programaId = $("#selPrg").val();
    var programaDescr = $("#selPrg option:selected").text();
    var proyectoCod = $("#selProy").val();
    var arrayProy = proyectoCod.split(",");
    var proyectoId = arrayProy[0];
    var tipoProyecto = arrayProy[1];
    var proyectoDescr = $("#selProy option:selected").text();
    var metaId = $("#selMeta").val();
    var metaDescr = $("#selMeta option:selected").text();
    var accionId = $("#selAccion").val();
    var accionDescr = $("#selAccion option:selected").text();
    var partida = $("#selPartida").val();
    var partidaDescr = $("#selPartida option:selected").text();
    var relLaboral = $("#selRelLaboral").val();
    var relLaboralDescr = $("#selRelLaboral option:selected").text();
    var fuente = $("#selFuente").val();
    var fuenteDescr = $("#selFuente option:selected").text();
    var req = $("#selReq").val();
    var reqDescr = $("#selReq option:selected").text();
    var folio = $("#inp-folio-usr").val();
    $("#identificador").val(identificador);
    $("#estatus").val(estatus);
    var fecha = $("#dateFor").val();
    //var accionReqRep = contarMovimientosAccionReq();
    //if(accionReqRep){
    if (req !== '-1') {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetMovOficioAccionRequerimiento.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                ramoDescr: ramoDescr,
                programaId: programaId,
                programaDescr: programaDescr,
                proyectoId: proyectoId,
                tipoProyecto: tipoProyecto,
                proyectoDescr: proyectoDescr,
                metaId: metaId,
                metaDescr: metaDescr,
                accionId: accionId,
                accionDescr: accionDescr,
                identificador: identificador,
                estatus: estatus,
                partida: partida,
                partidaDescr: partidaDescr,
                relLaboral: relLaboral,
                relLaboralDescr: relLaboralDescr,
                fuente: fuente,
                fuenteDescr: fuenteDescr,
                req: req,
                reqDescr: reqDescr,
                fecha: fecha,
                folio: folio
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var resplit = res.split("|");
                $('#mensaje').hide();
                if (res !== '-1') {
                    if (resplit[0] === "3") {
                        alert(resplit[1]);
                        return false;
                    }
                    $("#PopUpZone").html(res);
                    $("#PopUpZone").fadeIn();
                } else {
                    alert("Ocurri\u00f3 un error inesperado al cargar la informaci\u00f3n");
                }
            }
        });
    } else {
        alert("Debe de seleccionar un requerimiento para continuar");
    }
    /*}else{
     alert("Ya existe un requerimiento con movimientos sin autorizar");
     return false;
     }*/
}
function getInfoEdicionAccionRequerimiento(identificador, estatus, ramo, programa, meta, accion, requerimiento, fuente, partida, relLaboral) {
    $("#identificador").val(identificador);
    $("#estatus").val(estatus);
    var folio = $("#inp-folio-usr").val();
    var importeTrans = $("#importeTrans").val();
    var fecha = $("#dateFor").val();

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetMovOficioAccionRequerimiento.jsp',
        datatype: 'html',
        data: {
            ramoId: ramo,
            programaId: programa,
            metaId: meta,
            accionId: accion,
            fecha: fecha,
            identificador: identificador,
            estatus: estatus,
            req: requerimiento,
            edicion: 1,
            importeTrans: importeTrans,
            folio: folio,
            fuente: fuente,
            partida: partida,
            relLaboral: relLaboral
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            var resplit = res.split("|");
            $('#mensaje').hide();
            if (res !== '-1') {
                if (resplit[0] === "3") {
                    alert(resplit[1]);
                    return false;
                }
                $("#PopUpZone").html(res);
                $("#PopUpZone").fadeIn();
            } else {
                alert("Ocurri\u00f3 un error inesperado al cargar la informaci\u00f3n");
            }
        }
    });
}

function getInfoEdicionAccionRequerimientoAmpReq(identificador, estatus, ramo, programa, meta, accion, requerimiento, tipo) {
    $("#identificador").val(identificador);
    $("#estatus").val(estatus);
    var folio = $("#inp-folio-usr").val();
    var fecha = $("#dateFor").val();
    if (tipo === "A") {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoMovAmpRedAccionReq.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: programa,
                meta: meta,
                fecha: fecha,
                accion: accion,
                identificador: identificador,
                estatus: estatus,
                req: requerimiento,
                edicion: 1
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var resplit = res.split("|");
                $('#mensaje').hide();
                if (res !== '-1') {
                    if (resplit[0] === "3") {
                        alert(resplit[1]);
                        return false;
                    }
                    $("#PopUpZone").html(res);
                    $("#PopUpZone").fadeIn();
                } else {
                    alert("Ocurri\u00f3 un error inesperado al cargar la informaci\u00f3n");
                }
            }
        });
    } else {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetImporteReduccion.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: programa,
                meta: meta,
                fecha: fecha,
                accion: accion,
                identificador: identificador,
                estatus: estatus,
                edicion: 1,
                folio: folio
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var resplit = res.split("|");
                $('#mensaje').hide();
                if (res !== '-1') {
                    if (resplit[0] === "3") {
                        alert(resplit[1]);
                        return false;
                    }
                    $("#PopUpZone").html(res);
                    $("#PopUpZone").fadeIn();
                } else {
                    alert("Ocurri\u00f3 un error inesperado al cargar la informaci\u00f3n");
                }
            }
        });
    }
}

function getInfoEdicionAccionRequerimientoTransferencia(identificador, estatus, ramo, programa, meta, accion, requerimiento, importe) {
    $("#identificador").val(identificador);
    $("#estatus").val(estatus);
    var folio = $("#inp-folio-usr").val();
    var fecha = $("#fecha").val();
    var transferenciaId = $("#transferenciaId").val();
    var importeTrans = $("#importeTrans").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoMovTransferenciaAccionReq.jsp',
        datatype: 'html',
        data: {
            ramo: ramo,
            programa: programa,
            meta: meta,
            fecha: fecha,
            accion: accion,
            identificador: identificador,
            estatus: estatus,
            req: requerimiento,
            importe: importe,
            edicion: 1,
            transferenciaId: transferenciaId,
            importeTrans: importeTrans,
            fecha: fecha
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            var resplit = res.split("|");
            $('#mensaje').hide();
            if (res !== '-1') {
                if (resplit[0] === "3") {
                    alert(resplit[1]);
                    return false;
                } else if (resplit[0] === "2") {
                    alert("La acción o meta en el movimiento no puede ser calendarizada en cero");
                    return false;
                }
                $("#PopUpZone").html(res);
                $("#PopUpZone").fadeIn();
            } else {
                alert("Ocurri\u00f3 un error inesperado al cargar la informaci\u00f3n");
            }
        }
    });
}

function actualizarRecalendarizacionAccion() {

    var calPropuesta = $("#calPropuesta").val();
    calPropuesta = calPropuesta.replaceAll(",", "");
    var calOriginal = $("#calOriginal").val();
    calOriginal = calOriginal.replaceAll(",", "");
    var identificador = new String($("#identificador").val());
    var ramoId = $("#ramoId").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();
    var input = $(".calenVistaRC .estimacion");
    var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);
    var valores = "";
    var valorSnComa;

    /*if (sumEstimacion != sumRecalendarizacion) {
     alert("La sumatoria de la recalendarizaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        valores += valorSnComa + ",";
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarRecalendarizacionAccion.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            valores: valores,
            metaId: metaId,
            accionId: accionId,
            identificador: identificador,
            calPropuesta: calPropuesta,
            calOriginal: calOriginal,
            sumEstimacion: sumEstimacion,
            sumRecalendarizacion: sumRecalendarizacion
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            actualizaTablaMovimientos();
            $("#PopUpZone").html();
            fadeOutPopUp("PopUpZone");
            $('#mensaje').hide();

        }
    });

}

function getMovimientos() {
    var tipoFlujo = $("#selEstatusBase").val().split('|')[0];
    var estatusBase = $("#selEstatusBase").val().split('|')[1];
    var tipoMovId = $("#selTipoMovto").val();
    var opcion = 1;
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var cont = $("#contRegSelect").val();
    var i = 0;

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        $("#ramoInList").val("-1");
        ramoSelect = "-1";
    }

    if (estatusBase.length > 0 && tipoMovId.length > 0) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetMovimientos.jsp",
            dataType: 'html',
            data: {
                estatusBase: estatusBase,
                tipoMovId: tipoMovId,
                tipoFlujo: tipoFlujo,
                ramoSelect: ramoSelect,
                opcion: opcion
            },
            success: function(respoce) {
                $('#mensaje').hide();
                $("#div-movimientos").html(respoce);
            }
        });
    }
}

function verMovtoAutorizar(folio, estatus, tipoMov, tipoOficio, tipoFlujo) {
    $("#folio").val(folio);
    $("#estatus").val(estatus);
    $("#tipoMov").val(tipoMov);
    $("#tipoOficio").val(tipoOficio);
    $("#tipoFlujo").val(tipoFlujo);
    $("#autorizacion").val(true);

    if (tipoMov == 'C') {
        $("#frmAutoriza").attr('action', 'recalendarizacion.jsp');
    } else if (tipoMov == 'R') {
        $("#frmAutoriza").attr('action', 'reprogramacion.jsp');
    } else if (tipoMov == 'A') {
        $("#frmAutoriza").attr('action', 'ampliacionReduccion.jsp');
    } else if (tipoMov == 'T') {
        $("#frmAutoriza").attr('action', 'transferencia.jsp');
    }
    $("#frmAutoriza").submit();
}

function regresarAutorizacion() {
    $("#frmRegresa").submit();
}

function getTablaSolicitudesMovimientosByRamo() {

    var ramo = $("#opcRamo").val();
    var nuevo = $("#nuevo").val();
    $("#opcion").val(2);
    if (nuevo !== '0') {
        $("#nuevo").val('1');
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizaTablaSolicitudesMovimientos.jsp',
            datatype: 'html',
            data: {
                selRamo: ramo,
                opcion: 2
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    reincioTablaSolicitudes();
                    $("#TableZone tbody").html(res);
                }
                dataTablePOA("tbl-solMovs");
            }
        });
    } else {
        $("#nuevo").val('1');
    }
}

function getMovtosByRamoAfectado() {

    var ramoAfec = $("#selRamoAfectado").val();
    var tipoSolicitudId = $("#selTipoSolicitud").val();
    var nuevo = $("#nuevo").val();
    var estatusMovimientoId = $("#selEstatusMovimiento").val();
    $("#opcion").val(3);
    if (nuevo !== '0') {
        $("#nuevo").val('1');
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizaTablaSolicitudesMovimientos.jsp',
            datatype: 'html',
            data: {
                tipoSolicitudId: tipoSolicitudId,
                estatusMovimientoId: estatusMovimientoId,
                ramoAfectado: ramoAfec,
                opcion: 3
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    reincioTablaSolicitudes();
                    $("#TableZone tbody").html(res);
                }
                dataTablePOA("tbl-solMovs");
            }
        });
    } else {
        $("#nuevo").val('1');
    }
}

function getTablaSolicitudesMovimientos() {

    var tipoSolicitudId = $("#selTipoSolicitud").val();
    var nuevo = $("#nuevo").val();
    var estatusMovimientoId = $("#selEstatusMovimiento").val();
    $("#opcion").val(0);
    if (nuevo !== '0') {
        $("#nuevo").val('1');
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizaTablaSolicitudesMovimientos.jsp',
            datatype: 'html',
            data: {
                tipoSolicitudId: tipoSolicitudId,
                estatusMovimientoId: estatusMovimientoId,
                opcion: 0
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    reincioTablaSolicitudes();
                    $("#TableZone tbody").html(res.split("|")[0]);
                    if (res.split("|").length > 1) {
                        $("#selRamoAfectado").html(res.split("|")[1]);
                    }
                }
                dataTablePOA("tbl-solMovs");
            }
        });
    } else {
        $("#nuevo").val('1');
    }
}

function getTablaSolicitudesMovimientosByFolio() {

    var folio = $("#inpt-folio").val();
    var nuevo = $("#nuevo").val();
    $("#opcion").val(1);
    if (nuevo !== '0') {
        $("#nuevo").val('1');
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizaTablaSolicitudesMovimientos.jsp',
            datatype: 'html',
            data: {
                folio: folio,
                opcion: 1
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    if (res === '1') {
                        $("#TableZone tbody").html("");
                        alert("No se encontr\u00f3 el folio solicitado");
                    } else {
                        reincioTablaSolicitudes();
                        $("#TableZone tbody").html(res);
                    }
                } else {
                    alert("Ocurri\u00f3 un erro al procesar la solicitud");
                }
                dataTablePOA("tbl-solMovs");
            }
        });
    } else {
        $("#nuevo").val('1');
    }
}

function consultaSolicitudMovto(folio, estatus, tipoMov, tipoOficio) {

    var tipoSolicitudId = $("#selTipoSolicitud").val();
    if (tipoSolicitudId === '-1') {
        tipoSolicitudId = tipoMov;
    }

    if (tipoSolicitudId == "A")
        $('#frmConsulta').attr('action', 'ampliacionReduccion.jsp');

    if (tipoSolicitudId == "C")
        $('#frmConsulta').attr('action', 'recalendarizacion.jsp');

    if (tipoSolicitudId == "R")
        $('#frmConsulta').attr('action', 'reprogramacion.jsp');

    if (tipoSolicitudId == "T")
        $('#frmConsulta').attr('action', 'transferencia.jsp');

    $("#folio").val(folio);
    $("#estatus").val(estatus);
    $("#tipoMov").val(tipoMov);
    $("#tipoOficio").val(tipoOficio);
    $("#ramoCons").val($("#opcRamo").val());

    $("#frmConsulta").submit();

}

function getCancelaMov(opcion) {
    var folio = $("#inp-folio-usr").val();

    if (folio.length > 0) {
        getPopUpMotivo("Motivo de la Cancelaci&oacute;n", "getCancelaMovAceptar(" + opcion + ")");
    }
}

function getRechazaMov(opcion) {
    var folio = $("#inp-folio-usr").val();

    if (folio.length > 0) {
        getPopUpMotivo("Motivo del Rechazo", "getRechazaMovAceptar(" + opcion + ")");
    }
}

function getAutorizaMovRecalendarizacion() {
    var folio = $("#inp-folio-usr").val();
    var tipoFlujo = $("#tipoFlujo").val();
    var comentario = $("#comentario").val();
    var opcion = 3;
    var estatus = "";
    if ($("#estatus") != undefined) {
        estatus = $("#estatus").val();
    }

    if (folio.length > 0 && confirm("El movimiento ser\u00e1 autorizado")) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAutorizacion.jsp",
            dataType: 'html',
            data: {
                folio: folio,
                tipoFlujo: tipoFlujo,
                opcion: opcion,
                estatus: estatus,
                comentario: comentario
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(res) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                if (trim(res) == '1') {
                    alert("El movimiento fue autorizado");
                    regresarAutorizacion();
                } else {
                    res = res.split("|");
                    alert(res[1]);
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });
        $('#mensaje').hide();
    }
}

function actualizarAccionReq(identificador) {
    var costoOriginal = $("#inpTxtCostoOriginal").val().replaceAll(',', '');
    var costoNuevo = $("#inpTxtCantAnual").val().replaceAll(',', '');
    var ene = $("#recalinpTxtEne").val().replaceAll(',', '');
    var feb = $("#recalinpTxtFeb").val().replaceAll(',', '');
    var mar = $("#recalinpTxtMar").val().replaceAll(',', '');
    var abr = $("#recalinpTxtAbr").val().replaceAll(',', '');
    var may = $("#recalinpTxtMay").val().replaceAll(',', '');
    var jun = $("#recalinpTxtJun").val().replaceAll(',', '');
    var jul = $("#recalinpTxtJul").val().replaceAll(',', '');
    var ago = $("#recalinpTxtAgo").val().replaceAll(',', '');
    var sep = $("#recalinpTxtSep").val().replaceAll(',', '');
    var oct = $("#recalinpTxtOct").val().replaceAll(',', '');
    var nov = $("#recalinpTxtNov").val().replaceAll(',', '');
    var dic = $("#recalinpTxtDic").val().replaceAll(',', '');
    var costoUnitario = $("#inpTxtCantUnit").val().replaceAll(',', '');
    var ramo = $("#selRamoEd").val();
    var programa = $("#selPrgEd").val();
    var meta = $("#selMetaEd").val();
    var accion = $("#selAccionEd").val();
    var partida = $("#selPartida").val();
    var relLaboral = $("#selRelLaboral").val();
    var fuente = $("#selFuente").val();
    var requerimiento = $("#selReqEd").val();
    var descr = $("#descripcion").val();
    var tipoGasto = $("#tipoGasto").val();
    var articulo = $("#articulo").val();
    var depto = $("#depto").val();
    var folio = $("#inp-folio-usr").val();
    var cadenaMes = $("#cadenaMeses").val().split("|");
    var normativo = $("isNormativo").val();
    var igual = true;
    var estatus = $("#estatus").val();
    if (parseFloat(cadenaMes[0]) !== parseFloat(ene))
        igual = false;
    if (parseFloat(cadenaMes[1]) !== parseFloat(feb))
        igual = false;
    if (parseFloat(cadenaMes[2]) !== parseFloat(mar))
        igual = false;
    if (parseFloat(cadenaMes[3]) !== parseFloat(abr))
        igual = false;
    if (parseFloat(cadenaMes[4]) !== parseFloat(may))
        igual = false;
    if (parseFloat(cadenaMes[5]) !== parseFloat(jun))
        igual = false;
    if (parseFloat(cadenaMes[6]) !== parseFloat(jul))
        igual = false;
    if (parseFloat(cadenaMes[7]) !== parseFloat(ago))
        igual = false;
    if (parseFloat(cadenaMes[8]) !== parseFloat(sep))
        igual = false;
    if (parseFloat(cadenaMes[9]) !== parseFloat(oct))
        igual = false;
    if (parseFloat(cadenaMes[10]) !== parseFloat(nov))
        igual = false;
    if (parseFloat(cadenaMes[11]) !== parseFloat(dic))
        igual = false;
    if (normativo === "1") {
        if (parseFloar(cadenaMes[12]) !== costoUnitario)
            igual = false;
    }
    costoOriginal = parseFloat(costoOriginal).toFixed(2);
    costoNuevo = parseFloat(costoNuevo).toFixed(2);
    if (!igual) {
        if (costoOriginal === costoNuevo) {
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGuardaMovimientoAccionRequerimiento.jsp",
                dataType: 'html',
                data: {
                    enero: ene,
                    febrero: feb,
                    marzo: mar,
                    abril: abr,
                    mayo: may,
                    junio: jun,
                    julio: jul,
                    agosto: ago,
                    sept: sep,
                    octubre: oct,
                    noviembre: nov,
                    diciembre: dic,
                    ramo: ramo,
                    programa: programa,
                    meta: meta,
                    accion: accion,
                    partida: partida,
                    relLaboral: relLaboral,
                    fuente: fuente,
                    requerimiento: requerimiento,
                    costoUnitario: costoUnitario,
                    identificador: identificador,
                    descripcion: descr,
                    tipoGasto: tipoGasto,
                    articulo: articulo,
                    depto: depto,
                    folio: folio,
                    estatus: estatus
                },
                success: function(res) {
                    $('#mensaje').hide();
                    if (res !== '-1') {
                        actualizaTablaAccionReq();
                        $("#PopUpZone").fadeOut();
                    } else {
                        alert("Ocurri\u00f3 un error al autorizar el movimiento");
                    }
                }
            });
        } else {
            alert("La cantidad anual actualizada debe de ser igual a la anterior");
        }
    } else {
        alert("Debe de modificar al menos un campo para continuar")
    }
}

function getInfoMetaReprogramacionRecalendarizacion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var oficio = $("#inp-folio-usr").val();
    var autorizacion = $("#autorizacion").val();
    if (metaId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarMovimientosPendientesReprogramacion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                oficio: oficio,
                identificador: identificador,
                estatus: estatus
            },
            success: function(response) {
                $('#mensaje').hide();

                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var abrirPopUp = true;

                if (res == "3") {
                    $('#mensaje').hide();
                    alert("Existe un movimiento igual pendiente no se puede agregar uno nuevo. \nFavor de editar el ya existente.");
                    abrirPopUp = false;
                } else {
                    if (res.split("|")[0] === "1") {
                        $('#mensaje').hide();
                        abrirPopUp = false;
                        alert("Existe un movimiento igual pendiente de autorizar no se puede agregar uno nuevo. Folio: " + res.split("|")[1]);
                    }
                }

                if (abrirPopUp == true) {
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxGetInfoReprogramacionRecalendarizacionMeta.jsp',
                        datatype: 'html',
                        data: {
                            ramoId: ramoId,
                            metaId: metaId,
                            identificador: identificador,
                            estatus: estatus,
                            autorizacion: autorizacion
                        },
                        success: function(response) {
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            $("#PopUpZone").html(res);
                            $("#PopUpZone").fadeIn();
                            $('#mensaje').hide();

                        }
                    });
                }
            }
        });


    } else {
        $('#mensaje').hide();
        alert("Debe de seleccionar una meta para continuar");
    }
}

function validarExistenciaMovFolio() {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionId = $("#selAccion").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxValidarExistenMovOficios.jsp",
        dataType: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            accionId: accionId
        },
        success: function(response) {
            $('#mensaje').hide();

            response = trim(response.replace("<!DOCTYPE html>", ""));

            if (response != "0") {
                if (accionId == "-1")
                    alert("Ya existe un oficio de movimiento para esta clave de meta.");
                else
                    alert("Ya existe un oficio de movimiento para esta clave de acci\u00f3n.");
            }

        }
    });
}
function enviarRecalendarizacion() {
    var estatus = $("#estatus").val();
    var tipoMov = $("#tipoMov").val();
    var folio = $("#inp-folio-usr").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    enviarAutorizacion(true);
    if ($("#errorGrabar").val() == 1) {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxEnviarRecalendarizacion.jsp",
            dataType: 'html',
            data: {
                estatus: estatus,
                tipoMov: tipoMov,
                folio: folio
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(response) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $('#mensaje').hide();
                response = trim(response.replace("<!DOCTYPE html>", ""));
                if (response === "-1") {
                    alert("Ocurri\u00f3 un error inesperado");
                } else {
                    if (response.startsWith("Existe")) {
                        alert(response);
                        return false;
                    } else {
                        alert("El oficio fue enviado satisfactoriamente");
                        window.location = "recalendarizacion.jsp";
                    }
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });
    }
}

function getPopUpMotivo(label, funcion) {
    var popUpMotivo = "";
    $('#PopUpZone').html("");

    $("#btn-save-movs").attr("disabled", "disabled");
    $("#btnAceptar").attr("disabled", "disabled");
    $("#btnRechazar").attr("disabled", "disabled");
    $("#btnCancelar").attr("disabled", "disabled");
    $("#btnEnviarAutorizar").attr("disabled", "disabled");
    $("#btnRegresar").attr("disabled", "disabled");
    $("#btnInicio").attr("disabled", "disabled");
    $("#btn-add-meta").attr("disabled", "disabled");
    $("#btn-edit-meta").attr("disabled", "disabled");
    $("#btn-add-accion").attr("disabled", "disabled");
    $("#btn-edit-accion").attr("disabled", "disabled");
    $("#btn-tranferir").attr("disabled", "disabled");

    popUpMotivo = "<div id='popUp-motivo'>"
            + "<label>" + label + ":</label><br/>"
            + "<textarea id='txt-area-motivo' class='no-enter' maxlength='500'></textarea>"
            + "<input id='btnCerrar' type='button' value='Cancelar' onclick='getCancelaPopUp()' />"
            + "<input id='btnOk' type='button' value='Aceptar' onclick='" + funcion + "' />"
            + "<div/>";

    $('#PopUpZone').html(popUpMotivo);
    $('#PopUpZone').css("display", "block");

    $('.no-enter').bind('keypress', function(e) {
        if (e.keyCode == 13)
        {
            return false;
        }
    });
}

function getCancelaMovAceptar(par_opcion) {
    var folio = $("#inp-folio-usr").val();
    var motivo = $("#txt-area-motivo").val();
    var tipoFlujo = $("#tipoFlujo").val();
    var tipoOficio = "";
    var estatus = "";
    var opcion = par_opcion;

    if ($("#tipoOficio") != undefined) {
        tipoOficio = $("#tipoOficio").val();
    }
    if ($("#estatus") != undefined) {
        estatus = $("#estatus").val();
    }

    if (motivo.length > 0) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAutorizacion.jsp",
            dataType: 'html',
            data: {
                folio: folio,
                motivo: motivo,
                tipoOficio: tipoOficio,
                estatus: estatus,
                tipoFlujo: tipoFlujo,
                opcion: opcion
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
                $("#btn-tranferir").attr("disabled", "disabled");
                $("#btnCerrar").attr("disabled", "disabled");
                $("#btnOk").attr("disabled", "disabled");
            },
            success: function(res) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $("#btn-tranferir").removeAttr("disabled");
                $("#btnCerrar").removeAttr("disabled");
                $("#btnOk").removeAttr("disabled");
                getCancelaPopUp();
                if (trim(res) == '1') {
                    alert("El movimiento fue cancelado");
                    regresarAutorizacion();
                } else {
                    res = res.split("|");
                    alert(res[1]);
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $("#btn-tranferir").removeAttr("disabled");
                $("#btnCerrar").removeAttr("disabled");
                $("#btnOk").removeAttr("disabled");
            }
        });
        $('#mensaje').hide();
    } else {
        alert('Capture el motivo de la cancelaci\u00f3n');
    }
}

function getRechazaMovAceptar(par_opcion) {
    var folio = $("#inp-folio-usr").val();
    var motivo = $("#txt-area-motivo").val();
    var tipoFlujo = $("#tipoFlujo").val();
    var tipoOficio = "";
    var estatus = "";
    var opcion = par_opcion;

    if ($("#tipoOficio") != undefined) {
        tipoOficio = $("#tipoOficio").val();
    }
    if ($("#estatus") != undefined) {
        estatus = $("#estatus").val();
    }

    if (motivo.length > 0) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAutorizacion.jsp",
            dataType: 'html',
            data: {
                folio: folio,
                motivo: motivo,
                tipoOficio: tipoOficio,
                estatus: estatus,
                tipoFlujo: tipoFlujo,
                opcion: opcion
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
                $("#btn-tranferir").attr("disabled", "disabled");
                $("#btnCerrar").attr("disabled", "disabled");
                $("#btnOk").attr("disabled", "disabled");
            },
            success: function(res) {
                getCancelaPopUp();
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $("#btn-tranferir").removeAttr("disabled");
                $("#btnCerrar").removeAttr("disabled");
                $("#btnOk").removeAttr("disabled");
                if (trim(res) == '1') {
                    alert("El movimiento fue rechazado")
                    regresarAutorizacion();
                } else {
                    res = res.split("|");
                    alert(res[1]);
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $("#btn-tranferir").removeAttr("disabled");
                $("#btnCerrar").removeAttr("disabled");
                $("#btnOk").removeAttr("disabled");
            }
        });
        $('#mensaje').hide();
    } else {
        alert('Capture el motivo del rechazo')
    }
}

function getCancelaPopUp() {
    $("#btn-save-movs").removeAttr("disabled");
    $("#btnAceptar").removeAttr("disabled");
    $("#btnRechazar").removeAttr("disabled");
    $("#btnCancelar").removeAttr("disabled");
    $("#btnEnviarAutorizar").removeAttr("disabled");
    $("#btnRegresar").removeAttr("disabled");
    $("#btnInicio").removeAttr("disabled");
    $("#btn-add-meta").attr("disabled", "disabled");
    $("#btn-edit-meta").attr("disabled", "disabled");
    $("#btn-add-accion").attr("disabled", "disabled");
    $("#btn-edit-accion").attr("disabled", "disabled");
    $("#btn-tranferir").removeAttr("disabled");
    $('#PopUpZone').html("");
}

function getInfoMetaEstimacionTabla(identificador, estatus, ramoId, metaId) {
    $("#identificador").val(identificador);
    var oficio = $("#inp-folio-usr").val();
    var autorizacion = $("#autorizacion").val();
    if (metaId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoRecalendarizacionMeta.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                identificador: identificador,
                estatus: estatus,
                autorizacion: autorizacion,
                folio: oficio
            },
            success: function(response) {

                var res = trim(response.replace("<!DOCTYPE html>", ""));
                $("#PopUpZone").html(res);
                $("#PopUpZone").fadeIn();
                $('#mensaje').hide();

            }
        });
    } else {
        alert("Debe de seleccionar una meta para continuar");
    }
}

function getInfoAccionEstimacionTabla(identificador, estatus, ramoId, metaId, accionId) {
    var autorizacion = $("#autorizacion").val();
    var oficio = $("#inp-folio-usr").val();
    if (accionId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoRecalendarizacionAccion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                identificador: identificador,
                estatus: estatus,
                autorizacion: autorizacion,
                folio: oficio
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                $("#PopUpZone").html(res);
                $("#PopUpZone").fadeIn();
                $('#mensaje').hide();

            }
        });
    } else {
        alert("Debe de seleccionar una acci\u00f3n para continuar");
    }
}

function contarMovimientosAccionReq() {
    var ramo = $("#selRamo").val();
    var programa = $("#selPrg").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var partida = $("#selPartida").val();
    var fuente = $("#selFuente").val();
    var relLaboral = $("#selRelLaboral").val();
    var requerimiento = $("#selReq").val();
    var folio = $("#inp-folio-usr").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxContarMovtoAccionReq.jsp',
        datatype: 'html',
        async: false,
        data: {
            ramo: ramo,
            programa: programa,
            meta: meta,
            accion: accion,
            partida: partida,
            fuente: fuente,
            relLaboral: relLaboral,
            requerimiento: requerimiento,
            folio: folio
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                if (res === '0') {
                    return false;
                } else if (res === '1') {
                    return true;
                }
            } else {
                alert("Ocurri\u00f3 un error inesperado al procesar la petici\u00f3n");
            }
        }
    });
}

function borrarRecalendarizacion(identificador, tipoRecal) {
    if (confirm("Est\u00e1 seguro que quiere borrar este registro")) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxBorrarRecalendarizacion.jsp',
            datatype: 'html',
            data: {
                identificador: identificador,
                tipoRecal: tipoRecal
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    actualizaTablaMovimientos();
                    actualizaTablaAccionReq();
                } else {
                    alert("Ocurri\u00f3 un error inesperado al procesar la petici\u00f3n");
                }
            }
        });
    }
}

function borrarAmpliacionReduccion(identificador, tipoRecal) {
    if (confirm("Est\u00e1 seguro que quiere borrar este registro")) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxBorrarAmpliacionReduccion.jsp',
            datatype: 'html',
            data: {
                identificador: identificador,
                tipoRecal: tipoRecal
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                res = res.split("|");

                if (res[0] !== '-1') {
                    if (res[0] === '1') {
                        if (tipoRecal === '1' || tipoRecal === '2') {
                            actualizaTablaMovimientosAmplRed();
                        } else if (tipoRecal === '3') {
                            actualizaTablaAmpRedAccionReq();
                        }
                    } else {
                        alert(res[1]);
                    }
                } else {
                    alert("Ocurri\u00f3 un error inesperado al procesar la petici\u00f3n");
                }
            }
        });
    }
}

function getLineaSectorialRPoriginal() {
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyecto = $("#proyectoId").val();
    var est = $("#selLineaPed").val();

    if (est != "-1") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetLineaSectorialOnChange.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyecto,
                estrategia: est
            },
            success: function(responce) {
                $("#selLineaSect").html(responce);
            }
        });
    } else {
        $("#selLineaSect").html("<option value='-1'> -- Seleccione una línea sectorial -- </option>");
    }
}

function getLineaSectorialRPreprogramacion() {
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyecto = $("#proyectoId").val();
    var est = $("#selLineaPedRP").val();

    if (est != "-1") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetLineaSectorialOnChange.jsp",
            dataType: 'html',
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyecto,
                estrategia: est
            },
            success: function(responce) {
                $("#selLineaSectRP").html(responce);
            }
        });
    } else {
        $("#selLineaSectRP").html("<option value='-1'> -- Seleccione una línea sectorial -- </option>");
    }
}

function validarAccionesInhabilitadasByAccion(tipo, ramo, meta) {
    var isHabilitada = false;
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxValidaAccionesInhabilitadas.jsp",
        dataType: 'html',
        async: false,
        data: {
            tipo: tipo,
            ramo: ramo,
            meta: meta
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                if (res === '1')
                    isHabilitada = true;
                else
                    isHabilitada = false;
            } else {
                isHabilitada = false;
                alert("Ocurrió un erro al comprobar las acciones inhabilitadas para esta meta");
            }
        }
    })
    return isHabilitada;
}

function actualizarReprogramacionRecalendarizacionMeta() {

    var calPropuesta = $("#calPropuesta").val();
    calPropuesta = calPropuesta.replaceAll(",", "");
    var calOriginal = $("#calOriginal").val();
    calOriginal = calOriginal.replaceAll(",", "");
    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();

    var unidadMedidaAct = $("#selMedida").val();
    if (unidadMedidaAct == "")
        unidadMedidaAct = "-1";
    var calculoAct = $("#selCalculo").val();
    if (calculoAct == "")
        calculoAct = "-1";
    var compromisoAct = $("#selCompromiso").val();
    if (compromisoAct == "")
        compromisoAct = "-1";
    var lineaAct = $("#selLineaPed").val();
    if (lineaAct == "")
        lineaAct = "-1";
    var lineaSectorialAct = $("#selLineaSect").val();
    if (lineaSectorialAct == "")
        lineaSectorialAct = "-1";
    var ponderacionAct = $("#selPonderacion").val();
    if (ponderacionAct == "")
        ponderacionAct = "-1";
    var transversalidadAct = $("#selTrasver").val();
    if (transversalidadAct == "")
        transversalidadAct = "-1";
    var appEstimacion = $("#appEstimacion").val();
    var txtAreaMetaRP = $("#txtAreaMetaRP").val();
    var cadenaAct = unidadMedidaAct + calculoAct + compromisoAct + lineaAct + lineaSectorialAct + ponderacionAct + transversalidadAct + appEstimacion + txtAreaMetaRP;

    var input = $(".calenVistaRC .estimacion");
    var unidadMedida = $("#selMedidaRP").val();
    var calculo = $("#selCalculoRP").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromisoRP").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacionRP").val();
    var transversalidad = $("#selTrasverRP").val();
    var appEstimacionRP = "";
    var valores = "";
    var valorSnComa;
    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        appEstimacionRP += valorSnComa;
        valores += valorSnComa + ",";
    }
    var metaDescr = $("#txtAreaMeta").val();
    metaDescr = metaDescr.toUpperCase();
    var cadena = unidadMedida + calculo + compromiso + linea + lineaSectorial + ponderacion + transversalidad + appEstimacionRP + metaDescr;
    var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);


    if (sumRecalendarizacion <= 0) {
        if (validarAccionesInhabilitadasByAccion('R', ramoId, metaId)) {
            var respConfirm = confirm("Al dejar la meta en cero puede ser considerada para su inhabilitación. \n \00BF Desea Continuar ?");
            if (respConfirm != true) {
                return false;
            }
        } else {
            alert("No es posible calendarizar a cero en la meta ya que aun cuenta con acciones calendarizadas");
            return false;
        }
    }

    if (cadena == cadenaAct) {
        alert("Se debe modificar al menos un campo de la informaci\u00f3n");
        return false;
    }

    if (metaDescr == "" || metaDescr == null) {
        alert("La descripci\u00f3n de la meta no puede ser vac\u00eda");
        return false;
    }

    if (clasificacion == "-1" || clasificacion == "" || clasificacion == null) {
        alert("La clasificaci\u00f3n funcional es un par\u00e1metro requerido");
        return false;
    }

    /*   if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la reprogramaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }

    if (transversalidad == "-1") {
        alert("El criterio de transversalidad es un par\u00e1metro requerido");
        return false;
    }

    if (compromiso == "-1") {
        alert("El compromiso es un par\u00e1metro requerido");
        return false;
    }

    if (ponderacion == "-1") {
        alert("La ponderaci\u00f3n es un par\u00e1metro requerido");
        return false;
    }



    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarReprogramacionRecalendarizacionMeta.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            valores: valores,
            metaId: metaId,
            metaDescr: metaDescr,
            programaId: programaId,
            tipoProy: tipoProy,
            proyectoId: proyectoId,
            unidadMedida: unidadMedida,
            calculo: calculo,
            clasificacion: clasificacion,
            compromiso: compromiso,
            linea: linea,
            lineaSectorial: lineaSectorial,
            ponderacion: ponderacion,
            transversalidad: transversalidad,
            identificador: identificador,
            sumRecalendarizacion: sumRecalendarizacion,
            sumEstimacion: sumEstimacion,
            calPropuesta: calPropuesta,
            calOriginal: calOriginal
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            actualizaMovimientosReprogramacion();
            $("#PopUpZone").html();
            fadeOutPopUp("PopUpZone");
            $('#mensaje').hide();

        }
    });

}

function guardarReprogramacion(isAutorizado) {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#inpEstatus").val();
    var justificacion = $("#txt-area-justif").val();
    justificacion = justificacion.toUpperCase();
    var folio = $("#inp-folio-usr").val();
    var dateFor = $("#dateFor").val();
    var isActual = $("#isActual").val();
    var selCaratula = $("#selCaratula").val();
    var cont = 0;

    if (justificacion === "") {
        cont++;
        $("#txt-area-justif").addClass("errorClass");
    } else {
        $("#txt-area-justif").removeClass("errorClass");
    }


    if (cont > 0) {
        $('#mensaje').hide();
        $("#errorGrabar").val(0);
        alert("Favor de completar los campos marcados");
        return false;
    } else {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGuardarReprogramacion.jsp',
            datatype: 'html',
            async: false,
            data: {
                estatus: estatus,
                tipoMov: 'R',
                justificacion: justificacion,
                folio: folio,
                dateFor: dateFor,
                isActual: isActual,
                selCaratula: selCaratula
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(response) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    res = res.split("|");
                    if (res[0] === "1") {
                        $("#inp-folio-usr").val(res[2]);
                        $(".btn-enviar").show();
                        $(".btn-cancelar").show();
                    }
                    res = res[1];
                    if (!isAutorizado) {
                        alert(res);
                    } else {
                        $("#errorGrabar").val(1);
                        return true;
                    }
                } else {
                    alert("Ocurri\u00f3 un error no esperado");
                    $("#errorGrabar").val(0);
                    return false;
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });

    }
}

function enviarReprogramacion() {
    var estatus = $("#estatus").val();
    var tipoMov = $("#tipoMov").val();
    var folio = $("#inp-folio-usr").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    guardarReprogramacion(true);
    if ($("#errorGrabar").val() === '1') {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxEnviarReprogramacion.jsp",
            dataType: 'html',
            data: {
                estatus: estatus,
                tipoMov: tipoMov,
                folio: folio
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(response) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $('#mensaje').hide();
                response = trim(response.replace("<!DOCTYPE html>", ""));
                if (response === "-1") {
                    alert("Ocurri\u00f3 un error inesperado");
                } else {
                    if (response.startsWith("Existe")) {
                        alert(response);
                        return false;
                    } else {
                        alert("El oficio fue enviado satisfactoriamente");
                        window.location = "reprogramacion.jsp";
                    }
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });
    }
}

function enviarAmpliacionReduccion() {
    var estatus = $("#estatus").val();
    var fechaContable = $("#fechaContabilidad").val();
    var tipoMov = $("#tipoMov").val();
    var tipoOficio = $("#tipoOficio").val();
    var folio = $("#inp-folio-usr").val();
    var fecha = $("#dateFor").val();
    var isActual = $("#isActual").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    enviarAutorizacionAmpliacionReduccion(true);
    if ($("#errorGrabar").val() === '1') {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxEnviarAutorizacionAmpliacionReduccion.jsp",
            dataType: 'html',
            data: {
                estatus: estatus,
                tipoMov: tipoMov,
                folio: folio,
                fecha: fecha,
                tipoOficio: tipoOficio,
                fechaContable: fechaContable,
                isActual: isActual
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(response) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $('#mensaje').hide();
                var res;
                response = trim(response.replace("<!DOCTYPE html>", ""));
                if (response === "-1") {
                    alert("Ocurri\u00f3 un error inesperado");
                } else {
                    res = response.split("|");
                    if (res[0] === "1") {
                        alert("El oficio fue enviado satisfactoriamente");
                        window.location = "ampliacionReduccion.jsp";
                    } else if (res[0] === "3") {
                        alert(res[1]);
                    } else {
                        alert(res[1]);
                    }
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });
    }
}

function enviarTransferencia() {
    var estatus = $("#estatus").val();
    var fechaContable = $("#fechaContabilidad").val();
    var tipoMov = $("#tipoMov").val();
    var tipoOficio = $("#tipoOficio").val();
    var folio = $("#inp-folio-usr").val();
    var fecha = $("#dateFor").val();
    var isActual = $("#isActual").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    guardarTransferencia(true);
    if ($("#errorGrabar").val() === "1") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxEnviarAutorizacionTransferencia.jsp",
            dataType: 'html',
            data: {
                estatus: estatus,
                tipoMov: tipoMov,
                folio: folio,
                fecha: fecha,
                tipoOficio: tipoOficio,
                fechaContable: fechaContable,
                isActual: isActual
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(response) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $('#mensaje').hide();
                var res;
                response = trim(response.replace("<!DOCTYPE html>", ""));
                if (response === "-1") {
                    alert("Ocurri\u00f3 un error inesperado");
                } else {
                    res = response.split("|");
                    if (res[0] === "1") {
                        alert("El oficio fue enviado satisfactoriamente");
                        window.location = "transferencia.jsp";
                    } else if (res[0] === "3") {
                        alert(res[1]);
                    } else {
                        alert(res[1]);
                    }
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });
    }
}

function borrarReprogramacion(identificador, tipoProg) {
    if (confirm("Est\u00e1 seguro que quiere borrar este registro")) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxBorrarReprogramacion.jsp',
            datatype: 'html',
            data: {
                identificador: identificador,
                tipoProg: tipoProg
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    actualizaMovimientosReprogramacion();
                } else {
                    alert("Ocurri\u00f3 un error inesperado al procesar la petici\u00f3n");
                }
            }
        });
    }
}

function borrarTransferencia(identificador, tipoProg, transferenciaId) {
    if (confirm("Est\u00e1 seguro que quiere borrar este registro")) {
        var reciben = $("#transfRecibe").val();
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxBorrarTransferencia.jsp',
            datatype: 'html',
            data: {
                identificador: identificador,
                tipoProg: tipoProg,
                transferenciaId: transferenciaId
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    res = res.split("|");
                    if (res[0] === '1') {
                        if (tipoProg === '1' || tipoProg === '2' && !reciben) {
                            actualizaMovimientosTransferencia();
                        } else if (tipoProg === '3') {
                            actualizaTablaTransferencia();
                        }
                        if (tipoProg === '1' || tipoProg === '2' && reciben) {
                            if (tipoProg === '1') {
                                getMetasByProyectoUsuarioAmpliacionReduccion();
                            } else {
                                getAccionByMetaUsuarioAmpliacionReduccion();
                            }
                            actualizaMovimientosTransferenciaReciben();
                        } else if (tipoProg === '6') {
                            actualizaTablaTransRecibeAccionReq();
                        }
                    } else {
                        alert(res[1]);
                    }
                } else {
                    alert("Ocurri\u00f3 un error inesperado al procesar la petici\u00f3n");
                }
            }
        });
    }
}

function getInfoMetaReprogramacionRecalendarizacionTabla(identificador, estatus, ramoId, metaId, folio) {
    $("#identificador").val(identificador);
    var autorizacion = $("#autorizacion").val();
    if (metaId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoReprogramacionRecalendarizacionMeta.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                identificador: identificador,
                estatus: estatus,
                autorizacion: autorizacion,
                folio: folio
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                $("#PopUpZone").html(res);
                $("#PopUpZone").fadeIn();

            }
        });
    } else {
        $('#mensaje').hide();
        alert("Debe de seleccionar una meta para continuar");
    }
}
/*transparencia*/
function cargarFuncionesReportesTransparencia() {
    var finalidadIni = $("#selFinalidad1").val();
    var finalidadFin = $("#selFinalidad2").val();
    $("#selFuncion1").html('<option value="-1">-- Selecciona una Funci&oacute;n --</option>');
    $("#selFuncion2").html('<option value="-1">-- Selecciona una Funci&oacute;n --</option>');
    $("#selSubFuncion1").html('<option value="-1">-- Selecciona una SubFunci&oacute;n --</option>');
    $("#selSubFuncion2").html('<option value="-1">-- Selecciona una SubFunci&oacute;n --</option>');
    if (finalidadIni == finalidadFin) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetFuncionesByFinalidad.jsp',
            dataType: 'html',
            data: {
                'finalidad1': finalidadIni,
                'finalidad2': finalidadFin
            },
            async: false,
            beforeSend: function() {
                $("#divEspera").css("display", "block");
            },
            success: function(responce) {
                $("#selFuncion1").html(responce);
                $("#selFuncion2").html(responce);
            }
        });
    }
}

/*transparencia*/
function cargarSubfuncionesReportesTransparencia() {
    var finalidadIni = $("#selFinalidad1").val();
    var finalidadFin = $("#selFinalidad2").val();
    var funcionIni = $("#selFuncion1").val();
    var funcionFin = $("#selFuncion2").val();
    $("#selSubFuncion1").html('<option value="-1">-- Selecciona una SubFunci&oacute;n --</option>');
    $("#selSubFuncion2").html('<option value="-1">-- Selecciona una SubFunci&oacute;n --</option>');
    if (funcionIni == funcionFin) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetSubfuncionesByFinalidadFuncion.jsp',
            dataType: 'html',
            data: {
                'finalidad1': finalidadIni,
                'finalidad2': finalidadFin,
                'funcion1': funcionIni,
                'funcion2': funcionFin
            },
            async: false,
            beforeSend: function() {
                $("#divEspera").css("display", "block");
            },
            success: function(responce) {
                $("#selSubFuncion1").html(responce);
                $("#selSubFuncion2").html(responce);

            }
        });
    }
}

function generaReporteTransparencia() {
    $("#reporttype").val("pdf");
    var mensajeError = "";
    var reporte = 4;
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
        return false;
    }

    $("#frmRptExcel").submit();

}

function generaReporteTransparenciaExcel() {
    $("#reporttype").val("xls");
    var mensajeError = "";
    var reporte = 4;
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
        return false;
    }

    $("#frmRptExcel").submit();
}

function getInfoAccionReprogramacionRecalendarizacion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionId = $("#selAccion").val();
    var oficio = $("#inp-folio-usr").val();
    var autorizacion = $("#autorizacion").val();
    if (accionId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarMovimientosPendientesReprogramacion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                oficio: oficio
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var abrirPopUp = true;
                $('#mensaje').hide();
                if (res == "4") {
                    $('#mensaje').hide();
                    alert("Existe un movimiento igual pendiente no se puede agregar uno nuevo. \nFavor de editar el ya existente.");
                    abrirPopUp = false;
                } else {
                    if (res.split("|")[0] == "2") {
                        $('#mensaje').hide();
                        abrirPopUp = false;
                        alert("Existe un movimiento igual pendiente de autorizar no se puede agregar uno nuevo. Folio: " + res.split("|")[1]);
                    }
                }

                if (abrirPopUp == true) {
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxGetInfoReprogramacionRecalendarizacionAccion.jsp',
                        datatype: 'html',
                        data: {
                            ramoId: ramoId,
                            metaId: metaId,
                            accionId: accionId,
                            identificador: identificador,
                            estatus: estatus,
                            autorizacion: autorizacion
                        },
                        success: function(response) {
                            $('#mensaje').hide();
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            $("#PopUpZone").html(res);
                            $("#PopUpZone").fadeIn();

                        }
                    });
                }
            }
        });



    } else {
        $('#mensaje').hide();
        alert("Debe de seleccionar una acci\u00f3n para continuar");
    }
}

function actualizarReprogramacionRecalendarizacionAccion() {

    var calPropuesta = $("#calPropuesta").val();
    calPropuesta = calPropuesta.replaceAll(",", "");
    var calOriginal = $("#calOriginal").val();
    calOriginal = calOriginal.replaceAll(",", "");
    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();

    var input = $(".calenVistaRC .estimacion");

    var unidadMedidaAct = $("#selMedida").val();
    if (unidadMedidaAct == "")
        unidadMedidaAct = "-1";
    var calculoAct = $("#selCalculo").val();
    if (calculoAct == "")
        calculoAct = "-1";
    var lineaAct = $("#selLineaPed").val();
    if (lineaAct == "")
        lineaAct = "-1";
    var lineaSectorialAct = $("#selLineaSect").val();
    if (lineaSectorialAct == "")
        lineaSectorialAct = "-1";
    var accMujRP = $("#accMuj").val();
    var accHomRP = $("#accHom").val();
    var appEstimacion = $("#appEstimacion").val();
    var txtAreaAccionRP = $("#txtAreaAccionRP").val();

    var cadenaAct = unidadMedidaAct + calculoAct + lineaAct + lineaSectorialAct + accMujRP + accHomRP + appEstimacion + txtAreaAccionRP;

    var unidadMedida = $("#selMedidaRP").val();
    var calculo = $("#selCalculoRP").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var accMuj = $("#accMujRP").val();
    var accHom = $("#accHomRP").val();
    var appEstimacionRP = "";
    var valores = "";
    var valorSnComa;
    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        appEstimacionRP += valorSnComa;
        valores += valorSnComa + ",";
    }
    var accionDescr = $("#txtAreaAccion").val();
    accionDescr = accionDescr.toUpperCase();
    var cadena = unidadMedida + calculo + linea + lineaSectorial + accMuj + accHom + appEstimacionRP + accionDescr;

    var selGrupo = $("#selGrupo").val();
    var selUnidadEj = $("#selUnidadEj").val();
    var selMunicipio = $("#selMunicipio").val();
    var selLocalidad = $("#selLocalidad").val();

    var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);


    if (sumRecalendarizacion <= 0) {
        var respConfirm = confirm("Al dejar la acci\u00f3n en cero puede ser considerada para su inhabilitaci\u00f3n. \n \00BF Desea Continuar?");
        if (respConfirm != true) {
            return false;
        }
    }

    if (cadena == cadenaAct) {
        alert("Se debe modificar al menos un campo de la informaci\u00f3n");
        return false;
    }

    if (accionDescr == "" || accionDescr == null) {
        alert("La descripci\u00f3n de la acci\u00f3n no puede ser vac\u00eda");
        return false;
    }

    /*   if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la reprogramaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }

    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        valores += valorSnComa + ",";
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarReprogramacionRecalendarizacionAccion.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            valores: valores,
            metaId: metaId,
            accionId: accionId,
            accionDescr: accionDescr,
            programaId: programaId,
            tipoProy: tipoProy,
            proyectoId: proyectoId,
            unidadMedida: unidadMedida,
            calculo: calculo,
            selGrupo: selGrupo,
            selUnidadEj: selUnidadEj,
            selMunicipio: selMunicipio,
            selLocalidad: selLocalidad,
            accMuj: accMuj,
            accHom: accHom,
            linea: linea,
            lineaSectorial: lineaSectorial,
            identificador: identificador,
            sumRecalendarizacion: sumRecalendarizacion,
            sumEstimacion: sumEstimacion,
            calPropuesta: calPropuesta,
            calOriginal: calOriginal
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            actualizaMovimientosReprogramacion();
            $("#PopUpZone").html();
            fadeOutPopUp("PopUpZone");
            $('#mensaje').hide();

        }
    });

}

function getInfoAccionReprogramacionRecalendarizacionTabla(identificador, estatus, ramoId, metaId, accionId, folio) {
    $("#identificador").val(identificador);
    var autorizacion = $("#autorizacion").val();
    if (accionId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoReprogramacionRecalendarizacionAccion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                identificador: identificador,
                estatus: estatus,
                autorizacion: autorizacion,
                folio: folio
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                $("#PopUpZone").html(res);
                $("#PopUpZone").fadeIn();
                $('#mensaje').hide();

            }
        });
    } else {
        alert("Debe de seleccionar una acci\u00f3n para continuar");
    }
}
function getAutorizaMovReprogramacion() {
    var folio = $("#inp-folio-usr").val();
    var tipoFlujo = $("#tipoFlujo").val();
    var inpEstatus = $("#inpEstatus").val();
    var comentario = $("#comentario").val();
    var opcion = 4;

    if (folio.length > 0 && confirm("El movimiento ser\u00e1 autorizado")) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAutorizacion.jsp",
            dataType: 'html',
            data: {
                folio: folio,
                tipoFlujo: tipoFlujo,
                opcion: opcion,
                estatus: inpEstatus,
                comentario: comentario
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(res) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                if (trim(res) == '1') {
                    alert("El movimiento fue autorizado");
                    regresarAutorizacion();
                } else {
                    res = res.split("|");
                    alert(res[1]);
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });
        $('#mensaje').hide();
    }
}

function getInfoAccionAmpliacionReduccion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionId = $("#selAccion").val();
    var oficio = $("#inp-folio-usr").val();
    var tipoValidacion = "A";
    var autorizacion = $("#autorizacion").val();
    if (estatus !== "V") {
        if (metaId !== '-1') {
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxGetInfoAmpliacionReduccionAccion.jsp',
                datatype: 'html',
                data: {
                    ramoId: ramoId,
                    metaId: metaId,
                    identificador: identificador,
                    estatus: estatus,
                    autorizacion: autorizacion

                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res !== "-1") {
                        if (res === "-2") {
                            alert("No es posible crear acciones nuevas debido a que la meta seleccionada fue deshabilitada");
                        } else {
                            $("#PopUpZone").html(res);
                            fadeInPopUp("PopUpZone");
                            fadeOutPopUp("estimacionMeta");
                            fadeInPopUp("infoAccion");
                        }
                    } else {
                        alert("Ocurri/u00F3 un error inesperado al procesar la solicitud");
                    }

                }
            });
        } else {
            alert("Debe de seleccionar una meta para continuar");
        }
    } else {
        alert("No se puede crear una acci\u00f3n si el movimiento se encuentra en revisi\u00f3n");
    }

}

function getBotonesAmpliacionReduccion() {
    var importe = $("#inp-txt-impor-ampred").val().replaceAll(",", "");
    importe = parseFloat(importe);
    if (importe < 0) {
        $("#btn-reduccion").css("display", "block");
        $("#tbl-req-ampred").css("display", "none");
    } else {
        $("#btn-reduccion").css("display", "none");
        $("#tbl-req-ampred").css("display", "block");
    }
}

function fadeInPopUp(popUpName) {
    $("#" + popUpName).show();
}

function validarFlotanteNew(valor) {
    var total = 0;
    var input;
    var tipoC = $("#selCalculo").val();

    input = $(".calenVistaC .estimacion");
    if (valor == "")
        valor = 0;
    if (tipoC == "AC") {
        for (it = 0; it < input.length; it++) {
            if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                total += parseFloat(input[it].value.replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (total < parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
            }
        } else if (tipoC == "MI") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (parseFloat(input[it].value.replaceAll(",", "")) != 0) {
                        if (total > parseFloat(input[it].value.replaceAll(",", ""))) {
                            total = parseFloat(input[it].value.replaceAll(",", ""));
                        } else {
                            if (total == 0)
                                total = parseFloat(input[it].value.replaceAll(",", ""));
                        }
                    }
                }
            }
        }
    }
    if (total !== 0)
        $("#inTxtTotalEst").val(addCommas(total.toFixed(2)));
}

function getInfoAccionAmpliacionReduccionTabla(identificador, estatus, ramoId, metaId, accionId) {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoAmpliacionReduccionAccion.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            accionId: accionId,
            identificador: identificador,
            estatus: estatus,
            autorizacion: autorizacion

        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));

            $("#PopUpZone").html(res);
            fadeInPopUp("PopUpZone");
            fadeOutPopUp("estimacionMeta");
            fadeInPopUp("infoAccion");
            $('#mensaje').hide();

        }
    });
}

function SaveAmpliacion() {
    var fecha = $("#dateFor").val();
    $("#nvoRequerimiento").attr("disabled", true);
    var disponible = parseFloat($("#inp-txt-disp-ampred").val().replaceAll(",", ""));
    var importe = parseFloat($("#inp-txt-impor-ampred").val().replaceAll(",", ""));
    if (importe > 0) {
        var cont = 0;
        var ramo = $("#popRamo").val();
        var programa = $("#popPrg").val();
        var proy = $("#popProy").val();
        var tipoProy = $("#popTipoProy").val();
        var meta = $("#popMeta").val();
        var articulo = $("#ArtPartida").val();
        var accion = $("#popAccion").val();
        var partida = $("#popPartida").val();
        var fuente = $("#popFuente").val();
        var relLaboral = $("#popRelLaboral").val();
        var descripcion = $("#txtAreaPart").val();
        var justifReq = $("#justifReq").val();
        var justificacion = $("#txtAreaJust").val();
        justificacion = justificacion.toUpperCase();
        var tipoGasto = $("#vwTipoGasto").val().split("-")[0];
        if (typeof articulo !== 'undefined') {
            if (articulo === '-1') {
                $("#ArtPartida").addClass("errorClass");
                cont++;
            } else {
                $("#ArtPartida").removeClass("errorClass");
            }
        }
        if (typeof descripcion !== 'undefined') {
            descripcion = descripcion.toUpperCase();
            if (descripcion === "") {
                $("#txtAreaPart").addClass("errorClass");
                cont++;
            } else {
                $("#txtAreaPart").removeClass("errorClass");
            }
        }

        if (justificacion == "" && justifReq == "1") {
            $("#txtAreaJust").addClass("errorClass");
            cont++;
        } else {
            $("#txtAreaJust").removeClass("errorClass");
        }

        var ene = parseFloat($("#recalinpTxtEne").val().replaceAll(",", ""));
        var feb = parseFloat($("#recalinpTxtFeb").val().replaceAll(",", ""));
        var mar = parseFloat($("#recalinpTxtMar").val().replaceAll(",", ""));
        var abr = parseFloat($("#recalinpTxtAbr").val().replaceAll(",", ""));
        var may = parseFloat($("#recalinpTxtMay").val().replaceAll(",", ""));
        var jun = parseFloat($("#recalinpTxtJun").val().replaceAll(",", ""));
        var jul = parseFloat($("#recalinpTxtJul").val().replaceAll(",", ""));
        var ago = parseFloat($("#recalinpTxtAgo").val().replaceAll(",", ""));
        var sep = parseFloat($("#recalinpTxtSep").val().replaceAll(",", ""));
        var oct = parseFloat($("#recalinpTxtOct").val().replaceAll(",", ""));
        var nov = parseFloat($("#recalinpTxtNov").val().replaceAll(",", ""));
        var dic = parseFloat($("#recalinpTxtDic").val().replaceAll(",", ""));
        if ($("#recalinpTxtEne").val() === "") {
            $("#recalinpTxtEne").addClass("errorClass");
            ene = 0;
            cont++;
        } else {
            $("#recalinpTxtEne").removeClass("errorClass");
        }
        if ($("#recalinpTxtFeb").val() === "") {
            $("#recalinpTxtFeb").addClass("errorClass");
            feb = 0;
            cont++;
        } else {
            $("#recalinpTxtFeb").removeClass("errorClass");
        }
        if ($("#recalinpTxtMar").val() === "") {
            $("#recalinpTxtMar").addClass("errorClass");
            mar = 0;
            cont++;
        } else {
            $("#recalinpTxtMar").removeClass("errorClass");
        }
        if ($("#recalinpTxtAbr").val() === "") {
            $("#recalinpTxtAbr").addClass("errorClass");
            abr = 0;
            cont++;
        } else {
            $("#recalinpTxtAbr").removeClass("errorClass");
        }
        if ($("#recalinpTxtMay").val() === "") {
            $("#recalinpTxtMay").addClass("errorClass");
            may = 0;
            cont++;
        } else {
            $("#recalinpTxtMay").removeClass("errorClass");
        }
        if ($("#recalinpTxtJun").val() === "") {
            $("#recalinpTxtJun").addClass("errorClass");
            jun = 0;
            cont++;
        } else {
            $("#recalinpTxtJun").removeClass("errorClass");
        }
        if ($("#recalinpTxtJul").val() === "") {
            $("#recalinpTxtJul").addClass("errorClass");
            jul = 0;
            cont++;
        } else {
            $("#recalinpTxtJul").removeClass("errorClass");
        }
        if ($("#recalinpTxtAgo").val() === "") {
            $("#recalinpTxtAgo").addClass("errorClass");
            ago = 0;
            cont++;
        } else {
            $("#recalinpTxtAgo").removeClass("errorClass");
        }
        if ($("#recalinpTxtSep").val() === "") {
            $("#recalinpTxtSep").addClass("errorClass");
            sep = 0;
            cont++;
        } else {
            $("#recalinpTxtSep").removeClass("errorClass");
        }
        if ($("#recalinpTxtOct").val() === "") {
            $("#recalinpTxtOct").addClass("errorClass");
            oct = 0;
            cont++;
        } else {
            $("#recalinpTxtOct").removeClass("errorClass");
        }
        if ($("#recalinpTxtNov").val() === "") {
            $("#recalinpTxtNov").addClass("errorClass");
            nov = 0;
            cont++;
        } else {
            $("#recalinpTxtNov").removeClass("errorClass");
        }
        if ($("#recalinpTxtDic").val() === "") {
            $("#recalinpTxtDic").addClass("errorClass");
            dic = 0;
            cont++;
        } else {
            $("#recalinpTxtDic").removeClass("errorClass");
        }
        if ($("#inpTxtCantUnit").val() <= 0) {
            $("#inpTxtCantUnit").addClass("errorClass");
            cont++;
        } else {
            $("#inpTxtCantUnit").removeClass("errorClass");
        }
        var costoUnitario = parseFloat($("#inpTxtCantUnit").val().replaceAll(",", "")).toFixed(2);
        var cantAnual = ene + feb + mar + abr + may + jun + jul + ago + sep + oct + nov + dic;
        var costoTotal = parseFloat((cantAnual * costoUnitario).toFixed(2));
        if (cont == 0) {
            if (importe == costoTotal) {
                $.ajax({
                    type: 'POST',
                    url: 'librerias/ajax/ajaxInsertarAmpliacion.jsp',
                    datatype: 'html',
                    data: {
                        ramo: ramo,
                        programa: programa,
                        proy: proy,
                        tipoProy: tipoProy,
                        meta: meta,
                        accion: accion,
                        partida: partida,
                        fuente: fuente,
                        relLaboral: relLaboral,
                        importe: importe,
                        disponible: disponible,
                        justificacion: justificacion,
                        reqDescr: descripcion,
                        tipoGasto: tipoGasto,
                        ene: ene,
                        feb: feb,
                        mar: mar,
                        abr: abr,
                        may: may,
                        jun: jun,
                        jul: jul,
                        ago: ago,
                        sep: sep,
                        oct: oct,
                        nov: nov,
                        dic: dic,
                        costoUnitario: costoUnitario,
                        cantAnual: cantAnual,
                        costoTotal: costoTotal,
                        fecha: fecha,
                        articulo: articulo
                    },
                    success: function(response) {
                        $('#mensaje').hide();
                        var res = trim(response.replace("<!DOCTYPE html>", ""));
                        if (res !== '-1') {
                            actualizaTablaAmpRedAccionReq();
                            fadeOutPopUp("PopUpZone");
                        } else {
                            $("#nvoRequerimiento").removeAttr("disabled");
                            alert("Sucedi\u00f3 un error inesperado al realizar la operaci\u00f3n");
                        }
                    }
                });
            } else {
                $("#nvoRequerimiento").removeAttr("disabled");
                alert("El costo anual debe de ser igual al importe capturado");
            }
        } else {
            $("#nvoRequerimiento").removeAttr("disabled");
            alert("Favor de completar los campos marcados");
        }
    } else {
        $("#nvoRequerimiento").removeAttr("disabled");
        alert("Debe de capturar un importe para continuar");
    }
}

function saveTransferencia() {
    //var fecha = $("#dateFor").val();
    var fecha = $("#fecha").val();
    $("#nvoRequerimiento").attr("disabled", true);
    var importe = parseFloat($("#inp-txt-impor-ampred").val().replaceAll(",", ""));
    if (importe > 0) {
        var cont = 0;
        var ramo = $("#popRamo").val();
        var programa = $("#popPrg").val();
        var proy = $("#popProy").val();
        var tipoProy = $("#popTipoProy").val();
        var meta = $("#popMeta").val();
        var accion = $("#popAccion").val();
        var partida = $("#popPartida").val();
        var fuente = $("#popFuente").val();
        var relLaboral = $("#popRelLaboral").val();
        var descripcion = $("#txtAreaPart").val();
        var justifReq = $("#justifReq").val();
        var justificacion = $("#txtAreaJust").val();
        justificacion = justificacion.toUpperCase();
        var tipoGasto = $("#vwTipoGasto").val().split("-")[0];
        var transferenciaId = $("#transferenciaId").val();
        var articulo = $("#ArtPartida").val();
        if (typeof descripcion != 'undefined') {
            descripcion = descripcion.toUpperCase();
            if (descripcion === "") {
                $("#txtAreaPart").addClass("errorClass");
                cont++;
            } else {
                $("#txtAreaPart").removeClass("errorClass");
            }
        }
        if (typeof articulo != 'undefined') {
            if (articulo === '-1') {
                $("#ArtPartida").addClass("errorClass");
                cont++;
            } else {
                $("#ArtPartida").removeClass("errorClass");
                descripcion = $("#ArtPartida option:selected").text();
            }
        }
        if (justificacion == "" && justifReq == "1") {
            $("#txtAreaJust").addClass("errorClass");
            cont++;
        } else {
            $("#txtAreaJust").removeClass("errorClass");
        }
        var ene = parseFloat($("#recalinpTxtEne").val().replaceAll(",", ""));
        var feb = parseFloat($("#recalinpTxtFeb").val().replaceAll(",", ""));
        var mar = parseFloat($("#recalinpTxtMar").val().replaceAll(",", ""));
        var abr = parseFloat($("#recalinpTxtAbr").val().replaceAll(",", ""));
        var may = parseFloat($("#recalinpTxtMay").val().replaceAll(",", ""));
        var jun = parseFloat($("#recalinpTxtJun").val().replaceAll(",", ""));
        var jul = parseFloat($("#recalinpTxtJul").val().replaceAll(",", ""));
        var ago = parseFloat($("#recalinpTxtAgo").val().replaceAll(",", ""));
        var sep = parseFloat($("#recalinpTxtSep").val().replaceAll(",", ""));
        var oct = parseFloat($("#recalinpTxtOct").val().replaceAll(",", ""));
        var nov = parseFloat($("#recalinpTxtNov").val().replaceAll(",", ""));
        var dic = parseFloat($("#recalinpTxtDic").val().replaceAll(",", ""));
        if ($("#recalinpTxtEne").val() === "") {
            $("#recalinpTxtEne").addClass("errorClass");
            ene = 0;
            cont++;
        } else {
            $("#recalinpTxtEne").removeClass("errorClass");
        }
        if ($("#recalinpTxtFeb").val() === "") {
            $("#recalinpTxtFeb").addClass("errorClass");
            feb = 0;
            cont++;
        } else {
            $("#recalinpTxtFeb").removeClass("errorClass");
        }
        if ($("#recalinpTxtMar").val() === "") {
            $("#recalinpTxtMar").addClass("errorClass");
            mar = 0;
            cont++;
        } else {
            $("#recalinpTxtMar").removeClass("errorClass");
        }
        if ($("#recalinpTxtAbr").val() === "") {
            $("#recalinpTxtAbr").addClass("errorClass");
            abr = 0;
            cont++;
        } else {
            $("#recalinpTxtAbr").removeClass("errorClass");
        }
        if ($("#recalinpTxtMay").val() === "") {
            $("#recalinpTxtMay").addClass("errorClass");
            may = 0;
            cont++;
        } else {
            $("#recalinpTxtMay").removeClass("errorClass");
        }
        if ($("#recalinpTxtJun").val() === "") {
            $("#recalinpTxtJun").addClass("errorClass");
            jun = 0;
            cont++;
        } else {
            $("#recalinpTxtJun").removeClass("errorClass");
        }
        if ($("#recalinpTxtJul").val() === "") {
            $("#recalinpTxtJul").addClass("errorClass");
            jul = 0;
            cont++;
        } else {
            $("#recalinpTxtJul").removeClass("errorClass");
        }
        if ($("#recalinpTxtAgo").val() === "") {
            $("#recalinpTxtAgo").addClass("errorClass");
            ago = 0;
            cont++;
        } else {
            $("#recalinpTxtAgo").removeClass("errorClass");
        }
        if ($("#recalinpTxtSep").val() === "") {
            $("#recalinpTxtSep").addClass("errorClass");
            sep = 0;
            cont++;
        } else {
            $("#recalinpTxtSep").removeClass("errorClass");
        }
        if ($("#recalinpTxtOct").val() === "") {
            $("#recalinpTxtOct").addClass("errorClass");
            oct = 0;
            cont++;
        } else {
            $("#recalinpTxtOct").removeClass("errorClass");
        }
        if ($("#recalinpTxtNov").val() === "") {
            $("#recalinpTxtNov").addClass("errorClass");
            nov = 0;
            cont++;
        } else {
            $("#recalinpTxtNov").removeClass("errorClass");
        }
        if ($("#recalinpTxtDic").val() === "") {
            $("#recalinpTxtDic").addClass("errorClass");
            dic = 0;
            cont++;
        } else {
            $("#recalinpTxtDic").removeClass("errorClass");
        }
        if ($("#inpTxtCantUnit").val() <= 0) {
            $("#inpTxtCantUnit").addClass("errorClass");
            cont++;
        } else {
            $("#inpTxtCantUnit").removeClass("errorClass");
        }
        var costoUnitario = parseFloat($("#inpTxtCantUnit").val().replaceAll(",", "")).toFixed(2);
        var cantAnual = ene + feb + mar + abr + may + jun + jul + ago + sep + oct + nov + dic;
        var costoTotal = parseFloat((cantAnual * costoUnitario).toFixed(2));
        if (cont == 0) {
            if (importe >= costoTotal) {
                $.ajax({
                    type: 'POST',
                    url: 'librerias/ajax/ajaxInsertarTransferencia.jsp',
                    datatype: 'html',
                    data: {
                        ramo: ramo,
                        programa: programa,
                        proy: proy,
                        tipoProy: tipoProy,
                        meta: meta,
                        accion: accion,
                        partida: partida,
                        fuente: fuente,
                        relLaboral: relLaboral,
                        importe: importe,
                        justificacion: justificacion,
                        reqDescr: descripcion,
                        tipoGasto: tipoGasto,
                        ene: ene,
                        feb: feb,
                        mar: mar,
                        abr: abr,
                        may: may,
                        jun: jun,
                        jul: jul,
                        ago: ago,
                        sep: sep,
                        oct: oct,
                        nov: nov,
                        dic: dic,
                        costoUnitario: costoUnitario,
                        cantAnual: cantAnual,
                        costoTotal: costoTotal,
                        fecha: fecha,
                        transferenciaId: transferenciaId,
                        articulo: articulo
                    },
                    success: function(response) {
                        $('#mensaje').hide();
                        var res = trim(response.replace("<!DOCTYPE html>", ""));
                        var msg = res.split("|");
                        if (msg[0] === '2') {
                            $("#nvoRequerimiento").removeAttr("disabled");
                            alert(msg[1]);
                        } else {
                            if (res !== '-1') {
                                actualizaTablaTransRecibeAccionReq();
                                fadeOutPopUp("PopUpZone");
                            } else {
                                $("#nvoRequerimiento").removeAttr("disabled");
                                alert("Sucedi\u00f3 un error inesperado al realizar la operaci\u00f3n");
                            }
                        }
                    }
                });
            } else {
                $("#nvoRequerimiento").removeAttr("disabled");
                alert("El costo anual debe de ser menor o igual al importe disponible");
            }
        } else {
            $("#nvoRequerimiento").removeAttr("disabled");
            alert("Favor de completar los campos marcados");
        }
    } else {
        $("#nvoRequerimiento").removeAttr("disabled");
        alert("Debe de capturar un importe para continuar");
    }
}

function editarAmpliacion(identificador) {
    var fecha = $("#dateFor").val();
    var disponible = parseFloat($("#popDisponible").val().replaceAll(",", ""));
    var importe = parseFloat($("#popImporte").val().replaceAll(",", ""));
    /*if (importe > 0) {*/
    var cont = 0;
    var ramo = $("#popRamo").val();
    var programa = $("#popPrg").val();
    var proy = $("#popProy").val();
    var tipoProy = $("#popTipoProy").val();
    var meta = $("#popMeta").val();
    var accion = $("#popAccion").val();
    var partida = $("#popPartida").val();
    var articulo = $("#ArtPartida").val();
    var fuente = $("#popFuente").val();
    var relLaboral = $("#popRelLaboral").val();
    var descripcion = $("#txtAreaPart").val();
    var justifReq = $("#justifReq").val();
    var justificacion = $("#txtAreaJust").val();
    justificacion = justificacion.toUpperCase();
    var tipoGasto = $("#vwTipoGasto").val().split("-")[0];
    if (typeof articulo != 'undefined') {
        if (articulo === '-1') {
            $("#ArtPartida").addClass("errorClass");
            cont++;
        } else {
            $("#ArtPartida").removeClass("errorClass");
        }
    }
    if (typeof descripcion != 'undefined') {
        descripcion = descripcion.toUpperCase();
        if (descripcion === "") {
            $("#txtAreaPart").addClass("errorClass");
            cont++;
        } else {
            $("#txtAreaPart").removeClass("errorClass");
        }
    }
    if (justificacion == "" && justifReq == "1") {
        $("#txtAreaJust").addClass("errorClass");
        cont++;
    } else {
        $("#txtAreaJust").removeClass("errorClass");
    }
    var ene = parseFloat($("#recalinpTxtEne").val().replaceAll(",", ""));
    var feb = parseFloat($("#recalinpTxtFeb").val().replaceAll(",", ""));
    var mar = parseFloat($("#recalinpTxtMar").val().replaceAll(",", ""));
    var abr = parseFloat($("#recalinpTxtAbr").val().replaceAll(",", ""));
    var may = parseFloat($("#recalinpTxtMay").val().replaceAll(",", ""));
    var jun = parseFloat($("#recalinpTxtJun").val().replaceAll(",", ""));
    var jul = parseFloat($("#recalinpTxtJul").val().replaceAll(",", ""));
    var ago = parseFloat($("#recalinpTxtAgo").val().replaceAll(",", ""));
    var sep = parseFloat($("#recalinpTxtSep").val().replaceAll(",", ""));
    var oct = parseFloat($("#recalinpTxtOct").val().replaceAll(",", ""));
    var nov = parseFloat($("#recalinpTxtNov").val().replaceAll(",", ""));
    var dic = parseFloat($("#recalinpTxtDic").val().replaceAll(",", ""));
    if ($("#recalinpTxtEne").val() === "") {
        $("#recalinpTxtEne").addClass("errorClass");
        ene = 0;
        cont++;
    } else {
        $("#recalinpTxtEne").removeClass("errorClass");
    }
    if ($("#recalinpTxtFeb").val() === "") {
        $("#recalinpTxtFeb").addClass("errorClass");
        feb = 0;
        cont++;
    } else {
        $("#recalinpTxtFeb").removeClass("errorClass");
    }
    if ($("#recalinpTxtMar").val() === "") {
        $("#recalinpTxtMar").addClass("errorClass");
        mar = 0;
        cont++;
    } else {
        $("#recalinpTxtMar").removeClass("errorClass");
    }
    if ($("#recalinpTxtAbr").val() === "") {
        $("#recalinpTxtAbr").addClass("errorClass");
        abr = 0;
        cont++;
    } else {
        $("#recalinpTxtAbr").removeClass("errorClass");
    }
    if ($("#recalinpTxtMay").val() === "") {
        $("#recalinpTxtMay").addClass("errorClass");
        may = 0;
        cont++;
    } else {
        $("#recalinpTxtMay").removeClass("errorClass");
    }
    if ($("#recalinpTxtJun").val() === "") {
        $("#recalinpTxtJun").addClass("errorClass");
        jun = 0;
        cont++;
    } else {
        $("#recalinpTxtJun").removeClass("errorClass");
    }
    if ($("#recalinpTxtJul").val() === "") {
        $("#recalinpTxtJul").addClass("errorClass");
        jul = 0;
        cont++;
    } else {
        $("#recalinpTxtJul").removeClass("errorClass");
    }
    if ($("#recalinpTxtAgo").val() === "") {
        $("#recalinpTxtAgo").addClass("errorClass");
        ago = 0;
        cont++;
    } else {
        $("#recalinpTxtAgo").removeClass("errorClass");
    }
    if ($("#recalinpTxtSep").val() === "") {
        $("#recalinpTxtSep").addClass("errorClass");
        sep = 0;
        cont++;
    } else {
        $("#recalinpTxtSep").removeClass("errorClass");
    }
    if ($("#recalinpTxtOct").val() === "") {
        $("#recalinpTxtOct").addClass("errorClass");
        oct = 0;
        cont++;
    } else {
        $("#recalinpTxtOct").removeClass("errorClass");
    }
    if ($("#recalinpTxtNov").val() === "") {
        $("#recalinpTxtNov").addClass("errorClass");
        nov = 0;
        cont++;
    } else {
        $("#recalinpTxtNov").removeClass("errorClass");
    }
    if ($("#recalinpTxtDic").val() === "") {
        $("#recalinpTxtDic").addClass("errorClass");
        dic = 0;
        cont++;
    } else {
        $("#recalinpTxtDic").removeClass("errorClass");
    }
    if ($("#inpTxtCantUnit").val() <= 0) {
        $("#inpTxtCantUnit").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtCantUnit").removeClass("errorClass");
    }
    var costoUnitario = parseFloat($("#inpTxtCantUnit").val().replaceAll(",", "")).toFixed(2);
    var cantAnual = ene + feb + mar + abr + may + jun + jul + ago + sep + oct + nov + dic;
    var costoTotal = parseFloat((cantAnual * costoUnitario).toFixed(2));
    if (cont == 0) {
        if (importe == costoTotal) {
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxInsertarAmpliacion.jsp',
                datatype: 'html',
                data: {
                    ramo: ramo,
                    programa: programa,
                    proy: proy,
                    tipoProy: tipoProy,
                    meta: meta,
                    accion: accion,
                    partida: partida,
                    fuente: fuente,
                    relLaboral: relLaboral,
                    importe: importe,
                    disponible: disponible,
                    justificacion: justificacion,
                    reqDescr: descripcion,
                    tipoGasto: tipoGasto,
                    ene: ene,
                    feb: feb,
                    mar: mar,
                    abr: abr,
                    may: may,
                    jun: jun,
                    jul: jul,
                    ago: ago,
                    sep: sep,
                    oct: oct,
                    nov: nov,
                    dic: dic,
                    costoUnitario: costoUnitario,
                    cantAnual: cantAnual,
                    costoTotal: costoTotal,
                    edicion: 1,
                    identificador: identificador,
                    fecha: fecha,
                    articulo: articulo
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res !== '-1') {
                        actualizaTablaAmpRedAccionReq();
                        fadeOutPopUp("PopUpZone");
                    } else {
                        alert("Sucedi\u00f3 un error inesperado al realizar la operaci\u00f3n");
                    }
                }
            });
        } else {
            alert("El costo anual no puede ser mayor al importe capturado");
        }
    } else {
        alert("Favor de completar los campos marcados");
    }
    /*} else {
     alert("Debe de capturar un importe para continuar");
     }*/
}
function editarTransferencia(identificador) {
    var fecha = $("#dateFor").val();
    var disponible = parseFloat($("#popDisponible").val().replaceAll(",", ""));
    var importe = parseFloat($("#importeOriginal").val().replaceAll(",", ""));
    /*if (importe > 0) {*/
    var cont = 0;
    var ramo = $("#popRamo").val();
    var programa = $("#popPrg").val();
    var proy = $("#popProy").val();
    var tipoProy = $("#popTipoProy").val();
    var meta = $("#popMeta").val();
    var accion = $("#popAccion").val();
    var partida = $("#popPartida").val();
    var fuente = $("#popFuente").val();
    var relLaboral = $("#popRelLaboral").val();
    var descripcion = $("#txtAreaPart").val();
    var justifReq = $("#justifReq").val();
    var justificacion = $("#txtAreaJust").val();
    justificacion = justificacion.toUpperCase();
    var tipoGasto = $("#vwTipoGasto").val().split("-")[0];
    var transferenciaId = $("#transferenciaId").val();
    var articulo = $("#ArtPartida").val();
    if (typeof articulo != 'undefined') {
        if (articulo === '-1') {
            $("#ArtPartida").addClass("errorClass");
            cont++;
        } else {
            $("#ArtPartida").removeClass("errorClass");
            descripcion = $("#ArtPartida option:selected").text();
        }
    }
    if (typeof descripcion != 'undefined') {
        descripcion = descripcion.toUpperCase();
        if (descripcion === "") {
            $("#txtAreaPart").addClass("errorClass");
            cont++;
        } else {
            $("#txtAreaPart").removeClass("errorClass");
        }
    }
    if (justificacion == "" && justifReq == "1") {
        $("#txtAreaJust").addClass("errorClass");
        cont++;
    } else {
        $("#txtAreaJust").removeClass("errorClass");
    }
    var ene = parseFloat($("#recalinpTxtEne").val().replaceAll(",", ""));
    var feb = parseFloat($("#recalinpTxtFeb").val().replaceAll(",", ""));
    var mar = parseFloat($("#recalinpTxtMar").val().replaceAll(",", ""));
    var abr = parseFloat($("#recalinpTxtAbr").val().replaceAll(",", ""));
    var may = parseFloat($("#recalinpTxtMay").val().replaceAll(",", ""));
    var jun = parseFloat($("#recalinpTxtJun").val().replaceAll(",", ""));
    var jul = parseFloat($("#recalinpTxtJul").val().replaceAll(",", ""));
    var ago = parseFloat($("#recalinpTxtAgo").val().replaceAll(",", ""));
    var sep = parseFloat($("#recalinpTxtSep").val().replaceAll(",", ""));
    var oct = parseFloat($("#recalinpTxtOct").val().replaceAll(",", ""));
    var nov = parseFloat($("#recalinpTxtNov").val().replaceAll(",", ""));
    var dic = parseFloat($("#recalinpTxtDic").val().replaceAll(",", ""));
    if ($("#recalinpTxtEne").val() === "") {
        $("#recalinpTxtEne").addClass("errorClass");
        ene = 0;
        cont++;
    } else {
        $("#recalinpTxtEne").removeClass("errorClass");
    }
    if ($("#recalinpTxtFeb").val() === "") {
        $("#recalinpTxtFeb").addClass("errorClass");
        feb = 0;
        cont++;
    } else {
        $("#recalinpTxtFeb").removeClass("errorClass");
    }
    if ($("#recalinpTxtMar").val() === "") {
        $("#recalinpTxtMar").addClass("errorClass");
        mar = 0;
        cont++;
    } else {
        $("#recalinpTxtMar").removeClass("errorClass");
    }
    if ($("#recalinpTxtAbr").val() === "") {
        $("#recalinpTxtAbr").addClass("errorClass");
        abr = 0;
        cont++;
    } else {
        $("#recalinpTxtAbr").removeClass("errorClass");
    }
    if ($("#recalinpTxtMay").val() === "") {
        $("#recalinpTxtMay").addClass("errorClass");
        may = 0;
        cont++;
    } else {
        $("#recalinpTxtMay").removeClass("errorClass");
    }
    if ($("#recalinpTxtJun").val() === "") {
        $("#recalinpTxtJun").addClass("errorClass");
        jun = 0;
        cont++;
    } else {
        $("#recalinpTxtJun").removeClass("errorClass");
    }
    if ($("#recalinpTxtJul").val() === "") {
        $("#recalinpTxtJul").addClass("errorClass");
        jul = 0;
        cont++;
    } else {
        $("#recalinpTxtJul").removeClass("errorClass");
    }
    if ($("#recalinpTxtAgo").val() === "") {
        $("#recalinpTxtAgo").addClass("errorClass");
        ago = 0;
        cont++;
    } else {
        $("#recalinpTxtAgo").removeClass("errorClass");
    }
    if ($("#recalinpTxtSep").val() === "") {
        $("#recalinpTxtSep").addClass("errorClass");
        sep = 0;
        cont++;
    } else {
        $("#recalinpTxtSep").removeClass("errorClass");
    }
    if ($("#recalinpTxtOct").val() === "") {
        $("#recalinpTxtOct").addClass("errorClass");
        oct = 0;
        cont++;
    } else {
        $("#recalinpTxtOct").removeClass("errorClass");
    }
    if ($("#recalinpTxtNov").val() === "") {
        $("#recalinpTxtNov").addClass("errorClass");
        nov = 0;
        cont++;
    } else {
        $("#recalinpTxtNov").removeClass("errorClass");
    }
    if ($("#recalinpTxtDic").val() === "") {
        $("#recalinpTxtDic").addClass("errorClass");
        dic = 0;
        cont++;
    } else {
        $("#recalinpTxtDic").removeClass("errorClass");
    }
    if ($("#inpTxtCantUnit").val() <= 0) {
        $("#inpTxtCantUnit").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtCantUnit").removeClass("errorClass");
    }
    var costoUnitario = parseFloat($("#inpTxtCantUnit").val().replaceAll(",", "")).toFixed(2);
    var cantAnual = ene + feb + mar + abr + may + jun + jul + ago + sep + oct + nov + dic;
    var costoTotal = parseFloat((cantAnual * costoUnitario).toFixed(2));
    if (cont === 0) {
        if (importe >= costoTotal) {
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxInsertarTransferencia.jsp',
                datatype: 'html',
                data: {
                    ramo: ramo,
                    programa: programa,
                    proy: proy,
                    tipoProy: tipoProy,
                    meta: meta,
                    accion: accion,
                    partida: partida,
                    fuente: fuente,
                    relLaboral: relLaboral,
                    importe: importe,
                    disponible: disponible,
                    justificacion: justificacion,
                    reqDescr: descripcion,
                    tipoGasto: tipoGasto,
                    ene: ene,
                    feb: feb,
                    mar: mar,
                    abr: abr,
                    may: may,
                    jun: jun,
                    jul: jul,
                    ago: ago,
                    sep: sep,
                    oct: oct,
                    nov: nov,
                    dic: dic,
                    costoUnitario: costoUnitario,
                    cantAnual: cantAnual,
                    costoTotal: costoTotal,
                    edicion: 1,
                    identificador: identificador,
                    fecha: fecha,
                    transferenciaId: transferenciaId,
                    articulo: articulo
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res !== '-1') {
                        actualizaTablaTransRecibeAccionReq();
                        fadeOutPopUp("PopUpZone");
                    } else {
                        alert("Sucedi\u00f3 un error inesperado al realizar la operaci\u00f3n");
                    }
                }
            });
        } else {
            alert("El costo anual no puede ser mayor al importe capturado");
        }
    } else {
        alert("Favor de completar los campos marcados");
    }
}
/*else {
 alert("Debe de capturar un importe para continuar");
 }*/
//}

function validaCodigoReduccion() {
    var ramo = $("#selRamo").val();
    var programa = $("#selPrg").val();
    var proy = $("#selProy").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var partida = $("#selPartida").val();
    var fuente = $("#selFuente").val();
    var relLaboral = $("#selRelLaboral").val();
    if (fuente !== "-1") {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidaCodigoReduccion.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: programa,
                proyecto: proy,
                meta: meta,
                accion: accion,
                partida: partida,
                fuente: fuente,
                relLaboral: relLaboral
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                res = res.split("|");
                if (res !== '-1') {
                    if (res[0] !== '2')
                        saveReduccion();
                    else {
                        alert(res[1]);
                    }
                } else {
                    alert("Sucedió un error inesperado al realizar la operación");
                }
            }
        });
    }
}

function saveReduccion() {
    var fecha = $("#dateFor").val();
    var disponible = parseFloat($("#inp-txt-disp-ampred").val().replaceAll(",", ""));
    var importe = parseFloat($("#inp-txt-impor-ampred").val().replaceAll(",", ""));
    var importeAbs = Math.abs(importe);
    if (importeAbs > 0) {
        var ramo = $("#selRamo").val();
        var programa = $("#selPrg").val();
        var proy = $("#selProy").val();
        var meta = $("#selMeta").val();
        var accion = $("#selAccion").val();
        var partida = $("#selPartida").val();
        var fuente = $("#selFuente").val();
        var relLaboral = $("#selRelLaboral").val();
        if (fuente !== "-1") {
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxInsertarReduccion.jsp',
                datatype: 'html',
                data: {
                    ramo: ramo,
                    programa: programa,
                    proyecto: proy,
                    meta: meta,
                    accion: accion,
                    partida: partida,
                    fuente: fuente,
                    importe: importe,
                    disponible: disponible,
                    relLaboral: relLaboral,
                    fecha: fecha,
                    importeAbs: importeAbs
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    res = res.split("|");
                    if (res !== '-1') {
                        if (res[0] !== '2')
                            actualizaTablaAmpRedAccionReq();
                        else {
                            alert(res[1]);
                        }
                    } else {
                        alert("Sucedi\u00f3 un error inesperado al realizar la operaci\u00f3n");
                    }
                }
            });
        } else {
            alert("Debe de completar la selecci\u00f3n de los campos para continuar");
        }
    } else {
        alert("Debe de capturar un importe para continuar");
    }
}


function actualizaTablaAmpRedAccionReq() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    var autorizar = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaAmpRedAccionReq.jsp',
        datatype: 'html',
        data: {
            estatus: estatus,
            autorizar: autorizar
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#tbl-ampred-reqs tbody").html(response);
            }
        }
    });
}

function actualizaTablaTransRecibeAccionReq() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    var transferenciaId = $("#transferenciaId").val();
    var autorizar = $("#utorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaTransferenciaAccionReqRecibe.jsp',
        datatype: 'html',
        data: {
            estatus: estatus,
            transferenciaId: transferenciaId,
            autorizar: autorizar
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#tbl-ampred-reqs tbody").html(response);
            }
        }
    });
}

function actualizaTablaTransferencia() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var estatus = $("#estatus").val();
    var transferenciaId = $("#transferenciaId").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaTransferencia.jsp',
        datatype: 'html',
        data: {
            estatus: estatus,
            transferenciaId: transferenciaId
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#tbl-ampred-reqs tbody").html(response);
            }
        }
    });
}

function editReduccion(identificador) {
    var fecha = $("#dateFor").val();
    var ramo = $("#popRamo").val();
    var programa = $("#popPrg").val();
    var meta = $("#popMeta").val();
    var accion = $("#popAccion").val();
    var disponible = parseFloat($("#inpTxtDisponible").val().replaceAll(",", ""));
    var importe = parseFloat($("#inpTxtImporte").val().replaceAll(",", ""));
    var importeAbs = Math.abs(importe);

    if (importe > 0) {
        alert("El importe debe ser negativo");
        return false;
    }

    if (importeAbs > 0) {
        if (importeAbs <= disponible) {
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxInsertarReduccion.jsp',
                datatype: 'html',
                data: {
                    ramo: ramo,
                    programa: programa,
                    meta: meta,
                    accion: accion,
                    importe: importe,
                    disponible: disponible,
                    identificador: identificador,
                    edicion: 1,
                    fecha: fecha
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res !== '-1') {
                        actualizaTablaAmpRedAccionReq();
                        $("#PopUpZone").fadeOut();
                    } else {
                        alert("Sucedi\u00f3 un error inesperado al realizar la operaci\u00f3n");
                    }
                }
            });
        } else {
            alert("El importe no puede sobrepasar el total del disponible");
            return;
        }
    } else {
        alert("Debe de capturar un importe para continuar");
    }
}

function editTransferencia(identificador, isParaestatal) {
    var fecha = $("#dateFor").val();
    var ramo = $("#popRamo").val();
    var programa = $("#popPrg").val();
    var meta = $("#popMeta").val();
    var accion = $("#popAccion").val();
    if (!isParaestatal)
        var disponible = parseFloat($("#inpTxtDisponible").val().replaceAll(",", ""));
    else
        var disponible = 9999999999999.00;
    var importe = parseFloat($("#inpTxtImporte").val().replaceAll(",", ""));

    if (importe < 0) {
        alert("El importe debe ser mayor a cero");
        return false;
    }

    if (importe > 0) {
        if ((importe <= disponible) || isParaestatal) {
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxEditarImporteTransferencia.jsp',
                datatype: 'html',
                data: {
                    importe: importe,
                    disponible: disponible,
                    identificador: identificador,
                    fecha: fecha
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res !== '-1') {
                        if (res === "1") {
                            alert("El nuevo importe no puede ser menor a las transferencias realizadas");
                        } else {
                            actualizaTablaTransferencia();
                            $("#PopUpZone").fadeOut();
                        }
                    } else {
                        alert("Sucedi\u00f3 un error inesperado al realizar la operaci\u00f3n");
                    }
                }
            });
        } else {
            alert("El importe no puede sobrepasar el total del disponible");
            return;
        }
    } else {
        alert("Debe de capturar un importe para continuar");
    }
}

function actualizarAmpliacionReduccionAccion() {

    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();
    var input = $(".calenVistaC .estimacion");
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var selGrupo = $("#selGrupo").val();
    var selUnidadEj = $("#selUnidadEj").val();
    var selMunicipio = $("#selMunicipio").val();
    var selLocalidad = $("#selLocalidad").val();
    var accMuj = $("#accMuj").val();
    var accHom = $("#accHom").val();
    var acumulado = 0;
    var valores = "";
    var valorSnComa;
    var accionDescr = $("#txtAreaAccion").val();
    accionDescr = accionDescr.toUpperCase();
    if (accionDescr === "" || accionDescr === null) {
        alert("La descripci\u00f3n de la acci\u00f3n no puede ser vac\u00eda");
        return false;
    }

    /*   if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la reprogramaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }

    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        if (valorSnComa == "")
            valorSnComa = "0.0";
        valores += valorSnComa + ",";
        acumulado += parseFloat(valorSnComa);
    }
    if (acumulado > 0) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizarAmpliacionReduccionAccion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                valores: valores,
                metaId: metaId,
                accionId: accionId,
                accionDescr: accionDescr,
                programaId: programaId,
                tipoProy: tipoProy,
                proyectoId: proyectoId,
                unidadMedida: unidadMedida,
                calculo: calculo,
                selGrupo: selGrupo,
                selUnidadEj: selUnidadEj,
                selMunicipio: selMunicipio,
                selLocalidad: selLocalidad,
                accMuj: accMuj,
                accHom: accHom,
                linea: linea,
                lineaSectorial: lineaSectorial,
                identificador: identificador,
                sumRecalendarizacion: acumulado
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                getAccionByMetaUsuario(ramoId, programaId, proyectoId + "," + tipoProy, metaId);
                actualizaTablaMovimientosAmplRed();
                $("#PopUpZone").html();
                fadeOutPopUp("estimacionMeta");
                $('#mensaje').hide();

            }
        });
    } else {
        alert("La estimaci\u00f3n de la acci\u00f3n no puede ser cero");
    }

}

function validarGuardarInfoAmpliacionReduccionAccion() {

    var accionDescr = $("#txtAreaAccion").val();
    accionDescr = accionDescr.toUpperCase();
    var selUnidadEj = $("#selUnidadEj").val();
    var unidadMedida = $("#selMedida").val();
    var selGrupo = $("#selGrupo").val();
    var selMunicipio = $("#selMunicipio").val();
    var selLocalidad = $("#selLocalidad").val();
    var calculo = $("#selCalculo").val();
    var linea = $("#selLineaPedRP").val();

    if (accionDescr == "" || accionDescr == null) {
        alert("La descripci\u00f3n de la acci\u00f3n no puede ser vac\u00eda");
        return false;
    }
    /*if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la reprogramaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/
    if (selUnidadEj == "-1") {
        alert("La unidad ejecutora es un par\u00e1metro requerido");
        return false;
    }
    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }
    /*if(selGrupo == "-1"){
     alert("El grupo poblacional es un parámetro requerido");
     return false;
     }*/
    if (selMunicipio == "-1") {
        alert("El municipio es un par\u00e1metro requerido");
        return false;
    }
    if (selLocalidad == "-1") {
        alert("El localidad es un par\u00e1metro requerido");
        return false;
    }
    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }
    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }

    fadeOutPopUp('infoAccion');
    fadeInPopUp('estimacionMeta');


}

function getInfoAccionAmpliacionReduccionRecalendarizacion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionId = $("#selAccion").val();
    var oficio = $("#inp-folio-usr").val();
    var tipoValidacion = "A";
    var autorizacion = $("#autorizacion").val();

    if (accionId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarMovimientosPendientesAmpliacionReduccion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                oficio: oficio,
                tipoValidacion: tipoValidacion,
                autorizacion: autorizacion
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var abrirPopUp = true;

                if (res == "3") {
                    $('#mensaje').hide();
                    alert("Existe un movimiento igual pendiente no se puede agregar uno nuevo. \nFavor de editar el ya existente.");
                    abrirPopUp = false;
                } else {
                    if (res.split("|")[0] == "4") {
                        $('#mensaje').hide();
                        abrirPopUp = false;
                        alert("Existe un movimiento igual pendiente de autorizar no se puede agregar uno nuevo. Folio: " + res.split("|")[1]);
                    }
                }

                if (abrirPopUp == true) {
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxGetInfoAmpliacionReduccionRecalendarizacionAccion.jsp',
                        datatype: 'html',
                        data: {
                            ramoId: ramoId,
                            metaId: metaId,
                            accionId: accionId,
                            identificador: identificador,
                            estatus: estatus,
                            folio: oficio,
                            autorizacion: autorizacion
                        },
                        success: function(response) {
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            $("#PopUpZone").html(res);
                            $("#PopUpZone").fadeIn();
                            $('#mensaje').hide();

                        }
                    });
                }
            }
        });

    } else {
        alert("Debe de seleccionar una acción para continuar");
    }
}

function getInfoAccionAmpliacionReduccionRecalendarizacionTabla(identificador, estatus, ramoId, metaId, accionId) {
    $("#identificador").val(identificador);
    var oficio = $("#inp-folio-usr").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoAmpliacionReduccionRecalendarizacionAccion.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            accionId: accionId,
            identificador: identificador,
            estatus: estatus,
            autorizacion: autorizacion,
            folio: oficio
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $("#PopUpZone").html(res);
            fadeInPopUp("PopUpZone");
            $("#PopUpZone").fadeIn();
            $('#mensaje').hide();

        }
    });
}

function actualizarAmpliacionReduccionRecalendarizacionAccion() {
    var calPropuesta = $("#calPropuesta").val();
    calPropuesta = calPropuesta.replaceAll(",", "");
    var calOriginal = $("#calOriginal").val();
    calOriginal = calOriginal.replaceAll(",", "");
    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();
    var input = $(".calenVistaRC .estimacion");
    var unidadMedidaAct = $("#selMedida").val();
    if (unidadMedidaAct == "")
        unidadMedidaAct = "-1";
    var calculoAct = $("#selCalculo").val();
    if (calculoAct == "")
        calculoAct = "-1";
    var lineaAct = $("#selLineaPed").val();
    if (lineaAct == "")
        lineaAct = "-1";
    var lineaSectorialAct = $("#selLineaSect").val();
    if (lineaSectorialAct == "")
        lineaSectorialAct = "-1";
    var accMujRP = $("#accMuj").val();
    var accHomRP = $("#accHom").val();
    var appEstimacion = $("#appEstimacion").val();
    var txtAreaAccionRP = $("#txtAreaAccionRP").val();
    var cadenaAct = unidadMedidaAct + calculoAct + lineaAct + lineaSectorialAct + accMujRP + accHomRP + appEstimacion + txtAreaAccionRP;
    var unidadMedida = $("#selMedidaRP").val();
    var calculo = $("#selCalculoRP").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var accMuj = $("#accMujRP").val();
    var accHom = $("#accHomRP").val();
    var valores = "";
    var valorSnComa;
    var appEstimacionRP = "";
    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        appEstimacionRP += valorSnComa;
        valores += valorSnComa + ",";
    }
    var accionDescr = $("#txtAreaAccion").val();
    accionDescr = accionDescr.toUpperCase();
    var cadena = unidadMedida + calculo + linea + lineaSectorial + accMuj + accHom + appEstimacionRP + accionDescr;
    var selGrupo = $("#selGrupo").val();
    var selUnidadEj = $("#selUnidadEj").val();
    var selMunicipio = $("#selMunicipio").val();
    var selLocalidad = $("#selLocalidad").val();
    var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);

    if (sumRecalendarizacion <= 0) {
        var respConfirm = confirm("Esta acci\u00f3n fue recalendarizada en cero. \n \u00BFDesea Continuar?");
        if (respConfirm != true) {
            return false;
        }
    }

    if (cadena == cadenaAct) {
        alert("Se debe modificar al menos un campo de la informaci\u00f3n");
        return false;
    }

    if (accionDescr == "" || accionDescr == null) {
        alert("La descripci\u00f3n de la acci\u00f3n no puede ser vac\u00eda");
        return false;
    }
    /*if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la recalendarizaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }


    //if (sumRecalendarizacion > 0) {
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarAmpliacionReduccionRecalendarizacionAccion.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            valores: valores,
            metaId: metaId,
            accionId: accionId,
            sumRecalendarizacion: sumRecalendarizacion,
            sumEstimacion: sumEstimacion,
            accionDescr: accionDescr,
            programaId: programaId,
            tipoProy: tipoProy,
            proyectoId: proyectoId,
            unidadMedida: unidadMedida,
            calculo: calculo,
            selGrupo: selGrupo,
            selUnidadEj: selUnidadEj,
            selMunicipio: selMunicipio,
            selLocalidad: selLocalidad,
            accMuj: accMuj,
            accHom: accHom,
            linea: linea,
            lineaSectorial: lineaSectorial,
            identificador: identificador,
            calPropuesta: calPropuesta,
            calOriginal: calOriginal
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            actualizaTablaMovimientosAmplRed();
            $("#PopUpZone").html();
            fadeOutPopUp("PopUpZone");
            $('#mensaje').hide();

        }
    });
    /*}else {
     alert("La propuesta de modificaci\u00f3n no puede ser cero");
     }*/
}

function getInfoMetaAmpliacionReduccion(identificador, estatus) {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrg").val();
    var proyecto = $("#selProy").val();
    var arrayTipoProyecto = proyecto.split(",");
    var proyectoId = arrayTipoProyecto[0];
    var tipoProyecto = arrayTipoProyecto[1];
    var autorizacion = $("#autorizacion").val();
    if (estatus !== 'V') {
        if (proyecto !== '-1') {
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxGetInfoAmpliacionReduccionMeta.jsp',
                datatype: 'html',
                data: {
                    ramoId: ramoId,
                    proyectoId: proyectoId,
                    tipoProyecto: tipoProyecto,
                    programaId: programaId,
                    identificador: identificador,
                    estatus: estatus,
                    autorizacion: autorizacion

                },
                success: function(response) {
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    fadeInPopUp("PopUpZone");
                    $("#PopUpZone").html(res);
                    fadeInPopUp("infoMetaAmpRed");
                    fadeOutPopUp("estimacionMetaAR");
                    $('#mensaje').hide();
                }
            });
        } else {
            alert("Debe de seleccionar un proyecto/Actividad para continuar");
        }
    } else {
        alert("No se pueden crear nuevas metas si el movimiento está en revisi\u00f3n");
    }
}

function enviarAutorizacionAmpliacionReduccion(isAutorizado) {
    var fechaContable = $("#fechaContabilidad").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#inpAutorizacion").val();
    var estatus = $("#inpEstatus").val();
    var justificacion = $("#txt-area-justif").val();
    justificacion = justificacion.toUpperCase();
    var comentarioPlan = $("#txt-area-coment-plan").val();
    if (typeof comentarioPlan != 'undefined')
        comentarioPlan = comentarioPlan.toUpperCase();
    var checkImpacto;
    var folio = $("#inp-folio-usr").val();
    var dateFor = $("#dateFor").val();
    var isActual = $("#isActual").val();
    var selCaratula = $("#selCaratula").val();
    var tipoOficio = $("#tipoOficio").val();
    var cont = 0;
    if ($("#chk-imp-prog").is(":checked")) {
        checkImpacto = true;
    } else {
        checkImpacto = false;
    }
    if (justificacion === "") {
        cont++;
        $("#txt-area-justif").addClass("errorClass");
    } else {
        $("#txt-area-justif").removeClass("errorClass");
    }
    /*
     if(typeof comentarioPlan != 'undefined'){
     if (comentarioPlan === "") {
     cont++;
     $("#txt-area-coment-plan").addClass("errorClass");
     } else {
     $("#txt-area-coment-plan").removeClass("errorClass");
     }
     }
     */
    if (cont > 0) {
        alert("Favor de completar los campos marcados");
        $('#mensaje').hide();
        $("#errorGrabar").val(0);
        return false;
    } else {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxSaveMovtoAmpRed.jsp',
            datatype: 'html',
            async: false,
            data: {
                autorizacion: autorizacion,
                estatus: estatus,
                tipoMov: 'A',
                justificacion: justificacion,
                checkImpacto: checkImpacto,
                folio: folio,
                dateFor: dateFor,
                isActual: isActual,
                fechaContable: fechaContable,
                selCaratula: selCaratula,
                tipoOficio: tipoOficio,
                comentarioPlan: comentarioPlan
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(response) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    res = res.split("|");
                    if (res[0] === "1") {
                        $("#inp-folio-usr").val(res[2]);
                        $(".btn-enviar").show();
                        $(".btn-cancelar").show();
                    }
                    res = res[1];
                    if (!isAutorizado) {
                        if (res.indexOf("folio") > -1) {
                            $("#estatus").val("X");
                            $("#inp-folio-rel-usr").val(folio);
                            $("#tr-folio-rel").show();
                        }
                        alert(res);
                    } else {
                        $("#errorGrabar").val(1);
                        return true;
                    }
                } else {
                    alert("Ocurri\u00f3 un error no esperado");
                    $("#errorGrabar").val(0);
                    if (!isAutorizado)
                        return false;
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });
    }
}

function getAutorizaMovAmpliacionReduccion() {
    var folio = $("#inp-folio-usr").val();
    var tipoOficio = $("#tipoOficio").val();
    var estatus = $("#estatus").val();
    var tipoFlujo = $("#tipoFlujo").val();
    var comentario = $("#comentario").val();
    var bFechaContabilidad = $("#fechaContabilidad").val();
    enviarAutorizacionAmpliacionReduccion(true);
    if (folio.length > 0 && confirm("El movimiento ser\u00e1 autorizado")) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAutorizacion.jsp",
            dataType: 'html',
            data: {
                folio: folio,
                tipoOficio: tipoOficio,
                estatus: estatus,
                tipoFlujo: tipoFlujo,
                bFechaContabilidad: bFechaContabilidad,
                opcion: 6,
                comentario: comentario,
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(res) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                if (trim(res) == '1') {
                    alert("El movimiento fue autorizado");
                    regresarAutorizacion();
                } else {
                    res = res.split("|");
                    alert(res[1]);
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });

        $('#mensaje').hide();
    }
}

function mostrarEdicionMeta() {
    var selMeta = $("#selMeta").val();
    if ($("#chk-imp-prog").is(":checked") && selMeta === '-1') {
        $("#btn-add-meta").css("display", "block");
        $("#btn-edit-meta").css("display", "none");
        $("#btn-add-accion").css("display", "block");
        $("#btn-edit-accion").css("display", "none");
    } else if ($("#chk-imp-prog").is(":checked") && selMeta !== '-1') {
        $("#btn-add-meta").css("display", "none");
        $("#btn-edit-meta").css("display", "block");
        $("#btn-add-accion").css("display", "block");
        $("#btn-edit-accion").css("display", "none");
    }
}
function mostrarEdicionMetaReprogramacion() {
    var selMeta = $("#selMeta").val();
    if (selMeta === '-1') {
        $("#btn-add-meta").css("display", "block");
        $("#btn-edit-meta").css("display", "none");
        $("#btn-add-accion").css("display", "block");
        $("#btn-edit-accion").css("display", "none");
    } else if (selMeta !== '-1') {
        $("#btn-add-meta").css("display", "none");
        $("#btn-edit-meta").css("display", "block");
        $("#btn-add-accion").css("display", "block");
        $("#btn-edit-accion").css("display", "none");
    }
}

function mostrarEdicionAccion() {
    var selAccion = $("#selAccion").val();
    if ($("#chk-imp-prog").is(":checked") && selAccion === '-1') {
        $("#btn-add-accion").css("display", "block");
        $("#btn-edit-accion").css("display", "none");
    } else if ($("#chk-imp-prog").is(":checked") && selAccion !== '-1') {
        $("#btn-add-accion").css("display", "none");
        $("#btn-edit-accion").css("display", "block");
    }
}
function mostrarEdicionAccionReprogramacion() {
    var selAccion = $("#selAccion").val();
    if (selAccion === '-1') {
        $("#btn-add-accion").css("display", "block");
        $("#btn-edit-accion").css("display", "none");
    } else if (selAccion !== '-1') {
        $("#btn-add-accion").css("display", "none");
        $("#btn-edit-accion").css("display", "block");
    }
}

function getInfoMetaAmpliacionReduccionRecalendarizacion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var oficio = $("#inp-folio-usr").val();
    var tipoValidacion = "M";
    var autorizacion = $("#autorizacion").val();

    if (metaId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarMovimientosPendientesAmpliacionReduccion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                oficio: oficio,
                tipoValidacion: tipoValidacion,
                autorizacion: autorizacion
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var abrirPopUp = true;

                if (res == "1") {
                    $('#mensaje').hide();
                    alert("Existe un movimiento igual pendiente no se puede agregar uno nuevo. \nFavor de editar el ya existente.");
                    abrirPopUp = false;
                } else {
                    if (res.split("|")[0] === "2") {
                        $('#mensaje').hide();
                        abrirPopUp = false;
                        alert("Existe un movimiento igual pendiente de autorizar no se puede agregar uno nuevo. Folio: " + res.split("|")[1]);
                    }
                }

                if (abrirPopUp == true) {
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxGetInfoAmpliacionReduccionRecalendarizacionMeta.jsp',
                        datatype: 'html',
                        data: {
                            ramoId: ramoId,
                            metaId: metaId,
                            identificador: identificador,
                            estatus: estatus,
                            folio: oficio,
                            autorizacion: autorizacion
                        },
                        success: function(response) {
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            $("#PopUpZone").html(res);
                            $("#PopUpZone").fadeIn();
                            $('#mensaje').hide();

                        }
                    });
                }
            }
        });

    } else {
        alert("Debe de seleccionar una acci\u00f3n para continuar");
    }
}

function getInfoMetaAmpliacionReduccionRecalendarizacionTabla(identificador, estatus, ramoId, metaId) {
    $("#identificador").val(identificador);
    var oficio = $("#inp-folio-usr").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoAmpliacionReduccionRecalendarizacionMeta.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            identificador: identificador,
            estatus: estatus,
            autorizacion: autorizacion,
            folio: oficio
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $("#PopUpZone").html(res);
            $("#PopUpZone").fadeIn();
            $('#mensaje').hide();

        }
    });

}

function validarFlotanteNewRecalendarizacionAccion(valor, tipoC) {
    var total = 0;
    var input;
    input = $(".calenVistaRC .estimacion");
    if (valor == "")
        valor = 0;
    if (tipoC == "AC") {
        for (it = 0; it < input.length; it++) {
            if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                total += parseFloat(input[it].value.replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (total < parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
            }
        } else if (tipoC == "MI") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (parseFloat(input[it].value.replaceAll(",", "")) != 0) {
                        if (total > parseFloat(input[it].value.replaceAll(",", ""))) {
                            total = parseFloat(input[it].value.replaceAll(",", ""));
                        } else {
                            if (total == 0)
                                total = parseFloat(input[it].value.replaceAll(",", ""));
                        }
                    }
                }
            }
        }
    }
    if (total !== 0)
        $("#inTxtTotalEst").val(addCommas(total.toFixed(2)));
}


function actualizarAmpliacionReduccionMeta() {
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var input = $(".calenVistaCAR .estimacion");
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromiso").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacion").val();
    var transversalidad = $("#selTrasver").val();
    var obra = $("#selObraP").val();
    var identificador = $("#identificador").val();
    var inTxtTotalEst = $("#inTxtTotalEst").val();
    var cont;
    var acumulado = 0;
    var num_meses = $("#num_meses").val();
    var valores = "";
    var variable;
    var valor = 0;
    for (i = 0; i < num_meses; i++) {
        variable = "";
        variable = "estimacion" + i;
        if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
            valor = "0";
        } else {
            valor = $("#" + variable).val().replaceAll(",", "");
        }
        valores += valor + ",";
        acumulado += parseFloat(valor.replaceAll(",", ""));
    }

    var MetaDescr = $("#txtAreaMeta").val();
    MetaDescr = MetaDescr.toUpperCase();
    if (MetaDescr == "" || MetaDescr == null) {
        alert("La descripci\u00f3n de la meta no puede ser vac\u00eda");
    }

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
    }

    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
    }


    if (acumulado > 0) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizarAmpliacionReduccionMeta.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                metaDescr: MetaDescr,
                programaId: programaId,
                tipoProy: tipoProy,
                proyectoId: proyectoId,
                unidadMedida: unidadMedida,
                calculo: calculo,
                linea: linea,
                lineaSectorial: lineaSectorial,
                identificador: identificador,
                clasificacion: clasificacion,
                compromiso: compromiso,
                ponderacion: ponderacion,
                transversalidad: transversalidad,
                valores: valores,
                obra: obra,
                sumRecalendarizacion: acumulado.toFixed(2)
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                getMetasByProyectoUsuarioAmpliacionReduccion();
                actualizaTablaMovimientosAmplRed();
                $("#PopUpZone").html();
                fadeOutPopUp("estimacionMetaAR");
                $('#mensaje').hide();
            }
        });
    } else {
        alert("La estimaci\u00f3n de la meta no puede ser cero");
    }
}


function getInfoMetaAmpliacionReduccionTabla(identificador, estatus, ramoId, metaId) {
    // if(metaId !== '-1'){ 
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoAmpliacionReduccionMeta.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            identificador: identificador,
            estatus: estatus,
            autorizacion: autorizacion
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));

            $("#PopUpZone").html(res);
            $("#PopUpZone").fadeIn();
            // fadeInPopUp("PopUpZone");
            fadeInPopUp("infoMetaAmpRed");
            fadeOutPopUp("estimacionMetaAR");
            $('#mensaje').hide();


        }
    });


    /* }else{
     alert("Debe de seleccionar una meta para continuar");
     }*/

}

function validaDatosMetaAR() {

    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromiso").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacion").val();
    var transversalidad = $("#selTrasver").val();

    var MetaDescr = $("#txtAreaMeta").val();
    MetaDescr = MetaDescr.toUpperCase();
    if (MetaDescr == "" || MetaDescr == null) {
        alert("La descripci\u00f3n de la meta no puede ser vac\u00eda");
    } else if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
    } else if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
    } else if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
    } else if (clasificacion == "-1") {
        alert("La Clasificaci\u00f3n funcional es un par\u00e1metro requerido");
    } else if (compromiso == "-1") {
        alert("El compromiso es un par\u00e1metro requerido");
    } else if (ponderacion == "-1") {
        alert("La ponderaci\u00f3n es un par\u00e1metro requerido");
    } else if (transversalidad == "-1") {
        alert("El criterio de transversalidad es un  par\u00e1metro requerido");
        /*}else if(lineaSectorial=="-1"){
         alert("La linea sectorial es un  parámetro requerido");*/
    } else {
        fadeOutPopUp('infoMetaAmpRed');
        fadeInPopUp('estimacionMetaAR');
    }


}


function validarFlotanteNewAR(valor) {
    var total = 0;
    var input;
    var tipoC = $("#selCalculo").val();


    input = $(".calenVistaCAR .estimacion");
    if (valor == "")
        valor = 0;
    if (tipoC == "AC") {
        for (it = 0; it < input.length; it++) {
            if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                total += parseFloat(input[it].value.replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (total < parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
            }
        } else if (tipoC == "MI") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (parseFloat(input[it].value.replaceAll(",", "")) != 0) {
                        if (total > parseFloat(input[it].value.replaceAll(",", ""))) {
                            total = parseFloat(input[it].value.replaceAll(",", ""));
                        } else {
                            if (total == 0)
                                total = parseFloat(input[it].value.replaceAll(",", ""));
                        }
                    }
                }
            }
        }
    }
    if (total !== 0)
        $("#inTxtTotalEst").val(addCommas(total.toFixed(2)));
}

function getAmplRedMovimientoAccionrReq(identificador, estatus) {
    var fecha = $("#dateFor").val();
    var fechaCont = $("#fechaContabilidad").val();
    var ramo = $("#selRamo").val();
    var ramoDescr = $("#selRamo option:selected").text();
    var programa = $("#selPrg").val();
    var programaDescr = $("#selPrg option:selected").text();
    var proyectoCod = $("#selProy").val();
    var arrayProy = proyectoCod.split(",");
    var proyecto = arrayProy[0];
    var tipoProyecto = arrayProy[1];
    var proyectoDescr = $("#selProy option:selected").text();
    var meta = $("#selMeta").val();
    var metaDescr = $("#selMeta option:selected").text();
    var accion = $("#selAccion").val();
    var accionDescr = $("#selAccion option:selected").text();
    var partida = $("#selPartida").val();
    var partidaDescr = $("#selPartida option:selected").text();
    var relLaboral = $("#selRelLaboral").val();
    var relLaboralDescr = $("#selRelLaboral option:selected").text();
    var fuente = $("#selFuente").val();
    var fuenteDescr = $("#selFuente option:selected").text();
    var importe = parseFloat($("#inp-txt-impor-ampred").val().replaceAll(",", "")).toFixed(2);
    if (fuente !== '-1') {
        if (importe > 0) {
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxGetInfoMovAmpRedAccionReq.jsp',
                datatype: 'html',
                data: {
                    ramo: ramo,
                    ramoDescr: ramoDescr,
                    programa: programa,
                    programaDescr: programaDescr,
                    proyecto: proyecto,
                    tipoProyecto: tipoProyecto,
                    proyectoDescr: proyectoDescr,
                    meta: meta,
                    metaDescr: metaDescr,
                    accion: accion,
                    accionDescr: accionDescr,
                    partida: partida,
                    partidaDescr: partidaDescr,
                    fuente: fuente,
                    fuenteDescr: fuenteDescr,
                    relLaboral: relLaboral,
                    relLaboralDescr: relLaboralDescr,
                    importe: importe,
                    identificador: identificador,
                    estatus: estatus,
                    opcion: 1,
                    fecha: fecha
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    var msj = res.split("|");
                    if (res !== '-1') {
                        if (res === "-2") {
                            alert("No es posible realizar una ampliaci\u00F3n debido a que la meta o acci\u00F3n seleccionadas fueron deshabilitadas");
                        } else {
                            if (msj[0] === 2) {
                                alert(msj[1]);
                            } else {
                                $("#PopUpZone").html(res);
                                fadeInPopUp("PopUpZone");
                            }
                        }
                    } else {
                        alert("Ocurri\u00f3 un error inesperado al procesar la solicitud");
                    }
                }
            });
        } else {
            alert("El importe capturado debe de ser mayor a cero");
        }
    } else {
        alert("Debe de seleccionar todos los campos de captura");
    }
}

function getTransferenciaAccionrReq(identificador, estatus) {

    var codigoRecibe = "";
    var fechaCont = $("#fechaContabilidad").val();
    var transferenciaId = $("#transferenciaId").val();
    var ramo = $("#selRamo").val();
    var ramoDescr = $("#selRamo option:selected").text();
    var programa = $("#selPrg").val();
    var programaDescr = $("#selPrg option:selected").text();
    var proyectoCod = $("#selProy").val();
    var arrayProy = proyectoCod.split(",");
    var proyecto = arrayProy[0];
    var tipoProyecto = arrayProy[1];
    var proyectoDescr = $("#selProy option:selected").text();
    var meta = $("#selMeta").val();
    var metaDescr = $("#selMeta option:selected").text();
    var accion = $("#selAccion").val();
    var accionDescr = $("#selAccion option:selected").text();
    var partida = $("#selPartida").val();
    var partidaDescr = $("#selPartida option:selected").text();
    var relLaboral = $("#selRelLaboral").val();
    var relLaboralDescr = $("#selRelLaboral option:selected").text();
    var fuente = $("#selFuente").val();
    var fuenteDescr = $("#selFuenteD").val();
    var importe = parseFloat($("#inp-txt-impor-ampred").val().replaceAll(",", "")).toFixed(2);
    var fecha = $("#fecha").val();
    var codigoDisminuye = $("#codigoDisminuye").val();


    if (partida != '-1') {

        if (relLaboral == "-1") {
            codigoRecibe = ramo + "-" + programa + "-" + proyectoCod + "-" + meta + "-" + accion + "-" + partida + "-" + fuente + "-" + 0 + "-" + fuente;
        } else {
            codigoRecibe = ramo + "-" + programa + "-" + proyectoCod + "-" + meta + "-" + accion + "-" + partida + "-" + fuente + "-" + relLaboral + "-" + fuente;
        }

        if (codigoDisminuye == codigoRecibe) {
            alert("El c\u00f3digo que disminuye no puede ser el mismo que el que recibe");
            return 0;
        }

        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoMovTransferenciaAccionReq.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                ramoDescr: ramoDescr,
                programa: programa,
                programaDescr: programaDescr,
                proyecto: proyecto,
                tipoProyecto: tipoProyecto,
                proyectoDescr: proyectoDescr,
                meta: meta,
                metaDescr: metaDescr,
                accion: accion,
                accionDescr: accionDescr,
                partida: partida,
                partidaDescr: partidaDescr,
                fuente: fuente,
                fuenteDescr: fuenteDescr,
                relLaboral: relLaboral,
                relLaboralDescr: relLaboralDescr,
                importe: importe,
                transferenciaId: transferenciaId,
                estatus: estatus,
                opcion: 1,
                fecha: fecha
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-2") {
                    alert("No es posible realizar una ampliaci\u00f3n debido a que la meta o acci\u00f3n seleccinadas fueron deshabilitadas");
                } else {
                    var msj = res.split("|");
                    if (res !== '-1') {
                        if (msj[0] !== '2') {
                            $("#PopUpZone").html(res);
                            fadeInPopUp("PopUpZone");
                        } else {
                            alert("No hay disponible para otro requerimiento");
                        }
                    } else {
                        alert("Ocurri\u00f3 un error inesperado al procesar la solicitud");
                    }
                }
            }
        });
    } else {
        alert("Debe completar los campos de captura");
    }
}

function actualizarAmpliacionReduccionRecalendarizacionMeta() {

    var calPropuesta = $("#calPropuesta").val();
    calPropuesta = calPropuesta.replaceAll(",", "");
    var calOriginal = $("#calOriginal").val();
    calOriginal = calOriginal.replaceAll(",", "");
    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var unidadMedidaAct = $("#selMedida").val();
    if (unidadMedidaAct == "")
        unidadMedidaAct = "-1";
    var calculoAct = $("#selCalculo").val();
    if (calculoAct == "")
        calculoAct = "-1";
    var compromisoAct = $("#selCompromiso").val();
    if (compromisoAct == "")
        compromisoAct = "-1";
    var lineaAct = $("#selLineaPed").val();
    if (lineaAct == "")
        lineaAct = "-1";
    var lineaSectorialAct = $("#selLineaSect").val();
    if (lineaSectorialAct == "")
        lineaSectorialAct = "-1";
    var ponderacionAct = $("#selPonderacion").val();
    if (ponderacionAct == "")
        ponderacionAct = "-1";
    var transversalidadAct = $("#selTrasver").val();
    if (transversalidadAct == "")
        transversalidadAct = "-1";
    var appEstimacion = $("#appEstimacion").val();
    var txtAreaMetaRP = $("#txtAreaMetaRP").val();
    var cadenaAct = unidadMedidaAct + calculoAct + compromisoAct + lineaAct + lineaSectorialAct + ponderacionAct + transversalidadAct + appEstimacion + txtAreaMetaRP;
    var input = $(".calenVistaRC .estimacion");
    var unidadMedida = $("#selMedidaRP").val();
    var calculo = $("#selCalculoRP").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromisoRP").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacionRP").val();
    var transversalidad = $("#selTrasverRP").val();
    var valores = "";
    var valorSnComa;
    var appEstimacionRP = "";
    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        appEstimacionRP += valorSnComa;
        valores += valorSnComa + ",";
    }
    var metaDescr = $("#txtAreaMeta").val();
    metaDescr = metaDescr.toUpperCase();
    var cadena = unidadMedida + calculo + compromiso + linea + lineaSectorial + ponderacion + transversalidad + appEstimacionRP + metaDescr;
    var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);

    if (sumRecalendarizacion <= 0) {
        if (validarAccionesInhabilitadasByAccion("A", ramoId, metaId)) {
            var respConfirm = confirm("Esta meta fue recalendarizada en cero. \n \u00BFDesea Continuar ?");
            if (respConfirm !== true) {
                return false;
            }
        } else {
            alert("No es posible calendarizar a cero en la meta ya que aun cuenta con acciones calendarizadas");
            return false;
        }
    }

    if (cadena == cadenaAct) {
        alert("Se debe modificar al menos un campo de la informaci\u00f3n");
        return false;
    }

    if (metaDescr == "" || metaDescr == null) {
        alert("La descripci\u00f3n de la meta no puede ser vac\u00eda");
        return false;
    }

    if (clasificacion == "-1" || clasificacion == "" || clasificacion == null) {
        alert("La clasificaci\u00f3n funcional es un parámetro requerido");
        return false;
    }

    /*if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la recalendarizaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La linea PED es un par\u00e1metro requerido");
        return false;
    }

    if (transversalidad == "-1") {
        alert("El criterio de transversalidad es un par\u00e1metro requerido");
        return false;
    }

    if (compromiso == "-1") {
        alert("El compromiso es un par\u00e1metro requerido");
        return false;
    }

    if (ponderacion == "-1") {
        alert("La ponderaci\u00f3n es un par\u00e1metro requerido");
        return false;
    }


    //if (sumRecalendarizacion > 0) {
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarAmpliacionReduccionRecalendarizacionMeta.jsp',
        datatype: 'html',
        data: {
            identificador: identificador,
            ramoId: ramoId,
            valores: valores,
            metaId: metaId,
            programaId: programaId,
            tipoProy: tipoProy,
            proyectoId: proyectoId,
            sumEstimacion: sumEstimacion,
            sumRecalendarizacion: sumRecalendarizacion,
            metaDescr: metaDescr,
            unidadMedida: unidadMedida,
            calculo: calculo,
            clasificacion: clasificacion,
            compromiso: compromiso,
            linea: linea,
            lineaSectorial: lineaSectorial,
            ponderacion: ponderacion,
            transversalidad: transversalidad,
            calPropuesta: calPropuesta,
            calOriginal: calOriginal
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            actualizaTablaMovimientosAmplRed();
            $("#PopUpZone").html();
            fadeOutPopUp("PopUpZone");
            $('#mensaje').hide();

        }
    });
    /*} else {
     $('#mensaje').hide();
     alert("La propuesta de modificaci\u00f3n no puede ser cero");
     }*/

}

/*****cambios avance poa*****/
function getMetasAvancePoaOnChange() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var arrayProy;
    var optMeta = 1;
    var tipoProyecto;
    arrayProy = proyectoId.split(",");
    tipoProyecto = arrayProy[1];
    proyectoId = arrayProy[0];
    if (proyectoId != "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetMetasAvancePoa.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyectoId,
                optMeta: optMeta,
                tipoProyecto: tipoProyecto
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#div-listado").html(responce);
                $("#control-metas").show();
            }
        });
    } else {
        $("#div-listado").html("");
        $("#control-metas").hide();
    }
}

function AvancePoaMeta() {
    var ramoId = $("#selRamo").val();
    var metaRow = $("#tblMetas .selected td");
    var metaId;
    var metaDescr;
    var calculo;
    if (metaRow.length > 0) {
        metaId = metaRow[0].innerHTML;
        metaDescr = metaRow[1].innerHTML;
        calculo = metaRow[2].innerHTML;
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAvancePoaMetas.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                metaId: metaId,
                calculo: calculo,
                metaDescr: metaDescr

            },
            success: function(response) {
                $("#avancePoaMeta").html(response);
                $("#avancePoaMeta").show();

            }
        });

    } else {
        alert("Debe seleccionar una meta para continuar");

    }

}

function validarGuardarInfoTransferenciaAccion() {

    var accionDescr = $("#txtAreaAccion").val();
    accionDescr = accionDescr.toUpperCase();
    var selUnidadEj = $("#selUnidadEj").val();
    var unidadMedida = $("#selMedida").val();
    var selGrupo = $("#selGrupo").val();
    var selMunicipio = $("#selMunicipio").val();
    var selLocalidad = $("#selLocalidad").val();
    var calculo = $("#selCalculo").val();
    var linea = $("#selLineaPedRP").val();

    if (accionDescr == "" || accionDescr == null) {
        alert("La descripci\u00f3n de la acci\u00f3n no puede ser vac\u00eda");
        return false;
    }
    /*if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la reprogramaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/
    if (selUnidadEj == "-1") {
        alert("La unidad ejecutora es un par\u00e1metro requerido");
        return false;
    }
    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }
    /*if(selGrupo == "-1"){
     alert("El grupo poblacional es un parámetro requerido");
     return false;
     }*/
    if (selMunicipio == "-1") {
        alert("El municipio es un par\u00e1metro requerido");
        return false;
    }
    if (selLocalidad == "-1") {
        alert("El localidad es un par\u00e1metro requerido");
        return false;
    }
    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }
    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }

    fadeOutPopUp('infoAccion');
    fadeInPopUp('estimacionMeta');

}

function actualizarTransferenciaAccion() {

    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();
    var input = $(".calenVistaC .estimacion");
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var selGrupo = $("#selGrupo").val();
    var selUnidadEj = $("#selUnidadEj").val();
    var selMunicipio = $("#selMunicipio").val();
    var selLocalidad = $("#selLocalidad").val();
    var accMuj = $("#accMuj").val();
    var accHom = $("#accHom").val();
    //var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    //var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);
    var tipoTransferencia = $("#tipoTransferencia").val();
    var valores = "";
    var valorSnComa;
    var accionDescr = $("#txtAreaAccion").val();
    accionDescr = accionDescr.toUpperCase();
    if (accionDescr == "" || accionDescr == null) {
        alert("La descripci\u00f3n de la acci\u00f3n no puede ser vac\u00eda");
        return false;
    }

    /*   if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la reprogramaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }

    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        if (valorSnComa == "")
            valorSnComa = "0.0";
        valores += valorSnComa + ",";
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarTransferenciaAccion.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            valores: valores,
            metaId: metaId,
            accionId: accionId,
            accionDescr: accionDescr,
            programaId: programaId,
            tipoProy: tipoProy,
            proyectoId: proyectoId,
            unidadMedida: unidadMedida,
            calculo: calculo,
            selGrupo: selGrupo,
            selUnidadEj: selUnidadEj,
            selMunicipio: selMunicipio,
            selLocalidad: selLocalidad,
            accMuj: accMuj,
            accHom: accHom,
            linea: linea,
            lineaSectorial: lineaSectorial,
            identificador: identificador
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (tipoTransferencia === "T") {
                actualizaMovimientosTransferencia();
            } else {
                getAccionByMetaUsuarioAmpliacionReduccion();
                actualizaMovimientosTransferenciaReciben();
            }

            $("#PopUpZone").html();
            fadeOutPopUp("estimacionMeta");

        }
    });

    $('#mensaje').hide();

}

function getInfoAccionTransferenciaRecalendarizacion(identificador, estatus, tipoTransferencia) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionId = $("#selAccion").val();
    var oficio = $("#inp-folio-usr").val();
    var tipoValidacion = "A";
    var transferenciaId = $("#transferenciaId").val();
    var autorizacion = $("#autorizacion").val();

    if (accionId !== '-1') {

        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarMovimientosPendientesTransferencia.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                oficio: oficio,
                tipoValidacion: tipoValidacion,
                transferenciaId: transferenciaId,
                autorizacion: autorizacion
            },
            success: function(response) {

                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var abrirPopUp = true;

                if (res == "3") {
                    $('#mensaje').hide();
                    alert("Existe un movimiento igual pendiente no se puede agregar uno nuevo. \nFavor de editar el ya existente.");
                    abrirPopUp = false;
                } else {
                    if (res.split("|")[0] === "4") {
                        $('#mensaje').hide();
                        abrirPopUp = false;
                        alert("Existe un movimiento igual pendiente de autorizar no se puede agregar uno nuevo. Folio:" + res.split("|")[1]);
                    } else if (res === '6') {
                        $('#mensaje').hide();
                        alert("Esta acci\u00f3n no corresponde a ninguna transferencia registrada");
                        abrirPopUp = false;
                    } else {
                        if (res === '7') {
                            $('#mensaje').hide();
                            alert("No ha capturado transferencias");
                            abrirPopUp = false;
                        }
                    }
                }

                if (abrirPopUp == true) {
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxGetInfoTransferenciaRecalendarizacionAccion.jsp',
                        datatype: 'html',
                        data: {
                            ramoId: ramoId,
                            metaId: metaId,
                            accionId: accionId,
                            identificador: identificador,
                            estatus: estatus,
                            tipoTransferencia: tipoTransferencia,
                            folio: oficio,
                            autorizacion: autorizacion
                        },
                        success: function(response) {
                            $('#mensaje').hide();
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            if (res !== "-1") {
                                $("#PopUpZone").html(res);
                                $("#PopUpZone").fadeIn();
                            } else {
                                alert("Ocurri\u00f3 un error insesperado al procesar la solicitud");
                            }
                        }
                    });
                }
            }
        });

    } else {
        alert("Debe de seleccionar una acci\u00f3n para continuar");
    }

}

function getInfoAccionTransferenciaRecalendarizacionTabla(identificador, estatus, ramoId, metaId, accionId, tipoTransferencia) {

    $("#identificador").val(identificador);
    var oficio = $("#inp-folio-usr").val();
    var autorizacion = $("#autorizacion").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoTransferenciaRecalendarizacionAccion.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            accionId: accionId,
            identificador: identificador,
            estatus: estatus,
            tipoTransferencia: tipoTransferencia,
            autorizacion: autorizacion,
            folio: oficio
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $("#PopUpZone").html(res);
            fadeInPopUp("PopUpZone");
            $("#PopUpZone").fadeIn();

        }
    });

    $('#mensaje').hide();

}

function actualizarTransferenciaRecalendarizacionAccion() {
    var calPropuesta = $("#calPropuesta").val();
    calPropuesta = calPropuesta.replaceAll(",", "");
    var calOriginal = $("#calOriginal").val();
    calOriginal = calOriginal.replaceAll(",", "");
    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();
    var input = $(".calenVistaRC .estimacion");
    var unidadMedidaAct = $("#selMedida").val();
    if (unidadMedidaAct == "")
        unidadMedidaAct = "-1";
    var calculoAct = $("#selCalculo").val();
    if (calculoAct == "")
        calculoAct = "-1";
    var lineaAct = $("#selLineaPed").val();
    if (lineaAct == "")
        lineaAct = "-1";
    var lineaSectorialAct = $("#selLineaSect").val();
    if (lineaSectorialAct == "")
        lineaSectorialAct = "-1";
    var accMujRP = $("#accMuj").val();
    var accHomRP = $("#accHom").val();
    var appEstimacion = $("#appEstimacion").val();
    var txtAreaAccionRP = $("#txtAreaAccionRP").val();
    var cadenaAct = unidadMedidaAct + calculoAct + lineaAct + lineaSectorialAct + accMujRP + accHomRP + appEstimacion + txtAreaAccionRP;
    var unidadMedida = $("#selMedidaRP").val();
    var calculo = $("#selCalculoRP").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var accMuj = $("#accMujRP").val();
    var accHom = $("#accHomRP").val();
    var valorSnComa;
    var valores = "";
    var appEstimacionRP = "";
    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        appEstimacionRP += valorSnComa;
        valores += valorSnComa + ",";
    }
    var accionDescr = $("#txtAreaAccion").val();
    accionDescr = accionDescr.toUpperCase();
    var cadena = unidadMedida + calculo + linea + lineaSectorial + accMuj + accHom + appEstimacionRP + accionDescr;
    var selGrupo = $("#selGrupo").val();
    var selUnidadEj = $("#selUnidadEj").val();
    var selMunicipio = $("#selMunicipio").val();
    var selLocalidad = $("#selLocalidad").val();
    var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);
    var tipoTransferencia = $("#tipoTransferencia").val();

    if (sumRecalendarizacion <= 0) {
        var respConfirm = confirm("\u00c9sta acci\u00F3n fue recalendarizada con cero. \n \u00BFDesea Continuar?");
        if (respConfirm !== true) {
            return false;
        }
    }

    if (cadena == cadenaAct) {
        alert("Se debe modificar al menos un campo de la informaci\u00f3n");
        return false;
    }

    if (accionDescr == "" || accionDescr == null) {
        alert("La descripci\u00f3n de la acci\u00f3n no puede ser vac\u00eda");
        return false;
    }
    /*if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la recalendarizaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarTransferenciaRecalendarizacionAccion.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            valores: valores,
            metaId: metaId,
            accionId: accionId,
            sumRecalendarizacion: sumRecalendarizacion,
            sumEstimacion: sumEstimacion,
            accionDescr: accionDescr,
            programaId: programaId,
            tipoProy: tipoProy,
            proyectoId: proyectoId,
            unidadMedida: unidadMedida,
            calculo: calculo,
            selGrupo: selGrupo,
            selUnidadEj: selUnidadEj,
            selMunicipio: selMunicipio,
            selLocalidad: selLocalidad,
            accMuj: accMuj,
            accHom: accHom,
            linea: linea,
            lineaSectorial: lineaSectorial,
            identificador: identificador,
            calPropuesta: calPropuesta,
            calOriginal: calOriginal
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#PopUpZone").html("");
                fadeOutPopUp("PopUpZone");

                if (tipoTransferencia === "T") {
                    actualizaMovimientosTransferencia();
                }
                if (tipoTransferencia === "R") {
                    actualizaMovimientosTransferenciaReciben();
                }

            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }
        }
    });

    $('#mensaje').hide();

}

function getInfoMetaTransferenciaRecalendarizacion(identificador, estatus, tipoTransferencia) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var oficio = $("#inp-folio-usr").val();
    var tipoValidacion = "M";
    var transferenciaId = $("#transferenciaId").val();
    var autorizacion = $("#autorizacion").val();

    if (metaId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarMovimientosPendientesTransferencia.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                oficio: oficio,
                tipoValidacion: tipoValidacion,
                transferenciaId: transferenciaId,
                autorizacion: autorizacion
            },
            success: function(response) {

                var res = trim(response.replace("<!DOCTYPE html>", ""));
                var abrirPopUp = true;

                if (res == "1") {
                    $('#mensaje').hide();
                    alert("Existe un movimiento igual pendiente no se puede agregar uno nuevo. \nFavor de editar el ya existente.");
                    abrirPopUp = false;
                } else {
                    if (res.split("|")[0] == "2") {
                        $('#mensaje').hide();
                        abrirPopUp = false;
                        alert("Existe un movimiento igual pendiente de autorizar no se puede agregar uno nuevo. Folio: " + res.split("|")[1]);
                    } else if (res === '5') {
                        $('#mensaje').hide();
                        alert("Ésta meta no corresponde a ninguna transferencia registrada");
                        abrirPopUp = false;
                    }
                    if (res === '7') {
                        $('#mensaje').hide();
                        alert("No ha capturado transferencias");
                        abrirPopUp = false;
                    }
                }

                if (abrirPopUp == true) {
                    $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxGetInfoTransferenciaRecalendarizacionMeta.jsp',
                        datatype: 'html',
                        data: {
                            ramoId: ramoId,
                            metaId: metaId,
                            identificador: identificador,
                            estatus: estatus,
                            tipoTransferencia: tipoTransferencia,
                            autorizacion: autorizacion,
                            folio: oficio
                        },
                        success: function(response) {
                            $('#mensaje').hide();
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            if (res != "-1") {
                                $("#PopUpZone").html(res);
                                fadeInPopUp("PopUpZone");
                            } else {
                                alert("Ocurri\u00f3 un error al procesar la solicitud");
                            }
                        }
                    });
                }
            }
        });

    } else {
        alert("Debe de seleccionar una meta para continuar");
    }

}

function getInfoMetaTransferenciaRecalendarizacionTabla(identificador, estatus, ramoId, metaId, tipoTransferencia) {

    $("#identificador").val(identificador);
    var oficio = $("#inp-folio-usr").val();
    var autorizacion = $("#autorizacion").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoTransferenciaRecalendarizacionMeta.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            identificador: identificador,
            estatus: estatus,
            tipoTransferencia: tipoTransferencia,
            autorizacion: autorizacion,
            folio: oficio
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $("#PopUpZone").html(res);
            fadeInPopUp("PopUpZone");

        }
    });

    $('#mensaje').hide();

}



function abrirConfirm() {
    $("#dialog-confirm").dialog("open");
}


function comprobarContabilidad() {
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxComprobarContabilidad.jsp',
        datatype: 'html',
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== "-1") {
                if (res === "1") {
                    abrirConfirm();
                } else {
                    enviarAmpliacionReduccion();
                }
            } else {
                alert("Ocurri\u00f3 un erro al procesar fecha de contabilidad");
            }

        }
    });
}

function validaDatosMetaTransferencia() {

    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromiso").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacion").val();
    var transversalidad = $("#selTrasver").val();
    var MetaDescr = $("#txtAreaMeta").val();
    MetaDescr = MetaDescr.toUpperCase();
    if (MetaDescr == "" || MetaDescr == null) {
        alert("La descripci\u00f3n de la meta no puede ser vac\u00eda");
        return false;
    } else if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    } else if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    } else if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    } else if (clasificacion == "-1") {
        alert("La Clasificaci\u00f3n funcional es un par\u00e1metro requerido");
        return false;
    } else if (compromiso == "-1") {
        alert("El compromiso es un par\u00e1metro requerido");
        return false;
    } else if (ponderacion == "-1") {
        alert("La ponderaci\u00f3n es un par\u00e1metro requerido");
        return false;
    } else if (transversalidad == "-1") {
        alert("El criterio de transversalidad es un  par\u00e1metro requerido");
        return false;
    } else {
        fadeOutPopUp('infoMetaAmpRed');
        fadeInPopUp('estimacionMetaAR');
    }

}

function validarFlotanteNewMetaTransferencia(valor) {

    var total = 0;
    var input;
    var tipoC = $("#selCalculo").val();
    input = $(".calenVistaCAR .estimacion");

    if (valor == "") {
        valor = 0;
    }

    if (tipoC == "AC") {
        for (it = 0; it < input.length; it++) {
            if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                total += parseFloat(input[it].value.replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (total < parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
            }
        } else {
            if (tipoC == "MI") {
                total = parseFloat(valor.replaceAll(",", ""));
                for (it = 0; it < input.length; it++) {
                    if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                        if (parseFloat(input[it].value.replaceAll(",", "")) != 0) {
                            if (total > parseFloat(input[it].value.replaceAll(",", ""))) {
                                total = parseFloat(input[it].value.replaceAll(",", ""));
                            } else {
                                if (total == 0)
                                    total = parseFloat(input[it].value.replaceAll(",", ""));
                            }
                        }
                    }
                }
            }
        }
    }

    if (total !== 0) {
        $("#inTxtTotalEst").val(addCommas(total.toFixed(2)));
    }

}



function avancePoaAccionSelAccion() {
    var accionRow = $("#tblAvancePoaAccion .selected td");
    var accionId = "";
    var ramoId = $("#ramo").val();
    var metaId = $("#meta").val();
    var accionDescr = "";
    var calculo;

    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        accionDescr = accionRow[1].innerHTML;
        calculo = accionRow[4].innerHTML;

        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAvancePoaAccion2.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                accionDescr: accionDescr,
                calculo: calculo
            },
            success: function(response) {
                $("#avanceAccionAvancePOA").html(response);
                $("#avanceAccionAvancePOA").show();

            }
        });

    } else {
        alert("Debe seleccionar una acci\u00f3n antes de continuar");
    }


}

function validarFlotanteAvancePoaMetas(valor) {
    var total = 0;
    var input;
    var tipoC = $("#calc").val();
    var cont = $("#contador").val();
    var campo = "";

    if (valor == "")
        valor = 0;
    if (tipoC == "AC") {
        for (it = 1; it <= cont; it++) {
            campo = "realizado" + it;
            if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                total += parseFloat($("#" + campo).val().replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 1; it <= cont; it++) {
                campo = "realizado" + it;
                if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                    if (total < parseFloat($("#" + campo).val().replaceAll(",", ""))) {
                        total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    }
                }
            }
        } else if (tipoC == "MI") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 1; it <= cont; it++) {
                campo = "realizado" + it;
                if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                    if (total > parseFloat($("#" + campo).val().replaceAll(",", ""))) {
                        total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    } else {
                        if (total == 0)
                            total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    }
                }
            }
        }
    }
    if (total !== 0)
        $("#totalRealizado").val(addCommas(total.toFixed(2)));
}


function redirectAvancePoa() {
    $("#frmAvanceAcciones").submit();
}


function ActualizaAvancePoaAcciones() {
    var ramoId = $("#ramo").val();
    var metaId = $("#meta").val();
    var accionId = $("#accion").val();
    var contador = $("#cont").val();
    var periodo = $("#periodo").val();
    var realizado = "";
    var ampRed = "";
    var ejercido = "";
    var observacion = "";
    var valor = "";
    var variable = "";


    variable = "realizado" + periodo;
    if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
        valor = "0";
    } else {
        valor = $("#" + variable).val().replaceAll(",", "")
    }
    realizado = valor;

    variable = "IMPT_ASIG_XMES" + periodo;
    if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
        valor = "0";
    } else {
        valor = $("#" + variable).val()
    }
    ampRed = valor;

    variable = "IMPT_EJER_XMES" + periodo;
    if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
        valor = "0";
    } else {
        valor = $("#" + variable).val()
    }
    ejercido = valor;

    variable = "observaciones" + periodo;
    if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
        valor = " ";
    } else {
        valor = $("#" + variable).val()
    }
    observacion = valor;
    variable = "observaciones" + periodo;
    var ObligadoObservacion = $("#ObligadoObservacion").val();
    if (ObligadoObservacion == "S" && (periodo % 3) == 0) {
        if ($("#" + variable).val().trim().length > 0) {
            ObligadoObservacion = "N";

        }
    }
    if (ObligadoObservacion == "N") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAvancePoaAccion3.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                mes: periodo,
                realizado: realizado,
                ampRed: ampRed,
                ejercido: ejercido,
                observacion: observacion
            },
            success: function(response) {
                $("#Observa").html(response);
                $("#Observa").show();

            }
        });
    } else {

        alert("Esta Obligado a capturar observacion por ser una accion que esta definida como de Resultado");
    }


}

function darFormato(valor) {
    var parts = (valor + "").split("."),
            main = parts[0],
            len = main.length,
            output = "",
            i = len - 1;

    while (i >= 0) {
        output = main.charAt(i) + output;
        if ((len - i) % 3 === 0 && i > 0) {
            output = "," + output;
        }
        --i;
    }
    // put decimal part back
    if (parts.length > 1) {
        output += "." + parts[1].substring(0, 2);
    }
    return output;
}




/*****cambios avance poa*****/
function getMetasAvancePoaOnChange() {
    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrograma").val();
    var proyectoId = $("#selProyecto").val();
    var arrayProy;
    var optMeta = 1;
    var tipoProyecto;
    if (proyectoId != null) {
        arrayProy = proyectoId.split(",");
        tipoProyecto = arrayProy[1];
        proyectoId = arrayProy[0];
    } else {
        tipoProyecto = "-1";
        proyectoId = "-1";
    }
    if (proyectoId != "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetMetasAvancePoa.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                programaId: programaId,
                proyectoId: proyectoId,
                optMeta: optMeta,
                tipoProyecto: tipoProyecto
            },
            success: function(responce) {
                $('#mensaje').hide();
                $("#div-listado").html(responce);
                $("#control-metas").show();
            }
        });
    } else {
        $("#div-listado").html("");
        $("#control-metas").hide();
    }
}

function actualizarTransferenciaRecalendarizacionMeta() {
    var calPropuesta = $("#calPropuesta").val();
    calPropuesta = calPropuesta.replaceAll(",", "");
    var calOriginal = $("#calOriginal").val();
    calOriginal = calOriginal.replaceAll(",", "");
    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var tipoTransferencia = $("#tipoTransferencia").val();
    var unidadMedidaAct = $("#selMedida").val();
    if (unidadMedidaAct == "")
        unidadMedidaAct = "-1";
    var calculoAct = $("#selCalculo").val();
    if (calculoAct == "")
        calculoAct = "-1";
    var compromisoAct = $("#selCompromiso").val();
    if (compromisoAct == "")
        compromisoAct = "-1";
    var lineaAct = $("#selLineaPed").val();
    if (lineaAct == "")
        lineaAct = "-1";
    var lineaSectorialAct = $("#selLineaSect").val();
    if (lineaSectorialAct == "")
        lineaSectorialAct = "-1";
    var ponderacionAct = $("#selPonderacion").val();
    if (ponderacionAct == "")
        ponderacionAct = "-1";
    var transversalidadAct = $("#selTrasver").val();
    if (transversalidadAct == "")
        transversalidadAct = "-1";
    var appEstimacion = $("#appEstimacion").val();
    var txtAreaMetaRP = $("#txtAreaMetaRP").val();
    var cadenaAct = unidadMedidaAct + calculoAct + compromisoAct + lineaAct + lineaSectorialAct + ponderacionAct + transversalidadAct + appEstimacion + txtAreaMetaRP;
    var input = $(".calenVistaRC .estimacion");
    var unidadMedida = $("#selMedidaRP").val();
    var calculo = $("#selCalculoRP").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromisoRP").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacionRP").val();
    var transversalidad = $("#selTrasverRP").val();
    var appEstimacionRP = "";
    var valorSnComa;
    var valores = "";
    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        appEstimacionRP += valorSnComa;
        valores += valorSnComa + ",";
    }
    var metaDescr = $("#txtAreaMeta").val();
    metaDescr = metaDescr.toUpperCase();
    var cadena = unidadMedida + calculo + compromiso + linea + lineaSectorial + ponderacion + transversalidad + appEstimacionRP + metaDescr;
    var sumEstimacion = parseFloat($("#sumEstimacion").val().replaceAll(",", "")).toFixed(2);
    var sumRecalendarizacion = parseFloat($("#sumRecalendarizacion").val().replaceAll(",", "")).toFixed(2);

    if (sumRecalendarizacion <= 0) {
        if (validarAccionesInhabilitadasByAccion('T', ramoId, metaId)) {
            var respConfirm = confirm("\u00c9sta meta fue recalendarizada con cero. \n \u00BFDesea Continuar ?");
            if (respConfirm !== true) {
                return false;
            }
        } else {
            alert("No es posible calendarizar a cero en la meta ya que aun cuenta con acciones calendarizadas");
            return false;
        }
    }

    if (cadena == cadenaAct) {
        alert("Se debe modificar al menos un campo de la informaci\u00f3n");
        return false;
    }

    if (metaDescr == "" || metaDescr == null) {
        alert("La descripci\u00f3n de la meta no puede ser vac\u00eda");
        return false;
    }

    if (clasificacion == "-1" || clasificacion == "" || clasificacion == null) {
        alert("La clasificaci\u00f3n funcional es un par\u00e1metro requerido");
        return false;
    }

    /*if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la recalendarizaci\u00f3n tiene que ser igual a la sumatoria de la estimaci\u00f3n");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
        return false;
    }

    if (transversalidad == "-1") {
        alert("El criterio de transversalidad es un par\u00e1metro requerido");
        return false;
    }

    if (compromiso == "-1") {
        alert("El compromiso es un par\u00e1metro requerido");
        return false;
    }

    if (ponderacion == "-1") {
        alert("La ponderaci\u00f3n es un par\u00e1metro requerido");
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarTransferenciaRecalendarizacionMeta.jsp',
        datatype: 'html',
        data: {
            linea: linea,
            ramoId: ramoId,
            metaId: metaId,
            valores: valores,
            calculo: calculo,
            tipoProy: tipoProy,
            metaDescr: metaDescr,
            programaId: programaId,
            proyectoId: proyectoId,
            compromiso: compromiso,
            ponderacion: ponderacion,
            unidadMedida: unidadMedida,
            identificador: identificador,
            clasificacion: clasificacion,
            lineaSectorial: lineaSectorial,
            transversalidad: transversalidad,
            sumEstimacion: sumEstimacion,
            sumRecalendarizacion: sumRecalendarizacion,
            calPropuesta: calPropuesta,
            calOriginal: calOriginal

        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== "-1") {
                fadeOutPopUp("PopUpZone");
                $("#PopUpZone").html("");
                if (tipoTransferencia === "T") {
                    actualizaMovimientosTransferencia();
                }
                if (tipoTransferencia === "R") {
                    actualizaMovimientosTransferenciaReciben();
                }

            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }

        }
    });

}

function abrirConfirm() {
    $("#dialog-confirm").dialog("open");
}

/*
 function comprobarContabilidad() {
 $.ajax({
 type: 'POST',
 url: 'librerias/ajax/ajaxComprobarContabilidad.jsp',
 datatype: 'html',
 success: function(response) {
 var res = trim(response.replace("<!DOCTYPE html>", ""));
 if (res !== "-1") {
 if (res === "1") {
 abrirConfirm();
 }
 else {
 enviarAmpliacionReduccion();
 }
 }
 else {
 alert("Ocurri\u00f3 un erro al procesar fecha de contabilidad");
 }
 
 }
 });
 }
 */
function getInfoMetaTransferencia(identificador, estatus, tipoTransferencia) {

    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrg").val();
    var proyecto = $("#selProy").val();
    var arrayTipoProyecto = proyecto.split(",");
    var proyectoId = arrayTipoProyecto[0];
    var tipoProyecto = arrayTipoProyecto[1];

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    if (estatus !== 'V') {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoTransferenciaMeta.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                proyectoId: proyectoId,
                tipoProyecto: tipoProyecto,
                programaId: programaId,
                identificador: identificador,
                estatus: estatus,
                tipoTransferencia: tipoTransferencia
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== "-1") {
                    $("#PopUpZone").html(res);
                    fadeInPopUp("PopUpZone");
                    fadeInPopUp("infoMetaAmpRed");
                    fadeOutPopUp("estimacionMetaAR");
                } else {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                }

            }
        });
    } else {
        $('#mensaje').hide();
        alert("No es posible crear metas si el movimiento est\u00e1 en revisi\u00f3n");
    }

}

function validaDatosMetaTransferencia() {

    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromiso").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacion").val();
    var transversalidad = $("#selTrasver").val();
    var MetaDescr = $("#txtAreaMeta").val();
    MetaDescr = MetaDescr.toUpperCase();
    if (MetaDescr == "" || MetaDescr == null) {
        alert("La descripci\u00f3n de la meta no puede ser vac\u00eda");
        return false;
    } else if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    } else if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    } else if (linea == "-1") {
        alert("La linea PED es un par\u00e1metro requerido");
        return false;
    } else if (clasificacion == "-1") {
        alert("La Clasificaci\u00f3n funcional es un par\u00e1metro requerido");
        return false;
    } else if (compromiso == "-1") {
        alert("El compromiso es un par\u00e1metro requerido");
        return false;
    } else if (ponderacion == "-1") {
        alert("La ponderaci\u00f3n es un par\u00e1metro requerido");
        return false;
    } else if (transversalidad == "-1") {
        alert("El criterio de transversalidad es un  par\u00e1metro requerido");
        return false;
    } else {
        fadeOutPopUp('infoMetaAmpRed');
        fadeInPopUp('estimacionMetaAR');
    }

}

function actualizarTransferenciaMeta() {

    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var input = $(".calenVistaCAR .estimacion");
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromiso").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacion").val();
    var transversalidad = $("#selTrasver").val();
    var obra = $("#selObraP").val();
    var identificador = $("#identificador").val();
    var inTxtTotalEst = $("#inTxtTotalEst").val();
    var num_meses = $("#num_meses").val();
    var MetaDescr = $("#txtAreaMeta").val();
    MetaDescr = MetaDescr.toUpperCase();
    var valores = "";
    var variable;
    var valor = 0;
    var tipoTransferencia = $("#tipoTransferencia").val();

    for (i = 0; i < num_meses; i++) {
        variable = "";
        variable = "estimacion" + i;
        if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
            valor = "0";
        } else {
            valor = $("#" + variable).val().replaceAll(",", "");
        }
        valores += valor + ",";
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizarTransferenciaMeta.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            metaDescr: MetaDescr,
            programaId: programaId,
            tipoProy: tipoProy,
            proyectoId: proyectoId,
            unidadMedida: unidadMedida,
            calculo: calculo,
            linea: linea,
            lineaSectorial: lineaSectorial,
            identificador: identificador,
            clasificacion: clasificacion,
            compromiso: compromiso,
            ponderacion: ponderacion,
            transversalidad: transversalidad,
            valores: valores,
            obra: obra
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== "-1") {

                if (tipoTransferencia === "T") {
                    actualizaMovimientosTransferencia();
                }
                if (tipoTransferencia === "R") {
                    getMetasByProyectoUsuarioAmpliacionReduccion();
                    actualizaMovimientosTransferenciaReciben();
                }

                $("#PopUpZone").html();
                fadeOutPopUp("estimacionMetaAR");
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }
        }
    });
}

function getMovimientosAutorizados() {
    var ramo = $("#selRamo").val();
    var opcion = 2;

    if (ramo.length > 0 && ramo.length > 0) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetMovimientos.jsp",
            dataType: 'html',
            data: {
                ramo: ramo,
                opcion: opcion
            },
            success: function(respoce) {
                $('#mensaje').hide();
                $("#div-movimientos").html(respoce);
            }
        });
    } else {
        alert("El ramo es un par\u00e1metro requerido");
        return false;
    }
}
function verMovto(folio, estatus, tipoMov) {
    $("#folio").val(folio);
    $("#estatus").val(estatus);
    $("#tipoMov").val(tipoMov);

    if (tipoMov == 'C') {
        $("#frmEnvia").attr('action', 'recalendarizacion.jsp');
    } else if (tipoMov == 'R') {
        $("#frmEnvia").attr('action', 'reprogramacion.jsp');
    } else if (tipoMov == 'A') {
        $("#frmEnvia").attr('action', 'ampliacionReduccion.jsp');
    }
    $("#frmEnvia").submit();
}

function getMetasByProyectoUsuarioAmpliacionReduccion() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    $("#selMeta").html("<option value='-1'>-- Seleccione una meta --</option>");
    $("#selAccion").html("<option value='-1'> -- Seleccione una acci&oacute;n --</option>");
    $("#selPartida").html("<option value='-1'> -- Seleccione una partida --</option>");
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    $("#selReq").html("<option value='-1'> -- Seleccione un requerimiento --</option>");
    var transferenciaId = $("#transferenciaId").val();
    if (proy !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetMetaByProyectoUsuarioAmpliacionReduccion.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                transferenciaId: transferenciaId
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selMeta").html(response);
                    }
                }
            }
        });
    }
}

function getAccionByMetaUsuarioAmpliacionReduccion() {
    var ramo = $("#selRamo").val();
    var prg = $("#selPrg").val();
    var proy = $("#selProy").val();
    var meta = $("#selMeta").val();
    var transferenciaId = $("#transferenciaId").val();
    $("#selAccion").html("<option value='-1'> -- Seleccione una acci&oacute;n --</option>");
    $("#selPartida").html("<option value='-1'> -- Seleccione una partida --</option>");
    $("#selRelLaboral").html("<option value='-1'> -- Seleccione una relaci&oacute;n laboral -- </option>");
    $("#selFuente").html("<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>");
    if (meta !== "-1") {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetAccionByMetaUsuarioAmpliacionReduccion.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: prg,
                proyecto: proy,
                meta: meta,
                transferenciaId: transferenciaId
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selAccion").html(response);
                    }
                }
            }
        });
    }
}

function asignarFechaContable() {
    if ($("#checkContable").is(":checked")) {
        $("#fechaContabilidad").val(true);
    } else {
        $("#fechaContabilidad").val(false);
    }
}






function AvancePoaMeta() {
    var ramoId = $("#selRamo").val();
    var metaRow = $("#tblMetas .selected td");
    var metaId;
    var metaDescr;
    var calculo;
    if (metaRow.length > 0) {
        metaId = metaRow[0].innerHTML;
        metaDescr = metaRow[1].innerHTML;
        calculo = metaRow[2].innerHTML;
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAvancePoaMetas.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                metaId: metaId,
                calculo: calculo,
                metaDescr: metaDescr
            },
            success: function(response) {
                $("#avancePoaMeta").html(response);
                $("#avancePoaMeta").show();

            }
        });

    } else {
        alert("Debe seleccionar una meta para continuar");

    }

}


function actualizaAvancePoaMeta() {
    var totalProgramado = $("#totalProgramado").val();
    var totalRealizado = $("#totalRealizado").val();
    var metaId = $("#met").val();
    var ramoId = $("#ramoId").val();
    var periodo = $("#periodo").val();
    var anio = $("#anio").val();
    var contador = $("#contador").val();
    var valoresRealizado = "";
    var valoresProgramado = "";
    var valoresActivo = "";
    var observacion = "";//$("#observacion").val();
    var observacionAnual = $("#observacionAnual").val().toUpperCase();
    for (i = 1; i <= contador; i++) {
        variable = "";
        variable = "programado" + i;
        if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
            valor = "0";
        } else {
            valor = $("#" + variable).val().replaceAll(",", "");
        }
        valoresProgramado += valor + "}";
        variable = "";
        variable = "realizado" + i;
        if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
            valor = "0";
        } else {
            valor = $("#" + variable).val().replaceAll(",", "");
        }
        valoresRealizado += valor + "}";
        variable = "";
        variable = "activo" + i;
        if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
            valor = "0";
        } else {
            valor = $("#" + variable).val()
        }
        valoresActivo += valor + "}";

        variable = "";
        variable = "observacion" + i;
        if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
            valor = " ";
        } else {
            valor = $("#" + variable).val().toUpperCase();
        }
        observacion += valor + "}";

    }
    //  alert("contador:"+contador+"programado:"+valoresProgramado)
    // alert("realizado:"+valoresRealizado)
    var ObservObligatoria = $("#ObligadoObservacion").val();
    if (ObservObligatoria == "S") {
        // alert('1');
        variable = "observacion" + periodo;
        if (($("#" + variable).val().trim().length > 0) || (periodo == 1 || periodo == 2 || periodo == 4 || periodo == 5 || periodo == 7 || periodo == 8 || periodo == 10 || periodo == 11)) {
            // alert('2'+observacion.trim().length+" "+observacion);
            ObservObligatoria = "N";
        }
    }

    if (ObservObligatoria == "N") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxActualizaAvancePoaMetas.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                metaId: metaId,
                periodo: periodo,
                anio: anio,
                totalProgramado: totalProgramado,
                totalRealizado: totalRealizado,
                valoresProgramado: valoresProgramado,
                valoresRealizado: valoresRealizado,
                valoresActivo: valoresActivo,
                observacion: observacion,
                observacionAnual: observacionAnual

            },
            success: function(response) {
                response += "<br/><br/><center><input type='button' value='Cerrar' onclick='$(\"#Observa\").hide();'/></center>"
                $("#Observa").html(response);
                $("#Observa").show();


            }
        });
    } else {

        alert("Esta Meta esta Obligada a captura de observación por estar definida como con Resultado");
    }

}

function AvancePoaAccion() {
    var metaRow = $("#tblMetas .selected td");
    var metaId;
    if (metaRow.length > 0) {
        metaId = metaRow[0].innerHTML;
        $("#meta").val(metaId);
        $("#frmListadoMetas").submit();
    } else {
        alert("Debe seleccionar una meta para continuar");
    }

}

function avancePoaAccionSelAccion() {
    var accionRow = $("#tblAvancePoaAccion .selected td");
    var accionId = "";
    var ramoId = $("#ramo").val();
    var metaId = $("#meta").val();
    var accionDescr = "";
    var calculo = "";
    if (accionRow.length > 0) {
        accionId = accionRow[0].innerHTML;
        accionDescr = accionRow[1].innerHTML;
        calculo = accionRow[4].innerHTML;
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAvancePoaAccion2.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                accionDescr: accionDescr,
                calculo: calculo
            },
            success: function(response) {
                $("#avanceAccionAvancePOA").html(response);
                $("#avanceAccionAvancePOA").show();
            }
        });
    } else {
        alert("Debe seleccionar una acci\u00f3n antes de continuar");
    }


}

function validarFlotanteAvancePoaMetas(valor) {
    var total = 0;
    var input;
    var tipoC = $("#calc").val();
    var cont = $("#contador").val();
    var campo = "";

    if (valor == "")
        valor = 0;
    if (tipoC == "AC") {
        for (it = 1; it <= cont; it++) {
            campo = "realizado" + it;
            if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                total += parseFloat($("#" + campo).val().replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 1; it <= cont; it++) {
                campo = "realizado" + it;
                if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                    if (total < parseFloat($("#" + campo).val().replaceAll(",", ""))) {
                        total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    }
                }
            }
        } else if (tipoC == "MI") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 1; it <= cont; it++) {
                campo = "realizado" + it;
                if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                    if (total > parseFloat($("#" + campo).val().replaceAll(",", ""))) {
                        total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    } else {
                        if (total == 0)
                            total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    }
                }
            }
        }
    }
    if (total !== 0)
        $("#totalRealizado").val(addCommas(total.toFixed(2)));
}


function redirectAvancePoa() {
    $("#frmAvanceAcciones").submit();
}


function ActualizaAvancePoaAcciones() {
    var ramoId = $("#ramo").val();
    var metaId = $("#meta").val();
    var accionId = $("#accion").val();
    var contador = $("#cont").val();
    var periodo = $("#periodo").val();
    var realizado = "";
    var ampRed = "";
    var ejercido = "";
    var observacion = "";
    var valor = "";
    var variable = "";


    variable = "realizado" + periodo;
    if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
        valor = "0";
    } else {
        valor = $("#" + variable).val().replaceAll(",", "")
    }
    realizado = valor;

    variable = "IMPT_ASIG_XMES" + periodo;
    if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
        valor = "0";
    } else {
        valor = $("#" + variable).val()
    }
    ampRed = valor;

    variable = "IMPT_EJER_XMES" + periodo;
    if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
        valor = "0";
    } else {
        valor = $("#" + variable).val()
    }
    ejercido = valor;

    variable = "observaciones" + periodo;
    if ($("#" + variable).val() == null || $("#" + variable).val() == "") {
        valor = " ";
    } else {
        valor = $("#" + variable).val()
    }
    observacion = valor;
    variable = "observaciones" + periodo;
    var ObligadoObservacion = $("#ObligadoObservacion").val();
    if (ObligadoObservacion == "S") {
        if ($("#" + variable).val().trim().length > 0) {
            ObligadoObservacion = "N";

        }
    }
    if (ObligadoObservacion == "N") {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAvancePoaAccion3.jsp",
            dataType: 'html',
            async: false,
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                mes: periodo,
                realizado: realizado,
                ampRed: ampRed,
                ejercido: ejercido,
                observacion: observacion
            },
            success: function(response) {
                $("#Observa").html(response);
                $("#Observa").show();
                $("#avanceAccionAvancePOA").hide();

            }
        });
    } else {

        alert("Esta Accion esta Obligada a capturar Observación por estar definida como con Resultado");
    }


}

function darFormato(valor) {
    var parts = (valor + "").split("."),
            main = parts[0],
            len = main.length,
            output = "",
            i = len - 1;

    while (i >= 0) {
        output = main.charAt(i) + output;
        if ((len - i) % 3 === 0 && i > 0) {
            output = "," + output;
        }
        --i;
    }
    // put decimal part back
    if (parts.length > 1) {
        output += "." + parts[1].substring(0, 2);
    }
    return output;
}

function generarInformacionSIP() {

    var ramoId = $("#selRamo").val();

    var generarInformacion = false;
    var mensajeError = "";

    if (ramoId != "-1")
        generarInformacion = true;
    else
        mensajeError += "\nEl ramo es un parametro requerido"

    if (generarInformacion) {
        var vSeleccionado = "";
        var inputs = document.querySelectorAll("input[type='radio']");

        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].checked)
            {
                vSeleccionado = "1";
            }
        }
        if (vSeleccionado != "1")
        {
            alert("Es necesario seleccionar al menos un oficio");
            return false;
        }
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetInformacionSIP.jsp",
            dataType: 'html',
            data: $("#ramoForm").serialize(),
            success: function(response) {
                $('#mensaje').hide();
                response = trim(response.replace("<!DOCTYPE html>", ""));
                /**aquivoy**/
                var jsons = jQuery.parseJSON(response);
                if (jsons.resultado == "-1")
                    alert("No se pudo generar los archivos");
                else {
                    if (jsons.resultado == "-2")
                        alert("No se pudo generar los archivos porque el Ramo debe de estar cerrado");
                    else {
                        $("#donwLink").attr("href", "temp/TempEstructuraPresupuestal/" + jsons.ruta + ".zip");
                        var donwLink = document.getElementById('donwLink');
                        donwLink.click();
                        setTimeout(continueExecution(ramoId, numTemp), 500000);

                    }

                }
            }
        });



    } else {
        alert(mensajeError);
    }


}

function cargaCaratula()
{
    if (!($("#fecha").val() == "" && $("#txtSesion").val() == ""))
    {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxCargaCaratula.jsp",
            dataType: 'html',
            data: $("#formCaratula").serialize(),
            success: function(response) {
                var jsons;
                //response = trim(response.replace("<!DOCTYPE html>", ""));
                jsons = jQuery.parseJSON(response);
                if (jsons.resultado == "1") {
                    $("#txtSesPres").val(jsons.pres);
                    $("#txtSesProg").val(jsons.prog);
                    $("#dIDCaratula").html(jsons.id);
                } else
                {
                    $("#txtSesPres").val("");
                    $("#txtSesProg").val("");
                    $("#dIDCaratula").html("");
                }
            }
        });
    }
    $('#mensaje').hide();
}

function getInfoCaratulas() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxMostrarCaratulas.jsp',
        datatype: 'html',
        data: $("#formCaratula").serialize(),
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $("#PopUpZone").html(res);
            //$("#PopUpZone").fadeIn();
            fadeInPopUp("PopUpZone");

        }
    });
}

function cargaInfoCaratulas(sSesion, sFecha, sTipo) {
    $("#fecha").val(sFecha);
    $("#txtSesion").val(sSesion);
    $("#selTipo").val(sTipo);
    cargaCaratula();
    $("#PopUpZone").hide();
}

function transferirCodigo(transferenciaId) {
    var importe = parseFloat($("#inp-txt-impor-trans").val().replaceAll(",", ""));
    var disponible = parseFloat($("#inp-txt-disp-trans").val().replaceAll(",", ""));
    var ramoDescr = $("#selRamo option:selected").text();
    var programaDescr = $("#selPrg option:selected").text();
    var proyectoDescr = $("#selProy option:selected").text();
    var metaDescr = $("#selMeta option:selected").text();
    var accionDescr = $("#selAccion option:selected").text();
    var partidaDescr = $("#selPartida option:selected").text();
    var relLaboralDescr = $("#selRelLaboral option:selected").text();
    var fuenteDescr = $("#selFuente option:selected").text();
    var ramo = $("#selRamo").val();
    var programa = $("#selPrg").val();
    var partida = $("#selPartida").val();
    var proyecto = $("#selProy").val();
    var meta = $("#selMeta").val();
    var accion = $("#selAccion").val();
    var relLaboral = $("#selRelLaboral").val();
    var fuente = $("#selFuente").val();
    var idCaratula = $("#selCaratula option:selected").val();
    $("#justificacionTrans").val($("#txt-area-justif").val());
    if (fuente !== "-1") {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidaCodigoTransferencia.jsp',
            datatype: 'html',
            data: {
                ramo: ramo,
                programa: programa,
                partida: partida,
                proyecto: proyecto,
                meta: meta,
                accion: accion,
                relLaboral: relLaboral,
                fuente: fuente,
                importe: importe,
                disponible: disponible
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === '1') {
                    if (importe > 0) {
                        $("#ramoDescr").val(ramoDescr);
                        $("#programaDescr").val(programaDescr);
                        $("#proyDescr").val(proyectoDescr);
                        $("#metaDescr").val(metaDescr);
                        $("#accionDescr").val(accionDescr);
                        $("#partidaDescr").val(partidaDescr);
                        $("#relLaboralDescr").val(relLaboralDescr);
                        $("#fuenteDescr").val(fuenteDescr);
                        $("#transferenciaId").val(transferenciaId);
                        $("#idCaratula").val(idCaratula);
                        $("#form-transferencia").submit();
                    } else {
                        alert("El importe debe de ser positivo");
                    }
                } else {
                    if(res === '2'){
                        alert("Este c\u00f3digo ya fue registrado para transferencia. Seleccione otro por favor");
                    } else {
                        alert("El importe a transferir no puede ser mayor al disponible");
                    }                            
                }
            }
        });
    } else {
        alert("Debe de completar la selecci\u00f3n de los campos para continuar");
    }
}

function getInfoMetaTransferenciaTabla(identificador, estatus, ramoId, metaId, tipoTransferencia) {

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoTransferenciaMeta.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            identificador: identificador,
            estatus: estatus,
            tipoTransferencia: tipoTransferencia,
            autorizacion: autorizacion
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== "-1") {
                $("#PopUpZone").html(res);
                fadeInPopUp("PopUpZone");
                fadeInPopUp("infoMetaAmpRed");
                fadeOutPopUp("estimacionMetaAR");
            } else {
                alert("Ocurri\u00f3 un erro al procesar la solicitud");
            }
        }
    });

    $('#mensaje').hide();

}


function getInfoAccionTransferencia(identificador, estatus, tipoTransferencia) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionId = $("#selAccion").val();
    var oficio = $("#inp-folio-usr").val();
    var tipoValidacion = "A";
    var autorizacion = $("#autorizacion").val();
    if (estatus !== 'V') {
        if (metaId !== '-1') {
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxGetInfoTransferenciaAccion.jsp',
                datatype: 'html',
                data: {
                    ramoId: ramoId,
                    metaId: metaId,
                    identificador: identificador,
                    estatus: estatus,
                    tipoTransferencia: tipoTransferencia,
                    autorizacion: autorizacion
                },
                success: function(response) {
                    $('#mensaje').hide();
                    var res = trim(response.replace("<!DOCTYPE html>", ""));
                    if (res === '-2') {
                        alert("No puede crear metas debido a que la meta seleccionada fue inhabilitada");
                    } else {
                        if (res !== "-1") {
                            $("#PopUpZone").html(res);
                            fadeInPopUp("PopUpZone");
                            fadeOutPopUp("estimacionMeta");
                            fadeInPopUp("infoAccion");
                        } else {
                            alert("Ocurri\u00f3 un error al procesar la solicitud");
                        }
                    }
                }
            });
        } else {
            alert("Debe de seleccionar una meta para continuar");
        }
    } else {
        alert("No es posible crear acciones si el movimiento está en revisi\u00f3n");
    }
}

function getInfoAccionTransferenciaTabla(identificador, estatus, ramoId, metaId, accionId, tipoTransferencia) {

    if (metaId !== '-1') {

        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        var autorizacion = $("#autorizacion").val();
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoTransferenciaAccion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                identificador: identificador,
                estatus: estatus,
                tipoTransferencia: tipoTransferencia,
                autorizacion: autorizacion
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== "-1") {
                    $("#PopUpZone").html(res);
                    fadeInPopUp("PopUpZone");
                    fadeOutPopUp("estimacionMeta");
                    fadeInPopUp("infoAccion");
                } else {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                }
            }
        });
    } else {
        alert("Debe de seleccionar una meta para continuar");
    }
}

function getAutorizaMovTransferencia() {
    var folio = $("#inp-folio-usr").val();
    var tipoOficio = $("#tipoOficio").val();
    var estatus = $("#estatus").val();
    var tipoFlujo = $("#tipoFlujo").val();
    var comentario = $("#comentario").val();
    var bFechaContabilidad = $("#fechaContabilidad").val();
    guardarTransferencia(true);
    if (folio.length > 0 && confirm("El movimiento ser\u00e1 autorizado")) {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxAutorizacion.jsp",
            dataType: 'html',
            data: {
                folio: folio,
                tipoOficio: tipoOficio,
                estatus: estatus,
                tipoFlujo: tipoFlujo,
                bFechaContabilidad: bFechaContabilidad,
                opcion: 11,
                comentario: comentario
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-add-meta").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-add-accion").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
            },
            success: function(res) {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                if (trim(res) == '1') {
                    alert("El movimiento fue autorizado");
                    regresarAutorizacion();
                } else {
                    res = res.split("|");
                    alert(res[1]);
                }
            },
            error: function() {
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-add-meta").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-add-accion").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
            }
        });

        $('#mensaje').hide();
    }

}
function getMovimientoAutorizado() {
    var ramo = $("#selRamo").val();
    var opcion = 2;

    if (ramo.length > 0 && ramo.length > 0) {

        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxGetMovimientos.jsp",
            dataType: 'html',
            data: {
                ramo: ramo,
                opcion: opcion
            },
            success: function(respoce) {
                $('#mensaje').hide();
                $("#div-movimientos").html(respoce);
            }
        });
    } else {
        alert("El ramo es un par\u00e1metro requerido");
        return false;
    }
}

function cancelarTransferencia() {
    var transferenciaId = $("#transferenciaId").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var idCaratula = $("#idCaratula").val();
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxRegresarTransferencias.jsp",
        dataType: 'html',
        data: {
            opcion: 2,
            transferenciaId: transferenciaId,
            idCaratula: idCaratula
        },
        success: function(respoce) {
            respoce = respoce.trim();
            $('#mensaje').hide();
            if (respoce !== "-1") {
                var res = respoce.split("|");
                if (res[0] === "1") {
                    $("#form-trans-rec").submit();
                } else if (res[0] === "2") {
                    alert(res[1]);
                }
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }
        }
    });
}

function salvarTransferencia() {
    var estatus = $("#estatus").val();
    var ramo = $("#ramo").val();
    var programa = $("#programa").val();
    var proyecto = $("#proyecto").val();
    var tipoProy = $("#tipoProy").val();
    var meta = $("#meta").val();
    var accion = $("#accion").val();
    var partida = $("#partida").val();
    var relLaboral = $("#relLaboral").val();
    var importeTrans = $("#importeTrans").val();
    var fuente = $("#fuente").val();
    var transferenciaId = $("#transferenciaId").val();
    var mesActual = $("#mesActual").val();
    var idCaratula = $("#idCaratula").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxRegresarTransferencias.jsp",
        dataType: 'html',
        data: {
            ramo: ramo,
            programa: programa,
            proyecto: proyecto,
            tipoProy: tipoProy,
            meta: meta,
            accion: accion,
            partida: partida,
            fuente: fuente,
            relLaboral: relLaboral,
            transferenciaId: transferenciaId,
            importeTrans: importeTrans,
            mesActual: mesActual,
            opcion: 1,
            estatus: estatus,
            idCaratula: idCaratula
        },
        success: function(respoce) {
            respoce = respoce.trim();
            $('#mensaje').hide();
            if (respoce !== "-1") {
                var res = respoce.split("|");
                if (res[0] === "1") {
                    $("#form-trans-rec").submit();
                } else if (res[0] === "2") {
                    alert(res[1]);
                }
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }
        }
    });
}

function guardarTransferencia(isAutorizando) {
    var selCaratula = $("#selCaratula").val();
    var fechaContable = $("#fechaContabilidad").val();
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#inpAutorizacion").val();
    var estatus = $("#inpEstatus").val();
    var justificacion = $("#txt-area-justif").val();
    justificacion = justificacion.toUpperCase();
    var comentarioPlan = $("#txt-area-coment-plan").val();
    if (typeof comentarioPlan != 'undefined')
        comentarioPlan = comentarioPlan.toUpperCase();
    var checkImpacto;
    var folio = $("#inp-folio-usr").val();
    var dateFor = $("#dateFor").val();
    var isActual = $("#isActual").val();
    var cont = 0;
    var tipoOficio = $("#tipoOficio").val();
    var msj = "";
    if ($("#chk-imp-prog").is(":checked")) {
        checkImpacto = true;
    } else {
        checkImpacto = false;
    }
    if (justificacion === "") {
        cont++;
        $("#txt-area-justif").addClass("errorClass");
    } else {
        $("#txt-area-justif").removeClass("errorClass");
    }
    /*
     if(typeof comentarioPlan != 'undefined'){
     if (comentarioPlan === "") {
     cont++;
     $("#txt-area-coment-plan").addClass("errorClass");
     } else {
     $("#txt-area-coment-plan").removeClass("errorClass");
     }
     }
     */
    if (cont > 0) {
        alert("Favor de completar los campos marcados");
        $("#errorGrabar").val(0);
        $('#mensaje').hide();
        return;
    } else {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxSaveMovtoTransferencia.jsp',
            datatype: 'html',
            async: false,
            data: {
                autorizacion: autorizacion,
                estatus: estatus,
                tipoMov: 'A',
                justificacion: justificacion,
                checkImpacto: checkImpacto,
                folio: folio,
                dateFor: dateFor,
                isActual: isActual,
                fechaContable: fechaContable,
                selCaratula: selCaratula,
                tipoOficio: tipoOficio,
                comentarioPlan: comentarioPlan
            },
            beforeSend: function() {
                $("#btn-save-movs").attr("disabled", "disabled");
                $("#btnAceptar").attr("disabled", "disabled");
                $("#btnRechazar").attr("disabled", "disabled");
                $("#btnCancelar").attr("disabled", "disabled");
                $("#btnEnviarAutorizar").attr("disabled", "disabled");
                $("#btnRegresar").attr("disabled", "disabled");
                $("#btnInicio").attr("disabled", "disabled");
                $("#btn-edit-meta").attr("disabled", "disabled");
                $("#btn-edit-accion").attr("disabled", "disabled");
                $("#btn-tranferir").attr("disabled", "disabled");
            },
            success: function(response) {
                $('#mensaje').hide();
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $("#btn-tranferir").removeAttr("disabled");
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    res = res.split("|");
                    if (res[0] === "1") {
                        $("#inp-folio-usr").val(res[2]);
                        $(".btn-enviar").show();
                        $(".btn-cancelar").show();
                        if (res[3] === 'N') {
                            alert("Se gener\u00f3 un tipo de oficio nuevo.\n Ser\u00e1 redirigido a la b\u00fasqueda de solicitudes.");
                            $("#folio").val(folio);
                            $("#opcion").val(0);
                            $("#tipoMov").val('T');
                            regresarAutorizacion();
                        }
                    }
                    msj = res[1];
                    if (!isAutorizando) {
                        if (msj.indexOf("folio") > -1) {
                            $("#estatus").val("X");
                            $("#inp-folio-rel-usr").val(folio);
                            $("#tr-folio-rel").show();
                        }
                        alert(msj);
                    } else {
                        if (res[0] !== "1") {
                            alert(msj);
                            $("#errorGrabar").val(0);
                            return false;
                        }
                        $("#errorGrabar").val(1);
                        return true;
                    }
                } else {
                    alert("Ocurri\u00f3 un error no esperado al guardar el folio");
                    if (isAutorizando) {
                        $("#errorGrabar").val(0);
                        return false;
                    }
                }
            },
            error: function() {
                $('#mensaje').hide();
                $("#btn-save-movs").removeAttr("disabled");
                $("#btnAceptar").removeAttr("disabled");
                $("#btnRechazar").removeAttr("disabled");
                $("#btnCancelar").removeAttr("disabled");
                $("#btnEnviarAutorizar").removeAttr("disabled");
                $("#btnRegresar").removeAttr("disabled");
                $("#btnInicio").removeAttr("disabled");
                $("#btn-edit-meta").removeAttr("disabled");
                $("#btn-edit-accion").removeAttr("disabled");
                $("#btn-tranferir").removeAttr("disabled");
            }
        });
    }
}

function cancelarMovimiento(tipoMovimiento, url) {
    var folio = $("#inp-folio-usr").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxCancelarMovimiento.jsp',
        datatype: 'html',
        data: {
            folio: folio,
            tipoMovimiento: tipoMovimiento
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = response.trim();
            if (res !== '-1') {
                res = res.split("|");
                if (res[0] === "1") {
                    alert("El movimiento fue cancelado exitosamente")
                    window.location = url + ".jsp";
                }
                res = res[1];
                alert(res);
            } else {
                alert("Ocurri\u00f3 un error no esperado");
            }
        }
    });
}

function getTablaListadoCaratulas() {

    var ramoId = $("#selRamo").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaListadoCaratulas.jsp',
        datatype: 'html',
        async: false,
        data: {
            ramoId: ramoId
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#TableZone tbody").html(res);
            }
        }
    });
}

function evaluarCaratulaSelected() {

    var selCaratula = $("#selCaratula").val();

    if (selCaratula == -1) {

        $("#fechaRevision").val("");
        $("#numeroSesion").val("");
        $("#fechaRegistro").val("");
        $("#tipoSesion").val("");
        fadeOutPopUp("disabledCaratula");

    } else {

        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxActualizaInfoCaratula.jsp",
            dataType: 'html',
            async: false,
            data: {
                selCaratula: selCaratula
            },
            success: function(response) {

                var res = trim(response.replace("<!DOCTYPE html>", ""));

                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $("#infoCaratula").html(res);
                    $("#infoCaratula").show();
                }

            }
        });

        fadeInPopUp("disabledCaratula");

    }

}


function NumCheck(e, field) {
    /*key = e.keyCode ? e.keyCode : e.which
     // backspace
     if (key == 8 || key == 9) return true
     // 0-9
     if (key > 47 && key < 58) {
     if (field.value == "") return true
     regexp = /.[0-9]{0-9}$/
     return !(regexp.test(field.value))
     }
     // .
     if (key == 46) {
     if (field.value == "") return false
     regexp = /^[0-9]+$/
     return regexp.test(field.value)
     }
     // other key
     return false
     */
}

function OcultaObsrvacionAvancePoa() {
    //var observ=$("#"+id).val();
    $("#Observa").html();
    $("#Observa").hide();


//alert(observ); 

}


function MuestraObsrvacionAvancePoa(id, periodo, valida, periodoPOA) {
    var observ = $("#" + id).val();
    var strcad = "";


    strcad = "<br/><b>Observaci&oacute;n:</b><br/>"
    strcad += "<textarea id='txtAreaObs' style='text-transform:uppercase' onKeyPress='return validarSaltosLineaAvancePoa(event)'>" + observ + "</textarea>";
    if (valida == "S" && ((periodo == 3 && periodoPOA != 3) || (periodo == 6 && periodoPOA != 6) || (periodo == 9 && periodoPOA != 9) || (periodo == 12 && periodoPOA != 12))) {
        strcad += ""
    } else {
        strcad += "<br/><br/><center><input type='button' value='Guardar' onClick='asignarObserAvancePoa(\"" + id + "\");'>"
    }
    strcad += "\n\n<input type='button' value='Cerrar' onClick='OcultaObsrvacionAvancePoa();'></center><br/>"
    $("#Observa").html(strcad);
    $("#Observa").show();


//alert(observ); 

}

function asignarObserAvancePoa(id) {
    $("#" + id).val($("#txtAreaObs").val().toUpperCase());
    $("#txtAreaObs").val("");
    $("#Observa").html();
    $("#Observa").hide();
}

function validarFlotanteAvancePoaAcciones(valor) {
    var total = 0;
    var input;
    var tipoC = $("#calc").val();
    var cont = 12
    var campo = "";

    if (valor == "")
        valor = 0;
    if (tipoC == "AC") {
        for (it = 1; it <= cont; it++) {
            campo = "realizado" + it;
            if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                total += parseFloat($("#" + campo).val().replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 1; it <= cont; it++) {
                campo = "realizado" + it;
                if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                    if (total < parseFloat($("#" + campo).val().replaceAll(",", ""))) {
                        total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    }
                }
            }
        } else if (tipoC == "MI") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 1; it <= cont; it++) {
                campo = "realizado" + it;
                if ($("#" + campo).val() != "" && $("#" + campo).val() != undefined && $("#" + campo).val() != NaN) {
                    if (total > parseFloat($("#" + campo).val().replaceAll(",", ""))) {
                        total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    } else {
                        if (total == 0)
                            total = parseFloat($("#" + campo).val().replaceAll(",", ""));
                    }
                }
            }
        }
    }
    if (total !== 0)
        $("#totalRealizado").val(addCommas(total.toFixed(2)));
}

function verificaCambioContrasena() {

    var d = document.forms[0];
    var bPassVencido = d.bPassVencido;

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    if (d.strContrasenaAct.value == "") {
        $.msgBox({
            title: "Aviso",
            content: "Debe introducir contrase\u00f1a actual",
            type: "info"
        });
        $('#mensaje').hide();
        return "";
    }

    if (d.strContrasenaNva.value == "") {
        $.msgBox({
            title: "Aviso",
            content: "Debe introducir contrase\u00f1a nueva",
            type: "info"
        });
        $('#mensaje').hide();
        return "";
    }

    if (d.strContrasenaConf.value == "") {
        $.msgBox({
            title: "Aviso",
            content: "Debe Introducir la Confirmaci\u00f3n de la Contrase\u00f1a Nueva",
            type: "info"
        });
        $('#mensaje').hide();
        return "";
    }

    if (d.strContrasenaNva.value != d.strContrasenaConf.value) {
        $.msgBox({
            title: "Aviso",
            content: "La confirmaci\u00f3n de la contrase\u00f1a debe coincidir con la Contrase\u00f1a Nueva",
            type: "info"
        });
        $('#mensaje').hide();
        return "";
    }

    $.ajax({
        type: "POST",
        url: "librerias/ajax/ajaxCambiaContrasenaAct.jsp",
        dataType: "html",
        data: {
            strContrasenaAct: d.strContrasenaAct.value,
            strContrasenaNva: d.strContrasenaNva.value
        },
        success: function(response) {
            $('#mensaje').hide();
            d.strContrasenaAct.value = "";
            d.strContrasenaNva.value = "";
            d.strContrasenaConf.value = "";
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res == "-2") {
                $.msgBox({
                    title: "Aviso",
                    content: "La contrase\u00f1a actual introduccida es erronea. ",
                    type: "info"
                });
            }
            if (res == "-3") {
                $.msgBox({
                    title: "Aviso",
                    content: "No se logro modificar la contrase\u00f1a. ",
                    type: "info"
                });
            }
            if (res == "-1") {
                $.msgBox({
                    title: "Aviso",
                    content: "Error al intentar modificar la contrase\u00f1a. ",
                    type: "info"
                });
            }
            if (res == "1") {
                $.msgBox({
                    title: "Aviso",
                    content: "Contrase\u00f1a cambiada exitosamente",
                    type: "Info",
                    buttons: [{value: "Aceptar"}],
                    success: function(result) {
                        if (result == "Aceptar") {
                            veSeleccionAno();
                        }
                    }
                });
            }
        }
    });

    return "";
}

function veSeleccionAno() {
    window.location.href = "seleccionaAnio.jsp";
}

function getInfoEdicionTransferencia(ramo, programa, proy, meta, accion, partida, fuenteFin, relLaboral, transferenciaId) {
    $("#edRamo").val(ramo);
    $("#edPrg").val(programa);
    $("#edProy").val(proy);
    $("#edMeta").val(meta);
    $("#edAccion").val(accion);
    $("#edPartida").val(partida);
    $("#edRelLaboral").val(relLaboral);
    $("#edFuente").val(fuenteFin);
    $("#transferenciaId").val(transferenciaId);
    $("#edicionTrans").val(1);
    $("#justificacionTrans").val($("#txt-area-justif").val());
    $("#form-transferencia #folio").val($("#inp-folio-usr").val());
    $("#idCaratula").val($("#selCaratula").val());
    $("#form-transferencia").submit();
}

function consultaCaratulaByFechaSessionNumeroSession(caratula, ramoId) {

    $("#caratula").val(caratula);
    $("#ramoId").val(ramoId);
    $("#frmConsulta").submit();

}

function regresarTransferencia() {
    var cambioMovto = $("#cambio-movto").val();
    var cambioTrans = $("#cambio-transferencia").val();
    if (cambioMovto || cambioTrans) {
        if (confirm("Lo movimientos que no se han aceptado no ser\u00e1n guardados. ¿Desea continuar?")) {
            window.history.back();
        } else {
            return false;
        }
    } else {
        window.history.back();
    }
}
//.removeAttr("disabled")
function cambioBusqueda() {
    if ($("#radioFolio").is(':checked')) {
        $("#inpt-folio").removeAttr("disabled");
        $("#inpt-folio").val("");
        $("#btnFolio").removeAttr("disabled");
        $("#selTipoSolicitud").val(-1);
        $("#selTipoSolicitud").attr("disabled", "disabled");
        $("#selEstatusMovimiento").val(-1);
        $("#selEstatusMovimiento").attr("disabled", "disabled");
        $("#opcRamo").val(-1);
        $("#opcRamo").attr("disabled", "disabled");
        getTablaSolicitudesMovimientosByFolio();
    } else if ($("#radioCompleto").is(':checked')) {
        $("#selTipoSolicitud").removeAttr("disabled");
        $("#selEstatusMovimiento").removeAttr("disabled");
        $("#inpt-folio").val("");
        $("#inpt-folio").attr("disabled", "disabled");
        $("#btnFolio").attr("disabled", "disabled");
        $("#opcRamo").val(-1);
        $("#opcRamo").attr("disabled", "disabled");
        $("#inpt-folio").val("");
        getTablaSolicitudesMovimientos();
    } else if ($("#radioRamo").is(':checked')) {
        $("#inpt-folio").attr("disabled", "disabled");
        $("#btnFolio").attr("disabled", "disabled");
        $("#selTipoSolicitud").val(-1);
        $("#selTipoSolicitud").attr("disabled", "disabled");
        $("#selEstatusMovimiento").val(-1);
        $("#selEstatusMovimiento").attr("disabled", "disabled");
        $("#opcRamo").removeAttr("disabled");
        $("#inpt-folio").val("");
        getTablaSolicitudesMovimientosByRamo();
    }
    $("#tbl-movs tbody").html("");
}

function calculaPropuesta() {

    var total = 0;
    var max = 0;
    var min = 0;
    var input = $(".calenVistaRC .estimacion");
    var calculoAct = $("#selCalculoRP").val();

    for (var it = 0; it < input.length; it++) {
        if (it == 0)
        {
            max = parseFloat(input[it].value.replaceAll(",", ""));
            min = parseFloat(input[it].value.replaceAll(",", ""));
        }

        if (parseFloat(input[it].value.replaceAll(",", "")) != 0) {
            if (min > parseFloat(input[it].value.replaceAll(",", ""))) {
                min = parseFloat(input[it].value.replaceAll(",", ""));
            } else {
                if (min == 0)
                    min = parseFloat(input[it].value.replaceAll(",", ""));
            }
        }

        if (max < parseFloat(input[it].value.replaceAll(",", "")))
            max = parseFloat(input[it].value.replaceAll(",", ""));

        total += parseFloat(input[it].value.replaceAll(",", ""));
    }

    if (calculoAct == "MA") {
        $("#calPropuesta").val(addCommas(max));
    }

    if (calculoAct == "MI") {
        $("#calPropuesta").val(addCommas(min));
    }

    if (calculoAct == "AC") {
        $("#calPropuesta").val(addCommas(total));
    }

}

function evaluarPropuesta(valor, tipoC) {
    var total = 0;
    var input;
    input = $(".calenVistaRC .estimacion");
    if (valor == "")
        valor = 0;
    if (tipoC == "AC") {
        for (it = 0; it < input.length; it++) {
            if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                total += parseFloat(input[it].value.replaceAll(",", ""));
            }
        }
    } else {
        if (tipoC == "MA") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {
                    if (total < parseFloat(input[it].value.replaceAll(",", ""))) {
                        total = parseFloat(input[it].value.replaceAll(",", ""));
                    }
                }
            }
        } else if (tipoC == "MI") {
            total = parseFloat(valor.replaceAll(",", ""));
            for (it = 0; it < input.length; it++) {
                if (input[it].value != "" && input[it].value != undefined && input[it].value != NaN) {

                    if (parseFloat(input[it].value.replaceAll(",", "")) != 0) {
                        if (total > parseFloat(input[it].value.replaceAll(",", ""))) {
                            total = parseFloat(input[it].value.replaceAll(",", ""));
                        } else {
                            if (total == 0)
                                total = parseFloat(input[it].value.replaceAll(",", ""));
                        }
                    }

                }
            }
        }
    }
    if (total !== 0)
        $("#calPropuesta").val(addCommas(total.toFixed(2)));
}

function getProgramasOnChangeAvancePoa() {
    var ramoId = $("#selRamo").val();
    var divInfoPlantilla = $('#divInfoPlantilla').val();
    if (typeof divInfoPlantilla != 'undefined') {
        $('#divInfoPlantilla').html("<input id='contadorGrupos' type='hidden' value='0'/> <input id='contadorRegistros' type='hidden' value='0' />");
    }
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetProgramasByRamoAvancePoa.jsp',
        datatype: 'html',
        async: false,
        data: {
            ramo: ramoId
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                if (trim(response.replace("<!DOCTYPE html>", "")) != -2)
                    $("#selPrograma").html(response);
                else {
                    alert("El ramo seleccionado no est\u00e1 disponible para la captura del avance");
                    $("#selPrograma").html("<option value='-1'> -- Seleccione un programa -- </option>");
                }
            }
            $('#mensaje').hide();

        }
    });
    $("#tabContent").html("");
    $("#tabContainer").hide();
    $("#divInfoProyecto").html("");
    $("#selProyecto").html("<option value='-1'> -- Seleccione un proyecto -- </option>");
    $("#selDepartamento").html("<option value='-1'> -- Seleccione un departamento -- </option>");
    $("#div-listado").html("");
    $("#control-metas").hide();
}

function operarRecalendarizacion(valorNuevo, tipoC, idMesEstimacion, valorViejo, tipoValidacion, numMes, nomMes) {

    var ramoId = $("#ramoId").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();

    if (valorNuevo != "" && valorNuevo != undefined && valorNuevo != NaN && valorNuevo != null) {
        valorNuevo = parseFloat(valorNuevo.replaceAll(",", ""));
    } else {
        valorNuevo = 0;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxValidarAvanceEstimacion.jsp',
        datatype: 'html',
        data: {
            tipoValidacion: tipoValidacion,
            ramoId: ramoId,
            metaId: metaId,
            accionId: accionId,
            numMes: numMes,
            valorNuevo: valorNuevo
        },
        success: function(response) {

            $('#mensaje').hide();

            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res == "correcto") {
                validarFlotanteNewRecalendarizacionAccion("" + valorNuevo, tipoC);
                sumatoriaRecalendarizacion();
                evaluarPropuesta(valorNuevo, tipoC);
                agregarFormato(idMesEstimacion);
            } else {
                $("#" + idMesEstimacion).val(valorViejo);
                alert("La cantidad programada del mes de " + nomMes + " no puede ser menor a la cantidad registrada como avance, que es " + res + "");
            }
        },
        error: function(response) {
            $('#mensaje').hide();
            alert(response);
        }
    });

}

function operarReprogramacion(valorNuevo, tipoC, idMesEstimacion, valorViejo, tipoValidacion, numMes, nomMes) {

    var ramoId = $("#ramoId").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();

    if (valorNuevo != "" && valorNuevo != undefined && valorNuevo != NaN && valorNuevo != null) {
        valorNuevo = parseFloat(valorNuevo.replaceAll(",", ""));
    } else {
        valorNuevo = 0;
    }
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxValidarAvanceEstimacion.jsp',
        datatype: 'html',
        data: {
            tipoValidacion: tipoValidacion,
            ramoId: ramoId,
            metaId: metaId,
            accionId: accionId,
            numMes: numMes,
            valorNuevo: valorNuevo
        },
        success: function(response) {

            $('#mensaje').hide();

            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res == "correcto") {
                sumatoriaRecalendarizacion();
                calculaPropuesta();
                agregarFormato(idMesEstimacion);
            } else {
                $("#" + idMesEstimacion).val(valorViejo);
                alert("La cantidad programada del mes de " + nomMes + " no puede ser menor a la cantidad registrada como avance, que es " + res + "");
            }
        },
        error: function(response) {
            $('#mensaje').hide();
            alert(response);
        }
    });

}

function cargarDatosProgramaMetaAvancePoa(ramo) {
    $("#selRamo").val(ramo);
    getProgramasOnChangeAvancePoa();
}

function cargarMetasAvancePoa1(proyecto) {
    $("#selProyecto").val(proyecto);
    getMetasAvancePoaOnChange();
}

function prepararReporteMovtos(rptNombre, rptDir) {
    $("#rptPath").val(rptDir);
    $("#filename").val(rptNombre);

}

function imprimirReporteTransAmp(iOficio, vTipo, vTipoImpresion, vTipoMovimiento, vEstatus) {
    //window.location.href = "ejecutaReporte/ejecutarReporte.jsp?oficio=" + iOficio + "&tipoOficio=" + vTipo + "&reporttype=" + vTipoImpresion + "&tipoReporteAmpTran=" + vtipoReporte + "&tipoMovimiento=" + vTipoMovimiento;
    $("#tipoMovimiento").val(vTipo);
    $("#reporttype").val(vTipoImpresion);
    $("#oficio").val(iOficio);
    $("#tipoOficio").val(vTipoMovimiento);
    $("#estatusMovimientoId").val(vEstatus);
    if (document.getElementById("chkCodigoCompleto").checked == true) {
        $("#codCompleto").val("S");
    } else {
        $("#codCompleto").val("N");
    }
    $("#frmConsulta").submit();
}

function getTablaSolicitudesMovimientosReporte() {
    var tipoSolicitudId = $("#selTipoSolicitud").val();
    var rdGeneral = $('input[name=rdGeneral]:checked').val();

    switch (tipoSolicitudId) {
        case 'A':
            document.getElementById("rdBtnAmpliacion").checked = true;
            $("#rdBtnAmpliacion").change();
            $("#divRptGral").show();
            if (document.getElementById("rdGeneralDet").checked) {
                $("#divSelCodigo").show();
            } else {
                $("#divSelCodigo").hide();
            }

            break;
        case 'C':
            document.getElementById("rdBtnRecal").checked = true
            $("#rdBtnRecal").change();
            $("#divRptGral").hide();
            $("#divSelCodigo").hide();
            rdGeneral = 3;
            break;
        case 'R':
            document.getElementById("rdBtnRepro").checked = true;
            $("#rdBtnRepro").change();
            $("#divRptGral").show();
            $("#divSelCodigo").hide();
            break;
        case 'T':
            document.getElementById("rdBtnTransferencia").checked = true;
            $("#divSelcodigo").show();
            $("#rdBtnTransferencia").change();
            if (document.getElementById("rdGeneralDet").checked) {
                $("#divSelCodigo").show();
            } else {
                $("#divSelCodigo").hide();
            }

            break;
    }


    var estatusMovimientoId = $("#selEstatusMovimiento").val();


    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaSolicitudesMovimientosReporte.jsp',
        datatype: 'html',
        data: {
            tipoSolicitudId: tipoSolicitudId,
            estatusMovimientoId: estatusMovimientoId,
            rdGeneral: rdGeneral
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                $("#TableZone tbody").html(res);
            }
        }
    });
}

function getTablaReporteSolicitudesMovimientosByFolio() {

    var folio = $("#inpt-folio").val();
    var nuevo = $("#nuevo").val();

    $("#divRptGral").show();
    if (document.getElementById("rdGeneralDet").checked) {
        $("#divSelCodigo").show();
    } else {
        $("#divSelCodigo").hide();
    }

    if (nuevo !== '0') {
        $("#nuevo").val('1');
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizaTablaSolicitudesMovimientosReporteByFolio.jsp',
            datatype: 'html',
            data: {
                folio: folio,
                opcion: 1
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    if (res === '1') {
                        $("#TableZone tbody").html("");
                        alert("No se encontr\u00f3 el folio solicitado");
                    } else {
                        $("#TableZone tbody").html(res);
                    }
                } else {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                }
            }
        });
    } else {
        $("#nuevo").val('1');
    }
}

function imprimirReporteTransAmpByOficio(iOficio, vTipoMovimiento, vTipoImpresion, vTipoOficio, vEstatus) {

    $("#tipoMovimiento").val(vTipoMovimiento);
    $("#reporttype").val(vTipoImpresion);
    $("#oficio").val(iOficio);
    $("#tipoOficio").val(vTipoOficio);
    $("#estatusMovimientoId").val(vEstatus);
    var continuar = true;
    var mensaje = "";

    switch (vTipoMovimiento) {
        case 'A':
            document.getElementById("rdBtnAmpliacion").checked = true;
            $("#rdBtnAmpliacion").change();
            prepararReporteMovtosByOficio('', 'repAmpTrans', vTipoMovimiento);
            break;
        case 'C':
            document.getElementById("rdBtnRecal").checked = true
            $("#rdBtnRecal").change();
            $("#rptPath").val("RecalendarizacionFormatoRevision");
            $("#filename").val("rptRecalendarizacionFormatoRevision.jasper");
            break;
        case 'R':
            document.getElementById("rdBtnRepro").checked = true;
            $("#rdBtnRepro").change();
            $("#rptPath").val("reprogramado");
            $("#filename").val("rptOficialReprogramacion.jasper");
            break;
        case 'T':
            document.getElementById("rdBtnTransferencia").checked = true;
            $("#rdBtnTransferencia").change();
            prepararReporteMovtosByOficio('', 'repAmpTrans', vTipoMovimiento);
            break;
        case 'W':
            document.getElementById("rdBtnRepro").checked = true;
            $("#rdBtnRepro").change();
            $("#rptPath").val("repAmpTrans");
            $("#filename").val("rptPptoXDetalleRequerimientos.jasper");
            break;
    }

    if (document.getElementById("chkCodigoCompleto").checked == true) {
        $("#codCompleto").val("S");
    } else {
        $("#codCompleto").val("N");

        if ($("#rdBtnAmpliacion").is(":checked") || $("#rdBtnTransferencia").is(":checked")) {

            if ($("#rdBtnAmpliacion").is(":checked")) {
                if (!($("#rdGeneralCon").is(":checked"))) {
                    $("#filename").val("rptAmpliacionesDetalladoCC.jasper");
                }
            } else if ($("#rdBtnTransferencia").is(":checked")) {
                if (!($("#rdGeneralCon").is(":checked"))) {
                    $("#filename").val("rptTransferenciasDetalladoCC.jasper");
                }
            }
        }
    }
    
    var fileNameVal = $("#filename").val();
    
    if( fileNameVal == "rptAmpliacionesConcentrado.jasper" || fileNameVal == "rptTransferenciaConcentrado.jasper" ){
        if(vTipoOficio == "" || vTipoOficio == null || vTipoOficio == "null")
        {
            continuar = false;
            mensaje = "No es posible generar el reporte, el oficio no tiene definido el tipo oficio.";
        }
    } else {
        continuar = true;
        mensaje = "";
    }

    if(continuar){
        $("#frmConsulta").submit();
    } else {
        alert(mensaje);
    }
}

function prepararReporteMovtosByOficio(rptNombre, rptDir, tipoSolicitudId) {
    $("#rptPath").val(rptDir);

    if (tipoSolicitudId == 'A' || tipoSolicitudId == 'T') {
        if (document.getElementById("rdGeneralDet").checked) {
            $("#divSelCodigo").show();
        } else {
            $("#divSelCodigo").hide();
        }
    } else {
        $("#divSelcodigo").hide();
    }

    if ($("#rdBtnAmpliacion").is(":checked") || $("#rdBtnTransferencia").is(":checked")) {
        $("#divRptGral").show();
        /*$(".btn-Excel").removeAttr("disabled");*/

        if ($("#rdBtnAmpliacion").is(":checked")) {
            if ($("#rdGeneralCon").is(":checked")) {
                $("#filename").val("rptAmpliacionesConcentrado.jasper");
            } else {
                $("#filename").val("rptAmpliacionesDetallado.jasper");
            }
        } else if ($("#rdBtnTransferencia").is(":checked")) {
            if ($("#rdGeneralCon").is(":checked")) {
                $("#filename").val("rptTransferenciaConcentrado.jasper");
            } else {
                $("#filename").val("rptTransferenciasDetallado.jasper");
            }
        }
    } else {
        $("#divRptGral").hide();
        $("#filename").val(rptNombre);
        if ($("#rdBtnRepro").is(":checked")) {
            $(".btn-Excel").attr("disabled", "disabled");
            $("#banderaPage").val("true");
        } else if ($("#rdBtnRecal").is(":checked")) {
            $(".btn-Excel").attr("disabled", "disabled");
        }
    }
}

function cambiaRadiosReporteMovtos(rptNombre, rptDir) {

    if (document.getElementById("rdGeneralDet").checked) {
        $("#divSelCodigo").show();
    } else {
        $("#divSelCodigo").hide();
    }

}

function getTablaReporteSolicitudesMovimientosByCaratula() {

    var caratula = $("#selCaratula").val();
    var nuevo = $("#nuevo").val();

    $("#divRptGral").show();
    if (document.getElementById("rdGeneralDet").checked) {
        $("#divSelCodigo").show();
    } else {
        $("#divSelCodigo").hide();
    }
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    if (nuevo !== '0') {
        $("#nuevo").val('1');
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizaTablaSolicitudesMovimientosReporteByCaratula.jsp',
            datatype: 'html',
            data: {
                caratula: caratula,
                opcion: 1
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res !== '-1') {
                    if (res === '1') {
                        $("#TableZone tbody").html("");
                        alert("No se encontr\u00f3 el folio solicitado");
                    } else {
                        $("#TableZone tbody").html(res);
                    }
                } else {
                    alert("Ocurri\u00f3 un erro al procesar la solicitud");
                }
            }
        });
    } else {
        $("#nuevo").val('1');
    }

}

function disminuirPeriodoIniMonitoreo() {
    var periodoIni = parseInt($("#periodoIni").val());
    if (periodoIni != 1) {
        periodoIni--;
        $("#periodoIniV").val(periodoIni);
        $("#periodoIni").val(periodoIni);
    } else {
        return(false);
    }
}

function aumentarPeriodoIniMonitoreo() {
    var periodoIni = parseInt($("#periodoIni").val());
    var periodoFin = parseInt($("#periodoFin").val());

    if (periodoFin < (periodoIni + 1)) {
        alert("El periodo inicial debe de ser menor que el periodo final");
        return(false);
    } else {
        if (periodoIni != 12) {
            periodoIni++;
            $("#periodoIniV").val(periodoIni);
            $("#periodoIni").val(periodoIni);
        } else {
            return(false);
        }
    }
}

function aumentarPeriodoFinMonitoreo() {
    var periodoFin = parseInt($("#periodoFin").val());
    if (periodoFin != 12) {
        periodoFin++;
        $("#periodoFinV").val(periodoFin);
        $("#periodoFin").val(periodoFin);
    } else {
        return(false);
    }
}

function disminuirPeriodoFinMonitoreo() {
    var periodoIni = parseInt($("#periodoIni").val());
    var periodoFin = parseInt($("#periodoFin").val());

    if (periodoIni > (periodoFin - 1)) {
        alert("El periodo final debe de ser mayor que el periodo inicial");
        return(false);
    } else {
        if (periodoFin != 1) {
            periodoFin--;
            $("#periodoFinV").val(periodoFin);
            $("#periodoFin").val(periodoFin);
        } else {
            return(false);
        }
    }
}

function getDepartamentosByRamoProgramaMonitoreo() {

    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var i = 0;

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    var programaId = $("#selProgI").val();

    if (ramoSelect.indexOf(",") == -1) {
        if (programaId != -1) {
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetDepartamentosMonitoreoPresupuestal.jsp",
                dataType: 'html',
                data: {
                    ramo: ramoSelect,
                    programa: programaId
                },
                success: function(response) {
                    $('#mensaje').hide();
                    $(".selRangoDepartamento").html(response);
                }
            });
        } else {
            $(".selRangoDepartamento").html("<option value='-1'>-- Seleccione un departamento --</option>");
        }
    } else {
        $(".selRangoDepartamento").html("<option value='-1'>-- Seleccione un departamento --</option>");
    }

}

function getProyActividadByRamoProgramaMonitoreo() {

    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var i = 0;

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    var programaId = $("#selProgI").val();

    if (ramoSelect.indexOf(",") == -1) {
        if (programaId != -1) {
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetProyectosByPrograma.jsp",
                dataType: 'html',
                data: {
                    ramo: ramoSelect,
                    programa: programaId
                },
                success: function(response) {
                    $('#mensaje').hide();
                    $(".selRangoProyecto").html(response);
                }
            });
        } else {
            $(".selRangoProyecto").html("<option value='-1'>-- Seleccione un proyecto/actividad --</option>");
        }
    } else {
        $(".selRangoProyecto").html("<option value='-1'>-- Seleccione un proyecto/actividad --</option>");
    }

}

function getProyActividadByRamoProgramaReprogramacion() {

    var ramo = $("#selRamo").val();
    var programaId = $("#selProgI").val();

    if (ramo !== -1) {
        if (programaId !== -1) {
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: "librerias/ajax/ajaxGetProyectosByPrograma.jsp",
                dataType: 'html',
                data: {
                    ramo: ramo,
                    programa: programaId
                },
                success: function(response) {
                    $('#mensaje').hide();
                    $(".selRangoProyecto").html(response);
                }
            });
        } else {
            $(".selRangoProyecto").html("<option value='-1'>-- Seleccione un proyecto/actividad --</option>");
        }
    } else {
        $(".selRangoProyecto").html("<option value='-1'>-- Seleccione un proyecto/actividad --</option>");
    }

}

function validaProgramaMenorFinalMonitoreo() {
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    if (progF < progI && progF !== "-1") {
        alert("El programa inicial debe de ser menor que el programa final");
        $("#selProgI").val(-1);
    }
    if (progI == progF) {
        getDepartamentosByRamoProgramaMonitoreo();
    } else {
        $(".selRangoDepartamento").html("<option value='-1'>-- Seleccione un departamento --</option>");
    }
}

function validaProgramaMayorInicialMonitoreo() {
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    if (progI > progF && progI !== "-1") {
        alert("El programa inicial debe de ser menor que el programa final");
        $("#selProgF").val(-1);
    }

    if (progI == progF) {
        getDepartamentosByRamoProgramaMonitoreo();
    } else {
        $(".selRangoDepartamento").html("<option value='-1'>-- Seleccione un departamento --</option>");
    }
}

function validaProgramaMenorFinalMonitoreoReprogramacion() {
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    if (progF < progI && progF !== "-1") {
        alert("El programa inicial debe de ser menor que el programa final");
        $("#selProgI").val(-1);
    }
    if (progI == progF) {
        getProyActividadByRamoProgramaReprogramacion();
    } else {
        $(".selRangoProyecto").html("<option value='-1'>-- Seleccione un proy/act --</option>");
    }
}

function validaProgramaMayorInicialReprogramacion() {
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    if (progI > progF && progI !== "-1") {
        alert("El programa inicial debe de ser menor que el programa final");
        $("#selProgF").val(-1);
    }

    if (progI == progF) {
        getProyActividadByRamoProgramaReprogramacion();
    } else {
        $(".selRangoProyecto").html("<option value='-1'>-- Seleccione un proy/act --</option>");
    }
}

function validaDepartamentoMenorFinalMonitoreo() {
    var deptoI = $("#selDeptoI").val();
    var deptoF = $("#selDeptoF").val();
    if (deptoF < deptoI && deptoF !== "-1") {
        alert("El departamento inicial debe de ser menor que el departamento final");
        $("#selDeptoI").val(-1);
    }
}

function validaDepartamentoMayorInicialMonitoreo() {
    var deptoI = $("#selDeptoI").val();
    var deptoF = $("#selDeptoF").val();
    if (deptoI > deptoF && deptoI !== "-1") {
        alert("El departamento inicial debe de ser menor que el departamento final");
        $("#selDeptoF").val(-1);
    }
}

function validaPartidaMenorFinalMonitoreo() {
    var partidaI = $("#selPartidaI").val();
    var partidaF = $("#selPartidaF").val();
    if (partidaF < partidaI && partidaF !== "-1") {
        alert("La partida inicial debe de ser menor que final");
        $("#selPartidaI").val(-1);
    }
}

function validaPartidaMayorInicialMonitoreo() {
    var partidaI = $("#selPartidaI").val();
    var partidaF = $("#selPartidaF").val();
    if (partidaI > partidaF && partidaI !== "-1") {
        alert("La partida inicial debe de ser menor que final");
        $("#selPartidaF").val(-1);
    }
}

function validaProyectoMenorFinalMonitoreo() {
    var proyectoI = $("#selProActI").val();
    var proyectoF = $("#selProActF").val();
    if (proyectoF < proyectoI && proyectoF !== "-1") {
        alert("La partida inicial debe de ser menor que final");
        $("#selProActI").val(-1);
    }
}

function validaProyectoMayorInicialMonitoreo() {
    var proyectoI = $("#selProActI").val();
    var proyectoF = $("#selProActF").val();
    if (proyectoI > proyectoF && proyectoI !== "-1") {
        alert("La partida inicial debe de ser menor que final");
        $("#selProActF").val(-1);
    }
}

function generarReporteMonitoreoPresupuestalExcel() {
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var mensajeError = "";
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    var deptoI = $("#selDeptoI").val();
    var deptoF = $("#selDeptoF").val();
    var partidaI = $("#selPartidaI").val();
    var partidaF = $("#selPartidaF").val();
    var cont = $("#contRegSelect").val();
    var isConsultaxRangoFolios = $("#isConsultaxRangoFolios").val();
    var i = 0;
    var contReg1 = $("#contReg1").val();
    var folioSelect = "";
    var contFolioSelect = 0;

    $("#reporttype").val("xls");

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }

    if (isConsultaxRangoFolios == "S") {
        //tomar los folios seleccionados en caso de que tenga el filtro por rango de folios
        for (i = 1; i <= contReg1; i++) {
            var tempChecks1 = $("#checkk" + i).val();
            if ($("#checkk" + i).is(':checked')) {
                var tempFolioChecks = $("#folioCheck" + i).val();
                if (contFolioSelect == 0)
                    folioSelect = folioSelect + "'" + tempFolioChecks + "'";
                else
                    folioSelect = folioSelect + "," + "'" + tempFolioChecks + "'";
                contFolioSelect++;
            }
        }

        if (contFolioSelect > 0) {
            $("#folioInList").val(folioSelect);
        } else {
            alert("Debe de seleccionar al menos un folio para continuar");
            $("#folioInList").val("-1");
            return;
        }

    }

    /*******************************reportesConcentrado********************/
    if ($("#radioReporteRecalendarizacionesConsolidadoConcentrado").is(":checked")) {
        if (cont == 1 && (($("#radioReporteRecalendarizacionesConsolidadoConcentrado").is(":checked")) && !($("#R").is(":checked")))) {
            if ((progI == '-1' || progF == '-1') && isConsultaxRangoFolios == "N") {
                alert("Debe de seleccionar un programa inicial y final");
            } else {
                if (progI == progF && (($("#radioReporteRecalendarizacionesConsolidadoConcentrado").is(":checked")) && !($("#P").is(":checked")))) {
                    if (deptoI == '-1' || deptoF == '-1' && isConsultaxRangoFolios == "N") {
                        alert("Debe de seleccionar un departamento inicial y final");
                    } else {
                        if (deptoI === deptoF) {
                            if ((partidaI === '-1' || partidaF === '-1') && isConsultaxRangoFolios == "N") {
                                alert("Debe de seleccionar una partida inicial y partida final");
                            } else {
                                if (cont == 0) {
                                    alert("Debe de seleccionar al menos un ramo");
                                } else {
                                    $("#frmRptExcel").submit();
                                }
                            }
                        } else {
                            if (cont == 0) {
                                alert("Debe de seleccionar al menos un ramo");
                            } else {
                                $("#frmRptExcel").submit();
                            }
                        }
                    }
                } else {
                    if (cont == 0) {
                        alert("Debe de seleccionar al menos un ramo");
                    } else {
                        $("#frmRptExcel").submit();
                    }
                }
            }
        } else {
            if (cont == 0) {
                alert("Debe de seleccionar al menos un ramo");
            } else {
                $("#frmRptExcel").submit();
            }
        }
    } else {
        /********************************************************/
        if (isConsultaxRangoFolios == "S") {
            //alert("Debe de seleccionar un departamento inicial y final");
            if ($("#folioIni").val() == "") {
                alert("Debe de ingresar un folio inicial ");
                return
            } else if ($("#folioFin").val() == "") {
                alert("Debe de ingresar un folio final");
                return
            }

        }
        if (cont == 1) {
            if ((progI == '-1' || progF == '-1') && isConsultaxRangoFolios == "N") {
                alert("Debe de seleccionar un programa inicial y final");
            } else {
                if (progI == progF) {
                    if ((deptoI == '-1' || deptoF == '-1') && isConsultaxRangoFolios == "N") {
                        alert("Debe de seleccionar un departamento inicial y final");
                    } else {
                        if (deptoI === deptoF) {
                            if ((partidaI === '-1' || partidaF === '-1') && isConsultaxRangoFolios == "N") {
                                alert("Debe de seleccionar una partida inicial y partida final");
                            } else {
                                if (cont == 0) {
                                    alert("Debe de seleccionar al menos un ramo");
                                } else {
                                    $("#frmRptExcel").submit();
                                }
                            }
                        } else {
                            if (cont == 0) {
                                alert("Debe de seleccionar al menos un ramo");
                            } else {
                                $("#frmRptExcel").submit();
                            }
                        }
                    }
                } else {
                    if (cont == 0) {
                        alert("Debe de seleccionar al menos un ramo");
                    } else {
                        $("#frmRptExcel").submit();
                    }
                }
            }
        } else {
            if (cont == 0) {
                alert("Debe de seleccionar al menos un ramo");
            } else {
                $("#frmRptExcel").submit();
            }
        }
    }
}
function generarReporteReprogramacion(tipoReporte) {
    var bandera = true;
    var paraestatal = $("#isParaestatal").val();
    var mod = $(".chkModProgramatica");
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    var proyI = $("#selProActI").val();
    var proyF = $("#selProActF").val();
    var nombreReportes = "";

    if ($("#radioFolio").is(":checked")) {
        var folio = $("#inpt-folio").val();
        if (folio === '0' || folio === "") {
            alert("Debe de capturar un oficio para procesar");
            bandera = false;
        }
    } else if ($("#radioCaratula").is(":checked")) {
        if ($("#selCaratula").val() === '-1') {
            alert("Debe de seleccionar una car\u00e1tula");
            bandera = false;
        } else if ($("#selCaratula").val() === '-2' || paraestatal === 'false') {
            if (progI === '-1' || progF === '-1') {
                alert("Debe de seleccionar un programa inicial y final");
                bandera = false;
            } else {
                if (progI === progF) {
                    if (proyI === '-1' || proyF === '-1') {
                        alert("Debe de seleccionar un proyecto/actividad inicial y final");
                        bandera = false;
                    }
                }
            }
        }
    }
    for (var it = 0; it < mod.length; it++) {
        nombreReportes += mod[it].value;
        if (it < mod.length - 1) {
            nombreReportes += ",";
        }
    }
    if (tipoReporte == "xls")
        nombreReportes = nombreReportes.replace("rptReproMetasNuevas.jasper", "rptReproMetasNuevasExcel.jasper");

    $("#nombresReporte").val(nombreReportes);
    if ($("#selCaratula").val() === "-2") {
        $("#frmRptExcel").attr("action", "ejecutaReporte/ejecutarReporte.jsp");
    } else {
        $("#frmRptExcel").attr("action", "PaqueteReportesReprogramado");
    }
    $("#reporttype").val(tipoReporte);
    if (bandera)
        $("#frmRptExcel").submit();
}

function generarReporteMonitoreoPresupuestalPdf() {
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var mensajeError = "";
    var progI = $("#selProgI").val();
    var progF = $("#selProgF").val();
    var deptoI = $("#selDeptoI").val();
    var deptoF = $("#selDeptoF").val();
    var partidaI = $("#selPartidaI").val();
    var partidaF = $("#selPartidaF").val();
    var cont = $("#contRegSelect").val();
    var i = 0;

    $("#reporttype").val("pdf");

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }

    if (cont == 1) {
        if (progI == '-1' || progF == '-1') {
            alert("Debe de seleccionar un programa inicial y final");
        } else {
            if (progI == progF) {
                if (deptoI == '-1' || deptoF == '-1') {
                    alert("Debe de seleccionar un departamento inicial y final");
                } else {
                    if (deptoI === deptoF) {
                        if (partidaI === '-1' || partidaF === '-1') {
                            alert("Debe de seleccionar una partida inicial y partida final");
                        } else {
                            if (cont == 0) {
                                alert("Debe de seleccionar al menos un ramo");
                            } else {
                                $("#frmRptExcel").submit();
                            }
                        }
                    } else {
                        if (cont == 0) {
                            alert("Debe de seleccionar al menos un ramo");
                        } else {
                            $("#frmRptExcel").submit();
                        }
                    }
                }
            } else {
                if (cont == 0) {
                    alert("Debe de seleccionar al menos un ramo");
                } else {
                    $("#frmRptExcel").submit();
                }
            }
        }
    } else {
        if (cont == 0) {
            alert("Debe de seleccionar al menos un ramo");
        } else {
            $("#frmRptExcel").submit();
        }
    }

}

function contCheckRptRamPrgMonitoreo(IdCheck) {
    var contReg = $("#contReg").val();
    var contRamoSelect = 0;
    var RamoList = document.getElementById("contRegSelect");
    var ramoSelect = "";
    var rangoDepto = $(".selRangoDepto");
    var longRamoList = RamoList.value;
    var checkRam = document.getElementById("check" + IdCheck);
    var labelCont = document.getElementById("labelCont");

    if (checkRam.checked)
        longRamoList++;
    else
        longRamoList--;

    if (longRamoList == 1) {
        labelCont.value = longRamoList + " Seleccionado";
        cargarProgramasReportes();
        $(".selRangoPrograma").prop("disabled", false);
        $(".selRangoPrograma").change();
        for (i = 1; i <= contReg; i++) {
            var tempChecks = $("#check" + i).val();
            if ($("#check" + i).is(':checked')) {
                var tempRamoChecks = $("#ramoCheck" + i).val();
                if (contRamoSelect == 0)
                    ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
                else
                    ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
                contRamoSelect++;
            }
        }
        ramoSelect = ramoSelect.replaceAll("'", "");
        $("#ramoInList").val(ramoSelect);
    } else {
        labelCont.value = longRamoList + " Seleccionados";
        $(".selRangoPrograma").html("<option value='-1'>-- Seleccione un programa --</option>");
        $(".selRangoPrograma").change();
    }


    RamoList.value = longRamoList;
    $("#labelCont").change();
}


function cambiaDirectorioReporte(pdf, ocupaPartida) {
    if (ocupaPartida === '2') {
        $(".selRangoPartida").attr("disabled", "disabled");
        $(".selRangoPartida").val(-1);
    } else {
        $(".selRangoPartida").removeAttr("disabled");
    }
    if (pdf === 'hiddenPdf') {
        $("#botonPdf").attr("disabled", "disabled");
    } else {
        $("#botonPdf").removeAttr("disabled");
    }
    if ($("#radioReporteCompRep").is(':checked')) {
        $("#rptPath").val("reprogramadoRevision");
    }
    if ($("#radioReporteDesgAccion").is(':checked')) {
        $("#rptPath").val("/reprogramadoRevision");
    }
    if ($("#radioReporteMetaNueva").is(':checked')) {
        $("#rptPath").val("reprogramadoRevision");
    }
    if ($("#radioReporteRecalendarizacionesConsolidado").is(':checked')) {
        $("#rptPath").val("");
    }
    if ($("#radioReporteRecalendarizacionesConsolidadoConcentrado").is(':checked')) {
        $("#opcionConcentradoRecal").css("display", "block")
    } else {
        $("#opcionConcentradoRecal").css("display", "none")
    }
    if ($("#radioReporteTransferencias").is(':checked')) {
        $("#rptPath").val("");
    }
    if ($("#radioReporteAmpliaciones").is(':checked')) {
        $("#rptPath").val("");
    }
}
function cambiaDirectorioReprogReporte(pdf, ocupaPartida) {
    if (ocupaPartida === '2') {
        $(".selRangoPartida").val(-1);
    } else {
        $(".selRangoPartida").removeAttr("disabled");
    }
    if (pdf === 'hiddenPdf') {
        $("#botonPdf").attr("disabled", "disabled");
    } else {
        $("#botonPdf").removeAttr("disabled");
    }
    if ($("#radioReporteCompRep").is(':checked')) {
        $("#rptPath").val("reprogramadoRevision");
    }
    if ($("#radioReporteDesgAccion").is(':checked')) {
        $("#rptPath").val("/reprogramadoRevision");
    }
    if ($("#radioReporteMetaNueva").is(':checked')) {
        $("#rptPath").val("reprogramadoRevision");
    }
}

function dataTablePOA(table) {
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

function reincioTablaSolicitudes() {
    $("#tbl-solMovs_wrapper").remove();
    $("#TableZone").html("<table id='tbl-solMovs' class='display'><thead><tr><th align='right' >Oficio</th><th align='right' >Fecha<br>Elaboración</th><th align='right' >Fecha Envío<br>Autorización</th><th align='right' width ='200'>Ramo</th><th align='right' >Estatus</th><th align='right' ></th></tr></thead><tbody></tbody></table>");
}

function editarImporteTrans(consec) {
    var estatus = $("#estatus").val();
    var folio = $("#inp-folio-usr").val();
    var fecha = $("#dateFor").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetImporteTransferencia.jsp',
        datatype: 'html',
        data: {
            identificador: consec,
            estatus: estatus,
            edicion: 1,
            folio: folio
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            var resplit = res.split("|");
            $('#mensaje').hide();
            if (res !== '-1') {
                if (resplit[0] === "3") {
                    alert(resplit[1]);
                    return false;
                }
                $("#PopUpZone").html(res);
                $("#PopUpZone").fadeIn();
            } else {
                alert("Ocurri\u00f3 un error inesperado al cargar la informaci\u00f3n");
            }
        }
    });
}

function getSubconceptosCapturaIngresos() {

    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var yearPres = $("#inp-periodo").val();
    var selCaratula = $("#selCaratula").val();

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetSubconceptosCapturaIngresos.jsp",
        dataType: 'html',
        data: {
            ramo: ramo,
            concepto: concepto,
            yearPres: yearPres,
            opcion: 1,
            selCaratula: selCaratula
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
                $("divPresupuesto div:last-child").hide();
            } else {
                $("#divPres").html(response);
                $("#divPresupuesto div:last-child").show();
            }
        }
    });

}

function insertSubConceptoIngreso() {

    var yearPres = $("#inp-periodo").val();
    var ramo = $("#selRamo").val();
    var ramoDescr = $("#selRamo option:selected").text();
    var concepto = $("#selConcepto").val();
    var conceptoDescr = $("#selConcepto option:selected").text();
    var selCaratula = $("#selCaratula").val();

    if (ramo == "-1") {
        alert("Para Continuar Debe De Seleccionar Un Ramo.");
        return false;
    }
    if (concepto == "-1") {
        alert("Para Continuar Debe De Seleccionar Un Concepto.");
        return false;
    }
    if (selCaratula == "-1") {
        alert("Para Continuar Debe De Seleccionar Una Caratula.");
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetInfoSubConceptoIngreso.jsp",
        dataType: 'html',
        data: {
            selCaratula: selCaratula,
            selCaratulaDescr: -1,
            yearPres: yearPres,
            ramo: ramo,
            ramoDescr: ramoDescr,
            concepto: concepto,
            conceptoDescr: conceptoDescr,
            subConcepto: -1
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
                $("divPresupuesto div:last-child").hide();
            } else {
                $("#infoPresupuestacion").html(response);
                $("#infoPresupuestacion").show();
            }
            getSubconceptosCapturaPresupuestoIngreso();
            getSubconceptosCapturaIngresos();
            getStatusIngresoModificado();
        }
    });

}

function totalCapturaIngreso() {

    var presup = $(".capt-mes");
    var selTipoMov = $("#selTipoMov").val();
    var total = 0;

    for (var it = 0; it < presup.length; it++) {
        total += parseFloat(presup[it].value.replaceAll(",", ""));
    }

    if (selTipoMov == "R") {
        total = total * -1;
    }

    $("#totalNvo").val(total.toFixed(2));
    $("#totalIngreso").val(total.toFixed(2));
    agregarFormato("totalIngreso");
}

function nuevoSubConceptoCapturaIngreso() {

    var cont = 0;
    var selCaratula = $("#selCaratula").val();
    var yearPres = $("#inp-periodo").val();
    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var selTipoMov = $("#selTipoMov").val();
    var subConcepto = $("#subConcepto").val();
    var subConceptoDescr = $("#txtAreaPresupuestacion").val();
    subConceptoDescr = subConceptoDescr.toUpperCase();

    if (selCaratula == "-1") {
        $("#selCaratula").addClass("errorClass");
        alert("Debe de seleccionar una caratula");
        cont++;
    } else {
        $("#selCaratula").removeClass("errorClass");
    }

    if (subConceptoDescr.trim() == "") {
        $("#txtAreaPresupuestacion").addClass("errorClass");
        cont++;
    } else {
        $("#txtAreaPresupuestacion").removeClass("errorClass");
    }

    var ene = $("#inpTxtEne").val().replaceAll(",", "");
    var feb = $("#inpTxtFeb").val().replaceAll(",", "");
    var mar = $("#inpTxtMar").val().replaceAll(",", "");
    var abr = $("#inpTxtAbr").val().replaceAll(",", "");
    var may = $("#inpTxtMay").val().replaceAll(",", "");
    var jun = $("#inpTxtJun").val().replaceAll(",", "");
    var jul = $("#inpTxtJul").val().replaceAll(",", "");
    var ago = $("#inpTxtAgo").val().replaceAll(",", "");
    var sep = $("#inpTxtSep").val().replaceAll(",", "");
    var oct = $("#inpTxtOct").val().replaceAll(",", "");
    var nov = $("#inpTxtNov").val().replaceAll(",", "");
    var dic = $("#inpTxtDic").val().replaceAll(",", "");

    if (ene.split(".")[0].length > 10) {
        $("#inpTxtEne").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtEne").removeClass("errorClass");
    }

    if (feb.split(".")[0].length > 10) {
        $("#inpTxtFeb").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtFeb").removeClass("errorClass");
    }

    if (mar.split(".")[0].length > 10) {
        $("#inpTxtMar").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMar").removeClass("errorClass");
    }

    if (abr.split(".")[0].length > 10) {
        $("#inpTxtAbr").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAbr").removeClass("errorClass");
    }

    if (may.split(".")[0].length > 10) {
        $("#inpTxtMay").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMay").removeClass("errorClass");
    }

    if (jun.split(".")[0].length > 10) {
        $("#inpTxtJun").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJun").removeClass("errorClass");
    }

    if (jul.split(".")[0].length > 10) {
        $("#inpTxtJul").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJul").removeClass("errorClass");
    }

    if (ago.split(".")[0].length > 10) {
        $("#inpTxtAgo").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAgo").removeClass("errorClass");
    }

    if (sep.split(".")[0].length > 10) {
        $("#inpTxtSep").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtSep").removeClass("errorClass");
    }

    if (oct.split(".")[0].length > 10) {
        $("#inpTxtOct").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtOct").removeClass("errorClass");
    }

    if (nov.split(".")[0].length > 10) {
        $("#inpTxtNov").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtNov").removeClass("errorClass");
    }

    if (dic.split(".")[0].length > 10) {
        $("#inpTxtDic").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtDic").removeClass("errorClass");
    }

    var opcion = 2;

    if (cont == 0) {

        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxSaveSubconceptoCapturaIngreso.jsp",
            dataType: 'html',
            data: {
                selCaratula: selCaratula,
                yearPres: yearPres,
                ramo: ramo,
                concepto: concepto,
                selTipoMov: selTipoMov,
                subConcepto: subConcepto,
                subConceptoDescr: subConceptoDescr,
                ene: ene,
                feb: feb,
                mar: mar,
                abr: abr,
                may: may,
                jun: jun,
                jul: jul,
                ago: ago,
                sep: sep,
                oct: oct,
                nov: nov,
                dic: dic
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    getSubconceptosCapturaIngresos();
                    $("#divPres").html(response);
                    $("#infoPresupuestacion").hide();
                }
            }
        });

    } else {
        alert("Debe corregir los datos marcados");
    }
}

function editarSubConceptoCapturaIngreso() {

    var presRow = $("#tblPresupuesto .selected td");

    if (presRow.length > 0) {
        var selCaratula = $("#selCaratula").val();
        var selCaratulaDescr = $("#selCaratula option:selected").text();
        var yearPres = $("#inp-periodo").val();
        var ramo = $("#selRamo").val();
        var ramoDescr = $("#selRamo option:selected").text();
        var concepto = $("#selConcepto").val();
        var conceptoDescr = $("#selConcepto option:selected").text();
        var subConcepto = presRow[0].innerHTML;

        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoSubConceptoIngreso.jsp',
            dataType: 'html',
            data: {
                selCaratula: selCaratula,
                selCaratulaDescr: selCaratulaDescr,
                yearPres: yearPres,
                ramo: ramo,
                ramoDescr: ramoDescr,
                concepto: concepto,
                conceptoDescr: conceptoDescr,
                subConcepto: subConcepto
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $("#infoPresupuestacion").html(response);
                    $("#infoPresupuestacion").show();
                }
                getSubconceptosCapturaPresupuestoIngreso();
                getSubconceptosCapturaIngresos();
                getStatusIngresoModificado();
            }
        });



    } else {
        alert("Debe de seleccionar un registro para continuar");
    }
}

function modificarSubConceptoCapturaIngreso(subConcepto) {

    var cont = 0;
    var selCaratula = $("#selCaratula").val();
    var yearPres = $("#inp-periodo").val();
    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var selTipoMov = $("#selTipoMov").val();
    var subConceptoDescr = $("#txtAreaPresupuestacion").val();
    subConceptoDescr = subConceptoDescr.toUpperCase();
    var total = $("#totalIngreso").val();
    var totalAnt = parseFloat($("#totalAnt").val().replaceAll(",", ""));
    var totalNvo = parseFloat($("#totalNvo").val().replaceAll(",", ""));

    if (selCaratula == "-1") {
        $("#selCaratula").addClass("errorClass");
        alert("Debe de seleccionar una caratula");
        cont++;
    } else {
        $("#selCaratula").removeClass("errorClass");
    }

    if (subConceptoDescr.trim() == "") {
        $("#txtAreaPresupuestacion").addClass("errorClass");
        cont++;
    } else {
        $("#txtAreaPresupuestacion").removeClass("errorClass");
    }

    var ene = $("#inpTxtEne").val().replaceAll(",", "");
    var feb = $("#inpTxtFeb").val().replaceAll(",", "");
    var mar = $("#inpTxtMar").val().replaceAll(",", "");
    var abr = $("#inpTxtAbr").val().replaceAll(",", "");
    var may = $("#inpTxtMay").val().replaceAll(",", "");
    var jun = $("#inpTxtJun").val().replaceAll(",", "");
    var jul = $("#inpTxtJul").val().replaceAll(",", "");
    var ago = $("#inpTxtAgo").val().replaceAll(",", "");
    var sep = $("#inpTxtSep").val().replaceAll(",", "");
    var oct = $("#inpTxtOct").val().replaceAll(",", "");
    var nov = $("#inpTxtNov").val().replaceAll(",", "");
    var dic = $("#inpTxtDic").val().replaceAll(",", "");

    if (ene.split(".")[0].length > 10) {
        $("#inpTxtEne").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtEne").removeClass("errorClass");
    }

    if (feb.split(".")[0].length > 10) {
        $("#inpTxtFeb").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtFeb").removeClass("errorClass");
    }

    if (mar.split(".")[0].length > 10) {
        $("#inpTxtMar").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMar").removeClass("errorClass");
    }

    if (abr.split(".")[0].length > 10) {
        $("#inpTxtAbr").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAbr").removeClass("errorClass");
    }

    if (may.split(".")[0].length > 10) {
        $("#inpTxtMay").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtMay").removeClass("errorClass");
    }

    if (jun.split(".")[0].length > 10) {
        $("#inpTxtJun").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJun").removeClass("errorClass");
    }

    if (jul.split(".")[0].length > 10) {
        $("#inpTxtJul").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtJul").removeClass("errorClass");
    }

    if (ago.split(".")[0].length > 10) {
        $("#inpTxtAgo").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtAgo").removeClass("errorClass");
    }

    if (sep.split(".")[0].length > 10) {
        $("#inpTxtSep").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtSep").removeClass("errorClass");
    }

    if (oct.split(".")[0].length > 10) {
        $("#inpTxtOct").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtOct").removeClass("errorClass");
    }

    if (nov.split(".")[0].length > 10) {
        $("#inpTxtNov").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtNov").removeClass("errorClass");
    }

    if (dic.split(".")[0].length > 10) {
        $("#inpTxtDic").addClass("errorClass");
        cont++;
    } else {
        $("#inpTxtDic").removeClass("errorClass");
    }

    var opcion = 2;

    if (cont == 0) {

        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxSaveSubconceptoCapturaIngreso.jsp",
            dataType: 'html',
            data: {
                selCaratula: selCaratula,
                yearPres: yearPres,
                ramo: ramo,
                concepto: concepto,
                selTipoMov: selTipoMov,
                subConcepto: subConcepto,
                subConceptoDescr: subConceptoDescr,
                ene: ene,
                feb: feb,
                mar: mar,
                abr: abr,
                may: may,
                jun: jun,
                jul: jul,
                ago: ago,
                sep: sep,
                oct: oct,
                nov: nov,
                dic: dic
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    alert("Se actualizo la informaci\u00f3n satisfactoriamente");
                    getSubconceptosCapturaIngresos();
                    $("#divPres").html(response);
                    $("#infoPresupuestacion").hide();
                }
            }
        });



    } else {
        alert("Debe corregir los datos marcados");
    }
}

function borrarSubConceptoCaptura(selCaratula, yearPres, ramo, concepto, subConcepto) {

    if (confirm("¿Est\u00e1 seguro que desea borrar este registro? \n Al aceptar se eliminaran todas las modificaciones posteriores a esta caratula.")) {
        $.ajax({
            type: 'POST',
            url: "librerias/ajax/ajaxBorrarSubconceptoCapturaIngreso.jsp",
            dataType: 'html',
            data: {
                selCaratula: selCaratula,
                yearPres: yearPres,
                ramo: ramo,
                concepto: concepto,
                subConcepto: subConcepto
            },
            success: function(response) {

                var res = trim(response.replace("<!DOCTYPE html>", ""));

                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    getSubconceptosCapturaIngresos();
                    $("#divPres").html(response);
                    $("#infoPresupuestacion").hide();
                }
            }
        });
    }
}

function getPartidasReporteMoitoreo() {
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetPartidasByAccionAmpRed.jsp',
        datatype: 'html',
        data: {
            ampRed: true
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                res = res.split("|");
                if (res[0] === '0') {
                    res = res[1];
                    alert(res);
                } else {
                    $(".selRangoPartida").html(response);
                }
            }
        }
    });
}

function cambioBusquedaReporteReprogramacion(tipoBusqueda) {
    var caratula = $("#selCaratula").val();
    if (tipoBusqueda === '0') {
        $("#selRamo").val('-1');
        $("#rangos-param").hide();
        if (typeof tipoBusqueda !== 'undefined') {
            $("#selCaratula").val(-1);
            $("#selCaratula").attr("disabled", "disabled");
        }
        $("#inpt-folio").removeAttr("disabled");
    } else if (tipoBusqueda === '2') {
        $("#rangos-param").show();
        if (typeof tipoBusqueda !== 'undefined') {
            $("#selCaratula").val(-1);
            $("#selCaratula").attr("disabled", "disabled");
        }
        $("#inpt-folio").attr("disabled", "disabled");
    } else if (tipoBusqueda === '1') {
        $("#rangos-param").hide();
        $("#selCaratula").val(-1);
        $("#selCaratula").removeAttr("disabled");
        $("#inpt-folio").attr("disabled", "disabled");
    }
}

function cambioBusquedaReporteByFolioCaratula() {

    if ($("#radioFolio").is(':checked')) {

        $("#selCaratula").val(-1);
        $("#selCaratula").attr("disabled", "disabled");
        $("#btnCaratula").attr("disabled", "disabled");
        $("#inpt-folio").removeAttr("disabled");
        $("#btnFolio").removeAttr("disabled");

    } else {

        if ($("#radioCaratula").is(':checked')) {

            $("#inpt-folio").val("");
            $("#inpt-folio").attr("disabled", "disabled");
            $("#btnFolio").attr("disabled", "disabled");
            $("#selCaratula").removeAttr("disabled");
            $("#btnCaratula").removeAttr("disabled");

        }

    }

    $("#tbl-movs tbody").html("");

}

function changeConsiderarRecalendarizacion(identificador) {

    $("#identificador").val(identificador);
    $("#estatus").val(estatus);

    var folio = $("#inp-folio-usr").val();
    var considerar;

    if ($("#considerar" + identificador).is(':checked')) {
        considerar = "S";
    } else {
        considerar = "N";
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxChangeConsiderarRecalendarizacion.jsp',
        datatype: 'html',
        data: {
            identificador: identificador,
            folio: folio,
            considerar: considerar
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert(res);

        },
        error: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert(res);

        }

    });
}
function changeIngresoPropio(consec) {

    var folio = $("#inp-folio-usr").val();
    var considerar;

    if ($("#ingPropio").is(':checked')) {
        considerar = "S";
    } else {
        considerar = "N";
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxChangeIngresopropio.jsp',
        datatype: 'html',
        data: {
            consec: consec,
            folio: folio,
            considerar: considerar
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert(res);

        },
        error: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);

        }

    });
}

function changeConsiderarTransAmp(consec, tipoMovimiento) {

    var folio = $("#inp-folio-usr").val();
    var considerar;

    if ($("#considerar").is(':checked')) {
        considerar = "S";
    } else {
        considerar = "N";
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxChangeConsideraTransAmp.jsp',
        datatype: 'html',
        data: {
            consec: consec,
            folio: folio,
            considerar: considerar,
            tipoMovimiento: tipoMovimiento
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert(res);

        },
        error: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);

        }

    });
}

function acomodoBotones() {
    var selMeta = $('#selMeta').val();
    if (selMeta === '-1') {
        $('#btn-add-meta').css('display', 'block');
        $('#btn-edit-meta').css('display', 'none');
        $('#btn-add-accion').css('display', 'block');
        $('#btn-edit-accion').css('display', 'none');
    } else if (selMeta !== '-1') {
        $('#btn-add-meta').css('display', 'none');
        $('#btn-edit-meta').css('display', 'block');
        $('#btn-add-accion').css('display', 'block');
        $('#btn-edit-accion').css('display', 'none');
    }
}

function getInfoMetaReprogramacion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var programaId = $("#selPrg").val();
    var proyecto = $("#selProy").val();
    var arrayTipoProyecto = proyecto.split(",");
    var proyectoId = arrayTipoProyecto[0];
    var tipoProyecto = arrayTipoProyecto[1];

    if (proyecto !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoReprogramacionMeta.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                proyectoId: proyectoId,
                tipoProyecto: tipoProyecto,
                programaId: programaId,
                identificador: identificador,
                estatus: estatus

            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                fadeInPopUp("PopUpZone");
                $("#PopUpZone").html(res);
                fadeInPopUp("infoMetaAmpRed");
                fadeOutPopUp("estimacionMetaAR");
                $('#mensaje').hide();
            }
        });
    } else {
        alert("Debe de seleccionar un proyecto/Actividad para continuar");
    }
}


function actualizarReprogramacionMeta() {

    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var input = $(".calenVistaCAR .estimacion");
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var clasificacion = $("#selClasificacion").val();
    var compromiso = $("#selCompromiso").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var ponderacion = $("#selPonderacion").val();
    var transversalidad = $("#selTrasver").val();
    var obra = $("#selObraP").val();
    var identificador = $("#identificador").val();
    var inTxtTotalEst = $("#inTxtTotalEst").val();
    var cont;
    var acumulado = 0;
    var num_meses = $("#num_meses").val();
    var valores = "";
    var variable;
    var valor = 0;

    for (i = 0; i < num_meses; i++) {
        variable = "";
        variable = "estimacion" + i;
        if ($("#" + variable).val() === null || $("#" + variable).val() === "") {
            valor = "0";
        } else {
            valor = $("#" + variable).val().replaceAll(",", "");
        }
        valores += valor + ",";
        acumulado += parseFloat(valor.replaceAll(",", ""));
    }

    var MetaDescr = $("#txtAreaMeta").val();
    MetaDescr = MetaDescr.toUpperCase();
    if (MetaDescr == "" || MetaDescr == null) {
        alert("La descripci\u00f3n de la meta no puede ser vac\u00eda");
    }
    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
    }
    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
    }
    if (linea == "-1") {
        alert("La l\u00ednea PED es un par\u00e1metro requerido");
    }


    if (acumulado > 0) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizarReprogramacionMeta.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                metaDescr: MetaDescr,
                programaId: programaId,
                tipoProy: tipoProy,
                proyectoId: proyectoId,
                unidadMedida: unidadMedida,
                calculo: calculo,
                linea: linea,
                lineaSectorial: lineaSectorial,
                identificador: identificador,
                clasificacion: clasificacion,
                compromiso: compromiso,
                ponderacion: ponderacion,
                transversalidad: transversalidad,
                valores: valores,
                obra: obra,
                sumRecalendarizacion: acumulado.toFixed(2)
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                getMetasByProyectoUsuarioAmpliacionReduccion();
                actualizaMovimientosReprogramacion();
                $("#PopUpZone").html();
                fadeOutPopUp("estimacionMetaAR");
                $('#mensaje').hide();
            }
        });
    } else {
        alert("La estimaci\u00f3n de la meta no puede ser cero");
    }
}

function getInfoMetaReprogramacionTabla(identificador, estatus, ramoId, metaId) {

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var autorizacion = $("#autorizacion").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetInfoReprogramacionMeta.jsp',
        datatype: 'html',
        data: {
            ramoId: ramoId,
            metaId: metaId,
            identificador: identificador,
            estatus: estatus,
            autorizacion: autorizacion

        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));

            $("#PopUpZone").html(res);
            $("#PopUpZone").fadeIn();
            // fadeInPopUp("PopUpZone");
            fadeInPopUp("infoMetaAmpRed");
            fadeOutPopUp("estimacionMetaAR");
            $('#mensaje').hide();


        }
    });

}

function getInfoAccionReprogramacion(identificador, estatus) {

    var ramoId = $("#selRamo").val();
    var metaId = $("#selMeta").val();
    var accionId = $("#selAccion").val();
    var oficio = $("#inp-folio-usr").val();
    var tipoValidacion = "A";
    if (estatus !== "V") {
        if (metaId !== '-1') {
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
            $.ajax({
                type: 'POST',
                url: 'librerias/ajax/ajaxGetInfoReprogramacionAccion.jsp',
                datatype: 'html',
                data: {
                    ramoId: ramoId,
                    metaId: metaId,
                    identificador: identificador,
                    estatus: estatus

                },
                success: function(response) {

                    var res = trim(response.replace("<!DOCTYPE html>", ""));

                    $("#PopUpZone").html(res);
                    fadeInPopUp("PopUpZone");
                    fadeOutPopUp("estimacionMeta");
                    fadeInPopUp("infoAccion");
                    $('#mensaje').hide();

                }
            });
        } else {
            alert("Debe de seleccionar una meta para continuar");
        }
    } else {
        alert("No se puede crear una acci\u00f3n si el movimiento se encuentra en revisi\u00f3n");
    }

}

function getInfoAccionReprogramacionTabla(identificador, estatus, ramoId, metaId, accionId) {
    var autorizacion = $("#autorizacion").val();
    if (metaId !== '-1') {
        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetInfoReprogramacionAccion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                metaId: metaId,
                accionId: accionId,
                identificador: identificador,
                estatus: estatus,
                autorizacion: autorizacion
            },
            success: function(response) {

                var res = trim(response.replace("<!DOCTYPE html>", ""));

                $("#PopUpZone").html(res);
                fadeInPopUp("PopUpZone");
                fadeOutPopUp("estimacionMeta");
                fadeInPopUp("infoAccion");
                $('#mensaje').hide();

            }
        });


    } else {
        alert("Debe de seleccionar una meta para continuar");
    }

}

function actualizarReprogramacionAccion() {

    var identificador = $("#identificador").val();
    var ramoId = $("#ramoId").val();
    var programaId = $("#programaId").val();
    var proyectoId = $("#proyectoId").val();
    var tipoProy = $("#tipoProy").val();
    var metaId = $("#metaId").val();
    var accionId = $("#accionId").val();
    var input = $(".calenVistaC .estimacion");
    var unidadMedida = $("#selMedida").val();
    var calculo = $("#selCalculo").val();
    var linea = $("#selLineaPedRP").val();
    var lineaSectorial = $("#selLineaSectRP").val();
    var selGrupo = $("#selGrupo").val();
    var selUnidadEj = $("#selUnidadEj").val();
    var selMunicipio = $("#selMunicipio").val();
    var selLocalidad = $("#selLocalidad").val();
    var accMuj = $("#accMuj").val();
    var accHom = $("#accHom").val();
    var acumulado = 0;
    var valores = "";
    var valorSnComa;
    var accionDescr = $("#txtAreaAccion").val();
    accionDescr = accionDescr.toUpperCase();
    if (accionDescr == "" || accionDescr == null) {
        alert("La descripci\u00f3n de la acci\u00f3n no puede ser vac\u00eda");
        return false;
    }

    /*   if(sumEstimacion != sumRecalendarizacion){
     alert("La sumatoria de la reprogramación tiene que ser igual a la sumatoria de la estimación");
     return false;
     }*/

    if (unidadMedida == "-1") {
        alert("La unidad de medida es un par\u00e1metro requerido");
        return false;
    }

    if (calculo == "-1") {
        alert("El c\u00e1lculo es un par\u00e1metro requerido");
        return false;
    }

    if (linea == "-1") {
        alert("La linea PED es un par\u00e1metro requerido");
        return false;
    }

    for (var it = 0; it < input.length; it++) {
        valorSnComa = input[it].value.replaceAll(",", "");
        if (valorSnComa == "")
            valorSnComa = "0.0";
        valores += valorSnComa + ",";
        acumulado += parseFloat(valorSnComa);
    }
    if (acumulado > 0) {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxActualizarReprogramacionAccion.jsp',
            datatype: 'html',
            data: {
                ramoId: ramoId,
                valores: valores,
                metaId: metaId,
                accionId: accionId,
                accionDescr: accionDescr,
                programaId: programaId,
                tipoProy: tipoProy,
                proyectoId: proyectoId,
                unidadMedida: unidadMedida,
                calculo: calculo,
                selGrupo: selGrupo,
                selUnidadEj: selUnidadEj,
                selMunicipio: selMunicipio,
                selLocalidad: selLocalidad,
                accMuj: accMuj,
                accHom: accHom,
                linea: linea,
                lineaSectorial: lineaSectorial,
                identificador: identificador,
                sumRecalendarizacion: acumulado
            },
            success: function(response) {
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                getAccionByMetaUsuario(ramoId, programaId, proyectoId + "," + tipoProy, metaId);
                actualizaMovimientosReprogramacion();
                $("#PopUpZone").html();
                fadeOutPopUp("estimacionMeta");
                $('#mensaje').hide();

            }
        });
    } else {
        alert("La estimaci\u00f3n de la acci\u00f3n no puede ser cero");
    }

}

function getTablaMovimientosByCaratula() {

    var caratula = $("#selCaratula").val();
    var ramoId = $("#selRamo").val();
    var caratulaSelect = caratula;

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaMovimientosByCaratula.jsp',
        datatype: 'html',
        data: {
            caratula: caratula,
            ramoId: ramoId,
            caratulaSelect: caratulaSelect
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res != '-1') {
                if (res == '1') {
                    $("#TableZone tbody").html("");
                    alert("No se encontr\u00f3 el folio solicitado");
                } else {
                    reincioTablaSolicitudesCaratula();
                    $("#TableZone tbody").html(res);
                }
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }

            dataTablePOA("tbl-solMovs");
        },
        error: function(response) {
        }

    });

    if (caratula != "-1") {
        caratula = "-2";
        $('#btn-add-caratula').hide();
        $('#btn-edit-caratula').show();
        $('#btn-borrar-caratula').show();
    } else {
        $('#btn-add-caratula').show();
        $('#btn-edit-caratula').hide();
        $('#btn-borrar-caratula').hide();
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxActualizaTablaMovimientosByCaratula.jsp',
        datatype: 'html',
        data: {
            caratula: caratula,
            ramoId: ramoId,
            caratulaSelect: caratulaSelect
        },
        success: function(response) {

            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res != '-1') {
                if (res == '1') {
                    $("#TableNoAplica tbody").html("");
                    alert("No se encontr\u00f3 el folio solicitado");
                } else {
                    reincioTablaSolicitudesCaratulaNoAplica();
                    $("#TableNoAplica tbody").html(res);
                }
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }
            $('#mensaje').hide();
            dataTablePOA("tbl-solMovsNoAplica");
        },
        error: function(response) {
            $('#mensaje').hide();
        }
    });

}

function reincioTablaSolicitudesCaratula() {
    $("#tbl-solMovs_wrapper").remove();
    $("#TableZone").html("<table id='tbl-solMovs' class='display' cellspacing='0' width='100%'><thead><tr><th align='right' >Folio</th><th align='right' >Fecha<br>Elaboración</th><th align='right' >Estatus</th><th align='right' ></th></tr></thead><tbody></tbody></table>");
}

function reincioTablaSolicitudesCaratulaNoAplica() {
    $("#tbl-solMovsNoAplica_wrapper").remove();
    $("#TableNoAplica").html("<table id='tbl-solMovsNoAplica' class='display' cellspacing='0' width='100%'><thead><tr><th align='right' >Folio</th><th align='right' >Fecha<br>Elaboración</th><th align='right' >Estatus</th><th align='right' ></th></tr></thead><tbody></tbody></table>");
}

function asignarCaratulaOficio(caratulaAnterior, folio) {

    var caratula = $("#selCaratula").val();
    var ramoId = $("#selRamo").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxSaveCaratulaOficio.jsp',
        datatype: 'html',
        data: {
            caratula: caratula,
            caratulaAnterior: caratulaAnterior,
            folio: folio,
            ramoId: ramoId
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);
            $('#mensaje').hide();
            getTablaMovimientosByCaratula();

        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);
            $('#mensaje').hide();
        }
    });

}

function desasignarCaratulaOficio(caratulaAnterior, folio) {

    var caratula = "-2";
    var ramoId = $("#selRamo").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxSaveCaratulaOficio.jsp',
        datatype: 'html',
        data: {
            caratula: caratula,
            caratulaAnterior: caratulaAnterior,
            folio: folio,
            ramoId: ramoId
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);
            $('#mensaje').hide();
            getTablaMovimientosByCaratula();

        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);
            $('#mensaje').hide();
        }
    });

}


function SeguimientoFirmasBitacora(oficio, tipoFicio) {
    window.open('ejecutaReporte/ejecutarReporte.jsp?filename=BitacoraAutorizacion//rptBitacoraAutorizacion.jasper&reporttype=pdf&opcionReporte=1&oficio=' + oficio + '&tipoOficio=' + tipoFicio);
}

function checkPlantillaNueva(ramoId, programaId, deptoId, metaId, accionId, year, conReg, estadoAnterior) {

    var plantilla = "off";
    var checkRadio = document.getElementById("plantillaId" + conReg);
    var plantillaNum = $("#plantillaNum").val();

    if (checkRadio.checked)
        plantilla = "on";

    if (estadoAnterior == "1" && plantilla == "on")
        plantilla = "off";


    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxCheckPlantillaNueva.jsp',
        datatype: 'html',
        data: {
            plantilla: plantilla,
            estadoAnterior: estadoAnterior,
            ramoId: ramoId,
            programaId: programaId,
            deptoId: deptoId,
            metaId: metaId,
            accionId: accionId,
            year: year
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res != '1') {
                $("#plantillaId" + conReg).prop('checked', false);
                $("#plantillaId" + plantillaNum).prop('checked', true);
                alert(res);
            } else {
                if (plantillaNum == conReg)
                    $("#plantillaId" + plantillaNum).prop('checked', false);
                alert("Informaci\u00f3n actualizada correctamente");
            }
            refreshAccion();
            $('#mensaje').hide();
        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);
            $('#mensaje').hide();
        }
    });

}

function refreshAccion() {
    var optAccion = 4;
    var metaRow = $("#tblAcciones .selected td");
    var accion = metaRow[0].innerHTML;
    var ramo = $("#selRamo").val();
    var programa = $("#selPrograma").val();
    var meta = $("#selMeta").val();

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetAcciones.jsp",
        dataType: 'html',
        data: {
            ramoId: ramo,
            programaId: programa,
            metaId: meta,
            accionId: accion,
            optAcc: optAccion
        },
        success: function(response) {
            var resultado = trim(response.replace("<!DOCTYPE html>", ""));
            $("#listAccion").html(response);

        }
    });

}

function getSubconceptosCapturaPresupuestoIngreso() {

    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var yearPres = $("#inp-periodo").val();
    var selCaratula = $("#selCaratula").val();

    cambiarEstadoBySelectCombos('Subconcepto');

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetSubconceptosCapturaPresupuestoIngreso.jsp",
        dataType: 'html',
        data: {
            ramo: ramo,
            concepto: concepto,
            yearPres: yearPres,
            opcion: 1,
            selCaratula: selCaratula
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            res = res.split("|");
            if (res[0] == '-1') {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $("#selSubconcepto").html(res[0]);
                if (res[1] == "C") {
                    $("#selSubconcepto").attr("disabled", "disabled");
                    $("#btn-add-Subconcepto").hide();
                    $("#btn-edit-Subconcepto").hide();
                    $("#botonEditar").val("Consultar");
                } else {
                    $("#selSubconcepto").removeAttr("disabled");
                    $("#btn-add-Subconcepto").show();
                    $("#botonEditar").val("Editar");
                }
            }
        }
    });

}


function cambiarEstadoBySelect(select) {

    var valSelect = $("#sel" + select).val();

    if (valSelect != "-1") {
        $("#btn-add-" + select).hide();
        $("#btn-edit-" + select).show();
    } else {
        $("#btn-add-" + select).show();
        $("#btn-edit-" + select).hide();
    }

}

function cambiarEstadoBySelectCombos(select) {

    $("#btn-add-" + select).show();
    $("#btn-edit-" + select).hide();

}

function editSubConceptoIngreso() {

    var yearPres = $("#inp-periodo").val();
    var ramo = $("#selRamo").val();
    var ramoDescr = $("#selRamo option:selected").text();
    var concepto = $("#selConcepto").val();
    var conceptoDescr = $("#selConcepto option:selected").text();
    var subconcepto = $("#selSubconcepto").val();
    var selCaratula = $("#selCaratula").val();

    if (ramo == "-1") {
        alert("Para Continuar Debe De Seleccionar Un Ramo.");
        return false;
    }
    if (concepto == "-1") {
        alert("Para Continuar Debe De Seleccionar Un Concepto.");
        return false;
    }
    if (selCaratula == "-1") {
        alert("Para Continuar Debe De Seleccionar Una Caratula.");
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetInfoSubConceptoIngreso.jsp",
        dataType: 'html',
        data: {
            yearPres: yearPres,
            ramo: ramo,
            ramoDescr: ramoDescr,
            concepto: concepto,
            conceptoDescr: conceptoDescr,
            subConcepto: subconcepto,
            selCaratula: selCaratula
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
                $("divPresupuesto div:last-child").hide();
            } else {
                $("#infoPresupuestacion").html(response);
                $("#infoPresupuestacion").show();
            }
            getSubconceptosCapturaPresupuestoIngreso();
            getSubconceptosCapturaIngresos();
            getStatusIngresoModificado();
        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert(res);
        }
    });

}

function getCaratulasByRamo() {

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    var ramoId = $("#selRamo").val();
    var tipoCaratula = 1;

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
            tipoCaratula: tipoCaratula
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            if (res == "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $("#selCaratula").html(response);
            }
            getTablaMovimientosByCaratula();
        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert(res);
        }
    });
}

function getCaratulasByRamoReprogramacion(isParaestatal) {

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    var ramoId = $("#selRamo").val();
    var tipoCaratula = 1;

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
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            if (res == "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $("#selCaratula").html(response);
            }
            if (!isParaestatal)
                $("#rangos-param").show();
        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert(res);
        }
    });
}

function validaMetasAccionesHabilitadas(isUsuario) {
    $.ajax({
        type: 'POST',
        data: {
            isUsuario: isUsuario
        },
        url: "librerias/ajax/ajaxValidaMetasAccionesHabilitadas.jsp",
        dataType: 'html',
        beforeSend: function() {
            $("#btn-save-movs").attr("disabled", "disabled");
            $("#btnAceptar").attr("disabled", "disabled");
            $("#btnRechazar").attr("disabled", "disabled");
            $("#btnCancelar").attr("disabled", "disabled");
            $("#btnEnviarAutorizar").attr("disabled", "disabled");
            $("#btnRegresar").attr("disabled", "disabled");
            $("#btnInicio").attr("disabled", "disabled");
            $("#btn-add-meta").attr("disabled", "disabled");
            $("#btn-edit-meta").attr("disabled", "disabled");
            $("#btn-add-accion").attr("disabled", "disabled");
            $("#btn-edit-accion").attr("disabled", "disabled");
        },
        success: function(response) {
            $("#btn-save-movs").removeAttr("disabled");
            $("#btnAceptar").removeAttr("disabled");
            $("#btnRechazar").removeAttr("disabled");
            $("#btnCancelar").removeAttr("disabled");
            $("#btnEnviarAutorizar").removeAttr("disabled");
            $("#btnRegresar").removeAttr("disabled");
            $("#btnInicio").removeAttr("disabled");
            $("#btn-add-meta").removeAttr("disabled");
            $("#btn-edit-meta").removeAttr("disabled");
            $("#btn-add-accion").removeAttr("disabled");
            $("#btn-edit-accion").removeAttr("disabled");
            var res;
            response = trim(response);
            res = response.split("|");
            if (trim(res[0]) !== '-1') {
                if (!isUsuario) {
                    if (res[0] === '0') {
                        getAutorizaMovAmpliacionReduccion();
                    } else {
                        if (confirm("La meta/acci\u00f3n en este movimiento es candidata a ser inhabilitada")) {
                            getAutorizaMovAmpliacionReduccion();
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (res[0] === '3') {
                        enviarAmpliacionReduccion();
                    } else {
                        if (confirm("La meta/acci\u00f3n del c\u00f3digo " + res[1] + " es candidata a ser inhabilitada.")) {
                            enviarAmpliacionReduccion();
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                alert("Ocurri\u00f3 un error al validar las metas y acciones habilitadas");
            }
        },
        error: function() {
            $("#btn-save-movs").removeAttr("disabled");
            $("#btnAceptar").removeAttr("disabled");
            $("#btnRechazar").removeAttr("disabled");
            $("#btnCancelar").removeAttr("disabled");
            $("#btnEnviarAutorizar").removeAttr("disabled");
            $("#btnRegresar").removeAttr("disabled");
            $("#btnInicio").removeAttr("disabled");
            $("#btn-add-meta").removeAttr("disabled");
            $("#btn-edit-meta").removeAttr("disabled");
            $("#btn-add-accion").removeAttr("disabled");
            $("#btn-edit-accion").removeAttr("disabled");
        }
    });
}

function validaMetasAccionesHabilitadasTransferencias(isUsuario) {
    var oficio = $("#folio").val();
    $.ajax({
        type: 'POST',
        data: {
            isUsuario: isUsuario,
            oficio: oficio
        },
        url: "librerias/ajax/ajaxValidaMetasAccionesHabilitadasTransferencias.jsp",
        dataType: 'html',
        beforeSend: function() {
            $("#btn-save-movs").attr("disabled", "disabled");
            $("#btnAceptar").attr("disabled", "disabled");
            $("#btnRechazar").attr("disabled", "disabled");
            $("#btnCancelar").attr("disabled", "disabled");
            $("#btnEnviarAutorizar").attr("disabled", "disabled");
            $("#btnRegresar").attr("disabled", "disabled");
            $("#btnInicio").attr("disabled", "disabled");
            $("#btn-edit-meta").attr("disabled", "disabled");
            $("#btn-edit-accion").attr("disabled", "disabled");
            $("#btn-tranferir").attr("disabled", "disabled");
        },
        success: function(response) {
            $("#btn-save-movs").removeAttr("disabled");
            $("#btnAceptar").removeAttr("disabled");
            $("#btnRechazar").removeAttr("disabled");
            $("#btnCancelar").removeAttr("disabled");
            $("#btnEnviarAutorizar").removeAttr("disabled");
            $("#btnRegresar").removeAttr("disabled");
            $("#btnInicio").removeAttr("disabled");
            $("#btn-edit-meta").removeAttr("disabled");
            $("#btn-edit-accion").removeAttr("disabled");
            $("#btn-tranferir").removeAttr("disabled");
            var res;
            response = trim(response);
            res = response.split("|");
            if (res[0] !== '-1') {
                if (!isUsuario) {
                    if (res[0] === '0') {
                        getAutorizaMovTransferencia();
                    } else {
                        if (confirm("La meta/acci\u00f3n en este movimiento es candidata a ser inhabilitada")) {
                            getAutorizaMovTransferencia();
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (res[0] === '3') {
                        enviarTransferencia();
                    } else {
                        if (confirm("El la meta/acción del codigo " + res[1] + " es candidata a ser inhabilitada.")) {
                            enviarTransferencia();
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                alert("Ocurri\u00f3 un error al validar las metas y acciones habilitadas");
            }
        },
        error: function() {
            $("#btn-save-movs").removeAttr("disabled");
            $("#btnAceptar").removeAttr("disabled");
            $("#btnRechazar").removeAttr("disabled");
            $("#btnCancelar").removeAttr("disabled");
            $("#btnEnviarAutorizar").removeAttr("disabled");
            $("#btnRegresar").removeAttr("disabled");
            $("#btnInicio").removeAttr("disabled");
            $("#btn-edit-meta").removeAttr("disabled");
            $("#btn-edit-accion").removeAttr("disabled");
            $("#btn-tranferir").removeAttr("disabled");
        }
    });
}

function validaMetasAccionesHabilitadasReprogramacion(isUsuario) {
    $.ajax({
        type: 'POST',
        data: {
            isUsuario: isUsuario
        },
        url: "librerias/ajax/ajaxValidaMetasAccionesHabilitadasReprogramacion.jsp",
        dataType: 'html',
        beforeSend: function() {
            $("#btn-save-movs").attr("disabled", "disabled");
            $("#btnAceptar").attr("disabled", "disabled");
            $("#btnRechazar").attr("disabled", "disabled");
            $("#btnCancelar").attr("disabled", "disabled");
            $("#btnEnviarAutorizar").attr("disabled", "disabled");
            $("#btnRegresar").attr("disabled", "disabled");
            $("#btnInicio").attr("disabled", "disabled");
            $("#btn-add-meta").attr("disabled", "disabled");
            $("#btn-edit-meta").attr("disabled", "disabled");
            $("#btn-add-accion").attr("disabled", "disabled");
            $("#btn-edit-accion").attr("disabled", "disabled");
        },
        success: function(response) {
            $("#btn-save-movs").removeAttr("disabled");
            $("#btnAceptar").removeAttr("disabled");
            $("#btnRechazar").removeAttr("disabled");
            $("#btnCancelar").removeAttr("disabled");
            $("#btnEnviarAutorizar").removeAttr("disabled");
            $("#btnRegresar").removeAttr("disabled");
            $("#btnInicio").removeAttr("disabled");
            $("#btn-add-meta").removeAttr("disabled");
            $("#btn-edit-meta").removeAttr("disabled");
            $("#btn-add-accion").removeAttr("disabled");
            $("#btn-edit-accion").removeAttr("disabled");
            var res;
            response = trim(response);
            res = response.split("|");
            if (res[0] !== '-1') {
                if (!isUsuario) {
                    if (res[0] === '0') {
                        getAutorizaMovReprogramacion();
                    } else {
                        if (confirm("La meta/acci\u00f3n en este movimiento es candidata a ser inhabilitada")) {
                            getAutorizaMovReprogramacion();
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (res[0] === '3') {
                        enviarReprogramacion();
                    } else {
                        if (confirm("El la meta/acción del codigo " + res[1] + " es candidata a ser inhabilitada.\n")) {
                            enviarReprogramacion();
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                alert("Ocurri\u00f3 un error al validar las metas y acciones habilitadas");
            }
        },
        error: function() {
            $("#btn-save-movs").removeAttr("disabled");
            $("#btnAceptar").removeAttr("disabled");
            $("#btnRechazar").removeAttr("disabled");
            $("#btnCancelar").removeAttr("disabled");
            $("#btnEnviarAutorizar").removeAttr("disabled");
            $("#btnRegresar").removeAttr("disabled");
            $("#btnInicio").removeAttr("disabled");
            $("#btn-add-meta").removeAttr("disabled");
            $("#btn-edit-meta").removeAttr("disabled");
            $("#btn-add-accion").removeAttr("disabled");
            $("#btn-edit-accion").removeAttr("disabled");
        }
    });
}

function generarReporteCaratula(reportType) {
    var mensajeError = "";
    var ramo = $("#selRamo").val();
    var caratula = $("#selCaratula").val();
    var nombresPaquete = "";

    if (reportType === 'xls' && $("#radioFormatoModificacionPresupuestalDetallado").is(":checked")) {
        $("#radioFormatoModificacionPresupuestalDetallado").val("rptModificacionPPTOCaratulaDetalladoExcel.jasper");
    } else {
        $("#radioFormatoModificacionPresupuestalDetallado").val("rptModificacionPPTOCaratulaDetallado.jasper");
    }

    if (reportType === 'xls') {
        $("#radioFormatoModificacionPresupuestalDetallado").val("rptModificacionPPTOCaratulaDetalladoExcel.jasper");
    }

    var paquete = $(".paqueteReportes");

    for (var it = 0; it < paquete.length; it++) {

        if (reportType == 'pdf') {
            if (paquete[it].value != "repModificacionPPTOConsolidado.jasper") {
                nombresPaquete += paquete[it].value;
            }
        } else {
            nombresPaquete += paquete[it].value;
        }

        if (it != paquete.length - 1) {
            nombresPaquete += ",";
        }

    }

    $("#nombresReporte").val(nombresPaquete);

    $("#reporttype").val(reportType);

    if (ramo == '-1') {
        alert("Debe de seleccionar un ramo");
        return false;
    }

    if (caratula == '-1') {
        alert("Debe de seleccionar una caratula");
        return false;
    }

    $("#frmRptExcel").submit();

}

function cambiarReporteCaratula(pdf) {

    if ($("#radioFormatoModificacionPresupuestal").is(':checked')) {
        $("#botonPdf").removeAttr("disabled");
    } else {
        $("#botonPdf").attr("disabled", "disabled");
    }

}

function allChecksRptEstatusNivel() {
    var contReg = document.getElementById("contReg1").value;
    var estatusList = document.getElementById("contRegSelect1");
    var longEstatusList = estatusList.value;
    var allChecks = document.getElementById("allChecks1");
    var labelCont = document.getElementById("labelCont1");
    var NivelesSelect = "";

    var i = 0;
    for (i = 2; i <= contReg; i++) {
        var tempChecks = document.getElementById("checks" + i);
        if (allChecks.checked)
            tempChecks.checked = true;
        else
            tempChecks.checked = false;
    }

    if (allChecks.checked == true) {
        labelCont.value = contReg + " Seleccionados";
        estatusList.value = contReg;

    } else {
        labelCont.value = "0 Seleccionados";
        estatusList.value = 0;
    }

}


function cambiaTipoRep(tipo) {

    if (tipo == 2) {
        $("#divCombo").hide()
        $("#divCombo1").show()
        $("#OpcionRepDep").val(2);


    } else if (tipo == 1) {
        $("#divCombo").show()
        $("#divCombo1").hide()
        $("#OpcionRepDep").val(1);
    }

}


function contCheckNiveles(IdCheck) {
    var contReg = $("#contReg1").val();
    var contRamoSelect = 0;
    var RamoList = document.getElementById("contRegSelect1");
    var ramoSelect = "";
    //var rangoDepto = $(".selRangoDepto");
    var longRamoList = RamoList.value;
    var checkRam = document.getElementById("checks" + IdCheck);
    var labelCont = document.getElementById("labelCont1");

    if (checkRam.checked)
        longRamoList++;
    else
        longRamoList--;

    if (longRamoList == 1) {
        labelCont.value = longRamoList + " Seleccionado";
        //cargarProgramasReportes();
        //$(".selRangoPrograma").prop("disabled", false);
        //$(".selRangoPrograma").change();
        for (i = 1; i <= contReg; i++) {
            var tempChecks = $("#checks" + i).val();
            if ($("#checks" + i).is(':checked')) {
                var tempRamoChecks = $("#NivelCheck1" + i).val();
                if (contRamoSelect == 0)
                    ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
                else
                    ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
                contRamoSelect++;
            }
        }
        ramoSelect = ramoSelect.replaceAll("'", "");
        $("#NivelesInList").val(ramoSelect);
    } else {
        labelCont.value = longRamoList + " Seleccionados";
        // $(".selRangoPrograma").html("<option value='-1'>-- Seleccione un programa --</option>");
        // $(".selRangoPrograma").change();
    }


    RamoList.value = longRamoList;

}

function generarReportePendientesxDepPdf(tipoReporte) {
    var contReg = $("#contReg").val();
    var contReg1 = $("#contReg1").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var mensajeError = "";
    var cont = $("#contRegSelect").val();
    var nivelSelect = "";
    var contNivelSelect = 0;
    var i = 0;

    if (tipoReporte == 1) {
        $("#reporttype").val("pdf");
        $("#filename").val("rptPendientesDependencia.jasper");
    } else {
        $("#reporttype").val("XLS");
        $("#filename").val("rptPendientesDependenciaExcel.jasper");
    }

    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un ramo para continuar";
        $("#ramoInList").val("-1");
    }



    for (i = 1; i <= contReg1; i++) {
        var tempChecks = $("#checks" + i).val();
        if ($("#checks" + i).is(':checked')) {
            var tempRamoChecks = ($("#NivelCheck1" + i).val());
            if (contNivelSelect == 0)
                nivelSelect = nivelSelect + "'" + tempRamoChecks + "'";
            else
                nivelSelect = nivelSelect + "," + "'" + tempRamoChecks + "'";
            contNivelSelect++;
        }
    }

    if (contNivelSelect > 0) {
        $("#NivelesInList").val(nivelSelect);
    } else {
        mensajeError += "Debe de seleccionar al menos un nivel para continuar";
        $("#NivelesInList").val("-1");
    }



    $("#frmRptExcel").submit();



}

function mostrarRangoSeleccion() {
    var caratula = $("#selCaratula").val();
    if (caratula === "-2") {
        $("#rangos-param").show();
        $("#btnSeleccionReporte").show();
    } else {
        $("#rangos-param").hide();
        $("#btnSeleccionReporte").hide();
        $(".selRangoPrograma").val(-1);
        $(".selRangoProyecto").val(-1);
    }
}

function actualizaStatusIngresoModificado() {

    var yearPres = $("#inp-periodo").val();
    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var selCaratula = $("#selCaratula").val();
    var estatus = "";

    if ($("#checkStatus").is(":checked")) {
        estatus = "A";
    } else {
        estatus = "C";
    }

    if (ramo == "-1") {
        if ($("#checkStatus").is(":checked")) {
            $("#checkStatus").prop('checked', false);
        } else {
            $("#checkStatus").prop('checked', true);
        }
        alert("Para Continuar Debe De Seleccionar Un Ramo.");
        return false;
    }
    if (selCaratula == "-1") {
        if ($("#checkStatus").is(":checked")) {
            $("#checkStatus").prop('checked', false);
        } else {
            $("#checkStatus").prop('checked', true);
        }
        alert("Para Continuar Debe De Seleccionar Una Caratula.");
        return false;
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxActualizaStatusIngresoModificado.jsp",
        dataType: 'html',
        data: {
            selCaratula: selCaratula,
            selCaratulaDescr: -1,
            yearPres: yearPres,
            ramo: ramo,
            concepto: concepto,
            status: estatus
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res == "-1") {
                if ($("#checkStatus").is(":checked")) {
                    $("#checkStatus").prop('checked', false);
                } else {
                    $("#checkStatus").prop('checked', true);
                }
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {

                if ($("#checkStatus").is(":checked")) {
                    $("#selSubconcepto").removeAttr("disabled");
                    $("#btn-add-Subconcepto").show();
                    $("#botonEditar").val("Editar");
                } else {
                    $("#selSubconcepto").attr("disabled", "disabled");
                    $("#btn-add-Subconcepto").hide();
                    $("#btn-edit-Subconcepto").hide();
                    $("#botonEditar").val("Consultar");
                }
            }
            getSubconceptosCapturaPresupuestoIngreso();
            getSubconceptosCapturaIngresos();
        }
    });

}

function getStatusIngresoModificado() {

    var yearPres = $("#inp-periodo").val();
    var ramo = $("#selRamo").val();
    var concepto = $("#selConcepto").val();
    var selCaratula = $("#selCaratula").val();

    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetStatusIngresoModificado.jsp",
        dataType: 'html',
        data: {
            selCaratula: selCaratula,
            selCaratulaDescr: -1,
            yearPres: yearPres,
            ramo: ramo,
            concepto: concepto
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res == "-1") {
                if ($("#checkStatus").is(":checked")) {
                    $("#checkStatus").prop('checked', false);
                } else {
                    $("#checkStatus").prop('checked', true);
                }
            } else {
                if (res == "A") {
                    $("#checkStatus").prop('checked', true);
                } else {
                    $("#checkStatus").prop('checked', false);
                }
            }
        }
    });

}

function LimpiaImptAmpRed() {
    $("#inp-txt-impor-ampred").val("0.0");
}

function getRequerimientosMigrarRequerimientosByRamoFuenteFinanciamiento() {

    var ramoId = $("#selRamo").val();
    var selFuenteFiltrada = $("#selFuenteFiltrada").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    //Hay que crear un ajax para que jale con esos parametros es para filtar los requerimientos por Ramo, Fuente Financiamiento, Year.

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetProgramasByRamo.jsp',
        datatype: 'html',
        async: false,
        data: {
            ramo: ramoId,
            selFuenteFiltrada: selFuenteFiltrada
        },
        success: function(response) {

            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                if (trim(response.replace("<!DOCTYPE html>", "")) != -2) {
                    //Poner Requerimientos en la tabla
                } else {
                    alert("El ramo seleccionado no esta disponible para presupuestaci\u00f3n");
                }
            }
            $('#mensaje').hide();
        }
    });
}

function getFuenteFinanciamientoMigrarRequerimientosByRamo() {

    var ramo = $("#selRamo").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    //Hay que crear un ajax para que jale con esos parametros es para filtar las Fuentes Financiamiento por Ramo de los Requerimientos Existentes.

    if (accion != "-1") {
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxGetFuenteFinanciamientolByRelLaboralAmpRed.jsp',
            datatype: 'html',
            data: {
                ramo: ramo
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === "-1") {
                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                } else {
                    $('#mensaje').hide();
                    res = res.split("|");
                    if (res[0] === '0') {
                        res = res[1];
                        alert(res);
                    } else {
                        $("#selFuenteFiltrada").html(response);
                    }
                }
            }
        });
    }

}

function limparFuentesFinanciamiento() {
    $("#selFuente").val('-1');
    $("#selFuenteNueva").val('-1');
    $("#tbl-accion-fuente tbody").html("");
}


function getAccionesByFuenteFinanciamiento() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var ramo = $("#selRamo").val();
    var fuenteFin = $("#selFuente").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetAccionByFuenteFinanciamiento.jsp',
        datatype: 'html',
        data: {
            ramo: ramo,
            fuenteFin: fuenteFin
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                if (res === '-2') {
                    $("#tbl-accion-fuente tbody").html("");
                    alert("Este Ramo-Fuente de financiamiento no contienen informaci\u00f3n para mostrar");
                    calculaTodalMetaAccionSeleccionada();
                } else {
                    $("#tbl-accion-fuente tbody").html(response);
                    calculaTodalMetaAccionSeleccionada();
                }
            }
        }
    });
}

function cambiarFuenteFinanciamiento() {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var accionList = $('input[name="chk-cambio"]:checked');
    var ramo = $("#selRamo").val();
    var fuente = $("#selFuente").val();
    var fuenteN = $("#selFuenteNueva").val();
    var accionArray = [];
    for (var cont = 0; cont < accionList.length; cont++) {
        accionArray[cont] = accionList[cont].value;
    }
    if (ramo !== '-1') {
        if (fuente !== '-1') {
            if (fuenteN !== '-1') {
                if (accionList.length > 0) {
                    if (fuenteN !== fuente) {
                        $.ajax({
                            type: 'POST',
                            url: 'librerias/ajax/ajaxCambiarFuenteFinanciamiento.jsp',
                            datatype: 'html',
                            data: {
                                ramo: ramo,
                                fuente: fuente,
                                fuenteN: fuenteN,
                                accionArray: accionArray
                            },
                            success: function(response) {
                                $('#mensaje').hide();
                                var res = trim(response.replace("<!DOCTYPE html>", ""));
                                if (res === "-1") {
                                    alert("Ocurri\u00f3 un error al procesar la solicitud");
                                } else {
                                    if (res === "-2")
                                        alert("Ocurri\u00f3 un error");
                                    else {
                                        alert("El proceso termin\u00f3 correctamente");
                                        getAccionesByFuenteFinanciamiento();
                                    }
                                }
                            }
                        });
                    } else {
                        alert("Las fuentes de financiamiento no pueden ser iguales");
                    }
                } else {
                    alert("Debe de seleccionar al menos una acci\u00f3n para continuar");
                }
            } else {
                alert("Debe de seleccionar una fuente de financiamiento nueva");
            }
        } else {
            alert("Debe de seleccionar una fuente de financiamiento");
        }
    } else {
        alert("Debe de seleccionar un ramo");
    }
}

function selectAllCheck() {
    var checkboxes = $(".chk-metaAccion");
    if ($("#selAllCheck").is(":checked")) {
        checkboxes.prop('checked', true);
    } else {
        checkboxes.prop('checked', false);
    }
}

function calculaTodalMetaAccionSeleccionada() {
    var accionList = $('input[name="chk-cambio"]:checked');
    var accionArray = [];
    var total = 0.0;
    for (var cont = 0; cont < accionList.length; cont++) {
        accionArray[cont] = accionList[cont].value;
        total += parseFloat(accionArray[cont].split("|")[2]);
    }
    $("#total-cambios").html("$ " + addCommas(total.toFixed(2)));
}

function generarRepPendAsigPlantilla(year, strRamosReporte) {
    //RepDefPlantilla
    var opcion = "";
    var reporte = "";
    var valor = "";
    valor = strRamosReporte;
    var RepDefPlantilla = " R.RAMO IN(" + valor + ") AND  ";
    if ($("#opcion1").is(":checked")) {
        reporte = "rptDefinicionPlantillaAsig.jasper";
    } else if ($("#opcion2").is(":checked")) {
        reporte = "rptDefinicionPlantillaPend.jasper";
    }

    window.open('ejecutaReporte/ejecutarReporte.jsp?filename=repDefinicionPlantilla/' + reporte + '&reporttype=pdf&yearT=' + year + '&RepDefPlantilla=' + RepDefPlantilla + '');

}

function cambiaEjercicio() {
    $("#cambiaEjercicio").submit();
}

function validaRamoCargaCambioFuenteFondo() {

    var ramo = $("#selRamo").val();

    if (ramo != "-1") {

        mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/ajaxValidarRamoCerradoCambioFuenteFondo.jsp',
            datatype: 'html',
            async: false,
            data: {
                ramo: ramo
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res == "C") {
                    $("#selRamo").val('-1');
                    alert("El ramo seleccionado est\u00e1 cerrado para su captura");
                }
                limparFuentesFinanciamiento();
            },
            error: function(response) {
                $('#mensaje').hide();
                $("#selRamo").val('-1');
                limparFuentesFinanciamiento();
                alert("Error al procesar solicitud.");
            }
        });
    }
}

function validaDeptoPlantilla(depto, accion) {
    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");
    var ramo = $("#selRamo").val();
    var prg = $("#selPrograma").val();
    var meta = $("#selMeta").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxValidaDeptoPlantilla.jsp',
        datatype: 'html',
        data: {
            ramo: ramo,
            programa: prg,
            meta: meta,
            accion: accion,
            depto: depto
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res !== '-1') {
                if (res === '1') {
                    alert("Esta acción fue seleccionada para plantilla.\n Debe deseleccionarla para realizar el cambio.");
                    $('#selUnidadEj').val(depto);
                } else {
                    return;
                }
            } else {
                alert("Error al procesar solicitud.");
            }
        }
    });
}

function cambiarValorChkParametros(checkEditado) {

    if ($("#" + checkEditado + "").is(":checked")) {
        $("#" + checkEditado + "").val("S");
    } else {
        $("#" + checkEditado + "").val("N");
    }

}

function saveParametros() {

    var chkFirmas = $("#chkFirmas").val();
    var chkTrimestre = $("#chkTrimestre").val();
    var chkAvance = $("#chkAvance").val();
    var chkEtiqueta = $("#chkEtiqueta").val();
    var chkTodosTrimestre = $("#chkTodosTrimestre").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxSaveParametros.jsp',
        datatype: 'html',
        data: {
            chkFirmas: chkFirmas,
            chkTrimestre: chkTrimestre,
            chkAvance: chkAvance,
            chkEtiqueta: chkEtiqueta,
            chkTodosTrimestre: chkTodosTrimestre
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));

            if (res == "1") {
                alert("Se actualizaron los par\u00e1metros correctamente.");
            } else {
                alert(res)
            }

            $('#mensaje').hide();
        },
        error: function(response) {
            $('#mensaje').hide();
            alert("Ocurri\u00f3 un error al procesar solicitud.");
        }
    });

}

function getRamosSelectorByYearIndicador() {

    var selIndicador = $("#selIndicador").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetRamosSector.jsp',
        datatype: 'html',
        data: {
            selIndicador: selIndicador
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                $("#selSectorRamo").html(response);

            }
        }
    });


}


function getIndicadoresSectorRamosByYearClaveIndicador() {

    var selIndicador = $("#selIndicador").val();
    var selRamo = $("#selSectorRamo").val();


    $.ajax({
        type: 'POST',
        url: "librerias/ajax/ajaxGetIndicadorSectorRamos.jsp",
        dataType: 'html',
        data: {
            selIndicador: selIndicador,
            selRamo: selRamo
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
                $("divPresupuesto div:last-child").hide();
            } else {
                $("#divPres").html(response);
                $("#divPresupuesto div:last-child").show();
            }
        }
    });

}

function desligarIndicadorSectorRamo(selIndicador, selRamo, selPrograma) {

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxDesligarIndicadorSectorRamo.jsp',
        datatype: 'html',
        data: {
            selIndicador: selIndicador,
            selRamo: selRamo,
            selPrograma: selPrograma
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            if (res != "-1") {
                getProgramasFinByYearRamoIndicador();
                getIndicadoresSectorRamosByYearClaveIndicador();
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }

        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert("Ocurri\u00f3 un error al procesar la solicitud");
        }
    });

}

function ligarIndicadorSectorRamo() {

    var selIndicador = $("#selIndicador").val();
    var selRamo = $("#selSectorRamo").val();
    var selPrograma = $("#selPrograma").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxLigarIndicadorSectorRamo.jsp',
        datatype: 'html',
        data: {
            selIndicador: selIndicador,
            selRamo: selRamo,
            selPrograma: selPrograma
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            if (res != "-1") {
                getProgramasFinByYearRamoIndicador();
                getIndicadoresSectorRamosByYearClaveIndicador();
            } else {
                alert("Ocurri\u00f3 un error al procesar la solicitud");
            }

        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            $('#mensaje').hide();
            alert("Ocurri\u00f3 un error al procesar la solicitud");
        }
    });

}

function getProgramasFinByYearRamoIndicador() {

    var selIndicador = $("#selIndicador").val();
    var selRamo = $("#selSectorRamo").val();

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetProgramasFinByYearRamoIndicador.jsp',
        datatype: 'html',
        data: {
            selIndicador: selIndicador,
            selRamo: selRamo
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                $("#selPrograma").html(response);

            }
        }
    });

}

function mostrarCheckMovimientos() {
    $("#div-MovtosPres").show();
}

function ocultarCheckMovimientos() {
    $("#div-MovtosPres").hide();
}


function enviarTransferenciaProcedure() {
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/hablarProcedire.jsp',
        datatype: 'html',
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else {
                $('#mensaje').hide();
                $("#selPrograma").html(response);

            }
        }
    });
}

function actCamposConsultaSolicitudes(oficio, tipoMov, status, ramo) {
    $("#inpt-folio").val(oficio);
    $("#selTipoSolicitud").val(tipoMov);
    $("#selEstatusMovimiento").val(status);

    if (ramo == "07") {
        if ($("#opcRamo option[value='" + ramo + "']").length == 0) {
            $('#opcRamo').append("<option value='" + ramo + "'>" + "07-SECRETARIA DE PLANEACION Y FINANZAS" + "</option>");
        }
    }

    $("#opcRamo").val(ramo);
}

function getRamosAsociadosOficioByOficio(oficio, year) {

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetRamosAsociadosOficioByOficio.jsp',
        datatype: 'html',
        data: {
            oficio: oficio,
            year: year
        },
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else {
                $.msgBox({
                    title: "Ramos asociados en el oficio",
                    content: res,
                    type: "info"
                });
            }
        },
        error: function(response) {
            $('#mensaje').hide();
            alert("Ocurrió un error al procesar la solicitud");
        }
    });

}

function allChecksComboGrupoPoblacional() {
    var contReg = document.getElementById("contRegGP").value;
    var GPList = document.getElementById("contRegSelectGP");
    var allChecksGP = document.getElementById("allChecksGP");
    var labelContGP = document.getElementById("labelContGP");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecksGP = document.getElementById("checkGP" + i);
        if (allChecksGP.checked)
            tempChecksGP.checked = true;
        else
            tempChecksGP.checked = false;
    }

    if (allChecksGP.checked == true) {
        labelContGP.value = contReg + " Seleccionados";
        GPList.value = contReg;
    } else {
        labelContGP.value = "0 Seleccionados";
        GPList.value = 0;
    }

    $("#labelContGP").change();

}


function contCheckComboGrupoPoblacional(IdCheck) {

    var GPList = document.getElementById("contRegSelectGP");
    var longGPList = GPList.value;
    var checkRam = document.getElementById("checkGP" + IdCheck);
    var labelCont = document.getElementById("labelContGP");

    if (checkRam.checked)
        longGPList++;
    else
        longGPList--;

    if (longGPList == 1) {
        labelCont.value = longGPList + " Seleccionado";

    } else {
        labelCont.value = longGPList + " Seleccionados";
    }

    GPList.value = longGPList;

}



function checkDefincionMetaTraeResultado(renglon, ramo, meta, year, estadoAnterior) {

    var traeResultado = "off";
    var checkRadio = document.getElementById("MetaTraeResultado" + renglon);
    var metaNum = $("#metaNum").val();

    if (checkRadio.checked)
        traeResultado = "on";
    if (estadoAnterior == "1") {
        traeResultado = "off";
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxCheckMetaTraeResultado.jsp',
        datatype: 'html',
        data: {
            traeResultado: traeResultado,
            estadoAnterior: estadoAnterior,
            ramoId: ramo,
            metaId: meta,
            year: year
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res != '1' && res != '2') {

                alert(res);
            }
            else {
                getMetasOnChange();
                alert("Información actualizada correctamente");
            }
            $('#mensaje').hide();
        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);
            $('#mensaje').hide();
        }
    });

}



function checkDefincionAccionTraeResultado(ramo, meta, accion, year, renglon, estadoAnterior) {

    var traeResultado = "off";
    var checkRadio = document.getElementById("AccionTraeResultado" + renglon);
    //var metaNum = $("#metaNum").val();
    var id = "AccionTraeResultado" + renglon;
    if (checkRadio.checked)
        traeResultado = "on";
    if (estadoAnterior == "1") {
        traeResultado = "off";
    }

    mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensaje", "procesando ...", "");

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxCheckAccionTraeResultado.jsp',
        datatype: 'html',
        data: {
            traeResultado: traeResultado,
            estadoAnterior: estadoAnterior,
            ramoId: ramo,
            metaId: meta,
            year: year,
            accion: accion
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res != '1' && res != '2') {

                alert(res);
            }
            else {
                refreshAccion();
                alert("Información actualizada correctamente");
            }
            $('#mensaje').hide();
        },
        error: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            alert(res);
            $('#mensaje').hide();
        }
    });

}


function validarSaltosLineaAvancePoa(e)
{
    key = (document.all) ? e.keyCode : e.which;
    if (key == 13)
    {
        return false
    }
    else {
        return true;
    }
}


function mostrarCheckMovimientosMetas() {
    $("#div-MovtosPresMetas").show();
}

function ocultarCheckMovimientosMetas() {
    $("#div-MovtosPresMetas").hide();
}

function generarReportePrgMetaPonderado() {
    var contReg = document.getElementById("contReg").value;
    var contRegPonP = document.getElementById("contRegPonP").value;
    var contRegPonM = document.getElementById("contRegPonM").value;
    var ramoInList = document.getElementById("ramoInList");
    var ponderadoProgramaInList = document.getElementById("ponderadoProgramaList");
    var ponderadoMetaInList = document.getElementById("ponderadoMetaList");
    var ramoSelect = "";
    var PonPSelect = "";
    var PonMSelect = "";
    var contRamoSelect = 0;
    var contPonPSelect = 0;
    var contPonMSelect = 0;
    var llamarReporte = true;
    var mensajeError = "";
    $("#reporttype").val("pdf");

    //Ramo - Combo MultiCheck
    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);
        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }
    if (contRamoSelect > 0) {
        ramoInList.value = ramoSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>El ramo es un parametro requerido</p>";
        ramoInList.value = "-1";
    }

    //Ponderado Programa - Combo MultiCheck
    i = 0;
    for (i = 1; i <= contRegPonP; i++) {
        var tempChecksPonP = document.getElementById("checkPonP" + i);
        if (tempChecksPonP.checked) {
            var tempPonPChecks = document.getElementById("PonPCheck" + i);
            if (contPonPSelect == 0)
                PonPSelect = PonPSelect + tempPonPChecks.value;
            else
                PonPSelect = PonPSelect + "," + tempPonPChecks.value;
            contPonPSelect++;
        }
    }
    if (contPonPSelect > 0) {
        ponderadoProgramaInList.value = PonPSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>La ponderación de programa es un parametro requerido</p>";
        ponderadoProgramaInList.value = "-1";
    }

    //Ponderado Meta - Combo MultiCheck
    i = 0;
    for (i = 1; i <= contRegPonM; i++) {
        var tempChecksPonM = document.getElementById("checkPonM" + i);
        if (tempChecksPonM.checked) {
            var tempPonMChecks = document.getElementById("PonMCheck" + i);
            if (contPonMSelect == 0)
                PonMSelect = PonMSelect + tempPonMChecks.value;
            else
                PonMSelect = PonMSelect + "," + tempPonMChecks.value;
            contPonMSelect++;
        }
    }
    if (contPonMSelect > 0) {
        ponderadoMetaInList.value = PonMSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>La ponderación de la meta es un parametro requerido</p>";
        ponderadoMetaInList.value = "-1";
    }

    //Llamar Reporte
    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        $.msgBox({
            title: "Error",
            content: mensajeError,
            type: "error",
            buttons: [{value: "Aceptar"}]
        });
    }
}
function generarReportePrgMetaPonderadoExcel() {

    var contReg = document.getElementById("contReg").value;
    var contRegPonP = document.getElementById("contRegPonP").value;
    var contRegPonM = document.getElementById("contRegPonM").value;
    var ramoInList = document.getElementById("ramoInList");
    var ponderadoProgramaInList = document.getElementById("ponderadoProgramaList");
    var ponderadoMetaInList = document.getElementById("ponderadoMetaList");
    var ramoSelect = "";
    var PonPSelect = "";
    var PonMSelect = "";
    var contRamoSelect = 0;
    var contPonPSelect = 0;
    var contPonMSelect = 0;
    var llamarReporte = true;
    var mensajeError = "";
    $("#reporttype").val("xls");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);

        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }

    if (contRamoSelect > 0) {
        ramoInList.value = ramoSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>El ramo es un parametro requerido.</p>";
        ramoInList.value = "-1";
    }
    //Ponderado Programa - Combo MultiCheck
    i = 0;
    for (i = 1; i <= contRegPonP; i++) {
        var tempChecksPonP = document.getElementById("checkPonP" + i);
        if (tempChecksPonP.checked) {
            var tempPonPChecks = document.getElementById("PonPCheck" + i);
            if (contPonPSelect == 0)
                PonPSelect = PonPSelect + tempPonPChecks.value;
            else
                PonPSelect = PonPSelect + "," + tempPonPChecks.value;
            contPonPSelect++;
        }
    }
    if (contPonPSelect > 0) {
        ponderadoProgramaInList.value = PonPSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>La ponderación de programa es un parametro requerido</p>";
        ponderadoProgramaInList.value = "-1";
    }

    //Ponderado Meta - Combo MultiCheck
    i = 0;
    for (i = 1; i <= contRegPonM; i++) {
        var tempChecksPonM = document.getElementById("checkPonM" + i);
        if (tempChecksPonM.checked) {
            var tempPonMChecks = document.getElementById("PonMCheck" + i);
            if (contPonMSelect == 0)
                PonMSelect = PonMSelect + tempPonMChecks.value;
            else
                PonMSelect = PonMSelect + "," + tempPonMChecks.value;
            contPonMSelect++;
        }
    }
    if (contPonMSelect > 0) {
        ponderadoMetaInList.value = PonMSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>La ponderación de la meta es un parametro requerido</p>";
        ponderadoMetaInList.value = "-1";
    }

    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        $.msgBox({
            title: "Error",
            content: mensajeError,
            type: "error",
            buttons: [{value: "Aceptar"}]
        });
    }
}
function allChecksRptRam() {
    var contReg = document.getElementById("contReg").value;
    var RamoList = document.getElementById("contRegSelect");
    var allChecks = document.getElementById("allChecks");
    var labelCont = document.getElementById("labelCont");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);
        if (allChecks.checked)
            tempChecks.checked = true;
        else
            tempChecks.checked = false;
    }

    if (allChecks.checked == true) {
        labelCont.value = contReg + " Seleccionados";
        RamoList.value = contReg;
    } else {
        labelCont.value = "0 Seleccionados";
        RamoList.value = 0;
    }
    $("#labelCont").change();
}

function contCheckRptRam(IdCheck) {

    var RamoList = document.getElementById("contRegSelect");
    var longRamoList = RamoList.value;
    var checkRam = document.getElementById("check" + IdCheck);
    var labelCont = document.getElementById("labelCont");

    if (checkRam.checked)
        longRamoList++;
    else
        longRamoList--;

    if (longRamoList == 1) {
        labelCont.value = longRamoList + " Seleccionado";

    } else {
        labelCont.value = longRamoList + " Seleccionados";
    }

    RamoList.value = longRamoList;

}

function allChecksComboPonderadoPrg() {
    var contReg = document.getElementById("contRegPonP").value;
    var PonPList = document.getElementById("contRegSelectPonP");
    var allChecksPonP = document.getElementById("allChecksPonP");
    var labelContPonP = document.getElementById("labelContPonP");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecksPonP = document.getElementById("checkPonP" + i);
        if (allChecksPonP.checked)
            tempChecksPonP.checked = true;
        else
            tempChecksPonP.checked = false;
    }
    if (allChecksPonP.checked == true) {
        labelContPonP.value = contReg + " Seleccionados";
        PonPList.value = contReg;
    } else {
        labelContPonP.value = "0 Seleccionados";
        PonPList.value = 0;
    }
    $("#labelContPonP").change();
}

function allChecksComboPonderadoMeta() {
    var contReg = document.getElementById("contRegPonM").value;
    var PonMList = document.getElementById("contRegSelectPonM");
    var allChecksPonM = document.getElementById("allChecksPonM");
    var labelContPonM = document.getElementById("labelContPonM");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecksPonM = document.getElementById("checkPonM" + i);
        if (allChecksPonM.checked)
            tempChecksPonM.checked = true;
        else
            tempChecksPonM.checked = false;
    }
    if (allChecksPonM.checked == true) {
        labelContPonM.value = contReg + " Seleccionados";
        PonMList.value = contReg;
    } else {
        labelContPonM.value = "0 Seleccionados";
        PonMList.value = 0;
    }
    $("#labelContPonM").change();
}

function contCheckComboPonderadoPrg(IdCheck) {

    var PonPList = document.getElementById("contRegSelectPonP");
    var longPonPList = PonPList.value;
    var checkRam = document.getElementById("checkPonP" + IdCheck);
    var labelCont = document.getElementById("labelContPonP");

    if (checkRam.checked)
        longPonPList++;
    else
        longPonPList--;

    if (longPonPList == 1) {
        labelCont.value = longPonPList + " Seleccionado";

    } else {
        labelCont.value = longPonPList + " Seleccionados";
    }

    PonPList.value = longPonPList;

}

function contCheckComboPonderadoMeta(IdCheck) {

    var PonMList = document.getElementById("contRegSelectPonM");
    var longPonMList = PonMList.value;
    var checkRam = document.getElementById("checkPonM" + IdCheck);
    var labelCont = document.getElementById("labelContPonM");

    if (checkRam.checked)
        longPonMList++;
    else
        longPonMList--;

    if (longPonMList == 1) {
        labelCont.value = longPonMList + " Seleccionado";

    } else {
        labelCont.value = longPonMList + " Seleccionados";
    }

    PonMList.value = longPonMList;

}

function generarReporteRam() {
    var contReg = document.getElementById("contReg").value;
    var ramoInList = document.getElementById("ramoInList");
    var ramoSelect = "";
    var contRamoSelect = 0;
    var llamarReporte = true;
    var mensajeError = "";
    $("#reporttype").val("pdf");

    //Ramo - Combo MultiCheck
    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);
        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }
    if (contRamoSelect > 0) {
        ramoInList.value = ramoSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>El ramo es un parametro requerido</p>";
        ramoInList.value = "-1";
    }
    //Llamar Reporte
    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        $.msgBox({
            title: "Error",
            content: mensajeError,
            type: "error",
            buttons: [{value: "Aceptar"}]
        });
    }
}

function generarReporteRamExcel() {

    var contReg = document.getElementById("contReg").value;
    var ramoInList = document.getElementById("ramoInList");
    var ramoSelect = "";
    var contRamoSelect = 0;
    var llamarReporte = true;
    var mensajeError = "";
    $("#reporttype").val("xls");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("check" + i);

        if (tempChecks.checked) {
            var tempRamoChecks = document.getElementById("ramoCheck" + i);
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks.value + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks.value + "'";
            contRamoSelect++;
        }
    }
    if (contRamoSelect > 0) {
        ramoInList.value = ramoSelect;
    } else {
        llamarReporte = false;
        mensajeError += "<p>El ramo es un parametro requerido.</p>";
        ramoInList.value = "-1";
    }
    if (llamarReporte) {
        document.getElementById('frmActInfoRpt').submit();
    } else {
        $.msgBox({
            title: "Error",
            content: mensajeError,
            type: "error",
            buttons: [{value: "Aceptar"}]
        });
    }
}


function validaConsultaxRangoFolios(op, evt) {


    if (op == 0) {
        //Inicializacion de valores en limpio
        $("#selProgI").removeAttr("disabled");
        $("#selProgF").removeAttr("disabled");
        $("#selDeptoI").removeAttr("disabled");
        $("#selDeptoF").removeAttr("disabled");
        $("#selPartidaI").removeAttr("disabled");
        $("#selPartidaF").removeAttr("disabled");
        $("#folioIni").val("");
        $("#folioFin").val("");
        $("#folioIni").attr("disabled", "disabled");
        $("#folioFin").attr("disabled", "disabled");
        $("#isConsultaxRangoFolios").val("N");


    } else if (op == 1) {
        // si elige el rporte concentrado de recalendarizaciones no se habilitara el filtro por folio
        document.getElementById("chkXfolios").checked = false;
        $("#folioIni").val("");
        $("#folioFin").val("");
        $("#chkXfolios").attr("disabled", "disabled");
        $("#folioIni").attr("disabled", "disabled");
        $("#folioFin").attr("disabled", "disabled");
        $("#selProgI").removeAttr("disabled");
        $("#selProgF").removeAttr("disabled");
        $("#selDeptoI").removeAttr("disabled");
        $("#selDeptoF").removeAttr("disabled");
        $("#selPartidaI").removeAttr("disabled");
        $("#selPartidaF").removeAttr("disabled");
        $("#isConsultaxRangoFolios").val("N");

    } else if (op == 2) {
        //si selecciona cualquiera de los otros 3 resportes se habilita el filtro por rango de folio
        $("#chkXfolios").removeAttr("disabled");
        $("#folioIni").attr("disabled", "disabled");
        $("#folioFin").attr("disabled", "disabled");


    } else if (op == 3) {
        if ($("#chkXfolios").is(":checked")) {
            //si esta checkeada la opcion filtrar por rango de folios
            mensajes.mostrarAjaxLoad("../poa/imagenes/ajax-mini.gif", "mensajeBuscaFolios", "Buscando folios ...", "");
            cargarFoliosFiltroReportesMonitoreoPresup();
            $("#selProgI").attr("disabled", "disabled");
            $("#selProgF").attr("disabled", "disabled");
            $("#selDeptoI").attr("disabled", "disabled");
            $("#selDeptoF").attr("disabled", "disabled");
            $("#selPartidaI").attr("disabled", "disabled");
            $("#selPartidaF").attr("disabled", "disabled");
            $("#folioIni").removeAttr("disabled");
            $("#folioFin").removeAttr("disabled");
            $("#isConsultaxRangoFolios").val("S");
        } else {
            // si no esta checkeada la opcion de filtro por rango de folios habilitar los demas filtros
            $("#selProgI").removeAttr("disabled");
            $("#selProgF").removeAttr("disabled");
            $("#selDeptoI").removeAttr("disabled");
            $("#selDeptoF").removeAttr("disabled");
            $("#selPartidaI").removeAttr("disabled");
            $("#selPartidaF").removeAttr("disabled");
            $("#folioIni").val("");
            $("#folioFin").val("");
            $("#folioIni").attr("disabled", "disabled");
            $("#folioFin").attr("disabled", "disabled");
            $("#isConsultaxRangoFolios").val("N");
            LimpiaFoliosFiltroRep();

        }
    } else if (op == 4) {
        //validaciones de solo numeros
        var nav4 = window.Event ? true : false;
        // Backspace = 8, Enter = 13, ’0′ = 48, ’9′ = 57, ‘.’ = 46
        var key = nav4 ? evt.which : evt.keyCode;
        return (key <= 13 || (key >= 48 && key <= 57) || key == 46);
    }


}



function cargarFoliosFiltroReportesMonitoreoPresup() {
    var contReg = $("#contReg").val();
    var ramoSelect = "";
    var contRamoSelect = 0;
    var op = 1;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }

    var periodoIni = $("#periodoIni").val();
    var periodoFin = $("#periodoFin").val();
    if ($("#radioReporteRecalendarizacionesConsolidado").is(":Checked")) {
        op = 1;
    } else if ($("#radioReporteTransferencias").is(":Checked")) {
        op = 2;
    } else if ($("#radioReporteAmpliaciones").is(":Checked")) {
        op = 3;
    }

    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetFoliosFiltroRepMonitoreoPresup.jsp',
        dataType: 'html',
        data: {
            ramos: ramoSelect,
            op: op,
            periodoIni: periodoIni,
            periodoFin: periodoFin
        },
        success: function(responce) {
            $("#resultFolios").html("");
            $("#resultFolios").html(responce);
            $('#mensajeBuscaFolios').hide();

        }
    });
}

function allChecksRptFoliosRepMonPresup() {
    // var rangoDepto = $(".selRangoPrograma");
    var contReg = document.getElementById("contReg1").value;
    var RamoList = document.getElementById("contRegSelect1");
    var longRamoList = RamoList.value;
    var allChecks = document.getElementById("allChecks1");
    var labelCont = document.getElementById("labelCont1");

    var i = 0;
    for (i = 1; i <= contReg; i++) {
        var tempChecks = document.getElementById("checkk" + i);
        if (allChecks.checked)
            tempChecks.checked = true;
        else
            tempChecks.checked = false;
    }

    if (allChecks.checked == true) {
        labelCont.value = contReg + " Seleccionados";
        RamoList.value = contReg;
    } else {
        labelCont.value = "0 Seleccionados";
        RamoList.value = 0;
    }
    //$(".selRangoPrograma").prop("disabled", true);
    /*$(".selRangoPrograma").html("<option value='-1'>-- Seleccione un programa --</option>");
     if (typeof rangoDepto !== 'undefined') {
     $(".selRangoDepto").html("<option value='-1'>-- Seleccione un departamento --</option>");
     }*/
    $("#labelCont1").change();

}


function contCheckRptFolioRepMonPresup(IdCheck) {
    var contReg = $("#contReg1").val();
    var contRamoSelect = 0;
    var RamoList = document.getElementById("contRegSelect1");
    var ramoSelect = "";
    //var rangoDepto = $(".selRangoDepto");
    var longRamoList = RamoList.value;
    var checkRam = document.getElementById("checkk" + IdCheck);
    var labelCont = document.getElementById("labelCont1");

    if (checkRam.checked)
        longRamoList++;
    else
        longRamoList--;

    if (longRamoList == 1) {
        labelCont.value = longRamoList + " Seleccionado";
        //cargarProgramasReportes();
        //$(".selRangoPrograma").prop("disabled", false);
        //$(".selRangoPrograma").change();
        for (i = 1; i <= contReg; i++) {
            var tempChecks = $("#checkk" + i).val();
            if ($("#checkk" + i).is(':checked')) {
                var tempRamoChecks = $("#folioCheck" + i).val();
                if (contRamoSelect == 0)
                    ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
                else
                    ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
                contRamoSelect++;
            }
        }
        ramoSelect = ramoSelect.replaceAll("'", "");
        $("#folioInList").val(ramoSelect);
    } else {
        labelCont.value = longRamoList + " Seleccionados";
        // $(".selRangoPrograma").html("<option value='-1'>-- Seleccione un programa --</option>");
        // $(".selRangoPrograma").change();
    }


    RamoList.value = longRamoList;
    $("#labelCont1").change();
}


function LimpiaFoliosFiltroRep() {
    document.getElementById("chkXfolios").checked = false;
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxGetFoliosFiltroRepMonitoreoPresup.jsp',
        dataType: 'html',
        data: {
            ramos: -1,
            op: 1,
            periodoIni: 01,
            periodoFin: 01
        },
        success: function(responce) {
            $("#resultFolios").html("");
            $("#resultFolios").html(responce);
            $('#mensajeBuscaFolios').hide();

        }
    });


}

function generarReporteEstadisticaFirmaFoliosExcel() {
    var contReg = $("#contReg").val();
    var contReg1 = $("#contReg1").val();
    var ramoSelect = "";
    var nivelSelect = "";
    var contRamoSelect = 0;
    var contNivelSelect = 0;
    var mensajeError = "";
    /* var progI = $("#selProgI").val();
     var progF = $("#selProgF").val();*/
    var cont = $("#contRegSelect").val();
    //  $("#periodo").val(document.getElementById("inp-periodo").value);
    var i = 0;

    $("#reporttype").val("xls");
    for (i = 1; i <= contReg; i++) {
        var tempChecks = $("#check" + i).val();
        if ($("#check" + i).is(':checked')) {
            var tempRamoChecks = $("#ramoCheck" + i).val();
            if (contRamoSelect == 0)
                ramoSelect = ramoSelect + "'" + tempRamoChecks + "'";
            else
                ramoSelect = ramoSelect + "," + "'" + tempRamoChecks + "'";
            contRamoSelect++;
        }
    }


    if (contRamoSelect > 0) {
        $("#ramoInList").val(ramoSelect);
    } else {
        alert("Debe de seleccionar al menos un ramo para continuar");
        $("#ramoInList").val("-1");

    }



    for (i = 1; i <= contReg1; i++) {
        var tempChecks = $("#checks" + i).val();
        if ($("#checks" + i).is(':checked')) {
            var tempRamoChecks = ($("#NivelCheck1" + i).val());
            if (contNivelSelect == 0)
                nivelSelect = nivelSelect + "'" + tempRamoChecks + "'";
            else
                nivelSelect = nivelSelect + "," + "'" + tempRamoChecks + "'";
            contNivelSelect++;
        }
    }

    if (contNivelSelect > 0) {
        $("#NivelesInList").val(nivelSelect);
        $("#frmRptExcel").submit();
    } else {
        alert("Debe de seleccionar al menos un nivel para continuar");
        $("#NivelesInList").val("-1");

    }


}

function allChecksRptEstatusNivelRepEst() {
    var contReg = document.getElementById("contReg1").value;
    var estatusList = document.getElementById("contRegSelect1");
    var longEstatusList = estatusList.value;
    var allChecks = document.getElementById("allChecks1");
    var labelCont = document.getElementById("labelCont1");
    var NivelesSelect = "";

    var i = 0;
    for (i = 2; i <= contReg; i++) {
        var tempChecks = document.getElementById("checks" + i);
        if (allChecks.checked)
            tempChecks.checked = true;
        else
            tempChecks.checked = false;
    }

    if (allChecks.checked == true) {
        labelCont.value = contReg + " Seleccionados";
        estatusList.value = contReg;

    } else {
        labelCont.value = "0 Seleccionados";
        estatusList.value = 0;
    }

}


