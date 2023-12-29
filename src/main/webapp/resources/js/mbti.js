
	$j(document).ready(function(){
		
		let selectedData = [];

		
		$j(document).on("click", "[class^='option']", function(){
			let row = $j(this).closest('.mbtiRadio');
			let boardType = row.find(".boardType").val();
			
			let option = $j(this).val();
			
			let selectedOption = {
				type : row.find(".boardType").val(),
				questionNum : $j(this).attr("name").replace("option[", "").replace("]", ""),
				optionValue : $j(this).val()
			}
			
			let index = selectedData.findIndex(item =>
				item.type === selectedOption.type && item.questionNum === selectedOption.questionNum
			);
			
			if(index !== -1){
				selectedData[index] = selectedOption;
			}else{
				selectedData.push(selectedOption);
			}
			
			console.log("selectedData = "+JSON.stringify(selectedData));
			console.log("isChecked: "+$j(this).is(":checked"));
		});
		
		$j(document).on("click", "#next", function(){
			let firstNum = $j(".boardNum").first().val();
			let pageNo = Math.ceil(firstNum / 5 + 1);
			
			if(!validateRadioChecked()){
				return;
			}
			
			$j.ajax({
				type: "GET",
			    url: "/mbti/pagingAction.do",
			    data: {
			        pageNo: pageNo,
		        },
			    dataType: "json",
			    success: function (data) {
			    	console.log("pageNo: "+data.page.pageNo);
			    	console.log("pa: "+pageNo);

			    	updatePage(data);
			    	
			    	if(pageNo === data.page.totalPage){
			    		$j("#btnGroup").html("<input type='button' value='제출' id='submit'/>");
			    	}
			    },
			    error: function (xhr) {
					alert("실패");
			    }
			});
		});
		
		function validateRadioChecked(){
			let boardNum = $j(".boardNum").first().val().replace(".", "");
			let allSelected = true;			
			
			for(let i=boardNum; i<=(parseInt(boardNum) + 4); i++){
				if(!$j("input[name='option["+i+"]']:checked").length){
					allSelected = false;
					alert("모든 항목을 선택해야합니다.");
					break;
				}
			}
			
			return allSelected;
		}

		function updatePage(data) {
		    // 데이터가 정의되어 있지 않거나 비어있는 경우에 대한 체크
		    if (!data || !data.list || data.list.length === 0) {
		        console.error('Data is empty or undefined.');
		        return;
		    }

		    // 기존 테이블 바디에 있는 행들을 지웁니다.
		    var mbtiQuestionTable = $j("#mbtiQuestion tbody");
		    mbtiQuestionTable.empty();

		    // 데이터 리스트를 순회하며 HTML 행을 생성합니다.
		    $j.each(data.list, function(index, mbti) {
		        let row =
		            "<tr>" +
		            "<td class='num'><input type='hidden' class='boardNum' value='"+ mbti.boardNum +"'/></td>" +
		            "<td><label class='txt comment'>" + mbti.boardComment + "</label></td>" +
		            "</tr>" +
		            "<tr>" +
		            "<td></td>" +
		            "<td class='mbtiRadio'>" +
		            "<label class='txt t_green'>동의</label>" +
		            "<div  class='radio'>" +
		            "<input type='hidden' value='" + mbti.boardType + "' class='boardType'/>" +
		            "<input type='radio' name='option[" + mbti.boardNum + "]' value='1' class='option3 green'/>" +
		            "<input type='radio' name='option[" + mbti.boardNum + "]' value='2' class='option2 green'/>" +
		            "<input type='radio' name='option[" + mbti.boardNum + "]' value='3' class='option1 green'/>" +
		            "<input type='radio' name='option[" + mbti.boardNum + "]' value='4' class='option0 gray'/>" +
		            "<input type='radio' name='option[" + mbti.boardNum + "]' value='5' class='option1 purple'/>" +
		            "<input type='radio' name='option[" + mbti.boardNum + "]' value='6' class='option2 purple'/>" +
		            "<input type='radio' name='option[" + mbti.boardNum + "]' value='7' class='option3 purple'/>" +
		            "</div>" +
		            "<label class='txt t_purple'>비동의</label>" +
		            "</td>" +
		            "</tr>";

		        // 생성된 행들을 테이블 바디에 추가합니다.
		        mbtiQuestionTable.append(row);
		    });
		}
		
		$j(document).on("click", "#submit", function(){
			
			let length = $j("input[type=radio]").length / $j(".radio").length;
			
			if(!validateRadioChecked()){
				return;
			}
			
			$j.ajax({
				type: "POST",
				url: "/mbti/action.do",
				data: {
					options: JSON.stringify(selectedData),
					length : length
				},
				dataType: "json",
				success: function(data){
					/* alert(data.mbti); */
					/* location.replace ("/mbti/result.do?mbti="+data.mbti); */
					console.log("length: "+length);
					$j("#mbtiList").empty();
					
					let row = "<div class='container'>"+
					"<div class='center'><label>당신의 성격 유형은:</label>"+
					"<label>"+data.mbti+"</label>"+
					"<input type='button' value='처음으로' id='previous'/></div></div>";
					
					$j("#mbtiList").append(row);
					
				},
				error: function(xhr){
					alert("실패");
				}
			});
			
			$j(document).on("click", "#previous", function(){
				location.href = "/mbti/view.do";
			});
			
		});
		
	});
	