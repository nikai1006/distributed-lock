package com.nikai.distributed.lock.sort;

/**
 * 冒泡排序
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 21:19 2019/3/21
 * @Modified By:
 */
public class Bubble {

    public static void main(String[] args) {
        int[] nums = {3, 2, 15, 8, 5, 20, 36, 25, 19};

        int length = nums.length;
        for (int i = 0; i < length - 1; i++) {

            for (int j = 0; j < length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int a = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = a;
                }
            }
            System.out.println("第" + i  + "次排序结果");
            for (int num : nums) {
                System.out.printf(num + ",");
            }
            System.out.println();
        }
    }
}
