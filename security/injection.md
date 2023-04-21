下面是一些常见的注入攻击示例，其中包括了基于 Gremlin 查询的注入攻击：

SQL 注入攻击：在 Gremlin 查询中注入恶意 SQL 代码，例如：

```groovy
g.V().has('name', "' or 1=1 --")
```

在这个示例中，攻击者试图绕过输入验证，将 "name" 参数设置为 " ' or 1=1 -- "，这将返回所有人的信息。

命令注入攻击：在 Gremlin 查询中注入恶意命令，例如：

```groovy
g.V().has('name', "`cat /etc/passwd`")
```

在这个示例中，攻击者试图通过命令注入攻击获取系统的敏感信息。

LDAP 注入攻击：在 Gremlin 查询中注入恶意 LDAP 代码，例如：

```groovy
g.V().has('name', "*' OR 1=1)(&(!(cn=*))(|(cn=*")
```

在这个示例中，攻击者试图利用 LDAP 注入漏洞，绕过身份验证，访问敏感数据。

XPath 注入攻击：在 Gremlin 查询中注入恶意 XPath 代码，例如：

```groovy
g.V().has('name', "'' or //user[password=md5('1234')]")
```

在这个示例中，攻击者试图利用 XPath 注入漏洞，绕过身份验证，访问敏感数据。

OS 命令注入攻击：在 Gremlin 查询中注入恶意 OS 命令，例如：

```groovy
g.V().has('name', "; touch /tmp/test.txt")
```

在这个示例中，攻击者试图通过操作系统命令注入攻击，在目标系统中创建一个恶意文件。

XML 注入攻击：在 Gremlin 查询中注入恶意 XML 代码，例如：

```groovy
g.V().has('name', "<!DOCTYPE roottag [<!ENTITY example 'data'>]> <tag>&example;</tag>")
```

在这个示例中，攻击者试图利用 XML 注入漏洞，访问目标系统中的敏感数据。

需要注意的是，以上示例仅用于说明注入攻击的概念和原理，实际情况中，攻击者可能会使用更加复杂和隐蔽的攻击方式，因此需要采取综合性的安全措施来保护系统的安全。
