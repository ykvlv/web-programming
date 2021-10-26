package table;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Table implements Serializable {
    private List<Hit> hits = new LinkedList<>();

    public Table() { }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void addHit(Hit hit) {
        hits.add(0, hit);
    }

    public void clearHits() {
        hits.clear();
    }

    public long getCountInvalidHits() {
        return hits.stream().filter(hit -> !hit.isValid()).count();
    }

    public List<Hit> getValidHits() {
        return hits.stream().filter(Hit::isValid).collect(Collectors.toList());
    }
}
