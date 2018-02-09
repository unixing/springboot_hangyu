package org.ldd.ssm.hangyu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.query.ChatDto;


public interface ChatMapper {

	Long getEmployeeIdByName(@Param("name") String fromName);

	void insertChatText(Chat chat);

	List<Long> employees(@Param("userName") String userName);

	List<Chat> employeesChat(@Param("fromName") Long fromName, @Param("toName") Long toName);
	
	
	/** wxm begin**/
	//聊天记录
	List<Chat> findChatRecord(ChatDto dto);
	
	int getChatRecordCount(ChatDto dto);
	//修改记录
	List<Chat> findModifyRecord(ChatDto dto);
	
	int getModifyRecordCount(ChatDto dto);
	//太美信息
	Map<String,String> getCompanyInfo();
	//需求信息
	Demand getDemandDtail(Long id);
	//获取响应方案详情
	Demand getResponseDtail(ChatDto dto);
	
	Map<String,String> getChatObject(ChatDto dto);
	
	int updateState(ChatDto dto);
	
	int updateSystemState(ChatDto dto);
	
	
	List<ChatDto> findChatObject(ChatDto dto);
	
	//未读消息条数
	int getNoReadChatRecordCount(ChatDto dto);
	
	List<Chat> findSystemMessage(ChatDto dto);
	
	int getSystemMessageCount(ChatDto dto);
	
	int getNoReadSystemRecordCount(ChatDto dto);
	/** wxm end**/
	
	//获取某一条需求的未读消息总数
	int getUnreadMesseageCountByEmployeeAndDemandId(@Param("employeeId") Long employeeId, @Param("demandId") Long demandId);
	
	int insertChatSelective(Chat chat);
	
	//太美委托托管列表未读消息条数
	@Select("select count(id) from chat where toName_id = #{employeeId,jdbcType=BIGINT} and demand_id=#{demandId,jdbcType=BIGINT} and textType = '0' and state = '1'")
	int getNoReadChatRecordCountForTameiForWeituoAndTuoguan(@Param("employeeId") Long employeeId, @Param("demandId") Long demandId);
	
	//获取我的发布列表的需求未读消息总数
	int selectUnReadMessageCountForMyReleaseDemandList(@Param("employeeId") Long employeeId);
	
}
