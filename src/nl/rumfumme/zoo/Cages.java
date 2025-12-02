package nl.rumfumme.zoo;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.gamecontrolplus.ButtonCallback;
import org.gamecontrolplus.ControlDevice;
import org.gamecontrolplus.ControlIO;
import org.gamecontrolplus.EventType;

import com.hamoid.VideoExport;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.zoo.bee.BeeApp;
import nl.rumfumme.zoo.birb.BirbApp;
import nl.rumfumme.zoo.brezelbub.BrezelbubApp;
import nl.rumfumme.zoo.oneeye.OneEyeApp;
import nl.rumfumme.zoo.peacock.PeacockApp;
import nl.rumfumme.zoo.pentagon.Agent;
import nl.rumfumme.zoo.pred.PredApp;
import nl.rumfumme.zoo.red.RedApp;
import nl.rumfumme.zoo.skeleton.SkeletonApp;
import nl.rumfumme.zoo.title.TitleApp;
import nl.rumfumme.zoo.tortle.TortleApp;
import nl.rumfumme.zoo.xray.XrayApp;
import processing.core.PApplet;

public class Cages extends PApplet {
    private List<ProcessingApp> apps = new ArrayList<ProcessingApp>();
    private List<ProcessingApp> special_apps = new ArrayList<ProcessingApp>();
    private int app_idx = 0;
    private int next_app_idx = 0;
    private VideoExport videoExport;
    private ControlIO controlIO;
    private ControlDevice device;
    private boolean video_running = false;

    private static enum DrawMode {
        INITIALIZING, FADING_IN, DRAWING, FADING_OUT
    }

    private DrawMode mode = DrawMode.INITIALIZING;

    public static void main(String[] args) {
//      System.setProperty("net.java.games.input.librarypath", new File("lib").getAbsolutePath());
//      copyNativeLibs();
      PApplet.runSketch(new String[] { "MAIN" }, new Cages());
  }

  @Override
  public void settings() {
      fullScreen(P3D, 2);

      apps.add(new TitleApp());
      apps.add(new BrezelbubApp());
      apps.add(new RedApp());
      apps.add(new TortleApp());
      apps.add(new BeeApp());
      apps.add(new OneEyeApp());
      apps.add(new PredApp());
      apps.add(new BirbApp());
      apps.add(new PeacockApp());
      apps.add(new SkeletonApp());
      apps.add(new XrayApp());

      special_apps.add(new Agent());

      controlIO = ControlIO.getInstance(this);
      
      try {
          device = controlIO.getDevice("USB gamepad           ");
          
          println(controlIO.devicesToText("\t"));

          for (int i = 0; i < device.getNumberOfButtons(); i++) {
              device.plug(new ButtonCallback() {
                  @Override
                  public void call() {
                      buttonHandler();
                  }
              }, EventType.ON_PRESS, i);
          }
      } catch(RuntimeException e) {
      }
  }
  
  int specialButtons[] = { 3, 0, 3, 0, 3, 0, 2, 1 };
  int specialCnt = 0;
  int specialButtons2[] = { 3, 0, 3, 0, 3, 0, 2, 2, 1 };
  int specialCnt2 = 0;
  int specialButtons3[] = { 3, 0, 3, 0, 3, 0, 2, 2, 2, 2, 1 };
  int specialCnt3 = 0;

  public void buttonHandler() {
      for (int i = 0; i < device.getNumberOfButtons(); i++) {
          if (device.getButton(i).pressed()) {
              if(i == specialButtons[specialCnt]) {
                  specialCnt++;
                  if(specialCnt == specialButtons.length) {
                      specialCnt = 0;
                      key = '-';
                      keyPressed = true;

                      keyPressed();
                      
                      return;
                  }
              } else {
                  specialCnt = 0;
              }
              
              if(i == specialButtons2[specialCnt2]) {
                  specialCnt2++;

                  if(specialCnt2 == specialButtons2.length) {
                      apps.addAll(special_apps);
                  }
              } else {
                  specialCnt2 = 0;
              }

              if(i == specialButtons3[specialCnt3]) {
                  specialCnt3++;

                  if(specialCnt3 == specialButtons3.length) {
                      exit();
                  }
              } else {
                  specialCnt3 = 0;
              }

              switch (i) {
                  case 4:
                  case 8:
                      key = ',';
                      keyPressed = true;
                      
                      keyPressed();
                      
                      specialCnt = 0;
                      specialCnt2 = 0;
                      break;

                  case 5:
                  case 9:
                      key = '.';
                      keyPressed = true;

                      keyPressed();
                      
                      specialCnt = 0;
                      specialCnt2 = 0;
                      break;

                  case 1:
                      key = 'b';
                      keyPressed = true;

                      keyPressed();

                      specialCnt = 0;
                      specialCnt2 = 0;
                      break;
                      
                  default:
                      break;
              }
          }
      }
  }

  int start_time;
  boolean teaser = false;

  @Override
  public void setup() {
      noCursor();
      frameRate(60);
      setup_done = false;

      new Thread(() -> {
          println("loading apps...");
         
          for (ProcessingApp app : apps) {
              println("loading app " + app.getClass().getName() + "...");
              app.setup(this);
              app.load();
          }

          for (ProcessingApp app : special_apps) {
              println("loading app " + app.getClass().getName() + "...");
              app.setup(this);
              app.load();
          }
 
          setup_done = true;

          println("done...");

          if(teaser) {
              start_time = millis();
              videoExport.startMovie();
              video_running = true;
          }
      }).start();

      videoExport = new VideoExport(this, getClass().getName() + "_"
              + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".mp4");

      videoExport.setQuality(70, 128);
      videoExport.setFrameRate(frameRate);
  }
  
  boolean setup_done = false;
 
  @Override
  public void draw() {
      if (apps.isEmpty()) {
          return;
      }
      
      if(!setup_done) {
          return;
      }
      
      ProcessingApp app = apps.get(app_idx);
      
      app.frameCount = frameCount;

      if (mode == DrawMode.INITIALIZING) {
          // Empty style and matrix stacks. There is no explicit way of doing this, so we
          // rely on exceptions here.
          try {
              while (true) {
                  popStyle();
              }
          } catch (RuntimeException e) {
          }
          try {
              while (true) {
                  popMatrix();
              }
          } catch (RuntimeException e) {
          }

          app.init();

          mode = DrawMode.FADING_IN;
      }

      if (mode == DrawMode.FADING_IN) {
          if (!app.fadeIn()) {
              mode = DrawMode.DRAWING;
          }
      }

      if (mode == DrawMode.DRAWING) {
          if (!app.finished()) {
              app.draw();
          } else {
              nextApp();
          }
      }

      if (mode == DrawMode.FADING_OUT) {
          if (!app.fadeOut()) {
              app_idx = next_app_idx;

              mode = DrawMode.INITIALIZING;
          }
      }

      if (video_running) {
          videoExport.saveFrame();
      }
      
      if(teaser && millis() -start_time >= 1000) {
          start_time = millis();
          
          nextApp();
          
          if(next_app_idx == 0) {
              videoExport.endMovie();
              exit();
          }
      }
  }

  private void copyKeyEvent(PApplet app) {
      app.key = key;
      app.keyPressed = keyPressed;
      app.keyCode = keyCode;
      app.keyEvent = keyEvent;
  }
  
  private void nextApp() {
      next_app_idx = app_idx + 1;

      if (next_app_idx == apps.size()) {
          next_app_idx = 0;
      }

      mode = DrawMode.FADING_OUT;
  }
  
  private void previousApp() {
      next_app_idx = app_idx - 1;

      if (next_app_idx == -1) {
          next_app_idx = apps.size() - 1;
      }

      mode = DrawMode.FADING_OUT;
  }

  @Override
  public void keyPressed() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);
      
      switch (key) {
          case '#':
              if (!video_running) {
                  videoExport.startMovie();

                  video_running = true;

                  return;
              } else {
                  video_running = false;

                  videoExport.endMovie();
                  exit();
              }
              break;

          case ',':
              previousApp();
              break;

          case '.':
              nextApp();
              break;

          default:
              copyKeyEvent(app);
              app.keyPressed();
              break;
      }
  }

  @Override
  public void keyReleased() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyKeyEvent(app);
      app.keyReleased();
  }

  @Override
  public void keyTyped() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyKeyEvent(app);
      app.keyTyped();
  }

  private void copyMouseEvent(PApplet app) {
      app.mouseButton = mouseButton;
      app.mousePressed = mousePressed;
      app.mouseX = mouseX;
      app.mouseY = mouseY;
      app.pmouseX = pmouseX;
      app.pmouseY = pmouseY;
      app.mouseEvent = mouseEvent;
  }

  @Override
  public void mousePressed() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyMouseEvent(app);
      app.mousePressed();
  }

  @Override
  public void mouseReleased() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyMouseEvent(app);
      app.mouseReleased();
  }

  @Override
  public void mouseClicked() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyMouseEvent(app);
      app.mouseClicked();
  }

  @Override
  public void mouseDragged() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyMouseEvent(app);
      app.mouseDragged();
  }

  @Override
  public void mouseMoved() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyMouseEvent(app);
      app.mouseMoved();
  }

  @Override
  public void mouseEntered() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyMouseEvent(app);
      app.mouseEntered();
  }

  @Override
  public void mouseExited() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyMouseEvent(app);
      app.mouseExited();
  }

  @Override
  public void mouseWheel() {
      if (apps.isEmpty()) {
          return;
      }

      ProcessingApp app = apps.get(app_idx);

      copyMouseEvent(app);
      app.mouseWheel();
  }
}
