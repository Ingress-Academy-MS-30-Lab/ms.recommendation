    package az.ingress.dao.entity;

    import az.ingress.model.enums.RecommendationSourceType;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import javax.persistence.Entity;
    import javax.persistence.EnumType;
    import javax.persistence.Enumerated;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    import javax.persistence.Table;

    import java.time.LocalDateTime;
    import java.util.Objects;

    import static javax.persistence.EnumType.STRING;
    import static javax.persistence.GenerationType.IDENTITY;

    @Entity
    @Table(name = "category_based_recommendations_events")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class RecommendationEventEntity {

        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Long id;

        private Long userId;
        private Long categoryId;

        @Enumerated(STRING)
        private RecommendationSourceType sourceType;

        private Double weight;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof RecommendationEventEntity that)) return false;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }