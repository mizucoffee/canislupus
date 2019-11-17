package net.mizucoffee.canislupus.enumerate

enum class Winner(val result: String, val sub: String) {
    VILLAGER_NORMAL("村人陣営の勝利", ""),
    WEREWOLF_NORMAL("人狼陣営の勝利", ""),
    WEREWOLF_MADMAN("人狼陣営の勝利", "狂人死亡"),
    TANNER_NORMAL("第三陣営の勝利", ""),
    VILLAGER_PEACE("村人陣営の勝利", "平和村"),
    WEREWOLF_PEACE("勝者なし", "平和村"),
    VILLAGER_TANNER("村人陣営の勝利", "吊人回避")
}
// 怪盗で入れ替わった狂人が死ぬ
// 怪盗で入れ替わった人狼が死ぬ OK