/*
*   Evento que cambia el texto en color rojo a un elemento input mask o numeric, cuando el número sea negativo.
*/
function changeValueFormatToRed()
{
    this.element.siblings('.k-input').removeClass('text-danger');

    if (this.value() !== null && this.value() < 0)
        this.element.siblings('.k-input').addClass('text-danger');
}

//Funcion para verificar si estan habilitadas las cookies en el navegador
function are_cookies_enabled()
{
    var cookieEnabled = (navigator.cookieEnabled) ? true : false;

    if (typeof navigator.cookieEnabled === "undefined" && !cookieEnabled)
    {
        document.cookie = "testcookie";
        cookieEnabled = (document.cookie.indexOf("testcookie") != -1) ? true : false;
    }

    return (cookieEnabled);
}

//Funcion para mostrar pagina de no habilitadas las cookies, para evitar navegacion
function showCookieFail()
{
    alert('Es necesario que habilite las cookies para el funcionamiento del sitio');
}

//funcion que permite encontrar el primer elemento mostrado en errores para hacer el foco
function onErrorOnForm() {
    var firstLi = $('.validation-summary-errors li:eq(0)');
    var input = $('input[name="' + firstLi.data('key') + '"]');
    var div = input.closest('.contenedorForma');

    if (div.attr('id') != null) {
        var tab = $('a[href="#' + div.attr('id') + '"]');

        if (tab != null) {
            tab.get(0).click();
        }
    }

    $.Framework.setFocusOnForm({ input: input })
}

function toggle_visibility(id)
{
    var e = document.getElementById(id);
    if (e.style.display === 'block')
        e.style.display = 'none';
    else
        e.style.display = 'block';
}

function createEventsToSwitchEjercicio() {
    
    $('.open-switcher').click(function () {
        if ($(this).hasClass('show-switcher')) {
            $('.switcher-box').css({ 'right': 0 });
            $('.open-switcher').removeClass('show-switcher');
            $('.open-switcher').addClass('hide-switcher');
        } else if (jQuery(this).hasClass('hide-switcher')) {
            $('.switcher-box').css({ 'right': '-212px' });
            $('.open-switcher').removeClass('hide-switcher');
            $('.open-switcher').addClass('show-switcher');
        }
    });

    $('.switcher-box input').on('change', function () {
        var ejerSelected = $('input[name=ejercicio]:checked').val();
        
        $.Framework.ajax({
            url: 'Home/SeleccionarEjercicio',
            data: { ejercicio: ejerSelected },
            done: function (response, textStatus, jqXHR) {
                if (response == true) {
                    $.Framework.showNotification({
                        text: 'Se ha seleccionado el Ejercicio ' + ejerSelected + ' correctamente'
                    });

                    location = $.Framework.resuelveUrl({ url: '/Home' });

                } else {
                    $.Framework.showNotification({
                        type: 'error',
                        text: 'Se presentó un problema al seleccionar el ejercicio ' + ejerSelected
                    });
                }
            }
        });
    });
}
//
//$(window).resize(function() {
//    //Ajustar contenido a ventana
//    var sidebarHeight = $('.sidebar').height();
//    var mainHeight = $('.main').height();
//    var calcOnScreenMain = window.innerHeight-$('header').outerHeight()-$('footer').outerHeight();
//
//    if(calcOnScreenMain > sidebarHeight)
//        $('.sidebar').css('min-height', calcOnScreenMain); 
//    else
//        $('.main').css('min-height', sidebarHeight);
//    
//    if(calcOnScreenMain > mainHeight) 
//        $('.main').css('min-height', calcOnScreenMain);
//    else
//        $('.sidebar').css('min-height', mainHeight); 
//    //----------------------------
//});

$(document).ready(function ()
{
    //Ajustar contenido a ventana
//    var sidebarHeight = $('.sidebar').height();
//    var mainHeight = $('.main').height();
//    var calcOnScreenMain = window.innerHeight-$('header').outerHeight()-$('footer').outerHeight();
//
//    if(calcOnScreenMain > sidebarHeight)
//        $('.sidebar').css('min-height', calcOnScreenMain); 
//    else
//        $('.main').css('min-height', sidebarHeight);
//    
//    if(calcOnScreenMain > mainHeight) 
//        $('.main').css('min-height', calcOnScreenMain);
//    else
//        $('.sidebar').css('min-height', mainHeight); 
    //----------------------------
    
    $.ajaxSetup({ cache: false });

    $(document).ajaxError(function (e, xhr, settings)
    {
        if (xhr.status == 401)
            location = $.Framework.resuelveUrl({ url: '/Seguridad/Cuenta/IniciarSesion' });
    });

    var result = are_cookies_enabled();

    if (!result)
        showCookieFail();

    //
    $.Framework = $.Framework || {};

    /*
    *   Método que genera un modal/ventana génerica con acciones.
    *   @param {Number} width               - Ancho de la ventana.
    *   @param {String} iconCss             - Es la clase que se pondra en el span del icono antes del titulo.
    *   @param {Number} height              - Altura de la ventana.
    *   @param {String} title               - Título de la ventana (Default: 'Aviso del Sistema').
    *   @param {String} content             - Contenido o mensaje de la ventana, permite Html.
    *   @param {Object[]} buttons           - Lista de botones, si no se envía el parámetro, por default dejará un botón de Aceptar que al dar click cerrará la ventana.
    *   @param {String} buttons[].classCss  - Clase Css para darle estilo al botón si es que se requiere.
    *   @param {String} buttons[].iconCss   - Icono Css que mostrará el botón.
    *   @param {String} buttons[].text      - Texto que tendrá el botón.
    *   @param {Function} buttons[].click   - Función que se realizará al dar click en el botón.
    *   @param {Object[]} positionModal     - Dentro de este objeto se pueden definir las posiciones en top, botton, left rigth, por default solo tiene top: "30px".
    *   @param {String} ModalId             - Es el Id del modal, si se quiere cambiar, por default el id es 'modal'
    *   @param {Boolean} centrarModal       - Indica si se desea centrar el modal en la pantalla tanto horizontal como vertical, se ignora el objeto positionModal, por Default el valor es false
    *   @param {Function} done              - Función que se realiza después de haber creado el modal.
    */
    $.Framework.showModal = function (options)
    {
        var windowWidth = window.screen.width < window.outerWidth ? window.screen.width : window.outerWidth;
        
        var options = $.extend({
            width: 350,
            iconCss: 'fa fa-warning text-yellow',
            height: null,
            title: 'Aviso del Sistema',
            content: '',
            buttons: [],
            positionModal: { top: "30px", left: "0px" },
            ModalId: 'modal',
            centrarModal: false,
            done: function () { }
        }, options);

        var marginLeft = (windowWidth / 2) - (options.width / 2);
        options.positionModal.left = marginLeft + "px";

        if ($('#' + options.ModalId).length > 0)
        {
            $('#' + options.ModalId).data('kendoWindow').close();
            $('#' + options.ModalId).remove();
        }

        var modal = $("<div id='" + options.ModalId + "'></div>");

        modal.appendTo("body").kendoWindow({
            modal: true,
            position: options.positionModal,
            resizable: false,
            visible: false,
            actions: [],
            title: '<span class="' + options.iconCss + '"></span> ' + options.title,
            width: options.width + "px",
            height: (options.height == null ? null : (options.height + 'px')),
            close: function (e) {
                this.destroy();
            }
        })
        .data('kendoWindow')
        .content('<div class="modal-message" style="padding:10px;line-height:1.35em"></div><div class="modal-buttons text-right" style="border-top:1px solid #eee;padding-top:10px;margin-top:10px"></div>');

        if (options.centrarModal == true)
            modal.data('kendoWindow').center().open();
        else
            modal.data('kendoWindow').open();

        modal.find('.modal-message').html(options.content);

        if (options.buttons.length <= 0)
        {
            modal.find('.modal-buttons').append('<button class="btn btn-success accept"><span class="fa fa-check"></span> Aceptar</button>');

            modal.find('.accept').click(function ()
            {
                modal.data('kendoWindow').close();
            });
        }
        else
        {
            $(options.buttons).each(function (idx, item)
            {
                modal.find('.modal-buttons').append(
                      '<button class="btn ' + item.classCss + ' btn-' + idx + '">'
                    + (item.iconCss == null ? '' : '<span class="' + item.iconCss + '"></span> ')
                    + item.text
                    + '</button> '
                );

                modal.find('.btn-' + idx).off('click').on('click', item.click);
            });
        }

        options.done();
    };

    /*
    * Metodo que permite realizar una accion anteponiendo una confirmacion
    *   @param {string} content               - Es el contenido que se le mostrara al usuario al mostrar la confirmación.
    *   @param {Function} funcionSi           - Es la accion que debera realizar en caso de que el usuario confirme
    */
    $.Framework.hacerAccionConConfirmacion = function (options) {
       var options = $.extend({
           content: '¿Esta seguro de realizar esta acción?',
           funcionSi: function () { }
       }, options);

       var ModalId = 'modalConfirm';

       if ($('#' + ModalId).length > 0) {
           $('#' + ModalId).data('kendoWindow').close();
           $('#' + ModalId).remove();
       }

       var modal = $("<div id='" + ModalId + "'></div>");
       var buttons = [{
               classCss: 'btn-primary',
               iconCss: 'fa fa-check',
               text: 'Sí',
               click: options.funcionSi
           },
           {
               classCss: 'btn-cerrar',
               iconCss: 'fa fa-ban',
               text: 'No',
               click: function (e) {
                   $("#" + ModalId).data('kendoWindow').close();
               }
           }];

       modal.appendTo("body").kendoWindow({
           modal: true,
           position: { top: "30px", left: "0px" },
           resizable: false,
           visible: false,
           animation: false,
           actions: [],
           title: 'Confirmar',
           width: "350px",
           height: null,
           close: function (e) {
               this.destroy();
           }
       })
       .data('kendoWindow')
       .content('<div class="modal-message" style="padding:10px;line-height:1.35em"></div><div class="modal-buttons text-right" style="border-top:1px solid #eee;padding-top:10px;margin-top:10px"></div>');

       modal.data('kendoWindow').center().open();
       modal.find('.modal-message').html(options.content);

       $(buttons).each(function (idx, item) {
           modal.find('.modal-buttons').append(
           '<button class="btn ' + item.classCss + ' btn-' + idx + '">'
               + (item.iconCss == null ? '' : '<span class="' + item.iconCss + '"></span> ')
               + item.text
               + '</button> '
       );

           modal.find('.btn-' + idx).off('click').on('click', item.click);
       });
    }
//    
//    /*
//    *   Metodo que permite obtener de una url relativa la url completa con protocolo, servidor y subdominio o folder.
//    *   @param {String} url - Url que se desea completar. Ej: '/MiArea/MiController/MiAccion'.
//    *   @returns {String}     http://111.111.111.111/MiArea/MiController/MiAccion ó http://111.111.111.111/NombreAplicacion/MiArea/MiController/MiAccion
//    */
//    $.Framework.resuelveUrl = function (options) {
//        var options = $.extend({
//            url: ''
//        }, options);
//
//        var urlFinal = '';
//
//        var sectionMain = $("#sectionMain");
//
//        if (sectionMain.length <= 0) {
//            alert('No esta definido un sectionMain con la Url Principal');
//            return;
//        }
//
//        urlFinal = sectionMain.data('urlbase');
//
//        if (options.url.substr(0, 1) == '/')
//            urlFinal += options.url;
//        else
//            urlFinal += '/' + options.url;
//
//        return urlFinal;
//    };
//
    /*
    *   Método para mostrar un mensaje de procesando/cargando y además se bloquea la pantalla.
    *   @param {String} text - Mensaje a mostrar (opcional) (default: "Procesando...").
    */
    $.Framework.startLoading = function (options)
    {
        var options = $.extend({
            text: 'Procesando...'
        }, options);

        if ($('#loading').length > 0)
            $('#loading').remove();

        var loading = $('<div id="loading" style="display:none"><div class="loading-body"><span class="loading-icon"></span> <span>' + options.text + '</span></div></div>');

        loading.appendTo('body');

        loading.height($(document).height());

        loading.show();

        loading.find('.loading-body').css({
            margin: '-' + (loading.find('.loading-body').height() / 2) + 'px 0 0 -' + (loading.find('.loading-body').width() / 2) + 'px'
        });
    };

    /*
    *   Método para limpiar cualquier mensaje de Loading activo.
    */
    $.Framework.endLoading = function ()
    {
        if ($('#loading').length > 0)
            $('#loading').remove();
    };

    /*
    *   Método que muestra una alerta en la parte superior derecha de la pantalla y que desaparece automáticamente.
    *   @param {String} text     - Mensaje de la notificación (permite html).
    *   @param {String} type     - Tipo/estilo del contenedor (opcional) (valores: success, warning, error, info).
    *   @param {Number} duration - Tiempo que estará visible la notificacion en milisegundos (opcional) (default: 4500).
    *   @param {Number} iconCss  - Clase(s) CSS necesarias para colocar un icono especial (opcional).
    */
    $.Framework.showNotification = function (options) {
       var options = $.extend({
           text: '',
           iconCss: '',
           type: 'success',
           duration: 4500
       }, options);

       if ($('#notification').length > 0)
           $('#notification').remove();

       var classCss = '';

       switch (options.type) {
           default:
           case 'success':
               classCss = 'alert-success';
               options.iconCss = (options.iconCss.length == 0 ? 'fa fa-check-circle' : options.iconCss);
               break;

           case 'warning':
               classCss = 'alert-warning';
               options.iconCss = (options.iconCss.length == 0 ? 'fa fa-exclamation-triangle' : options.iconCss);
               break;

           case 'error':
               classCss = 'alert-danger';
               options.iconCss = (options.iconCss.length == 0 ? 'fa fa-ban' : options.iconCss);
               break;

           case 'info':
               classCss = 'alert-info';
               options.iconCss = (options.iconCss.length == 0 ? 'fa fa-info-circle' : options.iconCss);
               break;
       }

       var notification = $(
       '<div id="notification" class="alertNotification ' + classCss + ' " role="alert" style="display:none">'
           + '<span class="' + options.iconCss + '" style="font-size:1.7em;vertical-align:middle"></span> ' + options.text + '</div>'
    );

       notification.appendTo("body");

       //Lo mostramos con un efecto.
       notification.show(800);

       //Lo desaparecemos con un efecto después del tiempo fijado.
       notification.delay(options.duration).fadeOut('slow');
    }

    /*
    *   Método para ejecutar ajax con funcionalidad por default. Ver documentación: http://api.jquery.com/jquery.ajax/
    *   @param {String} url         - Url del servicio, internamente se usará el método de resuelveUrl.
    *   @param {String} type        - Tipo de petición (opcional) (valores: GET, POST, PUT) (default: GET).
    *   @param {String} contentType - Tipo de dato que se envía al servidor (opcional) (default: "application/json").
    *   @param {String} dataType    - Tipo de dato que se espera de la petición (opcional) (valores: json, html, xml, script) (default: json).
    *   @param {Object} data        - Objeto con los parámetros de la petición.
    *   @param {Function} beforeSend - Funcionalidad antes de iniciar la petición (opcional).
    *   @param {Function} done      - Funcionalidad al terminar correctamente la petición (opcional).
    *   @param {Function} fail      - Funcionalidad al fallar la petición (opcional).
    *   @param {Function} always    - Funcionalidad al terminar la petición, sin importar si hubo problemas o no (opcional).
    */
    $.Framework.ajax = function (options)
    {
        var options = $.extend({
            url: '',
            type: 'GET',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: null,
            beforeSend: function (jqXHR, settings) { },
            done: function (response, textStatus, jqXHR) { },
            fail: function (jqXHR, textStatus) { },
            always: function (data, textStatus, errorThrown) { }
        }, options);

        //Si no se envió una URL, regresamos null.
        if (options.url.length <= 0)
            return false;

        $.ajax({
            url: options.url.indexOf("http") == 0 ? options.url : $.Framework.resuelveUrl({ url: options.url }),
            type: options.type,
            contentType: options.contentType,
            dataType: options.dataType,
            data: options.data,
            async: options.async,
            cache: false,
            beforeSend: function (jqXHR, settings)
            {
                //Si es el primer servicio, mostramos el loading.
                if ($.active <= 1)
                    $.Framework.startLoading();

                options.beforeSend(jqXHR, settings);
            }
        })
        .done(options.done)
        .fail(options.fail)
        .always(function (data, textStatus, errorThrown)
        {
            //Si es el último servicio en terminar, quitamos el loading.
            if ($.active <= 1)
                $.Framework.endLoading();

            options.always(data, textStatus, errorThrown);
        });
    };

    /*
    *   Método para la descarga de archivos por medio de un ajax. Los servicios deben ser GET.
    *   @param {String} url          - Url del servicio para la descarga, internamente se usará el método de resuelveUrl.
    *   @param {String} type         - Tipo de petición (opcional) (valores: GET, POST, PUT) (default: GET).
    *   @param {Object} data         - Objeto con los parámetros de la petición (opcional) (default: null).
    *   @param {Function} beforeSend - Funcionalidad antes de iniciar la petición (opcional).
    *   @param {Function} done       - Funcionalidad al terminar correctamente la petición (opcional).
    *   @param {Function} fail       - Funcionalidad al fallar la petición (opcional).
    *   @param {String} cookieName   - Nombre de la cookie que usará para verificar si se terminó la descarga (opcional) (default: "fileDownload").
    */
    $.Framework.fileDownload = function (options) {
        var options = $.extend({
            url: '',
            type: 'GET',
            data: null,
            beforeSend: function (url) { },
            done: function (url) { },
            fail: function (responseHtml, url) { },
            cookieName: 'fileDownload'
        }, options);

        //Si no hay ruta, terminamos con la función.
        if (options.url.length <= 0)
            return false;

        $.Framework.startLoading();

        $.fileDownload($.Framework.resuelveUrl({ url: options.url }),
        {
            httpMethod: options.type,
            data: options.data,
            cookieName: options.cookieName,
            prepareCallback: options.beforeSend
        })
        .done(function (url) {
            $.Framework.endLoading();

            $.Framework.showNotification({
                text: 'Se ha descargado correctamente su documento.'
            });

            options.done(url);
        })
        .fail(function (responseHtml, url) {
            $.Framework.endLoading();

            $.Framework.showNotification({
                type: 'error',
                text: 'No se ha podido descargar el documento que solicito, inténtelo más tarde.'
            });

            options.fail(responseHtml, url);
        });
    };

    /*
    *   Método realizar el foco en cualquier componente sea kendo o normal.
    *   @param {Object} input   - Es el elemento jquery sobre el cual se quiere poner el foco. 
    */
    $.Framework.setFocusOnForm = function (options)
    {
        var options = $.extend({
            input: $([])
        }, options);

        if (options.input.length <= 0)
            return;

        var role = options.input.data('role');

        if (role == null)
        {
            options.input.focus();
            return;
        }
        
        var dataKendo = '';
        switch (role)
        {
            case 'dropdownlist':
                dataKendo = "kendoDropDownList";
                break;
            case 'numerictextbox':
                dataKendo = "kendoNumericTextBox";
                break;
            case 'combobox':
                dataKendo = "kendoComboBox";
                break;
            case 'maskedtextbox':
                dataKendo = "kendoMaskedTextBox";
                break;
            case 'datepicker':
                dataKendo = "kendoDatePicker";
                break;
            case 'timepicker':
                dataKendo = "kendoTimePicker";
                break;
        }

        $('#' + options.input.attr('id')).data(dataKendo).focus();
    };

    /*
    *   Método inicializa los eventos de los botones para los menús de la izquierda y del encabezado.
    */
    $.Framework.initMenus = function ()
    {
        //Agrega el evento click al botón del encabezado de la página cuando es en dispositivos pequeños.
        $('.navbar-header button').off('click').on('click', function ()
        {
            var sidebar = $('.sidebar');
            if (sidebar.hasClass('hidden-xs'))
                sidebar.removeClass('hidden-xs');
            else
                sidebar.addClass('hidden-xs');
        });

        //Agrega el evento click a las opciones del menú de la izquierda.
        $('.sidebar a').off('click').on('click', function(event)
        {
            var self = $(this);
            var sidebar = self.closest('.sidebar');

            //Verificamos si tiene la clase 'menu-parent', que son los que tienen sub-menus.
            if (self.hasClass('menu-parent'))
            {
                event.preventDefault();
                //Cambiamos el icono de la flecha.
                self.find('.menu-child').toggleClass('fa-angle-right fa-angle-down');
                //Mostramos u ocultamos la lista del submenu.
                self.next('.sub-menu').slideToggle();
            }
            else
            {
                //Quitamos del menu la opción activa.
                sidebar.find('.menu-active').removeClass('menu-active');
                //Ponemos como activo el menú que se acaba de dar click.
                self.addClass('menu-active');

                if (self.parent().parent('.sub-menu').length > 0)
                    self.parent().parent('.sub-menu').prev().addClass('menu-active');
            }              
            
            //Ajustar contenido a ventana
            var sidebarHeight = $('.sidebar').height();
            var mainHeight = $('.main').height();
            var calcOnScreenMain = window.innerHeight-$('header').outerHeight()-$('footer').outerHeight();

            if(calcOnScreenMain > sidebarHeight){
                $('.sidebar').css('min-height', calcOnScreenMain); 
            }
            if(calcOnScreenMain > mainHeight) {
                $('.main').css('min-height', calcOnScreenMain);
            }
            //----------------------------
            
            $('.main').css('min-height', $('.nav-sidebar').height());
            $('.sidebar').css('min-height', $('.main').height());            
        });
    };

    createEventsToSwitchEjercicio();

    onErrorOnForm();
    $.Framework.initMenus();
});

function Home() {
    window.location = "menu.jsp";
}