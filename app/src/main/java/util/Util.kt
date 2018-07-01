package util


/**
 * Created by cdc_dev on 6/30/18.
 */
class Util {
    companion object {

        //


        //
        fun validate(email:String,password:String): Int {


            if (email.isEmpty()) {
                return 9999
            }

            if (password.isEmpty()) {
                return 9999
            }

            if(password.length<6){
                return 9998
            }

           return 0
        }


        fun isNotNullText(text:String):Boolean{

            var isValid=true
            if (text.isEmpty()) isValid=false
            return isValid
        }




    }
}