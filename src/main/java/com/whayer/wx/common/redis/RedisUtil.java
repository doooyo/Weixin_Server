//package com.whayer.wx.common.redis;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//public class RedisUtil {
//
//	private final static Logger log = LoggerFactory.getLogger(RedisUtil.class);
//
//	private static JedisPool pool = null;
//
//	public static JedisPool createPool(String ip, int port) {
//		if (pool == null) {
//			// 连接池配置对象
//			JedisPoolConfig config = new JedisPoolConfig();
//			// 最大连接池数
//			config.setMaxTotal(500);
//			// 最大空闲连接数
//			config.setMaxIdle(200);
//			config.setMinIdle(10);
//			config.setMaxWaitMillis(100000);
//			config.setTimeBetweenEvictionRunsMillis(3000);
//			config.setTestOnBorrow(true);
//			pool = new JedisPool(config, ip, port);
//		}
//		return pool;
//	}
//	
//	public static void release(Jedis jedis){
//		if(null != jedis){
//			jedis.close();
//		}
//	}
//
//	public static String get(String key) {
//		String value = null;
//		Jedis jedis = null;
//		try {
//			//获取连接
//			jedis = pool.getResource();
//			value = jedis.get(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(null, e);
//		} finally {
//			release(jedis);
//		}
//
//		return value;
//	}
//}
