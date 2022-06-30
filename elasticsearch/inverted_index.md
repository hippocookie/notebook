# 倒排索引 - Inverted Index

## 倒排表 - Posting List

- int有序数组：存储了匹配某个term的所有id

## 词项字典 - Term Dictionary

- tip: 词典索引，存放前缀后缀指针，需要内存加载
- tim: 后缀词块，倒排表指针
- doc: 倒排表、词频

## 词项索引 - Term Index

极大的节省内存，FST压缩倍率最高可以达到20倍，性能不如HashMap

