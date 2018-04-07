package com.stalana.phonegen.model;

/**
 * Bean to represent phone number page request
 * Created by stan on 4/5/18.
 */
public class PhoneNumberPageRequest {
    Integer pageStart;
    Integer pageEnd;

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
}
