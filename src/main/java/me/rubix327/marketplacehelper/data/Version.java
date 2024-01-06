package me.rubix327.marketplacehelper.data;

import lombok.Getter;

import java.util.Objects;

@Getter
@SuppressWarnings("unused")
public class Version implements Comparable<Version> {

    private final int major;
    private final int minor;
    private final int sub;

    public Version(int major, int minor, int sub) {
        this.major = major;
        this.minor = minor;
        this.sub = sub;
    }

    public Version(String version) throws NumberFormatException{
        int firstPeriod = version.indexOf(".");
        int secondPeriod = version.indexOf(".", firstPeriod + 1);

        this.major = Integer.parseInt(version.substring(0, firstPeriod));
        this.minor = Integer.parseInt(version.substring(firstPeriod + 1, secondPeriod));
        this.sub = Integer.parseInt(version.substring(secondPeriod + 1));
    }

    @Override
    public int compareTo(Version o) {
        if (major < o.getMajor()) return -1;
        if (major > o.getMajor()) return 1;

        if (minor < o.getMinor()) return -1;
        if (minor > o.getMinor()) return 1;

        return Integer.compare(sub, o.getSub());
    }

    public boolean isHigherThan(Version o){
        return compareTo(o) > 0;
    }

    public boolean isLessThan(Version o){
        return compareTo(o) < 0;
    }

    public boolean isSameAs(Version o){
        return compareTo(o) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return major == version.major && minor == version.minor && sub == version.sub;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, sub);
    }
}
