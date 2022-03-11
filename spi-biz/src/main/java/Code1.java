import java.util.ArrayList;
import java.util.List;

public class Code1 {

    public static void main(String[] args) {
        int[] A = {1,2,2,3,7,8};
        int[] B = {2,4};
        System.out.println(interaction(A, B));
    }

    // A， B 单调递增， 找A有的， B 没有
    // A 1,2,2,3
    // B 2,4

    private static List<Integer> interaction(int[] A, int[] B) {
        List<Integer> res = new ArrayList<>();
        int startA = 0;
        int startB = 0;
        while (startA < A.length && startB < B.length) {
            if (A[startA] < B[startB]) {
                res.add(A[startA]);
                startA++;
            } else if (A[startA] == B[startB]) {
                startA++;
            } else { //  A[startA] > B[startB]
                startB++;
            }
        }
        // 如果退出是因为 startB < B.length
        // 把剩余的A 的元素全部加入
        for (int i = startA; i < A.length; i++) {
            res.add(A[i]);
        }
        return res;
    }

    //    private boolean findValue(TreeNode root, int val) {
    //        traverse(root, val);
    //        return isFind;
    //    }


    private boolean traverse(TreeNode root, int val) {
        if (root == null) {
            return false;
        }
        if (root.val == val) {
            return true;
        }

        boolean left = traverse(root.left, val);
        boolean right = traverse(root.right, val);
        return left || right;
    }

    class TreeNode {

        private int val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public int getVal() {
            return val;
        }
    }
}
