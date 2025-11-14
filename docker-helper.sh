#!/bin/bash
# Docker Helper Script - Servants Management System
# Helper script para facilitar operações comuns com Docker

set -e

PROJECT_NAME="servants-management"
COMPOSE_FILE="docker-compose.yml"

# Cores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

function print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

function print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

function print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

function show_help() {
    cat << EOF
Docker Helper Script - Servants Management System

Usage: ./docker-helper.sh [COMMAND]

Commands:
  start       - Build e start todos os containers
  stop        - Para todos os containers
  restart     - Restart todos os containers
  logs        - Mostra logs de todos os containers
  status      - Mostra status dos containers
  clean       - Para e remove containers, volumes e imagens
  rebuild     - Rebuild completo sem cache
  db-shell    - Abre shell no PostgreSQL
  backend-logs - Mostra logs do backend
  frontend-logs - Mostra logs do frontend
  health      - Verifica health dos serviços
  help        - Mostra esta mensagem

Examples:
  ./docker-helper.sh start
  ./docker-helper.sh logs
  ./docker-helper.sh health

EOF
}

function check_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker não está instalado!"
        exit 1
    fi

    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose não está instalado!"
        exit 1
    fi
}

function start_services() {
    print_info "Iniciando serviços..."
    docker-compose up --build -d
    print_info "Serviços iniciados!"
    print_info "Frontend: http://localhost"
    print_info "Backend: http://localhost:8080"
    print_info "Swagger: http://localhost:8080/swagger-ui.html"
}

function stop_services() {
    print_info "Parando serviços..."
    docker-compose stop
    print_info "Serviços parados!"
}

function restart_services() {
    print_info "Reiniciando serviços..."
    docker-compose restart
    print_info "Serviços reiniciados!"
}

function show_logs() {
    print_info "Mostrando logs (Ctrl+C para sair)..."
    docker-compose logs -f
}

function show_status() {
    print_info "Status dos containers:"
    docker-compose ps
}

function clean_all() {
    print_warning "Esta operação irá remover containers, volumes e imagens!"
    read -p "Tem certeza? (s/N): " confirm
    if [[ $confirm =~ ^[Ss]$ ]]; then
        print_info "Removendo tudo..."
        docker-compose down -v --rmi all
        print_info "Limpeza completa!"
    else
        print_info "Operação cancelada."
    fi
}

function rebuild_all() {
    print_info "Rebuild completo sem cache..."
    docker-compose build --no-cache
    docker-compose up -d
    print_info "Rebuild concluído!"
}

function db_shell() {
    print_info "Abrindo shell PostgreSQL..."
    docker-compose exec postgres psql -U postgres -d servants_db
}

function backend_logs() {
    print_info "Logs do backend (Ctrl+C para sair)..."
    docker-compose logs -f backend
}

function frontend_logs() {
    print_info "Logs do frontend (Ctrl+C para sair)..."
    docker-compose logs -f frontend
}

function check_health() {
    print_info "Verificando health dos serviços..."

    echo ""
    print_info "PostgreSQL:"
    docker-compose exec postgres pg_isready -U postgres || print_error "PostgreSQL não está respondendo"

    echo ""
    print_info "Backend:"
    curl -s http://localhost:8080/health | python -m json.tool || print_error "Backend não está respondendo"

    echo ""
    print_info "Frontend:"
    curl -s -o /dev/null -w "Status: %{http_code}\n" http://localhost/ || print_error "Frontend não está respondendo"
}

# Main
check_docker

case "${1:-help}" in
    start)
        start_services
        ;;
    stop)
        stop_services
        ;;
    restart)
        restart_services
        ;;
    logs)
        show_logs
        ;;
    status)
        show_status
        ;;
    clean)
        clean_all
        ;;
    rebuild)
        rebuild_all
        ;;
    db-shell)
        db_shell
        ;;
    backend-logs)
        backend_logs
        ;;
    frontend-logs)
        frontend_logs
        ;;
    health)
        check_health
        ;;
    help|*)
        show_help
        ;;
esac
