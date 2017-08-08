<%-- 
    Document   : ajaxGetArticuloPartida
    Created on : May 7, 2015, 12:29:16 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="gob.gbc.aplicacion.PartidaBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Articulo"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String partida = new String();
    int year = 0;
    int justific = 0;
    String tipoGasto = new String();
    String ramoId = new String();
    String disabled = new String();
    String tipoDependencia = new String();
    boolean isArticuloP = false;
    boolean isRelacionLab = false;
    boolean reqDescr = false;
    AccionBean accionBean = null;
    List<Articulo> articuloList = new ArrayList<Articulo>();
    try {

        request.setCharacterEncoding("UTF-8");

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("partida") != null && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

        reqDescr = accionBean.isRequDescrCerrado(ramoId, year);

        if (reqDescr) {
            disabled = "disabled";
        } else {
            disabled = "";
        }

        strResultado += "<div> Requerimiento </div>";

        isArticuloP = accionBean.isArticuloPartidas(year, partida);

        if (!isArticuloP) {
            strResultado += "<select id='ArtPartida' " + disabled + " onchange='getCosto()'>";
            strResultado += "<option value='-1'> -- Seleccione un art&iacute;culo -- </option>";

            articuloList = accionBean.getArticulosByPartida(year, partida);

            for (Articulo articulo : articuloList) {
                strResultado += "<option value='" + articulo.getArticuloId() + "'>" + articulo.getArticuloId() + "-" + articulo.getArticulo() + "</option>";
            }

            strResultado += "<select>";      
        } else {
            strResultado += "<textArea id='txtAreaPart' class='no-enter' name='limitedtextarea' "
                    + "onKeyDown='limitText(this.form.limitedtextarea,this.form.countdown,100);'"
                    + "onKeyUp='limitText(this.form.limitedtextarea,this.form.countdown,100);' " + disabled + "> </textArea>";
        }
        tipoGasto = accionBean.getTipoGastoByPartida(partida, year);
        isRelacionLab = accionBean.isRelLaboral(year, partida);
        justific = accionBean.isPartidaJustific(year, partida);
        strResultado += "<input id='isRelLaboral' type='hidden' value='" + isRelacionLab + "' />";
        strResultado += "<input id='existeArtParCosto' type='hidden' value='" + isRelacionLab + "' />";
        strResultado += "<input id='reqJust' type='hidden' value='" + justific + "' />";
        strResultado += "<input id='selTipoGasto' type='hidden' value='" + tipoGasto + "' />";
        strResultado += "<input id='selTipoPartida' type='hidden' value='" + isArticuloP + "' />";

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
<script>
    $('.no-enter').bind('keypress', function(e){
        if(e.keyCode == 13)
        {
           return false;
        }
     });     
</script>