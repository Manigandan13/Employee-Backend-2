package employeemanage.ems.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    
    List<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
    Attendance findByEmployeeId(Long employeeId);
    List<Attendance> findByDate(LocalDate date);
    void deleteByEmployeeId(Long employeeId);
   List<Attendance> findByEmployeeIdAndDateBetween(Long employeeId, Date startDate, Date endDate);
}

