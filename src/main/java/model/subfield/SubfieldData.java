package model.subfield;

public class SubfieldData {
    private Integer subfieldId;
    private Integer fieldId;
    private String  subfieldName;

    // ★ 追加
    private String  fieldName;

    // getter / setter
    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    // 既存の getter/setter はそのまま

  public Integer getSubfieldId() { return subfieldId; }
  public void setSubfieldId(Integer subfieldId) { this.subfieldId = subfieldId; }

  public Integer getFieldId() { return fieldId; }
  public void setFieldId(Integer fieldId) { this.fieldId = fieldId; }

  public String getSubfieldName() { return subfieldName; }
  public void setSubfieldName(String subfieldName) { this.subfieldName = subfieldName; }
}
