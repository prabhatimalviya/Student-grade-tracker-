import java.util.ArrayList;
import java.util.Scanner;

// Room class
class Room {
    int roomNumber;
    String roomType;
    double price;
    boolean available;

    Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.available = true;
    }

    void displayRoom() {
        System.out.printf("%-10d %-15s ₹%-12.2f %-10s%n",
                roomNumber, roomType, price,
                available ? "Available" : "Booked");
    }
}

// Reservation class
class Reservation {
    String customerName;
    String phoneNumber;
    int roomNumber;
    String roomType;
    int nights;
    double totalAmount;
    boolean paymentDone;

    Reservation(String customerName, String phoneNumber,
                int roomNumber, String roomType,
                int nights, double totalAmount) {

        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.paymentDone = false;
    }

    void displayReservation() {
        System.out.println("\n----------- BOOKING DETAILS -----------");
        System.out.println("Customer Name : " + customerName);
        System.out.println("Phone Number   : " + phoneNumber);
        System.out.println("Room Number    : " + roomNumber);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Number of Nights: " + nights);
        System.out.printf("Total Amount   : ₹%.2f%n", totalAmount);
        System.out.println("Payment Status : "
                + (paymentDone ? "Paid" : "Pending"));
        System.out.println("---------------------------------------");
    }
}

// Main class
public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {

        addRooms();

        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("       HOTEL RESERVATION SYSTEM");
            System.out.println("========================================");

            System.out.println("1. View Available Rooms");
            System.out.println("2. Search Rooms");
            System.out.println("3. Book a Room");
            System.out.println("4. View Booking Details");
            System.out.println("5. Cancel Reservation");
            System.out.println("6. Make Payment");
            System.out.println("7. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    viewAvailableRooms();
                    break;

                case 2:
                    searchRooms();
                    break;

                case 3:
                    bookRoom();
                    break;

                case 4:
                    viewBookingDetails();
                    break;

                case 5:
                    cancelReservation();
                    break;

                case 6:
                    makePayment();
                    break;

                case 7:
                    System.out.println("\nThank you for using the Hotel Reservation System!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 7);

        sc.close();
    }

    // Add rooms
    static void addRooms() {

        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));
        rooms.add(new Room(103, "Standard", 2000));

        rooms.add(new Room(201, "Deluxe", 3500));
        rooms.add(new Room(202, "Deluxe", 3500));
        rooms.add(new Room(203, "Deluxe", 3500));

        rooms.add(new Room(301, "Suite", 5000));
        rooms.add(new Room(302, "Suite", 5000));
    }

    // Display available rooms
    static void viewAvailableRooms() {

        System.out.println("\n------------- AVAILABLE ROOMS -------------");

        System.out.printf("%-10s %-15s %-12s %-10s%n",
                "Room No.", "Room Type", "Price/Night", "Status");

        System.out.println("-------------------------------------------");

        boolean found = false;

        for (Room room : rooms) {

            if (room.available) {
                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
    }

    // Search rooms by type
    static void searchRooms() {

        System.out.println("\nRoom Types:");
        System.out.println("1. Standard");
        System.out.println("2. Deluxe");
        System.out.println("3. Suite");

        System.out.print("Enter room type: ");
        String type = sc.nextLine();

        boolean found = false;

        System.out.println("\n------------- SEARCH RESULTS -------------");

        for (Room room : rooms) {

            if (room.roomType.equalsIgnoreCase(type)
                    && room.available) {

                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available rooms found for this type.");
        }
    }

    // Book room
    static void bookRoom() {

        viewAvailableRooms();

        System.out.print("\nEnter room number to book: ");
        int roomNumber = sc.nextInt();
        sc.nextLine();

        Room selectedRoom = findRoom(roomNumber);

        if (selectedRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        if (!selectedRoom.available) {
            System.out.println("This room is already booked.");
            return;
        }

        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        System.out.print("Enter number of nights: ");
        int nights = sc.nextInt();

        if (nights <= 0) {
            System.out.println("Invalid number of nights.");
            return;
        }

        double totalAmount = selectedRoom.price * nights;

        Reservation reservation = new Reservation(
                name,
                phone,
                selectedRoom.roomNumber,
                selectedRoom.roomType,
                nights,
                totalAmount
        );

        reservations.add(reservation);

        selectedRoom.available = false;

        System.out.println("\n===== ROOM BOOKED SUCCESSFULLY =====");
        System.out.println("Customer Name : " + name);
        System.out.println("Room Number   : " + selectedRoom.roomNumber);
        System.out.println("Room Type     : " + selectedRoom.roomType);
        System.out.println("Nights        : " + nights);
        System.out.printf("Total Amount  : ₹%.2f%n", totalAmount);
        System.out.println("Payment Status: Pending");
    }

    // View booking details
    static void viewBookingDetails() {

        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations found.");
            return;
        }

        System.out.print("\nEnter room number: ");
        int roomNumber = sc.nextInt();

        Reservation reservation = findReservation(roomNumber);

        if (reservation == null) {
            System.out.println("No reservation found for this room.");
        } else {
            reservation.displayReservation();
        }
    }

    // Cancel reservation
    static void cancelReservation() {

        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations available to cancel.");
            return;
        }

        System.out.print("\nEnter room number to cancel: ");
        int roomNumber = sc.nextInt();

        Reservation reservation = findReservation(roomNumber);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        Room room = findRoom(roomNumber);

        if (room != null) {
            room.available = true;
        }

        reservations.remove(reservation);

        System.out.println("\nReservation cancelled successfully.");
        System.out.println("Room " + roomNumber + " is now available.");
    }

    // Make payment
    static void makePayment() {

        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations found.");
            return;
        }

        System.out.print("\nEnter room number for payment: ");
        int roomNumber = sc.nextInt();

        Reservation reservation = findReservation(roomNumber);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        if (reservation.paymentDone) {
            System.out.println("Payment has already been completed.");
            return;
        }

        System.out.println("\n----------- PAYMENT -----------");
        System.out.printf("Amount to Pay: ₹%.2f%n",
                reservation.totalAmount);

        System.out.println("1. UPI");
        System.out.println("2. Debit/Credit Card");
        System.out.println("3. Cash");

        System.out.print("Select payment method: ");
        int paymentChoice = sc.nextInt();

        if (paymentChoice >= 1 && paymentChoice <= 3) {

            reservation.paymentDone = true;

            System.out.println("\nPayment successful!");
            System.out.printf("Amount Paid: ₹%.2f%n",
                    reservation.totalAmount);

        } else {
            System.out.println("Invalid payment method.");
        }
    }

    // Find a room
    static Room findRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.roomNumber == roomNumber) {
                return room;
            }
        }

        return null;
    }

    // Find reservation
    static Reservation findReservation(int roomNumber) {

        for (Reservation reservation : reservations) {

            if (reservation.roomNumber == roomNumber) {
                return reservation;
            }
        }

        return null;
    }
}
