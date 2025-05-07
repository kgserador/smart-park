"SmartPark" is a technology company developing an intelligent parking management system for urban areas. The company is aiming to optimize the use of parking spaces and facilitate easy navigation for drivers.
A Parking Lot has:
•Lot ID (Unique identifier, 50 characters max)
•Location
•Capacity (Total number of parking spaces)
•Occupied Spaces (Number of currently occupied spaces)
•Cost Per Minute

Each Vehicle has:
•License Plate (Unique identifier, allowed only letters, numbers, and dashes)
•Type (Car, Motorcycle, Truck)
•Owner Name (Letters and spaces only)

Develop a service exposing a REST API which allows clients to communicate with the parking management system.
The service should be capable of doing,
•Authenticate using a username and password to generate token
•Registering a parking lot
•Registering a vehicle
•Checking in a vehicle to a parking lot
•Checking out a vehicle from a parking lot and display parking cost
•Viewing current occupancy and availability of a parking lot



When trying to run the project, you have to mvn clean install it inside the parent directory.
Then when there is a target folder is created and there is a jar build for the project,

You may run the jar by:

Java -jar <path-of-the-jar-file>
Then enter on terminal/cmd

Once the jar file is running, you may import the collection of postan then try to execute each request to test it.

Please check the properties file or yml file of the application for the port of server

Or you may run this inside of IDE as an alternative

To begin with the application, you have to sign up a user first then login with the username and password you signed up with. Use the token generated upon login for other api services related to smart park, as a bearer token authentication.
