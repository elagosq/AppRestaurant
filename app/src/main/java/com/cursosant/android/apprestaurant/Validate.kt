package com.cursosant.android.apprestaurant

import android.util.Patterns
import java.util.regex.Pattern


class Validate {

    fun validarEmail(correo: String) : String?{
        if(correo.isEmpty()){
          return "El campo no puede estar vacio"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            return "Dirección de correo electrónico no válida"
        }

        return null
    }

    fun validatePassword(password: String) : String? {
        if(password.length < 8)
        {
            return "Contraseña mínima de 8 caracteres"
        }
        if(!password.matches(".*[A-Z].*".toRegex()))
        {
            return "Debe contener 1 carácter en mayúscula"
        }
        if(!password.matches(".*[a-z].*".toRegex()))
        {
            return "Debe contener 1 carácter en minúscula"
        }
        if(!password.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Debe contener 1 carácter especial (@#\$%^&+=)"
        }

        return null
    }

    fun validateInputNulo(texto:String):Boolean{
        return texto.equals("")||texto.length==0
    }

    fun validatePhone(phoneText: String) : String?{

        if(!phoneText.matches(".*[0-9].*".toRegex()))
        {
            return "Deben ser solo numero dígitos"
        }
        if(phoneText.length <= 10)
        {
            return "Debe tener 10 dígitos"
        }
        return null
    }

    fun validateWeb(webText: String): String?{
        if(webText.isEmpty()){
            return "El campo no puede estar vacio"
        }else if(!Patterns.WEB_URL.matcher(webText).matches()){
            return "La url del sitio web no valida"
        }

        return null
    }


}