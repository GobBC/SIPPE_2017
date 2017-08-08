<%-- 
    Document   : capturaAccionAvancePOA
    Created on : Jan 29, 2016, 10:43:10 AM
    Author     : mavalle
--%>

<%@page import="gob.gbc.util.EnumProcesoEspecial"%>
<%@page import="gob.gbc.entidades.AccionesAvancePoa"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.entidades.AvancePoaAcciones"%>
<%@page import="gob.gbc.entidades.AvancePoaMeta"%>
<%@page import="gob.gbc.entidades.AvancePoaMetaObservaciones"%>
<%@page import="gob.gbc.entidades.TipoGasto"%>
<%@page import="gob.gbc.entidades.FuenteFinanciamiento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captura avance acciones</title>

    </head>
    <%
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        int year = 0;
        String ramoId = new String();
        String programaId = new String();
        String tipoDependencia = new String();
        int proyectoId = -1;
        int metaId = 0;
        int contPar = 0;
        boolean accesoPrg=true;
        String obra = new String();
        String arrayProy[] = new String[2];
        String tipoProyecto = new String();
        String rowPar = new String();
        String usuario = new String();
        String obraAccion = new String();
        boolean bandEncabezadoObra = false;
        String encabezadoObra = new String();
        Ramo objRamo = new Ramo();
        Programa objPrograma = new Programa();
        Proyecto objProyecto = new Proyecto();
        Meta objMeta = new Meta();
        List<Accion> accionList = new ArrayList<Accion>();
        List<FuenteFinanciamiento> fuenteFinList = new ArrayList<FuenteFinanciamiento>();
        List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
        String cadValidaPeriodo = new String();
        String validaPeriodo[] = null;
        String calculo="";
        String validaCierreAccion="";
        String metaDescr="";
        int selEstilo=0;

        int rol=0;
            
        List<AvancePoaMetaObservaciones> metaObservList = new ArrayList<AvancePoaMetaObservaciones>(); 
        AvancePoaMetaObservaciones metaObserv;

        List<AvancePoaMeta> avancePoaMetaList=new ArrayList<AvancePoaMeta>();
        AvancePoaMeta avancePoaMeta;

        List<AvancePoaAcciones> avancePoaAccionesList=new ArrayList<AvancePoaAcciones>();
        AvancePoaAcciones avancePoaAcciones;


        List<Evaluacion> evaluacionList = new ArrayList<Evaluacion>();
        Evaluacion evaluacion;
        List<AccionesAvancePoa> accionesAvanceList = new ArrayList<AccionesAvancePoa>();
        AccionesAvancePoa accionesAvancePoa;

        
        
        
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
    
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = Integer.parseInt((String) session.getAttribute("strRol") );
        }
    
        if (request.getParameter("selRamo") != null
                && !request.getParameter("selRamo").equals("")) {
            ramoId = (String) request.getParameter("selRamo");
        }
        if (request.getParameter("selPrograma") != null
                && !request.getParameter("selPrograma").equals("")) {
            programaId = (String) request.getParameter("selPrograma");
        }

        if (request.getParameter("proyectoId") != null
                && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt((String) request.getParameter("proyectoId"));
        }
        
        if (request.getParameter("tipoProyecto") != null
                && !request.getParameter("tipoProyecto").equals("")) {
            tipoProyecto = (String) request.getParameter("tipoProyecto");
        }
        
        if (ramoId.isEmpty() || programaId.isEmpty() || proyectoId == -1) {
            response.sendRedirect("capturaAccionAvancePOA.jsp");
            //response.setHeader("Location", "/capturaDefinicionMeta.jsp");
            return;
        }
        if (request.getParameter("meta") != null
                && !request.getParameter("meta").equals("")) {
            metaId = Integer.parseInt(request.getParameter("meta"));
        }
        AccionBean accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(((String) request.getHeader("Host")));
        accionBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        accionBean.resultSQLConecta(tipoDependencia);

        objRamo = accionBean.getRamoById(ramoId, year, usuario);
        objPrograma = accionBean.getProgramaById(ramoId, programaId, year, usuario);
        objProyecto = accionBean.getProyectoById(ramoId, programaId, proyectoId, year, tipoProyecto);
        objMeta = accionBean.getMetaById(ramoId, programaId, proyectoId, metaId, year, tipoProyecto);
        accionList = accionBean.getAccionesByMetaAvance(year, ramoId, metaId);
       /* if (objRamo != null) {
            fuenteFinList = accionBean.getFuenteFinanciamiento(year);
            tipoGastoList = accionBean.getTipoGasto(year);
            obra = accionBean.getObraByMeta(year, ramoId, metaId);
            if (obra == null || obra.equals(" ")) {
                obra = "G";
            }
            accionBean.resultSQLDesconecta();*/
    %>
    <jsp:include page="template/encabezado.jsp" />
    
    <div Id="TitProcess"><label>Captura de Avance Acciones<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-atras" onclick="redirectAvancePoa()">
                <br/> <small> Atr&aacute;s </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    
       
    
    <%
        validaCierreAccion=accionBean.getValidaCierreAccionAvancePoa(ramoId,year);                
             if(validaCierreAccion.equals("S")){
                accesoPrg=accionBean.getResultSQLhasProcesoEspecual(usuario, EnumProcesoEspecial.NORMATIVO_AVANCE.getProceso());
              }
            if(accesoPrg==false){%>
               <center><table width="100%"><tr><td><font color='red'>El ramo esta cerrado para el avance accion<br><br></td></tr></table></center>
             <% }else{ 
              ///si no esta cerrado

            %> 
               <form action="capturaAvancePOA.jsp" id="frmAvanceAcciones" method="post">
                  <div class="center-div center-req" >
             <table id='infoSeccion'>
                <tr>
                    <td><label> Ramo: </label></td>
                    <td><%= objRamo.getRamo() + "-" + objRamo.getRamoDescr()%></td>
                </tr>
                <tr>
                    <td><label> Programa: </label></td>
                    <td><%= objPrograma.getProgramaId() + "-" + objPrograma.getProgramaDesc()%></td>
                </tr>
                <tr>
                    <td><label> Proy./Act.: </label></td>
                    <td><%=  objProyecto.getTipoProyecto() + objProyecto.getProyectoId() + "-" + objProyecto.getProyecto()%></td>
                </tr>
                <tr>
                    <td><label> Meta: </label></td>
                    <td><%= objMeta.getMetaId() + "-" + objMeta.getMeta()%></td>
                </tr>
            </table>
                </div>
            
               <input type='hidden' id='meta' name='meta' value='<%=metaId%>' >
               <input type='hidden' id='ramo' name='ramo' value='<%=ramoId%>' >
               <input type='hidden' id='programa' name='programa' value='<%=programaId%>' >
              <input type='hidden' id='proyecto' name='proyecto' value='<%=proyectoId%>' >
              <input type='hidden' id='tipoProy' name='tipoProy' value='<%=tipoProyecto%>' >
             <br/>
             
               <table id='tblAvancePoaAccion' ><thead><th>Accion&nbsp;&nbsp;</th><th>Descripci&oacute;n</th><th>Unidad Medida&nbsp;&nbsp;</th><th>Unidad Ejecutora&nbsp;&nbsp;</th><th></th></thead> <tbody id='tbody-acciones'>
                <%
                 accionesAvanceList=accionBean.getObtieneAccionesAvancePoa(metaId,ramoId,year);
                 for(AccionesAvancePoa accionesAvanceTemp : accionesAvanceList){
                     selEstilo++;
                     
                if (selEstilo % 2 == 0) {
                    %>
                   <tr  class='rowPar' style="font-size:9px;"> 
                <% } else { %>
                   <tr style=' font-size:9px;'> 
               <% }
                   %>  
                   
                   <td><%=accionesAvanceTemp.getACCION()%></td><td><%=accionesAvanceTemp.getDESCR()%></td><td><%=accionesAvanceTemp.getUNIDAD_MEDIDA()%></td><td><%=accionesAvanceTemp.getUE_DESCR()%></script></td><td style="font-size: 0px;"><%=accionesAvanceTemp.getCALCULO()%></td></tr>  
                   
                   <%
                 }
                 
                 %>
                </tbody></table>
                <%
                
               if(selEstilo==0){
               %>
               <center><br/><br/><p><font color="red">No existen acciones para esta Meta!</font></p></center>
               <%
               }
                %>
                 <center>      
                
            
            <script type="text/javascript">
             $('#tblAvancePoaAccion tbody tr').click(function(){
             $(this).addClass('selected').siblings().removeClass('selected');
             });
            </script>
           <br/><br/>
           
           <input type='button' id='btnAvancetAccion' value='Captura Avance Accion' onclick='avancePoaAccionSelAccion()' class='btnControl'  />     
  
           
           <div id="avanceAccionAvancePOA" style="display: none;">
           </div>            
           
           <div id="Observa" style="display:none;">
           </div> 
           
           

          
    
    
    <div id="mensaje"></div></form>
               <% }%>
    <jsp:include page="template/piePagina.jsp" />
</html>
