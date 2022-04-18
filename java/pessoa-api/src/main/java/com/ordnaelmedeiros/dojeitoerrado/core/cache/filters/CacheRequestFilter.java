package com.ordnaelmedeiros.dojeitoerrado.core.cache.filters;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.ordnaelmedeiros.dojeitoerrado.core.cache.CacheKeys;

import io.quarkus.redis.client.RedisClient;

public class CacheRequestFilter implements ContainerRequestFilter {
	
	private RedisClient redisClient;
	
	public CacheRequestFilter(RedisClient redisClient) {
		this.redisClient = redisClient;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String key = CacheKeys.eTag(requestContext);
		
		EntityTag tag = null;
		Date lastModified = null;
		
		try {
			var res = redisClient.get(key);
			String etagsCache = res.toString();
			String[] etags = etagsCache.split("\\|");
			tag = new EntityTag(etags[0]);
			lastModified = CacheKeys.parse(etags[1]);
		} catch (Exception e) {
		}
		
		try {
			String eTagRes = requestContext.getHeaderString("If-None-Match");
			if (eTagRes!=null) {
				ResponseBuilder conditionalResponse = requestContext.getRequest().evaluatePreconditions(lastModified, tag);
				if (conditionalResponse!=null) {
					requestContext.abortWith(conditionalResponse.build());
					return;
				}
			}
		} catch (Exception e) {
		}
		
		key = CacheKeys.content(requestContext);
		
		var res = redisClient.get(key);
		if (res!=null) {
			requestContext.abortWith(Response
				.ok(res.toString())
				.tag(tag)
				.lastModified(lastModified)
				.cacheControl(CacheControl.valueOf("redis"))
				.build());
		}
		
	}
	

}
