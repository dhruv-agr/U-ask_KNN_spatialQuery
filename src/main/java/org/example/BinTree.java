package org.example;

import java.util.ArrayList;
import java.util.List;

public class BinTree {


     static class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
     }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        Solution s = new Solution();
        List<List<Integer>> ans =s.levelOrder(root);
        System.out.println(ans);

    }

    static class Solution {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<List<Integer>>();
            if(root==null){
                return res;
            }

            ArrayList<TreeNode> q = new ArrayList<TreeNode>();
            q.add(root);
            while (!q.isEmpty()){
                ArrayList<Integer> same_level = new ArrayList<Integer>();
                int qsize = q.size();
                for(int i=0;i<qsize;i++){
                    TreeNode node = q.remove(0);
                    same_level.add(node.val);

                    if(node.left!=null){
                        q.add(node.left);
                    }
                    if(node.right!=null){
                        q.add(node.right);
                    }

                }
                res.add(same_level);
            }
            return res;

        }
    }
}
