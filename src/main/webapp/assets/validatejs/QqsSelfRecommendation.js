var saveQqsSelfRecommendationConfig = {
	name:[{rule:notBlank,msg:"供应商名称 不能为空"},{rule:length,params:{min:0,max:50},msg:"供应商名称 长度不在0-50范围"},],
	address:[{rule:notBlank,msg:"供应商地址 不能为空"},],
	properties:[{rule:notBlank,msg:"企业性质 不能为空"},],
	url:[{rule:notBlank,msg:"供应商网址 不能为空"},{rule:regex,params:/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/,msg:"网址输入不正确，请以http://或者https://开头"},],
	establishDate:[{rule:notBlank,msg:"公司成立时间 不能为空"},],
	registeredCapital:[{rule:notBlank,msg:"注册资本 不能为空"},],
	legalRepresentative:[{rule:notBlank,msg:"法人代表 不能为空"},],
	tel:[{rule:notBlank,msg:"联系电话 不能为空"},{rule:regex,params:/(^(\d{3,4}-)?\d{7,8})$|(1[3|4|5|7|8][0-9]{9})/,msg:"请输入正确的联系电话"},],
	fax:[{rule:notBlank,msg:"传真 不能为空"},{rule:regex,params:/(^(\d{3,4}-)?\d{7,8})$|(1[3|4|5|7|8][0-9]{9})/,msg:"请输入正确的传真号码"},],
	contactsPerson:[{rule:notBlank,msg:"业务联系人 不能为空"},],
	contactsTel:[{rule:notBlank,msg:"联系电话 不能为空"},{rule:regex,params:/(^(\d{3,4}-)?\d{7,8})$|(1[3|4|5|7|8][0-9]{9})/,msg:"请输入正确的联系电话"},],
	contactsFax:[{rule:notBlank,msg:"传真 不能为空"},{rule:regex,params:/(^(\d{3,4}-)?\d{7,8})$|(1[3|4|5|7|8][0-9]{9})/,msg:"请输入正确的传真号码"}]
};
