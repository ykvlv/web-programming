package table;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
    private final List<Hit> hits = new LinkedList<>();

    public Table() { }

    public void addHit(Hit hit) {
        hits.add(0, hit);
    }

    public void clearHits() {
        hits.clear();
    }

    public List<Hit> getHits() {
        return hits;
    }

    public List<Hit> getValidHits() {
        return hits.stream().filter(Hit::isValid).collect(Collectors.toList());
    }
}
