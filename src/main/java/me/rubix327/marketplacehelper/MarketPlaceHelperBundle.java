package me.rubix327.marketplacehelper;

import me.rubix327.marketplacehelper.data.BundleSource;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class MarketPlaceHelperBundle {

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getVersion();
    public abstract void init(BundleSource source);

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

    @Override
    public String toString() {
        return "MarketPlaceHelperBundle{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", version='" + getVersion() + '\'' +
                '}';
    }
}
