<%-- 
    Document   : mirActividadEdit
    Created on : Jul 7, 2017, 1:29:00 PM
    Author     : ealonso
--%>

<%@page import="gob.gbc.Framework.KendoGrid"%>
<%@page import="gob.gbc.Framework.KendoGridColumn"%>
<%@page import="java.util.List" %>


<%
    try{
%>
<kendo:grid-detailTemplate id="template">
    <kendo:grid name="<%=grid.getName()%>" pageable="<%=grid.isPageable()%>" sortable="<%=grid.isSortable()%>" filterable="<%=grid.isFilterable()%>" scrollable="<%=grid.isScrollable()%>" noRecords="<%=grid.isNoRecords()%>"
                dataBound="<%=grid.getOnDataBound()%>" dataBinding="<%=grid.getOnDataBinding()%>" data-entityName="<%=grid.getEntityName()%>" data-add-url="<%=grid.getAddUrl()%>" data-edit-url="<%=grid.getEditUrl()%>" data-delete-url="<%=grid.getDeleteUrl()%>">
        <% if(grid.isPageable()){ %> 
            <kendo:grid-pageable refresh="false" pageSizes="true" buttonCount="5"/>
        <%  }   %>
        <kendo:grid-sortable allowUnsort="true" mode="multiple" />    
        <% if(grid.isEditRow()){ %> 
            <kendo:grid-editable mode="<%=grid.getEditableMode()%>" confirmation="Está seguro que desea eliminar esta línea?"/>
        <%  }   %>
        <kendo:grid-toolbar>
             <kendo:grid-toolbarItem text="Buscar">
                <kendo:grid-toolbarItem-template>
                    <script>
                        function(e){
                            return "<div class='container-grid-toolbar' style='float:left;margin-left:10px'><input class='grid-toolbar-search' onkeyup='search(this)' placeholder='BUSCAR EN LISTADO' style='border: #ccc 1px solid;padding: 5px;-webkit-border-radius: 2px;'/><span class='fa fa-search form-control-feedback' style='top:7px;left:190px;z-index:1;'></span></div>";                    
                        }
                    </script>
                </kendo:grid-toolbarItem-template>
            </kendo:grid-toolbarItem>
            <% if(grid.isAddRow()){ %>  
                <kendo:grid-toolbarItem name="create" text="Nuevo">
                    <kendo:grid-toolbarItem-template>
                        <script>
                            function(e){
                                return "<div class='container-grid-toolbar'><button onclick='<%=grid.getAddFunction()%>' type='button' class='btn-grid-toolbar'><span class='fa fa-plus-circle text-primary'></span>&nbsp;Nuevo</button></div>";                    
                            }
                        </script>
                    </kendo:grid-toolbarItem-template>
                </kendo:grid-toolbarItem>
            <%  }   %>
            <kendo:grid-toolbarItem>
                <kendo:grid-toolbarItem-template>
                    <script>
                        function(e){
                            return "<div class='container-grid-toolbar'><button type='button' class='btn-grid-toolbar k-pager-refresh'><span class='fa fa-refresh text-success'></span>&nbsp;Refrescar</button></div>";                         
                        }
                    </script>
                </kendo:grid-toolbarItem-template>
            </kendo:grid-toolbarItem>           
        </kendo:grid-toolbar>
        <kendo:grid-excel fileName="<%=grid.getFileName()%>"/>
        <kendo:dataSource pageSize="<%=grid.getPageSize()%>" batch="false" serverPaging="true" serverSorting="true" serverFiltering="true">
            <kendo:dataSource-transport parameterMap="<%=grid.getParameterMap()%>">
                <kendo:dataSource-transport-read url="<%=grid.getReadUrl()%>" dataType="json" type="GET" contentType="application/json"/>           
                <kendo:dataSource-transport-create url="<%=grid.getAddUrl()%>" dataType="json" type="POST" contentType="application/x-www-form-urlencoded" />
                <kendo:dataSource-transport-update url="<%=grid.getEditUrl()%>" dataType="json" type="POST" contentType="application/x-www-form-urlencoded" />
                <kendo:dataSource-transport-destroy url="<%=grid.getDeleteUrl()%>" dataType="json" type="POST" contentType="application/x-www-form-urlencoded" />
            </kendo:dataSource-transport>
            <kendo:dataSource-schema data="Data" total="Total">           
                <kendo:dataSource-schema-model id="actividad">
                    <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="renglon" type="string" editable="false" >
                            <kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="strYear" type="number" editable="false">
                            <kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="ramo" type="string" editable="false">
                            <kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="prg" type="string" editable="false">
                            <kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="componente" type="number" editable="false">
                            <kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="actividad" type="number" editable="false">
                            <kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="descr" type="string" >
                            <kendo:dataSource-schema-model-field-validation  />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="indicadores" type="string" editable="false">
                            <kendo:dataSource-schema-model-field-validation  />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="medios" type="string" editable="false">
                            <kendo:dataSource-schema-model-field-validation  />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="supuestos" type="string" editable="false">
                            <kendo:dataSource-schema-model-field-validation  />
                        </kendo:dataSource-schema-model-field>
                        </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>          
        <kendo:grid-columns>
            <% if(grid.isEditRow()){ %> 
            <kendo:grid-column width="3%" >
                <kendo:grid-column-command>
                    <kendo:grid-column-commandItem name="edit" click="<%=grid.getEditFunction()%>" text="">
                    </kendo:grid-column-commandItem>
                </kendo:grid-column-command>
            </kendo:grid-column>
            <%  }   %>
            <% if(grid.isDeleteRow()){ %> 
            <kendo:grid-column width="3%" >
                <kendo:grid-column-command>
                    <kendo:grid-column-commandItem name="destroy" click="<%=grid.getDeleteFunction()%>" text="">
                    </kendo:grid-column-commandItem>
                </kendo:grid-column-command>
            </kendo:grid-column>
            <%  }   %>
            <% if(grid.isPrintRow()){ %> 
            <kendo:grid-column width="3%" format="">
                <kendo:grid-column-command>
                    <kendo:grid-column-commandItem name="print-row" click="printRow" text="<span class='fa fa-print grid-command-item'/>">
                    </kendo:grid-column-commandItem>
                </kendo:grid-column-command>
            </kendo:grid-column>
            <kendo:grid-column width="3%" format="">
                <kendo:grid-column-command>
                    <kendo:grid-column-commandItem name="print-row" click="excelPrintRow" text="<span class='fa fa-table grid-command-item'/>">
                    </kendo:grid-column-commandItem>
                </kendo:grid-column-command>
            </kendo:grid-column>
            <%  }   %>
            <% for (final KendoGridColumn column : grid.getColumns()) { %>
                <% if(column.isAddEditor()){ %>    
                    <kendo:grid-column 
                        field="<%=column.getField()%>" 
                        title="<%=column.getTitle()%>"                 
                        editor="<%=column.getEditor()%>"
                        width="<%=column.getWidth()%>" 
                        hidden="<%=column.isHidden()%>" >
                    </kendo:grid-column>
                 <%  } else {  %>
                    <kendo:grid-column 
                        field="<%=column.getField()%>" 
                        title="<%=column.getTitle()%>"                 
                        width="<%=column.getWidth()%>" 
                        hidden="<%=column.isHidden()%>" >
                    </kendo:grid-column>
                 <%  }   %>
            <% }%>
        </kendo:grid-columns>
    </kendo:grid>
</kendo:grid-detailTemplate>
<%
    }catch(Exception ex){
        throw ex;
    }
%>