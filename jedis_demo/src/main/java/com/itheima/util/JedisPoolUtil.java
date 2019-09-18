package com.itheima.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
    private static JedisPool jedisPool=null;
   static {
       //这是连接池相关的配置
       JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
       jedisPoolConfig.setMaxTotal(30);//最大连接数
       jedisPoolConfig.setMaxIdle(20);//最大空闲连接数
       //创建连接池
       jedisPool = new JedisPool(jedisPoolConfig,"localhost", 6379);
   }
   public static Jedis getJedis(){
       return jedisPool.getResource();
   }
}
