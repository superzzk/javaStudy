package zzk.study.java.core.util.collection.map.hashing;

public class MemberWithBadHashing extends Member {
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
