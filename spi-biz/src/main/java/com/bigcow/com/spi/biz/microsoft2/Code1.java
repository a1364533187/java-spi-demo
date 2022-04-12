package com.bigcow.com.spi.biz.microsoft2;

public class Code1 {

    public static void main(String[] args) {
        int[] nums = {1,3,7,2,3,6};
        Node head = buildLinkedList(nums);
        printLinkedList(head);
        System.out.println();
        Node newHead = quickSort(head);
        printLinkedList(newHead);
    }

    // 链表的一个快速排序
    public static void printLinkedList(Node head) {
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.val + "---");
            cur = cur.next;
        }
    }

    public static Node buildLinkedList(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        Node head = new Node(nums[0], null);
        Node cur = head;
        for (int i = 1; i < nums.length; i++) {
            cur.next = new Node(nums[i], null);
            cur = cur.next;
        }
        return head;
    }


    // 链表的一个快速排序
    public static Node quickSort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node[] partitionNodes = partition(head);
        Node left = quickSort(partitionNodes[0]);
        Node right = quickSort(partitionNodes[2]);
        // merge
        Node dummy = new Node(-1, null);
        Node cur = dummy;
        Node tailLeft = findTailNode(left);
        if (tailLeft == null) {
            cur.next = partitionNodes[1];
            partitionNodes[1].next = right;
        } else {
            cur.next = left;
            tailLeft.next = partitionNodes[1];
            partitionNodes[1].next = right;
        }
        return dummy.next;
    }

    private static Node findTailNode(Node head)  {
        if (head == null) {
            return null;
        }
        Node cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * node[0] 前半部分小于pivot值的链表的头节点
     * node[1] pivot
     * node[2] 大于pivot的链表的头节点
     * @param head
     * @return
     */
    private static Node[] partition(Node head) {
        Node dummyLeft = new Node(-1, null);
        Node curLeft = dummyLeft;
        Node dummyRight = new Node(-1, null);
        Node curRight = dummyRight;
        Node dummyMid = new Node(-1, null);
        Node curMid = dummyMid;
        Node cur = head;
        Node pivot = head;
        while (cur != null) {
            if (cur.val < pivot.val) {
                curLeft.next = cur;
                cur = cur.next;
                curLeft = curLeft.next;
                curLeft.next = null;
            } else if (cur.val == pivot.val) {
                curMid.next = cur;
                cur = cur.next;
                curMid = curMid.next;
                curMid.next = null;
            } else {
                curRight.next = cur;
                cur = cur.next;
                curRight = curRight.next;
                curRight.next= null;
            }
        }
        pivot = dummyMid.next;
        Node pivotNext = pivot.next;
        pivot.next = null;
        curLeft.next = pivotNext;
        return new Node[] {dummyLeft.next, pivot, dummyRight.next};
    }
}

class Node {
    int val;
    Node next;

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
