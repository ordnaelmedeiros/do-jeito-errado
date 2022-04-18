package com.ordnaelmedeiros.dojeitoerrado.core.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.container.ContainerRequestContext;

public class CacheKeys {
	
	private static final String E_TAG = "/eTag";
	private static final String CONTENT = "/content";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public static String eTag(String key) {
		return key+E_TAG;
	}
	public static String content(String key) {
		return key+CONTENT;
	}
	
	public static String eTag(ContainerRequestContext requestContext) {
		return eTag(requestContext.getUriInfo().getPath());
	}
	public static String content(ContainerRequestContext requestContext) {
		return content(requestContext.getUriInfo().getPath());
	}
	
	public static String format(Date lastModified) {
		return sdf.format(lastModified);
	}
	public static Date parse(String lastModified) {
		try {
			return sdf.parse(lastModified);
		} catch (ParseException e) {
			return null;
		}
	}
	
}
