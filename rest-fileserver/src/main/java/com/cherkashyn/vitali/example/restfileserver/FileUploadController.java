package com.cherkashyn.vitali.example.restfileserver;

import com.cherkashyn.vitali.example.restfileserver.event.ReceiveFileEvent;
import com.cherkashyn.vitali.example.restfileserver.service.storage.exception.StorageFileNotFoundException;
import com.cherkashyn.vitali.example.restfileserver.service.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final Storage storage;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    public FileUploadController(Storage storage) {
        this.storage = storage;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storage.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    // curl -X GET  http://127.0.0.1:8080/image.png
    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storage.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    // curl -X PUT -H "Content-Type: multipart/form-data" -F "file=@uploader-local.png;filename=image.png" http://127.0.0.1:8080
    @PutMapping(value = "/") // , consumes = "multipart/form-data"
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        eventPublisher.publishEvent(new ReceiveFileEvent(file));
        return ResponseEntity.ok().body("OK");
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
