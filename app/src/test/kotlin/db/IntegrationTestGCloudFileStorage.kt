package com.dealtech.IntA.db

import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

@Tag("integration")
class GCloudFileStorageIT {

    @Test
    fun `Reading GCStorage succeeds`() {
        val storage = GCloudFileStorage("dealtech-testing-bucket")

        val read = storage.readFile(ReadFileRequest("test_data_bucket.docx"));

        assertTrue(read.status.success);
    }
}
