package com.myapplication.exception;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(String uuid) {
        super("\nNote " + uuid + " is not exist", uuid + "\n");
    }
}