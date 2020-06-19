/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.foodflix.helpers.constant.TableNames;
import com.example.foodflix.helpers.util.database.BaseEntity;

/**
 * The type Code.
 */
@Entity(tableName = TableNames.CODES)
public class Code extends BaseEntity {

    /**
     * Constants
     */
    public static final int QR_CODE = 1;
    /**
     * The constant BAR_CODE.
     */
    public static final int BAR_CODE = 2;
    /**
     * The constant CREATOR.
     */
    public static final Parcelable.Creator<Code> CREATOR = new Parcelable.Creator<Code>() {
        @Override
        public Code createFromParcel(Parcel source) {
            return new Code(source);
        }

        @Override
        public Code[] newArray(int size) {
            return new Code[size];
        }
    };

    /**
     * Fields
     */
    private String mContent;
    private int mType;
    private String mCodeImagePath;
    private long mTimeStamp;

    /**
     * Instantiates a new Code.
     */
    public Code() {
    }

    /**
     * Instantiates a new Code.
     *
     * @param content the content
     * @param type    the type
     */
    @Ignore
    public Code(String content, int type) {
        mContent = content;
        mType = type;
    }

    /**
     * Instantiates a new Code.
     *
     * @param content   the content
     * @param type      the type
     * @param timeStamp the time stamp
     */
    @Ignore
    public Code(String content, int type, long timeStamp) {
        mContent = content;
        mType = type;
        mTimeStamp = timeStamp;
    }

    /**
     * Instantiates a new Code.
     *
     * @param content       the content
     * @param type          the type
     * @param codeImagePath the code image path
     * @param timeStamp     the time stamp
     */
    @Ignore
    public Code(String content, int type, String codeImagePath, long timeStamp) {
        mContent = content;
        mType = type;
        mCodeImagePath = codeImagePath;
        mTimeStamp = timeStamp;
    }

    /**
     * Instantiates a new Code.
     *
     * @param in the in
     */
    @Ignore
    protected Code(Parcel in) {
        this.mContent = in.readString();
        this.mType = in.readInt();
        this.mTimeStamp = in.readLong();
        this.mCodeImagePath = in.readString();
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return mContent;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        mContent = content;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return mType;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(int type) {
        mType = type;
    }

    /**
     * Gets code image path.
     *
     * @return the code image path
     */
    public String getCodeImagePath() {
        return mCodeImagePath;
    }

    /**
     * Sets code image path.
     *
     * @param codeImagePath the code image path
     */
    public void setCodeImagePath(String codeImagePath) {
        mCodeImagePath = codeImagePath;
    }

    /**
     * Gets time stamp.
     *
     * @return the time stamp
     */
    public long getTimeStamp() {
        return mTimeStamp;
    }

    /**
     * Sets time stamp.
     *
     * @param timeStamp the time stamp
     */
    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    /**
     * Below codes are written in order to make the object parcelable
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mContent);
        dest.writeInt(this.mType);
        dest.writeLong(this.mTimeStamp);
        dest.writeString(this.mCodeImagePath);
    }
}
