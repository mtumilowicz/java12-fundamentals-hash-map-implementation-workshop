# java12-fundamentals-hash-map-implementation

https://mincong-h.github.io/2018/04/08/learning-hashmap/  
https://www.nurkiewicz.com/2014/04/hashmap-performance-improvements-in.html  
https://www.javarticles.com/2012/11/hashmap-faq.html

TDD - everybody knows that TDD stand for test driven development; however people too often concentrate on the words: test and development, and don't conciders what the world driven really implies. For tests to drive development they must do more than just test that code performs its required functionality: they must clearly express that required functionality to the reader. - Nat Pryce and Steve Freeman, Are your tests really driving your development?

1. initial capacity
1. resize (capacity factor) - how to reasonably define load factor?
1. what to do with null? null key - possible? null value - possible?
1. get should return Optional ?
1. what to do in case of collision? linkedlist vs balanced tree (threshold)
1. thread safe vs not thread safe
1. immutable or mutable?
1. thread safe or not? what kind of thread safe?
1. validations?
1. growing based on total size keeps the collision lists at a reasonable size with realistic imperfect hash function, 
because it’s reasonable to expect every hash function at least makes a best attempt to distribute hash codes
1. Well, an alternative would be to use
   hash % capacity
   but that's actually quite a bit slower than
   hash & (capacity - 1)
   and when capacity is a power of two, both calculations actually do the same thing.
   
When you subtract 1 from a number which is a power of 2, what you get is a number whose binary representation 
is all 1. E.g. 16 is a power of 2. If you subtract 1 from it, you get 15, whose binary representation is 1111. 
Now, if you do a bitwise AND of any number with 1111, you're going to get the last 4 bits of the number which, 
in other words, is equivalent to the modulo of the number by 16 (Division operation is usually an expensive operation. 
Hence, bitwise operation is usually preferred over division). These last 4 bits will evaluate to any number from 
0 to 15 which are the indexes of your underlying array.

You could make the size 17 instead. In that case, after subtracting 1 from it, you'd get 16 which is 10000 in binary. 
Now you do a bit wise AND of a number with 16, you'll lose all bits of the number except the 5th bit from the end. 
So, regardless of the number you take, the array index will be either 16 or 0. That means you'd have lot of 
collisions, which in turn means poor performance. Instead of O(1) for retrieval, you'd need O(log n), because 
when collision occurs, all the nodes in a given bucket are going to be stored in a red black tree. Not only that. 
In case you are using a ConcurrentHashMap in a multithreaded environmemt, you'd experience lot of synchronizations, 
because all the new additions will end up in a very small number of buckets (only two - 0 and 16 in the above case) 
and when you add new nodes in a bucket that already has other nodes, the bucket will be locked to avoid data 
inconsistancies due to modifications by multiple threads. Therefore, other threads trying to add new nodes need to 
wait until the current thread release the lock.
