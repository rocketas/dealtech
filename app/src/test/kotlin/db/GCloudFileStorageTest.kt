package com.dealtech.IntA.db

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertFalse

class GCloudFileStorageTest {

    private val storage: InterfaceFileStorage = GCloudFileStorage()

    @Test
    fun `readFile returns failure status and null content`() {
        val request : ReadFileRequest = ReadFileRequest(fileName = "any-file.txt");

        val response : ReadFileResponse = storage.readFile(request);

        val expectedResponse : ReadFileResponse  = ReadFileResponse(
            content = null,
            status = Status(false, "Reading Files is not supported", "N/A")
        );
        assertEquals(response, expectedResponse);
    }

    @Test
    fun `writeFile returns failure status`() {
        val data = "hello".toByteArray();
        val request = WriteFileRequest(fileName = "any-file.txt", data = data);

        val response = storage.writeFile(request);

        val expectedResponse = WriteFileResponse(
            status = Status(false, "Writing Files is not supported", "N/A")
        );
        assertEquals(response, expectedResponse);

    }
}
