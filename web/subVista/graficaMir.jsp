<%-- 
    Document   : graficaMir
    Created on : 20/07/2017, 01:55:51 PM
    Author     : ugarcia
--%>
    <div class="col-md-12 padding-zero margin-zero ${(grafica.totalPosterior + grafica.totalInicial) == 0 ? "hidden" : ""}" style="margin-bottom: 10px !important">
            RESUMEN DE MIR (TOTAL: ${grafica.totalPosterior + grafica.totalInicial}) 
    </div>
    
    <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 margin-zero padding-zero ${(grafica.totalPosterior + grafica.totalInicial) == 0 ? "hidden" : ""}">
            Inicial: ${grafica.totalInicial} MIR
    </div>
    <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 margin-zero padding-zero ${(grafica.totalPosterior + grafica.totalInicial) == 0 ? "hidden" : ""}">
        <div class="progress margin-zero">
            <div class="progress-bar progress-bar-danger barraEstatus ${grafica.cancelada == 0 ? "hidden" : ""}"
                 data-toggle="tooltip" role="progressbar"
                 title="${grafica.cancelada}"
                 style="width:${grafica.totalInicial > 0 ? (grafica.cancelada * 100) / (grafica.totalInicial) : 0}%">
              CANCELADAS
            </div>
            <div class="progress-bar barraEstatus ${grafica.borrador == 0 ? "hidden" : ""}"
                 title="${grafica.borrador}"
                 data-toggle="tooltip" role="progressbar"
                 style="width:${grafica.totalInicial > 0 ? (grafica.borrador * 100) / (grafica.totalInicial) : 0}%;background-color:#c1c1c1">
              BORRADOR
            </div>
            <div class="progress-bar barraEstatus ${grafica.enviada == 0 ? "hidden" : ""}"
                 title="${grafica.enviada}"
                 data-toggle="tooltip" role="progressbar"
                 style="width:${grafica.totalInicial > 0 ? (grafica.enviada * 100) / (grafica.totalInicial) : 0}%;background-color:#3c9dff">
              ENVIADAS
            </div>
            <div class="progress-bar barraEstatus ${grafica.rechazada == 0 ? "hidden" : ""}"
                 title="${grafica.rechazada}"
                 data-toggle="tooltip" role="progressbar"
                 style="width:${grafica.totalInicial > 0 ? (grafica.rechazada * 100) / (grafica.totalInicial) : 0}%;background-color:#ffa851">
              RECHAZADAS
            </div>
            <div class="progress-bar barraEstatus ${grafica.validada == 0 ? "hidden" : ""}"
                 title="${grafica.validada}"
                 data-toggle="tooltip" role="progressbar"
                 style="width:${grafica.totalInicial > 0 ? (grafica.validada * 100) / (grafica.totalInicial) : 0}%;background-color: #72dc72">
              VALIDADAS
            </div>
        </div>
    </div>
    
    <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 margin-zero padding-zero ${(grafica.totalPosterior + grafica.totalInicial) == 0 ? "hidden" : ""}">
        Posterior: ${grafica.totalPosterior} MIR
    </div>
    <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 margin-zero padding-zero ${(grafica.totalPosterior + grafica.totalInicial) == 0 ? "hidden" : ""}"
        style="margin-bottom: 15px !important">
        <div class="progress margin-zero">
            <div class="progress-bar barraEstatus ${grafica.enviadaPosterior == 0 ? "hidden" : ""}"
                 title="${grafica.enviadaPosterior}"
                 data-toggle="tooltip" role="progressbar"
                 style="width:${grafica.totalPosterior > 0 ? (grafica.enviadaPosterior * 100) / (grafica.totalPosterior) : 0}%;background-color:#3c9dff">
              ENVIADAS
            </div>
            <div class="progress-bar barraEstatus ${grafica.rechazadaPosterior == 0 ? "hidden" : ""}"
                 title="${grafica.rechazadaPosterior}"
                 data-toggle="tooltip" role="progressbar"
                 style="width:${grafica.totalPosterior > 0 ? (grafica.rechazadaPosterior * 100) / (grafica.totalPosterior) : 0}%;background-color:#ffa851">
              RECHAZADAS
            </div>
            <div class="progress-bar barraEstatus ${grafica.validadaPosterior == 0 ? "hidden" : ""}"
                 title="${grafica.validadaPosterior}"
                 data-toggle="tooltip" role="progressbar"
                 style="width:${grafica.totalPosterior > 0 ? (grafica.validadaPosterior * 100) / (grafica.totalPosterior) : 0}%;background-color: #72dc72">
              VALIDADAS
            </div>
        </div>
    </div>  
              
              