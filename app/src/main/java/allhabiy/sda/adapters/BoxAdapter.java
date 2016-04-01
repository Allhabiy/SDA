package allhabiy.sda.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import allhabiy.sda.R;
import allhabiy.sda.models.Box;

/**
 * Created by Shareef on 3/15/2016.
 */
public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder> {

    private Context mContext;
    private List<Box> boxes;

    public BoxAdapter(Context context, List<Box> boxes) {
        this.mContext = context;
        this.boxes = boxes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Box box = boxes.get(position);
        holder.textViewName.setText(box.getName());
//        holder.textViewName.setOnClickListener(clickListener);
//        holder.textViewName.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return boxes.size();
    }

//    View.OnClickListener clickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            ViewHolder holder = (ViewHolder) view.getTag();
//            int position = holder.getPosition();
//
//            User user = users.get(position);
//            Intent intent = new Intent(mContext, .class);
//            intent.putExtra("User", user);
//            mContext.startActivity(intent);
//        }
//    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }

    }

}
