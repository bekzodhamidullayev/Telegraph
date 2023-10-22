package uz.telegraph.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.telegraph.dto.TelegraphDto;
import uz.telegraph.entity.TelegraphEntity;
import uz.telegraph.service.TelegraphService;

import java.util.UUID;

@RestController
@RequestMapping("/telegraph")
@RequiredArgsConstructor
public class TelegraphController {
    private final TelegraphService telegraphService;
    @PostMapping("/create/{ownerId}")
    public TelegraphEntity create(
            @PathVariable UUID ownerId,
            @RequestBody TelegraphDto dto
    ) {
        return telegraphService.create(dto, ownerId);
    }
    @GetMapping("/find-by-id/{id}")
    public TelegraphDto findById(@PathVariable UUID id) {
        return telegraphService.findById(id);
    }
    @PutMapping("/update/{id}")
    public TelegraphDto update(
            @PathVariable UUID id,
            @RequestBody TelegraphDto update
    ) {
        return telegraphService.update(update, id);
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        return telegraphService.deleteById(id);
    }
}
