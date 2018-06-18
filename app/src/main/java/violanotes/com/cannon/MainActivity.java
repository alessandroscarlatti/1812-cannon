package violanotes.com.cannon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final List<MediaPlayer> cannons = new ArrayList<>();
    private final int cannonsSize = 40;
    private int cannonIndex = 0;

    private int silence;
    private int cannon;

    private boolean volumeOn = true;

    private static final Integer[] IMAGES= {
            R.raw.page1,
            R.raw.page2,
            R.raw.page3,
            R.raw.page4,
            R.raw.page5,
            R.raw.page6,
            R.raw.page7,
            R.raw.page8,
            R.raw.page9,
            R.raw.page10,
            R.raw.page11,
            R.raw.page12,
            R.raw.page13,
            R.raw.page14,
            R.raw.page15,
            R.raw.page16,
            R.raw.page17,
            R.raw.page18,
            R.raw.page19,
            R.raw.page20,
            R.raw.page21,
            R.raw.page22,
            R.raw.page23,
            R.raw.page24,
            R.raw.pageb1,
            R.raw.pageb2,
            R.raw.pageb3,
            R.raw.pageb4,
            R.raw.pageb5,
            R.raw.pageb6,
            R.raw.pageb7,
            R.raw.pageb8,
            R.raw.pageb9
    };

    private static SoundPool soundPool;
    private static Map soundPoolMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            final ImageView buttonFire = (ImageView) findViewById(R.id.buttonFire);
            Bitmap fireImage = BitmapFactory.decodeResource(getResources(), R.raw.explosion);
            buttonFire.setImageBitmap(fireImage);

//            ImageView buttonVolume = (ImageView) findViewById(R.id.buttonVolume);
//            Bitmap volumeImage = BitmapFactory.decodeResource(getResources(), R.raw.explosion);
//            buttonVolume.setImageBitmap(volumeImage);


            final ImageView buttonVolume = (ImageView) findViewById(R.id.buttonVolume);
            buttonVolume.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (volumeOn) {
                        buttonVolume.setBackgroundResource(R.drawable.ic_volume_off_black_24dp);
                    } else {
                        buttonVolume.setBackgroundResource(R.drawable.ic_volume_up_black_24dp);
                    }

                    volumeOn = !volumeOn;
                }
            });


            ArrayList<Integer> imagesArray = new ArrayList<Integer>();

            for (int i : IMAGES) {
                imagesArray.add(i);
            }

            ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            viewPager.setAdapter(new SlidingImageAdapter(MainActivity.this, imagesArray));




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setupAudio2();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    soundPool.play(cannon, 0.0f, 0.0f, 1, -1, 1f);
                } catch (Exception e) {

                }
            }
        }.start();

    }



    private void setupAudio2() {

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);

        silence = soundPool.load(this, R.raw.silence_short, 1);
        cannon = soundPool.load(this, R.raw.cannon4, 1);

//        soundPoolMap = new HashMap(2);
//        soundPoolMap.put(R.raw.cannon4, soundPool.load(this, R.raw.cannon4, 1));
//        soundPoolMap.put(R.raw.silence_short, soundPool.load(this, R.raw.silence_short, 1));


        ImageView imageView = (ImageView) findViewById(R.id.buttonFire);
        imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    try {
                        if (volumeOn)
                            soundPool.play(cannon, 0.5f, 0.5f, 1, 0, 1f);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }

                return true;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        soundPool.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
