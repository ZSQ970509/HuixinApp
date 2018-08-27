package com.king.photo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class WeiHuProjectModel {

    /**
     * result : 1
     * msg : 获取维护列表成功
     * totalcount : 112
     * data : [{"ProjectId":20739,"ProjectName":"福州苏宁广场B13地块项目桩基工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-06-07 08:30","FailureNum":1,"ActualDistance":1.49},{"ProjectId":32380,"ProjectName":"中交隧道工程局有限公司福州地铁二号线第六标段项目经理部","CamfailReslt":"获取视频超时","CreateTime":"2018-03-15 13:37","FailureNum":2,"ActualDistance":2.82},{"ProjectId":34040,"ProjectName":"六一新苑（福州市商业汽车运输公司及东侧地块安置房建设项目）","CamfailReslt":"巡查","CreateTime":"2018-01-11 18:19","FailureNum":2,"ActualDistance":4.21},{"ProjectId":33514,"ProjectName":"世茂连潘天城项目","CamfailReslt":"故障申报","CreateTime":"2017-07-24 11:48","FailureNum":8,"ActualDistance":6.02},{"ProjectId":29044,"ProjectName":"福州市轨道交通2号线工程BT项目第七标段（西洋站、南门兜站、宁化站\u2014西洋站区间、西洋站\u2014南门兜站区间、南门兜站\u2014水部站区间）","CamfailReslt":"获取视频超时","CreateTime":"2018-03-15 13:45","FailureNum":3,"ActualDistance":7.03},{"ProjectId":30212,"ProjectName":"交通银行福建省分行营业大楼建设项目（施工）","CamfailReslt":"获取视频超时","CreateTime":"2017-09-17 11:28","FailureNum":3,"ActualDistance":8.1},{"ProjectId":42987,"ProjectName":"福兴经济开发区改造提升项目C1地块","CamfailReslt":"巡查","CreateTime":"2018-01-22 15:58","FailureNum":2,"ActualDistance":8.67},{"ProjectId":42837,"ProjectName":"福州世茂南通项目","CamfailReslt":"用户申报","CreateTime":"2018-02-27 17:11","FailureNum":10,"ActualDistance":13.89},{"ProjectId":39840,"ProjectName":"福建移动通信技术研发项目","CamfailReslt":"用户申报","CreateTime":"2018-02-26 13:23","FailureNum":2,"ActualDistance":14.6},{"ProjectId":33920,"ProjectName":"福州市轨道交通1号线二期工程第1标段（施工）项目","CamfailReslt":"巡查","CreateTime":"2018-03-15 13:09","FailureNum":7,"ActualDistance":14.74},{"ProjectId":35658,"ProjectName":"福州永通电线电缆有限公司改扩建项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2018-03-15 13:05","FailureNum":4,"ActualDistance":15.7},{"ProjectId":37627,"ProjectName":"县城新区水厂二期项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-29 18:16","FailureNum":2,"ActualDistance":16.36},{"ProjectId":23362,"ProjectName":"福建LNG监控调度中心项目主体工程","CamfailReslt":"获取视频超时","CreateTime":"2018-01-29 19:50","FailureNum":1,"ActualDistance":39.08},{"ProjectId":37750,"ProjectName":"水岸观溪住宅小区项目（地下室、幼儿园、8#-12#楼）","CamfailReslt":"巡查","CreateTime":"2018-01-03 16:08","FailureNum":1,"ActualDistance":42.22},{"ProjectId":38061,"ProjectName":"连江县透堡卫生院综合楼（含职工宿舍楼）项目","CamfailReslt":"巡查","CreateTime":"2017-12-29 09:33","FailureNum":1,"ActualDistance":47.22},{"ProjectId":43580,"ProjectName":"年产5万吨动力电池负极材料项目一期","CamfailReslt":"巡查","CreateTime":"2018-03-13 11:25","FailureNum":1,"ActualDistance":63.79},{"ProjectId":43563,"ProjectName":"涵江临港产业园标准厂房一期项目","CamfailReslt":"省站抽查","CreateTime":"2018-02-22 11:52","FailureNum":9,"ActualDistance":68.39},{"ProjectId":42924,"ProjectName":"莆田保利中央公园项目(1#~3#、5#~13#、15#~23#、25#~29#楼、地下室)","CamfailReslt":"巡查","CreateTime":"2018-03-05 14:32","FailureNum":1,"ActualDistance":70.48},{"ProjectId":42252,"ProjectName":"黄石七境园中村规划1#路至小学、幼儿园3条道路项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-23 15:18","FailureNum":3,"ActualDistance":70.85},{"ProjectId":38494,"ProjectName":"宁德金禾雅居建设工程项目","CamfailReslt":"省站抽查","CreateTime":"2018-03-14 10:58","FailureNum":3,"ActualDistance":72.76},{"ProjectId":30620,"ProjectName":"宁德火车站综合客运枢纽项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-17 17:17","FailureNum":6,"ActualDistance":73.52},{"ProjectId":38474,"ProjectName":"海坛古城项目（二期）J区（J2#-J4#、J12#-J27#及J区1号地下室）","CamfailReslt":"省站抽查","CreateTime":"2018-01-31 17:44","FailureNum":5,"ActualDistance":83.72},{"ProjectId":38438,"ProjectName":"屏南中学项目EPC","CamfailReslt":"巡查","CreateTime":"2018-01-24 10:49","FailureNum":2,"ActualDistance":97.58},{"ProjectId":42210,"ProjectName":"屏南县中心片区（棚户区）改造建设项目工程（一期）","CamfailReslt":"巡查","CreateTime":"2018-02-05 17:57","FailureNum":6,"ActualDistance":98.03},{"ProjectId":40068,"ProjectName":"福建海上风电制造基地建设项目","CamfailReslt":"巡查","CreateTime":"2018-02-05 10:45","FailureNum":3,"ActualDistance":98.89},{"ProjectId":42644,"ProjectName":"建瓯市妇幼保健院整体搬迁建设项目","CamfailReslt":"用户申报","CreateTime":"2018-03-02 15:05","FailureNum":2,"ActualDistance":142.09},{"ProjectId":38999,"ProjectName":"泉州市金鸡水厂工程（一期）项目第三标段","CamfailReslt":"省站抽查","CreateTime":"2018-01-22 16:30","FailureNum":2,"ActualDistance":145.28},{"ProjectId":43543,"ProjectName":"福鼎市文渡工业园区集中供热项目","CamfailReslt":"省站抽查","CreateTime":"2018-03-01 15:49","FailureNum":1,"ActualDistance":145.9},{"ProjectId":43365,"ProjectName":"兴田大厦及梅林国有林场综合服务中心建设项目","CamfailReslt":"巡查","CreateTime":"2018-01-24 11:11","FailureNum":1,"ActualDistance":150.52},{"ProjectId":45146,"ProjectName":"新城广场项目","CamfailReslt":"用户申报","CreateTime":"2018-02-26 17:28","FailureNum":2,"ActualDistance":153.04},{"ProjectId":36759,"ProjectName":"石狮世茂钞坑项目二期2012-37-01地块（一期）总承包Ι标段","CamfailReslt":"巡查","CreateTime":"2018-02-09 18:03","FailureNum":1,"ActualDistance":164.03},{"ProjectId":38543,"ProjectName":"三明恒大御府项目（一标段及三标段）主体及配套建设工程","CamfailReslt":"巡查","CreateTime":"2017-12-28 11:36","FailureNum":4,"ActualDistance":165.94},{"ProjectId":39006,"ProjectName":"九星军民两用行业应用无人机及复合材料制造产业化项目","CamfailReslt":"用户申报","CreateTime":"2018-01-22 16:27","FailureNum":4,"ActualDistance":182.14},{"ProjectId":34858,"ProjectName":"同安高新园区惠禾盛项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-09 17:54","FailureNum":3,"ActualDistance":182.27},{"ProjectId":30361,"ProjectName":"福建省翔安监狱行政区工程（福建省翔安监狱项目-15#狱政管理楼、16#综合楼、17#备勤房、20#配电室、开闭所、23#门卫(1#)(2#)(3#)）","CamfailReslt":"省站抽查","CreateTime":"2018-02-05 16:42","FailureNum":1,"ActualDistance":182.96},{"ProjectId":33842,"ProjectName":"汽配及冷链仓储项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-23 14:24","FailureNum":4,"ActualDistance":186.24},{"ProjectId":35981,"ProjectName":"厦门波士安达汽车销售服务有限公司奔驰汽车AH250店、综合楼项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-23 14:24","FailureNum":3,"ActualDistance":186.31},{"ProjectId":31334,"ProjectName":"同安区原食品厂三旧改造项目1#、2#楼","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-09-18 15:12","FailureNum":4,"ActualDistance":186.41},{"ProjectId":20800,"ProjectName":"ABB厦门工业中心项目","CamfailReslt":"故障申报","CreateTime":"2017-11-28 15:56","FailureNum":5,"ActualDistance":190.65},{"ProjectId":35729,"ProjectName":"金日制药（中国）有限公司T2014G06-G建设项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-15 11:49","FailureNum":5,"ActualDistance":191.5},{"ProjectId":31797,"ProjectName":"X2015G02-G地块项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-24 15:39","FailureNum":5,"ActualDistance":192.76},{"ProjectId":34860,"ProjectName":"X2010P02地块项目桩基工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-27 15:11","FailureNum":5,"ActualDistance":192.78},{"ProjectId":30183,"ProjectName":"福建省金湖电力有限责任公司范厝小区三旧改造项目","CamfailReslt":"获取视频超时","CreateTime":"2018-02-03 13:42","FailureNum":2,"ActualDistance":193.22},{"ProjectId":39363,"ProjectName":"将乐县水岸明珠二期开发项目高层5#、6#、7#、9#、10#和商业2#及别墅19#-28#","CamfailReslt":"巡查","CreateTime":"2018-01-04 17:44","FailureNum":1,"ActualDistance":194.46},{"ProjectId":20156,"ProjectName":"厦门演艺职业学院建设项目（2-2#\u20142-8#楼）","CamfailReslt":"故障申报","CreateTime":"2016-10-07 14:27","FailureNum":2,"ActualDistance":194.88},{"ProjectId":11802,"ProjectName":"新景国际城-菁英公馆(2013XP01地块）项目土方开挖及基坑支护工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2016-08-19 00:21","FailureNum":4,"ActualDistance":196.8},{"ProjectId":31951,"ProjectName":"集美北部新城区中央公园项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-17 17:43","FailureNum":3,"ActualDistance":200.57},{"ProjectId":27489,"ProjectName":"厦门材料研究院配套住宅建设项目一期（1#~4#楼、一期地下室）","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-07 09:47","FailureNum":7,"ActualDistance":200.58},{"ProjectId":19906,"ProjectName":"厦门2013JP03地块项目（永同昌广场）","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-30 15:07","FailureNum":4,"ActualDistance":200.7},{"ProjectId":28768,"ProjectName":"厦门市集美建设发展有限公司颐和花园商住小区项目","CamfailReslt":"省站抽查","CreateTime":"2018-03-12 16:30","FailureNum":5,"ActualDistance":201.99},{"ProjectId":44275,"ProjectName":"西客明珠安置房项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-11 14:30","FailureNum":2,"ActualDistance":202.37},{"ProjectId":32218,"ProjectName":"厦门恒大帝景项目二期（地块四）Ⅰ标段工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-06-21 10:19","FailureNum":6,"ActualDistance":202.39},{"ProjectId":30174,"ProjectName":"厦门恒大帝景项目首期（一标段 ）、二期（地块六）Ⅱ标段主体及配套建设工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-04-30 11:32","FailureNum":11,"ActualDistance":202.41},{"ProjectId":36266,"ProjectName":"轨道交通1号线岩内综合维修基地上部社会保障性住房项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-02 14:45","FailureNum":4,"ActualDistance":202.85},{"ProjectId":11918,"ProjectName":"集美·创意城项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-07 09:43","FailureNum":7,"ActualDistance":203.04},{"ProjectId":13720,"ProjectName":"正新地产集美项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-09-04 15:07","FailureNum":8,"ActualDistance":203.22},{"ProjectId":30216,"ProjectName":"住宅.莲花国际1#-2#地块商品房项目地下室及上部主体工程","CamfailReslt":"获取视频超时","CreateTime":"2017-12-27 14:51","FailureNum":2,"ActualDistance":204.4},{"ProjectId":10963,"ProjectName":"福建万翔现代有限公司物流仓储项目（2009Y06地块）工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2016-09-18 14:07","FailureNum":7,"ActualDistance":205.04},{"ProjectId":13818,"ProjectName":"厦门眼科中心扩建项目(二期)桩基工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-04-30 10:08","FailureNum":4,"ActualDistance":205.66},{"ProjectId":10251,"ProjectName":"厦门万银投资发展有限公司物流仓储项目(原厦门万翔网络商务有限公司物流仓储项目）（2009Y05地块）","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-28 09:31","FailureNum":7,"ActualDistance":205.86},{"ProjectId":20458,"ProjectName":"轨道交通1号线软件园站地块（±0.00以下部分）配套项目","CamfailReslt":"巡查","CreateTime":"2018-01-24 12:02","FailureNum":2,"ActualDistance":206.1},{"ProjectId":32431,"ProjectName":"厦门轨道交通1号线园博苑站温泉东地块项目工程","CamfailReslt":"故障申报","CreateTime":"2017-08-24 09:38","FailureNum":4,"ActualDistance":206.42},{"ProjectId":30762,"ProjectName":"内林社区琦珏商业中心及外口公寓(集体发展项目)","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-31 15:03","FailureNum":2,"ActualDistance":206.48},{"ProjectId":19559,"ProjectName":"2006G01-A4地块项目","CamfailReslt":"故障申报","CreateTime":"2017-10-27 13:58","FailureNum":1,"ActualDistance":207.01},{"ProjectId":28567,"ProjectName":"欣贺股份有限公司服装生产线及仓储物流配送中心建设项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-05-17 22:23","FailureNum":1,"ActualDistance":207.01},{"ProjectId":36471,"ProjectName":"厦门龙湖灌口项目5号地块四期（1-12号楼及地下室）","CamfailReslt":"获取视频超时","CreateTime":"2017-07-28 09:31","FailureNum":4,"ActualDistance":207.03},{"ProjectId":36477,"ProjectName":"厦门龙湖灌口项目4号地块六期（1和21号楼及地下室）","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-07-27 10:06","FailureNum":5,"ActualDistance":207.27},{"ProjectId":10513,"ProjectName":"万科湖心岛项目二期\u2014八期","CamfailReslt":"省站抽查","CreateTime":"2018-03-13 17:23","FailureNum":9,"ActualDistance":208.39},{"ProjectId":34105,"ProjectName":"集美轻工业学校和福建化工学校整合提升项目嘉庚大楼","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-07-29 11:27","FailureNum":2,"ActualDistance":208.71},{"ProjectId":43199,"ProjectName":"嘉晟供应链物流基地项目二期工程","CamfailReslt":"省站抽查","CreateTime":"2018-01-10 17:03","FailureNum":2,"ActualDistance":209.57},{"ProjectId":35881,"ProjectName":"原汇兴石材地块项目（华信广场）主体工程","CamfailReslt":"获取视频超时","CreateTime":"2017-08-19 12:04","FailureNum":1,"ActualDistance":210.28},{"ProjectId":30191,"ProjectName":"厦门市体育中心综合健身馆及育秀路公共停车场项目","CamfailReslt":"巡查","CreateTime":"2018-03-09 17:41","FailureNum":3,"ActualDistance":211.97},{"ProjectId":32231,"ProjectName":"厦门恒大帝景项目首期（一标段 ）、二期（地块六）Ⅱ标段主体及配套建设工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-08-22 10:16","FailureNum":5,"ActualDistance":212.97},{"ProjectId":35218,"ProjectName":"龙湖马銮湾H2015P05地块5#地块项目","CamfailReslt":"省站抽查","CreateTime":"2018-03-14 17:28","FailureNum":9,"ActualDistance":214.13},{"ProjectId":13352,"ProjectName":"东坑安居房项目","CamfailReslt":"获取视频超时","CreateTime":"2018-03-12 11:37","FailureNum":7,"ActualDistance":214.33},{"ProjectId":34212,"ProjectName":"厦顺四期一步铝箔生产线扩建项目","CamfailReslt":"巡查","CreateTime":"2018-01-05 09:54","FailureNum":4,"ActualDistance":214.65},{"ProjectId":30412,"ProjectName":"H2015G02地块项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-23 15:33","FailureNum":6,"ActualDistance":214.78},{"ProjectId":39253,"ProjectName":"H2016P02地块项目","CamfailReslt":"巡查","CreateTime":"2018-01-24 11:18","FailureNum":1,"ActualDistance":214.94},{"ProjectId":10214,"ProjectName":"泰禾海沧H2013P04地块项目","CamfailReslt":"获取视频超时","CreateTime":"2018-01-18 13:25","FailureNum":5,"ActualDistance":215.5},{"ProjectId":31074,"ProjectName":"福建中烟糖香料调配中心技术改造项目（一期）","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-05-21 13:51","FailureNum":1,"ActualDistance":215.98},{"ProjectId":32931,"ProjectName":"丹麦共歌感统产品基地项目","CamfailReslt":"获取视频超时","CreateTime":"2018-02-26 11:16","FailureNum":1,"ActualDistance":216.09},{"ProjectId":40174,"ProjectName":"厦门阳光恩耐照明有限公司LED照明智能化生产项目(四期）","CamfailReslt":"省站抽查","CreateTime":"2018-01-17 18:10","FailureNum":6,"ActualDistance":216.54},{"ProjectId":38673,"ProjectName":"照明器具制造（LED灯）项目二期\u20143#厂房","CamfailReslt":"省站抽查","CreateTime":"2018-01-05 18:38","FailureNum":2,"ActualDistance":216.55},{"ProjectId":20501,"ProjectName":"海沧H2014P05地块（华玺)项目基坑支护及土石方工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2018-03-15 13:45","FailureNum":8,"ActualDistance":216.57},{"ProjectId":11923,"ProjectName":"厦门大学附属第一医院内科综合大楼暨院区综合改造项目","CamfailReslt":"省站抽查","CreateTime":"2018-03-13 17:34","FailureNum":5,"ActualDistance":216.88},{"ProjectId":39252,"ProjectName":"致善厂房建设项目（二标段）","CamfailReslt":"巡查","CreateTime":"2018-03-13 11:28","FailureNum":1,"ActualDistance":217.59},{"ProjectId":35927,"ProjectName":"智能制造生产线建设项目","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-09-20 17:20","FailureNum":1,"ActualDistance":217.61},{"ProjectId":30219,"ProjectName":"忆通国际众创物流中心项目","CamfailReslt":"获取视频超时","CreateTime":"2018-03-11 12:33","FailureNum":3,"ActualDistance":218.24},{"ProjectId":43913,"ProjectName":"长泰县十里村石帽山2013年一期（J区）项目1#-3#楼住宅、5#-6#楼住宅、8#-13#楼住宅、15#-18#楼住宅、消防泵房及消防水箱","CamfailReslt":"省站抽查","CreateTime":"2018-03-08 15:53","FailureNum":3,"ActualDistance":218.66},{"ProjectId":37987,"ProjectName":"中国智能骨干网（漳州.台商投资区）项目一期","CamfailReslt":"巡查","CreateTime":"2018-01-30 17:11","FailureNum":1,"ActualDistance":219.02},{"ProjectId":39171,"ProjectName":"H2016P03地块项目","CamfailReslt":"巡查","CreateTime":"2018-01-24 11:20","FailureNum":3,"ActualDistance":219.1},{"ProjectId":40244,"ProjectName":"文化创作（泰宁）基地二期建设项目","CamfailReslt":"巡查","CreateTime":"2018-01-24 11:02","FailureNum":1,"ActualDistance":231.35},{"ProjectId":27391,"ProjectName":"碧湖棚户区改造项目（鸿浦豪园）","CamfailReslt":"获取视频超时","CreateTime":"2017-11-19 12:56","FailureNum":3,"ActualDistance":234.97},{"ProjectId":30647,"ProjectName":"西蕃莲棚户区危旧房改造项目","CamfailReslt":"获取视频超时","CreateTime":"2018-02-08 12:47","FailureNum":3,"ActualDistance":235.69},{"ProjectId":35854,"ProjectName":"漳州高新区龙江新苑项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-02 14:31","FailureNum":4,"ActualDistance":238.97},{"ProjectId":33404,"ProjectName":"龙岩融侨观邸项目第一标段","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2017-05-04 12:13","FailureNum":5,"ActualDistance":254.2},{"ProjectId":34792,"ProjectName":"宁化县医院新建PPP项目","CamfailReslt":"获取视频超时","CreateTime":"2017-11-24 12:45","FailureNum":4,"ActualDistance":259.65},{"ProjectId":39789,"ProjectName":"宁化县河龙贡米产业园建设项目一期","CamfailReslt":"巡查","CreateTime":"2017-12-28 11:34","FailureNum":4,"ActualDistance":266.81},{"ProjectId":32045,"ProjectName":"福建土楼梦幻剧场项目酒店式公寓员工宿舍项目","CamfailReslt":"获取视频超时","CreateTime":"2018-03-15 14:21","FailureNum":4,"ActualDistance":292.85},{"ProjectId":35403,"ProjectName":"龙岩市永定区体育中心田径运动场扩建项目","CamfailReslt":"获取视频超时","CreateTime":"2017-11-20 13:04","FailureNum":4,"ActualDistance":297.08},{"ProjectId":36043,"ProjectName":"永定区城南农副产品批发市场（城南综合市场）项目","CamfailReslt":"省站抽查","CreateTime":"2018-01-04 16:25","FailureNum":2,"ActualDistance":297.38},{"ProjectId":29264,"ProjectName":"乌石浦旧村改造项目-乌石浦花园安置房基坑及土石方工程","CamfailReslt":"机柜主电源中断或主光纤破坏","CreateTime":"2016-11-03 11:18","FailureNum":3,"ActualDistance":12920.09},{"ProjectId":45323,"ProjectName":"安溪县妇幼保健院勘察设计采购施工总承包（EPC）项目","CamfailReslt":"巡查","CreateTime":"2018-01-30 10:14","FailureNum":1,"ActualDistance":12920.09},{"ProjectId":38251,"ProjectName":"城南路市政工程项目南段","CamfailReslt":"巡查","CreateTime":"2018-01-23 11:54","FailureNum":2,"ActualDistance":12920.09},{"ProjectId":39169,"ProjectName":"莆田北岸总部经济区建设项目（地块三）办公区及配套工程（一期）","CamfailReslt":"省站抽查","CreateTime":"2017-12-28 11:17","FailureNum":3,"ActualDistance":12920.09},{"ProjectId":43583,"ProjectName":"水产品深加工项目（一期）","CamfailReslt":"省站抽查","CreateTime":"2018-02-01 11:45","FailureNum":2,"ActualDistance":12920.09},{"ProjectId":32191,"ProjectName":"福州市轨道交通2号线宁化站土建工程项目经理部","CamfailReslt":"获取视频超时","CreateTime":"2018-02-23 11:40","FailureNum":2,"ActualDistance":12920.09},{"ProjectId":39049,"ProjectName":"延平新城产业综合服务区木材加工工业仓储建设项目7#金属配件厂房","CamfailReslt":"巡查","CreateTime":"2018-01-24 10:00","FailureNum":3,"ActualDistance":12920.09},{"ProjectId":42574,"ProjectName":"永定一河两岸滨河生态休闲绿廊项目示范段（二期）东门桥至风景桥东岸","CamfailReslt":"省站抽查","CreateTime":"2017-12-29 19:08","FailureNum":1,"ActualDistance":12920.09},{"ProjectId":30827,"ProjectName":"福州市体育运动学校改扩建项目第2标段（施工）","CamfailReslt":"获取视频超时","CreateTime":"2017-08-28 12:59","FailureNum":4,"ActualDistance":12920.09},{"ProjectId":42152,"ProjectName":"厦门龙湖首开风景湖项目","CamfailReslt":"巡查","CreateTime":"2018-01-17 14:57","FailureNum":1,"ActualDistance":12920.09},{"ProjectId":28543,"ProjectName":"厦门轨道交通1号线园博苑站集杏海堤北侧地块配套项目(不含生产使用设备)","CamfailReslt":"故障申报","CreateTime":"2017-05-01 17:20","FailureNum":4,"ActualDistance":12920.09}]
     */

    private String result;
    private String msg;
    private String totalcount;
    private List<DataBean> data;

    public WeiHuProjectModel(String result, String msg, String totalcount, List<DataBean> data) {
        this.result = result;
        this.msg = msg;
        this.totalcount = totalcount;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * ProjectId : 20739
         * ProjectName : 福州苏宁广场B13地块项目桩基工程
         * CamfailReslt : 机柜主电源中断或主光纤破坏
         * CreateTime : 2017-06-07 08:30
         * FailureNum : 1
         * ActualDistance : 1.49
         */

        private String ProjectId;
        private String ProjectName;
        private String CamfailReslt;
        private String CreateTime;
        private String FailureNum;
        private String ActualDistance;
        private String ProjectLat;
        private String ProjectLng;
        public DataBean(String projectId, String projectName, String actualDistance,String projectLat ,String projectLng) {
            ProjectId = projectId;
            ProjectName = projectName;
            ActualDistance = actualDistance;
            ProjectLat = projectLat;
            ProjectLng = projectLng;
        }

        public DataBean(String projectId, String projectName, String camfailReslt, String createTime, String failureNum, String actualDistance,String projectLat ,String projectLng) {
            ProjectId = projectId;
            ProjectName = projectName;
            CamfailReslt = camfailReslt;
            CreateTime = createTime;
            FailureNum = failureNum;
            ActualDistance = actualDistance;
            ProjectLat = projectLat;
            ProjectLng = projectLng;
        }

        public String getProjectLat() {
            return ProjectLat;
        }

        public void setProjectLat(String projectLat) {
            ProjectLat = projectLat;
        }

        public String getProjectLng() {
            return ProjectLng;
        }

        public void setProjectLng(String projectLng) {
            ProjectLng = projectLng;
        }

        public String getProjectId() {
            return ProjectId;
        }

        public void setProjectId(String ProjectId) {
            this.ProjectId = ProjectId;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getCamfailReslt() {
            return CamfailReslt;
        }

        public void setCamfailReslt(String CamfailReslt) {
            this.CamfailReslt = CamfailReslt;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getFailureNum() {
            return FailureNum;
        }

        public void setFailureNum(String FailureNum) {
            this.FailureNum = FailureNum;
        }

        public String getActualDistance() {
            return ActualDistance;
        }

        public void setActualDistance(String ActualDistance) {
            this.ActualDistance = ActualDistance;
        }
    }
}
