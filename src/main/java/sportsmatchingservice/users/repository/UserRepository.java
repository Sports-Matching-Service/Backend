package sportsmatchingservice.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sportsmatchingservice.users.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
