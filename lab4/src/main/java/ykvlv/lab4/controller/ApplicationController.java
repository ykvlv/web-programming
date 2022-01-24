package ykvlv.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ykvlv.lab4.service.HitService;
import ykvlv.lab4.data.dto.HitDto;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.entity.Hit;
import ykvlv.lab4.exception.BadArgumentException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/app")
public class ApplicationController {
    private final HitService hitService;

    public ApplicationController(HitService hitService) {
        this.hitService = hitService;
    }

    @ResponseBody
    @PostMapping
    public Response<Hit> addHit(@RequestBody HitDto hitDto, Principal principal) {
        try {
            Hit hit = hitService.add(hitDto, principal.getName());
            return new Response<>("Запись успешно добавлена", true, hit);
        } catch (BadArgumentException e) {
            return new Response<>(e.getMessage(), false, null);
        }
    }

    @ResponseBody
    @DeleteMapping("{id}")
    public Response<Hit> deleteHit(@PathVariable long id, Principal principal) {
        String hitOwner;
        try {
            hitOwner = hitService.getById(id).getOwner();
        } catch (EntityNotFoundException e) {
            return new Response<>("Записи с таким id не существует", false, null);
        }
        if (hitOwner.equals(principal.getName())) {
            try {
                Hit hit = hitService.delete(id);
                return new Response<>("Попадание успешно удалено", true, hit);
            } catch (BadArgumentException e) {
                return new Response<>(e.getMessage(), false, null);
            }
        }
        return new Response<>("Удалять записи может только владелец", false, null);
    }

    @ResponseBody
    @GetMapping("/all")
    public Response<List<Hit>> allHits() {
        return new Response<>("Все записи успешно получены", true, hitService.getAll());
    }

    @GetMapping
    public String app() {
        return "app";
    }
}
