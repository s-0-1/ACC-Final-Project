package com.yihaoyang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        File texts = new File("C:\\Application\\Codes\\Final\\src\\com\\xiongjinyue\\TextFiles");
        String[] filenames = texts.list();
        InputRecommendation recommendList = new InputRecommendation();
        if (filenames != null)
        {
            for (String fileName : filenames)
            {
                try
                {
                    BufferedReader reader = new BufferedReader(new FileReader("C:\\Application\\Codes\\Final\\src\\com" +
                            "\\xiongjinyue\\TextFiles\\" + fileName));
                    String text;
                    while ((text = reader.readLine()) != null)
                    {
                        recommendList.addToTheList(text);
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        }

        Scanner s = new Scanner(System.in);
        System.out.println("Please enter the key word(-r to get input recommendation):");
        while(true)
        {
            String cmd = s.nextLine();
            if (recommendList.getParameter(cmd))
            {
                String searchKeyWord = cmd.substring(0, cmd.indexOf("-r")).replaceAll("\\p{Blank}", "");
                System.out.println(searchKeyWord);
                System.out.println(recommendList.getPrefix(searchKeyWord));
            }
        }
    }
}