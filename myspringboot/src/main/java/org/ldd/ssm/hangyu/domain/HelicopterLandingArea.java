package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class HelicopterLandingArea implements Serializable {
    private Long id;

    private String iata;

    private String tlofcoordinatesorfatoentrancecoordinatesandgeoidamplitud;

    private String tlofandorfatoelevation;

    private String tlofandfatoarearoadsurfacestrengthandmarks;

    private String fatorealorientationandmagneticbearing;

    private String releasedfrom;

    private String approachandfatolight;

    private String helicopterNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata == null ? null : iata.trim();
    }

    public String getTlofcoordinatesorfatoentrancecoordinatesandgeoidamplitud() {
        return tlofcoordinatesorfatoentrancecoordinatesandgeoidamplitud;
    }

    public void setTlofcoordinatesorfatoentrancecoordinatesandgeoidamplitud(String tlofcoordinatesorfatoentrancecoordinatesandgeoidamplitud) {
        this.tlofcoordinatesorfatoentrancecoordinatesandgeoidamplitud = tlofcoordinatesorfatoentrancecoordinatesandgeoidamplitud == null ? null : tlofcoordinatesorfatoentrancecoordinatesandgeoidamplitud.trim();
    }

    public String getTlofandorfatoelevation() {
        return tlofandorfatoelevation;
    }

    public void setTlofandorfatoelevation(String tlofandorfatoelevation) {
        this.tlofandorfatoelevation = tlofandorfatoelevation == null ? null : tlofandorfatoelevation.trim();
    }

    public String getTlofandfatoarearoadsurfacestrengthandmarks() {
        return tlofandfatoarearoadsurfacestrengthandmarks;
    }

    public void setTlofandfatoarearoadsurfacestrengthandmarks(String tlofandfatoarearoadsurfacestrengthandmarks) {
        this.tlofandfatoarearoadsurfacestrengthandmarks = tlofandfatoarearoadsurfacestrengthandmarks == null ? null : tlofandfatoarearoadsurfacestrengthandmarks.trim();
    }

    public String getFatorealorientationandmagneticbearing() {
        return fatorealorientationandmagneticbearing;
    }

    public void setFatorealorientationandmagneticbearing(String fatorealorientationandmagneticbearing) {
        this.fatorealorientationandmagneticbearing = fatorealorientationandmagneticbearing == null ? null : fatorealorientationandmagneticbearing.trim();
    }

    public String getReleasedfrom() {
        return releasedfrom;
    }

    public void setReleasedfrom(String releasedfrom) {
        this.releasedfrom = releasedfrom == null ? null : releasedfrom.trim();
    }

    public String getApproachandfatolight() {
        return approachandfatolight;
    }

    public void setApproachandfatolight(String approachandfatolight) {
        this.approachandfatolight = approachandfatolight == null ? null : approachandfatolight.trim();
    }

	public String getHelicopterNote() {
		return helicopterNote;
	}

	public void setHelicopterNote(String helicopterNote) {
		this.helicopterNote = helicopterNote;
	}
}