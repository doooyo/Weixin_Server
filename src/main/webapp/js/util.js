;(function(global,$){
	/**
	 * /base on jquery ,jquery easyui.	 
	 */
	/*
	* 是否存在数组中
	 */
	Array.prototype.contains = function (obj) {  
	    var i = this.length;  
	    while (i--) {  
	        if (this[i] === obj) {  
	            return true;
	        }  
	    }  
	    return false;  
	}   
	/**  
	* 得到一个数组不重复的元素集合<br/>  
	* 唯一化一个数组  
	* @returns {Array} 由不重复元素构成的数组  
	*/  
	Array.prototype.uniquelize = function(){  
	     var ra = new Array();  
	     for(var i = 0; i < this.length; i ++){  
	         if(!ra.contains(this[i])){  
	            ra.push(this[i]);  
	         }  
	     }  
	     return ra;  
	};

	/*
	* @param {Function} fn 进行迭代判定的函数  
	* @param more ... 零个或多个可选的用户自定义参数  
	* @returns {Array} 结果集，如果没有结果，返回空集  
	*/  
	Array.prototype.each = function(fn){  
	    fn = fn || Function.K;  
	     var a = [];  
	     var args = Array.prototype.slice.call(arguments, 1);  
	     for(var i = 0; i < this.length; i++){  
	         var res = fn.apply(this,[this[i],i].concat(args));  
	         if(res != null) a.push(res);  
	     }  
	     return a;  
	}; 

	/**
	* @param {Array} a 集合A  
	* @param {Array} b 集合B  
	* @returns {Array} 两个集合的差集  
	*/  
	Array.minus = function(a, b){  
	     return a.uniquelize().each(function(o){return b.contains(o) ? null : o});  
	}; 

	/* @param {Array} a 集合A  
	* @param {Array} b 集合B  
	* @returns {Array} 两个集合的并集  
	*/  
	Array.union = function(a, b){  
	     return a.concat(b).uniquelize();  
	}; 

	Date.prototype.Format = function (fmt) {
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	};
	var whayer = {
		proxy:false,//是否启用代理
		messager:function(title,msg,timeout){
			$.messager.show({
                title:title,
                msg:msg,
                timeout:timeout||2000,
                showType:'slide'
            });
		},
		showLoading:function(){
			$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");   
    		$("<div class=\"datagrid-mask-msg\"></div>").
    		html("正在处理，请稍候...").
    		appendTo("body").
    		css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2,'font-size':'12px'}); 
		},
		hideLoading:function(){
			$(".datagrid-mask").remove();   
     		$(".datagrid-mask-msg").remove();  
		},
		post:function(url,data,successCallback,errorCallback){
			var _this = this;
			if(_this.proxy){
				url='/proxy'+url;
			}
			_this.showLoading();
			$.ajax({
				method:'post',
				data:data,
				// traditional: false,
				url:url,
				success:function(res){
					_this.hideLoading();
					if(res.isSuccess){
						successCallback&&successCallback(res);
						// if(!res.list){
						// 	_this.messager('提示','未查询到相关数据!');
						// }else{
						// 	successCallback&&successCallback(res);
						// }					
					}else{					
						if(errorCallback){
							errorCallback&&errorCallback(res);
						}else{
							_this.messager('提示',res.errorMsg);
						}						
					}					
				},
				error:function(res){
					_this.hideLoading();
					_this.messager('提示','请求失败!');
					errorCallback&&errorCallback();					
				}
			});
		},
		get:function(url,data,successCallback,errorCallback){
			var _this = this;
			//url = url.replace('/proxy','');
			if(_this.proxy){
				url='/proxy'+url;
			}
			_this.showLoading();
			$.ajax({
				method:'get',
				data:data,
				url:url,
				success:function(res){
					_this.hideLoading();
					if(res.isSuccess){
						successCallback&&successCallback(res);
						// if(!res.list){
						// 	_this.messager('提示','未查询到相关数据!');
						// }else{
						// 	successCallback&&successCallback(res);
						// }						
					}else{
						if(errorCallback){
							errorCallback&&errorCallback(res);
						}else{
							_this.messager('提示',res.errorMsg);
						}
					}	
				},
				error:function(res){
					_this.hideLoading();
					_this.messager('提示','请求失败!');
					errorCallback&&errorCallback();	
				}
			});
		},
		submitForm:function(url,formData,successCallback,errorCallback){
			var _this = this;
			if(_this.proxy){
				url='/proxy'+url;
			}
			_this.showLoading(); 
		     $.ajax({  
			      url:url,  
			      type:"post",  
			      data:formData,  
			      cache: false,  
			      processData: false,  
			      contentType: false,  
			      success:function(res){ 
			      	_this.hideLoading();
			      	if(res.isSuccess){
						successCallback&&successCallback(res);					
					}else{
						if(errorCallback){
							errorCallback&&errorCallback(res);
						}else{
							_this.messager('提示',res.errorMsg);
						}
					}			        
			      },  
			      error:function(error){  
			        _this.hideLoading();
					_this.messager('提示','请求失败!');
					errorCallback&&errorCallback(error);
			      }  
		     });
		},
		addCookie:function(key,value){
			
		},
		deleteCookie:function(key){
			
		}
	};
	global.whayer = whayer;
})(window,jQuery);