package nl.rumfumme.zoo2;

import com.badlogic.gdx.utils.Timer;

public class CageTimer extends Timer {
    private Task task;
    private float seconds;

    public CageTimer(Task task, float seconds) {
        this.task = task;
        this.seconds = seconds;
    }
    
    public void restart() {
        task.cancel();
        scheduleTask(task, seconds, seconds);
        start();
    }
}
