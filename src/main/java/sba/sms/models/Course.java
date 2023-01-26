package sba.sms.models;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="course")
public class Course {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;
   
   @NonNull
   @Column(name="name", nullable=false, length=50)
   String name;
   
   @NonNull
   @Column(name="instructor", nullable=false, length=50)
   String instructor;
   
   @ToString.Exclude
   @ManyToMany(mappedBy="courses", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH},
			   fetch = FetchType.EAGER)
	List<Student> students = new LinkedList<Student>();
   
   @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course c = (Course) obj;
		return id == c.id && Objects.equals(name, c.name)&& Objects.equals(instructor, c.instructor);
	}

	@Override
	public int hashCode() {
		return Objects.hash( id, name, instructor);
	}
}
