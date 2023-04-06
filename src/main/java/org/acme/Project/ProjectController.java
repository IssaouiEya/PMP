package org.acme.Project;

import graphql.ExecutionResult;
import graphql.GraphQL;

import graphql.schema.GraphQLSchema;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/project-graphql")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
@GraphQLApi

public class ProjectController {

    public ProjectRepository projectRepository;
    @Inject
    ProjectFetcher projectFetcher;
    @Inject
    ProjectUpdater projectUpdater;
    @Inject
    ProjectDeleter projectDeleter;

    @Inject
    GraphQLSchema schema;



    @POST
    public Response graphql(String requestBody) {
        ExecutionResult executionResult = new GraphQL.Builder(schema).build()
                .execute(requestBody);
        return Response.ok(executionResult).build();
    }

    @Query("getAllProject")
    public List<Project> getAllProject() {
        return projectFetcher.getAllProject();
    }

    @Query("getProjectById")
    public Project getProjectById(String id) {

        return projectFetcher.getProjectById(id);
    }


    @Mutation("addProject")
    public Project addProject(String name, String description,String deliverydate) {
        return projectUpdater.addProject(name, description,deliverydate);
    }



    @Mutation("updateProject")
    public Project updateProject(String id,String name, String description,String deliverydate) {
        return projectUpdater.updateProject(id, name, description,deliverydate);
    }

    @Mutation("deleteProject")
    public  boolean deleteProject (String id) {
        try {

            return projectDeleter.deleteProject(id);
            // Return true if deletion is successful
        } catch (Exception e) {
            // Handle any exception that may occur during deletion
            return false; // Return false if deletion fails
        }
    }


}