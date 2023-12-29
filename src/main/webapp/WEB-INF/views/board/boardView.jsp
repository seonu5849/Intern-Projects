<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>boardView</title>
<script type="text/javascript">
	
	$j(document).ready(function(){
		
		$j("#delete").on("click", function(){
			var $frm = $j('.boardDelete :input');
			var param = $frm.serialize();
			/* var boardType = $j("#boardType").val();
			var boardNum = $j("#boardNum").val();
			var param = "boardType="+boardType+"&boardNum="+boardNum; */
			
			$j.ajax({
				url : "/board/boardDeleteAction.do?"+param,
				dataType : "json",
				type: "DELETE",
				/* data : {
					boardType: boardType,
					boardNum: boardNum
				}, */
				success: function(data, textStatus, jqXHR)
				{
					alert("삭제완료");
					
					alert("메세지:"+data.success);
					
					location.href = "/board/boardList.do?pageNo=";
				},
			    error: function (jqXHR, textStatus, errorThrown)
			    {
			    	alert("실패");
			    }
			});
		});
	});
</script>
</head>
<body>
<div class="boardDelete">
<input type="hidden" name="boardType" id="boardType" value="${board.boardType}">
<input type="hidden" name="boardNum" id="boardNum" value="${board.boardNum}">
</div>

<table align="center">
	<tr>
		<td>
			<table border ="1">
				<tr>
					<td width="120" align="center">
					Title
					</td>
					<td width="400">
					${board.boardTitle}
					</td>
				</tr>
				<tr>
					<td height="300" align="center">
					Comment
					</td>
					<td>
					${board.boardComment}
					</td>
				</tr>
				<tr>
					<td align="center">
					Writer
					</td>
					<td>
					${board.creator}
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right">
			<a href="/board/boardList.do">List</a>
			<c:if test="${user.userName == board.creator}">
				<a href="/board/${board.boardType}/${board.boardNum}/boardUpdateView.do" id="update">글수정</a>
				<input type="button" id="delete" value="글삭제">
			</c:if>
		</td>
	</tr>
</table>	
</body>
</html>