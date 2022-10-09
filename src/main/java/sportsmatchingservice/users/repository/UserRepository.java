package sportsmatchingservice.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sportsmatchingservice.users.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
