# GFS

什么是大数据？

大数据”是指传统数据处理应用软件时，不足以处理的大的或者复杂的数据集的术语。

核心技术理念

第一个，是能够伸缩到一千台服务器以上的分布式数据处理集群的技术。

第二个，是这个上千个节点的集群，是采用廉价的 PC 架构搭建起来的。

最后一个，则是“把数据中心当作是一台计算机”（Datacenter as a Computer）。

## Master

master 里面会存放三种主要的元数据（metadata）：

1. 文件和 chunk 的命名空间信息，也就是类似前面 /data/geektime/bigdata/gfs01 这样的路径和文件名；
2. 这些文件被拆分成了哪几个 chunk，也就是这个全路径文件名到多个 chunk handle 的映射关系；
3. 这些 chunk 实际被存储在了哪些 chunkserver 上，也就是 chunk handle 到 chunkserver 的映射关系。

