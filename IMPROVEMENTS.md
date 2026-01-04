# Funko Rocks ShoppingCart - Feature Improvements

This document provides setup instructions for the improved Funko Rocks ShoppingCart application with Keycloak integration and enhanced features.

## 🚀 Quick Start

### Prerequisites
- Docker and Docker Compose
- Java 21+
- Maven 3.6+

### Setup Instructions

1. **Clone the repository**:
   ```bash
   git checkout feature/improvements
   ```

2. **Start Keycloak**:
   ```bash
   # Create volume directories
   mkdir -p keycloak-imports
   
   # Start Keycloak with PostgreSQL
   docker-compose -f docker-compose.keycloak.yml up -d
   
   # Wait for Keycloak to start (2-3 minutes)
   docker-compose -f docker-compose.keycloak.yml logs -f keycloak
   ```

3. **Import Keycloak Realm**:
   - Access Keycloak Admin Console: http://localhost:8081/admin
   - Login with: admin / admin123
   - Navigate to "Realm Settings" → "Import"
   - Select the `keycloak-realm.json` file
   - Click "Import"

4. **Run the application**:
   ```bash
   # Set environment variables
   export KEYCLOAK_CLIENT_SECRET="shoppingcart-secret-2024"
   
   # Run with Maven (if available) or IDE
   ./mvnw spring-boot:run
   ```

## 🏗️ Architecture Overview

### Enhanced Components

#### 1. Session Management
- **Spring Session JDBC** with H2 database storage
- **30-minute session timeout** with automatic cleanup
- **Session persistence** across application restarts

#### 2. JPA Persistence Layer
- **Product Entity**: Stored in H2 database
- **ShoppingCart Entity**: Related to CommerceItems
- **CommerceItem Entity**: Linked to Product and ShoppingCart
- **Automatic schema creation** with Hibernate DDL

#### 3. Keycloak Integration
- **OIDC/OAuth2** authentication
- **Role-based access control** (CUSTOMER, ADMINISTRATOR)
- **User attributes** for personalization
- **Single Logout (SLO)** support

#### 4. Security Features
- **JWT token storage** in HTTP-only cookies
- **CORS configuration** for frontend integration
- **CSRF protection** enabled
- **Session fixation** prevention

## 🔐 Security Configuration

### Keycloak Users

#### Test Customer
- **Email**: customer@funkorocks.com
- **Password**: customer123
- **Role**: CUSTOMER
- **Attributes**: PREMIUM customer with favorite artists

#### Test Administrator  
- **Email**: admin@funkorocks.com
- **Password**: admin123
- **Role**: ADMINISTRATOR
- **Attributes**: Full system access

### OAuth2 Configuration
- **Client ID**: shoppingcart-service
- **Client Secret**: shoppingcart-secret-2024
- **Redirect URIs**: http://localhost:8080/*, http://localhost:3000/*
- **Scopes**: funkorocks.read, funkorocks.write, funkorocks.profile

## 🎯 API Endpoints

### Public Endpoints
- `GET /products` - List all products
- `GET /actuator/health` - Health check

### Protected Endpoints (Requires Authentication)
- `GET /shoppingcart` - Get user's shopping cart
- `POST /shoppingcart/items` - Add item to cart
- `DELETE /shoppingcart/items/{id}` - Remove item from cart
- `POST /logout` - Single logout with Keycloak

### Admin Endpoints (Requires ADMINISTRATOR Role)
- `GET /admin/products` - Product management (future)
- `GET /admin/analytics` - Sales analytics (future)

## 🛠️ Development Guide

### Running Tests
```bash
# Unit tests
./mvnw test

# Integration tests  
./mvnw verify

# All tests
./mvnw clean verify
```

### Code Quality
```bash
# Checkstyle validation
./mvnw checkstyle:check

# Generate sources and Javadoc
./mvnw source:jar javadoc:jar
```

### Database Access
- **H2 Console**: http://localhost:8080/h2-console
- **JDBC URL**: jdbc:h2:mem:testdb
- **Username**: sa
- **Password**: (empty)

## 🐳 Docker Deployment

### Build Image
```bash
# Build optimized image
./mvnw clean package -Pprod dockerfile:build

# Tag and push
docker tag shoppingcart-backend:latest your-registry/shoppingcart-backend:latest
docker push your-registry/shoppingcart-backend:latest
```

### Kubernetes Deployment
```bash
# Apply updated manifests
kubectl apply -f src/main/resources/kubernetes/

# Check deployment status
kubectl get pods -l app=shoppingcart
```

## 🔧 Configuration

### Environment Variables
- `KEYCLOAK_CLIENT_SECRET` - OAuth2 client secret
- `SPRING_PROFILES_ACTIVE` - Active profile (dev/test/prod)
- `DATABASE_URL` - Custom database URL (production)

### Application Properties
See `src/main/resources/application.yml` for complete configuration.

## 🧪 Testing the Setup

### 1. Verify Keycloak
```bash
# Check Keycloak health
curl http://localhost:8081/realms/funkorocks/.well-known/openid-configuration

# Test token endpoint
curl -X POST http://localhost:8081/realms/funkorocks/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password&client_id=shoppingcart-service&client_secret=shoppingcart-secret-2024&username=customer@funkorocks.com&password=customer123"
```

### 2. Verify Application
```bash
# Test public endpoint
curl http://localhost:8080/products

# Test protected endpoint (after getting token)
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     http://localhost:8080/shoppingcart
```

## 🚨 Troubleshooting

### Common Issues

#### Session Not Working
- Check if `spring-session-jdbc` dependency is in pom.xml
- Verify database connection in application.yml
- Check browser cookies for JSESSIONID

#### Keycloak Connection Issues
- Verify Keycloak is running: `docker-compose ps`
- Check realm configuration matches client settings
- Verify client secret is correct

#### Database Problems
- Access H2 console to inspect data
- Check Hibernate logs for DDL errors
- Verify JPA entity relationships

#### CORS Errors
- Check frontend URL in Keycloak client configuration
- Verify CORS settings in BackendApplication.java
- Ensure proper preflight requests are handled

## 📊 Performance Considerations

### Session Optimization
- Session timeout set to 30 minutes
- JDBC session storage with connection pooling
- Automatic session cleanup on expiry

### Database Performance  
- H2 in-memory for development
- Connection pooling configured
- Lazy loading for relationships where appropriate

### Security Hardening
- HTTP-only cookies for JWT tokens
- CSRF protection enabled
- Secure redirect URIs in Keycloak

## 🔄 Migration Guide

### From Original Implementation
1. **Session Management**: Static session handling → Spring Session JDBC
2. **Data Storage**: In-memory products → JPA entities with H2
3. **Security**: No security → Keycloak OIDC integration
4. **Testing**: Basic tests → Comprehensive test coverage
5. **Deployment**: Basic Docker → Optimized containers with probes
6. **Architecture**: Mixed reactive/traditional → Consistent traditional MVC

### Database Migration
```sql
-- Existing data will be automatically migrated
-- New tables: products, commerce_items, shopping_carts, spring_session_tables
```

### Production Environment
- **HTTPS Required**: Set `secure: true` for production cookies
- **Proper CORS**: Configure specific frontend domains only
- **Resource Scaling**: Configured with limits and requests
- **Health Monitoring**: Liveness and readiness probes configured
- **Secret Management**: Use Kubernetes secrets for sensitive data

### Authentication Flow
1. **Login**: OAuth2 authorization code flow with Keycloak
2. **Token Storage**: HTTP-only cookies with 30-minute expiration
3. **Session Sync**: User ID-based cart keys
4. **Single Logout**: Full SLO support with Keycloak integration
5. **Authorization**: Role-based access (CUSTOMER, ADMINISTRATOR)

### API Security
- **Public Endpoints**: `/products/**`, `/actuator/health/**`
- **Customer Endpoints**: `/shoppingcart/**` (requires CUSTOMER role)
- **Admin Endpoints**: `/admin/**` (requires ADMINISTRATOR role)
- **JWT Claims**: customerType, favoriteArtists, preferredGenres, totalSpent

### Database Migration
```sql
-- Existing data will be automatically migrated
-- New tables: products, commerce_items, shopping_carts, spring_session_tables
```

## 📚 Documentation

- [API Documentation](http://localhost:8080/swagger-ui.html) - Auto-generated
- [Keycloak Documentation](http://localhost:8081/docs/) - Identity provider
- [H2 Documentation](http://localhost:8080/h2-console) - Database access

## 🤝 Contributing

1. Follow the existing code style (checkstyle.xml)
2. Add unit tests for new features  
3. Update documentation for API changes
4. Test Keycloak integration thoroughly
5. Use meaningful commit messages

## 📝 License

This project maintains the same license as the original Funko Rocks ShoppingCart application.