package com.trms.repository;

import com.trms.models.File;

import java.util.List;

public interface FileRepo {

    public File addFile(File f);

    public List<File> getAllFiles();

    public List<File> getAllFiles(int eventId);

    public File getFile(int fileId);

    public File updateFile(File change);

    public File deleteFile(int fileId);

}
