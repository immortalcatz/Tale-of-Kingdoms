package kingdoms.server

import com.google.gson.annotations.SerializedName
import java.util.*

internal class ConfigTemplate
{
    @SerializedName("onlyOpBuild")
    var isOp: Boolean = false

    private val prices = HashMap<String, Int>()
    private val sales = HashMap<String, Int>()

    val names: List<String>
        get() = ArrayList(prices.keys)

    val salesName: List<String>
        get() = ArrayList(sales.keys)

    fun setPrices(name: String, price: Int)
    {
        prices[name] = price
    }

    fun setSales(name: String, price: Int)
    {
        sales[name] = price
    }

    fun getPrices(name: String): Int?
    {
        return if (prices[name] != null) prices[name] else 0
    }

    fun getSales(name: String): Int?
    {
        return if (sales[name] != null) prices[name] else 0
    }
}