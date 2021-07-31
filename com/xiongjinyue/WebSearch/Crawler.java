package com.xiongjinyue.WebSearch;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Crawler {
	
	static HashSet<String> URLs = new HashSet<String>();
	private static final int DEPTH = 3;
	static int count = 0;
	
	
	public static void downloadURLs(final String urlname,int depth,int numURL) throws IOException, MalformedURLException {
		
		//the number of URLs that are downloaded
		
		String[]  files;
		File file = new File("C:\\Application\\Codes\\Final\\src\\com\\xiongjinyue\\HTMLFiles");
		files = file.list();
		
		// There is a limited depth
			if ((depth < DEPTH) && !URLs.contains(urlname)&&(files.length < numURL)) {
				if (URLs.add(urlname)) {
					// Download all the URLs
					// Create an URL 
					URL url = new URL(urlname);
					
					//build the HTTP connection 	
					BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

					int spliter = url.toString().lastIndexOf("/");
					String tempFile = url.toString().substring(spliter, url.toString().length());
					String str = tempFile + ".html";

					// create a new file
					BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Application\\Codes\\Final\\src\\com" +
							"\\xiongjinyue\\HTMLFiles\\" + str));
					
					// read each line from stream
					String link;
					while ((link = br.readLine()) != null) {
						bw.write(link);
						
					}
					
					br.close();
					bw.close();
					//print the URLs
					System.out.println(urlname);
					count++;
					
					
					// Parse HTML 
					Document document = Jsoup.connect(urlname).get();
					
					Elements linksofPage = document.select("a[href]");
					
					depth++;
					   
					// extract links
					for (Element links : linksofPage) {
						//get the absolute path
						downloadURLs(links.attr("abs:href"),depth,numURL);
					}	
					
				}
				
				
			}
			
		}
	

	public static void downloadHTMLFiles(String urlname,int depth,int numURL) throws MalformedURLException, IOException {
		
		File files = new File("C:\\Application\\Codes\\Final\\src\\com\\xiongjinyue\\HTMLFiles");
		for(File file: files.listFiles()) {
			if (!file.isDirectory()) 
		        file.delete();
		}
		
		downloadURLs(urlname, depth,numURL);
	
		System.out.println(count + " files Downloading Completed");
	}
			
	
}
