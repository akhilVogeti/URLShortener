package com.luv2code.urlShortenerApp.cache;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

@Component
public class Cache<T extends Serializable>  {

    @Value("${redis.port:6379}")
    int redisPort ;

    @Value("${redis.host:localhost}")
    String redisHost ;

    private JedisPool jedisPool;

    @PostConstruct
    private void getJedisPool() {
        jedisPool =  new JedisPool(redisHost, redisPort);
    }

    public void save(String key, T obj, long expiryInSeconds) throws Exception{
        Jedis conn = jedisPool.getResource();
        byte[] keyBytes = SerializationUtils.serialize(key);
        byte[] valueBytes = SerializationUtils.serialize(obj);
        conn.setex(keyBytes, expiryInSeconds, valueBytes);
        conn.close();
    }

    public T get(String key) throws Exception {
        byte[] keyBytes = SerializationUtils.serialize(key);
        Jedis conn = jedisPool.getResource();
        byte[] valueBytes = conn.get(keyBytes);
        T obj = SerializationUtils.deserialize(valueBytes);
        conn.close();
        return obj;
    }

}
