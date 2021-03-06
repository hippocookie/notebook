# 数据库索引
## 常见索引
- B+树索引: B+树中的B不是代表二叉（binary），而是代表平衡（balance），因为B+树是从最早的平衡二叉树演化而来，但是B+树不是一个二叉树，B+树是通过二叉查找树，再由平衡二叉树，B树演化而来。
- 哈希索引
- 全文索引

### 二叉查找树和平衡二叉树
**二叉查找树**：左子树的键值总是小于根键值，右子树键值总是大于根键值。
>           6
>          / \
>         3   7
>        / \   \
>       2   5   8

二叉树极端情况下查找效率劣化与链表相同。
>       1
>        \
>         2
>          \
>           3
>            \
>             4
**平衡二叉树(AVL树)**：符合二叉树的定义，此外需满足任何节点的两个子树高度最大差为1。
平衡二叉树通过使用左旋和右旋在节点插入删除操作之后维护结构的平衡性，保持较高的查询性能，但其维护成本也响应较高。
>           6                       6
>          / \                    /   \
>         3   7         左旋      3     8
>        / \   \         =>     / \   / \
>       2   5   8              2   5 7   9 
>                \
>                 9

