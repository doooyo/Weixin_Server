<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="static/jquery-3.2.0.js"></script>
<script>
$(function(){
	//注意get参数本质是username=test&password=123456
	//   post参数如何使用{} 或 $.param({}) 其本质也是转为了username=test&password=123456
	//若使用JSON.stringify({}) 转化为了json字符串,后台就不能以request.getParameter来获取值
	//即使request.getParameter能获取get或post提交的参数,但实际上也只能获取上述类型的参数
	/* $.ajax({
        type: "POST",
        url: "/user/login",
        data: $.param({username: 'test', password: '123456'}),//{username: 'test', password: '123456'}也可
        dataType: "json",
        success: function(data){
          console.log(data);          
        }
    });  */
	
    //注意POST 如果传递的是json对象,则不能使用'application/json;charset=utf-8',直接传递对象
    //如果传递的是json字符串:首先必须设置请求头'application/json;charset=utf-8'
    //                   其次必须使用 @RequestBody,默认接收的enctype (MIME编码)是application/json
    //                   最后必须将请求的json转为字符串JSON.stringify(params)
    //@see http://blog.csdn.net/moshenglv/article/details/51973325
	/* $.ajax({
		type: "POST",
        url: "/user/approval/audit",
        data: {ids: ['002', 'be9e9b0c-39df-456b-9223-c03fe0f3e77c']},
        dataType: "json",
        //contentType : 'application/json;charset=utf-8',
        success: function(data){
          console.log(data);          
        }
	})*/
	
	/* $.ajax({
		type: "POST",
        url: "/product2role/associate",
        data: {ids: ['D54CACEA241849AFBE2C5FD66545FAB5', '3FE2A699E16D4F05BCC34572DDD89BF5'], 
        	role: 'pQ8wQqDt'},
        dataType: "json",
        //contentType : 'application/json;charset=utf-8',
        success: function(data){
          console.log(data);          
        }
	}) */
	
	$.ajax({
		type: "POST",
        url: "/order/save",
        data: JSON.stringify({
        	productIdList:'FE13F756DA374B099C20E27E4F93CBF4,E7491E5B907547A7970C1830830EFCC6',
        		//['2FC0791BAC744601893E78F1EA599FEB', '3FE2A699E16D4F05BCC34572DDD89BF5','4DFFB6884C12438DBE2694230E27EE22', '6F8F18D75BB84833BBC54DE379CC5F55'].join(','),
			couponId:'lMoumzDQ',//'5WR8ZUml',
			vouchersId:'ClHfxTMh',//['8ZF66dIr', '9OePRmio', 'BctTVjRq'].join(','),
			examinee:{
				name:'李某',
				age:20,
				address:'成都高新区天府大道5段',
				gender:0,//	0/false:男，1/true：女 
				mobile:'18236524578',
				identityId:'500225198911055',
				birthday:'1988-01-05'
			},
			amount:2500,
			isInvoice:true,
			userId:'be9e9b0c-39df-456b-9223-c03fe0f3e77c'
        }),
        dataType: "json",
        contentType : 'application/json;charset=utf-8',
        success: function(data){
          console.log(data);          
        }
	}) 
	
	
	
})
</script>
</head>
<body>
	test page
</body>
</html>