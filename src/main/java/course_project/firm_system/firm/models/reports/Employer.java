package course_project.firm_system.firm.models.reports;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employer implements Comparable<Employer> {

  private int id;

  private String name;

  @Override
  public int compareTo(Employer o) {
    return Integer.compare(this.getId(), o.getId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employer employer = (Employer) o;
    return id == employer.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
