package com.trms.controller;

import com.google.gson.Gson;
import com.trms.models.Event;
import com.trms.service.EventService;
import com.trms.util.MyLogger;
import io.javalin.http.Handler;

import java.util.List;

public class EventController {
    EventService eventService;
    Gson gson = new Gson();
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    public Handler getAllEventsForRole = (ctx) -> {
        String input  = ctx.pathParam("employeeId");
        int employeeId;

        try {
            employeeId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            employeeId = -1;
            MyLogger.logger.error(e.getMessage());
            e.printStackTrace();
        }

        List<Event> send= eventService.getEventForRole(employeeId);

        if (send != null){
            ctx.result(gson.toJson(send));
            MyLogger.logger.info(200);
        }
        else{
            ctx.result("{}");
            MyLogger.logger.warn(400);;
            ctx.status(404);
        }
    };

    public Handler addEvent = (ctx) -> {
        Event event = gson.fromJson(ctx.body(), Event.class);

        if(event == null){
            MyLogger.logger.warn("Request was invalid");
            ctx.status(400);
        }

        else{
            event = eventService.addEvent(event);

            if(event != null){
                ctx.result(gson.toJson(event));
                MyLogger.logger.info("Added the event Successfully");
            }
            else{
                MyLogger.logger.error("Was not able to Event");
                ctx.status(404);
            }
        }
    };

    public Handler deleteEvent = (ctx) -> {
        String input  = ctx.pathParam("eventId");
        int eventId;

        try {
           eventId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            eventId = -1;
            MyLogger.logger.error(e.getMessage());
            e.printStackTrace();
        }

        Event send = eventService.deleteEvent(eventId);

        if (send != null){
            ctx.result(gson.toJson(send));
            MyLogger.logger.info(200);
        }
        else{
            ctx.result("{}");
            MyLogger.logger.warn(400);;
            ctx.status(404);
        }
    };

    public Handler updateEvent = (ctx) -> {
        String input  = ctx.pathParam("employeeId");
        int employeeId;

        try {
            employeeId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            employeeId = -1;
            MyLogger.logger.error(e.getMessage());
            e.printStackTrace();
        }
        Event event = gson.fromJson(ctx.body(), Event.class);

        if(event == null){
            MyLogger.logger.warn("Request was invalid");
            ctx.status(400);
        }
        else {
            Event send = eventService.updateEvent(employeeId,event);

            if (send != null){
                ctx.result(gson.toJson(send));
                MyLogger.logger.info(200);
            }
            else{
                ctx.result("{}");
                MyLogger.logger.warn(400);;
                ctx.status(404);
            }
        }
    };
}
