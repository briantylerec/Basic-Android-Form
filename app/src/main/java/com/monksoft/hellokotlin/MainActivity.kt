package com.monksoft.hellokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.monksoft.hellokotlin.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val paises = arrayOf("Ecuador", "Colombia", "Peru")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, paises)

        binding.actvCountries.setAdapter((adapter))
        binding.actvCountries.setOnItemClickListener { adapterView, view, i, l ->
            binding.lNacimiento.requestFocus()
            Toast.makeText(this, paises.get(i), Toast.LENGTH_SHORT).show()
        }

        binding.fechaNacimiento.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener { timems ->
                val dateStr = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply{
                    timeZone = TimeZone.getTimeZone("UTC")
                }.format(timems)
                binding.fechaNacimiento.setText(dateStr)
            }

            picker.show(supportFragmentManager, picker.toString())

        }

        inicio()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_send && validateFields()){
            val name:String = binding.nombre.text.toString()
            val apellido:String = binding.apellidos.text.toString()
            Toast.makeText(this, "CLic en send $name", Toast.LENGTH_SHORT).show()

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Dialogo")
            builder.setMessage("$name $apellido")
            builder.setPositiveButton("ok",{
                dialogInterface, i ->
                with(binding){
                    nombre.text?.clear()
                    apellidos.text?.clear()
                    altura.text?.clear()
                }
            })
            builder.setNegativeButton("Cancelar", null)

            val dialog:AlertDialog = builder.create()
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        Toast.makeText(this, "Cerrando app", Toast.LENGTH_SHORT).show()
        super.onDestroy()
        Log.i("INFOR","DESTROY")
    }

    private fun validateFields(): Boolean{
        var isValid = true

        if (binding.nombre.text.isNullOrEmpty()) {
            binding.tilnombre.run {
                error = "Obligatorio"
                requestFocus()
            }
            isValid = false
        } else {
            binding.tilnombre.error = null
        }

        if (binding.apellidos.text.isNullOrEmpty()) {
            binding.tilapellido.run {
                error = "Obligatorio"
                requestFocus()
            }
            isValid = false
        } else {
            binding.tilapellido.error = null
        }

        if (binding.altura.text.isNullOrEmpty()) {
            binding.tilaltura.run {
                error = "Obligatorio"
                requestFocus()
            }
            isValid = false
        } else if (binding.altura.text.toString().toInt() < 50){
            binding.tilaltura.run {
                error = "Obligatorio"
                requestFocus()
            }
            isValid = false
        }
        else {
            binding.tilaltura.error = null
        }

        return isValid
    }

    private fun inicio() {

//        binding.button.setOnClickListener {
//            if(binding.textView.text.equals("Hola"))binding.textView.text = "Hola Brian"
//            else binding.textView.text = "Hola"
//        }
    }
}