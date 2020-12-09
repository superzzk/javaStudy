package zzk.study.java.core.designpattern.criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-01 19:25
 **/
public class CriteriaMale implements Criteria {
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> malePersons = new ArrayList<Person>();
        for (Person person : persons) {
            if(person.getGender().equalsIgnoreCase("MALE")){
                malePersons.add(person);
            }
        }
        return malePersons;
    }
}
