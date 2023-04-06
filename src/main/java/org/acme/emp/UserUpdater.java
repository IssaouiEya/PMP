package org.acme.emp;


import org.acme.Project.Project;
import org.acme.Project.ProjectRepository;
import org.bson.types.ObjectId;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@ApplicationScoped
public class UserUpdater {
    @Inject
    UserRepository userRepository;

    public User addUser(String firstname, String lastname, String birthdate, UserRole role ,
                        String username, String password, List<String> projectIds) {

        User user = new User();
        user.firstname = firstname;
        user.lastname = lastname;
        user.birthdate = birthdate;
        user.role = role;
        user.username = username;
        user.password=password;
        userRepository.persist(user);
        user.projects= projectIds ;
        return user;
    }
    public User updateUser(String id, String firstname, String lastname,String birthdate,
                           UserRole role ,String username, String password) {
        User user = userRepository.findById(id);
        user.firstname = firstname;
        user.lastname = lastname;
        user.birthdate = birthdate;
        user.role = role;
        user.username = username;
        user.password=password;
        userRepository.update(user);
        return user;
    }
}