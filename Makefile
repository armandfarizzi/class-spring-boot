log-spring:
	@docker logs -f --tail 25 spring
dev:
	./mvnw spring-boot:run
image:
	./mvnw spring-boot:build-image