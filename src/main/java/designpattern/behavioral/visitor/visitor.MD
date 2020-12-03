#intro
The purpose of a Visitor pattern is to define a new operation without introducing the modifications to an existing object structure.

Imagine that we have a composite object which consists of components. The object's structure is fixed – we either can't change it, or we don't plan to add new types of elements to the structure.

Now, how could we add new functionality to our code without modification of existing classes?

The Visitor design pattern might be an answer. Simply put, we'll have to do is to add a function which accepts the visitor class to each element of the structure.

That way our components will allow the visitor implementation to “visit” them and perform any required action on that element.

In other words, we'll extract the algorithm which will be applied to the object structure from the classes.

Consequently, we'll make good use of the Open/Closed principle as we won't modify the code, but we'll still be able to extend the functionality by providing a new Visitor implementation.
#uml:
![GitHub Logo](Visitor-UML.png)

The visitor pattern consists of two parts:

* a method called Visit() which is implemented by the visitor and is called for every element in the data structure
* visitable classes providing Accept() methods that accept a visitor

#Design components
* Client : The Client class is a consumer of the classes of the visitor design pattern. It has access to the data structure objects and can instruct them to accept a Visitor to perform the appropriate processing.
* Visitor : This is an interface or an abstract class used to declare the visit operations for all the types of visitable classes.
* ConcreteVisitor : For each type of visitor all the visit methods, declared in abstract visitor, must be implemented. Each Visitor will be responsible for different operations.
* Visitable : This is an interface which declares the accept operation. This is the entry point which enables an object to be “visited” by the visitor object.
* ConcreteVisitable : These classes implement the Visitable interface or class and defines the accept operation. The visitor object is passed to this object using the accept operation.

#Advantages :

If the logic of operation changes, then we need to make change only in the visitor implementation rather than doing it in all the item classes.
Adding a new item to the system is easy, it will require change only in visitor interface and implementation and existing item classes will not be affected.
#Disadvantages :

We should know the return type of visit() methods at the time of designing otherwise we will have to change the interface and all of its implementations.
If there are too many implementations of visitor interface, it makes it hard to extend.