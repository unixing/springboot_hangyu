package org.ldd.ssm.crm.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldd.ssm.crm.domain.AddFltNbr;
import org.ldd.ssm.crm.domain.Conflictresolution;
import org.ldd.ssm.crm.domain.ETermInfo;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.Instructions;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.ETermMapper;
import org.ldd.ssm.crm.query.AirLineObject;
import org.ldd.ssm.crm.query.AirLineQuery;
import org.ldd.ssm.crm.query.ETermQuery;
import org.ldd.ssm.crm.query.EtermInfoObject;
import org.ldd.ssm.crm.service.IETermService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.ldd.ssm.crm.utils.EtermUtils;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.ETermAccessManage;
import org.tempuri.IeTermAccessManage;
@Service
public class ETermServiceImpl implements IETermService {
	private static final List<Z_Airdata> DOWQuery = new ArrayList<Z_Airdata>();
	 /** 拿到接口对象 */
	private static final IeTermAccessManage accessManage = new ETermAccessManage().getBasicHttpBindingIeTermAccessManage();
//	private static final IeTermAccessManage accessManage = null;
	private Date date = new Date();//这个时间很重要,多处使用的都是这一个时间
	private String format = EtermUtils.getNextDay(date,-1);//获得昨天的日期
	@Autowired
	private ETermMapper eTermMapper;
	@Override
	public void addeTerm(ETermQuery query) {
		try {
			String password = query.getPassword();
			String wordPassword = query.getWordPassword();
//			String decode = EtermUtils.decode(password);//解密
//			String wordDecode = EtermUtils.decode(wordPassword);//解密
			query.setPassword(password);
			query.setWordPassword(wordPassword);
			if(TextUtil.isEmpty(query.getId())){
				addNewEterm(query);
			}else{
				updateEterm(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateEterm(ETermQuery query) {
		String id = query.getId();
		eTermMapper.etermAirInfo_delete(id);
		eTermMapper.etermAuto_delete(id);
		eTermMapper.etermConf_delete(id);
		eTermMapper.eterm_delete(id);
		
		addNewEterm(query);
	}

	private void addNewEterm(ETermQuery query) {
		//eterm基础账号信息
		Instructions instructions = new Instructions();
		instructions.setIsc_Tem(UserContext.getCompanyName());
		instructions.setEtm_Usr(query.geteTermName());
		instructions.setEtm_Psw(query.getPassword());//加密
		instructions.setEtm_Pot(Integer.valueOf(query.getPort()));
		instructions.setEtm_IP(query.getIp());
		instructions.setOff_ID(query.getWordName());
		instructions.setOff_Pwd(query.getWordPassword());//加密
		instructions.setAcc_Lvl(query.getLvl());
		instructions.setEmployee_id(UserContext.getUser().getId()+"");
		instructions.setIs_Four(query.getSecurity()=="0"?true:false);
		instructions.setItia(UserContext.getcompanyItia());
		instructions.setAic_Mal(1);
		instructions.setSi(query.getSi());
		instructions.setState(0);
		instructions.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		try {
			instructions.setAic_Tie(new SimpleDateFormat("HH:mm").parse(query.getAuto()));
		} catch (ParseException e) {
			Z_Airdata z = new Z_Airdata();
			z.setDpt_AirPt_Cd("102");
			DOWQuery.add(z);
			UserContext.setData("102", DOWQuery);
		}
		instructions.setScope(Integer.valueOf(query.getChe()));
		
		eTermMapper.addeTerm(instructions);
		//自动采集时间
		eTermMapper.addeTermAuto(instructions);
		//冲突等待时间
		Conflictresolution conflictresolution = new Conflictresolution();
		conflictresolution.setCct_Ron("4");
		conflictresolution.setDte_Aic_Rrt(Integer.valueOf(query.getAvoidTime()));
		conflictresolution.setId(instructions.getId());
		eTermMapper.addeTermCt(conflictresolution);
		//eterm采集航班信息
		
		List<AddFltNbr> addFltNbrs = EtermUtils.getAddFltNbrs(query);
		for (AddFltNbr addFltNbr : addFltNbrs) {
			addFltNbr.setEterm_account_id(instructions.getId());
			if(!TextUtil.isEmpty(addFltNbr.getFlt_Nbr())&&!TextUtil.isEmpty(addFltNbr.getAir_line())){
				eTermMapper.addETermFlt_Rte_Cd(addFltNbr);
			}
			if(!TextUtil.isEmpty(addFltNbr.getFlt_one())){
				eTermMapper.addFlt_One(addFltNbr);
			}
			if(!TextUtil.isEmpty(addFltNbr.getFlt_two())){
				eTermMapper.addFlt_two(addFltNbr);
			}
			if(!TextUtil.isEmpty(addFltNbr.getFlt_three())){
				eTermMapper.addFlt_three(addFltNbr);
			}
		}
	}
	
	@Override
	public List<EtermInfoObject> getAirportOnLineData() {
		List<EtermInfoObject> objects = new ArrayList<EtermInfoObject>();
		Long id = UserContext.getUser().getId();
		List<Instructions> lists = eTermMapper.getFindEtermByUserId(id);
		for (Instructions instructions : lists) {
			EtermInfoObject etermInfoObject = new EtermInfoObject();
			ETermInfo eTermInfo = new ETermInfo();
			eTermInfo.setId(instructions.getId()+"");
			eTermInfo.setUser(instructions.getEtm_Usr());//用户名                   
			eTermInfo.setPas(instructions.getEtm_Psw());//密码                     
//			eTermInfo.setPas(EtermUtils.eccode(instructions.getEtm_Psw()));//密码                     
			eTermInfo.setJobn(instructions.getOff_ID());//工作号                   
//			eTermInfo.setJobp(EtermUtils.eccode(instructions.getOff_Pwd()));//工作号密码                 
			eTermInfo.setJobp(instructions.getOff_Pwd());//工作号密码                 
			eTermInfo.setLevel(instructions.getAcc_Lvl());//级别                   
			eTermInfo.setEtermIp(instructions.getEtm_IP());//eTermIP            
			eTermInfo.setSecure(instructions.isIs_Four()?"0":"1");//安全传输                
			eTermInfo.setEtermPort(instructions.getEtm_Pot()+"");//端口               
			eTermInfo.setSi(instructions.getSi());//SI指令                    
			String format = new SimpleDateFormat("HH:mm").format(instructions.getAic_Tie());
			eTermInfo.setAcquisitionT(format);//自动采集时间        
			String arrTime[] = {format.substring(0, 1),format.substring(1, 2),format.substring(3, 4),format.substring(4, 5)};
			eTermInfo.setBroken(arrTime);//自动采集时间数组          
			eTermInfo.setConflict(instructions.getDte_Aic_Rrt());//冲突等待时间            
			eTermInfo.setConflictAvoid(instructions.getCct_Ron()+"");//冲突自动避让       
			eTermInfo.setDepth(instructions.getScope()+"");//是否深度采集               
			eTermInfo.setLastTime(instructions.getDate());//最后更新时间            
			eTermInfo.setState(instructions.getState()+"");//测试状态                 
			List<AddFltNbr> addFltNbr = eTermMapper.getFindAirlineByEtermId(instructions.getId());
			eTermInfo.setAddFltNbrs(addFltNbr);
			etermInfoObject.seteTermUserName(eTermInfo.getUser());
			etermInfoObject.setInfo(eTermInfo);
			objects.add(etermInfoObject);
		}
		
		return objects;
	}

	@Override
	public AjaxResult eterm_delete(String id) {
		Employee user = UserContext.getUser();
		String user_id = eTermMapper.getFindUserByEtermId(id);
		if(user.getId()==Long.valueOf(user_id)){
			eTermMapper.etermAirInfo_delete(id);
			eTermMapper.etermAuto_delete(id);
			eTermMapper.etermConf_delete(id);
			eTermMapper.eterm_delete(id);
			return new AjaxResult();
		}else{
			return new AjaxResult("错误的操作", 103);
		}
	}
	/**
	 * 99:代表si登录异常
	 * 98:FDL指令无权限
	 * 97:MLB指令无权限
	 * 96:RT指令无权限
	 * 95:DETR指令无权限
	 * 94:当前航班号下fd 指令无权限
	 * 93:当前航班号下flk p指令无权限
	 * 92:当前采集所有航班,无PNR信息
	 * 91:当前采集所有航班,无DETR信息
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public AjaxResult eterm_test(String id) throws UnsupportedEncodingException {
		int rt = 0;//状态, 如果一个rt编码都提取不到, 那么就用0表示, 否则是1
		int detr = 0;//状态, 如果一个detr编码都提取不到, 那么就用0表示, 否则是1
		Instructions instructions = eTermMapper.getFindInstrByEterm_id(id);
		try {
			
		
		Map<String,String> si = si(instructions, instructions.getSi());
		//1表示登录错误
		if("1".equals(si.get("key"))){
			String str = si.get("val");
			return new AjaxResult(str, 99);
		}else if("2".equals(si.get("key"))){
			//测试FDL
			List<Instructions> ITIA = eTermMapper.getIATAAll(instructions);//获得FDL指令
			for (Instructions instructions2 : ITIA) {
				String order = "fdl " + format + "/"+ instructions2.getIsc_Ist() + "/99";
				byte[] sendOrder = send(instructions, order);
				//获得解析后的数据
				String string = new String(sendOrder, "gbk");
				if("AUTHORITY".equals(string.replaceAll("[^A-Z]", ""))){
					return new AjaxResult("FDL指令无权限", 98);
				}
				if(string.length()>200){
					break;
				}
			}
			/**
			 * 如果FDL指令没问题,则说明账号正常
			 */
			updateEtermInfo(1,instructions);
			
			//测试MLB
			if(instructions.getScope()==1){
				List<Instructions> instr = eTermMapper.getAll(instructions);//获得当前账号的MLB查询指令
				List<String> mlbs = new ArrayList<String>();
				for (Instructions instructions2 : instr) {
					String order = "ml:b/" + instructions2.getIsc_Ist() + "/" + format;
					byte[] sendOrder = send(instructions, order);
					String string = new String(sendOrder, "gbk");
					if("AUTHORITY".equals(string.replaceAll("[^A-Z]", ""))){
						return new AjaxResult("当前航班号下MLB指令无权限:"+instructions2.getIsc_Ist(), 97);
					}
					if(string.length()>200){
						mlbs.add(string);
					}
					//测试rt指令
					//拿到pnr编码
					String rt_string = "";
					Set<String> pars = pars(string);
					for (String string2 : pars) {
						byte[] rt_sendOrder = send(instructions2, string2);
						rt_string = new String(rt_sendOrder, "gbk");
						if("AUTHORITY".equals(rt_string.replaceAll("[^A-Z]", ""))){
							return new AjaxResult("当前航班号下rt指令无权限:"+instructions2.getIsc_Ist(), 96);
						}
						if(rt_string.length()>100){
							rt = 1;
							break;
						}
					}
					//测试detr指令
					//拿到票号
					Set<String> parsdetr = parsdetr(rt_string);
					for (String string2 : parsdetr) {
						
						byte[] detr_sendOrder = send(instructions, string2);
						String detr_string = new String(detr_sendOrder, "gbk");
						if("AUTHORITY".equals(detr_string.replaceAll("[^A-Z]", ""))){
							return new AjaxResult("当前航班号下detr指令无权限:"+instructions2.getIsc_Ist(), 95);
						}
						if(detr_string.length()>100){
							detr = 1;
							break;
						}
					}
					//测试fd指令
					AirLineQuery airLineQuery = new AirLineQuery();
					airLineQuery.setFlt_Nbr(instructions2.getIsc_Ist());
					List<AirLineObject> airLineObjects = eTermMapper.getAirPt(airLineQuery);
					for (AirLineObject airLineObject : airLineObjects) {
						String fd_order = "fd "+airLineObject.getDpt_Arrv();
						byte[] fd_sendOrder = send(instructions, fd_order);
						//获得解析后的数据
						String fd_string = new String(fd_sendOrder, "gbk");
						if("AUTHORITY".equals(fd_string.replaceAll("[^A-Z]", ""))){
							return new AjaxResult("当前航班号下fd 指令无权限:"+instructions2.getIsc_Ist(), 94);
						}
						if(fd_string.length()>100){
							break;
						}
					}
					//测试klp
					for (AirLineObject airLineObject : airLineObjects) {
						String flp_order = "flp k/" + instructions2.getIsc_Ist() + "/" + format+"/"+EtermUtils.getNextDay(date,32)+"/"+airLineObject.getDpt_Arrv();
						byte[] fld_sendOrder = send(instructions, flp_order);
						//获得解析后的数据
						String fld_string = new String(fld_sendOrder, "gbk");
						if("AUTHORITY".equals(fld_string.replaceAll("[^A-Z]", ""))){
							return new AjaxResult("当前航班号下flk p指令无权限:"+instructions2.getIsc_Ist(), 93);
						}
						if(fld_string.length()>100){
							break;
						}
					}
					
				}
				if(rt==0){
					return new AjaxResult("当前采集所有航班,无PNR信息", 92);
				}
				if(detr == 0){
					return new AjaxResult("当前采集所有航班,无DETR信息", 91);
				}
			}else{
				return new AjaxResult("测试完成",50);
			}
			updateEtermInfo(1,instructions);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			send(instructions, "SO");
		}
		return new AjaxResult("测试完成");
	}
	private void updateEtermInfo(int i,Instructions instructions) {
		instructions.setState(1);
		String testDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		instructions.setDate(testDate);
		eTermMapper.UpdateEtermState(instructions);
		
	}

	public byte[] send(Instructions instructions,String order){
		byte[] sendOrder = accessManage.sendOrder(instructions.getEtm_IP(), instructions.getEtm_Pot(), instructions.getEtm_Usr(), instructions.getEtm_Psw(), order);
		
		return sendOrder;
	}
	//拿到票号
	public Set<String> parsdetr(String str) {
		//将字符串的,+,-等符号全部移除
		String replaceAll = str.toString().replaceAll(",|\\+|\\-", "");
		//创一个set对象
		Set<String> set = new HashSet<String>();
		//通过空格和/来分割
		String[] split = replaceAll.split("\\s|/");
		for (String string : split) {
			//if字符串的长度13并且为全数字,则表示票号信息
			if (string.length() == 13 && isDecimal(string)) {
				//将字符串添加进集合中
				set.add("detr:tn/" + string);
			}
		}
		//返回集合
		return set;
	}
	
	public static Boolean isDecimal(String orginal) {
		String replaceAll = orginal.replaceAll("\\s*|\t|\r|\n", "");
		replaceAll = orginal.replaceAll("-", "");
		try {
			if (replaceAll.matches("[0-9]+")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	//拿到pnr编码
	public Set<String> pars(String pNR_FileData) {
		// 此处拿到所有PNR编码,去除重复
		Set<String> Pnr_set = new HashSet<String>();
		//通过回车分割
		String[] split ; 
		split = pNR_FileData.toString().split("\\,");
		//遍历每一行数据
		for (String str : split) {
			//正则表达式,A-Z0-9 6位, 空格 A-Z 1位, 空格 A-Z0-9 1位
			Pattern Pnr_pattern = Pattern.compile("[A-Z0-9]{6}\\s[A-Z]{1}\\s*[A-Z0-9]{3}");
			Pattern Pnr_pattern2 = Pattern.compile("[A-Z0-9]{6}\\s[A-Z]{1}\\S[A-Z0-9]{3}");
			//匹配每次遍历的数据
			Matcher Pnr_matcher = Pnr_pattern.matcher(str);
			Matcher Pnr_matcher2 = Pnr_pattern2.matcher(str);
			//如果存在
			if (Pnr_matcher.find()) {
				//取到正则表达式符合的字符串,通过空格分隔,取到索引0的字符
				String Pnr_string = Pnr_matcher.group().split("\\ +")[0];
				//拼接成rt指令, 将指令存入set集合中, 通过set特性淘汰重复数据
				Pnr_set.add("rt:/" + Pnr_string);
			}
			if (Pnr_matcher2.find()) {
				//取到正则表达式符合的字符串,通过空格分隔,取到索引0的字符
				String Pnr_string = Pnr_matcher2.group().split("\\ +")[0];
				//拼接成rt指令, 将指令存入set集合中, 通过set特性淘汰重复数据
				Pnr_set.add("rt:/" + Pnr_string);
			}
		}
		//返回pnr编码指令集合
		return Pnr_set;
	}
	/**
	 * 异常检测方法
	 * @throws Exception 
	 */
	public void checByte(Instructions iTIA,String si,String siOrder,String format2) throws Exception{
		String replaceAll = si.replaceAll("[^A-Z]", "");
		byte[] send = {};
		if("MCSS: Session path to Host is down.".equals(si)){
			byte[] send2 = send(iTIA, "$$open tipb");
			si = new String(send2,"gbk");
			send = send(iTIA, siOrder);
			si = new String(send,"gbk");
		}
		if("SI".equals(replaceAll)){
			throw new Exception("SI");
		}
		if(si.contains("密码不正确")){
			throw new Exception("密码不正确");
		}
		if(si.contains("PASSWORD EXPIRED - PLEASE CHANGE PASSWORD FIRST")){
			iTIA.setIsc_Ist(si);
			throw new Exception("密码过期");
		}
		if(si.contains("错误:")){
			throw new Exception(si);
		}
		if(si.contains("登录失败")){
			throw new Exception(si);
		}
		if(si.contains("SIGNEDOUT")){
			throw new Exception("SI");
		}
		if(TextUtil.isEmpty(si)||si.length()<5){
			throw new Exception(siOrder);
		}
	}
	
	public Map<String,String> si(Instructions iTIA,String siOrder) {
		Map<String,String> maps =new  HashMap<String, String>();
		String format2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		byte[] send = send(iTIA, siOrder);
		String si = "";
		
		try {
			si = new String(send,"gbk");
			System.out.println("登录反馈信息:"+si);
			checByte(iTIA, si, siOrder, format2);
		} catch (Exception e1) {
			iTIA.setState(2);
			if(eTermMapper!=null){
				eTermMapper.updateEtermState(iTIA);
			}
			maps.put("key", "1");//1表示登录错误
			maps.put("val", TextUtil.isEmpty(si)?"登录无法得到反馈,请检查密码信息是否正常":si);
			return maps;
		}
		maps.put("key", "2");//2表示登录成功
		return maps;
	}

	@Override
	public List<Instructions> getEtermByName(String etermName,String companyName) {
		List<Instructions> list = null;
		try {
			list = eTermMapper.getEtermsByName(etermName,companyName);
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
}
