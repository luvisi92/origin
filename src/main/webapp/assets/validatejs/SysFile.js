var saveSysFileConfig = {
	folderId:[{rule:length,params:{min:0,max:100},msg:"文件夹 长度不在0-100范围"}],
	fileName:[{rule:length,params:{min:0,max:200},msg:"文件名 长度不在0-200范围"}],
	descn:[{rule:length,params:{min:0,max:256},msg:"描述 长度不在0-256范围"}],
	path:[{rule:length,params:{min:0,max:200},msg:"文件路径 长度不在0-200范围"}],
	type:[{rule:length,params:{min:0,max:100},msg:"文件类型 长度不在0-100范围"}],
	extension:[{rule:length,params:{min:0,max:20},msg:"扩展名 长度不在0-20范围"}],
	size:[{rule:integer,msg:"文件大小 必须是数字"}],
	refId:[{rule:length,params:{min:0,max:32},msg:"对象主键 长度不在0-32范围"}],
	refObj:[{rule:length,params:{min:0,max:50},msg:"引用对象 长度不在0-50范围"}],
	locked:[{rule:integer,msg:"是否锁定(0:否,1:是) 必须是数字"}],
	userId:[{rule:integer,msg:"上传人ID 必须是数字"}],
	username:[{rule:length,params:{min:0,max:32},msg:"上传人名称 长度不在0-32范围"}],
	extField:[{rule:length,params:{min:0,max:30},msg:"扩展字段 长度不在0-30范围"}],
	createTime:[{rule:length,params:{min:0,max:19},msg:"创建时间 长度不在0-19范围"}]
};
