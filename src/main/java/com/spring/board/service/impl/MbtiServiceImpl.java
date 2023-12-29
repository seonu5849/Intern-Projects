package com.spring.board.service.impl;

import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.MbtiDao;
import com.spring.board.dao.impl.MbtiDaoImpl;
import com.spring.board.service.MbtiService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.MbtiVo;
import com.spring.board.vo.PageVo;

@Service
public class MbtiServiceImpl implements MbtiService{

	@Autowired
	private MbtiDao mbtiDao;
	private final int PER_PAGE = 5;

	@Override
	public List<BoardVo> findAllMbti(PageVo pageVo) throws Exception {
		int page = (pageVo.getPageNo() - 1) * PER_PAGE;
		
		pageVo.setPageNo(page);
		
		return this.mbtiDao.selectAllMbti(pageVo);
	}

	
	public List<MbtiVo> arrayToObject(String[] optionsArray) throws Exception {
		MbtiVo mbtiVo = new MbtiVo();
		if (optionsArray != null) {
	        // Jackson ObjectMapper를 이용하여 JSON 배열을 파싱
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(optionsArray[0], new TypeReference<List<MbtiVo>>() {});
	    }
		
		return null;
	}
	
	@Override
	public String calculateMbti(String[] optionsArray, Integer radioLength) throws Exception { // map 객체에 의해 각 항목에 맞게 계산
		mbtiDao.initMap();
		List<MbtiVo> mbtiOptions = arrayToObject(optionsArray);
		
        for (MbtiVo option : mbtiOptions) {
        	System.out.println("option: "+ option.toString());
        	assignScoreBasedOnItemType(option.getType(), option.getOptionValue(), radioLength);
        }
		
		System.out.println("result : "+ this.mbtiDao.getResult());
		
		return printMbti();
	}
	
	public void assignScoreBasedOnItemType(String key, int value, int radioLength) throws Exception {
		int score; // 변환할 점수값
		String personalityType; // value값에 따라 왼쪽인지 오른쪽인지를 구분
		String left = key.substring(0,1);
		String right = key.substring(1);
		
		// value값에 의한 왼쪽 오른쪽 구분
//		if(value <= 3 && value >= 1) { // 왼쪽
//			personalityType = key.substring(0,1);
//			score = (radioLength / 2 + 1) - value; // radioLength = 7
//		}else { // 오른쪽 또는 중앙값
//			personalityType = key.substring(1);
//			score = value - (radioLength / 2 + 1);
//		}
		
		switch(value) {
			case 1: {
				personalityType = left;
				score = 3;
				break;
			}
			case 2: {
				personalityType = left;
				score = 2;
				break;
			}
			case 3: {
				personalityType = left;
				score = 1;
				break;
			}
			case 5: {
				personalityType = right;
				score = 1;
				break;
			}
			case 6: {
				personalityType = right;
				score = 2;
				break;
			}
			case 7: {
				personalityType = right;
				score = 3;
				break;
			}
			default: {
				personalityType = right;
				score = 0;
				break;
			}
		}
		
		accumulateScoreAndMapMBTI(personalityType, score);
	}
	
	public void accumulateScoreAndMapMBTI(String mbti, Integer value) throws Exception { // 각 key에 맞게 점수를 합계해서 넣어줌.
		Integer resultValue = mbtiDao.getResult().get(mbti) + value;
		
		mbtiDao.getResult().put(mbti, resultValue);
	}
	
	public String printMbti() throws Exception { // 문항들의 점수들을 각 유형과 대소비교를 하여 MBTI 결과를 출력
		
		Map<String, Integer> result = this.mbtiDao.getResult();
		StringBuffer sb = new StringBuffer();
		// 이차원 배열을 통해 각 유형별 대비되는 유형을 짝 지어줌.
		String[][] strArr = new String[][] { {"E","I"}, {"S","N"}, {"F", "T"}, {"P", "J"} };
		
		// 이차원 배열 해체
		for(int i=0; i<strArr.length; i++) {
			String[] arr = strArr[i];
			
			// 이차원 배열에서 열 별로 반복문 실행
			for(int j=0; j<arr.length-1; j++) {
				String elementOne = arr[j]; // 열에서 첫번째 문자
				String elementTwo = arr[j+1]; // 열에서 두번째 문자
				
				// 첫번째, 두번째 문자에 대한 점수 추출
				int oneScore = result.get(elementOne);
				int twoScore = result.get(elementTwo);
				
				String str;
	
				// 점수의 대소 비교를 통해 결과값을 출력, 단 합산한 결과가 같거나 0이면 사전순으로 정렬 후 출력
				if (oneScore > twoScore) {
				    str = elementOne;
				} else if (oneScore == twoScore) {
				    str = (elementOne.compareTo(elementTwo) < 0) ? elementOne : elementTwo;
				} else {
				    str = elementTwo;
				}
				
				sb.append(str);
			}
		}
		
		System.out.println("before: "+sb.toString());
//		return stringTransfer(sb.toString());
		return sb.toString();
	}
	
//	public String stringTransfer(String input) { // 사전순으로 정렬된 mbti 문자열을 순서에 맞게 배치
//		StringBuffer sb = new StringBuffer();
//		
//		sb.append(input.contains("E")?"E":"I");
//		sb.append(input.contains("N")?"N":"S");
//		sb.append(input.contains("F")?"F":"T");
//		sb.append(input.contains("P")?"P":"J");
//		
//		System.out.println("after: "+sb.toString());
//		
//		return sb.toString();
//	}
	
	@Override
	public Integer mbtiTotalCnt() throws Exception { // 문항의 총 개수를 반환
		return this.mbtiDao.selectMbtiTotalCnt();
	}

}
