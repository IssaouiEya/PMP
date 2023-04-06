package org.acme.Project;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ProjectFetcher {
    @Inject
    ProjectRepository projectRepository;

    public List<Project> getAllProject() {
        return projectRepository.listAll();
    }

    public Project getProjectById(String id) {
        return projectRepository.findById(id);
    }


}
