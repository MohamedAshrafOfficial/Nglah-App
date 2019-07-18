package com.example.nglah.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nglah.Model.NglahModel.Nglah;
import com.example.nglah.Model.NglahModel.NglahOrdersService;
import com.example.nglah.R;

import java.util.List;

import retrofit2.Callback;

public class DriverNotificationAdapter extends RecyclerView.Adapter<DriverNotificationAdapter.NumberViewHolder>{

    private static final String TAG = NglahNotificationAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private List<Nglah> nglahOrders;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public DriverNotificationAdapter(List<Nglah> nglahOrders, ListItemClickListener listener) {

        this.nglahOrders = nglahOrders;
        this.mOnClickListener = listener;
    }

    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */

    @Override
    public DriverNotificationAdapter.NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.noti_nglah_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        DriverNotificationAdapter.NumberViewHolder viewHolder = new DriverNotificationAdapter.NumberViewHolder(view);

        return viewHolder;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(DriverNotificationAdapter.NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return nglahOrders.size();
    }

    /**
     * Cache of the children views for a list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView listItemNglahName;
        TextView listItemThingType;
        TextView listItemNglahType;
        TextView listItemNglahDetails;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listItemNglahName = itemView.findViewById(R.id.tv_nglah_name);
            listItemThingType = itemView.findViewById(R.id.tv_thing_type);
            listItemNglahType = itemView.findViewById(R.id.tv_nglah_type);
            listItemNglahDetails = itemView.findViewById(R.id.tv_details);

            itemView.setOnClickListener(this);
        }


        void bind(int listIndex) {

            listItemNglahName.setText(
                    nglahOrders.get(listIndex).getFirstName() +
                            " " + nglahOrders.get(listIndex).getLastName());
            listItemThingType.setText(nglahOrders.get(listIndex).getThingType());
            listItemNglahType.setText(nglahOrders.get(listIndex).getNglahType());
            listItemNglahDetails.setText(nglahOrders.get(listIndex).getDetails());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
