package org.ldd.ssm.crm.query;
/**
 * 高级查询加分页的封装对象
 */
public class PermissionQuery {
	private String name;//数据字典目录名称
	private String resource;//数据字典目录编号
	//分页用的字段
	private Integer currentPage = 1;//当前页数
	private Integer pageSize = 5;//当前页数量
	public Integer getBeginIndex() {
		return (currentPage - 1)* pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRows(Integer rows){
		this.pageSize = rows;
	}
	public void setPage(Integer page){
		this.currentPage = page;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	@Override
	public String toString() {
		return "PermissionQuery [name=" + name + ", resource=" + resource
				+ ", currentPage=" + currentPage + ", pageSize=" + pageSize
				+ "]";
	}
	
}