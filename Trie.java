package spell;

import spell.Node;
import spell.ITrie;

import java.util.Locale;

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
        String lowerWord = word.toLowerCase();
        Node currNode = root;
        for (int i = 0; i < lowerWord.length(); ++i) {
            int index = lowerWord.charAt(i) - 'a';
            if (currNode.getChildren()[index] == null) {
                Node newNode = new Node();
                ++nodeCount;
                currNode.getChildren()[index] = newNode;
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
        String lowerWord = word.toLowerCase();
        Node currNode = root;
        for (int i = 0; i < lowerWord.length(); ++i) {
            char matchChar = lowerWord.charAt(i);
            int index = (int)matchChar - 'a';
            if (currNode.getChildren()[index] == null) {
                return null;
            }
            currNode = (Node)currNode.getChildren()[index];
        }
        if (currNode.getValue() == 0) {
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

    @Override
    public int hashCode() {
        int rootIndex = 0;
        Node[] rootChildren = (Node[]) root.getChildren();
        for (int i = 0; i < rootChildren.length; ++i) {
            if (rootChildren[i] != null) {
                rootIndex = i;
                break;
            }
        }
        return wordCount * nodeCount * rootIndex;
    }

    @Override
    public String toString() {
        StringBuilder currWord = new StringBuilder();
        StringBuilder outputWord = new StringBuilder();
        toStringRecursive(root, currWord, outputWord);
        String lowerOutput = (outputWord.toString()).toLowerCase();
        return lowerOutput;
    }

    private void toStringRecursive(INode n, StringBuilder currWord, StringBuilder output) {
        if (n.getValue() > 0) {
            output.append(currWord.toString() + "\n");
        }
        for (int i = 0; i < n.getChildren().length; ++i) {
            INode child = n.getChildren()[i];
            if (child != null) {
                char childChar = (char)('a' + i);
                currWord.append(childChar);
                toStringRecursive(child, currWord, output);
                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Trie other = (Trie)obj;

        if ((this.wordCount != other.wordCount) || (this.nodeCount != other.nodeCount)) {
            return false;
        }

        return equalsHelper(this.root, other.root);
    }

    private boolean equalsHelper(Node thisNode, Node otherNode) {
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
            if (!equalsHelper(thisNode, otherNode)) {
                return false;
            }
        }
        return true;
    }

}
