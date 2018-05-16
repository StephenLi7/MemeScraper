import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupExample {

	private static final LinkedList<String> Document = null;
	private String baseUrl;
	private Document currentDoc;
	private LinkedList<String> UrlList;

	/**
	 * Constructor that initializes the base URL and loads the document produced
	 * from that URL
	 */
	public JSoupExample(int pageNum) {
		this.baseUrl = "http://knowyourmeme.com/memes/popular/page/" + pageNum;
		System.out.println(baseUrl);
		try {
			this.currentDoc = getDOMFromURL(baseUrl);
		} catch (IOException e) {
			System.out.println("Could not get the home page!");
		}
		System.out.println("Getting memes from page " + pageNum);
	}

	// getter method to get the main document
	public Document getCurrentDoc() {
		return currentDoc;
	}

	/**
	 * Method to get the elements of the international organizations, store them
	 * in a arraylist, sort them, and print the results
	 * 
	 * 
	 * @return void
	 */
	public void getElements() {

		UrlList = new LinkedList<String>();
		Element table = currentDoc.getElementById("entries");
		Elements listTags = table.select("tbody tr h2 a");

		for (org.jsoup.nodes.Element item : listTags) {

			String str = item.attr("href");
			UrlList.add("http://knowyourmeme.com" + str);

		}
		// for each url, create meme
		System.out.println("Calling createMemeObject for each url...");

		for (String url : UrlList) {

			createMemeObject(url);
		}

		/*
		 * Thread t1 = new MyThread(UrlList, 1, 3); Thread t2 = new
		 * MyThread(UrlList, 5, 3); Thread t3 = new MyThread(UrlList, 9, 3);
		 * Thread t4 = new MyThread(UrlList, 13, 3);
		 * 
		 * t1.run(); t2.run(); t3.run(); t4.run();
		 */
		System.out.println("Finished scraping page!");

	}

	class MyThread extends Thread {
		String url;
		LinkedList<String> UrlList;
		int start;
		int range;

		MyThread(LinkedList<String> UrlList, int start, int range) {
			this.UrlList = UrlList;
			this.start = start;
			this.range = range;
		}

		public void run() {

			for (int i = start; i < start + range; i++) {
				url = UrlList.get(i);
				createMemeObject(url);
			}

		}
	}

	public void createMemeObject(String url) {
		String name = "";
		String about = "";
		int year = 0;
		String origin = "";
		LinkedList<String> Typelist = new LinkedList<String>();
		LinkedList<String> Taglist = new LinkedList<String>();

		Document d = null;
		try {
			d = getDOMFromURL(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// get name
		Elements elementName = d.select("header section h1");
		for (org.jsoup.nodes.Element item : elementName) {
			String str = item.text();
			str.replace("\n", "");
			// System.out.println(str);
			name = str;

		}

		// get about Elements
		Elements elementAbout = d.select("h2:contains(About) + p");
		for (org.jsoup.nodes.Element item : elementAbout) {

			String str = item.text();
			about = str;

		}

		// get origin
		Elements elementOrigin = d.select("dt:contains(Origin) + dd a");
		for (org.jsoup.nodes.Element item : elementOrigin) {
			String str = item.text();
			origin = str;

		}

		// get year
		Elements elementYear = d.select("dt:contains(Year) + dd a");
		for (org.jsoup.nodes.Element item : elementYear) {

			String str = item.text();
			year = Integer.parseInt(str);
			// System.out.println(str);

		}

		// get type
		Elements listTypes = d.select("dt:contains(Type) + dd a");
		for (org.jsoup.nodes.Element item : listTypes) {

			String str = item.text();
			Typelist.add(str);
			// System.out.println(str);

		}

		// get tags
		Elements listTags = d.select("dt:contains(Tags) + dd a");
		for (org.jsoup.nodes.Element item : listTags) {

			String str = item.text();
			Taglist.add(str);
		}

		// create meme object
		Meme thisMeme = new Meme(name, about, year, origin, Typelist, Taglist);

		// add to map
		// System.out.println(thisMeme.getName());
		UserQueryManager.memeMap.put(thisMeme.getName(), thisMeme);

		UserQueryManager.memeNameList.add(thisMeme.getName());
	}

	/**
	 * Method to get a Document from a String URL
	 * 
	 * @param u
	 * @return Document
	 * @throws IOException
	 */
	public Document getDOMFromURL(String u) throws IOException {
		URL url = new URL(u);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder sb = new StringBuilder();
		String curr = in.readLine();
		while (curr != null) {
			sb.append(curr);
			curr = in.readLine();
		}
		return Jsoup.parse(sb.toString());
	}

}
