 Inventory Management System (Java)

A menu-driven **Inventory Management System** developed in **Java** using the **Collections Framework**.  
This project demonstrates efficient inventory handling using multiple data structures such as `HashSet`, `TreeSet`, `LinkedList`, `Stack`, and `Queue`.

---

## 📌 Features
- Add and manage products with unique SKU
- Update product quantity
- View products with multiple sorting options
- Low stock alert system
- Transaction history tracking
- Inventory statistics (total value & category-wise breakdown)
- Undo last update operation
- Menu-driven console interface

---

## 🛠️ Technologies Used
- Java (Core Java)
- Java Collections Framework
- OOP Concepts

---

## 🧩 Data Structures Used
| Data Structure | Purpose |
|---------------|--------|
| HashSet | Ensure unique product SKU |
| HashMap | Store inventory items |
| TreeSet | Sorted product display |
| LinkedList | Transaction history |
| Stack | Undo last update |
| Queue | Low stock alerts |
| Comparable | Natural sorting |
| Comparator | Custom sorting (price, value, name) |

---

## ▶️ How to Run
1. Clone the repository
   ```bash
   git clone https://github.com/your-username/Inventory-Management-System-Java.git
Navigate to project folder

bash

cd Inventory-Management-System-Java
Compile the program

bash

javac InventoryManagementSystem.java
Run the program

bash
Copy code
java InventoryManagementSystem
📂 Project Structure
pgsql
Copy code
InventoryManagementSystem.java   // Main class
Product.java                    // Product model (Comparable)
Transaction.java                // Transaction record
comparators/
 ├── PriceComparator.java
 ├── ValueComparator.java
 └── NameComparator.java


 
🎓 Academic Use
BCA / BSc / MCA Mini Project

Java Practicals

Demonstrates Java Collections and OOP concepts

👨‍💻 Author
Satyam Sharma
BCA Student
