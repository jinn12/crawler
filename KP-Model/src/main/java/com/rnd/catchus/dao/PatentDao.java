package com.rnd.catchus.dao;

import org.springframework.stereotype.Repository;

import com.cmn.cmn.dao.CommonAbstractCatchUsMapper;
import com.rnd.catchus.dao.mapper.PatentMapper;
import com.rnd.catchus.model.vo.Tb20300PatentDbVO;

@Repository
public class PatentDao  extends CommonAbstractCatchUsMapper{
	public int insertOnePatentDb(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).insertOnePatentDb(vo);
	}
	public int insertListCpc(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).insertListCpc(vo);
	}
	public int deleteCpc(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).deleteCpc(vo);
	}
	public int insertListApplicant(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).insertListApplicant(vo);
	}
	public int deleteApplicant(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).deleteApplicant(vo);
	}
	public int insertListAgent(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).insertListAgent(vo);
	}
	public int deleteAgent(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).deleteAgent(vo);
	}
	public int insertListIpc(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).insertListIpc(vo);
	}
	public int deleteIpc(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).deleteIpc(vo);
	}
	public int insertListInventor(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).insertListInventor(vo);
	}
	public int deleteInventor(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).deleteInventor(vo);
	}
	public int updateOnePatentDb(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).updateOnePatentDb(vo);
	}
	public int selectCount(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).selectCount(vo);
	}
	public Tb20300PatentDbVO selectPatentSeq(Tb20300PatentDbVO vo) {
		return getSqlSession().getMapper(PatentMapper.class).selectPatentSeq(vo);
	}

}
