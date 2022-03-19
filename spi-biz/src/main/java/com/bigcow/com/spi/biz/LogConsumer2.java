package com.bigcow.com.spi.biz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class LogConsumer2 {

    public static void main(String[] args) {
        // 假设windowSize = 10, 对于1min 内数据假设1000条，这里的windowSize 可以 大于 1000， 确保数据局部有序
        KafkaLogConsumer logConsumer = new KafkaLogConsumer();
        // 排序 [1, 2, 4, 5, 7, 12, 15, 16, 19, 20, 23]
        int[] nums1 = { 4, 7, 5, 2, 1, 19, 16, 15, 12, 23, 20 };
        // 排序 [1, 2, 3, 5, 7, 10, 12, 17, 19, 20, 21]
        int[] nums2 = { 3, 7, 1, 2, 19, 10, 5, 12, 17, 20, 21 };
        List<KafkaLog> kafkaLogs = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            kafkaLogs.add(
                    new KafkaLog(nums1[i], "host1", 3, "message1", System.currentTimeMillis()));
        }
        for (int i = 0; i < nums2.length; i++) {
            kafkaLogs.add(
                    new KafkaLog(nums2[i], "host2", 4, "message2", System.currentTimeMillis()));
        }
        // 测试下缺失的数据
        for (int i = 0; i < kafkaLogs.size(); i++) {
            logConsumer.logger(kafkaLogs.get(i));
        }
        // {host1:3=[3, 6, 8, 9, 10, 11, 13, 14, 17, 18, 21, 22], host2:4=[4, 6, 8, 9, 11, 13, 14, 15, 16, 18]}
        System.out.println(logConsumer.missTreeSetMap);
    }
}

class KafkaLogConsumer {

    public KafkaLogConsumer() {
    }

    // key： hostname + pid
    // int: 缺失的序列号ID
    Map<String, TreeSet<Integer>> missTreeSetMap = new TreeMap<>();
    Map<String, Integer> maxValueMap = new HashMap<>();

    public void logger(KafkaLog kafkaLog) {
        String key = kafkaLog.getHostName() + ":" + kafkaLog.pid;
        TreeSet<Integer> curMissTreeSet = missTreeSetMap.getOrDefault(key,
                new TreeSet<>(new Comparator<Integer>() {

                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o1 - o2;
                    }
                }));
        int curMax = maxValueMap.getOrDefault(key, 1);
        if (kafkaLog.sequence < curMax) {
            curMissTreeSet.remove(kafkaLog.sequence);
        } else {
            for (int i = curMax + 1; i < kafkaLog.sequence; i++) {
                curMissTreeSet.add(i);
            }
            missTreeSetMap.put(key, curMissTreeSet);
        }
        curMax = Math.max(curMax, kafkaLog.sequence);
        maxValueMap.put(key, curMax);
    }
}

class KafkaLog {

    int sequence;
    String hostName;
    int pid;
    String message;
    long timestamp;

    public KafkaLog(int sequence, String hostName, int pid, String message, long timestamp) {
        this.sequence = sequence;
        this.hostName = hostName;
        this.pid = pid;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
