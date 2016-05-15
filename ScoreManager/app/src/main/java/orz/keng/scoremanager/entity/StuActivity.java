package orz.keng.scoremanager.entity;

public class StuActivity {
    private Long id;
    private Long stuCode;
    private String activityName;
    private Double activityPoint;

    @Override
    public String toString() {
        return "StuActivity{" +
                "id=" + id +
                ", stuCode=" + stuCode +
                ", activityName='" + activityName + '\'' +
                ", activityPoint=" + activityPoint +
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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Double getActivityPoint() {
        return activityPoint;
    }

    public void setActivityPoint(Double activityPoint) {
        this.activityPoint = activityPoint;
    }
}
