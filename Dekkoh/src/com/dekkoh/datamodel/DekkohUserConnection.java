
package com.dekkoh.datamodel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.TextUtils;

import com.dekkoh.util.Log;
import com.google.gson.annotations.SerializedName;

public class DekkohUserConnection implements Serializable {
    private String connection_id;
    private String connection_name;
    private String connection_pic;
    private String connection_type;
    private String created_at;
    private String updated_at;
    private boolean delete_flg;
    private ConnectionID _id;
    private UserID user_id;
    private Date date;

    /**
     * @return the connection_id
     */
    public String getConnectionId() {
        return connection_id;
    }

    /**
     * @param connection_id the connection_id to set
     */
    public void setConnectionId(String connection_id) {
        this.connection_id = connection_id;
    }

    /**
     * @return the connection_name
     */
    public String getConnectionName() {
        return connection_name;
    }

    /**
     * @param connection_name the connection_name to set
     */
    public void setConnectionName(String connection_name) {
        this.connection_name = connection_name;
    }

    /**
     * @return the connection_pic
     */
    public String getConnectionPic() {
        return connection_pic;
    }

    /**
     * @param connection_pic the connection_pic to set
     */
    public void setConnectionPic(String connection_pic) {
        this.connection_pic = connection_pic;
    }

    /**
     * @return the connection_type
     */
    public String getConnectionType() {
        return connection_type;
    }

    /**
     * @param connection_type the connection_type to set
     */
    public void setConnectionType(String connection_type) {
        this.connection_type = connection_type;
    }

    /**
     * @return the created_at
     */
    public String getCreatedAt() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    /**
     * @return the updated_at
     */
    public String getUpdatedAt() {
        return updated_at;
    }

    /**
     * @param updated_at the updated_at to set
     */
    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * @return the connectionID
     */
    public String getConnectionID() {
        return _id.getConnectionID();
    }

    /**
     * @param connectionID the connectionID to set
     */
    public void setConnectionID(String connectionID) {
        this._id.setConnectionID(connectionID);
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return user_id.getUserID();
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.user_id.setUserID(userID);
        ;
    }

    /**
     * @return the delete_flg
     */
    public boolean isDeleted() {
        return delete_flg;
    }

    /**
     * @param delete_flg the delete_flg to set
     */
    public void setDeleted(boolean delete_flg) {
        this.delete_flg = delete_flg;
    }

    class ConnectionID {
        @SerializedName("$oid")
        private String connectionID;

        /**
         * @return the connectionID
         */
        public String getConnectionID() {
            return connectionID;
        }

        /**
         * @param connectionID the connectionID to set
         */
        public void setConnectionID(String connectionID) {
            this.connectionID = connectionID;
        }

    }

    class UserID {
        @SerializedName("$oid")
        private String userID;

        /**
         * @return the userID
         */
        public String getUserID() {
            return userID;
        }

        /**
         * @param userID the userID to set
         */
        public void setUserID(String userID) {
            this.userID = userID;
        }

    }

    public Date getDate() {
        if (date == null) {
            SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                if (TextUtils.isEmpty(updated_at))
                    this.date = simpleDateFormatter.parse(created_at);
                else
                    this.date = simpleDateFormatter.parse(updated_at);
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e(e);
            }
        }
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String created_at) {
        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            this.date = simpleDateFormatter.parse(created_at);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(e);
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DekkohUserConnection other = (DekkohUserConnection) obj;
        if (this.getConnectionId() != other.getConnectionId()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.getConnectionId().hashCode();
        return hash;
    }
}
