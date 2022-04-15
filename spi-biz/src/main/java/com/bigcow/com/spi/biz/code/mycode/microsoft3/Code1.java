package com.bigcow.com.spi.biz.code.mycode.microsoft3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code1 {

    public List<Integer> findMaxValues(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        List<Integer> res = new ArrayList<>();
        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                TreeNode curNode = nodeQueue.poll();
                max = Math.max(max, curNode.val);
                if (curNode.left != null) {
                    nodeQueue.add(curNode.left);
                }
                if (curNode.right != null) {
                    nodeQueue.add(curNode.right);
                }
            }
            res.add(max);
        }
        return res;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
