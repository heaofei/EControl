package com.mxkj.econtrol.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;

public class PlayMusicActivity extends BaseActivity {

    private Button bt_start;
    private Button bt_next;
    private Button bt_pause;
    private Button bt_stop;
    private ImageView imageview;
    private SeekBar vol_seekbar;
    AudioManager mAudioManager;


    public static final String SERVICECMD = "com.android.music.musicservicecommand";
    public static final String CMDNAME = "command";


    public static final String CMDPLAY = "play";
    public static final String CMDSTOP = "stop";
    public static final String CMDPAUSE = "pause";
    public static final String CMDPREVIOUS = "previous";
    public static final String CMDNEXT = "next";

    public static final String TOGGLEPAUSE_ACTION = "com.android.music.musicservicecommand.play";
    public static final String PAUSE_ACTION = "com.android.music.musicservicecommand.stop";  // pause 跟stop 都是一样效果， 还没深入研究
    public static final String PREVIOUS_ACTION = "com.android.music.musicservicecommand.previous";
    public static final String NEXT_ACTION = "com.android.music.musicservicecommand.next";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_music);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView() {
        vol_seekbar = (SeekBar) findViewById(R.id.vol_seekbar);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        imageview = (ImageView) findViewById(R.id.imageview);

    }

    @Override
    protected void initData() {
        //初始化音频管理器
         mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //获取系统最大音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 获取设备当前音量
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        // 设置seekbar的最大值
        vol_seekbar.setMax(maxVolume);
        // 显示音量
        vol_seekbar.setProgress(currentVolume);








    }

    @Override
    protected void initListener() {
        vol_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0); //progress:音量绝对值
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        bt_start.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_stop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_start:

                // 开启音乐播放服务
//                startService(new Intent(PlayMusicActivity.this,MusicService.class));

              /*  Intent intent = new Intent(SERVICECMD);
                intent.putExtra(CMDNAME, "play");
                sendBroadcast(intent);*/


            /*    Intent intent = new Intent();
                KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

                int key =   KeyEvent.KEYCODE_MEDIA_NEXT;
                int key02 = KeyEvent.KEYCODE_MEDIA_PREVIOUS;
                int key03 = KeyEvent.KEYCODE_HEADSETHOOK;//中间按钮,暂停or播放

//                intent.setAction(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                intent.putExtra(Intent.EXTRA_KEY_EVENT,key03);
                intent.setAction(Intent.ACTION_MEDIA_BUTTON);
                sendBroadcast(intent);*/

           /*     //由于在模拟器上测试，我们手动发送一个MEDIA_BUTTON的广播,有真机更好处理了
                Intent mbIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                //构造一个KeyEvent对象
                KeyEvent keyEvent = new KeyEvent (KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_HEADSETHOOK) ;
                //作为附加值添加至mbIntent对象中
                mbIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                //此时China_MBReceiver和England_MBReceiver都会接收到该广播
                sendBroadcast(mbIntent);
*/
                break;
            case R.id.bt_next:

                /*Intent freshIntent = new Intent();
                freshIntent.setAction(SERVICECMD);
                freshIntent.putExtra(CMDNAME, "next");
                sendBroadcast(freshIntent);*/


               /* //由于在模拟器上测试，我们手动发送一个MEDIA_BUTTON的广播,有真机更好处理了
                Intent mbIntent01 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                //构造一个KeyEvent对象
                KeyEvent keyEvent01 = new KeyEvent (KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_MEDIA_NEXT) ;
                //作为附加值添加至mbIntent对象中
                mbIntent01.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent01);
                //此时China_MBReceiver和England_MBReceiver都会接收到该广播
                sendBroadcast(mbIntent01);*/

                break;
            case R.id.bt_pause:


            /*    //由于在模拟器上测试，我们手动发送一个MEDIA_BUTTON的广播,有真机更好处理了
                Intent freshIntent02 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                //构造一个KeyEvent对象
                KeyEvent keyEvent02 = new KeyEvent (KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_MEDIA_PREVIOUS) ;
                //作为附加值添加至mbIntent对象中
                freshIntent02.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent02);
                //此时China_MBReceiver和England_MBReceiver都会接收到该广播
                sendBroadcast(freshIntent02);*/

               /* Intent freshIntent02 = new Intent();
                freshIntent02.setAction(SERVICECMD);
                freshIntent02.putExtra(CMDNAME, "previous");
                sendBroadcast(freshIntent02);*/

                break;
            case R.id.bt_stop:

               /* //由于在模拟器上测试，我们手动发送一个MEDIA_BUTTON的广播,有真机更好处理了
                Intent intent04 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                //构造一个KeyEvent对象
                KeyEvent keyEvent04 = new KeyEvent (KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_MEDIA_STOP) ;
                //作为附加值添加至mbIntent对象中
                intent04.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent04);
                //此时China_MBReceiver和England_MBReceiver都会接收到该广播
                sendBroadcast(intent04);*/


                /*Intent intent04 = new Intent(SERVICECMD);
                intent04.putExtra(CMDNAME, "stop");
                sendBroadcast(intent04);*/

                // 实现原理：打开服务，第三方音乐暂停；关闭服务，第三音乐继续播放
                // 关闭音乐播放服务
//                stopService(new Intent(PlayMusicActivity.this,MusicService.class));


                break;

        }
    }


    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action02 = intent.getAction();
            String cmd = intent.getStringExtra("command");
            String cmd02 = intent.getStringExtra("command");

            String action = intent.getAction();
            // 获得KeyEvent对象
            KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (Intent.ACTION_MEDIA_BUTTON.equals(action)) {
                // 获得按键码
                int keycode = event.getKeyCode();
                switch (keycode) {
                    case KeyEvent.KEYCODE_MEDIA_NEXT:
                        //播放下一首
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                        //播放上一首
                        break;
                    case KeyEvent.KEYCODE_HEADSETHOOK:
                        //中间按钮,暂停or播放
                        //可以通过发送一个新的广播通知正在播放的视频页面,暂停或者播放视频
                        break;
                    default:
                        break;
                }
            }

        }
    };


/*
    @Override
    protected void onResume() {
        IntentFilter commandFilter = new IntentFilter();
        commandFilter.addAction(SERVICECMD);
        commandFilter.addAction(TOGGLEPAUSE_ACTION);
        commandFilter.addAction(PAUSE_ACTION);
        commandFilter.addAction(NEXT_ACTION);
        commandFilter.addAction(PREVIOUS_ACTION);


        commandFilter.addAction(Intent.ACTION_MEDIA_BUTTON);

        registerReceiver(mIntentReceiver, commandFilter);


        super.onResume();
    }

    protected void onPause() {
        if(mIntentReceiver != null){
            unregisterReceiver(mIntentReceiver);
        }
        super.onPause();
    }
*/



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照
        if (requestCode == 02 && resultCode == Activity.RESULT_OK && null != data) {
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
//            savePic(bitmap);
            imageview.setImageBitmap(bitmap);//显示图片

        }
        if (null==data || null==data.getExtras() || null == (Bitmap) data.getExtras().get("data") ){
            showToast("返回为空");
        }

        }


}
