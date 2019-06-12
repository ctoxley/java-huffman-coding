
package huffman.coding;

import huffman.coding.Model.BranchNode;
import huffman.coding.Model.Node;
import huffman.coding.Model.LeafNode;
import huffman.coding.Model.PathAndNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.LongAdder;

import static huffman.coding.Model.END_OF_TEXT;
import static java.util.Collections.unmodifiableMap;

public class Dictionary {

    private static int MORE_REFINEMENT_NEEDED = 1;

    private Map<Character, LongAdder> characterFrequency;
    private Node tree;

    public Dictionary(String letters) {
        characterFrequency = initialiseCharacterFrequencyWith(letters);
        tree = refine(flatTreeFor(characterFrequency));
    }

    public Map<Character, LongAdder> characterFrequency() {
        return unmodifiableMap(characterFrequency);
    }

    public String pathValuesTo(char character) {
        if (tree.isLeaf()) return tree.value();
        return "[" + tree.value()+ "]->" + walkValuePath(character, tree);
    }

    public String byteRepresentationFor(char character) {
        if (tree.isLeaf()) return "0";
        return walk(character, tree);
    }

    private String walkValuePath(char character, Node tree) {
        PathAndNode pathAndNode = tree.next(character);
        return pathAndNode.isLeaf()
                ? wrapInBrackets(pathAndNode)
                : wrapInBrackets(pathAndNode) + "->" + walkValuePath(character, pathAndNode.node());
    }

    private String wrapInBrackets(PathAndNode pathAndNode) {
        String wrapper = pathAndNode.wasLeft() ? "<0:" : "<1:";
        return wrapper + pathAndNode.node().value() + ">";
    }

    private String walk(char character, Node tree) {
        PathAndNode pathAndNode = tree.next(character);
        return pathAndNode.isLeaf() ? pathAndNode.path() : pathAndNode.path() + walk(character, pathAndNode.node());
    }

    private Node refine(PriorityQueue<Node> tree) {
        if (tree.size() > MORE_REFINEMENT_NEEDED) {
            Node firstLowestFrequency = tree.poll();
            Node secondLowestFrequency = tree.poll();
            long frequency = firstLowestFrequency.frequency() + secondLowestFrequency.frequency();
            String value = firstLowestFrequency.value() + secondLowestFrequency.value();
            tree.offer(new BranchNode(frequency, value, firstLowestFrequency, secondLowestFrequency));
            refine(tree);
        }
        return tree.peek();
    }

    private PriorityQueue<Node> flatTreeFor(Map<Character, LongAdder> characterFrequency) {
        PriorityQueue<Node> flatTree = new PriorityQueue(priorityQueueComparator());
        characterFrequency.forEach((character, frequency) -> {
            LeafNode leafNode = new LeafNode(frequency.longValue(), String.valueOf(character));
            flatTree.add(leafNode);
        });
        return flatTree;
    }

    private Comparator<Node> priorityQueueComparator() {
        return Comparator.comparingLong(Node::frequency).thenComparing(Node::value);
    }

    private Map<Character, LongAdder> initialiseCharacterFrequencyWith(String letters) {
        Map<Character, LongAdder> characterFrequency = new HashMap();
        letters.chars().mapToObj(c -> (char) c).forEach(c -> characterAdder(c, characterFrequency));
        characterAdder(END_OF_TEXT, characterFrequency);
        return characterFrequency;
    }

    private void characterAdder(Character c, Map<Character, LongAdder> characterFrequency) {
        characterFrequency.computeIfAbsent(c, (value) -> new LongAdder());
        characterFrequency.get(c).increment();
    }
}
