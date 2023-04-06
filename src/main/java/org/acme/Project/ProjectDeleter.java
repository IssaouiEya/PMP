package org.acme.Project;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProjectDeleter {
    @Inject
    ProjectRepository projectRepository;

    public boolean deleteProject(String id) {
        Project person = projectRepository.findById(id);
        if (person != null) {
            projectRepository.delete(person);
            return true;
        }
        return false;
    }
}