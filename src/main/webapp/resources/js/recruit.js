
$j(document).ready(function(){

	/* template 기본 세팅 */
	$j('#educationTable >  tbody').append($j('.educationTemp').prop('content').cloneNode(true));
	$j('#careerTable >  tbody').append($j('.careerTemp').prop('content').cloneNode(true));
	$j('#certificateTable >  tbody').append($j('.certificateTemp').prop('content').cloneNode(true));
	
	// 한번 제출했는지를 확인
	if($j('#seq').val() !== ''){
		let seq = $j('#seq').val();
		saveUserLoad(seq);
		
		$j('#gender').val($j('#userGender').val()).prop('selected', true);
		$j('#workType').val($j('#userWorkType').val()).prop('selected', true);
	
		let eduLength = $j('.educationTr').length;
		for(let i=0; i<eduLength; i++){
			$j('.division').eq(i).val($j('.pickOption').eq(i).val()).prop('selected', true);
		}
		
		/* submit 시에 사용 */
		userSubmitLoad();
	}

	/* ================== 검증 영역 ===================== */
	
	$j("#name").on("input", function(e){
			let input = $j(this).val();
			const regex = /[a-zA-Z0-9]/g;
			
			let value = input.replace(regex, '');

			$j(this).val(value);
			
		});
		
		$j("#phone").on("input", function(e){
			let input = $j(this).val();
			const regex = /[^0-9]/g;
			
			let value = input.replace(regex, '');
			$j(this).val(value);
			
		});
		
		$j('#birth').on('input', function(e){
			let input = $j(this).val();
			const regex = /[^0-9]/g;
			
			
			let value = input.replace(regex, '');
			if(value.length > 4){
				value = value.slice(0,4)+'.'+value.slice(4);
			}
			if(value.length > 7){
				value = value.slice(0,7)+'.'+value.slice(7);
			}
			$j(this).val(value);
		});
		
		$j(document).on('input', '[class$="Period"]', function(e){
			let input = $j(this).val();
			const regex = /[^0-9]/g;
			
			let value = input.replace(regex, '');
			
			if(value.length > 4){
				value = value.slice(0,4)+'.'+value.slice(4);
			}
			
			$j(this).val(value);			
		});
		
		$j(document).on('input', '.acquDate', function(e){
			let input = $j(this).val();
			const regex = /[^0-9]/g;
			
			let value = input.replace(regex, '');
			
			if(value.length > 4){
				value = value.slice(0,4)+'.'+value.slice(4);
			}
			$j(this).val(value);			
		})
		
		$j(document).on('input', '.grade', function(e){
			let input = $j(this).val();
			const regex = /[^0-9.]/g;
			
			let value = input.replace(regex, '');
			$j(this).val(value);
		});
		
});

function userSubmitLoad(){
	if($j('#userSubmit').val() !== ''){
		$j('.educationTr').css('text-align', 'center');
		$j('.careerTr').css('text-align', 'center');
		$j('.certificateTr').css('text-align', 'center');
		
		$j('input[type=button]').hide();
		
		$j('input[type=text]').each(function(){
			let input = $j(this);
			
			let span = $j("<span></span>");
			span.text(input.val());
			
			input.replaceWith(span);
		});
		
		$j('select').each(function(){
			let select = $j(this);
			
			let span = $j('<span></span>');
			span.text(select.val());
			
			select.replaceWith(span);
		});
		
		$j('input[type=checkbox]').each(function(){
			let input = $j(this);
			let table = input.closest('table');
			let column = input.parent().index();
			
			input.parent().hide();
			table.find('th').eq(column).hide();
		});
	}
}

function inputToSpan(){
	let inputs = $j('input[type=text]');
	
	for(input of inputs){
		let span = $j("<span></span>");
		span.text(input.val());
		
		input.replaceWith(span);
	}
}

function saveUserLoad(seq){
	let educationSection = document.querySelector('.education');
	let careerSection = document.querySelector('.career');
	let certificateSection = document.querySelector('.certificate');
	
	$j.ajax({
		type: "GET",
		url: `/recruit/saveUserLoad.do?seq=${seq}`,
		dataType: "JSON",
		success: function(data){
			userEducationLoad(data, educationSection);
			userCareerLoad(data, careerSection);
			userCertificateLoad(data, certificateSection);
        
        	userSubmitLoad();
        	['education', 'career', 'certificate'].forEach(function (sectionName) {
                if ($j(`#${sectionName}Table tbody`).find('tr').length < 1) {
                    sectionSwitchAddRows(sectionName);
                }
            });
		},
		error: function(xhr, error){
			console.log('Ajax fail message'+ error);
		}
	})
}

function userEducationLoad(data, section){
	var tbody = document.querySelector('#educationTable > tbody');
	tbody.innerHTML = '';
	
	let template = section.querySelector('[class$="Temp"]');
	
	data.educations.forEach(function(edu) {
		let cloneNode = document.importNode(template.content, true);
		let tr = cloneNode.querySelector('tr');
		
		tr.querySelector('.eduSeq').value = edu.eduSeq;
		tr.querySelector('.eduStartPeriod').value = edu.eduStartPeriod;
		tr.querySelector('.eduEndPeriod').value = edu.eduEndPeriod;
		tr.querySelector('.division').value = edu.division;
		tr.querySelector('.schoolName').value = edu.schoolName;
		tr.querySelector('.major').value = edu.major;
		tr.querySelector('.grade').value = edu.grade;
		
		let select = tr.querySelector('.location');
		for(option of select){
			if(option.value === edu.schoolLocation){
				option.selected = true;
			}
		}
		
        tbody.appendChild(cloneNode);
    });
}

function userCareerLoad(data, section){
	var tbody = document.querySelector('#careerTable > tbody');
	tbody.innerHTML = '';
	
	let template = section.querySelector('[class$="Temp"]');
	
	data.careers.forEach(function(car){
		let cloneNode = document.importNode(template.content, true);
		let tr = cloneNode.querySelector('tr');
		
		tr.querySelector('.carSeq').value = car.carSeq;
		tr.querySelector('.jobStartPeriod').value = car.jobStartPeriod;
		tr.querySelector('.jobEndPeriod').value = car.jobEndPeriod;
		tr.querySelector('.compName').value = car.compName;
		tr.querySelector('.task').value = car.task;
		tr.querySelector('.jobLocation').value = car.jobLocation;

		tbody.appendChild(cloneNode);
	});
}

function userCertificateLoad(data, section){
	var tbody = document.querySelector('#certificateTable > tbody');
	tbody.innerHTML = '';
	
	let template = section.querySelector('[class$="Temp"]');
	
	data.certificates.forEach(function(cert){
		let cloneNode = document.importNode(template.content, true);
		let tr = cloneNode.querySelector('tr');
		
		tr.querySelector('.certSeq').value = cert.certSeq;
		tr.querySelector('.qualifiName').value = cert.qualifiName;
		tr.querySelector('.acquDate').value = cert.acquDate;
		tr.querySelector('.organizeName').value = cert.organizeName;
		
		tbody.appendChild(cloneNode);
	});
}

function sectionSwitchAddRows(section){
	switch(section){
		case 'education':{
			$j('#educationTable >  tbody').append($j('.educationTemp').prop('content').cloneNode(true));
			break;
		}
		case 'career':{
			$j('#careerTable >  tbody').append($j('.careerTemp').prop('content').cloneNode(true));
			break;
		}
		case 'certificate':{
			$j('#certificateTable >  tbody').append($j('.certificateTemp').prop('content').cloneNode(true));
			break;
		}
	}
}
	
function addRowsBtn(button){
	const section = $j(button).closest('section').attr('class');
	sectionSwitchAddRows(section);
}

// 배열에서 undefined가 있을 경우 없애주는 함수
function removeUndefined(array){
	let removeUndefindedList = array.filter(data => data != undefined && data != '');
	
	return removeUndefindedList
}

function deleteRowsBtn(button){
	const section = $j(button).closest('section').attr('class');
	let section_checkbox = $j(`.${section}`).find('input[type="checkbox"]');
	let seqArray = [];
	let removeUndefindedList = [];
	let seqValue;
	
	if(section_checkbox.is(':checked')){
		let checked = $j(`.${section} input[type=checkbox]:checked`);
		let checkedTr = checked.parents('tr');

		checked.each(function(){
			seqValue = $j(this).closest('td').find('[class $= "Seq"]').val();
			seqArray.push(seqValue);
			removeUndefindedList = removeUndefined(seqArray);
		});
		
		if(removeUndefindedList.length === 0){
			alert("삭제가 완료되었습니다.");
			checkedTr.remove();
			
			if($j(`.${section} tbody`).find('tr').length < 1){
				sectionSwitchAddRows(section);
			}
		}
		
		if(removeUndefindedList.length > 0){
			$j.ajax({
				type: "DELETE",
				url: `/recruit/${section}.do?seqs=${removeUndefindedList.join(",")}`,
				dataType: "json",
				success: function(data){
					if(data.result === 1){
						alert("삭제가 완료되었습니다.");
						checkedTr.remove();
						
						if($j(`.${section} tbody`).find('tr').length < 1){
							sectionSwitchAddRows(section);
						}
					}else{
						alert("삭제를 실패했습니다.");
					}
				},error: function(xhr, error, errorThrow){
					console.error("AJAX request failed: " + error);
				}
			});
		}
	}else{
		alert("삭제할 항목을 선택해주세요.");
	}
}

function validateStartEndDate(){
	let inputPeriods = $j('[class$="Period"]');
	
	for(let i=0; i<inputPeriods.length-1; i+=2){
		let start = inputPeriods.eq(i).val();
		let end = inputPeriods.eq((i+1)).val();
		
		if(start > end && start != '' && end != ''){
			alert('시작기간과 종료기간의 순서가 잘못되었습니다.');
			inputPeriods.eq(i).focus();
			return false;
		}else if(start == end && start != '' && end != ''){
			alert('시작기간과 종료기간이 같습니다.');
			inputPeriods.eq(i).focus();
			return false;
		}
	}	
	return true;
}

function validateEduEndPeriod(){
	let endPeriods = $j('.eduEndPeriod');
	
	for(let i=0; i<endPeriods.length-1; i++){
		let end1 = endPeriods.eq(i).val();
		let end2 = endPeriods.eq(i+1).val();
		
		if(end1 === end2){
			alert('두 학력사항의 종료일이 같습니다.');
			endPeriods.eq(i).focus();
			return false;
		}
	}
	return true;
}

function saveOrSubmit(button){
		
		if(!validateInput() || !validatePeriod()){
			return;
		}
		
		if(!validateStartEndDate() || !validateEduEndPeriod()){
			return;
		};

		let userInfo = getUserInfo();
        let education = getAllEducationData();
        let career = getAllCareerData();
        let certificate = getAllCertificateData();
        
        console.log('career: '+JSON.stringify(career));
        
        let emptyField;

		if(!validateUser(userInfo) || !validateText(userInfo)){
			return;
		}
		if(!filterOutEmptyValues(career) || !filterOutEmptyValues(certificate)){
			return;
		}
		if(!validateBirthAndOtherDate(userInfo, education, career, certificate)){
			return;
		}
		if(!validateJobInfo(career)){
				return;	
		}
		emptyField = findEmptyFieldInArray(education);
			if(emptyField){
				switch(emptyField.key){
					case 'eduStartPeriod':
					case 'eduEndPeriod':{
						printAlert('재학기간');
						break;
					}
					case 'schoolName':{
						printAlert('학교명(소재지)');
						break;
					}
					case 'major':{
						printAlert('전공');
						break;
					}
					case 'grade':{
						printAlert('학점');
						break;
					}
				}

				$j('.education').find('input[name="'+emptyField.key+'"]').eq(emptyField.index).focus();
				return;
			}
			if(!blurPhone() || !blurPeriod() || !blurAcquDate()){
				return;
			}
			if(!checkDateRanges(education) || !checkDateRanges(career)){
				return;
			}
			/*alert('성공');*/
		if(button.id === 'submit'){
			submitBtn(userInfo, education, career, certificate);
		}else{
			saveBtn(userInfo, education, career, certificate);
		}
	}
	
	function validateBirthAndOtherDate(userInfo, education, career, certificate) {
	    let userBirth = userInfo.birth;
	
	    for (let i = 0; i < education.length; i++) {
	        if (userBirth > education[i].eduStartPeriod && education[i].eduStartPeriod !== '') {
	            alert('학력의 시작날짜가 생년월일보다 빠릅니다.');
	            $j('.eduStartPeriod').eq(i).focus();
	            return false;
	        }
	    }
	
		if(career !== null && career !== undefined){
		    for (let i = 0; i < career.length; i++) {
		        if (userBirth > career[i].jobStartPeriod && career[i].jobStartPeriod !== '') {
		            alert('경력의 근무시작날짜가 생년월일보다 빠릅니다.');
		            $j('.jobStartPeriod').eq(i).focus();
		            console.log(career[i].jobStartPeriod);
		            return false;
		        }
		    }	
	    }
	
	    for (let i = 0; i < certificate.length; i++) {
	        if (userBirth > certificate[i].acquDate && certificate[i].acquDate !== '') {
	            alert('자격증의 취득날짜 생년월일보다 빠릅니다..');
	            $j('.acquDate').eq(i).focus();
	            return false;
	        }
	    }
		return true;
	}
	
	function validateText(input){ // 배열 아닐때
			let keys = Object.keys(input);

		    for (let key of keys) {
		        
		        if(input[key] === '' && key !== 'seq'){
		        	switch(key){
				        case 'name':{
							printAlert("이름");
							break;
						}
						case 'birth':{
							printAlert("생년월일");
							break;
						}
						case 'phone':{
							printAlert("연락처");
							break;
						}
						case 'email':{
							printAlert("이메일");
							break;
						}
						case 'addr':{
							printAlert('주소');
							break;
						}
			        }
		        	$j('#'+key).focus();
			        return false;
		        }
		    }
			return true;
		}
	
	function printAlert(str){
		alert(`"${str}"란이 입력되지 않았습니다.`);
	}

    function saveBtn(userInfo, education, career, certificate){
		$j.ajax({
            type: "POST",
            url: "/recruit/main/save.do",
            dataType: "json",
            data: JSON.stringify({recruitVo : userInfo, educationVo : education, careerVo : career, certificateVo : certificate}),
            contentType : "application/json;charset=UTF-8",
            success: function(data, textStatus, jqXHR){
				if(data.result === 1){
					let pick = confirm("저장이 완료되었습니다.\n로그인화면으로 이동하시겠습니까?");
                	
                	if(pick){
						location.href = "/recruit/login.do";
					}else{
						location.reload();
					}
                	
                }else{
					alert("저장이 실패했습니다.");
				}
            },
            error: function (jqXHR, textStatus, errorThrown, error) {
					console.error("AJAX request failed: " + error);
			}
        });
	}
	
	function submitBtn(userInfo, education, career, certificate){
		let isSubmit = confirm('제출하시면 수정이 불가능합니다.\n제출하시겠습니까?');
		
		if(isSubmit){		
			$j.ajax({
	            type: "POST",
	            url: "/recruit/main/submit.do",
	            dataType: "json",
	            data: JSON.stringify({recruitVo : userInfo, educationVo : education, careerVo : career, certificateVo : certificate}),
	            contentType : "application/json;charset=UTF-8",
	            success: function(data, textStatus, jqXHR){
					if(data.result === 1){
						let pick = confirm("제출이 완료되었습니다.\n로그인화면으로 이동하시겠습니까?");
	                	
	                	if(pick){
							location.href = "/recruit/login.do";
						}else{
							location.reload();
						}
	                	
	                }else{
						alert("제출이 실패했습니다.");
					}
	            },
	            error: function (jqXHR, textStatus, errorThrown, error) {
						console.error("AJAX request failed: " + error);
				}
	        });
        }else{
			return;
		}
	}
    
    function isFieldEmpty(value){
		return !value || value.trim() === '';
	}
	
	function findEmptyFields(data){
		for(let key in data){
			if(data.hasOwnProperty(key) && isFieldEmpty(data[key]) && !key.endsWith('Seq')){
				return key;
			}
		}
		return null;
	}
	
	function findEmptyFieldInArray(array) {
	    for (let i = 0; i < array.length; i++) {
	        let emptyField = findEmptyFields(array[i]);
	
	        if (emptyField) {
	            return { index: i, key: emptyField };
	        }
	    }
	    return null;
	}
    
    function getUserInfo(){
		let userInfo = {
			seq: $j('#seq').val(),
			name: $j('#name').val(),
			birth: $j('#birth').val(),
			gender: $j('#gender').val(),
			phone: $j('#phone').val(),
			email: $j('#email').val(),
			addr: $j('#addr').val(),
			hopeLocation: $j('#hopeLocation').val(),
			workType: $j('#workType').val()
		};

		return userInfo;
	}
	
	function validateUser(userInfo){
		let regex;
		regex = /^(19|20)\d\d\.(0[1-9]|1[0-2])\.(0[1-9]|[12][0-9]|3[01])$/;
		if(userInfo.birth === ''){
			alert('날짜를 입력해주세요.');
			$j('#birth').focus();
			return false;
		}
		
		if(!regex.test(userInfo.birth)){
			alert('날짜를 다시 확인해주세요.');
			$j('#birth').focus();
			return false;
		}
		
		regex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		if(!regex.test(userInfo.email)){
			alert('이메일을 xxx@xxx.xxx 형식을 맞춰서 입력하세요.');
			$j('#email').focus();
			return false;
		}
		
		return true;
	}
    
    function getAllEducationData(){
		let educationDataArray = [];
		
		$j('#educationTable tr.educationTr').each(function () {
			let educationData = {
				eduSeq : $j(this).find('.eduSeq').val(),
				eduStartPeriod: $j(this).find('.eduStartPeriod').val(),
		        eduEndPeriod: $j(this).find('.eduEndPeriod').val(),
		        division: $j(this).find('.division').val(),
		        schoolName: $j(this).find('.schoolName').val(),
		        schoolLocation: $j(this).find('.location').val(),
		        major: $j(this).find('.major').val(),
		        grade: $j(this).find('.grade').val(),
		        
		    };
		    educationDataArray.push(educationData);
		});
		
		return educationDataArray;
	}
	
	function getAllCareerData(){
		let careerDataArray = [];
		
		$j('#careerTable tr.careerTr').each(function(){
			let careerData = {
				carSeq: $j(this).find('.carSeq').val(),
				jobStartPeriod: $j(this).find('.jobStartPeriod').val(),
				jobEndPeriod: $j(this).find('.jobEndPeriod').val(),
				compName: $j(this).find('.compName').val(),
				task: $j(this).find('.task').val(),
				jobLocation: $j(this).find('.jobLocation').val()
			};
			
			if(!careerData.jobStartPeriod && 
			!careerData.jobEndPeriod && 
			!careerData.compName && 
			!careerData.task &&
			!careerData.jobLocation){
				return;
			}
			
			careerDataArray.push(careerData);
		});
		return careerDataArray;
	}
	
	function filterOutEmptyValues(items){
		for(let i=0; i<items.length; i++){
			let item = items[i];
			
			const condition = Object.keys(item).filter(key => !key.endsWith('Seq') && item[key] !== '');
			if(condition.length >= 1){
				for(let key in item){
					if(!key.endsWith('Seq') && item[key] === ''){
						let topTh = searchTopTh(key);
						
						alert(`${topTh} 를 입력해주세요.`);
						$j(`.${key}`).eq(i).focus();
						return false;
					}
				}
			}
		}
		return true;
	}
	
	function searchTopTh(key){
		let table = $j(`.${key}`).closest('table');
		let column = $j(`.${key}`).parent().index();
		let topTh = table.find('th').eq(column).text();
		
		return topTh;
	}
	
	function getAllCertificateData(){
		let certificateDataArray = [];
		
		$j('#certificateTable tr.certificateTr').each(function(){
			let certificateData = {
				certSeq: $j(this).find('.certSeq').val(),
				qualifiName: $j(this).find('.qualifiName').val(),
				acquDate: $j(this).find('.acquDate').val(),
				organizeName: $j(this).find('.organizeName').val()
			};
			
			if(!certificateData.qualifiName && !certificateData.acquDate && !certificateData.organizeName){
				return;
			}
			
			certificateDataArray.push(certificateData);
		});
		return certificateDataArray;
	}
    
    function validateInput(){
			let inputs = $j('input[type=text]');
			
			for(let i=0; i<inputs.length; i++){
				let input = inputs.eq(i).val();
				let inputLength = getByteLength(input);
				
				if(inputLength > 255){
					alert('입력값이 너무 깁니다.\n'+input);
					inputs.eq(i).focus();
					return false;
				}
			}
			
			return true;
		}
		
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
		}; // getByteLength
		
		function checkDateRanges(items){
			let arrayDateRanges = [];
			let periodKeys;
			
			for (let i = 0; i < items.length; i++) {
			  let item = items[i];
			
			  // 'Period'로 끝나는 키들만 필터링
			  periodKeys = Object.keys(item).filter(key => key.endsWith('Period'));
			
			  for (let j = 0; j < periodKeys.length; j += 2) {
			    // j번째와 j+1번째 키를 사용하여 start와 end를 가져옴
			    let startKey = periodKeys[j];
			    let endKey = periodKeys[j + 1];
			    let start = item[startKey];
			    let end = item[endKey];
			
			    arrayDateRanges.push([start, end]);
			  }
			}
			let isDateRanges = validateDateRanges(arrayDateRanges, periodKeys);
			
			return isDateRanges;
		}
		
		function validateDateRanges(ranges, periodKeys){
			const dateObjects = ranges.map(range =>({
				start: new Date(range[0]),
				end: new Date(range[1])
			}));
			
			for(let i=0; i<dateObjects.length-1; i++){
				for(let j=i+1; j<dateObjects.length; j++){
					if (dateObjects[i].start <= dateObjects[j].end && dateObjects[i].end >= dateObjects[j].start) {
			          alert('날짜 범위가 겹칩니다!');
			          $j(`.${periodKeys[0]}`).eq(j).focus();
			          return false;
			        }
				}
			}
			
			return true;
		}
		
		function validateJobInfo(items){
			for(let i=0; i<items.length; i++){
				let item = items[i];
				
				const condition = item.task;
				const regex = /[가-힣\w]+\/[가-힣\w]+\/[가-힣\w]+/;
				
				if(!regex.test(condition)){
					alert('부서/직급/직책 순으로 입력해주세요.');
					$j('.task').eq(i).focus();
					return false;
				}
			}
			return true;
		}

		function validatePeriod(){
			const inputPeriods = $j('[class$="Period"]');
			const regex = /^\d{4}\.\d{2}$/;
			
			for(let i=0; i<inputPeriods.length; i++){
				let inputPeriod = inputPeriods.eq(i).val();
				
				if(!regex.test(inputPeriod) && inputPeriod !== ''){
					alert('YYYY.MM 형식으로 입력하시기 바랍니다.');
					inputPeriods.eq(i).focus();
					return false;
				}
			}
			
			return true;			
		}
		
		function blurPeriod(){
		    let input = $j('[class$="Period"]');
		    
		    for(let i=0; i<input.length; i++){
				let inputSubString = parseFloat(input.eq(i).val().slice(5)); // 문자열을 숫자로 변환

			    if (inputSubString <= 0 || inputSubString > 12) {
			            alert('날짜를 다시 확인해주세요.');
			            input.eq(i).focus();
			            return false;
			    }
			}
		    
		    return true;
		};
		
		function blurAcquDate(){
		    let input = $j('.acquDate');

			for(let i=0; i<input.length; i++){
				let inputSubString = parseFloat(input.eq(i).val().slice(5));
				
				if (inputSubString <= 0 || inputSubString > 12) {
					alert('날짜를 다시 확인해주세요.');
					input.eq(i).focus();
					return false;
				}
			}
			return true;
		};
		
		function blurPhone(){
			let input = $j('#phone').val();
			const regex = /^[0-9]{11}$/;

			if(!regex.test(input) && !!input){
				alert('휴대폰번호를 11자리 입력해주세요.');
				$j('#phone').focus();
				return false;
			}
			return true;
		};
    
		
