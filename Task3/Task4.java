import java.util.*;

enum BookStatus {
    AVAILABLE, OUT_OF_STOCK
}

enum OrderStatus {
    NEW, COMPLETED, CANCELLED
}

class Book {
    private String title;
    private double price;
    private BookStatus status;

    public Book(String title, double price, BookStatus status) {
        this.title = title;
        this.price = price;
        this.status = status;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public BookStatus getStatus() { return status; }

    public void setStatus(BookStatus status) { this.status = status; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return title + " (" + status + ", " + price + " руб)";
    }
}

class BookRequest {
    private Book book;
    private boolean fulfilled;

    public BookRequest(Book book) {
        this.book = book;
        this.fulfilled = false;
    }

    public Book getBook() { return book; }
    public boolean isFulfilled() { return fulfilled; }
    public void fulfill() { fulfilled = true; }

    @Override
    public String toString() {
        return "Запрос на книгу: " + book.getTitle() + " (" + (fulfilled ? "выполнен" : "ожидает") + ")";
    }
}

class Order {
    private static int nextId = 1;
    private int id;
    private Book book;
    private OrderStatus status;

    public Order(Book book) {
        this.id = nextId++;
        this.book = book;
        this.status = OrderStatus.NEW;
    }

    public int getId() { return id; }
    public Book getBook() { return book; }
    public OrderStatus getStatus() { return status; }

    public void setStatus(OrderStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Заказ #" + id + " (" + book.getTitle() + ", статус: " + status + ")";
    }
}

class BookStore {
    private List<Book> books = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<BookRequest> requests = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Добавлена книга: " + book);
        if (book.getStatus() == BookStatus.AVAILABLE) {
            for (BookRequest req : requests) {
                if (req.getBook().getTitle().equals(book.getTitle()) && !req.isFulfilled()) {
                    req.fulfill();
                    book.setStatus(BookStatus.AVAILABLE);
                    System.out.println("Запрос на книгу \"" + book.getTitle() + "\" выполнен.");
                }
            }
        }
    }

    public void changeBookPrice(String title, double newPrice) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                book.setPrice(newPrice);
                System.out.println("Цена книги \"" + title + "\" изменена на " + newPrice + " руб.");
                return;
            }
        }
        System.out.println("Книга \"" + title + "\" не найдена.");
    }

    public void markBookOutOfStock(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                book.setStatus(BookStatus.OUT_OF_STOCK);
                System.out.println("Книга \"" + title + "\" теперь отсутствует.");
                return;
            }
        }
        System.out.println("Книга \"" + title + "\" не найдена.");
    }

    public void createOrder(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                Order order = new Order(book);
                orders.add(order);
                System.out.println("Создан " + order);
                if (book.getStatus() == BookStatus.OUT_OF_STOCK) {
                    BookRequest req = new BookRequest(book);
                    requests.add(req);
                    System.out.println("Создан " + req);
                }
                return;
            }
        }
        System.out.println("Книга \"" + title + "\" не найдена.");
    }

    public void cancelOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                order.setStatus(OrderStatus.CANCELLED);
                System.out.println("Заказ #" + id + " отменен.");
                return;
            }
        }
        System.out.println("Заказ #" + id + " не найден.");
    }

    public void completeOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                if (order.getBook().getStatus() == BookStatus.AVAILABLE) {
                    order.setStatus(OrderStatus.COMPLETED);
                    System.out.println("Заказ #" + id + " выполнен.");
                } else {
                    System.out.println("Невозможно выполнить заказ #" + id + ", книга отсутствует.");
                }
                return;
            }
        }
        System.out.println("Заказ #" + id + " не найден.");
    }

    public void printAll() {
        System.out.println("\n=== КНИГИ ===");
        for (Book b : books) System.out.println(b);
        System.out.println("\n=== ЗАКАЗЫ ===");
        for (Order o : orders) System.out.println(o);
        System.out.println("\n=== ЗАПРОСЫ ===");
        for (BookRequest r : requests) System.out.println(r);
    }
}

public class Task4 {
    public static void main(String[] args) {
        BookStore store = new BookStore();

        Book b1 = new Book("Java Основы", 500, BookStatus.AVAILABLE);
        Book b2 = new Book("Паттерны проектирования", 700, BookStatus.OUT_OF_STOCK);
        store.addBook(b1);
        store.addBook(b2);

        store.createOrder("Java Основы");
        store.createOrder("Паттерны проектирования");

        store.completeOrder(1);
        store.completeOrder(2);

        store.addBook(new Book("Паттерны проектирования", 700, BookStatus.AVAILABLE));

        store.completeOrder(2);
        store.changeBookPrice("Java Основы", 600);
        store.markBookOutOfStock("Java Основы");
        store.cancelOrder(1);

        store.printAll();
    }
}