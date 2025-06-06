import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirlineReservation {

    // Static variables
    static String[] passengers;
    static int planeRows;
    static int firstClassRows;
    static int businessClassRows;

    // Static final arrays for travel classes and commands
    private static final String[] CLASS_LIST = {"F", "B", "E"};
    private static final String[] CLASS_FULLNAME_LIST = {"First Class", "Business Class", "Economy Class"};
    private static final String[] COMMANDS_LIST = {"book", "cancel", "lookup", "availabletickets", "upgrade", "print", "exit"};

    // Constants
    private static final int FIRST_CLASS = 0;
    private static final int BUSINESS_CLASS = 1;
    private static final int ECONOMY_CLASS = 2;

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java AirlineReservation <filename>");
            return;
        }
        initPassengers(args[0]);

        // Command line interface
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("exit")) {
                break;
            }
            processCommand(command);
        }
        scanner.close();
    }

    private static void processCommand(String command) {
        String[] parts = command.split(" ");
        switch (parts[0].toLowerCase()) {
            case "book":
                if (parts.length == 3) {
                    if (isNumeric(parts[1])) {
                        int row = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        boolean success = book(row, name);
                        if (success) {
                            System.out.println("Booked passenger " + name + " successfully.");
                        } else {
                            System.out.println("Could not book passenger " + name + ".");
                        }
                    } else {
                        int travelClass = getClassFromString(parts[1]);
                        String name = parts[2];
                        boolean success = book(name, travelClass);
                        if (success) {
                            System.out.println("Booked passenger " + name + " successfully.");
                        } else {
                            System.out.println("Could not book passenger " + name + ".");
                        }
                    }
                }
                break;
            case "cancel":
                if (parts.length == 2) {
                    String name = parts[1];
                    boolean success = cancel(name);
                    if (success) {
                        System.out.println("Canceled passenger " + name + "'s booking successfully.");
                    } else {
                        System.out.println("Could not cancel booking for " + name + ".");
                    }
                }
                break;
            case "lookup":
                if (parts.length == 2) {
                    String name = parts[1];
                    int row = lookUp(name);
                    if (row != -1) {
                        System.out.println(name + " is seated at row " + row + ".");
                    } else {
                        System.out.println(name + " not found.");
                    }
                }
                break;
            case "availabletickets":
                int[] tickets = availableTickets();
                System.out.println("First Class: " + tickets[FIRST_CLASS]);
                System.out.println("Business Class: " + tickets[BUSINESS_CLASS]);
                System.out.println("Economy Class: " + tickets[ECONOMY_CLASS]);
                break;
            case "upgrade":
                if (parts.length == 3) {
                    String classStr = parts[1];
                    String name = parts[2];
                    int upgradeClass = getClassFromString(classStr);
                    boolean success = upgrade(name, upgradeClass);
                    if (success) {
                        System.out.println("Upgraded passenger " + name + " successfully.");
                    } else {
                        System.out.println("Could not upgrade passenger " + name + ".");
                    }
                }
                break;
            case "print":
                printPlane();
                break;
            default:
                System.out.println("Unknown command.");
        }
    }

    private static int getClassFromString(String classStr) {
        switch (classStr.toUpperCase()) {
            case "F":
                return FIRST_CLASS;
            case "B":
                return BUSINESS_CLASS;
            case "E":
                return ECONOMY_CLASS;
            default:
                return -1;
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void initPassengers(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        if (scanner.hasNextLine()) {
            String[] initialLine = scanner.nextLine().split(",");
            planeRows = Integer.parseInt(initialLine[0].trim());
            firstClassRows = Integer.parseInt(initialLine[1].trim());
            businessClassRows = Integer.parseInt(initialLine[2].trim());
            passengers = new String[planeRows];
        }
        while (scanner.hasNextLine()) {
            String[] bookingLine = scanner.nextLine().split(",");
            int row = Integer.parseInt(bookingLine[0].trim());
            String passengerName = bookingLine[1].trim();
            passengers[row] = passengerName;
        }
        scanner.close();
    }

    private static int findClass(int row) {
        if (row < 0 || row >= planeRows) {
            return -1;
        }
        if (row < firstClassRows) {
            return FIRST_CLASS;
        }
        if (row < firstClassRows + businessClassRows) {
            return BUSINESS_CLASS;
        }
        return ECONOMY_CLASS;
    }

    private static int findFirstRow(int travelClass) {
        switch (travelClass) {
            case FIRST_CLASS:
                return 0;
            case BUSINESS_CLASS:
                return firstClassRows;
            case ECONOMY_CLASS:
                return firstClassRows + businessClassRows;
            default:
                return -1;
        }
    }

    private static int findLastRow(int travelClass) {
        switch (travelClass) {
            case FIRST_CLASS:
                return firstClassRows - 1;
            case BUSINESS_CLASS:
                return firstClassRows + businessClassRows - 1;
            case ECONOMY_CLASS:
                return planeRows - 1;
            default:
                return -1;
        }
    }

    public static boolean book(String passengerName, int travelClass) {
        if (passengerName == null) {
            return false;
        }
        int startRow = findFirstRow(travelClass);
        int endRow = findLastRow(travelClass);
        for (int i = startRow; i <= endRow; i++) {
            if (passengers[i] == null) {
                passengers[i] = passengerName;
                return true;
            }
        }
        return false;
    }

    public static boolean book(int row, String passengerName) {
        if (passengerName == null) {
            return false;
        }
        if (passengers[row] == null) {
            passengers[row] = passengerName;
            return true;
        }
        int travelClass = findClass(row);
        int startRow = findFirstRow(travelClass);
        int endRow = findLastRow(travelClass);
        for (int i = startRow; i <= endRow; i++) {
            if (passengers[i] == null) {
                passengers[i] = passengerName;
                return true;
            }
        }
        return false;
    }

    public static boolean cancel(String passengerName) {
        if (passengerName == null) {
            return false;
        }
        for (int i = 0; i < planeRows; i++) {
            if (passengers[i] != null && passengers[i].equals(passengerName)) {
                passengers[i] = null;
                return true;
            }
        }
        return false;
    }

    public static int lookUp(String passengerName) {
        if (passengerName == null) {
            return -1;
        }
        for (int i = 0; i < planeRows; i++) {
            if (passengers[i] != null && passengers[i].equals(passengerName)) {
                return i;
            }
        }
        return -1;
    }

    public static int[] availableTickets() {
        int[] available = new int[3];
        available[FIRST_CLASS] = 0;
        available[BUSINESS_CLASS] = 0;
        available[ECONOMY_CLASS] = 0;
        for (int i = 0; i < planeRows; i++) {
            if (passengers[i] == null) {
                int travelClass = findClass(i);
                available[travelClass]++;
            }
        }
        return available;
    }

    public static boolean upgrade(String passengerName, int upgradeClass) {
        if (passengerName == null) {
            return false;
        }
        int currentRow = lookUp(passengerName);
        if (currentRow == -1) {
            return false;
        }
        int currentClass = findClass(currentRow);
        if (currentClass <= upgradeClass) {
            return false;
        }
        boolean success = book(passengerName, upgradeClass);
        if (success) {
            passengers[currentRow] = null;
        }
        return success;
    }

    public static void printPlane() {
        for (int i = 0; i < planeRows; i++) {
            int travelClass = findClass(i);
            String classStr = travelClass == FIRST_CLASS ? "F" : (travelClass == BUSINESS_CLASS ? "B" : "E");
            System.out.println(i + "        | " + classStr + " | " + (passengers[i] == null ? "" : passengers[i]));
        }
    }
}