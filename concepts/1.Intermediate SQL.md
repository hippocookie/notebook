# Intermediate SQL

- Data Manipulation Language (DML)
- Data Definition Language (DDL)
- Data Control Language (DCL)

## Basic Syntax

### Joins

```sql
SELECT s.name FROM enrolled AS e, student AS s
WHERE e.grade = 'A' AND e.cid = '15-721'
AND e.sid = s.sid
```

### Aggregates

Functions that return a single value from a bag of tuples

```sql

AVG(col)
MIN(col)
MAX(col)
SUM(col)
COUNT(col)

SELECT AVG(GPA), COUNT(sid) FROM student WHERE login LIKE '%@cs'

SELECT COUNT(DISTINCT login) FROM student WHERE login LIKE '%@cs'
```

### Group By
Project tules into subsets and calculate aggregates against each subset.

Non-aggregated values in SELECT output clause must appear in GROUP BY clause.

```sql
SELECT AVG(s.gpa), e.cid FROM entrolled AS e, student AS s
WHERE e.sid = s.sid
GROUP BY e.cid
```

### Having

Filters results based on aggregation computation.
Like a WHERE clause for a GROUP BY.

```sql
SELECT AVG(s.gpa) AS avg_gpa, e.cid FROM entrolled AS e, student AS s
WHERE e.sid = s.sid
GROUP BY e.cid
HAVING AVG(s.gpa) > 3.9
```

### String Operations

#### LIKE
LIKE is used for string matching

- %: Matches any substring, including empty strings
- _: Match any one character

#### SUBSTRING

```sql
SELECT SUBSTRING(name, 1, 5) AS abbrv_name
FROM student WHERE sid = 123
```

#### UPPER

```sql
SELECT * FROM student WHERE UPPER(name) LIKE '%123'
```

#### ||

Concatenate two or more strings together.

```sql
SELECT 'data' || 'base' AS name 
```

### Date/Time Operations

### Output Redirection

Store query result in another table

- Table must not already be defined
- Table will have the same # of columns with the same types as the input

```sql
SELECT DISTINCT cid INTO CourseIds FROM enrolled

INSERT INTO CourseIds (SELECT DISTINCT cid FROM enrolled)
```

### Output Control

#### ORDER BY [column*] [ASC|DESC]

Order the outpu tules by the values in one or more of their columns

#### LIMIT [count] [offset]

- Limit the # of tuples returned in output
- Can set An offset to return a range

### Nested Queries

Queries containing other queries, they are often difficult to optimize.

- ALL: Must satisfy expression for all rows in the sub-query
- ANY: Must satisfy expression for at-least one row in the sub-query
- IN: Equivalent to '=ANY()'
- EXISTS: At least one row is returned

```sql
SELECT name FROM student
WHERE sid = ANY(SELECT sid FROM enrolled WHERE cid = '123')

SELECT * FROM COURSE
WHERE NOT EXISTS(SELECT * FROM enrolled WHERE course.cid = enrolled.cid)
```

### Window Functions

Performs a sliding calculation across a set of tuples that are related.
Like an aggregation but tuples are not grouped into a single output tuples.

```sql
SELECT cid, sid,
ROW_NUMBER() OVER (PARTITION BY cid)
FROM enrolled
ORDER BY cid

SELECT *, 
ROW_NUMBER() OVER (ORDER BY cid)
FROM enrolled
ORDER BY cid

-- Find the student with the second highest grade for each course
SELECT * FROM (
    SELECT *, RANK() OVER (PARTITION BY cid ORDER BY grade ASC) AS rank
    FROM enrolled
) AS ranking
WHERE ranking.rank = 2
```

### Common Table Expressions

Provide a way to write auxiliary statements for use in a large query, think of it like a temp table just for one query.

```sql
WITH cteName AS (col1, col2) AS (
    SELECT 1, 2
)
SELECT col1 + col2 FROM cteName

-- Print sequence of numer from 1 to 10
WITH RECURSIVE cteSource (counter) AS (
    (SELECT 1)
    UNION ALL
    (SELECT counter + 1 FROM cteSource WHERE counter < 10)
)
SELECT * FROM cteSource
```






