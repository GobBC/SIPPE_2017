<%-- 
    Document   : ajaxGetMetas
    Created on : Apr 8, 2015, 10:14:03 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Meta> metaList = new ArrayList<Meta>();
    MetaBean metaBean = null;
    Meta meta;
    Dependencia dependencia;
    String strResultado = new String();
    String tipoDependencia = new String();
    String obra = new String();
    boolean resultado = false;
    int optMeta = 0;
    String tipoProyecto = new String();
    Proyecto proyecto;
    int estiloPar = 0;
    String ramoId = new String();
    int metaId = 0;
    int ponderado = 0;
    String programaId = new String();
    int proyectoId = 0;
    int medida = 0;
    int countAcc = 0;
    String calculo = new String();
    String clasificacion = new String();
    int compromiso = 0;
    String lineaPed = new String();
    String lineaSect = new String();
    int grupoPob = 0;
    int benefH = 0;
    int benefM = 0;
    String autorizacion = new String();
    String principal = new String();
    String objComp = new String();
    int year = 0;
    int criterio  =0;
    
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year") );
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
        if (request.getParameter("obra") != null
                && !request.getParameter("obra").equals("")) {
            obra = (String) request.getParameter("obra");
            if(obra == "-1"){
                obra = null;
            }
        }
        if (request.getParameter("tipoProyecto") != null
                && !request.getParameter("tipoProyecto").equals("")) {
            tipoProyecto = (String) request.getParameter("tipoProyecto");
        }        
        if (request.getParameter("criterio") != null
                && !request.getParameter("criterio").equals("")) {
            criterio = Integer.parseInt((String) request.getParameter("criterio"));
        }
        if (request.getParameter("optMeta") != null
                && !request.getParameter("optMeta").equals("")) {
            optMeta = Integer.parseInt((String) request.getParameter("optMeta"));
        }
        if (request.getParameter("programaId") != null
                && !request.getParameter("programaId").equals("")) {
            programaId = (String) request.getParameter("programaId");
        }
        if (request.getParameter("proyectoId") != null
                && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt((String) request.getParameter("proyectoId"));
        }
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }
        if (request.getParameter("medida") != null
                && !request.getParameter("medida").equals("")) {
            medida = Integer.parseInt((String) request.getParameter("medida"));
        }
        if (request.getParameter("objComp") != null
                && !request.getParameter("objComp").equals("")) {
            objComp = (String) request.getParameter("objComp");
        }
        if (request.getParameter("calculo") != null
                && !request.getParameter("calculo").equals("")) {
            calculo = (String) request.getParameter("calculo");
        }
        if (request.getParameter("clasificacion") != null
                && !request.getParameter("clasificacion").equals("")) {
            clasificacion = (String) request.getParameter("clasificacion");
        }
        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
        if (request.getParameter("compromiso") != null
                && !request.getParameter("compromiso").equals("")) {
            compromiso = Integer.parseInt((String) request.getParameter("compromiso"));
        }
        if (request.getParameter("grupoPob") != null
                && !request.getParameter("grupoPob").equals("")) {
            grupoPob = Integer.parseInt((String) request.getParameter("grupoPob"));
        }
        if (request.getParameter("benefH") != null
                && !request.getParameter("benefH").equals("")) {
            benefH = Integer.parseInt((String) request.getParameter("benefH"));
        }
        if (request.getParameter("benefM") != null
                && !request.getParameter("benefM").equals("")) {
            benefM = Integer.parseInt((String) request.getParameter("benefM"));
        }
        if (request.getParameter("ponderado") != null
                && !request.getParameter("ponderado").equals("")) {
            ponderado = Integer.parseInt((String) request.getParameter("ponderado"));
        }
        if (request.getParameter("principal") != null
                && !request.getParameter("principal").equals("")) {
            principal = (String) request.getParameter("principal");
        }
        if (request.getParameter("autorizacion") != null
                && !request.getParameter("autorizacion").equals("")) {
            autorizacion = (String) request.getParameter("autorizacion");
        }
        if (request.getParameter("lineaPed") != null
                && !request.getParameter("lineaPed").equals("")) {
            lineaPed = (String) request.getParameter("lineaPed");
        }
        if (request.getParameter("lineaSect") != null
                && !request.getParameter("lineaSect").equals("")) {
            lineaSect = (String) request.getParameter("lineaSect");
        }
        if (!ramoId.equals("-1")) {
            //dependencia = new Dependencia();
            proyecto = new Proyecto();
            proyecto = metaBean.getResultSQLProyectoById(ramoId, programaId, proyectoId, year,tipoProyecto);
            dependencia = metaBean.getResultSQLGetDependenciaById(ramoId, programaId, year, proyecto.getDepto_resp());
            strResultado = "Unidad responsable: ";
            if(dependencia != null){
                if(proyecto.getDepto_resp() == null)
                    dependencia.setDepartamento("");
            }else{
                dependencia = new Dependencia();
                dependencia.setDepartamento("");
                dependencia.setDeptoId("");
            }
            strResultado += "<input id='inTxtDepto' value='" + dependencia.getDepartamento() + "' disabled style='width:350px;'/>";
            if (optMeta == 1) {
                metaList = metaBean.getMetas(ramoId, programaId, proyectoId,year,tipoProyecto);
            } else {
                /*
                if (session.getAttribute("metaList") != null) {
                    metaList = (List<Meta>) session.getAttribute("metaList");
                }
                */
                metaList = metaBean.getMetas(ramoId, programaId, proyectoId,year,tipoProyecto);
                if (optMeta == 3) {
                    metaId = (metaBean.getMaxMetaId(year, ramoId));
                    if(dependencia == null){
                        dependencia = new Dependencia();
                        dependencia.setDeptoId("");
                    }
                    if(!obra.equals("A") && !obra.equals("I") ){
                        obra = "G";
                    }
                    if(lineaSect == null || lineaSect.equals("-1"))
                        lineaSect = new String();
                    resultado = metaBean.insertaMeta(metaId, ramoId,
                            metaBean.getProgramaString(Integer.parseInt(programaId)), year, medida,
                            proyectoId, calculo, tipoProyecto,
                            dependencia.getDeptoId(), ramoId + "-" + metaBean.getProgramaString(Integer.parseInt(programaId))
                            + "-" + tipoProyecto + metaId, lineaPed, compromiso, benefM, benefH, principal,
                            objComp, grupoPob, ponderado, lineaSect, clasificacion, autorizacion, criterio, obra);
                    if (resultado) {
                        /*
                        meta = new Meta();
                        meta.setMetaId(metaId);
                        meta.setRamo(ramoId);
                        meta.setPrograma(programaId);
                        meta.setTipoProyecto(proyecto.getTipoProyecto());
                        meta.setMeta(objComp);
                        metaList.add(meta);
                                */
                       
                        
                        metaList = metaBean.getMetas(ramoId, programaId, proyectoId,year,tipoProyecto);
                    }
                } else {
                    if (optMeta == 4) {
                        if(!obra.equals("A") && !obra.equals("I") ){
                            obra = "G";
                        }
                        if(lineaSect == null || lineaSect.equals("-1"))
                            lineaSect = new String();
                        resultado = metaBean.updateMeta(year, ramoId,
                                metaBean.getProgramaString(Integer.parseInt(programaId)), proyectoId, metaId,
                                medida, calculo, lineaPed, compromiso, benefH, benefM, principal, objComp,
                                grupoPob, ponderado, lineaSect, clasificacion, autorizacion, criterio, obra);
                        metaList = metaBean.getMetas(ramoId, programaId, proyectoId,year,tipoProyecto);
                    }else{                        
                        countAcc = metaBean.getCountAccionByMeta(year, ramoId, programaId, metaId);
                        if(countAcc > 0){
                            strResultado = "borrar " + strResultado;
                        }else{
                            resultado = metaBean.deleteEstimacionMeta(year, ramoId, metaId);
                            if(resultado){
                                meta = new Meta();
                                for (Meta metaTemp : metaList) {
                                    if (metaTemp.getMetaId() == metaId) {
                                        meta = metaTemp;
                                    }
                                }
                                //metaList.remove(meta);
                                resultado = metaBean.deleteMeta(year, ramoId,programaId, proyectoId, metaId, tipoProyecto);
                                metaList = metaBean.getMetas(ramoId, programaId, proyectoId,year,tipoProyecto);
                            }
                        }
                    }
                }
            }
            strResultado += "<table id='tblMetas' width='645px'>";
            strResultado += "   <thead> <tr>";
            strResultado += "       <th width='15px'> No. </th>";
            //strResultado += "       <th> Clave </th>";
            strResultado += "       <th width='420px'> Descripci&oacute;n de meta </th>";
              strResultado += "     <th width='65px'><center>Resultado</center></th>";
            strResultado += "       </tr> </thead>";
            strResultado += "   <tbody id='tbody-metas'>";
            for (Meta metaTemp : metaList) {
                estiloPar++;
                //claveMeta = metaTemp.getRamo() + "-" + metaTemp.getPrograma() + "-" + metaTemp.getTipoProyecto() + metaTemp.getMetaId();
                if (estiloPar % 2 == 0) {
                    strResultado += "<tr class='rowPar'> <td style='width: 15px;'>";
                } else {
                    strResultado += "<tr> <td style='width: 15px;'>";
                }
                strResultado += metaTemp.getMetaId() + "</td>";
                //strResultado += "<td style='width: 50px;'>" + claveMeta + "</td>";
                strResultado += "<td><div title='" + metaTemp.getMeta() + "'>" + metaTemp.getMeta() + "</div></td>";
                 strResultado += "<td width='65px'><center>";
                 if(metaBean.getResultSQLIsTraeResultadoMeta(year, metaTemp.getRamo(),metaTemp.getMetaId())){
                 strResultado +=" <input type='radio' id='MetaTraeResultado"+estiloPar+"' name='MetaTraeResultado"+estiloPar+"' onclick=\"checkDefincionMetaTraeResultado("+estiloPar+",'"+metaTemp.getRamo()+"',"+metaTemp.getMetaId()+","+year+",1)\" checked>";
                 }else{
                 strResultado +=" <input type='radio' id='MetaTraeResultado"+estiloPar+"' name='MetaTraeResultado"+estiloPar+"' onclick=\"checkDefincionMetaTraeResultado("+estiloPar+",'"+metaTemp.getRamo()+"',"+metaTemp.getMetaId()+","+year+",0)\">";
                 }
                 strResultado +=" </center></td>";
                strResultado += "</tr>";
            }
            strResultado += "   </tbody>";
            strResultado += "</table>";
            strResultado +=" <input type='hidden' id='MetaNum' value='"+estiloPar+"'>";
            strResultado += "<script type=\"text/javascript\">";
            strResultado += "       $('#tblMetas tbody tr').click(function(){";
            strResultado += "       $(this).addClass('selected').siblings().removeClass('selected');";
            strResultado += "   });";
            strResultado += "</script>";
            
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
            //session.setAttribute("metaList", metaList);
            metaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>