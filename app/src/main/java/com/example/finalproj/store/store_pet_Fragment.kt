package com.example.finalproj.store
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproj.R
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.list_item_store.view.*
import kotlinx.android.synthetic.main.list_item_store.view.btn_buy
class store_pet_Fragment: Fragment() {
    private var list: RecyclerView? = null
    private var recyclerAdapter: adapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.store_snack_fragment, container, false)
        list = view.findViewById(R.id.store_rv) as RecyclerView
        val test = ArrayList<Data_Pet>()
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        list!!.layoutManager = layoutManager
        recyclerAdapter = adapter(requireActivity().applicationContext, test)
        list!!.addItemDecoration(CustomDividerItemDecoration(requireActivity().applicationContext))
        list!!.adapter = recyclerAdapter
        return view
    }// end of onCreateView
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
        }
    }//end of CustomDividerItemDecoration
    private inner class adapter(private val context: Context, internal var mData: ArrayList<Data_Pet>) : RecyclerView.Adapter<adapter.myViewHolder>(){
        private fun updateData(coins:Int){
            var sharedPref: SharedPreferences = requireActivity().getSharedPreferences("TODO_PREF", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =sharedPref.edit()
            // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
            var coin=sharedPref.getInt("KEY_COIN",0)
            coin = coin?.minus(coins)
            // 키와 밸류를 쌍으로 저장하고 apply한다
            editor.putInt("KEY_COIN",coin)
            editor.commit()
            var test = activity!!.coinView
            test.text = coin.toString()
        }
        override fun getItemCount(): Int {
            Log.d("ITEM", "getItemCount() 호출")
            return Data_Pet.values().size
        }//end of ItemAdapter
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int): myViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_store, parent, false)
            return myViewHolder(view)
        }
        override fun onBindViewHolder(holder: adapter.myViewHolder, position: Int) {
            holder.bindItemData(Data_Pet.values()[position])
        }//end of onBindViewHolder
        inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val itemName = itemView.tv_item_name;
            val itemDetail = itemView.tv_detail_info
            val Price = itemView.tv_item_price
            val imgDetailItem = itemView.tv_item_image
            val won = itemView.tv_item_coin

            var use: Button = itemView.btn_buy

            var sharedPref: SharedPreferences = requireActivity().getSharedPreferences("TODO_PREF", Context.MODE_PRIVATE)
            var storePref: SharedPreferences = requireActivity().getSharedPreferences("STORE_PREF", Context.MODE_PRIVATE)
            val store_editor: SharedPreferences.Editor = storePref.edit()

            fun reset() {
                if(storePref.getInt(Data_Pet.Pet1.itemName, 0).equals(2)) {
                    store_editor.putInt(Data_Pet.Pet1.itemName, 1)
                    store_editor.commit()
                } else if(storePref.getInt(Data_Pet.Pet1.itemName, 0).equals(2)) {
                    store_editor.putInt(Data_Pet.Pet2.itemName, 1)
                    store_editor.commit()
                }
                store_editor.putInt("change_pet", 1)
                store_editor.commit()
            }//end of reset

            fun bindItemData(dataPet: Data_Pet){
                itemName.text = dataPet.itemName
                itemDetail.text = dataPet.itemDetail
                Price.text = dataPet.price.toString()
//                imgDetailItem.setImageResource(dataPet.itemImg)
                var item = storePref.getInt(dataPet.itemName, 0)
                if (item == 1 || item == 2){
                    Price.visibility = GONE
                    use.text = "적용"
                    won.visibility = View.GONE
                }
                else{
                    use.text = "구매"
                }

                itemView.btn_buy.setOnClickListener( OnClickListener {
                    var coin=sharedPref.getInt("KEY_COIN",0)
                    if(coin>=dataPet.price){
                        if (storePref.getInt(dataPet.itemName, 0).equals(0)) {
                            Toast.makeText(
                                context,
                                dataPet.itemName + "을(를) 구매했습니다!\n"+"뒤로 가기를 누르면 앱을 재시작합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Price.visibility = View.GONE
                            won.visibility = View.GONE
                            use.text = "적용"
                            reset()
                            store_editor.putInt(dataPet.itemName, 2)
                            store_editor.putInt("use_theme", 1)
                            store_editor.commit()
                            updateData(dataPet.price)
                        }
                        else{
                            val toast: Toast = Toast.makeText(context, dataPet.itemName + "을(를) 적용합니다.\n" +
                                    "뒤로 가기를 누르면 앱을 재시작합니다.", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, 0)
                            toast.show()
                            reset()
                            store_editor.putInt(dataPet.itemName, 2)
                            store_editor.putInt("use_theme", 1)
                            store_editor.commit()
                        }
                    } else {
                        Toast.makeText(context,"돈이 부족합니다",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}
