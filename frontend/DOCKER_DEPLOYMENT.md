# Docker Deployment Guide

## Multi-Stage Dockerfile

This application uses a 3-stage Docker build:

### Stage 1: Dependencies
- Base: `node:18-alpine`
- Installs production dependencies only
- Optimized for caching

### Stage 2: Build
- Base: `node:18-alpine`
- Installs all dependencies
- Builds Angular application for production

### Stage 3: Production
- Base: `nginx:1.25-alpine`
- Serves built application
- Runs as non-root user
- Includes health checks

## Build & Run

### Build Docker Image
```bash
docker build -t course-platform-frontend .
```

### Run Container
```bash
docker run -d -p 4200:80 --name frontend course-platform-frontend
```

### Using Docker Compose
```bash
# Build and start
docker-compose up -d

# Stop
docker-compose down

# View logs
docker-compose logs -f

# Rebuild
docker-compose up -d --build
```

## Configuration

### Environment Variables
- `NODE_ENV=production` - Production mode

### Ports
- Container: `80`
- Host: `4200`

### Backend Connection
Update `nginx.conf` to point to your backend:
```nginx
proxy_pass http://your-backend-host:8080/api/;
```

## Image Size Optimization

Multi-stage build reduces image size:
- Development dependencies excluded
- Only production build included
- Alpine Linux base (minimal size)
- Final image: ~50MB

## Security Features

- Non-root user execution
- Minimal Alpine base
- Health checks enabled
- Production-optimized build

## Access Application

Open browser: `http://localhost:4200`
