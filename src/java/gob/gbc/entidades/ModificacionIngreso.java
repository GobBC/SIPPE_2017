/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class ModificacionIngreso extends Movimiento {

    private List<CapturaPresupuestoIngreso> modificacionesIngresoList;

    public ModificacionIngreso() {
        super();
        modificacionesIngresoList = new ArrayList<CapturaPresupuestoIngreso>();
    }

    public List<CapturaPresupuestoIngreso> getModificacionesIngresoList() {
        return modificacionesIngresoList;
    }

    public void setModificacionesIngresoList(List<CapturaPresupuestoIngreso> modificacionesIngresoList) {
        this.modificacionesIngresoList = modificacionesIngresoList;
    }


}
