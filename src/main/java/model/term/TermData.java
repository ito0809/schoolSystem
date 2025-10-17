package model.term;

public class TermData {
  private Integer termId;
  private String  termName;
  private Integer termOrder; // null 可

  public Integer getTermId() { return termId; }
  public void setTermId(Integer termId) { this.termId = termId; }

  public String getTermName() { return termName; }
  public void setTermName(String termName) { this.termName = termName; }

  public Integer getTermOrder() { return termOrder; }
  public void setTermOrder(Integer termOrder) { this.termOrder = termOrder; }
}
