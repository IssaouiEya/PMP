package org.acme.Project;



import java.util.Arrays;
import java.util.List;

public class TestConstants {
    public static  List<Project> PROJECTS = Arrays.asList(
            new Project("project1", "incredible app","12-12-2023"),
            new Project("project2", "new app","12-10-2023"),
            new Project("project3", "app","12-11-2024")
    );
}
