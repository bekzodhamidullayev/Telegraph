package uz.telegraph.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.telegraph.dto.TelegraphDto;
import uz.telegraph.entity.TelegraphEntity;
import uz.telegraph.entity.UserEntity;
import uz.telegraph.exception.DataNotFoundException;
import uz.telegraph.repo.TelegraphRepository;
import uz.telegraph.repo.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TelegraphService {
    private final TelegraphRepository telegraphRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public TelegraphEntity create(TelegraphDto dto, UUID ownerId) {
        UserEntity user = userRepository.findById(ownerId).orElseThrow(
                () -> new DataNotFoundException("user not found"));
        TelegraphEntity telegraph = modelMapper.map(dto, TelegraphEntity.class);
        telegraph.setUser(user);
        telegraphRepository.save(telegraph);
        telegraph.setLink("http://localhost:8080/telegraph/find-by-id/" + telegraph.getId());
        telegraphRepository.save(telegraph);
        return telegraph;
    }

    public TelegraphDto findById(UUID id) {
        TelegraphEntity entity = telegraphRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("data not found"));
        return modelMapper.map(entity, TelegraphDto.class);
    }
    public TelegraphDto update(TelegraphDto dto, UUID id) {
        TelegraphEntity entity = telegraphRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("data not found"));
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setDescription(dto.getDescription());
        telegraphRepository.save(entity);
        return modelMapper.map(entity, TelegraphDto.class);
    }

    public String deleteById(UUID id) {
        telegraphRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("data not found"));
        telegraphRepository.deleteById(id);
        return "post deleted";
    }
}
