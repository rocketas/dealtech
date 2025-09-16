package com.dealtech.IntA.db;

class GCloudFileStorage : InterfaceFileStorage {

    override fun readFile(request: ReadFileRequest): ReadFileResponse {
        return ReadFileResponse(
            content = null,
            status = Status(false, "Reading Files is not supported", "N/A")
        )
    }

    override fun writeFile(request: WriteFileRequest): WriteFileResponse {
        return WriteFileResponse(
            status = Status(false, "Writing Files is not supported", "N/A")
        )
    }

}