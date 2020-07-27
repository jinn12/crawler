package com.rnd.solstice.service;

import com.rnd.solstice.model.vo.Tb20300TmDbVO;

//20200725 - 파라미터 타입변경
public interface SolsticeService {
	public int mergeUpdate(Tb20300TmDbVO vo) throws Exception;
}
