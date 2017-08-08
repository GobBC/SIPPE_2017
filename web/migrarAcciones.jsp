<%-- 
    Document   : migrarAcciones
    Created on : Nov 22, 2016, 12:14:06 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.MigracionRequerimientoBean"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache"> 
        <title>Migraci&oacute;n de acciones</title>    
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <script src="librerias/_migracion.js" type="text/javascript"></script>
    <div Id="TitProcess"><label>Migraci&oacute;n de acciones</label></div>
    <%
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }
        
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        request.setCharacterEncoding("UTF-8");
        
        String tipoDependencia = new String();
        String usuario = new String();
        
        int year = 0;
        
        List<Ramo> ramoList = new ArrayList<Ramo>();
        
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        
        MigracionRequerimientoBean migrarBean = new MigracionRequerimientoBean(tipoDependencia);
        migrarBean.setStrServer(((String) request.getHeader("Host")));
        migrarBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        migrarBean.resultSQLConecta(tipoDependencia);
        ramoList = migrarBean.getResultRamoByYear(year, usuario);
        migrarBean.resultSQLDesconecta();
    %>
    <div class="col-md-8 col-md-offset-2" style="z-index: 2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div-auto">
        <div class="col-md-3">
            <label>Ramo:</label>
        </div>
        <div class="col-md-9">
            <select id="selRamo" name="selRamo" onchange='getAllMetas()'>
                <option value="-1">
                    -- Seleccione un ramo --
                </option>
                <%
                    for (Ramo ramo : ramoList) {
                        out.print("<option value='" + ramo.getRamo() + "'>");
                        out.print(ramo.getRamo() + "-" + ramo.getRamoDescr());
                        out.print("</option>");
                    }
                %>
            </select>
        </div>
        <form id="frm-migracion">
            <fieldset class="form-group">
                <legend>Acci&oacute;n actual</legend>
                <div class="col-md-12">
                    <div class="col-md-3">
                        <label>Meta:</label>
                    </div>
                    <div class="col-md-9">
                        <select id="selMetaAct" class="drop-select metaSel" onchange="getAccionesByMeta(false)">
                            <option value="-1">
                                -- Seleccione una meta --
                            </option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label>Acci&oacute;n:</label>
                    </div>
                    <div class="col-md-9">
                        <select id="selAccionAct" class="drop-select">
                            <option value="-1">
                                -- Seleccione una acci&oacute;n --
                            </option>
                        </select>
                    </div>
                </div>
            </fieldset>

            <fieldset class="form-group">
                <legend>Acci&oacute;n nueva</legend>
                <div class="col-md-3">
                        <label>Meta:</label>
                    </div>
                    <div class="col-md-9">
                        <select id="selMetaNew" class="drop-select metaSel" onchange="getAccionesByMeta(true)">
                            <option value="-1">
                                -- Seleccione una meta --
                            </option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label>Acci&oacute;n:</label>
                    </div>
                    <div class="col-md-9">
                        <select id="selAccionNew" class="drop-select">
                            <option value="-1">
                                -- Seleccione una acci&oacute;n --
                            </option>
                        </select>
                    </div>
            </fieldset>
            
            <div class="col-md-12 text-center">
                <input type="button" onclick="migrarAccion()" value="Aceptar" />
            </div>
        </form>
        <div id="mensaje"></div>
    </div>
<jsp:include page="template/piePagina.jsp" />
</html>
