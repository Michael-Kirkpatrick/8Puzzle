import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    public static final int STATE_SIZE = 9;

    private final int[] state;
    private final Node parent;
    private ArrayList<Node> children;
    private final Relation relationToParent;
    private final int zeroPosition;


    // Creates node given puzzle state. State must be exactly a length of 9.
    public Node(int[] state, Node parent, Relation relationToParent) {
        if (state.length != STATE_SIZE) {
            throw new IllegalArgumentException("State array must be of length " + STATE_SIZE);
        }
        this.state = state;
        this.parent = parent;
        this.children = new ArrayList<Node>();
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
        if (!(o instanceof Node n)) {
            return false;
        }

        return Arrays.equals(state, n.state);
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
    public ArrayList<Node> getChildren() {
        createChildren();
        return children;
    }

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

    private int getZeroPosition() {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}
