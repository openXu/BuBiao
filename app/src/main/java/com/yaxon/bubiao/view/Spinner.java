package com.yaxon.bubiao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.utils.FLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: openXu
 * Time: 2020/12/15 9:26
 * class: Spinner
 * Description:
 */
public class Spinner extends androidx.appcompat.widget.AppCompatSpinner {
    public Spinner(Context context) {
        super(context);
    }

    public Spinner(Context context, int mode) {
        super(context, mode);
    }

    public Spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Spinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Spinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public void setAdapter(MySpinnerAdapter adapter, SpinnersAction action) {
        adapter.setAction(action);
        super.setAdapter(adapter);
        setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                FLog.w("1spinner选中了："+pos);
                if(action!=null) {
                    action.onItemSelected(view, pos, adapter.getItem(pos));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public interface SpinnersAction<T>{
        String getItemStr(T data);
        void onItemSelected(View view, int pos, T data);
    }

 /*   public static class MySpinnerAdapter1<T> extends ArrayAdapter{

        public MySpinnerAdapter1(Context context, int resource) {
            super(context, resource);
            setDropDownViewResource();
        }
    }
*/
    public static class MySpinnerAdapter<T> extends BaseAdapter {
        private Context context;
        private List<T> mList;
        private SpinnersAction<T> action;
        private int itemRes, dropItemRes;
        public MySpinnerAdapter(Context context, int itemRes, int dropItemRes, List<T> mList) {
            this.context = context;
            this.mList = mList;
            this.itemRes = itemRes;
            this.dropItemRes = dropItemRes;
        }

        public void setAction(SpinnersAction<T> action) {
            this.action = action;
        }

        public void setDataList(List<T> mList) {
            if(mList!=null)
                this.mList = mList;
            else
                this.mList = new ArrayList<>();
//            FLog.w("设置spinner数据："+mList);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return mList==null?0:mList.size();
        }
        @Override
        public T getItem(int position) {
            if(mList==null || position>=mList.size()){
                return null;
            }
            return mList.get(position);
        }
        @Override
        public long getItemId(int position) {
            if(mList==null || position>=mList.size())
                return -1;
            return getItem(position).hashCode();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
                convertView = LayoutInflater.from(context).inflate(itemRes, null);
            if(action!=null) {
                ((TextView)convertView.findViewById(R.id.tv_text)).setText(action.getItemStr(getItem(position)));
            }
//            FLog.v("获取item->getView  "+position+"    "+convertView);
            return convertView;
        }
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
//            return super.getDropDownView(position, convertView, parent);
//            FLog.v("获取item->getDropDownView  "+position);
            if(convertView==null)
                convertView = LayoutInflater.from(context).inflate(dropItemRes, null);
            if(action!=null) {
                ((TextView)convertView.findViewById(R.id.tv_text)).setText(action.getItemStr(getItem(position)));
            }
            return convertView;
        }
    }



    // 一个 item 选中的时候，总是会触发 setSelection 方法
    // 所以在这个方法中，我们记录并检查上一次的selection position 就行了，如果是相同的，手动调用监听即可
/*
    @Override
    public void setSelection(int position, boolean animate) {
        super.setSelection(position, animate);
        FLog.e("2设置选中："+position);
     */
/*   if (position == lastPosition){
            if (getOnItemSelectedListener() != null)
                getOnItemSelectedListener().onItemSelected(this, getChildAt(position), position, 0);
        }
        lastPosition = position;*//*
    }
*/

   /* private boolean isFirst;
    private boolean adapterChanged;
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        isFirst = getAdapter() == null;
        adapterChanged = getAdapter()==adapter;
        super.setAdapter(adapter);
        *//*adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                itemCount = adapter.getCount();
            }
            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        });*//*
    }*/

    //解决当更换adapter后，第一次不回调onItemSelected()的问题
 /*   @Override
    public void setSelection(int position) {
        super.setSelection(position);
        FLog.e("1设置选中："+position);
        if(isFirst){
            FLog.e("第一次设置适配器，会自动回调");
            return;
        }
        if(adapterChanged){
            if (getOnItemSelectedListener() != null)
                getOnItemSelectedListener().onItemSelected(this, getChildAt(position), position, getAdapter().getItemId(position));
            adapterChanged = false;
        }

    }*/


    //androidx.appcompat.widget.AppCompatSpinner中DropdownPopup就是Item的PopupWindow，onTouchEvent中会调用show方法显示，当点击item时会调用performItemClick()

    //Spinner
    //layout()方法中调用了handleDataChanged()
    //  @Override
    //    void layout(int delta, boolean animate) {
    //
    //        if (mDataChanged) {
    //            handleDataChanged();
    //        }

    //AbsSpinner
    // @Override
    //    public void setSelection(int position) {
    //        setNextSelectedPositionInt(position);
    //        requestLayout();  //重新布局，会触发Spinner的layout()，从而触发下面AdapterView的步骤
    //        invalidate();
    //    }
    /*public void setAdapter(SpinnerAdapter adapter) {
        mAdapter = adapter;
        ...
        mDataSetObserver = new AdapterDataSetObserver();     //当调用adapternotifyDataSetChanged()时会触发观察者，然后mDataChanged=true;
        mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }*/

    //AdapterView
    //handleDataChanged()-> checkSelectionChanged()->selectionChanged() -> dispatchOnItemSelected()-> mOnItemSelectedListener.onItemSelected()
    //checkSelectionChanged()判断了新的选中mSelectedPosition和历史选中(默认INVALID_POSITION = -1;)position相同就不回调，这就是为什么切换adapter后不回调onItemSelected()
    //但是还有一个条件mSelectedRowId，getItemIdAtPosition()-> adapter.getItemId(position),只需要重写getItemId，为每个item赋予唯一值即可
    /*void checkSelectionChanged() {
        if ((mSelectedPosition != mOldSelectedPosition) || (mSelectedRowId != mOldSelectedRowId)) {
            selectionChanged();
            mOldSelectedPosition = mSelectedPosition;
            mOldSelectedRowId = mSelectedRowId;
        }
        ...
    }*/

}