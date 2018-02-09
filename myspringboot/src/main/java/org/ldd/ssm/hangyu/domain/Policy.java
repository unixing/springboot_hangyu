package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class Policy implements Serializable {
    private Long id;

    private String policyyear;

    private String subsidypolicy;

    private Long cityinfothreeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyyear() {
        return policyyear;
    }

    public void setPolicyyear(String policyyear) {
        this.policyyear = policyyear == null ? null : policyyear.trim();
    }

    public String getSubsidypolicy() {
        return subsidypolicy;
    }

    public void setSubsidypolicy(String subsidypolicy) {
        this.subsidypolicy = subsidypolicy == null ? null : subsidypolicy.trim();
    }

    public Long getCityinfothreeId() {
        return cityinfothreeId;
    }

    public void setCityinfothreeId(Long cityinfothreeId) {
        this.cityinfothreeId = cityinfothreeId;
    }
}