package com.example.javaclass.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@Slf4j
public class JedisUtil {
    @Autowired
    private JedisPool jedisPool;

    public Object hgetAsObj(String key, String field) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hget(key, field);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
            returnResource(jedisPool, jedis);
        }
        return unserialize(res);
    }

    public Long hset(String key, String field, Object value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hset(key, field, ObjTOSerialize(value));
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
            returnResource(jedisPool, jedis);
        }
        return res;
    }


    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String ObjTOSerialize(Object obj) throws JsonProcessingException {
        try {
            var string = objectMapper.writeValueAsString(obj);
            return string;
        } catch (Exception ignored) {
            return "";
        }
    }
    public static Object unserialize(String string)  {
        try {
            var value = objectMapper.readValue(string, Object.class);
            return value;
        } catch (Exception ignored){
            return null;
        }
    }

    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
