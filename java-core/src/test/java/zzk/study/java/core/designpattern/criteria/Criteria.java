package zzk.study.java.core.designpattern.criteria;

import java.util.List;

public interface Criteria {
    public List<Person> meetCriteria(List<Person> persons);
}
