package com.github.fernandoteixxeira.roles.unittest.dataprovider.role;

import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;
import com.github.fernandoteixxeira.roles.dataprovider.role.RoleRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration test to find all RoleORM on database")
@DataJpaTest
public class RoleRepositoryAllFinderTest {

    @Autowired
    RoleRepository roleRepository;

    @DisplayName("When find all on database then return a list of RoleORM with three or more records")
    @Test
    void when_find_all_on_database_then_return_a_list_of_RoleORM_with_three_or_more_records() {
        val result = roleRepository.findAll();
        assertThat(result).isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(3)
                .extracting(RoleORM::getId)
                .contains(PRODUCT_OWNER_ID);
    }
}
