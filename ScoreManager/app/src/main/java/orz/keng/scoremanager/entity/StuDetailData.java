package orz.keng.scoremanager.entity;

public class StuDetailData {
    private String stuName;
    private Double gradePoint;
    private Double activityPoint;
    private Double statisPoint;
    private Long stuCode;
    private String className;

    @Override
    public String toString() {
        return "StuDetailData{" +
                "stuName='" + stuName + '\'' +
                ", gradePoint=" + gradePoint +
                ", activityPoint=" + activityPoint +
                ", statisPoint=" + statisPoint +
                ", stuCode=" + stuCode +
                ", className='" + className + '\'' +
                '}';
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public Double getActivityPoint() {
        return activityPoint;
    }

    public void setActivityPoint(Double activityPoint) {
        this.activityPoint = activityPoint;
    }

    public Double getStatisPoint() {
        return statisPoint;
    }

    public void setStatisPoint(Double statisPoint) {
        this.statisPoint = statisPoint;
    }

    public Long getStuCode() {
        return stuCode;
    }

    public void setStuCode(Long stuCode) {
        this.stuCode = stuCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
