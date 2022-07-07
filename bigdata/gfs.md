# GFS


## Master

master 里面会存放三种主要的元数据（metadata）：

1. 文件和 chunk 的命名空间信息，也就是类似前面 /data/geektime/bigdata/gfs01 这样的路径和文件名；
2. 这些文件被拆分成了哪几个 chunk，也就是这个全路径文件名到多个 chunk handle 的映射关系；
3. 这些 chunk 实际被存储在了哪些 chunkserver 上，也就是 chunk handle 到 chunkserver 的映射关系。

