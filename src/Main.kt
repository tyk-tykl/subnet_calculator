import kotlin.math.pow

fun main(){
    print("Введите IP сети: ")
    val ip = readLine()!!

    print("Введите маску (например 24): ")
    val mask = readLine()!!.toInt()

    print ("Сколько подсетей нужно? ")
    val count = readLine()!!.toInt()

    val hosts = mutableListOf<Int>()

    for (i in 1..count){
        print("Введите колчество хостов для подсети $i: ")
        hosts.add(readLine()!!.toInt())
    }

    hosts.sortDescending()

    var currentIp = ipToInt(ip)
    println("Результат: ")

    for (host in hosts){
        var blockSize = 1

        while (blockSize - 2 < host){
            blockSize *= 2
        }

        val newMask = 32 - log2(blockSize)

        val network = currentIp
        val first = currentIp + 1
        val last = currentIp + blockSize - 2
        val broadcast = currentIp + blockSize - 1

        println("\nПодсеть:")
        println("Сеть: ${intToIp(network)}")
        println("Маска: /$newMask")
        println("Первый IP: ${intToIp(first)}")
        println("Последний IP: ${intToIp(last)}")
        println("Broadcast: ${intToIp(broadcast)}")

        currentIp += blockSize
    }
}

fun log2(x: Int): Int{
    var n = 0
    var value = x

    while (value > 1){
        value /= 2
        n++
    }

    return n
}

fun ipToInt(ip: String): Int{
    val parts = ip.split(".")
    val a = parts[0].toInt()
    val b = parts[1].toInt()
    val c = parts[2].toInt()
    val d = parts[3].toInt()

    return a * 256 * 256 * 256 +
            b * 256 * 256 +
            c * 256 +
            d
}

fun intToIp(num: Int): String{
    val a = num / (256 * 256 * 256)
    val b = (num / (256 * 256)) % 256
    val c = (num / 256) % 256
    val d = num % 256

    return "$a.$b.$c.$d"
}