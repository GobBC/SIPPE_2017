<%-- 
    Document   : ajaxDisplayPopUpCaratula
    Created on : May 24, 2016, 08:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.TipoModificacion"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.entidades.TipoSesion"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.aplicacion.RevisionCaratulaBean"%>
<%@page import="gob.gbc.entidades.RevisionCaratula"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    Caratula objCaratula = null;
    CaratulaBean caratulaBean = null;
    RevisionCaratulaBean revisionCaratulaBean = null;
    ArrayList<TipoSesion> tipoSesionList = null;
    List<RevisionCaratula> revisionesCaratulaList = null;
    ArrayList<TipoModificacion> arrTiposModificaciones = null;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String ramo = new String();
    String disabledCheckStatus = "";
    String allDisabled = "";
    String rol = new String();
    String selected = new String();
    String strResultado = new String();
    String fechaSession = new String();
    String tipoDependencia = new String();
    long caratula = -1;
    int yearAct;
    int monthAct;
    int cont = 1;
    int year = 0;
    int caratulaYearSesion = 0;
    int caratulaTipoSesion = 0;
    int caratulaTipoModificacion = 0;

    try {
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramo = request.getParameter("ramoId");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependecia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (Utilerias.existeParametro("fechaSession", request)) {
            fechaSession = (String) request.getParameter("fechaSession");
        }
        if (request.getParameter("caratula") != null && !request.getParameter("caratula").equals("")) {
            caratula = Long.parseLong(request.getParameter("caratula"));
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        revisionCaratulaBean = new RevisionCaratulaBean(tipoDependencia);
        revisionCaratulaBean.setStrServer((request.getHeader("Host")));
        revisionCaratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        revisionCaratulaBean.resultSQLConecta(tipoDependencia);

        tipoSesionList = caratulaBean.getTipoSesiones();
        arrTiposModificaciones = caratulaBean.getTiposModificaciones();

        if (caratula != -1) {
            objCaratula = caratulaBean.getResultSQLCaratulaByYearIdRamoIdCaratula(String.valueOf(year), ramo, caratula);

            if (objCaratula.getiStatus().equals("C")) {
                allDisabled = " disabled ";
            }

            if (!rol.equals(caratulaBean.getRolNormativo())) {
                disabledCheckStatus = "disabled";

            }

        }

        strResultado += "<div> ";
        strResultado += "<div> ";
        strResultado += "<div > ";
        strResultado += "<form id='formCaratula' name='formCaratula'> ";
        strResultado += "<table> ";

        if (caratula != -1) {

            strResultado += "<tr> ";
            strResultado += "<td>Caratula Abierta:</td>";
            strResultado += "<td> ";

            if (objCaratula.getiStatus().equals("C")) {
                //strResultado += "<input " + disabledCheckStatus + "  type='checkbox' id='checkStatus' onchange='actualizaCaratula(1);' > ";
                strResultado += "<input " + disabledCheckStatus + "  type='checkbox' id='checkStatus'  > ";
            } else {
                //strResultado += "<input " + disabledCheckStatus + " checked type='checkbox' id='checkStatus'  onchange='actualizaCaratula(1);'  > ";
                strResultado += "<input " + disabledCheckStatus + " checked type='checkbox' id='checkStatus'   > ";
            }

            strResultado += "</td> ";
            strResultado += "</tr> ";

        } else {

            strResultado += "<tr> ";
            strResultado += "<td> ";
            strResultado += "</td> ";
            strResultado += "<td> ";
            //strResultado += "<input style='display:none' checked type='checkbox' id='checkStatus'  onchange='actualizaCaratula(1);' > ";
            strResultado += "<input style='display:none' checked type='checkbox' id='checkStatus'   > ";
            strResultado += "</td> ";
            strResultado += "</tr> ";

        }

        //Ramo del usuario
        strResultado += "<tr> ";
        strResultado += "<td>Ramo:</td> ";
        strResultado += "<td>" + ramo + "</td>";
        strResultado += "</tr> ";

        //Año presupuestal
        strResultado += "<tr> ";
        strResultado += "<td>Año Presupuestal:</td> ";
        strResultado += "<td>" + year + "</td>  ";
        strResultado += "</tr> ";

        //Fecha sesion
        strResultado += "<tr> ";
        strResultado += "<td> ";
        strResultado += "Fecha de la Sesi&oacute;n: ";
        strResultado += "</td> ";
        strResultado += "<td> ";
        if (objCaratula != null) {
            fechaSession = df.format(formatter.parse(objCaratula.getsFechaRevision()));
        } else {
            Date date = new Date();
            fechaSession = df.format(date);
        }
        strResultado += "<input " + allDisabled + " readonly='readonly' type='text' id='fecha' name='fecha' maxlength='10' class='datepicker input-fecha' value='" + fechaSession + "'  onChange='capturaCaratulasGetComboNumSesion()' >";
        strResultado += "</td>  ";
        strResultado += "</tr> ";

        //Tipo de modificacion
        strResultado += "<tr> ";
        strResultado += "<td>Tipo Modificaci&oacute;n:</td> ";
        strResultado += "<td> ";
        //strResultado += "<select " + allDisabled + " id='selTipoModificacion' name='selTipoModificacion'  style='width: 400px;' onchange='javascript:cargaCaratula()'>";
        strResultado += "<select " + allDisabled + " id='selTipoModificacion' name='selTipoModificacion'  style='width: 400px;' onChange='capturaCaratulasGetComboNumSesion()' >";
        strResultado += "<option value='-1'> -- Seleccione un tipo de modificaci&oacute;n -- </option>";
        for (TipoModificacion objTipoModifiacion : arrTiposModificaciones) {
            selected = "";
            if (objCaratula != null) {
                if (objTipoModifiacion.getIntTipoModificacion() == objCaratula.getIntTipoModificacion()) {
                    selected = "selected";
                } else {
                    selected = "";
                }
            }
            strResultado += "<option " + selected + " value='" + objTipoModifiacion.getIntTipoModificacion() + "'>" + objTipoModifiacion.getStrDescr() + "</option>";
        }
        strResultado += "</select>";
        strResultado += "</td> ";
        strResultado += "</tr> ";

        //Tipo de sesion
        strResultado += "<tr> ";
        strResultado += "<td>Tipo Sesi&oacute;n:</td> ";
        strResultado += "<td> ";
        //strResultado += "<select " + allDisabled + " id='selTipoSesion' name='selTipoSesion'  style='width: 400px;' onchange='javascript:cargaCaratula()' onChange='capturaCaratulasGetComboNumSesion()' >";
        strResultado += "<select " + allDisabled + " id='selTipoSesion' name='selTipoSesion'  style='width: 400px;' onChange='capturaCaratulasGetComboNumSesion()' >";
        strResultado += "<option value='-1'> -- Seleccione un tipo de sesi&oacute;n -- </option>";
        for (TipoSesion tipoSesion : tipoSesionList) {
            selected = "";
            if (objCaratula != null) {
                if (tipoSesion.getiSesion() == objCaratula.getiTipoSesion()) {
                    selected = "selected";
                } else {
                    selected = "";
                }
            }
            strResultado += "<option " + selected + " value='" + tipoSesion.getiSesion() + "'>" + tipoSesion.getsDescripcion() + "</option>";
        }
        strResultado += "</select>";
        strResultado += "</td> ";
        strResultado += "</tr> ";

        //Obtenemos la lista de revisiones que vamos a manejar en Numero de sesion, Numero de modificacion presupuestal, numero de modificacion programatica
        if (objCaratula != null) {
            caratulaYearSesion = objCaratula.getiYearSesion();
            caratulaTipoModificacion = objCaratula.getIntTipoModificacion();
            caratulaTipoSesion = objCaratula.getiTipoSesion();
        }
        revisionesCaratulaList = revisionCaratulaBean.getListRevisionesCaratulaByRamoCaratulaYear(ramo, caratula, year, caratulaYearSesion, caratulaTipoModificacion, caratulaTipoSesion);

        //Numero de sesion       
        strResultado += "<tr> ";
        strResultado += "<td> ";
        strResultado += "Numero de la Sesi&oacute;n: ";
        strResultado += "</td>  ";
        strResultado += "<td> ";
        //strResultado += "<select " + allDisabled + " id='selNumeroSesion' name='selNumeroSesion'  style='width: 400px;' onchange='cargaCaratula()'> ";
        strResultado += "<select " + allDisabled + " id='selNumeroSesion' name='selNumeroSesion'  style='width: 400px;' > ";
        strResultado += "<option value='-1'> -- Seleccione un numero de sesi&oacute;n -- </option>";
        if (objCaratula != null) {
            for (RevisionCaratula revisionCaratula : revisionesCaratulaList) {
                if (revisionCaratula.isSelected_Num_Session()) {
                    strResultado += "<option selected class='enabled' value='" + revisionCaratula.getRevision() + "'>";
                } else {
                    if (revisionCaratula.isLibre_Num_Session()) {
                        strResultado += "<option class='enabled' value='" + revisionCaratula.getRevision() + "'>";
                    } else {
                        strResultado += "<option class='disabled' disabled value='" + revisionCaratula.getRevision() + "'>";
                    }
                }
                strResultado += " " + revisionCaratula.getDescr_Corta() + " " + revisionCaratula.getDescr();
                strResultado += "</option>";
            }
        }
        strResultado += "</select> ";
        strResultado += "</td>  ";
        strResultado += "</tr> ";

        //Numero de modificacion presupuestal
        strResultado += "<tr> ";
        strResultado += "<td> ";
        strResultado += "Numero de Modificaci&oacute;n Presupuestal: ";
        strResultado += "</td>  ";
        strResultado += "<td> ";
        //strResultado += "<select " + allDisabled + " id='selModPresup' name='selModPresup'  style='width: 400px;' onchange='cargaCaratula()'> ";
        strResultado += "<select " + allDisabled + " id='selModPresup' name='selModPresup'  style='width: 400px;' > ";
        strResultado += "<option value=''> -- No aplica -- </option>";
        for (RevisionCaratula revisionCaratula : revisionesCaratulaList) {
            if (revisionCaratula.isSelected_Mod_Presup()) {
                strResultado += "<option selected class='enabled' value='" + revisionCaratula.getRevision() + "'>";
            } else {
                strResultado += "<option class='enabled' value='" + revisionCaratula.getRevision() + "'>";
            }
            strResultado += revisionCaratula.getDescr_Corta() + " " + revisionCaratula.getDescr();
            strResultado += "</option>";
        }
        strResultado += "</select> ";
        strResultado += "</td>  ";
        strResultado += "</tr> ";

        //Numero de modificacion programatica
        strResultado += "<tr> ";
        strResultado += "<td> ";
        strResultado += "Numero de Modificaci&oacute;n Program&aacute;tica: ";
        strResultado += "</td>  ";
        strResultado += "<td> ";
        //strResultado += "<select " + allDisabled + " id='selModProg' name='selModProg'  style='width: 400px;' onchange='cargaCaratula()'> ";
        strResultado += "<select " + allDisabled + " id='selModProg' name='selModProg'  style='width: 400px;' > ";
        strResultado += "<option value=''> -- No aplica -- </option>";
        for (RevisionCaratula revisionCaratula : revisionesCaratulaList) {
            if (revisionCaratula.isSelected_Mod_Prog()) {
                strResultado += "<option selected class='enabled' value='" + revisionCaratula.getRevision() + "'>";
            } else {
                strResultado += "<option class='enabled' value='" + revisionCaratula.getRevision() + "'>";
            }
            strResultado += revisionCaratula.getDescr_Corta() + " " + revisionCaratula.getDescr();
            strResultado += "</option>";
        }
        strResultado += "</select> ";
        strResultado += "</td>  ";
        strResultado += "</tr> ";

        strResultado += "</table> ";
        strResultado += "</form> ";
        strResultado += "</div> ";
        strResultado += "   </div>";
        /* strResultado += "<center>";

         if (objCaratula != null) {
         if (objCaratula.getiStatus().equals("A")) {
         strResultado += "<input id='GuardarCaratula' type='button' value='Guardar' onclick=\"actualizaCaratula();\" /> ";
         }
         } else {
         strResultado += "<input id='GuardarCaratula' type='button' value='Guardar' onclick=\"actualizaCaratula();\" /> ";
         }

         strResultado += "<input id='CancelarCaratula' type='button' value='Cancelar' onclick=\"fadeOutPopUp('PopUpZone')\" /> ";
         strResultado += "</center>";*/
        strResultado += "</div> ";
        strResultado += "<script type='text/javascript'>";
        strResultado += "$('.datepicker').datepicker(); ";
        strResultado += "$('.datepicker' ).datepicker('option','dateFormat','dd/mm/yy');";
        strResultado += "</script>";

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {

        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }

        if (revisionCaratulaBean != null) {
            revisionCaratulaBean.resultSQLDesconecta();
        }

        out.print(strResultado);
    }
%>
