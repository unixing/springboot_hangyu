package org.ldd.ssm.crm.domain;

public class PlaneTeam {
    private Integer id;

    private String teamName;

    private Integer planeCount;

    private String planeType;

    private Integer planeAge;

    private Integer flyerCount;

    private String teamLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
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

    public Integer getPlaneAge() {
        return planeAge;
    }

    public void setPlaneAge(Integer planeAge) {
        this.planeAge = planeAge;
    }

    public Integer getFlyerCount() {
        return flyerCount;
    }

    public void setFlyerCount(Integer flyerCount) {
        this.flyerCount = flyerCount;
    }

    public String getTeamLevel() {
        return teamLevel;
    }

    public void setTeamLevel(String teamLevel) {
        this.teamLevel = teamLevel == null ? null : teamLevel.trim();
    }
}