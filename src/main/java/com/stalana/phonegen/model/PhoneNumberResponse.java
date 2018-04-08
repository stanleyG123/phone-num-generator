package com.stalana.phonegen.model;

import java.util.List;
/**
 * Bean to represent phone number response
 * Created by stan on 4/5/18.
 */
public class PhoneNumberResponse {
    // total number of combination
    private Integer numberOfCombos;
    // combinations in a current page
    private List<String> combos;

    //current page start
    private Integer pageStart;
    //current page end
    private Integer pageEnd;

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(Integer pageEnd) {
        this.pageEnd = pageEnd;
    }

    public Integer getNumberOfCombos() {
        return numberOfCombos;
    }

    public void setNumberOfCombos(Integer numberOfCombos) {
        this.numberOfCombos = numberOfCombos;
    }

    public List<String> getCombos() {
        return combos;
    }

    public void setCombos(List<String> combos) {
        this.combos = combos;
    }
}
