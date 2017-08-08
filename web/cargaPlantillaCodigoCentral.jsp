<%-- 
    Document   : cargaPlantillaCodigoCentral
    Created on : Oct 15, 2015, 2:22:11 PM
    Author     : ugarcia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carga de plantilla</title>
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"> <label> Carga de plantilla </label> </div> 
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-guardar" onclick="cargarPlantillaCentral();">
                <br/> <small> Cargar </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div">Seleccione el tipo de plantilla que va cargar<br/>
        <div>
            <input type="radio" id="radioSIRHB" value="SIRHB" name="origen" />
            <label for='radioSIRHB'> Burocracia </label>            
        </div>
        <div>
            <input type="radio" id="radioSIRHM" value="SIRHM" name="origen" />
            <label for='radioSIRHM'> Magisterio </label>
        </div>
        <div id="mensajeCarga"></div>
        <div id="mensaje"></div>
    </div>
    <jsp:include page="template/piePagina.jsp" />
</html>
