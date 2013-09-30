package org.daniels.samples.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * URL searcher
 * 
 */
public class UrlSearcher {

	private static final String URL_PATTERN_STR = "^((http://|https://|www).*?)";
	private static final String LINK_PATTERN_STR = "href=\"((http://|https://|www).*?)\"";

	private final Pattern URL_PATTERN = Pattern.compile(URL_PATTERN_STR);
	private final Pattern LINK_PATTERN = Pattern.compile(LINK_PATTERN_STR);

	// private final Logger logger = Logger.getLogger(this.getClass());

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("You have to provide the URL!");
			System.exit(0);
		}

		String url = args[0];
		Integer urlCount = 1000;

		if (url.startsWith("www")) {
			url = "http://" + url;
		}

		if (args.length > 1) {
			try {
				Integer count = Integer.parseInt(args[1]);
				urlCount = count;
			} catch (NumberFormatException e) {
				// do nothing
			}
		}

		System.out
				.println("====================================================");
		System.out.println("=========== URL: " + url);
		System.out.println("=========== URL count: " + urlCount);
		System.out
				.println("====================================================");

		final UrlSearcher urlSearcher = new UrlSearcher();

		final boolean isValidURL = urlSearcher.validateURL(url);

		if (!isValidURL) {
			System.out
					.println("You have to provide the valid URL it should starts with http:// or https:// or www (e.g. http://java.dzone.com)");
			System.exit(0);
		}

		try {
			final Collection<URL> urlList = urlSearcher.createPageList(url,
					urlCount);
			urlSearcher.printResults(urlList);
		} catch (Exception e) {
			System.err.println("The URL is not invalid.");
		}
	}

	private boolean validateURL(final String url) {
		final Matcher matcher = URL_PATTERN.matcher(url);
		return matcher.matches();
	}

	private void printResults(final Collection<URL> urlList) {
		System.out
				.println("======================= URL list =================");
		int i = 0;
		for (URL url : urlList) {
			i++;
			System.out.println(" Page nr: " + i + " url: " + url.toString());
		}
	}

	public Collection<URL> createPageList(final String pageURL,
			final int urlCount) throws Exception {
		final URL startURL = new URL(pageURL);
		final List<URL> urlList = new ArrayList<URL>(urlCount);
		urlList.add(startURL);

		int idx = 0;
		while (urlCount > urlList.size() && idx < urlList.size()) {
			final URL currentUrl = urlList.get(idx);
			idx++;
			// System.out.println(">>> i: " + idx);
			// System.out.println(">>> Current url: " + currentUrl);
			final Collection<URL> linkList = createLinkList(currentUrl);
			for (URL url : linkList) {
				urlList.add(url);
				if (urlList.size() == urlCount) {
					break;
				}
			}

		}
		return urlList;
	}

	private Collection<URL> createLinkList(final URL currentUrl) throws Exception {

		final Collection<URL> linkList = new LinkedHashSet<URL>();
		final String pageContent = retrievePageContent(currentUrl);
		final Matcher macher = LINK_PATTERN.matcher(pageContent);

		while (macher.find()) {
			String foundLink = macher.group(1);
			try {
				URL url = new URL(foundLink);
				linkList.add(url);
			} catch (MalformedURLException e) {
				throw new Exception("Error - link to invalid url: " + foundLink);
			}
		}
		return linkList;
	}

	private String retrievePageContent(URL url) throws Exception{
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				stringBuilder.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			throw new Exception("An error occured while atempt to retrieve content from "
					+ url);
			
		}
		return stringBuilder.toString();
	}

}
