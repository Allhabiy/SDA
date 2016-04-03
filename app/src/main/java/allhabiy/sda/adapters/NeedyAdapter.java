package allhabiy.sda.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import allhabiy.sda.R;
import allhabiy.sda.models.User;


public class NeedyAdapter extends RecyclerView.Adapter<NeedyAdapter.ViewHolder> {

    private Context mContext;
    private List<User> users;

    public NeedyAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
//        holder.textViewName.setOnClickListener(clickListener);
//        holder.textViewName.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

//    View.OnClickListener clickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            ViewHolder holder = (ViewHolder) view.getTag();
//            int position = holder.getPosition();
////            Intent intent = new Intent(mContext, NeedyDetailsActivity.class);
//            User user = users.get(position);
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
