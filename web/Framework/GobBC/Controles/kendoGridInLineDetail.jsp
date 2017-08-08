<%-- 
    Document   : kendoGridInLineDetail
    Created on : Jul 14, 2017, 9:26:48 AM
    Author     : ealonso
--%>

<%@page import="gob.gbc.Framework.KendoGrid"%>
<%@page import="gob.gbc.Framework.KendoGridColumn"%>
<%@page import="gob.gbc.Framework.KendoGridSchemaModelField"%>
<%@page import="java.util.List" %>

<%
    try{
%>
<kendo:grid-detailTemplate id="template">
    <kendo:grid id="componenteGrid_#=componente#" name="componenteGrid_#=componente#" pageable="<%=grid.isPageable()%>" sortable="<%=grid.isSortable()%>" scrollable="<%=grid.isScrollable()%>" noRecords="<%=grid.isNoRecords()%>"
            edit="<%=grid.getOnEdit()%>" dataBound="<%=grid.getOnDataBound()%>" dataBinding="<%=grid.getOnDataBinding()%>" data-entityName="<%=grid.getEntityName()%>" data-add-url="<%=grid.getAddUrl()%>" data-edit-url="<%=grid.getEditUrl()%>" data-delete-url="<%=grid.getDeleteUrl()%>">
        <% if(grid.isPageable()){ %> 
            <kendo:grid-pageable refresh="false" pageSizes="true" buttonCount="5"/>
        <%  }   %>
       <kendo:grid-sortable allowUnsort="true" mode="multiple" />    
       <% if(grid.isEditRow()){ %> 
            <kendo:grid-editable mode="<%=grid.getEditableMode()%>" confirmation="Está seguro que desea eliminar esta línea?"/>
       <%  }   %>
       <kendo:grid-toolbar>
            <% if(grid.isAddRow()){ %>  
                <kendo:grid-toolbarItem name="create" text="<%=grid.getAddText()%>">
                    <kendo:grid-toolbarItem-template>
                        <script>
                            function(e){
                                return "<div class='container-grid-toolbar'><button onclick='<%=grid.getAddFunction()%>' type='button' class='btn-grid-toolbar'><span class='fa fa-plus-circle text-primary'></span>&nbsp;<%=grid.getAddText()%></button></div>";                    
                            }
                        </script>
                    </kendo:grid-toolbarItem-template>
                </kendo:grid-toolbarItem>
            <%  }   %>
            <% if(grid.isGridRefresh()){ %> 
                <kendo:grid-toolbarItem>
                    <kendo:grid-toolbarItem-template>
                        <script>
                            function(e){
                                return "<div class='container-grid-toolbar'><button type='button' class='btn-grid-toolbar k-pager-refresh'><span class='fa fa-refresh text-success'></span>&nbsp;Refrescar</button></div>";                         
                            }
                        </script>
                    </kendo:grid-toolbarItem-template>
                </kendo:grid-toolbarItem>
            <%  }   %>
            <% if(grid.isGridExcel()){ %> 
                    <kendo:grid-toolbarItem name="excel">
                        <kendo:grid-toolbarItem-template>
                            <script>
                                function(e){
                                    return "<div class='container-grid-toolbar'><button type='button' class='btn-grid-toolbar k-grid-excel'><span class='k-font-icon k-i-xls text-success'></span>&nbsp;Exportar a Excel</button></div>";
                                }
                            </script>
                        </kendo:grid-toolbarItem-template>
                    </kendo:grid-toolbarItem>
           <%  }   %>
        </kendo:grid-toolbar>
       <kendo:dataSource pageSize="<%=grid.getPageSize()%>" batch="false" serverPaging="true" serverSorting="true" serverFiltering="true" requestEnd="<%=grid.getOnRequestEnd()%>">
            <kendo:dataSource-transport parameterMap="<%=grid.getParameterMap()%>">
                <kendo:dataSource-transport-read url="<%=grid.getReadUrl()%>" data="<%=data %>" dataType="json" type="GET" contentType="application/x-www-form-urlencoded; charset=utf-8"/> 
                <kendo:dataSource-transport-create url="<%=grid.getAddUrl()%>" dataType="json" type="POST" contentType="application/x-www-form-urlencoded; charset=utf-8" />
                <kendo:dataSource-transport-update url="<%=grid.getEditUrl()%>" dataType="json" type="POST" contentType="application/x-www-form-urlencoded; charset=utf-8" />
                <kendo:dataSource-transport-destroy url="<%=grid.getDeleteUrl()%>" dataType="json" type="POST" contentType="application/x-www-form-urlencoded; charset=utf-8" />
            </kendo:dataSource-transport>
            <kendo:dataSource-schema data="Data" total="Total"> 
                <% if(grid.isAddModel()){ %> 
                    <kendo:dataSource-schema-model id="<%=grid.getModelId()%>">
                        <kendo:dataSource-schema-model-fields>
                            <%  for (final KendoGridSchemaModelField fieldTemp : grid.getFields()) { %>  
                                    <kendo:dataSource-schema-model-field 
                                        name="<%=fieldTemp.getName()%>" 
                                        type="<%=fieldTemp.getType()%>" 
                                        editable="<%=fieldTemp.isEditable()%>" >
                                        <kendo:dataSource-schema-model-field-validation required="<%=fieldTemp.isValidationRequired()%>" />
                                    </kendo:dataSource-schema-model-field>                            
                            <% } %>            
                        </kendo:dataSource-schema-model-fields>
                    </kendo:dataSource-schema-model>
                <% } %>   
        </kendo:dataSource-schema>
        </kendo:dataSource>          
        <kendo:grid-columns>
            <% if(grid.isEditRow()){ %> 
            <kendo:grid-column width="35px" >
                <kendo:grid-column-command>
                    <kendo:grid-column-commandItem name="edit" text="">
                    </kendo:grid-column-commandItem>
                </kendo:grid-column-command>
            </kendo:grid-column>
            <%  }   %>
            <% if(grid.isDeleteRow()){ %> 
            <kendo:grid-column width="35px" >
                <kendo:grid-column-command>
                    <kendo:grid-column-commandItem name="delete-row" click="<%=grid.getDeleteFunction()%>" text="<span class='fa fa-trash grid-command-item'/>">
                    </kendo:grid-column-commandItem>
                </kendo:grid-column-command>
            </kendo:grid-column>
            <%  }   %>
            <% if(grid.isPrintRow()){ %> 
                <kendo:grid-column width="3%" format="">
                    <kendo:grid-column-command>
                        <kendo:grid-column-commandItem name="print-row" click="printRow" text="<span class='fa fa-file-pdf-o grid-command-item'/>">
                        </kendo:grid-column-commandItem>
                    </kendo:grid-column-command>
                </kendo:grid-column>
                <kendo:grid-column width="3%" format="">
                    <kendo:grid-column-command>
                        <kendo:grid-column-commandItem name="print-row" click="excelPrintRow" text="<span class='fa fa-file-excel-o grid-command-item'/>">
                        </kendo:grid-column-commandItem>
                    </kendo:grid-column-command>
                </kendo:grid-column>
            <%  }   %>
            <% for (final KendoGridColumn column : grid.getColumns()) { %>
                    <% if(column.isAddEditor()){ %>    
                        <% if(column.isAddTemplate()){ %>    
                            <kendo:grid-column 
                                field="<%=column.getField()%>" 
                                title="<%=column.getTitle()%>"                 
                                editor="<%=column.getEditor()%>"
                                template="<%=column.getTemplate()%>"
                                width="<%=column.getWidth()%>" 
                                hidden="<%=column.isHidden()%>" >
                            </kendo:grid-column>
                        <%  } else {  %>
                            <kendo:grid-column 
                                field="<%=column.getField()%>" 
                                title="<%=column.getTitle()%>"                 
                                editor="<%=column.getEditor()%>"
                                width="<%=column.getWidth()%>" 
                                hidden="<%=column.isHidden()%>" >
                            </kendo:grid-column>
                        <%  }   %>
                     <%  } else {  %>
                        <% if(column.isAddTemplate()){ %>    
                            <kendo:grid-column 
                                field="<%=column.getField()%>" 
                                title="<%=column.getTitle()%>"                 
                                template="<%=column.getTemplate()%>"
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
                     <%  }   %>
                <% }%>
        </kendo:grid-columns>
    </kendo:grid>
</kendo:grid-detailTemplate>
<%
    } catch(Exception ex){
        throw ex;
    }
%>