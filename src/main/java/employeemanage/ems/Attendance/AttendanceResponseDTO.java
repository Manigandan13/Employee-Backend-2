package employeemanage.ems.Attendance;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AttendanceResponseDTO {

   @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy")
   private LocalDate date;
   private Long employeeId;
   private String employeeName;
   private boolean present;

   public AttendanceResponseDTO(LocalDate date, Long employeeId, String employeeName,boolean present) {
    this.date = date;
    this.employeeId = employeeId;
    this.employeeName = employeeName;
    this.present = present;
}

}

