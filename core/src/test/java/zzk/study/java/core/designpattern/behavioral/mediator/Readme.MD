
#pattern
The intent of the Mediator Pattern is to reduce the complexity and dependencies between tightly coupled objects communicating directly with one another. This is achieved by creating a mediator object that takes care of the interaction between dependent objects. Consequently, all the communication goes through the mediator.

This promotes loose coupling, as a set of components working together no longer have to interact directly. Instead, they only refer to the single mediator object. This way, it is also easier to reuse these objects in other parts of the system.

#UML
![GitHub Logo](Mediator.png)
In the above UML diagram, we can identify the following participants:

Mediator defines the interface the Colleague objects use to communicate
Colleague defines the abstract class holding a single reference to the Mediator
ConcreteMediator encapsulates the interaction logic between Colleague objects
ConcreteColleague1 and ConcreteColleague2 communicate only through the Mediator
As we can see, Colleague objects do not refer to each other directly. Instead, all the communication is carried out by the Mediator.

Consequently, ConcreteColleague1 and ConcreteColleague2 can be more easily reused.