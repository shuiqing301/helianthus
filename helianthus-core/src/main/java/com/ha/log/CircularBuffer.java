package com.ha.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;

/**
 * A circular buffer of items of a given length. It will grow up to the give
 * size as items are appended, then it will begin to overwrite older items.
 *
 * @param <T> The type of the item contained.
 */
public class CircularBuffer<T> implements Iterable<T> {

    private final List<T> lines;
    private final int size;
    private int start;

    public CircularBuffer(int size) {
        this.lines = new ArrayList<T>();
        this.size = size;
        this.start = 0;
    }

    public void append(T line) {
        if (lines.size() < size) {
            lines.add(line);
        } else {
            lines.set(start, line);
            start = (start + 1) % size;
        }
    }

    @Override
    public String toString() {
        return "[" + Joiner.on(", ").join(lines) + "]";
    }

    public Iterator<T> iterator() {
        if (start == 0)
            return lines.iterator();
        else
            return Iterators.concat(lines.subList(start, lines.size()).iterator(),
                    lines.subList(0, start).iterator());
    }

    public int getMaxSize() {
        return this.size;
    }

    public int getSize() {
        return this.lines.size();
    }

}
