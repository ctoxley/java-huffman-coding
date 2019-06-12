package huffman.coding;

import huffman.coding.Model.BranchNode;
import huffman.coding.Model.LeafNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModelTest {

    private LeafNode leftNode = new LeafNode(1, "l");
    private LeafNode rightNode = new LeafNode(1, "r");
    private BranchNode branchNode = new BranchNode(2, "lr", leftNode, rightNode);

    @Test
    public void branchNodeNextLeft() {
        assertThat(branchNode.next('l').node()).isSameAs(leftNode);
        assertThat(branchNode.next('l').path()).isEqualTo("0");
        assertThat(branchNode.next('l').wasLeft()).isTrue();
    }

    @Test public void branchNodeNextRight() {
        assertThat(branchNode.next('r').node()).isSameAs(rightNode);
        assertThat(branchNode.next('r').path()).isEqualTo("1");
        assertThat(branchNode.next('r').wasLeft()).isFalse();
    }

    @Test public void leafNodeNext() {
        assertThrows(UnsupportedOperationException.class,
                () -> leftNode.next('c'),
                "Leaf node does contain any other nodes.");
    }

    @Test public void leafNodeIsLeaf() {
        assertThat(rightNode.isLeaf()).isTrue();
    }
}
