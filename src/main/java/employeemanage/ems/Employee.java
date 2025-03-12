package employeemanage.ems;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import employeemanage.ems.Attendance.Attendance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String emailId;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Long payScale;

    @Column(nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy")
    private Date dob;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, unique = true)
    private String panNumber;

    private String imageType;
    private String imageName;
    @Lob
    private byte[] image;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Attendance> attendances;


    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", emailId=" + emailId + ", mobileNumber=" + mobileNumber
                + ", gender=" + gender + ", payScale=" + payScale + ", dob=" + dob + ", role=" + role + ", panNumber="
                + panNumber + ", imageType=" + imageType + ", imageName=" + imageName + ",image="+ image+ "]";
    }

}