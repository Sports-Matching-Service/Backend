package sportsmatchingservice.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sportsmatchingservice.users.dto.UserPostDto;
import sportsmatchingservice.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean createUser(UserPostDto userPostDto) {
        if (userPostDto == null) return false;
        userRepository.save(userPostDto.toEntity());
        return true;
    }

}
