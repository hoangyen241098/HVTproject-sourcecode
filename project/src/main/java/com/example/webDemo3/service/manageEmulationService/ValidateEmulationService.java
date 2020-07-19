package com.example.webDemo3.service.manageEmulationService;

import java.sql.Date;

/*
kimpt142 - 14/07
 */
public interface ValidateEmulationService {
    Boolean checkRoleForEmulate(Integer classID, String username, Date dayEmulate);
    Boolean checkRankedDate(Integer classId,Date date);
    Boolean checkRankedDateByViolationId(Long violationClassId);
    Integer getDayIdByDate(Date date);
    Boolean checkMonitorOfClass(Integer classId, String username);
}
