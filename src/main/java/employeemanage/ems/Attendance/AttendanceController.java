package employeemanage.ems.Attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Mark Attendance for an Employee
    @PostMapping("/mark/{employeeId}")
    public ResponseEntity<Attendance> markAttendance(
            @PathVariable Long employeeId,
            @RequestParam boolean status) {
        Attendance attendance = attendanceService.markAttendance(employeeId, status);
        return ResponseEntity.ok(attendance);
    }

    // Get Attendance by Employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceByEmployee(@PathVariable Long employeeId) {
        List<AttendanceResponseDTO> attendance = attendanceService.getAttendanceByEmployeeDTO(employeeId);
        return ResponseEntity.ok(attendance);
    }

    // Get Attendance by Date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceByDate( @PathVariable("date")
        @DateTimeFormat(pattern = "dd-MM-yyyy") Date date) {
            List<AttendanceResponseDTO> attendance = attendanceService.getAttendanceByDate(date);
            return ResponseEntity.ok(attendance);
    }

    // Get All Attendance Records
    @GetMapping
    public ResponseEntity<List<AttendanceResponseDTO>> getAllAttendance() {
        List<AttendanceResponseDTO> attendance = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendance);
    }
}

