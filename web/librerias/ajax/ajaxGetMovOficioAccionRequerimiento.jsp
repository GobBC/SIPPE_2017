<%-- 
    Document   : ajaxGetMovOficioAccionRequerimiento
    Created on : Dec 14, 2015, 9:25:07 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Parametros"%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Locale"%>
<%@page import="gob.gbc.entidades.MovOficiosAccionReq"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ParametrosBean parametrosBean = null;
    Parametros objParametros = new Parametros();
    boolean enableAll = false;
    RecalendarizacionBean recalBean = null;
    Requerimiento requerimiento = new Requerimiento();
    MovOficiosAccionReq movAccReq = null;
    List<RecalendarizacionAccionReq> recalAccionReq = new ArrayList<RecalendarizacionAccionReq>();
    MovimientosRecalendarizacion recalendarizacion;
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    Double disponible = 0.0;
    Double sumRecalendarizacion = 0.0;
    DateFormat formatYear = new SimpleDateFormat("yyyy");
    DateFormat formatMonth = new SimpleDateFormat("MM");
    Calendar cal = Calendar.getInstance();
    String tipoDependencia = new String();
    String strResultado = new String();
    String ramoId = new String();
    String ramoDescr = new String();
    String programaId = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String status = new String();
    String inputAllDisable = "";
    String metaDescr = new String();
    String accionDescr = new String();
    String partida = new String();
    String partidaDescr = new String();
    String relLaboral = new String();
    String relLaboralDescr = new String();
    String fuente = new String();
    String fuenteDescr = new String();
    String infoReq[] = new String[19];
    String disabled = new String();
    String rol = new String();
    String cadenaMeses = new String();
    String tipoProyecto = new String();
    boolean existe = false;
    boolean puedeModificar = false;
    boolean enero = false;
    boolean validaTrimestre = false;
    int meses[] = new int[3];
    int proyectoId = 0;
    int edicion = 0;
    int year = 0;
    int metaId = 0;
    int accion = 0;
    int requ = 0;
    int numAccionReq = 0;
    int identificador = -1;
    int yearAct = Integer.parseInt(formatYear.format(cal.getTime()));
    int mesTemp = 0;
    int monthAct = 0;
    int normativo = 0;
    int folio = 0;
    String infoReqTemp[] = new String[19];

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    request.setCharacterEncoding("UTF-8");

    try {
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("ramoDescr") != null && !request.getParameter("ramoDescr").equals("")) {
            ramoDescr = request.getParameter("ramoDescr");
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
        if (request.getParameter("programaDescr") != null && !request.getParameter("programaDescr").equals("")) {
            programaDescr = request.getParameter("programaDescr");
        }
        if (request.getParameter("proyectoDescr") != null && !request.getParameter("proyectoDescr").equals("")) {
            proyectoDescr = request.getParameter("proyectoDescr");
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("metaDescr") != null && !request.getParameter("metaDescr").equals("")) {
            metaDescr = request.getParameter("metaDescr");
        }
        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accion = Integer.parseInt(request.getParameter("accionId"));
        }
        if (request.getParameter("accionDescr") != null && !request.getParameter("accionDescr").equals("")) {
            accionDescr = request.getParameter("accionDescr");
        }
        if (request.getParameter("partida") != null && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if (request.getParameter("partidaDescr") != null && !request.getParameter("partidaDescr").equals("")) {
            partidaDescr = request.getParameter("partidaDescr");
        }
        if (request.getParameter("relLaboral") != null && !request.getParameter("relLaboral").equals("")) {
            relLaboral = request.getParameter("relLaboral");
            if (relLaboral.equals("-1")) {
                relLaboral = "0";
            }
        }
        if (request.getParameter("relLaboralDescr") != null && !request.getParameter("relLaboralDescr").equals("")) {
            relLaboralDescr = request.getParameter("relLaboralDescr");
        }
        if (request.getParameter("fuente") != null && !request.getParameter("fuente").equals("")) {
            fuente = request.getParameter("fuente");
        }
        if (request.getParameter("fuenteDescr") != null && !request.getParameter("fuenteDescr").equals("")) {
            fuenteDescr = request.getParameter("fuenteDescr");
        }
        if (request.getParameter("req") != null && !request.getParameter("req").equals("")) {
            requ = Integer.parseInt(request.getParameter("req"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt(request.getParameter("identificador"));
        }
        if (Utilerias.existeParametro("edicion", request)) {
            edicion = Integer.parseInt(request.getParameter("edicion"));
        }
        if (Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (Utilerias.existeParametro("fecha", request)) {
            mesTemp = Integer.parseInt(request.getParameter("fecha").split("\\/")[1]);
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            status = request.getParameter("estatus");
            if ((!status.equalsIgnoreCase("C")) && (!status.equalsIgnoreCase("X")) && (!status.equalsIgnoreCase("R"))) {
                inputAllDisable = "disabled=''";
                puedeModificar = false;
                disabled = "disabled";
            }
        }

        if (session.getAttribute("recalendarizacion") != null && session.getAttribute("recalendarizacion") != "") {
            recalendarizacion = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
            recalAccionReq = recalendarizacion.getMovOficiosAccionReq();
            if (identificador != -1) {
                for (RecalendarizacionAccionReq movtoAccionReq : recalAccionReq) {
                    if (movtoAccionReq.getIdentificador() == identificador) {
                        movAccReq = movtoAccionReq.getMovAccionReq();
                    }
                }
            }
        }

        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);

        parametrosBean = new ParametrosBean(tipoDependencia);
        parametrosBean.setStrServer((request.getHeader("Host")));
        parametrosBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        parametrosBean.resultSQLConecta(tipoDependencia);

        objParametros = parametrosBean.getParametros();
        infoReqTemp = recalBean.getRequerimientoByIdUsuario(year, ramoId, programaId, metaId, accion, requ);
        disponible = recalBean.getDisponible(year,
                ramoId,
                programaId,
                infoReqTemp[4],
                Integer.parseInt(infoReqTemp[5]),
                metaId,
                accion,
                partida, fuente.split("\\.")[0],
                fuente.split("\\.")[1],
                fuente.split("\\.")[2],
                relLaboral, 12);
        
        if(disponible > 0 || !status.equalsIgnoreCase("X")){        
            if (objParametros.getValidaTodosTrimestre().equals("S")) {
                enableAll = true;
            } else {
                enableAll = false;
            }

            validaTrimestre = recalBean.getResultSqlGetparametroTrimestre();
            monthAct = mesTemp;
            meses = recalBean.getMesesTrimestreByMes(monthAct, validaTrimestre);
            if (!validaTrimestre) {
                if (meses.length > 0) {
                    monthAct = meses[0];
                } else {
                    monthAct = 1;
                }
            }

            if (!rol.equals(recalBean.getRolNormativo())) {
                disabled = "disabled";
                normativo = 1;
            }
            enero = recalBean.modificaEnero();
            if (edicion > 0) {
                infoReq = recalBean.getRequerimientoByIdUsuario(year, ramoId, programaId, metaId, accion, requ);
                ramoId = infoReq[0];
                ramoDescr = infoReq[1];
                programaId = infoReq[2];
                programaDescr = infoReq[3];
                proyectoDescr = infoReq[6];
                metaId = Integer.parseInt(infoReq[7]);
                metaDescr = infoReq[8];
                accion = Integer.parseInt(infoReq[9]);
                accionDescr = infoReq[10];
                requ = Integer.parseInt(infoReq[11]);
                partida = infoReq[13];
                partidaDescr = infoReq[14];
                relLaboral = infoReq[15];
                relLaboralDescr = infoReq[16];
                fuente = infoReq[17];
                fuenteDescr = infoReq[18];
            }
            numAccionReq = recalBean.countMovtoAccionReqByInfo(year, ramoId,
                    programaId, metaId, accion, partida, fuente.split("\\.")[0],
                    fuente.split("\\.")[0], fuente.split("\\.")[0], relLaboral, requ);
            if (numAccionReq > 0) {
                existe = true;
            } else {
                for (RecalendarizacionAccionReq movtoAccionReq : recalAccionReq) {
                    if (ramoId.equals(movtoAccionReq.getMovAccionReq().getRamo())
                            && programaId.equals(movtoAccionReq.getMovAccionReq().getPrograma())
                            && metaId == movtoAccionReq.getMovAccionReq().getMeta()
                            && accion == movtoAccionReq.getMovAccionReq().getAccion()
                            && partida.equals(movtoAccionReq.getMovAccionReq().getPartida())
                            && relLaboral.equals(movtoAccionReq.getMovAccionReq().getRelLaboral())
                            && fuente.split("\\.")[0].equals(movtoAccionReq.getMovAccionReq().getFuente())
                            && fuente.split("\\.")[1].equals(movtoAccionReq.getMovAccionReq().getFondo())
                            && fuente.split("\\.")[2].equals(movtoAccionReq.getMovAccionReq().getRecurso())
                            && requ == movtoAccionReq.getMovAccionReq().getRequerimiento() && puedeModificar) {
                        existe = true;
                    } else {
                        existe = false;
                    }
                }
            }
            if (!existe) {
                if (!recalBean.getCountMovsAccionRequerimientoById(ramoId, programaId, metaId,
                        accion, partida, fuente.split("\\.")[0], fuente.split("\\.")[1],
                        fuente.split("\\.")[2], requ) || edicion > 0) {
                    if (!status.equals("A")) {
                        requerimiento = recalBean.getRequerimientoById(year, ramoId, programaId, metaId, accion, requ);
                    } else {
                        requerimiento = recalBean.getResultGetRequerimientoHistorico(year, ramoId, programaId, metaId, accion, requ, folio);
                    }
                    strResultado += "<div id='popUp-accion-req' style=''> ";
                    strResultado += "	<div> ";
                    strResultado += "		<table id='infoMetaRC' cellspacing='10'> ";
                    strResultado += "			<tbody> ";
                    strResultado += "				<tr>  ";
                    strResultado += "					<td>Ramo:</td>  ";
                    strResultado += "					<td> " + ramoDescr + " </td> ";
                    strResultado += "				</tr> ";
                    strResultado += "				<tr>  ";
                    strResultado += "					<td>Programa:</td> ";
                    strResultado += "					<td> " + programaDescr + " </td> ";
                    strResultado += "				</tr>  ";
                    strResultado += "				<tr>  ";
                    strResultado += "					<td> Proyecto/Actividad: </td>  ";
                    strResultado += "					<td> " + proyectoDescr + " </td>  ";
                    strResultado += "				</tr> ";
                    strResultado += "				<tr>  ";
                    strResultado += "					<td>Meta:</td>  ";
                    strResultado += "					<td> " + metaDescr + " </td>  ";
                    strResultado += "				</tr> ";
                    strResultado += "				<tr>  ";
                    strResultado += "					<td>Acci&oacute;n:</td>  ";
                    strResultado += "					<td> " + accionDescr + " </td>  ";
                    strResultado += "				</tr> ";
                    strResultado += "				<tr>  ";
                    strResultado += "					<td>Partida</td>  ";
                    strResultado += "					<td> " + partidaDescr + " </td>  ";
                    strResultado += "				</tr> ";
                    if (!relLaboral.equals("-1") && !relLaboral.equals("0")) {
                        strResultado += "				<tr>  ";
                        strResultado += "					<td>Relaci&oacute;n laboral</td>  ";
                        strResultado += "					<td> " + relLaboralDescr + " </td>  ";
                        strResultado += "				</tr> ";
                    }
                    strResultado += "				<tr>  ";
                    strResultado += "					<td>Fuente de financiamiento</td>  ";
                    strResultado += "					<td> " + fuenteDescr + " </td>  ";
                    strResultado += "				</tr> ";
                    strResultado += "			</tbody> ";
                    strResultado += "		</table>  ";
                    strResultado += "		<br> ";
                    strResultado += "<div> ";
                    strResultado += "			<fieldset id='fieldsetMetaRC'> ";
                    strResultado += "				<legend>Calendarizaci&oacute;n original</legend> ";
                    strResultado += "<table id='tbl-info-req'>";
                    strResultado += "<tr> <td> <div> Descripci&oacute;n </div> <textArea disabled class='txtArea-accReq no-enter'>" + requerimiento.getReq() + "</textArea> </td> </tr>";
                    strResultado += "<tr> <td> <div> Justificaci&oacute;n </div> <textArea disabled class='txtArea-accReq no-enter'>" + requerimiento.getJustif() + "</textArea> </td> </tr>";
                    strResultado += "</table>";
                    strResultado += "<input type='hidden' id='selRamoEd' value='" + ramoId + "' />";
                    strResultado += "<input type='hidden' id='selPrgEd' value='" + programaId + "' />";
                    strResultado += "<input type='hidden' id='selMetaEd' value='" + metaId + "' />";
                    strResultado += "<input type='hidden' id='selAccionEd' value='" + accion + "' />";
                    strResultado += "<input type='hidden' id='selReqEd' value='" + requ + "' />";
                    strResultado += Utileria.getCalendarizacionAccionReq(requerimiento, "disabled", true, 0, false, meses,enableAll);
                    strResultado += "<br/><div class='cant-accion-req'> ";
                    strResultado += "<div> Cantidad <input value='" + numberF.format(Utileria.sumaRequerimiento(requerimiento)) + "' disabled> </div>";
                    strResultado += "<div> Costo unitario <input value='" + numberF.format(requerimiento.getCostoUnitario()) + "'  disabled> </div>";
                    strResultado += "<div> Costo anual <input id='inpTxtCostoOriginal' value='" + numberF.format(requerimiento.getCostoAnual()) + "' disabled> </div>";
                    strResultado += "</div> ";
                    strResultado += "</fieldset> ";
                    strResultado += "</div> ";
                    strResultado += "		<br>  ";
                    strResultado += "		<div> ";
                    strResultado += "				<fieldset id='fieldsetMetaRC'> ";
                    strResultado += "					<legend>Recalendarizaci&oacute;n</legend> ";
                    if (movAccReq != null) {
                        strResultado += Utileria.getCalendarizacionAccionReq(Utileria.getRequerimiento(movAccReq), inputAllDisable, true, monthAct, enero, meses,enableAll);
                        strResultado += "<br/><div class='cant-accion-req'> ";
                        strResultado += "<div> Cantidad <input id='inpTxtCantidad' value='" + numberF.format(movAccReq.getCantidad()) + "' disabled> </div>";
                        strResultado += "<div> Costo unitario <input id='inpTxtCantUnit' value='" + numberF.format(movAccReq.getCostoUnitario()) + "' tabindex='0' maxlength='16' "
                                + "onblur='agregarFormato(\"inpTxtCantUnit\");calculaCosto()' onkeyup='validaMascara(\"inpTxtCantUnit\")' " + disabled + " > </div>";
                        strResultado += "<div> Costo anual <input id='inpTxtCantAnual' value='" + numberF.format(movAccReq.getCostoAnual()) + "' disabled /> </div>";
                        cadenaMeses = movAccReq.getEne() + "|" + movAccReq.getFeb() + "|" + movAccReq.getMar() + "|" + movAccReq.getAbr() + "|" + movAccReq.getMay() + "|" + movAccReq.getJun()
                                + "|" + movAccReq.getJul() + "|" + movAccReq.getAgo() + "|" + movAccReq.getSep() + "|" + movAccReq.getOct() + "|" + movAccReq.getNov() + "|" + movAccReq.getDic();
                        if (rol.equals(recalBean.getRolNormativo())) {
                            cadenaMeses += "|" + movAccReq.getCostoUnitario();
                        }
                    } else {
                        strResultado += Utileria.getCalendarizacionAccionReq(requerimiento, "", true, monthAct, enero, meses,enableAll);
                        strResultado += "<br/><div class='cant-accion-req'> ";
                        strResultado += "<div> Cantidad <input id='inpTxtCantidad' value='" + numberF.format(requerimiento.getCantidad()) + "' disabled> </div>";
                        strResultado += "<div> Costo unitario <input id='inpTxtCantUnit' value='" + numberF.format(requerimiento.getCostoUnitario()) + "' tabindex='0' maxlength='16' "
                                + "onblur='agregarFormato(\"inpTxtCantUnit\");calculaCosto()' onkeyup='validaMascara(\"inpTxtCantUnit\")' " + disabled + " />  </div>";
                        strResultado += "<div> Costo anual <input id='inpTxtCantAnual' value='" + numberF.format(requerimiento.getCostoAnual()) + "' disabled /> </div>";
                        cadenaMeses = requerimiento.getCantEne() + "|" + requerimiento.getCantFeb() + "|" + requerimiento.getCantMar() + "|" + requerimiento.getCantAbr()
                                + "|" + requerimiento.getCantMay() + "|" + requerimiento.getCantJun() + "|" + requerimiento.getCantJul() + "|" + requerimiento.getCantAgo()
                                + "|" + requerimiento.getCantSep() + "|" + requerimiento.getCantOct() + "|" + requerimiento.getCantNov() + "|" + requerimiento.getCantDic();
                        if (rol.equals(recalBean.getRolNormativo())) {
                            cadenaMeses += "|" + requerimiento.getCostoUnitario();
                        }
                    }
                    strResultado += "			<div class='calenVistaRC'> ";
                    strResultado += "</div> ";
                    strResultado += "</fieldset> ";
                    strResultado += "<br>   ";
                    strResultado += "			<script type='text/javascript'> ";
                    strResultado += "				$('.capt-mes').keydown(function (e) { ";
                    strResultado += "					if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || (e.keyCode >= 35 && e.keyCode <= 40)) { ";
                    strResultado += "						return; ";
                    strResultado += "					}  ";
                    strResultado += "					if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {  ";
                    strResultado += "						e.preventDefault();  ";
                    strResultado += "					}                 ";
                    strResultado += "				});              ";
                    strResultado += "				$('#inpTxtCantUnit').keydown(function (e) { ";
                    strResultado += "					if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || (e.keyCode >= 35 && e.keyCode <= 40)) {  ";
                    strResultado += "						return;  ";
                    strResultado += "					} ";
                    strResultado += "					if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {  ";
                    strResultado += "						e.preventDefault();          ";
                    strResultado += "					}          ";
                    strResultado += "				}); ";
                    strResultado += "			</script> ";
                    strResultado += "		</div> ";
                    strResultado += "	</div> ";
                    strResultado += "	<br> ";
                    strResultado += "    <center> ";
                    if (!status.equals("R") && !status.equals("X")) {
                        strResultado += "        <input type='button' value='Cerrar' onclick='fadeOutPopUp(\"PopUpZone\");' /> ";
                    } else {
                        strResultado += "        <input type='button' value='Guardar' onclick='actualizarAccionReq(\"" + identificador + "\")' /> ";
                        strResultado += "        <input type='button' value='Cancelar' onclick='fadeOutPopUp(\"PopUpZone\");' /> ";
                    }
                    strResultado += "        <input type='hidden' value='" + requerimiento.getTipoGasto() + "' id='tipoGasto' /> ";
                    strResultado += "        <input type='hidden' value='" + requerimiento.getDepto() + "' id='depto' /> ";
                    strResultado += "        <input type='hidden' value='" + requerimiento.getReq() + "' id='descripcion' /> ";
                    strResultado += "        <input type='hidden' value='" + requerimiento.getArticulo() + "' id='articulo' /> ";
                    strResultado += "        <input type='hidden' value='" + cadenaMeses + "' id='cadenaMeses' /> ";
                    strResultado += "        <input type='hidden' value='" + normativo + "' id='isNormativo' /> ";
                    strResultado += "    </center> ";
                    strResultado += "</div> ";
                } else {
                    strResultado += "3|Este requerimiento tiene un movimiento previo no autorizado";
                }
            } else {
                strResultado += "3|Este requerimiento ya se encuentra enlistado";
            }
        }else{
            strResultado = "3|Este código no cuenta con disponible";
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
        if (recalBean != null) {
            recalBean.resultSQLDesconecta();
        }
        if (parametrosBean != null) {
            parametrosBean.resultSQLDesconecta();
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