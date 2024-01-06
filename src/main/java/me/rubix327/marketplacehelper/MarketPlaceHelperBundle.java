package me.rubix327.marketplacehelper;

import lombok.Getter;
import lombok.NonNull;
import me.rubix327.marketplacehelper.data.BundleResult;
import me.rubix327.marketplacehelper.data.BundleSource;

import java.util.Objects;

@Getter
@SuppressWarnings("unused")
public abstract class MarketPlaceHelperBundle {

    @NonNull
    protected MarketPlaceHelperLogger logger;

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getVersion();
    public abstract BundleResult init(BundleSource source);

    public MarketPlaceHelperBundle() {
        this.logger = new MarketPlaceHelperLogger();
    }

    public MarketPlaceHelperBundle(MarketPlaceHelperLogger logger) {
        this.logger = logger;
    }

    public void setLogger(@NonNull MarketPlaceHelperLogger logger) {
        this.logger = logger;
    }

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
