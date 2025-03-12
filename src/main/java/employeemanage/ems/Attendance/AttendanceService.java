package employeemanage.ems.Attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import employeemanage.ems.Employee;
import employeemanage.ems.EmployeeRepo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepository;

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private JavaMailSender mailSender;

    
    public Attendance markAttendance(Long employeeId, boolean status) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(currentDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(formattedDate, formatter);

        List<Attendance> existingAttendances = attendanceRepository.findByEmployeeIdAndDate(employee.getId(), localDate);
        if (!existingAttendances.isEmpty()) {
            throw new RuntimeException("Attendance for today is already marked.");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(localDate);
        attendance.setPresent(status);
        List<Attendance> previousAttendances = getAttendanceByEmployee(employee.getId());
        int daysWorked = (int) previousAttendances.stream().filter(Attendance::isPresent).count();
    
        

        // Only increment days if the employee is present
        if (attendance.isPresent()) {
            attendance.setDays(daysWorked + 1);  
        }
        else
        {
            attendance.setDays(daysWorked);
            sendEmail(employeeId);
        }
        return attendanceRepository.save(attendance);
    }
    

    // Get Attendance by Date
    public List<AttendanceResponseDTO> getAttendanceByDate(Date currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(currentDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(formattedDate, formatter);
        List<Attendance> allAttendance = attendanceRepository.findByDate(localDate);
        return allAttendance.stream()
        .map(attendance -> new AttendanceResponseDTO(
            attendance.getDate(), 
            attendance.getEmployee().getId(), 
            attendance.getEmployee().getName(),
            attendance.isPresent())) 
        .collect(Collectors.toList());
    }

    // Get All Attendance Records
    public List<AttendanceResponseDTO> getAllAttendance() {
        List<Attendance> allAttendances = attendanceRepository.findAll();
        
        
        return allAttendances.stream()
                .map(attendance -> new AttendanceResponseDTO(
                    attendance.getDate(), // Assuming `attendance.getDate()` is a Date or similar
                    attendance.getEmployee().getId(), // Assuming `Attendance` has a reference to `Employee`
                    attendance.getEmployee().getName(), // Assuming `Attendance` has a reference to `Employee`
                    attendance.isPresent())) // Assuming `attendance.isPresent()` indicates presence
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDTO> getAttendanceByEmployeeDTO(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    
        
        // Map the list of Attendances to the custom DTO, formatting the date
        return employee.getAttendances().stream()
                .map(attendance -> new AttendanceResponseDTO(
                    attendance.getDate(), // Format the date
                    employee.getId(),
                    employee.getName(),
                    attendance.isPresent()))
                .collect(Collectors.toList());
    }
    

        public List<Attendance> getAttendanceByEmployee(Long employeeId) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
            return employee.getAttendances(); // Get all attendance records for the employee
        }

        private void sendEmail(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new RuntimeException("Employee not found"));
        String to = employee.getEmailId();  // Assuming Employee has an 'email' field

        // Create a simple email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Attendance Notification");
        message.setText("Dear " + employee.getName() + ",\n\n" +
                        "We noticed that you were absent today. Please ensure you mark your attendance " +
                        "or inform HR if this was a mistake.\n\nBest regards,\nYour Company");

        try {
            // Send the email using the JavaMailSender
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}