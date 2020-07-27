package com.kiprisplus.api.trademark.service.impl;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cmn.cmn.log.Log;
import com.cmn.cmn.model.Constants;
import com.cmn.utils.DateUtils;
import com.cmn.utils.ExceptionUtils;
import com.kiprisplus.api.trademark.model.vo.TrademarkAdvancedSearchResponsVO;
import com.kiprisplus.api.trademark.model.vo.TrademarkBibliographyDetailInfoSearchResponseVO;
import com.kiprisplus.api.trademark.model.vo.TrademarkChangeInfoSerarchVO;
import com.kiprisplus.api.trademark.model.vo.advancedSearch.AdSearchResponseItemVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.ApplicantInfoVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.ApplicationNumberInfoVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.AsignProductVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.BiblioSummaryInfoVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.OriginalApplicationIndicationVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.PriorityClaimInfoVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.PublicationInfoVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.RevisionPublicationInfoVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item.SampleImageInfoVO;
import com.kiprisplus.api.trademark.service.KiprisPlusTrademarkService;
import com.kiprisplus.helper.KiprisPlusApiServiceBuilder;
import com.naver.helper.NaverRomanizationBuilder;
import com.naver.helper.NaverTranslationBuilder;
import com.naver.model.vo.AItemsVO;
import com.naver.model.vo.NaverRomanizationResultVO;
import com.naver.model.vo.NaverTranslationResultVO;
import com.rnd.solstice.model.vo.Tb20300TmDbVO;
import com.rnd.solstice.model.vo.Tb20310ProductDetailVO;
import com.rnd.solstice.model.vo.Tb20310TmBiblioWithDimensionInfosVO;
import com.rnd.solstice.service.SolsticeService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import net.crizin.KoreanCharacter;
import net.crizin.KoreanRomanizer;
import okhttp3.ResponseBody;
import retrofit2.Call;

@Service
@PropertySource("/META-INF/config/${spring.profiles.active:local}.config.xml")
public class KiprisPlusTrademarkServiceImpl implements KiprisPlusTrademarkService, EnvironmentAware {

	private Environment env;
	private String serviceKey;
	private String naverRomanizationClientId;
	private String naverRomanizationClientSecret;
	private String naverTranslationClientId;
	private String naverTranslationClientSecret;

	//@Autowired
	//TrademarkDao trademarkDao;

	@Autowired
	SolsticeService solsticeService;



	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		this.env = environment;
		serviceKey = env.getProperty("kp.kiprisplus.trademark.restApi.accessKey");
		naverRomanizationClientId = env.getProperty("kp.naver.romanization.clientId");
		naverRomanizationClientSecret = env.getProperty("kp.naver.romanization.clientSecret");

		naverTranslationClientId = env.getProperty("kp.naver.translation.clientId");
		naverTranslationClientSecret = env.getProperty("kp.naver.translation.clientSecret");
	}

	@Override
	public String execKiprisPlusTrademarkBulkBatch(String uuid) throws Exception {
		//int currentPage = 1;
		KiprisPlusApiServiceBuilder trademarkKiprisPlusApiServiceBuilder = new KiprisPlusApiServiceBuilder(serviceKey);
		int processingCount = NumberUtils.toInt(env.getProperty("kp.batch.transaction.count"));
		String applicationDate = env.getProperty("kp.kiprisplus.trademark.advancedSearch.applicationDate");


		PaginationInfo pageInfo = new PaginationInfo();
		pageInfo.setCurrentPageNo(1);
		pageInfo.setRecordCountPerPage(processingCount);
		pageInfo.setPageSize(10);


		do {
			Call<ResponseBody> adSearchCall = trademarkKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
					.getTrademarkAdvancedSearch(applicationDate, true, true, true, true, true, true, true, true, true, true, pageInfo.getRecordCountPerPage(), pageInfo.getCurrentPageNo());
			ResponseBody groupResponse = adSearchCall.execute().body();

			JAXBContext jaxbContext = JAXBContext.newInstance(TrademarkAdvancedSearchResponsVO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			TrademarkAdvancedSearchResponsVO kpAdSerarchResult = (TrademarkAdvancedSearchResponsVO) unmarshaller.unmarshal(new StringReader(groupResponse.string()));

			if(Constants.YN_Y.getValue().equalsIgnoreCase(kpAdSerarchResult.getHeader().getSuccessYN())) {
				if(1 == pageInfo.getCurrentPageNo()) {
					pageInfo.setTotalRecordCount(kpAdSerarchResult.getCount().getTotalCount());
				}

				//Tb20310TmBiblioListVO insertParams = new Tb20310TmBiblioListVO();

				//상세조회 및 DB insert
				for(AdSearchResponseItemVO itemRow: kpAdSerarchResult.getBody().getItems().getItem()) {

					String biblioDetailResponseStr = "";
					TrademarkBibliographyDetailInfoSearchResponseVO kpBiblioDetailResult = null;
					for(int reSearchCnt = 0; reSearchCnt < 3; reSearchCnt++) {//서지정보가 안넘어올때가 있어서 3회까지 시도..

						Call<ResponseBody> biblioDetailSearchCall = trademarkKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
								.getTrademarkBibliographyDetailInfoSearch(itemRow.getApplicationNumber());

						ResponseBody biblioDetailResponse = biblioDetailSearchCall.execute().body();

						JAXBContext jaxbContextBiblio = JAXBContext.newInstance(TrademarkBibliographyDetailInfoSearchResponseVO.class);
						Unmarshaller unmarshallerBiblio = jaxbContextBiblio.createUnmarshaller();

						biblioDetailResponseStr = biblioDetailResponse.string();
						kpBiblioDetailResult = (TrademarkBibliographyDetailInfoSearchResponseVO) unmarshallerBiblio.unmarshal(new StringReader(biblioDetailResponseStr));

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
					if(null != kpBiblioDetailResult && null != kpBiblioDetailResult.getHeader() && null != kpBiblioDetailResult.getHeader().getSuccessYN()
							&& Constants.YN_Y.getValue().equalsIgnoreCase(kpBiblioDetailResult.getHeader().getSuccessYN())) {
						//biblioSummaryInfoArray 가 빈값으로 넘어올때가 있음


						setAppRefNo2ApplNo(kpBiblioDetailResult);
						//db insert
						if(null != kpBiblioDetailResult.getBody().getItem()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo()
								&& 0 < kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().size()
								&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0)
								&& null !=  kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber()
								&& !"".equals(kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber().trim())) {

							//insertParams.addBiblioList(mappingKipirsPlusBiblio2Solstice(kpBiblioDetailResult));
							try {
								solsticeService.mergeUpdate(mappingKipirsPlusBiblio2Solstice(kpBiblioDetailResult));
							} catch(Exception e) {
								//디비저장 실패 로그
								Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################DB 저장실패");
								Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "[AN: " + kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber() + "] "
										+ e.getMessage());
								Log.TRADEMARK_BATCH_LOGGER.error(ExceptionUtils.exceptionToString(e));
								e.printStackTrace();
							}

						} else {
							//서지정보 없음 - 패스 로그
							Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음[" + itemRow.getApplicationNumber() + "]");
							//Gson gson = new Gson();
							//Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + gson.toJson(kpBiblioDetailResult) + "]");
							Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + biblioDetailResponseStr + "]");
						}
					} else {
						//해당 출원번호의 상세조회 실패 - 로그
						Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################해당 출원번호의 상세조회 실패[" + itemRow.getApplicationNumber() + "]");
					}
				}
			} else {
				//데이터 조회 실패
				Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################데이터 조회 실패[KIPRIS PLUS Advanced Seracrh 실패] 로 종료..[Page: " + pageInfo.getCurrentPageNo() + "]");
				throw new Exception("[" + uuid + "]" + "데이터 조회 실패[KIPRIS PLUS Advanced Seracrh 실패]");
				//break;
			}
			pageInfo.setCurrentPageNo(pageInfo.getCurrentPageNo() + 1);//페이지 +1
		} while(pageInfo.getCurrentPageNo() <= pageInfo.getLastPageNo());

		return "SUCCESS";
	}
	@Override
	public String execKiprisPlusTrademarkUpdateBatch(String uuid) throws Exception {
		KiprisPlusApiServiceBuilder trademarkKiprisPlusApiServiceBuilder = new KiprisPlusApiServiceBuilder(serviceKey);
		int previousDay = NumberUtils.toInt(env.getProperty("kp.trademark.schedules.update.previousDay"));
		Date now = new Date();
		for(int i = previousDay; 0 <= i; i--) {
			String targetDate = DateUtils.getDate2String(DateUtils.addDay(now, -1 * i), "yyyyMMdd");
			//System.out.println(DateUtils.getDate2String(DateUtils.addDay(now, -1 * i), "yyyyMMdd"));
			Call<ResponseBody> changeInfoSerachrCall = trademarkKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
					.getTrademarkChangeInfoSearch(targetDate);
			ResponseBody changeInfoResponse = changeInfoSerachrCall.execute().body();

			JAXBContext jaxbContext = JAXBContext.newInstance(TrademarkChangeInfoSerarchVO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			TrademarkChangeInfoSerarchVO kpChangeInfoResult = (TrademarkChangeInfoSerarchVO) unmarshaller.unmarshal(new StringReader(changeInfoResponse.string()));

			if(Constants.YN_Y.getValue().equalsIgnoreCase(kpChangeInfoResult.getHeader().getSuccessYN())) {

				if(null != kpChangeInfoResult.getBody() && null != kpChangeInfoResult.getBody().getItme()
						&& null != kpChangeInfoResult.getBody().getItme().getTransListString() && !"".equals(kpChangeInfoResult.getBody().getItme().getTransListString().trim())) {
					StringTokenizer stTrans = new StringTokenizer(kpChangeInfoResult.getBody().getItme().getTransListString(), "|");
					while(stTrans.hasMoreTokens()) {
						String targetApplNo = stTrans.nextToken().trim();


						String biblioDetailResponseStr = "";
						TrademarkBibliographyDetailInfoSearchResponseVO kpBiblioDetailResult = null;
						for(int reSearchCnt = 0; reSearchCnt < 3; reSearchCnt++) {//서지정보가 안넘어올때가 있어서 3회까지 시도..

							Call<ResponseBody> biblioDetailSearchCall = trademarkKiprisPlusApiServiceBuilder.getKiprisPlusApiService()
									.getTrademarkBibliographyDetailInfoSearch(targetApplNo);

							ResponseBody biblioDetailResponse = biblioDetailSearchCall.execute().body();

							JAXBContext jaxbContextBiblio = JAXBContext.newInstance(TrademarkBibliographyDetailInfoSearchResponseVO.class);
							Unmarshaller unmarshallerBiblio = jaxbContextBiblio.createUnmarshaller();

							biblioDetailResponseStr = biblioDetailResponse.string();
							kpBiblioDetailResult = (TrademarkBibliographyDetailInfoSearchResponseVO) unmarshallerBiblio.unmarshal(new StringReader(biblioDetailResponseStr));

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
						if(null != kpBiblioDetailResult && null != kpBiblioDetailResult.getHeader() && null != kpBiblioDetailResult.getHeader().getSuccessYN()
								&& Constants.YN_Y.getValue().equalsIgnoreCase(kpBiblioDetailResult.getHeader().getSuccessYN())) {

							setAppRefNo2ApplNo(kpBiblioDetailResult);
							//db insert
							if(null != kpBiblioDetailResult.getBody().getItem()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo()
									&& 0 < kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().size()
									&& null != kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0)
									&& null !=  kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber()
									&& !"".equals(kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber().trim())) {

								//insertParams.addBiblioList(mappingKipirsPlusBiblio2Solstice(kpBiblioDetailResult));
								try {
									solsticeService.mergeUpdate(mappingKipirsPlusBiblio2Solstice(kpBiblioDetailResult));
								} catch(Exception e) {
									//디비저장 실패 로그
									Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################DB 저장실패");
									Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "[AN: " + kpBiblioDetailResult.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber() + "] "
											+ e.getMessage());
									Log.TRADEMARK_BATCH_LOGGER.error(ExceptionUtils.exceptionToString(e));
									e.printStackTrace();
								}

							} else {
								//서지정보 없음 - 패스 로그
								Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음[" + targetApplNo + "]");
								//Gson gson = new Gson();
								//Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + gson.toJson(kpBiblioDetailResult) + "]");
								Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + biblioDetailResponseStr + "]");
								//Log.BATCH_LOGGER.error("[" + uuid + "]" + "##################################서지정보 없음(결과)[" + biblioDetailResponse.string() + "]");
							}
						} else {
							//해당 출원번호의 상세조회 실패 - 로그
							Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################해당 출원번호의 상세조회 실패[" + targetApplNo + "]");
						}
					}
				} else {
					//변경정보 없음
					Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################변경정보 없음.");
				}
			} else {
				//변경정보 조회 실패
				Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################변경정보 조회 실패.");
			}

		}
		return "SUCCESS";
	}


	//20200725 - 리턴타입변경(product_Detail insert 삭제 및 컬럼명 변경)
	private Tb20300TmDbVO mappingKipirsPlusBiblio2Solstice(TrademarkBibliographyDetailInfoSearchResponseVO  kpBiblioDetail) throws Exception {
		Tb20300TmDbVO result = new Tb20300TmDbVO();
		BiblioSummaryInfoVO biblioSummaryInfo = kpBiblioDetail.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0);
		List<AsignProductVO> asignProductList = kpBiblioDetail.getBody().getItem().getAsignProductArray().getAsignProduct();

		List<OriginalApplicationIndicationVO> originalApplicationIndicationList = kpBiblioDetail.getBody().getItem().getOriginalApplicationIndicationArray().getOriginalApplicationIndication();
		List<PriorityClaimInfoVO> priorityClaimInfo = kpBiblioDetail.getBody().getItem().getPriorityClaimInfoArray().getPriorityClaimInfo();
		List<ApplicationNumberInfoVO> applicationNumberList = kpBiblioDetail.getBody().getItem().getApplicationNumberInfoArray().getApplicationNumberInfo();
		List<SampleImageInfoVO> sampleImageInfoList = kpBiblioDetail.getBody().getItem().getSampleImageInfoArray().getSampleImageInfo();
		List<PublicationInfoVO> publicationInfoList = kpBiblioDetail.getBody().getItem().getPublicationInfoArray().getPublicationInfo();
		List<RevisionPublicationInfoVO> revisionPublicationInfoList = kpBiblioDetail.getBody().getItem().getRevisionPublicationInfoArray().getRevisionPublicationInfo();
		List<ApplicantInfoVO> applicantInfoList = kpBiblioDetail.getBody().getItem().getApplicantInfoArray().getApplicantInfo();

		result.setApplNo(trimNEmpty2Null(biblioSummaryInfo.getApplicationNumber()));//메소드 적용해주야하고 빈칸 확인..
		result.setApplDate(trimNEmpty2Null(biblioSummaryInfo.getApplicationDate()));

		if(0 < applicantInfoList.size()) {
			result.setApplicant(trimNEmpty2Null(applicantInfoList.get(0).getNameKoreanLong()));
		}

		String orgRegNo = trimNEmpty2Null(biblioSummaryInfo.getRegistrationNumber());
		if(null != orgRegNo && 20 < orgRegNo.length()) {
			StringTokenizer stRegNoInfos = new StringTokenizer(orgRegNo, ",");
			if(stRegNoInfos.hasMoreTokens()) {
				result.setRegNo(stRegNoInfos.nextToken().trim());//하나만 넣음.
			}
		} else {
			result.setRegNo(orgRegNo);
		}

		String orgRegDt = trimNEmpty2Null(biblioSummaryInfo.getRegistrationDate());
		if(null != orgRegDt && 8 < orgRegDt.length()) {
			StringTokenizer stRegDtInfos = new StringTokenizer(orgRegDt, ",");
			if(stRegDtInfos.hasMoreTokens()) {
				result.setRegDate(stRegDtInfos.nextToken().trim());//하나만 넣음.
			}
		} else {
			//result.setRegNo(orgRegNo);
			result.setRegDate(orgRegDt);
		}
		//result.setRegDate(trimNEmpty2Null(biblioSummaryInfo.getRegistrationDate()));
		//result.setProdCateVersion("");
		//result.setProdCateCodeList(getAsignProductListStr(asignProductList));
		result.setProductCategoryVersion(null);//20200725 - 변경
		result.setProductCategoryList(getAsignProductListStr(asignProductList));//20200725 - 변경
		result.setPubNo(trimNEmpty2Null(biblioSummaryInfo.getPublicationNumber()));
		result.setPubDate(trimNEmpty2Null(biblioSummaryInfo.getPublicationDate()));
		result.setRegPubNo(trimNEmpty2Null(biblioSummaryInfo.getRegistrationPublicNumber()));
		result.setRegPubDate(trimNEmpty2Null(biblioSummaryInfo.getRegistrationPublicDate()));

		if(0 < originalApplicationIndicationList.size()) {
			result.setOrgApplNo(trimNEmpty2Null(originalApplicationIndicationList.get(0).getOriginalApplicationNumber()));
			result.setOrgApplDate(trimNEmpty2Null(originalApplicationIndicationList.get(0).getOriginalApplicationDate()));
		}

		if(0 < priorityClaimInfo.size()) {
			result.setPriorNo(trimNEmpty2Null(priorityClaimInfo.get(0).getPriorityApplicationNumber()));
			result.setPriorDate(trimNEmpty2Null(priorityClaimInfo.get(0).getPriorityApplicationDate()));
		}

		if(0 < applicationNumberList.size()) {
			result.setRelApplNo(trimNEmpty2Null(applicationNumberList.get(0).getRelatedApplicationNumber()));
		}
		result.setCommonStatus("");

		result.setRegalStatus(trimNEmpty2Null(biblioSummaryInfo.getRegisterStatus()));
		result.setExamStatus(trimNEmpty2Null(biblioSummaryInfo.getLastDisposalCode()));
		result.setExamDate(trimNEmpty2Null(biblioSummaryInfo.getLastDisposalDate()));


		result.setRetroCategory(trimNEmpty2Null(biblioSummaryInfo.getRetroDivisionCode()));
		result.setRetroDate(trimNEmpty2Null(biblioSummaryInfo.getRetroDate()));

		result.setTmKo(trimNEmpty2Null(biblioSummaryInfo.getProductName()));
		result.setTmEn(trimNEmpty2Null(biblioSummaryInfo.getProductNameEng()));

		if(0 < sampleImageInfoList.size()) {
			result.setImageUrl(trimNEmpty2Null(sampleImageInfoList.get(0).getPath()));
		}/////////////////////////여기할차례.

		if(0 < publicationInfoList.size()) {
			result.setApplUrl(trimNEmpty2Null(publicationInfoList.get(0).getPath()));
		}

		if(0 < revisionPublicationInfoList.size()) {
			result.setRegUrl(trimNEmpty2Null(revisionPublicationInfoList.get(0).getPath()));
			result.setRegTime(trimNEmpty2Null(revisionPublicationInfoList.get(0).getPublicationDate()));
		}

		//20200725 - product_detail insert 삭제
//		if(0 < asignProductList.size()) {
//			result.setProdeuctDetailInfoList(convertProductDetails(asignProductList));
//		}

		result.setDelFlag(Constants.YN_N.getValue());
		result.setDbRegDate(null);//20200725 - 추가
		result.setDbRegMonth(null);//20200725 - 추가
		result.setDbRegYear(null);//20200725 - 추가

		//로마변경 -

		if(null != biblioSummaryInfo.getProductName() && !"".equals(biblioSummaryInfo.getProductName().trim())){

			NaverRomanizationResultVO naverRomanizationResult = new NaverRomanizationResultVO();

			if(Constants.TRUE.getValue().equalsIgnoreCase(env.getProperty("kp.naver.romanization.trademark.use"))) {
				Call<NaverRomanizationResultVO> naverRomanizationCall = NaverRomanizationBuilder.getInstance(naverRomanizationClientId, naverRomanizationClientSecret).getNaverRomanizationService().getRomanization(biblioSummaryInfo.getProductName());
				naverRomanizationResult = naverRomanizationCall.execute().body();
			}
			if(null != naverRomanizationResult.getaResult() && 0 < naverRomanizationResult.getaResult().size()) {
				int romanIdx = 0;
				for(AItemsVO roman : naverRomanizationResult.getaResult().get(0).getaItems()) {
					//System.out.println(roman.getName());
					invokeSetter(result, "setTmRoman" + Integer.toString(++romanIdx), roman.getName());
					if(5 <= romanIdx) {
						break;
					}
				}
			} else {
				//파파고 번역
				Call<NaverTranslationResultVO> naverTranCall = NaverTranslationBuilder.getInstance(naverTranslationClientId, naverTranslationClientSecret)
						.getNaverTranslationService().getTranslation("ko", "en", biblioSummaryInfo.getProductName());

				NaverTranslationResultVO naverTranResult = naverTranCall.execute().body();

				result.setTmRoman1(trimNEmpty2Null(naverTranResult.getMessage().getResult().getTranslatedText()));
				result.setTmRoman2(trimNEmpty2Null((KoreanRomanizer.romanize(biblioSummaryInfo.getProductName(), KoreanCharacter.Type.Substantives))));
				result.setTmRoman3(trimNEmpty2Null(KoreanRomanizer.romanize(biblioSummaryInfo.getProductName(), KoreanCharacter.ConsonantAssimilation.Progressive)));
				result.setTmRoman4(trimNEmpty2Null(KoreanRomanizer.romanize(biblioSummaryInfo.getProductName(), KoreanCharacter.Type.Name)));
				result.setTmRoman5(trimNEmpty2Null(KoreanRomanizer.romanize(biblioSummaryInfo.getProductName(), KoreanCharacter.Type.NameTypical)));
			}

		}
		return result;
	}

	private String getAsignProductListStr(List<AsignProductVO> voList) {
		StringBuilder sb = new StringBuilder();
		List<String> chkDuplicateList = new ArrayList<String>();
		for(AsignProductVO row: voList) {
			if(!chkDuplicateList.contains(row.getMainCode())) {
				sb.append(" ").append(row.getMainCode());
				chkDuplicateList.add(row.getMainCode());
			}
		}

		return null != sb ? sb.toString().trim() : "";
	}

	private Object invokeSetter(Object obj, String methodName, Object params) throws Exception {
		Method exMethod = obj.getClass().getMethod(methodName, params.getClass());
		if(exMethod.getReturnType().getName().equals("void")) {
			exMethod.invoke(obj,  params);
		} else {
			return exMethod.invoke(obj, params);
		}
		return null;
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

	private void setAppRefNo2ApplNo(TrademarkBibliographyDetailInfoSearchResponseVO vo) {
		//참조번호가 존재하고 출원번호가 없을경우 참조번호를 출원번호에 set함.

		if(null != vo.getBody().getItem()
				&& null != vo.getBody().getItem().getBiblioSummaryInfoArray()
				&& 0 < vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().size()
				&& null != vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0)
				&& null != vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getAppReferenceNumber()
				&& !"".equals(vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getAppReferenceNumber())
				&& (null == vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber()
				|| "".equals(vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getApplicationNumber().trim()))) {

			vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).setApplicationNumber(vo.getBody().getItem().getBiblioSummaryInfoArray().getBiblioSummaryInfo().get(0).getAppReferenceNumber().trim());

		}


	}
	private List<Tb20310ProductDetailVO> convertProductDetails(List<AsignProductVO> asignProductList){
		List<Tb20310ProductDetailVO> result = new ArrayList<Tb20310ProductDetailVO>();

		for(AsignProductVO kpvo: asignProductList) {
			Tb20310ProductDetailVO row = new Tb20310ProductDetailVO();
			row.setProdDetailSeq(kpvo.getSeq());
			row.setProdCateVersion(null);
			row.setProdCate(kpvo.getMainCode() + "(" + kpvo.getSubCode() + ")");
			//row.setProdCate(kpvo.getMainCode());
			if(null != kpvo.getProductNameEng() && !"".equals(kpvo.getProductNameEng().trim())) {
				row.setProdCateDetail(kpvo.getProductName() + "(" + kpvo.getProductNameEng().trim() + ")");
			} else {
				row.setProdCateDetail(kpvo.getProductName());
			}

			result.add(row);
		}

		return result;
	}
}
