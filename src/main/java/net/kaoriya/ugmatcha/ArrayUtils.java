package net.kaoriya.ugmatcha;

class ArrayUtils {

    static void shiftRight(int[] array, int start, int end, int shift) {
        for (int r = end - 1 - shift, w = end - 1; r >= start; --r, --w) {
            array[w] = array[r];
        }
    }

    static String toString(int[] array) {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (int i = 0; i < array.length; ++i) {
            if (i != 0) {
                s.append(", ");
            }
            s.append(array[i]);
        }
        s.append("]");
        return s.toString();
    }
}
