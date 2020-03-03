package com.example.jdemo.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Calendar;

@Entity(tableName = "fav_shoe",
        foreignKeys = {
            @ForeignKey(entity = Shoe.class,
                parentColumns = "id",
                childColumns = "shoe_id"),
            @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id")
        },
        indices = {@Index(value = {"shoe_id"}),
                @Index(value = {"user_id"})})
public class FavouriteShoe {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "shoe_id")
    private long shoeId;

    @ColumnInfo(name = "user_id")
    private long userId;

    @ColumnInfo(name = "fav_date")
    private Calendar date;

    public FavouriteShoe(long shoeId, long userId, Calendar date) {
        this.shoeId = shoeId;
        this.userId = userId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getShoeId() {
        return shoeId;
    }

    public void setShoeId(long shoeId) {
        this.shoeId = shoeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
