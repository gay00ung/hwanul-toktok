package net.ifmain.hwanul_toktok.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ExchangeRateDto(
    @SerializedName("result")
    val result: Int,
    @SerializedName("cur_unit")
    val currencyUnit: String,
    @SerializedName("ttb")
    val ttb: String,
    @SerializedName("tts")
    val tts: String,
    @SerializedName("deal_bas_r")
    val dealBaseRate: String,
    @SerializedName("bkpr")
    val bookPrice: String,
    @SerializedName("yy_efee_r")
    val yearExchangeFeeRate: String,
    @SerializedName("ten_dd_efee_r")
    val tenDayExchangeFeeRate: String,
    @SerializedName("kftc_bkpr")
    val kftcBookPrice: String,
    @SerializedName("kftc_deal_bas_r")
    val kftcDealBaseRate: String,
    @SerializedName("cur_nm")
    val currencyName: String
)