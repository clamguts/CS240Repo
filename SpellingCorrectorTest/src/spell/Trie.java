package spell;

public class Trie implements ITrie {

    private Node root;
    private int nodeCount;
    private int wordCount;

    public Trie() {
        this.root = new Node();
        this.nodeCount = 1;
        this.wordCount = 0;
    }

    @Override
    public void add(String word) {
        Node currNode = root;
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); ++i) {
            int index = word.charAt(i) - 'a';
            if (currNode.getChildren()[index] == null) {
                currNode.getChildren()[index] = new Node();
                ++nodeCount;
            }
            currNode = (Node)currNode.getChildren()[index];
        }
        if (currNode.getValue() == 0) {
            ++wordCount;
        }
        currNode.incrementValue();
    }

    @Override
    public INode find(String word) {
        Node currNode = root;
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); ++i) {
            int index = word.charAt(i) - 'a';
            if (currNode.getChildren()[index] == null) {
                return null;
            }
            currNode = (Node)currNode.getChildren()[index];
        }
        if (currNode.getValue() < 1) {
            return null;
        }
        return currNode;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    private void toStringRecursive(Node n, StringBuilder current, StringBuilder out) {
        if (n.getValue() > 0) {
            out.append(current + "\n");
        }
        for (int i = 0; i < n.getChildren().length; ++i) {
            Node child = (Node)n.getChildren()[i];
            if (child != null) {
                char childChar = (char)(i + 'a');
                current.append(childChar);
                toStringRecursive(child, current, out);
                current.deleteCharAt(current.length()-1);
            }
        }
    }

    public String toString() {
        StringBuilder currString = new StringBuilder();
        StringBuilder outputString = new StringBuilder();
        toStringRecursive(root, currString, outputString);
        return outputString.toString().toLowerCase();
    }

    private boolean equalsRecursive(Node thisNode, Node otherNode) {
        if (thisNode == null && otherNode == null) {
            return true;
        }
        if (thisNode == null && otherNode != null) {
            return false;
        }
        if (thisNode != null && otherNode == null) {
            return false;
        }
        if (thisNode.getValue() != otherNode.getValue()) {
            return false;
        }

        Node[] thisArray = (Node[]) thisNode.getChildren();
        Node[] otherArray = (Node[]) otherNode.getChildren();

        for (int i = 0; i < thisArray.length; ++i) {
            thisNode = thisArray[i];
            otherNode = otherArray[i];
            if (!equalsRecursive(thisNode, otherNode)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Trie other = (Trie)obj;

        if ((this.wordCount != other.wordCount) || (this.nodeCount != other.nodeCount)) {
            return false;
        }

        return equalsRecursive(this.root, other.root);
    }

    public int hashCode() {
        int rootIndex = 0;
        for (int i = 0; i < root.getChildren().length; ++i) {
            if (root.getChildren()[i] != null) {
                rootIndex = i;
            }
        }
        return wordCount * nodeCount * rootIndex;
    }

}
