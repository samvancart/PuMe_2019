package pume2019.domain;

import java.util.ArrayList;
import java.util.List;

public class Notifications {

    private List<Notification> notifications;

    public Notifications() {
        this.notifications = new ArrayList<>();
    }

    public void addNotification(Notification notification) {
        if (notification != null) {
            this.notifications.add(notification);
        }
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    
    public void clear(){
        this.notifications = new ArrayList<>();
    }

}
