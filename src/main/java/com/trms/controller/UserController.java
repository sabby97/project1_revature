package com.trms.controller;

import com.google.gson.Gson;
import com.trms.models.Employee;
import com.trms.service.UserService;
import com.trms.util.MyLogger;
import io.javalin.http.Handler;

public class UserController {
    UserService userService;
    Gson gson = new Gson();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Handler login = (ctx) -> {
        Employee e = gson.fromJson(ctx.body(), Employee.class);

        ctx.header("Access-Control-Allow-Origin" ,"*");
        ctx.header("Access-Control-Allow-Credentials" ,"true" );
        ctx.header("Content-Type", "application/json");
        e = userService.login(e);

        if (e != null){
            ctx.result(gson.toJson(e));
            MyLogger.logger.info(200);
        }
        else{
            ctx.result("{}");
            MyLogger.logger.error(404);
            ctx.status(404);
        }
    };
}
