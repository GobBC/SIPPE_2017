/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getComentarios(){
    var oficio = $("#inp-folio-usr").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/comentario/ajaxGetComentariosAutorizacion.jsp',
        datatype: 'html',
        data: {
            oficio: oficio
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else {
                if(res === '0'){
                    $("#dialog-tbl-comentario").dialog("open");
                    $("#numComentario").val(0)
                    $("#cancelEdit").addClass("hidden");
                }else{
                    $("#tbl-comentario tbody").html(res);
                    $("#dialog-tbl-comentario").dialog("open");
                    $("#numComentario").val(0)
                    $("#cancelEdit").addClass("hidden");
                }
            }
        }
    });
}

function updateComentarioAut(){
    var oficio = $("#inp-folio-usr").val();
    var comentario = $("#comentario").val();
    var numComentario = $("#numComentario").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/comentario/ajaxUpdateComentarioAut.jsp',
        datatype: 'html',
        data: {
            oficio: oficio,
            comentario: comentario,           
            numComentario: numComentario
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else if(res === "1|"){                
                getComentarios();
                $("#txt-area-coment").val(comentario);
                $("#comentarioAuto").val("");
                $("#cancelEdit").addClass("hidden");
            }else{
                alert("No se pudo actualizar el comentario");
                $("#numComentario").val(0);
                $("#cancelEdit").addClass("hidden");
                $("#dialog-tbl-comentario").dialog("close");
            }
        }
    });
}

function deleteComentarioAut(numComentario){
    var oficio = $("#inp-folio-usr").val();
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/comentario/ajaxUpdateComentarioAut.jsp',
        datatype: 'html',
        data: {
            oficio: oficio,
            borrar: 1,           
            numComentario: numComentario
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
            } else if(res.split("|")[0] === "1"){      
                $("#txt-area-coment").val(res.split("|")[1]);
                $("#cancelEdit").addClass("hidden");  
                getComentarios();
            }else{
                alert("No se pudo actualizar el comentario");
                $("#numComentario").val(0);
                $("#cancelEdit").addClass("hidden");
                $("#dialog-tbl-comentario").dialog("close");
            }
        }
    });
}

function editComentarioAut(numComentario){
    var oficio = $("#inp-folio-usr").val();
    var comentario = $("#comentario").val();    
    $.ajax({
        type: 'POST',
        url: 'librerias/ajax/comentario/ajaxgetComentarioAutEdicion.jsp',
        datatype: 'html',
        data: {
            oficio: oficio,
            comentario: comentario,           
            numComentario: numComentario
        },
        success: function(response) {
            var res = trim(response.replace("<!DOCTYPE html>", ""));
            if (res === "-1") {
                alert("Ocurrió un error al procesar la solicitud");
                $("#cancelEdit").addClass("hidden");
                $("#numComentario").val(0);
            } else{ 
                $("#comentarioAuto").val(res);
                $("#numComentario").val(numComentario);
                $("#cancelEdit").removeClass("hidden");
            }
        
        }
    });
}

function cancelEdicion(){
    $("#comentarioAuto").val("");
    $("#numComentario").val(0);
    $("#cancelEdit").addClass("hidden");
}