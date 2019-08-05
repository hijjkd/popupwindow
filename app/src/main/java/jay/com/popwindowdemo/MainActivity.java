package jay.com.popwindowdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_show;
    private Context mContext;
    private RelativeLayout target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        target = findViewById(R.id.re_layout_btn_parent);
        btn_show = (Button) findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(v);
            }
        });
    }


    private void initPopWindow(View v) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup, null, false);
        Button btn_xixi = (Button) view.findViewById(R.id.btn_xixi);
        Button btn_hehe = (Button) view.findViewById(R.id.btn_hehe);


        Rect rect = new Rect();
        target.getGlobalVisibleRect(rect);
        int height = rect.bottom - rect.top;


        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                500, height, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(target, 0, height, Gravity.BOTTOM);

        //设置popupWindow里的按钮的事件
        btn_xixi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了嘻嘻~", Toast.LENGTH_SHORT).show();
            }
        });
        btn_hehe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast toast = Toast.makeText(MainActivity.this, "你点击了呵呵~", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.LEFT, -100, 0);
//                toast.show();
                show(view);
                popWindow.dismiss();
            }
        });
    }


    public void show(View v) {
        //1、构建Toast对象
        Toast toast = new Toast(this);
        //显示文本

        TextView tv = new TextView(this);
        tv.setText("哈哈哈");
        tv.setTextColor(Color.RED);
        tv.setBackgroundColor(Color.BLUE);
        //2、构建Toast显示图片
        //        ImageView tv = new ImageView(this);
        //        tv.setImageResource(R.drawable.ic_pop_bg);
        //3、设置显示出来的view
        toast.setView(tv);
        //4、设置Toast显示位置 (屏幕顶端中间位置开始算)
        toast.setGravity(Gravity.CENTER, 0, 0);
        //5、设置时常
        toast.setDuration(Toast.LENGTH_LONG);
        //6、显示
        toast.show();
    }


}
