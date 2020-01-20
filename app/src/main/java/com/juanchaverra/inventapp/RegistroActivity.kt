package com.juanchaverra.inventapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.juanchaverra.inventapp.utils.Constantes.Companion.EMPTY
import com.juanchaverra.inventapp.utils.Constantes.Companion.INTERLIN
import com.juanchaverra.inventapp.utils.Constantes.Companion.SPACE
import kotlinx.android.synthetic.main.activity_registro.*
import java.text.SimpleDateFormat
import java.util.*


class RegistroActivity : AppCompatActivity()
{
    private var cal = Calendar.getInstance()
    private lateinit var fecha : String
    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //val btn_registro : Button =findViewById(R.ud.bt_registrar
        var sexo = getString(R.string.masculino)
        rb_masculino.setOnClickListener{
            sexo = getString(R.string.masculino)
        }

        rb_femenino.setOnClickListener{
            sexo = getString(R.string.femenino)
        }

        val dataSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set ( Calendar.YEAR, year)
                cal.set (Calendar.MONTH, month)
                cal.set ( Calendar.DAY_OF_YEAR, dayOfMonth)

                val format = getString(R.string.formato)
                val sdf = SimpleDateFormat(format, Locale.US)
                fecha = sdf.format(cal.time).toString()
                tv_showPicker.text = fecha
                //Log.d("Fecha",fecha)
            }


        tv_showPicker.setOnClickListener {
            DatePickerDialog(
                this,
                dataSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        bt_registrar.setOnClickListener {
            Log.d("button", "click")
            val nombre = et_nombre_r.text.toString()
            val correo = et_correo_r.text.toString()
            val telefono  = et_telefono_r.text.toString()
            val password = et_password_r.text.toString()
            val verifyPass = et_verpass_r.text.toString()
            var pasatiempos = EMPTY
            var city = EMPTY

            if (cb_cine.isChecked) pasatiempos = pasatiempos + SPACE + getString(R.string.cine)
            if (cb_deporte.isChecked) pasatiempos =
                pasatiempos + SPACE + getString(R.string.deporte)
            if (cb_cicla.isChecked) pasatiempos = pasatiempos + SPACE + getString(R.string.ciclismo)
            if (cb_leer.isChecked) pasatiempos = pasatiempos + SPACE + getString(R.string.leer)

            val adapter = ArrayAdapter.createFromResource(this, R.array.city_origen,
                android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp_ciudad.adapter= adapter

            city = sp_ciudad.selectedItem.toString()

            // val repPassword =
            //var sexo = "Masculino"

            //  if(rb_masculino.isChecked) sexo = "Masculino"
            // else sexo = "Femenino"

            if (nombre.isEmpty() || correo.isEmpty() || telefono.toInt().toString().isEmpty() || password.isEmpty() ||
                verifyPass.isEmpty() || pasatiempos.isEmpty() || fecha.isEmpty()) {
                val toast = Toast.makeText(
                    this,
                    getString(R.string.msg_error_campos_vacios),
                    Toast.LENGTH_SHORT)
                toast.show()

            }

            else
            {
                if (verifyPassword(password, verifyPass))
                {
                    tv_resultado.text = getString(R.string.nombre_lb) + SPACE + nombre +
                        INTERLIN + getString(R.string.correo_lb) + SPACE + correo +
                        INTERLIN + getString(R.string.telefono_lb) + SPACE + telefono +
                        INTERLIN + getString(R.string.sexo_lb) + SPACE + sexo +
                        INTERLIN + getString(R.string.pasatiempos_lb) + SPACE + pasatiempos +
                        INTERLIN + getString(R.string.fecha_lb) + SPACE + fecha +
                        INTERLIN + getString(R.string.reg_act_city) + SPACE + city
                        Log.d("Ciudad: ", city)
                }

                else
                {   val error = Toast.makeText(
                        this,
                        getString(R.string.msg_pass_not_verify),
                        Toast.LENGTH_SHORT)
                    error.show()
                   // Log.d("contraseñas no iguales", password)
                }
            }
        }
    }

    private fun verifyPassword(pass: String, verify: String): Boolean {
        var v = false
        if(pass.equals(verify))
        {
            v = true
        }
        return v
    }
}



