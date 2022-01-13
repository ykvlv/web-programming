package ykvlv.lab4.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ykvlv.lab4.Service.HitService;
import ykvlv.lab4.data.dto.HitDto;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.entity.Hit;
import ykvlv.lab4.exception.BadArgumentException;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    private final HitService hitService;

    public ApplicationController(HitService hitService) {
        this.hitService = hitService;
    }

    @PostMapping("/addHit")
    public Response<Hit> registration(@RequestBody HitDto hitDto) {
        try {
            Hit hit = hitService.add(hitDto);
            return new Response<>("Попадание успешно добавлено", true, hit);
        } catch (BadArgumentException e) {
            return new Response<>(e.getMessage(), false, null);
        }
    }
}
