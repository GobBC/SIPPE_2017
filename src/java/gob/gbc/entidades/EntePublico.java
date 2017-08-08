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
public class EntePublico {

    private String entePublico;
    private String descripcion;
    private int sectorConac;
    private int sectorTipo;
    private int sectorEconomico;
    private int subsector;
    private String tipoEntePublico;


    public EntePublico() {
    }

    public String getEntePublico() {
        return entePublico;
    }

    public void setEntePublico(String entePublico) {
        this.entePublico = entePublico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the sectorConac
     */
    public int getSectorConac() {
        return sectorConac;
    }

    /**
     * @return the sectorTipo
     */
    public int getSectorTipo() {
        return sectorTipo;
    }

    /**
     * @return the sectorEconomico
     */
    public int getSectorEconomico() {
        return sectorEconomico;
    }

    /**
     * @return the subsector
     */
    public int getSubsector() {
        return subsector;
    }

    /**
     * @return the tipoEntePublico
     */
    public String getTipoEntePublico() {
        return tipoEntePublico;
    }

    /**
     * @param sectorConac the sectorConac to set
     */
    public void setSectorConac(int sectorConac) {
        this.sectorConac = sectorConac;
    }

    /**
     * @param sectorTipo the sectorTipo to set
     */
    public void setSectorTipo(int sectorTipo) {
        this.sectorTipo = sectorTipo;
    }

    /**
     * @param sectorEconomico the sectorEconomico to set
     */
    public void setSectorEconomico(int sectorEconomico) {
        this.sectorEconomico = sectorEconomico;
    }

    /**
     * @param subsector the subsector to set
     */
    public void setSubsector(int subsector) {
        this.subsector = subsector;
    }

    /**
     * @param tipoEntePublico the tipoEntePublico to set
     */
    public void setTipoEntePublico(String tipoEntePublico) {
        this.tipoEntePublico = tipoEntePublico;
    }
    
    
    
    
}
