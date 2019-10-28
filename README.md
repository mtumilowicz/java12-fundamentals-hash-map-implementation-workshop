# java12-fundamentals-hash-map-implementation-workshop

https://mincong-h.github.io/2018/04/08/learning-hashmap/  
https://www.nurkiewicz.com/2014/04/hashmap-performance-improvements-in.html  
https://www.javarticles.com/2012/11/hashmap-faq.html

> TDD - everybody knows that TDD stand for test driven development; however people too often concentrate on the 
words: test and development, and don't conciders what the world driven really implies. For tests to drive 
development they must do more than just test that code performs its required functionality: they must clearly 
express that required functionality to the reader.  
>
> Nat Pryce and Steve Freeman, Are your tests really driving  your development?

# java 12 implementation
1. signed left shift operator `<<` shifts a bit pattern to the left
    * e.g. `1 << 4 = 16`
1. `transient Node<K,V>[] table;`
    * `HashMap` uses `writeObject` and `readObject` to implement custom serialization rather than 
    just letting its field be serialized normally
    * it writes the number of buckets, the total size and each of the entries to the stream and rebuilds 
    itself from those fields when deserialized
    * table itself is unnecessary in the serial form, so it's not serialized to save space
1.  an unsigned right shift operator `>>>`: left operands value is moved right by the number of bits specified 
by the right operand and shifted values are filled up with zeros
    * e.g. `A = 60 = 0011 1100` => `A >>> 2 = 0000 1111` and `0000 1111 = 15`
1. hash function
    ```
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    ```    
    * `XOR` - cheapest possible way to reduce systematic lossage
        * XOR has the best bit shuffling properties
        * OR will on average produce 3/4 one-bits. AND on the other hand will produce on average 3/4 null-bits. 
        Only XOR has an even one-bit vs. null-bit distribution. That makes it so valuable for hash-code generation
    * `>>>` - what for? in order to use these highest bits
    * as a result, the modulo obtained has less collision
    ```
    h             | h (binary)                              | h % 32 | (h ^ h \>\>\> 16) % 32
    ------------: | :-------------------------------------: | -----: | ------------------:
           65,537 | 0000 0000 0000 0001 0000 0000 0000 0001 |      1 |                   0
          131,073 | 0000 0000 0000 0010 0000 0000 0000 0001 |      1 |                   3
          262,145 | 0000 0000 0000 0100 0000 0000 0000 0001 |      1 |                   5
          524,289 | 0000 0000 0000 1000 0000 0000 0000 0001 |      1 |                   1
    ```
* when a bucket becomes too big (currently: TREEIFY_THRESHOLD = 8), HashMap dynamically replaces it with an ad-hoc 
implementation of tree map. This way rather than having pessimistic O(n) we get much better O(logn). How does it work? 
Well, previously entries with conflicting keys were simply appended to linked list, which later had to be traversed. 
Now HashMap promotes list into binary tree, using hash code as a branching variable. If two hashes are different but 
ended up in the same bucket, one is considered bigger and goes to the right. If hashes are equal (as in our case), 
HashMap hopes that the keys are Comparable, so that it can establish some order. This is not a requirement of HashMap 
keys, but apparently a good practice. If keys are not comparable, don't expect any performance improvements in case of 
heavy hash collisions.



    
1. initial capacity
1. resize (capacity factor) - how to reasonably define load factor?
    * growing based on total size keeps the collision lists at a reasonable size with realistic imperfect hash function, 
    because it’s reasonable to expect every hash function at least makes a best attempt to distribute hash codes
    * https://github.com/mtumilowicz/hash-function
1. what to do with null? null key - possible? null value - possible?
1. get should return Optional ?
1. what to do in case of collision? linkedlist vs balanced tree (threshold)
1. thread safe vs not thread safe
    * approaches to thread safety
1. immutable or mutable?
1. thread safe or not? what kind of thread safe?
1. validations?
   
* `2^n - 1` is a number whose binary representation is all 1 
* `16 - 1 = 15`, whose binary representation is `1111`
* if you do a bitwise `AND` of any number with `1111`, you're going to get the last 4 bits of the number
* it is equivalent to the modulo of the number by `16`
* division operation is usually an expensive operation
* bitwise operation is usually preferred over division
* last 4 bits will evaluate to any number from  0 to 15

* suppose we use the size 17 instead
* `17 - 1 = 16 `which is `10000` in binary 
* bitwise AND with 16 - you'll lose all bits except the 5th bit from the end
* so, regardless of the number you take, the bucket index will be either 16 or 0
* that means you'd have lot of collisions, which in turn means poor performance
* instead of `O(1)` for retrieval, you'd need `O(log n)` (when collision occurs, all the nodes in a given bucket 
are stored in a red black tree) 
* in case of `ConcurrentHashMap` in a multithreaded environment, you'd experience lot of synchronizations, 
* all the new additions will end up in a very small number of buckets (only two - 0 and 16 in the above case) 
and when you add new nodes in a bucket that already has other nodes, the bucket will be locked to avoid data 
inconsistencies due to modifications by multiple threads
* therefore, other threads trying to add new nodes need to wait until the current thread release the lock
