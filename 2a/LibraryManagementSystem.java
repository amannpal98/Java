import java.util.*;

abstract class LibraryItem {
    protected String title;
    protected String itemID;
    protected boolean isAvailable;

    public LibraryItem(String title, String itemID) {
        this.title = title;
        this.itemID = itemID;
        this.isAvailable = true;
    }

    public abstract void borrow() throws ItemNotAvailableException;

    public abstract void returnItem();

    public String getTitle() {
        return title;
    }

    public String getItemID() {
        return itemID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}

class Book extends LibraryItem {
    private String author;
    private String genre;

    public Book(String title, String itemID, String author, String genre) {
        super(title, itemID);
        this.author = author;
        this.genre = genre;
    }

    @Override
    public void borrow() throws ItemNotAvailableException {
        if (!isAvailable) {
            throw new ItemNotAvailableException("Book '" + title + "' is not available.");
        }
        isAvailable = false;
        System.out.println("Book '" + title + "' borrowed successfully.");
    }

    @Override
    public void returnItem() {
        isAvailable = true;
        System.out.println("Book '" + title + "' returned successfully.");
    }

    @Override
    public String toString() {
        return "Book [Title=" + title + ", ID=" + itemID + ", Author=" + author + ", Genre=" + genre + ", Available=" + isAvailable + "]";
    }
}

class DVD extends LibraryItem {
    private String director;
    private double duration;

    public DVD(String title, String itemID, String director, double duration) {
        super(title, itemID);
        this.director = director;
        this.duration = duration;
    }

    @Override
    public void borrow() throws ItemNotAvailableException {
        if (!isAvailable) {
            throw new ItemNotAvailableException("DVD '" + title + "' is not available.");
        }
        isAvailable = false;
        System.out.println("DVD '" + title + "' borrowed successfully.");
    }

    @Override
    public void returnItem() {
        isAvailable = true;
        System.out.println("DVD '" + title + "' returned successfully.");
    }

    @Override
    public String toString() {
        return "DVD [Title=" + title + ", ID=" + itemID + ", Director=" + director + ", Duration=" + duration + " hrs, Available=" + isAvailable + "]";
    }
}

class ItemNotAvailableException extends Exception {
    public ItemNotAvailableException(String message) {
        super(message);
    }
}

class Library {
    private List<LibraryItem> items = new ArrayList<>();

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void search(String title) {
        System.out.println("Searching for items with title: " + title);
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                System.out.println(item);
            }
        }
    }

    public void searchByID(String itemID) {
        System.out.println("Searching for item with ID: " + itemID);
        for (LibraryItem item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                System.out.println(item);
            }
        }
    }

    public void displayItemsByType() {
        System.out.println("Books:");
        for (LibraryItem item : items) {
            if (item instanceof Book) {
                System.out.println(item);
            }
        }
        System.out.println("DVDs:");
        for (LibraryItem item : items) {
            if (item instanceof DVD) {
                System.out.println(item);
            }
        }
    }

    public void borrowItem(String itemID) {
        try {
            for (LibraryItem item : items) {
                if (item.getItemID().equalsIgnoreCase(itemID)) {
                    item.borrow();
                    return;
                }
            }
            System.out.println("Item with ID " + itemID + " not found.");
        } catch (ItemNotAvailableException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Borrow operation completed.");
        }
    }

    public void returnItem(String itemID) {
        for (LibraryItem item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                item.returnItem();
                return;
            }
        }
        System.out.println("Item with ID " + itemID + " not found.");
        System.out.println("Return operation completed.");
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book("The Great Gatsby", "B001", "F. Scott Fitzgerald", "Fiction");
        Book book2 = new Book("1984", "B002", "George Orwell", "Dystopian");
        DVD dvd1 = new DVD("Inception", "D001", "Christopher Nolan", 2.5);
        DVD dvd2 = new DVD("The Matrix", "D002", "The Wachowskis", 2.0);

        library.addItem(book1);
        library.addItem(book2);
        library.addItem(dvd1);
        library.addItem(dvd2);

        library.displayItemsByType();

        library.borrowItem("B001");
        library.borrowItem("B001");

        library.returnItem("B001");
        library.returnItem("D003");

        library.search("1984");
        library.searchByID("D002");
    }
}
