package org.ldd.ssm.crm.query;

import org.springframework.util.StringUtils;
public class BaseQuery {
	
//	public BaseQuery(){
//		// 在初始化时，自动获取当前登陆用户信息
//		Employee user = UserContext.getUser();
//		if(user!=null && user.getId()!=null){
//			this.currentUserId = user.getId();
//		}
//	}
//	
	private String searchKey;//关键字
	
	// 分页
	private Integer page=1;// 当前页码
	private Integer rows=10;//  每页长度
	
	// 权限
	private Long currentUserId;
	private boolean managerPermission = false;

	public void setManagerPermission(boolean managerPermission) {
		this.managerPermission = managerPermission;
	}
	
	public boolean isManagerPermission() {
		return managerPermission;
	}
	
	public Integer getStart(){// 起始位置
		return (page-1)*rows;
	}
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {// 每页长度
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSearchKey() {
		if(StringUtils.hasLength(searchKey)){
			return "%"+searchKey+"%";
		}
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}


	public Long getCurrentUserId() {
		return currentUserId;
	}

	@Override
	public String toString() {
		return "BaseQuery [searchKey=" + searchKey + ", page=" + page
				+ ", rows=" + rows + "]";
	}
	
	
}
