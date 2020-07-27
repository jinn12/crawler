package com.rnd.solstice.dao;

import org.springframework.stereotype.Repository;

import com.cmn.cmn.dao.CommonAbstractSolsticeMapper;
import com.rnd.solstice.dao.mapper.TrademarkMapper;
import com.rnd.solstice.model.vo.Tb20300TmDbVO;
import com.rnd.solstice.model.vo.Tb20310TmBiblioWithDimensionInfosVO;

@Repository
public class TrademarkDao extends CommonAbstractSolsticeMapper {

	public int selectCount(Tb20300TmDbVO vo) {
		return getSqlSession().getMapper(TrademarkMapper.class).selectCount(vo);
	}

	public int insertOne(Tb20300TmDbVO vo) {
		return getSqlSession().getMapper(TrademarkMapper.class).insertOne(vo);
	}

	public int updateOne(Tb20300TmDbVO vo) {
		return getSqlSession().getMapper(TrademarkMapper.class).updateOne(vo);
	}

	public Tb20300TmDbVO selectTmDbSeq(Tb20300TmDbVO vo) {
		return getSqlSession().getMapper(TrademarkMapper.class).selectTmDbSeq(vo);
	}

	/*
	 * public int deleteProductDetail(Tb20310TmBiblioWithDimensionInfosVO vo) {
	 * return
	 * getSqlSession().getMapper(TrademarkMapper.class).deleteProductDetail(vo); }
	 *
	 * public int insertListProductDetail(Tb20310TmBiblioWithDimensionInfosVO vo) {
	 * return
	 * getSqlSession().getMapper(TrademarkMapper.class).insertListProductDetail(vo);
	 * }
	 */

}
