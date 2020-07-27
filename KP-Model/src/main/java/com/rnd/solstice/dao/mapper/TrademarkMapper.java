package com.rnd.solstice.dao.mapper;

import com.rnd.solstice.model.vo.Tb20300TmDbVO;

public interface TrademarkMapper {
	public int selectCount(Tb20300TmDbVO vo);

	public int insertOne(Tb20300TmDbVO vo);

	public int updateOne(Tb20300TmDbVO vo);

	public Tb20300TmDbVO selectTmDbSeq(Tb20300TmDbVO vo);

	//public int deleteProductDetail(Tb20310TmBiblioWithDimensionInfosVO vo);

	//public int insertListProductDetail(Tb20310TmBiblioWithDimensionInfosVO vo);

}
