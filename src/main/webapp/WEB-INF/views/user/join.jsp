<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>join</title>
</head>
<script type="text/javascript">
	
	$j(document).ready(function(){
		
		let verifiedId = 0;

		$j("#duplicate").on("click", function(){
			
			let userId = $j("#userId").val();
			
			$j.ajax({
				url: "/user/duplicateAction.do",
				dataType: "json",
				type: "POST",
				data:{
					userId : userId
				},
				success: function(data, textStauts, jqXHR){
					if(data.success === "Y" && userId !== ""){
						alert("이미 사용중인 아이디 입니다.");
						$j("#userId").focus();
					}else if(userId.length === 0){
						alert("아이디를 입력해주세요.");
						$j("#userId").focus();
					}else{
						alert("사용가능한 아이디 입니다.");
						verifiedId = 1;
					}
				},
				error: function (jqXHR, textStatus, errorThrown, error) {
					console.error("AJAX request failed: " + error);
				}
			});
			
			
		});
		
		$j("#submit").on("click", function(){
			let $frm = $j('.joinForm :input');
			
			let user = {
					id : $j("#userId").val(),
					pw : $j("#userPw").val(),
					pwch : $j("#userPwCheck").val(),
					name : $j("#userName").val(),
					phoneNum : $j('.userPhone option:selected').text() + "-"+$j('input[name=userPhone2]').val() + "-"+$j('input[name=userPhone3]').val()
				};

			if(!validateUserId(user)){
				return;
			}
			if(!validateUserPw(user)){
				return;
			}
			if(!validateUserName(user)){
				return;
			}
			if(!validateUserPhoneNum(user)){
				return;
			}
			if(!validateUserPostNo()){
				return;
			}
			if(!validateUserAddress()){
				return;
			}
			if(!validateUserCompany()){
				return;
			}
				
			let param = $frm.serialize();
			$j.ajax({
				url: "/user/joinAction.do",
				dataType: "json",
				type: "POST",
				data: param,
				success: function(data, textStatus, jqXHR) {
					if(data.success === "Y"){
						alert("회원가입 성공");
						alert("메시지:"+data.success);
						window.location.href = "/board/boardList.do";
					}else{
						alert("회원가입 실패, 다시한번 확인해주세요.");
						alert("메시지:"+data.success);
					}
				},
				error: function (jqXHR, textStatus, errorThrown, error) {
					console.error("AJAX request failed: " + error);
				}
			});
		});
		
		function showAlertAndFocus(element, message){
			alert(message);
			element.focus();
		}
		
		function validateUserId(user){
			let id = $j("#userId");
			
			if(user.id === ""){
				showAlertAndFocus(id, "아이디를 입력해주세요.");
				return false;
			}

			if(verifiedId !== 1){
				showAlertAndFocus(id, "아이디 중복확인을 해주세요.");
				return false;
			}
			if(getByteLength(user.id) > 15){
				showAlertAndFocus(id, "아이디가 깁니다. (최대 15자)");
				return false;
			}
			return true;
		} // validateUserId
		function validateUserPw(user){
			let pw = $j("#userPw");
			let pwch = $j("#userPwCheck");
			
			if(user.pw === ""){
				showAlertAndFocus(pw, "비밀번호를 입력해주세요");
				return false;
			}
			if(user.pwch === ""){
				showAlertAndFocus(pwch, "비밀번호 확인을 입력해주세요");
				return false;
			}
			if(user.pw.length < 6 || user.pw.length > 12){
				showAlertAndFocus(pw, "비밀번호는 6~12자리 입력해주세요.");
				return false;
			} // if
			
			if(user.pw !== "" && user.pwch !== "" && user.pw !== user.pwch){
				showAlertAndFocus(pwch, "비밀번호가 동일하지 않습니다.");
				return false;
			}
			return true;
		} // validateUserPw
		function validateUserName(user){
			let name = $j("#userName");
			
			if(user.name == ""){
				showAlertAndFocus(name, "이름을 입력해주세요");
				return false;
			}
			if(getByteLength(user.name) > 15){	
				showAlertAndFocus(name, "이름의 길이가 깁니다. (최대 5자)");
				return false;
			} // if
			return true;
		} // validateUserName
		function validateUserPhoneNum(user){
			let phone2 = $j("input[name=userPhone2]");
			let phone3 = $j("input[name=userPhone3]");

			if (!validatePhoneInputs([phone2, phone3])) {
			    return false;
			} // if
			return true;
		} // validateUserPhoneNum
		function validateUserPostNo(){
			let postNoRegex = /^([0-9]{3})-([0-9]{3})$/;
			let postNo = $j("#userAddr1");
			
			if(!postNoRegex.test(postNo.val()) && postNo.val() != ""){
				showAlertAndFocus(postNo, "postNo을 다시 입력해주세요.\n예) xxx-xxx");
				return false;
			} // if
			return true;
		} // validateUserPostNo
		function validateUserAddress(){
			let address = $j("#userAddr2");
			if(getByteLength(address.val()) > 150){
				showAlertAndFocus(address, "주소가 깁니다.(최대 150자)");
				return false;
			}
			return true;
		} // validateUserAddress
		function validateUserCompany(){
			let company = $j("#userCompany");
			if(getByteLength(company.val()) > 60){
				showAlertAndFocus(company, "회사명이 깁니다.(최대 60자)");
				return false;
			}
			return true;
		} // validateUserCompany
		
		function validatePhoneNum(input){
			let phoneRegex = /^\d{4}$/;
			
			if(!phoneRegex.test(input.val())){
				alert("휴대폰 번호를 형식에 맞춰 다시 입력해주세요.\n예) xxx-xxxx-xxxx");
				input.focus();
			} // if
		} // validatePhoneNum
		function validatePhoneInputs(phones) {
		    for (let i = 0; i < phones.length; i++) {
		        let currentPhone = phones[i];
		        if (currentPhone.val() === "") {
		            alert("휴대폰 번호를 입력해주세요.");
		            currentPhone.focus();
		            return false;
		        } // if

		        if (currentPhone.val().length < 4) {
		            if (!validatePhoneNum(currentPhone)) {
		                return false;
		            } // if
		        } // if
		    } // for

		    return true;
		} // validatePhoneInputs
		function getByteLength(str){
			let byteLength = 0;
			
			for(let i=0; i<str.length; i++){
				const charCode = str.charCodeAt(i);
				if (charCode <= 0x007F) {
		            byteLength += 1;
		        } else if (charCode <= 0x07FF) {
		            byteLength += 2;
		        } else {
		            byteLength += 3;
		        } // if-elseif-else
			} // if
			
			return byteLength;
		} // getByteLength
		
		$j("#userId").on("input", function(e) {
			verifiedId = 0;
			let inputValue = $j(this).val();
			let sanitizedValue = inputValue.replace(/[^a-zA-Z0-9]/g, '');
		    $j(this).val(sanitizedValue);
		});
		
		$j("#userPw").on("input", function(e){
			let inputValue = $j(this).val();
			
			if(inputValue.length >= 6 && inputValue.length <= 12){
				$j("#pwCheck").text("적합").css("color", "green");
			}else{
				$j("#pwCheck").text("부적합").css("color", "red");
			}
		});
		$j("#userPwCheck").on("input", function(e){
			let inputValue = $j(this).val();
			
			if(inputValue.length >= 6 && inputValue.length <= 12){
				$j("#pwchCheck").text("적합").css("color", "green");
			}else{
				$j("#pwchCheck").text("부적합").css("color", "red");
			}
		});
		
		$j("#userName").on("input", function(e){
			let inputValue = $j(this).val();
			let sanitizedValue = inputValue.replace(/[a-zA-Z0-9]/g, '');
			$j(this).val(sanitizedValue);
		});
		
		$j(".userPhone").on("input", function(e){
			let inputValue = $j(this).val();
			let sanitizedValue = inputValue.replace(/[^0-9]/g, '');
			$j(this).val(sanitizedValue); 
		});
		
		$j("#userAddr1").on("input", function(e){
			let inputValue = $j(this).val();
			let numericValue = inputValue.replace(/\D/g, '');
			
			if(numericValue.length >= 4){
				let formattedValue = numericValue.slice(0,3) + "-" + numericValue.slice(3,6);
				this.value = formattedValue;
			}
		});
		
	});
	
</script>
<style>
	.join{	
		margin: auto;
		width: 380px;
	}
	.join > #submit {
		margin-top: 5px;
		float: right;
		background-color:transparent; 
		border:0px transparent solid;
		cursor: pointer;
	}
	.join .userPhone{
		width: 50px;
	}
	.join > table {
		margin-top: 5px;
	}
	.join .essential{
		color: red;
	}
	
</style>
<body>
	<form class="joinForm">
		<div class="join">
			<a href="/board/boardList.do" id="toList">List</a>
			<table align="center" border="1">
				<tr>
					<td width="80" align="center">
						<span class="essential">*</span>id
					</td>
					<td  width="300">
						<input type="text" name="userId" id="userId" maxlength="15"/>
						<input type="button" id="duplicate" value="중복확인"/>
					</td>
				</tr>
				<tr>
					<td align="center">
						<span class="essential">*</span>pw
					</td>
					<td>
						<input type="password" name="userPw" id="userPw" pattern="^[A-Za-z0-9]{6,12}$" title="6~12자리를 입력하시기 바랍니다."/>
						<span id=pwCheck></span>
						<br><span>6~12자리</span>
					</td>
				</tr>
				<tr>
					<td align="center">
						<span class="essential">*</span>pw check
					</td>
					<td>
						<input type="password" name="userPwCheck" id="userPwCheck" pattern="^[A-Za-z0-9]{6,12}$" title="6~12자리를 입력하시기 바랍니다."/>
						<span id=pwchCheck></span>
						<br><span>6~12자리</span>
					</td>
				</tr>
				<tr>
					<td align="center">
						<span class="essential">*</span>name
					</td>
					<td>
						<input type="text" name="userName" id="userName" maxlength="5"/>
					</td>
				</tr>
				<tr>
					<td align="center">
						<span class="essential">*</span>phone
					</td>
					<td>
						<select name="userPhone1" class="userPhone">
							<c:forEach var="code" items="${codes}">
								<c:if test="${code.codeType == 'phone'}">
									<option name="code" value="${code.codeId}">${code.codeName}</option>
								</c:if>
							</c:forEach>
						</select> - 
						<input type="text" name="userPhone2" class="userPhone" maxlength="4" pattern="^[0-9]{4}$" title="0~9까지 4자리를 입력하십시오."/> - 
						<input type="text" name="userPhone3" class="userPhone" maxlength="4" pattern="^[0-9]{4}$" title="0~9까지 4자리를 입력하십시오."/>
					</td>
				</tr>
				<tr>
					<td align="center">
						postNo
					</td>
					<td>
						<input type="text" name="userAddr1" id="userAddr1" pattern="^([0-9]{3})-([0-9]{3})$" title="'xxx-xxx' 형식으로 숫자만 입력하시기 바랍니다."/>
					</td>
				</tr>
				<tr>
					<td align="center">
						address
					</td>
					<td>
						<input type="text" name="userAddr2" id="userAddr2"/>
					</td>
				</tr>
				<tr>
					<td align="center">
						company
					</td>
					<td>
						<input type="text" name="userCompany" id="userCompany"/>
					</td>
				</tr>
			</table>
			<input type="button" value="join" id="submit"/>
		</div>
	</form>
</body>
</html>