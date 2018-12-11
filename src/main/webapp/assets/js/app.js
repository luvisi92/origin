function clearForm(myformId) {
    $(':input','#' +myformId)
    .not(':button, :submit, :reset, :hidden')  
    .val('')  
    .removeAttr('checked')  
    .removeAttr('selected'); 
    $(".select2").val("").trigger("change");
}

function clearFormAll(myformId) {
    $(':input','#' + myformId)
    .not(':button, :submit')
    .val('')
    .removeAttr('checked')
    .removeAttr('selected');
}

$(document).ready(function(){
	if ($(".dataList").length != 0) {
		$(".dataList tbody tr").click(function(){
			if ($(".nodata",this).length > 0) {
				return;
			}
			if ($(this).hasClass("active")) {
				$(this).removeClass("active");
			} else {
				$(this).addClass("active");
			}
			
			var cb = $("input[type=checkbox]",this);
			
			if (cb.prop("checked")) {
				cb.prop("checked",false);
			} else {
				cb.prop("checked",true);
			}
		});
		$(".dataList tbody tr input[type=checkbox]").click(function(e){
			e.stopPropagation();
		});
	}
	
	$('#checkAll').click(function(){
        var bischecked=$('#checkAll').prop('checked');
        var ids=$('.dataList input[type=checkbox]');
        if (bischecked){
        	ids.prop('checked',true);	
        	$(".dataList tbody tr").addClass("active");
        } else {
        	ids.prop('checked',false);
        	$(".dataList tbody tr").removeClass("active");
        }
    });

	
	$(".submitForm").click(function(){
		$("#startId").val(0);
		$("#limitId").val($("#pageSizeId").val());
		$("#searchForm").submit();
		return false;
	});
	
	$(".clearForm").click(function(){
		clearForm("searchForm");
		return false;
	});
	
	$(".refreshForm").click(function(){
		refreshList();
	});
	
	$("#limitId").bind("change",function(e,index){
		refreshList();
	});	
	$("select").each(function(){
		if ($(this).attr("val") != "undefined"){
			var opts = $("option", this);
			for(var i = 0; i <opts.length;i++) {
				if ($(this).attr("val") == opts[i].value) {
					$(opts[i]).attr("selected", true);
				}			
			}
		}
	});
	
	$(".cleanBtn").click(function() {
		var ids = $(this).attr("rel").split(",");
		for(var i = 0; i < ids.length; i++)
			$("#"+ids[i]).val('').removeAttr('checked').removeAttr('selected');
	});
});



function toPage(page) {
	$("#startId").val(page);
	document.getElementById("searchForm").reset();
	$("#searchForm").submit();
}

function refreshList() {
	if ($("#searchForm").length != 0) {
		$("#searchForm").submit();
	}
}

function del(url,params,callbackFun){
	Dialog.confirm({message: "确定要删除吗？"}).on(function(callback){
	if (callback)
	{
		$.ajax({
	        type : 'POST',
	        url : url,
	        data : params,  
	        dataType : 'json',
	        traditional:true,
	        success : function(result){
	        	if (callbackFun != undefined)
        		{
	        		callbackFun(result);
	        		return;
        		}
	        	switch(result.code){
	        	case 401:
	        		var n = noty({
			            text        : result.message,
			            type        : 'error',
			            dismissQueue: true,
			            layout      : 'topCenter',
			            theme       : 'relax',
			            timeout		: 1500
			        });
        			break;
        		default:
	        		if (result.success){
		        		var n = noty({
				            text        : result.message,
				            type        : 'success',
				            dismissQueue: true,
				            layout      : 'topCenter',
				            theme       : 'relax',
				            timeout		: 1500,
				            callback: {     // 设置回调函数
						        afterClose: function() {
						        	refreshList();
						        }
						    }
				        });
		        	}else{
		        		var n = noty({
				            text        : result.message,
				            type        : 'error',
				            dismissQueue: true,
				            layout      : 'topCenter',
				            theme       : 'relax',
				            timeout		: 1500
				        });
		        	}
	        	}
	        },
	        cache:false,
	        error : function(){
	           top.Dialog.alert("删除失败！");
	        }
	    });
	}
	});
}

function onAction(conf){
	var args = conf;
	
	if (conf.url == undefined){
		var n = noty({
            text        : "onAction 参数url未配置",
            type        : 'warning',
            dismissQueue: true,
            layout      : 'topCenter',
            theme       : 'relax',
            timeout		: 1500
        });
		return false;
	}
	
	
	//////////////////////////////////////
	if(args.mutiple != undefined) {
		if (args.params == undefined)
			args.params = {};
		if (args.mutiple) {//多选
			var length = $(".dataList tbody input:checked").length;
			if (length == 0) {
				var n = noty({
		            text        : "请选择!",
		            type        : 'warning',
		            dismissQueue: true,
		            layout      : 'topCenter',
		            theme       : 'relax',
		            timeout		: 1500
		        });
				return false;
			} else if (($(".nodata",$(".dataList tbody input:checked").parent().parent().parent()).length > 0)) {
				var n = noty({
		            text        : "选择无效!",
		            type        : 'warning',
		            dismissQueue: true,
		            layout      : 'topCenter',
		            theme       : 'relax',
		            timeout		: 1500
		        });
				return false;
			}
			
			var ids = [];
			$(".dataList tbody input:checked").parent().parent().each(function(index){
				if($(this).find(".pk").length > 1) {
					$(this).find(".pk").each(function(){
						args.params['ids['+index+']["'+$(this).attr("name")+'"]'] = $(this).attr("title");
					});
				} else {
					$(this).find(".pk").each(function(){
						ids[index] = $(this).attr("title");
					});
				}
			});
			args.params['ids'] = ids;
		} else {//单选
			var length = $(".dataList tbody input:checked").length;
			if (length == 0) {
				var n = noty({
		            text        : "请选择!",
		            type        : 'warning',
		            dismissQueue: true,
		            layout      : 'topCenter',
		            theme       : 'relax',
		            timeout		: 1500
		        });
				return false;
			} else if (length > 1) {		
				var n = noty({
		            text        : "请选择一条记录!",
		            type        : 'warning',
		            dismissQueue: true,
		            layout      : 'topCenter',
		            theme       : 'relax',
		            timeout		: 1500
		        });
				return false;
			} else if (($(".nodata",$(".dataList tbody input:checked").parent().parent()).length > 0)) {
				var n = noty({
		            text        : "选择无效!",
		            type        : 'warning',
		            dismissQueue: true,
		            layout      : 'topCenter',
		            theme       : 'relax',
		            timeout		: 1500
		        });
				return false;
			}
			$(".dataList tbody input:checked").parent().parent().find(".pk").each(function(){
				if (args.pname)
				{
					args.params[args.pname] = $(this).attr("title");
				} else 
				{
					args.params[$(this).attr("name")] = $(this).attr("title");
				}
			});
		}
	}
	
	if (args.confirm){
		if (args.message == undefined)
			args.message = '操作确认';
		Dialog.confirm({message:args.message}).on(function(callback){if (callback) onView(args);});
	} else {
		try{
			onView(args);
		}catch(e){
			alert(e);
		}
	}
	return false;
}
var callback;
//{url:'',width:'',height:'',params:{id:'',other:''}}
function onView(config) {
	config = $.extend({ajax:true},config);
	callback = config.callback;
	var params = config.params||{};
	
	for(p in params) {
		var val = params[p];
		if ($.isArray(val)){
			for(var i = 0; i < val.length; i++)				
				$("#postForm").append('<input type="hidden" name="' + p + '" value="' + val[i] + '"/>');		
		} else {
			$("#postForm").append('<input type="hidden" name="' + p + '" value="' + val + '"/>');		
		}
	}
	if (config.ajax) {
		$('#myModalLabel').html(config.title);  
		if(config.width !=undefined){
			$('.modal-dialog').width(config.width);
		}else{
			$('.modal-dialog').width(600);
		}
		$('#myModel .modal-body').html('<div class="col-sm-7 col-xs-offset-5"><img src="'+ctx+'assets/images/loading.gif"/></div>&nbsp;</div>');  
		$.ajax({  
	        type:'post',  
	        traditional :true,  
	        url:config.url,  
	        data:params,  
	        success:function(data){  
	        	if (data.success != null)
	    		{
	    			if (data.success)
	    			{
	    				var n = noty({
				            text        : data.message,
				            type        : 'success',
				            dismissQueue: true,
				            layout      : 'topCenter',
				            theme       : 'relax',
				            timeout		: 1500,
				            callback: {     // 设置回调函数
						        afterClose: function() {
						        	if (config.callback){
						        		config.callback.call();
						        	}
						        }
						    }
				        });
	    			}else {
	    				var n = noty({
	    					text        : data.message,
	    					type        : 'error',
	    					dismissQueue: true,
	    					layout      : 'topCenter',
	    					theme       : 'relax',
	    					timeout		: 1500,
				            callback: {     // 设置回调函数
						        afterClose: function() {
						        	if (config.callback){
						        		config.callback.call();
						        	}
						        }
						    }
	    				});
	    			}
	    			$('#myModel').hide();  
	    		} else {
	    			$('#myModel .modal-body').html(data);  
	    		}
	        },
	        error:function(data){
	        	alert('');
	        }
	    });
		$("#myModel").modal({show:true,backdrop:false}) ;
	} else {
		$("#postForm").attr("action",config.url);
		$("#postForm").submit();
	}
	return false;
}

