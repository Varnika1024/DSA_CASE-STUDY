import java.util.*;

class Node {
    boolean leaf;
    List<Integer> keys = new ArrayList<>();
    List<Node> branches = new ArrayList<>();
    List<String> records = new ArrayList<>();
    Node next = null;

    Node(boolean leaf) {
        this.leaf = leaf;
    }
}

public class Main {
    private Node root = new Node(true);
    private int limit = 3;

    public void add(int key, String val) {
        Node target = locate(root, key);
        place(target, key, val);

        if (target.keys.size() > limit) {
            divide(target);
        }
    }

    private Node locate(Node current, int key) {
        if (current.leaf) {
            return current;
        }
        for (int i = 0; i < current.keys.size(); i++) {
            if (key < current.keys.get(i)) {
                return locate(current.branches.get(i), key);
            }
        }
        return locate(current.branches.get(current.branches.size() - 1), key);
    }

    private void place(Node target, int key, String val) {
        int i = 0;
        while (i < target.keys.size() && target.keys.get(i) < key) {
            i++;
        }
        target.keys.add(i, key);
        target.records.add(i, val);
    }

    private void divide(Node left) {
        Node right = new Node(true);
        int mid = left.keys.size() / 2;

        right.keys.addAll(left.keys.subList(mid, left.keys.size()));
        right.records.addAll(left.records.subList(mid, left.records.size()));

        left.keys.subList(mid, left.keys.size()).clear();
        left.records.subList(mid, left.records.size()).clear();

        right.next = left.next;
        left.next = right;

        if (left == root) {
            Node newRoot = new Node(false);
            newRoot.keys.add(right.keys.get(0));
            newRoot.branches.add(left);
            newRoot.branches.add(right);
            root = newRoot;
        } else {
            Node upper = getUpper(root, left);
            link(upper, left, right, right.keys.get(0));
        }
    }

    private Node getUpper(Node current, Node child) {
        if (current.leaf || current.branches.get(0).leaf) {
            return root;
        }
        for (Node b : current.branches) {
            if (b.branches.contains(child)) {
                return b;
            }
            Node match = getUpper(b, child);
            if (match != null) {
                return match;
            }
        }
        return root;
    }

    private void link(Node upper, Node left, Node right, int key) {
        int i = 0;
        while (i < upper.keys.size() && upper.keys.get(i) < key) {
            i++;
        }
        upper.keys.add(i, key);
        upper.branches.add(i + 1, right);
    }

    public void show() {
        Node curr = root;
        while (!curr.leaf) {
            curr = curr.branches.get(0);
        }
        while (curr != null) {
            for (int i = 0; i < curr.keys.size(); i++) {
                System.out.print("[" + curr.keys.get(i) + ":" + curr.records.get(i) + "] ");
            }
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Main bPlusTree = new Main();
        bPlusTree.add(10, "A");
        bPlusTree.add(20, "B");
        bPlusTree.add(5, "C");
        bPlusTree.add(15, "D");
        bPlusTree.show();
    }
}
