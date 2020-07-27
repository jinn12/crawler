package com.rnd.catchus.dao.mapper;

import com.rnd.catchus.model.vo.Tb20300PatentDbVO;

public interface PatentMapper {
	public int insertOnePatentDb(Tb20300PatentDbVO vo);
	public int insertListCpc(Tb20300PatentDbVO vo);
	public int deleteCpc(Tb20300PatentDbVO vo);
	public int insertListApplicant(Tb20300PatentDbVO vo);
	public int deleteApplicant(Tb20300PatentDbVO vo);
	public int insertListAgent(Tb20300PatentDbVO vo);
	public int deleteAgent(Tb20300PatentDbVO vo);
	public int insertListIpc(Tb20300PatentDbVO vo);
	public int deleteIpc(Tb20300PatentDbVO vo);
	public int insertListInventor(Tb20300PatentDbVO vo);
	public int deleteInventor(Tb20300PatentDbVO vo);
	public int updateOnePatentDb(Tb20300PatentDbVO vo);
	public int selectCount(Tb20300PatentDbVO vo);
	public Tb20300PatentDbVO selectPatentSeq(Tb20300PatentDbVO vo);

}
