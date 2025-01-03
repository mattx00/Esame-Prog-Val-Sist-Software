package it.univr.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.univr.models.PasswordResetToken;
import it.univr.models.Project;
import it.univr.models.User;
import it.univr.services.UserService;
import it.univr.services.ProjectService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final ProjectService projectService;

    public DataInitializer(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public void run(String... args) {
        List<User> users = loadUserFromJson();
        if (users != null) {
            users.forEach(user -> {
                userService.save(user);
                System.out.println("User loaded: " + user.getUsername());
            });
        }
        List<Project> projects = loadProjectsFromJson();
        if( projects != null){
            projects.forEach(project -> {
                projectService.save(project);
                System.out.println("Project loaded: " + project.getName() + "(" + project.getId() + ")");
            });
        }
    }

    public static List<User> loadUserFromJson () {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        File file = new File("src/main/resources/data/Users.json");

        try {
            ArrayNode userNodes = (ArrayNode) objectMapper.readTree(file);

            for (JsonNode userNode : userNodes) {
                User user = objectMapper.treeToValue(userNode, User.class);

                JsonNode tokenNode = userNode.get("token");
                if (tokenNode != null) {
                    PasswordResetToken token = new PasswordResetToken();
                    token.setToken(tokenNode.get("token").asText());
                    token.setExpiryDate(LocalDateTime.parse(tokenNode.get("expiryDate").asText()));
                    token.setUserUsername(user.getUsername());
                    user.setToken(token);
                }
            }

            return objectMapper.readValue(userNodes.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Project> loadProjectsFromJson () {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        File file = new File("src/main/resources/data/Projects.json");

        try {
            ArrayNode projectNodes = (ArrayNode) objectMapper.readTree(file);

            /*for (JsonNode projectNode : projectNodes) {
                Project project = objectMapper.treeToValue(projectNode, Project.class);
            }*/

            return objectMapper.readValue(projectNodes.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Project.class));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
