// Detect if running in production (served by Nginx)
const isProduction = window.location.hostname !== 'localhost' || window.location.port === '80' || window.location.port === '';

export const environment = {
  production: isProduction,
  // In production (Docker), use relative URL (proxied by Nginx)
  // In development, use direct backend URL
  apiUrl: isProduction ? '' : 'http://localhost:8080'
};
