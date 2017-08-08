/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.Framework;

/**
 *
 * @author ealonso
 */

public class KendoGridColumn {
    private String title="";
    private String field= "";
    private String width= "";
    private boolean hidden= false;
    private String text= "";    
    private String click = "";
    private String editor = "";
    private boolean addEditor = false;
    private String template = "";
    private boolean addTemplate = false;
    private String attributes = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }
    
    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
        
    public boolean isAddEditor() {
        return addEditor;
    }

    public void setAddEditor(boolean addEditor) {
        this.addEditor = addEditor;
    }
    
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }    

    public boolean isAddTemplate() {
        return addTemplate;
    }

    public void setAddTemplate(boolean addTemplate) {
        this.addTemplate = addTemplate;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }       
}
