CS 3516 / CS 513 Class Project

A Client-Server Chat Program

Introduction

For this project, you must write a chat program based on the client-server model using C or C++ using Linux socket commands. The learning objective of this project is provide you with insight into practical computer networks and the problems faced when implementing them.

General Information

This is an individual project assignment for each student.  Each student must develop and submit a project report. Please see the Software Programming Project Sample Outline in the course syllabus section on our class website for more detailed information. 

All project sources and reports will be checked for plagiarism, including being checked against solutions from online sources. If you are found guilty there will be serious consequences, including receiving a mark of zero for the project.  Please review the WPI policies on Academic Honesty and Avoiding Plagiarism at http://www.wpi.edu/Images/CMS/Bio/academic.pdf


Project Specifications

The model used for this project is the single server - multiple client model. The following general specifications must be implemented:

1.	Multiple client
2.	A simple GUI can be implemented for the server
3.	Clients must be able to “whisper” to each other
4.	Clients must be able to choose a nickname


The Server

A single server program should handle all requests from the clients. Your client will have to implement a multi-service solution for your server. The following must be implemented in your server application:

1.	Server operations (such as connect requests and disconnect requests) should be printed out by the server.
2.	The server must handle connections / disconnections without disruption of other services.
3.	Clients must have unique nicknames, duplicates must be resolved before allowing a client to be connected.
4.	All clients must be informed of changes in the list of connected users.

The Client(s)

The following must be implemented in your client application:

1.	A list of online users must be displayed (via GUI or command).
2.	Connection / disconnection actions of users must be displayed.
3.	Messages from the originating user and other users must be displayed (in other words the messages you send must also be displayed).
4.	Must still be able to receive messages / actions while typing a message.
5.	Clients must be able to disconnect without disrupting the server.

