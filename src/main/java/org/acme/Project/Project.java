package org.acme.Project;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.acme.emp.User;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.graphql.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@MongoEntity(collection="Project")
public class Project extends PanacheMongoEntity {
    @Id
    public ObjectId id;
    public String name;
    public String description;
    public String  deliverydate;




    @ManyToOne
    public User user;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setId(ObjectId projectId) {
        this.id=id;
    }
    public ObjectId getId() {
        return id;
    }
    public void setName(String myProject) {
        this.name=name ;
    }

    public Project(String name, String description, String deliverydate) {
        this.name = name;
        this.description = description;
        this.deliverydate = deliverydate;

    }



}