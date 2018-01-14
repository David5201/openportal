package weixin.guanjia.core.entity.common;

public class Face
  implements Comparable<Face>
{
  private String faceId;
  private int ageValue;
  private int ageRange;
  private String genderValue;
  private double genderConfidence;
  private String raceValue;
  private double raceConfidence;
  private double smilingValue;
  private double centerX;
  private double centerY;

  public String getFaceId()
  {
    return this.faceId;
  }

  public void setFaceId(String faceId) {
    this.faceId = faceId;
  }

  public int getAgeValue() {
    return this.ageValue;
  }

  public void setAgeValue(int ageValue) {
    this.ageValue = ageValue;
  }

  public int getAgeRange() {
    return this.ageRange;
  }

  public void setAgeRange(int ageRange) {
    this.ageRange = ageRange;
  }

  public String getGenderValue() {
    return this.genderValue;
  }

  public void setGenderValue(String genderValue) {
    this.genderValue = genderValue;
  }

  public double getGenderConfidence() {
    return this.genderConfidence;
  }

  public void setGenderConfidence(double genderConfidence) {
    this.genderConfidence = genderConfidence;
  }

  public String getRaceValue() {
    return this.raceValue;
  }

  public void setRaceValue(String raceValue) {
    this.raceValue = raceValue;
  }

  public double getRaceConfidence() {
    return this.raceConfidence;
  }

  public void setRaceConfidence(double raceConfidence) {
    this.raceConfidence = raceConfidence;
  }

  public double getSmilingValue() {
    return this.smilingValue;
  }

  public void setSmilingValue(double smilingValue) {
    this.smilingValue = smilingValue;
  }

  public double getCenterX() {
    return this.centerX;
  }

  public void setCenterX(double centerX) {
    this.centerX = centerX;
  }

  public double getCenterY() {
    return this.centerY;
  }

  public void setCenterY(double centerY) {
    this.centerY = centerY;
  }

  public int compareTo(Face face)
  {
    int result = 0;
    if (getCenterX() > face.getCenterX())
      result = 1;
    else
      result = -1;
    return result;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.common.Face
 * JD-Core Version:    0.6.2
 */