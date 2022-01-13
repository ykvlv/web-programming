package ykvlv.lab4.Service;

import org.springframework.stereotype.Service;
import ykvlv.lab4.data.dto.HitDto;
import ykvlv.lab4.data.entity.Hit;
import ykvlv.lab4.data.repository.HitRepository;
import ykvlv.lab4.exception.BadArgumentException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class HitService {
    private static final double MIN_X = -3, MAX_X = 3;
    private static final double MIN_Y = -5, MAX_Y = 3;
    private static final double MIN_R = -3, MAX_R = 3;

    private final HitRepository hitRepository;

    public HitService(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    private boolean hitValid(HitDto hitDto) throws BadArgumentException {
        return isXValid(hitDto.getX()) &&
                isYValid(hitDto.getY()) &&
                isRValid(hitDto.getR());
    }

    private boolean isXValid(Double x) throws BadArgumentException {
        if (x == null) {
            throw new BadArgumentException("X не может быть null");
        } else if (x < MIN_X || x > MAX_X) {
            throw new BadArgumentException("X должен быть в промежутке от " + MIN_X + " до " + MAX_X);
        } else {
            return true;
        }
    }

    private boolean isYValid(Double y) throws BadArgumentException {
        if (y == null) {
            throw new BadArgumentException("Y не может быть null");
        } else if (y < MIN_Y || y > MAX_Y) {
            throw new BadArgumentException("Y должен быть в промежутке от " + MIN_Y + " до " + MAX_Y);
        } else {
            return true;
        }
    }

    private boolean isRValid(Double r) throws BadArgumentException {
        if (r == null) {
            throw new BadArgumentException("R не может быть null");
        } else if (r < MIN_R || r > MAX_R) {
            throw new BadArgumentException("R должен быть в промежутке от " + MIN_R + " до " + MAX_R);
        } else {
            return true;
        }
    }

    private boolean isHit(double x, double y, double r) {
        return checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r);
    }

    private boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y >= 0 && Math.sqrt(x * x + y * y) <= r;
    }

    private boolean checkTriangle(double x, double y, double r) {
        return x >= 0 && y <= 0 && -y + x <= r;
    }

    private boolean checkRectangle(double x, double y, double r) {
        return x >= 0 && y >= 0 && x <= r / 2 && y <= r;
    }

    public Hit add(HitDto hitDto) throws BadArgumentException {
        if (hitValid(hitDto)) {
            Hit hit = new Hit(
                    hitDto.getX(),
                    hitDto.getY(),
                    hitDto.getR(),
                    Timestamp.valueOf(LocalDateTime.now()),
                    isHit(hitDto.getX(), hitDto.getY(), hitDto.getR()));
            hitRepository.save(hit);
            return hit;
        }
        throw new BadArgumentException("Не удалось добавить попадание");
    }
}
