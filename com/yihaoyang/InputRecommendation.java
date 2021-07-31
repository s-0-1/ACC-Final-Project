package com.yihaoyang;

import com.yihaoyang.trie.TST;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

public class InputRecommendation
{
    private static final TST<Integer> recommendWordsList = new TST<>();

    public void addToTheList(String text)
    {
        String[] keys = processText(text);
        for (String key : keys)
        {
            recommendWordsList.put(key, key.hashCode());
        }
    }

    public void removeFromTheList(String key)
    {
        recommendWordsList.put(key,key.hashCode());
    }

    public LinkedList<String> getPrefix(String prefix)
    {
        Iterable words = recommendWordsList.prefixMatch(prefix);
        Iterator iterator = words.iterator();
        LinkedList<String> list = new LinkedList<>();
        int cont = 0;
        while (iterator.hasNext() && cont <10)
        {
            cont++;
            list.add((String) iterator.next());
        }
        return list;
    }

    private String[] processText(String text)
    {
        text = text.toLowerCase(Locale.ROOT).replaceAll("\\p{Punct}", " ");
        text = text.replaceAll("\\u00A0+", " ").replaceAll("\\s+"," ");
        String[] words = text.split("\\s");
        return words;
    }

    public boolean getParameter(String cmd)
    {
        return cmd.contains("-r");
    }
}
