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

class CulinaryPlantListAdapter(
    private var biljke: List<Biljka>
) : RecyclerView.Adapter<CulinaryPlantListAdapter.CulinaryPlantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CulinaryPlantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.culinary_item, parent, false)
        return CulinaryPlantViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: CulinaryPlantViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv
        holder.profil.text = biljke[position].profilOkusa.opis
        holder.jelo1.text = if(biljke[position].jela.size > 0) biljke[position].jela[0] else ""
        holder.jelo2.text = if(biljke[position].jela.size > 1) biljke[position].jela[1] else ""
        holder.jelo3.text = if(biljke[position].jela.size > 2) biljke[position].jela[2] else ""

        val context: Context = holder.slika.context
        val id: Int = context.resources.getIdentifier("picture1", "drawable", context.packageName)
        holder.slika.setImageResource(id)
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