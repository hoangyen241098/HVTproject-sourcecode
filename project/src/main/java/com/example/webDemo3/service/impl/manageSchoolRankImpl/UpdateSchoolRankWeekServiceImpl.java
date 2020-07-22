package com.example.webDemo3.service.impl.manageSchoolRankImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankWeekResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.UpdateSchoolRankWeekRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.SchoolRankWeek;
import com.example.webDemo3.entity.SchoolRankWeekId;
import com.example.webDemo3.entity.SchoolWeek;
import com.example.webDemo3.repository.SchoolRankWeekRepository;
import com.example.webDemo3.repository.SchoolWeekRepository;
import com.example.webDemo3.service.manageSchoolRank.SortSchoolRankWeekService;
import com.example.webDemo3.service.manageSchoolRank.UpdateSchoolRankWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/*
kimpt142 - 21/07
 */
@Service
public class UpdateSchoolRankWeekServiceImpl implements UpdateSchoolRankWeekService {

    @Autowired
    private SchoolRankWeekRepository schoolRankWeekRepository;

    @Autowired
    private SchoolWeekRepository schoolWeekRepository;

    @Autowired
    private SortSchoolRankWeekService sortSchoolRankWeekService;

    /**
     * kimpt142
     * 21/07
     * update the rank week list after user edit
     * @param model
     * @return
     */
    @Override
    public MessageDTO updateSchoolRankWeek(UpdateSchoolRankWeekRequestDto model) {
        List<RankWeekResponseDto> rankWeekList = model.getRankWeekList();
        MessageDTO message = new MessageDTO();
        List<SchoolRankWeek> schoolRankWeekList = new ArrayList<>();
        if(rankWeekList != null && rankWeekList.size() != 0) {
            Integer weekId = rankWeekList.get(0).getWeekId();
            SchoolWeek schoolWeek = schoolWeekRepository.findSchoolWeekByWeekID(weekId);
            if(schoolWeek == null || schoolWeek.getMonthID() != 0){
                message = Constant.RANKWEEK_NOT_EDIT;
                return message;
            }
            for (RankWeekResponseDto item : rankWeekList) {
                if(item.getLearningGrade() > Constant.LEARNING_GRADE){
                    message = Constant.LEARNINGGRADE_GREATER;
                    return message;
                }
                else if(item.getMovementGrade() > Constant.MOVEMENT_GRADE){
                    message = Constant.MOVEMENTGRADE_GREATER;
                    return message;
                }
                else if(item.getLaborGrade() > Constant.LABOR_GRADE){
                    message = Constant.LABORGRADE_GREATER;
                    return message;
                }
                else {
                    SchoolRankWeek schoolRankWeek = updateSchoolRankWeek(item);
                    schoolRankWeekList.add(schoolRankWeek);
                }
            }

            schoolRankWeekList = sortSchoolRankWeekService.arrangeSchoolRankWeek(schoolRankWeekList);

            try {
                for (SchoolRankWeek item : schoolRankWeekList) {
                    schoolRankWeekRepository.save(item);
                }
            } catch (Exception e) {
                message.setMessageCode(1);
                message.setMessage(e.toString());
                return message;
            }
        }
        else{
            message = Constant.RANKLIST_EMPTY;
            return message;
        }
        message = Constant.SUCCESS;
        return message;
    }

    /**
     * kimpt142
     * 21/07
     * update a rank week response dto into school rank week table
     * @param responseDto
     */
    private SchoolRankWeek updateSchoolRankWeek(RankWeekResponseDto responseDto){
        SchoolRankWeek schoolRankWeek = new SchoolRankWeek();
        SchoolRankWeekId schoolRankWeekId = new SchoolRankWeekId();

        schoolRankWeekId.setWEEK_ID(responseDto.getWeekId());
        schoolRankWeekId.setSchoolClass(new Class(responseDto.getClassId()));

        Double learningGrade = responseDto.getLearningGrade();
        Double movementGrade = responseDto.getMovementGrade();
        Double laborGrade = responseDto.getLaborGrade();
        Double newTotalGrade = learningGrade + movementGrade + laborGrade + responseDto.getEmulationGrade();
        schoolRankWeek.setSchoolRankWeekId(schoolRankWeekId);
        schoolRankWeek.setLearningGrade(learningGrade);
        schoolRankWeek.setMovementGrade(movementGrade);
        schoolRankWeek.setLaborGrade(laborGrade);
        schoolRankWeek.setTotalGrade(newTotalGrade);
        schoolRankWeek.setEmulationGrade(responseDto.getEmulationGrade());
        schoolRankWeek.setHistory(responseDto.getHistory());

        return schoolRankWeek;
    }
}
