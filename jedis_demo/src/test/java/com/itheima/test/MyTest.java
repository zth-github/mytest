package com.itheima.test;

import com.itheima.util.JedisPoolUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class MyTest {
    @Test
    public void testJedis(){
        //创建jedis的连接 地址和端口号,6379
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //操作
        jedis.set("name","heima");//想redis保存了一个数据
        String value = jedis.get("name");
        System.out.println(value);
        //关闭连接
        jedis.close();
    }

    @Test
    public void testJedisPool(){
        //这是连接池相关的配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(30);//最大连接数
        jedisPoolConfig.setMaxIdle(20);//最大空闲连接数
        //创建连接池
        JedisPool jedisPool = new JedisPool("localhost", 6379);
        //从连接池中获取连接
        Jedis jedis = jedisPool.getResource();
        //操作
        jedis.set("name2","heima2");
        String value = jedis.get("name2");
        System.out.println(value);
        //关闭
        jedis.close();
    }

    //String类型
    //字符串类型是redis中最为基础的数据存储类型,在redis中字符串那类型的value最多可以容纳的数据长度是512M
    @Test
    public void testString(){
        Jedis jedis = JedisPoolUtil.getJedis();
        //增加
        jedis.set("name","张三");
        //获取
        String name = jedis.get("name");
        //删除
        jedis.del("name");
        //增加数据设置过期时间
        jedis.setex("name1",20,"张三1");

    }

    //hash
    //hash类型类似于java中的map,里面可以存放一组的键值对,该类型非常适合于存储java中对象的信息
    @Test
    public void testHash(){
        Jedis jedis = JedisPoolUtil.getJedis();
        //添加
        jedis.hset("1","姓名","张三");
        jedis.hset("1","姓名1","张2三");
        jedis.hset("1","姓2名","张3三");
        //获取
        String hget = jedis.hget("1", "姓名");
//        System.out.println(hget);
        //获取所有
        Map<String, String> map = jedis.hgetAll("1");
//        System.out.println(map);
        //删除
//        jedis.hdel("1","姓名");
        //删除所有
        jedis.del("1");
    }

    //list
    //list类型底层是一个双向字符串链表,里面的元素是有序的,可重复的,我们可以从链表的任何一端进行元素的增删
    @Test
    public void testList(){
        Jedis jedis = JedisPoolUtil.getJedis();
        //添加数据
        jedis.rpush("张三","男");
        jedis.lpush("张三","39");
        //获取数据
        jedis.lrange("张三",0,-1);
        //删除数据
           //删除最左侧数据
          jedis.lpop("张三");
           //删除最右侧数据
          jedis.rpop("张三");

    }

    //set
    //set类型底层是一张hash表,里面的元素是无序的,不可重复的
    @Test
    public void testSet(){
        Jedis jedis = JedisPoolUtil.getJedis();
        //添加
        jedis.sadd("001","张三");
        jedis.sadd("002","李四");
        //查询
        jedis.smembers("001");
        //删除
        jedis.srem("001","张三");
    }

    //zset
    //zset在set的基础上,加入了有序功能,再添加元素的时候,允许指定一个分数,他会按照这个分数排序
    @Test
    public void testZset(){
        Jedis jedis = JedisPoolUtil.getJedis();
        //添加
        jedis.zadd("003",10,"张三");
        //查询
        jedis.zrange("003",0,-1);
        //删除
        jedis.zrem("003","张三");
    }
}
