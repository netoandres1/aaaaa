package eazekart.eazykart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;


public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    ListView mListView;
    ArrayList<ListItemBean> listData;

    public ListViewAdapter(Context mContext) {

        this.mContext = mContext;
    }
    public ListViewAdapter(Context mContext, ListView mListView,ArrayList<ListItemBean> listData) {

        this.mContext = mContext;
        this.mListView=mListView;
        this.listData=listData;
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    public void refreshAdapter(ArrayList<ListItemBean> listData){
        this.listData = listData;
        notifyDataSetChanged();
    }
    @Override
    public View generateView(int position, ViewGroup parent) {

        final int posi=position;
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
       final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                //Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();

            }
        });

        ImageView trash=(ImageView)v.findViewById(R.id.trash);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SwipeLayout)(mListView.getChildAt(posi - mListView.getFirstVisiblePosition()))).close(true);

            }
        });

        Button delete=(Button)v.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"delete"+posi,Toast.LENGTH_SHORT).show();
                 removemy(posi);
                ((SwipeLayout)(mListView.getChildAt(posi - mListView.getFirstVisiblePosition()))).close(true);

            }
        });
        ImageView im=(ImageView)v.findViewById(R.id.imageView);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((SwipeLayout)(mListView.getChildAt(posi - mListView.getFirstVisiblePosition()))).open(true);
                //Toast.makeText(mContext,"clciked",Toast.LENGTH_SHORT).show();
            }
        });

        TextView txtTitle = (TextView) v.findViewById(R.id.txt);
        ImageView imageView = (ImageView) v.findViewById(R.id.img);
        TextView rate = (TextView) v.findViewById(R.id.rate);


        ListItemBean l=listData.get(position);
        txtTitle.setText(l.getItemName());
        imageView.setImageResource(l.getItemresid());
        rate.setText("$"+l.getRate());

        fillValues(position, v);

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setVisibility(View.INVISIBLE);
        t.setText((position + 1) + ".");
    }

    @Override
    public int getCount() {

        return listData.size();
    }

    public ArrayList<ListItemBean> getData(){

        return listData;
    }

    @Override
    public ListItemBean getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removemy(int pos) {
        listData.remove(pos);
        notifyDataSetChanged();
//        MainActivity main=new MainActivity();
//        main.listVisibiltiy();
    }
    public void insertData(ListItemBean item,int pos) {
        listData.add(pos,item);
        notifyDataSetChanged();
    }
}
