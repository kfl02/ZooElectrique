package nl.rumfumme.zoo;

import nl.rumfumme.pt.ProcessingApp;

public abstract class ZooApp extends ProcessingApp {
    protected int start_time;

    @Override
    public void init() {
        start_time = millis();
    }

    @Override
    public boolean finished() {
        if(millis() - start_time > 90 * 1000) {
            return true;
        }
        return false;
    }
}
