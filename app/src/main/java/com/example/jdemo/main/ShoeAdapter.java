package com.example.jdemo.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jdemo.BaseConstant;
import com.example.jdemo.databinding.ShoeRecyclerItemBinding;
import com.example.jdemo.db.entity.Shoe;

public class ShoeAdapter extends PagedListAdapter<Shoe, ShoeAdapter.MyViewHolder> {

    private Context mContext;

    public ShoeAdapter(Context context) {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ShoeRecyclerItemBinding binding = ShoeRecyclerItemBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private ShoeRecyclerItemBinding binding;

        MyViewHolder(ShoeRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Shoe shoe, View.OnClickListener listener) {
            binding.setShoe(shoe);
            binding.setListener(listener);
        }
    }
}
