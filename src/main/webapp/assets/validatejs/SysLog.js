var saveSysLogConfig = {
	operation:[{rule:length,params:{min:0,max:50},msg:"操作 长度不在0-50范围"}],
	operContent:[{rule:length,params:{min:0,max:65535},msg:"操作内容 长度不在0-65535范围"}],
	userId:[{rule:length,params:{min:0,max:36},msg:"操作人账号 长度不在0-36范围"}],
	username:[{rule:length,params:{min:0,max:255},msg:"操作人 长度不在0-255范围"}],
	userAgent:[{rule:length,params:{min:0,max:256},msg:"客户端 长度不在0-256范围"}],
	ip:[{rule:length,params:{min:0,max:100},msg:"IP 长度不在0-100范围"}],
	operDate:[{rule:length,params:{min:0,max:19},msg:"操作日期 长度不在0-19范围"}]
};
