<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="static/jquery-3.2.0.js"></script>
<script>
$(function(){
	$.ajax({
        type: "POST",
        url: "/login",
        data: {username: 'test', password: '123456'},
        dataType: "json",
        success: function(data){
          console.log(data);          
        }
    });
	
	/* $.ajax({
		type: "POST",
        url: "/login/approval/audit",
        data: {ids: []},
        dataType: "json",
        success: function(data){
          console.log(data);          
        }
	}) */
})
</script>
</head>
<body>
	test page
</body>
</html>