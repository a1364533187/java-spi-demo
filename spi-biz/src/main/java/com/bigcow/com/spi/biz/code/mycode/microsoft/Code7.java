package com.bigcow.com.spi.biz.code.mycode.microsoft;

import java.util.Stack;

public class Code7 {

    // 设计一个最小栈

    /**
     * put 4,1,2,3  pop, pop
     * 栈1： 4, 1，2，3 -> 4,2,1 -> 4, 1
     * 栈2： 4, 1，1，1 -> 4,1,1 -> 4, 1
     * 维护2个栈
     * 第一个栈支持put 和 pop 元素
     * 另外一个栈 维护当前最小值的一个栈
     *
     * @param k
     * @return
     */
    Stack<Integer> normalStack = new Stack<>();

    Stack<Integer> minStack = new Stack<>();

    public boolean put(int k) {
        normalStack.push(k);
        if (!minStack.isEmpty()) {
            int minValue = Math.min(minStack.peek(), k);
            minStack.push(minValue);
        } else {
            minStack.push(k);
        }
        return true;
    }

    public int pop() {
        if (normalStack.isEmpty()) {
            throw new UnsupportedOperationException("Stack is empty!!!");
        }
        int res = normalStack.pop();
        minStack.pop();
        return res;
    }

    public int getMin() {
        if (minStack.isEmpty()) {
            throw new UnsupportedOperationException("Stack is empty!!!");
        }
        return minStack.peek();
    }

}
