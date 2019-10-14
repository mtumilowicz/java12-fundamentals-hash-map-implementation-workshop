# java13-fundamentals-hash-map-implementation

https://mincong-h.github.io/2018/04/08/learning-hashmap/

1. initial capacity
1. resize (capacity factor)
1. what to do with null? null key - possible? null value - possible?
1. get should return Optional ?
1. what to do in case of collision? linkedlist vs balanced tree (threshold)
1. thread safe vs not thread safe
1. immutable or mutable?
1. thread safe or not? what kind of thread safe?
1. growing based on total size keeps the collision lists at a reasonable size with realistic imperfect hash function, 
because it’s reasonable to expect every hash function at least makes a best attempt to distribute hash codes