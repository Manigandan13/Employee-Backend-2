package employeemanage.ems.Payroll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import employeemanage.ems.Attendance.Attendance;
import employeemanage.ems.Attendance.AttendanceService;



@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;
    @Autowired
    private AttendanceService attendance;

    // Calculate days worked for an employee in a given date range
    @GetMapping("/days/{employeeId}")
    public ResponseEntity<Long> calculateDaysWorked(
            @PathVariable Long employeeId) {
        
        List<Attendance> previousAttendances = attendance.getAttendanceByEmployee(employeeId);
        long daysWorked = (int) previousAttendances.stream().filter(Attendance::isPresent).count();
    
        return ResponseEntity.ok(daysWorked);
    }

    // Calculate pay for an employee in a given date range
    @GetMapping("/pay/{employeeId}")
    public ResponseEntity<Double> calculatePay(@PathVariable Long employeeId) {
        double totalPay = payrollService.calculatePay(employeeId);
        return ResponseEntity.ok(totalPay);
    }


}

