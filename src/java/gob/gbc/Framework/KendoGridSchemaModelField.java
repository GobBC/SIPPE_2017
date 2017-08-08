/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.Framework;

/**
 *
 * @author jarguelles
 */

public class KendoGridSchemaModelField {

    private String name = "";
    private String type = "string";
    private boolean editable = false;
    private boolean validationRequired = false;

    public KendoGridSchemaModelField() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isValidationRequired() {
        return validationRequired;
    }

    public void setValidationRequired(boolean validationRequired) {
        this.validationRequired = validationRequired;
    }
    
}
