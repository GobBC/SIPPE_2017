/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;

/**
 *
 * @author ugarcia 
 */
public class CongelaPresupuestoBean extends ResultSQL {

    Bitacora bitacora;

    public CongelaPresupuestoBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public String congelaMeta(int year) {
        String mensaje = new String();
        try {
            mensaje = super.getResultSQLcongelaMeta(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String congelaAccion(int year) {
        String mensaje = new String();
        try {
            mensaje = super.getResultSQLcongelaAccion(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String congelaEstimacion(int year) {
        String mensaje = new String();
        try {
            mensaje = super.getResultSQLcongelaEstimacion(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String congelaAccionEstimacion(int year) {
        String mensaje = new String();
        try {
            mensaje = super.getResultSQLcongelaAccionEstimacion(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String congelaAccionReq(int year) {
        String mensaje = new String();
        try {
            mensaje = super.getResultSQLcongelaAccionReq(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String congelaAccionProyecto(int year) {
        String mensaje = new String();
        try {
            mensaje = super.getResultSQLcongelaProyecto(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String congelaAccionPpto(int year) {
        String mensaje = new String();
        try {
            mensaje = super.getResultSQLcongelaPpto(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String congelaPresupuesto(int year) {
        String mensaje = new String();
        mensaje = this.congelaAccionProyecto(year);
        if (mensaje.isEmpty()) {
            mensaje = this.congelaMeta(year);
            if (mensaje.isEmpty()) {
                mensaje = this.congelaEstimacion(year);
                if (mensaje.isEmpty()) {
                    mensaje = this.congelaAccion(year);
                    if (mensaje.isEmpty()) {
                        mensaje = this.congelaAccionEstimacion(year);
                        if (mensaje.isEmpty()) {
                            mensaje = this.congelaAccionReq(year);
                            if (mensaje.isEmpty()) {
                                mensaje = this.congelaAccionPpto(year);
                                if (mensaje.isEmpty()) {
                                    super.transaccionCommit();
                                    mensaje = "1";
                                } else {
                                    super.transaccionRollback();
                                }
                            } else {
                                super.transaccionRollback();
                            }
                        }else{
                            super.transaccionRollback();
                        }
                    } else {
                        super.transaccionRollback();
                    }
                } else {
                    super.transaccionRollback();
                }
            } else {
                super.transaccionRollback();
            }
        } else {
            super.transaccionRollback();
        }
        return mensaje;
    }
    
    public int getCountPresupuestoCongelado(int year){
        int total = 0;
        try {
            total = super.getResultSQLCountCongelaPresupuesto(year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return total;
    }

}
