package com.bigcow.cn.code.排序链表;

import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

public class SortLinkedList {

    @Test
    public void testSortLinkedList() {
        LinkedNode<Integer> head = buildLinkedList(Lists.newArrayList(4, 2, 1, 3));
        LinkedNode<Integer> cur = SortLinkedList(head);
        printLinkedList(cur);
    }

    @Test
    public void testSortLinkedList1() {
        LinkedNode<Integer> head = buildLinkedList(Lists.newArrayList(-1, 5, 3, 4, 0));
        LinkedNode<Integer> cur = SortLinkedList(head);
        printLinkedList(cur);
    }

    @Test
    public void insertSortLinkedList1() {
        LinkedNode<Integer> p1 = new LinkedNode<>(null, 3);
        LinkedNode<Integer> p2Head = buildLinkedList(Lists.newArrayList(1, 2, 3, 4));
        LinkedNode<Integer> cur = insertSortLinkedList(p1, p2Head);
        printLinkedList(cur);
    }

    @Test
    public void insertSortLinkedList2() {
        LinkedNode<Integer> p1 = new LinkedNode<>(null, 3);
        LinkedNode<Integer> p2Head = buildLinkedList(Lists.newArrayList(1));
        LinkedNode<Integer> cur = insertSortLinkedList(p1, p2Head);
        printLinkedList(cur);
    }

    @Test
    public void insertSortLinkedList3() {
        LinkedNode<Integer> p1 = new LinkedNode<>(null, 4);
        LinkedNode<Integer> p2Head = buildLinkedList(Lists.newArrayList(5));
        LinkedNode<Integer> cur = insertSortLinkedList(p1, p2Head);
        printLinkedList(cur);
    }

    @Test
    public void insertSortLinkedList4() {
        LinkedNode<Integer> p1 = new LinkedNode<>(null, 5);
        LinkedNode<Integer> p2Head = buildLinkedList(Lists.newArrayList(1, 2, 3));
        LinkedNode<Integer> cur = insertSortLinkedList(p1, p2Head);
        printLinkedList(cur);
    }

    @Test
    public void insertSortLinkedList5() {
        LinkedNode<Integer> p1 = new LinkedNode<>(null, 5);
        LinkedNode<Integer> p2Head = buildLinkedList(Lists.newArrayList(6, 9, 10));
        LinkedNode<Integer> cur = insertSortLinkedList(p1, p2Head);
        printLinkedList(cur);
    }

    public LinkedNode<Integer> SortLinkedList(LinkedNode<Integer> head) {
        if (null == head || null == head.next) {
            return head;
        }
        LinkedNode<Integer> p1 = head;
        LinkedNode<Integer> p2 = head.next;
        p1.next = null; //切断链表
        LinkedNode<Integer> p2Head = SortLinkedList(p2);
        //只要把有序的p2Head 插入p1 即可
        return insertSortLinkedList(p1, p2Head);
    }

    // 将p1 插入到有序的p2Head list中, p2Head 不会为null
    public LinkedNode<Integer> insertSortLinkedList(LinkedNode<Integer> p1,
            LinkedNode<Integer> p2Head) {
        if (null == p2Head.next) {
            if (p1.value > p2Head.value) {
                p2Head.next = p1;
                return p2Head;
            } else {
                p1.next = p2Head;
                return p1;
            }
        }
        if (p2Head.value > p1.value) {
            p1.next = p2Head;
            return p1;
        }
        LinkedNode<Integer> curPrev = p2Head;
        LinkedNode<Integer> curNext = p2Head.next;
        while (null != curNext && curNext.value < p1.value) {
            curPrev = curNext;
            curNext = curNext.next;
        }
        // 退出 1、curNext = null
        if (null == curNext) {
            curPrev.next = p1;
        } else {
            curPrev.next = p1;
            p1.next = curNext;
        }
        return p2Head;
    }

    @Test
    public void testPrintLinkedList() {
        List<Integer> list = Lists.newArrayList(4, 2, 1, 3);
        LinkedNode<Integer> head = buildLinkedList(list);
        printLinkedList(head);
    }

    public <T> LinkedNode buildLinkedList(List<T> lists) {
        if (CollectionUtils.isEmpty(lists)) {
            return null;
        }
        LinkedNode<T> head = new LinkedNode<>(null, lists.get(0));
        LinkedNode<T> cur = head;
        for (int i = 1; i < lists.size(); i++) {
            LinkedNode<T> next = new LinkedNode<>(null, lists.get(i));
            cur.next = next;
            cur = next;
        }
        return head;
    }

    public <T> void printLinkedList(LinkedNode<T> head) {
        if (head == null) {
            return;
        }
        LinkedNode<T> cur = head;
        while (null != cur) {
            System.out.print(cur.getValue() + "--->");
            cur = cur.next;
        }
    }

    class LinkedNode<T> {

        LinkedNode next;
        T value;

        public LinkedNode(LinkedNode next, T value) {
            this.next = next;
            this.value = value;
        }

        public LinkedNode getNext() {
            return next;
        }

        public void setNext(LinkedNode next) {
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

}
