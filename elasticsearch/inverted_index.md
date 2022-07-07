# 倒排索引 - Inverted Index

## 倒排表 - Posting List

- int有序数组：存储了匹配某个term的所有id
  - RBM: Roaring Bitmaps
  - FOR: Frame Of Reference

### FOR: Frame Of Reference

只适合稠密数据（前后差值较小）

> 1,2,3,...,100W个
>
> 1 int = 4Byte, 400W Byte = 4MB = 3200W bit

Delta List: 存储当前数字和前一个数字的差值

> 1,2,3,...,100W个, 1 int = 1 bit, 100W bit
>
> list:
>
> [73, 300, 302, 322, 343, 372]
>
> posting list:
>
> [73, 227, 2 ,30, 11, 29] -- 8 bit each = 256
>
> 优化拆分(拆分数量越多meta占用越多)
>
> [73, 227] -- 8 bit each(meta 1 byte)  [2, 30, 11, 29] -- 5 bit each(meta 1 byte)

### RBM: Roaring Bitmaps

适合稀疏数据（前后差值较大）

> [1000, 62101, 131385, 132052] -- 2(16) * 2(16)
>
> 除以65536
>
> (0, 1000), (0, 62101), (2, 313), (2, 980) -- 熵(二进制前16位)和余数(二进制后16位)

|short key|Container|
|---|---|
|0|1000, 62101|
|2|313, 980|

- Array Container
- BitMap Container
- Range Container

## 词项字典 - Term Dictionary

- tip: 词典索引，存放前缀后缀指针，需要内存加载
- tim: 后缀词块，倒排表指针
- doc: 倒排表、词频

## 词项索引 - Term Index

极大的节省内存，FST压缩倍率最高可以达到20倍，性能不如HashMap
