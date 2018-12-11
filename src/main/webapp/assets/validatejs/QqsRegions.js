var saveQqsRegionsConfig = {
	pRegionId:[{rule:integer,msg:"父级区域 必须是数字"}],
	regionPath:[{rule:length,params:{min:0,max:255},msg:"region_path 长度不在0-255范围"}],
	regionGrade:[{rule:integer,msg:"等级 必须是数字"}],
	localName:[{rule:notBlank,msg:"区域名称 不能为空"},{rule:length,params:{min:0,max:100},msg:"区域名称 长度不在0-100范围"}],
	code:[{rule:length,params:{min:0,max:10},msg:"地区编码 长度不在0-10范围"}],
	zipcode:[{rule:length,params:{min:0,max:20},msg:"邮政编码 长度不在0-20范围"}],
	cod:[{rule:length,params:{min:0,max:4},msg:"cashondelivery货到付款 长度不在0-4范围"}],
	pinyin:[{rule:length,params:{min:0,max:100},msg:"拼音 长度不在0-100范围"}],
	isActiveCity:[{rule:integer,msg:"是否是激活城市(0:否;1:是;) 必须是数字"}]
};
