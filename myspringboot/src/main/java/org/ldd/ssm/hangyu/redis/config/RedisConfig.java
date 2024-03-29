package org.ldd.ssm.hangyu.redis.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ldd.ssm.hangyu.redis.cache.CacheTime;
import org.ldd.ssm.hangyu.redis.cache.CustomizedRedisCacheManager;
import org.ldd.ssm.hangyu.redis.serializer.FastJsonRedisSerializer;
import org.ldd.ssm.hangyu.redis.serializer.StringRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuhao.wang
 */
@Configuration
public class RedisConfig {

    // redis缓存的有效时间单位是秒
    @Value("${redis.default.expiration:3600}")
    private long redisDefaultExpiration;
    // 查询缓存有效时间
    @Value("${select.cache.timeout:1800}")
    private long selectCacheTimeout;
    // 查询缓存自动刷新时间
    @Value("${select.cache.refresh:1790}")
    private long selectCacheRefresh;

    /**
     * 重写Redis序列化方式，使用Json方式:
     * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
     * Spring Data JPA为我们提供了下面的Serializer：
     * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
     * 在此我们将自己配置RedisTemplate并定义Serializer。
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 全局开启AutoType，不建议使用
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
        ParserConfig.getGlobalInstance().addAccept("org.ldd.ssm.hangyu.");

        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 重写RedisCacheManager的getCache方法，实现设置key的有效时间
     * 重写RedisCache的get方法，实现触发式自动刷新
     * <p>
     * 自动刷新方案：
     * 1、获取缓存后再获取一次有效时间，拿这个时间和我们配置的自动刷新时间比较，如果小于这个时间就刷新。
     * 2、每次创建缓存的时候维护一个Map，存放key和方法信息（反射）。当要刷新缓存的时候，根据key获取方法信息。
     * 通过获取其代理对象执行方法，刷新缓存。
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        CustomizedRedisCacheManager redisCacheManager = new CustomizedRedisCacheManager(redisTemplate);
        // 开启使用缓存名称最为key前缀
        redisCacheManager.setUsePrefix(true);
        //这里可以设置一个默认的过期时间 单位是秒
//        redisCacheManager.setDefaultExpiration(redisDefaultExpiration);
        redisCacheManager.setDefaultExpiration(100);
        // 设置缓存的过期时间和自动刷新时间
//        Map<String, CacheTime> cacheTimes = new HashMap<>();
//        cacheTimes.put("getOthersDemandListIndex", new CacheTime(selectCacheTimeout, selectCacheRefresh));
//        cacheTimes.put("getOthersDemandListIndex", new CacheTime(10, 10));
//        cacheTimes.put("people1", new CacheTime(120, 115));
//        cacheTimes.put("people2", new CacheTime(120, 115));
//        redisCacheManager.setCacheTimess(cacheTimes);

        return redisCacheManager;
    }

    /**
     * 显示声明缓存key生成器
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {

        return new SimpleKeyGenerator();
    }

}
