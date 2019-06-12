package huffman.coding;

public class Model {

    public static char END_OF_TEXT = 3;

    public interface Node {
        boolean isLeaf();
        long frequency();
        PathAndNode next(char c);
        String value();
    }

    public static class LeafNode implements Node {

        private long frequency;
        private String value;

        public LeafNode(long frequency, String value) {
            this.frequency = frequency;
            this.value = value;
        }

        public boolean isLeaf() {
            return true;
        }

        public String value() {
            return value;
        }

        public long frequency() {
            return frequency;
        }

        public PathAndNode next(char c) {
            throw new UnsupportedOperationException("Leaf node does contain any other nodes.");
        }
    }

    public static class BranchNode extends LeafNode {

        private Node leftNode;
        private Node rightNode;

        public BranchNode(long frequency, String value, Node leftNode, Node rightNode) {
            super(frequency, value);
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public boolean isLeaf() {
            return false;
        }

        public PathAndNode next(char c) {
            return leftNode.value().indexOf(c) >=0 ? PathAndNode.left(leftNode) : PathAndNode.right(rightNode);
        }
    }

    public static class PathAndNode {

        private String path;
        private Node node;

        public static PathAndNode left(Node node) {
            return new PathAndNode("0", node);
        }

        public static PathAndNode right(Node node) {
            return new PathAndNode("1", node);
        }

        private PathAndNode(String path, Node node) {
            this.path = path;
            this.node = node;
        }

        public boolean wasLeft() {
            return "0".equals(path);
        }

        public String path() {
            return path;
        }

        public Node node() {
            return node;
        }

        public boolean isLeaf() {
            return node.isLeaf();
        }
    }
}
