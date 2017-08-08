<%-- 
    Document   : reinicioSequencias
    Created on : Dec 20, 2016, 10:03:09 AM
    Author     : ugarcia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<jsp:include page="template/encabezado.jsp" />
<script src="librerias/_secuencia.js" type="text/javascript"></script>
    <div Id="TitProcess"><label >Reinicio de sequencias<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div" style="width: 60%">
        <section class="col-lg-12 text-center" style="margin-top: 80px" onclick="reiniciarSecuencias()">
            <button class="btn btn-success">
                Reiniciar secuencias
            </button>            
        </section>
    </div>
<jsp:include page="template/piePagina.jsp" />
</html>