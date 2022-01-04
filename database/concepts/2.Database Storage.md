# Database Storage

The DBMS assumes that the primary storage location of database is on non-volatile disk. The DBMS's components manage the movement of data between non-volatile and volatile storage.

## Why not use the OS

- madvise: Tell the OS how you expect to read certain pages
- mlock: Tell the OS that memory ranges cannot be paged out
- msync: Tell the OS to flush memory ranges out to disk

DBMS always wants to control things itself and can do a better job at it

- Flushing dirty pages to disk in the correct order
- Specialized prefetching
- Buffer replacement policy
- Thread/process scheduling

## File Storage

The DBMS stores a databse as one or more files on disk.

### Storage Manager

Responsible for maintaining a database's files, it organizes the files as a collection of pages.

A page is a fixed-size block of data.

- It can contain tuples, meta-data, indexes, log records...
- Most systems do not mix page types
- Some system require a page to be self-contained
- Each page is given a unique identifier, the DBMS uses a indirection layer to map page ids to physical locations

Pages in DBMS, by hardware page, we mean at what level the device can guarantee a "failsafe write".

- Hardware Page (usually 4KB)
- OS Page (usually 4KB)
- Database Page (512B - 16KB)

### Page Storage Architecture

#### Database Heap

A heap file is an unordered collection of pages where tuples that are stored in random order.

Need meta-data to keep track of what pages exist and which ones have free space.

##### Linked List

Maintain a header page at the beginning of the file that stores two pointers:

- Head of the free page list
- Head of the data page list

Each page keeps track of the number of free slots in itself.

##### Page Directory

The DBMS maintains special pages that tracks the location of data pages in the database files, also records the number of free slots per page.

The DBMS has to make sure that the directory pages are in sync with the data pages.

**Page Header**

Every page contains a header of metadata about the page's contents.

- Page Size
- Checksum
- DBMS Version
- Transaction Visibility
- Compression Information

Some systems require pages to be self-contained(e.g. Oracle)

## Page Layout

**Strawman Idea** 
Keep track of the number of tuples in a page and then just append a new tule to the end.

|page|
|---|
|num tuples = 3|
|Tuple #1|
|Tuple #2|
|Tuple #3|

- What happens if we delete a tuple
- What happens if we have a variable length attribute

## Tuple Layout

