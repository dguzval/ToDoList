// David Guzman Valente
// TA: Zane Lee
// CSE 122 AF
// Oct 12, 2023
// ToDo List Manager
// This class is a to do list manager. It asks the user for different input
// options to change their to do list. The manager allows them to add tasks, mark tasks as done
// load tasks from a file, and save the task to a file. 
import java.util.*;
import java.io.*;

public class TodoListManager {
    public static final boolean EXTENSION_FLAG = false;

    public static void main(String[] args) throws FileNotFoundException {
        // TODO: Your Code Here
        Scanner console = new Scanner(System.in);
        List<String> todoList = new ArrayList<>();
        System.out.println("Welcome to your TODO List Manager!");
        System.out.println("What would you like to do?");
        System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs, (S)ave TODOs, (Q)uit? ");
        String input = console.nextLine();
        while (!input.equalsIgnoreCase("Q")){ 
            if(input.equalsIgnoreCase("A")){
                addItem(console, todoList);
            } else if(input.equalsIgnoreCase("M")){
                markItemAsDone(console, todoList);
            } else if(input.equalsIgnoreCase("L")){
                loadItems(console, todoList);
            } else if(input.equalsIgnoreCase("S")){
                saveItems(console, todoList);
            } else {
                System.out.println("Unknown input: " + input);
            }
            printTodos(todoList);
            System.out.println("What would you like to do?");
            System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs, (S)ave TODOs, (Q)uit? ");
            input = console.nextLine();
        }
    }

    // Behaviors: This method prints out a todo list in a numbered order. However,
    // if there is nothing within the list it will tell the user that there is nothing to do
    // Exceptions: N/A
    // Returns: If the list isn't empty it returns mutliple lines each with an index starting 
    // at 1, and the value of an item in an ArrayList. If the list is empty it returns a line
    // of text telling the user there is nothign to do yet 
    // Parameters: todos is an ArrayList containing the users tasks to do.
    public static void printTodos(List<String> todos) {
        // TODO: Your Code Here
        System.out.println("Today's TODOs:");
        int index = 1;
        if(todos.size() <= 0){
            System.out.println("  You have nothing to do yet today! Relax!");
        }
        for(int i = 0; i < todos.size(); i++){
            String task = todos.get(i);
            System.out.println("  " + index + ": " + task); 
            index++;
        }
    }

    // Behaviors: adds a task the user inputs into the console into a list. If the list
    // is not empty than the user is prompted at what index the task should be placed.
    // If the list is empty or the user inputs nothing the task is added to the end of the list.
    // Exceptions: N/A
    // Returns: N/A
    // Parameters: console allows for the method to read the users input. todos is a list that
    // takes the user input and stores it. 
    public static void addItem(Scanner console, List<String> todos) {
        // TODO: Your Code Here
        System.out.print("What would you like to add? ");
        String task = console.nextLine();
        if(todos.size() > 0) {
            System.out.print("Where in the list should it be (" + "1-" + 
                                (todos.size() + 1) + ")? (Enter for end): ");
            String input = console.nextLine();
            if(input == ""){
                todos.add(task);
            } else {
                int num = Integer.parseInt(input);
                if(num == todos.size() + 1){
                    todos.add(task);
                } else {
                    todos.add(num - 1, task);
                }
            }
        } else {
            todos.add(task);
        }
        
        if(EXTENSION_FLAG){
            createDeadline(console,todos);
        }
    }

    // Behaviors: prompts the user to input the index of the task that is finished,
    //and removes that task from the list.
    // If there are no tasks the console tells the user that nothing can be marked.
    // Exceptions: N/A
    // Returns: N/A
    // Parameters: console allows for the method to read the users input. todos 
    // takes the user input and removes whatever value is at the user inputs index.
    public static void markItemAsDone(Scanner console, List<String> todos) {
        // TODO: Your Code Here
        if(todos.size() < 1){
            System.out.println("All done! Nothing left to mark as done!");
        } else {
            System.out.print("Which item did you complete (1-" + todos.size() + ")? ");
            int task = Integer.parseInt(console.nextLine());
            todos.remove(task - 1);
        }
    }

    // Behaviors: Asks for the file name to load. Erases any tasks previously in the list
    // and replaces them with the tasks in the file.
    // Exceptions: N/A
    // Returns: N/A
    // Parameters: console allows for the method to read the users input. todos
    // stores the values from the file the user inputed.
    public static void loadItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        // TODO: Your Code Here
        for(int i = todos.size() - 1; i >= 0; i--){
            todos.remove(i);
        }
        System.out.print("File name? ");
        String fileName = console.nextLine();
        Scanner file = new Scanner(new File(fileName));
        while(file.hasNextLine()){
            String line = file.nextLine();
            todos.add(line);
        }
    }

    // Behaviors: asks the user to input the file name that they want
    // to save their list to, where each task in the list is seperated
    // by lines
    // Exceptions: throws a FileNotFoundException if the user inputs a
    // string that the program cannot find.
    // Returns: N/A
    // Parameters: console allows for the method to read the users input. todos
    //grabs the stores values in the list and puts them into the file.
    public static void saveItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        // TODO: Your Code Here
        System.out.print("File name? "); //asks for the file name
        String file = console.nextLine(); //creates a space for the file name to be inputted
        PrintStream output = new PrintStream(new File(file));

        for(int i = 0; i < todos.size(); i++){
            String task = todos.get(i);
            output.println(task);
        }
    }

    
    public static void createDeadline(Scanner console, List<String> todos){
        System.out.print("What's the deadline (Enter if none): ");
        String input = console.nextLine();
        if(!input.equals("")){
            int index = todos.size() - 1;
            String taskAndDeadline = todos.get(index) + "\tDeadline: " + input;
            todos.set(index, taskAndDeadline);
        }
    }
}