package nl.rumfumme.zooelectrique.cages.helloanybody;

public class Constants {
    /*--- Public constants --------------------------------------------------*/

    public static final int aspectRatioX = 16;
    public static final int aspectRatioY = 9;
    public static final float aspectRatio = (float) aspectRatioX / (float) aspectRatioY;
    public static final int videoWidth = 1024;
    public static final int videoHeight = videoWidth * aspectRatioY / aspectRatioX;
    public static final int videoSize = videoWidth * videoHeight;
    public static final int numDivisions = 7;
    public static final int numVideoElements = numDivisions * numDivisions;
    public static final int borderRatio = 32;
    public static final int videoWithBorderHeight = videoHeight + videoHeight / borderRatio;
    public static final int totalHeight = videoHeight + (numDivisions - 1) * videoWithBorderHeight;
    public static final int videoWithBorderWidth = videoWidth + videoWidth / borderRatio;
    public static final int totalWidth = videoWidth + (numDivisions - 1) * videoWithBorderWidth;
    public static final float updateDelay = 0.1f;
    public static final float anxietyStep = 1.0f / (30.0f / updateDelay);
    public static final float minAnxiety = 0.11f;
    public static final float maxAnxiety = 0.9f;
}
