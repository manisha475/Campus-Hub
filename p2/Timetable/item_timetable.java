package com.example.p2.Timetable;

public class item_timetable {

    String cname;
    String ctime;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public item_timetable(String cname, String ctime) {
        this.cname = cname;
        this.ctime = ctime;
    }
}
