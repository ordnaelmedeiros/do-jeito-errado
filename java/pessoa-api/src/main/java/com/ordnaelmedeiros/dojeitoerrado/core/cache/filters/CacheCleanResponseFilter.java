package com.ordnaelmedeiros.dojeitoerrado.core.cache.filters;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import com.ordnaelmedeiros.dojeitoerrado.core.cache.CacheKeys;

import io.quarkus.redis.client.RedisClient;

public class CacheCleanResponseFilter implements ContainerResponseFilter {
	
	RedisClient redisClient;
	
	public CacheCleanResponseFilter(RedisClient redisClient) {
		this.redisClient = redisClient;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		redisClient.del(List.of(
			CacheKeys.eTag(requestContext),
			CacheKeys.content(requestContext)
		));
	}

}
