package com.myapplication.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(String uuid) {
        super("\nNote " + uuid + " is  exist", uuid+"\n");
    }
}