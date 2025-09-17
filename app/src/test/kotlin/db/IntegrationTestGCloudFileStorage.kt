package com.dealtech.db

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.assertNotNull

@Tag("integration")
class GCloudFileStorageIT {

    @Test
    fun `Reading GCStorage succeeds`() {
        val storage = GCloudFileStorage("dealtech-testing-bucket")

        val readFileResponse: ReadFileResponse = storage.readFile(ReadFileRequest("test_data_bucket.docx"));

        assertTrue(readFileResponse.status.success);
        assertNotNull(readFileResponse.content);
    }
}
