package com.ruoqishan;

import com.kuohan.webdictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

	 
public class Search {
	static String pathName = "TextFiles\\";
	public static int[] StringMatch(String string, String txt){	  
	      String pattern = "\\b" + string + "\\b";
	      int sum = 0;     
	      Pattern r = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
	      Matcher m = r.matcher(txt);
	      if (m.find( )) {
	    	  sum++;
	    	  int pos = m.start(0);
	    	  while (m.find( ))
	    		 sum++; 
	            int[] arr = {pos,sum};
	   		    return arr;
	      }
	  
	        else
	            return null;      
	   }
    
    public static int getPosition(String string, String txt){
    	  String pattern = "\\b" + string + "\\b";   
	      Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
	      Matcher m = r.matcher(txt);
	      if (m.find( ))
	    	  return m.start(0);	 
        else
              return -1;
   }
    
    
    public static String getContent(int position, String fileName) throws IOException{
	String content = "";
	FileInputStream fis = new FileInputStream(pathName + fileName +".txt");
	byte[] b = new byte[30]; 
	if (position > 6) 
    fis.skip(position - 6);	
      
    int length = fis.read(b);

         if (length != -1) {      	
        	b = Arrays.copyOfRange(b,0,length);
        	content = new String(b) + "...";
    }
     fis.close();
	return content;      
	}
	
    
    
    
    public static void main(String[] args) throws IOException {
    	Scanner sacn = new Scanner(System.in);
        System.out.println("Please enter words");
        String sacnner = sacn.nextLine();
  
    	String str = sacnner;
	    ArrayList<String> list = new ArrayList<String>();     
	    StringTokenizer st1 = new StringTokenizer(str, " ,.");
	    while (st1.hasMoreTokens())
	    	list.add(st1.nextToken()); 
	    String[] inputs = new String[list.size()];
	    for(int i = 0 ;i < list.size() ; i ++){
	    	inputs[i] = list.get(i);
	    	//System.out.println("inputs: " + inputs[i]);
	    }     

	    TSTNew<Integer> st = new TSTNew<Integer>();  
	    for (int n = 0; n < inputs.length; n ++) {	    
	     	webdictionary h = new webdictionary();
	    	File f = new File(pathName);
	    	File[] allFile = f.listFiles();
	    	   for (int i = 0; i < allFile.length; i++){   
	    	      int frequency = h.returnFrequencyOfAWord(pathName + allFile[i].getName(),inputs[n]);
	    	   if (frequency > 0){
		          int[][] arr = new int[1][2];
		          arr[0][0]=i;
		          arr[0][1]=frequency;
		          st.put(inputs[n],arr);
	    	    }
	    	    }
	    }   
   	
    	List<Integer> fileMergeList = new ArrayList<>();
        HashMap<Integer,Float> score = new HashMap<Integer,Float>();  
        HashMap<Integer,Integer> fileOccurrenceAll=new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> contentPosition = new HashMap<Integer,Integer>();  
        
        BTree<Integer, String> webURL = new BTree<Integer, String>();
        File f = new File(pathName);
    	File[] allFile = f.listFiles();
    	for (int i = 0; i < allFile.length; i++){   
    	String string = allFile[i].getName(); 
    	webURL.put(i, string.substring(0, string.length() - 4));
    	}
                
       if(inputs.length > 1){  
	        if(inputs.length > 5){
		        inputs = Arrays.copyOfRange(inputs, 0, 5);
	        }
	        List<String> matchs = new ArrayList<>();
            for (int i = inputs.length; i > 1; i--) {
               int j = 0; 
               int a = i; 
               int b = a; 
               while (a <= inputs.length) {	
                    String match ="";
                    while (j < a) {	
                    	if(match == "")
                    		 match = inputs[j];
                    	else
                    		 match = match + " " + inputs[j];
                    	j++;
           
                   }    
                   j = j - b + 1;
                   a++;
                   matchs.add(match);
                   //System.out.println("match: " + match);
             }
                   
             for (int m = 0; m < matchs.size(); m++){  
           	    	for (int fileName = 0; fileName < allFile.length; fileName++){  
           	    		String txt = new String(Files.readAllBytes(Paths.get(pathName + allFile[fileName].getName())));
           	    		int[] arr = StringMatch(matchs.get(m),txt);
           	    		if(arr != null){
           	    	        float sum = arr[1];
           	    		    sum = (float) (sum * Math.pow(i, 3) * 100000);

           	    	        if(score.containsKey(fileName))
      	            	          score.put(fileName,score.get(fileName)+sum);
      	                    else
             	    	          score.put(fileName, sum);
         	                int position = arr[0];
        	                if(!contentPosition.containsKey(fileName))
        	            	     contentPosition.put(fileName,position);
           	    		}
                   a++;                 
             }
             }
         }
   

         
        for (int n = 0; n < inputs.length; n ++) {
             String input = inputs[n];           
              
             if(st.get(input) == null){
            	 int[][] arr = st.get(input); 
                 for (int i = 0; i < arr.length; i++) {
   	                  System.out.println(arr[i][0]+"     "+arr[i][1]);    	             
   	                  int fileName = arr[i][0];                
   	                  float occurrence = arr[i][1];
   	                  occurrence = occurrence/1000;
   	                  fileMergeList.add(arr[i][0]);
   	              if(score.containsKey(fileName))
   	            	  score.put(fileName,score.get(fileName)+occurrence);
   	              else
   	                  score.put(fileName, occurrence);
             	}
                 
             }
         }

        
      int[] fileMerge = new int[fileMergeList.size()];     
      for(int i = 0; i < fileMergeList.size(); i++)
    	  fileMerge[i] = fileMergeList.get(i);
      

	
	for(int a : fileMerge)
	{
		if(fileOccurrenceAll.containsKey(a))
		{
			int val = fileOccurrenceAll.get(a);
			val++;
			fileOccurrenceAll.put(a, val);
		}
		else
		{
			fileOccurrenceAll.put(a, 1);
		}
	}

	
	for (HashMap.Entry<Integer, Integer> entry : fileOccurrenceAll.entrySet()) {
        System.out.println(entry.getKey()+":"+entry.getValue());        
        int fileName = entry.getKey();
        String txt = new String(Files.readAllBytes(Paths.get(pathName + webURL.get(fileName) + ".txt")));
        for (int j = 0; j < inputs.length; j++) {
    	    int position = getPosition(inputs[j],txt);
    	    if(position > -1){
    	    	if(!contentPosition.containsKey(fileName))
    	    		contentPosition.put(fileName,position);
    		    break;
    	    }
    	}
        float sum = entry.getValue();
        sum = sum *1000;
        if(score.containsKey(fileName))
      	  score.put(fileName,score.get(fileName)+sum);
        else
          score.put(fileName, sum);
	}
       

	List<HashMap.Entry<Integer,Float>> fileOrder = new ArrayList<HashMap.Entry<Integer,Float>>(score.entrySet());
    Collections.sort(fileOrder ,new Comparator<HashMap.Entry<Integer,Float>>() {
        public int compare(Entry<Integer, Float> o1,
                Entry<Integer, Float> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
        
    });

    System.out.println("\n\n");
    if(fileOrder.size() > 10){
    Scanner sc = new Scanner(System.in); 
    for (int i = 0; i < fileOrder.size(); i += 10) {
    	 int n = 0;
    	 for (int j = i; j < i+10; j++) {
    		 if(j < fileOrder.size()){
                  HashMap.Entry<Integer,Float> mapping = fileOrder.get(j);
   
                  if(contentPosition.get(mapping.getKey()) != null){
                	  String fileName = webURL.get(mapping.getKey());
                	  System.out.println("URL: " + fileName);
                	  System.out.println("content: " + getContent(contentPosition.get(mapping.getKey()),fileName));
                	  System.out.println("\n\n");
                	  n = j;
        		    }                   		
    	         }
    	         else   	
      		           break;
           }
           if(n == fileOrder.size()-1)
           	   break;
           else
               sc.nextLine();

     }
     sc.close();
  }
    
    else{
   	 for (int i = 0; i < fileOrder.size(); i++) {     
   		HashMap.Entry<Integer,Float> mapping = fileOrder.get(i);
        
        if(contentPosition.get(mapping.getKey()) != null){
        	String fileName = webURL.get(mapping.getKey());
        	System.out.println("URL: " + fileName);
      		System.out.println("content: " + getContent(contentPosition.get(mapping.getKey()),fileName));
      		System.out.println("\n\n");
      		
        }    
   	 }
    
   }
   }
       
       //several words
   
  
    else{
                	
    	    List<Integer> fileOrder = new ArrayList<Integer>();
            if(st.get(inputs[0]) == null)
            	 System.out.println("No this key = " + inputs[0]);          
            else {
           	 int[][] arr = st.get(inputs[0]); 
            	Arrays.sort(arr, (o1, o2) -> o2[1] - o1[1]);
                for (int i = 0; i < arr.length; i++) {
  	                fileOrder.add(arr[i][0]);
                }
            }
           	                	
          
            for (int i = 0; i < fileOrder.size(); i++) {
            	int fileName = fileOrder.get(i);
            	String txt = new String(Files.readAllBytes(Paths.get(pathName + webURL.get(fileName) + ".txt")));
            	int position = getPosition(inputs[0],txt);       	    
            	    if(position > -1){
            	    	if(!contentPosition.containsKey(fileName))
            	    		contentPosition.put(fileName,position);
            	    }        	
            }
            
            
  
              System.out.println("\n\n");
              if(fileOrder.size() > 10){
  	          Scanner sc = new Scanner(System.in); 
  	          for (int i = 0; i < fileOrder.size(); i += 10) {
  	       	  int n = 0;
  	               for (int j = i; j < i + 10; j++) {	   
  	            	 
  	                	 if(j < fileOrder.size()){ 	                	
  	                		int order = fileOrder.get(j);    
  	                		if(contentPosition.get(order) != null){
  	                		String fileName = webURL.get(order);
  	                		System.out.println("URL: " + fileName);
  	                		System.out.println("content: " + getContent(contentPosition.get(order),fileName));
  	                		System.out.println("\n\n");
  	                		n = j;
  	                		
  	            		    }                   		
  	        	         }
  	        	         else   	
  	          		           break;
  	               }
	                 if(n == fileOrder.size()-1)
	            	 break;
	                 else
	                	 sc.nextLine();
  	          }
  	         sc.close();
            }
            
            else{
            	 for (int i = 0; i < fileOrder.size(); i++) {     
    	              
            	    	
               		int order = fileOrder.get(i);          		
               		if(contentPosition.get(order) != null){
                			String fileName = webURL.get(order);
  	                		System.out.println("URL: " + fileName);
  	                		System.out.println("content: " + getContent(contentPosition.get(order),fileName));
  	                		System.out.println("\n\n");  	         
            	 }
            }
            }
              sacn.close();
    }
    }
    //single word
    
}
       


