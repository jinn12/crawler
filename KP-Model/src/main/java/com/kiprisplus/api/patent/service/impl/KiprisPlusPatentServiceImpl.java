package com.kiprisplus.api.patent.service.impl;


import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cmn.cmn.log.Log;
import com.cmn.cmn.model.Constants;
import com.cmn.utils.DateUtils;
import com.cmn.utils.ExceptionUtils;
import com.kiprisplus.api.patent.model.vo.KpaResponseVO;
import com.kiprisplus.api.patent.model.vo.PatentAdvancedSearchResponsVO;
import com.kiprisplus.api.patent.model.vo.PatentBibliographyDetailInfoSearchResponseVO;
import com.kiprisplus.api.patent.model.vo.PatentChangeInfoSerarchVO;
import com.kiprisplus.api.patent.model.vo.PatentCpcInfoResponseVO;
import com.kiprisplus.api.patent.model.vo.advancedSearch.AdSearchResponseItemVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.AbstractInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.AgentInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.ApplicantInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.BiblioSummaryInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.ClaimInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.DesignatedStateInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.FamilyInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.ImagePathInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.InternationalInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.InventorInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.IpcInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.LegalStatusInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.PriorArtDocumentsInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.PriorityInfoVO;
import com.kiprisplus.api.patent.service.KiprisPlusPatentService;
import com.kiprisplus.helper.KiprisPlusApiServiceBuilder;
import com.rnd.catchus.model.vo.Tb20300PatentDbWithKirpisPlusDimensionInfosVO;
import com.rnd.catchus.service.CatchUsService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;

@Service
public class KiprisPlusPatentServiceImpl implements KiprisPlusPatentService, EnvironmentAware {

	private Environment env;
	private String serviceKey;

	@Autowired
	CatchUsService catchUsService;

	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		this.env = environment;
		serviceKey = env.getProperty("kp.kiprisplus.patent.restApi.accessKey");
	}

	@Override
	public String execKiprisPlusPatentBulkBatch(String uuid) throws Exception {
		//return null;
		//int currentPage = 1;
		KiprisPlusApiServiceBuilder patentKiprisPlusApiServiceBuilder = new KiprisPlusApiServiceBuilder(serviceKey);

		int processingCount = NumberUtils.toInt(env.getProperty("kp.batch.transaction.count"));
		String applicationDate = env.getProperty("kp.kiprisplus.patent.advancedSearch.applicationDate");


		PaginationInfo pageInfo = new PaginationInfo();
		pageInfo.setCurrentPageNo(1);
		pageInfo.setRecordCountPerPage(processingCount);
		pageInfo.setPageSize(10);


		do {
			Call<ResponseBody> adSearchCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
					.getPatentAdvancedSearch(applicationDate, pageInfo.getRecordCountPerPage(), pageInfo.getCurrentPageNo());
			ResponseBody groupResponse = adSearchCall.execute().body();

			JAXBContext jaxbContext = JAXBContext.newInstance(PatentAdvancedSearchResponsVO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			PatentAdvancedSearchResponsVO kpAdSerarchResult = (PatentAdvancedSearchResponsVO) unmarshaller.unmarshal(new StringReader(groupResponse.string()));

			if(Constants.YN_Y.getValue().equalsIgnoreCase(kpAdSerarchResult.getHeader().getSuccessYN())) {
				if(1 == pageInfo.getCurrentPageNo()) {
					pageInfo.setTotalRecordCount(kpAdSerarchResult.getCount().getTotalCount());
				}
				//상세조회 및 DB insert
				for(AdSearchResponseItemVO itemRow: kpAdSerarchResult.getBody().getItems().getItem()) {
					String biblioDetailResponseStr = "";
					PatentBibliographyDetailInfoSearchResponseVO kpBiblioDetailResult = null;
					for(int reSearchCnt = 0; reSearchCnt < 3; reSearchCnt++) {//서지정보가 안넘어올때가 있어서 3회까지 시도..

						Call<ResponseBody> biblioDetailSearchCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
								.getPatentBibliographyDetailInfoSearch(itemRow.getApplicationNumber());

						ResponseBody biblioDetailResponse = biblioDetailSearchCall.execute().body();

						JAXBContext jaxbContextBiblio = JAXBContext.newInstance(PatentBibliographyDetailInfoSearchResponseVO.class);
						Unmarshaller unmarshallerBiblio = jaxbContextBiblio.createUnmarshaller();

						biblioDetailResponseStr = biblioDetailResponse.string();
	//					biblioDetailResponseStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response><header><requestMsgID></requestMsgID><responseTime>2020-07-06 22:55:03.553</responseTime><responseMsgID></responseMsgID><successYN>Y</successYN><resultCode>00</resultCode><resultMsg>NORMAL SERVICE.</resultMsg></header><body><item><biblioSummaryInfoArray><biblioSummaryInfo xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\"/></biblioSummaryInfoArray><ipcInfoArray><ipcInfo><ipcDate>(2006.01.01)</ipcDate><ipcNumber>G06F 3/01</ipcNumber></ipcInfo><ipcInfo><ipcDate>(2006.01.01)</ipcDate><ipcNumber>G02B 27/01</ipcNumber></ipcInfo><ipcInfo><ipcDate>(2013.01.01)</ipcDate><ipcNumber>G06F 3/0481</ipcNumber></ipcInfo><ipcInfo><ipcDate>(2011.01.01)</ipcDate><ipcNumber>G06T 13/80</ipcNumber></ipcInfo><ipcInfo><ipcDate>(2006.01.01)</ipcDate><ipcNumber>A61B 5/0476</ipcNumber></ipcInfo><ipcInfo><ipcDate>(2006.01.01)</ipcDate><ipcNumber>A61B 5/0402</ipcNumber></ipcInfo><ipcInfo><ipcDate>(2006.01.01)</ipcDate><ipcNumber>A61B 5/024</ipcNumber></ipcInfo><ipcInfo><ipcDate>(2006.01.01)</ipcDate><ipcNumber>G08B 21/02</ipcNumber></ipcInfo><ipcInfo><ipcDate>(2012.01.01)</ipcDate><ipcNumber>G06Q 50/10</ipcNumber></ipcInfo></ipcInfoArray><familyInfoArray><familyInfo><familyApplicationNumber>1020180014945</familyApplicationNumber></familyInfo></familyInfoArray><abstractInfoArray><abstractInfo><astrtCont> 사용자의 상태를 보여주는 가상현실 헤드 마운트 디스플레이 및 이를 이용한 사용자 상태 표시 방법 및 콘텐츠 관리 방법이 개시된다. 본 발명의 일 실시예에 따르면, 사용자가 머리에 착용하여 프레임 바디 내부에 설치된 내부 디스플레이를 통해 재생되는 시청각 콘텐츠를 감상하도록 하는 가상현실 헤드 마운트 디스플레이(VR HMD)에 있어서, 상기 프레임 바디의 배면부에 설치되어, 상기 사용자의 눈을 포함하는 사용자 안면 영상을 획득하는 카메라부; 상기 사용자 안면 영상을 미리 정해진 영상 처리 알고리즘에 따라 영상 처리한 사용자 상태 영상을 생성하는 신호 처리부; 및 상기 프레임 바디의 전면부에 설치되며, 영상 처리된 상기 사용자 상태 영상을 표시하는 전면 디스플레이부를 포함하는 가상현실 헤드 마운트 디스플레이가 제공된다. </astrtCont></abstractInfo></abstractInfoArray><internationalInfoArray><internationalInfo><internationOpenDate> </internationOpenDate><internationOpenNumber> </internationOpenNumber><internationalApplicationDate> </internationalApplicationDate><internationalApplicationNumber> </internationalApplicationNumber></internationalInfo></internationalInfoArray><claimInfoArray><claimInfo><claim>1. 사용자의 상태를 보여주는 가상현실 헤드 마운트 디스플레이 시스템으로서, 상기 사용자가 머리에 착용하여 프레임 바디 내부에 설치된 내부 디스플레이를 통해 재생되는 시청각 콘텐츠를 감상하도록 하고, 상기 프레임 바디의 배면부에 설치된 카메라부를 통해 상기 사용자의 눈을 포함하는 사용자 안면 영상을 획득하는 가상현실 헤드 마운트 디스플레이(VR HMD);상기 가상현실 헤드 마운트 디스플레이와 네트워크를 통해 연결되어, 상기 사용자 안면 영상을 전송받거나 상기 사용자 안면 영상에 상응하는 사용자 상태 영상을 전송받고, 상기 사용자 안면 영상을 전송받은 경우에는 미리 정해진 영상 처리 알고리즘에 따라 상기 사용자 상태 영상으로 변환하고 상기 사용자 상태 영상을 전송받은 경우에는 그대로 상기 사용자 상태 영상을 화면에 출력하는 관리자 단말을 포함하는 가상현실 헤드 마운트 디스플레이 시스템. </claim></claimInfo><claimInfo><claim>2. 제1항에 있어서,상기 관리자 단말은 상기 사용자 상태 영상에 따른 관리자의 치료지시를 입력받고, 상기 가상현실 헤드 마운트 디스플레이는 상기 네트워크를 통해 상기 치료지시를 전달받아 상기 사용자가 확인 가능하게 출력하는 것을 특징으로 하는 가상현실 헤드 마운트 디스플레이 시스템. </claim></claimInfo><claimInfo><claim>3. 제1항에 있어서, 상기 가상현실 헤드 마운트 디스플레이 혹은 상기 관리자 단말은 상기 사용자 안면 영상 혹은 상기 사용자 상태 영상을 분석하는 분석 모듈을 더 포함하되,상기 분석 모듈에서의 분석 결과 상기 사용자가 치료회피 중인지 여부를 판별하고, 치료회피로 판정된 경우 상기 사용자가 착용한 상기 가상현실 헤드 마운트 디스플레이를 통해 경고음 혹은 디렉션 음이 출력되게 하는 것을 특징으로 하는 가상현실 헤드 마운트 디스플레이 시스템. </claim></claimInfo></claimInfoArray><applicantInfoArray><applicantInfo><address>서울특별시 성북구...</address><code>419987090404</code><country>대한민국</country><engName>Joonseok Chang</engName><name>장준석</name></applicantInfo></applicantInfoArray><inventorInfoArray><inventorInfo><address>서울특별시 성북구...</address><code>419987090404</code><country>대한민국</country><engName>Joonseok Chang</engName><name>장준석</name></inventorInfo></inventorInfoArray><agentInfoArray><agentInfo><address>서울특별시 성동구 성수일로 **, ****호(성수동*가)(에이앤에이특허법률사무소)</address><code>920060009331</code><country>대한민국</country><engName>Jinbong, Do</engName><name>도진봉</name></agentInfo></agentInfoArray><priorityInfoArray/><designatedStateInfoArray/><priorArtDocumentsInfoArray/><legalStatusInfoArray><legalStatusInfo><commonCodeName>수리 (Accepted) </commonCodeName><documentEngName>[Divisional Application] Patent Application</documentEngName><documentName>[분할출원]특허출원서</documentName><receiptDate>2019.08.19</receiptDate><receiptNumber>1-1-2019-0845145-12</receiptNumber></legalStatusInfo><legalStatusInfo><commonCodeName>발송처리완료 (Completion of Transmission) </commonCodeName><documentEngName>Notification of reason for refusal</documentEngName><documentName>의견제출통지서</documentName><receiptDate>2019.11.12</receiptDate><receiptNumber>9-5-2019-0816058-15</receiptNumber></legalStatusInfo><legalStatusInfo><commonCodeName>수리 (Accepted) </commonCodeName><documentEngName>[Designated Period Extension] Application of Period Extension(Reduction, Progress relief)</documentEngName><documentName>[지정기간연장]기간연장(단축, 경과구제)신청서</documentName><receiptDate>2020.01.10</receiptDate><receiptNumber>1-1-2020-0028167-33</receiptNumber></legalStatusInfo><legalStatusInfo><commonCodeName>보정승인간주 (Regarded as an acceptance of amendment) </commonCodeName><documentEngName>[Amendment to Description, etc.] Amendment</documentEngName><documentName>[명세서등 보정]보정서</documentName><receiptDate>2020.02.12</receiptDate><receiptNumber>1-1-2020-0147770-68</receiptNumber></legalStatusInfo><legalStatusInfo><commonCodeName>수리 (Accepted) </commonCodeName><documentEngName>[Opinion according to the Notification of Reasons for Refusal] Written Opinion(Written Reply, Written Substantiation)</documentEngName><documentName>[거절이유 등 통지에 따른 의견]의견(답변, 소명)서</documentName><receiptDate>2020.02.12</receiptDate><receiptNumber>1-1-2020-0147750-55</receiptNumber></legalStatusInfo><legalStatusInfo><commonCodeName>발송처리완료 (Completion of Transmission) </commonCodeName><documentEngName>Decision to Refuse a Patent</documentEngName><documentName>거절결정서</documentName><receiptDate>2020.06.01</receiptDate><receiptNumber>9-5-2020-0373676-11</receiptNumber></legalStatusInfo><legalStatusInfo><commonCodeName>접수중 (On receiving) </commonCodeName><documentEngName> </documentEngName><documentName>[거절이유 등 통지에 따른 의견]의견서·답변서·소명서</documentName><receiptDate>2020.07.01</receiptDate><receiptNumber>1-1-2020-0682723-82</receiptNumber></legalStatusInfo><legalStatusInfo><commonCodeName>수리 (Accepted) </commonCodeName><documentEngName>Amendment to Description, etc(Reexamination)</documentEngName><documentName>[명세서등 보정]보정서(재심사)</documentName><receiptDate>2020.07.01</receiptDate><receiptNumber>1-1-2020-0682722-36</receiptNumber></legalStatusInfo></legalStatusInfoArray><imagePathInfo><docName>1020190100885.jpg</docName><largePath>http://plus.kipris.or.kr/kiprisplusws/fileToss.jsp?arg=2ba38663aa11ff0f6ca91af6061a2e00cac00c2345e4ed82ced3c7c6f5f4e475880da8bd71abf60bfb06203d4d543c1d1b38cc3de5c26e50bd8e57eb4dc9701df5d11909f7d71bc3</largePath><path>http://plus.kipris.or.kr/kiprisplusws/fileToss.jsp?arg=ed43a0609e94d6e22d01c5c32ba711cfa7c65927e879da0a79d987d2f33875c2f51d5d641997f21902e62a3a4feb77f04b4157478e468d45b5f472702609e5ea024d9866b7e73479</path></imagePathInfo></item></body><count><numOfRows>1</numOfRows><pageNo>1</pageNo><totalCount>1</totalCount></count></response>";

						kpBiblioDetailResult = (PatentBibliographyDetailInfoSearchResponseVO) unmarshallerBiblio.unmarshal(new StringReader(biblioDetailResponseStr));

						if(null != kpBiblioDetailResult && null != kpBiblioDetailResult.getBody() && null != kpBiblioDetailResult.getBody().getItem()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo()
								&& 0 < kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().size()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0)) {//사이즈는 있는데 null로 된 경우가있음.
							break;
						} else {
							Thread.sleep(500);
						}
					}
					if(null != kpBiblioDetailResult && null != kpBiblioDetailResult.getHeader() && null != kpBiblioDetailResult.getHeader().getSuccessYN()
							&& Constants.YN_Y.getValue().equalsIgnoreCase(kpBiblioDetailResult.getHeader().getSuccessYN())) {
						//db insert
						if(null != kpBiblioDetailResult.getBody().getItem()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo()
								&& 0 < kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().size()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0)
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber()
								&& !"".equals(kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber())) {

							String patentCpcInfoResponseStr = "";
							PatentCpcInfoResponseVO cpcInfoResult = null;
							for(int reCpcSearchCnt = 0; reCpcSearchCnt < 3; reCpcSearchCnt++) {//서지정보가 안넘어올때가 있어서 3회까지 시도..
								Call<ResponseBody> patentCpcInfoCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
										.getPatentCpcInfo(itemRow.getApplicationNumber(), serviceKey);
								ResponseBody patentCpcInfoResponse = patentCpcInfoCall.execute().body();
								JAXBContext jaxbContextCpcInfo = JAXBContext.newInstance(PatentCpcInfoResponseVO.class);
								Unmarshaller unmarshallerCpcInfo = jaxbContextCpcInfo.createUnmarshaller();
								patentCpcInfoResponseStr = patentCpcInfoResponse.string();
								cpcInfoResult = (PatentCpcInfoResponseVO) unmarshallerCpcInfo.unmarshal(new StringReader(patentCpcInfoResponseStr));

								if(null != cpcInfoResult && null != cpcInfoResult.getHeader()
										&& null != cpcInfoResult.getHeader().getResultCode() && "".equals(cpcInfoResult.getHeader().getResultCode().trim())) {//사이즈는 있는데 null로 된 경우가있음.
									break;
								} else {
									Thread.sleep(500);
								}
							}
							//cpc api 호출
//							Call<ResponseBody> patentCpcInfoCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
//									.getPatentCpcInfo(itemRow.getApplicationNumber(), serviceKey);
//							ResponseBody patentCpcInfoResponse = patentCpcInfoCall.execute().body();
//							JAXBContext jaxbContextCpcInfo = JAXBContext.newInstance(PatentCpcInfoResponseVO.class);
//							Unmarshaller unmarshallerCpcInfo = jaxbContextCpcInfo.createUnmarshaller();
//							String patentCpcInfoResponseStr = patentCpcInfoResponse.string();
//							PatentCpcInfoResponseVO cpcInfoResult = (PatentCpcInfoResponseVO) unmarshallerCpcInfo.unmarshal(new StringReader(patentCpcInfoResponseStr));

							if(null != cpcInfoResult && null != cpcInfoResult.getHeader()
									&& null != cpcInfoResult.getHeader().getResultCode() && "".equals(cpcInfoResult.getHeader().getResultCode().trim())) {

								String kpaResponseStr = "";
								KpaResponseVO kpaResult = null;
								for(int reKpaSearchCnt = 0; reKpaSearchCnt < 3; reKpaSearchCnt++) {//안넘어올때가 있어서 3회까지 시도..
									Call<ResponseBody> kpaInfoCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
											.getKpaInfo(itemRow.getApplicationNumber(), serviceKey);
									ResponseBody kpaInfoResponse = kpaInfoCall.execute().body();
									JAXBContext jaxbContextCpcInfo = JAXBContext.newInstance(KpaResponseVO.class);
									Unmarshaller unmarshallerCpcInfo = jaxbContextCpcInfo.createUnmarshaller();
									kpaResponseStr = kpaInfoResponse.string();
									kpaResult = (KpaResponseVO) unmarshallerCpcInfo.unmarshal(new StringReader(kpaResponseStr));

									if(null != kpaResult && null != kpaResult.getHeader()
											&& null != kpaResult.getHeader().getResultCode() && "".equals(kpaResult.getHeader().getResultCode().trim())) {
										break;
									} else {
										Thread.sleep(500);
									}
								}
								if(null != kpaResult && null != kpaResult.getHeader()
										&& null != kpaResult.getHeader().getResultCode() && "".equals(kpaResult.getHeader().getResultCode().trim())) {
									try {
										//solsticeService.mergeUpdate(mappingKipirsPlusBiblio2Solstice(kpBiblioDetailResult));
										catchUsService.mergeUpdate(mappingKipirsPlusBiblio2DbItem(kpBiblioDetailResult, cpcInfoResult, kpaResult));
										//System.out.println(kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber());
									} catch(Exception e) {
										//디비저장 실패 로그
										Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################DB 저장실패");
										Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "[AN: " + kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber() + "] "
												+ e.getMessage());
										Log.PATENT_BATCH_LOGGER.error(ExceptionUtils.exceptionToString(e));
										e.printStackTrace();
									}
								} else {
									Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################KPA 정보조회 실패[" + itemRow.getApplicationNumber() + "]");
									Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################KPA 정보조회 실패(결과)[" + kpaResponseStr + "]");
								}

							} else {
								Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################CPC 정보조회 실패[" + itemRow.getApplicationNumber() + "]");
								Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################CPC 정보조회 실패(결과)[" + patentCpcInfoResponseStr + "]");
							}
							//insertParams.addBiblioList(mappingKipirsPlusBiblio2Solstice(kpBiblioDetailResult));
						} else {
							//서지정보 없음 - 패스 로그
							Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음[" + itemRow.getApplicationNumber() + "]");
							//Gson gson = new Gson();
							//Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + gson.toJson(kpBiblioDetailResult) + "]");
							Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + biblioDetailResponseStr + "]");
						}
					} else {
						//해당 출원번호의 상세조회 실패 - 로그
						Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################해당 출원번호의 상세조회 실패[" + itemRow.getApplicationNumber() + "]");
					}
				}
			} else {
				//데이터 조회 실패
				Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################데이터 조회 실패[KIPRIS PLUS Advanced Seracrh 실패] 로 종료..[Page: " + pageInfo.getCurrentPageNo() + "]");
				throw new Exception("[" + uuid + "]" + "데이터 조회 실패[KIPRIS PLUS Advanced Seracrh 실패]");
				//break;
			}
			pageInfo.setCurrentPageNo(pageInfo.getCurrentPageNo() + 1);//페이지 +1
		} while(pageInfo.getCurrentPageNo() <= pageInfo.getLastPageNo());

		return "SUCCESS";
	}

	@Override
	public String execKiprisPlusPatentUpdateBatch(String uuid) throws Exception {//결과안넘어옴..테스트필요.

		KiprisPlusApiServiceBuilder patentKiprisPlusApiServiceBuilder = new KiprisPlusApiServiceBuilder(serviceKey);
		int previousDay = NumberUtils.toInt(env.getProperty("kp.patent.schedules.update.previousDay"));
		Date now = new Date();
		for(int i = previousDay; 0 <= i; i--) {
			String targetDate = DateUtils.getDate2String(DateUtils.addDay(now, -1 * i), "yyyyMMdd");
			Call<ResponseBody> changeInfoSerachrCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
					.getPatentChangeInfoSearch(targetDate);
			ResponseBody changeInfoResponse = changeInfoSerachrCall.execute().body();

			JAXBContext jaxbContext = JAXBContext.newInstance(PatentChangeInfoSerarchVO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			PatentChangeInfoSerarchVO kpChangeInfoResult = (PatentChangeInfoSerarchVO) unmarshaller.unmarshal(new StringReader(changeInfoResponse.string()));

			if(Constants.YN_Y.getValue().equalsIgnoreCase(kpChangeInfoResult.getHeader().getSuccessYN())) {

				if(null != kpChangeInfoResult.getBody() && null != kpChangeInfoResult.getBody().getItme()
						&& null != kpChangeInfoResult.getBody().getItme().getTransListString() && !"".equals(kpChangeInfoResult.getBody().getItme().getTransListString().trim())) {
					StringTokenizer stTrans = new StringTokenizer(kpChangeInfoResult.getBody().getItme().getTransListString(), "|");
					while(stTrans.hasMoreTokens()) {
						String targetApplNo = stTrans.nextToken().trim();

						String biblioDetailResponseStr = "";
						PatentBibliographyDetailInfoSearchResponseVO kpBiblioDetailResult = null;
						for(int reSearchCnt = 0; reSearchCnt < 3; reSearchCnt++) {//서지정보가 안넘어올때가 있어서 3회까지 시도..

							Call<ResponseBody> biblioDetailSearchCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
									.getPatentBibliographyDetailInfoSearch(targetApplNo);

							ResponseBody biblioDetailResponse = biblioDetailSearchCall.execute().body();

							JAXBContext jaxbContextBiblio = JAXBContext.newInstance(PatentBibliographyDetailInfoSearchResponseVO.class);
							Unmarshaller unmarshallerBiblio = jaxbContextBiblio.createUnmarshaller();

							biblioDetailResponseStr = biblioDetailResponse.string();
							kpBiblioDetailResult = (PatentBibliographyDetailInfoSearchResponseVO) unmarshallerBiblio.unmarshal(new StringReader(biblioDetailResponseStr));

							if(null != kpBiblioDetailResult && null != kpBiblioDetailResult.getBody() && null != kpBiblioDetailResult.getBody().getItem()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo()
									&& 0 < kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().size()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0)) {
								break;
							} else {
								Thread.sleep(1000);
							}
						}

//						Call<ResponseBody> biblioDetailSearchCall = KiprisPlusApiServiceBuilder.getInstance(serviceKey).getKiprisPlusApiService()
//								.getTrademarkBibliographyDetailInfoSearch(targetApplNo);
//						ResponseBody biblioDetailResponse = biblioDetailSearchCall.execute().body();
//
//						JAXBContext jaxbContextBiblio = JAXBContext.newInstance(TrademarkBibliographyDetailInfoSearchResponseVO.class);
//						Unmarshaller unmarshallerBiblio = jaxbContextBiblio.createUnmarshaller();
//						String biblioDetailResponseStr = biblioDetailResponse.string();
//						TrademarkBibliographyDetailInfoSearchResponseVO kpBiblioDetailResult = (TrademarkBibliographyDetailInfoSearchResponseVO) unmarshallerBiblio.unmarshal(new StringReader(biblioDetailResponseStr));
						if(null != kpBiblioDetailResult && null != kpBiblioDetailResult.getHeader() && null != kpBiblioDetailResult.getHeader().getSuccessYN()
								&& Constants.YN_Y.getValue().equalsIgnoreCase(kpBiblioDetailResult.getHeader().getSuccessYN())) {
							//db insert
							if(null != kpBiblioDetailResult.getBody().getItem()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo()
									&& 0 < kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().size()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0)
									&& null !=  kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber()
									&& !"".equals(kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber())) {

								//insertParams.addBiblioList(mappingKipirsPlusBiblio2Solstice(kpBiblioDetailResult));
								//cpc api 호출
//								Call<ResponseBody> patentCpcInfoCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
//										.getPatentCpcInfo(targetApplNo, serviceKey);
//
//								ResponseBody patentCpcInfoResponse = patentCpcInfoCall.execute().body();
//
//								JAXBContext jaxbContextCpcInfo = JAXBContext.newInstance(PatentCpcInfoResponseVO.class);
//								Unmarshaller unmarshallerCpcInfo = jaxbContextCpcInfo.createUnmarshaller();
//
//								String patentCpcInfoResponseStr = patentCpcInfoResponse.string();
//								PatentCpcInfoResponseVO cpcInfoResult = (PatentCpcInfoResponseVO) unmarshallerCpcInfo.unmarshal(new StringReader(patentCpcInfoResponseStr));
								String patentCpcInfoResponseStr = "";
								PatentCpcInfoResponseVO cpcInfoResult = null;
								for(int reCpcSearchCnt = 0; reCpcSearchCnt < 3; reCpcSearchCnt++) {//정보가 안넘어올때가 있어서 3회까지 시도..
									Call<ResponseBody> patentCpcInfoCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
											.getPatentCpcInfo(targetApplNo, serviceKey);
									ResponseBody patentCpcInfoResponse = patentCpcInfoCall.execute().body();
									JAXBContext jaxbContextCpcInfo = JAXBContext.newInstance(PatentCpcInfoResponseVO.class);
									Unmarshaller unmarshallerCpcInfo = jaxbContextCpcInfo.createUnmarshaller();
									patentCpcInfoResponseStr = patentCpcInfoResponse.string();
									cpcInfoResult = (PatentCpcInfoResponseVO) unmarshallerCpcInfo.unmarshal(new StringReader(patentCpcInfoResponseStr));

									if(null != cpcInfoResult && null != cpcInfoResult.getHeader()
											&& null != cpcInfoResult.getHeader().getResultCode() && "".equals(cpcInfoResult.getHeader().getResultCode().trim())) {//사이즈는 있는데 null로 된 경우가있음.
										break;
									} else {
										Thread.sleep(500);
									}
								}

								if(null != cpcInfoResult && null != cpcInfoResult.getHeader()
										&& null != cpcInfoResult.getHeader().getResultCode() && "".equals(cpcInfoResult.getHeader().getResultCode().trim())) {

									String kpaResponseStr = "";
									KpaResponseVO kpaResult = null;
									for(int reKpaSearchCnt = 0; reKpaSearchCnt < 3; reKpaSearchCnt++) {//안넘어올때가 있어서 3회까지 시도..
										Call<ResponseBody> kpaInfoCall = patentKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
												.getKpaInfo(targetApplNo, serviceKey);
										ResponseBody kpaInfoResponse = kpaInfoCall.execute().body();
										JAXBContext jaxbContextCpcInfo = JAXBContext.newInstance(KpaResponseVO.class);
										Unmarshaller unmarshallerCpcInfo = jaxbContextCpcInfo.createUnmarshaller();
										kpaResponseStr = kpaInfoResponse.string();
										kpaResult = (KpaResponseVO) unmarshallerCpcInfo.unmarshal(new StringReader(kpaResponseStr));

										if(null != kpaResult && null != kpaResult.getHeader()
												&& null != kpaResult.getHeader().getResultCode() && "".equals(kpaResult.getHeader().getResultCode().trim())) {
											break;
										} else {
											Thread.sleep(500);
										}
									}
									if(null != kpaResult && null != kpaResult.getHeader()
											&& null != kpaResult.getHeader().getResultCode() && "".equals(kpaResult.getHeader().getResultCode().trim())) {
										try {
											catchUsService.mergeUpdate(mappingKipirsPlusBiblio2DbItem(kpBiblioDetailResult, cpcInfoResult, kpaResult));
										} catch(Exception e) {
											//디비저장 실패 로그
											Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################DB 저장실패");
											Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "[AN: " + kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber() + "] "
													+ e.getMessage());
											Log.PATENT_BATCH_LOGGER.error(ExceptionUtils.exceptionToString(e));
											e.printStackTrace();
										}
									} else {
										Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################KPA 정보조회 실패[" + targetApplNo + "]");
										Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################KPA 정보조회 실패(결과)[" + kpaResponseStr + "]");
									}



								} else {
									Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################CPC 정보조회 실패[" + targetApplNo + "]");
									Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################CPC 정보조회 실패(결과)[" + patentCpcInfoResponseStr + "]");
								}



							} else {
								//서지정보 없음 - 패스 로그
								Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음[" + targetApplNo + "]");
								//Gson gson = new Gson();
								Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + biblioDetailResponseStr + "]");
								//Log.BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + biblioDetailResponse.string() + "]");
							}
						} else {
							//해당 출원번호의 상세조회 실패 - 로그
							Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################해당 출원번호의 상세조회 실패[" + targetApplNo + "]");
						}
					}
				} else {
					//변경정보 없음
					Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################변경정보 없음.");
				}
			} else {
				//변경정보 조회 실패
				Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################변경정보 조회 실패.");
			}

		}
		return "SUCCESS";
	}

	private Tb20300PatentDbWithKirpisPlusDimensionInfosVO mappingKipirsPlusBiblio2DbItem(PatentBibliographyDetailInfoSearchResponseVO  kpBiblioDetail, PatentCpcInfoResponseVO cpcInfo, KpaResponseVO kpaInfo) throws Exception {

		Tb20300PatentDbWithKirpisPlusDimensionInfosVO result = new Tb20300PatentDbWithKirpisPlusDimensionInfosVO();
		BiblioSummaryInfoVO biblioSummaryInfo = kpBiblioDetail.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0);
		List<IpcInfoVO> ipcInfoList = kpBiblioDetail.getBody().getItem().getIpcInfoArray().getIpcInfo();

		List<FamilyInfoVO> familyInfoList = kpBiblioDetail.getBody().getItem().getFamilyInfoArray().getFamilyInfo();
		List<AbstractInfoVO> abstractInfoList = kpBiblioDetail.getBody().getItem().getAbstractInfoArray().getAbstractInfo();
		List<InternationalInfoVO> internationalInfoList = kpBiblioDetail.getBody().getItem().getInternationalInfoArray().getInternationalInfo();
		List<ClaimInfoVO> claimInfoList = kpBiblioDetail.getBody().getItem().getClaimInfoArray().getClaimInfo();
		List<ApplicantInfoVO> applicantInfoList = kpBiblioDetail.getBody().getItem().getApplicantInfoArray().getApplicantInfo();
		List<InventorInfoVO> inventorInfoList = kpBiblioDetail.getBody().getItem().getInventorInfoArray().getInventorInfo();
		List<AgentInfoVO> agentInfoList = kpBiblioDetail.getBody().getItem().getAgentInfoArray().getAgentInfo();

		List<PriorityInfoVO> priorityInfoList = kpBiblioDetail.getBody().getItem().getPriorityInfoArray().getPriorityInfo();
		List<DesignatedStateInfoVO> designatedStateInfoList = kpBiblioDetail.getBody().getItem().getDesignatedStateInfoArray().getDesignatedStateInfo();
		List<PriorArtDocumentsInfoVO> priorArtDocumentsInfoList = kpBiblioDetail.getBody().getItem().getPriorArtDocumentsInfoArray().getPriorArtDocumentsInfo();
		List<LegalStatusInfoVO> legalStatusInfoList = kpBiblioDetail.getBody().getItem().getLegalStatusInfoArray().getLegalStatusInfo();
		ImagePathInfoVO imagePathInfo = kpBiblioDetail.getBody().getItem().getImagePathInfo();

		result.setApplNo(replaceNtrimNEmpty2Null(biblioSummaryInfo.getApplicationNumber(), "\\-", ""));
		result.setTitleKo(trimNEmpty2Null(biblioSummaryInfo.getInventionTitle()));
		result.setTitleEn(trimNEmpty2Null(biblioSummaryInfo.getInventionTitleEng()));

		result.setApplDate(replaceNtrimNEmpty2Null(biblioSummaryInfo.getApplicationDate(), "\\.", ""));

		if(0 < applicantInfoList.size()) {
			result.setApplicant(trimNEmpty2Null(applicantInfoList.get(0).getName()));
		}

		String orgRegNo = replaceNtrimNEmpty2Null(biblioSummaryInfo.getRegisterNumber(), "\\-", "");
		if(null != orgRegNo && 20 < orgRegNo.length()) {
			StringTokenizer stRegNoInfos = new StringTokenizer(orgRegNo, ",");
			if(stRegNoInfos.hasMoreTokens()) {
				result.setRegNo(stRegNoInfos.nextToken().trim());//하나만 넣음.
			}
		} else {
			result.setRegNo(orgRegNo);
		}
		String orgRegDt = replaceNtrimNEmpty2Null(biblioSummaryInfo.getRegisterDate(), "\\.", "");
		if(null != orgRegDt && 8 < orgRegDt.length()) {
			StringTokenizer stRegDtInfos = new StringTokenizer(orgRegDt, ",");
			if(stRegDtInfos.hasMoreTokens()) {
				result.setRegDate(stRegDtInfos.nextToken().trim());//하나만 넣음.
			}
		} else {
			result.setRegDate(orgRegDt);
		}

		result.setOpenNo(replaceNtrimNEmpty2Null(biblioSummaryInfo.getOpenNumber(), "\\-", ""));
		result.setOpenDate(replaceNtrimNEmpty2Null(biblioSummaryInfo.getOpenDate(), "\\.", ""));
		result.setPubNo(replaceNtrimNEmpty2Null(biblioSummaryInfo.getPublicationNumber(), "\\-", ""));
		result.setPubDate(replaceNtrimNEmpty2Null(biblioSummaryInfo.getPublicationDate(), "\\.", ""));

		result.setApplType(trimNEmpty2Null(biblioSummaryInfo.getOriginalApplicationKind()));

		if(0 < internationalInfoList.size()) {
			result.setIntApplNo(replaceNtrimNEmpty2Null(internationalInfoList.get(0).getInternationalApplicationNumber(), "\\-", ""));
			result.setIntApplDate(replaceNtrimNEmpty2Null(internationalInfoList.get(0).getInternationalApplicationDate(), "\\.", ""));
			result.setIntOpenNo(replaceNtrimNEmpty2Null(internationalInfoList.get(0).getInternationOpenNumber(), "\\-", ""));
			result.setIntOpenDate(replaceNtrimNEmpty2Null(internationalInfoList.get(0).getInternationOpenDate(), "\\.", ""));
		}

		if(0 < priorityInfoList.size()) {
			StringBuilder sbPriority = new StringBuilder();
			for(PriorityInfoVO priorityRow: priorityInfoList) {

				if(0 < sbPriority.length()) {
					sbPriority.append(", ");
				}

				sbPriority
				//.append(", ")
				.append(trimNnull2EmptyString(priorityRow.getPriorityApplicationCountry()))
				.append("|")
				.append(trimNnull2EmptyString(priorityRow.getPriorityApplicationNumber()))
				.append("|")
				.append(trimNnull2EmptyString(priorityRow.getPriorityApplicationDate()).replaceAll("\\.", ""));
			}

			if(2000 < sbPriority.length()) {
				sbPriority.substring(0, 2000);
			}

			if(0 < sbPriority.length()) {
				result.setPriority(sbPriority.toString());
			}
		}
		result.setRegStatus(trimNEmpty2Null(biblioSummaryInfo.getRegisterStatus()));
		result.setExamStatus(null);
		result.setJudge(null);

		result.setOrgApplNo(replaceNtrimNEmpty2Null(biblioSummaryInfo.getOriginalApplicationNumber(), "\\-", ""));
		result.setOrgApplDate(replaceNtrimNEmpty2Null(biblioSummaryInfo.getOriginalApplicationDate(), "\\.", ""));

		result.setFinalExec(trimNEmpty2Null(biblioSummaryInfo.getFinalDisposal()));
		result.setRelApplNo(null);

		result.setExamFlag(trimNEmpty2Null(biblioSummaryInfo.getOriginalExaminationRequestFlag()));
		result.setExamDate(replaceNtrimNEmpty2Null(biblioSummaryInfo.getOriginalExaminationRequestDate(), "\\.", ""));
		result.setExamClaim(trimNEmpty2Null(biblioSummaryInfo.getClaimCount()));

		result.setDbYear(null);
		result.setDbMonth(null);
		result.setDbDay(null);
		result.setIpcKeyword(null);
		result.setClaimKeyword(null);

		if(0 < claimInfoList.size()) {
			String tMainClaim = trimNEmpty2Null(claimInfoList.get(0).getClaim());
			if(null != tMainClaim && 4000 < tMainClaim.length()) {
				tMainClaim = tMainClaim.substring(0,  4000);
			}
			result.setMainClaim(tMainClaim);
		}

		if(null != kpaInfo && null != kpaInfo.getBody() && null != kpaInfo.getBody().getItems()
				&& null != kpaInfo.getBody().getItems().getSummation()
				&& null != kpaInfo.getBody().getItems().getSummation().getAstrtCont()) {

			String tKpaAb = trimNEmpty2Null(kpaInfo.getBody().getItems().getSummation().getAstrtCont());
			if(null != tKpaAb && 4000 < tKpaAb.length()) {
				tKpaAb = tKpaAb.substring(0,  4000);
			}

			result.setKpa(tKpaAb);
		}



		result.setFile1Name(trimNEmpty2Null(imagePathInfo.getDocName()));
		result.setDrawPath(trimNEmpty2Null(imagePathInfo.getLargePath()));
		result.setDrawFile(trimNEmpty2Null(imagePathInfo.getPath()));

		if(0 < applicantInfoList.size()) {
			result.setApplicantInfoList(applicantInfoList);
		}
		if(0 < agentInfoList.size()) {
			result.setAgentInfoList(agentInfoList);
		}
		if(0 < ipcInfoList.size()) {
			result.setIpcInfoList(ipcInfoList);
		}
		if(0 < inventorInfoList.size()) {
			result.setInventorInfoList(inventorInfoList);
		}

		if(null != cpcInfo && null != cpcInfo.getBody() && null != cpcInfo.getBody().getItems()
			&& null != cpcInfo.getBody().getItems().getPatentCpcInfo()
			&& 0 < cpcInfo.getBody().getItems().getPatentCpcInfo().size()
			&& null != cpcInfo.getBody().getItems().getPatentCpcInfo().get(0)) {//특허는 리스트는 넘어오나 널이 있는경구가 있음
			result.setCpcInfoList(cpcInfo.getBody().getItems().getPatentCpcInfo());
		}
		return result;
	}

	private String trimNEmpty2Null(String str) {
		String result;
		if(null == str) {
			result = null;
		} else if("".equals(str.trim())) {
			result = null;
		} else {
			result = str.trim();
		}
		return result;
	}
	private String trimNnull2EmptyString(String str) {
		String result;
		if(null == str) {
			result = "";
		} else if("".equals(str.trim())) {
			result = "";
		} else {
			result = str.trim();
		}
		return result;
	}
	private String replaceNtrimNEmpty2Null(String str, String replaceRegex, String replacement) {
		String result;
		if(null == str) {
			result = null;
		} else if("".equals(str.trim())) {
			result = null;
		} else {
			result = str.trim().replaceAll(replaceRegex, replacement);
		}
		return result;
	}
	/*
	 * private void setAppRefNo2ApplNo(PatentBibliographyDetailInfoSearchResponseVO
	 * vo) { //참조번호가 존재하고 출원번호가 없을경우 참조번호를 출원번호에 set함.
	 *
	 * if(null != vo.getBody().getItem() && null !=
	 * vo.getBody().getItem().getBiblioSummaryInfoArray() && 0 <
	 * vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().
	 * size() && null !=
	 * vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get
	 * (0).getapp() &&
	 * !"".equals(vo.getBody().getItem().getBiblioSummaryInfoArray().
	 * getBiblioSummaryInfo().get(0).getAppReferenceNumber()) && (null ==
	 * vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get
	 * (0).getApplicationNumber() ||
	 * "".equals(vo.getBody().getItem().getBiblioSummaryInfoArray().
	 * getBiblioSummaryInfo().get(0).getApplicationNumber().trim()))) {
	 *
	 * vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get
	 * (0).setApplicationNumber(vo.getBody().getItem().getBiblioSummaryInfoArray().
	 * getBiblioSummaryInfo().get(0).getAppReferenceNumber().trim());
	 *
	 * }
	 *
	 *
	 * }
	 */

}
