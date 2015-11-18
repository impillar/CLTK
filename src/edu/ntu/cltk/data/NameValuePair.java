package edu.ntu.cltk.data;

public class NameValuePair<K, V> {

    private K key;
    private V value;

    public NameValuePair() {
    }

    public NameValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{%s:%s}", this.key != null ? this.key.toString() : this.value.toString(), this.value != null ? this.value.toString() : this.value.toString());
    }

    public boolean keyEqualsTo(byte val) {
        if (key == null) return false;
        return Byte.parseByte(key.toString()) == val;
    }

    public boolean keyEqualsTo(int val) {
        if (key == null) return false;
        return Integer.parseInt(key.toString()) == val;
    }

    public boolean keyEqualsTo(short val) {
        if (key == null) return false;
        return Short.parseShort(key.toString()) == val;
    }

    public boolean keyEqualsTo(long val) {
        if (key == null) return false;
        return Long.parseLong(key.toString()) == val;
    }

    public boolean keyEqualsTo(float val) {
        if (key == null) return false;
        return Float.parseFloat(key.toString()) == val;
    }

    public boolean keyEqualsTo(double val) {
        if (key == null) return false;
        return Double.parseDouble(key.toString()) == val;
    }

    public boolean keyEqualsTo(boolean val) {
        if (key == null) return false;
        return Boolean.parseBoolean(key.toString()) == val;
    }

    public boolean keyEqualsTo(char val) {
        return Integer.parseInt(key.toString()) == val;
    }

    public boolean keyEqualsTo(String val) {
        if (key == null || val == null) return false;
        return key.toString().equals(val);
    }

    public boolean keyEqualsIgnoreCase(String val) {
        if (key == null || val == null) return false;
        return key.toString().equalsIgnoreCase(val);
    }

    public boolean valueEqualsTo(int val) {
        if (value == null) return false;
        return Integer.parseInt(value.toString()) == val;
    }

    public boolean valueEqualsTo(short val) {
        if (value == null) return false;
        return Short.parseShort(value.toString()) == val;
    }

    public boolean valueEqualsTo(long val) {
        if (value == null) return false;
        return Long.parseLong(value.toString()) == val;
    }

    public boolean valueEqualsTo(float val) {
        if (value == null) return false;
        return Float.parseFloat(value.toString()) == val;
    }

    public boolean valueEqualsTo(double val) {
        if (value == null) return false;
        return Double.parseDouble(value.toString()) == val;
    }

    public boolean valueEqualsTo(boolean val) {
        if (value == null) return false;
        return Boolean.parseBoolean(value.toString()) == val;
    }

    public boolean valueEqualsTo(char val) {
        if (value == null) return false;
        return Integer.parseInt(value.toString()) == val;
    }

    public boolean valueEqualsTo(String val) {
        if (value == null || val == null) return false;
        return value.toString().equals(val);
    }

    public boolean valueEqualsIgnoreCase(String val) {
        if (value == null || val == null) return false;
        return value.toString().equalsIgnoreCase(val);
    }
}
