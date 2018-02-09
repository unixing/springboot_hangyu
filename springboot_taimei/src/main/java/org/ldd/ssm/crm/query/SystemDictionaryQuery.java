package org.ldd.ssm.crm.query;
/**
 * 高级查询加分页的封装对象
 */
public class SystemDictionaryQuery {
	private String name;//数据字典目录名称
	private String sn;//数据字典目录编号
	private Long state = -1L;//数据字典目录状态
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
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public void setRows(Integer rows){
		this.pageSize = rows;
	}
	public void setPage(Integer page){
		this.currentPage = page;
	}
	@Override
	public String toString() {
		return "SystemDictionaryQuery [name=" + name + ", sn=" + sn
				+ ", state=" + state + ", currentPage=" + currentPage
				+ ", pageSize=" + pageSize + "]";
	}
}