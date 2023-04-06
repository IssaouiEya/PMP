package org.acme.Project;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProjectUpdater {
    @Inject
   ProjectRepository projectRepository;

    public Project addProject(String name, String description, String deliverydate) {
        Project project = new Project(name,description,deliverydate);
        project.name = name;
        project.description = description;
        project.deliverydate=deliverydate;
        projectRepository.persist(project);
        return project;
    }


    public Project updateProject(String id, String name,String description , String deliverydate ) {
        Project project = projectRepository.findById(id);
        project.name = name;
        project.description=description ;
        project.deliverydate= deliverydate;
        projectRepository.update(project);
        return project;
    }


}