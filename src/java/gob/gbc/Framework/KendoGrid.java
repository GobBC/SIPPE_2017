/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.Framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ealonso
 */

public class KendoGrid {

    private String name = "grid";
    private boolean pageable = true;
    private boolean sortable = true;
    private boolean filterable = false;
    private boolean scrollable = false;
    private boolean noRecords = true;
    private boolean searchable = true;
    private String editableMode = "popup";//popup, inline
    private String editFormUrl = "";//"sectorEnteEdit.jsp";
    private String editActionsUrl = "";//"../ajax/ajaxCapturaSectorEnte.jsp";
    private String onDataBound = "onDataBound";
    private String onDataBinding = "onDataBinding";
    private String onEdit = "onEdit";
    private int pageSize = 10;
    private boolean autoSync = true;
    private String parameterMap = "parameterMap";
    private String readUrl = "/ReadRecords";//"/nombre";
    private String addUrl = "/AddRecords";
    private String editUrl = "/EditRecords";
    private String deleteUrl = "/DeleteRecords";
    private String addFunction = "addRow(this)";
    private String editFunction = "editRow";
    private String deleteFunction = "deleteRow";
    private String printFunction = "printRow";
    private boolean addRow = true;
    private boolean editRow = true;
    private boolean deleteRow = true;
    private boolean printRow = false;
    private boolean gridExcel = true;
    private boolean gridRefresh = true;
    private String addText = "Nuevo";
    public List<KendoGridColumn> columns = new ArrayList();
    private String entityName = "";//TITULO: Relación Sector-Ente
    private String modelName = "";
    private String onRequestEnd = "handle_requestEnd";

    private boolean customCol1 = false;
    private String customCol1Name = "customCol1";
    private String customCol1Function = "CustomCol1Row";
    private String customCol1Icon = "fa-question-circle";
    

    
    private boolean addModel = false;
    private String modelId = ""; //Campo llave del modelo
    public List<KendoGridSchemaModelField> fields = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPageable() {
        return pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public boolean isNoRecords() {
        return noRecords;
    }

    public void setNoRecords(boolean noRecords) {
        this.noRecords = noRecords;
    }

    public String getEditableMode() {
        return editableMode;
    }

    public void setEditableMode(String editableMode) {
        this.editableMode = editableMode;
    }

    public String getEditFormUrl() {
        return editFormUrl;
    }

    public void setEditFormUrl(String editFormUrl) {
        this.editFormUrl = editFormUrl;
    }

    public String getEditActionsUrl() {
        return editActionsUrl;
    }

    public void setEditActionsUrl(String editActionsUrl) {
        this.editActionsUrl = editActionsUrl;
    }

    public String getOnDataBound() {
        return onDataBound;
    }

    public void setOnDataBound(String onDataBound) {
        this.onDataBound = onDataBound;
    }

    public String getOnDataBinding() {
        return onDataBinding;
    }

    public void setOnDataBinding(String onDataBinding) {
        this.onDataBinding = onDataBinding;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isAutoSync() {
        return autoSync;
    }

    public void setAutoSync(boolean autoSync) {
        this.autoSync = autoSync;
    }

    public String getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(String parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getReadUrl() {
        return readUrl;
    }

    public void setReadUrl(String readUrl) {
        this.readUrl = readUrl;
    }

    public String getAddUrl() {
        return addUrl;
    }

    public void setAddUrl(String addUrl) {
        this.addUrl = addUrl;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    public List<KendoGridColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<KendoGridColumn> columns) {
        this.columns = columns;
    }

    public String getAddFunction() {
        return addFunction;
    }

    public void setAddFunction(String addFunction) {
        this.addFunction = addFunction;
    }

    public String getEditFunction() {
        return editFunction;
    }

    public void setEditFunction(String editFunction) {
        this.editFunction = editFunction;
    }

    public String getDeleteFunction() {
        return deleteFunction;
    }

    public void setDeleteFunction(String deleteFunction) {
        this.deleteFunction = deleteFunction;
    }

    public String getPrintFunction() {
        return printFunction;
    }

    public void setPrintFunction(String printFunction) {
        this.printFunction = printFunction;
    }

    public String getFileName() {
        DateFormat dateFormat = new SimpleDateFormat("_yyyyMMdd_HHmmss");
        Date date = new Date();
        return name + dateFormat.format(date) + ".xlsx";
    }

    public String getTooltipName() {
        return "#" + name;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public boolean isAddRow() {
        return addRow;
    }

    public void setAddRow(boolean addRow) {
        this.addRow = addRow;
    }

    public boolean isEditRow() {
        return editRow;
    }

    public void setEditRow(boolean editRow) {
        this.editRow = editRow;
    }

    public boolean isDeleteRow() {
        return deleteRow;
    }

    public void setDeleteRow(boolean deleteRow) {
        this.deleteRow = deleteRow;
    }

    public boolean isPrintRow() {
        return printRow;
    }

    public void setPrintRow(boolean printRow) {
        this.printRow = printRow;
    }

    public boolean isCustomCol1() {
        return customCol1;
    }

    public void setCustomCol1(boolean customCol1) {
        this.customCol1 = customCol1;
    }

    public String getCustomCol1Name() {
        return customCol1Name;
    }

    public void setCustomCol1Name(String customCol1Name) {
        this.customCol1Name = customCol1Name;
    }

    public String getCustomCol1Function() {
        return customCol1Function;
    }

    public void setCustomCol1Function(String customCol1Function) {
        this.customCol1Function = customCol1Function;
    }

    public String getCustomCol1Icon() {
        return customCol1Icon;
    }

    public void setCustomCol1Icon(String customCol1Icon) {
        this.customCol1Icon = customCol1Icon;
    }

    public String getOnRequestEnd() {
        return onRequestEnd;
    }

    public void setOnRequestEnd(String onRequestEnd) {
        this.onRequestEnd = onRequestEnd;
    }

    public boolean isGridExcel() {
        return gridExcel;
    }

    public void setGridExcel(boolean gridExcel) {
        this.gridExcel = gridExcel;
    }

    public boolean isGridRefresh() {
        return gridRefresh;
    }

    public void setGridRefresh(boolean gridRefresh) {
        this.gridRefresh = gridRefresh;
    }

    public String getAddText() {
        return addText;
    }

    public void setAddText(String addText) {
        this.addText = addText;
    }    
    
    //MODELO DE DATOS
    public boolean isAddModel() {
        return addModel;
    }

    public void setAddModel(boolean addModel) {
        this.addModel = addModel;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<KendoGridSchemaModelField> getFields() {
        return fields;
    }

    public void setFields(List<KendoGridSchemaModelField> fields) {
        this.fields = fields;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public String getOnEdit() {
        return onEdit;
    }

    public void setOnEdit(String onEdit) {
        this.onEdit = onEdit;
    }
    
}
