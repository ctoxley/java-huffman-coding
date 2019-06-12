
package huffman.coding;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static huffman.coding.Model.END_OF_TEXT;
import static org.assertj.core.api.Assertions.assertThat;

public class DictionaryTest {

    private static final String PHRASE = "mississippi river";

    @RepeatedTest(10) public void pathValuesToOfMany() {
        Dictionary dictionary = new Dictionary(PHRASE);
        assertThat(dictionary.pathValuesTo('v')).isEqualTo("[empsirv\u0003 ]-><1:irv\u0003 >-><1:rv\u0003 >-><1:v\u0003 >-><0:v>");
    }

    @RepeatedTest(10) public void byteRepresentationForOfMany() {
        Dictionary dictionary = new Dictionary(PHRASE);
        assertThat(dictionary.byteRepresentationFor('m')).isEqualTo("0001");
        assertThat(dictionary.byteRepresentationFor('i')).isEqualTo("10");
        assertThat(dictionary.byteRepresentationFor('s')).isEqualTo("01");
        assertThat(dictionary.byteRepresentationFor('p')).isEqualTo("001");
        assertThat(dictionary.byteRepresentationFor('r')).isEqualTo("110");
        assertThat(dictionary.byteRepresentationFor('v')).isEqualTo("1110");
        assertThat(dictionary.byteRepresentationFor('e')).isEqualTo("0000");
        assertThat(dictionary.byteRepresentationFor(END_OF_TEXT)).isEqualTo("11110");
    }

    @RepeatedTest(10) public void byteRepresentationForOf2WithDuplicates() {
        Dictionary dictionary = new Dictionary("mii");
        assertThat(dictionary.byteRepresentationFor('m')).isEqualTo("01");
        assertThat(dictionary.byteRepresentationFor('i')).isEqualTo("1");
        assertThat(dictionary.byteRepresentationFor(END_OF_TEXT)).isEqualTo("00");
    }

    @RepeatedTest(10) public void byteRepresentationForOf2() {
        Dictionary dictionary = new Dictionary("mi");
        assertThat(dictionary.byteRepresentationFor('m')).isEqualTo("0");
        assertThat(dictionary.byteRepresentationFor('i')).isEqualTo("11");
        assertThat(dictionary.byteRepresentationFor(END_OF_TEXT)).isEqualTo("10");
    }

    @Test public void byteRepresentationForOf1() {
        Dictionary dictionary = new Dictionary("m");
        assertThat(dictionary.byteRepresentationFor('m')).isEqualTo("1");
        assertThat(dictionary.byteRepresentationFor(END_OF_TEXT)).isEqualTo("0");
    }

    @Test public void dictionaryOfMany() {
        Dictionary dictionary = new Dictionary(PHRASE);
        assertThat(dictionary.characterFrequency().get('m').intValue()).isEqualTo(1);
        assertThat(dictionary.characterFrequency().get('i').intValue()).isEqualTo(5);
        assertThat(dictionary.characterFrequency().get('s').intValue()).isEqualTo(4);
        assertThat(dictionary.characterFrequency().get('p').intValue()).isEqualTo(2);
        assertThat(dictionary.characterFrequency().get(' ').intValue()).isEqualTo(1);
        assertThat(dictionary.characterFrequency().get('v').intValue()).isEqualTo(1);
        assertThat(dictionary.characterFrequency().get('e').intValue()).isEqualTo(1);
        assertThat(dictionary.characterFrequency().get('r').intValue()).isEqualTo(2);
        assertThat(dictionary.characterFrequency().get(END_OF_TEXT).intValue()).isEqualTo(1);
    }

    @Test public void dictionaryOf1() {
        Dictionary dictionary = new Dictionary("m");
        assertThat(dictionary.characterFrequency().get('m').intValue()).isEqualTo(1);
        assertThat(dictionary.characterFrequency().get(END_OF_TEXT).intValue()).isEqualTo(1);
    }
}
