package net.redreaper.twilight_spellbooks.item.staffs;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import net.minecraft.world.item.Rarity;
import net.redreaper.twilight_spellbooks.init.ModStaffMaterials;

public class BorealStaff extends StaffItem {
    public BorealStaff() {
        super(
                new Properties()
                        .stacksTo(1)
                        .rarity(Rarity.RARE)
                        .attributes(ExtendedSwordItem.createAttributes(ModStaffMaterials.BOREAL)
                        )
        );
    }

}
