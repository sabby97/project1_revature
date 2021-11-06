package com.trms.repository;

import com.trms.models.Status;

import java.util.List;

public interface StatusRepo {

    public Status addStatus(Status s);

    public List<Status> getAllStatuses();

    public Status getStatus(int statusId);

    public Status updateStatus(Status change);

    public Status deleteStatus(int statusId);

}
