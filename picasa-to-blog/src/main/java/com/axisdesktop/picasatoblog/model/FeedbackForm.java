package com.axisdesktop.picasatoblog.model;

import javax.validation.constraints.Size;

public class FeedbackForm {

	@Size( min = 1, max = 100 )
	private String name;

	private String email;

	@Size( min = 10, max = 1000 )
	private String message;

	public FeedbackForm() {
	}

	public FeedbackForm( String name, String email, String message ) {
		this.name = name;
		this.email = email;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage( String message ) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "FeedbackForm [name=" + name + ", email=" + email + ", message=" + message + "]";
	}

}
