package hit;

import java.util.ArrayList;
import java.util.List;

public class HitList implements HitCollection {
    private final List<Hit> hitList;

    public HitList() {
        hitList = new ArrayList<>();
    }

    @Override
    public boolean addHit(Hit hit) {
        return hitList.add(hit);
    }

    @Override
    public List<Hit> getHits() {
        return hitList;
    }

    @Override
    public boolean clearHits() {
        hitList.clear();
        return true;
    }
}
