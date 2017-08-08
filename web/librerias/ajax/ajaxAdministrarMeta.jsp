<%-- 
    Document   : ajaxInformacionMeta
    Created on : Apr 9, 2015, 11:47:05 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.ClasificacionFuncional"%>
<%@page import="gob.gbc.entidades.Transversalidad"%>
<%@page import="gob.gbc.entidades.TipoCalculo"%>
<%@page import="gob.gbc.entidades.GrupoPoblacional"%>
<%@page import="gob.gbc.entidades.TipoCompromiso"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="gob.gbc.entidades.UnidadMedida"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MetaBean metaBean = null;
    Meta meta = new Meta();
    List<Transversalidad> transversalidadList = new ArrayList<Transversalidad>();
    List<TipoCalculo> tipoCalculoList = new ArrayList<TipoCalculo>();
    List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
    List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
    List<Linea> lineaPedList = new ArrayList<Linea>();
    List<ClasificacionFuncional> clasificacionList = new ArrayList<ClasificacionFuncional>();
    List<TipoCompromiso> tipoCompromisoList = new ArrayList<TipoCompromiso>();
    String strResultado = new String();
    String programaId = new String();
    String programaDescr = new String();
    String ramoId = new String();
    String ramoDescr = new String();
    String tipoProyecto = new String();
    String proyectoDescr = new String();
    String tipoDepenendencia = new String();
    String tipoMeta = new String();
    String disTipoMeta = new String();
    String disabledClas = new String();
    int disClasif = 0;
    int countAccion = 0;
    int proyectoId = 0;
    int metaId = 0;
    int optMeta = 0;
    int year = 0;
    String selected = new String();

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDepenendencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = request.getParameter("programaId");
        }
        if (request.getParameter("tipoProyecto") != null && !request.getParameter("tipoProyecto").equals("")) {
            tipoProyecto = request.getParameter("tipoProyecto");
        }
        if (request.getParameter("proyectoId") != null && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt(request.getParameter("proyectoId"));
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("optMeta") != null && !request.getParameter("optMeta").equals("")) {
            optMeta = Integer.parseInt(request.getParameter("optMeta"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoDescr") != null && !request.getParameter("ramoDescr").equals("")) {
            ramoDescr = request.getParameter("ramoDescr");
        }
        if (request.getParameter("programaDescr") != null && !request.getParameter("programaDescr").equals("")) {
            programaDescr = request.getParameter("programaDescr");
        }
        if (request.getParameter("proyectoDescr") != null && !request.getParameter("proyectoDescr").equals("")) {
            proyectoDescr = request.getParameter("proyectoDescr");
        }
        metaBean = new MetaBean(tipoDepenendencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDepenendencia);
        tipoMeta = metaBean.getObraProyecto(year, proyectoId, tipoProyecto);
        disClasif = metaBean.getCountAccionByMeta(year, ramoId, programaId, metaId);
        if(disClasif > 0){
            disabledClas = "disabled";
        }
        if (optMeta == 1) {
            meta = new Meta();
            meta.setBenefH(0);
            meta.setBenefM(0);
            meta.setMeta("");
            meta.setTipoProyecto(tipoProyecto);
            meta.setObra("");
        } else {
            if (optMeta == 2) {
                meta = metaBean.getMetaById(ramoId, programaId, proyectoId, metaId, year, tipoProyecto);
                countAccion = metaBean.getCountAccionByMeta(year, ramoId, programaId, metaId);
                /*if(countAccion > 0){
                    disTipoMeta = "disabled";
                }*/
            } else {
                if (optMeta == 3) {
                    meta = new Meta();
                    meta.setMetaId(metaId);
                }
            }
        }
        if (meta != null) {

            strResultado += "<table id='informacionMeta' cellspacing='10'>";
            strResultado += "   <tr>";
            strResultado += "       <td> Ramo </td>";
            strResultado += "       <td>" + ramoDescr + "</td>";
            strResultado += "   </tr>";
            strResultado += "   <tr>";
            strResultado += "       <td> Programa </td>";
            strResultado += "       <td>" + programaDescr + "</td>";
            strResultado += "   </tr>";
            strResultado += "   <tr> ";
            strResultado += "       <td> Proy/Act. </td>";
            strResultado += "       <td>" + proyectoDescr + "</td> ";
            strResultado += "   </tr>";
            strResultado += "</table>";
            strResultado += "<br>";
            strResultado += "<table id='identMeta' cellspacing='10'>";
            
            if (optMeta != 1) {
                strResultado += "<tr> <td> Meta </td>";
                strResultado += "<td>" + meta.getMetaId() + "</td> </tr>";
            }
            //strResultado += "<tr> <td> Descripci&oacute;n de meta </td>";
            strResultado += "<tr> <td> <p id='descrMeta'>Descripci&oacute;n de meta</p> </td>";
            strResultado += "<td> <div> <textArea maxlength='700' class='no-enter' id='txtAreaMeta' style='text-transform: uppercase;'>" + meta.getMeta() + "</textArea></div> </td> </tr> </table>";
            //strResultado += "<div id='divInfoMeta'> <div class='separador-info'><div> No.</div> <div> Descripci&oacute;n del compromiso</div> </div>";
            //strResultado += "<div class='separador-info'> <div> "+meta.getMetaId()+" </div> <div> "+ meta.getMeta() +"</div> </div> </div>";
            strResultado += "<table id='tblInfoMeta'>";
            strResultado += "   <tr> <td> <div> Unidad de medida </div>";
            strResultado += "           <select id='selMedida'>";
            strResultado += "               <option value='-1'> -- Seleccione unidad de medida -- </option>";
            unidadMedidaList = metaBean.getUnidadMedidaList(year);
            for (UnidadMedida unidadMedida : unidadMedidaList) {
                if (unidadMedida.getUnidadMedidaId() == meta.getClaveMedida()) {
                    selected = "selected";
                }
                strResultado += "           <option value=" + unidadMedida.getUnidadMedidaId() + " " + selected + ">";
                strResultado += unidadMedida.getUnidadMedida();
                strResultado += "           </option>";
                selected = "";
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> C&aacute;lculo </div>";
            strResultado += "           <select id='selCalculo'>";
            strResultado += "               <option value='-1'> -- Seleccione tipo de c&aacute;lculo -- </option>";
            if (meta.getCalculo() == null) {
                meta.setCalculo("");
            }
            tipoCalculoList = metaBean.getTipoCalculo();
            for (TipoCalculo tipoCalculo : tipoCalculoList) {
                if (meta.getCalculo().equals(tipoCalculo.getAbrev())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-"
                        + tipoCalculo.getTipoCalculo() + "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> Clasificaci&oacute;n funcional </div>";
            clasificacionList = metaBean.getClasificacionFuncional(ramoId, programaId, proyectoId, year, tipoProyecto);
            strResultado += "           <select id='selClasificacion' "+disabledClas+">";
            strResultado += "               <option value='-1'> -- Seleccione una clasificaci&oacute;n -- </option>";
            if(clasificacionList.size() == 1){
                strResultado += "           <option value='" + clasificacionList.get(0).getClasificacion() + "' selected> " + 
                        clasificacionList.get(0).getClasificacion() + "-" + clasificacionList.get(0).getClasificacionDescr() + " </option>";
            }else{
                for (ClasificacionFuncional clasificacion : clasificacionList) {
                    if (clasificacion.getClasificacion().equals(meta.getClasificacion())) {
                        selected = "selected";
                    }
                    strResultado += "           <option value='" + clasificacion.getClasificacion() + "' " + selected + "> " + clasificacion.getClasificacion() + "-" + clasificacion.getClasificacionDescr() + " </option>";
                    selected = "";
                }
            }
            strResultado += "           </select> </td> </tr>";
            strResultado += "   <tr> <td> <div> Compromiso </div>";
            strResultado += "           <select id='selCompromiso' >";
            strResultado += "               <option value='-1'> -- Seleccione tipo de compromiso -- </option>";
            tipoCompromisoList = metaBean.getTipoCompromiso();
            /*if(meta.getCompromiso() == null){
             meta.setCompromiso("");
             }*/
            for (TipoCompromiso tipoCompromiso : tipoCompromisoList) {
                if (meta.getCompromiso() == tipoCompromiso.getTipoCompromisoId()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCompromiso.getTipoCompromisoId() + "' " + selected + "> " + tipoCompromiso.getTipoCompromisoDes() + " </option>";
                selected = new String();
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> L&iacute;nea PED </div>";
            strResultado += "           <select id='selLineaPed' onchange='getLineaSectorial()'>";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea PED -- </option>";
            lineaPedList = metaBean.getLineaRamoPrograma(year, ramoId, programaId);
            if (meta.getLineaPED() == null) {
                meta.setLineaPED("");
            }
            for (Linea lineaPedTemp : lineaPedList) {
                if (meta.getLineaPED().equals(lineaPedTemp.getLineaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + lineaPedTemp.getLineaId() + "' " + selected + ">";
                strResultado += lineaPedTemp.getLineaId() + "-" + lineaPedTemp.getLinea();
                strResultado += "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> L&iacute;nea sectorial </div>";
            strResultado += "           <select id='selLineaSect'>";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea Sectorial -- </option>";
            if (optMeta == 2) {
                lineaSectorialList = metaBean.getLineaSectorialByPED(meta.getLineaPED(), year, ramoId, programaId, proyectoId);
                for (LineaSectorial lineaS : lineaSectorialList) {
                    if (meta.getLineaSectorial().equals(lineaS.getLineaId())) {
                        selected = "selected";
                    }
                    strResultado += "<option value='" + lineaS.getLineaId() + "' " + selected + " >"
                            + lineaS.getLineaId() + "-" + lineaS.getLineaSectorial() + "</option>";
                }
            }
            strResultado += "           </select> </td> </tr>";
            strResultado += "   <tr> <td> <div> Ponderaci&oacute;n </div> <select id='selPonderacion' value='" + meta.getPonderado() + "'>";
            strResultado += "               <option value='-1'> -- Seleccione una ponderaci&oacute;n -- </option>";
            if (meta.getPonderado() == 1) {
                strResultado += "       <option selected value='1'>";
            } else {
                strResultado += "       <option value='1'>";
            }
            strResultado += "           1-Baja";
            strResultado += "       </option>";
            if (meta.getPonderado() == 2) {
                strResultado += "       <option selected value='2'>";
            } else {
                strResultado += "       <option value='2'>";
            }
            strResultado += "           2-Media";
            strResultado += "       </option>";
            if (meta.getPonderado() == 3) {
                strResultado += "       <option selected value='3'>";
            } else {
                strResultado += "       <option value='3'>";
            }
            strResultado += "           3-Alta";
            strResultado += "       </option>";
            strResultado += "   </select> </td>";
            strResultado += "<td align='left'> <div> Criterio de transversalidad </div>";
            strResultado += "<select id='selTrasver' > <option value='-1'> -- Seleccione un criterio -- </option>";
            transversalidadList = metaBean.getTransversalidad();
            for (Transversalidad transv : transversalidadList) {
                if (meta.getCriterio() == transv.getTransversalidadId()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + transv.getTransversalidadId() + "' " + selected + ">" + transv.getTransversalidadId()
                        + "-" + transv.getTransvesalidad() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td>";
            strResultado += "<input type='hidden' id='tipoMetaObra' value='"+meta.getObra()+"'/>";
            if(tipoMeta.equals("S")){
                if(meta.getObra() == null){
                    meta.setObra(new String());
                }
                strResultado += "<td> <div> Inversi&oacute;n P&uacute;blica </div> ";
                if(countAccion > 0){
                    strResultado += "<select id='selObraP'  onChange='validarTipoMeta()'> <option value='-1'>-- Seleccione tipo de meta -- </option> ";
                }else{
                    strResultado += "<select id='selObraP'  > <option value='-1'>-- Seleccione tipo de meta -- </option> ";
                }
                if(meta.getObra().equals("G")){
                    strResultado += "<option value='G' selected> Gasto corriente </option>";
                }else{
                    strResultado += "<option value='G' > Gasto corriente </option>";
                }
                if(meta.getObra().equals("I")){
                    strResultado += "<option value='I' selected> Inversi&oacute;n p&uacute;blica </option>";
                }else{
                    strResultado += "<option value='I' > Inversi&oacute;n p&uacute;blica </option>";
                }
                if(meta.getObra().equals("A")){
                    strResultado += "<option value='A' selected> Inversi&oacute;n p&uacute;blica y gasto corriente </option>";
                }else{
                    strResultado += "<option value='A' > Inversi&oacute;n p&uacute;blica y gasto corriente</option>";
                }
            }
            strResultado += "</tr>";
            selected = "";
            if (meta.getPrincipal() == null) {
                meta.setPrincipal("");
            }
            if (meta.getPrincipal().equals("S")) {
                selected = "checked";
            }
            strResultado += "       <td align='center'> <div style='display:none;'> Principal </div>";
            strResultado += "       <td align='center'>";
            strResultado += "           <input type='checkbox' id='chkPrincipal' " + selected + " style='display:none;' /> </td>";
            selected = "";
            if (meta.getAutorizacion() == null) {
                meta.setAutorizacion("");
            }
            if (meta.getAutorizacion().equals("S")) {
                selected = "checked";
            }
            
            strResultado += "       <td align='center'> <div  style='display:none;'> Proceso de autorizaci&oacute;n </div>";
            strResultado += "           <input type='checkbox' id='chkAutorizacion' " + selected + " style='display:none;'  /> </td>";
            selected = "";
            strResultado += "</tr>";
            strResultado += "</table> <br/>";
            strResultado += "<center>";
            if (optMeta == 1) {
                strResultado += "   <input id='guardarMeta' type='button' value='Guardar' onclick='guardarMeta()'/>";
            } else {
                strResultado += "   <input id='actualizarMeta' type='button' value='Guardar' onclick='actualizarMeta(" + meta.getMetaId() + ")'/>";
            }
            strResultado += "   <input type='button' value='Cancelar' onclick='cerrarInfoMeta()'/>";
            strResultado += "</center>";
        }
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (metaBean != null) {
            metaBean.resultSQLDesconecta();
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