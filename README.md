# SC2002 Project

## Hospital Management System (HMS)
Hospital Management System (HMS) is a Command Line Interface (CLI) application designed using the object-oriented design approach to automate the management of hospital operations.

This project was done by SCS4 Group 3

## Approach Taken
HMS uses the entity-control-boundary (ECB) class stereotypes for better organization and maintainability of classes in the application. Users interact with the application through the respective menus, which are the boundary classes. The boundary classes then call upon the control classes, such as the AppointmentManager class, to perform the required operations. The control classes subsequently retrieve the data from the entity classes in order to perform the requested operations and also update the data accordingly. Utilizing this framework will improve the maintainability of our code and allow our system to be easily extendable for additional functionalities 

