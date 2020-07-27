package com.rnd.solstice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rnd.solstice.dao.TrademarkDao;
import com.rnd.solstice.model.vo.Tb20300TmDbVO;
import com.rnd.solstice.service.SolsticeService;
@Service
public class SolsticeServiceImpl implements SolsticeService {

	@Autowired
	TrademarkDao trademarkDao;

	//20200725 - 파라미터 타입변경
	@Transactional(value = "txManagerSolstice", rollbackFor = {Exception.class} )
	public int mergeUpdate(Tb20300TmDbVO vo) throws Exception {
		int result = 0;
		try {
			//int cnt = trademarkDao.selectCount(vo);
			Tb20300TmDbVO tmInfo = trademarkDao.selectTmDbSeq(vo);

			//20200725 - tb_db_seq 컬럼명 변경
			if(null != tmInfo && null != tmInfo.getTmDbSeq() && !"".equals(tmInfo.getTmDbSeq())) {
				vo.setTmDbSeq(tmInfo.getTmDbSeq());
				result = trademarkDao.updateOne(vo);
			} else {
				result = trademarkDao.insertOne(vo);
			}
			//20200725 - product_detail insert 안함
//			trademarkDao.deleteProductDetail(vo);
//			if(null != vo.getProdeuctDetailInfoList() && 0 < vo.getProdeuctDetailInfoList().size()) {
//				trademarkDao.insertListProductDetail(vo);
//			}
		} catch(Exception e) {
			//저장실패 로그
			e.printStackTrace();
			throw e;
		}
		return result;
	}
}
