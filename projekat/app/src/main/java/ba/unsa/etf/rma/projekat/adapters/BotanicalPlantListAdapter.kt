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
        holder.klima.text = biljka.klimatskiTipovi[0].opis
        holder.zemljiste.text = biljka.zemljisniTipovi[0].naziv

        val context: Context = holder.slika.context
        val id: Int = context.resources.getIdentifier("picture1", "drawable", context.packageName)
        holder.slika.setImageResource(id)

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

