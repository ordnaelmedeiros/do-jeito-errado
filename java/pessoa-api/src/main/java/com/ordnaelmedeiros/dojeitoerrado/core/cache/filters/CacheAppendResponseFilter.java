package com.ordnaelmedeiros.dojeitoerrado.core.cache.filters;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.jboss.resteasy.reactive.RestResponse.StatusCode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordnaelmedeiros.dojeitoerrado.core.cache.CacheKeys;
import com.ordnaelmedeiros.dojeitoerrado.core.cache.annotations.Cache;

import io.quarkus.redis.client.RedisClient;

public class CacheAppendResponseFilter implements ContainerResponseFilter {
	
	private RedisClient redisClient;
	private ObjectMapper objectMapper;
//	private Cache cache;
	private String expire;
	
	public CacheAppendResponseFilter(
		RedisClient redisClient,
		ObjectMapper objectMapper,
		Cache cache
	) {
		this.redisClient = redisClient;
		this.objectMapper = objectMapper;
//		this.cache = cache;
		this.expire = Integer.toString(cache.expire());
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if (StatusCode.OK==responseContext.getStatus()) {
			cacheETag(requestContext, responseContext);
			cacheContent(requestContext, responseContext);
		}
	}
	
	private void cacheETag(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
		if (responseContext.getEntityTag()!=null) { 
			String key = CacheKeys.eTag(requestContext);
			redisClient.del(List.of(key));
			redisClient.append(key, new StringJoiner("|")
				.add(responseToETag(responseContext))
				.add(responseToLastModified(responseContext))
				.toString());
			redisClient.expire(key, expire);
		}
	}

	private void cacheContent(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws JsonProcessingException {
		if (responseContext.getEntity()!=null) {
			String key = CacheKeys.content(requestContext);
			redisClient.del(List.of(key));
			redisClient.append(key, objectMapper.writeValueAsString(responseContext.getEntity()));
			redisClient.expire(key, expire);
		}
	}
	
	private String responseToETag(ContainerResponseContext responseContext) {
		return responseContext.getEntityTag().getValue();
	}
	
	private String responseToLastModified(ContainerResponseContext responseContext) {
		Date lastModified = responseContext.getLastModified();
		if (lastModified!=null)
			return CacheKeys.format(lastModified);
		else
			return null;
	}
	
}
