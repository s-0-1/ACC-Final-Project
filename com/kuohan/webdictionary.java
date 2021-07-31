package com.kuohan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.jsoup.*;
import org.jsoup.nodes.Document;

//import htmlparser.*;
//import searchtrees.*;

public class webdictionary {

	public String transformWebToText(String theURL, String txtName)
	{
		//String txtName = "";  //String get from file
		Document doc;
		try {
			doc = Jsoup.connect(theURL).get();
			String text = doc.text();
			//System.out.println(text);
			//txtName = theURL.replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/???~??@#??%????&*??????+|{}???????????????????? ????\\\"??-]", "+") + ".txt";
			
			PrintWriter out = new PrintWriter("TemporaryFolder\\"+txtName);
			out.println(text);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return txtName;
	}
	
	//1.save those contents in a txt file
	//2.return the file name of txt
	public String transformHtmlToText(String fileName)
	{
		System.out.println("File name: "+fileName + "\n");
		String txtName = "";  //String get from file
		try {
			File input = new File(fileName);
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			String text = doc.text();
			System.out.println("The contents of the HTML file:\n" + text + "\n");
			PrintWriter out = new PrintWriter(fileName + ".txt");
			//out.println(text);
			out.close();
			
		     // Write the text to a file  
		    File tempFile =new File( fileName.trim());  
		    fileName = "TemporaryFolder\\"+tempFile.getName();  
		    txtName = fileName.substring(0, fileName.length()-4)+".txt";
		    //System.out.println("File saved in: " + txtName + "\n");
		    
		     
		     BufferedWriter writerTxt = new BufferedWriter(new FileWriter(txtName));
		     writerTxt.write(text);
		     writerTxt.close();
		     
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return txtName;
	}
	
	public void createFolder(String folderName)
	{
		File file = new File(folderName);// ???? File file = new File("D:\\123.txt");
		
		if(file.mkdirs()) {
			return;
			//System.out.println("The \"" + folderName +"\" folder was created successfully!\n");
		} else {
			System.out.println("The \"" + folderName +"\" folder already exists or failed to be created!\n");
		}
	}
	
	//delete folder
	public void deleteFolder(String folderName) 
	{
		File dir = new File(folderName);
		
		if(dir.isDirectory() == false) 
		{
			//System.out.println("Not a directory. Do nothing");
			return;
		}
		File[] listFiles = dir.listFiles();
		for(File file : listFiles)
		{
			//System.out.println("Deleting "+file.getName());
			file.delete();
		}
		//now directory is empty, so we can delete it
		//System.out.println("Deleting Directory. Success = "+dir.delete());
		
	}
	
	
	
	
	//Read a file in line, often used to read line-oriented formatted files
	//keep all lines in one string
	public String readFileByLines(String fileName) 
	{
	     
		String allLines = "";
        try {
            //System.out.println("Read the contents of the file in line, one line at a time??");
        	File file = new File(fileName);
	        
	        BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // ??ζ?????У????????null????????
            while ((tempString = reader.readLine()) != null) {
                // ????к?
                //System.out.println("line " + line + ": " + tempString);
                allLines = allLines +" "+ tempString;
                line++;
            }
            reader.close();
             
        } catch (IOException e) {
            e.printStackTrace();
            
        } 
        return allLines;
	       
	    }
	
	//Write data at the end of the document
	public void writeStringToDat(String data, String pathName) 
		{
	        try {
	            FileWriter fw = new FileWriter(pathName, true);
	            fw.write(data+ "\n");
	            
	            fw.close();
	        }
	        catch (IOException e){
	            e.printStackTrace();
	        }  
	        //System.out.println("The data has been saved to:  "+ pathName);
	    }
	
	//write contents of Map to a txt file
	public void writeMapToTxt(Map<String, Integer> map, String fileName) 
	{
		createFolder("Frequency");
	    File tempFile =new File( fileName.trim());  
	    fileName = "Frequency\\"+tempFile.getName();  
	    String txtName = fileName+".txt";
	    String frequencies ="";
	    Set<Map.Entry<String, Integer>> entries = map.entrySet();
  	     for (Map.Entry<String, Integer> entry : entries) 
  	     {
  	    	frequencies = frequencies + entry.getKey() + "=" + entry.getValue()+ "\n";
         }
	    try {
            FileWriter fw = new FileWriter(txtName);
            fw.write(frequencies);
            
            fw.close();
            System.out.println("The words and their frequencies are stored in: " + txtName);
        }
        catch (IOException e){
            e.printStackTrace();
        }  
        //System.out.println("The data has been saved to:  "+ pathName);
    }
	
	
	//got those single words from a string, all words are lowercase
	//1.Remove special characters, such as ", ". !"
	//2.Extract words
	//3.Converts all uppercase letters to lowercase
	public List<String> gotStringAllWords(String string)
	{
		//1.Remove special characters, such as ", ". !"
		//1) You can enclose the brackets with any character you want to remove, which is actually a regular expression
		String regExp="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/???~??@#??%????&*??????+|{}???????????????????? ????\"??-]";
		//2) In this case, the special character is replaced with an empty string, and the "" means to remove it directly
		String replace = " ";
		//3) The string to process
		string = string.replaceAll(regExp,replace); 
		
		//System.out.println("Value with special characters removed:\n"+ string);
		
		//2.Extract words
		List<String> list = new ArrayList();
		StringTokenizer st=new StringTokenizer(string);
		while(st.hasMoreTokens()) { 
		       list.add(st.nextToken());
		   }
		
		//3.Converts all uppercase letters to lowercase
		list = list.stream().map(String::toLowerCase).collect(Collectors.toList());
		/*for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));// ????get(int index)??????????????λ??????
			}*/
		//System.out.println(list);
		
		return list;
	}
	
	public void avlTree(String[] words, AVLTree<String> t)
	{
		//System.out.println( "Creating the Dictionary..." );

	     for( int i = 0; i < words.length; i++)
	     {
	     //    System.out.println( "INSERT: " + i );
	         t.insert( words[i] );
	         t.checkBalance( );
	     }
	     
	     //System.out.println( "Tree after insterions:" );
	     //t.printTree();
	     
	}
	
	//"words" saves all of the words in one file
	// "treeWords" is a dictionary 
	// save words and its frequency in "map"
	// Arrange the map in descending order and save to "sortedMap"
	public Map<String, Integer> calculateFrequency(String[] words, List<String> treeWords)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(int i=0; i<treeWords.size(); i++)
		{
			int sum =0;
			for(int j=0; j<words.length; j++)
			{
				if(words[j].equals(treeWords.get(i)))
				{
					sum++;
				}
			}
			map.put(treeWords.get(i), sum);
		}

		 //???entrySet
        Set<Map.Entry<String, Integer>> mapEntries = map.entrySet();

        //????????????????????????LinkedList????????????
        List<Map.Entry<String, Integer>> result = new LinkedList<>(mapEntries);
        //???????????????????е????
        Collections.sort(result, new Comparator<Map.Entry<String, Integer>>() {
            //????entry?????Entry.getValue()??????????????????
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        //???????????LinkedHashMap(????????)?У?????洢??????????????????С?
        //Integer sort = 1;
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> newEntry : result) {
            // ????????5???
            //if (sort <= 5) {
                sortedMap.put(newEntry.getKey(), newEntry.getValue());
                //++sort;
            //}
        }
        return sortedMap;

	}
	
	public void searchWords(AVLTree<String> t,String word)
	{
		word = word.toLowerCase();
		if (t.contains( word ))
		{
			System.out.println("key: " + word + " - exist in this dictionary" );
		}
		else
		{
			System.out.println("key: " + word + " - not found" );
			//t.insert(word);
			//System.out.println("key: " + word + " - have been insert in dictionary" );
			//System.out.println("Here are all the words in the dictionary:" );
			//t.printTree();
		}
   		 
	}
	
	//Split all the words in the file into an array of strings
	public String[] gotTxtAllWords(String txtName)
	{
		String txtString = readFileByLines(txtName);
		String[] allWords =  gotStringAllWords(txtString).toArray(new String[0]);
		
		return allWords;
	}
	
	//Create an avltree using an array of strings = ???????String??????????????? 
	public AVLTree<String> wordsToTree(String[] allWords)
	{
		
		//create tree??????
		AVLTree<String> avltree = new AVLTree<>( );
		avlTree(allWords, avltree);
		//avltree.printTree();
		//List<String> treeWords = avltree.treeToList(); 
		//System.out.println(treeWords);
		
		return avltree;
	}
	
	
	public Map<String, Integer> treeToSortedMap(String[] allWords, AVLTree<String> avltree)
	{
		
		List<String> treeWords = avltree.treeToList();
		
		Map<String, Integer> map = calculateFrequency(allWords, treeWords);

	
		return map;
	}
	
	//Output the map data the number is "num"
	public void printMapContent(Map<String, Integer> map, Integer num)
	{
		Integer sort = 1;
		Set<Map.Entry<String, Integer>> entries = map.entrySet();
	     for (Map.Entry<String, Integer> entry : entries) 
	     {
	        if (sort <= num)
	        {
        		 System.out.println(entry.getKey() + "=" + entry.getValue());
                 ++sort;
             }    
        }
	}
	
	//
	public Integer returnFrequencyOfAWord(String txtName, String word)
	{
		//create tree??????
		String[] allWords =  gotTxtAllWords(txtName);
		AVLTree<String> avltree = wordsToTree(allWords);
		//System.out.println("Words contained in the dictionary:\n"+avltree.treeToList()+"\n");
		//List<String> treeWords = t.treeToList(); 
		
		Map<String, Integer> map = treeToSortedMap(allWords, avltree);
		//System.out.println("Top 10 words frequency in the dictionary:");
		//printMapContent(map,10);
		word = word.toLowerCase();//Prevent capitalization
		Integer frequency = -1;
		if (map.get(word) != null)
		{
			frequency = map.get(word);
		}
		else
		{
			frequency = 0;
		}

		System.out.println(word+"="+frequency);
		return frequency;
		
	}
	
	
	public void dictionaryAndFrequency(String fileType)
	{
		Scanner sc = new Scanner(System.in);
		String txtName = "";
		fileType = fileType.toLowerCase(); //Convert all words to lowercase
		if (fileType.equals("1")||fileType.equals("webpage")||fileType.equals("web"))
		{
			System.out.println("Please enter the URL of the webpage:");	
			String webPath = sc.nextLine();
			txtName = transformWebToText(webPath,"webpage.txt");

		}
		else if (fileType.equals("2")||fileType.equals("html"))
		{
			System.out.println("Please enter the HTML file path and name:");	
			String filePath = sc.nextLine();
			txtName = transformHtmlToText(filePath);

		}
		else if (fileType.equals("3")||fileType.equals("text")||fileType.equals("txt"))
		{
			System.out.println("Please enter the file path and name:");	
			txtName = sc.nextLine();

		}
		else 
		{
			System.out.println("Sorry, what you typed is wrong!!!");	
			return;
		}
		
		//create tree??????
		System.out.println( "Creating the Dictionary..." );
		String[] allWords =  gotTxtAllWords(txtName);
		AVLTree<String> avltree = wordsToTree(allWords);
		System.out.println("Words contained in the dictionary:\n"+avltree.treeToList()+"\n");
		//List<String> treeWords = t.treeToList(); 
		
		Map<String, Integer> map = treeToSortedMap(allWords, avltree);
		System.out.println("Top 10 words frequency in the dictionary:");
		printMapContent(map,10);
		
		if(fileType.equals("1")||fileType.equals("webpage"))
		{
			System.out.println("Please enter a file name to store the words and word frequency in the webpage:");	
			String webTxt = sc.nextLine();//"webFrequency";
			writeMapToTxt(map, webTxt+"-frequency");
		}
		else if(fileType.equals("2")||fileType.equals("html")||fileType.equals("3")||fileType.equals("text"))
		{
			txtName = txtName.substring(0, txtName.length()-4);
			writeMapToTxt(map, txtName + "-frequency");
		}
		
		System.out.println("\nDo you want to find out if a word is in this file or how often a word appears?(y/n)");
		String answer = sc.nextLine();
		while(answer.equals("y")||answer.equals("Y")||answer.equals("yes")||answer.equals("Yes"))
		{
			answer = "n";
			queryWordFrequency(map,avltree);
			System.out.println("\nDo you want search another word?(y/n)");
			answer = sc.nextLine();
		}
		
	}
	
	public void queryWordFrequency(Map<String, Integer> map,AVLTree<String> avltree)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the word you want to query:");
		String word = sc.nextLine();
		word = word.toLowerCase();//Prevent capitalization
		searchWords(avltree,word);
		System.out.println("Frequency: "+word + " = " + map.get(word));
	}
	
	public void startProgram()
	{
		System.out.println("This subroutine is used to count the words in a file and the frequency of each word.\r\n"
				+ "It can handle TEXT files, HTML files and web pages.");
		Scanner sc = new Scanner(System.in);
		String start = "y";
		while(start.equals("y")||start.equals("yes"))
		{
			start = "no";
			createFolder("TemporaryFolder");//Temporary folder is used to save files generated when the program is running	
			System.out.println("Please enter the type of file you want to process:\r\n"
					+ "1.Webpage 2.HTML 3.TEXT");	
			System.out.println("\nPlease enter the file type:");	
			String fileType = sc.nextLine();
			
			dictionaryAndFrequency(fileType);
			System.out.println("Do you want to continue processing another file?(y/n)");
			start = sc.nextLine();
			start = start.toLowerCase();
		}
		deleteFolder("TemporaryFolder");

	}
	
	public static void main(String[] args)
	{
		webdictionary h = new webdictionary();
		h.startProgram();
		//h.returnFrequencyOfAWord(".html.txt","meet");
	}
}
