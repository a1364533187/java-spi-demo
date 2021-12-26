package com.bigcow.cn.code.排序链表;

import java.util.List;

import org.apache.hadoop.shaded.org.apache.curator.shaded.com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

/**
 * Create by suzhiwu on 2021/12/26
 */
public class SortLinkedList1 {

    public ListNode sortList(ListNode head) {
        // 用快慢指针找到链表中间位置
        // 将链表切开
        // 切开后归并排序2个子链表
        // 归并排序子链表
        if (null == head || null == head.next) {
            return head;
        }
        ListNode mid = findMiddleNode(head);
        //将链表切断
        ListNode rightP = mid.next;
        mid.next = null;

        ListNode left = sortList(head);
        ListNode right = sortList(rightP);

        return merge(left, right);
    }

    /**
     * 找到链表中间节点middle node
     * @param head
     * @return
     */
    public ListNode findMiddleNode(ListNode head) {
        ListNode pSlow = head;
        ListNode pFast = head;
        while (null != pFast.next && null != pFast.next.next) {
            pSlow = pSlow.next;
            pFast = pFast.next.next;
        }
        return pSlow;
    }

    // 合并有序链表
    public ListNode merge(ListNode left, ListNode right) {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        while (null != left && null != right) {
            if (left.val < right.val) {
                cur.next = left;
                cur = cur.next;
                left = left.next;

            } else {
                cur.next = right;
                cur = cur.next;
                right = right.next;
            }
        }

        if (left == null) {
            cur.next = right;
        } else {
            cur.next = left;
        }
        return head.next;
    }

    @Test
    public void testSortList() {
        ListNode head = buildLinkedList(Lists.newArrayList(1, 7, 4, 3, 5));
        ListNode node = sortList(head);
        printLinkedList(node);
    }

    @Test
    public void testFindMidNode() {
        ListNode head = buildLinkedList(Lists.newArrayList(1, 2, 3, 4, 5));
        ListNode mid = findMiddleNode(head);
        System.out.println(mid.val);
    }

    @Test
    public void testMergeTwoSortList() {
        ListNode left = buildLinkedList(Lists.newArrayList(1, 3, 5));
        ListNode right = buildLinkedList(Lists.newArrayList(2, 4));
        ListNode head = merge(left, right);
        printLinkedList(head);
    }

    @Test
    public void testBuildLinkedList() {
        List<Integer> lists = Lists.newArrayList(1, 3, 2, 4);
        ListNode head = buildLinkedList(lists);
        printLinkedList(head);
    }

    public ListNode buildLinkedList(List<Integer> lists) {
        if (CollectionUtils.isEmpty(lists)) {
            return null;
        }
        ListNode head = new ListNode(lists.get(0));
        ListNode cur = head;
        for (int i = 1; i < lists.size(); i++) {
            ListNode next = new ListNode(lists.get(i));
            cur.next = next;
            cur = next;
        }
        return head;
    }

    public void printLinkedList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode cur = head;
        while (null != cur) {
            System.out.print(cur.val + "--->");
            cur = cur.next;
        }
    }

}
