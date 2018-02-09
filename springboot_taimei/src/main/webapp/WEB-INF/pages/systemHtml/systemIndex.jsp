<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<template id="verifyPassword">
    <div class="cPBoxItm-title">
        <div v-text="title"></div>
        <span class="c-recovery" @click="shut"></span>
    </div>
    <div class="setPublic-tipBox">
        <span class="setPublic-tipCont">请输入当前密码</span>
            <span class="setPublic-tipWarning">
            <span class="setPublic-tipIcon"></span>
            <span class="setPublic-tipErr">密码不能为空</span>
        </span>
    </div>
    <div class="setPublic-input">
        <input type="password" class="setPublic-inputCont" v-model="pas">
    </div>
    <there v-if="open" @my-code="get"></there>
    <div class="setPublic-next">
        <div class="setPublic-nextOne nice-btn-lBlue" tag="" @click="verifyPas">下一步</div>
        <div class="setPublic-nextClear" @click="shut">取消</div>
    </div>
</template>
<template id="thereErr">
    <div class="setPublic-thereErr" style="display: block;">
        <input type="text" class="setPublic-thereErrInput" maxlength="4" v-model="code" @input="send">
        <canvas id="twoImg" width="100" height="40" class="twoImg"></canvas>
    </div>
</template>
<template id='replace'>  <!-- 手机更换绑定 -->
    <div class="cPBoxItm-title">
        <div v-text="title"></div>
        <span class="c-recovery" @click="shut"></span>
    </div>
    <div class="setPublic-tipBox">
        <span class="setPublic-tipCont"></span>
        <span class="setPublic-tipWarning">
            <span class="setPublic-tipIcon"></span>
            <span class="setPublic-tipErr">密码不能为空</span>
        </span>
    </div>
    <div class="setPublic-phone">
        <div class="setPublic-phoneInput">
            <span class="setPublic-phoneInputTip">输入新手机号</span>
            <div class="setPublic-phoneInputBox">
                <input class="setPublic-inputCont" v-model="pNumber"/>
            </div>
        </div>
        <div class="setPublic-phoneInput">
            <div class="setPublic-phoneInputBox">
                <input class="setPublic-inputCont" v-model="pCode"/>
            </div>
            <div class="setPublic-inputSend" @click="obtainCode" v-text="timing"></div>
        </div>
        <div class="setPublic-next">
            <div class="setPublic-nextOne" @click="uploadPone">确定</div>
            <div class="setPublic-nextClear" @click="shut">取消 </div>
        </div>
    </div>
</template>
<template id='setOsucc'>  <!-- 操作成功 -->
    <div class="cPBoxItm-title">
        <div></div>
        <span class="c-recovery" @click="shut">&#xe64e;</span>
    </div>
    <div class="changeSuccessful">
        <img src="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/images/setSucc.png" alt="修改成功">
        <p>手机更换成功</p>
        <p>您可以使用新的手机号登录系统</p>
    </div>
</template>
<template id='untieSuccessful'>  <!-- 解绑成功 -->
    <div class="cPBoxItm-title">
        <div></div>
        <span class="c-recovery" @click="shut">&#xe64e;</span>
    </div>
    <div class="changeSuccessful">
        <img src="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/images/setSucc.png" alt="修改成功">
        <p>解绑成功</p>
    </div>
</template>
<template id='emailSuccessful'>  <!-- 邮箱解绑成功 -->
    <div class="cPBoxItm-title">
        <div></div>
            <span class="c-recovery" @click="shut">&#xe64e;</span>
        </div>
        <div class="changeSuccessful">
            <img src="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/images/setSucc.png" alt="修改成功">
        <p>发送成功</p>
        <p>请登录邮箱确认绑定</p>
    </div>
</template>
<template id='phoneSuccessful'>  <!-- 邮箱解绑成功 -->
    <div class="cPBoxItm-title">
        <div></div>
            <span class="c-recovery" @click="shut">&#xe64e;</span>
        </div>
        <div class="changeSuccessful">
            <img src="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/images/setSucc.png" alt="修改成功">
        <p>手机号绑定成功</p>
    </div>
</template>
<template id="emailVerify">
    <div class="cPBoxItm-title">
        <div v-text="title"></div>
        <span class="c-recovery" @click="shut"></span>
    </div>
    <div class="setPublic-tipBox">
        <span class="setPublic-tipCont"></span>
        <span class="setPublic-tipWarning">
            <span class="setPublic-tipIcon"></span>
            <span class="setPublic-tipErr">邮箱格式错误！</span>
        </span>
    </div>
    <div class="setPublic-phone">
        <div class="setPublic-phoneInput">
            <span class="setPublic-phoneInputTip">输入邮箱</span>
            <div class="setPublic-phoneInputBox">
                <input class="setPublic-inputCont" v-model="eNumber"/>
            </div>
        </div>
        <div class="setPublic-next">
            <div class="setPublic-nextOne" @click="emailRequest">确定</div>
            <div class="setPublic-nextClear" @click="shut">取消 </div>
        </div>
    </div>
</template>
<div class="pla-set-interface">
	<div class="pla-set-interface-head">
		<div class="pla-set-interface-head-hPortrait">
			<div class="bg-img">
				<div>&#xe65a;
                    <input type='file'  id='imgBg' name="myfile"  title="修改背景图片"/>
                </div>
			</div>
			<div class="user-nick">${user_in_session.usrNm}</div>
			<div class="ph-img">
				<input type='file' id='phBg' name="myfile" title="修改头像图片"/>
			</div>
		</div>
		<ul class="pla-set-interface-head-set">
			<li class="personal-center unified">
				<ul>
					<li class="li-mark1"><span>&#xe670;</span>个人中心</li>
					<li class="barMove userCenter"><span></span>个人信息</li>
                    <li class="barMove changePas"><span></span>修改密码</li>
				</ul>
			</li>
			<li class="unified">
				<ul>
					<li class="li-mark1"><span>&#xe66d;</span>消息</li>
				</ul>
			</li>
			<li class="unified">
				<ul>
					<li class="li-mark1"><span>&#xe66e;</span>数据设置</li>
                    <li><span></span>报表参数设置</li>
                    <li class="barMove fltSet"><span></span>关注航班配置</li>
				</ul>
			</li>
			<li class="unified">
				<ul>
					<li class="li-mark1"><span>&#xe671;</span>权限设置</li>
					<li><span></span>部门管理</li>
					<li><span></span>成员管理</li>
					<li><span></span>角色管理</li>
					<li><span></span>角色关系管理</li>
					<li class="configuration barMove etermInfo"><span></span>采集号配置信息</li>
					<li><span></span>航线航班管理</li>
				</ul>
			</li>
		</ul>
	</div>
	<div class="pla-set-interface-box">
       <!--个人中心-->
       <div class="personal-center">
           <div class="personal-center-setBth">&#xe65a;</div>
           <div class="personal-center-title">
               <h3>个人中心</h3>
           </div>
           <div class="personal-information">
               <div class="personal-information-title">
                   <h5>个人信息：</h5>
               </div>
               <div class="personal-information-box">
                   <div class="personal-information-item">
                       <div class="personal-information-itemBind1">手机：</div>
                       <div class="personal-information-itemBind2"><input type="text" id="phone"></div>
                       <div class="personal-information-itemBind3 nice-btn-dBlue" @click="open('phone')">更换</div>
                   </div>
                   <div class="personal-information-item2">
                       <div class="personal-information-itemBind1">用户名：</div>
                       <div class="personal-information-item2Bind"><input type="text" id="usrNm"></div>
                       <div class="personal-information-must" title="必填项">*</div>
                   </div>
                   <div class="personal-information-item2">
                       <div class="personal-information-itemBind1">姓名：</div>
                       <div class="personal-information-item2Bind"><input type="text" id="compellation"></div>
                       <div class="personal-information-must" title="必填项">*</div>
                   </div>
                   <div class="personal-information-item2">
                       <div class="personal-information-itemBind1">部门：</div>
                       <div class="personal-information-item2Bind" id="dptList"></div>
                   </div>
                   <div class="personal-information-item2">
                       <div class="personal-information-itemBind1">职务：</div>
                       <div  class="personal-information-item2Bind"><input type="text" id="duty"></div>
                   </div>
                   <div class="personal-information-item">
                       <div class="personal-information-itemBind1">邮箱：</div>
                       <div class="personal-information-itemBind2"><input type="text" id="email"></div>
                       <div class="personal-information-itemBind3 bindemail nice-btn-dBlue" @click="open('email')"></div>
                   </div>
                   <!-- <div class="personal-information-item">
                       <div class="personal-information-itemBind1">QQ：</div>
                       <div class="personal-information-itemBind2"><input type="text" readonly="readonly" id="qqNbr"></div>
                       <div class="personal-information-itemBind3">更换</div>
                   </div>
                   <div class="personal-information-item">
                       <div class="personal-information-itemBind1">微信：</div>
                       <div class="personal-information-itemBind2"><input type="text" readonly="readonly" id="weixin"></div>
                       <div class="personal-information-itemBind3">更换</div>
                   </div> -->
                   <div class="personal-information-select">
                       <div class="determine-tip"><span class="plales-warning-icon">&#xe64a;</span>请填写完整的信息！</div>
                       <div class="personal-information-determine information-qe nice-btn-lBlue">确认</div>
                       <div class="personal-information-cancel information-of cancel-btn-ordinary">取消 </div>
                   </div>
               </div>
           </div>
           <div class="personal-center-cPassword">
               <div class="personal-center-cPBth">
                   <h5>修改密码：</h5>
               </div>
               <div class="personal-center-cPCont">
                   <p>为了您的账号安全，请定期修改密码</p>
                   <div class="cPBoxItm-pas nice-btn-dBlue">修改密码</div>
               </div>
               <div class="personal-center-cPBox">
                   <div class="personal-center-cPBoxItm">
                       <div class="cPBoxItm-title">
                           <div>修改密码</div>
                           <span class="c-recovery">&#xe64e;</span>
                       </div>
                       <div class="plales-t"><span class="plales-t-tip">请输入当前密码</span><span class='plales-warning'><span class='plales-warning-icon'>&#xe64a;</span><span class='plales-warning-cont'>密码不能为空</span></span></div>
                       <div class="plales-input">
                           <input type="password" class="cPBox-pas-input">
                       </div>
                       <div class="plales-input-verificatio">
                           <input type="text" class="verificatio-input" maxlength="4">
                           <canvas id="verificatio" width="100" height="40"></canvas>
                       </div>
                       <div class="newPassword">
                           <div class="newPassword-input">
                               <span>新密码</span>
                               <div><input class="Password-input" minlength="6"  maxlength="20" type="password" placeholder="支持6-20位字母加数字非特殊字符"></div>
                           </div>
                           <div class="newPassword-input">
                               <span>确认新密码</span>
                               <div><input class="Password-input" minlength="6"  maxlength="20" type="password"><span class="Password-icon">&#xe649;</span></div>
                           </div>
                       </div>
                       <div class="personal-information-select">
                           <div class="personal-information-determine cPBox-pas nice-btn-lBlue" tag="">下一步</div>
                           <div class="personal-information-cancel  c-recovery cancel-btn-ordinary">取消 </div>
                       </div>
                       <div class="passwordSuccessful">
                           <img src="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/images/setSucc.png" alt="密码修改成功">
                           <p>修改密码成功</p>
                           <p><span id="insevr">3</span>秒后自动跳转至登录页面</p>
                       </div>
                   </div>
               </div>
           </div>
	       <div class="mobile-bind">
                <component :is="templates"></component>
	       </div>
       </div>
       <!--数据设置-->
           <div class="data-set">
               <div class="data-set-title">
                   <h3>数据设置</h3>
               </div>
               <div class="data-set-concent">
                   <div class="data-set-focus">
                       <div>关注航班配置：</div>
                   </div>
                   <div class="data-set-r">
                       <div class="data-set-rT">
                           <div class="rTmin">本航季在飞航班</div>
                           <div class="rHandle">
                               <div id="setUp"><span class="rHandle-icon1">&#xe661;</span>关注航班<span class="rHandle-icon2">&#xe65a;</span></div>
                               <div id="setClera"><input type="checkbox" id="attentionAll">关注全部航班</div>
                           </div>
                       </div>
                       <div id="flightC">
                           
                       </div>
                       <div id="paging">
                           <div id="fixed-page">
                               
                           </div>
                           <div id="move-page">
                               
                           </div>
                       </div>
                       <div id="page-select">
                           <div id="page-select-t">确定</div>
                           <div id="page-select-f">取消</div>
                       </div>
                   </div>
               </div>
           </div>
           <!--采集号配置-->
			<div class="collect-configuration">
				<h3 class="collect-configuration-title">采集号配置信息：</h3>
				<div class="addBox">
					<span>&#xe60d;</span>
					<div>新增</div>
				</div>
			</div>
       </div>
    <div class="modification">
<input id="id" hidden="hidden">
<div class="modification-clear">&#xe64e;</div>
<div class="modification-class modification-values">
	<div class="modification-Key">账号类型：</div>
	<div class="modification-value">
		<span class="modification-rotate">&#xe61e;</span>
		<ul class="modification-list">
			<li>中航信eTerm</li>
		</ul>
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">用户名：</div>
	<div class="modification-value">
		<input class="modification-input p-user" name="eTermName"
			type="text" placeholder="第三方系统配置号" />
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">密码：</div>
	<div class="modification-value">
		<input class="modification-input p-pas" name="password"
			type="password" placeholder="第三方系统配置号密码" /> <span
			class="modification-pas">&#xe667;</span>
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">工作号：</div>
	<div class="modification-value">
		<input class="modification-input p-jobn" name="wordName"
			type="text" placeholder="第三方系统二级账号" />
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">工作号密码：</div>
	<div class="modification-value">
		<input class="modification-input p-jobp" name="wordPassword"
			type="password" placeholder="第三方系统二级账号密码" /> <span
			class="modification-pas">&#xe667;</span>
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">级别：</div>
	<div class="modification-value">
		<input class="modification-input p-level" name="lvl" type="text"
			onkeyup="key()" />
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">eTermIP：</div>
	<div class="modification-value">
		<input class="modification-input p-etermIp" name="ip" type="text"
			onkeyup="key()" placeholder="eTermIP服务器地址" />
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">eTerm端口：</div>
	<div class="modification-value modification-port">
		<input class="modification-input p-etermPort" name="port"
			type="text" onkeyup="key()" placeholder="端口号" />
	</div>
	<div class="secure-transport">
		<input class="p-Secure" type="checkbox" name="security"
			checked="checked" /><span>安全传输</span>
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">si指令：</div>
	<div class="modification-value">
		<input class="modification-input p-si" name="si" type="text" />
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">自动采集时间：</div>
	<div class="acquisition-time">
		<input type="text" name="auto" onkeyup="key()" /> <input
			type="text" name="auto1" onkeyup="key()" /> <span>：</span> <input
			type="text" name="auto2" onkeyup="key()" /> <input type="text"
			name="auto3" onkeyup="key()" /> <span class="modK">&#xe672;</span>
	</div>
</div>
<div class="modification-class conflict-class-resolution">
	<div class="modification-Key">账号冲突处理：</div>
	<div class="conflict-resolution">
		<input class="p-conflict" type="text" name="avoidTime"
			onkeyup="key()" /> <span>分钟</span> <input class="p-conflictAvoid"
			type="radio" checked="checked" /> <span>冲突时自动避让</span> <span
			class="conR">&#xe672;</span>
	</div>
</div>
<div class="modification-class">
	<div class="modification-Key">深度采集：</div>
	<div class="epth-acquisition">
		<input type="radio" name="che" id="radio-state" value="1"
			onclick="iszhen()" /><span>是</span> <input type="radio" name="che"
			id="radio-jia" checked="checked" value="0" onclick="isjia()" /><span>否</span>
			<span class="epA">&#xe672;</span>
		</div>
	</div>
	<div class="modification-class collection-scope">
		<div class="modification-Key">采集范围：</div>
		<div class="collection-scope-box">
			<div>航班号</div>
			<div>航线</div>
			<span class="cosb">&#xe672;</span>
		</div>
	</div>
	<div class="modification-specia specia0">
		<div class="speciaList-tag"></div>
		<div class="modification-speciaList">
			<div>
				<div>
					<input type="text" class="speciaList-in1" name="flb_nbr1"
						onchange="keyflb(this)" disabled placeholder="AA0000">
				</div>
				<div>
					<input type="text" class="speciaList-in2" name="flt_rte_cd1"
						onchange="keyflt(this)" disabled placeholder="AAABBBCCC">
				</div>
			</div>
			<div>
				<div>
					<input type="text" class="speciaList-in1" name="flb_nbr2"
						onchange="keyflb(this)" disabled placeholder="AA0000">
				</div>
				<div>
					<input type="text" class="speciaList-in2" name="flt_rte_cd2"
						onchange="keyflt(this)" disabled placeholder="AAABBBCCC">
				</div>
			</div>
		</div>
	</div>
	<div class="modification-specia specia0">
		<div class="speciaList-tag"></div>
		<div class="modification-speciaList">
			<div>
				<div>
					<input type="text" class="speciaList-in1" name="flb_nbr3"
						onchange="keyflb(this)" disabled placeholder="AA0000">
				</div>
				<div>
					<input type="text" class="speciaList-in2" name="flt_rte_cd3"
						onchange="keyflt(this)" disabled placeholder="AAABBBCCC">
				</div>
			</div>
			<div>
				<div>
					<input type="text" class="speciaList-in1" name="flb_nbr4"
						onchange="keyflb(this)" disabled placeholder="AA0000">
				</div>
				<div>
					<input type="text" class="speciaList-in2" name="flt_rte_cd4"
						onchange="keyflt(this)" disabled placeholder="AAABBBCCC">
				</div>
			</div>
		</div>
	</div>
	<div class="modification-specia specia0">
		<div class="speciaList-tag"></div>
		<div class="modification-speciaList">
			<div>
				<div>
					<input type="text" class="speciaList-in1" name="flb_nbr5"
						onchange="keyflb(this)" disabled placeholder="AA0000">
				</div>
				<div>
					<input type="text" class="speciaList-in2" name="flt_rte_cd5"
						onchange="keyflt(this)" disabled placeholder="AAABBBCCC">
				</div>
			</div>
			<div>
				<div>
					<input type="text" class="speciaList-in1" name="flb_nbr6"
						onchange="keyflb(this)" disabled placeholder="AA0000">
				</div>
				<div>
					<input type="text" class="speciaList-in2" name="fltnP_rte_cd6"
						onchange="keyflt(this)" disabled placeholder="AAABBBCCC">
				</div>
			</div>
		</div>
	</div>
	<div class="modification-sub">保存</div>
</div>
    <div class="pla-set-interface-load" ><img src="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/images/loda.gif" alt=""></div>
	<!--测试框-->
	<div class="tipBox">
		<div class="tipBox-cont"></div>
		<span class="tipBox-clear">&#xe612;</span>
	</div>
</div>
<div class="pla-set-clear">&#xe64e;</div>
<div class="modification-pro"></div>
