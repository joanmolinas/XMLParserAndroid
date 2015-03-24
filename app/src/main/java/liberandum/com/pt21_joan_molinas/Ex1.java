package liberandum.com.pt21_joan_molinas;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.concurrent.ExecutionException;


public class Ex1 extends Activity {

    ImageView iv;
    Bitmap b1 = null, b2=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ex1, menu);
        return true;
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v) throws ExecutionException, InterruptedException {
        iv = (ImageView)findViewById(R.id.imageView);

       switch (v.getId()) {
           case R.id.button:


               if (b1 == null || b2 == null) {
                   changeImage change = new changeImage();
                   change.execute(R.drawable.happy, R.drawable.logo_impremta).get();
                   iv.setTag(R.drawable.happy);

               }

               if (iv.getTag().equals(R.drawable.happy)) {
                   iv.setImageBitmap(b2);
                   iv.setTag(R.drawable.logo_impremta);
               } else {
                   iv.setImageBitmap(b1);
                   iv.setTag(R.drawable.happy);
               }
               break;
           case R.id.button2:
               ImageView imagen = new ImageView(this);
               Bitmap b = ((BitmapDrawable)iv.getDrawable()).getBitmap();
               imagen.setImageBitmap(b);
               LinearLayout linear = (LinearLayout)findViewById(R.id.linearScroll);
               linear.addView(imagen);

               final ScrollView scroller = (ScrollView)findViewById(R.id.scrollView);
               scroller.post(new Runnable() {
                   @Override
                   public void run() {
                       scroller.fullScroll(ScrollView.FOCUS_DOWN);
                   }
               });
               break;
       }

    }
    public class changeImage extends AsyncTask<Integer, Void, Void> {
        protected Void doInBackground(Integer... params){
            b1 = decodeSampledBitmapFromResource(getResources(), params[0], 100, 100);
            b2 = decodeSampledBitmapFromResource(getResources(), params[1], 100, 100);
            return null;
        }
    }
}
