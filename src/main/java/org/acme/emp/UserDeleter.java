package org.acme.emp;



import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserDeleter {
    @Inject
    UserRepository userRepository;

    public boolean deleteUser(String id) {
        User user = userRepository.findById(id);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}