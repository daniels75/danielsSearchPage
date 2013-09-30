package org.daniels.samples.data;

import java.io.Serializable;
import java.net.URL;

public class SimpleURL implements Serializable {
	private Long id;
	private String url;

	public SimpleURL() {

	}

	public SimpleURL(final Long id, final String url) {
		this.id = id;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
