package com.etoak.nio;

import org.apache.commons.lang.CharSet;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * Created by zhangcheng on 2016/9/28.
 */
public class AvailableCharSets {
    public static void main(String[] args) {
        SortedMap<String,Charset> charsets = Charset.availableCharsets();
        Iterator<String> it = charsets.keySet().iterator();
        while (it.hasNext()){
            String csName = it.next();
            System.out.println();
            System.out.print(csName);
            Iterator aliases = charsets.get(csName).aliases().iterator();
            if (aliases.hasNext()) {
                System.out.print(": ");
                while (aliases.hasNext()) {
                    System.out.print(aliases.next());
                    if (aliases.hasNext()) {
                        System.out.print(", ");
                    }
                }
            }
        }
        System.out.println("\n");
    }
}
