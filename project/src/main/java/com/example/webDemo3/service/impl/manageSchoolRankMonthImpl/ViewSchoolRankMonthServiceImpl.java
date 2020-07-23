package com.example.webDemo3.service.impl.manageSchoolRankMonthImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.*;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankMonthRequestDto;
import com.example.webDemo3.entity.SchoolMonth;
import com.example.webDemo3.entity.SchoolRankMonth;
import com.example.webDemo3.repository.SchoolMonthRepository;
import com.example.webDemo3.repository.SchoolRankMonthRepository;
import com.example.webDemo3.service.manageSchoolRankMonthService.ViewSchoolRankMonthService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/*
kimp142 - 23/07
 */
@Service
public class ViewSchoolRankMonthServiceImpl implements ViewSchoolRankMonthService {

    @Autowired
    private SchoolMonthRepository schoolMonthRepository;

    @Autowired
    private SchoolRankMonthRepository schoolRankMonthRepository;

    /**
     * kimpt142
     * 23/07
     * get all month in school months table exclude monthid = 0
     * @return
     */
    @Override
    public ViewMonthListResponseDto getMonthList() {
        ViewMonthListResponseDto responseDto = new ViewMonthListResponseDto();
        List<SchoolMonthResponseDto> schoolMonthListDto = new ArrayList<>();
        MessageDTO message = new MessageDTO();

        List<SchoolMonth> schoolMonthList = schoolMonthRepository.findSchoolMonthExcludeZero();
        if(schoolMonthList != null && schoolMonthList.size() != 0){
            for(SchoolMonth item : schoolMonthList)
            {
                SchoolMonthResponseDto monthDto = new SchoolMonthResponseDto();
                monthDto.setMonthId(item.getMonthId());
                monthDto.setMonthName("Tuần " + item.getMonth());
                schoolMonthListDto.add(monthDto);
            }
        }
        else{
            message = Constant.MONTHLIST_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        message = Constant.SUCCESS;
        responseDto.setSchoolMonthList(schoolMonthListDto);
        responseDto.setMessage(message);
        return responseDto;
    }

    /**
     * kimpt142
     * 23/07
     * get information rank with month id
     * @param model
     * @return
     */
    @Override
    public RankMonthListResposeDto searchRankMonthByMonthId(SearchRankMonthRequestDto model) {
        RankMonthListResposeDto resposeDto = new RankMonthListResposeDto();
        List<RankMonthResponseDto> rankMonthDtoList = new ArrayList<>();
        MessageDTO message;

        Integer monthId = model.getMonthId();
        if(monthId == null){
            message = Constant.MONTHID_EMPTY;
            resposeDto.setMessage(message);
            return resposeDto;
        }

        List<SchoolRankMonth> schoolRankMonthList = schoolRankMonthRepository.findAllBySchoolRankMonthId(monthId);
        if(schoolRankMonthList == null || schoolRankMonthList.size() == 0)
        {
            message = Constant.RANKMONTHLIST_EMPTY;
            resposeDto.setMessage(message);
            return resposeDto;
        }
        else{
            for(SchoolRankMonth item : schoolRankMonthList) {
                RankMonthResponseDto rankMonthDto = new RankMonthResponseDto();
                rankMonthDto.setClassId(item.getSchoolRankMonthId().getSchoolClass().getClassId());
                rankMonthDto.setClassName(item.getSchoolRankMonthId().getSchoolClass().getGrade()+ " "
                        + item.getSchoolRankMonthId().getSchoolClass().getGiftedClass().getName());
                rankMonthDto.setTotalGradeWeek(round(item.getTotalGradeWeek()));
                rankMonthDto.setTotalRankWeek(item.getTotalRankWeek());
                rankMonthDto.setRank(item.getRank());
                rankMonthDto.setHistory(item.getHistory());
                rankMonthDtoList.add(rankMonthDto);
            }
        }

        message = Constant.SUCCESS;
        resposeDto.setMessage(message);
        resposeDto.setRankMonthList(rankMonthDtoList);
        return resposeDto;
    }

    /**
     * download the rank month using month id
     * @param model
     * @return
     */
    @Override
    public ByteArrayInputStream downloadRankMonth(SearchRankMonthRequestDto model) {
        //get rank week list
        List<RankMonthResponseDto> rankMonthList = searchRankMonthByMonthId(model).getRankMonthList();

        String[] COLUMNs = {"Lớp", "Tổng thứ tự", "Tổng điểm", "Xếp hạng"};

        try {
            Workbook workbook = new HSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            //CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Bảng xếp hạng tháng");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (RankMonthResponseDto item : rankMonthList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(item.getClassName());
                row.createCell(1).setCellValue(item.getTotalRankWeek());
                row.createCell(2).setCellValue(round(item.getTotalGradeWeek()));
                row.createCell(3).setCellValue(item.getRank());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        }catch (Exception e){
            System.out.println(e);
        }

        return null;
    }

    private double round(Double input) {
        return (double) Math.round(input * 100) / 100;
    }
}
