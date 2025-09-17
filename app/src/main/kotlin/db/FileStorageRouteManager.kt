package com.dealtech.db

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/files")
class FileStorageRouteManager(private val cloudStorage: InterfaceFileStorage) {

    @GetMapping("/read")
    fun readFile(@RequestParam fileName: String): ResponseEntity<Resource> {
        val resp = cloudStorage.readFile(ReadFileRequest(fileName));
        if (!resp.status.success || resp.content == null) {
            return ResponseEntity.notFound().build();
        }
        val resource = ByteArrayResource(resp.content);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .contentLength(resp.content.size.toLong())
            .body(resource);
    }

    @PostMapping(
        path = ["/write"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun writeFile(
        @RequestParam fileName: String,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<WriteFileResponse> {
        val resp = cloudStorage.writeFile(WriteFileRequest(fileName, file.bytes));
        val statusCode = if (resp.status.success) 200 else 500;
        return ResponseEntity.status(statusCode).body(resp);
    }
}
