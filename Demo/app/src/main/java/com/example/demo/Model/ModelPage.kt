package com.example.demo.Model

import com.google.gson.annotations.SerializedName




class Data {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: Any? = null
    @SerializedName("datetime")
    var datetime: Int? = null
    @SerializedName("cover")
    var cover: String? = null
    @SerializedName("cover_width")
    var coverWidth: Int? = null
    @SerializedName("cover_height")
    var coverHeight: Int? = null
    @SerializedName("account_url")
    var accountUrl: String? = null
    @SerializedName("account_id")
    var accountId: Int? = null
    @SerializedName("privacy")
    var privacy: String? = null
    @SerializedName("layout")
    var layout: String? = null
    @SerializedName("views")
    var views: Int? = null
    @SerializedName("link")
    var link: String? = null
    @SerializedName("ups")
    var ups: Int? = null
    @SerializedName("downs")
    var downs: Int? = null
    @SerializedName("points")
    var points: Int? = null
    @SerializedName("score")
    var score: Int? = null
    @SerializedName("is_album")
    var isAlbum: Boolean? = null
    @SerializedName("vote")
    var vote: Any? = null
    @SerializedName("favorite")
    var favorite: Boolean? = null
    @SerializedName("nsfw")
    var nsfw: Boolean? = null
    @SerializedName("section")
    var section: String? = null
    @SerializedName("comment_count")
    var commentCount: Int? = null
    @SerializedName("favorite_count")
    var favoriteCount: Int? = null
    @SerializedName("topic")
    var topic: String? = null
    @SerializedName("topic_id")
    var topicId: Int? = null
    @SerializedName("images_count")
    var imagesCount: Int? = null
    @SerializedName("in_gallery")
    var inGallery: Boolean? = null
    @SerializedName("is_ad")
    var isAd: Boolean? = null
    @SerializedName("tags")
    var tags: List<Any>? = null
    @SerializedName("ad_type")
    var adType: Int? = null
    @SerializedName("ad_url")
    var adUrl: String? = null
    @SerializedName("in_most_viral")
    var inMostViral: Boolean? = null
    @SerializedName("include_album_ads")
    var includeAlbumAds: Boolean? = null
    @SerializedName("images")
    var images: List<Image>? = null
    @SerializedName("ad_config")
    var adConfig: AdConfig? = null
}

class Image {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("title")
    var title: Any? = null
    @SerializedName("description")
    var description: Any? = null
    @SerializedName("datetime")
    var datetime: Int? = null
    @SerializedName("type")
    var type: String? = null
    @SerializedName("animated")
    var animated: Boolean? = null
    @SerializedName("width")
    var width: Int? = null
    @SerializedName("height")
    var height: Int? = null
    @SerializedName("size")
    var size: Int? = null
    @SerializedName("views")
    var views: Int? = null
    @SerializedName("vote")
    var vote: Any? = null
    @SerializedName("favorite")
    var favorite: Boolean? = null
    @SerializedName("nsfw")
    var nsfw: Any? = null
    @SerializedName("section")
    var section: Any? = null
    @SerializedName("account_url")
    var accountUrl: Any? = null
    @SerializedName("account_id")
    var accountId: Any? = null
    @SerializedName("is_ad")
    var isAd: Boolean? = null
    @SerializedName("in_most_viral")
    var inMostViral: Boolean? = null
    @SerializedName("has_sound")
    var hasSound: Boolean? = null
    @SerializedName("tags")
    var tags: List<Any>? = null
    @SerializedName("ad_type")
    var adType: Int? = null
    @SerializedName("ad_url")
    var adUrl: String? = null
    @SerializedName("edited")
    var edited: String? = null
    @SerializedName("in_gallery")
    var inGallery: Boolean? = null
    @SerializedName("link")
    var link: String? = null
    @SerializedName("comment_count")
    var commentCount: Any? = null
    @SerializedName("favorite_count")
    var favoriteCount: Any? = null
    @SerializedName("ups")
    var ups: Any? = null
    @SerializedName("downs")
    var downs: Any? = null
    @SerializedName("points")
    var points: Any? = null
    @SerializedName("score")
    var score: Any? = null
}

class AdConfig {
    @SerializedName("safeFlags")
    var safeFlags: List<String>? = null
    @SerializedName("highRiskFlags")
    var highRiskFlags: List<Any>? = null
    @SerializedName("unsafeFlags")
    var unsafeFlags: List<String>? = null
    @SerializedName("wallUnsafeFlags")
    var wallUnsafeFlags: List<Any>? = null
    @SerializedName("showsAds")
    var showsAds: Boolean? = null
}

class ModelPage {
    @SerializedName("data")
    var data: List<Data>? = null
    @SerializedName("success")
    var success: Boolean? = null
    @SerializedName("status")
    var status: Int? = null
}