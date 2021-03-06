package back_tracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/combination-sum-ii/description/
 * 输入：候选int[] , int target
 * 输出: 所有整数list,使得各个元素来自候选数组且元素和等于target
 *
 * 候选数组中的每个元素只可以被使用一次，候选元素不一定唯一
 * 结果集中不能有重复的组合
 *
 * 使用 used boolean[] 来解决候选元素本身重复的问题。当前重复元素可用的前提是（排序后的）数组中前一个元素已经被使用；
 */
public class CombinationSumII {


    public static void main(String args[]){
        CombinationSumII cs = new CombinationSumII();
        Solution solution = new Solution();
        int [] candidates = {10, 1, 2, 7, 6, 1, 5};  // 1 1 2 5 6 7 10
        int target = 8;
        System.out.println(cs.combinationSum(candidates,target));
        System.out.println(solution.combinationSum(candidates,target));
    }


    private List<List<Integer>> result;
    private boolean[] used;
    private List<Integer> tempList;
    private int [] nums;
    private int target;
    private int curSum;


    /**
     * 使用 fromIndex来避免 1 2 5, 2 1 5, 5 1 2这种问题
     * 使用 used[] 来避免 1 1 6, 1 2 5, 1 7, 1 2 5, 1 7这种问题
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        this.result = new ArrayList<>();
        this.tempList = new ArrayList<>();
        this.nums = candidates;
        this.target = target;
        this.curSum = 0;
        this.used = new boolean[candidates.length];
        Arrays.sort(candidates);
        backTrack( 0);
        return  result;
    }

    public void backTrack(int fromIndex){

        if(curSum == target){
            result.add(new ArrayList<>(tempList));
            return;
        }

        for(int i = fromIndex; i < nums.length ; i++){
            if(used[i]) continue;
            if(i>0 && nums[i] == nums[i-1] && !used[i-1]) continue;
            if(curSum + nums[i] <= target){
                tempList.add(nums[i]);
                used[i] = true;
                curSum += nums[i];
                backTrack( i+1);
                tempList.remove(tempList.size()-1);
                curSum -= nums[i];
                used[i] = false;
            }
        }
    }

    /**
     * 用于对比,是否使用used[] 的区别
     */
    private static class Solution{
        public List<List<Integer>> combinationSum(int[] candidates, int target) {

            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(candidates);
           // boolean [] used = new boolean[candidates.length];
            backTrack(candidates,target,result,new ArrayList<>(),0 , 0);
            return  result;
        }

        public void backTrack(int [] nums, int target, List<List<Integer>> result,List<Integer> tempList, int curSum , int fromIndex){

            if(curSum == target){
                result.add(new ArrayList<>(tempList));
                return;
            }

            for(int i = fromIndex; i < nums.length ; i++){
                if(curSum + nums[i] <= target){
                    tempList.add(nums[i]);
                    curSum += nums[i];
                    backTrack(nums,target,result,tempList,curSum , i+1);
                    tempList.remove(tempList.size()-1);
                    curSum -= nums[i];
                }
            }
        }
    }


}
