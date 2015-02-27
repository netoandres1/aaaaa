package eazekart.eazykart;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<ListItemBean>{
    private final Activity context;
    ArrayList<ListItemBean> listData;
    public MyAdapter(Activity context,ArrayList<ListItemBean> listData) {
        super(context, R.layout.list_item,listData);
        this.context = context;
        this.listData = listData;
    }
    public void refreshAdapter(ArrayList<ListItemBean> listData){
        this.listData = listData;
        notifyDataSetChanged();
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
    }
    public void insertData(ListItemBean item,int pos) {
        listData.add(pos,item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView rate = (TextView) rowView.findViewById(R.id.rate);


        ListItemBean l=listData.get(position);
        txtTitle.setText(l.getItemName());
        imageView.setImageResource(l.getItemresid());
        rate.setText("$"+l.getRate());
        return rowView;
    }
}