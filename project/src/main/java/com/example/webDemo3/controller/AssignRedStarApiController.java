package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.assignRedStarRequestDto.DownloadAssignRedStarRequestDto;
import com.example.webDemo3.dto.request.manageTimeTableRequestDto.CheckDateRequestDto;
import com.example.webDemo3.service.assignRedStarService.CreateAssignRedStarService;
import com.example.webDemo3.service.assignRedStarService.DownloadAssignRedStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.sql.Date;

@RestController
@RequestMapping("/api/assignRedStar")
public class AssignRedStarApiController {
    @Autowired
    private CreateAssignRedStarService assignRedStarService;

    @Autowired
    private DownloadAssignRedStarService downloadAssignRedStarService;

    @PostMapping("/checkDate")
    public MessageDTO checkDate(@RequestBody CheckDateRequestDto data)
    {
        return assignRedStarService.checkDate(data.getDate());
    }

    @PostMapping("/create")
    public MessageDTO create(@RequestBody CheckDateRequestDto data)
    {
        return assignRedStarService.create(data.getDate());
    }

    @PostMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestBody DownloadAssignRedStarRequestDto data)
    {
        ByteArrayInputStream in = downloadAssignRedStarService.download(data);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=phancong.xls");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));

    }
}
