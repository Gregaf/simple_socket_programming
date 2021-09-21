# Simple Socket Programming
This is a simple console application that uses a client/service architecture. Where the Server listens on a port number thats specified and the Client connects via the IP address and port. The client sends simple expressions in the form *number op number =*, where the server evaluates the expression and responds to the client.
# How to run the Program
You will need atleast openJDK 8 in order to run the following programs, no guarentees for versions below that.
## Running on Linux
You will be able to run this program using this command, also make sure you are in the root of the project directory:
`java Server.java 5000 & sleep 2 && java Client.java localhost 5000`
The above command will launch the server on port 5000, wait for 2 seconds, then launch the client listening on
127.0.0.0:5000. You can change the parameters as needed, if using the port like so. Watch out for ports that are
already in use, the server will try 5 times to find another port, if thats the case it will output the port number
found. Use the command `java Client.java IP PORT` filling in the IP and PORT values accordingly to connect.

## Running on Windows
If running on windows, ensure that you are passing the command line arguments to Server.java and Client.java accordingly. `Server.java PORT` and `Client.java IP PORT` otherwise it will obviously fail.