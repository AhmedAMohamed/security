import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by AhmedA on 2/19/2015.
 */
public class Encrypter {
    private static char[] alpha;

    public static char[] EncryptByCaesar(int key, char[] plainText){
        char[] cipher = new char[plainText.length];
        for (int i = 0; i <plainText.length ; i++) {
            cipher[i] = (char)(plainText[i]+key);
        }
        return cipher;
    }
    public static char[] EncryptByAffine(int sumKey, int producyKey, char[] plainText){
        char[] cipher = new char[plainText.length];
        for (int i = 0; i <plainText.length ; i++) {
            cipher[i] = (char)((((plainText[i]-97)*producyKey + sumKey)%26)+97);
        }
        return cipher;
    }
    public static char[] EncryptByPlayFair(char[] key, char[] plainText){
        char[][] keyMatrix = new char[5][5];
        char[] cipher;
        fillFullMatrix();
        int startX = 0 , startY = 0;
        boolean first = true;
        char[] currentKey = generateValidKey(key);

        //System.out.println(currentKey);
        for (int i = 0; i < currentKey.length ; i++) {
            markAlpha(currentKey[i]);
        }
        //System.out.println(alpha);
        for (int i = 0, k = 0 ; i < 5 ; i++) {
            for (int j = 0; j < 5 ; j++) {
                if(k >= currentKey.length){
                    if(first){
                        first = false;
                        startX = i ;
                        startY = j;
                    }
                }
                else {
                    keyMatrix[i][j] = currentKey[k];
                    k++;
                }
            }
        }
        ArrayList<Character> currentAlpha = new ArrayList<Character>();
        for (int i = 0; i < alpha.length ; i++) {
            if(alpha[i] == '0'){

            }
            else{
                currentAlpha.add(alpha[i]);
            }
        }
        for (int i = startX, k = 0; i < 5 ; i++) {
            for (int j = startY; j < 5  ; j++,k++) {
                keyMatrix[i][j] += currentAlpha.get(k);
            }
        }
        plainText = createValidPlainText(plainText);
        cipher = new char[plainText.length];
        for (int i = 0; i < plainText.length ; i+=2) {
            Pair charLocationFirst = findLocation(keyMatrix, plainText[i]);
            Pair charLocationSecond = findLocation(keyMatrix,plainText[i+1]);
            if(charLocationFirst.getX() == charLocationSecond.getX()){
                cipher[i] = keyMatrix[charLocationFirst.getX()][(charLocationFirst.getY()+1)%5];
                cipher[i+1] = keyMatrix[charLocationFirst.getX()][(charLocationSecond.getY()+1)%5];
            }
            else if(charLocationFirst.getY() == charLocationSecond.getY()){
                cipher[i] = keyMatrix[(charLocationFirst.getX()+1)%5][charLocationSecond.getY()];
                cipher[i+1] = keyMatrix[(charLocationSecond.getX()+1)%5][charLocationSecond.getY()];
            }
            else{
                cipher[i] = keyMatrix[charLocationFirst.getX()][charLocationSecond.getY()];
                cipher[i+1] = keyMatrix[charLocationSecond.getX()][charLocationFirst.getY()];
            }
        }
        return cipher;
    }

    private static char[] createValidPlainText(char[] plainText) {
        ArrayList<Character> plain = new ArrayList<Character>();
        char[] validPlainText;
        for (int i = 0; i < plainText.length ; i++) {
            plain.add(plainText[i]);
        }
        for (int i = 0; i < plainText.length ; i+=2) {
            if(plain.get(i) == plain.get(i+1)){
               plain.add(i+1,'x');
            }
        }
        if(plain.size() % 2 != 0){
            plain.add('x');
        }
        validPlainText = new char[plain.size()];
        for (int i = 0; i <plain.size() ; i++) {
            validPlainText[i] = plain.get(i);
        }
        return validPlainText;
    }

    private static Pair findLocation(char[][] keyMatrix, char c) {
        Pair current = new Pair(0,0);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5 ; j++) {
                if(c == keyMatrix[i][j]){
                    current.setX(i);
                    current.setY(j);
                    break;
                }
            }
        }
        return current;
    }

    private static void fillFullMatrix() {
        alpha = new char[25];
        int j = 0;
        for (int i = 0; i < 25 ; i++) {
            if (i == 'j'-'a'){
                j++;
            }
            alpha[i] = (char) ('a' + j + i);
        }
    }

    private static void markAlpha(char c) {
        for (int i = 0; i < 25 ; i++) {
            if(alpha[i] == c){
                alpha[i] = '0';
                break;
            }
        }
    }

    private static char[] generateValidKey(char[] key) {
        ArrayList<Character> keys = new ArrayList<Character>();
        for (int i = 0; i <key.length ; i++) {
            for (int j = i+1; j <key.length ; j++) {
                if (key[i] == key[j]) {
                    key[j] = '0';
                }
            }
        }
        int k = 0;
        for (int i = 0; i < key.length ; i++) {
            if (key[i] == '0'){
                continue;
            }
            else{
                keys.add(key[i]);
            }
        }
        char[] valid = new char[keys.size()];
        for (int i = 0; i < keys.size() ; i++) {
            valid[i] = keys.get(i);
        }
        return valid;
    }

    public static void main(String[] args){
        String x = "playfairexample";
        char[] y = x.toCharArray();
        System.out.println();
    }
}
