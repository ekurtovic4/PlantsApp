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
import ba.unsa.etf.rma.projekat.web.TrefleDAO
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CulinaryPlantListAdapter(
    private var biljke: List<Biljka>,
    private val onItemClicked: (biljka: Biljka) -> Unit
) : RecyclerView.Adapter<CulinaryPlantListAdapter.CulinaryPlantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CulinaryPlantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.culinary_item, parent, false)
        return CulinaryPlantViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: CulinaryPlantViewHolder, position: Int) {
        val biljka = biljke[position]
        holder.naziv.text = biljka.naziv
        holder.profil.text = biljka.profilOkusa?.opis
        holder.jelo1.text = if(biljka.jela.size > 0) biljka.jela[0] else ""
        holder.jelo2.text = if(biljka.jela.size > 1) biljka.jela[1] else ""
        holder.jelo3.text = if(biljka.jela.size > 2) biljka.jela[2] else ""

        val context: Context = holder.slika.context
        val trefle = TrefleDAO(context)
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            Glide.with(context)
                .load(trefle.getImage(biljka))
                .centerCrop()
                .placeholder(R.drawable.picture1)
                .into(holder.slika)
        }

        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }
    }

    fun updatePlants(biljke: List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }

    inner class CulinaryPlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val slika: ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val profil: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val jelo1: TextView = itemView.findViewById(R.id.jelo1Item)
        val jelo2: TextView = itemView.findViewById(R.id.jelo2Item)
        val jelo3: TextView = itemView.findViewById(R.id.jelo3Item)
    }
}
