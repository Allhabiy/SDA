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
import allhabiy.sda.models.DonationCollection;

public class DonationCollectionAdapter extends RecyclerView.Adapter<DonationCollectionAdapter.ViewHolder> {

    private Context mContext;
    private List<DonationCollection> collections;

    public DonationCollectionAdapter(Context context, List<DonationCollection> collections) {
        this.mContext = context;
        this.collections = collections;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DonationCollection donations = collections.get(position);
        holder.textViewName.setText(donations.getType());
//        holder.textViewName.setOnClickListener(clickListener);
//        holder.textViewName.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }

    }

}
