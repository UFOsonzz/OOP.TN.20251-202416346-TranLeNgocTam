import java.util.Date;

public class DateTimeModel {
    Date currentDate;
    DateTimeModel() {
        currentDate = new Date();
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public String getCurrentDateString() {
        return currentDate.toString();
    }

    public void IncreaseOneDay() {
        currentDate.setTime(currentDate.getTime() + 24 * 60 * 60 * 1000);
    }

    public void DecreaseOneDay() {
        currentDate.setTime(currentDate.getTime() - 24 * 60 * 60 * 1000);
    }

}
