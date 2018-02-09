package org.ldd.ssm.crm.domain;

public class Base {
    private Integer id;

    private String baseName;

    private String planeStopPosition;

    private String cityname;

    private Integer planeCount;

    private String planeType;

    private String flyingRoutes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName == null ? null : baseName.trim();
    }

    public String getPlaneStopPosition() {
        return planeStopPosition;
    }

    public void setPlaneStopPosition(String planeStopPosition) {
        this.planeStopPosition = planeStopPosition == null ? null : planeStopPosition.trim();
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public Integer getPlaneCount() {
        return planeCount;
    }

    public void setPlaneCount(Integer planeCount) {
        this.planeCount = planeCount;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType == null ? null : planeType.trim();
    }

    public String getFlyingRoutes() {
        return flyingRoutes;
    }

    public void setFlyingRoutes(String flyingRoutes) {
        this.flyingRoutes = flyingRoutes == null ? null : flyingRoutes.trim();
    }
}