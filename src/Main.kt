import kotlin.math.pow

fun main(){
    print("Введите IP сети: ")
    val ip = readLine()!!

    print("Введите маску (например 24): ")
    val mask = readLine()!!.toInt()

    val totalAddresses = 1 shl (32 - mask)

    print ("Сколько подсетей нужно?: ")
    val count = readLine()!!.toInt()
    if (count <= 0) {
        println("Ошибка: количество подсетей должно быть больше 0")
        return
    }

    val hosts = mutableListOf<Int>()

    var usedAddresses = 0

    for (i in 1..count) {
        print("Введите количество хостов для подсети $i: ")
        val host = readLine()!!.toInt()

        if (host <= 0) {
            println("Ошибка: количество хостов должно быть больше 0")
            return
        }

        hosts.add(host)
    }

    hosts.sortDescending()

    var currentIp = ipToInt(ip)
    println("Результат: ")

    for (host in hosts){
        var blockSize = 1

        while (blockSize - 2 < host){
            blockSize *= 2
        }

        if (usedAddresses + blockSize > totalAddresses) {
            println("\nОшибка: адресов не хватает!")
            println("Нужно еще $blockSize адресов, но свободных осталось ${totalAddresses - usedAddresses}")
            return
        }

        val newMask = 32 - log2(blockSize)

        val network = currentIp
        val first = currentIp + 1L
        val last = currentIp + blockSize - 2L
        val broadcast = currentIp + blockSize - 1L

        println("\nПодсеть:")
        println("Сеть: ${intToIp(network)}")
        println("Маска: /$newMask")
        println("Первый IP: ${intToIp(first)}")
        println("Последний IP: ${intToIp(last)}")
        println("Broadcast: ${intToIp(broadcast)}")

        currentIp += blockSize.toLong()
        usedAddresses += blockSize
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

fun ipToInt(ip: String): Long{
    val parts = ip.split(".")
    val a = parts[0].toLong()
    val b = parts[1].toLong()
    val c = parts[2].toLong()
    val d = parts[3].toLong()

    return a * 256 * 256 * 256 +
            b * 256 * 256 +
            c * 256 +
            d
}

fun intToIp(num: Long): String{
    val a = num / (256 * 256 * 256)
    val b = (num / (256 * 256)) % 256
    val c = (num / 256) % 256
    val d = num % 256

    return "$a.$b.$c.$d"
}