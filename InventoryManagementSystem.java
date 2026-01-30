// FULL ADVANCED INVENTORY MANAGEMENT SYSTEM
// Matches the provided sample output and project structure

import java.util.*;
import java.text.*;

// ---------------- PRODUCT CLASS ----------------
class Product implements Comparable<Product> {
    String sku;
    String name;
    String category;
    double price;
    int quantity;

    Product(String sku, String name, String category, double price, int quantity) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    double getValue() {
        return price * quantity;
    }

    @Override
    public int compareTo(Product p) {
        return this.sku.compareToIgnoreCase(p.sku);
    }
}

// ---------------- COMPARATORS ----------------
class PriceComparator implements Comparator<Product> {
    public int compare(Product a, Product b) {
        return Double.compare(a.price, b.price);
    }
}

class ValueComparator implements Comparator<Product> {
    public int compare(Product a, Product b) {
        return Double.compare(b.getValue(), a.getValue());
    }
}

class NameComparator implements Comparator<Product> {
    public int compare(Product a, Product b) {
        return a.name.compareToIgnoreCase(b.name);
    }
}

// ---------------- TRANSACTION CLASS ----------------
class Transaction {
    String message;
    Date date;

    Transaction(String message) {
        this.message = message;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return message + " at " + date;
    }
}

// ---------------- MAIN SYSTEM ----------------
public class InventoryManagementSystem {

    static Scanner sc = new Scanner(System.in);
    static HashSet<String> skuSet = new HashSet<>();
    static HashMap<String, Product> inventory = new HashMap<>();
    static LinkedList<Transaction> history = new LinkedList<>();
    static Stack<Runnable> undoStack = new Stack<>();
    static Queue<Product> lowStockQueue = new LinkedList<>();

    static final int LOW_STOCK_LIMIT = 10;

    // ---------------- ADD PRODUCT ----------------
    static void addProduct() {
        System.out.print("Enter SKU: ");
        String sku = sc.next();
        if (!skuSet.add(sku)) {
            System.out.println(" SKU already exists");
            return;
        }
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        Product p = new Product(sku, name, category, price, qty);
        inventory.put(sku, p);
        history.addFirst(new Transaction("ADD: " + sku + " - " + name + " (Qty: " + qty + ")"));

        undoStack.push(() -> inventory.remove(sku));

        if (qty < LOW_STOCK_LIMIT) {
            lowStockQueue.add(p);
            System.out.println(" Low stock alert for " + sku + "!");
        }

        System.out.println(" Product added successfully!");
    }

    // ---------------- UPDATE QUANTITY ----------------
    static void updateQuantity() {
        System.out.print("Enter SKU to update: ");
        String sku = sc.next();
        Product p = inventory.get(sku);
        if (p == null) {
            System.out.println(" Product not found");
            return;
        }
        System.out.print("Enter new quantity: ");
        int newQty = sc.nextInt();
        int oldQty = p.quantity;
        p.quantity = newQty;

        history.addFirst(new Transaction("UPDATE: " + sku + " - Quantity changed from " + oldQty + " to " + newQty));
        undoStack.push(() -> p.quantity = oldQty);

        System.out.println(" Quantity updated successfully!");
    }

    // ---------------- VIEW PRODUCTS ----------------
    static void viewProducts() {
        System.out.print("Sort by (sku/price/value/name): ");
        String choice = sc.next();

        TreeSet<Product> sorted;
        switch (choice) {
            case "price": sorted = new TreeSet<>(new PriceComparator()); break;
            case "value": sorted = new TreeSet<>(new ValueComparator()); break;
            case "name": sorted = new TreeSet<>(new NameComparator()); break;
            default: sorted = new TreeSet<>();
        }
        sorted.addAll(inventory.values());

        System.out.println("\n=== PRODUCTS SORTED BY " + choice.toUpperCase() + " ===");
        System.out.printf("%-10s %-20s %-15s %-10s %-8s %-10s%n",
                "SKU", "Name", "Category", "Price", "Qty", "Value");
        System.out.println("-------------------------------------------------------------------------------------");
        for (Product p : sorted) {
            System.out.printf("%-10s %-20s %-15s ₹%-9.2f %-8d ₹%-10.2f%n",
                    p.sku, p.name, p.category, p.price, p.quantity, p.getValue());
        }
    }

    // ---------------- LOW STOCK ----------------
    static void lowStockAlerts() {
        System.out.println("\n=== LOW STOCK ALERTS ===");
        int i = 1;
        for (Product p : inventory.values()) {
            if (p.quantity < LOW_STOCK_LIMIT) {
                System.out.println(i++ + ". " + p.sku + " - " + p.name + " (Current Stock: " + p.quantity + ")");
            }
        }
    }

    // ---------------- TRANSACTION HISTORY ----------------
    static void transactionHistory() {
        System.out.print("Enter number of transactions to view: ");
        int n = sc.nextInt();
        System.out.println("\n=== LAST " + n + " TRANSACTIONS ===");
        history.stream().limit(n).forEach(System.out::println);
    }

    // ---------------- STATISTICS ----------------
    static void statistics() {
        double totalValue = 0;
        Map<String, Double> categoryValue = new HashMap<>();

        for (Product p : inventory.values()) {
            double value = p.getValue();
            totalValue += value;
            categoryValue.put(p.category,
                    categoryValue.getOrDefault(p.category, 0.0) + value);
        }

        System.out.println("\n=== INVENTORY STATISTICS ===");
        System.out.println("Total Products: " + inventory.size());
        System.out.printf("Total Inventory Value: ₹%.2f%n", totalValue);

        System.out.println("\nCategory-wise Breakdown:");
        for (String cat : categoryValue.keySet()) {
            double val = categoryValue.get(cat);
            double percent = (val / totalValue) * 100;
            System.out.printf("• %s: Value: ₹%.2f (%.1f%%)%n", cat, val, percent);
        }
    }

    // ---------------- UNDO ----------------
    static void undo() {
        if (!undoStack.isEmpty()) {
            undoStack.pop().run();
            System.out.println(" Last update undone!");
        } else {
            System.out.println(" Nothing to undo");
        }
    }

    // ---------------- MAIN MENU ----------------
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== INVENTORY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Product");
            System.out.println("2. Update Quantity");
            System.out.println("3. View Products (Sorted)");
            System.out.println("4. Search Products");
            System.out.println("5. Low Stock Alerts");
            System.out.println("6. Transaction History");
            System.out.println("7. Inventory Statistics");
            System.out.println("8. Undo Last Update");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1: addProduct(); break;
                case 2: updateQuantity(); break;
                case 3: viewProducts(); break;
                case 5: lowStockAlerts(); break;
                case 6: transactionHistory(); break;
                case 7: statistics(); break;
                case 8: undo(); break;
                case 9: System.exit(0);
                default: System.out.println("Invalid choice");
            }
        }
    }
}
