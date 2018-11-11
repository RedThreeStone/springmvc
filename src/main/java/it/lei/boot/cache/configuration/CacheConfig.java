package it.lei.boot.cache.configuration;

import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.UnsupportedEncodingException;

@Configuration
public class CacheConfig {
    @Value("${springext.cache.redis.topic.twoLevel.channelType}")
    private  String channelName;
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory, MessageListenerAdapter adapter){
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(factory);
        //PatternTopic设置接收哪种格式的消息
        listenerContainer.addMessageListener(adapter,new PatternTopic(channelName));
        return  listenerContainer;
    }
    @Bean
    MessageListenerAdapter listenerAdapter(final TwoLevelCacheManager cacheManager){
        return  new MessageListenerAdapter(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                try {
                    String cacheName = new String(message.getBody(), "UTF-8");
                    byte[] channelByte = message.getChannel();
                    String channel2=new String(channelByte,"UTF-8");
                    if("twoLevelClear".equals(channel2)){
                        cacheManager.receiver(cacheName);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Bean
    public TwoLevelCacheManager cacheManager(RedisTemplate redisTemplate){
        RedisCacheWriter cacheWriter = RedisCacheWriter.lockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        //使用string座位序列化器
        RedisSerializationContext.SerializationPair<String> pair = RedisSerializationContext.SerializationPair.fromSerializer
                (new StringRedisSerializer(CharsetUtil.UTF_8));
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(pair);

        return new  TwoLevelCacheManager(redisTemplate,cacheWriter,configuration);
    }
}
