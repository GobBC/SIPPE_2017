<%-- 
    Document   : mirEdit
    Created on : Jun 27, 2017, 4:57:08 PM
    Author     : ealonso
--%>

<%@page import="gob.gbc.Framework.KendoDropdown"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="gob.gbc.entidades.MIR"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>

<%
    int accion = 1;
    request.setCharacterEncoding("UTF-8");
    String entidad = request.getParameter("entidad");
    MIR data = new MIR();
    KendoDropdown drop = null;//Importar libreria, declarar
    String readContextPath = null;
    String valor = "";
    if(entidad != null && entidad != ""){
        accion = 2;
        data = new Gson().fromJson(entidad, MIR.class);
    }    
%>

<div class="modal-message" style="padding:10px;line-height:1.35em;overflow:hidden">
    <form id="popup-edit" style="width:90%">
        <input id="accion" hidden value="<%= accion %>">
        <input id="programa-anterior" hidden value="<%=data.getPrg() %>">
        <br/> 
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <%  readContextPath = request.getContextPath()+"/RamoList";
                if(data.getRamo() != null){
                    valor = data.getRamo();
                } else {
                    valor = "";
                } 
                drop = new KendoDropdown(); //inicializar
                drop.setLabelFor("cmbRamoEdit"); //Personalizar Kendo DropDownList
                drop.setLabel("Ramo: ");
                drop.setName("ramo");
                drop.setDataTextField("ramoDescr");
                drop.setDataValueField("ramo");
                drop.setChange("onChangeRamoEdit");
                drop.setReadUrl(readContextPath);
                drop.setParameterMap("parameterMapRamoEdit");
                drop.setAutoBind(true);
                drop.setValue(valor); 
    %>                            
            <%@include file="../Framework/GobBC/Controles/kendoDropDown.jsp" %>
        </div>
        <br/>           
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <%  readContextPath = request.getContextPath()+"/ProgramaList";
                if(data.getPrg() != null){
                    valor = data.getPrg();
                } else {
                    valor = "";
                }  
                drop = new KendoDropdown(); //inicializar
                drop.setLabelFor("cmbProgramaEdit"); //Personalizar Kendo DropDownList
                drop.setLabel("Programa: ");
                drop.setName("prg");
                drop.setDataTextField("programaDesc");
                drop.setDataValueField("programaId");
                drop.setChange("onChangeProgramaEdit");
                drop.setReadUrl(readContextPath);
                drop.setParameterMap("parameterMapProgramaEdit");
                drop.setAutoBind(true);
                drop.setValue(valor);    
            %>                       
            <%@include file="../Framework/GobBC/Controles/kendoDropDown.jsp" %>
        </div>
        <br/> 
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-right">
            <button type="submit" class="save btn btn-primary btn-menu-pagina"><span class="fa fa-check"></span>&nbsp;Guardar Cambios</button>
            <button type="button" class="btn btn-warning btn-menu-pagina" onclick="cancel()"><span class="fa fa-times"></span>&nbsp;Cerrar</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        var drop1 = $("#ramo").data("kendoDropDownList");       
        var drop2 = $("#prg").data("kendoDropDownList");
        if ($('[name="programaComboBox"]').val()!= "") {
            drop2.value($('[name="programaComboBox"]').val());
        }
        if ($('[name="programaComboBox"]').val()!= "") {     
            drop1.value($('[name="ramoComboBox"]').val());
        }
        if ($('[name="prg"]').val()!= "") {
            refreshDrop(drop1);
        }
        if ($('[name="ramoComboBox"]').val()!= "") {     
            refreshDrop(drop2);
        }
        if($('#accion').val() == "2") {
             drop1.readonly()
        }
    });
    
    $(function () {
        cargarEventos();
    });
            
    function cargarEventos(){
        var forma = $('#popup-edit');

        forma.off('submit').on('submit', function (e) {
            e.preventDefault();
            var form = $(this);
            var data = form.serializeObject();

            if(data.ramo !== "" && data.prg !== "" ){
                $.ajax({
                    type: 'POST',
                    url: 'librerias/ajax/MIR/ajaxCapturaMIR.jsp',
                    datatype: 'json',
                    data: {accion: $('#accion').val(), entidad: kendo.stringify(data), programaAnt: $("input#programa-anterior").val()},
                    success: function(response) {
                        form.closest("[data-role=window]").data("kendoWindow").close();

                        $.Framework.showNotification({
                            text: response.mensaje,
                            type: response.exito ? 'success' : 'warning'
                        });

                        var grid = $('.k-grid:visible').data('kendoGrid');
                        refreshGrid(grid);
                    }
                });
            }

        });

        forma.find('button.close').off('click').on('click', function(e){
            e.preventDefault();
            $(this).closest("[data-role=window]").data("kendoWindow").close();
        });

        forma.kendoValidator({
            rules: {
                customRule2: function(input) {
                    //only 'Tom' will be valid value for the username input
                    if (input.is("[name=ramo]")) {
                        return input.val() !== "";
                    }
                    return true;
                },
                customRule3: function(input) {
                    //only 'Tom' will be valid value for the username input
                    if (input.is("[name=prg]")) {
                        return input.val() !== "";
                    }
                    return true;
                }
            },
            messages: {
                customRule2: "El campo ramo es requerido.",
                customRule3: "El campo programa es requerido."
            }
        });
    }
</script>