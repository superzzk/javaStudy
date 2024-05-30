//你这个学期必须选修 numCourse 门课程，记为 0 到 numCourse-1 。 
//
// 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们：[0,1] 
//
// 给定课程总量以及它们的先决条件，请你判断是否可能完成所有课程的学习？ 
//
// 
//
// 示例 1: 
//
// 输入: 2, [[1,0]] 
//输出: true
//解释: 总共有 2 门课程。学习课程 1 之前，你需要完成课程 0。所以这是可能的。 
//
// 示例 2: 
//
// 输入: 2, [[1,0],[0,1]]
//输出: false
//解释: 总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0；并且学习课程 0 之前，你还应先完成课程 1。这是不可能的。 
//
// 
//
// 提示： 
//
// 
// 输入的先决条件是由 边缘列表 表示的图形，而不是 邻接矩阵 。详情请参见图的表示法。 
// 你可以假定输入的先决条件中没有重复的边。 
// 1 <= numCourses <= 10^5 
// 
// Related Topics 深度优先搜索 广度优先搜索 图 拓扑排序

package com.zzk.study.leetcode.leetcode.editor.cn;


import java.util.*;

class Q207CourseSchedule {

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        Set<Integer> checked = new HashSet<>();

        public boolean canFinish(int numCourses, int[][] prerequisites) {
            // 提前计算每个course的直接依赖
            HashSet[] depends = new HashSet[numCourses];
            for (int i = 0; i < numCourses; i++) {
                depends[i] = new HashSet<>();
            }
            for (int i = 0; i < prerequisites.length; i++) {
                depends[prerequisites[i][0]].add(prerequisites[i][1]);
            }

            for (int i = 0; i < prerequisites.length; i++) {
                if (!checked.contains(prerequisites[i][0])) {
                    int rt = dfs( prerequisites[i][0], new HashSet<>(), depends);
                    if (rt == -1)
                        return false;
                    checked.add(prerequisites[i][0]);
                }
            }
            return true;
        }

        private int dfs(int course, Set<Integer> path, HashSet[] depends) {
            if (depends[course].isEmpty())
                return 0;
            for (Object o : depends[course]) {
                int depend = (int) o;
                if (path.contains(depend))
                    return -1;
                if(checked.contains(depend))
                    continue;
                path.add(depend);
                int rt = dfs(depend, path, depends);
                if (rt == -1)
                    return -1;
                path.remove(depend);
                checked.add(depend);
            }
            checked.add(course);
            return 0;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        Solution solution = new Q207CourseSchedule().new Solution();
    }
}