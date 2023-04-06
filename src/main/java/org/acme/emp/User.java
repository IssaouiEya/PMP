package org.acme.emp;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.graphql.Id;


import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
@MongoEntity(collection="User")
public class User extends PanacheMongoEntity {
    @Id
    public ObjectId id ;
    public String firstname;
    public String lastname;
    public String  birthdate;
    public UserRole role;
    public String username;
    public String password;
    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL)
    public List<String> projects;



}