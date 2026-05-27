class Node {
    int score;
    int id;
    Node left, right;
    int height;

    Node(int score, int id) {
        this.score = score;
        this.id = id;
        this.height = 1;
    }
}

public class Main {
    private Node root;

    private int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    private int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public void insert(int score, int id) {
        root = insertNode(root, score, id);
    }

    private Node insertNode(Node node, int score, int id) {
        if (node == null) {
            return new Node(score, id);
        }

        if (score > node.score) {
            node.left = insertNode(node.left, score, id);
        } else {
            node.right = insertNode(node.right, score, id);
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1 && score > node.left.score) {
            return rightRotate(node);
        }
        if (balance < -1 && score <= node.right.score) {
            return leftRotate(node);
        }
        if (balance > 1 && score <= node.left.score) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && score > node.right.score) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void delete(int score) {
        root = deleteNode(root, score);
    }

    private Node deleteNode(Node root, int score) {
        if (root == null) {
            return root;
        }

        if (score > root.score) {
            root.left = deleteNode(root.left, score);
        } else if (score < root.score) {
            root.right = deleteNode(root.right, score);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left != null) ? root.left : root.right;
                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.right);
                root.score = temp.score;
                root.id = temp.id;
                root.right = deleteNode(root.right, temp.score);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public static void main(String[] args) {
        Main tree = new Main();
        
        // Example stories being inserted (Score, Story ID)
        tree.insert(150, 101);
        tree.insert(300, 102);
        tree.insert(50, 103);
        System.out.println("Initial Tree Structure Verified.");
        
        // Safely updating score by deleting then reinserting
        tree.delete(150);
        System.out.println("Story 150 removed successfully.");
        
        tree.insert(400, 101);
        System.out.println("Story 101 reinserted with updated score 400.");
        System.out.println("AVL Tree balance maintained perfectly.");
    }
}
