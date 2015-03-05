/**
 * Created by AhmedA on 2/27/2015.
 */
public class Decrypt {

    public static String EncryptByRowTransposition(int[] key, String plainText){
        char[][] realPlainText = new char[(int)Math.ceil(plainText.length()/key.length)][key.length];
        int k = 0;
        int j = 0;
        for (int i = 0; i < Math.ceil(plainText.length()/key.length); i++) {
            for (j = 0; j <key.length; j++) {
                realPlainText[i][j] = plainText.charAt(k++);
            }
        }
        char[] cipher = new char[realPlainText.length*realPlainText[0].length];
        for (int i = 0, w = 0; i < realPlainText[0].length ; i++) {
            for (int q = 0; q < realPlainText.length ; q++, w++) {
                cipher[w] = realPlainText[q][key[i]];
            }
        }
        StringBuilder cii = new StringBuilder();
        for (int i = 0; i <cipher.length; i++) {
            cii.append(cipher[i]);
        }
        return cii.toString();
    }


}
