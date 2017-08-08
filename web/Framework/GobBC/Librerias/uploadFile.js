
$.Framework = $.Framework || {};
$.Framework.UploadFile = $.Framework.UploadFile || {};

/*
*   Método/evento al seleccionar un archivo para subirlo. Válida que no se pase del máximo de archivos permitidos.
*   @param {Object} event   - Objeto del evento Select de Kendo Upload.
*/
$.Framework.UploadFile.onSelect = function (event)
{
    var maxFilesAllowed = this.element.data('maxfilesallowed');

    //Sólo validamos si la cantidad de archivos permitidos es mayor a 0.
    if (maxFilesAllowed > 0)
    {
        //Contamos los archivos que ya se listan en el componente.
        if ((this.element.closest('.k-upload').find('.k-upload-files li').length + 1) > maxFilesAllowed)
        {
            $.Framework.showModal({
                content: 'Sólo se permiten máximo <strong>' + maxFilesAllowed + '</strong> archivos.'
            });
            event.preventDefault();
        }
    }
}

/*
*   Método/evento que se ejecuta antes de subir un archivo.
*   @param {Object} event   - Objeto del evento onUpload de Kendo Upload.
*/
$.Framework.UploadFile.onUpload = function (event)
{
    $.Framework.startLoading();

    //Ponemos los filtros extras si es que se configuraron.
    var extraFilters = this.element.data('extrafilters');
    if (extraFilters != null)
    {
        event.data = extraFilters;
    }
}

/*
*   Método/evento subir un archivo correctamente.
*   @param {Object} event   - Objeto del evento Success de Kendo Upload.
*/
$.Framework.UploadFile.onSuccess = function (event)
{
    //Revisamos que la operación sea Upload, que significa que subió el archivo correctamente.
    if (event.operation == "upload")
    {
        $.Framework.showNotification({
            text: 'Se ha guardado el archivo correctamente.'
        });

        //Recargamos todos los archivos.
        $.Framework.UploadFile.getAllFiles(this.wrapper);
    }
}

/*
*   Método/evento al ocurrir un error en alguno de los servicios.
*   @param {Object} event   - Objeto del evento OnError de Kendo Upload.
*/
$.Framework.UploadFile.onError = function (event)
{
    //Revisamos que la operación que falló, sea el upload o remove.
    if (event.operation == "upload" || event.operation == 'remove')
    {
        //Eliminamos el archivo que se intento subir, para que no quede en el listado de archivos.
        this.wrapper.find('.k-file-error').remove();

        $.Framework.showNotification({
            type: 'error',
            text: event.XMLHttpRequest.responseText
        });
    }
}

/*
*   Método/evento al terminar de subir un archivo.
*   @param {Object} event   - Objeto del evento Complete de Kendo Upload.
*/
$.Framework.UploadFile.onUploadComplete = function (event)
{
    //Quitamos el mensaje de "Procesando...".
    $.Framework.endLoading();
}

/*
*   Método que obtiene todos los archivos por medio de ajax para ponerlos dentro del listado del elemento Upload.
*   @param {Object} upload   - Objeto jQuery del elemento upload.
*/
$.Framework.UploadFile.getAllFiles = function (upload)
{
    //Si no esta el listado de archivos subidos, lo agregamos. Y si sí está, limpiamos su contenido.
    if (upload.find('.k-upload-files').length <= 0)
    {
        upload.append('<ul class="k-upload-files k-reset"></ul>');
    }
    else
    {
        upload.find('.k-upload-files').empty();
    }

    //Recargamos por medio del ajax, todos los archivos subidos y lo ponemos en el listado del elemento upload.
    $.Framework.ajax({
        url: upload.find('[type="file"]').data('getall'),
        data: upload.find('[type="file"]').data('extrafilters'),
        done: function (response, textStatus, jqXHR)
        {
            $(response).each(function (idx, item)
            {
                var html = $('#' + upload.find('[type="file"]').data('templateid')).html();
                html = html.replace('#=name#', item.Name);
                html = html.replace('#=files[0].isImage#', item.isImage);
                html = html.replace("\\\\", '');
                html = html.replace("class='k-progress'", "class='k-progress' style='width:100%'");

                upload.find('.k-upload-files').append('<li class="k-file k-file-success">' + html + '</li>');
            });

            //Ponemos los eventos clicks para ver el archivo y poder eliminarlo.
            $.Framework.UploadFile.bindClickShowFile(upload);
            $.Framework.UploadFile.bindClickDeleteFile(upload);
        }
    });
}

/*
*   Método que pone el evento click a los elementos .item-file para que puedan ser abiertos en un modal o la descarga del archivo.
*   @param {Object} upload  - Objeto jQuery del elemento upload.
*/
$.Framework.UploadFile.bindClickShowFile = function (upload)
{
    upload.find('.item-file').off('click').on('click', function (e)
    {
        e.preventDefault();
        var itemFile = $(this);
        var filters = {
            fileName: itemFile.text()
        };

        //Combinamos el objeto filters con los filtros extras.
        $.extend(filters, upload.find('[type="file"]').data('extrafilters'));

        //Si es imagen, mostramos el archivo en un modal. Si no, hacemos que se descargue.
        if (itemFile.data('isimage') == true)
        {
            $.Framework.ajax({
                type: 'POST',
                url: upload.find('[type="file"]').data('getfile'),
                data: JSON.stringify(filters),
                done: function (response, textStatus, jqXHR)
                {
                    var windowWidth = window.screen.width < window.outerWidth ? window.screen.width : window.outerWidth;
                    var mobile = windowWidth <= 640;

                    var windowOptions = {
                        width: mobile == true ? 450 : 660,
                        height: mobile == true ? 280 : 400
                    };

                    $.Framework.showModal({
                        title: 'Visor de fotografías',
                        iconCss: 'fa fa-file-image-o text-info',
                        width: windowOptions.width,
                        height: windowOptions.height + 60,
                        content: "<img style='max-height:" + (windowOptions.height - 30) + "px' class='img-responsive' src='" + response + "'/>"
                    });
                },
                fail: function (jqXHR, textStatus)
                {
                    $.Framework.showModal({
                        content: 'Ocurrió un error al intentar mostrar el archivo. Favor de intentarlo más tarde.'
                    });
                }
            });
        }
        else
        {
            $.Framework.fileDownload({
                type: 'POST',
                url: upload.find('[type="file"]').data('getfile'),
                data: filters
            });
        }
    });
}

/*
*   Método que pone el evento click a los elementos .file-delete, que correrá el servicio para eliminar archivos.
*   @param {Object} upload  - Objeto jQuery del elemento upload.
*/
$.Framework.UploadFile.bindClickDeleteFile = function (upload)
{
    upload.find('.delete-file').off('click').on('click', function (e)
    {
        e.preventDefault();
        var button = $(this);

        $.Framework.showModal({
            content: '¿Esta seguro que desea borrar el archivo?',
            buttons: [{
                classCss: 'btn-success',
                iconCss: 'fa fa-check',
                text: 'Aceptar',
                click: function (e)
                {
                    var modal = $(this).closest('.k-window-content').data('kendoWindow');
                    var kendoUpload = upload.find('[type="file"]').data('kendoUpload');

                    //Combinamos los objetos para los filtros.
                    var filters = {
                        fileName: button.closest('.k-file').find('.item-file').text(),
                        __RequestVerificationToken: $('input[name="__RequestVerificationToken"]').val()
                    };

                    $.extend(filters, upload.find('[type="file"]').data('extrafilters'));

                    $.Framework.ajax({
                        type: kendoUpload.options.async.removeVerb,
                        url: kendoUpload.options.async.removeUrl,
                        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                        dataType: 'html',
                        data: filters,
                        done: function (response, textStatus, jqXHR)
                        {
                            //Si la respuesta JSON tiene contenido (lo que sea), quiere decir que hubo algo mal.
                            if (response.length > 0)
                            {
                                $.Framework.showModal({
                                    content: response
                                });
                            }
                            else
                            {
                                $.Framework.showNotification({
                                    text: 'El archivo se ha borrado correctamente.'
                                });

                                modal.close();

                                $.Framework.UploadFile.getAllFiles(upload);
                            }
                        },
                        fail: function (jqXHR, textStatus)
                        {
                            modal.close();

                            $.Framework.showNotification({
                                type: 'error',
                                text: jqXHR.responseText
                            });
                        }
                    });
                }
            },
            {
                classCss: 'btn-danger',
                iconCss: 'fa fa-ban',
                text: 'Cancelar',
                click: function (e)
                {
                    $(this).closest('.k-window-content').data('kendoWindow').close();
                }
            }]
        });
    });
}