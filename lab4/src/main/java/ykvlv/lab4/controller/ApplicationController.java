package ykvlv.lab4.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ykvlv.lab4.Service.HitService;
import ykvlv.lab4.data.dto.HitDto;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.entity.Hit;
import ykvlv.lab4.exception.BadArgumentException;

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
    public Response<Hit> addHit(@RequestBody HitDto hitDto) {
        try {
            Hit hit = hitService.add(hitDto);
            return new Response<>("Попадание успешно добавлено", true, hit);
        } catch (BadArgumentException e) {
            return new Response<>(e.getMessage(), false, null);
        }
    }

    @ResponseBody
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response<Hit> deleteHit(@PathVariable long id) {
        try {
            Hit hit = hitService.delete(id);
            return new Response<>("Попадание успешно удалено", true, hit);
        } catch (BadArgumentException e) {
            return new Response<>(e.getMessage(), false, null);
        }
    }

    @ResponseBody
    @GetMapping("/all")
    public List<Hit> allHits() {
        return hitService.getAll();
    }

    @GetMapping
    public String app() {
        return "app";
    }
}
