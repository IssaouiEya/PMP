package org.acme.Project;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProjectRepository implements PanacheMongoRepositoryBase<Project, String> {
    public void addProject(Project project) {
        persist(project);
    }

    public void updateProject(Project project) {
        update("name = ?1 and description = ?2 and deliverydate= ?3", project.name, project.description, project.deliverydate);
    }

    public List<Project> getAllProject() {
        return findAll().list();
    }

    public Project findById(String id) {
        return find("_id", new ObjectId(id)).firstResult();
    }

    public void deleteProject(String id) {
        deleteById(id);
    }

    public Project getProjectById(String id) {
        return findById(id);
    }
}

