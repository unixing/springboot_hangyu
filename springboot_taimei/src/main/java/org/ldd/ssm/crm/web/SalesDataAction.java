package org.ldd.ssm.crm.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.TicketInfo;
import org.ldd.ssm.crm.domain.TravellerTicketInfo;
import org.ldd.ssm.crm.query.SalesDateQuery;
import org.ldd.ssm.crm.query.TicketInfoCompare;
import org.ldd.ssm.crm.service.ISalesDataService;
import org.ldd.ssm.crm.utils.CompareExcelView;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.TravellerTicketInfoExcelExample;
import org.ldd.ssm.crm.utils.TravellerTicketInfoExcelView;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 销售数据
 * @author wxm
 *
 * @date 2017-3-7
 */

@Controller
@RequestMapping("/SalesData")
public class SalesDataAction {
	
	@Resource
	private ISalesDataService salesDataService;
	
	@MyMethodNote(comment="销售数据:2")
	@MethodNote(comment="销售数据:8")
	@RequestMapping("/accountCheck")
	public String getAccountCheck() {
		return "newHtml/account_check";
	}

	@RequestMapping("/findFltRteCdList")
	@MethodNote(comment="销售数据:8")
	@ResponseBody
	public List<String> findFltRteCdList(SalesDateQuery dto,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		/*dto.setEndTime("2017-03-01");
		dto.setStartTime("2016-01-01");
		dto.setFlightNum("GS7620");*/
		List<String> list=new ArrayList<String>(); 
		try {
			list=salesDataService.findFltRteCdList(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
	}
	
	@RequestMapping("/findSalesData")
	@MethodNote(comment="销售数据:8")
	@ResponseBody
	public Map<String,Object> findSalesData(SalesDateQuery dto,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map=new HashMap<String, Object>();
		/*dto.setEndTime("2017-03-01");
		dto.setStartTime("2016-01-01");
		dto.setFlightNum("GS7620");
		dto.setFltRteCd("HAKLZOXIY");*/
		try {
			if(!dto.getFlightNum().isEmpty()&&!dto.getFltRteCd().isEmpty()){
				Map<String, Object> findSalesData = salesDataService.findSalesData(dto);
				if(!findSalesData.isEmpty()){
					map.put("success",true);
					map.put("data",findSalesData);
				}else{
					map.put("success",false);
					map.put("data",null);
				}
			}else{
				map.put("success",false);
				map.put("data",null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 排序 分页
	 * @param dto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTicketInfo")
	@MethodNote(comment="销售数据:8")
	@ResponseBody
	public Map<String,Object> findTicketInfo(SalesDateQuery dto,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map=new HashMap<String, Object>();
		/*dto.setEndTime("2017-03-01");
		dto.setStartTime("2016-01-01");
		dto.setFlightNum("GS7620");
		dto.setFltRteCd("HAKLZOXIY");
		dto.setLeg("HAK-LZO");
		dto.setOrder("flightDate");
		dto.setSort("desc");*/
		try {
			if(!dto.getFlightNum().isEmpty()&&!dto.getFltRteCd().isEmpty()){
				List<TravellerTicketInfo> findSalesData = salesDataService.findTicketInfo(dto);
				if(!findSalesData.isEmpty()){
					map.put("success",true);
					map.put("data",findSalesData);
				}else{
					map.put("success",false);
					map.put("data",null);
				}
			}else{
				map.put("success",false);
				map.put("data",null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/exportSalesdata")
	@MethodNote(comment="销售数据:8")
	public ModelAndView exportExcel(SalesDateQuery dto,HttpServletRequest request,HttpServletResponse response) {		
		response.setHeader("Access-Control-Allow-Origin", "*");
		/*dto.setEndTime("2017-03-01");
		dto.setStartTime("2016-01-01");
		dto.setFlightNum("GS7620");
		dto.setFltRteCd("HAKLZOXIY");*/
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		list=salesDataService.findSegmentList(dto);
		if(list.isEmpty()){
			return null;
		}
		map.put("titles", list);
		Map<String,List<List<TravellerTicketInfo>>> model = new HashMap<String,List<List<TravellerTicketInfo>>>();
		List<List<TravellerTicketInfo>> travellerTicketInfo = new ArrayList<List<TravellerTicketInfo>>();
		for (int i = 0; i < list.size(); i++) {
			dto.setLeg(list.get(i));
			List<TravellerTicketInfo> findData = salesDataService.findTravellerTicketExc(dto);
			travellerTicketInfo.add(findData);
		}
		model.put("list", travellerTicketInfo);
		return new ModelAndView(new TravellerTicketInfoExcelView(map), model);
	}
	/**
	 * 返回最近有数据的时间
	 */
	@RequestMapping("/getCurrentTime")
	@MethodNote(comment="销售数据:8")
	@ResponseBody
	public Map<String,Object> getCurrentTime(SalesDateQuery dto,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map=new HashMap<String, Object>();
		//dto.setFltRteCd("PEKLZO");
		try {
			if(!dto.getFlightNum().isEmpty()&&!dto.getFltRteCd().isEmpty()){
				String date= salesDataService.getCurrentTime(dto);
				if(!TextUtil.isEmpty(date)){
					map.put("success",true);
					map.put("data",date);
				}else{
					map.put("success",false);
					map.put("data",null);
				}
			}else{
				map.put("success",false);
				map.put("data",null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 上传excel文件后对比  
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/importExcel")
	@ResponseBody
	public Map<String,Object> importExcel(@RequestParam(value = "file", required = false)MultipartFile file,HttpServletRequest request,SalesDateQuery dto){
	//public String importExcel(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,SalesDateQuery dto){
		Map<String,Object> map=new HashMap<>();
		Map<String,Object> mapExcel=new HashMap<>();
		HttpSession session = UserContext.getRequest().getSession();
		session.setAttribute("downloadExcel",null);
//		dto.setFlightNum("HU7305");
//		dto.setEndTime("2017-05-22");
//		dto.setStartTime("2017-05-20");
//		dto.setFltRteCd("CANWDSLHW");
     	try {
        	if(dto.getFlightNum().isEmpty()||dto.getEndTime().isEmpty()||dto.getStartTime().isEmpty()||dto.getFltRteCd().isEmpty()){
        		map.put("msg","传入参数不可为空");
            	map.put("success", false);
            	return map;
        	}
	        String [] pfix= file.getOriginalFilename().split("\\.");
	        String suffix = pfix[pfix.length -1];
	        List<TicketInfo> listUpload=new ArrayList<>();
            Set<String> flightNums=new HashSet<String>();
            List<String> dates=new LinkedList<>();
        	mapExcel=PoiExcel(file.getInputStream(),suffix);
        	if(!(boolean) mapExcel.get("success")){
        		return mapExcel;
        	}else{
        		 listUpload=(List<TicketInfo>) mapExcel.get("listUpload");
        		 if(listUpload.isEmpty()){
 		        		map.put("msg","上传数据为空");
            		  	map.put("success", false);
            		  	return map;
            		  	
 		        }
                 flightNums=(Set<String>) mapExcel.get("flightNums");
                 dates=(List<String>) mapExcel.get("dates");
        	}
	        //删除上传文件 
       		Collections.sort(dates);
            String begin=dates.get(0);
            String end=dates.get(dates.size()-1);
            String msg="";
            boolean succ=true;
            //判断传入的航班号是否唯一 且与当前条件相等
            if((flightNums.size()==1&&!dto.getFlightNum().equals(listUpload.get(0).getFlightNum()))){
            	msg+="上传票面航班号与该查询航班号不一致；";
            	succ=false;
            }
            if(flightNums.size()>1){
            	msg+="上传文件只能包含该查询航班号；";
            	succ=false;
            }
            if(!(begin.compareTo(dto.getStartTime())>=0&&end.compareTo(dto.getEndTime())<=0)){
            	msg+="上传文件中时间范围需在查询的时间范围内；";
            	succ=false;
            }
            if(!succ){
            	map.put("msg", msg);
            	map.put("success", false);
            	return map;
            }
            //将系统的查询时间换位 上传的时间范围
            dto.setEndTime(end);
            dto.setStartTime(begin);
            List<TicketInfo> listSystem=salesDataService.findTicketInfoList(dto);
            //将上传的list 传到service和系统数据对比
            List<TicketInfoCompare> list=salesDataService.findTicketInfoCompareList(listUpload,listSystem);
            session.setAttribute("downloadExcel",list);
            map.put("data",list);
        	map.put("msg","对比成功");
            map.put("success", true);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
				file.getInputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return map;
        //return "/index/test1";
	    }
	
	 
	/**
	 * 下载上传模板
	 * @return
	 */
	@RequestMapping("/excelExample")
	@ResponseBody
	public ModelAndView excelExample(){
		Map<String,List<TicketInfo>> model = new HashMap<String,List<TicketInfo>>();
		TicketInfo info=new TicketInfo("2016-02-01","HU7306","WDS-CAN","82611965432",new BigDecimal(1000),"1");
		List<TicketInfo> list=new ArrayList<>();
		list.add(info);
		model.put("list", list);
		return new ModelAndView(new TravellerTicketInfoExcelExample(null),model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportCompare")
	@ResponseBody
	public ModelAndView exportCompare(HttpServletRequest request,HttpServletResponse response) {		
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String, List<TicketInfoCompare>> model = new HashMap<String,List<TicketInfoCompare>>();
		List<TicketInfoCompare> list=(List<TicketInfoCompare>) UserContext.getRequest().getSession().getAttribute("downloadExcel");
		model.put("list", list);
		return new ModelAndView(new CompareExcelView(map), model);
	}

	public Map<String,Object> PoiExcel(InputStream str,String suffix) throws IOException{
		Map<String,Object> map=new HashMap<>();
		List<TicketInfo> listUpload=new ArrayList<>();
        Set<String> flightNums=new HashSet<String>();
        List<String> dates=new LinkedList<>();
        try {
        	Workbook xwb=null;
        	if("xlsx".equals(suffix)){
        		xwb = new XSSFWorkbook(str);  //利用poi读取excel文件流
        	}else if("xls".equals(suffix)){
        		xwb = new HSSFWorkbook(str);  //利用poi读取excel文件流
        	}
            Sheet st =xwb.getSheetAt(0);  //读取sheet的第一个工作表
            int rows=st.getLastRowNum();//总行数 比行数小1
            if(rows==0){
            	map.put("msg","上传票面信息为空");
       		  	map.put("success", false);
       		  	return map;
            }
            int cols;//总列数
           //判断第一行与模板一样不
            Row rowTitle=st.getRow(0);//读取某一行数据
            List<String> listName=new LinkedList<>();
            listName.add("航班日期");
            listName.add("航班号");
            listName.add("航段");
            listName.add("票号");
            listName.add("票款");
            if(rowTitle!=null){
                //获取行中所有列数据
            	List<String> listNameUpload=new LinkedList<>();
                cols=rowTitle.getLastCellNum();//列数 比最后一列列标大1
                for(int j=0;j<cols;j++){
                    Cell cell=rowTitle.getCell(j);
                    if(cell!=null){
                    	//判断单元格的数据类型
                    	listNameUpload.add(cell.getStringCellValue());
                    }
                }
                for(int j=0;j<cols;j++){
                	   if(!listName.get(j).equals(listNameUpload.get(j))){
                		  map.put("msg","导入excel模板错误");
                		  map.put("success", false);
                		 return map;
                	   }
                   }
            }
            /***从第二行开始读取数据*****/
            for(int i=1;i<=rows;i++){
                Row row=st.getRow(i);//读取某一行数据
                if(row!=null){
                    //获取行中所有列数据
                    cols=row.getLastCellNum();
                    for(int j=0;j<cols;j++){
                    	//判断单元格的数据类型
                    	String flightDate=exchange(row.getCell(j++));//默认最左边编号也算一列 所以这里得j++
 	                    String flightNum=exchange(row.getCell(j++));
 	                    String leg=exchange(row.getCell(j++));
 	                    String ticketNum=exchange(row.getCell(j++));
 	                    BigDecimal ticketPri=new BigDecimal(exchange(row.getCell(j++))).setScale(2);
 	                    flightNums.add(flightNum);
 	                    dates.add(flightDate);
 	                    listUpload.add(new TicketInfo(flightDate,flightNum,leg,ticketNum,ticketPri,"0"));
                    }
                }
            }
            map.put("flightNums", flightNums);
            map.put("dates", dates);
            map.put("listUpload",listUpload);
            map.put("success",true);
		} catch (NullPointerException e) {
	         map.put("msg","解析文件出错，请检查上传格式是否为为excel2007/Excel2003");
	         map.put("success",false);
			 e.printStackTrace();
			 return map;
		}catch (Exception e) {
			 e.printStackTrace();
		}finally{
			str.close();
		}
		return map;
	}
	
	public String exchange(Object celltemp){
		Cell cell = (Cell) celltemp;
		String cellValue="";
		switch (cell.getCellType()){  
        case Cell.CELL_TYPE_NUMERIC: //数字  
        	DecimalFormat df = new DecimalFormat("0");//使用DecimalFormat类对科学计数法格式的数字进行格式化
        	 cellValue = df.format(cell.getNumericCellValue());
            break;  
        case Cell.CELL_TYPE_STRING: //字符串  
            cellValue = String.valueOf(cell.getStringCellValue());  
            break;  
        case Cell.CELL_TYPE_BOOLEAN: //Boolean  
            cellValue = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case Cell.CELL_TYPE_FORMULA: //公式  
            cellValue = String.valueOf(cell.getCellFormula());  
            break;  
        case Cell.CELL_TYPE_BLANK: //空值   
            cellValue = "";  
            break;  
        case Cell.CELL_TYPE_ERROR: //故障  
            cellValue = "非法字符";  
            break;  
        default:  
            cellValue = "未知类型";  
            break;  
   	 	}  
		return cellValue;
	}
}
