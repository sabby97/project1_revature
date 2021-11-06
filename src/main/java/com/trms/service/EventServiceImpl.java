package com.trms.service;

import com.trms.models.*;
import com.trms.repository.*;
import com.trms.util.MyLogger;

import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements EventService {
    EventRepo eventRepo;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public Event addEvent(Event e) {
        StatusRepo statusRepo = new StatusRepoImpl();

        eventBuilder(e);

        if(isSupervisor(e.getEmployeeId().getEmployeeId())){
            MyLogger.logger.info("The employee is Supervisor ADDING");
            Status a = statusRepo.getStatus(2);
            e.setEventLastStatus(a);
            e.setEventCurrStatus(a);
        }
        else if(isDepHead(e.getEmployeeId().getEmployeeId())){
            MyLogger.logger.info("The employee is DepHead ADDING");
            Status a = statusRepo.getStatus(3);
            e.setEventLastStatus(a);
            e.setEventCurrStatus(a);
        }
        else if(e.getEmployeeId().isBenco()){
            MyLogger.logger.info("The employee is Benco ADDING");
            Status a = statusRepo.getStatus(4);
            e.setEventLastStatus(a);
            e.setEventCurrStatus(a);
        }

        else{
            // if the employee does not have a supervisor
            if (!hasAsupervisor(e.getEmployeeId().getEmployeeId())){
                MyLogger.logger.info("The employee is REGULAR AND DOES NOT HAVE A SUPERVISOR ADDING");
                Status dummy = statusRepo.getStatus(2);
                e.setEventLastStatus(dummy);
                e.setEventCurrStatus(dummy);
            }
            if (!hasHead(e.getEmployeeId().getEmployeeId())){
                MyLogger.logger.info("The employee is REGULAR AND DOES NOT HAVE A DEPARTMENT HEAD ADDING");
                Status dummy = statusRepo.getStatus(3);
                e.setEventLastStatus(dummy);
                e.setEventCurrStatus(dummy);
            }
            else{
                MyLogger.logger.info("The employee is REGULAR ADDING");
                Status dummy = statusRepo.getStatus(1);
                e.setEventLastStatus(dummy);
                e.setEventCurrStatus(dummy);
            }
        }
        double actualAmount = e.getEventCost()*((e.getEventType().getTypePercentage())/100.0);
        e.setEventCost(actualAmount);
        return eventRepo.addEvent(e);
    }

    @Override
    public List<Event> getEventForRole(int employeeId) {
        List<Event> finalList = new ArrayList<>();
        //Getting all events for the user
        List<Event> firstList = eventRepo.getAllEvents(employeeId);
        if(firstList != null){
            finalList.addAll(firstList);
        }
        //checking to see if the user is a supervisor if so add all their subordinates events to the list with the status code 1
        SupervisorRepo supervisorRepo = new SupervisorRepoImpl();
        List<Supervisor> supervisorList= supervisorRepo.getAllSupervisors(employeeId);
        if(supervisorList != null){
            MyLogger.logger.info("Employee is a supervisor");
            for(Supervisor s : supervisorList){
                List<Event> temp1 = eventRepo.getAllEventsByStatus(s.getEmployeeId(),1);
                List<Event> temp2 = eventRepo.getAllEventsByStatus(s.getEmployeeId(),10);
                List<Event> temp3 = eventRepo.getAllEventsByStatus(s.getEmployeeId(),12);
                if(temp1 != null){
                    finalList.addAll(temp1);
                }
                if(temp2 != null){
                    finalList.addAll(temp2);
                }
                if(temp3 != null){
                    finalList.addAll(temp3);
                }
            }
        }
        //Checking to see if the user is Department Head if so add all employees events under him to the list with the status code 2
        DepartmentHeadRepo departmentHeadRepo = new DepartmentHeadRepoImpl();
        EmployeeRepo employeeRepo = new EmployeeRepoImpl();
        Employee checker = employeeRepo.getEmployee(employeeId);
        DepartmentHead isHead = departmentHeadRepo.getDepartmentHead(checker.getDepartment().getDepartmentId());
        if(isHead != null){
            if(checker.getEmployeeId() == isHead.getHeadId()){ //meaning isHead is the head of the department
                MyLogger.logger.info("Employee is a Department Head");
                List<Employee> temp = employeeRepo.getAllEmployees(isHead.getDepartmentId());
                if(temp != null){
                    for(Employee e : temp){
                        if(e.getEmployeeId() != checker.getEmployeeId()) { // don't want any duplicates since head is also part of the department
                            List<Event> curr1 = eventRepo.getAllEventsByStatus(e.getEmployeeId(),2);
                            List<Event> curr2 = eventRepo.getAllEventsByStatus(e.getEmployeeId(),13);
                            if(curr1 != null){
                                finalList.addAll(curr1);
                            }
                            if(curr2 != null){
                                finalList.addAll(curr2);
                            }
                        }
                    }
                }
            }
        }

        //if the employee is Benco
        if(checker.isBenco()){
            MyLogger.logger.info("Employee is a Benco");
            List<Event> another1 = eventRepo.getAllEventsByStatus(3);
            if(another1 != null){
                finalList.addAll(another1);
            }
            List<Event> another2 = eventRepo.getAllEventsByStatus(5);
            if(another2 != null){
                finalList.addAll(another2);
            }
        }


        if(finalList.size() <= 0){
           MyLogger.logger.warn("No Reimbursements found");
           return null;
        }
        else{
            MyLogger.logger.info("Getting all Reimbursements given a role");
            return finalList;
        }
    }

    @Override
    public Event deleteEvent(int eventId) {
        return eventRepo.deleteEvent(eventId);
    }

    @Override
    public Event updateEvent(int userId, Event e) {
        if(userId == e.getEmployeeId().getEmployeeId()){
            //means that the grade has been updated by the user
            MyLogger.logger.info("The user updating is the owner of the event");
            if(e.getEventCurrStatus().getStatusId() == 4){
                MyLogger.logger.info("The user is updating their grades");
                e.setEventCurrStatus(getStatus(5));
            }
            else if(e.getEventCurrStatus().getStatusId() == 8){
                MyLogger.logger.info(e.getEventCurrStatus().getStatusMsg());
                e.setEventCurrStatus(getStatus(1));
            }
            else if(e.getEventCurrStatus().getStatusId() == 9){
                MyLogger.logger.info(e.getEventCurrStatus().getStatusMsg());
                e.setEventCurrStatus(getStatus(2));
            }
            else if(e.getEventCurrStatus().getStatusId() == 11){
                MyLogger.logger.info(e.getEventCurrStatus().getStatusMsg());
                e.setEventCurrStatus(getStatus(3));
            }

        }
        else{
            if(isBenco(userId)){
                //if the request is approved and the needs to get graded
                MyLogger.logger.info("The user updating the is Benco");
                if(e.getEventCurrStatus().getStatusId() == 3){
                    MyLogger.logger.info("Benco gave initial approval");
                    e.setEventCurrStatus(getStatus(4));
                }
                //final approval and need to be checked if the grade format matches
                else if(e.getEventCurrStatus().getStatusId() == 5){
                    MyLogger.logger.info("Benco gave final approval");
                    if(e.getGradeFormat().getGradeId() == 7){
                        MyLogger.logger.info("Benco gave final approval and it's been presented");
                        e.setEventCurrStatus(getStatus(7));
                    }
                    else{
                        MyLogger.logger.info("Benco gave final approval and the grades are good");
                        e.setEventCurrStatus(getStatus(6));
                    }
                }
                //handles rejection and additional information
                else{
                    MyLogger.logger.info("Updating the status: " + e.getEventCurrStatus().getStatusId());
                    e.setEventCurrStatus(getStatus(e.getEventCurrStatus().getStatusId()));
                }
            }
            else{

                MyLogger.logger.info("Other admins");
                //rejection
                if(e.getEventCurrStatus().getStatusId() == 14){
                    e.setEventCurrStatus(getStatus(e.getEventCurrStatus().getStatusId()));
                }
                else if (isSupervisor(userId)){
                    if(e.getEventCurrStatus().getStatusId() == 1){
                        MyLogger.logger.info(e.getEventCurrStatus().getStatusMsg());
                        e.setEventCurrStatus(getStatus(2));
                    }
                    else if(e.getEventCurrStatus().getStatusId() == 8){
                        MyLogger.logger.info(e.getEventCurrStatus().getStatusMsg());
                        e.setEventCurrStatus(getStatus(8));
                    }
                    else if(e.getEventCurrStatus().getStatusId() == 10){
                        MyLogger.logger.info("Answered" + e.getEventCurrStatus().getStatusMsg());
                        e.setEventCurrStatus(getStatus(2));
                    }
                    else if(e.getEventCurrStatus().getStatusId() == 12){
                        MyLogger.logger.info(e.getEventCurrStatus().getStatusMsg());
                        e.setEventCurrStatus(getStatus(3));
                    }
                }
                else {
                    if(e.getEventCurrStatus().getStatusId() == 2){
                        MyLogger.logger.info(e.getEventCurrStatus().getStatusMsg());
                        e.setEventCurrStatus(getStatus(3));
                    }
                    else if(e.getEventCurrStatus().getStatusId() == 9){
                        MyLogger.logger.info(e.getEventCurrStatus().getStatusMsg());
                        e.setEventCurrStatus(getStatus(9));
                    }
                    else if(e.getEventCurrStatus().getStatusId() == 10){
                        MyLogger.logger.info( e.getEventCurrStatus().getStatusMsg());
                        e.setEventCurrStatus(getStatus(10));
                    }
                    else if(e.getEventCurrStatus().getStatusId() == 13){
                        MyLogger.logger.info("Answered" + e.getEventCurrStatus().getStatusMsg());
                        e.setEventCurrStatus(getStatus(3));
                    }
                }
            }
        }
        return eventRepo.updateEvent(e);
    }

    private boolean hasAsupervisor(int employeeId){
        SupervisorRepo supervisorRepo = new SupervisorRepoImpl();
        Supervisor s = supervisorRepo.getMySupervisor(employeeId);
        return s != null;
    }

    private boolean hasHead(int employeeId){
        EmployeeRepo employeeRepo = new EmployeeRepoImpl();
        Employee e = employeeRepo.getEmployee(employeeId);
        DepartmentHeadRepo departmentHeadRepo = new DepartmentHeadRepoImpl();
        DepartmentHead d = departmentHeadRepo.getDepartmentHead(e.getDepartment().getDepartmentId());
        return d != null;
    }

    private boolean isSupervisor(int employeeId){
        SupervisorRepo supervisorRepo = new SupervisorRepoImpl();
        List<Supervisor> supervisorList= supervisorRepo.getAllSupervisors(employeeId);
        return supervisorList != null;
    }

    private boolean isDepHead(int employeeId){
        DepartmentHeadRepo departmentHeadRepo = new DepartmentHeadRepoImpl();
        DepartmentHead departmentHead = departmentHeadRepo.ifIsDepartmentHead(employeeId);
        return departmentHead != null;
    }

    private boolean isBenco(int employeeId){
        EmployeeRepo employeeRepo = new EmployeeRepoImpl();
        Employee e = employeeRepo.getEmployee(employeeId);
        return e.isBenco();
    }

    private Status getStatus(int statusId){
        StatusRepo statusRepo = new StatusRepoImpl();
        return statusRepo.getStatus(statusId);
    }


    private void eventBuilder(Event e){
        //setting the employee
        EmployeeRepo employeeRepo = new EmployeeRepoImpl();
        Employee setEmployee = employeeRepo.getEmployee(e.getEmployeeId().getEmployeeId());
        e.setEmployeeId(setEmployee);

        //EventType
        EventTypeRepo eventTypeRepo = new EventTypeRepoImpl();
        EventType setEventType = eventTypeRepo.getEventType(e.getEventType().getTypeId());
        e.setEventType(setEventType);

        //GradeFormat
        GradeFormatRepo gradeFormatRepo = new GradeFormatRepoImpl();
        GradeFormat setGradeFormat = gradeFormatRepo.getGradeFormat(e.getGradeFormat().getGradeId());
        e.setGradeFormat(setGradeFormat);

    }
}
