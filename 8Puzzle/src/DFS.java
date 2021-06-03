import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class DFS {
    private final Node startState;
    private final Node goalState;
    private final Stack<Node> frontier;
    private final ArrayList<Node> explored;

    public DFS(Node startState, Node goalState) {
        this.startState = startState;
        this.goalState = goalState;
        this.frontier = new Stack<Node>();
        this.explored = new ArrayList<Node>();
    }

    public void run() {
        Node currentState;
        frontier.push(startState);

        while (!frontier.empty()) {
            currentState = frontier.pop();
            explored.add(currentState);

            if (currentState.equals(goalState)) {
                printPuzzleSolution(currentState);
                return;
            }

            for (Node child : currentState.getChildren()) {
                if (!(frontier.contains(child) || explored.contains(child))) {
                    frontier.push(child);
                }
            }
        }

        printFailureMessage();
    }

    // Performed iteratively with another stack because large solutions will cause stack overflow errors if done with recursion and a call stack.
    // Solution written to a file to ensure the solution is not lost in the running environment to any sort of character limits or overflows.
    private void printPuzzleSolution(Node endNode) {
        System.out.println("The puzzle was solved successfully! The solution will be written in solution.txt");

        createSolutionFile();

        Stack<Node> solutionStack = new Stack<Node>();
        Node currentNode = endNode;
        while (currentNode.getParent() != null) {
            solutionStack.push(currentNode);
            currentNode = currentNode.getParent();
        }

        try {
            FileWriter solution = new FileWriter("solution.txt");

            solution.write("Starting state: " + Arrays.toString(currentNode.getState()) + System.lineSeparator());
            while (!solutionStack.empty()) {
                currentNode = solutionStack.pop();
                solution.write(currentNode.getRelationToParent() + " to state: " + Arrays.toString(currentNode.getState()) + System.lineSeparator());
            }
            solution.close();
            System.out.println("Solution successfully written to solution.txt");
        } catch (IOException e) {
            System.out.println("Error occurred writing to solution.txt");
            e.printStackTrace();
        }
    }

    private void printFailureMessage() {
        System.out.println("Puzzle could not be solved.");
    }

    private void createSolutionFile() {
        try {
            File f = new File("solution.txt");
            f.createNewFile(); // We don't care about the result of this call. If true, the file was created. If false, it exists already and we will overwrite the contents.
        } catch (IOException e) {
            System.out.println("Failed to create solutions.txt");
            e.printStackTrace();
        }
    }
}
