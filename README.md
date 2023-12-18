# Java-based-P2P-chat

This Java-based Peer-to-Peer (P2P) chat application enables local communication, allowing users to exchange messages seamlessly within a network. The application offers two distinct modes of connection: direct connection using the recipient's IP address and port, or through a STUN server registration for centralized contact management.

Features:
Local P2P Communication:

Users can engage in real-time messaging within the local network.
Direct connection option for communicating with known IP addresses and ports.
STUN Server Integration:

Utilizes a STUN (Session Traversal Utilities for NAT) server for managing contacts.
Users can register on the STUN server to store and retrieve contact information.
Simplifies the process of connecting with others by centralizing contact details.
Contact Management:

Allows users to save and view contacts through the STUN server.
Retrieval of necessary connection information from the server for establishing connections.


# Code Documentation

## Package: Mainpackage

### 1. Class: Actor

#### Description
The `Actor` class represents the main actor of the system. It contains a `ServerConnection` object to handle the server connection and a `PeerHandler` object to manage peer-to-peer connections.

#### Constructor

'''java
public Actor()

Initializes an instance of `ServerConnection` and `PeerHandler` upon application startup.

#### Methods
- `offlineLogin()`: Sets the main application panel to `PeerJPanel`.
- `getServerConnection()`: Returns the `ServerConnection` object.
- `getPh()`: Returns the `PeerHandler` object.

### 2. Class: ServerConnection

#### Description
The `ServerConnection` class manages the connection to the server and communication with it.

#### Constructor
Initializes a connection to the server by reading the address and port from the configuration file.

#### Methods
- `connect()`: Attempts to establish a connection to the server.
- `tryConnection()`: Tries to connect to the server and starts the listener thread.
- `startServerListener()`: Starts a new thread to listen for responses from the server.
- `login(String nome, String password, String ip, String port)`: Sends a login request to the server.
- `register(String ID, String nome, String password, String IP, String Port)`: Sends a registration request to the server.
- `newContact(String name, String UUID)`: Sends a request to the server to create a new contact.
- `getContact()`: Sends a request to the server to retrieve its contact list.
- `processServerResponse(String serverResponse)`: Processes the response received from the server.
- `sendMsgToServerTracker(String msg)`: Sends a message to the server.
- `closeAfterException()`: Shuts down the connection in case of an exception.
- `serverTrackerOffline()`: Handles the case when the server tracker is offline.
- Other getter methods to retrieve information about the connection.

### 3. Class: PeerHandler

#### Description
The `PeerHandler` class manages peer-to-peer connections and keeps track of the peer list.

#### Constructor
Initializes a `ServerSocket` object and starts the listener thread to accept peer connections.

#### Methods
- `startPeerListener()`: Starts a new thread to listen for incoming connections from other peers.
- `processServerSocketResponse(String msg)`: Processes the message received from other peers.
- `handleConnectionRequest(String info)`: Handles the connection request from another peer.
- `closeAfterException()`: Handles application shutdown in case of an exception.
- Other getter methods to retrieve information about the connection.

### 4. Class: PasswordHasher

#### Description
The `PasswordHasher` class provides a static method for hashing passwords using the SHA-256 algorithm.

#### Methods
- `hashPassword(String password)`: Returns the hash of the provided password.
