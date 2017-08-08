/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.Framework;
import java.util.List;
/**
 *
 * @author azatarain
 */
public class DataSourceResult {

	private int Total;
	private List<?> Data;
	public int getTotal() {
		return Total;
	}
	public void setTotal(int total) {
		Total = total;
	}
	public List<?> getData() {
		return Data;
	}
	public void setData(List<?> data) {
		Data = data;
	}
	
}
