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

    // Create the DFS. All we need to know is the start state and the goal state.
    public DFS(Node startState, Node goalState) {
        this.startState = startState;
        this.goalState = goalState;
        this.frontier = new Stack<>();
        this.explored = new ArrayList<>();
    }

    // Textbook depth first graph search. As long as the frontier is not empty, continue to pop Node's from the top of
    // the stack. Evaluate if the given Node is the goal state, and return if it is. If not, then continue to search by
    // checking the Node's children for any new states (i.e. not in the frontier, and not explored already), and push
    // any new states onto the frontier stack.
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

        printFailureMessage(); // If we exhaust the frontier without finding the goal state, the search failed.
    }

    // Print out the puzzle's solution to the solution.txt file. This will display all of the move's made to reach the
    // goal state from the start state.
    // Technical Note: Performed iteratively with another stack because large solutions will cause stack overflow errors
    // if done with recursion and a call stack. Solution written to a file to ensure the solution is not lost in the
    // running environment to any sort of character limits or overflows.
    private void printPuzzleSolution(Node endNode) {
        System.out.println("The puzzle was solved successfully! The solution will be written in solution.txt");

        createSolutionFile(); // Create the solution.txt file if it does not exist already.

        // Loop over parent nodes to create a stack where the top node is the starting state, thus allowing us to later
        // pop off the stack to iterate from start state to goal state.
        Stack<Node> solutionStack = new Stack<>();
        Node currentNode = endNode;
        while (currentNode.getParent() != null) {
            solutionStack.push(currentNode);
            currentNode = currentNode.getParent();
        }

        try {
            FileWriter solution = new FileWriter("solution.txt");

            solution.write("Starting state: " + Arrays.toString(currentNode.getState()) + System.lineSeparator());

            // Pop Node's off the stack until it is empty, writing the move made to reach the next Node at each step.
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

    // Print out a failure message in the event that no solution was found.
    private void printFailureMessage() {
        System.out.println("Puzzle could not be solved.");
    }

    // Create the solution.txt file if it does not exist, or do nothing if it does exist.
    private void createSolutionFile() {
        try {
            File f = new File("solution.txt");
            f.createNewFile(); // We don't care about the result of this call. If true, the file was created. If false,
            // it exists already and we will overwrite the contents.
        } catch (IOException e) {
            System.out.println("Failed to create solutions.txt");
            e.printStackTrace();
        }
    }
}
