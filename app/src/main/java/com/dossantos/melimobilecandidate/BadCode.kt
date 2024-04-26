package com.dossantos.melimobilecandidate

class BadCode {

    fun doSomething(a: Int, b: Int): Int {
        var result = 0

        // Um loop muito longo e desnecessário
        for (i in 0..999999999) {
            result += a * b
        }

        // Código redundante e sem sentido
        if (a > 0) {
            println("A é maior que zero")
        } else {
            println("A não é maior que zero")
        }

        // Manipulação direta de strings sem uso adequado de métodos
        var message = "Olá, Mundo!"
        message = message.replace("a", "b").replace("e", "f")

        // Uso excessivo de variáveis globais e efeitos colaterais
        var globalVariable = 10
        result += globalVariable

        return result
    }

    // Métodos muito longos e complexos
    fun complexMethod(input: String): String {
        var output = ""

        for (char in input) {
            if (char.isLetterOrDigit()) {
                output += char.toLowerCase()
            }
        }

        return output
    }

    // Falta de tratamento de erros e exceções
    fun divide(a: Int, b: Int): Int {
        return a / b
    }
}
