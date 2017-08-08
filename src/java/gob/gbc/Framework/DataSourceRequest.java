/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.Framework;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author azatarain
 */
public class DataSourceRequest {
    private String search = "";
    private int page = 1;
    private int pageSize = 0;
    private int skip = 0;
    private int take = 0;
    List<KendoGridSort> sort = new ArrayList();
    private String masterId = "";
    
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public List<KendoGridSort> getSort() {
        return sort;
    }

    public void setSort(List<KendoGridSort> sort) {
        this.sort = sort;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }
    
    
}
