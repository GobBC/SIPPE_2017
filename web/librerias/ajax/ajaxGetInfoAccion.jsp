<%-- 
    Document   : ajaxGetInfoAccion
    Created on : Apr 24, 2015, 11:30:34 AM
    Author     : ugarcia
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="gob.gbc.entidades.TipoAccion"%>
<%@page import="gob.gbc.entidades.TipoCalculo"%>
<%@page import="gob.gbc.entidades.Localidad"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="gob.gbc.entidades.Municipio"%>
<%@page import="gob.gbc.entidades.GrupoPoblacional"%>
<%@page import="gob.gbc.entidades.UnidadMedida"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    AccionBean accionBean = null;
    Accion accion = new Accion();
    MetaBean metaBean = null;
    boolean cierreAccion = false;
    boolean cierrePPTO = false;
    boolean plantBool = false;
    
    List<TipoAccion> tipoAccionList = new ArrayList<TipoAccion>();
    List<TipoCalculo> tipoCalculoList = new ArrayList<TipoCalculo>();
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
    List<GrupoPoblacional> grupoList = new ArrayList<GrupoPoblacional>();
    List<Municipio> municipioList = new ArrayList<Municipio>();
    List<Linea> lineaList = new ArrayList<Linea>();
    List<Linea> lineaSectorialList = new ArrayList<Linea>();
    List<Localidad> localidadList = new ArrayList<Localidad>();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    String numObra = new String();
    String strResultado = new String();
    String selected = new String();
    String ramoId = new String();
    String ramoDescr = new String();
    String metaDescr = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String disabled = new String();
    String disConsulta = new String();
    String tipoProy = new String();
    String tipoDependencia = new String();
    String disReq = new String();
    String compDis = new String();
    String programaId = new String();
    String tipoC = new String();
    String obraAccion = new String();
    int cont = 1;
    String tipoCompromiso = new String();
    int metaId = 0;
    int consulta = 0;
    int accionId = 0;
    int year = 0;
    int optAccion = 0;
    int proyectoId = 0;
    int contReq = 0;
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = request.getParameter("programaId");
        }
        if (request.getParameter("tipoProy") != null && !request.getParameter("tipoProy").equals("")) {
            tipoProy = request.getParameter("tipoProy");
        }
        if (request.getParameter("proyectoId") != null && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt(request.getParameter("proyectoId"));
        }
        if (request.getParameter("ramoDescr") != null && !request.getParameter("ramoDescr").equals("")) {
            ramoDescr = request.getParameter("ramoDescr");
        }
        if (request.getParameter("metaDescr") != null && !request.getParameter("metaDescr").equals("")) {
            metaDescr = request.getParameter("metaDescr");
        }
        if (request.getParameter("programaDescr") != null && !request.getParameter("programaDescr").equals("")) {
            programaDescr = request.getParameter("programaDescr");
        }
        if (request.getParameter("proyectoDescr") != null && !request.getParameter("proyectoDescr").equals("")) {
            proyectoDescr = request.getParameter("proyectoDescr");
        }
        if (request.getParameter("consulta") != null && !request.getParameter("consulta").equals("")) {
            consulta = Integer.parseInt(request.getParameter("consulta"));
        }
        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accionId"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("optAccion") != null && !request.getParameter("optAccion").equals("")) {
            optAccion = Integer.parseInt(request.getParameter("optAccion"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("obra") != null && !request.getParameter("obra").equals("")) {
            obraAccion = request.getParameter("obra");
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        cierreAccion = accionBean.isAccionCerrado(ramoId, year);
        contReq = accionBean.countRequerimientosByAccion(year, ramoId, programaId, metaId, accionId);
        cierrePPTO = accionBean.isAccionPPTOCerrado(ramoId, year);

        tipoCompromiso = accionBean.getTipoCompromisoMeta(year, ramoId, metaId);      
        if (tipoCompromiso.equals("N")) {
            compDis = "style='display:none;'";
        }
        if (optAccion == 1) {
            if (cierrePPTO) {
                cierreAccion = true;
            }
            accionId = accion.getAccionId();
            accion.setMunicipio(-1);
        } else {
            accion = accionBean.getAccionById(year, ramoId, metaId, accionId);
            accion.setAccionId(accionId);
            plantBool = accionBean.getResultSQLvalidaDeptoPlantilla(year, accion.getRamo(),
                    accion.getPrg(), accion.getDeptoId(), accion.getMeta(), accion.getAccionId());
        }
        if (!cierreAccion) {
            strResultado += "<input type='hidden' id='tipoCompro' value='" + tipoCompromiso + "' /> ";
            strResultado += "<table id='informacionAccion' cellspacing='10'>";
            strResultado += "   <tr>";
            strResultado += "       <td> Ramo </td>";
            strResultado += "       <td>" + ramoDescr + "</td>";
            strResultado += "   </tr>";
            strResultado += "   <tr>";
            strResultado += "       <td> Programa </td>";
            strResultado += "       <td>" + programaDescr + "</td>";
            strResultado += "   </tr>";
            strResultado += "   <tr> ";
            strResultado += "       <td> Proy./Act. </td>";
            strResultado += "       <td>" + proyectoDescr + "</td> ";
            strResultado += "   </tr>";
            strResultado += "   <tr> ";
            strResultado += "       <td> Meta </td>";
            strResultado += "       <td>" + metaDescr + "</td> ";
            strResultado += "   </tr>";
            strResultado += "</table>";

            metaBean = new MetaBean(tipoDependencia);
            metaBean.setStrServer(request.getHeader("host"));
            metaBean.setStrUbicacion(getServletContext().getRealPath(""));
            metaBean.resultSQLConecta(tipoDependencia);
            //Meter tabla calendarizar de meta
            estimacionList = metaBean.getEstimacionByMeta(year, ramoId, metaId);
            tipoC = metaBean.getTipoCalculo(year, ramoId, programaId, proyectoId, metaId);
            numObra = accionBean.getNumeroObra(year, ramoId, metaId, accionId);

            if (optAccion == 1) {
                strResultado += "<table>";
                numObra = "0";
            } else {
                strResultado += "<table> <tr> <td> No. Acci&oacute;n </td> <td>" + accion.getAccionId() + "</td> </tr>";
            }
            if (accion.getAccion() == null) {
                accion.setAccion("");
            }
            if (cierrePPTO) {
                disabled = "disabled";
            } else {
                disabled = "";
            }
            if (consulta == 1) {
                disabled = "disabled";
                disConsulta = "disabled";
            }
            if (!numObra.equals("0")) {
                disabled = "disabled";
                disConsulta = "disabled";
            }
            if (contReq > 0 && optAccion == 2) {
                disReq = "disabled";
            }
            if (!obraAccion.equals("")) {
                strResultado += "   <tr> ";
                strResultado += "       <td> Obra </td>";
                strResultado += "       <td>" + obraAccion + "</td> ";
                strResultado += "   </tr>";
            }
            strResultado += "<tr> <td> <p id='descrAccion'>Descripci&oacute;n </p></td> <td> <textArea id='txtDescrAcc' class='no-enter' maxlength='300' " + disabled + " style='text-transform:uppercase;' >"
                    + accion.getAccion() + "</textArea></td></tr></table>";
            strResultado += "<table> <tr> <td> <div> Unidad ejecutora </div> ";
            strResultado += "<select id='selUnidadEj' " + disConsulta + " " + disReq + ""
                    + " onChange='validaDeptoPlantilla(\""+accion.getDeptoId()+"\",\""+accion.getAccionId()+"\")'>";
            strResultado += "<option value='-1' " + disabled + "> -- Seleccione una unidad -- </option>";
            dependenciaList = accionBean.getDependencia(ramoId, year, programaId);
            for (Dependencia dependencia : dependenciaList) {
                if (dependencia.getDeptoId().equals(accion.getDeptoId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + dependencia.getDeptoId() + "' " + selected + ">" + dependencia.getDeptoId() + "-" + dependencia.getDepartamento() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td>";
            strResultado += "<td> <div> Unidad de medida </div> ";
            strResultado += "<select id='selMedida' " + disabled + ">";
            strResultado += "<option value='-1'> -- Seleccione una medida -- </option>";
            unidadMedidaList = accionBean.getUnidadMedida(year);
            for (UnidadMedida unidad : unidadMedidaList) {
                if (unidad.getUnidadMedidaId() == accion.getMedidaId()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + unidad.getUnidadMedidaId() + "' " + selected + ">" + unidad.getUnidadMedida() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td>";
            strResultado += "<td " + compDis + "> <div> Grupo poblacional </div>";
            strResultado += "<select id='selGrupo' " + disabled + ">";
            strResultado += "<option value='-1'> --Seleccione un grupo -- </option> ";
            grupoList = accionBean.getGrupoPoblacional();
            for (GrupoPoblacional grupo : grupoList) {
                if (grupo.getGrupoPobId() == accion.getGrupoPob()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + grupo.getGrupoPobId() + "' " + selected + ">" + grupo.getGripoPoblacional() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td> </tr>";
            strResultado += "<tr> <td> <div> Municipio </div> ";
            strResultado += "<select id='selMunicipio' onchange='getLocalidad()' " + disabled + " " + disReq + ">";
            strResultado += "<option value='-1'> --Seleccione un municipio-- </option>";
            if (optAccion == 1) {
                accion.setMedidaId(-1);
            }
            municipioList = accionBean.getMunicipios();
            for (Municipio municipio : municipioList) {
                if (municipio.getMunicipioId() == accion.getMunicipio()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + municipio.getMunicipioId() + "' " + selected + ">" + municipio.getMunicipioId() + "-" + municipio.getMunicipio() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td>";
            strResultado += "<td> <div> Localidad </div>";
            strResultado += "<select id='selLocalidad' " + disabled + " " + disReq + ">";
            strResultado += "<option value='-1'> -- Seleccione una localidad -- </option>";
            if (optAccion == 2) {
                localidadList = accionBean.getlocalidades(accion.getMunicipio());
                for (Localidad localidad : localidadList) {
                    if (localidad.getLocalidadId() == accion.getLocalidad()) {
                        selected = "selected";
                    }
                    strResultado += "<option value='" + localidad.getLocalidadId() + "' " + selected + ">" + localidad.getLocalidadId() + "-" + localidad.getLocalidad() + "</option>";
                    selected = "";
                }
            }
            strResultado += "</select> </td>";
            strResultado += "<td> <div> Tipo  de c&aacute;lculo</div>";
            tipoCalculoList = accionBean.getTipoCalculo();
            if (accion.getCalculo() == null) {
                accion.setCalculo("");
            }
            strResultado += "<select id='selCalculo' " + disabled + ">";
            strResultado += "<option value='-1' > -- Seleccione tipo de c&aacute;lculo --";
            for (TipoCalculo tipoCalculo : tipoCalculoList) {
                if (accion.getCalculo().equals(tipoCalculo.getAbrev())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-"
                        + tipoCalculo.getTipoCalculo() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td> </tr>";
            strResultado += "<tr> <td> <div> L&iacute;nea PED </div>";
            strResultado += "<select id='selLineaPed' " + disabled + " onchange='getLineaSectorial()'>";
            strResultado += "<option value='-1'> -- Seleccione una l&iacute;nea PED -- </option>";
            lineaList = accionBean.getLineaAccion(year, ramoId, programaId);
            for (Linea linea : lineaList) {
                if (linea.getLineaId().equalsIgnoreCase(accion.getLineaPed())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + linea.getLineaId() + "' " + selected + ">"
                        + linea.getLineaId() + "-" + linea.getLinea() + "</option>";
                selected = "";
            }
            if (accion.getCalculo() == null) {
                accion.setCalculo("");
            }
            strResultado += "</select> </td>";
            strResultado += "<td> <div> L&iacute;nea sectorial </div>";
            strResultado += "<select id='selLineaSect' " + disabled + "> ";
            strResultado += "<option value='-1'> -- Seleccione una l&iacute;nea -- </option>";
            if (accion.getLineaPed() == null) {
                accion.setLineaPed("");
            }
            if (accion.getLineaSectorial() == null) {
                accion.setLineaSectorial("");
            }
            if (optAccion == 2) {
                lineaSectorialList = accionBean.getLineaSectorialByLineaAccion(accion.getLineaPed(), year);
                for (Linea lineaSect : lineaSectorialList) {
                    if (lineaSect.getLineaId().equalsIgnoreCase(accion.getLineaSectorial())) {
                        selected = "selected";
                    }
                    strResultado += "<option value='" + lineaSect.getLineaId() + "' " + selected + "> " + lineaSect.getLineaId()
                            + "-" + lineaSect.getLinea() + " </option>";
                    selected = "";
                }
            }
            strResultado += "</select> </td>";
            strResultado += "<td " + compDis + "> <div id='divGenero'> <div> Mujeres <input type='text' id='accMuj' value='" + accion.getBenefMuj() + "' " + disabled + "/> </div>";
            strResultado += "<div> Hombres  <input type='text' id='accHom' value='" + accion.getBenefHom() + "' " + disabled + "/> </div> </div> </td> </tr>";
            strResultado += "<td> <div style='display:none;'> Tipo de acci&oacute;n </div>";
            strResultado += "<select id='selTipoAccion' style='display:none;' " + disConsulta + "> <option value='-1'> -- Seleccione un tipo de acci&oacute;n -- </option> ";
            tipoAccionList = accionBean.getTipoAccion();
            for (TipoAccion tipoAccion : tipoAccionList) {
                if (accion.getTipoAccion() == tipoAccion.getTipoAccionId()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoAccion.getTipoAccionId() + "' " + selected + ">"
                        + tipoAccion.getTipoAccionId() + "-" + tipoAccion.getTipoAccion() + "</option>";
                selected = "";
            }
            strResultado += "</tr> </table>";
            strResultado += "<center><div>";
            if (consulta != 1) {
                if (optAccion == 1) {
                    strResultado += "<input type='button' id='btnSaveAccion' value='Guardar' onclick='saveAccion()' " + disabled + ">";
                } else {
                    strResultado += "<input type='button' id='btnSaveAccion' value='Guardar' onclick='editAccion(" + accionId + ")' " + disabled + ">";
                }
            }
            strResultado += "<input type='button' id='btnCancelAccion' value='Cerrar' onclick='cerrarInfoAccion()'> </div> </center>";
            strResultado += "<script type='text/javascript'>";

            strResultado += "              $('#accMuj').keydown(function (e) {";
            strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||";
            strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
            strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
            strResultado += "                            return;";
            strResultado += "                  }";
            strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
            strResultado += " e.preventDefault();";
            strResultado += "                   }";
            strResultado += "                });";
            strResultado += "              $('#accHom').keydown(function (e) {";
            strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||";
            strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
            strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
            strResultado += "                            return;";
            strResultado += "                  }";
            strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
            strResultado += " e.preventDefault();";
            strResultado += "                   }";
            strResultado += "                });";

            strResultado += "</script>";




        } else {
            strResultado = "cerrado";
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