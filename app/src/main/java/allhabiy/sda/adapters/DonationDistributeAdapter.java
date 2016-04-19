package allhabiy.sda.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import allhabiy.sda.R;
import allhabiy.sda.models.DonationCollection;
import allhabiy.sda.models.DonationDistribute;

public class DonationDistributeAdapter extends RecyclerView.Adapter<DonationDistributeAdapter.ViewHolder> {

    private Context mContext;
    private List<DonationDistribute> distributes;

    public DonationDistributeAdapter(Context context, List<DonationDistribute> distributes) {
        this.mContext = context;
        this.distributes = distributes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DonationDistribute distribute = distributes.get(position);
        holder.textViewName.setText(distribute.getPriority1());
//        holder.textViewName.setOnClickListener(clickListener);
//        holder.textViewName.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return distributes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }

    }

}
