package com.dealtech.IntA.db

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.slf4j.LoggerFactory

class GCloudFileStorage(
    val bucketName: String,
    val storage: Storage = StorageOptions.getDefaultInstance().service
) : InterfaceFileStorage {

    private val logger = LoggerFactory.getLogger(GCloudFileStorage::class.java)

    override fun readFile(request: ReadFileRequest): ReadFileResponse {

        logger.info("Received realFile request: {}", request);
        return try {
            val blob = storage.get(BlobId.of(bucketName, request.fileName))
                ?: return ReadFileResponse(
                    content = null,
                    status = Status(false, "Object not found: ${request.fileName}", "NOT_FOUND")
                )

            val bytes = storage.readAllBytes(blob.blobId)
            ReadFileResponse(
                content = bytes,
                status = Status(true, null, null)
            )
        } catch (ex: Exception) {
            logger.error("Exception when trying to readFile with Request: {}. Error {}", request, ex.message);
            ReadFileResponse(
                content = null,
                status = Status(false, "Failed to read: ${ex.message}", "READ_ERROR")
            )
        }
    }

    override fun writeFile(request: WriteFileRequest): WriteFileResponse {
        return try {
            val blobInfo = BlobInfo.newBuilder(bucketName, request.fileName).build()
            storage.create(blobInfo, request.data)
            WriteFileResponse(
                status = Status(true, null, null)
            )
        } catch (ex: Exception) {
            WriteFileResponse(
                status = Status(false, "Failed to write: ${ex.message}", "WRITE_ERROR")
            )
        }
    }
}
