package com.example.jdemo.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jdemo.BaseConstant;
import com.example.jdemo.databinding.FavouriteRecyclerItemBinding;
import com.example.jdemo.db.entity.Shoe;

public class FavouriteAdapter extends ListAdapter<Shoe, FavouriteAdapter.FavouriteViewHolder> {

    private Context mContext;

    protected FavouriteAdapter(Context context) {
        super(new DiffUtil.ItemCallback<Shoe>() {
            @Override
            public boolean areItemsTheSame(@NonNull Shoe oldItem, @NonNull Shoe newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Shoe oldItem, @NonNull Shoe newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        });
        mContext = context;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FavouriteRecyclerItemBinding binding = FavouriteRecyclerItemBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false);
        return new FavouriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {

        Shoe shoe = getItem(position);
        if (shoe != null) {
            holder.bind(shoe, createListener(shoe));
        }
    }

    private View.OnClickListener createListener(final Shoe shoe) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(BaseConstant.DETAIL_SHOE_ID, shoe.getId());
                mContext.startActivity(intent);
            }
        };
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {

        private FavouriteRecyclerItemBinding binding;

        FavouriteViewHolder(FavouriteRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Shoe shoe, View.OnClickListener listener) {
            binding.setShoe(shoe);
            binding.setListener(listener);
        }
    }

}
