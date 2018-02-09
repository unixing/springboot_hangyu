package org.ldd.ssm.crm.domain;

public class Company {
    private Long id;

    private String cpyNm;

    private String cpyAds;

    private String cpyPho;

    private String cpyEml;

    private String cpyIno;
    
    private String cpyItia;
    
    private int type;//1.当前登录公司

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpyNm() {
        return cpyNm;
    }

    public void setCpyNm(String cpyNm) {
        this.cpyNm = cpyNm == null ? null : cpyNm.trim();
    }

    public String getCpyAds() {
        return cpyAds;
    }

    public void setCpyAds(String cpyAds) {
        this.cpyAds = cpyAds == null ? null : cpyAds.trim();
    }

    public String getCpyPho() {
        return cpyPho;
    }

    public void setCpyPho(String cpyPho) {
        this.cpyPho = cpyPho == null ? null : cpyPho.trim();
    }

    public String getCpyEml() {
        return cpyEml;
    }

    public void setCpyEml(String cpyEml) {
        this.cpyEml = cpyEml == null ? null : cpyEml.trim();
    }

    public String getCpyIno() {
        return cpyIno;
    }

    public void setCpyIno(String cpyIno) {
        this.cpyIno = cpyIno == null ? null : cpyIno.trim();
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCpyItia() {
		return cpyItia;
	}

	public void setCpyItia(String cpyItia) {
		this.cpyItia = cpyItia;
	}
    
}