var saveQqsJoinCooperationConfig = {
	name:[{rule:notBlank,msg:"姓名 不能为空"},{rule:length,params:{min:0,max:50},msg:"姓名 长度不在0-50范围"}],
	tel:[{rule:notBlank,msg:"联系电话 不能为空"},{rule:length,params:{min:0,max:20},msg:"联系电话 长度不在0-20范围"}],
	code:[{rule:notBlank,msg:"验证码不能为空"}],
};
