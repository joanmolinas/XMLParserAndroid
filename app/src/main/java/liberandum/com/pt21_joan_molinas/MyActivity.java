package liberandum.com.pt21_joan_molinas;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MyActivity extends Activity {

    AnimatorSet beat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_camera){
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePicture.resolveActivity(getPackageManager())!= null) {
                startActivityForResult(takePicture,1);
            }
        } else if(id == R.id.set_item){
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.happy);
            beat = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.beat);
            beat.setTarget(iv);
            LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
            linear.addView(iv);
            beat.start();
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v) {
        EditText et = (EditText)findViewById(R.id.editText);
        LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
        TextView tv = new TextView(this);
        tv.setText("user: n" +  et.getText());
        linear.addView(tv);

        final ScrollView scroller = (ScrollView)findViewById(R.id.scrollView);
        scroller.post(new Runnable() {
            @Override
            public void run() {
                scroller.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            ImageView iv = new ImageView(this);
            LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
            iv.setImageBitmap(imageBitmap);
            linear.addView(iv);
        }
    }
}
