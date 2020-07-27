package com.rnd.catchus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rnd.catchus.dao.PatentDao;
import com.rnd.catchus.model.vo.Tb20300PatentDbVO;
import com.rnd.catchus.model.vo.Tb20300PatentDbWithKirpisPlusDimensionInfosVO;
import com.rnd.catchus.service.CatchUsService;

@Service
public class CatchUsServiceImpl implements CatchUsService {
	@Autowired
	PatentDao patentDao;

	@Override
	@Transactional(value = "txManagerCatchUs", rollbackFor = {Exception.class} )
	public int mergeUpdate(Tb20300PatentDbWithKirpisPlusDimensionInfosVO vo) throws Exception {
		int result = 0;
		try {
			//int cnt = trademarkDao.selectCount(vo);
			Tb20300PatentDbVO patentInfo = patentDao.selectPatentSeq(vo);

			if(null != patentInfo && null != patentInfo.getPatentSeq() && !"".equals(patentInfo.getPatentSeq())) {
				vo.setPatentSeq(patentInfo.getPatentSeq());
				result += patentDao.updateOnePatentDb(vo);
			} else {
				result += patentDao.insertOnePatentDb(vo);
			}
			//cpc정보
			patentDao.deleteCpc(vo);
			if(null != vo.getCpcInfoList() && 0 < vo.getCpcInfoList().size()) {
				patentDao.insertListCpc(vo);
			}

			patentDao.deleteIpc(vo);
			if(null != vo.getIpcInfoList() && 0 < vo.getIpcInfoList().size()) {
				patentDao.insertListIpc(vo);
			}

			patentDao.deleteApplicant(vo);
			if(null != vo.getApplicantInfoList() && 0 < vo.getApplicantInfoList().size()) {
				patentDao.insertListApplicant(vo);
			}

			patentDao.deleteInventor(vo);
			if(null != vo.getInventorInfoList() && 0 < vo.getInventorInfoList().size()) {
				patentDao.insertListInventor(vo);
			}

			patentDao.deleteAgent(vo);
			if(null != vo.getAgentInfoList() && 0 < vo.getAgentInfoList().size()) {
				patentDao.insertListAgent(vo);
			}
		} catch(Exception e) {
			//저장실패 로그
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
