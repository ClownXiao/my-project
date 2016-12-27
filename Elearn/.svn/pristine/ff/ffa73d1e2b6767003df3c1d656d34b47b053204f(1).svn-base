/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package demo.bupt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



/*import weibo4android.http.Response;
import weibo4android.org.json.JSONArray;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;*/

/**
 * A data class representing one single status of a user.
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class Status  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8795691786466526420L;

	//private User user = null;
	
	private Date createdAt;             //status创建时间
	private long id;                    //status id
	private String text;                //微博内容
	private String source;              //微博内容
	private boolean isTruncated;        //微博内容
	private long inReplyToStatusId;
	private long inReplyToUserId;
	private boolean isFavorited;        //微博内容
	private String inReplyToScreenName;
	private double latitude = -1;       //纬度
	private double longitude = -1;      //经度
	private String thumbnail_pic;       //微博内容中的图片的缩略地址
	private String bmiddle_pic;         //中型图片
	private String original_pic;        //原始图片
	private Status retweeted_status;    //转发的微博内容
	private String mid;                 //mid
	private int reposts_count;//转发数
	private int comments_count;//评论数

	

	private void getGeoInfo(String geo) {
		StringBuffer value= new StringBuffer();
		for(char c:geo.toCharArray()){
			if(c>45&&c<58){
				value.append(c);
			}
			if(c==44){
				if(value.length()>0){
					latitude=Double.parseDouble(value.toString());
					value.delete(0, value.length());
				}
			}
		}
		longitude=Double.parseDouble(value.toString());
	}


	
	/**
	 * Return the created_at
	 *
	 * @return created_at
	 * @since Weibo4J 1.1.0
	 */
	

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public int getReposts_count() {
		return reposts_count;
	}

	public int getComments_count() {
		return comments_count;
	}

	/**
	 * Returns the id of the status
	 *
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Returns the text of the status
	 *
	 * @return the text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Returns the source
	 *
	 * @return the source
	 * @since Weibo4J 1.2.1
	 */
	public String getSource() {
		return this.source;
	}


	/**
	 * Test if the status is truncated
	 *
	 * @return true if truncated
	 * @since Weibo4J 1.2.1
	 */
	public boolean isTruncated() {
		return isTruncated;
	}

	/**
	 * Returns the in_reply_tostatus_id
	 *
	 * @return the in_reply_tostatus_id
	 * @since Weibo4J 1.2.1
	 */
	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	/**
	 * Returns the in_reply_user_id
	 *
	 * @return the in_reply_tostatus_id
	 * @since Weibo4J 1.2.1
	 */
	public long getInReplyToUserId() {
		return inReplyToUserId;
	}

	/**
	 * Returns the in_reply_to_screen_name
	 *
	 * @return the in_in_reply_to_screen_name
	 * @since Weibo4J 1.2.1
	 */
	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	/**
	 * returns The location's latitude that this tweet refers to.
	 *
	 * @since Weibo4J 1.2.1
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * returns The location's longitude that this tweet refers to.
	 *
	 * @since Weibo4J 1.2.1
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Test if the status is favorited
	 *
	 * @return true if favorited
	 * @since Weibo4J 1.2.1
	 */
	public boolean isFavorited() {
		return isFavorited;
	}

	public String getThumbnail_pic() {
		return thumbnail_pic;
	}

	public String getBmiddle_pic() {
		return bmiddle_pic;
	}

	public String getOriginal_pic() {
		return original_pic;
	}


	/**
	 * Return the user
	 *
	 * @return the user
	 */
	
	/**
	 *
	 * @since Weibo4J 1.2.1
	 */
	public boolean isRetweet(){
		return null != retweeted_status;
	}

	public Status getRetweeted_status() {
		return retweeted_status;
	}

	public String getMid() {
		return mid;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/*@Override
	public String toString() {
		return "Status [createdAt=" + createdAt + ", id=" + id + ", text="
		+ text + ", source=" + source + ", isTruncated=" + isTruncated
		+ ", inReplyToStatusId=" + inReplyToStatusId
		+ ", inReplyToUserId=" + inReplyToUserId + ", isFavorited="
		+ isFavorited + ", inReplyToScreenName=" + inReplyToScreenName
		+ ", latitude=" + latitude + ", longitude=" + longitude
		+ ", thumbnail_pic=" + thumbnail_pic + ", bmiddle_pic="
		+ bmiddle_pic + ", original_pic=" + original_pic
		+ ",  mid=" + mid + ", user=" + user 
		+", retweeted_status="+(retweeted_status==null?"null":retweeted_status.toString())+ 
		"]";
	}*/


}
