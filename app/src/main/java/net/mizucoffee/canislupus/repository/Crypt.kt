package net.mizucoffee.canislupus.repository

class Crypt {

    companion object {
        private const val cipher = "cpm8sk10hxgwy95uen7ofr4lvbj32tadq6zi"
        private const val plain = "0123456789abcdefghijklmnopqrstuvwxyz"
        fun crypt(text: String, number: Int): String {
            var result = text
            var crypted = ""
            for (k in 0..number) {
                for (i in result.indices) {
                    crypted += cipher[(plain.indexOf(result[i]) + i) % plain.length]
                }
                result = crypted
                crypted = ""
            }
            return result
        }
    }

}