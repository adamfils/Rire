package tech.zongogroup.rire_soteh;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class VideoDetail extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController;
    int position = 0;
    ProgressBar bar;
    private String videoUrl = "https://firebasestorage.googleapis.com/v0/b/rire-soteh.appspot.com/o/Charlie%20Puth%20-%20-Marvin%20Gaye-%20ft.%20Meghan%20Trainor%20(Mashup%20Cover%20Marvin%20Gaye_Stand%20By%20Me).3gp?alt=media&token=d5a3bcc1-5b99-4ea6-8fb7-efba9ecd3dcf";
    Uri uri = Uri.parse(videoUrl);
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        String vidId = getIntent().getStringExtra("vid");

        bar = (ProgressBar) findViewById(R.id.load_button);

        videoView = (VideoView) findViewById(R.id.video_show);
        if (mediaController == null) {
            mediaController = new MediaController(VideoDetail.this);
        }

        try {
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        videoView.seekTo(position);
                        if(position == 0)
                        {
                            videoView.start();

                        }
                        else {
                            videoView.pause();
                        }

                    }
                }
        );
        BarView();

    }

    public void BarView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                    if(what==MediaPlayer.MEDIA_INFO_BUFFERING_END){
                        bar.setVisibility(View.VISIBLE);
                        return true;
                    }else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                        bar.setVisibility(View.GONE);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        videoView.seekTo(position);
        videoView.start();
    }

   /* @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            videoView.setLayoutParams(layoutParams);
        }

    }*/
}
