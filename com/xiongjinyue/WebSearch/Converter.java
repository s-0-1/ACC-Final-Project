package com.xiongjinyue.WebSearch;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Scanner;

import org.jsoup.Jsoup;

//convert the HTMLFiles to TextFiles

public class Converter {
	
	public static void convert() throws IOException {
		File file = new File("C:\\Application\\Codes\\Final\\src\\com\\xiongjinyue\\HTMLFiles");
		File[] files = file.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				File instance = new File("C:\\Application\\Codes\\Final\\src\\com\\xiongjinyue\\HTMLFiles\\" + files[i].getName());
				org.jsoup.nodes.Document document = Jsoup.parse(instance,"UTF-8");
				PrintWriter out = new PrintWriter("C:\\Application\\Codes\\Final\\src\\com\\xiongjinyue\\TextFiles\\"+files[i].getName()+".txt");
				out.println(document.text());
				out.close();
			}	
		}
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("Please enter the URL: ");
		//String urlname = s.nextLine();
		System.out.println("Default URL: https://www.guru99.com/java-tutorial.html");
		String urlname = "https://www.guru99.com/java-tutorial.html";
		try {
			Crawler.downloadHTMLFiles(urlname,1,35);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		try {
			convert();
			System.out.println("Converting Completed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
