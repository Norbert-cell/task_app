package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String[] OPTION = {"add", "remove", "list", "exit"};
    private static String[][] tasks;
    private static String FILE_NAME = "tasks.csv";

    public static void main(String[] args) {
        tasks = loadFile(FILE_NAME);
        selectOption(OPTION);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            switch (input) {
                case "exit" :
                    saveTab(FILE_NAME,tasks);
                    System.out.println(ConsoleColors.RED_BOLD + "GOODBYE!" + ConsoleColors.RESET);
                    System.exit(0);
                    break;
                case "remove" :
                    deleteTask(tasks,numberOfDelete());
                    System.out.println("Succesfully remove.");
                    break;
                case "list" :
                    list(tasks);
                    break;
                case "add" :
                    addTask();
                    break;
                default:
                    System.out.println("Select correct option.");
            }
            selectOption(OPTION);
        }
    }

    public static void saveTab(String fileName ,String[][] tab) {
        Path dir = Paths.get(fileName);
        String[] lines = new String[tasks.length];
        for (int i = 0; i <tab.length ; i++ ) {
            lines[i] = String.join(", " , tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        }catch (IOException e ) {
            e.printStackTrace();
        }

    }

    public static boolean isNumberEquals0(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static int numberOfDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        String number = scanner.nextLine();
        while (!isNumberEquals0(number)) {
            System.out.println("Incorrect argument passed. Please give a number greater or equals 0.");
            scanner.next();
        }
        return Integer.parseInt(number);

    }

    public static void deleteTask(String[][] tab, int number) {
        try {
            if (number < tab.length) {
                tasks = ArrayUtils.remove(tab, number);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static void list (String[][] task) {
        for (int i =0; i < task.length ; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < task[i].length; j++) {
                System.out.print(task[i][j] + ", ");
            }
            System.out.println();
        }
}

    public static void addTask () {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE_BOLD + "Please add task description");
        String description = scanner.nextLine();
        System.out.println(ConsoleColors.BLUE_BOLD + "Please add task due date");
        String date = scanner.nextLine();
        System.out.println(ConsoleColors.BLUE_BOLD + "Is your task is important? true or false" + ConsoleColors.RESET);
        boolean isImportant = scanner.nextBoolean();
        tasks = Arrays.copyOf(tasks, tasks.length +1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1]= String.valueOf(date);
        tasks[tasks.length-1][2] = String.valueOf(isImportant);
    }

    public static String[][] loadFile (String fileName) {
        Path path = Paths.get(fileName);
        String[][] tab = null;
        if (!Files.exists(path)) {
            System.out.println("File dont exists!");
             System.exit(0);
        }
        try  {
            List<String> strings = Files.readAllLines(path);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i =0; i <strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j =0; j < split.length ; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void selectOption (String[] options) {
        System.out.println(ConsoleColors.WHITE_BACKGROUND_BRIGHT + ConsoleColors.BLUE_BOLD + "Please select an option" + ConsoleColors.RESET);
        for (String option: options) {
            System.out.println(ConsoleColors.CYAN + option);
        }
    }
}
