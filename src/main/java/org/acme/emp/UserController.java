package org.acme.emp;
import graphql.ExecutionResult;
import graphql.GraphQL;

import graphql.schema.GraphQLSchema;
import org.acme.Project.Project;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user-graphql")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
@GraphQLApi

public class UserController {

    @Inject
    UserFetcher userFetcher;
    @Inject
    UserUpdater userUpdater;
    @Inject
    UserDeleter userDeleter;

    @Inject
    GraphQLSchema schema;
    @POST
    public Response graphql(String requestBody) {
        ExecutionResult executionResult = new GraphQL.Builder(schema).build()
                .execute(requestBody);
        return Response.ok(executionResult).build();
    }

    @Query("getAllUser")
    public List<User> getAllUser () {
        return userFetcher.getAllUser();
    }

    @Query("getUserById")
    public User getUserById(String id) {
        return userFetcher.getPersonById(id);
    }

    @Mutation("addUser")
    public User addUser(String firstname, String lastname,String birthdate,UserRole role,String username, String password , List<String> projectIds) {
        return userUpdater.addUser(firstname,lastname,birthdate, role , username, password,projectIds);
    }


    @Mutation("updateUser")
    public User updateUser(String id, String firstname, String lastname,String birthdate,UserRole role,String username, String password) {
        return userUpdater.updateUser(id,firstname,lastname,birthdate, role , username, password);
    }

    @Mutation("deleteUser")
    public boolean deleteUser(String id) {
        return userDeleter.deleteUser(id);
    }

}