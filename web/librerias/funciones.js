var aniosSource;
var mesesSource;

//Está funcion permite la captura de solo letras y numeros con espacio,
//puede ser utilizado para el RFC y Homoclave.
//Parametro                         e=Evento de la tecla
function soloTextoNumerosEspacio(e) {
    caracter = (document.all) ? e.keyCode : e.which;

    if (caracter >= 97 && caracter <= 122) //minusculas
        return true;
    if (caracter >= 65 && caracter <= 90)//mayusculas
        return true;
    if (caracter >= 48 && caracter <= 57) //numeros
        return true;
    if (caracter == 32)
        return true;
    if (caracter == 9 || caracter == 11 || caracter == 0 || caracter == 8)
        return true;
    if (caracter == 32 || caracter == 209 || caracter == 241 || caracter == 225 || caracter == 233 || caracter == 237 || caracter == 243 || caracter == 250 || caracter == 46)
        return true
    return false;
}

//Está funcion solo permite la captura de puras letras y espacios en blanco en los
//campos de texto.
//Parametro                         e=Evento de la tecla
function soloTexto(e) {
    caracter = (document.all) ? e.keyCode : e.which;

    if (caracter >= 97 && caracter <= 122) //minusculas
        return true;
    if (caracter >= 65 && caracter <= 90)//mayusculas
        return true;
    if (caracter == 32 || caracter == 9 || caracter == 11 || caracter == 0 || caracter == 8)
        return true;

    return false;
}

/************VALIDACIONES GENERICAS******************/
//Está funcion solo permitira la captura de numeros para la fecha y 
//de los simbolos que conformen el formato de la fecha por ejemplo:
//caracteres(simbolo=ascii) -=45,/=47
//Parametro                         e=Evento de la tecla
function soloFechas(e) {

    caracter = (document.all) ? e.keyCode : e.which;

    if (caracter >= 48 && caracter <= 57) //numeros
        return true;
    if (caracter == 47)
        return true;
    if (caracter == 9 || caracter == 11 || caracter == 0 || caracter == 8)
        return true;

    return false;
}

//Está funcion solo permite la captura de solo numeros en los campos de texto
//Parametro                         e=Evento de la tecla
function soloNumeros(e) {
    caracter = (document.all) ? e.keyCode : e.which;

    if (caracter >= 48 && caracter <= 57)//numeros
        return true;
    if (caracter == 9 || caracter == 11 || caracter == 0 || caracter == 8 || caracter == 46 || caracter == 44)
        return true;
    return false;
}

//Está funcion permite la captura de solo letras y numeros sin espacio,
//puede ser utilizado para el RFC y Homoclave.
//Parametro                         e=Evento de la tecla
function soloTextoNumeros(e) {
    caracter = (document.all) ? e.keyCode : e.which;

    if (caracter >= 97 && caracter <= 122) //minusculas
        return true;
    if (caracter >= 65 && caracter <= 90)//mayusculas
        return true;
    if (caracter >= 48 && caracter <= 57) //numeros
        return true;
    if (caracter == 9 || caracter == 11 || caracter == 0 || caracter == 8)
        return true;

    return false;
}
function mayuscula(caracter)
{
    var caracter_m = caracter.toUpperCase();
    return caracter_m;
}
function letra(caracter)
{
    if (caracter >= 97 && caracter <= 122) //minusculas
        return true;
    if (caracter >= 65 && caracter <= 90)//mayusculas
        return true;
    return false;
}
function numero(caracter)
{
    if (caracter >= 48 && caracter <= 57) //numeros
        return true;
    return false;
}
function letraynum(caracter)
{
    if (letra(caracter) == true || numero(caracter) == true) //letras y numeros
        return true;
    return false;
}
function textoynum(caracter)
{
    if (letra(caracter) == true || numero(caracter) == true) //letras y numeros
        return true;
    if (caracter == 32 || caracter == 209 || caracter == 241 || caracter == 225 || caracter == 233 || caracter == 237 || caracter == 243 || caracter == 250 || caracter == 46)
        return true
    return false;
}
function textoynumyenter(caracter)
{
    if (letra(caracter) == true || numero(caracter) == true) //letras y numeros
        return true;
    if (caracter == 32 || caracter == 209 || caracter == 241 || caracter == 225 || caracter == 233 || caracter == 237 || caracter == 243 || caracter == 250 || caracter == 46)
        return true
    if (caracter == 13) //letras y numeros
        return true;
    return false;
}

function valida_forma()
{
    var d = document.forms[0];

    if (d.strFecha.value == "")
    {
        alert("Debe Seleccionar Fecha a la que corresponde el Peri\u00f3dico");
        return("");
    }
    if (d.strIndice.value == "")
    {
        alert("Debe Introducir \u00cdndice");
        return("");
    }
    if (d.iPagina.value == "")
    {
        alert("Debe Introducir P\u00e1gina");
        return("");
    }
    if (d.strSeccion.value == "")
    {
        alert("Debe Introducir Secci\u00f3n");
        return("");
    }
    /*			if(d.iCons.value=="")
     {
     alert("Debe Introducir Consecutivo");
     return("");
     }*/
    var aux;
    aux = d.strIndice.value;
    d.strIndiceSalva.value = aux.replace(/'"/g, "'");
    aux = d.strSeccion.value;
    d.strSeccionSalva.value = aux.replace(/"/g, "'");
    aux = d.strIndice.value;
    d.strIndiceSalva.value = aux.replace(/'/g, "'||''''||'");
    aux = d.strSeccion.value;
    d.strSeccionSalva.value = aux.replace(/'/g, "'||''''||'");

    d.strAccion.value = "1";
    d.submit();
}



function calendario(file, window)
{
    msgWindow = open('', window, 'resizable=yes,width=280,height=230,screenX=400,screenY=300,top=300,left=400');
    msgWindow.location.href = file;
    if (msgWindow.opener == null)
        msgWindow.opener = self;
}

/*USUARIOS*/

function verifica_datos() {
    var d = document.forms[0];
    if (d.strUsuario.value == "") {
        alert("Debe Introducir Nombre de Usuario");
        return("");
    }
    if (d.strContrasena.value == "") {
        alert("Debe Introducir Contrase\u00f1a");
        return("");
    }
    
    
    /* -->se agrego para validar que el usuario tenga ramo y departamente al momento del login*/
    var tipoDependencia=d.TipoDepTMP.value.toUpperCase();
    var strUsuario = d.strUsuario.value.toUpperCase();
    var strContrasena =d.strContrasena.value.toUpperCase();
    var ban="0";
    
      $.ajax({
                        type: 'POST',
                        url: 'librerias/ajax/ajaxMensajeValidaRamoDeptoUsrLogin.jsp',
                        datatype: 'html',
                        async: false,
                        data: {
                            tipoDependencia:tipoDependencia,
                            strUsuario:strUsuario,
                            strEncrypt:strContrasena
                            
                        },
                        success: function(response) {
                            var res = trim(response.replace("<!DOCTYPE html>", ""));
                            if (res != "1") {
                               if(trim(res.toString())>0){
                                alert(res);
                                ban="1";
                                d.strContrasena.value="";}
                            }
                        }
                    });
    /*<<--*/
    
    //d.strEncrypt.value=hash(d.strContrasena.value);
    d.strEncrypt.value = d.strContrasena.value.toUpperCase();
    d.strAccion.value = "validar";
    if(ban=="0"){
    d.submit();}
}

function valida_usuario()
{
    var d = document.forms[0];

    if (d.strUsuario.value == "")
    {
        alert("Debe Introducir Clave de Usuario");
        return("");
    }
    if (d.strNombre.value == "")
    {
        alert("Debe Introducir Nombre del Usuario");
        return("");
    }
    if (d.strEstatus.value == "0")
    {
        alert("Debe Seleccionar Estatus");
        return("");
    }
    if (d.strCorreo.value == "")
    {
        alert("Debe Introducir Correo Electr\u00f3nico");
        return("");
    }
    d.strContrasena.value = generarContrasena();
    d.strEncrypt.value = d.strContrasena.value;
    //d.strEncrypt.value = hash(d.strContrasena.value);
    d.strAccion.value = "guardar";
    d.submit();
}

function trim(s) {
    // Remove leading spaces and carriage returns
    while ((s.substring(0, 1) == ' ') || (s.substring(0, 1) == '\n') || (s.substring(0, 1) == '\r'))
    {
        s = s.substring(1, s.length);
    }
    // Remove trailing spaces and carriage returns
    while ((s.substring(s.length - 1, s.length) == ' ') || (s.substring(s.length - 1, s.length) == '\n') || (s.substring(s.length - 1, s.length) == '\r'))
    {
        s = s.substring(0, s.length - 1);
    }
    return s;
}

function valida_usuario_actualiza()
{
    var d = document.forms[0];

    if (d.strNombre.value == "")
    {
        alert("Debe Introducir Nombre del Usuario");
        return("");
    }
    if (d.strEstatus.value == "0")
    {
        alert("Debe Seleccionar Estatus");
        return("");
    }
    if (d.strCorreo.value == "")
    {
        alert("Debe Introducir Correo Electr\u00f3nico");
        return("");
    }
    d.strAccion.value = "guardar";
    d.submit();
}



function generarContrasena()
{
    return nuevaContrasena();
}

function hash(pass)
{
    var code = 0;
    var acum = 0;
    var aux = 0;

    for (var i = 0; i < pass.length; i++)
    {
        code = pass.charCodeAt(i);
        if (i % 2 == 0)

        {
            aux = acum + code;
            acum = Math.sin(aux);
        }
        else
        {
            aux = acum + code;
            acum = Math.cos(aux);
        }
    }
    acum = acum * Math.round(Math.pow(10, 9));
    acum = Math.round(acum);
    return acum;
}

function getRandomNum(lbound, ubound)
{
    return (Math.floor(Math.random() * (ubound - lbound)) + lbound);
}

function getRandomChar() {
    var charSet = "23456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    return charSet.charAt(getRandomNum(0, charSet.length));
}

function nuevaContrasena() {
    var rc = "";
    for (var idx = 1; idx < 7; idx++)
    {
        rc = rc + getRandomChar();
    }
    return rc;
}

var color = "";

function uno(src, color_entrada)
{
    color = src.bgColor
    src.bgColor = color_entrada;
}
;

function dos(src, color_entrada)
{
    src.bgColor = color;
}
;


function valida_solicitar()
{
    var d = document.forms[0];

    if (d.strUsuario.value == "")
    {
        alert("Debe Introducir Clave de Usuario");
        return("");
    }
    d.strAccion.value = "solicitar";
    d.strContrasena.value = generarContrasena();
    //d.strEncrypt.value = hash(d.strContrasena.value);
    d.strEncrypt.value = d.strContrasena.value;
    d.submit();
}

function valida_cambio_contrasena()
{
    var d = document.forms[0];

    if (d.cont_anterior.value == "")
    {
        alert("Debe Introducir Contrase\u00f1a Anterior");
        return("");
    }
    if (d.cont_nueva.value == "")
    {
        alert("Debe Introducir Contrase\u00f1a Nueva");
        return("");
    }
    if (d.cont_conf_nueva.value == "")
    {
        alert("Debe Introducir Confirmaci\u00f3n de Contrase\u00f1a Nueva");
        return("");
    }
    if (d.cont_conf_nueva.value != d.cont_nueva.value)
    {
        alert("Contraseña Nueva no Coincide con Confirmaci\u00f3n de Contrase\u00f1a");
        return("");
    }
    d.strAccion.value = "cambiar";
    //d.strContrasena.value = hash(d.cont_anterior.value);
    d.strContrasena.value = d.cont_anterior.value
    //d.strEncrypt.value = hash(d.cont_nueva.value);
    d.strEncrypt.value = d.cont_nueva.value;
    d.submit();
}


function habilita(opcion) {
    var d = document.forms[0];

    d.opc.value = opcion;

    if (opcion == "1") //palabras clave
    {
        d.strPalabra1.disabled = false;
        d.strPalabra2.disabled = false;

        d.strFechaInicio.value = "";
        d.strFechaInicio.disabled = true;
        document.getElementById('fecini').style.visibility = 'hidden';

        d.strFechaFin.value = "";
        d.strFechaFin.disabled = true;
        document.getElementById('fecfin').style.visibility = 'hidden';
    }
    if (opcion == "2") //rango de fechas
    {
        d.strFechaInicio.disabled = false;
        d.strFechaFin.disabled = false;
        document.getElementById('fecini').style.visibility = 'visible';
        document.getElementById('fecfin').style.visibility = 'visible';

        d.strPalabra1.value = "";
        d.strPalabra1.disabled = true;
        d.strPalabra2.value = "";
        d.strPalabra2.disabled = true;
    }

    if (opcion == "3") //ambas
    {
        document.getElementById('fecini').style.visibility = 'visible';
        document.getElementById('fecfin').style.visibility = 'visible';

        d.strFechaInicio.disabled = false;
        d.strFechaFin.disabled = false;
        d.strPalabra1.disabled = false;
        d.strPalabra2.disabled = false;
    }

    if (opcion == "4") //palabra clave
    {
        d.strPalabra1.disabled = false;

        d.strFechaInicio.value = "";
        d.strFechaInicio.disabled = true;
        document.getElementById('fecini').style.visibility = 'hidden';

        d.strFechaFin.value = "";
        d.strFechaFin.disabled = true;
        document.getElementById('fecfin').style.visibility = 'hidden';

        d.strPalabra2.value = "";
        d.strPalabra2.disabled = true;
    }
}

function validaBusqueda()
{
    var d = document.forms[0];

    if (d.strFechaInicio.value != "" && d.strFechaFin.value == "")
    {
        alert("Debe Seleccionar  Fecha Final de la Consulta");
        return("");
    }
    if (d.strFechaFin.value != "" && d.strFechaInicio.value == "")
    {
        alert("Debe Seleccionar  Fecha Inicial de la Consulta");
        return("");
    }
    if (d.strPalabra1.value == "" && d.strPalabra2.value == "" && d.strFechaInicio.value == "" && d.strFechaFin.value == "")
    {
        var respuesta = confirm("No ha Seleccionado Ninguna Opci\u00f3n de B\u00fasqueda por lo que se Mostrar\u00f3n todos los Registros Almacenados. ¿Desea Cont\u00ednuar?")
        if (!respuesta)
            return("");
    }

    d.action = "reporte.jsp";
    d.strUrlConsulta.value = "reporte.jsp-strTipoIndice=" + d.tipo_indice_opc.value + "*opcion=" + d.opc.value + "*strFechaInicio=" + d.strFechaInicio.value + "*strFechaFin=" + d.strFechaFin.value + "*strPalabra1=" + d.strPalabra1.value + "*strPalabra2=" + d.strPalabra2.value;
    d.submit();

    /*
     if(valida_radiobutton(d.opcion)=="0") 
     {
     //				alert("Debe Seleccionar Opci\u00f3n de Consulta");
     //				return("");
     var respuesta = confirm("No ha Seleccionado Ninguna Opci�n de B�squeda por lo que se Mostrar�n todos los Registros Almacenados. �Desea Continuar?")
     if(!respuesta)
     return("");
     }		
     if(valida_radiobutton(d.opcion)=="1") //palabras clave
     {
     if(d.strPalabra1.value=="")
     {
     alert("Debe Introducir Palabra Clave 1 a Buscar");
     return("");
     }
     if(d.strPalabra2.value=="")
     {
     alert("Debe Introducir Palabra Clave 2 a Buscar");
     return("");
     }
     }
     if(valida_radiobutton(d.opcion)=="2") //rango de fechas
     {
     if(d.strFechaInicio.value=="")
     {
     alert("Debe Seleccionar  Fecha de Inicio de la Consulta");
     return("");
     }
     if(d.strFechaFin.value=="")
     {
     alert("Debe Seleccionar  Fecha Final de la Consulta");
     return("");
     }
     }
     if(valida_radiobutton(d.opcion)=="3") //ambos
     {
     if(d.strFechaInicio.value=="")
     {
     alert("Debe Seleccionar  Fecha de Inicio de la Consulta");
     return("");
     }
     if(d.strFechaFin.value=="")
     {
     alert("Debe Seleccionar  Fecha Final de la Consulta");
     return("");
     }
     if(d.strPalabra1.value=="")
     {
     alert("Debe Introducir Palabra Clave 1 a Buscar");
     return("");
     }
     if(d.strPalabra2.value=="")
     {
     alert("Debe Introducir Palabra Clave 2 a Buscar");
     return("");
     }
     }
     if(valida_radiobutton(d.opcion)=="4") //palabra clave
     {
     if(d.strPalabra1.value=="")
     {
     alert("Debe Introducir Palabra Clave 1 a Buscar");
     return("");
     }
     }
     
     d.action="reporte.jsp";
     d.strUrlConsulta.value="reporte.jsp-strTipoIndice="+d.tipo_indice_opc.value+"*opcion="+d.opc.value+"*strFechaInicio="+d.strFechaInicio.value+"*strFechaFin="+d.strFechaFin.value+"*strPalabra1="+d.strPalabra1.value+"*strPalabra2="+d.strPalabra2.value;
     d.submit();
     */
}

function verificaFechaAlerta(strFecha, opcionFecha)
{
    var dia = "";
    var mes = "";
    var strAnio = "";
    if (strFecha != "")
    {
        dia = strFecha.substring(0, 2);
        mes = strFecha.substring(3, 5);
        strAnio = strFecha.substring(6, 10);

        var fechaElegida = new Date(strAnio, mes - 1, dia, 23, 59);
        var fechaInicial;
        var fechaFinal;


        if (opcionFecha == "1") { //me dieron la strFecha inicial

            var fechaNueva = window.opener.document.forms[0].strFechaFin.value;

            if (fechaNueva != "") {
                diaAux = fechaNueva.substring(0, 2);
                mesAux = fechaNueva.substring(3, 5);
                anioAux = fechaNueva.substring(6, 10);

                fechaFinal = new Date(anioAux, mesAux - 1, diaAux, 23, 59);
                fechaInicial = fechaElegida;


                if (fechaFinal < fechaInicial) {
                    alert("La Fecha Final no puede ser Anterior a la Fecha Inicial");
                    return("");
                }
            }
        }
        if (opcionFecha == "2") { //me dieron la strFecha final

            var fechaNueva = window.opener.document.forms[0].strFechaInicio.value;

            if (fechaNueva != "") {
                diaAux = fechaNueva.substring(0, 2);
                mesAux = fechaNueva.substring(3, 5);
                anioAux = fechaNueva.substring(6, 10);

                fechaInicial = new Date(anioAux, mesAux - 1, diaAux, 23, 59);
                fechaFinal = fechaElegida;

                if (fechaFinal < fechaInicial) {
                    alert("La Fecha Final no puede ser Anterior a la Fecha Inicial");
                    return("");
                }
            }
        }
    }
    return(strFecha);
}

function valida_radiobutton(button) {
    opcion = "0";
    for (i = button.length - 1; i > -1; i--)
    {
        if (button[i].checked)
        {
            opcion = button[i].value;
        }
    }
    return opcion;
}

function imprimir()
{
    document.getElementById('botones').style.visibility = 'hidden';
    print();
    document.getElementById('botones').style.visibility = 'visible'
}

function funcionCalendario(opcion)
{
    var mes = document.form1.mes.value;
    var liga;
    var strAnio;
    if (opcion == "uno")
        strAnio = document.form1.strAnio.value;
    if (opcion == "dos")
        strAnio = document.form1.anio2.value;
    if (opcion == "tres")
        strAnio = document.form1.anio3.value;
    if (strAnio == "")
        strAnio = document.form1.strAnioActual.value;
    liga = "javascript:calendario('librerias/calendario.jsp?strAnio=" + strAnio + "&mes=" + mes + "&strFecha=" + opcion + "')";
    document.getElementById('calendarioid' + opcion).href = liga;
}


function asigna_valor(valor)
{
    if (valor == true)
        document.form1.strPublicarValor.value = "1";
    else
        document.form1.strPublicarValor.value = "0";
}

function asigna_valorPresentar(valor)
{
    if (valor == true)
        document.form1.strPresentarValor.value = "1";
    else
        document.form1.strPresentarValor.value = "0";
}

function textCounter(field, countfield, maxlimit) {
    if (field.value.length > maxlimit)
        field.value = field.value.substring(0, maxlimit);
    else
        countfield.value = maxlimit - field.value.length;
}
/*INICIA VALIDACION F5*/

var asciiF5 = 116;

function interceptKeyDown(e) {
    keyCode = e.keyCode;
    if (keyCode == asciiF5) {
        e.keyCode = 505;
        return false;
    }
    return true;
}

attachEventListener(document, "keydown", interceptKeyDown, true);

function interceptKeyPress(e) {
    if (!e) {
        if (window.event)
            e = window.event;
        else
            return;
    }

    //NS 4, NS 6+, Mozilla 0.9+, Opera
    if (typeof (e.which) == 'number') {
        var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : void 0;
        if (e.charCode == null || e.charCode == 0) {
            if (keyCode == asciiF5) {
                e.stopPropagation();
                e.preventDefault();
            }
        }

    }
}

attachEventListener(document, "keypress", interceptKeyPress, true);

function attachEventListener(obj, type, func, capture) {
    if (window.addEventListener) { // Mozilla, Netscape, Firefox
        obj.addEventListener(type, func, capture);
    } else { // IE
        obj.attachEvent('on' + type, func);
    }
}

/*TERMINA VALIDACION F5*/



function getMesDescripcion(mes) {
    var iMes = parseInt(mes, 10);
    var strMes = "";
    switch (iMes) {
        case 1:
            strMes = "ENERO";
            break;
        case 2:
            strMes = "FEBRERO";
            break;
        case 3:
            strMes = "MARZO";
            break;
        case 4:
            strMes = "ABRIL";
            break;
        case 5:
            strMes = "MAYO";
            break;
        case 6:
            strMes = "JUNIO";
            break;
        case 7:
            strMes = "JULIO";
            break;
        case 8:
            strMes = "AGOSTO";
            break;
        case 9:
            strMes = "SEPTIEMBRE";
            break;
        case 10:
            strMes = "OCTUBRE";
            break;
        case 11:
            strMes = "NOVIEMBRE";
            break;
        case 12:
            strMes = "DICIEMBRE";
            break;
    }
    return strMes;
}

function verificaExtension(archivo) {
    var re = /\..+$/;
    var ext = archivo.substr((archivo.lastIndexOf('.') + 1));
    ext = ext.toString().toLowerCase();
    var extValida = new Array('pdf');
    var blValida = false;
    for (var i = 0; i < extValida.length; i++) {
        if (ext == extValida[i])
            blValida = true;
    }
    return blValida;
}



function guardarPublicacion() {
    $("#resultado").html("")
    if ($("#tomo").val() == "" || $("#numero").val() == ""
            || $("#seccion").val() == "0" || $("#fechaPub").val() == "") {
        mensajes.getMsgFormatoError("resultado", "Favor de capturar todos los datos.");
        return false;
    } else if ($("input[name='tieneDocto']:checked").val() == "S" && $("#documento").val() == "") {
        mensajes.getMsgFormatoError("resultado", "Favor de seleccionar el documento a publicar.");
        return false;
    } else if ($("input[name='tieneDocto']:checked").val() == "S" && !verificaExtension($("#documento").val())) {
        mensajes.getMsgFormatoError("resultado", "El tipo de documento es invalido.");
        return false;
    }


    $("#section-form").hide();
    mensajes.mostrarAjaxLoad("imagenes/cargandoAjax.gif", "resultado", "Actualizando informaci&oacute;n...");
    setTimeout(function() {
        $("#form2").attr("action", "AlmacenaInformacion")
        $("#form2").submit();
    }, 1000)

    return true;
}

function modificaPublicacion(publicacion) {
    $("#idPublicacion").val(publicacion)
    $("#form1").submit();
}

function eliminarPublicacion(publicacion) {
    if (confirm("Est\u00e1 seguro que desea eliminar esta publicaci\u00f3n?") === true) {
        $("#idPeriodico").val(publicacion);
        $("#frmEliminar").submit();
    }
}

function importarDocumentos() {
    //if(confirm("&iquest; Est\u00e1 seguro que desea importar los documentos?") === true){
    $("#frmImportar").submit();
    //}
}

function salirSistema() {
    $("#form2").attr("action", "logout.jsp");
    $("#form2").attr("target", "_parent");
    $("#form2").submit();
}

function nuevaPublicacion() {
    $("#idPublicacion").val(0);
    $("#form2").attr("action", "capturaPublicacion.jsp")
    $("#form2").submit();
}

function refreshIframe() {
    parent.window.location.reload();
    return true;
}


function muestraPublicaciones(anio, mes) {
    $("#popupContainer").bPopup({escClose: true, closeClass: 'popup-close' });
    $.ajax({
        type: "POST",
        url: "librerias/ajax/ajaxConsultaPublicaciones.jsp",
        dataType: "html",
        data: {'anio': anio, 'mes': mes},
        beforeSend: function() {
            mensajes.mostrarAjaxLoad("imagenes/cargandoAjax.gif", "mensajeDialog", "Consultando Informacion....");
        },
        success: function(response) {
            $("#mensajeDialog").html(response)
        }
    });
}

function cerrarPopup() {
    $("#popupContainer").bPopup().close();
}

function inicializarConsulta() {
    $.ajax({
        type: "POST",
        url: "librerias/ajax/ajaxConsultaDatosDisponibles.jsp",
        dataType: "html",
        beforeSend: function() {
            mensajes.mostrarAjaxLoad("imagenes/cargandoAjax.gif", "contenidoPorAnio", "Consultando Informaci&oacute;n...");
        },
        success: function(response) {
            if (response != "-1" && response != "0") {
                $("#contenidoPorAnio").html("");
                var json = jQuery.parseJSON(response);
                aniosSource = json.anios;
                mesesSource = json.meses;
                inicializaAnios();
                generaContenidoXAnio();

            }

        }
    });
}

function displayUpload(opcion) {
    if (opcion) {
        $("#seccionUpload").show();
        $("#notaUpload").hide();

    } else {
        $("#documento").val("");
        $("#seccionUpload").hide();
        $("#notaUpload").show();
    }
}
function getCantPrincipal(anio) {
    var decima = parseInt(anio.substring(2), 10);
    var cant = 0;
    if (decima % 10 != 0) {
        cant = decima % 10;
    }
    return cant;
}

function getUltimaDecada(anio, cant) {
    var iAnio = parseInt(anio);
    var iCant = parseInt(cant);

    return iAnio - iCant;
}

function filtrarReportesTransparecia(tipo,bd){
    limpiaValRepsTransp();
   // alert('inicio')
    if(tipo==1){
        /*chkRptClasifAdmin*/
        if(bd=="SPPD"){
            //PARA LA CENTRAL
            $("#tipoConcentrado").show()
            $("#filtroClasifAdmin").show();
            $("#tipoAnual").hide();
            $("#divCombo").hide();
            $("#filtrosRpt").hide();
            $("#filtroClasifFunc").hide();
        }else if(bd=="PROGD"){
            //PARA PARAESTATALES
            $("#tipoConcentrado").show()
            $("#filtroClasifAdmin").hide();
            $("#tipoAnual").hide();
            $("#divCombo").show();
            $("#filtrosRpt").show();
            $("#filtroClasifFunc").hide();
        }
    }else if(tipo==2){
        /*chkRptClasifEcon*/
        $("#tipoConcentrado").hide();
        $("#tipoAnual").hide();
        $("#filtroClasifAdmin").hide();
         $("#divCombo").hide();
        $("#filtrosRpt").hide();
         $("#filtroClasifFunc").hide();
    }else if(tipo==3){
        /*chkRptPptoClasifFunc*/
        $("#tipoConcentrado").show()
        $("#tipoAnual").hide()
        $("#filtroClasifAdmin").hide();
         $("#divCombo").hide();
        $("#filtrosRpt").hide();
         $("#filtroClasifFunc").show();
    }else if(tipo==4){
        /*chkRptClasifObjGto*/
        $("#tipoConcentrado").show()
        $("#tipoAnual").hide()
        $("#filtroClasifAdmin").hide();
         $("#divCombo").show();
        $("#filtrosRpt").show();
         $("#filtroClasifFunc").hide();
    }else if(tipo==5){
        /*chkCalendario*/
        $("#tipoConcentrado").hide()
        $("#tipoAnual").show()
        $("#filtroClasifAdmin").hide();
         $("#divCombo").show();
        $("#filtrosRpt").show();
         $("#filtroClasifFunc").hide();
    }
    
}

function validaMilesPesos(){
    /*para reportes de transparencia*/
    
    //alert('i');
   if($("#Miles").checked){
       $("#MilesPesos").val("S");
   }else{
       $("#MilesPesos").val("N");
   }
 
    var valor=$("#MilesPesos").val(); 
  //alert(valor);
    
}


 function validaClasifAdmin(){
       var TEI=$("#selTEI").val(); 
       var TEF=$("#selTEF").val(); 
       if((TEI>TEF) && (TEF!="-1")){
           alert("El tipo ente Inicial no puede ser mayor que el final");
           $("#selTEF").val("-1"); 
       } 
        
    }
    
    
    
 function validaClasiFunc(){
       var F1=$("#selFinalidad1").val(); 
       var F2=$("#selFinalidad2").val(); 
       var Fun1=$("#selFuncion1").val();
       var Fun2=$("#selFuncion2").val();
       var S1=$("#selSubFuncion1").val();
       var S2=$("#selSubFuncion2").val();
       if((F1>F2) && (F2!="-1")){
           alert("La Finalidad Inicial no puede ser mayor que la final");
           $("#selFinalidad2").val("-1"); 
           return false;
       } 
       if((Fun1>Fun2) && (Fun2!="-1")){
           alert("La Funci\u00f3n Inicial no puede ser mayor que la final");
           $("#selFuncion2").val("-1");
           return false
       } 
       
       if((S1>S2) && (S2!="-1")){
           alert("La Subfunci\u00f3n Inicial no puede ser mayor que la final");
           $("#selSubFuncion2").val("-1"); 
           return false;
       } 
       
       if(F1=="-1"){alert("La Finalidad inicial es requerida");return false;}
       if(F2=="-1"){alert("La Finalidad final es requerida");return false;}
       if(F1==F2 && (F2!="-1" && F1!="-1")){
        if(Fun1=="-1"){alert("La funci\u00f3n inicial es requerida"); return false;}
        if(Fun2=="-1"){alert("La funci\u00f3n final es requerida"); return false;}
       }
       if(Fun1==Fun2 && (Fun2!="-1" && Fun1!="-1")){
        if(S1=="-1"){alert("La Subfunci\u00f3n inicial es requerida"); return false;}
        if(S2=="-1"){alert("La Subfunci\u00f3n final es requerida"); return false;}
       }
       
       return true;
       
      
       
    }   
    
    
    
function validaRepsTransparencia(bd){
    var p1="";
    var p2="";
     
    
    if(!document.getElementById("chkRptClasifEcon").checked){
    if(document.getElementById("chkRptClasifAdmin").checked){
        /* alert(bd);*/
        if(bd=="SPPD"){
            var TEI=$("#selTEI").val(); 
            var TEF=$("#selTEF").val(); 
            if(TEI=="-1"){alert("Seleccione un Tipo Ente Inicial"); return false;
            }else if(TEF=="-1"){alert("Seleccione un Tipo Ente Final"); return false;
            }else{return true;}
      }else if(bd="PROGD"){
        p1=$("#selPartIni").val();
        p2=$("#selPartFin").val();
        if(p1=="-1"){alert("La partida inicial es requerida"); return false;}
        if(p2=="-1"){alert("La partida final es requerida"); return false;}
        if(p1>p2 && p2!="-1"){alert("La partida inicial no debe ser mayor a la final"); p2=$("#selPartFin").val("-1"); return false;}
     
       return true;
       
       }
    }
    
    if(document.getElementById("chkRptPptoClasifFunc").checked){
        //alert('2');
        var val1=validaClasiFunc();
        return val1;
    }
   if(document.getElementById("chkRptClasifObjGto").checked || document.getElementById("chkCalendario").checked){
     var p1=$("#selPartIni").val();
     var p2=$("#selPartFin").val();
     
     if(p1=="-1"){alert("La partida inicial es requerida"); return false;}
     if(p2=="-1"){alert("La partida final es requerida"); return false;}
     if(p1>p2 && p2!="-1"){alert("La partida inicial no debe ser mayor a la final"); p2=$("#selPartFin").val("-1"); return false;}
       
   }
    }else{return true;}
  
    
    
}   

function limpiaValRepsTransp(){
    $("#selTEI").val("-1");
    $("#selTEF").val("-1");
    $("#selFinalidad1").val("-1");
    $("#selFinalidad2").val("-1");
    $("#selFuncion1").val("-1");
    $("#selFuncion2").val("-1");
    $("#selSubFuncion1").val("-1");
    $("#selSubFuncion2").val("-1");
    $("#selProgramaI").val("-1");
    $("#selProgramaF").val("-1");
    $("#selDeptoI").val("-1");
    $("#selDeptoF").val("-1");
    $("#selPartIni").val("-1");
    $("#selPartFin").val("-1");
    
    
}