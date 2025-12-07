package com.jose30a2.dscommerce.repositories;


import com.jose30a2.dscommerce.entities.User;
import com.jose30a2.dscommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepossitory extends JpaRepository<User, Long> {

    //  Se realiza una consulta raiz para que al acceder al usuario JPA tb me traiga
    //    la lista de roles, ya que de otra manera los cargaria Lazy y daria problemas
    //    con Spring Security en el momento de querer cargar los roles
    @Query(nativeQuery = true, value = "SELECT tb_user.email AS username, tb_user.password, " +
        "tb_role.id AS roleId, tb_role.authority " +
            "FROM tb_user " +
            "INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id " +
            "INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id " +
            "WHERE tb_user.email = :email")
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
}
