/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.aplicacion;

import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ugarcia
 */
public class YearBean extends ResultSQL{
    Bitacora bitacora;
    public YearBean() throws Exception{        
        
        super();        
        bitacora = new Bitacora();
    }
    public YearBean(String tipoDependecia) throws Exception{
        super(tipoDependecia);
         bitacora = new Bitacora();
    }
     
    public List<String> getSelectYear(){
        List<String> yearList = new ArrayList<String>();
        try{
            yearList = super.getYears();
        }catch(SQLException sql){
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }   
        return yearList;
    }
    
    public String getAvisoInicial(){
        String avisoInicial = new String();
        try{
            avisoInicial = super.getResultSQLGetAvisoInicial();
        }catch(SQLException sql){
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }   
        return avisoInicial;
    }
    
}
