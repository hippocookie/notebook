# JDK 9 - String

> JEP 254: Compact Strings
>
> 使用更节省空间的存储方式
>
> 由char[] 类型修改为使用byte[] 类型存储。

## 动机

该类的当前实现String将字符存储在一个 char数组中，每个字符使用两个字节（十六位）。从许多不同应用程序收集的数据表明，字符串是堆使用的主要组成部分，而且大多数String对象仅包含 Latin-1 字符。此类字符仅需要一个字节的存储空间，因此此类对象的内部char数组中的一半空间未使用。

```java
class String {
    private final byte[] value;

    /*
    * code = 0 表示latin-1，用1个byte表示
    * code = 1 表示UTF-16，用2个byte表示
    */
    private final byte coder
    ...
}
```
