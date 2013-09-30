package org.daniels.samples.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.daniels.samples.data.SimpleURL;
import org.daniels.samples.utils.UrlSearcher;
import org.slf4j.Logger;

/**
 * Start page of application searchPagesWeb.
 */
public class Index {

	@Inject
	private Logger logger;

	@Inject
	private AlertManager alertManager;

	@Property
	private SimpleURL currentURL;

	@Property
	@Persist
	private String searchQuery;
	
	@Property
	@Persist
	private String urlCount;

	void setupRender() throws MalformedURLException {



	}

	Object onSubmit() {
		logger.info(">>> onSubmit");
		
		Integer urlCnt = 1000;
		if (StringUtils.isNotEmpty(urlCount)) {
			try {
				urlCnt = Integer.parseInt(urlCount);
			} catch (Exception exp) {
				alertManager.alert(Duration.TRANSIENT, Severity.WARN,
						exp.getMessage());
			}

		}

		if (StringUtils.isNotEmpty(searchQuery)) {

			logger.info(">>>> setupRender - searchSquery: " + searchQuery
					+ " Search for: " + urlCount);

			pages = new ArrayList<SimpleURL>();
			UrlSearcher urlSearcher = new UrlSearcher();
			Collection<URL> urlist = new ArrayList<URL>();

			if (searchQuery.startsWith("www")) {
				searchQuery = "http://" + searchQuery;
			}

			try {
				urlist = urlSearcher.createPageList(searchQuery, urlCnt);
			} catch (Exception exp) {
				alertManager.alert(Duration.TRANSIENT, Severity.WARN,
						exp.getMessage());
			}

			Iterator<URL> iterator = urlist.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i++;
				pages.add(new SimpleURL(new Long(i), iterator.next().toString()));
			}

		}
		
		return this;
	}

	Object onDelete() {
		logger.warn(">>> onDelete");
		searchQuery = null;
		urlCount = null;
		pages = new ArrayList<SimpleURL>();
		return this;
	}

	@Persist
	@Property
	private List<SimpleURL> pages;



}
