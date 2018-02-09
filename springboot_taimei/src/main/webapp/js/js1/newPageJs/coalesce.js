//1.0.1.16.11.24
//获取宽高等等
function infer(name){
    var infer=[];
    infer.push(parseFloat($(name).css("width").split("px")[0]));
    infer.push(parseFloat($(name).css("height").split("px")[0]));
    infer.push(parseFloat($(name).css("margin-top").split("px")[0]));
    infer.push(parseFloat($(name).css("left").split("px")[0]));
    infer.push(parseFloat($(name).css("top").split("px")[0]));
    return infer;
};
$(".err").on("mousewheel",function(e){e.stopPropagation()});  //屏蔽无数据滚轮事件
//机场信息数据
var nationalAirport=[
	{airportName:"松原查干湖机场",pinyin:"songyuanchaganhujichang",py:"sycghjc",code:"SOY",initial:"S",city:"松原",citycode:"SOY"},
	{airportName:"沧源佤山机场",pinyin:"cangyuanwashanjichang",py:"cywsjc",code:"CWJ",initial:"C",city:"临沧",citycode:"LNJ"},
	{airportName:"琼海博鳌机场",pinyin:"qionghaiboaojichang",py:"qhbajc",code:"BAR",initial:"Q",city:"琼海",citycode:"QHA"},
	{airportName:"扎兰屯成吉思汗机场",pinyin:"zhalantunchengjisihanjichang",py:"zltcjshjc",code:"NZL",initial:"C",city:"扎兰屯",citycode:"ZHL"},
	{airportName:"果洛玛沁机场",pinyin:"guoluomaqinjichang",py:"glmqjc",code:"GMQ",initial:"G",city:"果洛",citycode:""},
    {airportName:"阿坝红原机场",pinyin:"abahongyuanjichang",py:"abhyjc",code:"AHJ",initial:"A",city:"阿坝州",citycode:"ABA"},
    {airportName:"阿尔山伊尔施机场",pinyin:"aershanyiershijichang",py:"aesyes",code:"YIE",initial:"A",city:"阿尔山",citycode:""},
    {airportName:"阿克苏温宿机场",pinyin:"akesuwensujichang",py:"akswsjc",code:"AKU",initial:"A",city:"阿克苏",citycode:"AKU"},
    {airportName:"阿勒泰机场",pinyin:"aletaijichang",py:"altjc",code:"AAT",initial:"A",city:"阿勒泰",citycode:"AAT"},
    {airportName:"阿里昆莎机场",pinyin:"alikunshajichang",py:"alksjc",code:"NGQ",initial:"A",city:"阿里",citycode:""},
    {airportName:"安康富强机场",pinyin:"ankangfuqiangjichang",py:"akfqjc",code:"AKA",initial:"A",city:"安康",citycode:"AKA"},
    {airportName:"安庆天柱山机场",pinyin:"anqingtianzhushanjichang",py:"aqtzsjc",code:"AQG",initial:"A",city:"安庆",citycode:""},
    {airportName:"鞍山腾鳌机场",pinyin:"anshantengaojichang",py:"astajc",code:"AOG",initial:"A",city:"鞍山",citycode:"AOG"},
    {airportName:"安顺黄果树机场",pinyin:"anshunhuangguoshujichang",py:"ashgsjc",code:"AVA",initial:"A",city:"安顺",citycode:"ANS"},
    {airportName:"安阳北郊机场",pinyin:"anyangbeijiaojichang",py:"aybjjc",code:"AYN",initial:"A",city:"安阳",citycode:"AYN"},
    {airportName:"澳门国际机场",pinyin:"aomenguojijichang",py:"amgjjc",code:"MFM",initial:"A",city:"澳门",citycode:"MFM"},
    {airportName:"百色田阳机场",pinyin:"baisetianyangjichang",py:"bstyjc",code:"AEB",initial:"B",city:"百色",citycode:"BSI"},
    {airportName:"保山云瑞机场",pinyin:"baoshanyunruijichang",py:"bsyrjc",code:"BSD",initial:"B",city:"保山",citycode:"BSD"},
    {airportName:"包头二里半机场",pinyin:"baotouerlibanjichang",py:"btelbjc",code:"BAV",initial:"B",city:"包头",citycode:"BAV"},
    {airportName:"巴彦淖尔天吉泰机场",pinyin:"bayannaoertianjitaijichang",py:"byzetjtjc",code:"RLK",initial:"B",city:"巴彦淖尔",citycode:""},
    {airportName:"北海福成机场",pinyin:"beihaifuchengjichang",py:"bhfcjc",code:"BHY",initial:"B",city:"北海",citycode:"BHY"},
    {airportName:"北京南苑国际机场",pinyin:"beijingnanyuanguojijichang",py:"bjnygjjc",code:"NAY",initial:"B",city:"北京",citycode:"BJS"},
    {airportName:"北京首都国际机场",pinyin:"beijingshouduguojijichang",py:"bjsdgjjc",code:"PEK",initial:"B",city:"北京",citycode:"BJS"},
    {airportName:"毕节飞雄机场",pinyin:"bijiefeixiongjichang",py:"bjfxjc",code:"BFJ",initial:"B",city:"毕节",citycode:"BIJ"},
    {airportName:"博乐阿拉山口机场",pinyin:"bolealashankoujichang",py:"blalskjc",code:"BPL",initial:"B",city:"博乐",citycode:""},
    {airportName:"布尔津喀纳斯机场",pinyin:"buerjinkanasijichangjichang",py:"bejknsjc",code:"KJI",initial:"B",city:"布尔津",citycode:""},
    {airportName:"长白山机场",pinyin:"changbaishanjichang",py:"cbsjc",code:"NBS",initial:"C",city:"白山",citycode:"BAS"},
    {airportName:"长春龙嘉国际机场",pinyin:"changchunlongjiaguojijichang",py:"ccljgjjc",code:"CGQ",initial:"C",city:"长春",citycode:"CGQ"},
    {airportName:"常德桃花源机场",pinyin:"changdetaohuayuanjichang",py:"cdthyjc",code:"CGD",initial:"C",city:"常德",citycode:"CGD"},
    {airportName:"昌都邦达机场",pinyin:"changdubangdajichang",py:"cdbdjc",code:"BPX",initial:"C",city:"昌都",citycode:"CDU"},
    {airportName:"长海大长山岛机场",pinyin:"changhaidachangshandaojichang",py:"chdcsdjc",code:"CNI",initial:"C",city:"大连",citycode:""},
    {airportName:"长沙黄花国际机场",pinyin:"changshahuanghuaguojijichang",py:"cshhgjjc",code:"CSX",initial:"C",city:"长沙",citycode:"CSX"},
    {airportName:"长治王村机场",pinyin:"changzhiwangcunjichang",py:"czwcjc",code:"CIH",initial:"C",city:"长治",citycode:"CIH"},
    {airportName:"常州奔牛国际机场",pinyin:"changzhoubenniuguojijichang",py:"czbnjc",code:"CZX",initial:"C",city:"常州",citycode:"CZX"},
    {airportName:"朝阳机场",pinyin:"chaoyangjichang",py:"cyjc",code:"CHG",initial:"C",city:"朝阳",citycode:"CHG"},
    {airportName:"成都双流国际机场",pinyin:"chengdushuangliuguojijichang",py:"cdslgjjc",code:"CTU",initial:"C",city:"成都",citycode:"CTU"},
    {airportName:"赤峰玉龙机场",pinyin:"chifengyulongjichang",py:"cfyljc",code:"CIF",initial:"C",city:"赤峰",citycode:"CIF"},
    {airportName:"池州九华山机场",pinyin:"chizhoujiuhuashanjichang",py:"czjhsjc",code:"JUH",initial:"C",city:"池州",citycode:"CZU"},
    {airportName:"重庆江北国际机场",pinyin:"chongqingjiangbeiguojijichang",py:"cqjbgjjc",code:"CKG",initial:"C",city:"重庆",citycode:"CKG"},
    {airportName:"大理荒草坝机场",pinyin:"dalihuangcaobajichang",py:"dlhcbjc",code:"DLU",initial:"D",city:"大理",citycode:"DLU"},
    {airportName:"丹东浪头国际机场",pinyin:"dandonglangtouguojijichang",py:"ddltgjjc",code:"DDG",initial:"D",city:"丹东",citycode:"DDG"},
    {airportName:"稻城亚丁机场",pinyin:"daochengyadingjichang",py:"dcydjc",code:"DCY",initial:"D",city:"稻城",citycode:"DCY"},
    {airportName:"大庆萨尔图机场",pinyin:"daqingsaertujichang",py:"dqsetjc",code:"DQA",initial:"D",city:"大庆",citycode:"DAQ"},
    {airportName:"大同云冈机场",pinyin:"datongyungangjichang",py:"DTYGjc",code:"DAT",initial:"D",city:"大同",citycode:"DAT"},
    {airportName:"达州河市机场",pinyin:"dazhouheshijichang",py:"dzhsjc",code:"DAX",initial:"D",city:"达州",citycode:"DZX"},
    {airportName:"德宏芒市机场",pinyin:"dehongmangshijichang",py:"dhmsjc",code:"LUM",initial:"D",city:"芒市",citycode:""},
    {airportName:"迪庆香格里拉机场",pinyin:"diqingxianggelilajichang",py:"dqxglljc",code:"DIG",initial:"D",city:"香格里拉",citycode:"XIG"},
    {airportName:"东营胜利机场",pinyin:"dongyingshenglijichang",py:"dysljc",code:"DOY",initial:"D",city:"东营",citycode:"DOY"},
    {airportName:"敦煌莫高煌机场",pinyin:"dunhuangmogaohuangjichang",py:"dhmghjc",code:"DNH",initial:"D",city:"敦煌",citycode:"DNH"},
    {airportName:"大连周水子国际机场",pinyin:"dalianzhoushuizigguojijichang",py:"dlzszgjjc",code:"DLC",initial:"D",city:"大连",citycode:"DLC"},
    {airportName:"鄂尔多斯伊金霍洛国际机场",pinyin:"eerduosiyijinhuoluoguojijichang",py:"eedsyjhlgjjc",code:"DSN",initial:"E",city:"鄂尔多斯",citycode:"ERD"},
    {airportName:"额济纳旗桃来机场",pinyin:"ejinaqitaolaijichang",py:"ejnqtljc",code:"EJN",initial:"E",city:"额济纳旗",citycode:""},
    {airportName:"恩施许家坪机场",pinyin:"enshixujiapingjichang",py:"esxjpjc",code:"ENH",initial:"E",city:"恩施",citycode:"ENH"},
    {airportName:"二连浩特赛乌苏国际机场",pinyin:"erlianhaotesaiwusuguojijichang",py:"elhtswsgjjc",code:"ERL",initial:"E",city:"二连浩特",citycode:"ERL"},
    {airportName:"佛山沙堤机场",pinyin:"foshanshadijichang",py:"fssdjc",code:"FUO",initial:"F",city:"佛山",citycode:"FUO"},
    {airportName:"阜阳西关机场",pinyin:"fuyangxiguanjichang",py:"fyxgjc",code:"FUG",initial:"F",city:"阜阳",citycode:"FUG"},
    {airportName:"抚远东极机场",pinyin:"fuyuandongjijichang",py:"fydjjc",code:"FYJ",initial:"F",city:"抚远",citycode:""},
    {airportName:"富蕴可可托海机场",pinyin:"fuyunkeketuohaijichang",py:"fykkthjc",code:"FYN",initial:"F",city:"富蕴",citycode:""},
    {airportName:"福州长乐国际机场",pinyin:"fuzhouchangleguojijichang",py:"fzclgjjc",code:"FOC",initial:"F",city:"福州",citycode:"FOC"},
    {airportName:"甘南夏河机场",pinyin:"gannanxiahejichang",py:"gnxhjc",code:"GXH",initial:"G",city:"甘南",citycode:""},
    {airportName:"赣州黄金机场",pinyin:"ganzhouhuangjinjichang",py:"gzhjjc",code:"KOW",initial:"G",city:"赣州",citycode:"GZH"},
    {airportName:"甘孜康定",pinyin:"ganzikangdingjichang",py:"gzkdjc",code:"KGT",initial:"G",city:"甘孜州",citycode:"GAZ"},
    {airportName:"高雄国际机场",pinyin:"gaoxiongguojijichang",py:"gxgjjc",code:"KHH",initial:"G",city:"高雄",citycode:""},
    {airportName:"格尔木机场",pinyin:"geermujichang",py:"gemjc",code:"GOQ",initial:"G",city:"格尔木",citycode:"GOQ"},
    {airportName:"广元盘龙机场",pinyin:"guangyuanpanlongjichang",py:"gypljc",code:"GYS",initial:"G",city:"广元",citycode:"GUY"},
    {airportName:"广州白云国际机场",pinyin:"guangzhoubaiyunguojijichang",py:"gzbygjjc",code:"CAN",initial:"G",city:"广州",citycode:"CAN"},
    {airportName:"桂林两江国际机场",pinyin:"guilinliangjiangguojijichang",py:"glljgjjc",code:"KWL",initial:"G",city:"桂林",citycode:"KWL"},
    {airportName:"贵阳龙洞堡国际机场",pinyin:"guiyanglongdongbaoguojijichang",py:"gyldbgjjc",code:"KWE",initial:"G",city:"贵阳",citycode:"KWE"},
    {airportName:"固原六盘山机场",pinyin:"guyuanliupanshanjichang",py:"gylpsjc",code:"GYU",initial:"G",city:"固原",citycode:""},
    {airportName:"哈尔滨太平国际机场",pinyin:"haerbintaipingguojijichang",py:"hebtpgjjc",code:"HRB",initial:"H",city:"哈尔滨",citycode:"HRB"},
    {airportName:"海口美兰国际机场",pinyin:"haikoumeilanguojijichang",py:"hkmlgjjc",code:"HAK",initial:"H",city:"海口",citycode:"HAK"},
    {airportName:"呼伦贝尔东山机场",pinyin:"hulunbeierdongshanjichang",py:"hlbedsjc",code:"HLD",initial:"H",city:"呼伦贝尔",citycode:"HUL"},
    {airportName:"哈密机场",pinyin:"hamijichang",py:"hmjc",code:"HMI",initial:"H",city:"哈密",citycode:"HMI"},
    {airportName:"邯郸机场",pinyin:"handanjichang",py:"hdjc",code:"HDG",initial:"H",city:"邯郸",citycode:"HDN"},
    {airportName:"杭州萧山国际机场",pinyin:"hangzhouxiaoshanguojijichang",py:"hzxsgjjc",code:"HGH",initial:"H",city:"杭州",citycode:"HGH"},
    {airportName:"汉中城固机场",pinyin:"hanzhongchenggujichang",py:"hzcgjc",code:"HZG",initial:"H",city:"汉中",citycode:"HZG"},
    {airportName:"河池金城江机场",pinyin:"hechijinchengjiangjichang",py:"hcjcjjc",code:"HCJ",initial:"H",city:"河池",citycode:"HEC"},
    {airportName:"合肥新桥国际机场",pinyin:"hefeixinqiaoguojijichang",py:"hfxqgjjc",code:"HFE",initial:"H",city:"合肥",citycode:"HFE"},
    {airportName:"海西德令哈机场",pinyin:"haixidelinghajichang",py:"hzdlhjc",code:"HXD",initial:"H",city:"海西州",citycode:"DEL"},
    {airportName:"黑河瑷珲机场",pinyin:"heiheaihuijichang",py:"hhahjc",code:"HEK",initial:"H",city:"黑河",citycode:"HEK"},
    {airportName:"恒春五里亭机场",pinyin:"hengchunwulitingjichang",py:"hcwltjc",code:"HCN",initial:"H",city:"台湾",citycode:""},
    {airportName:"衡阳南岳机场",pinyin:"hengyangnanyuejichang",py:"hynyjc",code:"HNY",initial:"H",city:"衡阳",citycode:"HNY"},
    {airportName:"和田机场",pinyin:"hetianjichang",py:"htjc",code:"HTN",initial:"H",city:"和田",citycode:"HTN"},
    {airportName:"淮安涟水国际机场",pinyin:"huaianlianshuiguojijichang",py:"halsgjjc",code:"HIA",initial:"H",city:"淮安",citycode:"HUA"},
    {airportName:"怀化芷江机场",pinyin:"huaihuazhijiangjichang",py:"hhzjjc",code:"HJJ",initial:"H",city:"怀化",citycode:"HUH"},
    {airportName:"花莲机场",pinyin:"hualianjichang",py:"hljc",code:"HUN",initial:"H",city:"花莲",citycode:""},
    {airportName:"黄山屯溪国际机场",pinyin:"huangshantunxiguojijichang",py:"hstxgjjc",code:"TXN",initial:"H",city:"黄山",citycode:"TXN"},
    {airportName:"花土沟机场",pinyin:"huatugoujichang",py:"htgjc",code:"HTT",initial:"H",city:"海西州",citycode:""},
    {airportName:"呼和浩特白塔国际机场",pinyin:"huhehaotebaitaguojijichang",py:"hhhtbtgjjc",code:"HET",initial:"H",city:"呼和浩特",citycode:"HET"},
    {airportName:"惠州平潭机场",pinyin:"huizhoupingtanjichang",py:"hzptjc",code:"HUZ",initial:"H",city:"惠州",citycode:"HUZ"},
    {airportName:"加格达奇嘎仙机场",pinyin:"jiagedaqigaxianjichang",py:"jgdqgxjc",code:"JGD",initial:"J",city:"加格达奇",citycode:""},
    {airportName:"佳木斯东郊机场",pinyin:"jiamusidongjiaojichang",py:"jmsdjjc",code:"JMU",initial:"J",city:"佳木斯",citycode:"JMU"},
    {airportName:"嘉义水上机场",pinyin:"jiayishuishangjichang",py:"jyssjc",code:"CYI",initial:"J",city:"嘉义",citycode:""},
    {airportName:"嘉峪关机场",pinyin:"jiayuguanjichang",py:"jygjc",code:"JGN",initial:"J",city:"嘉峪关",citycode:"JGN"},
    {airportName:"揭阳潮汕国际机场",pinyin:"jieyangchaoshanguojijichang",py:"jycsgjjc",code:"SWA",initial:"J",city:"揭阳",citycode:"JYN"},
    {airportName:"吉林二台子机场",pinyin:"jilinertaizijichang",py:"jletzjc",code:"JIL",initial:"J",city:"吉林",citycode:"JIL"},
    {airportName:"济南遥墙国际机场",pinyin:"jinanyaoqiangguojijichang",py:"jnyqgjjc",code:"TNA",initial:"J",city:"济南",citycode:"TNA"},
    {airportName:"金昌金川机场",pinyin:"jinchangjinchuanjichang",py:"jcjcjc",code:"JIC",initial:"J",city:"金昌",citycode:"JCH"},
    {airportName:"景德镇罗家机场",pinyin:"jingdezhenluojiajichang",py:"jdzljjc",code:"JDZ",initial:"J",city:"景德镇",citycode:"JDZ"},
    {airportName:"井冈山机场",pinyin:"jinggangshanjichang",py:"jgsjc",code:"JGS",initial:"J",city:"井冈山",citycode:"JGS"},
    {airportName:"金门尚义机场",pinyin:"jinmenshangyijichang",py:"jmsyjc",code:"KNH",initial:"J",city:"金门",citycode:""},
    {airportName:"济宁曲阜机场",pinyin:"jiningqufujichang",py:"jnqfjc",code:"JNG",initial:"J",city:"济宁",citycode:"JNG"},
    {airportName:"锦州锦州湾机场",pinyin:"jinzhoujinzhouwanjichang",py:"jzjzwjc",code:"JNZ",initial:"J",city:"锦州",citycode:"JNZ"},
    {airportName:"九江庐山机场",pinyin:"jiujianglushanjichang",py:"jjlsjc",code:"JIU",initial:"J",city:"九江",citycode:"JIU"},
    {airportName:"九寨黄龙机场",pinyin:"jiuzhaihuanglongjichang",py:"jzhljc",code:"JZH",initial:"J",city:"九寨沟",citycode:"JZH"},
    {airportName:"鸡西兴凯湖机场",pinyin:"jixixingkaihujichang",py:"jxxkhjc",code:"JXA",initial:"J",city:"鸡西",citycode:"JXI"},
    {airportName:"凯里黄平机场",pinyin:"kailihuangpingjichang",py:"klhpjc",code:"KJH",initial:"K",city:"凯里",citycode:"KAL"},
    {airportName:"喀什机场",pinyin:"kashijichang",py:"ksjc",code:"KHG",initial:"K",city:"喀什",citycode:"KHG"},
    {airportName:"克拉玛依机场",pinyin:"kelamayijichang",py:"klmyjc",code:"KRY",initial:"K",city:"克拉玛依",citycode:"KLY"},
    {airportName:"库车龟兹机场",pinyin:"kucheqiucijichang",py:"kcqcjc",code:"KCA",initial:"K",city:"库车",citycode:""},
    {airportName:"库尔勒机场",pinyin:"kuerlejichang",py:"keljc",code:"KRL",initial:"K",city:"库尔勒",citycode:"KEL"},
    {airportName:"昆明长水国际机场",pinyin:"kunmingchangshuiguojijichang",py:"kmcsgjjc",code:"KMG",initial:"K",city:"昆明",citycode:"KMG"},
    {airportName:"拉萨贡嘎国际机场",pinyin:"lasagonggaguojijichang",py:"lsgggjjc",code:"LXA",initial:"L",city:"拉萨",citycode:"LXA"},
    {airportName:"兰州中川国际机场",pinyin:"lanzhouzhongchuanguojijichang",py:"lzzcgjjc",code:"LHW",initial:"L",city:"兰州",citycode:"LHW"},
    {airportName:"连云港白塔埠机场",pinyin:"lianyungangbaitabujichang",py:"lygbtbjc",code:"LYG",initial:"L",city:"连云港",citycode:"LYG"},
    {airportName:"荔波机场",pinyin:"libojichang",py:"lbjc",code:"LLB",initial:"L",city:"荔波",citycode:""},
    {airportName:"丽江三义国际机场",pinyin:"lijiangsanyiguojijichang",py:"ljsygjjc",code:"LJG",initial:"L",city:"丽江",citycode:"LJG"},
    {airportName:"临沧博尚机场",pinyin:"lincangboshangjichang",py:"lcbsjc",code:"LNJ",initial:"L",city:"临沧",citycode:"LCH"},
    {airportName:"临汾乔李机场",pinyin:"linfenqiaoli",py:"lfql",code:"LFQ",initial:"L",city:"临汾",citycode:"LIF"},
    {airportName:"林西机场",pinyin:"linxijichang",py:"lxjc",code:"LXI",initial:"L",city:"林西县",citycode:""},
    {airportName:"临沂沭埠岭机场",pinyin:"linyishubulingjichang",py:"lymbljc",code:"LYI",initial:"L",city:"临沂",citycode:"LYI"},
    {airportName:"林芝米林机场",pinyin:"linzhimilinjichang",py:"lzmljc",code:"LZY",initial:"L",city:"林芝",citycode:"LZI"},
    {airportName:"黎平机场",pinyin:"lipingjichang",py:"lpjc",code:"HZH",initial:"L",city:"黎平县",citycode:""},
    {airportName:"六盘水月照机场",pinyin:"liupanshuiyuezhaojichang",py:"lpsyzjc",code:"LPF",initial:"L",city:"六盘水",citycode:"LIP"},
    {airportName:"柳州白莲机场",pinyin:"liuzhoubailianjichang",py:"lubljc",code:"LZH",initial:"L",city:"柳州",citycode:"LZH"},
    {airportName:"龙岩冠豸山机场",pinyin:"longyanguanzhaishanjichang",py:"fjlygzsjc",code:"LCX",initial:"L",city:"龙岩",citycode:""},
    {airportName:"洛阳北郊机场",pinyin:"luoyangbeijiaojichang",py:"lybjjc",code:"LYA",initial:"L",city:"洛阳",citycode:"LYA"},
    {airportName:"泸州蓝田机场",pinyin:"luzhoulantianjichang",py:"lzltjc",code:"LZO",initial:"L",city:"泸州",citycode:"LZO"},
    {airportName:"吕梁大武机场",pinyin:"lvliangdawujichang",py:"lldwjc",code:"LLV",initial:"L",city:"吕梁",citycode:""},
    {airportName:"满洲里西郊国际机场",pinyin:"manzhoulixijiaoguojijichang",py:"mzlxjgjjc",code:"NZH",initial:"M",city:"满洲里",citycode:"MAZ"},
    {airportName:"马祖北竿机场",pinyin:"mazubeiganjichang",py:"mzbgjc",code:"MFK",initial:"M",city:"马祖",citycode:""},
    {airportName:"马祖南竿机场",pinyin:"mazunanganjichang",py:"mzngjc",code:"LZN",initial:"N",city:"马祖",citycode:""},
    {airportName:"梅州梅县长岗岌机场",pinyin:"meixianchanggangjijichang",py:"mxcgjjc",code:"MXZ",initial:"M",city:"梅州",citycode:"MZU"},
    {airportName:"绵阳南郊机场",pinyin:"mianyangnanjiaojichang",py:"mynjjc",code:"MIG",initial:"M",city:"绵阳",citycode:"MIG"},
    {airportName:"漠河古莲机场",pinyin:"mohegulianjichang",py:"mhgljc",code:"OHE",initial:"M",city:"漠河",citycode:""},
    {airportName:"牡丹江海浪机场",pinyin:"mudanjianghailangjichang",py:"mdjhljc",code:"MDG",initial:"M",city:"牡丹江",citycode:"MDG"},
    {airportName:"那拉提机场",pinyin:"nalatijichang",py:"nltjc",code:"NLT",initial:"N",city:"新源",citycode:""},
    {airportName:"南昌昌北国际机场",pinyin:"nanchangchangbeiguojijichang",py:"nccbgjjc",code:"KHN",initial:"N",city:"南昌",citycode:"KHN"},
    {airportName:"南充高坪机场",pinyin:"nanchonggaopingjichang",py:"nvgpjc",code:"NAO",initial:"N",city:"南充",citycode:"NAO"},
    {airportName:"南京禄口国际机场",pinyin:"nanjinglukouguojijichang",py:"njlkgjjc",code:"NKG",initial:"N",city:"南京",citycode:"NKG"},
    {airportName:"南宁吴圩国际机场",pinyin:"nanningwuxuguojijichang",py:"nnwxgjjc",code:"NNG",initial:"N",city:"南宁",citycode:"NNG"},
    {airportName:"南沙永暑礁机场",pinyin:"nanshayongshujiaojichang",py:"nsysjjc",code:"YXG",initial:"N",city:"永暑礁",citycode:""},
    {airportName:"南通兴东国际机场",pinyin:"nantongxingdongguojijichang",py:"ntxdjc",code:"NTG",initial:"N",city:"南通",citycode:"NTG"},
    {airportName:"南阳姜营机场",pinyin:"nanyangjiangyingjichang",py:"nyjyjc",code:"NNY",initial:"N",city:"南阳",citycode:"NNY"},
    {airportName:"宁波栎社国际机场",pinyin:"ningbolisheguojijichang",py:"nblsgjjc",code:"NGB",initial:"N",city:"宁波",citycode:"NGB"},
    {airportName:"宁蒗泸沽湖机场",pinyin:"ninglangluguhujichang",py:"nllghjc",code:"NLH",initial:"N",city:"宁蒗",citycode:""},
    {airportName:"攀枝花保安营机场",pinyin:"panzhihuabaoanyingjichang",py:"pzhbayjc",code:"PZI",initial:"P",city:"攀枝花",citycode:"PZI"},
    {airportName:"屏东机场",pinyin:"pingdongjichang",py:"pdjc",code:"PIF",initial:"P",city:"屏东",citycode:""},
    {airportName:"普洱思茅机场",pinyin:"puersimaojichang",py:"pesmjc",code:"SYM",initial:"P",city:"普洱",citycode:""},
    {airportName:"黔江武陵山机场",pinyin:"qianjiangwulingshanjichang",py:"qjwlsjc",code:"JIQ",initial:"Q",city:"黔江",citycode:""},
    {airportName:"且末机场",pinyin:"qiemojichang",py:"qmjc",code:"IQM",initial:"Q",city:"且末",citycode:""},
    {airportName:"七美机场",pinyin:"qimeijichang",py:"qmjc",code:"CMJ",initial:"Q",city:"七美",citycode:""},
    {airportName:"青岛流亭国际机场",pinyin:"qingdaoliutingguojijichang",py:"qdltgjjc",code:"TAO",initial:"Q",city:"青岛",citycode:"TAO"},
    {airportName:"庆阳西峰机场",pinyin:"qingyangxifengjichang",py:"qyxfjc",code:"IQN",initial:"Q",city:"庆阳",citycode:"IQN"},
    {airportName:"秦皇岛山海关机场",pinyin:"qinhuangdaoshanhaiguanjichang",py:"qhdshgjc",code:"SHP",initial:"Q",city:"秦皇岛",citycode:"SHP"},
    {airportName:"齐齐哈尔三家子机场",pinyin:"qiqihaersanjiazijichang",py:"qqhesjzjc",code:"NDG",initial:"Q",city:"齐齐哈尔",citycode:"NDG"},
    {airportName:"泉州晋江国际机场",pinyin:"quanzhoujinjiangguojijichang",py:"qzjjgjjc",code:"JJN",initial:"Q",city:"泉州",citycode:"QUZ"},
    {airportName:"衢州机场",pinyin:"quzhoujichang",py:"qzjc",code:"JUZ",initial:"Q",city:"衢州",citycode:"JUZ"},
    {airportName:"日喀则和平机场",pinyin:"rikazehepingjichang",py:"rkzhpjc",code:"RKZ",initial:"R",city:"日喀则",citycode:"RIK"},
    {airportName:"日照山字河机场",pinyin:"rizhaoshanzihejichang",py:"rzszhjc",code:"RIZ",initial:"R",city:"日照",citycode:"RIZ"},
    {airportName:"三亚凤凰国际机场",pinyin:"sanyafenghuangguojijichang",py:"syfhgjjc",code:"SYX",initial:"S",city:"三亚",citycode:"SYX"},
    {airportName:"上海虹桥国际机场",pinyin:"shanghaihongqiaoguojijichang",py:"shhqgjjc",code:"SHA",initial:"S",city:"上海",citycode:"SHA"},
    {airportName:"上海浦东国际机场",pinyin:"shanghaipudongguojijichang",py:"shpdgjjc",code:"PVG",initial:"S",city:"上海",citycode:"SHA"},
    {airportName:"上饶三清山机场",pinyin:"shangraosanqingshanjichang",py:"srsqsjc",code:"SQD",initial:"S",city:"上饶",citycode:"SHR"},
    {airportName:"韶关丹霞山机场",pinyin:"shaoguandanxiashanjichang",py:"sgdxsjc",code:"HSC",initial:"S",city:"韶关",citycode:"HSC"},
    {airportName:"神农架红坪机场",pinyin:"shennongjiahongpingjichang",py:"sljhpjc",code:"HPG",initial:"S",city:"神龙架",citycode:"SNJ"},
    {airportName:"沈阳桃仙国际机场",pinyin:"shenyangtaoxianguojijichang",py:"sytxgjjc",code:"SHE",initial:"S",city:"沈阳",citycode:"SHE"},
    {airportName:"深圳宝安国际机场",pinyin:"shenzhenbaoanguojijichang",py:"szbagjjc",code:"SZX",initial:"S",city:"深圳",citycode:"SZX"},
    {airportName:"石河子花园机场",pinyin:"shihezihuayuanjichang",py:"shzhyjc",code:"SHF",initial:"S",city:"石河子",citycode:"SHH"},
    {airportName:"石家庄正定国际机场",pinyin:"shijiazhuangzhengdingguojijichang",py:"sjzzdgjjc",code:"SJW",initial:"S",city:"石家庄",citycode:""},
    {airportName:"十堰武当山机场",pinyin:"shiyanwudangshanjichang",py:"sywdsjc",code:"WDS",initial:"S",city:"十堰",citycode:"SYA"},
    {airportName:"塔城机场",pinyin:"tachengjichang",py:"tcjc",code:"TCG",initial:"T",city:"塔城",citycode:"TCG"},
    {airportName:"台北松山机场",pinyin:"taibeisongshanjichang",py:"tbssjc",code:"TSA",initial:"T",city:"台北",citycode:""},
    {airportName:"台东丰年机场",pinyin:"taidongfengnianjichang",py:"tdfnjc",code:"TTT",initial:"T",city:"台东",citycode:""},
    {airportName:"台南机场",pinyin:"tainanjichang",py:"tnjc",code:"TNN",initial:"T",city:"台南",citycode:""},
    {airportName:"台湾桃园国际机场",pinyin:"taiwantaoyuanguojijichang",py:"tbtygjjc",code:"TPE",initial:"T",city:"台北",citycode:""},
    {airportName:"太原武宿国际机场",pinyin:"taiyuanwusuguojijichang",py:"tywsgjjc",code:"TYN",initial:"T",city:"太远",citycode:"TYN"},
    {airportName:"台中清泉岗机场",pinyin:"taizhongqingquangangjichang",py:"taqqgjc",code:"RMQ",initial:"T",city:"台中",citycode:""},
    {airportName:"台州路桥机场",pinyin:"taizhouluqiaojichang",py:"tzlqjc",code:"HYN",initial:"T",city:"台州",citycode:"TAZ"},
    {airportName:"唐山三女河机场",pinyin:"tangshansannvhejichang",py:"tssnhjc",code:"TVS",initial:"T",city:"唐山",citycode:"TAS"},
    {airportName:"腾冲驼峰机场",pinyin:"tengchongtuofengjichang",py:"tctfjc",code:"TCZ",initial:"T",city:"腾冲",citycode:""},
    {airportName:"天津滨海国际机场",pinyin:"tianjinbinhaiguojijichang",py:"tjbhgjjc",code:"TSN",initial:"T",city:"天津",citycode:"TSN"},
    {airportName:"天水麦积山机场",pinyin:"tianshuimaijishan",py:"tsmjs",code:"THQ",initial:"T",city:"天水",citycode:"TIS"},
    {airportName:"通化三源浦机场",pinyin:"tonghuasanyuanpujichang",py:"thsypjc",code:"TNH",initial:"T",city:"通化",citycode:"TNH"},
    {airportName:"通辽机场",pinyin:"tongliaojichang",py:"tljc",code:"TGO",initial:"T",city:"通辽",citycode:"TGO"},
    {airportName:"铜仁凤凰机场",pinyin:"tongrenfenghuangjichang",py:"trfhjc",code:"TEN",initial:"T",city:"铜仁",citycode:"TOR"},
    {airportName:"吐鲁番交河机场",pinyin:"tulufanjiaohejichang",py:"tlfjhjc",code:"TLQ",initial:"T",city:"吐鲁番",citycode:"TUL"},
    {airportName:"万州五桥机场",pinyin:"wanzhouwuqiaojichang",py:"wzwqjc",code:"WXN",initial:"W",city:"万州",citycode:""},
    {airportName:"潍坊南苑机场",pinyin:"weifangnanyuanjichang",py:"wfnyjc",code:"WEF",initial:"W",city:"潍坊",citycode:"WEF"},
    {airportName:"威海大水泊国际机场",pinyin:"weihaidashuipoguojijichang",py:"whdspgjjc",code:"WEH",initial:"W",city:"威海",citycode:"WEH"},
    {airportName:"文山普者黑机场",pinyin:"wenshanpuzheheiguojijichang",py:"wspzhgjjc",code:"WNH",initial:"W",city:"WES",citycode:""},
    {airportName:"温州龙湾国际机场",pinyin:"wenzhoulongwanguojijichang",py:"wzlwgjjc",code:"WNZ",initial:"W",city:"温州",citycode:"WNZ"},
    {airportName:"乌海机场",pinyin:"wuhaijichang",py:"whjc",code:"WUA",initial:"W",city:"乌海",citycode:"WHA"},
    {airportName:"武汉天河国际机场",pinyin:"wuhantianheguojijichang",py:"whthgjjc",code:"WUH",initial:"W",city:"武汉",citycode:"WUH"},
    {airportName:"乌兰察布集宁机场",pinyin:"wulanchabujiningjichang",py:"wlcbjnjc",code:"UCB",initial:"W",city:"乌兰察布",citycode:""},
    {airportName:"乌兰浩特依勒力特机场",pinyin:"wulanhaoteyilelitejichang",py:"wlhtylltjc",code:"HLH",initial:"W",city:"乌兰浩特",citycode:"HLH"},
    {airportName:"乌鲁木齐地窝堡机场",pinyin:"wulumuqidiwopujichang",py:"wlmqdwpjc",code:"URC",initial:"W",city:"乌鲁木齐",citycode:"URC"},
    {airportName:"无锡苏南硕放国际机场",pinyin:"wuxisunanshuofangguojijichang",py:"wxsnsfgjjc",code:"WUX",initial:"W",city:"无锡",citycode:"WUX"},
    {airportName:"武夷山机场",pinyin:"wuyishanjichang",py:"wysjc",code:"WYS",initial:"W",city:"武夷山",citycode:"WYS"},
    {airportName:"梧州长洲岛机场",pinyin:"wuzhouchangzhoudaojichang",py:"wzczdjc",code:"WUZ",initial:"W",city:"梧州",citycode:"WUZ"},
    {airportName:"厦门高崎国际机场",pinyin:"xiamengaoqiguojijichang",py:"xmgq",code:"XMN",initial:"X",city:"厦门",citycode:""},
    {airportName:"香港赤鱲角国际机场",pinyin:"xianggangchiliejiaoguojijichang",py:"xgcljgjjc",code:"HKG",initial:"X",city:"香港",citycode:"HKG"},
    {airportName:"襄阳刘集机场",pinyin:"xiangyangliujijichang",py:"xyljjc",code:"XFN",initial:"X",city:"襄阳",citycode:""},
    {airportName:"西安咸阳国际机场",pinyin:"xianxianyangguojijichang",py:"xaxygjjc",code:"XIY",initial:"X",city:"西安",citycode:"SIA"},
    {airportName:"西昌青山机场",pinyin:"xichangqingshanjichang",py:"xcqsjc",code:"XIC",initial:"X",city:"西昌",citycode:"XIC"},
    {airportName:"锡林浩特机场",pinyin:"xilinhaotejichang",py:"xlhtjc",code:"XIL",initial:"X",city:"锡林浩特",citycode:"XIL"},
    {airportName:"邢台褡裢机场",pinyin:"xingtaidalianjichang",py:"xtdljc",code:"XNT",initial:"X",city:"邢台",citycode:"XNT"},
    {airportName:"兴义万峰林机场",pinyin:"xingyiwanfenglinjichang",py:"xywfljc",code:"ACX",initial:"X",city:"兴义",citycode:"XYN"},
    {airportName:"西宁曹家堡机场",pinyin:"xiningcaojiapujichang",py:"xncjpjc",code:"XNN",initial:"X",city:"西宁",citycode:"XNN"},
    {airportName:"忻州五台山机场",pinyin:"xinzhouwutaishanjichang",py:"xzwtsjc",code:"WUT",initial:"X",city:"忻州",citycode:"XIU"},
    {airportName:"西双版纳嘎洒国际机场",pinyin:"xishuangbannagasaguojijichang",py:"xsbngsgjjc",code:"JHG",initial:"X",city:"西双版纳",citycode:"JHG"},
    {airportName:"徐州观音国际机场",pinyin:"xuzhouguanyinguojijichang",py:"xzgygjjc",code:"XUZ",initial:"X",city:"徐州",citycode:"XUZ"},
    {airportName:"延安二十里铺机场",pinyin:"yananershilipujichang",py:"yaeslpjc",code:"ENY",initial:"Y",city:"延安",citycode:"ENY"},
    {airportName:"盐城南洋国际机场",pinyin:"yanchengnanyangguojijichang",py:"ycnygjjc",code:"YNZ",initial:"Y",city:"盐城",citycode:"YNZ"},
    {airportName:"扬州泰州国际机场",pinyin:"yangzhoutaizhouguojijichang",py:"yztzgjjc",code:"YTY",initial:"Y",city:"扬州",citycode:"YZO"},
    {airportName:"延吉朝阳川国际机场",pinyin:"yanjichaoyangchuanguojijichang",py:"yjcycgjjc",code:"YNJ",initial:"Y",city:"延吉",citycode:"YNJ"},
    {airportName:"烟台蓬莱国际机场",pinyin:"tantaipenglaiguojijichang",py:"ytplgjjc",code:"YNT",initial:"Y",city:"烟台",citycode:"YNT"},
    {airportName:"宜宾菜坝机场",pinyin:"yibincaibajichang",py:"ybcbjc",code:"YBP",initial:"Y",city:"宜宾",citycode:"YBP"},
    {airportName:"宜昌三峡机场",pinyin:"yichangsanxiajichang",py:"ycsxjc",code:"YIH",initial:"Y",city:"宜昌",citycode:"YIH"},
    {airportName:"伊春林都机场",pinyin:"yichunlindujichang",py:"ycldjc",code:"LDS",initial:"Y",city:"伊春",citycode:"YCH"},
    {airportName:"宜春明月山机场",pinyin:"yichunmingyueshanjichang",py:"ycmysjc",code:"YIC",initial:"Y",city:"宜春",citycode:"YIC"},
    {airportName:"银川河东国际机场",pinyin:"yinchuanhedongguojijichang",py:"ychdgjjc",code:"INC",initial:"Y",city:"银川",citycode:"INC"},
    {airportName:"营口兰旗机场",pinyin:"yingkoulanqijichang",py:"yklqjc",code:"YKH",initial:"Y",city:"营口",citycode:"YIK"},
    {airportName:"伊宁机场",pinyin:"yiningjichang",py:"ynjc",code:"YIN",initial:"Y",city:"伊宁",citycode:"YIN"},
    {airportName:"义乌机场",pinyin:"yiwujichang",py:"ywjc",code:"YIW",initial:"Y",city:"义乌",citycode:"YIW"},
    {airportName:"永州零陵机场",pinyin:"yongzhoulinglingjichang",py:"yzlljc",code:"LLF",initial:"Y",city:"永州",citycode:"YOZ"},
    {airportName:"右旗巴丹吉林机场",pinyin:"youqibadanjilinjichang",py:"yqbdjljc",code:"RHT",initial:"Y",city:"阿拉善右旗",citycode:""},
    {airportName:"榆林榆阳机场",pinyin:"yulinyuyangjichang",py:"ylyyjc",code:"UYN",initial:"Y",city:"榆林",citycode:"UYN"},
    {airportName:"运城关公机场",pinyin:"yunchengguangonjichangg",py:"ycggjc",code:"YCU",initial:"Y",city:"运城",citycode:"YUC"},
    {airportName:"玉树巴塘机场",pinyin:"yushubatangjichang",py:"ysbtjc",code:"YYUS",initial:"Y",city:"玉树",citycode:"YUS"},
    {airportName:"张家界荷花国际机场",pinyin:"zhangjiajiehehuaguojijichang",py:"zjjhhgjjc",code:"DYG",initial:"Z",city:"张家界",citycode:"DYG"},
    {airportName:"张家口宁远机场",pinyin:"zhangjiakouningyuanjichang",py:"zjknyjc",code:"ZQZ",initial:"Z",city:"张家口",citycode:"ZJK"},
    {airportName:"张掖甘州机场",pinyin:"zhangyeganzhoujichang",py:"zygzjc",code:"YZY",initial:"Z",city:"张掖",citycode:"ZHY"},
    {airportName:"湛江机场",pinyin:"zhanjiangjichang",py:"zjjc",code:"ZHA",initial:"Z",city:"湛江",citycode:"ZHA"},
    {airportName:"昭通机场",pinyin:"zhaotongjichang",py:"ztjc",code:"ZAT",initial:"Z",city:"昭通",citycode:"ZAT"},
    {airportName:"郑州新郑国际机场",pinyin:"zhengzhouxinzhengguojijichang",py:"zzxzgjjc",code:"CGO",initial:"Z",city:"郑州",citycode:"CGO"},
    {airportName:"中卫沙坡头机场",pinyin:"zhongweishapotoujichang",py:"zwsptjc",code:"ZHY",initial:"Z",city:"中卫",citycode:""},
    {airportName:"舟山普陀山机场",pinyin:"zhoushanputuoshanjichang",py:"zsptsjc",code:"HSN",initial:"Z",city:"舟山",citycode:"HSN"},
    {airportName:"珠海金湾机场",pinyin:"zhuhaijinwanjichangjichang",py:"zhjwjc",code:"ZUH",initial:"Z",city:"珠海",citycode:"ZUH"},
    {airportName:"遵义新舟国际机场",pinyin:"zunyixinzhouguojijichang",py:"zyxzgjjc",code:"ZYI",initial:"Z",city:"遵义",citycode:"ZYI"},
    {airportName:"左旗巴彦浩特通勤机场",pinyin:"zuoqibayanhaotetongqinjichang",py:"zqbyhttqjc",code:"AXF",initial:"Z",city:"阿拉善左旗",citycode:""}
];
//滚动条
function addBar(cNode,zNode,gNode){ // cNode-/最大区域事件绑定块/ zNode-/区域固定盒子/ gNode-/滚动不固定盒子（滚动盒子）
    if(infer(gNode)[1]>infer(zNode)[1]){
        if($(zNode).css("position")=="static"){ //初始化盒子
            $(zNode).css("position","relative")
        };
        if($(gNode).css("position")=="static"){ //初始化滚动盒子
            $(gNode).css("position","relative")
        };
        
        if($("_bar").length==0){  //添加滚动条
            $(zNode).append("<div class='_bar-box'><div class='_bar'></div></div>");
            $("._bar-box").css({"position": "absolute","right": "0px","background-color": "rgba(153,153,153,0.1)","height": "100%","width": "15px",'top':'0px'});
            $("._bar").css({'width':'8px','height':'100px','background-color':'rgb(121, 178, 223)',"cursor":"pointer",'position':'absolute','left':'3.5px','top':'0px','border-radius':'2px'});
        };
        var Scrollable=(infer("._bar-box")[1]-infer("._bar")[1])/(infer(gNode)[1]-infer(zNode)[1]);
        $(cNode).on("mousewheel",function(e,delta){
            if(delta=="1"){
                $(gNode).css({"top":"+=15px"});
                if(infer(gNode)[4]>0){
                    $(gNode).css({"top":"0px"});
                };
                $("._bar").css({"top":-(infer(gNode)[4]*Scrollable)+"px"})
            }else {
                $(gNode).css({"top":"-=15px"});
                if(infer(gNode)[4]<-(infer(gNode)[1]-infer(zNode)[1])){
                    $(gNode).css({"top":-(infer(gNode)[1]-infer(zNode)[1])+"px"});
                }
                $("._bar").css({"top":-(infer(gNode)[4]*Scrollable)+"px"})
            }
        });
        $("._bar").on("mousedown",function(e){
            e.stopPropagation();
            var oTop= e.screenY;
            var top=infer("._bar")[4];
            $(cNode).on("mousemove",function(b){
                var nTop= b.screenY;
                var tTop=top+(nTop-oTop);
                $("._bar").css({"top":tTop+"px"});
                if(infer("._bar")[4]<0){
                    $("._bar").css({"top":"0px"});
                }else if(infer("._bar")[4]>(infer("._bar-box")[1]-infer("._bar")[1])){
                    $("._bar").css({"top":(infer("._bar-box")[1]-infer("._bar")[1])+"px"});
                }
                $(gNode).css({"top":-(infer("._bar")[4]/Scrollable)+"px"});
            })
        });
        $("._bar").on("click",function(e){  //阻止事件冒泡
            e.stopPropagation();
        });
        $(cNode).on("mouseup",function(){
            $(cNode).unbind("mousemove");
        });
        $("._bar-box").on("click",function(e){
            var et=e.offsetY;
            if(et<infer("._bar")[1]){
                $("._bar").css("top","0px");
            }else if(et>(infer("._bar-box")[1]-infer("._bar")[1])){
                $("._bar").css("top",(infer("._bar-box")[1]-infer("._bar")[1])+"px");
            }else {
                $("._bar").css("top",(et-(infer("._bar")[1]/2))+"px");
            }
            $(gNode).css({"top":-(infer("._bar")[4]/Scrollable)+"px"});
        });
    }
};
$(function(){
    $('body').on("click",function(){ //关闭
		$(".c-selectCity").nextAll().remove();
		$("._set-allList").css({"display":"none"});
	});
});
//模拟选择框
function setChoose(list){// data-/list节点集合  summary-/是否包含往返 true包含false不包含 name-/添加list节点 cleName-/取消绑定事件函数节点
   var nums="";
   if(list.data.length>0){
	   if(list.summary=="true"){
		   nums+="<div class='_set-list-title'>汇总</div><div class='_set-allList'>";
		   nums+="<div>汇总</div>";
	   }else {
		   nums+="<div class='_set-list-title'>"+list.data[0]+"</div><div class='_set-allList'>";
	   }
	   for(var i=0;i<list.data.length;i++){
		   nums+="<div>"+list.data[i]+"</div>";
	   }
	   $(list.name).html(nums);
	   $("._set-list-title").on("click",function(event){
		   $('.c-selectCityBox').remove();//关闭机场选择框
		   $('.opensright').css('display','none');
		   event.stopPropagation();
		   if( $("._set-allList").css("display")=="none"){
			   $("._set-allList").css({"display":"block"});
		   }else {
			   $("._set-allList").css({"display":"none"});
		   }
	   });
	   $("._set-allList>div").on("click",function(event){
		   event.stopPropagation();
		   $("._set-list-title").text($(this).text());
		   $("._set-allList").css("display","none");
		   list.fun();
	   });
	   $(list.cleName).on("click",function(){
		   $("._set-allList").css({"display":"none"});
	   });
	   var indexx = parseInt(list.data.indexOf(list.flyNbr));
	   if(list.summary=="true"){
		   $("._set-allList>div").eq(indexx+1).click();
	   }else{
		   $("._set-allList>div").eq(indexx).click(); 
	   }
   }else{
	   nums+="<div class='_set-list-title'></div>";
	   $(list.name).html(nums); 
   }
}

// 机场控件
function airControl(name,lists){
    $(".c-selectCity").parent().css({"position":"relative"});
    $(".c-selectCity").on("click",function(e){
    	$('._set-allList').css('display','none');//关闭航班列表框
    	$('.opensright').css('display','none');//关闭时间选择框
        var ables=1;
        var storage="";
        $(".c-selectCity").removeClass("c-selectCity2");
        $(this).addClass("c-selectCity2");
        e.stopPropagation(); //屏蔽事件冒泡
        $(".c-selectCity").nextAll().remove();
        $(this).after("<div class='c-selectCityBox'><div class='c-selectHead'><div class='c-selectHead-dis c-selectHead-how c-selectHead-tag'>热门城市</div><div class='c-selectHead-dis' tag='A-B-C-D'>ABCD</div> <div class='c-selectHead-dis' tag='E-F-G-H'>EFGH</div><div class='c-selectHead-dis' tag='I-J-K-L-M'>IJKLM</div><div class='c-selectHead-dis' tag='N-O-P-Q-R-S'>NOPQRS</div><div class='c-selectHead-dis' tag='T-U-V-W'>TUVW</div><div class='c-selectHead-dis' tag='X-Y-Z'>XYZ</div></div><div class='c-selectBody'><div class='c-selectBody-how' abbr='北京首都国际机场-PEK'>北京首都</div><div class='c-selectBody-how' abbr='成都双流国际机场-CTU'>成都双流</div><div class='c-selectBody-how' abbr='上海浦东国际机场-PVG'>上海浦东</div><div class='c-selectBody-how' abbr='上海虹桥国际机场-SHA'>上海虹桥</div><div class='c-selectBody-how' abbr='深圳宝安国际机场-SZX'>深圳宝安</div><div class='c-selectBody-how' abbr='昆明长水国际机场-KMG'>昆明长水</div><div class='c-selectBody-how' abbr='西安咸阳国际机场-XIY'>西安咸阳</div><div class='c-selectBody-how' abbr='重庆江北国际机场-CKG'>重庆江北</div><div class='c-selectBody-how' abbr='杭州萧山国际机场-HGH'>杭州萧山</div><div class='c-selectBody-how' abbr='厦门高崎国际机场-XMN'>厦门高崎</div><div class='c-selectBody-how' abbr='南京禄口国际机场-NKG'>南京禄口</div><div class='c-selectBody-how' abbr='武汉天河国际机场-WUH'>武汉天河</div><div class='c-selectBody-how' abbr='沈阳桃仙国际机场-SHE'>沈阳桃仙</div><div class='c-selectBody-how' abbr='青岛流亭国际机场-TAO'>青岛流亭</div><div class='c-selectBody-how' abbr='福州长乐国际机场-FOC'>福州长乐</div><div class='c-selectBody-how' abbr='三亚凤凰国际机场-SYX'>三亚凤凰</div><div class='c-selectBody-how' abbr='天津滨海国际机场-TSN'>天津滨海</div></div></div>");
        $(".c-selectHead-dis").on("click",function(){
            $(this).addClass("c-selectHead-tag").siblings().removeClass("c-selectHead-tag");
            if($(this).hasClass("c-selectHead-how")){
                $(".c-selectBody").html("<div class='c-selectBody-how' abbr='北京首都国际机场-PEK'>北京首都</div><div class='c-selectBody-how' abbr='成都双流国际机场-CTU'>成都双流</div><div class='c-selectBody-how' abbr='上海浦东国际机场-PVG'>上海浦东</div><div class='c-selectBody-how' abbr='上海虹桥国际机场-SHA'>上海虹桥</div><div class='c-selectBody-how' abbr='深圳宝安国际机场-SZX'>深圳宝安</div><div class='c-selectBody-how' abbr='昆明长水国际机场-KMG'>昆明长水</div><div class='c-selectBody-how' abbr='西安咸阳国际机场-XIY'>西安咸阳</div><div class='c-selectBody-how' abbr='重庆江北国际机场-CKG'>重庆江北</div><div class='c-selectBody-how' abbr='杭州萧山国际机场-HGH'>杭州萧山</div><div class='c-selectBody-how' abbr='厦门高崎国际机场-XMN'>厦门高崎</div><div class='c-selectBody-how' abbr='南京禄口国际机场-NKG'>南京禄口</div><div class='c-selectBody-how' abbr='武汉天河国际机场-WUH'>武汉天河</div><div class='c-selectBody-how' abbr='沈阳桃仙国际机场-SHE'>沈阳桃仙</div><div class='c-selectBody-how' abbr='青岛流亭国际机场-TAO'>青岛流亭</div><div class='c-selectBody-how' abbr='福州长乐国际机场-FOC'>福州长乐</div><div class='c-selectBody-how' abbr='三亚凤凰国际机场-SYX'>三亚凤凰</div><div class='c-selectBody-how' abbr='天津滨海国际机场-TSN'>天津滨海</div>")
            }else {
                var aData=$(this).attr("tag").split("-");
                var nums="";
                var abbr=$(this).attr("abbr");
                var arr=["I","O","U","V"];
                for(var j=0;j<aData.length;j++){
                    if(arr.indexOf(aData[j])==-1){
                        nums+="<div class='c-selectBody-ord'><div class='c-selectBody-ord-ti'>"+aData[j]+"</div><div class='c-selectBody-ord-bo'>";
                        for(var i=0;i<nationalAirport.length;i++){
                            if(aData[j]==nationalAirport[i].initial){
                                nums+="<div class='c-selectBody-how' abbr='"+nationalAirport[i].airportName+"-"+nationalAirport[i].code+"'>"+nationalAirport[i].airportName.split("机场")[0].split("国际")[0]+"</div>"
                            }
                        }
                        nums+="</div></div>";
                    }
                }
                $(".c-selectBody").html(nums);
            }
            $(".c-selectBody-how").on("click",function(){  //绑定选择事件
                var p="";
                var abbr=$(this).attr("abbr");
                for(var i=0;i<$(".c-selectCity").length;i++){
                    if(!$(".c-selectCity").eq(i).hasClass("c-selectCity2")){
                        if($(".c-selectCity").eq(i).val()==$(this).text()){
                            p=1;
                        }
                    }
                }
                if(p!=1){
                    $(".c-selectCity2").val($(this).text()).attr("abbr",abbr);
                    $(".c-selectCity2").trigger('input');
                    $(".c-selectCity").nextAll().remove();
                    blur($(".c-selectCity2"),lists);
                }else {
                    if(ables==1){
                        storage=$(".c-selectCity2").val();
                        ables=0;
                    }
                    $(".c-selectCity2").addClass("c-vague-repeat").val("所选机场已存在");
                    setTimeout(function(){
                        $(".c-selectCity2").removeClass("c-vague-repeat").val(storage);
                        ables=1;
                    },500);
                }
            });
        });
        $(".c-selectBody-how").on("click",function(){
            var p="";
            var abbr=$(this).attr("abbr");
            for(var i=0;i<$(".c-selectCity").length;i++){
                if(!$(".c-selectCity").eq(i).hasClass("c-selectCity2")){
                    if($(".c-selectCity").eq(i).val()==$(this).text()){
                        p=1;
                    }
                }
            }
            if(p!=1){
                $(".c-selectCity2").val($(this).text()).attr("abbr",abbr);
                $(".c-selectCity2").trigger('input');
                $(".c-selectCity").nextAll().remove();
                blur($(".c-selectCity2"),lists);
            }else {
                if(ables==1){
                    storage=$(".c-selectCity2").val();
                    ables=0;
                }
                $(".c-selectCity2").addClass("c-vague-repeat").val("所选机场已存在");
                setTimeout(function(){
                    $(".c-selectCity2").removeClass("c-vague-repeat").val(storage);
                    ables=1;
                },500);
            }
        });
        $(".c-selectCityBox").on("click",function(e){
            e.stopPropagation(); //屏蔽事件冒泡
        });
    });
    $(".c-selectCity").on("input",function(){
    	$('._set-allList').css('display','none');//关闭航班列表框
        $(this).nextAll().remove();
        $(this).removeAttr("abbr");
        var ele="<div class='c-vague'>";
        var air=$(this).val().toUpperCase();
        var tagArr=[];
        var airportArr = [];
        var ables=1;
        var storage="";
        if(air!=""){
            for(var i=0;i<nationalAirport.length;i++){
                var airportName=nationalAirport[i].airportName;//机场名字
                var pinyin=nationalAirport[i].pinyin.toUpperCase();//全拼音
                var py=nationalAirport[i].py.toUpperCase();//首字母拼音
                var code=nationalAirport[i].code.toUpperCase();//机场三字码
                if(nationalAirport[i].citycode!=""){
                    var citycode=nationalAirport[i].citycode.toUpperCase();//城市三字码
                }else {
                    var citycode="";//城市三字码
                }
                var city=nationalAirport[i].city;//城市
                if(new RegExp(air).test(city)&&tagArr.indexOf(city)=="-1"){  //匹配城市名字
                    var cury=city.split(air);
                    if(cury.length==2){
                        if(cury[0]==""){
                            ele+="<div class='c-vague-clue'><span class='c-vague-opt'>"+air+"</span>"+cury[1]+"</div>";
                        }else if(cury[1]==""){
                            ele+="<div class='c-vague-clue'>"+cury[0]+"<span class='c-vague-opt'>"+air+"</span></div>";
                        }else {
                            ele+="<div class='c-vague-clue'>"+cury[0]+"<span class='c-vague-opt'>"+air+"</span>"+cury[1]+"</div>";
                        }
                    }
                    for(var j=0;j<nationalAirport.length;j++){
                        if(city==nationalAirport[j].city){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+nationalAirport[j].airportName+"-"+nationalAirport[j].code+"'><span class='c-vague-cityTag'>&#xe67a;</span>"+nationalAirport[j].airportName+""+nationalAirport[j].code.toUpperCase()+"</div>";
                            airportArr.push(nationalAirport[j].airportName);
                        }
                    }
                    tagArr.push(city);
                }else if(new RegExp(air).test(airportName)&&airportArr.indexOf(airportName)=="-1"){  //匹配机场名字
                    var cury=airportName.split(air);
                    if(cury.length==2){
                        if(cury[0]==""){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'><span class='c-vague-opt'>"+air+"</span>"+cury[1]+code+"</div>";
                        }else if(cury[1]==""){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+cury[0]+"<span class='c-vague-opt'>"+air+"</span>"+code+"</div>";
                        }else {
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+cury[0]+"<span class='c-vague-opt'>"+air+"</span>"+cury[1]+code+"</div>";
                        }
                    }
                    airportArr.push(airportName);
                }else if(new RegExp(air).test(code)){  //三字码
                    var cury=code.split(air);
                    var airC=air;
                    if(cury.length==2){
                        if(cury[0]==""){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+"<span class='c-vague-opt'>"+airC+"</span>"+cury[1]+"</div>";
                        }else if(cury[1]==""){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+cury[0]+"<span class='c-vague-opt'>"+airC+"</span></div>";
                        }else {
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+cury[0]+"<span class='c-vague-opt'>"+airC+"</span>"+cury[1]+"</div>";
                        }
                    }
                }else if(new RegExp(air).test(citycode)){  //城市code
                    if(ele.indexOf(citycode)>-1) break;
                    var cury=citycode.split(air);
                    var airC=air;
                    if(cury.length==2){
                        if(cury[0]==""){
                            ele+="<div class='c-vague-clue'>"+city+"<span class='c-vague-opt'>"+airC+"</span>"+cury[1]+"</div>";
                        }else if(cury[1]==""){
                            ele+="<div class='c-vague-clue'>"+city+"<span class='c-vague-opt'>"+airC+"</span></div>";
                        }else {
                            ele+="<div class='c-vague-clue'>"+city+"<span class='c-vague-opt'>"+airC+"</span>"+cury[1]+"</div>";
                        }
                    }
                    for(var j=0;j<nationalAirport.length;j++) {
                        if (city == nationalAirport[j].city) {
                            ele += "<div class='c-vague-clue c-vague-optional' abbr='"+nationalAirport[j].airportName+"-"+nationalAirport[j].code+"'><span class='c-vague-cityTag'>&#xe67a;</span>" + nationalAirport[j].airportName + "" + nationalAirport[j].code.toUpperCase() + "</div>";
                        }
                    }
                }else if(new RegExp(air).test(py)){  //首字母拼音
                    var cury=py.toLowerCase().split(air.toLowerCase());
                    var airC=air.toLowerCase();
                    if(cury.length==2){
                        if(cury[0]==""){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+"<span class='c-vague-opt'>"+airC+"</span>"+cury[1]+"</div>";
                        }else if(cury[1]==""){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+cury[0]+"<span class='c-vague-opt'>"+airC+"</span></div>";
                        }else {
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+cury[0]+"<span class='c-vague-opt'>"+airC+"</span>"+cury[1]+"</div>";
                        }
                    }
                }else if(new RegExp(air).test(pinyin)){  //全拼音
                    var cury=pinyin.toLowerCase().split(air.toLowerCase());
                    var airC=air.toLowerCase();
                    if(cury.length==2){
                        if(cury[0]==""){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+"<span class='c-vague-opt'>"+airC+"</span>"+cury[1]+"</div>";
                        }else if(cury[1]==""){
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+cury[0]+"<span class='c-vague-opt'>"+airC+"</span></div>";
                        }else {
                            ele+="<div class='c-vague-clue c-vague-optional' abbr='"+airportName+"-"+code+"'>"+airportName+cury[0]+"<span class='c-vague-opt'>"+airC+"</span>"+cury[1]+"</div>";
                        }
                    }
                }
            };
            if(ele=="<div class='c-vague'>"){
                ele+="<div class='c-vague-clue c-vague-setErr'>对不起，不支持该目的地</div>";
            }
        }
        $(this).after(ele);
        //去掉展示框中重复机场-start
        
        //去掉展示框中重复机场-end
        $(".c-vague-optional").eq(0).addClass("c-vague-setTag");
        $(".c-vague-clue").on("click",function(e){
            e.stopPropagation();
            if($(this).hasClass("c-vague-optional")){
                var tag=1;
                var nus=$(this).attr("abbr").split("-")[0].split("机场")[0].split("国际")[0];
                var abbr=$(this).attr("abbr");
                for(var i=0;i<$(".c-selectCity").length;i++){
                    if(!$(".c-selectCity").eq(i).hasClass("c-selectCity2")){
                        if(nus==$(".c-selectCity").eq(i).val()){
                            tag=0;
                        }
                    }
                }
                if(tag==1){
                    $(".c-selectCity2").val(nus).attr("abbr",abbr);
                    $(".c-selectCity").nextAll().remove();
                    blur($(".c-selectCity2"),lists);
                }else if(tag==0){
                    if(ables==1){
                        storage=$(".c-selectCity2").val();
                        ables=0;
                    }
                    $(".c-selectCity2").addClass("c-vague-repeat").val("所选机场已存在");
                    setTimeout(function(){
                        $(".c-selectCity2").removeClass("c-vague-repeat").val(storage);
                        ables=1;
                    },500)
                }
            }
           
        });
        $(".c-vague-optional").on("mouseover",function(){
            $(".c-vague-optional").removeClass("c-vague-setTag");
            $(this).addClass("c-vague-setTag")
        })
        if($(this).val()==""){
        	oub.back();
        }
    });
    $(".c-selectCity").on("keydown",function(e){  //按键事件
        var nus=$(".c-vague-setTag").attr("abbr") ? $(".c-vague-setTag").attr("abbr").split("-")[0].split("机场")[0].split("国际")[0] :'';
        var tag=$(".c-vague-setTag").attr("abbr");
        var p="";
        if(e.keyCode==13){
            for(var i=0;i<$(".c-selectCity").length;i++){
                if($(".c-selectCity").eq(i).attr("abbr")==""||$(".c-selectCity").eq(i).attr("abbr")==undefined){
                    p=1;
                }else {
                    if(tag==$(".c-selectCity").eq(i).attr("abbr")){
                        p=0;
                        break;
                    }
                }
            }
            if(p==1){
                $(".c-selectCity2").attr("abbr",tag).val(nus);
                $(".c-selectCity").nextAll().remove();
                blur($(".c-selectCity2"),lists);
                $(".c-selectCity2").blur();
            }else if(p==0){
                $(".c-selectCity2").addClass("c-vague-repeat").val("所选机场已存在");
                setTimeout(function(){
                    $(".c-selectCity2").removeClass("c-vague-repeat").val("");
                },500);
            }
        }else if(e.keyCode==38){
            var liT=parseInt($(".c-vague-optional").index($(".c-vague-setTag")));
            if(liT>0){
                $(".c-vague-optional").eq(liT).removeClass("c-vague-setTag");
                $(".c-vague-optional").eq(liT-1).addClass("c-vague-setTag");
            }
        }else if(e.keyCode==40){
            var liT=parseInt($(".c-vague-optional").index($(".c-vague-setTag")));
            var len=0;
            if($(".c-vague-clue").length>=6){
                for(var i=0;i<6;i++){
                    if($(".c-vague-clue").eq(i).hasClass("c-vague-optional")){
                        len+=1;
                    }
                }
            }else {
                for(var i=0;i<($(".c-vague-clue").length-1);i++){
                    if($(".c-vague-clue").eq(i).hasClass("c-vague-optional")){
                        len+=1;
                    }
                }
            }
            if(liT<len){
                $(".c-vague-optional").eq(liT).removeClass("c-vague-setTag");
                $(".c-vague-optional").eq(liT+1).addClass("c-vague-setTag");
            }
        }
    });
//    $(".c-selectCity").on("blur",function(){ //input失去焦点判定输入值
//        if($(this).attr("abbr")==undefined){
//            $(this).val("");
//            blur($(".c-selectCity2"),lists);
//        }else {
////            blur($(".c-selectCity2"),lists);
//        }
//    });
    $(".c-selectCity").on("blur",function(){ //input失去焦点判定输入值
        if($(".c-vague").length!=0&&$(".c-vague-setTag").length!=0){
        	$(".c-vague-setTag").click();
        }
    	if($(this).attr("abbr")==undefined){
            $(this).val("");
            blur($(".c-selectCity2"),lists);
        }else {
//            blur($(".c-selectCity2"),lists);
        }
    });
    $(".c-selectCity").on("focus",function(){  //input得到焦点判定输入值
    	Ocity=$(".c-selectCity2").val();
        $(".c-selectCity").removeClass("c-selectCity2");
        $(this).addClass("c-selectCity2");
        $(".c-selectCity").nextAll().remove();
    });
}
var Ocity="";
function blur(name,fun){
    var nu=true;
    setTimeout(function(){
        if(name.hasClass("c-selectChange-stp")){
            if(name.attr("abbr")==undefined){
                if(Ocity!=$(".c-selectCity2").val()){
                	if(nu==true){
                        fun.back();
                    }
                }
            }else {
                if(Ocity!=$(".c-selectCity2").val()){
                	if(nu==true){
                        fun.back();
                    }
                }
            }
        }else {
            if($(".c-selectChange-stp").length==0){
                if(Ocity!=$(".c-selectCity2").val()){
                	if(nu==true){
                        fun.back();
                    }
                }
            }else {
                if($(".c-selectChange-stp").attr("abbr")==undefined){
                    if(Ocity!=$(".c-selectCity2").val()){
                    	if(nu==true){
                            fun.back();
                        }
                    }
                }else {
                    if(Ocity!=$(".c-selectCity2").val()){
                    	if(nu==true){
                            fun.back();
                        }
                    }
                }
            }
        }
    },500);
}
var oub="";
//页面导航切换



