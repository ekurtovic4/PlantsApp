package ba.unsa.etf.rma.projekat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.projekat.dataetc.Biljka
import ba.unsa.etf.rma.projekat.R
import ba.unsa.etf.rma.projekat.dataetc.BiljkaDatabase
import ba.unsa.etf.rma.projekat.web.TrefleDAO
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BotanicalPlantListAdapter(
    private var biljke: List<Biljka>,
    private val onItemClicked: (biljka: Biljka) -> Unit
) : RecyclerView.Adapter<BotanicalPlantListAdapter.BotanicalPlantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotanicalPlantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.botanical_item, parent, false)
        return BotanicalPlantViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: BotanicalPlantViewHolder, position: Int) {
        val biljka = biljke[position]
        holder.naziv.text = biljka.naziv
        holder.porodica.text = biljka.porodica

        if(biljka.klimatskiTipovi.isNotEmpty())
            holder.klima.text = biljka.klimatskiTipovi[0].opis
        else holder.klima.text = ""

        if(biljka.zemljisniTipovi.isNotEmpty())
            holder.zemljiste.text = biljka.zemljisniTipovi[0].naziv
        else holder.zemljiste.text = ""

        val context: Context = holder.slika.context
        val trefle = TrefleDAO()
        trefle.setContext(context)
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            try{
                val imgBitmap = trefle.getImage(biljka)
                Glide.with(context)
                    .load(imgBitmap)
                    .centerCrop()
                    .placeholder(R.drawable.picture1)
                    .into(holder.slika)

                val biljkaDao = BiljkaDatabase.getInstance(context).biljkaDao()
                biljkaDao.addImage(biljka.id, imgBitmap)
            }
            catch(e: Exception){
                Glide.with(context)
                    .load(R.drawable.picture1)
                    .centerCrop()
                    .placeholder(R.drawable.picture1)
                    .into(holder.slika)
            }
        }

        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }
    }

    fun updatePlants(biljke: List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }

    inner class BotanicalPlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val slika: ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val porodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val klima: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val zemljiste: TextView = itemView.findViewById(R.id.zemljisniTipItem)
    }
}

