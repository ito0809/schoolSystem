package model.subject;

import java.math.BigDecimal;

public class SubjectData {
  private Integer subjectId;
  private Integer subfieldId;
  private String subjectName;
  private BigDecimal credits; // null 可

  public Integer getSubjectId() { return subjectId; }
  public void setSubjectId(Integer subjectId) { this.subjectId = subjectId; }

  public Integer getSubfieldId() { return subfieldId; }
  public void setSubfieldId(Integer subfieldId) { this.subfieldId = subfieldId; }

  public String getSubjectName() { return subjectName; }
  public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

  public BigDecimal getCredits() { return credits; }
  public void setCredits(BigDecimal credits) { this.credits = credits; }
}
