package ru.otus.hw.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.SystemInfo;


@RestController
public class SystemInfoController {

    @GetMapping("api/server/system/info")
    public ResponseEntity<SystemInfo> getServerSystemInfo(SystemInfo systemInfo) {
        return ResponseEntity.ok(systemInfo);
    }
}
