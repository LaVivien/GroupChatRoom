# Chatty Chat Chat 

## Decription

In this assignment you will implement your very own internet chat protocol -- the ChattyChatChat protocol (CCC). The protocol governs how a single ChattyChatChat server mediates connections between any number of ChattyChatChat clients as they communicate with each other.
Implementing this protocol will require that you create at least two Java classes:
•	The ChattyChatChatServer, which is run on a single computer at a specified port and receives connections from clients.
•	The ChattyChatChatClient, which is run by each client computer and connects to the server.

## Specification

Your implementation of the ChattyChatChat protocol requires that you write two programs -- a ChattyChatChat server and a ChattyChatChat client.

### ChattyChatChatServer

The server program must be a class named ChattyChatChatServer; this program should accept a single command-line argument describing the port for the server to listen on. For example, to start the server and have it listen to port 9876, the command-line invocation would be:
java ChattyChatChatServer 9876
A single instance of the ChattyChatChat server will server as the common point of connection for all clients wanting to interact with the chat server.

### ChattyChatChatClient

The client program must be a class named ChattyChatChatClient; this program should accept two command-line arguments describing the server name and port to connect to. For example, to start a client and connect to a server running on port 9876 on cs-class, the command-line invocation would be:
java ChattyChatChatClient cs-class.uis.georgetown.edu 9876
Note that the server must be running in order for any client to successfully connect.

### chat protocol and commands

The communication protocol for the chat clients and server should obey the following rules:
•	A "normal" message is text sent by one client to the server; this message should be relayed to all other clients, who will print it to standard out (e.g., using System.out.println()) upon receipt.

•	The CCC protocol also provides the following chat commands which should be interpreted by the server to perform a special task:
	/nick <name> : Set this client's nickname to be the string <name>. For example:
```	
•	/nick cosc150student
```
would set the user's nickname to cosc150student.

	The nickname command may be used more than once per session by any user; the current nickname is retained unless/until a subsequent /nick command is received.
	Nicknames do not need to be unique on the server.
	Nicknames are single-words and do not contain spaces.
	Any additional characters beyond the first word may be ignored; that is, the above and below commands would have identical effect:
```	
/nick cosc150student these words may be discarded
```
	/dm <name> <msg> : Send a message to user(s) with the specified nickname. For example:
```	
/dm cosc150student This is a "secret" message
```
should deliver the message "This is a "secret" message" only to user(s) who have the nickname "cosc150student".

	Only clients with the correct nickname should receive this message; nothing should be sent to any other clients.
	If no client has the specified nickname, this message may be ignored.
	If multiple clients have the specified nickname, all of them should receive the message.
	/quit : Disconnect from the server and end the client program.
		When a client enters this message, it should still be sent to the server as a notice that the client will disconnect; the server may then safely close this socket connection and clean-up details related to the client.
		The client program should disconnect, clean up, and end when this string is entered.
		Any additional characters after the /quit may be safely ignored.
•	Any other input (including one beginning with a slash, but not exactly matching the above) should be considered a regular message.









