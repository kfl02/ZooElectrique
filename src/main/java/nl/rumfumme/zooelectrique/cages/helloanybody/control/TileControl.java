package nl.rumfumme.zooelectrique.cages.helloanybody.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ColorSpace;
import com.jme3.util.BufferUtils;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallbackAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallbackAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;

import java.nio.ByteBuffer;

/*
 * Control class responsible for playing the videos on the tiles.
 */
public class TileControl extends AbstractControl {
    // This must go elsewhere
    private static final String[] movies = {"28_days_later_1.mp4",
            "28_days_later_2.mp4",
            "2001_maniacs_2.mp4",
            "agents_of_SHIELD.mp4",
            "band_of_brothers.mp4",
            "battlestar_galactica_1_1.mp4",
            "better_living_through_chemistry.mp4",
            "botched.mp4",
            "buried.mp4",
            "burn_notice_7_5.mp4",
            "cabin_fever_3.mp4",
            "castle_3_9.mp4",
            "castle_7_22.mp4",
            "columbo_5_5.mp4",
            "contagion.mp4",
            "continuum_1_6.mp4",
            "cursed.mp4",
            "dark_star.mp4",
            "day_of_the_dead_1.mp4",
            "day_of_the_dead_2.mp4",
            "day_of_the_dead_3.mp4",
            "day_of_the_triffids.mp4",
            "doghouse.mp4",
            "eureka_5_13.mp4",
            "from.mp4",
            "futureworld.mp4",
            "ghost_whisperer_4_5.mp4",
            "ghost_whisperer_5_18.mp4",
            "graveyard_shark.mp4",
            "hansel_gretel_witch_hunters.mp4",
            "hansel_und_gretel.mp4",
            "haven_1_13.mp4",
            "hawaii_five_0_2_3.mp4",
            "heroes_4_18.mp4",
            "ijon_tichy_2_8.mp4",
            "joy_ride_2_1.mp4",
            "joy_ride_2_2.mp4",
            "joy_ride_3.mp4",
            "lois_and_clark_4_21.mp4",
            "lost_1_6.mp4",
            "lost_1_19.mp4",
            "lost_2_7.mp4",
            "lost_6_15.mp4",
            "malcolm_3_12.mp4",
            "medium_2_21.mp4",
            "medium_5_6_2.mp4",
            "medium_5_7.mp4",
            "mute_witness.mp4",
            "mysterious_island.mp4",
            "no_mans_land.mp4",
            "o_lucky_man.mp4",
            "person_of_interest_2_14.mp4",
            "pushing_daisies_1_5.mp4",
            "raumpatrouille_3.mp4",
            "remington_steele_1_5.mp4",
            "remington_steele_3_14.mp4",
            "return_of_the_living_dead_2.mp4",
            "roswell_1_13.mp4",
            "shining.mp4",
            "skinned_deep.mp4",
            "slumdog_millionaire.mp4",
            "smallville_1_14.mp4",
            "smallville_3_14.mp4",
            "suspiria.mp4",
            "taken_1_1.mp4",
            "taken_1_5.mp4",
            "texas_chainsaw_massacre_1.mp4",
            "texas_chainsaw_massacre_2.mp4",
            "the_abandoned.mp4",
            "the_adventurer_curse_of_the_midas_box.mp4",
            "the_book_of_eli.mp4",
            "the_day_after.mp4",
            "the_hills_have_eyes.mp4",
            "the_hills_have_eyes_2.mp4",
            "the_quiet_earth.mp4",
            "the_scribbler.mp4",
            "the_strain_1_2.mp4",
            "the_unit_2_5.mp4",
            "titanic.mp4",
            "togetherness_1_3.mp4",
            "warehouse_13_4_12.mp4",
            "wrong_turn.mp4",
            "wuerger.mp4",
            "xfiles_5_7.mp4",
            "xfiles_5_19.mp4",
            "xfiles_6_13.mp4",
            "xfiles_7_13.mp4",
    };

    /*--- Private constants -------------------------------------------------*/

    private static final float reservedTime = 1.0f;
    private static final MediaPlayerFactory factory = new MediaPlayerFactory();

    private final EmbeddedMediaPlayer mediaPlayer;

    private Texture2D texture;
    private float timeElapsed = 0.0f;

    private enum Mode {
        ACTIVE,
        FINISHED,
        INACTIVE,
        RESERVED
    }
    Mode mode = Mode.INACTIVE;

    /*--- Constructors ------------------------------------------------------*/

    public TileControl() {
        this.mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
    }

    /*--- Private methods ---------------------------------------------------*/

    // Fill the texture with a solid color.
    private void fillBuffer(int value) {
        ByteBuffer buffer = texture.getImage().getData(0);

        buffer.rewind();

        for(int i = 0; i < buffer.capacity() / 4; i++) {
            buffer.putInt(value);
        }

        texture.getImage().setUpdateNeeded();
    }

    private void uodateMode(float tpf) {
        if(mode == Mode.FINISHED) {
            mode = Mode.RESERVED;
            timeElapsed = 0.0f;

            // Set cull hint to always to make the tile transparent.
            this.getSpatial().setCullHint(Spatial.CullHint.Always);
            fillBuffer(0xffffffff);
        } else if(mode == Mode.RESERVED) {
            if(timeElapsed >= reservedTime) {
                mode = Mode.INACTIVE;
                timeElapsed = 0.0f;
            }
            else {
                timeElapsed += tpf;
            }
        }
    }

    // Start a random video on the tile. TODO
    private void startVideo() {
        if(!mediaPlayer.status().isPlaying()) {
            int index = (int) (Math.random() * movies.length);

            mediaPlayer.media().play("/home/kfl02/Videos/Hello/assets_stage_3/" + movies[index]);
            mediaPlayer.controls().setRepeat(false);
            mediaPlayer.controls().start();

            mode = Mode.ACTIVE;

            // Set cull hint to always to make the tile visible.
            this.getSpatial().setCullHint(Spatial.CullHint.Never);
        }
    }

    private void stopVideo() {
        mediaPlayer.controls().stop();
        mediaPlayer.media().reset();
        mode = Mode.INACTIVE;
    }

    /*--- Public methods ----------------------------------------------------*/

    public void activate() {
        startVideo();
    }

    public boolean isActive() {
        return mode == Mode.ACTIVE;
    }

    public boolean isReserved() {
        return mode == Mode.RESERVED;
    }

    public boolean isInactive() {
        return mode == Mode.INACTIVE;
    }

    /*--- Overridden methods ------------------------------------------------*/

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        Geometry geometry = (Geometry) spatial;
        Texture2D texture = (Texture2D) geometry.getMaterial().getTextureParam("ColorMap").getTextureValue();
        VideoSurfaceCallbacks callbacks = new VideoSurfaceCallbacks(texture);
        this.texture = texture;

        mediaPlayer.videoSurface().set(
                factory.videoSurfaces().newVideoSurface(
                        callbacks.bufferFormatCallback,
                        callbacks.renderCallback,
                        false
                )
        );
        mediaPlayer.events().addMediaPlayerEventListener(
                new MediaPlayerEventAdapter() {
                    @Override
                    public void finished(MediaPlayer mediaPlayer) {
                        mode = Mode.FINISHED;
                    }
                }
        );
        this.getSpatial().setCullHint(Spatial.CullHint.Always);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if(!enabled) {
            stopVideo();
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        uodateMode(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        // Nothing to do here, the VideoSurface Display callback does the rendering.
    }

    /*--- Inner classes -----------------------------------------------------*/

    private static class VideoSurfaceCallbacks {
        SurfaceBufferFormatCallback bufferFormatCallback;
        SurfaceRenderCallback renderCallback;
        Texture2D texture;

        protected VideoSurfaceCallbacks(Texture2D texture) {
            this.texture = texture;
            bufferFormatCallback = new SurfaceBufferFormatCallback();
            renderCallback = new SurfaceRenderCallback(texture);
        }

        private class SurfaceBufferFormatCallback extends BufferFormatCallbackAdapter {
            @Override
            public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
                return new RV32BufferFormat(sourceWidth, sourceHeight);
            }

            // Sometimes the VLC player wants to resize the video a bit, and buffer sizes might go out of sync.
            // Catch any resizing here and update the texture and the render callback accordingly.
            @Override
            public void newFormatSize(int bufferWidth, int bufferHeight, int displayWidth, int displayHeight) {
                int bufferSize = bufferWidth * bufferHeight;

                if(bufferSize * Integer.BYTES > texture.getImage().getData(0).limit()) {
                    texture.setImage(new Image(Image.Format.BGRA8,
                            displayWidth, displayHeight,
                            BufferUtils.createByteBuffer(bufferSize * Integer.BYTES),
                            ColorSpace.Linear));
                    renderCallback.setBuffer(new int[bufferSize]);
                }
            }
        }

        protected class SurfaceRenderCallback extends RenderCallbackAdapter {
            SurfaceRenderCallback(Texture2D texture) {
                super(new int[texture.getImage().getWidth() * texture.getImage().getHeight()]);
            }

            @Override
            protected void onDisplay(MediaPlayer mediaPlayer, int[] buffer) {
                boolean triedToChangeBuffer = false;

                // Ugh, sometimes the buffer size seems to grow without having called newFormatSize().
                if(buffer.length * Integer.BYTES > texture.getImage().getData(0).limit()) {
                    texture.getImage().setData(0, BufferUtils.createByteBuffer(buffer.length * Integer.BYTES));
                    triedToChangeBuffer = true;
                }

                try {
                    texture.getImage().getData(0).asIntBuffer().put(buffer);
                } catch(Exception e) {
                    if(triedToChangeBuffer) {
                        System.err.println("Tried to change buffer size but failed.");
                    }
                }

                texture.getImage().setUpdateNeeded();
            }
        }
    }
}
