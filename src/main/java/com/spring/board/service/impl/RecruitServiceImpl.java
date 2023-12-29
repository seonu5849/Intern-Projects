package com.spring.board.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.RecruitDao;
import com.spring.board.service.RecruitService;
import com.spring.board.vo.CareerVo;
import com.spring.board.vo.CertificateVo;
import com.spring.board.vo.EducationVo;
import com.spring.board.vo.NationwideAddrVo;
import com.spring.board.vo.RecruitTotalInputData;
import com.spring.board.vo.RecruitVo;
import com.spring.board.vo.UserSaveInfo;

@Service
public class RecruitServiceImpl implements RecruitService{

	private final static String API_URL = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00000000";
	private final RecruitDao recruitDao;
	
	@Autowired
	public RecruitServiceImpl(RecruitDao recruitDao) {
		this.recruitDao = recruitDao;
	}
	
	@Override
	public NationwideAddrVo jsonParseNationwideAddr() throws Exception {
		
		URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		ObjectMapper objectMapper = new ObjectMapper();
		NationwideAddrVo nationwideAddrVo;
		
		try {
            // Set up the connection
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Check if the request was successful (status code 200)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Read the JSON content from the input stream
                nationwideAddrVo = objectMapper.readValue(connection.getInputStream(), NationwideAddrVo.class);
                return nationwideAddrVo;
            } else {
                // Handle the error response
                throw new RuntimeException("JSON파싱 중 런타임 에러: " + connection.getResponseCode());
            }
        } finally {
            // Close the connection
            connection.disconnect();
        }
	}

	@Override
	public Integer validateUser(RecruitVo recruitVo) throws Exception {
		return this.recruitDao.selectUserByNameAndPhone(recruitVo);
	}

	@Override
	public Integer saveRecruit(RecruitTotalInputData data) throws Exception {
		
		Integer saveRecruitResult = this.recruitDao.mergeRecruit(data.getRecruitVo());
		
		RecruitVo findUser = findUserByNameAndPhone(data.getRecruitVo());
		
		Integer saveEducationResult = saveEducation(data.getEducationVo(), findUser.getSeq());
		Integer saveCareerResult = saveCareer(data.getCareerVo(), findUser.getSeq());
		Integer saveCertificate = saveCertificate(data.getCertificateVo(), findUser.getSeq());
		
		if (saveRecruitResult != 1 || saveEducationResult != 1) {
			return 0;
		}
		
		return saveRecruitResult;
	}
	
	@Override
	public Integer submitRecruit(RecruitTotalInputData data) throws Exception {

		data.getRecruitVo().setSubmit("제출");
		
		Integer saveRecruitResult = this.recruitDao.mergeRecruit(data.getRecruitVo());
		
		RecruitVo findUser = findUserByNameAndPhone(data.getRecruitVo());
		
		Integer saveEducationResult = saveEducation(data.getEducationVo(), findUser.getSeq());
		Integer saveCareerResult = saveCareer(data.getCareerVo(), findUser.getSeq());
		Integer saveCertificate = saveCertificate(data.getCertificateVo(), findUser.getSeq());
		
		if (saveRecruitResult != 1 || saveEducationResult != 1) {
			return 0;
		}
		
		return saveRecruitResult;
	}

	public Integer saveEducation(List<EducationVo> educationVos, String seq) throws Exception {
		Integer affectedRows = 0;
		
		for(EducationVo educationVo : educationVos) {
			educationVo.setSeq(seq);
			
			if(educationVo.getEduSeq() == null) {
				educationVo.setEduSeq("0");
			}
			
			
			affectedRows = this.recruitDao.mergeEducation(educationVo);
		}
		
		return affectedRows;
	}
	
	public Integer saveCareer(List<CareerVo> careerVos, String seq) throws Exception {
		Integer affectedRows = 0;
		
		for(CareerVo careerVo : careerVos) {
			careerVo.setSeq(seq);
			
			if(careerVo.getCarSeq() == null) {
				careerVo.setCarSeq("0");
			}
			if(careerVo.getSalary() == null) {
				careerVo.setSalary("회사내규에 따름");
			}
			
			affectedRows = this.recruitDao.mergeCareer(careerVo);
			System.out.println("careerAffectedRows: "+affectedRows);
		}
		
		return affectedRows;
	}
	
	public Integer saveCertificate(List<CertificateVo> certificateVos, String seq) throws Exception {
		Integer affectedRows = 0;
		
		for(CertificateVo certificateVo : certificateVos) {
			certificateVo.setSeq(seq);
			
			if(certificateVo.getCertSeq() == null) {
				certificateVo.setCertSeq("0");
			}
			
			System.out.println("certificate: "+certificateVo);
			affectedRows =  this.recruitDao.mergeCertificate(certificateVo);
		}
		
		return affectedRows;
	}
	
	@Override
	public RecruitVo findUserByNameAndPhone(RecruitVo recruitVo) throws Exception {
		return this.recruitDao.selectUser(recruitVo);
	}

	@Override
	public Integer deleteEducations(String[] eduSeqs) throws Exception {
		
		Integer affectedRows = null;
		
		for(String eduSeq : eduSeqs) {
			affectedRows = this.recruitDao.deleteEducation(eduSeq);
		}
		
		return affectedRows;
	}

	@Override
	public List<EducationVo> findUserEducation(String seq) throws Exception {
		return this.recruitDao.selectEducationDetail(seq);
	}

	@Override
	public List<CareerVo> findUserCareer(String seq) throws Exception {
		return this.recruitDao.selectCareerDetail(seq);
	}

	@Override
	public List<CertificateVo> findUserCertificate(String seq) throws Exception {
		return this.recruitDao.selectCertificateDetail(seq);
	}

	@Override
	public Integer deleteCareers(String[] carSeqs) throws Exception {
		Integer affectedRows = null;
		
		for(String carSeq : carSeqs) {
			affectedRows = this.recruitDao.deleteCareer(carSeq);
		}
		
		return affectedRows;
	}

	@Override
	public Integer deleteCertifiacates(String[] certSeqs) throws Exception {
		Integer affectedRows = null;
		
		for(String certSeq : certSeqs) {
			affectedRows = this.recruitDao.deleteCertificate(certSeq);
		}
		
		return affectedRows;
	}

	@Override
	public UserSaveInfo findUserEducationDetail(String seq) throws Exception {
		String university;
		
		UserSaveInfo userSaveInfo = null;
		
		try {
			userSaveInfo = this.recruitDao.selectUserInfoEducationDetail(seq);
			int schoolYear = Integer.parseInt(userSaveInfo.getSchoolYear());
			
			if(schoolYear >=4) {
				university = "대학교";
			}else if(schoolYear < 4 && schoolYear >=3) {
				university = "대학교";
			}else {
				university = "전문대";
			}
			
			userSaveInfo.setSchoolYear("("+schoolYear+"년)");
			userSaveInfo.setSchoolName(university);
		}catch(NullPointerException e) {
			userSaveInfo = new UserSaveInfo();
			userSaveInfo.setDivision("학력사항 없음");
		}
		
		return userSaveInfo;
	}

	@Override
	public UserSaveInfo findUserCareerDetail(String seq) throws Exception {
		int year = 0;
		int month = 0;
		
		UserSaveInfo userSaveInfo = null;
		
		try {
			userSaveInfo = this.recruitDao.selectUserInfoCareerDetail(seq);
			
			if(userSaveInfo.getJobMonth() != null) {
				month = Integer.parseInt(userSaveInfo.getJobMonth());
			}
			
			if(month / 12 > 0) {
				year = month / 12;
			}
			userSaveInfo.setJobYear(String.valueOf(year));
			userSaveInfo.setJobMonth(String.valueOf(month%12));
			if(userSaveInfo.getSalary() == null) {
				userSaveInfo.setSalary("회사내규에 따름");
			}
		}catch(NullPointerException e) {
			userSaveInfo = new UserSaveInfo();
			userSaveInfo.setJobYear(String.valueOf(year));
			userSaveInfo.setJobMonth(String.valueOf(month%12));
			if(userSaveInfo.getSalary() == null) {
				userSaveInfo.setSalary("회사내규에 따름");
			}
		}
		
		return userSaveInfo;
	}

	
//	public static void main(String[] args) {
//		
//		List<String> startArray = Arrays.asList("2020.02", "2020.11", "2022.12");
//		List<String> endArray = Arrays.asList("2020.12", "2020.12", "2023.01");
//		
//		int year = 0;
//		int month = 0;
//		int dateSum = 0;
//		String[] dateDiff = new String[0];
//		for(int i=0; i<startArray.size(); i++) {
//			dateDiff = dateDiff(startArray.get(i), endArray.get(i));
//			
//			System.out.println("dateDiff: "+Arrays.toString(dateDiff));
//			dateSum += dateDiff.length;
//		}
//		System.out.println(dateSum);
//		
//		year = dateSum / 12;
//		month = dateSum % 12;
//		
//		
//		System.out.println("year = "+year+", month = "+month);
//		
//	}
//	
//	public static String[] dateDiff(String start, String end) {
//		String[] retArray = new String[0];
//		
//		Calendar startCal = toCalendar(start);
//		Calendar endCal = toCalendar(end);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//		
//		int startYM = 0;
//		int endYM = 0;
//		do {
//			startYM = Integer.parseInt(sdf.format(startCal.getTime()));
//			endYM = Integer.parseInt(sdf.format(endCal.getTime()));
//			
//			retArray = (String[]) ArrayUtils.add(retArray, startYM+""); // 배열 안에 startYM을 담아줌.
//			startCal.add(Calendar.MONTH, 1); // startYM이 담겼다면 1을 추가해준다.
//		}while(startYM < endYM); // 계속 반복을 통해 startYM이 endYM보다 커지면 반복을 종료한다.
//		
//		return retArray;
//	}
//	
//	public static Calendar toCalendar(String dateStr) {
//		DateFormat format = new SimpleDateFormat("yyyy.MM");
//		
//		Date convDate;
//		
//		try {
//			convDate = format.parse(dateStr);
//		}catch(ParseException e) {
//			return null;
//		}
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(convDate);
//		
//		return calendar;		
//	}

}
