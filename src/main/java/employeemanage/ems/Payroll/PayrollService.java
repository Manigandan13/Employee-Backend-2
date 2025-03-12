package employeemanage.ems.Payroll;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import employeemanage.ems.Employee;
import employeemanage.ems.EmployeeService;
import employeemanage.ems.Attendance.Attendance;
import employeemanage.ems.Attendance.AttendanceService;

@Service
public class PayrollService {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    public Long calculatePay(Long employeeId) {
        // Get the employee by ID
        Employee employee = employeeService.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        
        // Assuming payScale represents the daily wage
        long dailyPay = employee.getPayScale(); // Pay scale is the daily wage

        // Calculate the number of days worked
        List<Attendance> attendance1 = attendanceService.getAttendanceByEmployee(employeeId);
        long daysWorked= attendance1.size();
        
        return dailyPay * daysWorked;
    }

}

