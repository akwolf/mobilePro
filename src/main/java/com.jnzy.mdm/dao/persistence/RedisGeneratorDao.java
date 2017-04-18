package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.other.MemberRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hardy on 2017/1/18.
 */
@Repository
public class RedisGeneratorDao <K extends Serializable, V extends Serializable> {
    @Autowired
    private RedisTemplate<K, V> redisTemplate ;

    /**
     * 设置redisTemplate
     * @param redisTemplate the redisTemplate to set
     */
    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 RedisSerializer
     * <br>------------------------------<br>
     */
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }
    /**
     * 删除集合 ,依赖key集合
     */
    public void delete(final String key) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.del(redisTemplate.getStringSerializer().serialize(key));
                return null;
            }
        });
    }

    /**
     * 根据key获取对象
     */
    public MemberRedis get(final String keyRedis) {
        MemberRedis result = redisTemplate.execute(new RedisCallback<MemberRedis>() {
            public MemberRedis doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(keyRedis);
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                String valueRedis = serializer.deserialize(value);
                return new MemberRedis(keyRedis, valueRedis);
            }
        });
        return result;
    }
    /**
     * 添加集合
     */
    public void add(final MemberRedis memberRedis) {
        boolean result =  redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(memberRedis.getKeyRedis());
                byte[] name = serializer.serialize(memberRedis.getValueRedis());
                connection.setNX(key, name);
                return true;
            }
        }, false, true);
        System.out.println("----------"+result);
    }


    /**
     * 修改对象
     */
    public void update(final MemberRedis member) {
        String key = member.getKeyRedis();
        if (get(key) == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(member.getKeyRedis());
                byte[] name = serializer.serialize(member.getValueRedis());
                connection.set(key, name);
                return true;
            }
        });
        System.out.println("----修改redis的值------"+result);
    }
}
