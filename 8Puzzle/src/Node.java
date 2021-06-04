import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    public static final int STATE_SIZE = 9; // Length of state array, 9 integers to represent 3x3 puzzle.

    private final int[] state;
    private final Node parent;
    private final ArrayList<Node> children;
    private final Relation relationToParent; // The move made from the parent Node's state to achieve this Node's state.
    private final int zeroPosition; // The index of the zero in the state array, which represents the empty square.


    // Creates node given puzzle state. State must be exactly a length of 9.
    public Node(int[] state, Node parent, Relation relationToParent) {
        if (state.length != STATE_SIZE) {
            throw new IllegalArgumentException("State array must be of length " + STATE_SIZE);
        }
        this.state = state;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.relationToParent = relationToParent;
        this.zeroPosition = getZeroPosition();
    }

    // Override the equals operator so that we can test for the goal state or previously seen states based solely upon
    // the state attribute and not the object itself or its search tree position.
    @Override
    public boolean equals(Object o) {
        // If it is the same object, then that will be true by default.
        if (o == this) {
            return true;
        }

        // If the compared object isn't even a Node, then that will be false by default.
        if (!(o instanceof Node)) {
            return false;
        }

        // If we are comparing to another Node, then compare solely on the value of the state to determine equality.
        return Arrays.equals(state, ((Node)o).state);
    }

    public Node getParent() {
        return parent;
    }

    public int[] getState() {
        return state;
    }

    public Relation getRelationToParent() {
        return relationToParent;
    }

    // Create all child nodes possible to be obtained from this state and return them.
    // Child nodes are not created until requested so as to preserve memory space.
    public ArrayList<Node> getChildren() {
        createChildren();
        return children;
    }

    // Create all child nodes possible from this Node's current state. This requires checking if each of the four
    // possible moves are possible in this state: LEFT, RIGHT, UP, DOWN
    private void createChildren() {
        // Check if it is possible to move the empty square left, and add that child if possible.
        if (!(zeroPosition == 0 || zeroPosition == 3 || zeroPosition == 6)) {
            createChild(Relation.LEFT);
        }

        // Check right
        if (!(zeroPosition == 2 || zeroPosition == 5 || zeroPosition == 8)) {
            createChild(Relation.RIGHT);
        }

        // Check Up
        if (zeroPosition > 2) {
            createChild(Relation.UP);
        }

        // Check Down
        if (zeroPosition < 6) {
            createChild(Relation.DOWN);
        }
    }

    // Create the child node as given by the relation argument. I.e., if relation is UP, then create a Node whose
    // state is that of the current Node's after an UP move has been made.
    private void createChild(Relation relation) {
        int offset = switch (relation) {
            case LEFT -> -1;
            case RIGHT -> 1;
            case UP -> -3;
            case DOWN -> 3;
            default -> 0;
        };

        int[] childState = state.clone();
        int temp = childState[zeroPosition + offset];
        childState[zeroPosition + offset] = 0;
        childState[zeroPosition] = temp;

        Node child = new Node(childState, this, relation);
        children.add(child);
    }

    // Find the index of the zero (empty space) in the current Node's state.
    private int getZeroPosition() {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}
