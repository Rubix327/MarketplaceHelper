package me.rubix327.marketplacehelper;

import java.util.Objects;

public abstract class MarketPlaceHelperBundle {

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getVersion();
    public abstract void init();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketPlaceHelperBundle that = (MarketPlaceHelperBundle) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getVersion(), that.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getVersion());
    }
}
