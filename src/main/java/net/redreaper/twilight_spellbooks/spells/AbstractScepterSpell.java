package net.redreaper.twilight_spellbooks.spells;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;

public abstract class AbstractScepterSpell extends AbstractSpell {

    @Override
    public boolean allowLooting() {
        return false;
    }
}
