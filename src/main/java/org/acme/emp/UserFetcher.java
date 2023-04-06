package org.acme.emp;



import org.acme.Project.Project;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UserFetcher {
    @Inject
    UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.listAll();
    }

    public User getPersonById(String id) {
        return userRepository.findById(id);
    }
}
