package com.ordnaelmedeiros.dojeitoerrado.core.cache.filters;

import javax.inject.Inject;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordnaelmedeiros.dojeitoerrado.core.cache.annotations.Cache;
import com.ordnaelmedeiros.dojeitoerrado.core.cache.annotations.CacheClean;

import io.quarkus.redis.client.RedisClient;

@Provider
public class CacheResourceFilterFactory implements DynamicFeature {

	@Inject RedisClient redisClient;
	@Inject ObjectMapper objectMapper;
	
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		Cache cache = resourceInfo.getResourceMethod().getAnnotation(Cache.class);
		CacheClean cacheClean = resourceInfo.getResourceMethod().getAnnotation(CacheClean.class);
		if (cache!=null) {
			context.register(new CacheRequestFilter(redisClient));
			context.register(new CacheAppendResponseFilter(redisClient, objectMapper, cache));
		}
		if (cacheClean!=null)
			context.register(new CacheCleanResponseFilter(redisClient));
	}

}
