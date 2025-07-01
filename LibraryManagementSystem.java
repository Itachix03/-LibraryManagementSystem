import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.run();
    }

    public void run() {
        int choice;
        do {
            showMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> addUser();
                    case 3 -> issueBook();
                    case 4 -> returnBook();
                    case 5 -> listBooks();
                    case 6 -> listUsers();
                    case 7 -> System.out.println("Exiting the system. Goodbye!");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = 0;
            }
        } while (choice != 7);
    }

    private void showMenu() {
        System.out.println("\n--- Library Management System Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. Add User");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. List All Books");
        System.out.println("6. List All Users");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    // --- Core Features ---
    private void addBook() {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();

        books.add(new Book(id, title, author));
        System.out.println("Book added successfully.");
    }

    private void addUser() {
        System.out.print("Enter User ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();

        users.add(new User(id, name));
        System.out.println("User added successfully.");
    }

    private void issueBook() {
        System.out.print("Enter Book ID to issue: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter User ID to issue to: ");
        String userId = scanner.nextLine();

        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book == null || user == null) {
            System.out.println("Invalid book or user ID.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is already issued.");
            return;
        }

        book.setAvailable(false);
        user.borrowBook(book);
        System.out.println("Book issued successfully.");
    }

    private void returnBook() {
        System.out.print("Enter Book ID to return: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book == null || user == null) {
            System.out.println("Invalid book or user ID.");
            return;
        }

        if (!user.getBorrowedBooks().contains(book)) {
            System.out.println("User has not borrowed this book.");
            return;
        }

        book.setAvailable(true);
        user.returnBook(book);
        System.out.println("Book returned successfully.");
    }

    private void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("\n--- Book List ---");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void listUsers() {
        if (users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            System.out.println("\n--- User List ---");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    private Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equalsIgnoreCase(id)) {
                return book;
            }
        }
        return null;
    }

    private User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(id)) {
                return user;
            }
        }
        return null;
    }
}

// --- Book Class ---
class Book {
    private String id, title, author;
    private boolean isAvailable;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return id + " - " + title + " by " + author + " [" + (isAvailable ? "Available" : "Issued") + "]";
    }
}

// --- User Class ---
class User {
    private String id, name;
    private ArrayList<Book> borrowedBooks;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public ArrayList<Book> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return id + " - " + name + " (Borrowed: " + borrowedBooks.size() + ")";
    }
}
