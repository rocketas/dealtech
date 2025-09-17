package com.dealtech.db

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(FileStorageRouteManager::class)
class FileStorageRouteManagerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var cloudStorage: InterfaceFileStorage

    @Test
    fun `GET returns failure from fileStorage fails`() {
        val fileName = "doc.txt"
        val content = "hello".toByteArray()
        val failedMockedResponse = ReadFileResponse(
            content = content,
            status = Status(success = false, errorMessage = null, errorCode = null)
        )
        `when`(cloudStorage.readFile(ReadFileRequest(fileName))).thenReturn(failedMockedResponse)

        mockMvc.perform(
            get("/api/files/read")
                .param("fileName", fileName)
        )
            .andExpect(status().isNotFound());
    }

}
