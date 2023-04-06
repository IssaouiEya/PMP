package org.acme.Project;


import graphql.schema.GraphQLSchema;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static org.wildfly.common.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectControllerTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectFetcher projectFetcher;

    @Mock
    private ProjectUpdater projectUpdater;

    @Mock
    private ProjectDeleter projectDeleter;

    @Mock
    private GraphQLSchema schema;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }




        @Test
        @DisplayName("Should get all projects")
        public void testGetAllProjects() {
            // given
            List<Project> expected = TestConstants.PROJECTS;
            when(projectFetcher.getAllProject()).thenReturn(expected);

            // when
            List<Project> actual = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body("{ \"query\": \"{"
                            + " getAllProject {"
                            + " name"
                            + " description"
                            + " deliverydate"
                            + " }"
                            + "}\""
                            + "}")
                    .when()
                    .post("/graphql")
                    .then()
                    .extract()
                    .path("data.getAllProject");
// add null check
            Objects.requireNonNull(actual, "Actual value is null");
            // then
            assertThat(actual).hasSize(expected.size());
            assertThat(actual.stream().map(Project::getName)).containsExactlyInAnyOrderElementsOf(expected.stream().map(Project::getName).toList());
            assertThat(actual.stream().map(Project::getDescription)).containsExactlyInAnyOrderElementsOf(expected.stream().map(Project::getDescription).toList());
            assertThat(actual.stream().map(Project::getDeliverydate)).containsExactlyInAnyOrderElementsOf(expected.stream().map(Project::getDeliverydate).toList());
        }



    @Test

    @DisplayName("Should get project by ID")
    public void testGetProjectById() {
        // given
        Project expected = TestConstants.PROJECTS.get(1);
        when(projectController.getProjectById(String.valueOf(expected.getId()))).thenReturn(expected);

        // when & then
        given()
                .contentType(ContentType.JSON)
                .body("{ \"query\": \"{" +
                        " getProjectById(id: " + expected.getId() + ") {" +
                        "    name" +
                        "    description" +
                        "    deliverydate"+
                        "  }"+
                        "}\"" +
                        "}")
                .when()
                .post("/graphql")
                .then()
                .statusCode(200)
                .body("data.getProjectById.name",  equalTo(expected.getName()))
                .body("data.getProjectById.description", equalTo(expected.getDescription()))
                .body("data.getProjectById.deliverydate", equalTo(expected.getDeliverydate().toString()));
    }


    @Test
    @DisplayName("Should add project")
    public void testAddProject() {
        // given
        Project expected = new Project("project1", "new app","12-12-2023");
        when(projectUpdater.addProject(eq("project1"), eq("new app"),eq("12-12-2023"))).thenReturn(expected);

        // when
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"mutation\": \"{" +
                        "  addProject(name: \\\"project1\\\", description:\\\"new app\\\" , deliverydate:\\\"12-12-2023\\\") {" +
                        "    name" +
                        "    description" +
                        "    deliverydate" +
                        "  }" +
                        "}\"" +
                        "}")
                .when()
                .post("/graphql");

        // then
        verify(projectUpdater, times(1)).addProject(eq("project1 "), eq("new app "),eq ("12-12-2023"));
        response.then()
                .statusCode(200)
                .body("data.addProject.name", equalTo(expected.getName()))
                .body("data.addProject.description",equalTo(expected.getDescription()))
                .body("data.addProject.deliverydate",  equalTo(expected.getDeliverydate())) ;
    }
    @Test
    @DisplayName("Should delete project")
    public void testDeleteProject() {
        // given
        Project expected = TestConstants.PROJECTS.get(0);
        when(projectDeleter.deleteProject(String.valueOf(expected.getId()))).thenReturn(true);

        // when
        boolean result = projectController.deleteProject(String.valueOf(expected.getId()));

        // then
        assertTrue(result); // assert that the deletion was successful

        // verify that the person has been deleted
        given()
                .contentType(ContentType.JSON)
                .body("{ \"query\": \"mutation {" +
                        "  deleteProject(id: " + expected.getId() + ") {" +
                        "    id" +
                        "    name" +
                        "    description" +
                        "    deliverydate"+
                        "  }" +
                        "}\"" +
                        "}")
                .when()
                .post("/graphql")
                .then()
                .statusCode(200)
                .body("data.deleteProject.id",  equalTo(String.valueOf(expected.getId())))
                .body("data.deleteProject.name",  equalTo(expected.getName()))
                .body("data.deleteProject.description",  equalTo(expected.getDescription()))
                .body("data.deleteProject.deliverydate",  equalTo(expected.getDeliverydate()));

    }


    @Test
    @DisplayName("Should update project")
    public void testUpdatePerson() {
        // given
        Project expected = new Project("project2", "new new app","12-12-2024");
        when(projectUpdater.updateProject(eq("1"), eq("project2"), eq("new new app"),eq ("12-12-2024"))).thenReturn(expected);

        // when
       Project result = projectController.updateProject("1", "project2", "new new app ", "12-12-2024");

        // then
        verify(projectUpdater, times(1)).updateProject(eq("1"), eq("project2"), eq("new new app "),eq("12-12-2024"));


        // verify that the person has been updated
        given()
                .contentType(ContentType.JSON)
                .body("{ \"query\": \"mutation {" +
                        "  updatePerson(id: 1, name: \\\"project2\\\", description: \\\"new new app \\\", deliverydate:\\\"12-12-2024\\\") {" +
                        "    id" +
                        "    name" +
                        "    description" +
                        " deliverydate "+
                        "  }" +
                        "}\"" +
                        "}")
                .when()
                .post("/graphql")
                .then()
                .statusCode(200)
                .body("data.deleteProject.id", (Matcher<?>) equalTo(String.valueOf(expected.getId())))
                .body("data.deleteProject.name", (Matcher<?>) equalTo(expected.getName()))
                .body("data.deleteProject.description", (Matcher<?>) equalTo(expected.getDescription()))
                .body("data.deleteProject.deliverydate", (Matcher<?>) equalTo(expected.getDeliverydate()));
    }


}