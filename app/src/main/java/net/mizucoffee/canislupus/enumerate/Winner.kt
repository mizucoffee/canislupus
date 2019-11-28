package net.mizucoffee.canislupus.enumerate

enum class Winner(val result: String, val sub: String) {
    VILLAGER_NORMAL("村人陣営", ""),
    WEREWOLF_NORMAL("人狼陣営", ""),
    WEREWOLF_MADMAN("人狼陣営", "狂人死亡"),
    TANNER_NORMAL("第三陣営", ""),
    VILLAGER_PEACE("村人陣営", "平和村"),
    WEREWOLF_PEACE("人狼陣営", "平和村 - 勝者なし"),
    VILLAGER_TANNER("村人陣営", "吊人回避")
}