# <p align="center">Section 2</p>


## Design requirements
Extensions or changes to functionality should be possible with minimal impact on the code produced: in particular, it should be 'simple' to define new functions and new searches on cells. The aim is to increase the flexibility of the application with regard to the support of new functions.
The structure for storing the contents of the sheet should be flexible, so as to allow the efficient representation of sheets of different sizes. Thus, it should be possible to use different strategies for representing the content, without impacting on the rest of the application.
The aim of this requirement is to optimize the space taken up in memory to store the content of a spreadsheet. It doesn't need to be implemented for all situations, it just needs to be flexible enough to allow for new implementations.

Determining the value of a function involving a range can be relatively costly if it involves a large number of cells.
If none of the cells involved have changed since the last time the value of the function was determined, then calculating the value again is a waste. The aim is to obtain a solution that optimizes the number of times a function involving a range needs to calculate its value. This requirement should only be considered once everything has been implemented and will only be taken into account in the final delivery of the project.

The solution found to optimize the number of times a function has to be calculated should be extended to allow other types of functionality to be specified when the value of a cell changes make it possible to specify other types of functionality that should occur when the value of a cell changes value. For example, it should be possible to implement the following requirement without modifying existing classes (it is not necessary to implement this example): send an email to a given user each time the cell in question changes its value. Your solution should be flexible enough to support different types of entities interested in modifying a cell without having to change the code already in place. 

The application should already be prepared to support multiple users, and should already have the code (at the domain layer level only) for inserting new users.

### 2.1 Application functionality

The application manages information about the entities in the model. It also has the ability to preserve their state (it is not possible to maintain multiple versions of the application's state simultaneously).
At the start of the application, a textual database can be loaded database with pre-defined concepts can be loaded. Initially, the application only has information about the entities that were loaded at application start-up. 

It should be possible to insert, delete and display the contents of cells on the active sheet and perform copy, paste and cut operations.
It should be possible to search the contents of cells from different aspects: (i) values resulting from evaluation; (ii) names of functions.

### 2.2 Serialization
It is possible to store and retrieve the current state of the application, preserving all the relevant information from the application's domain and which was described in section 1.