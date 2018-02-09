package org.ldd.ssm.crm.domain;
/**
 * 可以求得平均值,用于计算承认人数大于平均值得班次
 * @author Taimei
 *
 */
public class SunAndCount {
	private int sum;//当月乘客人数
	private int count;//当月飞行次数
	
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "SunAndCount [sum=" + sum + ", count=" + count + "]";
	}
	
}
