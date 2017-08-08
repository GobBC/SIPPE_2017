/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function evaluarMovimiento(oficio){
    if(confirm("¿Está seguro que desea evaluar el oficio " + oficio + "?")){
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/evaluacion/ajaxEvaluaMovimiento.jsp',
            datatype: 'html',
            data: {
                oficio: oficio
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === '-1') {
                    $("#eva"+oficio).removeAttr("checked");
                    alert("Ocurrió un error al procesar la solicitud");
                } else {
                    if(res === '1'){
                        $("#eva"+oficio).attr("disabled",true);
                    }else{
                        alert(res);
                        $("#eva"+oficio).removeAttr("checked");
                    }
                }
            }
       });
    }else{
        $("#eva"+oficio).removeAttr("checked");
        return false;
    }
}

function evaluarCaratula(caratulaID){
    if(confirm("¿Está seguro que desea evaluar esta carátula?")){
        $.ajax({
            type: 'POST',
            url: 'librerias/ajax/evaluacion/ajaxEvaluaCaratula.jsp',
            datatype: 'html',
            data: {
                caratulaID: caratulaID
            },
            success: function(response) {
                $('#mensaje').hide();
                var res = trim(response.replace("<!DOCTYPE html>", ""));
                if (res === '-1') {
                    $("#eva"+caratulaID).removeAttr("checked");
                    alert("Ocurrió un error al procesar la solicitud");
                } else {
                    if(res === '1'){
                        $("#eva"+caratulaID).attr("disabled",true);
                    }else{
                        alert(res);
                        $("#eva"+caratulaID).removeAttr("checked");
                    }
                }
            }
       });
    }else{
        $("#eva"+caratulaID).removeAttr("checked");
        return false;
    }
}