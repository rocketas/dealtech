package com.dealtech.IntA.db

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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
        val failed_mocked_response = ReadFileResponse(
            content = content,
            status = Status(success = false, errorMessage = null, errorCode = null)
        )
        `when`(cloudStorage.readFile(ReadFileRequest(fileName))).thenReturn(failed_mocked_response)

        mockMvc.perform(
            get("/api/files/read")
                .param("fileName", fileName)
        )
            .andExpect(status().isNotFound());
    }

}
