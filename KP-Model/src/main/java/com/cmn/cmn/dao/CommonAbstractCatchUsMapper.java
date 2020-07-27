package com.cmn.cmn.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

public abstract class CommonAbstractCatchUsMapper extends EgovAbstractMapper {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@Resource(name = "sqlSessionCatchUs")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}
}
