<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>REGISTER</title>
<script src="statics/js/jquery-3.6.0.js"></script>
<script src="statics/js/all.min.js"></script>
<link rel="stylesheet" href="statics/css/bootstrap.css"></link>
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
	input[type='radio']{
		visibility: hidden;
	}
	input[type='radio']:after {
        width: 15px;
        height: 15px;
        border-radius: 15px;
        top: -5px;
        left: 20px;
        position: relative;
        background-color: #d1d3d1;
        content: '';
        display: inline-block;
        visibility: visible;
        border: 2px solid white;
    }

    input[type='radio']:checked:after {
        width: 15px;
        height: 15px;
        border-radius: 15px;
        top: -5px;
        left: 20px;
        position: relative;
        background-color: #525564;
        content: '';
        display: inline-block;
        visibility: visible;
        border: 2px solid white;
    }
    .notification{
    	display: none;
    	color: red;
    }
	.form-header{
		text-align: center;
		font-size: 28px;
		color: #fff;
	}
	.form-header a{
		color: #fff;
	}
	.form-header a:hover{
		color: #a2a9af;
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
			<header class="col-4">T I T L E</header>
		</div>
		<div class="row justify-content-center align-items-center" id="main">
			<div class="col-8 col-md-5 col-lg-4" id="form">
				<div class="row form-header">
					<a href="/WebApp/Login.jsp" class="col-3"><i class="fas fa-chevron-left"></i></a>
					<span class="col-6">REGISTER</span>
				</div>
				<div class="row input-area">
					<input type="text" id="name" placeholder="name" class="col-12 steven-form-element steven-text-light" />
				</div>
				<div class="row input-area">
					<input type="text" id="ac" placeholder="account" class="col-12 steven-form-element steven-text-light" />
				</div>
				<div class="row input-area">
					<input type="password" placeholder="password" id="pwd" class="col-12 steven-form-element steven-text-light" />
				</div>
				<div class="row input-area">
					<input type="password" placeholder="password again" id="pwdCheck" class="col-12 steven-form-element steven-text-light" />
				</div>
				<div class="row input-area">
					<input type="text" id="num" placeholder="phoneNumber" class="col-12 steven-form-element steven-text-light" />
				</div>
				<div class="row input-area align-items-center">
					<label class="col-md-3 col-6">male</label>
					<input type="radio" name="gender" value="male" class="col-md-3 col-6" />
					<label class="col-md-3 col-6">female</label>
					<input type="radio" name="gender" value="female" class="col-md-3 col-6" />
				</div>
				<div class="row input-area align-items-center">
					<span class="notification">select your gender!</span>
				</div>
				<div class="row input-area justify-self-end justify-content-center">
					<button type="button" id="submit" class="steven-btn-light steven-form-element col-md-8">
						submit
					</button>
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
		if(verification()){
			$.ajax({
				url: "/WebApp/register",
				type: "post",
				data: {
					name: $("#name").val(),
					account: $("#ac").val(),
					password: $("#pwd").val(),
					number: $("#num").val(),
					gender: $("input[name='gender']:checked").val()
				},
				success: function(result){
					var json = JSON.parse(result)
					console.log(json)
					if(json["status"]){
						location.href="/WebApp/Login.jsp";
					}
					else{
						if(!json["account"]){
							$("#ac").css("border-color", "red");
						}
						else{
							$("#ac").css("border-color", "#83FFE6");
						}
						if(!json["password"]){
							$("#pwd").css("border-color", "red");
							$("#pwdCheck").css("border-color", "red");
						}
						else{
							$("#pwd").css("border-color", "#83FFE6");
							$("#pwdCheck").css("border-color", "#83FFE6");
						}
						if(!json["number"]){
							$("#num").css("border-color", "red");
						}
						else{
							$("#num").css("border-color", "#83FFE6");
						}
					}
				}
			})
		}
	})
	
	function verification(){
		var result = true;
		
		if($("#name").val() == ""){
			$("#name").css("border-color", "red");
			result = false;
		}
		else{
			$("#name").css("border-color", "#83FFE6");
		}
		if($("#ac").val().length < 8){
			$("#ac").css("border-color", "red");
			result = false;
		}
		else{
			$("#ac").css("border-color", "#83FFE6");
		}
		if($("#pwd").val().length < 8){
			$("#pwd").css("border-color", "red");
			result = false;
		}
		else{
			$("#pwd").css("border-color", "#83FFE6");
		}
		if($("#pwd").val() != $("#pwdCheck").val()){
			$("#pwdCheck").css("border-color", "red");
			result = false;
		}
		else{
			$("#pwdCheck").css("border-color", "#83FFE6");
		}
		if($("#num").val().length != 10){
			$("#num").css("border-color", "red");
			result = false;
		}
		else{
			$("#num").css("border-color", "#83FFE6");
		}
		if($("input[name='gender']:checked").length){
			$(".notification").hide();
		}
		else{
			$(".notification").show();
			result = false;
		}
		
		return result;
	}
</script>
</html>