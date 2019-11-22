package net.mizucoffee.canislupus.enumerate

import net.mizucoffee.canislupus.model.werewolf.*
import net.mizucoffee.canislupus.werewolf.*

enum class CardEnum(val init: () -> Card) {
    WEREWOLF({ Werewolf() }),
    MADMAN({ Madman() }),
    SEER({ Seer() }),
    THIEF({ Thief() }),
    TANNER({ Tanner() }),
    VILLAGER({ Villager() })
}