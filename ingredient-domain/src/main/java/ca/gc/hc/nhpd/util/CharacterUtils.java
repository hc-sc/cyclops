package ca.gc.hc.nhpd.util;

import java.util.HashMap;

/**
 * This class contains generic utility methods to deal with character
 * operations. Unlike the class StringUtils, this class deals exclusively with
 * character manipulation and replacement.
 * @author RROY (Ronit Roy)
 */
public class CharacterUtils {
    @SuppressWarnings("unchecked")
    // ----- Class Variables ----- //
    public static HashMap invalidCharacters = new HashMap();
    // Represents the invalid character corresponding to the invalid double
    // cross character.
    //public static final char doubleCrossInvalidCharacter = '';
    // Represents the byte numbers (in UTF-8) corresponding to the incorrectly encoded
    // doublecross character.
    public static final byte[] doubleCrossInvalidCharacterBytes = { -62, -121 };
    // Represents the string corresponding to the doublecross character.
    public static final char doubleCrossReplacementCharacter = '\u2021';
    // Represents the byte numbers (in ASCII) corresponding to the doublecross character.
    public static final byte[] doubleCrossReplacementCharacterBytes = {(byte)0x87};
    // Represents the invalid character corresponding to the invalid single
    // quote character.
    //public static final char leftSingleQuoteInvalidCharacter = '';
    // Represents the byte numbers (in UTF-8) corresponding to the incorrectly encoded
    // left quote character.
    public static final byte[] leftSingleQuoteInvalidCharacterBytes = { -62,
            -111 };
    // Represents the string corresponding to the left quote character.
    public static final char leftSingleQuoteReplacementCharacter = '\u2018';
    // Represents the byte numbers (in ASCII) corresponding to the left single quote character.
    public static final byte[] leftSingleQuoteReplacementCharacterBytes = {(byte)0x91};    
    // Represents the invalid character corresponding to the invalid trademark
    // character.
    //public static final char trademarkInvalidCharacter = '';
    // Represents the byte numbers corresponding to the incorrectly encoded
    // trademark character.
    public static final byte[] trademarkInvalidCharacterBytes = { -62, -103 };
    // Represents the string corresponding to the trademark character.
    public static final char trademarkReplacementCharacter = '\u2122';
    // Represents the byte numbers (in ASCII) corresponding to the trademark character.
    public static final byte[] trademarkReplacementCharacterBytes = {(byte)0x99};     

    // ----- Class Public Methods ----- //
/*    *//**
     * Gets a hashmap of invalid characters and their replacements. The hashmap
     * contains the following keys and values. The key is the specific invalid
     * character (e.g. ''). The value contains a hashmap with a byte array and
     * a replacement character. For example, the hashmap could contain a byte
     * array of { -62, -121 } (which represents the bytes for the invalid
     * character ''). The value for { -62, -121 } is the replacement character
     * ('\u2021') for the invalid character ''. Thus a typical entry for the
     * invalid character '' would be '' => { -62, -121 } => '\u2021'.
     * @return A hashmap with the combination of invalid characters for keys,
     *         and a value containing a hashmap with the invalid character
     *         bytes, and the correct replacement character.
     *//*
    @SuppressWarnings("unchecked")
    public static HashMap getInvalidCharacters() {
        // The HashMap representing the bytes corresponding to the invalid
        // double cross character, and the correct valid replacement double
        // cross character.
        HashMap doubleCrossCharacters = new HashMap();
        // The HashMap representing the bytes corresponding to the invalid
        // single quote character, and the correct valid replacement single
        // quote character.
        HashMap leftSingleQuoteCharacters = new HashMap();
        // The HashMap representing the bytes corresponding to the invalid
        // trademark character, and the correct valid replacement trademark
        // character.
        HashMap trademarkCharacters = new HashMap();
        doubleCrossCharacters.put(doubleCrossInvalidCharacterBytes,
                doubleCrossReplacementCharacter);
        leftSingleQuoteCharacters.put(leftSingleQuoteInvalidCharacterBytes,
                leftSingleQuoteReplacementCharacter);
        trademarkCharacters.put(trademarkInvalidCharacterBytes,
                trademarkReplacementCharacter);
        // Adding the double cross character, to byte, to replacement character
        // mapping.
        invalidCharacters.put(doubleCrossInvalidCharacter,
                doubleCrossCharacters);
        // Adding the single quote character, to byte, to replacement character
        // mapping.
        invalidCharacters.put(leftSingleQuoteInvalidCharacter,
                leftSingleQuoteCharacters);
        // Adding the trademark quote character, to byte, to replacement
        // character mapping.
        invalidCharacters.put(trademarkInvalidCharacter, trademarkCharacters);
        return invalidCharacters;
    }*/
}
