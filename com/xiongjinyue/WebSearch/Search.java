package com.xiongjinyue.WebSearch;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Search {
	private String filename;
    private int line;
    private String content;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }
    
    public static void Search(String strSearch, String strPath) {
   

            File searchDir = new File(strPath);
            List<Search> list = new ArrayList<Search>();
            try {
                Collection<File> files = FileUtils.listFiles(searchDir, null, true);
                List<String> lines = null;
                for (File file : files) {
                    try {
                        lines = FileUtils.readLines(file, "UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < lines.size(); i++) {
                        if (lines.get(i).indexOf(strSearch) != -1) {
                            Search s = new Search();
                            s.setFilename(file.getPath());
                            s.setLine(i + 1);
                            s.setContent(lines.get(i));
                            list.add(s);
                        }
                    }
                }
                StringBuffer sb = new StringBuffer();
                if(list.size() == 0) {
                    System.out.println("Did not find the string");
                    return ;
                }
                for (Search s : list) {
                    sb.append("path��" + s.getFilename() + "\nLine��" + s.getLine() + " \nContent��" + s.getContent()).append(IOUtils.LINE_SEPARATOR);
                    sb.append("---------------------------------------------------------------------\n");
                }
                System.out.println(sb.toString());
            } catch (Exception e) {
                System.out.println("Did not find the directory");
            }
        }
    	
    
    public static void main(String[] args) {
    	
    	
    	String strSearch = "Jenkins";
    	String strPath = "E:\\EclipseProjects-Course\\WebSearchEngine\\TextFiles";
    	Search(strSearch,strPath);
    }

}
