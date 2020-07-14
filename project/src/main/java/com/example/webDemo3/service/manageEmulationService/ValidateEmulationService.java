package com.example.webDemo3.service.manageEmulationService;

import java.sql.Date;

/*
kimpt142 - 14/07
 */
public interface ValidateEmulationService {
    Boolean checkRoleForEmulate(Integer classID, String username, Date dayEmulate);
    Boolean checkRankedDate(Date date);
}
