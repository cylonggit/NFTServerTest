package constant;


import java.util.HashMap;

//排行榜用 前缀
public class Constants {
    public final static HashMap<CATEGORY, HashMap<ACTION, HashMap<TIME, String>>> RANKMAP = new HashMap<CATEGORY, HashMap<ACTION, HashMap<TIME, String>>>() {{
        for (CATEGORY category : CATEGORY.values()) {
            HashMap tmp1 = new HashMap<ACTION, HashMap<TIME, String>>();
            for (ACTION action : ACTION.values()) {
                HashMap tmp2 = new HashMap<TIME, String>();
                for (TIME time : TIME.values()) {
                    tmp2.put(time, "{" + category + "_" + action + "_rank}:" + time);
                }
                tmp1.put(action, tmp2);
            }
            put(category, tmp1);
        }
    }};


    public enum ACTION {
        THUMBUP,
        VISIT,
        FAVORITE
    }


    public enum TIME {
        HOUR,
        DAY,
        WEEK,
        MONTH
    }


    public enum CATEGORY {
        IMAGE,
        VIDEO,
        AUDIO
    }


};