<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/resources/js/mbti.js"></script>
<link rel="stylesheet" href="/resources/css/mbti.css">
<title>list</title>
</head>
<body>
<table  align="center" id="mbtiList">
	<tr>
		<td>
			<table id="mbtiQuestion">
				<c:forEach items="${list}" var="mbti">
				<tr>
					<td class="num">
						<input type='hidden' class="boardNum" value="${mbti.boardNum}"/>
					</td>
					 <td>
					 	<label class="txt comment">${mbti.boardComment}</label>
					 </td>
				</tr>
				<tr>
					<td>
					</td>
					<td class="mbtiRadio">
						<label class="txt t_green">동의</label>
						<div class="radio">
							<input type="hidden" value="${mbti.boardType}" class="boardType"/>
							<input type="radio" name="option[${mbti.boardNum}]" value="1" class="option3 green"/>
							<input type="radio" name="option[${mbti.boardNum}]" value="2" class="option2 green"/>
							<input type="radio" name="option[${mbti.boardNum}]" value="3" class="option1 green"/>
							<input type="radio" name="option[${mbti.boardNum}]" value="4" class="option0 gray"/>
							<input type="radio" name="option[${mbti.boardNum}]" value="5" class="option1 purple"/>
							<input type="radio" name="option[${mbti.boardNum}]" value="6" class="option2 purple"/>
							<input type="radio" name="option[${mbti.boardNum}]" value="7" class="option3 purple"/>
						</div>
						<label class="txt t_purple">비동의</label>
					</td>
				</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
	<tr>
		<td id="btnGroup">
			<c:if test="${page.pageNo == page.totalPage}">
				<input type="button" value="제출" id="submit"/>
			</c:if>
			<c:if test="${page.pageNo != page.totalPage}">
				<input type="button" value="다음" id="next"/>
			</c:if>
		</td>
	</tr>
</table>	
</body>
</html>