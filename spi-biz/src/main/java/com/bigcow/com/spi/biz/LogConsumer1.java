//package com.bigcow.com.spi.biz;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.PriorityQueue;
//
//public class LogConsumer1 {
//
//    public static void main(String[] args) {
//        // 假设windowSize = 10, 对于1min 内数据假设1000条，这里的windowSize 可以 大于 1000， 确保数据局部有序
//        LogConsumer logConsumer = new LogConsumer(5);
//        // 排序 [1, 2, 4, 5, 7, 12, 15, 16, 19, 20, 23]
//        int[] nums1 = { 4, 7, 5, 2, 1, 19, 16, 15, 12, 23, 20 };
//        // 排序 [1, 2, 3, 5, 7, 10, 12, 17, 19, 20, 21]
//        int[] nums2 = { 3, 7, 1, 2, 19, 10, 5, 12, 17, 20, 21 };
//        List<KafkaLog> kafkaLogs = new ArrayList<>();
//        for (int i = 0; i < nums1.length; i++) {
//            kafkaLogs.add(
//                    new KafkaLog(nums1[i], "host1", 3, "message1", System.currentTimeMillis()));
//        }
//        for (int i = 0; i < nums2.length; i++) {
//            kafkaLogs.add(
//                    new KafkaLog(nums2[i], "host2", 4, "message2", System.currentTimeMillis()));
//        }
//        // 测试下缺失的数据
//        for (int i = 0; i < kafkaLogs.size(); i++) {
//            logConsumer.logger(kafkaLogs.get(i));
//        }
//        // {host2:4=[4, 6, 8, 9], host1:3=[3, 6, 8, 9, 10, 11]}
//        System.out.println(logConsumer.missLogs);
//    }
//
//}
//
//class LogConsumer {
//
//    private int windowSize;
//
//    public LogConsumer(int windowSize) {
//        this.windowSize = windowSize;
//    }
//
//    // key： hostname + pid
//    // int: 缺失的序列号ID
//    Map<String, List<Integer>> missLogs = new HashMap<>();
//    Map<String, PriorityQueue<KafkaLog>> queueMap = new HashMap<>();
//    Map<String, Integer> minValueMap = new HashMap<>();
//
//    public void logger(KafkaLog kafkaLog) {
//        String key = kafkaLog.getHostName() + ":" + kafkaLog.pid;
//        PriorityQueue<KafkaLog> curQueue = queueMap.getOrDefault(key,
//                new PriorityQueue<>((o1, o2) -> o1.sequence - o2.sequence));
//        int curMin = minValueMap.getOrDefault(key, 0);
//        curQueue.add(kafkaLog);
//        queueMap.put(key, curQueue);
//        if (curQueue.size() > windowSize) { //考虑window内的乱序
//            KafkaLog queueLog = curQueue.poll();
//            if (queueLog.sequence > curMin) {
//                List<Integer> curList = new ArrayList<>();
//                for (int i = curMin + 1; i < queueLog.sequence; i++) {
//                    curList.add(i);
//                }
//                List<Integer> missLogList = missLogs.getOrDefault(key, new ArrayList<>());
//                missLogList.addAll(curList);
//                missLogs.put(key, missLogList);
//                minValueMap.put(key, queueLog.sequence);
//            }
//        }
//    }
//}
//
//class KafkaLog {
//
//    int sequence;
//    String hostName;
//    int pid;
//    String message;
//    long timestamp;
//
//    public KafkaLog(int sequence, String hostName, int pid, String message, long timestamp) {
//        this.sequence = sequence;
//        this.hostName = hostName;
//        this.pid = pid;
//        this.message = message;
//        this.timestamp = timestamp;
//    }
//
//    public int getSequence() {
//        return sequence;
//    }
//
//    public void setSequence(int sequence) {
//        this.sequence = sequence;
//    }
//
//    public String getHostName() {
//        return hostName;
//    }
//
//    public void setHostName(String hostName) {
//        this.hostName = hostName;
//    }
//
//    public int getPid() {
//        return pid;
//    }
//
//    public void setPid(int pid) {
//        this.pid = pid;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public long getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(long timestamp) {
//        this.timestamp = timestamp;
//    }
//}
