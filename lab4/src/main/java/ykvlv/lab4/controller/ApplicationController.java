package ykvlv.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ykvlv.lab4.service.HitService;
import ykvlv.lab4.service.UserService;
import ykvlv.lab4.data.dto.HitDto;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.entity.Hit;
import ykvlv.lab4.data.role.Operation;
import ykvlv.lab4.exception.BadArgumentException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/app")
public class ApplicationController {
    private final HitService hitService;
    private final UserService userService;

    public ApplicationController(HitService hitService, UserService userService) {
        this.hitService = hitService;
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping
    public Response<Hit> addHit(@RequestBody HitDto hitDto, Principal principal) {
        if (userHavePermission(principal.getName(), Operation.OP_CREATE)) {
            try {
                Hit hit = hitService.add(hitDto, principal.getName());
                return new Response<>("Попадание успешно добавлено", true, hit);
            } catch (BadArgumentException e) {
                return new Response<>(e.getMessage(), false, null);
            }
        }
        return new Response<>("Вы не обладаете правами на добавление", false, null);
    }

    @ResponseBody
    @DeleteMapping("{id}")
    public Response<Hit> deleteHit(@PathVariable long id, Principal principal) {
        String hitOwner;
        try {
            hitOwner = hitService.getById(id).getOwner();
        } catch (EntityNotFoundException e) {
            return new Response<>("Попадания с таким id не существует", false, null);
        }
        if (userHavePermission(principal.getName(), Operation.OP_DELETE_ANY) ||
                (hitOwner.equals(principal.getName()) && userHavePermission(principal.getName(), Operation.OP_DELETE_OWN))) {
            try {
                Hit hit = hitService.delete(id);
                return new Response<>("Попадание успешно удалено", true, hit);
            } catch (BadArgumentException e) {
                return new Response<>(e.getMessage(), false, null);
            }
        }
        return new Response<>("Вы не можете удалить эту запись", false, null);
    }

    @ResponseBody
    @GetMapping("/all")
    public Response<List<Hit>> allHits(Principal principal) {
        if (userHavePermission(principal.getName(), Operation.OP_READ)) {
            return new Response<>("Успешно", true, hitService.getAll());
        }
        return new Response<>("Вы не обладаете правами на чтение", false, null);
    }

    @GetMapping
    public String app() {
        return "app";
    }

    private boolean userHavePermission(String username, Operation operation) {
        if (username == null) {
            return false;
        }
        Set<Operation> operations = userService.getAllowedUserOperationsByUsername(username);
        return operations.contains(operation);
    }
}
