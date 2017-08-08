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

function reiniciarSecuencias(){
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/ajaxRebootMovoficiosSequence.jsp',
        datatype: 'html',
        success: function(response) {
            $('#mensaje').hide();
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            var mensaje = res.split("|");
            if(mensaje[0] !== '-1')
                alert("El proceso terminó correctamente");
            else{
                if(mensaje.length > 1)
                    alert(mensaje[1]);
                else
                    alert("Ocurrió un error al procesar");
            }
        }
    });
}