var saveQqsJoinConfig = {
	name:[{rule:notBlank,msg:"姓名 不能为空"},{rule:length,params:{min:0,max:50},msg:"姓名 长度不在0-50范围"}],
	tel:[{rule:notBlank,msg:"联系电话 不能为空"},{rule:regex,params:/(^(\d{3,4}-)?\d{7,8})$|(1[3|4|5|7|8][0-9]{9})/,msg:"请输入正确的联系电话"}]
};
