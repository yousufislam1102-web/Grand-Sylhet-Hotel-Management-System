1. Project Title
Grand Sylhet Hotel Management System

2. Description
A GUI-based Java application designed to manage hotel bookings and guest records. The system allows users to Register and Login to a dashboard where they can Add, Edit, Update, and Delete guest information in real-time.

3. Features
User Authentication: Secure Login and Signup frames for staff access.

CRUD Operations: Full ability to Create, Read, Update, and Delete guest records.

Database Integration: Connects to a MySQL database using JDBC.

Professional UI: Large, high-visibility text and centered windows for a professional look.

Dynamic Table: Real-time data display using JTable.

4. Technologies Used
Language: Java

GUI Framework: Java Swing & AWT

Database: MySQL

Driver: MySQL Connector/J (JDBC)

Environment: Linux (Chromebook/Crostini)

5. Prerequisites
To run this project, GitHub users will need to know they must have:

Java Development Kit (JDK) installed.

MySQL Server (such as XAMPP or native Linux MySQL).

MySQL Connector Jar file added to the project build path.

6. Database Setup
You should provide the SQL structure so others can recreate your database:

Database Name: hotel_db

Table 1 (users): Fields for username, email, phone, and password.

Table 2 (bookings): Fields for id, guest_name, room_number, room_type, status, and price.

7. How to Run
Clone the repository.

Start your MySQL service.

Import the database schema.

Compile and run Login.java to start the application.
