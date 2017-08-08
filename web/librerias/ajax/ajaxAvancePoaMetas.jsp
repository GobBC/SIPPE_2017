<%-- 
    Document   : ajaxAvancePoaMetas.jsp
    Created on : Jan 22, 2015, 10:14:03 AM
    Author     : mavalle
--%>

<%@page import="gob.gbc.util.EnumProcesoEspecial"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.AvancePoaMetaObservaciones"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.AvancePoaMeta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Meta> metaList = new ArrayList<Meta>();
    Meta meta;
    MetaBean metaBean = null;
    List<AvancePoaMetaObservaciones> metaObservList = new ArrayList<AvancePoaMetaObservaciones>(); 
    AvancePoaMetaObservaciones metaObserv;
    
    List<AvancePoaMeta> avancePoaMetaList=new ArrayList<AvancePoaMeta>();
    AvancePoaMeta avancePoaMeta;
    List<Evaluacion> evaluacionList = new ArrayList<Evaluacion>();
    Evaluacion evaluacion;
    Dependencia dependencia;
    String strResultado = new String();
    String tipoDependencia = new String();
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    boolean accesoPrg = false;
    int estiloPar = 0;
    String ramoId = new String();
    String strExplode[]=null;
    int metaId = 0;
    String cadValidaPeriodo = new String();
    String validaPeriodo[] = null;
    int year = 0;
    int rol=0;
    int periodoParametrosPrg = 0;
    int evaluacionEstimacion = 0;
    int maxPeriodoEstimacion = 0;
    String esParaestatal="";
    String deshabilitado="";
    int intEvaluacion=0;
    int contEstimacion=0;
    Double totalRealizado =0.0;
    Double totalProgramado =0.0;
    String evaluacionDescr="";
    String deshabilitaObserv="";
    String deshabilitaObservAnual="";
    String calculo="";
    String metaDescr="";
    String strMesdescr="";
    String estiloActivo="";
    String metaObservacion="";
    String metaObservacionAnual="";
    String UnidadMedidaMeta="";
    String observatmp="";
    String usuario="";
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = Integer.parseInt((String) session.getAttribute("strRol") );
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year") );
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
        
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }else{
        metaId=-1;
        }
        
        if (request.getParameter("calculo") != null
                && !request.getParameter("calculo").equals("")) {
            calculo = (String) request.getParameter("calculo");
        }
        
        if (request.getParameter("metaDescr") != null
                && !request.getParameter("metaDescr").equals("")) {
            metaDescr = (String) request.getParameter("metaDescr");
        }
        
        
        
        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
        
        if (!ramoId.equals("-1")) {
          if(!(metaId == -1)){
            //dependencia = new Dependencia();
           /* proyecto = new Proyecto();
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
            }*/
           // strResultado += "<input id='inTxtDepto' value='" + dependencia.getDepartamento() + "' disabled style='width:350px;'/>";
            //Se valida que exista un periodo definido en la tabla de dgi.ramos y de igual forma que el usuario tenga acceso  al proceso.
            cadValidaPeriodo = metaBean.getResultSQLgetValidaPeriodoDefinido(year,ramoId);
            validaPeriodo=cadValidaPeriodo.split(",");
            if(Integer.parseInt(validaPeriodo[0])==0){
             strResultado +="<center><table><tr><td><font color='red'>El ramo no tiene un periodo definido<br><center><br>       <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('avancePoaMeta');\" /> </center></td></tr></table></center>";
            }else{
              accesoPrg=true;
            if(validaPeriodo[1].equals("S")){
               accesoPrg=metaBean.getResultSQLhasProcesoEspecual(usuario, EnumProcesoEspecial.NORMATIVO_AVANCE.getProceso());
            }
            if(accesoPrg==false){
               strResultado +="<center><table><tr><td><font color='red'>El ramo esta cerrado para la captura del avance<br><br><center>       <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('avancePoaMeta');\" /> </center></td></tr></table></center>";
            }else{
                  contEstimacion=metaBean.getContEstimacionMetaAvancePoa(ramoId, metaId, year);
                  if(contEstimacion>0){  ///validar que exista estimacion capturada para presentar el programdo
                  
                   if(Integer.parseInt(validaPeriodo[0])<12){
                      deshabilitaObservAnual=" visibility:hidden; ";
                      deshabilitaObserv="";
                   }else{
                      deshabilitaObservAnual=" visibility:visible; ";
                      deshabilitaObserv=" disabled='true' style='background-color: #CCC;'  ";
                   }
            /*presentar la captura del avance meta*/
                  
                  //obtengo el periodo de parametros_prg
                  periodoParametrosPrg=metaBean.getPeriodoParametroPrgAvancePoa();
                  //obtengo la evaluacion de la estimacion
                  evaluacionEstimacion=metaBean.getSQLEvaluacionEstimacionAvancePoa(periodoParametrosPrg);
                  //obtengo el maximo periodo en base a la evaluacion
                  maxPeriodoEstimacion=metaBean.getMaxPeriodoEstimacionAvancePoa(evaluacionEstimacion);
                  //obtengo la evaluacion de dgi.evaluacion
                  evaluacionList=metaBean.getEvaluacionAvancePoa(year);
                  for (Evaluacion evaluacionTemp : evaluacionList) {
                      intEvaluacion=evaluacionTemp.getNumMeses();
                      evaluacionDescr=evaluacionTemp.getTipoEvaluacion();
                  }
                  
                  //determino si es una paraestatal
                  //esParaestatal=metaBean.getIsParaestatalAvancePoa();
                  //if(esParaestatal.equals("N")){
                  //  if(periodoParametrosPrg==maxPeriodoEstimacion){
                    //habilitar observaciones
                    //   deshabilitaObservAnual="";
                     // deshabilitaObserv="";
                      
                      
                   // }else{
                     //deshabilitar observaciones 
                     //  deshabilitaObservAnual=" disabled='true' style='background-color: #CCC;' ";
                      // deshabilitaObserv=" disabled='true' style='background-color: #CCC;' ";
                       
                   // }
                  
                 // }
                  
            avancePoaMetaList=metaBean.getProgramacionAvancePoa(ramoId,metaId,year);
            UnidadMedidaMeta=metaBean.getResultSQLGetUnidadMedidaMetaAvancePoa(year, ramoId, metaId);//unidad de medida y verifa si trae Resultado
            strExplode=UnidadMedidaMeta.split(",");//unidad medida y nuevo campo resultado
            strResultado += "<table border='0' style='vertical-align:text-top; text-align:left; width:700px;'><tr width='100%'><td width='60px;' style='vertical-align:text-top;'><b>Meta:</b></td><td width='3px;' style='vertical-align:text-top;'><b>"+metaId+"-</b></td><td style='text-align:left;'><b>"+metaDescr.trim()+"</b></td></tr></table><br/><b>Unidad Medida: "+strExplode[1]+"<br/><br>Avande de Meta</b><br><br> <center><table id='tblAvancePoaMeta' >";
            strResultado += "   <thead><tr>";
            strResultado += "       <th> Mes </th>";
            strResultado += "       <th> Programado </th>";
            strResultado += "       <th> Realizado </th>";
             strResultado += "      <th> Observaciones </th>";
             strResultado += "       <th>  </th>";
             strResultado += "       <th>  </th>";
            strResultado += "       </tr> </thead>";
            strResultado += "   <tbody id='tbody-avancemetas'>";
            estiloPar=0;
             for (AvancePoaMeta avancePoaMetaTemp : avancePoaMetaList) {
                estiloPar++;
               if(Integer.parseInt(validaPeriodo[0])==estiloPar){ 
                   //periodo activo
                avancePoaMetaTemp.setActivo("S");
                estiloActivo="";
                //obtengo la observacion del periodo activo
                metaObservList=metaBean.getObtieneObservacionMetaAvancePoa(ramoId, metaId, periodoParametrosPrg, year);
                 for(AvancePoaMetaObservaciones metaObservTemp : metaObservList) {
                     metaObservacion=metaObservTemp.getObserva();
                     metaObservacionAnual=metaObservTemp.getObserva_anual();
                 }
                
               }else{
                avancePoaMetaTemp.setActivo("N");
                estiloActivo=" disabled='true' style='background-color: #CCC;' ";
                }
               /* if (estiloPar % 2 == 0) {
                    strResultado += "<tr class='rowPar'> <td style='width: 15px;'>";
                } else {
                    strResultado += "<tr> <td style='width: 15px;'>";
                }*/
                strMesdescr="";
                if(avancePoaMetaTemp.getEstimacion_periodo()==1)strMesdescr="ENERO";
                if(avancePoaMetaTemp.getEstimacion_periodo()==2)strMesdescr="FEBRERO";
                if(avancePoaMetaTemp.getEstimacion_periodo()==3)strMesdescr="MARZO";
                if(avancePoaMetaTemp.getEstimacion_periodo()==4)strMesdescr="ABRIL";
                if(avancePoaMetaTemp.getEstimacion_periodo()==5)strMesdescr="MAYO";
                if(avancePoaMetaTemp.getEstimacion_periodo()==6)strMesdescr="JUNIO";
                if(avancePoaMetaTemp.getEstimacion_periodo()==7)strMesdescr="JULIO";
                if(avancePoaMetaTemp.getEstimacion_periodo()==8)strMesdescr="AGOSTO";
                if(avancePoaMetaTemp.getEstimacion_periodo()==9)strMesdescr="SEPTIEMBRE";
                if(avancePoaMetaTemp.getEstimacion_periodo()==10)strMesdescr="OCTUBRE";
                if(avancePoaMetaTemp.getEstimacion_periodo()==11)strMesdescr="NOVIEMBRE";
                if(avancePoaMetaTemp.getEstimacion_periodo()==12)strMesdescr="DICIEMBRE";
                
                if(avancePoaMetaTemp.getActivo().equals("S")){deshabilitado="";}else{deshabilitado=" disabled='true' ";}
                strResultado +="<tr><td><input disabled='true' style='background-color: #CCC; text-align:left; width:120px;'  type='text' name='periodo"+estiloPar+"' id='periodo"+estiloPar+"' value='"+strMesdescr+"'></td>";
                strResultado += "<td><input disabled='true' style='background-color: #CCC;' type='text' name='programado"+estiloPar+"' id='programado"+estiloPar+"' value='"+numberF.format(Double.parseDouble(avancePoaMetaTemp.getEstimacion_valor()))+"'></td>";
                strResultado += "<td><input "+estiloActivo+" type='text' name='realizado"+estiloPar+"' id='realizado"+estiloPar+"' value='"+numberF.format(Double.parseDouble(avancePoaMetaTemp.getAvance_valor()))+"' onBlur='validarFlotanteAvancePoaMetas(this.value); agregarFormato(\"realizado" + estiloPar + "\"); validaMascara(\"realizado" + estiloPar + "\"); ' onkeyPress='return NumCheck(event,this);' ></td>";
                observatmp="";
                if(avancePoaMetaTemp.getAvance_observa()==null){observatmp="";}else{observatmp=avancePoaMetaTemp.getAvance_observa();}
                if(avancePoaMetaTemp.getAvance_observa_anual()==null){metaObservacionAnual="";}else{metaObservacionAnual=avancePoaMetaTemp.getAvance_observa_anual();}
                if(estiloPar%3!=0){
                estiloActivo =" disabled='true' style='background-color: #CCC;' ";
                }
                strResultado +="<td><input "+estiloActivo+" type='text' style='text-transform:uppercase' id='observacion"+estiloPar+"' name='observacion"+estiloPar+"' value='"+observatmp+"'></td>";
                if(estiloPar%3==0){
                strResultado +="<td><input type='button' value='...' onclick='MuestraObsrvacionAvancePoa(\"observacion"+estiloPar+"\",\""+estiloPar+"\",\"S\",\""+Integer.parseInt(validaPeriodo[0])+"\")'>";}
                strResultado +="<input type='hidden' value='"+avancePoaMetaTemp.getActivo()+"' name='activo"+estiloPar+"' id='activo"+estiloPar+"'></td>";
                               
                               

                strResultado += "</tr>";
            

                  if(calculo.equals("AC")){
                   totalProgramado = totalProgramado + Double.parseDouble(avancePoaMetaTemp.getEstimacion_valor());
                   totalRealizado = totalRealizado + Double.parseDouble(avancePoaMetaTemp.getAvance_valor());
                  }else if(calculo.equals("MI")){
                      if(estiloPar==1){
                         totalProgramado= Double.parseDouble(avancePoaMetaTemp.getEstimacion_valor());
                         totalRealizado = Double.parseDouble(avancePoaMetaTemp.getAvance_valor());
                      }else{
                         if(Double.parseDouble(avancePoaMetaTemp.getEstimacion_valor())<totalProgramado){
                             totalProgramado=Double.parseDouble(avancePoaMetaTemp.getEstimacion_valor());
                          }
                         if(Float.parseFloat(avancePoaMetaTemp.getAvance_valor())<totalRealizado){
                             totalRealizado=Double.parseDouble(avancePoaMetaTemp.getAvance_valor());
                          }
                     }
                      
                  }else if(calculo.equals("MA")){
                     if(estiloPar==1){
                         totalProgramado= Double.parseDouble(avancePoaMetaTemp.getEstimacion_valor());
                         totalRealizado = Double.parseDouble(avancePoaMetaTemp.getAvance_valor());
                      }else{
                         if(Float.parseFloat(avancePoaMetaTemp.getEstimacion_valor())>totalProgramado){
                             totalProgramado=Double.parseDouble(avancePoaMetaTemp.getEstimacion_valor());
                          }
                         if(Float.parseFloat(avancePoaMetaTemp.getAvance_valor())>totalRealizado){
                             totalRealizado=Double.parseDouble(avancePoaMetaTemp.getAvance_valor());
                          }
                     }
                  } 
                  
                }      
            strResultado +="<tr><td>("+calculo+") Total:</td><td><input disabled='true' style='background-color: #CCC;' type='text' id='totalProgramado' name='totalProgramado' value='"+numberF.format(totalProgramado)+"'  onBlur='agregarFormato(\"totalProgramado\"); validaMascara(\"totalProgramado\");'></td><td><input disabled='true' style='background-color: #CCC;' type='text' maxlength='20' id='totalRealizado' name='totalRealizado' value='"+numberF.format(totalRealizado)+"' onkeyup='agregarFormato(\"totalRealizado\"); validaMascara(\"totalRealizado\");'></td></tr>";           
            strResultado += "   </tbody>";      
            strResultado +="<table></center><tr><td><br/><br/><b><div style='"+deshabilitaObservAnual+"'>Observacion cierre anual:</div></b><br/><input  style='width:700px; height:60px; text-transform:uppercase; "+deshabilitaObservAnual+" ' type='text' id='observacionAnual' name='observacionAnual' value='"+metaObservacionAnual+"' />";
            strResultado +="</td><td style='text-aling:center;'>  </td></tr></table><br><br>";
            strResultado += " <center><input type='button' value='Guardar' onclick=\"actualizaAvancePoaMeta();\" />&nbsp;&nbsp;<input type='button' value='Cerrar' onclick=\"fadeOutPopUp('avancePoaMeta');\" /> </center>";
            strResultado +="<input type='hidden' id='ramoId' name='ramoId' value='"+ramoId+"'>";
            strResultado +="<input type='hidden' id='met' name='met' value='"+String.valueOf(metaId)+"'>";
            strResultado +="<input type='hidden' id='anio' name='anio' value='"+year+"'>";
            strResultado +="<input type='hidden' id='ObligadoObservacion' name='ObligadoObservacion' value='"+strExplode[0]+"'>";
            strResultado +="<input type='hidden' id='periodo' name='periodo' value='"+String.valueOf(Integer.parseInt(validaPeriodo[0]))/*periodoParametrosPrg*/+"'>";
            strResultado +="<input type='hidden' id='contador' name='contador' value='"+estiloPar+"'>";
            strResultado +="<input type='hidden' id='calc' name='calc' value='"+calculo+"'>";
            
          
            
                       }else{
                       strResultado +="<center><table><tr><td><font color='red'>No existe Estimacion capturada para esta Meta en dgi.estimacion <br><br><center>       <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('avancePoaMeta');\" /> </center></td></tr></table></center>";
                       }//validar estimacion

           
            
            }
            
            }
            
            
            
                    }else{
                    strResultado +="<table><tr><td><font color='red'>debe seleccionar una  Meta</td></tr></table>";
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


