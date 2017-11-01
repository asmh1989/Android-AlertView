package com.bigkoo.alertviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.smobiler.alertview.AlertView;
import com.smobiler.alertview.OnDismissListener;
import com.smobiler.alertview.OnItemClickListener;

/**
 * 精仿iOSAlertViewController控件Demo
 */
public class MainActivity extends Activity implements OnItemClickListener, OnDismissListener {

    private AlertView mAlertView;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    private AlertView mAlertViewExt;//窗口拓展例子
    private EditText etName;//拓展View内容
    private InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mAlertView = new AlertView(null, "写到这里估计会有读者吐槽了，人家好好的在酷壳写作，被你们拉来变成付费阅读了。这里其实有个杠杆效应。耗子以前的写作频率大概是一月一篇，写作素材比较随性，想起有意思的点就会成文一篇。而「左耳听风」的更新频率是一个月八篇，全年无休。文章是系统性的、实用性的，也是最新鲜的输入。读这样一个骨灰级程序员的一年专栏，就像在阅读一本有生命力的图书，你还可以与作者沟通和互动，这个价值，难道比不了周末的一顿聚餐么？也许一篇文章可以解决困扰你的一个问题，也许一句话，就能改变你人生的轨迹。要知道，改变我人生轨迹的一本书叫做《HTML4 动态编程》。那一年是1999。\n" +
                "\n" +
                "作者：池建强\n" +
                "链接：https://zhuanlan.zhihu.com/p/30622441\n" +
                "来源：知乎\n" +
                "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, this).setCancelable(false).setOnDismissListener(this);
        //拓展窗口
        mAlertViewExt = new AlertView("提示", "请完善你的个人资料！", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);
        ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName = (EditText) extView.findViewById(R.id.etName);
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                //输入框出来则往上移动
                boolean isOpen=imm.isActive();
                mAlertViewExt.setMarginBottom(isOpen&&focus ? 120 :0);
                System.out.println(isOpen);
            }
        });
        mAlertViewExt.addExtView(extView);
    }

    public void alertShow1(View view) {
        mAlertView.show();
    }

    public void alertShow2(View view) {
        new AlertView("标题", "内容", null, new String[]{"确定"}, null, this, AlertView.Style.Alert, this).show();
    }

    public void alertShow3(View view) {
        new AlertView(null, null, null, new String[]{"高亮按钮1", "高亮按钮2", "高亮按钮3"},
                new String[]{"其他按钮1", "其他按钮2", "其他按钮3", "其他按钮4", "其他按钮5", "其他按钮6",
                        "其他按钮7", "其他按钮8", "其他按钮9", "其他按钮10", "其他按钮11", "其他按钮12"},
                this, AlertView.Style.Alert, this).show();
    }

    public void alertShow4(View view) {
        new AlertView("标题", null, "取消", new String[]{"高亮按钮1"}, new String[]{"其他按钮1", "其他按钮2", "其他按钮3"}, this, AlertView.Style.ActionSheet, this).show();
    }

    public void alertShow5(View view) {
        new AlertView("标题", "内容", "取消", null, null, this, AlertView.Style.ActionSheet, this).setCancelable(true).show();
    }

    public void alertShow6(View view) {
        new AlertView("上传头像", null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, this).show();
    }

    public void alertShowExt(View view) {
        mAlertViewExt.show();
    }
    private void closeKeyboard() {
        //关闭软键盘
        imm.hideSoftInputFromWindow(etName.getWindowToken(),0);
        //恢复位置
        mAlertViewExt.setMarginBottom(0);
    }
    @Override
    public void onItemClick(Object o,int position) {
        closeKeyboard();
        //判断是否是拓展窗口View，而且点击的是非取消按钮
        if(o == mAlertViewExt && position != AlertView.CANCELPOSITION){
            String name = etName.getText().toString();
            if(name.isEmpty()){
                Toast.makeText(this, "啥都没填呢", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "hello,"+name, Toast.LENGTH_SHORT).show();
            }

            return;
        }
        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDismiss(Object o) {
        closeKeyboard();
        Toast.makeText(this, "消失了", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(mAlertView!=null && mAlertView.isShowing()){
                mAlertView.dismiss();
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);

    }
}
