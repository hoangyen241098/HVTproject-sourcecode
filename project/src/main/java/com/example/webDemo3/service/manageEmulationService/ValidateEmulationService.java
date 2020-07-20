package com.example.webDemo3.service.manageEmulationService;

import java.sql.Date;
import java.sql.Time;

/*
kimpt142 - 14/07
 */
public interface ValidateEmulationService {
    Boolean checkRoleForEmulate(Integer classID, String username, Date dayEmulate);
    Boolean checkRankedDateByViolationId(Long violationClassId);
    Integer getDayIdByDate(Date date);
    Boolean checkMonitorOfClass(Integer classId, String username);
    Boolean checkRoleForAddViolationClass(String username, Integer roleId,Integer classId, Date addDate);
    Boolean checkRoleForEditViolationClass(String username, Integer roleId,Integer classId, Date date);
}
