[![Build Status](https://travis-ci.com/mtumilowicz/java12-fundamentals-hash-map-implementation.svg?branch=master)](https://travis-ci.com/mtumilowicz/java12-fundamentals-hash-map-implementation)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# java12-fundamentals-hash-map-implementation-workshop

_Reference_: http://openjdk.java.net/jeps/180  
_Reference_: https://mincong-h.github.io/2018/04/08/learning-hashmap/  
_Reference_: https://www.nurkiewicz.com/2014/04/hashmap-performance-improvements-in.html  
_Reference_: https://www.javarticles.com/2012/11/hashmap-faq.html  
_Reference_: https://javaconceptoftheday.com/how-hashset-works-internally-in-java/

# quote
> TDD - everybody knows that TDD stand for test driven development; however people too often concentrate on the 
words: test and development, and don't conciders what the world driven really implies. For tests to drive 
development they must do more than just test that code performs its required functionality: they must clearly 
express that required functionality to the reader.  
>
> Nat Pryce and Steve Freeman, Are your tests really driving  your development?

# preface
* the main goal of this workshop:
    * practice TDD
    * practice asking right questions during development
    * become acquainted with implementation of `HashMap` 
    * chance to discuss some bitwise operations

# java 8 HashMap
## implementation overview
1. signed left shift operator `<<` 
    * shifts a bit pattern to the left
    * used in counting power of two
    * e.g. `1 << 4 = 16`
1.  an unsigned right shift operator `>>>`
    * shifts a bit pattern to the right regardless sign
    * e.g. `A = 60 = 0011 1100` => `A >>> 2 = 0000 1111` and `0000 1111 = 15`    
1. `transient Node<K,V>[] table;`
    * `HashMap` uses `writeObject` and `readObject` to implement custom serialization rather than 
    just letting its field be serialized normally
    * it writes the number of buckets and entries to the stream and rebuilds itself from those fields 
    when deserialized
    * table itself is simply unnecessary in the serial form
1. hash function
    ```
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    ```    
    * `XOR` - cheapest possible way to reduce systematic loss
        * `XOR` has the best bit shuffling properties (on average produces 1/2 one-bits)
        * `OR` on average produces 3/4 one-bits 
        * `AND` on average produces 3/4 zero-bits
    * `>>>` - in order to use highest bits in calculation of a bucket: `tab[(n - 1) & hash]`
    * as a result, the modulo obtained has less collision
    ```
    h             | h (binary)                              | h % 32 | (h ^ h >>> 16) % 32
    ------------: | :-------------------------------------: | -----: | ------------------:
           65,537 | 0000 0000 0000 0001 0000 0000 0000 0001 |      1 |                   0
          131,073 | 0000 0000 0000 0010 0000 0000 0000 0001 |      1 |                   3
          262,145 | 0000 0000 0000 0100 0000 0000 0000 0001 |      1 |                   5
          524,289 | 0000 0000 0000 1000 0000 0000 0000 0001 |      1 |                   1
    ```
1. small number of collisions is virtually inevitable: https://github.com/mtumilowicz/hash-function
1. `getNode`
    * `tab[(n - 1) & hash]`
    * **size have to be power of two**: `2^n - 1`, binary representation: all 1 
    * `16 - 1 = 15`, binary representation: `1111`
    * bitwise `AND` of any number with `1111` -> last 4 bits of the number
    * it is equivalent to the modulo of the number by `16`
    * division operation is usually an expensive operation
    * bitwise operation is usually preferred over division
    * last 4 bits will evaluate to any number from  0 to 15
    * **suppose we use the size 17 instead**
    * `17 - 1 = 16 `which is `10000` in binary 
    * bitwise AND with `16` -> lose all bits except the 5th bit from the end
    * regardless of the number, the bucket index will be either 16 or 0
    * it means a lot of collisions and poor performance
    * instead of `O(1)` for retrieval, you'd need `O(log n)`
    * in case of `ConcurrentHashMap` in a multithreaded environment, you'd experience lot of synchronizations
## performance
* **JEP 180: Handle Frequent HashMap Collisions with Balanced Trees**: improve the performance of 
`HashMap` under high hash-collision conditions by using balanced trees rather than linked lists to store map 
entries
* when a bucket becomes too big (`TREEIFY_THRESHOLD = 8`), `HashMap` dynamically replaces list
with a tree (using hash code as a branching variable)
    * if two hashes are different but ended up in the same bucket, one is considered bigger and goes to the right 
        * if hashes are equal, `HashMap` compares the keys (if possible) 
        * it is a good practice when keys are `Comparable`
        * if keys are not comparable, no performance improvements in case of heavy hash collisions
* in some cases (mentioned above) with heavy hash collisions rather than pessimistic `O(n)` we 
get much better `O(log n)`

# questions that should be asked
1. initial capacity
1. resize (capacity factor) - how to reasonably define load factor?
    * assumption: every hash function at least makes a best attempt to distribute hash codes
    * growing based on total size keeps the collision lists at a reasonable size with realistic imperfect 
    hash function
1. what to do with `null`, any validations?
    * `null` key
    * `null` value
    * note that there is no significant difference between getting key with null value and getting key that
    not exists
1. drop or replace entry with the key that already exists in map?
1. `get` should return `Optional`?
1. what to do in case of collision? list vs tree 
    * any threshold?
1. thread safe vs not thread safe
    * approaches to thread safety (global vs per-bucket synchronization)
1. immutable or mutable?
    * consequences - (HATM) - https://en.wikipedia.org/wiki/Hash_array_mapped_trie
1. how to write tests
    * you could unit-test implementation but tests should be easy to throw away
# digression
* `HashSet` uses `HashMap` internally to store it’s objects 
* whenever we create a `HashSet`, `HashMap` object associated with it is also created
    * elements you add into `HashSet` are stored as keys of `HashMap` object, value associated with those 
    keys is a constant
* from `HashSet` implementation
    ```
    private static final Object PRESENT = new Object();
    
    public boolean add(E e)
    {
            return map.put(e, PRESENT)==null;
    }
    
    public boolean remove(Object o)
    {
            return map.remove(o)==PRESENT;
    }
    ```