<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	boolean verify;
	try{
		verify = (boolean)session.getAttribute("verify");
	}
	catch(Exception e){
		verify = false;
	}
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<script src="statics/js/jquery-3.6.0.js"></script>
<script src="statics/js/all.min.js"></script>
<link rel="stylesheet" href="statics/css/bootstrap.css"></link>
<link rel="stylesheet" href="statics/css/fontawesome.min.css"></link>
<style>
	body{
		background-color:#525252;
	}
	header{
		text-align: center;
		font-size:34px;
	}
	a{
		text-decoration: none;
		cursor: pointer;
	}
	#head{
		background-color: #E8CA78;
		font-size: 20px;
		padding-left:10px;
		padding-right:10px;
	}
	#head a{
		color: #000;
	}
	#head a:hover{
		color: #fff;
	}
	#form{
		top:50%;
		left:50%;
		padding: 30px 30px 30px 30px;
		background-color:#313131;
		border-radius: 5%;
		color: #fff;
		font-size: 18px;
	}
	.form-header{
		text-align: center;
		font-size: 28px;
		color: #fff;
	}
	.account-problem{
		font-size: 12px;
	}
	.input-area{
		margin: 6% 0 6% 0;
	}
	.input-area a{
		text-decoration: none;
		color: #D0DFE6;
		font-style: italic;
	}
	.input-area a:hover{
		color: #fff;
	}
	.steven-form-element{
		padding: 2%;
	}
	.steven-btn-light{
		border: 0;
		border-radius: 24px;
		background-color: #2C2C2C;
		color: #fff;
		outline: none;
		display: block;
		border: 1.5px solid #83FFE6;
	}
	.steven-btn-light:hover{
		color: #000;
		background-color: #E6E6E6;		
	}
	.steven-text-light{
		border: 0;
		border-radius: 24px;
		background-color: #2C2C2C;
		color: #fff;
		outline: none;
		display: block;
		border: 1.5px solid #83FFE6;
	}
	.steven-text-light::placeholder{
		font-style: italic;
	}
	
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row align-items-center" id="head">
			<div class="col-3">
				<a id="join" href="/WebApp/Join.jsp">JOIN US</a>
				</br>
				<a id="upload" href="/WebApp/UploadInfo.jsp">Upload Restaurant Information</a>
			</div>
			<header class="col-6"><a href="/WebApp/Home.jsp">T I T L E</a></header>
			<div class="col-1">
				<a id="logout"><i class="fas fa-sign-out-alt">Logout</i></a>
				<a id="login" href="/WebApp/Login.jsp"><i class="fas fa-sign-in-alt">Login</i></a>
			</div>
			<a id="account" class="col-1">Account</a>
		</div>
	</div>
</body>
<script>
	var height = $(this).height();
	var verify = <%=verify%>
	
	$(this).ready(function(){
		setSize();
		if (verify){
			$("#logout").show();
			$("#login").hide();
			$("#account").show();
		}
		else{
			$("#logout").hide();
			$("#login").show();
			$("#account").hide();
		}
	})
	
	function setSize(){
		$(".container-fluid").height(height);
		$("#head").height(height/5.5);
		$("#body").height(height - $("#head").height());	
	}
	
	$(this).resize(function(){
		height = $(this).height();
		setSize();
	})
	
	$("#logout").click(function(){
		$.ajax({
			url: "/WebApp/logout",
			type: "post",
			success: function(){
				location.href="/WebApp/Login.jsp";
			}
		})	
	})
	
</script>
</html>