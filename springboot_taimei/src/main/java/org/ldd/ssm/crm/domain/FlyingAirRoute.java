package org.ldd.ssm.crm.domain;

public class FlyingAirRoute {
    private Integer id;

    private String airRoute;

    private String airRouteType;

    private String schedule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirRoute() {
        return airRoute;
    }

    public void setAirRoute(String airRoute) {
        this.airRoute = airRoute == null ? null : airRoute.trim();
    }

    public String getAirRouteType() {
        return airRouteType;
    }

    public void setAirRouteType(String airRouteType) {
        this.airRouteType = airRouteType == null ? null : airRouteType.trim();
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule == null ? null : schedule.trim();
    }
}