package com.trms.app;

import com.trms.controller.EventController;
import com.trms.controller.UserController;
import com.trms.models.Employee;
import com.trms.models.Status;
import com.trms.repository.*;
import com.trms.service.EventService;
import com.trms.service.EventServiceImpl;
import com.trms.service.UserService;
import com.trms.service.UserServiceImpl;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

import java.util.List;

public class App {
    public static void main(String[] args) {
        //kick-starting hibernate
        StatusRepo statusRepo = new StatusRepoImpl();
        Status kickStart = statusRepo.getStatus(1);

        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins);
        establishRoutes(app);
        app.start(7000);
    }
    private static void establishRoutes(Javalin app){
        userRoutes(app);
        eventRoutes(app);
    }
    private static void userRoutes(Javalin app){
        EmployeeRepo employeeRepo = new EmployeeRepoImpl();
        UserService userService = new UserServiceImpl(employeeRepo);
        UserController userController = new UserController(userService);

        app.post("/login", userController.login);
    }

    private static void eventRoutes(Javalin app){
        EventRepo eventRepo = new EventRepoImpl();
        EventService eventService = new EventServiceImpl(eventRepo);
        EventController eventController = new EventController(eventService);

        app.get("/employee/:employeeId", eventController.getAllEventsForRole);
        app.post("/employee/:employeeId/event", eventController.addEvent);
        app.delete("/employee/:employeeId/event/:eventId", eventController.deleteEvent);
        app.put("/employee/:employeeId/event/:eventId", eventController.updateEvent);
    }
}
