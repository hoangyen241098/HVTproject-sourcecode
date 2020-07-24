package com.example.webDemo3.controller;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankYearListResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.LoadByYearIdRequestDto;
import com.example.webDemo3.service.manageSchoolRankYearSerivce.ViewSchoolRankYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListSemesterSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ViewSemesterOfEditRankYearRequestDto;
import com.example.webDemo3.service.manageSchoolRankYearSerivce.CreateAndEditSchoolRankYearService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

/*
kimpt142 - 23/07
 */
@RestController
@RequestMapping("/api/rankyear")
public class RankYearApiContoller {

    @Autowired
    private ViewSchoolRankYearService viewSchoolRankYearService;

    @Autowired
    private CreateAndEditSchoolRankYearService createAndEditSchoolRankYearService;

    /**
     * kimpt142
     * 24/07
     * catch request to get month list by year id
     * @return responseDTO with a month list and messagedto
     */
    @PostMapping("/searchrankyear")
    public RankYearListResponseDto searchRankYearById(@RequestBody LoadByYearIdRequestDto model)
    {
        return viewSchoolRankYearService.searchRankYearById(model);
    }

    /**
     * kimpt142
     * 24/07
     * catch request to download school rank year list
     * @param model include yearid
     * @return ResponseEntity
     */
    @PostMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestBody LoadByYearIdRequestDto model) {
        ByteArrayInputStream in = viewSchoolRankYearService.downloadRankYear(model);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Bangxephangnam.xls");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    /**
     * lamnt98
     * 24/07
     * catch request to get semester list which has no ranked
     * @return ListSemesterSchoolRankResponseDto
     */
    @PostMapping("/loadsemesterlist")
    public ListSemesterSchoolRankResponseDto getSemesterList()
    {
        return createAndEditSchoolRankYearService.loadListSemester();
    }

    /**
     * lamnt98
     * 24/07
     * catch request to get semester list which has no ranked and semester is ranked with yearId
     * @return ListSemesterSchoolRankResponseDto
     */
    @PostMapping("/loadeditrankyear")
    public ListSemesterSchoolRankResponseDto getEditSemesterList(@RequestBody ViewSemesterOfEditRankYearRequestDto module)
    {
        return createAndEditSchoolRankYearService.loadEditListSemester(module);
    }
}
