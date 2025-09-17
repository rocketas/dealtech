package com.dealtech.db

import com.google.cloud.storage.Blob
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class GCloudFileStorageTest {

    private val storage = mock(Storage::class.java)
    private val bucket = "dealtech-testing-bucket"
    private val gcs = GCloudFileStorage(bucketName = bucket, storage = storage)

    @Test
    fun `readFile returns success and bytes when object exists`() {
        val name = "hello.txt"
        val blobId = BlobId.of(bucket, name)
        val blob = mock(Blob::class.java)
        val data = "hello".toByteArray()
        `when`(storage.get(eq(blobId))).thenReturn(blob)
        `when`(blob.blobId).thenReturn(blobId)
        `when`(storage.readAllBytes(eq(blobId))).thenReturn(data)

        val resp = gcs.readFile(ReadFileRequest(name))

        assertTrue(resp.status.success)
        assertNull(resp.status.errorMessage)
        assertNull(resp.status.errorCode)
        assertNotNull(resp.content)
        assertEquals("hello", String(resp.content!!))
    }
}