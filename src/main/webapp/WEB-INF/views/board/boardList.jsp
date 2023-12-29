<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>
</head>
<script type="text/javascript">

	$j(document).ready(function(){
		
		$j("#selectAll").change(function () {
            $j(".itemCheckbox").prop("checked", $j(this).prop("checked"));
        });
		
		
		$j(".itemCheckbox").change(function(){
			if(!$j(this).prop("checked")) {
				$j("#selectAll").prop("checked", false);
			}else{
				if($j(".itemCheckbox:checked").length === $j(".itemCheckbox").length){
					$j("#selectAll").prop("checked", true);
				}
			}
		})
		
		var checked = [];
 		$j("#search").on("click", function(e){
 			e.preventDefault();
 			
 			checked = [];

			$j("input[name=codeId]:checked").each(function(){
				checked.push($j(this).val());
			})
			
			console.log(checked);
			
			var searchCheck = $j("#boardCheck :input");
			var param = searchCheck.serialize();
			console.log("param::"+param);
			
			
			$j.ajax({
				url : "/board/boardSearch.do",
				type : "GET",
				data : param,
				success : function (data, jqXHR) {
					let totalCnt = data.totalCnt;
					let boardList = data.boardList;
					let page = data.page;
					
					boardUpdate(totalCnt, boardList, page);
				},
				error : function (xhr) {
					alert("실패");
				}
			});
			
		});
		
 		function boardUpdate(totalCnt, boardList, page){
 			let tableBody = $j("#boardTable");
			
			tableBody.find('tr:gt(0)').remove();
			
			$j("#totalCnt").text(totalCnt);
			
			$j.each(boardList, function(index, board){
				let row = '<tr>'+
				'<td align="center">'+board.comCodeVo.codeName+'</td>'+
				'<td>'+board.boardNum+'</td>'+
				'<td><a href = "/board/'+board.boardType+'/'+board.boardNum+'/boardView.do" class="boardTitle">'+board.boardTitle+'</a></td>'+
				'</tr>';
				
				tableBody.append(row);
			});
			
			updatePagination(page);
 		}
 		
		function updatePagination(page){
			var paginationDiv = $j("#pagination");
			paginationDiv.empty();
			
			if (page.totalPage !== 0) {
		        if (page.pageNo - 1 > 0) {
		            var prevLink = '<a href="#" class="pageNo" data-page="' + (page.pageNo - 1) + '">&lt;</a>';
		            paginationDiv.append(prevLink);
		        }

		        for (var pageNumber = page.beginPageNo; pageNumber <= Math.min(page.endPageNo, page.totalPage); pageNumber++) {
		            var pageLink = '<a href="#" class="pageNo" data-page="' + pageNumber + '">' + pageNumber + '</a>';
		            paginationDiv.append(pageLink);
		        }

		        if (page.pageNo + 1 <= page.totalPage) {
		            var nextLink = '<a href="#" class="pageNo" data-page="' + (page.pageNo + 1) + '">&gt;</a>';
		            paginationDiv.append(nextLink);
		        }
		    }
		}
		
		$j("#write").on("click", function(e){
			e.preventDefault();
			
			var user = $j("#userName").text();
			
			if(user === ""){
				alert("로그인 필요");
				window.location.href="/user/login.do";
			}else{
				window.location.href="/board/boardWrite.do";
			}
		});
		
		$j("#pagination").on("click", ".pageNo", function(e){
			e.preventDefault();

		    var currentUrl = new URL(window.location.href);

		    var pageNo = $j(this).data("page");

		    currentUrl.searchParams.set('pageNo', pageNo);
		    
			console.log(checked);
			console.log(pageNo);
		    /* window.location.href = currentUrl.href; */
		    
		    var serializedArray = checked.join(',');
		    console.log(serializedArray);
		    
		    $j.ajax({
		    	url : "/board/boardSearch.do",
		    	type : "GET",
		    	dataType : "json",
		    	data : {
		    		pageNo : pageNo,
		    		codeId : serializedArray
		    	},
		    	success : function (data, jqXHR) {
					let totalCnt = data.totalCnt;
					let boardList = data.boardList;
					let page = data.page;
					
					boardUpdate(totalCnt, boardList, page);
				},
				error : function (xhr) {
					alert("실패");
				}
		    	
		    }); // ajax
		});
		
		
	});
	
</script>
<style>
	.table-top{
		display : flex;
		justify-content: space-between;
	}
	#pagination {
    text-align: right;
    margin-top: 20px;
	}
	.pageNo {
	    display: inline-block;
	    padding: 8px;
	    margin: 0 5px;
	}
</style>
<body>
<table  align="center" id="boardList">
	<tr>
		<td align="right">
		    <div class="table-top">
		    	<c:if test="${user == null}">
			        <div>
			            <a href="/user/login.do">login</a>
			            <a href="/user/join.do">join</a>
			        </div>
		        </c:if>
		        <c:if test="${user != null}">
		        	<div>
		        		<span id="userName">${user.userName}</span>
		        	</div>
		        </c:if>
		        <div>
		            total : <span id=totalCnt>${totalCnt}</span>
		        </div>
		    </div>
		</td>
	</tr>
	<tr>
		<td>
			<table id="boardTable" border = "1">
				<tr>
					<td width="80" align="center">
						Type
					</td>
					<td width="40" align="center">
						No
					</td>
					<td width="300" align="center">
						Title
					</td>
				</tr>
				<c:forEach items="${boardList}" var="list">
					<tr>
						<td align="center" class="codeName">
							${list.comCodeVo.codeName}
						</td>
						<td class="boardNum">
							${list.boardNum}
						</td>
						<td>
							<a href = "/board/${list.boardType}/${list.boardNum}/boardView.do?pageNo=${page.pageNo}" class="boardTitle">${list.boardTitle}</a>
						</td>
					</tr>	
				</c:forEach>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right">
			<a href="#" id="write">글쓰기</a>
			<c:if test="${user != null}">
        		<a href="/user/logoutAction.do">로그아웃</a>
	        </c:if>
		</td>
	</tr>
	
	<!-- 필터링 처리 영역 -->
	<tr>
		<td>
		 <!-- action="/board/boardList.do?pageNo=" method="GET" -->
			<form id="boardCheck">
				<input type="checkbox" id="selectAll">전체
				<c:forEach var="ComCode" items="${ComCodes}">
					<c:if test="${ComCode.codeType == 'menu'}">
						<input type="checkbox" class="itemCheckbox" name="codeId" value="${ComCode.codeId}"
							<c:if test="${selectedCodeIds != null and selectedCodeIds.contains(ComCode.codeId)}">checked</c:if>
						/>${ComCode.codeName}
					</c:if>
				</c:forEach>
				<input type="button" value="조회" id="search"/>
			</form>
		</td>
	</tr>
	
	<!-- 페이징 처리 영역 -->
	<tr>
		<td align="right">
			<div  id="pagination">
				<c:if test="${page.totalPage != 0}">
					<c:if test="${page.pageNo-1 > 0}">
						<a href="#" class="pageNo" data-page="${page.pageNo - 1}">&lt;</a>
					</c:if>
					<c:forEach var="pageNumber" begin="${page.beginPageNo}" end="${(page.endPageNo >= page.totalPage) ? page.totalPage : page.endPageNo}">
						<a href="#" class="pageNo" data-page="${pageNumber}">${pageNumber}</a>
					</c:forEach>
					<c:if test="${page.pageNo+1 <= page.totalPage}">
						<a href="#" class="pageNo" data-page="${page.pageNo + 1}">&gt;</a>
					</c:if>
				</c:if>
			</div>
		</td>
	</tr>
</table>	
</body>
</html>