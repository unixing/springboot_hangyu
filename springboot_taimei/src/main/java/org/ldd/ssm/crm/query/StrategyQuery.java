package org.ldd.ssm.crm.query;

public class StrategyQuery extends BaseQuery{
	private Long user_id;
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
	@Override
	public void setRows(Integer rows){
		this.pageSize = rows;
	}
	@Override
	public void setPage(Integer page){
		this.currentPage = page;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "StrategyQuery [currentPage=" + currentPage + ", pageSize="
				+ pageSize + "]";
	}
}
