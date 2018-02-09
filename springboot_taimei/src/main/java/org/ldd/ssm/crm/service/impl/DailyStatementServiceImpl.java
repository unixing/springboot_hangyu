package org.ldd.ssm.crm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.ldd.ssm.crm.domain.DailyStatement;
import org.ldd.ssm.crm.domain.DailyStatementTotal;
import org.ldd.ssm.crm.mapper.DailyStatementMapper;
import org.ldd.ssm.crm.query.PageResult;
import org.ldd.ssm.crm.query.SystemLogQuery;
import org.ldd.ssm.crm.service.IDailyStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 日报表的服务类
 */
@Service
public class DailyStatementServiceImpl implements IDailyStatementService {
	@Autowired
	private DailyStatementMapper dailyStatementMapper;
//	@Autowired
//	private TotalMapper totalMapper;
	/**
	 * 拿到所以得数据
	 */
	public PageResult<DailyStatement> list(SystemLogQuery searchKey) {
		PageResult<DailyStatement> pageResult = new PageResult<DailyStatement>();
		List<DailyStatement> list = dailyStatementMapper.list(searchKey);
		DailyStatement dailyStatementTotal = new DailyStatement();
		for (DailyStatement dailyStatement : list) {
			if("折扣".equals(dailyStatement.getLeg())){
				continue;
			}
			//舱位坐人数
			dailyStatementTotal.setF(dailyStatementTotal.getF()+dailyStatement.getF());
			dailyStatementTotal.setY(dailyStatementTotal.getY()+dailyStatement.getY());
			dailyStatementTotal.setB(dailyStatementTotal.getB()+dailyStatement.getB());
			dailyStatementTotal.setH(dailyStatementTotal.getH()+dailyStatement.getH());
			dailyStatementTotal.setK(dailyStatementTotal.getK()+dailyStatement.getK());
			dailyStatementTotal.setL(dailyStatementTotal.getL()+dailyStatement.getL());
			dailyStatementTotal.setM(dailyStatementTotal.getM()+dailyStatement.getM());
			dailyStatementTotal.setQ(dailyStatementTotal.getQ()+dailyStatement.getQ());
			dailyStatementTotal.setX(dailyStatementTotal.getX()+dailyStatement.getX());
			dailyStatementTotal.setU(dailyStatementTotal.getU()+dailyStatement.getU());
			dailyStatementTotal.setE(dailyStatementTotal.getE()+dailyStatement.getE());
			dailyStatementTotal.setT(dailyStatementTotal.getT()+dailyStatement.getT());
			dailyStatementTotal.setZ(dailyStatementTotal.getZ()+dailyStatement.getZ());
			dailyStatementTotal.setG(dailyStatementTotal.getG()+dailyStatement.getG());
			dailyStatementTotal.setO(dailyStatementTotal.getO()+dailyStatement.getO());
			dailyStatementTotal.setS(dailyStatementTotal.getS()+dailyStatement.getS());
			dailyStatementTotal.setV(dailyStatementTotal.getV()+dailyStatement.getV());
			dailyStatementTotal.setyElse(dailyStatementTotal.getyElse()+dailyStatement.getyElse());
			//散团人数合计
			dailyStatementTotal.setCombinedGroup(dailyStatementTotal.getCombinedGroup()+dailyStatement.getCombinedGroup());
			//散客总人数
			dailyStatementTotal.setTotalNumber(dailyStatementTotal.getTotalNumber()+dailyStatement.getCombinedGroup());
			//散客总收入
			dailyStatementTotal.setIndividualIncome(dailyStatementTotal.getIndividualIncome().add(dailyStatement.getIndividualIncome()));
			//团队总收入
			dailyStatementTotal.setTeamTotalRevenue(dailyStatementTotal.getTeamTotalRevenue().add(dailyStatement.getTeamTotalRevenue()));
			//平均  折扣
			BigDecimal add = dailyStatementTotal.getIndividualIncome().add(dailyStatementTotal.getTeamTotalRevenue());
			Integer combinedGroup = dailyStatementTotal.getCombinedGroup();
			BigDecimal bigDecimal = new BigDecimal(combinedGroup);
			BigDecimal getyPrice = dailyStatement.getyPrice();
			dailyStatementTotal.setAverageDiscount(dailyStatementTotal.getCombinedGroup()>0?add.divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).divide(getyPrice,2,BigDecimal.ROUND_HALF_UP):new BigDecimal(0));
			dailyStatementTotal.setScatteredRegimentTotalRevenue(dailyStatementTotal.getIndividualIncome().add(dailyStatementTotal.getTeamTotalRevenue()));
			dailyStatementTotal.setLeg("合计");
			
		}
		list.add(dailyStatementTotal);
		pageResult.setRows(list);
		pageResult.setTotal(dailyStatementMapper.count(searchKey));
		return pageResult;
	}
	/**
	 * 将webservice得到的数据写入数据库
	 */
	public void save(DailyStatement dailyStatement,DailyStatementTotal dailyStatementTotal) {
		
		//写入基本数据
		dailyStatementMapper.save(dailyStatement);
		//写入合计数据
		dailyStatementMapper.saveCount(dailyStatementTotal);
	}
	public List<DailyStatementTotal> findByGroupBy1(SystemLogQuery searchKey) {
		return dailyStatementMapper.findByGroupBy1(searchKey);
	}

}
