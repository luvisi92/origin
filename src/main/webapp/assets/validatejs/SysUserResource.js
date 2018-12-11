var saveSysUserResourceConfig = {
	userId:[{rule:notBlank,msg:"user_id 不能为空"},{rule:integer,msg:"user_id 必须是数字"}],
	resource:[{rule:notBlank,msg:"resource 不能为空"},{rule:length,params:{min:0,max:150},msg:"resource 长度不在0-150范围"}]
};
