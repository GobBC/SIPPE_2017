
(function($) {
var opciones;
    $.fn.calendario = function(opciones_usuario){

        opciones = $.extend($.fn.calendario.opciones_default,opciones_usuario)

        this.each(function calendarioUI(){
            var elemento = $(this).attr("id")


            $.datepicker.setDefaults({
                beforeShowDay:$.fn.calendario.diasNoLaborales,
                dateFormat:opciones.formatoFecha,
                altFormat:opciones.formatoFecha,
                minDate: opciones.diasMinimos,
                maxDate:opciones.diasMaximos,
                showOn: opciones.mostrarSobre,
                buttonImage: opciones.imagen,
                buttonImageOnly: opciones.boton,
                prevText:opciones.anteriorTexto,
                nextText:opciones.siguienteTexto,
                textoHoy:opciones.hoyTexto,
                buttonText:opciones.botonTexto,
                changeYear:opciones.cambiarAnio,
                changeMonth:opciones.cambiarMes,
                duration:opciones.duracion
            })

            $("#"+elemento+"").datepicker($.datepicker.regional[opciones.idioma]);
        });
    };

    $.fn.calendario.setDefaults=function(opciones){
        $.fn.calendario.opciones_default = $.extend($.fn.calendario.opciones_default,opciones)
    }

    $.fn.calendario.opciones_default = {
        formatoFecha: "dd/mm/yy",
        diasMinimos: new Date("01/01/1900"),
        diasMaximos: new Date("31/12/2050"),
        imagen: "imagenes/calendario.gif",
        boton: true,
        botonTexto: "Calendario",
        sabados: false,
        domingos: false,
        anteriorTexto:"Anterior",
        siguienteTexto:"Siguiente",
        hoyTexto:"Hoy",
        mostrarSobre:'button',
        cambiarAnio:false,
        cambiarMes:false,
        duracion:'slow',
        idioma:'es',
        diasInhabiles:false,
        anioDiaInhabil:0
    };



    $.fn.calendario.diasInhabiles = [];

    $.fn.calendario.diasFestivos = function(date) {
        for (i = 0; i < $.fn.calendario.diasInhabiles.length; i++) {
            if (date.getMonth() == $.fn.calendario.diasInhabiles[i][0] - 1
                && date.getDate() == $.fn.calendario.diasInhabiles[i][1]) {
                return [false,'','D&iacute;a no laboral'];
            }
        }
        return [true, ''];
    };

    $.fn.calendario.diasNoLaborales = function(date) {
        if(opciones.diasInhabiles){
            var noWeekend = $.datepicker.noWeekends(date);
            if (noWeekend[0]) {
                return $.fn.calendario.diasFestivos(date);
            } else {
                return $.fn.calendario.noFinSemana(date);
            }
        }else{
            return [true,'']
        }
    };

    $.fn.calendario.noFinSemana = function(date){
        var day = date.getDay();
        if($.fn.calendario.opciones_default.sabados && $.fn.calendario.opciones_default.domingos){
            if(day > 0&&day<6)
                return $.fn.calendario.diasFestivos(date)
            else
                return [false, ''];

        }else if($.fn.calendario.opciones_default.sabados && !$.fn.calendario.opciones_default.domingos){
            if(day<6)
                return $.fn.calendario.diasFestivos(date)
            else
                return [false, ''];
        }else if(!$.fn.calendario.opciones_default.sabados && $.fn.calendario.opciones_default.domingos){
            if(day > 0)
                return $.fn.calendario.diasFestivos(date)
            else
                return [false, ''];
        }else if(!$.fn.calendario.opciones_default.sabados && !$.fn.calendario.opciones_default.domingos){
            return $.fn.calendario.diasFestivos(date)
        }
    };

    $.fn.calendario.diasInhabilesPersonalizado=function(texto){
        var fechas = texto.split("&")
        var fechasFinales = new Array(fechas.length-1)


        for(i = 0 ; i < fechas.length-1 ; i++){
            var temp = fechas[i].split("-")
            fechasFinales[i] = temp;
        }

        $.fn.calendario.diasInhabiles = fechasFinales;
    };

})(jQuery);

