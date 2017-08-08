/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author jarguelles
 */
public class RevisionCaratula {

    private String revision;
    private String descr_Corta;
    private String descr;
    private boolean libre_Num_Session;
    private boolean libre_Mod_Presup;
    private boolean libre_Mod_Prog;
    private boolean selected_Num_Session;
    private boolean selected_Mod_Presup;
    private boolean selected_Mod_Prog;

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getDescr_Corta() {
        return descr_Corta;
    }

    public void setDescr_Corta(String descr_Corta) {
        this.descr_Corta = descr_Corta;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isLibre_Num_Session() {
        return libre_Num_Session;
    }

    public void setLibre_Num_Session(boolean libre_Num_Session) {
        this.libre_Num_Session = libre_Num_Session;
    }

    public boolean isLibre_Mod_Presup() {
        return libre_Mod_Presup;
    }

    public void setLibre_Mod_Presup(boolean libre_Mod_Presup) {
        this.libre_Mod_Presup = libre_Mod_Presup;
    }

    public boolean isLibre_Mod_Prog() {
        return libre_Mod_Prog;
    }

    public void setLibre_Mod_Prog(boolean libre_Mod_Prog) {
        this.libre_Mod_Prog = libre_Mod_Prog;
    }

    public boolean isSelected_Num_Session() {
        return selected_Num_Session;
    }

    public void setSelected_Num_Session(boolean selected_Num_Session) {
        this.selected_Num_Session = selected_Num_Session;
    }

    public boolean isSelected_Mod_Presup() {
        return selected_Mod_Presup;
    }

    public void setSelected_Mod_Presup(boolean selected_Mod_Presup) {
        this.selected_Mod_Presup = selected_Mod_Presup;
    }

    public boolean isSelected_Mod_Prog() {
        return selected_Mod_Prog;
    }

    public void setSelected_Mod_Prog(boolean selected_Mod_Prog) {
        this.selected_Mod_Prog = selected_Mod_Prog;
    }   
    
}
