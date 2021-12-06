package hit;

import java.util.List;
import java.util.stream.Collectors;

public interface HitCollection {
    boolean addHit(Hit hit);
    boolean clearHits();
    // крысиный метод (может null вернуть)
    List<Hit> getHits();

    // пока что плохая версия
    default boolean synchronizeProjection(HitCollection hitCollection) {
        try {
            List<Hit> newHits = hitCollection.getHits().stream()
                    .sorted(Hit::compareTo)
                    .collect(Collectors.toList());
            if (clearHits()) {
                for (Hit hit : newHits) {
                    if (!addHit(hit)) return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    default boolean synchronizeOr(HitCollection hitCollection) {
        try {
            List<Hit> thisHits = this.getHits().stream()
                    .sorted(Hit::compareTo)
                    .collect(Collectors.toList());

            List<Hit> otherHits = hitCollection.getHits().stream()
                    .sorted(Hit::compareTo)
                    .collect(Collectors.toList());

            int thisPointer = 0;
            int otherPointer = 0;
            while (otherPointer < otherHits.size()) {
                if (thisPointer < thisHits.size()) {
                    if (thisHits.get(thisPointer).compareTo(otherHits.get(otherPointer)) == 0) {
                        if (!thisHits.get(thisPointer).getId().equals(otherHits.get(otherPointer).getId())) {
                            if (!this.addHit(otherHits.get(otherPointer))) return false;
                        }
                        otherPointer += 1;
                        thisPointer += 1;
                    } else if (thisHits.get(thisPointer).compareTo(otherHits.get(otherPointer)) < 0) {
                        thisPointer += 1;
                    } else {
                        if (!this.addHit(otherHits.get(otherPointer))) return false;
                        otherPointer += 1;
                    }
                } else {
                    if (!this.addHit(otherHits.get(otherPointer))) return false;
                    otherPointer += 1;
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
}