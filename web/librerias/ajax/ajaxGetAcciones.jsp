<%-- 
    Document   : ajaxGetAcciones
    Created on : Apr 23, 2015, 3:50:15 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.PlantillaBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    PlantillaBean plantillaBean = null;
    AccionBean accionBean = null;
    Accion accion = new Accion();
    List<Accion> accionList = new ArrayList<Accion>();
    String mensaje = new String();
    String strResultado = new String();
    String accionDesc = new String();
    String tipoCalculo = new String();
    String lineaPed = new String();
    String lineaSectorial = new String();
    String rowPar = new String();
    String programaId = new String();
    String ramoId = new String();
    String tipoDependencia = new String();
    String depto = new String();
    boolean cierreAccion = false;
    boolean plantBool = false;
    int countReq = 0;
    int localidad = 0;
    int mpo = 0;
    int grupoPob = 0;
    int metaId = 0;
    int year = 0;
    int accionId = 0;
    int medida = 0;
    int optAcc = 0;
    int contPar = 0;
    int benefHom = 0;
    int benefMuj = 0;
    int proyectoId = 0;
    int tipoAccion = 0;
    int conReg = 0;
    int plantillaNum = 0;
    boolean resultado = false;
    String encabezadoObra =new String();

    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("tipoAccion") != null && !request.getParameter("tipoAccion").equals("")) {
            tipoAccion = Integer.parseInt(request.getParameter("tipoAccion"));
        }
        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = request.getParameter("programaId");
        }
        if (request.getParameter("proyectoId") != null && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt(request.getParameter("proyectoId"));
        }
        if (request.getParameter("benefHom") != null && !request.getParameter("benefHom").equals("")) {
            benefHom = Integer.parseInt(request.getParameter("benefHom"));
        }
        if (request.getParameter("benefMuj") != null && !request.getParameter("benefMuj").equals("")) {
            benefMuj = Integer.parseInt(request.getParameter("benefMuj"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accionId"));
        }
        if (request.getParameter("localidad") != null && !request.getParameter("localidad").equals("")) {
            localidad = Integer.parseInt(request.getParameter("localidad"));
        }
        if (request.getParameter("municipio") != null && !request.getParameter("municipio").equals("")) {
            mpo = Integer.parseInt(request.getParameter("municipio"));
        }
        if (request.getParameter("grupoPob") != null && !request.getParameter("grupoPob").equals("")) {
            grupoPob = Integer.parseInt(request.getParameter("grupoPob"));
        }
        if (request.getParameter("depto") != null && !request.getParameter("depto").equals("")) {
            depto = request.getParameter("depto");
        }
        if (request.getParameter("medida") != null && !request.getParameter("medida").equals("")) {
            medida = Integer.parseInt(request.getParameter("medida"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("accionDesc") != null && !request.getParameter("accionDesc").equals("")) {
            accionDesc = request.getParameter("accionDesc");
        }
        if (request.getParameter("calculo") != null && !request.getParameter("calculo").equals("")) {
            tipoCalculo = request.getParameter("calculo");
        }
        if (request.getParameter("lineaPed") != null && !request.getParameter("lineaPed").equals("")) {
            lineaPed = request.getParameter("lineaPed");
        }
        if (request.getParameter("lineaSect") != null && !request.getParameter("lineaSect").equals("")) {
            lineaSectorial = request.getParameter("lineaSect");
        }
        if (request.getParameter("optAcc") != null && !request.getParameter("optAcc").equals("")) {
            optAcc = Integer.parseInt(request.getParameter("optAcc"));
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

        plantillaBean = new PlantillaBean(tipoDependencia);
        plantillaBean.setStrServer(request.getHeader("host"));
        plantillaBean.setStrUbicacion(getServletContext().getRealPath(""));
        plantillaBean.resultSQLConecta(tipoDependencia);

        cierreAccion = accionBean.isAccionCerrado(ramoId, year);
        if (!cierreAccion) {
            if (optAcc == 1) {
                accionId = accionBean.getMaxAccion(year, ramoId, metaId);
                accionList = accionBean.insertAccion(metaId, ramoId, year, accionId,
                        accionDesc, depto, medida, tipoCalculo, lineaPed, grupoPob,
                        mpo, localidad, lineaSectorial, benefMuj, benefHom, programaId, proyectoId, tipoAccion);
            } else if (optAcc == 2) {
                accion = accionBean.getResultGetAccionById(year, ramoId, metaId, accionId);
                accionList = accionBean.updateAccion(year, ramoId, metaId, accionId, accionDesc, depto, benefMuj, benefHom,
                        medida, tipoCalculo, lineaPed, grupoPob, mpo, localidad, lineaSectorial, programaId, proyectoId);
                if(!accionList.isEmpty()){
                    resultado = true;
                    plantBool = accionBean.getResultSQLvalidaDeptoPlantilla(year, ramoId, programaId, accion.getDeptoId(), metaId, accionId);
                    if(plantBool){
                        resultado = accionBean.getResultSQLUpdateDeptoPlantilla(year, ramoId, programaId, metaId, accionId, depto);
                    }
                    if(resultado)
                        accionBean.transaccionCommit();
                    else
                        accionBean.transaccionRollback();
                }
            } else if (optAcc == 3) {
                countReq = accionBean.countRequerimientosByAccion(year, ramoId, programaId, metaId, accionId);
                if (countReq > 0) {
                    strResultado = "borrar" + strResultado;
                } else {
                    resultado = accionBean.deleteEstimacionAccion(year, ramoId, metaId, accionId);
                    if (resultado) {
                        mensaje = accionBean.deleteAccion(year, ramoId, programaId, metaId, accionId);
                    }
                }
                if (mensaje.contains("ORA-02292") || mensaje.contains("ORA-02291")) {
                    strResultado = "integro" + strResultado;
                }
                accionList = accionBean.getAccionesByMeta(year, ramoId, metaId);
            } else if (optAcc == 4) {
                accionList = accionBean.getAccionesByMeta(year, ramoId, metaId);
            }
            
             for(Accion accionTemp : accionList){
                        if (accionTemp.getObra() != 0) {
                            encabezadoObra = "Obra";
                        }
               }

            strResultado += "<table id='tblAcciones' class='table col-md-12'>";
            strResultado += "<thead>";
            strResultado += "<tr>";
            strResultado += "<th width='78px'>Acci&oacute;n</th>";
            strResultado += "<th width='345px'>Descripci&oacute;n de la Acci&oacute;n</th>";
            strResultado += "<th width='60px'>"+encabezadoObra+"</th>";
            strResultado += "<th  width='94px'><center>" + "Plantilla" + "</center></th>";
            strResultado += "<th  width='105px'><center>" + "Resultado" + "</center></th>";
            strResultado += "</tr>";
            strResultado += "</thead>";
            strResultado += "<tbody>";
            for (Accion accionTemp : accionList) {
                contPar++;
                conReg++;
                if (contPar % 2 == 0) {
                    rowPar = "rowPar";
                } else {
                    rowPar = "";
                }
                strResultado += "<tr class='" + rowPar + "'>";
                strResultado += "<td width='78px'>" + accionTemp.getAccionId() + "</td>";
                strResultado += "<td width='345px'><div title='" + accionTemp.getAccion() + "' align='left'> " + accionTemp.getAccion() + "</div></td>";
                if (accionTemp.getObra() == 0) {
                    strResultado += "<td width='60px'></td>";
                } else {
                    strResultado += "<td width='60px'>" + accionTemp.getObra() + "</td>";
                }

                strResultado += "<td width='94px'>";
                if (plantillaBean.isPlantillaByRamoProgramaDeptoMetaAccionYear(accionTemp.getRamo(), accionTemp.getPrg(), accionTemp.getDeptoId(), accionTemp.getMeta(), accionTemp.getAccionId(), accionTemp.getYear())) {
                    plantillaNum = conReg;
                    strResultado += "<input onclick=\"checkPlantillaNueva('" + accionTemp.getRamo() + "','" + accionTemp.getPrg() + "','" + accionTemp.getDeptoId() + "'," + accionTemp.getMeta() + "," + accionTemp.getAccionId() + "," + accionTemp.getYear() + "," + conReg + "," + "1" + ")\" id='plantillaId" + conReg + "'  type='radio' name='FFR" + accionTemp.getDeptoId() + "'class='asigCheck' checked />";
                } else {
                    strResultado += "<input onclick=\"checkPlantillaNueva('" + accionTemp.getRamo() + "','" + accionTemp.getPrg() + "','" + accionTemp.getDeptoId() + "'," + accionTemp.getMeta() + "," + accionTemp.getAccionId() + "," + accionTemp.getYear() + "," + conReg + "," + "0" + ")\" id='plantillaId" + conReg + "'  type='radio' name='FFR" + accionTemp.getDeptoId() + "'class='asigCheck'  />";
                }
                strResultado += "</td>";
                strResultado += "<td width='105px'>";
                if(plantillaBean.getResultSQLIsTraeResultadoAccion(accionTemp.getYear(), accionTemp.getRamo().toString(), accionTemp.getMeta(), accionTemp.getAccionId())){
                strResultado += "<input type=\"radio\" id=\"AccionTraeResultado"+conReg+"\" name=\"AccionTraeResultado"+conReg+"\"  onclick=\"checkDefincionAccionTraeResultado('"+accionTemp.getRamo()+"','"+accionTemp.getMeta()+"',"+accionTemp.getAccionId()+","+accionTemp.getYear()+","+conReg+",1)\" checked/>";
                }else{
                strResultado += "<input type=\"radio\" id=\"AccionTraeResultado"+conReg+"\" name=\"AccionTraeResultado"+conReg+"\"  onclick=\"checkDefincionAccionTraeResultado('"+accionTemp.getRamo()+"','"+accionTemp.getMeta()+"',"+accionTemp.getAccionId()+","+accionTemp.getYear()+","+conReg+",0)\" />";
                   
                }
                
                strResultado += "</td>";
                strResultado += "</tr>";
            }
            strResultado += "</tbody>";
            strResultado += "</table>";
            strResultado += "<input type='hidden' id='plantillaNum' value='" + plantillaNum + "'/>";
            strResultado += "<script type='text/javascript'>";
            strResultado += "$('#tblAcciones tbody tr').click(function(){";
            strResultado += "$(this).addClass('selected').siblings().removeClass('selected');";
            strResultado += "  getRequerimientos();";
            strResultado += "});";
            strResultado += "</script>";
        } else {
            strResultado = "cerrado";
        }
    } catch (Exception ex) {
        accionBean.transaccionRollback();
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
        if (plantillaBean != null) {
            plantillaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

