package com.management.api.specification;


import com.management.api.domain.Users;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class UserPredicate  implements Specification<Users> {
    private Users user;
    private String username;
    private Long id;

    public UserPredicate(String username, Long id) {
        this.username = username;
        this.id = id;
    }

    public UserPredicate(Users user) {
        this.user = user;
    }

    @Override
    public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
       Predicate p =cb.and();
       if(user !=null){
           if(user.getId()!=null){
               p.getExpressions().add(cb.equal(root.get("id"),user.getId()));
           }

           if(user.getFirst_name() != null){
               p.getExpressions().add(cb.equal(root.get("first_name"),user.getFirst_name()));
           }
           if(user.getLast_name() != null){
               p.getExpressions().add(cb.equal(root.get("last_name"),user.getLast_name()));
           }

           if(user.getOther_name() != null){
               p.getExpressions().add(cb.equal(root.get("other_name"),user.getOther_name()));
           }
           if(user.getPhone_number() != null){
               p.getExpressions().add(cb.equal(root.get("phone_number"),user.getPhone_number()));
           }
           if(user.getUsername() != null){
               p.getExpressions().add(cb.equal(root.get("username"),user.getUsername()));
           }

           if(user.getPassword() != null){
               p.getExpressions().add(cb.equal(root.get("password"),user.getPassword()));
           }




       }

       if (username != null){
           p.getExpressions().add(cb.equal(cb.upper(root.get("username")),username.toUpperCase(Locale.ROOT)));
       }
       if(id != null){
        p.getExpressions().add(cb.equal(root.get("id"),id));
    }


       return p;
    }
}
