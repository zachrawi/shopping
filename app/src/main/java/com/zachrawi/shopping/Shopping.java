package com.zachrawi.shopping;

public class Shopping {
    boolean done;
    String description;

    public Shopping(boolean done, String description) {
        this.done = done;
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
