package com.food.recipe.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class HttpResponse<T> extends HttpEntity<HttpResponseEntity<T>> {

	private final Object status;
	
	public HttpResponse(@Nullable HttpResponseEntity<T> body) {
		super(body);
		this.status = HttpStatus.OK;
	}
	
	public HttpStatus getStatusCode() {
		if (this.status instanceof HttpStatus) {
			return (HttpStatus) this.status;
		}
		else {
			return HttpStatus.valueOf((Integer) this.status);
		}
	}

	/**
	 * Return the HTTP status code of the response.
	 * @return the HTTP status as an int value
	 * @since 4.3
	 */
	public int getStatusCodeValue() {
		if (this.status instanceof HttpStatus) {
			return ((HttpStatus) this.status).value();
		}
		else {
			return (Integer) this.status;
		}
	}
	
}
