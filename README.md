# Hotel Reservation System

Welcome to the **Hotel Reservation System**, a simple yet powerful Java-based application that allows users to book, update, and manage hotel reservations seamlessly.

## 🚀 Features
- **New Reservation** – Book a room with guest details.
- **Check Reservations** – View all current reservations.
- **Get Room Number** – Retrieve room details using a reservation ID.
- **Update Reservation** – Modify existing booking details.
- **Delete Reservation** – Cancel a reservation.
- 
### Screenshot
echo -e "\n\n### Screenshot:\n![Hotel Reservation System](Screenshot%202025-02-14%20184636.png)" >> README.md

## 🛠️ Tech Stack
- **Java** – Core programming language
- **MySQL** – Database for storing reservations
- **JDBC** – Database connectivity

## 🔧 Setup Instructions
### 1️⃣ Prerequisites
Ensure you have the following installed:
- Java (JDK 8+)
- MySQL Server
- MySQL JDBC Driver

### 2️⃣ Database Setup
1. Create a MySQL database named `hotel_db`.
2. Run the following SQL command to create the `reserve` table:

```sql
CREATE TABLE reserve (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(255) NOT NULL,
    room_no INT NOT NULL,
    contact_no VARCHAR(15) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3️⃣ Running the Application
1. Clone this repository:
   ```sh
   git clone https://github.com/yourusername/hotel-reservation.git
   ```
2. Open the project in your preferred Java IDE.
3. Update the database credentials in `Hotel.java`:
   ```java
   private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
   private static final String username = "root";
   private static final String password = "yourpassword";
   ```
4. Compile and run the project.

## 📌 Usage Guide
Once the program runs, you will be presented with a menu:
```
1. New Reservation
2. Check Reservation
3. Get your Room Number
4. Update Reservation
5. Delete Reservation
0. Quit
```
Simply enter the corresponding number to perform an action.

## ⚡ Future Enhancements
- GUI-based interface using JavaFX
- Online booking system
- Payment gateway integration

## 📝 License
This project is open-source and available under the MIT License.

📌 **Contributions are welcome!** Feel free to fork and submit pull requests. 🎉

