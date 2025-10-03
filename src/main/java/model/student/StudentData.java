package model.student;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentData {
  private int studentId;
  private String studentNumber;
  private String lastName;
  private String firstName;
  private String lastNameKana;
  private String firstNameKana;
  private LocalDate birthDate;
  private Integer genderId;
  private String postalCode;
  private String prefecture;
  private String city;
  private String addressLine;
  private String tel;
  private int schoolId;
  private LocalDate enrollmentDate;
  private LocalDate graduationDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
//追加フィールド
private String genderName;   // 表示用
private String schoolName;   // 表示用

public String getGenderName() { return genderName; }
public void setGenderName(String genderName) { this.genderName = genderName; }
public String getSchoolName() { return schoolName; }
public void setSchoolName(String schoolName) { this.schoolName = schoolName; }


  // --- getters/setters ---
  public int getStudentId() { return studentId; }
  public void setStudentId(int studentId) { this.studentId = studentId; }
  public String getStudentNumber() { return studentNumber; }
  public void setStudentNumber(String v) { this.studentNumber = v; }
  public String getLastName() { return lastName; }
  public void setLastName(String v) { this.lastName = v; }
  public String getFirstName() { return firstName; }
  public void setFirstName(String v) { this.firstName = v; }
  public String getLastNameKana() { return lastNameKana; }
  public void setLastNameKana(String v) { this.lastNameKana = v; }
  public String getFirstNameKana() { return firstNameKana; }
  public void setFirstNameKana(String v) { this.firstNameKana = v; }
  public LocalDate getBirthDate() { return birthDate; }
  public void setBirthDate(LocalDate v) { this.birthDate = v; }
  public Integer getGenderId() { return genderId; }
  public void setGenderId(Integer v) { this.genderId = v; }
  public String getPostalCode() { return postalCode; }
  public void setPostalCode(String v) { this.postalCode = v; }
  public String getPrefecture() { return prefecture; }
  public void setPrefecture(String v) { this.prefecture = v; }
  public String getCity() { return city; }
  public void setCity(String v) { this.city = v; }
  public String getAddressLine() { return addressLine; }
  public void setAddressLine(String v) { this.addressLine = v; }
  public String getTel() { return tel; }
  public void setTel(String v) { this.tel = v; }
  public int getSchoolId() { return schoolId; }
  public void setSchoolId(int v) { this.schoolId = v; }
  public LocalDate getEnrollmentDate() { return enrollmentDate; }
  public void setEnrollmentDate(LocalDate v) { this.enrollmentDate = v; }
  public LocalDate getGraduationDate() { return graduationDate; }
  public void setGraduationDate(LocalDate v) { this.graduationDate = v; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }

  // 表示用
  public String getFullName() { return (lastName==null?"":lastName) + " " + (firstName==null?"":firstName); }
  public String getFullNameKana() { return (lastNameKana==null?"":lastNameKana) + " " + (firstNameKana==null?"":firstNameKana); }
}
