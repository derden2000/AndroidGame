package ru.geekbrains.android.pool;

import ru.geekbrains.android.base.SpritesPool;
import ru.geekbrains.android.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }
}
