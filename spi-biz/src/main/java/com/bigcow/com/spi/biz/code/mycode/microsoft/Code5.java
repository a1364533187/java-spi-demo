package com.bigcow.com.spi.biz.code.mycode.microsoft;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Code5 {

    Map<String, String> memorizedMap;



    public Code5() {
        this.memorizedMap = new ConcurrentHashMap<>();
    }

    public boolean save(String k, String v) {
        memorizedMap.put(k, v);
        return true;
    }

    public String query(String k) {
        return memorizedMap.get(k);
    }

    /**
     * k 可能包含模糊匹配： *， 匹配0个或者多个字符
     * @param k
     * @return
     */
    public List<String> queryByFuzzy(String k) {

        return null;
    }
    
}
