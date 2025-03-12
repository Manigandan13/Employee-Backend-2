package employeemanage.ems.Attendance;

import employeemanage.ems.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "attendance")
@Data
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore // Prevent circular reference when serializing Attendance -> Employee
    private Employee employee;


    @Column(nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Column(nullable = false)
    private boolean present;

    private int days=0;

    @Override
    public String toString() {
        Long employeeId = (employee != null) ? employee.getId() : null;
        return "Attendance [id=" + id + ", date=" + date + ", present=" + present + ", days=" + days
                + ", employeeId=" + employeeId + "]";
    }
}
