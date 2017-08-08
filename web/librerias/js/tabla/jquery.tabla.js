(function($) {
    var objetoPrincipal;
    var opciones
    var ordenar = 0
    var tipoOrdenar;
    var textoOrdenar;
    var orientacion = " "
    var resultado;

    $.fn.tabla = function(opcs) {
        opciones = $.extend({}, $.fn.tabla.defaults, opcs);

        this.each(function() {
            objetoPrincipal = $(this);
            idElemento = $(this).attr("id");

            $(".imprimir").css('display', 'none');
            $('.imprimir_bm').css('display','none');
            $('.imprimir_vh').css('display','none');
            $(".consultar").css('left', '45%');
            $('#btnSubmit_bm').css({left:'30%'});
            $('#btnSubmit_vh').css({left:'40%'});
            $(objetoPrincipal).data("cantRegistros", 0);
            $(objetoPrincipal).data("paginaActual", 1);
            $(objetoPrincipal).data("idTabla", idElemento + "-tabla");
            $("div").each(function() {
                id = (this.id);
                if (id == idElemento) {
                    $(objetoPrincipal).data("tipoElemento", "div");
                    inicializaTabla();
                }
            });

            $("table").each(function() {
                id = (this.id);
                if (id == idElemento) {
                    $(objetoPrincipal).data("tipoElemento", "div");
                    estiloTablaEstatica(objetoPrincipal);
                }
            });
        });

    };

    /**
     * Opciones por default que tendra el plugin, que se podran modificar de
     * acuerdo a sus necesidades..
     *
     * @cssFila1            Nombre de la clase del estilo crear efecto zebra.
     *
     * @cssFila2            Nombre de la clase del estilo crear efecto zebra.
     *
     * @cssFilaHover        Clase de estilo que se utilizara cuando mouse pase por una fila.
     *
     * @cssTabla            Clase de estilo para la tabla.
     *
     * @cssTitulo           Clase de estilo para la parte del titulo de la tabla.
     *
     * @cssEncabezados      Clase de estilo para los encabezados de las columnas.
     *
     * @cssRegistros
     *
     * @regsXpagina         Cantidad de registros por pagina.
     *
     * @maxPaginas          Maximo de paginas que se mostraran en la navegacion.
     *
     * @valorFila           Nombre de la columna que tomara el valor al hacer click.
     *
     * @paginar             Opcion para que la tabla tenga paginacion.
     *
     * @fnClickFila         Funcion que ejecutara despues de hacer click, devoviendo el
     *                      valor de la columna.
     *
     * @urlAjax             Url de la pagina que ejecutara el ajax.
     *
     * @datosAjax           Datos que se enviaran por ajax para que obtenga la información.
     *
     * @metodoAjax          Metodo por el que se enviaran los datos a la pagina del ajax.
     *
     * @tipoAjax            Tipo de valor que devolvera el ajax.
     *
     * @columnasEncabezado  Titulo de los encabezados de las columnas.
     *
     * @columnasOrdenar     Nombre de las columnas que se van a ordenar
     */
    $.fn.tabla.defaults = {
        //variables para estilo
        titulo: "",
        cssFila1: "fila1",
        cssFila2: "fila2",
        cssFilaHover: "hover",
        cssTabla: "tabla",
        cssTitulo: "content-title",
        cssEncabezados: "thead",
        cssRegistros: "",
        cssPie: "",
        cssOrdenar: "order",
        cssOrdenarAsc: "order_asc",
        cssOrdenarDesc: "order_desc",
        cssPaginacion: "",
        cssPaginacionDisable: "",
        cssPaginacionHover: "nav_hover",
        regsXpagina: 20,
        maxPaginas: 5,
        valorFila: "",
        paginar: true,
        fnClickFila: function() {
        },
        //varibles para ajax
        urlAjax: "",
        datosAjax: "",
        metodoAjax: "post",
        tipoAjax: "html",
        columnasEncabezado: [],
        columnasOrdenar: [],
        msgBefore: ""
    };


    /**
     *Funcion para recargar la tabla con diferente opcion ya sea por que se haya
     *ordenado, seleccionaron otra pagina.
     *
     *@param pagina             Numero de la pagina que esta actualmente o seleccionaron.
     *
     **/
    $.fn.recargaTabla = function(pagina) {
        $(objetoPrincipal).data("paginaActual", parseInt(pagina, 10))
        switch ($(objetoPrincipal).data("tipoElemento")) {
            case'div':
                inicializaTabla()
                break;
            case'table':
                estiloTablaEstatica(objetoPrincipal);
                break;
        }
    }

    /**
     * Metodo que inicializa para crear la tabla utilizando ajax, para traer la informacion
     * desde el servidor, los datos necesarios para la ejecución del ajax los toma
     * del arreglo de varibles que recibio la invocacion la funcion principal del
     * objeto.
     *
     *
     */
    function inicializaTabla() {
        if (opciones.urlAjax != "") {
            $.ajax({
                type: opciones.metodoAjax,
                url: opciones.urlAjax,
                dataType: opciones.tipoAjax,
                data: opciones.datosAjax + "&pagina=" + $(objetoPrincipal).data("paginaActual") + "&ordenar=" +
                        ordenar + "&orientacion=" + orientacion + "&regsXpagina=" + opciones.regsXpagina + "&buscar=" + $("#search-table").val(),
                beforeSend: function() {
                    objetoPrincipal.html("<div id='msg-tabla-ajax' class='msg-tabla'>" + opciones.msgBefore + "</div>")
                },
                success: function(response) {
                    crearTabla(limpiaTexto(response));
                }
            });
        }
    }


    /**
     *Funcion principal para generar la tabla dinamicamente desde los datos obtenidos
     *del servidor.
     *
     *@param datos             Datos obtenidos por ajax con formato JSON
     *
     **/
    function crearTabla(datos) {
        var informacion = limpiaTexto(datos)
        var header = "";
        var cuerpo = "";
        var pie = "";

        $('#msg-tabla-ajax').html("");

        if (informacion == "0") {
            objetoPrincipal.html(mensaje(1))
        } else if (informacion == "-1") {
            objetoPrincipal.html(mensaje(2))
        } else {
            var arrJSON = eval("(" + informacion + ")");
            var idTabla = $(objetoPrincipal).data("idTabla")

            $(objetoPrincipal).data("cantRegistros", parseInt(arrJSON.totalRegistros, 10));
            $(objetoPrincipal).data("totalPaginas", parseInt(arrJSON.iTotalPaginas, 10));
            $(objetoPrincipal).data("paginaActual", parseInt(arrJSON.iPagina, 10))

            if (objetoPrincipal.html() != "") {
                $("#" + idTabla + "").remove()//eliminamos la tabla que ya existe
            }

            header = iniEncabezadoTabla(arrJSON);
            cuerpo = iniCuerpoTabla(arrJSON);
            pie = iniPieTabla();

            objetoPrincipal.html("<table id='" + idTabla + "' align='center' >"
                    + header + cuerpo + pie
                    + "</table>");
            estiloTabla();
        }
    }

    /**
     *Funcion para generar el encabezado de la tabla
     *
     *@param datos       Datos obtenidos por ajax con formato JSON
     *
     */
    function iniEncabezadoTabla(datos) {
        var arrTemp = opciones.columnasEncabezado;
        var arrJSON = eval(datos);
        var encabezado = "";

        if (arrTemp.length > -1) {
            var tmp;
            var tmpDatos = arrJSON.arrDatos;
            if (tmpDatos.length > -1) {
                tmp = tmpDatos[0];
                $(objetoPrincipal).data("columnas", tmp.length);
            }
            encabezado = "<thead><tr><th align='center' colspan='" + ($(objetoPrincipal).data("columnas")) + "'>" +
                    "<div class='" + opciones.cssTitulo + "'>" +
                    opciones.titulo + "</div></th></tr>"
                    + "<tr>";
            for (i = 0; i < $(objetoPrincipal).data("columnas"); i++) {
                encabezado += "<th>" + arrTemp[i][0] + "</th>"
            }
            encabezado += "</tr></thead>"
        }
        return encabezado;
    }

    /**
     *Este metodo realiza el cuerpo de la tabla, con los datos obtenidos por
     *medio del ajax.
     *
     *@param datos              Datos obtenidos por ajax con formato JSON.
     **/
    function iniCuerpoTabla(datos) {
        var informacion = eval(datos);
        var arrDatos = informacion.arrDatos;
        var cuerpo = "";
        var datos = "";
        if (arrDatos.length > -1) {
            cuerpo += "<tbody>";
            for (i = 0; i < arrDatos.length; i++) {
                var tmpDatos = arrDatos[i];
                if (tmpDatos.length > -1) {
                    cuerpo += "<tr>";
                    for (j = 0; j < $(objetoPrincipal).data("columnas"); j++) {
                        var head = opciones.columnasEncabezado;
                        var formato = head[j][1];
                        if( formato != undefined){
                            switch(formato){
                                case 'text':
                                    cuerpo += "<td>" + tmpDatos[j] + "</td>";
                                    break;
                                case 'number':
                                    cuerpo += "<td align='right'>" + tmpDatos[j] + "</td>";
                                    break;
                                case 'date':
                                    cuerpo += "<td align='center'>" + tmpDatos[j] + "</td>";
                                    break;
                                case 'money':
                                    cuerpo += "<td align='right'>" + tmpDatos[j] + "</td>";
                                    break;
                            }
                        }else{
                                cuerpo += "<td>" + tmpDatos[j] + "</td>";
                        }
                    }
                    cuerpo += "</tr>";
                }
            }
            cuerpo += "</tbody>";
        }

        return cuerpo
    }

    function isInt(n) {
        return n % 1 === 0;
    }

    /**
     *Funcion para realizar la parte del pie de la tabla, dividiendolo en dos
     *secciones, para distribuir los elementos de la navegación de paginas y
     *la cantidad de registros.
     *
     **/
    function iniPieTabla() {
        var idTabla = $(objetoPrincipal).data("idTabla")
        var pie = "";
        if ($(objetoPrincipal).data("columnas") > 0) {
            pie = "<tfoot><tr><td align='center' colspan='" + $(objetoPrincipal).data("columnas") + "'>";
            if (opciones.paginar) {
                pie += iniPaginarNav();
            }
            pie += "</td></tr></tfoot>";
        }
        return pie;
    }

    /**
     *Funcion que aplica todo estilo de la hoja de estilos tabla.css, tambien
     *agregamos los eventos necesarios para que la tabla realice todas sus
     *funciones.
     *
     **/
    function estiloTabla() {
        var idTabla = $(objetoPrincipal).data("idTabla")

        $("#" + idTabla + "").addClass(opciones.cssTabla)
        $("#" + idTabla + " thead tr").addClass(opciones.cssEncabezados)
        $("#" + idTabla + " tbody tr:even").addClass(opciones.cssFila1)
        $("#" + idTabla + " tbody tr:odd").addClass(opciones.cssFila2)

        $("#" + idTabla + " tbody tr").mouseover(function() {
            $(this).addClass(opciones.cssFilaHover)
        })
                .mouseout(function() {
            $(this).removeClass(opciones.cssFilaHover)
        })
                .click(function() {
            $(this).children("td").each(function(e) {
                if(opciones.columnasEncabezado[e][0] != undefined){
                    if (opciones.valorFila == opciones.columnasEncabezado[e][0]) {
                        resultado = $(this).text()
                        opciones.fnClickFila(resultado)
                        return false;
                    }
                }
                
            });
        });

        $("#" + idTabla + " thead tr th").each(function() {
            var texto = $(this).html().toUpperCase();
            var colsOrdenar = opciones.columnasOrdenar

            if (colsOrdenar.length > 0) {
                var opc;
                for (i = 0; i < colsOrdenar.length; i++) {
                    var tmp = colsOrdenar[i][0].toString()
                    if (tmp.toUpperCase() == texto) {
                        opc = colsOrdenar[i][1]
                        $(this).addClass(opciones.cssOrdenar)
                                .mouseover(function() {
                            $(this).css("cursor", "hand")
                                    .css("cursor", "pointer")
                        })
                                .click(function() {
                            ordenaTabla(opc, $(this));
                        });
                        if (tmp.toUpperCase() == textoOrdenar) {
                            $(this).removeClass(opciones.cssOrdenar)
                                    .addClass(tipoOrdenar)
                        }
                    }
                }
            }
        });

        $("#" + idTabla + " tfoot tr").addClass(opciones.cssEncabezados);
    }

    /**
     *Funcion que se ejecuta cuando hacen click en el header de la columna que
     *tiene la opcion para ordenar, se establece el nombre de la clase que se
     *utilizara para aplicar el estilo en el header y se agregara la opcion de
     *como se ordenara.
     *
     *@param opcion
     *
     *@param columna            Nombre de la columna por ordenar.
     */
    function ordenaTabla(opcion, columna) {
        ordenar = opcion
        textoOrdenar = columna.html().toUpperCase();
        switch (columna.attr("class")) {
            case opciones.cssOrdenar:
                tipoOrdenar = opciones.cssOrdenarAsc
                orientacion = "ASC"
                break;
            case opciones.cssOrdenarAsc:
                tipoOrdenar = opciones.cssOrdenarDesc
                orientacion = "DESC"
                break;
            case opciones.cssOrdenarDesc:
                tipoOrdenar = opciones.cssOrdenarAsc
                orientacion = "ASC"
                break;
        }
        inicializaTabla()
    }

    /**
     *Funcion principal para generar el menu de navegacion entre las diferentes
     *paginas que se generan para ver los registros.
     *
     **/
    function iniPaginarNav() {
        var nav = "";
        var regs = 0;
        var paginas = $(objetoPrincipal).data("totalPaginas");
        var pagActual = $(objetoPrincipal).data("paginaActual");
        var registros = $(objetoPrincipal).data("cantRegistros")
        var iregs = 0;

        if (pagActual < paginas) {
            regs = pagActual * parseInt(opciones.regsXpagina, 10);
        } else {
            regs = registros
        }

        if (pagActual * opciones.regsXpagina >= regs) {
            iregs = ((pagActual * opciones.regsXpagina) - opciones.regsXpagina) + 1;
        }
        else {
            iregs = regs - opciones.regsXpagina;
        }

        nav += "<div class='nav-content' align='left'>" +
                "Mostrando " + iregs + " - " + regs + " de " + registros + ". </div>" +
                "<div class='nav-content' align='right'>" +
                calculaPosicionNav(pagActual, paginas)
                + "</div>";

        return nav;
    }

    /**
     *Funcion que calcula que numeros de paginas se van a mostrar en el menu
     *de navegacion de acuerdo al numero maximo de paginas que se deben mostrar,
     *tambien valida si las opciones de siguiente o anterior se muestran en el menu.
     *
     *@param pagina         Pagina actual que se visualiza.
     *
     *@param totalPaginas   Total de paginas por todos los registros.
     *
     **/
    function calculaPosicionNav(pagina, totalPaginas) {
        var mitad = Math.floor(opciones.maxPaginas / 2)
        var resultado = "";
        var inicio;
        var fin;
        var idTabla = $(objetoPrincipal).data("idTabla")

        if ((opciones.maxPaginas % 2) > 0) {
            mitad += 1;
        }

        if (totalPaginas <= opciones.maxPaginas) {
            inicio = 1;
            fin = totalPaginas;
        } else {
            if (pagina <= mitad) {
                inicio = 1;
                fin = opciones.maxPaginas;
            } else if (pagina > (totalPaginas - mitad)) {
                inicio = (totalPaginas - (opciones.maxPaginas - 1))
                fin = totalPaginas
            } else {
                if ((totalPaginas % 2) == 0) {
                    inicio = (pagina - mitad)
                    fin = (pagina + mitad - 1)
                } else {
                    inicio = (pagina - mitad)
                    fin = (pagina + mitad)
                }
            }
        }

        if (1 < (inicio)) {
            resultado += "<span id='nav' class='nav_enable'" +
                    "onmouseover='$(this).addClass(\"" + opciones.cssPaginacionHover + "\")'" +
                    "onmouseout='$(this).removeClass(\"" + opciones.cssPaginacionHover + "\")'" +
                    "onclick='$.fn.recargaTabla(" + 1 + ")' >Primera</span>";
        }
        resultado += formaNavegacion(inicio, fin, idTabla);
        if ((fin) < totalPaginas) {
            resultado += "<span id='nav' class='nav_enable'" +
                    "onmouseover='$(this).addClass(\"" + opciones.cssPaginacionHover + "\")'" +
                    "onmouseout='$(this).removeClass(\"" + opciones.cssPaginacionHover + "\")'" +
                    "onclick='$.fn.recargaTabla(" + totalPaginas + ")' >Ultima</span>";
        }
        return resultado;
    }


    /**
     *Elabora la parte interna del menu de navegacion, generando los numeros de
     *las paginas que se pueden visualizar.
     *
     *@param inicio         Numero de la pagina que inicia.
     *
     *@param fin            Numero de la pagina que termina.
     *
     **/
    function formaNavegacion(inicio, fin, idTabla) {
        var nav = ""
        var pagActual = $(objetoPrincipal).data("paginaActual");
        var totalPaginas = $(objetoPrincipal).data("totalPaginas")

        if (1 < (inicio - 1)) {
            nav += "<span id='nav' class='nav_enable' " +
                    "onmouseover='$(this).addClass(\"" + opciones.cssPaginacionHover + "\")'" +
                    "onmouseout='$(this).removeClass(\"" + opciones.cssPaginacionHover + "\")'" +
                    "onclick='$.fn.recargaTabla(" + (inicio - 1) + ")'><<</span>";
        }
        for (; inicio <= fin; inicio++) {
            if (inicio == parseInt(pagActual, 10)) {
                nav += "<span id='nav' class='nav_disable'>" + inicio + "</span>";
            } else {
                nav += "<a id='nav' class='nav_enable' " +
                        "onmouseover='$(this).addClass(\"" + opciones.cssPaginacionHover + "\")'" +
                        "onmouseout='$(this).removeClass(\"" + opciones.cssPaginacionHover + "\")'" +
                        "onclick='$.fn.recargaTabla(" + inicio + ")'>" + inicio + "</a>";
            }
        }
        if ((fin + 1) < totalPaginas) {
            nav += "<span id='nav' class='nav_enable' " +
                    "onmouseover='$(this).addClass(\"" + opciones.cssPaginacionHover + "\")'" +
                    "onmouseout='$(this).removeClass(\"" + opciones.cssPaginacionHover + "\")'" +
                    "onclick='$.fn.recargaTabla(" + (fin + 1) + ")'>>></span>";
        }
        return nav
    }

    /**
     *Funcion para cuando se utiliza con una tabla estatica, valida si la tabla
     *ya contiene el pie de tabla, si no tiene se lo crea, luego realiza la
     *operacion para aplicar el estilo a la tabla.
     *
     *@param objeto            Dom de la tabla.
     **/
    function estiloTablaEstatica(objeto) {
        var tabla = objeto;
        var idTabla = $(objetoPrincipal).data("idTabla")

        if (opciones.paginar) {
            ocultarFilas($(objetoPrincipal).data("paginaActual"))
        }
        if ($("#" + idTabla + " tfoot").length == 0) {
            iniPieTabla();
        } else {
            $("#" + idTabla + " tfoot tr td").html(iniPaginarNav());
        }
        estiloTabla()
    }

    /**
     * Funcion para ocultar las filas cuando se utilice el script para tablas
     * que ya existen en la pagina.
     *
     * @param pagina        Numero de la pagina a mostrar.
     */
    function ocultarFilas(pagina) {
        var regXpag = parseInt(opciones.regsXpagina, 10);
        var inicio = 0;
        var fin = 0;
        var totalPaginas = $(objetoPrincipal).data("totalPaginas");
        var idTabla = $(objetoPrincipal).data("idTabla");
        var pagActual = pagina;
        var registros = $("#" + idTabla + " tbody tr").length;

        inicio = ((parseInt(pagina, 10) - 1) * regXpag);
        fin = (inicio + regXpag) - 1;
        totalPaginas = Math.floor(registros / regXpag);

        if (fin > registros) {
            fin = registros
        }
        if ((registros % regXpag) > 0) {
            totalPaginas += 1;
        }
        if (fin <= registros) {
            $("#" + idTabla + " tbody tr:lt(" + (fin) + ")").show()
        }
        if ((inicio - 1) > 1) {
            $("#" + idTabla + " tbody tr:lt(" + (inicio - 1) + ")").hide()
        }
        if ((fin + 1) < registros) {
            $("#" + idTabla + " tbody tr:gt(" + (fin) + ")").hide()
        }
    }

    /**
     * Funcion para eliminar los espacios vacios que vienen al utilizar el ajax.
     *
     * @param texto             Cadena que se desea limpiar los espacios vacios.
     */
    function limpiaTexto(texto) {
        textoFinal = texto.replace(/\n/gi, '')
        textoFinal = textoFinal.replace(/\r/gi, '')
        return textoFinal;
    }

    function mensaje(msg) {
        var mensaje = "";
        var mensajeFinal = "";
        switch (msg) {
            case 1:
                mensaje = "No se encontro informaci&oacute;n.";
                break;
            case 2:
                mensaje = "Ocurri&oacute; un problema al procesar la informaci&oacute;n.";
                break;

        }
        mensajeFinal = "<div id='msg-tabla-ajax' class='msg-tabla'>" + mensaje + "</div>"

        return mensajeFinal;
    }


})(jQuery);