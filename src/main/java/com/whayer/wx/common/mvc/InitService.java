package com.whayer.wx.common.mvc;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class InitService implements ApplicationContextAware {
	private final static Logger log = LoggerFactory.getLogger(InitService.class);
	private ApplicationContext applicationContext;

	//@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void init() {
		createTable();
	}

	public void createTable() {
		log.info("========================  Starting the databalse initial  =======================");
		Map<String, DAO> daoMap = applicationContext.getBeansOfType(DAO.class);
		log.debug("Found {} DAOs", daoMap.size());
		Set<String> names = daoMap.keySet();
		DAO dao;
		for (String name : names) {
			log.debug("Initial the [{}] invoking the init method", name);
			dao = daoMap.get(name);
			dao.init();
		}
		log.info("========================  Finished the databalse initial  =======================");

	}
}
