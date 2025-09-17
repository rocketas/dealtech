package com.dealtech.db


public data class Status(val success: Boolean, val errorMessage: String?, val errorCode: String?);

public data class ReadFileRequest(val fileName: String);
public data class ReadFileResponse(
    // If status.success = false, content will be empty
    val content: ByteArray?,
    val status: Status,
);

public data class WriteFileRequest(val fileName: String, val data: ByteArray );
public data class WriteFileResponse(
    val status: Status,
);

public interface InterfaceFileStorage {

    fun readFile(request: ReadFileRequest): ReadFileResponse;
    fun writeFile(request: WriteFileRequest): WriteFileResponse;

}