var idTmr;
function  getExplorer() {
    var explorer = window.navigator.userAgent ;
    //ie
    if (explorer.indexOf("MSIE") >= 0) {
        return 'ie';
    }
    //firefox
    else if (explorer.indexOf("Firefox") >= 0) {
        return 'Firefox';
    }
    //Chrome
    else if(explorer.indexOf("Chrome") >= 0){
        return 'Chrome';
    }
    //Opera
    else if(explorer.indexOf("Opera") >= 0){
        return 'Opera';
    }
    //Safari
    else if(explorer.indexOf("Safari") >= 0){
        return 'Safari';
    }
}
function method1(tableid) {//整个表格拷贝到EXCEL中
    if(getExplorer()=='ie')
    {
        var curTbl = document.getElementById(tableid);
        var oXL = new ActiveXObject("Excel.Application");

        //创建AX对象excel
        var oWB = oXL.Workbooks.Add();
        //获取workbook对象
        var xlsheet = oWB.Worksheets(1);
        //激活当前sheet
        var sel = document.body.createTextRange();
        sel.moveToElementText(curTbl);
        //把表格中的内容移到TextRange中
        sel.select;
        //全选TextRange中内容
        sel.execCommand("Copy");
        //复制TextRange中内容
        xlsheet.Paste();
        //粘贴到活动的EXCEL中
        oXL.Visible = true;
        //设置excel可见属性

        try {
            var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
        } catch (e) {
            print("Nested catch caught " + e);
        } finally {
            oWB.SaveAs(fname);

            oWB.Close(savechanges = false);
            //xls.visible = false;
            oXL.Quit();
            oXL = null;
            //结束excel进程，退出完成
            //window.setInterval("Cleanup();",1);
            idTmr = window.setInterval("Cleanup();", 1);
        }
    }
    else
    {
        tableToExcel(tableid)
    }
}
function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
}
var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
        base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
        format = function(s, c) {
            return s.replace(/{(\w+)}/g,
                function(m, p) { return c[p]; }) }
    return function(table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        window.location.href = uri + base64(format(template, ctx))
    }
})()
var pdlexd =function(name){
	if(name=="-"){
		return 0;
	}else{
		return name;
	}
} 
var pdlex = function (name,lent) {
	if(name == "Infinity"  || name == undefined || name == null || name == NaN){
		return '-';
	}else if(!isNaN(Number(name))){
		return Number(name).toFixed(lent);
	}else{
		return name;
	}
};
 var spae_tablex=function (data) {	//表格头部
        var len=0,node="<thead><tr>";
        for(var key in data.common){
           if(data.common[key].length>len){
               len=data.common[key].length;
           }
            node+="<th column='' line='"+key+"' class='table_px table_px0 table_px1' onclick='table_px(this,1)'>"+key+"<span column='' line='"+key+"' class='_px_span'><span>&#xe894;</span></br><span>&#xe895;</span></span></th>";
        }
        node+="</tr></thead><tbody>";

        for(var i=0;i<len;i++){
            node+="<tr>";
            for(var key in data.common){
            	//修改日期格式 ------------龙洪
                node+="<td column='"+i+"' line='"+key+"'>"+(data.common[key][i]!=undefined?data.common[key][i]:'')+"</td>";
            }
            node+="</tr>";
        }

        node+="</tbody>";
        return node;
};
 var spae_table=function (data) {	//表格body
        var len=0,node="<thead><tr>";
        for(var key in data.common){
           if(data.common[key].length>len){
               len=data.common[key].length;
           }
            node+="<th column='' line='"+key+"' class='table_px table_px0 table_px1'>"+pdlex(key,0)+"<span column='' line='"+key+"' class='_px_span' onclick='table_px(this,1)'><span>&#xe894;</span></br><span>&#xe895;</span></span></th>";
        }
        node+="</tr></thead><tbody>";

        for(var i=0;i<len;i++){
            node+="<tr>";
            for(var key in data.common){
                node+="<td column='"+i+"' line='"+key+"'>"+(pdlex(data.common[key][i]),0)+"</td>";
            }
            node+="</tr>";
        }

        node+="</tbody>";
        return node;
};
var spae_table1=function (data) {
    var len=0,node="<thead><tr>";
    for(var key in data.common){
       if(data.common[key].length>len){
           len=data.common[key].length;
       }
       if(key!="航班号"){
           node += "<th column='' line='"+key+"' class='table_px table_px0 table_px1'>"+pdlex(key,0)+"<span column='' line='"+pdlex(key,0)+"' class='_px_span' onclick='table_px(this,1)'><span>&#xe894;</span></br><span>&#xe895;</span></span></th>";
       }else{
           node += "<th column='' line='"+key+"' class='table_px table_px0 table_px1'>"+pdlex(key,0)+"</th>";
       }
    }
    node+="</tr></thead><tbody>";


    for(var i=0;i<len;i++){
        node += "<tr>";
        for(var key in data.common){
            node+="<td column='"+i+"' line='"+key+"'>"+(data.common[key][i].toString().indexOf(".") != -1 ? pdlex(data.common[key][i],2) : pdlex(data.common[key][i],0))+"</td>";
        }
        node+="</tr>";
    }
    // if(len == 0){
    // 	node += "<tr style='background-color: rgb(84, 118, 154)'><td colspan='"+(Object.keys(data.common).length)+"'>无数据</td></tr>";
    // }else{
    //
    // };
    
    

    node+="</tbody>";
    return node;
};
var spae_table2=function (data) {
    var len=0,node="<thead><tr>";
    for(var key in data.common){
       if(data.common[key].length>len){
           len=data.common[key].length;
       }
       node+="<th column='' line='"+key+"' class='table_px table_px0 table_px1'>"+pdlex(key,0)+"</th>";
    }
    node+="</tr></thead><tbody>";

    for(var i=0;i<len;i++){
        node+="<tr>";
        for(var key in data.common){
            node+="<td column="+i+">"+(data.common[key][i].toString().indexOf(".") != -1 ? pdlex(data.common[key][i],2) : pdlex(data.common[key][i],0))+"</td>";
// 删除td.line属性
//            node+="<td column='"+i+"' line='"+key+"'>"+(data.common[key][i].toString().indexOf(".") != -1 ? pdlex(data.common[key][i],2) : pdlex(data.common[key][i],0))+"</td>";
        }
        node+="</tr>";
    }

    node+="</tbody>";
    return node;
};

var spae_table4=function (data) {
    var len=0,node="<thead><tr>";
    for(var key in data.common){
       if(data.common[key].length>len){
           len=data.common[key].length;
       }
       node+="<th column='' line='"+key+"' class='table_px table_px0 table_px1'>"+pdlex(key,0)+"</th>";
    }
    node+="</tr></thead><tbody>";

    for(var i=0;i<len;i++){
        node+="<tr>";
        for(var key in data.common){
            node+="<td column='"+i+"' line='"+key+"'>"+(pdlex(data.common[key][i],0))+"</td>";
        }
        node+="</tr>";
    }
    
    node+="</tbody>";
    return node;
};
var spae_table3=function (data) {
    var len=0,node="<thead><tr>";
    node+="<th column='' line='日期' class='table_px table_px0 table_px1'>日期</th>";
    for(var key in data[0]){
    	if(key!="time"){
    	       node+="<th column='' line='"+key+"' class='table_px table_px0 table_px1'>"+data[0][key].name+"</th>";
    	}
    }
    node+="</tr></thead><tbody>";
    for(var i=0;i<data.length;i++){
    	node+="<tr>";
        var lcd= data[i].bc.data.length;
        node += "<td rowspan='" +(data[i].mbzw.data.length>1?lcd+1:1)+ "' column='0' line='日期' class='table_px table_px0 table_px1'>"+pdlex(data[i].time,0)+"</td>";
    	for(var j=0;j<data[i].bc.data.length;j++){
    		if(j!=0){
    			node+="<tr>";
    		}
    		node+="<td  column='' line='航段' class='table_px table_px0 table_px1'>"+pdlex(data[i].hd.data[j],0)+"</td>";
    		node+="<td  column='' line='班次' class='table_px table_px0 table_px1'>"+pdlex(data[i].bc.data[j],0)+"</td>";
    		node+="<td  column='' line='每班座位（人）' class='table_px table_px0 table_px1'>"+pdlex(data[i].mbzw.data[j],0)+"</td>";
    		node+="<td  column='' line='散团人数（人）' class='table_px table_px0 table_px1'>"+pdlex(data[i].strs.data[j],0)+"</td>";
    		node+="<td  column='' line='团队人数（人）' class='table_px table_px0 table_px1'>"+pdlex(data[i].tdrs.data[j],0)+"</td>";
    		node+="<td  column='' line='散团总营收（元）' class='table_px table_px0 table_px1'>"+pdlex(data[i].stzys.data[j],0)+"</td>";
    		node+="<td  column='' line='团队总营收（元）' class='table_px table_px0 table_px1'>"+pdlex(data[i].tdzys.data[j],0)+"</td>";
    		node+="<td  column='' line='平均折扣（%）' class='table_px table_px0 table_px1'>"+pdlex(data[i].pjzk.data[j],2)+"</td>";
    		node+="<td  column='' line='散团折扣（%）' class='table_px table_px0 table_px1'>"+pdlex(data[i].skzk.data[j],2)+"</td>";
    		node+="<td  column='' line='团队折扣（%）' class='table_px table_px0 table_px1'>"+pdlex(data[i].tdzk.data[j],2)+"</td>";
    		node+="<td  column='' line='平均客座率（%）' class='table_px table_px0 table_px1'>"+pdlex(data[i].pjkzl.data[j],2)+"</td>";
    		node+="</tr>";
    	}
    	if(data[i].hd.data.length>1){
            node+="<tr>";
            node+="<td  column='' line='航段' class='table_px table_px0 table_px1'>合计</td>";
            node+="<td  column='' line='班次' class='table_px table_px0 table_px1'>"+pdlex(data[i].bc.all,0)+"</td>";
            node+="<td  column='' line='每班座位（人）' class='table_px table_px0 table_px1'>"+pdlex(data[i].mbzw.all,0)+"</td>";
            node+="<td  column='' line='散团人数（人）' class='table_px table_px0 table_px1'>"+pdlex(data[i].strs.all,0)+"</td>";
            node+="<td  column='' line='团队人数（人）' class='table_px table_px0 table_px1'>"+pdlex(data[i].tdrs.all,0)+"</td>";
            node+="<td  column='' line='散团总营收（元）' class='table_px table_px0 table_px1'>"+pdlex(data[i].stzys.all,0)+"</td>";
            node+="<td  column='' line='团队总营收（元）' class='table_px table_px0 table_px1'>"+pdlex(data[i].tdzys.all,0)+"</td>";
            node+="<td  column='' line='平均折扣（%）' class='table_px table_px0 table_px1'>"+pdlex(data[i].pjzk.all,2)+"</td>";
            node+="<td  column='' line='散团折扣（%）' class='table_px table_px0 table_px1'>"+pdlex(data[i].skzk.all,2)+"</td>";
            node+="<td  column='' line='团队折扣（%）' class='table_px table_px0 table_px1'>"+pdlex(data[i].tdzk.all,2)+"</td>";
            node+="<td  column='' line='平均客座率（%）' class='table_px table_px0 table_px1'>"+pdlex(data[i].pjkzl.all,2)+"</td>";
            node+="</tr>";
        }    	
    }
    node+="</tbody>";
    return node;
};
var spae_table5=function (table_data) {
     var objs={};
     for (var key in table_data.success.newmap.data_person) {
    	 var timess=table_data.success.datee.split("-")[0]+"-"+(table_data.success.datee.split("-")[1].length==2?table_data.success.datee.split("-")[1]:"0"+table_data.success.datee.split("-")[1])+"-"+(table_data.success.datee.split("-")[2].length==2?table_data.success.datee.split("-")[2]:"0"+table_data.success.datee.split("-")[2]);
         if(objs[table_data.success.newmap.data_person[key].flt_Nbr]!=undefined){
             objs[table_data.success.newmap.data_person[key].flt_Nbr].push({
               hd:key,
               grp_Nbr:table_data.success.newmap.data_person[key].grp_Nbr,
               pgs_Per_Cls:table_data.success.newmap.data_person[key].pgs_Per_Cls,
               skrs:(parseFloat(table_data.success.newmap.data_person[key].pgs_Per_Cls)-parseFloat(table_data.success.newmap.data_person[key].grp_Nbr)),
               avg_Dct:table_data.success.newmap.data_person[key].avg_Dct,
               grp_Dct:table_data.success.newmap.data_person[key].grp_Dct,
               wak_tol_Nbr:table_data.success.newmap.data_person[key].wak_tol_Nbr,
               grp_Ine:table_data.success.newmap.data_person[key].grp_Ine,
               zglsr:(table_data.success.newmap.data_person[key].flt_Nbr==table_data.success.goNum?table_data.success.newmap.everyList["0"].dataMap.date[timess].go.set_Ktr_Ine:table_data.success.newmap.everyList["0"].dataMap.date[timess].back.set_Ktr_Ine)
             })
         }else{
           objs[table_data.success.newmap.data_person[key].flt_Nbr]=[
             {
               hd:key,
               grp_Nbr:table_data.success.newmap.data_person[key].grp_Nbr,
               pgs_Per_Cls:table_data.success.newmap.data_person[key].pgs_Per_Cls,
               skrs:(parseFloat(table_data.success.newmap.data_person[key].pgs_Per_Cls)-parseFloat(table_data.success.newmap.data_person[key].grp_Nbr)),
               avg_Dct:table_data.success.newmap.data_person[key].avg_Dct,
               grp_Dct:table_data.success.newmap.data_person[key].grp_Dct,
               wak_tol_Nbr:table_data.success.newmap.data_person[key].wak_tol_Nbr,
               grp_Ine:table_data.success.newmap.data_person[key].grp_Ine,
               zglsr:(table_data.success.newmap.data_person[key].flt_Nbr==table_data.success.goNum?table_data.success.newmap.everyList["0"].dataMap.date[timess].go.set_Ktr_Ine:table_data.success.newmap.everyList["0"].dataMap.date[timess].back.set_Ktr_Ine)
             }
           ]
         }
     }

     var name=["航段","团（人）","散团人数（人）","散客人数（人）","平均折扣（%）","团队平均折扣（%）","散客总营收（元）", "团队总营收（元）","座公里收入（元）"];
     var len=0,len1=0,vallen=0,node="<thead><tr>";
     for(var i=0;i<name.length;i++){
         node+="<th column='' line='"+name[i]+"' class='table_px table_px0 table_px1'>"+name[i]+"</th>";
     }
     node+="</tr></thead><tbody>";
     var hjData={
         grp_Nbr:0,
         pgs_Per_Cls:0,
         skrs:0,
         avg_Dct:0,
         grp_Dct: 0,
         wak_tol_Nbr:0,
         grp_Ine:0,
         zglsr:0
     };
     for(var key in objs){
           len1++;
           for(var i=0;i<objs[key].length;i++){
               len++;
               if(parseFloat(objs[key][i].grp_Dct)>0)vallen++;
               hjData.grp_Nbr+=parseFloat(objs[key][i].grp_Nbr);
               hjData.pgs_Per_Cls+=parseFloat(objs[key][i].pgs_Per_Cls);
               hjData.skrs+=parseFloat(objs[key][i].skrs);
               hjData.avg_Dct+=parseFloat(objs[key][i].avg_Dct);
               hjData.grp_Dct+=parseFloat(objs[key][i].grp_Dct);
               hjData.wak_tol_Nbr+=parseFloat(objs[key][i].wak_tol_Nbr);
               hjData.grp_Ine+=parseFloat(objs[key][i].grp_Ine);
       

               node+="<tr>";
               node+="<td column='"+key+"' line='航段''>"+pdlex(objs[key][i].hd,0)+"</td>";
               node+="<td column='"+key+"' line='团（人）'>"+pdlex(objs[key][i].grp_Nbr,0)+"</td>";
               node+="<td column='"+key+"' line='散团人数（人）'>"+pdlex(objs[key][i].pgs_Per_Cls,0)+"</td>";
               node+="<td column='"+key+"' line='散客人数（人）'>"+pdlex(objs[key][i].skrs,0)+"</td>";
               node+="<td column='"+key+"' line='平均折扣（%）'>"+pdlex(objs[key][i].avg_Dct,2)+"</td>";
               node+="<td column='"+key+"' line='平均折扣（%）'>"+pdlex(objs[key][i].grp_Dct,2)+"</td>";
               node+="<td column='"+key+"' line='散客总营收（元）'>"+pdlex(objs[key][i].wak_tol_Nbr,0)+"</td>";
               node+="<td column='"+key+"' line='团队总营收（元）'>"+pdlex(objs[key][i].grp_Ine,0)+"</td>";
               if(i==0){
            	   hjData.zglsr+=parseFloat(objs[key][i].zglsr);
                   node+="<td rowspan='"+objs[key].length+"' column='"+key+"' line='座公里收入（元）'>"+pdlex(objs[key][i].zglsr,2)+"</td>";
               }
               node+="</tr>";
           }
     }
     vallen = vallen ? vallen: 1;
     hjData.avg_Dct=hjData.avg_Dct/len;
     hjData.grp_Dct=hjData.grp_Dct/vallen;
     hjData.zglsr=hjData.zglsr/len1;
     node+="<td column='"+0+"' line='航段''>合计</td>";
     node+="<td column='"+0+"' line='团（人）'>"+pdlex(hjData.grp_Nbr,2)+"</td>";
     node+="<td column='"+0+"' line='散团人数（人）'>"+pdlex(hjData.pgs_Per_Cls,2)+"</td>";
     node+="<td column='"+0+"' line='散客人数（人）'>"+pdlex(hjData.skrs,2)+"</td>";
     node+="<td column='"+0+"' line='平均折扣（%）'>"+pdlex(hjData.avg_Dct,2)+"</td>";
     node+="<td column='"+0+"' line='平均折扣（%）'>"+pdlex(hjData.grp_Dct,2)+"</td>";
     node+="<td column='"+0+"' line='散客总营收（元）'>"+pdlex(hjData.wak_tol_Nbr,2)+"</td>";
     node+="<td column='"+0+"' line='团队总营收（元）'>"+pdlex(hjData.grp_Ine,2)+"</td>";
     node+="<td column='"+0+"' line='座公里收入（元）'>"+pdlex(hjData.zglsr,2)+"</td>";
     node+="</tbody>";

    var stzys=parseFloat(hjData.wak_tol_Nbr.toFixed(2))+parseFloat(hjData.grp_Ine.toFixed(2));
    $("#_space_stzys").text(pdlex(stzys,2));
    setTimeout(function(){
        // 移至：salesReports-day.js 1866
        // $("#_space_ssys").text(zjssj == "--" ? "-" : pdlex(pdlex(stzys/pdlex(zjssj,10)),2))
    },500);     
    return node;
};

var spae_table6=function (data) {
    var len=0,node="<thead><tr>";
    for(var key in data){
       if(data[key].length>len){
           len=data[key].length;
       }
       node+="<th column='' line='"+key+"' class='table_px table_px0 table_px1'>"+key+"</th>";
    }
    node+="</tr></thead><tbody>";
    for(var i=0;i<len;i++){
        node+="<tr>";
        for(var key in data){
        	if(key!="航段"){
                node+="<td column='"+i+"' line='"+key+"'>"+(pdlex(data[key].data[i],0))+"</td>";
        	}else{
        		node+="<td column='"+i+"' line='"+key+"'>"+(pdlex(data[key][i],0))+"</td>";
        	}
        }
        node+="</tr>";
    }
    
    node+="</tbody>";
    return node;
};