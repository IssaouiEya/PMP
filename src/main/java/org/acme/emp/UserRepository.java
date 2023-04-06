package org.acme.emp;



import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import org.acme.Project.Project;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepositoryBase<User, String> {


    public List<User> getAllUser() {
        return findAll().list();
    }
    public User findById(String id) {
        return find("_id", new ObjectId(id)).firstResult();
    }
    public void addUser(User user) {
        persist(user);
    }
    public void updateUser(User user) {
        update("firstname = ?1 and lastname = ?2 and birthdate=?3 and role=?4 and username =?5 and password=?6"
                , user.firstname, user.lastname,user.birthdate , user.role, user.username,user.password);
    }
    public User getUserById(String id) {
        return findById(id);
    }
    public void deleteUser(String id) {
        deleteById(id);
    }
    public List<User> findByProjectId(String projectIds){
             return findAll().list();
       }
    }

