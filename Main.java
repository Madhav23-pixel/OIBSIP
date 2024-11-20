import java.util.*;

public class Main {

    // In-memory lists to simulate database tables
    private static final List<User> users = new ArrayList<>();
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final List<String> availableTrains = Arrays.asList("Train A", "Train B", "Train C");

    // Simulating User login
    public static boolean login(String username, String password) {
        for (User user : users) {
            if (user.username().equals(username) && user.password().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Add a new user for login (for testing purposes)
    public static void addUser(String username, String password) {
        users.add(new User(username, password));
    }

    // Reservation Form: User inputs reservation details
    public static void makeReservation(String username, String name, String trainNumber, String trainName, String classType,
                                       String dateOfJourney, String fromStation, String toStation) {
        // Generate a unique PNR number and create reservation
        Reservation newReservation = new Reservation(username, name, trainNumber, trainName, classType, dateOfJourney, fromStation, toStation);
        reservations.add(newReservation);
        System.out.println("Reservation successfully made.");
    }

    // Cancellation Form: User enters PNR number to cancel the reservation
    public static void cancelReservation(int pnrNumber) {
        for (Iterator<Reservation> iterator = reservations.iterator(); iterator.hasNext();) {
            Reservation reservation = iterator.next();
            if (reservation.getPnrNumber() == pnrNumber) {
                System.out.println("Reservation Details:");
                System.out.println(reservation);
                // Confirm cancellation
                System.out.println("Do you want to cancel this reservation? (yes/no)");
                Scanner sc = new Scanner(System.in);
                String confirmation = sc.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    iterator.remove();  // Remove reservation from list
                    System.out.println("Reservation successfully cancelled.");
                    return;
                }
            }
        }
        System.out.println("No reservation found with PNR: " + pnrNumber);
    }

    // Main method: Login, Reservation, and Cancellation Workflow
    public static void main(String[] args) {
        // Add test users for login
        addUser("testUser", "testPassword");

        Scanner sc = new Scanner(System.in);

        // Login Form
        System.out.println("=== Login ===");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (login(username, password)) {
            System.out.println("Login successful.\n");

            // Main Menu for Reservation and Cancellation
            boolean continueProgram = true;
            while (continueProgram) {
                System.out.println("=== Main Menu ===");
                System.out.println("1. Make a Reservation");
                System.out.println("2. Cancel Reservation");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int option = sc.nextInt();
                sc.nextLine(); // Consume newline character

                switch (option) {
                    case 1:
                        // Reservation Form
                        System.out.println("\n=== Reservation Form ===");
                        System.out.print("Enter your full name: ");
                        String name = sc.nextLine();
                        System.out.println("Available Trains: " + availableTrains);
                        System.out.print("Enter train name from the available list: ");
                        String trainName = sc.nextLine();
                        System.out.print("Enter train number: ");
                        String trainNumber = sc.nextLine();
                        System.out.print("Enter class type (e.g., AC, Sleeper): ");
                        String classType = sc.nextLine();
                        System.out.print("Enter date of journey (YYYY-MM-DD): ");
                        String dateOfJourney = sc.nextLine();
                        System.out.print("Enter from station: ");
                        String fromStation = sc.nextLine();
                        System.out.print("Enter to station: ");
                        String toStation = sc.nextLine();
                        makeReservation(username, name, trainNumber, trainName, classType, dateOfJourney, fromStation, toStation);
                        break;

                    case 2:
                        // Cancellation Form
                        System.out.println("\n=== Cancellation Form ===");
                        System.out.print("Enter your PNR number: ");
                        int pnr = sc.nextInt();
                        cancelReservation(pnr);
                        break;

                    case 3:
                        // Exit the program
                        continueProgram = false;
                        System.out.println("Exiting the system...");
                        break;

                    default:
                        System.out.println("Invalid option, please try again.");
                }
            }

        } else {
            System.out.println("Invalid login credentials. Exiting...");
        }

        sc.close();  // Close the scanner
    }

    // User class to store user credentials
    record User(String username, String password) {
    }

    // Reservation class to store reservation details
    static class Reservation {
        private static int counter = 1000;  // Static counter for generating unique PNR numbers
        private final int pnrNumber;
        private final String username;
        private final String name;
        private final String trainNumber;
        private final String trainName;
        private final String classType;
        private final String dateOfJourney;
        private final String fromStation;
        private final String toStation;

        public Reservation(String username, String name, String trainNumber, String trainName, String classType, String dateOfJourney, String fromStation, String toStation) {
            this.pnrNumber = counter++;
            this.username = username;
            this.name = name;
            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.classType = classType;
            this.dateOfJourney = dateOfJourney;
            this.fromStation = fromStation;
            this.toStation = toStation;
        }

        public int getPnrNumber() {
            return pnrNumber;
        }

        @Override
        public String toString() {
            return "PNR: " + pnrNumber + "\n" +
                    "Username: " + username + "\n" +
                    "Name: " + name + "\n" +
                    "Train Number: " + trainNumber + "\n" +
                    "Train Name: " + trainName + "\n" +
                    "Class Type: " + classType + "\n" +
                    "Date of Journey: " + dateOfJourney + "\n" +
                    "From: " + fromStation + "\n" +
                    "To: " + toStation;
        }
    }
}