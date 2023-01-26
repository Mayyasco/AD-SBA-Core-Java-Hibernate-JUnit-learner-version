package sba.sms.models;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="student")
public class Student {
   @NonNull
   @Id
   @Column(name="email",length=50)
   String email;
   
   @NonNull
   @Column(name="name", nullable=false, length=50)
   String name;
   
   @NonNull
   @Column(name="password", nullable=false, length=50)
   String password;
   
   @ToString.Exclude
   @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH},
			   fetch = FetchType.EAGER)
   @JoinTable(name="student_courses",
	joinColumns = @JoinColumn(name="student_email"),
	inverseJoinColumns = @JoinColumn(name="courses_id"))
	List<Course> courses = new LinkedList<Course>();
   
   public void addCourse(Course c) {
	   courses.add(c);
		c.getStudents().add(this);
	}
   @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student s = (Student) obj;
		return Objects.equals(email, s.email)&& Objects.equals(name, s.name)&& Objects.equals(password, s.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash( email, name, password);
	}
}
