package org.ldd.ssm.crm.domain;
/**
 * 基础数据之小数位实体类
 * @Title:Digitt 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-20 下午4:31:19
 */
public class Digitt{	
	private String id ;//id
	private int digit;//保留多少位小数
	private String expression;	//小数对应表达式
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the digit
	 */
	public int getDigit() {
		return digit;
	}
	/**
	 * @param digit the digit to set
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Digitt [id=" + id + ", digit=" + digit + ", expression="
				+ expression + "]";
	}
	
	
}
