/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.Framework;

/**
 *
 * @author ealonso
 */
public class KendoDropdown {
    private String labelFor = "cmbSector";
    private String label = null;
    private String name  = null;//= "sectorComboBox";//TODO: QUITAR VALOR PREDETERMINADO = null;//
    private String dataTextField = null;//= "descr";
    private String dataValueField  = null;//="sector" ;//TODO: QUITAR VALOR PREDETERMINADO
    private String filter = "contains";
    private boolean autoBind = false;
    private String style = "width:100%;";//= "width: 200px;" ;//TODO: QUITAR VALOR PREDETERMINADO
    private String placeholder = "Buscar...";
    private String change = "onChangeFilters" ;//TODO: QUITAR VALOR PREDETERMINADO
    private String optionLabel = "Seleccione...";
    //kendo:dataSourse
    private boolean serverFiltering= false;
    private String parameterMap = "parameterMap";
    private String readUrl = null;//= "../ajax/ajaxConsultaSectorJson.jsp";//TODO: QUITAR VALOR PREDETERMINADO
    private String value = "";
    private boolean enable= true;
    //private boolean readOnlyField = false;//readOnly";
    private String noDataTemplate = "No se encontro información";
    /**
     * @return the labelFor
     */
    public String getLabelFor() {
        return labelFor;
    }

    /**
     * @param labelFor the labelFor to set
     */
    public void setLabelFor(String labelFor) {
        this.labelFor = labelFor;
    }
    
        /**
     * @return the labelFor
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dataTextField
     */
    public String getDataTextField() {
        return dataTextField;
    }

    /**
     * @param dataTextField the dataTextField to set
     */
    public void setDataTextField(String dataTextField) {
        this.dataTextField = dataTextField;
    }

    /**
     * @return the dataValueFiel
     */
    public String getDataValueField() {
        return dataValueField;
    }

    /**
     * @param dataValueField the dataValueField to set
     */
    public void setDataValueField(String dataValueField) {
        this.dataValueField = dataValueField;
    }

    /**
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * @return the autoBind
     */
    public boolean getAutoBind() {
        return autoBind;
    }

    /**
     * @param autoBind the autoBind to set
     */
    public void setAutoBind(boolean autoBind) {
        this.autoBind = autoBind;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the placeholder
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * @param placeholder the placeholder to set
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * @return the change
     */
    public String getChange() {
        return change;
    }

    /**
     * @param change the change to set
     */
    public void setChange(String change) {
        this.change = change;
    }

    /**
     * @return the optionLabel
     */
    public String getOptionLabel() {
        return optionLabel;
    }

    /**
     * @param optionLabel the optionLabel to set
     */
    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }

    /**
     * @return the serverFiltering
     */
    public boolean getServerFiltering() {
        return serverFiltering;
    }

    /**
     * @param serverFiltering the serverFiltering to set
     */
    public void setServerFiltering(boolean serverFiltering) {
        this.serverFiltering = serverFiltering;
    }

    /**
     * @return the serverFiltering
     */
    public String getParameterMap() {
        return parameterMap;
    }

    /**
     * @param parameterMap the parameterMap to set
     */
    public void setParameterMap(String parameterMap) {
        this.parameterMap = parameterMap;
    }
    
    /**
     * @return the readUrl
     */
    public String getReadUrl() {
        return readUrl;
    }

    /**
     * @param readUrl the readUrl to set
     */
    public void setReadUrl(String readUrl) {
        this.readUrl = readUrl;
    }
    
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * @return the enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
//    /**
//     * @return the readOnlyField
//     */
//    public boolean getReadOnlyField() {
//        return readOnlyField;
//    }
//
//    /**
//     * @param readOnlyField the readOnlyField to set
//     */
//    public void setReadOnlyField(boolean readOnlyField) {
//        this.readOnlyField = readOnlyField;
//    }
    
    /**
     * @return the noDataTemplate
     */
    public String getNoDataTemplate() {
        return noDataTemplate;
    }

    /**
     * @param noDataTemplate the noDataTemplate to set
     */
    public void setNoDataTemplate(String noDataTemplate) {
        this.noDataTemplate = noDataTemplate;
    }
}
