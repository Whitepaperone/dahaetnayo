import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproj.R
import kotlinx.android.synthetic.main.list_item_store.view.*
import kotlinx.android.synthetic.main.store_snack.*

class store_snack_Fragment: Fragment() {

    private var list: RecyclerView? = null
    private var recyclerAdapter: adapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.store_snack_fragment, container, false);
        list = view.findViewById(R.id.store_rv) as RecyclerView

        val test = ArrayList<Data_Snack>()

        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        list!!.layoutManager = layoutManager
        recyclerAdapter = adapter(requireActivity().applicationContext, test)
        list!!.addItemDecoration(CustomDividerItemDecoration(requireActivity().applicationContext))
        list!!.adapter = recyclerAdapter
        return view
    }

    private inner class CustomDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
        private val drawableline: Drawable? = androidx.core.content.ContextCompat.getDrawable(context, R.drawable.recyclerline)
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom = top + drawableline!!.intrinsicHeight
                drawableline.setBounds(left, top, right, bottom)
                drawableline.draw(c)
            }
        }//end of onDrawOver
    }// end of CustomDividerItemDecoration

    private inner class adapter(private val context: Context, internal var mData: ArrayList<Data_Snack>) : RecyclerView.Adapter<adapter.myViewHolder>(){
        var sharedPref = requireActivity().getSharedPreferences("TODO_PREF", Context.MODE_PRIVATE)

        private fun updateData(coins:Int){
            // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
            var coin=sharedPref.getInt("KEY_COIN",0)
            coin = coin?.minus(coins)

            val editor=sharedPref.edit()
            // 키와 밸류를 쌍으로 저장하고 apply한다
            editor.putInt("KEY_COIN",coin)
            editor.commit()

            var test = activity!!.coinView
            test.text = coin.toString()
        }

        override fun getItemCount(): Int {
            Log.d("ITEM", "getItemCount() 호출")
            return Data_Snack.values().size
        }//end of ItemAdapter

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int): myViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_store, parent, false)
            return myViewHolder(view)
        }


        override fun onBindViewHolder(holder: adapter.myViewHolder, position: Int) {
            holder.bindItemData(Data_Snack.values()[position])
        }

        inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val itemName = itemView.tv_item_name;
            val detailInfo = itemView.tv_detail_info
            val itemPrice = itemView.tv_item_price
            val imgDetailItem = itemView.tv_item_image


            val sharedPref = requireActivity().getSharedPreferences("TODO_PREF", Context.MODE_PRIVATE)
            val snackPref = requireActivity().getSharedPreferences("SNACK_PREF", Context.MODE_PRIVATE)
            val snack_editor = snackPref.edit()

            fun bindItemData(dataSnack: Data_Snack){
                itemName.text = dataSnack.itemName
                detailInfo.text = dataSnack.itemDetail
                itemPrice.text = dataSnack.price.toString()
                imgDetailItem.setImageResource(dataSnack.itemImg)

                itemView.btn_buy.setOnClickListener( View.OnClickListener {

                    var coin=sharedPref.getInt("KEY_COIN",0)
                    var snack = snackPref.getInt(dataSnack.itemName, 0)

                    if(coin>=dataSnack.price){
                        updateData(dataSnack.price)
                        snack += 1
                        snack_editor.putInt(dataSnack.itemName, snack)
                        snack_editor.commit()
                        Toast.makeText(context, dataSnack.itemName + "을(를) 구매했습니다!", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(context,"돈이 부족합니다",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}