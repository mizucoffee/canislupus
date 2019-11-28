package net.mizucoffee.canislupus.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.stephentuso.welcome.*
import net.mizucoffee.canislupus.R
import com.stephentuso.welcome.FragmentWelcomePage
import androidx.fragment.app.Fragment
import net.mizucoffee.canislupus.fragment.WelcomeLoginFragment


class WelcomeScreenActivity : WelcomeActivity() {

    override fun configuration(): WelcomeConfiguration {
        return WelcomeConfiguration.Builder(this)

            .page(
                TitlePage(R.drawable.logo, "CanisLupus")
                    .background(R.color.colorPrimary)
            )
            .page(
                BasicPage(R.drawable.ic_werewolf, "概要", "CanisLupusは次世代のワンナイト人狼用GMアプリです。")
                    .background(R.color.colorPrimaryDark)
            )
            .page(
                BasicPage(R.drawable.ic_seer, "タブレット連携機能", "タブレットと連携して結果表示を皆で見やすく。")
                    .background(R.color.orange)
            )
            .page(
                BasicPage(R.drawable.ic_thief, "ログイン機能", "QRコードを使って簡単ログイン。戦績を記録出来ます。")
                    .background(R.color.blue)
            )
            .page(
                BasicPage(R.drawable.ic_villager, "プレーヤー登録", "まずはあなたについて教えてください！")
                    .background(R.color.colorAccent)
            )
            .page(object : FragmentWelcomePage() {
                override fun fragment(): Fragment = WelcomeLoginFragment()
            }.background(R.color.colorPrimary)
            )
            .canSkip(false)
            .useCustomDoneButton(true)
            .exitAnimation(android.R.anim.fade_out)
            .build()
    }
}
