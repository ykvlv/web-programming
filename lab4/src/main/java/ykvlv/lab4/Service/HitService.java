package ykvlv.lab4.Service;

import org.springframework.stereotype.Service;
import ykvlv.lab4.data.dto.HitDto;
import ykvlv.lab4.data.entity.Hit;
import ykvlv.lab4.data.repository.HitRepository;
import ykvlv.lab4.exception.BadArgumentException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HitService {
    private final HitRepository hitRepository;
    private final HitChecker hitChecker;

    public HitService(HitRepository hitRepository, HitChecker hitChecker) {
        this.hitRepository = hitRepository;
        this.hitChecker = hitChecker;
    }

    public Hit add(HitDto hitDto) throws BadArgumentException {
        if (hitChecker.hitValid(hitDto)) {
            Hit hit = new Hit(
                    hitDto.getX(),
                    hitDto.getY(),
                    hitDto.getR(),
                    Timestamp.valueOf(LocalDateTime.now()),
                    hitChecker.isHit(hitDto));
            hitRepository.save(hit);
            return hit;
        } else {
            throw new BadArgumentException("Не удалось добавить попадание");
        }
    }

    public Hit delete(long id) throws BadArgumentException {
        if (hitRepository.existsById(id)) {
            Hit hit = hitRepository.getById(id);
            hitRepository.deleteById(id);
            return hit;
        } else {
            throw new BadArgumentException("Попадания с таким id не существует");
        }
    }

    public List<Hit> getAll() {
        return hitRepository.findAll();
    }
}
