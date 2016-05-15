package orz.keng.scoremanager.entity;

public class StuBaseInfo {
    private Long id;
    private Long stuCode;
    private String stuName;
    private String className;
    private Double gradePoint;

    @Override
    public String toString() {
        return "StuBaseInfo{" +
                "id=" + id +
                ", stuCode=" + stuCode +
                ", stuName='" + stuName + '\'' +
                ", className='" + className + '\'' +
                ", gradePoint=" + gradePoint +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStuCode() {
        return stuCode;
    }

    public void setStuCode(Long stuCode) {
        this.stuCode = stuCode;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Double gradePoint) {
        this.gradePoint = gradePoint;
    }
}
