<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
</head>
<script type="text/javascript">

	$j(document).ready(function(){
		
		$j("#loginBtn").on("click", function(e){
			
			e.preventDefault();
			
			var $frm = $j(".loginForm :input");
			var param = $frm.serialize();
			
			if(!validateInputs($frm)){
				return;
			} // if
			
			$j.ajax({
				url: "/user/loginAction.do",
				dataType : "json",
				type: "POST",
				data: param,
				success: function(data, textStatus, jqXHR){
					if(data.success === "LoginSuccess"){
						alert(data.findUserName+"님 환영합니다.");
						window.location.href="/board/boardList.do";
					}else if(data.success === "IdOkButPwNo"){
						alert("비밀번호가 일치하지 않습니다.");
					}else{
						alert("일치하는 계정이 없습니다.");
					}
				},
				error: function(jqXHR, textStaus, errorThrown, error){
					console.error("AJAX request failed: " + error);
				}
			}); // ajax
			
		}); // function
		
		function validateInputs(input){
			
			if(input.filter("#userId").val() === ""){
				alert("아이디를 입력해주세요.");
				input.filter("#userId").focus();
				return false;
			} // if
			if(input.filter("#userPw").val() === ""){
				alert("비밀번호를 입력해주세요.");
				input.filter("#userPw").focus();
				return false;
			} // if
			return true;
		} // validateInputs
		
	}); // document

</script>
<style>
	.login{
		margin: auto;
		width: 280px;
	}
	.login > #loginBtn {
		margin-top: 5px;
		float: right;
		background-color:transparent; 
		border:0px transparent solid;
		cursor: pointer
	}
	
</style>
<body>
	<form class="loginForm">
		<div class="login">
			<table align="center" border="1">
				<tr>
					<td width="80" align="center">
						id
					</td>
					<td  width="200">
						<input type="text" name="userId" id="userId"/>
					</td>
				</tr>
				<tr>
					<td align="center">
						pw
					</td>
					<td>
						<input type="password" name="userPw" id="userPw"/>
					</td>
				</tr>
			</table>
			<a href="/board/boardList.do">List</a>
			<input type="button" value="login" id="loginBtn"/>
		</div>
	</form>
</body>
</html>