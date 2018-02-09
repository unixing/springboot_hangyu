package org.ldd.ssm.crm.domain;

import java.io.Serializable;

public class MenuNew implements Serializable,Cloneable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String message;

    private String name;

    private String icon;

    private String target;

    private String url;
    
    private int type;//是否具有菜单权限：1是 ，0否
    
    public  Object clone() throws CloneNotSupportedException{
    	return super.clone();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MenuNew [id=" + id + ", message=" + message + ", name=" + name
				+ ", icon=" + icon + ", target=" + target + ", url=" + url
				+ ", type=" + type + "]";
	}
}