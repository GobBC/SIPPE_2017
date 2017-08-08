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
    String strColorCal="";
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
        
        if (request.getParameter("tipoProyecto") != null
                && !request.getParameter("tipoProyecto").equals("")) {
            tipoProyecto = (String) request.getParameter("tipoProyecto");
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
            strResultado += "<form id='frmListadoMetas' method='post' action='capturaAccionAvancePOA.jsp' ><input id='inTxtDepto' value='" + dependencia.getDepartamento() + "' disabled style='width:350px;'/>";
            strResultado += "<input type='hidden' id='meta' name='meta' value='' >";
            strResultado += "<input type='hidden' id='selRamo'name='selRamo' value='"+ramoId+"' >";
            strResultado += "<input type='hidden' id='selPrograma' name='selPrograma' value='"+programaId+"' >";
            strResultado += "<input type='hidden' id='proyectoId' name='proyectoId' value='"+proyectoId+"' >";
            strResultado += "<input type='hidden' id='tipoProyecto' name='tipoProyecto' value='"+tipoProyecto+"' >";
            if (optMeta == 1) {
                metaList = metaBean.getResultGetMetasAvancePoa(ramoId, programaId, proyectoId,year,tipoProyecto);
            } 
            strResultado += "<table id='tblMetas' >";
            strResultado += "   <thead><tr>";
            strResultado += "       <th> No. </th>";
            strResultado += "       <th> Descripci&oacute;n de meta </th><th style='width:1px;'></th>";
            strResultado += "       </tr> </thead>";
            strResultado += "   <tbody id='tbody-metas'>";
            for (Meta metaTemp : metaList) {
                estiloPar++;
               if(estiloPar==1){calculo=metaTemp.getCalculo();}
                if (estiloPar % 2 == 0) {
                    strResultado += "<tr class='rowPar'> <td style='width: 15px;'>";
                    strColorCal="";
                    strColorCal="#CCC";
                } else {
                    strResultado += "<tr> <td style='width: 15px;'>";
                    strColorCal="";
                            
                    strColorCal +="white";
                }
                strResultado += metaTemp.getMetaId() + "</td>";
                strResultado += "<td><div title='" + metaTemp.getMeta() + "'>" + metaTemp.getMeta() + "</div></td><td style='font-size:0px; color:"+strColorCal+";'>"+metaTemp.getCalculo()+"</td>";
                strResultado += "</tr>";
            }
            strResultado += "   </tbody>";
            strResultado += "</table>";
            strResultado += "<script type=\"text/javascript\">";
            strResultado += "       $('#tblMetas tbody tr').click(function(){";
            strResultado += "       $(this).addClass('selected').siblings().removeClass('selected');";
            strResultado += "   });  ";
            strResultado += "</script>";
            
            if(estiloPar==0){strResultado +="<center><br/><br/><font color=red>No existen Metas!</font></center>";}
           if(metaList.size()>=1){
                strResultado +=" <center>";
                strResultado +=" <div id='control-metas' style='display: none;'>";
                strResultado +=" <input type='button' id='btnAvancetMeta' value='Avance Meta' class='btnControl' onclick='AvancePoaMeta()'/><input type='button' id='btnAvancetAccion' value='Avance Accion' onclick='AvancePoaAccion()' class='btnControl'  />";
                strResultado +="</div></center><input type='hidden' id='cal' name='cal' value='"+calculo+"'></form>";
              
                           }
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
            session.setAttribute("metaList", metaList);
            metaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>