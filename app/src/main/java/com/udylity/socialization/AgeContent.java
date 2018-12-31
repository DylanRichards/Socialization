package com.udylity.socialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AgeContent {

    public static final List<AgeSection> ITEMS = new ArrayList<AgeSection>();

    public static final Map<String, AgeSection> ITEM_MAP = new HashMap<String, AgeSection>();

    private static String[] ageRange, ageDetails;

    public AgeContent(String[] stringRange, String[] stringDetails){
        //Remove if not empty
        while(!ITEMS.isEmpty()){
            ITEMS.remove(0);
        }

        ageRange = stringRange;
        ageDetails = stringDetails;
        for (int i = 0; i < stringRange.length; i++) {
            addItem(createAgeSectionItem(i));
        }
    }


    private static void addItem(AgeSection item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static AgeSection createAgeSectionItem(int position) {
        return new AgeSection(String.valueOf(position), ageRange[position], makeDetails(position));
    }

    private static String makeDetails(int position) {
        if(ageDetails != null){
            return ageDetails[position];
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class AgeSection {
        public final String id;
        public final String title;
        public final String details;

        public AgeSection(String id, String content, String details) {
            this.id = id;
            this.title = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
