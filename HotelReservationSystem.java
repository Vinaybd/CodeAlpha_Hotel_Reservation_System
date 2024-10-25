package Vinay


import java.util.*;


class Room {
    private String type;
    private double price;
    private boolean isAvailable;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
        this.isAvailable = true;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room Type: " + type + ", Price: $" + price + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

class User {
    private String name;
    private String email;
    private String phone;

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }
}
 
class Reservation {
    private User user;
    private Room room;
    private Date bookingDate;

    public Reservation(User user, Room room) {
        this.user = user;
        this.room = room;
        this.bookingDate = new Date(); // Current date and time
    }

    @Override
    public String toString() {
        return "Reservation Details:\n" + user + "\n" + room + "\nBooking Date: " + bookingDate;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        initializeRooms();
    }

    // Initialize rooms with different categories
    private void initializeRooms() {
        rooms.add(new Room("Single", 100));
        rooms.add(new Room("Double", 150));
        rooms.add(new Room("Suite", 300));
    }

    public List<Room> searchAvailableRooms(String type) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(type) && room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void makeReservation(User user, Room room) {
        room.setAvailable(false); // Mark room as unavailable
        Reservation reservation = new Reservation(user, room);
        reservations.add(reservation);
        System.out.println("Reservation Successful!");
        System.out.println(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}

class Payment {
    public static boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your total is: $" + amount);
        System.out.print("Enter payment amount: ");
        double payment = scanner.nextDouble();
        if (payment >= amount) {
            System.out.println("Payment successful! Change: $" + (payment - amount));
            return true;
        } else {
            System.out.println("Payment failed! Insufficient amount.");
            return false;
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n---- Hotel Reservation System ----");
            System.out.println("1. Search for available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. View reservations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String type = scanner.nextLine();
                    List<Room> availableRooms = hotel.searchAvailableRooms(type);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No rooms available of type: " + type);
                    } else {
                        System.out.println("Available rooms:");
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter your phone number: ");
                    String phone = scanner.nextLine();
                    User user = new User(name, email, phone);

                    System.out.print("Enter room type to reserve (Single/Double/Suite): ");
                    String roomType = scanner.nextLine();
                    List<Room> rooms = hotel.searchAvailableRooms(roomType);
                    if (rooms.isEmpty()) {
                        System.out.println("No rooms available of type: " + roomType);
                    } else {
                        Room roomToReserve = rooms.get(0); // Reserve the first available room
                        if (Payment.processPayment(roomToReserve.getPrice())) {
                            hotel.makeReservation(user, roomToReserve);
                        } else {
                            System.out.println("Reservation failed due to payment issue.");
                        }
                    }
                    break;

                case 3:
                    List<Reservation> reservations = hotel.getReservations();
                    if (reservations.isEmpty()) {
                        System.out.println("No reservations found.");
                    } else {
                        System.out.println("Current Reservations:");
                        for (Reservation reservation : reservations) {
                            System.out.println(reservation);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exiting the system...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

