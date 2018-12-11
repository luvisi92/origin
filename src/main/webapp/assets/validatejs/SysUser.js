var saveSysUserConfig = {
	fullname:[{rule:notBlank,msg:"姓名 不能为空"},{rule:length,params:{min:0,max:255},msg:"姓名 长度不在0-255范围"}],
	username:[{rule:notBlank,msg:"登录名 不能为空"},{rule:length,params:{min:0,max:255},msg:"登录名 长度不在0-255范围"}],
	password:[{rule:notBlank,msg:"密码(MD5加密后) 不能为空"},{rule:length,params:{min:0,max:255},msg:"密码(MD5加密后) 长度不在0-255范围"}],
	email:[{rule:length,params:{min:0,max:255},msg:"邮箱 长度不在0-255范围"}],
	lastLoginTime:[{rule:length,params:{min:0,max:19},msg:"最后登录时间 长度不在0-19范围"}],
	status:[{rule:integer,msg:"状态(0:已删除,1:启用,2:禁用) 必须是数字"}],
	isSuperadmin:[{rule:integer,msg:"类型(0:普通管理员,1:超级管理员) 必须是数字"}],
	remark:[{rule:length,params:{min:0,max:255},msg:"备注 长度不在0-255范围"}]
};
