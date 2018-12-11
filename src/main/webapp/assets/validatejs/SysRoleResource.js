var saveSysRoleResourceConfig = {
	roleId:[{rule:notBlank,msg:"role_id 不能为空"},{rule:integer,msg:"role_id 必须是数字"}],
	resource:[{rule:notBlank,msg:"resource 不能为空"},{rule:length,params:{min:0,max:255},msg:"resource 长度不在0-255范围"}]
};
