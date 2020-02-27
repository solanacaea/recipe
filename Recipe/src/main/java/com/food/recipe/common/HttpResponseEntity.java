package com.food.recipe.common;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import lombok.Data;

@Data
public class HttpResponseEntity<T> {

	private final HttpStatus status;
	
	@Nullable
	private final T body;
	
	public HttpResponseEntity(@Nullable T body, HttpStatus status) {
		this.body = body;
		this.status = status;
	}
	
	public static <T> HttpResponseEntity<T> response(T body) {
		DefaultBuilder builder = ok();
		return builder.body(body);
	}
	
	public static <T> HttpResponseEntity<T> response(T body, HttpStatus status) {
		DefaultBuilder builder = status(status);
		return builder.body(body);
	}
	
	public static DefaultBuilder ok() {
		return status(HttpStatus.OK);
	}
	
	public static DefaultBuilder status(HttpStatus status) {
		Assert.notNull(status, "HttpStatus must not be null");
		return new DefaultBuilder(status);
	}
	
	private static class DefaultBuilder {

		private final HttpStatus statusCode;
		
		public DefaultBuilder(HttpStatus statusCode) {
			this.statusCode = statusCode;
		}
		
		public <T> HttpResponseEntity<T> body(@Nullable T body) {
			return new HttpResponseEntity<>(body, this.statusCode);
		}
	}
}
