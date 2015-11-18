package edu.ntu.cltk.measurement;


public class Counting {

    private static Counting count = null;

    private int _base = 0;

    private Counting() {

    }

    public static synchronized Counting v() {
        if (count == null) {
            count = new Counting();
        }
        return count;
    }

    public void increase() {
        increase(1);
    }

    public void increase(int num) {
        _base += num;
    }

    public int result() {
        return _base;
    }

    public void reset() {
        _base = 0;
    }
}
