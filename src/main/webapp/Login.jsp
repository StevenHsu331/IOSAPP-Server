<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	
	try{
		boolean verify = (boolean)session.getAttribute("verify");
		if(verify){
			response.sendRedirect("/WebApp/Home.jsp");
		}
	}
	catch(Exception e){
	}

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOGIN</title>
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
	}
	#head a{
		color: #000;
	}
	#head a:hover{
		color: #393E41;
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
		<div class="row justify-content-center align-items-center" id="head">
			<header class="col-6">
				<a href="/WebApp/Home.jsp">T I T L E</a>
			</header>
		</div>
		<div class="row justify-content-center align-items-center" id="main">
			<div class="col-8 col-md-5 col-lg-4" id="form">
				<div class="row form-header">
					<span>L O G I N</span>
				</div>
				<div class="row input-area">
					<input type="text" id="ac" placeholder="account" class="col-12 steven-form-element steven-text-light" />
				</div>
				<div class="row input-area">
					<input type="password" placeholder="password" id="pwd" class="col-12 steven-form-element steven-text-light" />
				</div>
				<div class="row input-area justify-self-end justify-content-center">
					<button type="button" id="submit" class="steven-btn-light steven-form-element col-md-8">
						submit
					</button>
				</div>
				<div class="row input-area justify-self-end justify-content-center">
					<a href="FindPwd.jsp" class="col-md-9 col-12 input-element">forget password</a>
					<a href="Register.jsp" class="col-md-3 col-12 input-element">register</a>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	var height = $(this).height();
	$(".container-fluid").height(height);
	$("#head").height(height/5.5);
	$("#main").height(height - $("#head").height());
	
	$(this).resize(function(){
		height = $(this).height();
		$(".container-fluid").height(height);
		$("#head").height(height/5.5);
		$("#main").height(height - $("#head").height());
	})
	
	$(".steven-text-light").keydown(function(event){
		if(event.which == 13){
			$("#submit").click();
		}
	})
	
	$("#submit").click(function(){
		$.ajax({
			url: "/WebApp/login",
			type: "post",
			data: {
				account: $("#ac").val(),
				password: $("#pwd").val()
			},
			success: function(result){
				var json = JSON.parse(result);
				console.log(json);
				if(json["status"]){
					location.href="/WebApp/Home.jsp";
				}
				else{
					if(!json["account"]){
						alert("account doesn't exist!")
					}
					else{
						alert("wrong password!")
					}
				}
			}
		})
	})
</script>
</html>