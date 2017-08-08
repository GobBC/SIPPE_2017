var delay = (function () {
    var timer = 0;
    return function (callback, ms) {
        clearTimeout(timer);
        timer = setTimeout(callback, ms);
    };
})();

function handle_requestEnd(e) {
    // Code to handle the requestEnd event.
    if(e.type === "create" || e.type === "update"  || e.type === "destroy") {
        $.Framework.showNotification({
            text: e.response.map.mensaje,
            type: e.response.map.exito ? 'success' : 'warning'
        });
        var grid = $('.k-grid:visible').data('kendoGrid');
        refreshGrid(grid);
    }
}

function refreshGrid(grid){
    grid.dataSource.query({
        page: 1,
        pageSize: 5
    });
    grid.dataSource.read();
}

//Evento para dar funcionalidad a todos los campos de búsqueda de los grid.
function search(e) {
    var grid = $(e).closest("[data-role=grid]").data('kendoGrid');
    //Ignoramos las siguientes teclas:
    // Enter    = 13
    // Shift    = 16
    // Ctrl     = 17
    // Alt      = 18
    // Mayus    = 20
    // ESC      = 27
    // Arrows   = 37, 38, 39, 40
    // F1 - F12 = 112 al 123 
    if ((e.which == 13) || (e.which == 20) || (e.which == 27) || (e.which >= 16 && e.which <= 18) || (e.which >= 37 && e.which <= 40) || (e.which >= 112 && e.which <= 123)) {
        return false;
    }

    delay(function () {
        refreshGrid(grid);
    }, 600);
}

function onDataBinding(arg) {
    //EVENTO PARA AJUSTES ANTES DE CARGAR EL GRID
}

function onDataBound(arg) {
    //EVENTO PARA AJUSTES UNA VEZ CARGADO EL GRID
}

//BOTON IMPRIMIR CUSTOM
function printRow(e){
    e.preventDefault();
}

//FUNCIONES PARA EDICION CON POPUP

function cancel(){
        var form = $("#popup-edit");
        var modal = form.closest('.k-window-content').data('kendoWindow');
        modal.close();   
} 

function addRow(e){
    var grid = $(e).closest("[data-role=grid]");    
    $.ajax({
        type: "POST",
        url: grid.attr("data-url"), 
        dataType: "html", 
        data: { entidad: null}, 
        success: function(response) {
            var respuesta = response;
            if (respuesta != "") {
                var wnd = $("#details").data("kendoWindow");
                wnd.title("Agregar " + grid.attr("data-entityName"));
                wnd.content(respuesta);
                wnd.center().open();
            }
        }
    });
}

function editRow(e) {
    e.preventDefault();
    var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
    var grid = $(e.currentTarget).closest("[data-role=grid]");
    $.ajax({
        type: "POST",
        url: grid.attr("data-url"), 
        dataType: "html", 
        data: { entidad: kendo.stringify(dataItem)}, 
        success: function(response) {
            var respuesta = response;
            if (respuesta != "") {
                var wnd = $("#details").data("kendoWindow");
                wnd.title("Editar " + grid.attr("data-entityName"));
                wnd.content(respuesta);
                wnd.center().open();
            }
        }
    });
}

function deleteRow(e) {
    e.preventDefault();
    var data = kendo.stringify(this.dataItem($(e.currentTarget).closest("tr")));
    var grid = $(e.currentTarget).closest("[data-role=grid]");
    $.Framework.hacerAccionConConfirmacion({
        content: '¿Está seguro que desea eliminar el registro?',
        funcionSi: function () {
            var modal = $(this).closest('.k-window-content').data('kendoWindow');
            $.ajax({
                type: 'POST',
                url: grid.attr("data-editActionsUrl"),//url: grid.attr("data-delete-url"), //CAMBIAR A ESTA LINEA PARA ESTANDARIZAR UNA VEZ QUE SE SAQUE EL MANTO_AJAX DE LA MIR AL SERVLET
                datatype: 'json',
                data: {accion: 3, entidad: data},
                success: function(response) {
                    $.Framework.showNotification({
                        text: response.mensaje,
                        type: response.exito ? 'success' : 'warning'
                    });
                    var grid = $('.k-grid:visible').data('kendoGrid');
                    refreshGrid(grid);
                    modal.close();
                }
            });
        }
    });
}

//FUNCIONES PARA GRID INLINE
function onEditInline(e){
    e.preventDefault();

    var grid = e.sender;
    var row = e.sender.editable.element;
    var editColumn = 0;

    if (grid._editMode() != "inline")
        return false;
    if(row.hasClass('k-master-row')){
        editColumn = 1;
    }
    var cellEdit = row.children().eq(editColumn);
    var cellDelete = row.children().eq(editColumn+1);
    
    cellEdit.html('<a class="k-grid-update botonGrid columna-especial" title="Guardar" href="#"><span class="fa fa-floppy-o"></span></a>').removeClass("k-button-icontext");
    cellDelete.html('<a class="k-grid-cancel botonGrid columna-especial" title="Cancelar" href="#"><span class="fa fa-ban"></span></a>').removeClass("k-button-icontext");

}

function addRowInLine(e){         
    var grid = $(e).closest("[data-role=grid]").data("kendoGrid");
    grid.addRow();
}

//editRowInLine - se deja el default de Kendo y se moldea con onEditInline igual que el add

function deleteRowInLine(e) {
    e.preventDefault();
    var data = kendo.stringify(this.dataItem($(e.currentTarget).closest("tr")));
    var grid = $(e.currentTarget).closest("[data-role=grid]");
    $.Framework.hacerAccionConConfirmacion({
        content: '¿Está seguro que desea eliminar el registro?',
        funcionSi: function () {
            var modal = $(this).closest('.k-window-content').data('kendoWindow');
            $.ajax({
                type: 'POST',
                url: grid.attr("data-delete-url"),
                datatype: 'json',
                data: {accion: 3, entidad: data},
                success: function(response) {
                    $.Framework.showNotification({
                        text: response.map.mensaje,
                        type: response.map.exito ? 'success' : 'warning'
                    });
                    var grid = $('.k-grid:visible').data('kendoGrid');
                    refreshGrid(grid);
                    modal.close();
                }
            });
        }
    });
}

function deleteRowInLine(e, row) {
    e.preventDefault();
    var data = kendo.stringify(row.dataItem($(e.currentTarget).closest("tr")));
    var grid = $(e.currentTarget).closest("[data-role=grid]");
    $.Framework.hacerAccionConConfirmacion({
        content: '¿Está seguro que desea eliminar el registro?',
        funcionSi: function () {
            var modal = $(this).closest('.k-window-content').data('kendoWindow');
            $.ajax({
                type: 'POST',
                url: grid.attr("data-delete-url"),
                datatype: 'json',
                data: {accion: 3, entidad: data},
                success: function(response) {
                    $.Framework.showNotification({
                        text: response.map.mensaje,
                        type: response.map.exito ? 'success' : 'warning'
                    });
                    var grid = $('.k-grid:visible').data('kendoGrid');
                    refreshGrid(grid);
                    modal.close();
                }
            });
        }
    });
}

function textEditor(container, options) {
    $('<textarea name="' + options.field + '" data-bind="value: ' + options.field + '" style="width: ' + (container.width() - 8)  + 'px; font-size: 11px  !important" maxlength="150"></textarea>').appendTo(container);
}    

