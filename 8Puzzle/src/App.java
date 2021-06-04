public class App {
    public static void main(String[] args) {
        if (args.length < 2) { // If we don't have both a start and goal state given, then return.
            badArgumentsGiven();
            return;
        }

        String startStateString = args[0];
        String goalStateString = args[1];

        // If either the start or goal state given are not of the correct size, then return.
        if (startStateString.length() != Node.STATE_SIZE || goalStateString.length() != Node.STATE_SIZE) {
            badArgumentsGiven();
            return;
        }

        int[] startState;
        int[] goalState;

        // Attempt to convert the string states into an integer array. Return if that is not possible.
        try {
            startState = convertStringArgToState(startStateString);
            goalState = convertStringArgToState(goalStateString);
        }
        catch(Exception e) {
            badArgumentsGiven();
            return;
        }

        // We have now confirmed the given states are valid, so create the start and goal nodes and begin the search.
        Node startNode = new Node(startState, null, Relation.ROOT);
        Node goalNode = new Node(goalState, null, Relation.ROOT);
        DFS dfs = new DFS(startNode, goalNode);
        dfs.run();
    }

    // Prints a message to indicate the arguments were invalid, giving an example.
    private static void badArgumentsGiven() {
        System.out.println("Need two arguments. First is start state, second is goal state. Example: java App 012345678 102345678");
    }

    // Converts the given string argument into an integer array representing the puzzle's state.
    private static int[] convertStringArgToState(String arg) {
        int[] state = new int[Node.STATE_SIZE];
        for (int i = 0, n = arg.length(); i < n; i++) {
            int j = Character.getNumericValue(arg.charAt(i));
            state[i] = j;
        }
        return state;
    }
}
