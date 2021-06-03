public class App {
    public static void main(String[] args) {
        if (args.length < 2) {
            badArgumentsGiven();
            return;
        }

        String startStateString = args[0];
        String goalStateString = args[1];
        if (startStateString.length() != Node.STATE_SIZE || goalStateString.length() != Node.STATE_SIZE) {
            badArgumentsGiven();
            return;
        }

        int[] startState;
        int[] goalState;
        try {
            startState = convertStringArgToState(startStateString);
            goalState = convertStringArgToState(goalStateString);
        }
        catch(Exception e) {
            badArgumentsGiven();
            return;
        }

        Node startNode = new Node(startState, null, Relation.ROOT);
        Node goalNode = new Node(goalState, null, Relation.ROOT);
        DFS dfs = new DFS(startNode, goalNode);
        dfs.run();
    }

    private static void badArgumentsGiven() {
        System.out.println("Need two arguments. First is start state, second is goal state. Example: java App 012345678 102345678");
    }

    private static int[] convertStringArgToState(String arg) {
        int[] state = new int[Node.STATE_SIZE];
        for (int i = 0, n = arg.length(); i < n; i++) {
            int j = Character.getNumericValue(arg.charAt(i));
            state[i] = j;
        }
        return state;
    }
}
