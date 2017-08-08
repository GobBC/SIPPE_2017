<%-- 
    Document   : kendoDropDown
    Created on : Jun 5, 2017, 4:27:40 PM
    Author     : ealonso
--%>
<label for="<%=drop.getLabelFor()%>"><%=drop.getLabel()%></label>
<kendo:dropDownList  
    name="<%=drop.getName()%>"
    dataTextField="<%=drop.getDataTextField()%>" 
    dataValueField="<%=drop.getDataValueField()%>" 
    filter="<%=drop.getFilter()%>" 
    autoBind="<%=drop.getAutoBind()%>" 
    style="<%=drop.getStyle()%>" 
    placeholder="<%=drop.getPlaceholder()%>" 
    change="<%=drop.getChange()%>" 
    optionLabel="<%=drop.getOptionLabel()%>"
    value="<%=drop.getValue()%>"
    enable="<%=drop.isEnable()%>"
    noDataTemplate="<%=drop.getNoDataTemplate()%>"
    >
    <kendo:dataSource 
        serverFiltering="<%=drop.getServerFiltering()%>">
        <kendo:dataSource-transport 
            parameterMap="<%=drop.getParameterMap()%>">
            <kendo:dataSource-transport-read 
                url="<%=drop.getReadUrl()%>" 
                dataType="json" type="GET" contentType="application/json; charset=utf-8">
            </kendo:dataSource-transport-read>
        </kendo:dataSource-transport>
        <kendo:dataSource-schema data="Data" total="Total">
        </kendo:dataSource-schema>
    </kendo:dataSource>
</kendo:dropDownList >
