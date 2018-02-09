package org.ldd.ssm.crm.domain;

public class SaleDptInfo {
    private Integer id;

    private String dptName;

    private String address;

    private String linkman;

    private String contactWay;

    private String whetherLocalBase;

    private String mapDisplay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName == null ? null : dptName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay == null ? null : contactWay.trim();
    }

    public String getWhetherLocalBase() {
        return whetherLocalBase;
    }

    public void setWhetherLocalBase(String whetherLocalBase) {
        this.whetherLocalBase = whetherLocalBase == null ? null : whetherLocalBase.trim();
    }

    public String getMapDisplay() {
        return mapDisplay;
    }

    public void setMapDisplay(String mapDisplay) {
        this.mapDisplay = mapDisplay == null ? null : mapDisplay.trim();
    }
}