<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/resources/css/recruit.css">
<script type="text/javascript" src="/resources/js/recruit.js"></script>
<title>main</title>
</head>
<body>
	<h2 style="text-align: center;">입사지원서</h2>
	<div class="container">
		<session class="info">
			<input type="hidden" id="userSubmit" value="${recruit.submit}"/>
			<c:if test="${recruit.submit != null}">
				<div id="submitPage">
					<h2>이력서를 제출하셨습니다.</h2>
					<a href="/recruit/login.do">로그인 페이지로 이동</a>
				</div>
			</c:if>
			<table id="infoTable">
				<tr>
					<td>
						<input type="hidden" name="seq" id="seq" value="${recruit.seq}"/>
						<label>이름</label>
					</td>
					<td>
						<input type="text" name="name" id="name" value="${recruit.name}">
					</td>
					<td>
						<label for="birth">생년월일</label>
					</td>
					<td>
						<input type="text" name="birth" id="birth" value="${recruit.birth}" maxLength="10" placeholder="YYYY.MM.DD">
					</td>
				</tr>
				<tr>
					<td>
						<label>성별</label>
					</td>
					<td>
						<input type="hidden" id="userGender" value="${recruit.gender}"/>
						<select name="gender" id="gender">
							<option>남자</option>
							<option>여자</option>
						</select>
					</td>
					<td>
						<label for="phone">연락처</label>
					</td>
					<td>
						<input type="text" name="phone" id="phone" value="${recruit.phone}" maxLength="11">
					</td>
				</tr>
				<tr>
					<td>
						<label for="email">이메일</label>
					</td>
					<td>
						<input type="text" name="email" id="email" value="${recruit.email}">
					</td>
					<td>
						<label for="addr">주소</label>
					</td>
					<td>
						<input type="text" name="addr" id="addr" value="${recruit.addr}">
					</td>
				</tr>
				<tr>
					<td>
						<label for="hopeLocation">희망근무지</label>
					</td>
					<td>
						<select name="hopeLocation" id="hopeLocation">
							<c:forEach var="addr" items="${address}">
								<option <c:if test="${addr.name == recruit.hopeLocation}">selected="selected"</c:if>>${addr.name}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="workType">근무형태</label>
					</td>
					<td>
						<input type="hidden" id="userWorkType" value="${recruit.workType}"/>
						<select name="workType" id="workType">
							<option>계약직</option>
							<option>정규직</option>
						</select>
					</td>
				</tr>
			</table>
		</session>
		<c:if test="${recruit.seq != null}">
		<session id="user_info_detail">
			<table id="user_info_detail_table">
				<tr>
					<td>학력사항</td>
					<td>경력사항</td>
					<td>희망연봉</td>
					<td>희망근무지/근무형태</td>
				</tr>
				<tr >
					<td>
						<span>${submitEducationInfo.schoolName}</span>
						<span>${submitEducationInfo.schoolYear}</span>
						<span style="display: block;">${submitEducationInfo.division}</span>
					</td>
					<td>
						<span>경력</span>&nbsp
						<c:if test="${submitCareerInfo.jobYear > 0}">
							<span>${submitCareerInfo.jobYear}년</span>&nbsp
						</c:if>
						<span>${submitCareerInfo.jobMonth}개월</span>
					</td>
					<td>
						<span>${submitCareerInfo.salary}</span>
					</td>
					<td>
						<span>${recruit.hopeLocation}&nbsp전체</span><br>
						<span>${recruit.workType}</span>
					</td>
				</tr>
			</table>
		</session>
		</c:if>
		<section class="education">
			<div class="title">
				<h2>학력</h2>
				<div>
					<input type="button" value="추가" class="add" onclick="addRowsBtn(this);">
					<input type="button" value="삭제" class="del" onclick="deleteRowsBtn(this);">
				</div>
			</div>
			<table class="td1" id="educationTable">
				<thead>
				<tr>
					<th></th>
					<th>재학기간</th>
					<th>구분</th>
					<th>학교명(소재지)</th>
					<th>전공</th>
					<th>학점</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<template class=educationTemp>
					<tr class="educationTr">
						<td>
							<input type="hidden" name="eduSeq" value="${education.eduSeq}" class="eduSeq"/>
							<input type="checkbox" name="checkbox" class="checkbox">
						</td>
						<td>
							<input type="text" name="eduStartPeriod" id="eduStartPeriod" class="eduStartPeriod" value="${education.eduStartPeriod}" maxLength="7" placeholder="YYYY.MM">
							~
							<input type="text" name="eduEndPeriod" id="eduEndPeriod" class="eduEndPeriod" value="${education.eduEndPeriod}" maxLength="7" placeholder="YYYY.MM">
						</td>
						<td>
							<input type="hidden" class="pickOption" value="${education.division}"/>
							<select name="division" id="division" class="division">
								<option>졸업</option>
								<option>재학</option>
								<option>중퇴</option>
							</select>
						</td>
						<td>
							<input type="text" name="schoolName" id="schoolName" class="schoolName" value="${education.schoolName}">
							<select name="schoolLocation" id="schoolLocation" class="location">
								<c:forEach var="addr" items="${address}">
									<option <c:if test="${addr.name == education.schoolLocation}">selected="selected"</c:if>>${addr.name}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input type="text" name="major" id="major" class="major" value="${education.major}">
						</td>
						<td>
							<input type="text" name="grade" id="grade" class="grade" value="${education.grade}">
						</td>
					</tr>
				</template>
		</section>
		<section class="career">
			<div class="title">
				<h2>경력</h2>
				<div>
					<input type="button" value="추가" class="add" onclick="addRowsBtn(this);">
					<input type="button" value="삭제" class="del" onclick="deleteRowsBtn(this);">
				</div>
			</div>
			<table class="td1" id="careerTable">
				<thead>
					<tr>
						<th></th>
						<th>근무기간</th>
						<th>회사명</th>
						<th>부서/직급/직책</th>
						<th>지역</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<template class="careerTemp">
					<tr class="careerTr">
						<td>
							<input type="hidden" name="carSeq" class="carSeq" value="${career.carSeq}"/>
							<input type="checkbox" name="checkbox" class="checkbox">
						</td>
						<td>
							<input type="text" name="jobStartPeriod" id="jobStartPeriod" class="jobStartPeriod" value="${career.jobStartPeriod}" maxLength="7" placeholder="YYYY.MM">
							~
							<input type="text" name="jobEndPeriod" id="jobEndPeriod" class="jobEndPeriod" value="${career.jobEndPeriod}" maxLength="7" placeholder="YYYY.MM">
						</td>
						<td>
							<input type="text" name="compName" id="compName" class="compName" value="${career.compName}">
						</td>
						<td>
							<input type="text" name="task" id="task" class="task" value="${career.task}">
						</td>
						<td>
							<input type="text" name="jobLocation" id="jobLocation" class="jobLocation" value="${career.jobLocation}">
						</td>
					</tr>
				</template>
		</section>
		<section class="certificate">
			<div class="title">
				<h2>자격증</h2>
				<div>
					<input type="button" value="추가" class="add" onclick="addRowsBtn(this);">
					<input type="button" value="삭제" class="del" onclick="deleteRowsBtn(this);">
				</div>
			</div>
			<table class="td1" id="certificateTable">
				<thead>
				<tr>
					<th></th>
					<th>자격증명</th>
					<th>취득일</th>
					<th>발행처</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<template class="certificateTemp">
					<tr class="certificateTr">
						<td>
							<input type="hidden" name="certSeq" class="certSeq" value="${certificate.certSeq}"/>
							<input type="checkbox" name="checkbox" class="checkbox">
						</td>
						<td>
							<input type="text" name="qualifiName" id="qualifiName" class="qualifiName" value="${certificate.qualifiName}">
						</td>
						<td>
							<input type="text" name="acquDate" id="acquDate" class="acquDate" value="${certificate.acquDate}" placeholder="YYYY.MM">
						</td>
						<td>
							<input type="text" name="organizeName" id="organizeName" class="organizeName" value="${certificate.organizeName}">
						</td>
					</tr>
				</template>
		</section>
	</div>
	<div class="btnGroup">
		<input type="button" value="저장" id="save"  onclick="saveOrSubmit(this);">
		<input type="button" value="제출" id="submit" onclick="saveOrSubmit(this);">
	</div>
</body>
</html>