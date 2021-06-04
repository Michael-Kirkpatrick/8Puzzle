// Relation represents the move required to reach a Node from its parent node.
// The ROOT relation is for special nodes such as the start state which have no parent node.
public enum Relation {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    ROOT
}
