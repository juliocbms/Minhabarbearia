package com.minhabarbearia.barbearia.models.repository.specification;

import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoSpecification {

    public static Specification<AgendamentoEntity> filtrar(Long id, LocalDate dataInicio, LocalDate dataFim, String status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por usuário
            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("usuario").get("id"), id));
            }

            if (dataInicio != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("data"), dataInicio));
            }
            if (dataFim != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("data"), dataFim));
            }
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}